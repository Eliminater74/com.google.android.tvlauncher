package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class ForwardingSortedMap<K, V> extends ForwardingMap<K, V> implements SortedMap<K, V> {
    /* access modifiers changed from: protected */
    public abstract SortedMap<K, V> delegate();

    protected ForwardingSortedMap() {
    }

    public Comparator<? super K> comparator() {
        return delegate().comparator();
    }

    public K firstKey() {
        return delegate().firstKey();
    }

    public SortedMap<K, V> headMap(K toKey) {
        return delegate().headMap(toKey);
    }

    public K lastKey() {
        return delegate().lastKey();
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return delegate().subMap(fromKey, toKey);
    }

    public SortedMap<K, V> tailMap(K fromKey) {
        return delegate().tailMap(fromKey);
    }

    @Beta
    protected class StandardKeySet extends Maps.SortedKeySet<K, V> {
        public StandardKeySet(ForwardingSortedMap this$0) {
            super(this$0);
        }
    }

    private int unsafeCompare(Object k1, Object k2) {
        Comparator<? super K> comparator = comparator();
        if (comparator == null) {
            return ((Comparable) k1).compareTo(k2);
        }
        return comparator.compare(k1, k2);
    }

    /* access modifiers changed from: protected */
    @Beta
    public boolean standardContainsKey(@NullableDecl Object key) {
        try {
            if (unsafeCompare(tailMap(key).firstKey(), key) == 0) {
                return true;
            }
            return false;
        } catch (ClassCastException | NullPointerException | NoSuchElementException e) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    @Beta
    public SortedMap<K, V> standardSubMap(K fromKey, K toKey) {
        Preconditions.checkArgument(unsafeCompare(fromKey, toKey) <= 0, "fromKey must be <= toKey");
        return tailMap(fromKey).headMap(toKey);
    }
}
