package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
public class ImmutableSetMultimap<K, V> extends ImmutableMultimap<K, V> implements SetMultimap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private final transient ImmutableSet<V> emptySet;
    @MonotonicNonNullDecl
    private transient ImmutableSet<Map.Entry<K, V>> entries;
    @RetainedWith
    @MonotonicNonNullDecl
    @LazyInit
    private transient ImmutableSetMultimap<V, K> inverse;

    /* renamed from: of */
    public static <K, V> ImmutableSetMultimap<K, V> m156of() {
        return EmptyImmutableSetMultimap.INSTANCE;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableSetMultimap<K, V> m157of(K k1, V v1) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableSetMultimap<K, V> m158of(K k1, V v1, K k2, V v2) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableSetMultimap<K, V> m159of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableSetMultimap<K, V> m160of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = builder();
        builder.put((Object) k1, (Object) v1);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        builder.put((Object) k4, (Object) v4);
        return builder.build();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableSetMultimap<K, V> m161of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
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

    public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
        /* access modifiers changed from: package-private */
        public Collection<V> newMutableValueCollection() {
            return Platform.preservesInsertionOrderOnAddsSet();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
         arg types: [K, V]
         candidates:
          com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V> */
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
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            super.putAll((Iterable) entries);
            return this;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V>
         arg types: [K, java.lang.Iterable<? extends V>]
         candidates:
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
            super.putAll((Object) key, (Iterable) values);
            return this;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
         arg types: [K, java.util.List]
         candidates:
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K key, V... values) {
            return putAll((Object) key, (Iterable) Arrays.asList(values));
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
         arg types: [? extends K, java.lang.Iterable]
         candidates:
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableMultimap.Builder.putAll(java.lang.Object, java.lang.Object[]):com.google.common.collect.ImmutableMultimap$Builder<K, V>
          com.google.common.collect.ImmutableSetMultimap.Builder.putAll(java.lang.Object, java.lang.Iterable):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
                putAll((Object) entry.getKey(), (Iterable) entry.getValue());
            }
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

        public ImmutableSetMultimap<K, V> build() {
            Object entrySet = this.builderMap.entrySet();
            if (this.keyComparator != null) {
                entrySet = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(entrySet);
            }
            return ImmutableSetMultimap.fromMapEntries(entrySet, this.valueComparator);
        }
    }

    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        return copyOf(multimap, null);
    }

    private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> valueComparator) {
        Preconditions.checkNotNull(multimap);
        if (multimap.isEmpty() && valueComparator == null) {
            return m156of();
        }
        if (multimap instanceof ImmutableSetMultimap) {
            ImmutableSetMultimap<K, V> kvMultimap = (ImmutableSetMultimap) multimap;
            if (!kvMultimap.isPartialView()) {
                return kvMultimap;
            }
        }
        return fromMapEntries(multimap.asMap().entrySet(), valueComparator);
    }

    @Beta
    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries2) {
        return new Builder().putAll((Iterable) entries2).build();
    }

    static <K, V> ImmutableSetMultimap<K, V> fromMapEntries(Collection<? extends Map.Entry<? extends K, ? extends Collection<? extends V>>> mapEntries, @NullableDecl Comparator<? super V> valueComparator) {
        if (mapEntries.isEmpty()) {
            return m156of();
        }
        ImmutableMap.Builder<K, ImmutableSet<V>> builder = new ImmutableMap.Builder<>(mapEntries.size());
        int size = 0;
        for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : mapEntries) {
            K key = entry.getKey();
            ImmutableSet<V> set = valueSet(valueComparator, (Collection) entry.getValue());
            if (!set.isEmpty()) {
                builder.put(key, set);
                size += set.size();
            }
        }
        return new ImmutableSetMultimap<>(builder.build(), size, valueComparator);
    }

    ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> map, int size, @NullableDecl Comparator<? super V> valueComparator) {
        super(map, size);
        this.emptySet = emptySet(valueComparator);
    }

    public ImmutableSet<V> get(@NullableDecl K key) {
        return (ImmutableSet) MoreObjects.firstNonNull((ImmutableSet) this.map.get(key), this.emptySet);
    }

    public ImmutableSetMultimap<V, K> inverse() {
        ImmutableSetMultimap<V, K> result = this.inverse;
        if (result != null) {
            return result;
        }
        ImmutableSetMultimap<V, K> invert = invert();
        this.inverse = invert;
        return invert;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V>
     arg types: [V, K]
     candidates:
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableSetMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableSetMultimap$Builder<K, V> */
    private ImmutableSetMultimap<V, K> invert() {
        Builder<V, K> builder = builder();
        UnmodifiableIterator it = entries().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = (Map.Entry) it.next();
            builder.put((Object) entry.getValue(), (Object) entry.getKey());
        }
        ImmutableSetMultimap<V, K> invertedMultimap = builder.build();
        invertedMultimap.inverse = this;
        return invertedMultimap;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableSet<V> removeAll(Object key) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableSet<Map.Entry<K, V>> entries() {
        ImmutableSet<Map.Entry<K, V>> result = this.entries;
        if (result != null) {
            return result;
        }
        EntrySet entrySet = new EntrySet(this);
        this.entries = entrySet;
        return entrySet;
    }

    private static final class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        @Weak
        private final transient ImmutableSetMultimap<K, V> multimap;

        EntrySet(ImmutableSetMultimap<K, V> multimap2) {
            this.multimap = multimap2;
        }

        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) object;
            return this.multimap.containsEntry(entry.getKey(), entry.getValue());
        }

        public int size() {
            return this.multimap.size();
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.multimap.entryIterator();
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return false;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Collection):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [java.util.Comparator<? super V>, java.util.Collection<? extends V>]
     candidates:
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.lang.Iterable):com.google.common.collect.ImmutableSortedSet<E>
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Iterator):com.google.common.collect.ImmutableSortedSet<E>
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Collection):com.google.common.collect.ImmutableSortedSet<E> */
    private static <V> ImmutableSet<V> valueSet(@NullableDecl Comparator<? super V> valueComparator, Collection<? extends V> values) {
        if (valueComparator == null) {
            return ImmutableSet.copyOf((Collection) values);
        }
        return ImmutableSortedSet.copyOf((Comparator) valueComparator, (Collection) values);
    }

    private static <V> ImmutableSet<V> emptySet(@NullableDecl Comparator<? super V> valueComparator) {
        if (valueComparator == null) {
            return ImmutableSet.m149of();
        }
        return ImmutableSortedSet.emptySet(valueComparator);
    }

    private static <V> ImmutableSet.Builder<V> valuesBuilder(@NullableDecl Comparator<? super V> valueComparator) {
        if (valueComparator == null) {
            return new ImmutableSet.Builder<>();
        }
        return new ImmutableSortedSet.Builder(valueComparator);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(valueComparator());
        Serialization.writeMultimap(this, stream);
    }

    /* access modifiers changed from: package-private */
    @NullableDecl
    public Comparator<? super V> valueComparator() {
        ImmutableSet<V> immutableSet = this.emptySet;
        if (immutableSet instanceof ImmutableSortedSet) {
            return ((ImmutableSortedSet) immutableSet).comparator();
        }
        return null;
    }

    @GwtIncompatible
    private static final class SetFieldSettersHolder {
        static final Serialization.FieldSetter<ImmutableSetMultimap> EMPTY_SET_FIELD_SETTER = Serialization.getFieldSetter(ImmutableSetMultimap.class, "emptySet");

        private SetFieldSettersHolder() {
        }
    }

    /* JADX INFO: Multiple debug info for r4v4 com.google.common.collect.ImmutableMap: [D('i' int), D('tmpMap' com.google.common.collect.ImmutableMap<java.lang.Object, com.google.common.collect.ImmutableSet<java.lang.Object>>)] */
    /* JADX INFO: Multiple debug info for r8v5 com.google.common.collect.ImmutableSet: [D('valueSet' com.google.common.collect.ImmutableSet<java.lang.Object>), D('j' int)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.ImmutableSetMultimap, com.google.common.collect.ImmutableMap<java.lang.Object, com.google.common.collect.ImmutableSet<java.lang.Object>>]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
     arg types: [com.google.common.collect.ImmutableSetMultimap, int]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.ImmutableSetMultimap, com.google.common.collect.ImmutableSet]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    @GwtIncompatible
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Comparator<Object> valueComparator = (Comparator) stream.readObject();
        int keyCount = stream.readInt();
        if (keyCount >= 0) {
            ImmutableMap.Builder<Object, ImmutableSet<Object>> builder = ImmutableMap.builder();
            int tmpSize = 0;
            int i = 0;
            while (i < keyCount) {
                Object key = stream.readObject();
                int valueCount = stream.readInt();
                if (valueCount > 0) {
                    ImmutableSet.Builder<Object> valuesBuilder = valuesBuilder(valueComparator);
                    for (int j = 0; j < valueCount; j++) {
                        valuesBuilder.add(stream.readObject());
                    }
                    ImmutableSet<Object> valueSet = valuesBuilder.build();
                    if (valueSet.size() == valueCount) {
                        builder.put(key, valueSet);
                        tmpSize += valueCount;
                        i++;
                    } else {
                        String valueOf = String.valueOf(key);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 40);
                        sb.append("Duplicate key-value pairs exist for key ");
                        sb.append(valueOf);
                        throw new InvalidObjectException(sb.toString());
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder(31);
                    sb2.append("Invalid value count ");
                    sb2.append(valueCount);
                    throw new InvalidObjectException(sb2.toString());
                }
            }
            try {
                ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ConcurrentHashMultiset) this, (Object) builder.build());
                ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ConcurrentHashMultiset) this, tmpSize);
                SetFieldSettersHolder.EMPTY_SET_FIELD_SETTER.set((ConcurrentHashMultiset) this, (Object) emptySet(valueComparator));
            } catch (IllegalArgumentException e) {
                throw ((InvalidObjectException) new InvalidObjectException(e.getMessage()).initCause(e));
            }
        } else {
            StringBuilder sb3 = new StringBuilder(29);
            sb3.append("Invalid key count ");
            sb3.append(keyCount);
            throw new InvalidObjectException(sb3.toString());
        }
    }
}
