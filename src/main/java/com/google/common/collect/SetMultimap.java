package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public interface SetMultimap<K, V> extends Multimap<K, V> {
    Map<K, Collection<V>> asMap();

    Set<Map.Entry<K, V>> entries();

    boolean equals(@NullableDecl Object obj);

    Set<V> get(@NullableDecl Object obj);

    @CanIgnoreReturnValue
    Set<V> removeAll(@NullableDecl Object obj);

    @CanIgnoreReturnValue
    Set<V> replaceValues(Object obj, Iterable iterable);
}
