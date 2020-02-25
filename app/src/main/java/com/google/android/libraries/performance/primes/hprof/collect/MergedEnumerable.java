package com.google.android.libraries.performance.primes.hprof.collect;

import java.util.Arrays;
import java.util.Iterator;

public final class MergedEnumerable<E> implements IntObjectMap.Enumerable<E> {
    private final Iterator<IntObjectMap.Enumerable<? extends E>> enumerators;
    private IntObjectMap.Enumerable<? extends E> activeEnumerator = nextEnumerator();

    private MergedEnumerable(Iterator<IntObjectMap.Enumerable<? extends E>> enumerators2) {
        this.enumerators = enumerators2;
    }

    public static <E> MergedEnumerable<E> merge(IntObjectMap.Enumerable<? extends E>... enumerators2) {
        return new MergedEnumerable<>(Arrays.asList(enumerators2).iterator());
    }

    public boolean next() {
        while (true) {
            IntObjectMap.Enumerable<? extends E> enumerable = this.activeEnumerator;
            if (enumerable != null && !enumerable.next()) {
                this.activeEnumerator = nextEnumerator();
            }
        }
        return this.activeEnumerator != null;
    }

    private IntObjectMap.Enumerable<? extends E> nextEnumerator() {
        if (this.enumerators.hasNext()) {
            return this.enumerators.next();
        }
        return null;
    }

    public int getKey() {
        return this.activeEnumerator.getKey();
    }

    public E getValue() {
        return this.activeEnumerator.getValue();
    }
}
