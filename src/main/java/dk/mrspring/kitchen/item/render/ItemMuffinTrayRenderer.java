package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.ItemMuffin;
import dk.mrspring.kitchen.model.ModelMuffinTray;
import dk.mrspring.kitchen.tileentity.TileEntityMuffinTray;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created on 14-11-2015 for TheKitchenMod.
 */
public class ItemMuffinTrayRenderer implements IItemRenderer
{
    ModelMuffinTray model = new ModelMuffinTray();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/muffin_tray.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case INVENTORY:
            case ENTITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case BLOCK_3D:
                return type != ItemRenderType.ENTITY;
            case INVENTORY_BLOCK:
                return type == ItemRenderType.INVENTORY;
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return type == ItemRenderType.ENTITY;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType renderType, ItemStack item, Object... data)
    {
        Tray tray = new Tray(item);
        boolean[] filled = new boolean[tray.getMuffinCount()];
        float[][] colors = new float[tray.getMuffinCount()][];
        for (int i = 0; i < filled.length; i++)
        {
            ItemStack inSlot = tray.getInSlot(i);
            if (filled[i] = inSlot != null)
            {
                String type = ItemMuffin.getMuffinType(inSlot);
                if (type != null) colors[i] = ItemRenderMuffin.COLOR_HANDLER.getColorAsRGB(type);
                else colors[i] = new float[]{1F, 1F, 1F};
            }
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(35, 0F, 0F, 1F);
                GL11.glTranslatef(.6F, .8F, -.5F);

                float scale = 1.5F;

                GL11.glScalef(1, scale, scale);

                GL11.glTranslatef(.5F, .5F, .5F);
                break;
            case EQUIPPED:
//                GL11.glRotatef(180, 1, 0, 0);
//                GL11.glRotatef(-10, 0, 0, 1);
//                GL11.glTranslatef(.6F, -1.5F, .09F);
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glRotatef(25F, 0F, 0F, -1F);
                GL11.glRotatef(5, 0, -1, 0);
                GL11.glTranslatef(-0.875F, 1.2F, -0.05F);

                scale = 1.2F;

                GL11.glScalef(scale, scale, scale);
                break;
            case INVENTORY:
                GL11.glRotatef(180, 0, 1, 0);
                scale = 1.5F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0, 1.1F, -0.1F);
                break;
            case ENTITY:
                scale = 1.2F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                GL11.glTranslatef(0F, 0F, -0.1F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, filled, colors);
        GL11.glPopMatrix();
    }

    private class Tray
    {
        ItemStack[] stacks;

        public Tray(ItemStack input)
        {
            stacks = new ItemStack[6];
            if (!input.hasTagCompound()) input.setTagCompound(new NBTTagCompound());
            NBTTagCompound trayInfo = input.getTagCompound().getCompoundTag(TileEntityMuffinTray.MUFFIN_TRAY_INFO);
            NBTTagList itemList = trayInfo.getTagList(TileEntityMuffinTray.ITEM_LIST, 10);
            for (int i = 0; i < itemList.tagCount(); i++)
            {
                NBTTagCompound itemCompound = itemList.getCompoundTagAt(i);
                int slot = itemCompound.getInteger(TileEntityMuffinTray.ITEM_SLOT);
                ItemStack stack = ItemStack.loadItemStackFromNBT(itemCompound);
                stacks[slot] = stack;
            }
        }

        public int getMuffinCount()
        {
            return stacks.length;
        }

        public ItemStack getInSlot(int slot)
        {
            return stacks[slot];
        }
    }
}