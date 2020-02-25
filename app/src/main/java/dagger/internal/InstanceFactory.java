package dagger.internal;

import dagger.Lazy;

public final class InstanceFactory<T> implements Factory<T>, Lazy<T> {
    private static final InstanceFactory<Object> NULL_INSTANCE_FACTORY = new InstanceFactory<>(null);
    private final T instance;

    private InstanceFactory(T instance2) {
        this.instance = instance2;
    }

    public static <T> Factory<T> create(T instance2) {
        return new InstanceFactory(Preconditions.checkNotNull(instance2, "instance cannot be null"));
    }

    public static <T> Factory<T> createNullable(T instance2) {
        if (instance2 == null) {
            return nullInstanceFactory();
        }
        return new InstanceFactory(instance2);
    }

    private static <T> InstanceFactory<T> nullInstanceFactory() {
        return NULL_INSTANCE_FACTORY;
    }

    public T get() {
        return this.instance;
    }
}
