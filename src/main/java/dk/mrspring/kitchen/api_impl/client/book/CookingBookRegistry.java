package dk.mrspring.kitchen.api_impl.client.book;

import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api.book.ICookingBookRegistry;
import dk.mrspring.kitchen.api_impl.client.book.handler.CuttingBoardHandler;
import dk.mrspring.kitchen.api_impl.client.book.handler.OvenHandler;
import dk.mrspring.kitchen.api_impl.client.book.handler.PanHandler;
import dk.mrspring.kitchen.api_impl.client.book.handler.TableOfContentHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public class CookingBookRegistry implements ICookingBookRegistry
{
    private static CookingBookRegistry ourInstance = new CookingBookRegistry();

    public static CookingBookRegistry getInstance()
    {
        return ourInstance;
    }

    private Map<String, IChapterHandler> registeredHandlers;

    private CookingBookRegistry()
    {
        registeredHandlers = new LinkedHashMap<String, IChapterHandler>();

        registerChapterHandler("tableofcontent", new TableOfContentHandler());
        registerChapterHandler(CuttingBoardHandler.ID, new CuttingBoardHandler());
        registerChapterHandler("oven", new OvenHandler());
        registerChapterHandler("pan", new PanHandler());
    }

    @Override
    public void registerChapterHandler(String id, IChapterHandler handler)
    {
        if (!registeredHandlers.containsKey(id))
            registeredHandlers.put(id, handler);
    }

    @Override
    public IChapterHandler getChapterHandler(String id)
    {
        return registeredHandlers.get(id);
    }

    public IChapterHandler[] getRegisteredHandlers()
    {
        Collection<IChapterHandler> handlers = registeredHandlers.values();
        return handlers.toArray(new IChapterHandler[handlers.size()]);
    }
}
