package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

@GwtCompatible(emulated = true, serializable = true)
public class ImmutableListMultimap<K, V> extends ImmutableMultimap<K, V> implements ListMultimap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    @RetainedWith
    @LazyInit
    private transient ImmutableListMultimap<V, K> inverse;

    ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> map, int size) {
        super(map, size);
    }

    /* renamed from: of */
    public static <K, V> ImmutableListMultimap<K, V> m120of() {
        return EmptyImmutableListMultimap.INSTANCE;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableListMultimap<K, V> m121of(K k1, V v1) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableListMultimap<K, V> m122of(K k1, V v1, K k2, V v2) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableListMultimap<K, V> m123of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableListMultimap<K, V> m124of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        builder.put((Object) k4, (Object) v4);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableListMultimap<K, V> m125of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        builder.put((Object) k4, (Object) v4);
        builder.put((Object) k5, (Object) v5);
        return builder.build();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (multimap.isEmpty()) {
            return m120of();
        }
        if (multimap instanceof ImmutableListMultimap) {
            ImmutableListMultimap<K, V> kvMultimap = (ImmutableListMultimap) multimap;
            if (!kvMultimap.isPartialView()) {
                return kvMultimap;
            }
        }
        return fromMapEntries(multimap.asMap().entrySet(), null);
    }

    @Beta
    public static <K, V> ImmutableListMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return new Builder().putAll((Iterable) entries).build();
    }

    static <K, V> ImmutableListMultimap<K, V> fromMapEntries(Collection<? extends Map.Entry<? extends K, ? extends Collection<? extends V>>> mapEntries, @NullableDecl Comparator<? super V> valueComparator) {
        ImmutableList<V> list;
        if (mapEntries.isEmpty()) {
            return m120of();
        }
        ImmutableMap.Builder<K, ImmutableList<V>> builder = new ImmutableMap.Builder<>(mapEntries.size());
        int size = 0;
        for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : mapEntries) {
            K key = entry.getKey();
            Collection<? extends V> values = (Collection) entry.getValue();
            if (valueComparator == null) {
                list = ImmutableList.copyOf((Collection) values);
            } else {
                list = ImmutableList.sortedCopyOf(valueComparator, values);
            }
            if (!list.isEmpty()) {
                builder.put(key, list);
                size += list.size();
            }
        }
        return new ImmutableListMultimap<>(builder.build(), size);
    }

    public ImmutableList<V> get(@NullableDecl K key) {
        ImmutableList<V> list = (ImmutableList) this.map.get(key);
        return list == null ? ImmutableList.m107of() : list;
    }

    public ImmutableListMultimap<V, K> inverse() {
        ImmutableListMultimap<V, K> result = this.inverse;
        if (result != null) {
            return result;
        }
        ImmutableListMultimap<V, K> invert = invert();
        this.inverse = invert;
        return invert;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [V, K]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    private ImmutableListMultimap<V, K> invert() {
        Builder<V, K> builder = builder();
        UnmodifiableIterator it = entries().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = (Map.Entry) it.next();
            builder.put((Object) entry.getValue(), (Object) entry.getKey());
        }
        ImmutableListMultimap<V, K> invertedMultimap = builder.build();
        invertedMultimap.inverse = this;
        return invertedMultimap;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableList<V> removeAll(Object key) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableList<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMultimap(this, stream);
    }

    /* JADX INFO: Multiple debug info for r3v4 com.google.common.collect.ImmutableMap: [D('tmpMap' com.google.common.collect.ImmutableMap<java.lang.Object, com.google.common.collect.ImmutableList<java.lang.Object>>), D('i' int)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.ImmutableListMultimap, com.google.common.collect.ImmutableMap<java.lang.Object, com.google.common.collect.ImmutableList<java.lang.Object>>]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
     arg types: [com.google.common.collect.ImmutableListMultimap, int]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void */
    @GwtIncompatible
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int keyCount = stream.readInt();
        if (keyCount >= 0) {
            ImmutableMap.Builder<Object, ImmutableList<Object>> builder = ImmutableMap.builder();
            int tmpSize = 0;
            int i = 0;
            while (i < keyCount) {
                Object key = stream.readObject();
                int valueCount = stream.readInt();
                if (valueCount > 0) {
                    ImmutableList.Builder<Object> valuesBuilder = ImmutableList.builder();
                    for (int j = 0; j < valueCount; j++) {
                        valuesBuilder.add(stream.readObject());
                    }
                    builder.put(key, valuesBuilder.build());
                    tmpSize += valueCount;
                    i++;
                } else {
                    StringBuilder sb = new StringBuilder(31);
                    sb.append("Invalid value count ");
                    sb.append(valueCount);
                    throw new InvalidObjectException(sb.toString());
                }
            }
            try {
                ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ConcurrentHashMultiset) this, (Object) builder.build());
                ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ConcurrentHashMultiset) this, tmpSize);
            } catch (IllegalArgumentException e) {
                throw ((InvalidObjectException) new InvalidObjectException(e.getMessage()).initCause(e));
            }
        } else {
            StringBuilder sb2 = new StringBuilder(29);
            sb2.append("Invalid key count ");
            sb2.append(keyCount);
            throw new InvalidObjectException(sb2.toString());
        }
    }

    public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
         arg types: [java.lang.Object, java.lang.Object]
         candidates:
          com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> put(Object obj, Object obj2) {
            super.put(obj, obj2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            super.putAll((Iterable) entries);
            return this;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V>
         arg types: [K, java.lang.Iterable<? extends V>]
         candidates:
          com.google.common.collect.ImmutableListMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
          com.google.common.collect.ImmutableListMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
          com.google.common.collect.ImmutableListMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
            super.putAll((Object) key, (Iterable) values);
            return this;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder<K, V>
         arg types: [K, V[]]
         candidates:
          com.google.common.collect.ImmutableListMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
          com.google.common.collect.ImmutableListMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
          com.google.common.collect.ImmutableListMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K key, V... values) {
            super.putAll((Object) key, (Object[]) values);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            super.putAll((Multimap) multimap);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
            super.orderKeysBy((Comparator) keyComparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
            super.orderValuesBy((Comparator) valueComparator);
            return this;
        }

        public ImmutableListMultimap<K, V> build() {
            return (ImmutableListMultimap) super.build();
        }
    }
}
