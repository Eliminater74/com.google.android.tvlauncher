package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {
    ImmutableBiMap() {
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m99of() {
        return RegularImmutableBiMap.EMPTY;
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m100of(K k1, V v1) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        return new RegularImmutableBiMap(new Object[]{k1, v1}, 1);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m101of(K k1, V v1, K k2, V v2) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2}, 2);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m102of(K k1, V v1, K k2, V v2, K k3, V v3) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3}, 3);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m103of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4}, 4);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m104of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        CollectPreconditions.checkEntryNotNull(k5, v5);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5}, 5);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    @Beta
    public static <K, V> Builder<K, V> builderWithExpectedSize(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        return new Builder<>(expectedSize);
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableBiMap) {
            ImmutableBiMap<K, V> bimap = (ImmutableBiMap) map;
            if (!bimap.isPartialView()) {
                return bimap;
            }
        }
        return copyOf((Iterable) map.entrySet());
    }

    @Beta
    public static <K, V> ImmutableBiMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        int estimatedSize;
        if (entries instanceof Collection) {
            estimatedSize = ((Collection) entries).size();
        } else {
            estimatedSize = 4;
        }
        return new Builder(estimatedSize).putAll((Iterable) entries).build();
    }

    public abstract ImmutableBiMap<V, K> inverse();

    public ImmutableSet<V> values() {
        return inverse().keySet();
    }

    /* access modifiers changed from: package-private */
    public final ImmutableSet<V> createValues() {
        throw new AssertionError("should never be called");
    }

    @CanIgnoreReturnValue
    @Deprecated
    public V forcePut(K k, V v) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        public Builder() {
        }

        Builder(int size) {
            super(size);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableMap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMap$Builder<K, V>
         arg types: [K, V]
         candidates:
          com.google.common.collect.ImmutableBiMap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableBiMap$Builder<K, V>
          com.google.common.collect.ImmutableMap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> put(K key, V value) {
            super.put((Object) key, (Object) value);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll((Map) map);
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            super.putAll((Iterable) entries);
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> valueComparator) {
            super.orderEntriesByValue((Comparator) valueComparator);
            return this;
        }

        public ImmutableBiMap<K, V> build() {
            if (this.size == 0) {
                return ImmutableBiMap.m99of();
            }
            sortEntries();
            this.entriesUsed = true;
            return new RegularImmutableBiMap(this.alternatingKeysAndValues, this.size);
        }
    }

    private static class SerializedForm extends ImmutableMap.SerializedForm {
        private static final long serialVersionUID = 0;

        SerializedForm(ImmutableBiMap<?, ?> bimap) {
            super(bimap);
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return createMap(new Builder<>());
        }
    }
}
