package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
abstract class AbstractNavigableMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> implements NavigableMap<K, V> {
    /* access modifiers changed from: package-private */
    public abstract Iterator<Map.Entry<K, V>> descendingEntryIterator();

    @NullableDecl
    public abstract V get(@NullableDecl Object obj);

    AbstractNavigableMap() {
    }

    @NullableDecl
    public Map.Entry<K, V> firstEntry() {
        return (Map.Entry) Iterators.getNext(entryIterator(), null);
    }

    @NullableDecl
    public Map.Entry<K, V> lastEntry() {
        return (Map.Entry) Iterators.getNext(descendingEntryIterator(), null);
    }

    @NullableDecl
    public Map.Entry<K, V> pollFirstEntry() {
        return (Map.Entry) Iterators.pollNext(entryIterator());
    }

    @NullableDecl
    public Map.Entry<K, V> pollLastEntry() {
        return (Map.Entry) Iterators.pollNext(descendingEntryIterator());
    }

    public K firstKey() {
        Map.Entry<K, V> entry = firstEntry();
        if (entry != null) {
            return entry.getKey();
        }
        throw new NoSuchElementException();
    }

    public K lastKey() {
        Map.Entry<K, V> entry = lastEntry();
        if (entry != null) {
            return entry.getKey();
        }
        throw new NoSuchElementException();
    }

    @NullableDecl
    public Map.Entry<K, V> lowerEntry(K key) {
        return headMap(key, false).lastEntry();
    }

    @NullableDecl
    public Map.Entry<K, V> floorEntry(K key) {
        return headMap(key, true).lastEntry();
    }

    @NullableDecl
    public Map.Entry<K, V> ceilingEntry(K key) {
        return tailMap(key, true).firstEntry();
    }

    @NullableDecl
    public Map.Entry<K, V> higherEntry(K key) {
        return tailMap(key, false).firstEntry();
    }

    public K lowerKey(K key) {
        return Maps.keyOrNull(lowerEntry(key));
    }

    public K floorKey(K key) {
        return Maps.keyOrNull(floorEntry(key));
    }

    public K ceilingKey(K key) {
        return Maps.keyOrNull(ceilingEntry(key));
    }

    public K higherKey(K key) {
        return Maps.keyOrNull(higherEntry(key));
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    public SortedMap<K, V> headMap(K toKey) {
        return headMap(toKey, false);
    }

    public SortedMap<K, V> tailMap(K fromKey) {
        return tailMap(fromKey, true);
    }

    public NavigableSet<K> navigableKeySet() {
        return new Maps.NavigableKeySet(this);
    }

    public Set<K> keySet() {
        return navigableKeySet();
    }

    public NavigableSet<K> descendingKeySet() {
        return descendingMap().navigableKeySet();
    }

    public NavigableMap<K, V> descendingMap() {
        return new DescendingMap();
    }

    private final class DescendingMap extends Maps.DescendingMap<K, V> {
        private DescendingMap() {
        }

        /* access modifiers changed from: package-private */
        public NavigableMap<K, V> forward() {
            return AbstractNavigableMap.this;
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return AbstractNavigableMap.this.descendingEntryIterator();
        }
    }
}
