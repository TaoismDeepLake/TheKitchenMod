package dk.mrspring.kitchen.api.event;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public class BoardEventRegistry
{
	static Map<String, IBoardEvent> onAddedToBoardEvents = new HashMap<String, IBoardEvent>();
	static Map<String, IBoardEvent> onBoardRightClickedEvents = new HashMap<String, IBoardEvent>();
	static Map<String, IBoardEvent> topItemEvents = new HashMap<String, IBoardEvent>();

	static IOnAddedToBoardEvent defaultOnAddedToBoardEvent = new IOnAddedToBoardEvent()
	{
		@Override
		public void onAdded(List<ItemStack> layers, ItemStack added, NBTTagCompound specialTagInfo)
		{

		}

		@Override
		public boolean canAdd(List<ItemStack> currentLayers, ItemStack toAdd, NBTTagCompound specialTagInfo)
		{
			return true;
		}

		@Override
		public String getEventName()
		{
			return "on_added-default";
		}
	};
	static IOnBoardRightClickedEvent defaultOnBoardRightClickedEvent = new IOnBoardRightClickedEvent()
	{
		@Override
		public void onRightClicked(List<ItemStack> layers, ItemStack rightClicked, NBTTagCompound specialTagInfo)
		{
		}

		@Override
		public String getEventName()
		{
			return "on_right_click-default";
		}
	};
	static ITopItemEvent defaultTopItemEvent = new ITopItemEvent()
	{
		@Override
		public boolean canAddItemOnTop(List<ItemStack> layers, ItemStack tryingToAdd, NBTTagCompound specialTagInfo)
		{
			System.out.println("Returning true in canAddItemOnTop()");
			return true;
		}

		@Override
		public ItemStack getDroppeditem(List<ItemStack> layers, ItemStack removed, NBTTagCompound specialTagInfo)
		{
			return removed;
		}

		@Override
		public String getEventName()
		{
			return "top_item-default";
		}
	};

	public static void registerTopItemEvent(String itemName, IBoardEvent event)
	{
		if (itemName != null && !topItemEvents.containsKey(itemName))
			topItemEvents.put(itemName, event);
	}

	public static void registerTopItemEvent(Item item, IBoardEvent event)
	{
		registerTopItemEvent(GameRegistry.findUniqueIdentifierFor(item).toString(), event);
	}

	public static void registerTopItemEvent(Block block, IBoardEvent event)
	{
		registerTopItemEvent(GameRegistry.findUniqueIdentifierFor(block).toString(), event);
	}


	public static void registerOnRightClickedEvent(String itemName, IBoardEvent event)
	{
		if (itemName != null && !onBoardRightClickedEvents.containsKey(itemName))
			onBoardRightClickedEvents.put(itemName, event);
	}

	public static void registerOnRightClickedEvent(Item item, IBoardEvent event)
	{
		registerOnRightClickedEvent(GameRegistry.findUniqueIdentifierFor(item).toString(), event);
	}

	public static void registerOnRightClickedEvent(Block block, IBoardEvent event)
	{
		registerOnRightClickedEvent(GameRegistry.findUniqueIdentifierFor(block).toString(), event);
	}


	public static void registerOnAddedEvent(String itemName, IBoardEvent event)
	{
		if (itemName != null && !onAddedToBoardEvents.containsKey(itemName))
			onAddedToBoardEvents.put(itemName, event);
	}

	public static void registerOnAddedEvent(Item item, IBoardEvent event)
	{
		registerOnAddedEvent(GameRegistry.findUniqueIdentifierFor(item).toString(), event);
	}

	public static void registerOnAddedEvent(Block block, IBoardEvent event)
	{
		registerOnAddedEvent(GameRegistry.findUniqueIdentifierFor(block).toString(), event);
	}


	public static IBoardEvent getOnAddedToBoardEventFor(String itemName)
	{
		if (onAddedToBoardEvents.containsKey(itemName))
		{
			System.out.println("Returning an onAddedToBoardEvent for " + itemName);
			return onAddedToBoardEvents.get(itemName);
		} else return getDefaultOnAddedToBoardEvent();
	}

	public static IBoardEvent getOnAddedToBoardEventFor(Item item)
	{
		return getOnAddedToBoardEventFor(GameRegistry.findUniqueIdentifierFor(item).toString());
	}


	public static IBoardEvent getOnBoardRightClickedEventFor(String itemName)
	{
		if (onBoardRightClickedEvents.containsKey(itemName))
			return onBoardRightClickedEvents.get(itemName);
		else return getDefaultOnBoardRightClickedEvent();
	}

	public static IBoardEvent getOnBoardRightClickedEventFor(Item item)
	{
		return getOnBoardRightClickedEventFor(GameRegistry.findUniqueIdentifierFor(item).toString());
	}


	public static IBoardEvent getTopItemEventFor(String itemName)
	{
		if (topItemEvents.containsKey(itemName))
			return topItemEvents.get(itemName);
		else return getDefaultTopItemEvent();
	}

	public static IBoardEvent getTopItemEventFor(Item item)
	{
		return getTopItemEventFor(GameRegistry.findUniqueIdentifierFor(item).toString());
	}

	public static IBoardEvent getTopItemEventFor(ItemStack stack)
	{
		if (stack!=null)
			return getTopItemEventFor(stack.getItem());
		else return getDefaultTopItemEvent();
	}

	public static IOnAddedToBoardEvent getDefaultOnAddedToBoardEvent()
	{
		return defaultOnAddedToBoardEvent;
	}

	public static IOnBoardRightClickedEvent getDefaultOnBoardRightClickedEvent()
	{
		return defaultOnBoardRightClickedEvent;
	}

	public static ITopItemEvent getDefaultTopItemEvent()
	{
		return defaultTopItemEvent;
	}

	public static void registerDefaultEvents()
	{
		registerOnAddedEvent(KitchenItems.butter, new IOnAddedToBoardEvent()
		{
			@Override
			public void onAdded(List<ItemStack> layers, ItemStack added, NBTTagCompound specialTagInfo)
			{
				System.out.println("Setting ClickAmount to 2!");
				specialTagInfo.setInteger("ClickAmount", 2);
			}

			@Override
			public boolean canAdd(List<ItemStack> currentLayers, ItemStack toAdd, NBTTagCompound specialTagInfo)
			{
				if (currentLayers.size() > 0)
				{
					System.out.println("There are more than 0 layers!");
					if (currentLayers.get(currentLayers.size() - 1).getItem() == KitchenItems.bread_slice)
					{
						System.out.println("The tio layer is a slice of bread!");
						return true;
					}
				}
				return false;
			}

			@Override
			public String getEventName()
			{
				return "on_added-kitchen:butter";
			}
		});
		registerTopItemEvent(KitchenItems.butter, new ITopItemEvent()
		{
			@Override
			public boolean canAddItemOnTop(List<ItemStack> layers, ItemStack tryingToAdd, NBTTagCompound specialTagInfo)
			{
				return specialTagInfo.getInteger("ClickAmount") <= 0;
			}

			@Override
			public ItemStack getDroppeditem(List<ItemStack> layers, ItemStack removed, NBTTagCompound specialTagInfo)
			{
				if (specialTagInfo.getInteger("ClickAmount")==2)
					return removed;
				else return null;
			}

			@Override
			public String getEventName()
			{
				return "top_item-kitchen:butter";
			}
		});

		registerOnRightClickedEvent(KitchenItems.butter_knife, new IOnBoardRightClickedEvent()
		{
			@Override
			public void onRightClicked(List<ItemStack> layers, ItemStack rightClicked, NBTTagCompound specialTagInfo)
			{
				if (layers.size() > 0)
					if (layers.get(layers.size() - 1).getItem() == KitchenItems.butter)
						if (specialTagInfo.hasKey("ClickAmount"))
							specialTagInfo.setInteger("ClickAmount", specialTagInfo.getInteger("ClickAmount") - 1);
			}

			@Override
			public String getEventName()
			{
				return "on_right_clicked-kitchen:butter_knife";
			}
		});
	}
}
