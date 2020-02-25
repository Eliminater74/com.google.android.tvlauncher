package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Map;
import java.util.Set;

@GwtCompatible
public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V> {
    /* access modifiers changed from: protected */
    public abstract SetMultimap<K, V> delegate();

    public Set<Map.Entry<K, V>> entries() {
        return delegate().entries();
    }

    public Set<V> get(@NullableDecl K key) {
        return delegate().get((Object) key);
    }

    @CanIgnoreReturnValue
    public Set<V> removeAll(@NullableDecl Object key) {
        return delegate().removeAll(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
     arg types: [K, java.lang.Iterable<? extends V>]
     candidates:
      com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V> */
    @CanIgnoreReturnValue
    public Set<V> replaceValues(K key, Iterable<? extends V> values) {
        return delegate().replaceValues((Object) key, (Iterable) values);
    }
}
