package com.google.common.base;

import com.google.android.libraries.performance.primes.PrimesBatteryConfigurations;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

@GwtCompatible(serializable = true)
public abstract class Optional<T> implements Serializable {
    private static final long serialVersionUID = 0;

    Optional() {
    }

    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    /* renamed from: of */
    public static <T> Optional<T> m80of(T reference) {
        return new Present(Preconditions.checkNotNull(reference));
    }

    public static <T> Optional<T> fromNullable(@NullableDecl T nullableReference) {
        return nullableReference == null ? absent() : new Present(nullableReference);
    }

    @Beta
    public static <T> Iterable<T> presentInstances(final Iterable<? extends Optional<? extends T>> optionals) {
        Preconditions.checkNotNull(optionals);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new AbstractIterator<T>() {
                    private final Iterator<? extends Optional<? extends T>> iterator = ((Iterator) Preconditions.checkNotNull(optionals.iterator()));

                    /* access modifiers changed from: protected */
                    public T computeNext() {
                        while (this.iterator.hasNext()) {
                            Optional<? extends T> optional = (Optional) this.iterator.next();
                            if (optional.isPresent()) {
                                return optional.get();
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    public abstract Set<T> asSet();

    public abstract boolean equals(@NullableDecl Object obj);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    /* renamed from: or */
    public abstract Optional<T> mo22985or(Optional<? extends PrimesBatteryConfigurations> optional);

    @Beta
    /* renamed from: or */
    public abstract T mo22986or(Supplier<? extends PrimesBatteryConfigurations> supplier);

    /* renamed from: or */
    public abstract T mo22987or(PrimesBatteryConfigurations primesBatteryConfigurations);

    @NullableDecl
    public abstract T orNull();

    public abstract String toString();

    public abstract <V> Optional<V> transform(Function<? super T, V> function);
}
