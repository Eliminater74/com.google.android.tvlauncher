package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@GwtCompatible(emulated = true, serializable = true)
public class TreeMultimap<K, V> extends AbstractSortedKeySortedSetMultimap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Comparator<? super K> keyComparator;
    private transient Comparator<? super V> valueComparator;

    TreeMultimap(Comparator<? super K> keyComparator2, Comparator<? super V> valueComparator2) {
        super(new TreeMap(keyComparator2));
        this.keyComparator = keyComparator2;
        this.valueComparator = valueComparator2;
    }

    private TreeMultimap(Comparator<? super K> keyComparator2, Comparator<? super V> valueComparator2, Multimap<? extends K, ? extends V> multimap) {
        this(keyComparator2, valueComparator2);
        putAll(multimap);
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() {
        return new TreeMultimap<>(Ordering.natural(), Ordering.natural());
    }

    public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> keyComparator2, Comparator<? super V> valueComparator2) {
        return new TreeMultimap<>((Comparator) Preconditions.checkNotNull(keyComparator2), (Comparator) Preconditions.checkNotNull(valueComparator2));
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new TreeMultimap<>(Ordering.natural(), Ordering.natural(), multimap);
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsKey(@NullableDecl Object obj) {
        return super.containsKey(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ Set entries() {
        return super.entries();
    }

    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean put(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.put(obj, obj2);
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(@NullableDecl Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.remove(obj, obj2);
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ SortedSet removeAll(@NullableDecl Object obj) {
        return super.removeAll(obj);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.AbstractSortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection
     arg types: [java.lang.Object, java.lang.Iterable]
     candidates:
      com.google.common.collect.AbstractSortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection
      com.google.common.collect.AbstractSortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set
      com.google.common.collect.AbstractSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection
      com.google.common.collect.AbstractSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
      com.google.common.collect.AbstractMapBasedMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.AbstractMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
      com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
      com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
      com.google.common.collect.AbstractSortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection */
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ SortedSet replaceValues(@NullableDecl Object obj, Iterable iterable) {
        return super.replaceValues(obj, iterable);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    /* access modifiers changed from: package-private */
    public Map<K, Collection<V>> createAsMap() {
        return createMaybeNavigableAsMap();
    }

    /* access modifiers changed from: package-private */
    public SortedSet<V> createCollection() {
        return new TreeSet(this.valueComparator);
    }

    /* access modifiers changed from: package-private */
    public Collection<V> createCollection(@NullableDecl K key) {
        if (key == null) {
            keyComparator().compare(key, key);
        }
        return super.createCollection(key);
    }

    @Deprecated
    public Comparator<? super K> keyComparator() {
        return this.keyComparator;
    }

    public Comparator<? super V> valueComparator() {
        return this.valueComparator;
    }

    @GwtIncompatible
    public NavigableSet<V> get(@NullableDecl K key) {
        return (NavigableSet) super.get((Object) key);
    }

    public NavigableSet<K> keySet() {
        return (NavigableSet) super.keySet();
    }

    public NavigableMap<K, Collection<V>> asMap() {
        return (NavigableMap) super.asMap();
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(keyComparator());
        stream.writeObject(valueComparator());
        Serialization.writeMultimap(this, stream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyComparator = (Comparator) Preconditions.checkNotNull((Comparator) stream.readObject());
        this.valueComparator = (Comparator) Preconditions.checkNotNull((Comparator) stream.readObject());
        setMap(new TreeMap(this.keyComparator));
        Serialization.populateMultimap(this, stream);
    }
}
