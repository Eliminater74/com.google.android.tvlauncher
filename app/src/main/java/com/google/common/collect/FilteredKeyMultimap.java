package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@GwtCompatible
class FilteredKeyMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    final Predicate<? super K> keyPredicate;
    final Multimap<K, V> unfiltered;

    FilteredKeyMultimap(Multimap<K, V> unfiltered2, Predicate<? super K> keyPredicate2) {
        this.unfiltered = (Multimap) Preconditions.checkNotNull(unfiltered2);
        this.keyPredicate = (Predicate) Preconditions.checkNotNull(keyPredicate2);
    }

    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return Maps.keyPredicateOnEntries(this.keyPredicate);
    }

    public int size() {
        int size = 0;
        for (Collection<V> collection : asMap().values()) {
            size += collection.size();
        }
        return size;
    }

    public boolean containsKey(@NullableDecl Object key) {
        if (!this.unfiltered.containsKey(key)) {
            return false;
        }
        return this.keyPredicate.apply(key);
    }

    public Collection<V> removeAll(Object key) {
        return containsKey(key) ? this.unfiltered.removeAll(key) : unmodifiableEmptyCollection();
    }

    /* access modifiers changed from: package-private */
    public Collection<V> unmodifiableEmptyCollection() {
        if (this.unfiltered instanceof SetMultimap) {
            return ImmutableSet.m149of();
        }
        return ImmutableList.m107of();
    }

    public void clear() {
        keySet().clear();
    }

    /* access modifiers changed from: package-private */
    public Set<K> createKeySet() {
        return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
    }

    public Collection<V> get(Object obj) {
        if (this.keyPredicate.apply(obj)) {
            return this.unfiltered.get(obj);
        }
        if (this.unfiltered instanceof SetMultimap) {
            return new AddRejectingSet(obj);
        }
        return new AddRejectingList(obj);
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError("should never be called");
    }

    /* access modifiers changed from: package-private */
    public Collection<Map.Entry<K, V>> createEntries() {
        return new Entries();
    }

    /* access modifiers changed from: package-private */
    public Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    /* access modifiers changed from: package-private */
    public Map<K, Collection<V>> createAsMap() {
        return Maps.filterKeys(this.unfiltered.asMap(), this.keyPredicate);
    }

    /* access modifiers changed from: package-private */
    public Multiset<K> createKeys() {
        return Multisets.filter(this.unfiltered.keys(), this.keyPredicate);
    }

    static class AddRejectingSet<K, V> extends ForwardingSet<V> {
        final K key;

        AddRejectingSet(K key2) {
            this.key = key2;
        }

        public boolean add(V v) {
            String valueOf = String.valueOf(this.key);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
            sb.append("Key does not satisfy predicate: ");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        }

        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            String valueOf = String.valueOf(this.key);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
            sb.append("Key does not satisfy predicate: ");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public Set<V> delegate() {
            return Collections.emptySet();
        }
    }

    static class AddRejectingList<K, V> extends ForwardingList<V> {
        final K key;

        AddRejectingList(K key2) {
            this.key = key2;
        }

        public boolean add(V v) {
            add(0, v);
            return true;
        }

        public void add(int index, V v) {
            Preconditions.checkPositionIndex(index, 0);
            String valueOf = String.valueOf(this.key);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
            sb.append("Key does not satisfy predicate: ");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        }

        public boolean addAll(Collection<? extends V> collection) {
            addAll(0, collection);
            return true;
        }

        @CanIgnoreReturnValue
        public boolean addAll(int index, Collection<? extends V> elements) {
            Preconditions.checkNotNull(elements);
            Preconditions.checkPositionIndex(index, 0);
            String valueOf = String.valueOf(this.key);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
            sb.append("Key does not satisfy predicate: ");
            sb.append(valueOf);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public List<V> delegate() {
            return Collections.emptyList();
        }
    }

    class Entries extends ForwardingCollection<Map.Entry<K, V>> {
        Entries() {
        }

        /* access modifiers changed from: protected */
        public Collection<Map.Entry<K, V>> delegate() {
            return Collections2.filter(FilteredKeyMultimap.this.unfiltered.entries(), FilteredKeyMultimap.this.entryPredicate());
        }

        public boolean remove(@NullableDecl Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            if (!FilteredKeyMultimap.this.unfiltered.containsKey(entry.getKey()) || !FilteredKeyMultimap.this.keyPredicate.apply(entry.getKey())) {
                return false;
            }
            return FilteredKeyMultimap.this.unfiltered.remove(entry.getKey(), entry.getValue());
        }
    }
}
