package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@GwtCompatible
public abstract class ForwardingMultimap<K, V> extends ForwardingObject implements Multimap<K, V> {
    protected ForwardingMultimap() {
    }

    /* access modifiers changed from: protected */
    public abstract Multimap<K, V> delegate();

    public Map<K, Collection<V>> asMap() {
        return delegate().asMap();
    }

    public void clear() {
        delegate().clear();
    }

    public boolean containsEntry(@NullableDecl Object key, @NullableDecl Object value) {
        return delegate().containsEntry(key, value);
    }

    public boolean containsKey(@NullableDecl Object key) {
        return delegate().containsKey(key);
    }

    public boolean containsValue(@NullableDecl Object value) {
        return delegate().containsValue(value);
    }

    public Collection<Map.Entry<K, V>> entries() {
        return delegate().entries();
    }

    public Collection<V> get(@NullableDecl Object obj) {
        return delegate().get(obj);
    }

    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    public Multiset<K> keys() {
        return delegate().keys();
    }

    public Set<K> keySet() {
        return delegate().keySet();
    }

    @CanIgnoreReturnValue
    public boolean put(K key, V value) {
        return delegate().put(key, value);
    }

    @CanIgnoreReturnValue
    public boolean putAll(K key, Iterable<? extends V> values) {
        return delegate().putAll(key, values);
    }

    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        return delegate().putAll(multimap);
    }

    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object key, @NullableDecl Object value) {
        return delegate().remove(key, value);
    }

    @CanIgnoreReturnValue
    public Collection<V> removeAll(@NullableDecl Object key) {
        return delegate().removeAll(key);
    }

    @CanIgnoreReturnValue
    public Collection<V> replaceValues(Object obj, Iterable iterable) {
        return delegate().replaceValues(obj, iterable);
    }

    public int size() {
        return delegate().size();
    }

    public Collection<V> values() {
        return delegate().values();
    }

    public boolean equals(@NullableDecl Object object) {
        return object == this || delegate().equals(object);
    }

    public int hashCode() {
        return delegate().hashCode();
    }
}
