package ru.galaxy773.bukkit.api.utils.listener;

import com.google.common.collect.Multimap;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class ListenerResponse {
    
    private final Multimap<Class<? extends Event>, FastEvent<?>> registeredListenerMap;

    ListenerResponse(Multimap<Class<? extends Event>, FastEvent<?>> registeredListenerMap) {
        this.registeredListenerMap = registeredListenerMap;
    }

    public void enableEvent(Class<? extends Event> eventClass) {
        Collection<FastEvent<?>> registeredListeners = this.registeredListenerMap.get(eventClass);
        if (registeredListeners.isEmpty()) {
            return;
        }
        HandlerList handlerList = HandlerUtil.parseHandlerList(eventClass);
        registeredListeners.forEach(event -> handlerList.register(event.getRegisteredListener()));
    }

    public void enableEvent(Class<? extends Event> eventClass, String identefier) {
        this.registeredListenerMap.get(eventClass).stream().filter(event -> event.getIdentifier().equalsIgnoreCase(identefier)).findFirst().ifPresent(event -> HandlerUtil.parseHandlerList(eventClass).register(event.getRegisteredListener()));
    }

    public void enableAll() {
        this.registeredListenerMap.keySet().forEach(this::enableEvent);
    }

    public void disableEvent(Class<? extends Event> eventClass) {
        Collection<FastEvent<?>> registeredListeners = (Collection<FastEvent<?>>)this.registeredListenerMap.get(eventClass);
        if (registeredListeners.isEmpty()) {
            return;
        }
        HandlerList handlerList = HandlerUtil.parseHandlerList(eventClass);
        registeredListeners.forEach(event -> handlerList.unregister(event.getRegisteredListener()));
    }

    public void disableEvent(Class<? extends Event> eventClass, String identefier) {
        this.registeredListenerMap.get(eventClass).stream().filter(event -> event.getIdentifier().equalsIgnoreCase(identefier)).findFirst().ifPresent(event -> HandlerUtil.parseHandlerList(eventClass).unregister(event.getRegisteredListener()));
    }

    public void disableAll() {
        this.registeredListenerMap.keySet().forEach(this::disableEvent);
    }
}
