package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    @NullableDecl
    final K key;
    @NullableDecl
    final V value;

    ImmutableEntry(@NullableDecl K key2, @NullableDecl V value2) {
        this.key = key2;
        this.value = value2;
    }

    @NullableDecl
    public final K getKey() {
        return this.key;
    }

    @NullableDecl
    public final V getValue() {
        return this.value;
    }

    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
