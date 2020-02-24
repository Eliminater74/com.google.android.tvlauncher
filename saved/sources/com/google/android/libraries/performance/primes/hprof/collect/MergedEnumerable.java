package com.google.android.libraries.performance.primes.hprof.collect;

import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import java.util.Arrays;
import java.util.Iterator;

public final class MergedEnumerable<E> implements IntObjectMap.Enumerable<E> {
    private IntObjectMap.Enumerable<? extends E> activeEnumerator = nextEnumerator();
    private final Iterator<IntObjectMap.Enumerable<? extends E>> enumerators;

    public static <E> MergedEnumerable<E> merge(IntObjectMap.Enumerable<? extends E>... enumerators2) {
        return new MergedEnumerable<>(Arrays.asList(enumerators2).iterator());
    }

    private MergedEnumerable(Iterator<IntObjectMap.Enumerable<? extends E>> enumerators2) {
        this.enumerators = enumerators2;
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
