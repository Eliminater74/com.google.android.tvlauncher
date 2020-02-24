package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
final class FilteredKeyListMultimap<K, V> extends FilteredKeyMultimap<K, V> implements ListMultimap<K, V> {
    FilteredKeyListMultimap(ListMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        super(unfiltered, keyPredicate);
    }

    public ListMultimap<K, V> unfiltered() {
        return (ListMultimap) super.unfiltered();
    }

    public List<V> get(K key) {
        return (List) super.get((Object) key);
    }

    public List<V> removeAll(@NullableDecl Object key) {
        return (List) super.removeAll(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.AbstractMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
     arg types: [K, java.lang.Iterable<? extends V>]
     candidates:
      com.google.common.collect.FilteredKeyListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V>
      com.google.common.collect.ListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V>
      com.google.common.collect.AbstractMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V> */
    public List<V> replaceValues(K key, Iterable<? extends V> values) {
        return (List) super.replaceValues((Object) key, (Iterable) values);
    }
}
