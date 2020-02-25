package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collections;
import java.util.Set;

@GwtCompatible
final class Absent<T> extends Optional<T> {
    static final Absent<Object> INSTANCE = new Absent<>();
    private static final long serialVersionUID = 0;

    private Absent() {
    }

    static <T> Optional<T> withType() {
        return INSTANCE;
    }

    public boolean isPresent() {
        return false;
    }

    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    /* renamed from: or */
    public T mo22987or(T defaultValue) {
        return Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
    }

    /* renamed from: or */
    public Optional<T> mo22985or(Optional<? extends T> secondChoice) {
        return (Optional) Preconditions.checkNotNull(secondChoice);
    }

    /* renamed from: or */
    public T mo22986or(Supplier<? extends T> supplier) {
        return Preconditions.checkNotNull(supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
    }

    @NullableDecl
    public T orNull() {
        return null;
    }

    public Set<T> asSet() {
        return Collections.emptySet();
    }

    public <V> Optional<V> transform(Function<? super T, V> function) {
        Preconditions.checkNotNull(function);
        return Optional.absent();
    }

    public boolean equals(@NullableDecl Object object) {
        return object == this;
    }

    public int hashCode() {
        return 2040732332;
    }

    public String toString() {
        return "Optional.absent()";
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
