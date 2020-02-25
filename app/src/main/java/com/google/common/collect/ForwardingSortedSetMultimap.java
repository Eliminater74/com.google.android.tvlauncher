package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Comparator;
import java.util.SortedSet;

@GwtCompatible
public abstract class ForwardingSortedSetMultimap<K, V> extends ForwardingSetMultimap<K, V> implements SortedSetMultimap<K, V> {
    protected ForwardingSortedSetMultimap() {
    }

    /* access modifiers changed from: protected */
    public abstract SortedSetMultimap<K, V> delegate();

    public SortedSet<V> get(@NullableDecl K key) {
        return delegate().get((Object) key);
    }

    public SortedSet<V> removeAll(@NullableDecl Object key) {
        return delegate().removeAll(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.SortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.SortedSet<V>
     arg types: [K, java.lang.Iterable<? extends V>]
     candidates:
      com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
      com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.SortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.SortedSet<V> */
    public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
        return delegate().replaceValues((Object) key, (Iterable) values);
    }

    public Comparator<? super V> valueComparator() {
        return delegate().valueComparator();
    }
}
