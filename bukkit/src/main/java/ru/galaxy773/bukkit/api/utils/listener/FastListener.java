package ru.galaxy773.bukkit.api.utils.listener;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.function.Consumer;

public class FastListener implements Listener {
    
    private final Multimap<Class<? extends Event>, FastEvent<?>> FastEventMap;

    private FastListener() {
        this.FastEventMap = ArrayListMultimap.create();
    }

    public static FastListener create() {
        return new FastListener();
    }

    public FastListener event(FastEvent<?> event) {
        this.FastEventMap.put(event.getEventExecutor().getEventClass(), event);
        return this;
    }

    public FastListener event(FastEvent<?> event, boolean needRegister) {
        if (needRegister) {
            this.event(event);
        }
        return this;
    }

    public <E extends Event> FastListener easyEvent(Class<E> eventClass, Consumer<E> handler) {
        return this.event(FastEvent.builder(eventClass).handler(handler).build());
    }

    public <E extends Event> FastListener easyEvent(Class<E> eventClass, Consumer<E> handler, boolean needRegister) {
        if (needRegister) {
            this.easyEvent(eventClass, handler);
        }
        return this;
    }

    public ListenerResponse register(Plugin plugin) {
        this.FastEventMap.values().forEach(fastEvent -> {
            RegisteredListener registeredListener = fastEvent.toRegisteredListener(plugin, (Listener)this);
            Class<? extends Event> eventClass = fastEvent.getEventExecutor().getEventClass();
            HandlerUtil.parseHandlerList(eventClass).register(registeredListener);
            return;
        });
        return new ListenerResponse(this.FastEventMap);
    }
}
