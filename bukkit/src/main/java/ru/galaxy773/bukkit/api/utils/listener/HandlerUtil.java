package ru.galaxy773.bukkit.api.utils.listener;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;

public class HandlerUtil {

    private static final Map<Class<? extends Event>, HandlerList> HANDLER_LIST_CACHE = new HashMap<>();

    public static HandlerList parseHandlerList(Class<? extends Event> eventClass) {
        return HANDLER_LIST_CACHE.computeIfAbsent(eventClass, __ ->
                (HandlerList) ReflectionUtil.getMethodReturn(eventClass, "getHandlerList"));
    }
}
