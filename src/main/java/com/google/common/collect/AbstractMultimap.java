package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimaps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
    @MonotonicNonNullDecl
    private transient Map<K, Collection<V>> asMap;
    @MonotonicNonNullDecl
    private transient Collection<Map.Entry<K, V>> entries;
    @MonotonicNonNullDecl
    private transient Set<K> keySet;
    @MonotonicNonNullDecl
    private transient Multiset<K> keys;
    @MonotonicNonNullDecl
    private transient Collection<V> values;

    /* access modifiers changed from: package-private */
    public abstract Map<K, Collection<V>> createAsMap();

    /* access modifiers changed from: package-private */
    public abstract Collection<Map.Entry<K, V>> createEntries();

    /* access modifiers changed from: package-private */
    public abstract Set<K> createKeySet();

    /* access modifiers changed from: package-private */
    public abstract Multiset<K> createKeys();

    /* access modifiers changed from: package-private */
    public abstract Collection<V> createValues();

    /* access modifiers changed from: package-private */
    public abstract Iterator<Map.Entry<K, V>> entryIterator();

    AbstractMultimap() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsValue(@NullableDecl Object value) {
        for (Collection<V> collection : asMap().values()) {
            if (collection.contains(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEntry(@NullableDecl Object key, @NullableDecl Object value) {
        Collection<V> collection = (Collection) asMap().get(key);
        return collection != null && collection.contains(value);
    }

    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object key, @NullableDecl Object value) {
        Collection<V> collection = (Collection) asMap().get(key);
        return collection != null && collection.remove(value);
    }

    @CanIgnoreReturnValue
    public boolean put(@NullableDecl K key, @NullableDecl V value) {
        return get(key).add(value);
    }

    /* JADX INFO: Multiple debug info for r0v1 java.util.Iterator<? extends V>: [D('valueItr' java.util.Iterator<? extends V>), D('valueCollection' java.util.Collection<? extends V>)] */
    @CanIgnoreReturnValue
    public boolean putAll(@NullableDecl K key, Iterable<? extends V> values2) {
        Preconditions.checkNotNull(values2);
        if (values2 instanceof Collection) {
            Collection<? extends V> valueCollection = (Collection) values2;
            if (valueCollection.isEmpty() || !get(key).addAll(valueCollection)) {
                return false;
            }
            return true;
        }
        Iterator<? extends V> valueItr = values2.iterator();
        if (!valueItr.hasNext() || !Iterators.addAll(get(key), valueItr)) {
            return false;
        }
        return true;
    }

    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean changed = false;
        for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
            changed |= put(entry.getKey(), entry.getValue());
        }
        return changed;
    }

    @CanIgnoreReturnValue
    public Collection<V> replaceValues(@NullableDecl Object obj, Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        Collection<V> result = removeAll(obj);
        putAll(obj, iterable);
        return result;
    }

    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> result = this.entries;
        if (result != null) {
            return result;
        }
        Collection<Map.Entry<K, V>> createEntries = createEntries();
        this.entries = createEntries;
        return createEntries;
    }

    class Entries extends Multimaps.Entries<K, V> {
        Entries() {
        }

        /* access modifiers changed from: package-private */
        public Multimap<K, V> multimap() {
            return AbstractMultimap.this;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractMultimap.this.entryIterator();
        }
    }

    class EntrySet extends AbstractMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
        EntrySet(AbstractMultimap this$0) {
            super();
        }

        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        public boolean equals(@NullableDecl Object obj) {
            return Sets.equalsImpl(this, obj);
        }
    }

    public Set<K> keySet() {
        Set<K> result = this.keySet;
        if (result != null) {
            return result;
        }
        Set<K> createKeySet = createKeySet();
        this.keySet = createKeySet;
        return createKeySet;
    }

    public Multiset<K> keys() {
        Multiset<K> result = this.keys;
        if (result != null) {
            return result;
        }
        Multiset<K> createKeys = createKeys();
        this.keys = createKeys;
        return createKeys;
    }

    public Collection<V> values() {
        Collection<V> result = this.values;
        if (result != null) {
            return result;
        }
        Collection<V> createValues = createValues();
        this.values = createValues;
        return createValues;
    }

    class Values extends AbstractCollection<V> {
        Values() {
        }

        public Iterator<V> iterator() {
            return AbstractMultimap.this.valueIterator();
        }

        public int size() {
            return AbstractMultimap.this.size();
        }

        public boolean contains(@NullableDecl Object o) {
            return AbstractMultimap.this.containsValue(o);
        }

        public void clear() {
            AbstractMultimap.this.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public Iterator<V> valueIterator() {
        return Maps.valueIterator(entries().iterator());
    }

    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> result = this.asMap;
        if (result != null) {
            return result;
        }
        Map<K, Collection<V>> createAsMap = createAsMap();
        this.asMap = createAsMap;
        return createAsMap;
    }

    public boolean equals(@NullableDecl Object object) {
        return Multimaps.equalsImpl(this, object);
    }

    public int hashCode() {
        return asMap().hashCode();
    }

    public String toString() {
        return asMap().toString();
    }
}
