package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class AbstractListMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    /* access modifiers changed from: package-private */
    public abstract List<V> createCollection();

    protected AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    /* access modifiers changed from: package-private */
    public List<V> createUnmodifiableEmptyCollection() {
        return Collections.emptyList();
    }

    /* access modifiers changed from: package-private */
    public <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return Collections.unmodifiableList((List) collection);
    }

    /* access modifiers changed from: package-private */
    public Collection<V> wrapCollection(K key, Collection<V> collection) {
        return wrapList(key, (List) collection, null);
    }

    public List<V> get(@NullableDecl K key) {
        return (List) super.get((Object) key);
    }

    @CanIgnoreReturnValue
    public List<V> removeAll(@NullableDecl Object key) {
        return (List) super.removeAll(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.AbstractMapBasedMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
     arg types: [K, java.lang.Iterable<? extends V>]
     candidates:
      com.google.common.collect.AbstractListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V>
      com.google.common.collect.ListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V>
      com.google.common.collect.AbstractMapBasedMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V> */
    @CanIgnoreReturnValue
    public List<V> replaceValues(@NullableDecl K key, Iterable<? extends V> values) {
        return (List) super.replaceValues((Object) key, (Iterable) values);
    }

    @CanIgnoreReturnValue
    public boolean put(@NullableDecl K key, @NullableDecl V value) {
        return super.put(key, value);
    }

    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    public boolean equals(@NullableDecl Object object) {
        return super.equals(object);
    }
}
