package ru.galaxy773.multiplatform.api.utils;

import lombok.NonNull;

import java.util.function.Supplier;

public interface Lazy<T> extends Supplier<T> {

    T get();

    boolean isInitialized();

    default boolean init() {
        if (isInitialized()) {
            return false;
        }
        get();
        return true;
    }

    public static <T> Lazy<T> create(Supplier<T> initalizingClass) {
        return new SimpleLazy<T>(initalizingClass);
    }

    public static class SimpleLazy<T> implements Lazy<T> {

        private Supplier<T> initializingClass;
        private T initializedClass;

        protected SimpleLazy(@NonNull Supplier<T> initializingClass) {
            this.initializingClass = initializingClass;
        }

        @Override
        public T get() {
            if (!this.isInitialized()) {
                this.initializedClass = initializingClass.get();
                this.initializingClass = null;
            }
            return this.initializedClass;
        }

        @Override
        public boolean isInitialized() {
            return this.initializedClass != null;
        }
    }
}
