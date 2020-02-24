package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
final class ImmutableMapKeySet<K, V> extends IndexedImmutableSet<K> {
    private final ImmutableMap<K, V> map;

    ImmutableMapKeySet(ImmutableMap<K, V> map2) {
        this.map = map2;
    }

    public int size() {
        return this.map.size();
    }

    public UnmodifiableIterator<K> iterator() {
        return this.map.keyIterator();
    }

    public boolean contains(@NullableDecl Object object) {
        return this.map.containsKey(object);
    }

    /* access modifiers changed from: package-private */
    public K get(int index) {
        return this.map.entrySet().asList().get(index).getKey();
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return true;
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public Object writeReplace() {
        return new KeySetSerializedForm(this.map);
    }

    @GwtIncompatible
    private static class KeySetSerializedForm<K> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, ?> map;

        KeySetSerializedForm(ImmutableMap<K, ?> map2) {
            this.map = map2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return this.map.keySet();
        }
    }
}
