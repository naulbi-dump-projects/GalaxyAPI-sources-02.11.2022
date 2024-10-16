package ru.galaxy773.bukkit.api.utils.listener;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.TimedRegisteredListener;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public class FastEvent<E extends Event> {

    private final GalaxyEventExecutor<E> eventExecutor;
    private final EventPriority priority;
    private final String identifier;
    private final boolean ignoreCancelled;
    private RegisteredListener registeredListener;

    public static <E extends Event> Builder<E> builder(Class<? extends E> eventClass) {
        return new Builder<E>((Class)eventClass);
    }

    RegisteredListener toRegisteredListener(Plugin plugin, Listener listener) {
        if (this.registeredListener == null) {
            if (Bukkit.getPluginManager().useTimings()) {
                this.registeredListener = new TimedRegisteredListener(listener, this.eventExecutor, this.priority, plugin, this.ignoreCancelled);
            }
            else {
                this.registeredListener = new RegisteredListener(listener, this.eventExecutor, this.priority, plugin, this.ignoreCancelled);
            }
        }
        return this.registeredListener;
    }

    public static class Builder<E extends Event> {

        private final Class<? extends E> eventClass;
        private Consumer<E> handlingConsumer;
        private EventPriority priority;
        private boolean ignoreCancelled;
        private String identifier;
        private Predicate<E> filter;

        private Builder(Class<? extends E> eventClass) {
            this.priority = EventPriority.NORMAL;
            this.ignoreCancelled = false;
            this.identifier = "DefaultFastListener";
            this.eventClass = eventClass;
        }

        public Builder<E> handler(Consumer<? super E> handlingConsumer) {
            if (this.handlingConsumer == null) {
                this.handlingConsumer = handlingConsumer::accept;
            }
            else {
                this.handlingConsumer = this.handlingConsumer.andThen(handlingConsumer);
            }
            return this;
        }

        public Builder<E> optionalHandler(Consumer<Optional> handlingConsumer) {
            return this.handler(event -> handlingConsumer.accept(Optional.of(event)));
        }

        public Builder<E> priority(EventPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder<E> ignoreCancelled(boolean ignoreCancelled) {
            this.ignoreCancelled = ignoreCancelled;
            return this;
        }

        public Builder<E> identifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder<E> filter(Predicate<? super E> predicate) {
            if (this.filter == null) {
                this.filter = predicate::test;
            }
            else {
                this.filter = this.filter.and(predicate);
            }
            return this;
        }

        public FastEvent<E> build() {
            Preconditions.checkState(this.handlingConsumer != null, "Handling consumer not initialized!");
            return new FastEvent<E>(new GalaxyEventExecutor(this.eventClass, event -> {
                if (this.filter == null || this.filter.test((E)event)) {
                    this.handlingConsumer.accept((E)event);
                }
            }), this.priority, this.identifier, this.ignoreCancelled);
        }
    }
}
