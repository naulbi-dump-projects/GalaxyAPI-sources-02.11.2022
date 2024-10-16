package ru.galaxy773.bukkit.api.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    protected GEvent(boolean async) {
        super(async);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }


    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
