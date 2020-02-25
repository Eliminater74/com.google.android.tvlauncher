package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

@GwtCompatible(emulated = true, serializable = true)
final class ImmutableEnumMap<K extends Enum<K>, V> extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
    private final transient EnumMap<K, V> delegate;

    private ImmutableEnumMap(EnumMap<K, V> delegate2) {
        this.delegate = delegate2;
        Preconditions.checkArgument(!delegate2.isEmpty());
    }

    static <K extends Enum<K>, V> ImmutableMap<K, V> asImmutable(EnumMap<K, V> map) {
        int size = map.size();
        if (size == 0) {
            return ImmutableMap.m126of();
        }
        if (size != 1) {
            return new ImmutableEnumMap(map);
        }
        Map.Entry<K, V> entry = (Map.Entry) Iterables.getOnlyElement(map.entrySet());
        return ImmutableMap.m127of((Enum) entry.getKey(), entry.getValue());
    }

    /* access modifiers changed from: package-private */
    public UnmodifiableIterator<K> keyIterator() {
        return Iterators.unmodifiableIterator(this.delegate.keySet().iterator());
    }

    public int size() {
        return this.delegate.size();
    }

    public boolean containsKey(@NullableDecl Object key) {
        return this.delegate.containsKey(key);
    }

    public V get(Object key) {
        return this.delegate.get(key);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ImmutableEnumMap) {
            object = ((ImmutableEnumMap) object).delegate;
        }
        return this.delegate.equals(object);
    }

    /* access modifiers changed from: package-private */
    public UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
        return Maps.unmodifiableEntryIterator(this.delegate.entrySet().iterator());
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new EnumSerializedForm(this.delegate);
    }

    private static class EnumSerializedForm<K extends Enum<K>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final EnumMap<K, V> delegate;

        EnumSerializedForm(EnumMap<K, V> delegate2) {
            this.delegate = delegate2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return new ImmutableEnumMap(this.delegate);
        }
    }
}
