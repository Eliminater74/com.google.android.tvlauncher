package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;

import java.util.Map;
import java.util.Set;

@GwtCompatible
final class FilteredEntrySetMultimap<K, V> extends FilteredEntryMultimap<K, V> implements FilteredSetMultimap<K, V> {
    FilteredEntrySetMultimap(SetMultimap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
        super(unfiltered, predicate);
    }

    public SetMultimap<K, V> unfiltered() {
        return (SetMultimap) this.unfiltered;
    }

    public Set<V> get(K key) {
        return (Set) super.get((Object) key);
    }

    public Set<V> removeAll(Object key) {
        return (Set) super.removeAll(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.AbstractMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
     arg types: [K, java.lang.Iterable<? extends V>]
     candidates:
      com.google.common.collect.FilteredEntrySetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
      com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
      com.google.common.collect.AbstractMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V> */
    public Set<V> replaceValues(K key, Iterable<? extends V> values) {
        return (Set) super.replaceValues((Object) key, (Iterable) values);
    }

    /* access modifiers changed from: package-private */
    public Set<Map.Entry<K, V>> createEntries() {
        return Sets.filter(unfiltered().entries(), entryPredicate());
    }

    public Set<Map.Entry<K, V>> entries() {
        return (Set) super.entries();
    }
}
