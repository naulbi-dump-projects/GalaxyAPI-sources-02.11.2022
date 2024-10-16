package ru.galaxy773.bukkit.api.utils.listener;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.util.function.Consumer;

class GalaxyEventExecutor<E extends Event> implements EventExecutor {

    private final Class<? extends E> eventClass;
    private final Consumer<? super E> executingConsumer;

    GalaxyEventExecutor(Class<? extends E> eventClass, Consumer<? super E> executingConsumer) {
        this.eventClass = eventClass;
        this.executingConsumer = executingConsumer;
    }

    public void execute(Listener listener, Event event) {
        if (!this.eventClass.isInstance(event)) {
            return;
        }
        this.executingConsumer.accept(this.eventClass.cast(event));
    }

    Class<? extends E> getEventClass() {
        return this.eventClass;
    }
}
