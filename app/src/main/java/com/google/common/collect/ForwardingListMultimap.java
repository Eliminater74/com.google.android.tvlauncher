package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.List;

@GwtCompatible
public abstract class ForwardingListMultimap<K, V> extends ForwardingMultimap<K, V> implements ListMultimap<K, V> {
    protected ForwardingListMultimap() {
    }

    /* access modifiers changed from: protected */
    public abstract ListMultimap<K, V> delegate();

    public List<V> get(@NullableDecl K key) {
        return delegate().get((Object) key);
    }

    @CanIgnoreReturnValue
    public List<V> removeAll(@NullableDecl Object key) {
        return delegate().removeAll(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V>
     arg types: [K, java.lang.Iterable<? extends V>]
     candidates:
      com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.ListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V> */
    @CanIgnoreReturnValue
    public List<V> replaceValues(K key, Iterable<? extends V> values) {
        return delegate().replaceValues((Object) key, (Iterable) values);
    }
}
