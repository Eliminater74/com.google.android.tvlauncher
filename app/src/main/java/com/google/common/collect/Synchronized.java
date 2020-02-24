package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
final class Synchronized {
    private Synchronized() {
    }

    static class SynchronizedObject implements Serializable {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        final Object delegate;
        final Object mutex;

        SynchronizedObject(Object delegate2, @NullableDecl Object mutex2) {
            this.delegate = Preconditions.checkNotNull(delegate2);
            this.mutex = mutex2 == null ? this : mutex2;
        }

        /* access modifiers changed from: package-private */
        public Object delegate() {
            return this.delegate;
        }

        public String toString() {
            String obj;
            synchronized (this.mutex) {
                obj = this.delegate.toString();
            }
            return obj;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream stream) throws IOException {
            synchronized (this.mutex) {
                stream.defaultWriteObject();
            }
        }
    }

    /* access modifiers changed from: private */
    public static <E> Collection<E> collection(Collection<E> collection, @NullableDecl Object mutex) {
        return new SynchronizedCollection(collection, mutex);
    }

    @VisibleForTesting
    static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E> {
        private static final long serialVersionUID = 0;

        private SynchronizedCollection(Collection<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Collection<E> delegate() {
            return (Collection) super.delegate();
        }

        public boolean add(E e) {
            boolean add;
            synchronized (this.mutex) {
                add = delegate().add(e);
            }
            return add;
        }

        public boolean addAll(Collection<? extends E> c) {
            boolean addAll;
            synchronized (this.mutex) {
                addAll = delegate().addAll(c);
            }
            return addAll;
        }

        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public boolean contains(Object o) {
            boolean contains;
            synchronized (this.mutex) {
                contains = delegate().contains(o);
            }
            return contains;
        }

        public boolean containsAll(Collection<?> c) {
            boolean containsAll;
            synchronized (this.mutex) {
                containsAll = delegate().containsAll(c);
            }
            return containsAll;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = delegate().isEmpty();
            }
            return isEmpty;
        }

        public Iterator<E> iterator() {
            return delegate().iterator();
        }

        public boolean remove(Object o) {
            boolean remove;
            synchronized (this.mutex) {
                remove = delegate().remove(o);
            }
            return remove;
        }

        public boolean removeAll(Collection<?> c) {
            boolean removeAll;
            synchronized (this.mutex) {
                removeAll = delegate().removeAll(c);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> c) {
            boolean retainAll;
            synchronized (this.mutex) {
                retainAll = delegate().retainAll(c);
            }
            return retainAll;
        }

        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Object[] toArray() {
            Object[] array;
            synchronized (this.mutex) {
                array = delegate().toArray();
            }
            return array;
        }

        public <T> T[] toArray(T[] a) {
            T[] array;
            synchronized (this.mutex) {
                array = delegate().toArray(a);
            }
            return array;
        }
    }

    @VisibleForTesting
    static <E> Set<E> set(Set<E> set, @NullableDecl Object mutex) {
        return new SynchronizedSet(set, mutex);
    }

    static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        SynchronizedSet(Set<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Set<E> delegate() {
            return (Set) super.delegate();
        }

        public boolean equals(Object o) {
            boolean equals;
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(o);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }
    }

    /* access modifiers changed from: private */
    public static <E> SortedSet<E> sortedSet(SortedSet<E> set, @NullableDecl Object mutex) {
        return new SynchronizedSortedSet(set, mutex);
    }

    static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedSet(SortedSet<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public SortedSet<E> delegate() {
            return (SortedSet) super.delegate();
        }

        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        public SortedSet<E> subSet(E fromElement, E toElement) {
            SortedSet<E> access$100;
            synchronized (this.mutex) {
                access$100 = Synchronized.sortedSet(delegate().subSet(fromElement, toElement), this.mutex);
            }
            return access$100;
        }

        public SortedSet<E> headSet(E toElement) {
            SortedSet<E> access$100;
            synchronized (this.mutex) {
                access$100 = Synchronized.sortedSet(delegate().headSet(toElement), this.mutex);
            }
            return access$100;
        }

        public SortedSet<E> tailSet(E fromElement) {
            SortedSet<E> access$100;
            synchronized (this.mutex) {
                access$100 = Synchronized.sortedSet(delegate().tailSet(fromElement), this.mutex);
            }
            return access$100;
        }

        public E first() {
            E first;
            synchronized (this.mutex) {
                first = delegate().first();
            }
            return first;
        }

        public E last() {
            E last;
            synchronized (this.mutex) {
                last = delegate().last();
            }
            return last;
        }
    }

    /* access modifiers changed from: private */
    public static <E> List<E> list(List<E> list, @NullableDecl Object mutex) {
        if (list instanceof RandomAccess) {
            return new SynchronizedRandomAccessList(list, mutex);
        }
        return new SynchronizedList(list, mutex);
    }

    private static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
        private static final long serialVersionUID = 0;

        SynchronizedList(List<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public List<E> delegate() {
            return (List) super.delegate();
        }

        public void add(int index, E element) {
            synchronized (this.mutex) {
                delegate().add(index, element);
            }
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            boolean addAll;
            synchronized (this.mutex) {
                addAll = delegate().addAll(index, c);
            }
            return addAll;
        }

        public E get(int index) {
            E e;
            synchronized (this.mutex) {
                e = delegate().get(index);
            }
            return e;
        }

        public int indexOf(Object o) {
            int indexOf;
            synchronized (this.mutex) {
                indexOf = delegate().indexOf(o);
            }
            return indexOf;
        }

        public int lastIndexOf(Object o) {
            int lastIndexOf;
            synchronized (this.mutex) {
                lastIndexOf = delegate().lastIndexOf(o);
            }
            return lastIndexOf;
        }

        public ListIterator<E> listIterator() {
            return delegate().listIterator();
        }

        public ListIterator<E> listIterator(int index) {
            return delegate().listIterator(index);
        }

        public E remove(int index) {
            E remove;
            synchronized (this.mutex) {
                remove = delegate().remove(index);
            }
            return remove;
        }

        public E set(int index, E element) {
            E e;
            synchronized (this.mutex) {
                e = delegate().set(index, element);
            }
            return e;
        }

        public List<E> subList(int fromIndex, int toIndex) {
            List<E> access$200;
            synchronized (this.mutex) {
                access$200 = Synchronized.list(delegate().subList(fromIndex, toIndex), this.mutex);
            }
            return access$200;
        }

        public boolean equals(Object o) {
            boolean equals;
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(o);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }
    }

    private static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
        private static final long serialVersionUID = 0;

        SynchronizedRandomAccessList(List<E> list, @NullableDecl Object mutex) {
            super(list, mutex);
        }
    }

    static <E> Multiset<E> multiset(Multiset<E> multiset, @NullableDecl Object mutex) {
        if ((multiset instanceof SynchronizedMultiset) || (multiset instanceof ImmutableMultiset)) {
            return multiset;
        }
        return new SynchronizedMultiset(multiset, mutex);
    }

    private static class SynchronizedMultiset<E> extends SynchronizedCollection<E> implements Multiset<E> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<E> elementSet;
        @MonotonicNonNullDecl
        transient Set<Multiset.Entry<E>> entrySet;

        SynchronizedMultiset(Multiset<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Multiset<E> delegate() {
            return (Multiset) super.delegate();
        }

        public int count(Object o) {
            int count;
            synchronized (this.mutex) {
                count = delegate().count(o);
            }
            return count;
        }

        public int add(E e, int n) {
            int add;
            synchronized (this.mutex) {
                add = delegate().add(e, n);
            }
            return add;
        }

        public int remove(Object o, int n) {
            int remove;
            synchronized (this.mutex) {
                remove = delegate().remove(o, n);
            }
            return remove;
        }

        public int setCount(E element, int count) {
            int count2;
            synchronized (this.mutex) {
                count2 = delegate().setCount(element, count);
            }
            return count2;
        }

        public boolean setCount(E element, int oldCount, int newCount) {
            boolean count;
            synchronized (this.mutex) {
                count = delegate().setCount(element, oldCount, newCount);
            }
            return count;
        }

        public Set<E> elementSet() {
            Set<E> set;
            synchronized (this.mutex) {
                if (this.elementSet == null) {
                    this.elementSet = Synchronized.typePreservingSet(delegate().elementSet(), this.mutex);
                }
                set = this.elementSet;
            }
            return set;
        }

        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.typePreservingSet(delegate().entrySet(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        public boolean equals(Object o) {
            boolean equals;
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(o);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }
    }

    static <K, V> Multimap<K, V> multimap(Multimap<K, V> multimap, @NullableDecl Object mutex) {
        if ((multimap instanceof SynchronizedMultimap) || (multimap instanceof BaseImmutableMultimap)) {
            return multimap;
        }
        return new SynchronizedMultimap(multimap, mutex);
    }

    private static class SynchronizedMultimap<K, V> extends SynchronizedObject implements Multimap<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Map<K, Collection<V>> asMap;
        @MonotonicNonNullDecl
        transient Collection<Map.Entry<K, V>> entries;
        @MonotonicNonNullDecl
        transient Set<K> keySet;
        @MonotonicNonNullDecl
        transient Multiset<K> keys;
        @MonotonicNonNullDecl
        transient Collection<V> valuesCollection;

        /* access modifiers changed from: package-private */
        public Multimap<K, V> delegate() {
            return (Multimap) super.delegate();
        }

        SynchronizedMultimap(Multimap<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = delegate().isEmpty();
            }
            return isEmpty;
        }

        public boolean containsKey(Object key) {
            boolean containsKey;
            synchronized (this.mutex) {
                containsKey = delegate().containsKey(key);
            }
            return containsKey;
        }

        public boolean containsValue(Object value) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = delegate().containsValue(value);
            }
            return containsValue;
        }

        public boolean containsEntry(Object key, Object value) {
            boolean containsEntry;
            synchronized (this.mutex) {
                containsEntry = delegate().containsEntry(key, value);
            }
            return containsEntry;
        }

        public Collection<V> get(K key) {
            Collection<V> access$400;
            synchronized (this.mutex) {
                access$400 = Synchronized.typePreservingCollection(delegate().get(key), this.mutex);
            }
            return access$400;
        }

        public boolean put(K key, V value) {
            boolean put;
            synchronized (this.mutex) {
                put = delegate().put(key, value);
            }
            return put;
        }

        public boolean putAll(K key, Iterable<? extends V> values) {
            boolean putAll;
            synchronized (this.mutex) {
                putAll = delegate().putAll(key, values);
            }
            return putAll;
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean putAll;
            synchronized (this.mutex) {
                putAll = delegate().putAll(multimap);
            }
            return putAll;
        }

        public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
            Collection<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = delegate().replaceValues(key, values);
            }
            return replaceValues;
        }

        public boolean remove(Object key, Object value) {
            boolean remove;
            synchronized (this.mutex) {
                remove = delegate().remove(key, value);
            }
            return remove;
        }

        public Collection<V> removeAll(Object key) {
            Collection<V> removeAll;
            synchronized (this.mutex) {
                removeAll = delegate().removeAll(key);
            }
            return removeAll;
        }

        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.typePreservingSet(delegate().keySet(), this.mutex);
                }
                set = this.keySet;
            }
            return set;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.valuesCollection == null) {
                    this.valuesCollection = Synchronized.collection(delegate().values(), this.mutex);
                }
                collection = this.valuesCollection;
            }
            return collection;
        }

        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection;
            synchronized (this.mutex) {
                if (this.entries == null) {
                    this.entries = Synchronized.typePreservingCollection(delegate().entries(), this.mutex);
                }
                collection = this.entries;
            }
            return collection;
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map;
            synchronized (this.mutex) {
                if (this.asMap == null) {
                    this.asMap = new SynchronizedAsMap(delegate().asMap(), this.mutex);
                }
                map = this.asMap;
            }
            return map;
        }

        public Multiset<K> keys() {
            Multiset<K> multiset;
            synchronized (this.mutex) {
                if (this.keys == null) {
                    this.keys = Synchronized.multiset(delegate().keys(), this.mutex);
                }
                multiset = this.keys;
            }
            return multiset;
        }

        public boolean equals(Object o) {
            boolean equals;
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(o);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }
    }

    static <K, V> ListMultimap<K, V> listMultimap(ListMultimap<K, V> multimap, @NullableDecl Object mutex) {
        if ((multimap instanceof SynchronizedListMultimap) || (multimap instanceof BaseImmutableMultimap)) {
            return multimap;
        }
        return new SynchronizedListMultimap(multimap, mutex);
    }

    private static class SynchronizedListMultimap<K, V> extends SynchronizedMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedListMultimap(ListMultimap<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public ListMultimap<K, V> delegate() {
            return (ListMultimap) super.delegate();
        }

        public List<V> get(K key) {
            List<V> access$200;
            synchronized (this.mutex) {
                access$200 = Synchronized.list(delegate().get((Object) key), this.mutex);
            }
            return access$200;
        }

        public List<V> removeAll(Object key) {
            List<V> removeAll;
            synchronized (this.mutex) {
                removeAll = delegate().removeAll(key);
            }
            return removeAll;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V>
         arg types: [K, java.lang.Iterable<? extends V>]
         candidates:
          com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
          com.google.common.collect.ListMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.List<V> */
        public List<V> replaceValues(K key, Iterable<? extends V> values) {
            List<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = delegate().replaceValues((Object) key, (Iterable) values);
            }
            return replaceValues;
        }
    }

    static <K, V> SetMultimap<K, V> setMultimap(SetMultimap<K, V> multimap, @NullableDecl Object mutex) {
        if ((multimap instanceof SynchronizedSetMultimap) || (multimap instanceof BaseImmutableMultimap)) {
            return multimap;
        }
        return new SynchronizedSetMultimap(multimap, mutex);
    }

    private static class SynchronizedSetMultimap<K, V> extends SynchronizedMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, V>> entrySet;

        SynchronizedSetMultimap(SetMultimap<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public SetMultimap<K, V> delegate() {
            return (SetMultimap) super.delegate();
        }

        public Set<V> get(K key) {
            Set<V> set;
            synchronized (this.mutex) {
                set = Synchronized.set(delegate().get((Object) key), this.mutex);
            }
            return set;
        }

        public Set<V> removeAll(Object key) {
            Set<V> removeAll;
            synchronized (this.mutex) {
                removeAll = delegate().removeAll(key);
            }
            return removeAll;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
         arg types: [K, java.lang.Iterable<? extends V>]
         candidates:
          com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
          com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V> */
        public Set<V> replaceValues(K key, Iterable<? extends V> values) {
            Set<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = delegate().replaceValues((Object) key, (Iterable) values);
            }
            return replaceValues;
        }

        public Set<Map.Entry<K, V>> entries() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(delegate().entries(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }
    }

    static <K, V> SortedSetMultimap<K, V> sortedSetMultimap(SortedSetMultimap<K, V> multimap, @NullableDecl Object mutex) {
        if (multimap instanceof SynchronizedSortedSetMultimap) {
            return multimap;
        }
        return new SynchronizedSortedSetMultimap(multimap, mutex);
    }

    private static class SynchronizedSortedSetMultimap<K, V> extends SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }

        public SortedSet<V> get(K key) {
            SortedSet<V> access$100;
            synchronized (this.mutex) {
                access$100 = Synchronized.sortedSet(delegate().get((Object) key), this.mutex);
            }
            return access$100;
        }

        public SortedSet<V> removeAll(Object key) {
            SortedSet<V> removeAll;
            synchronized (this.mutex) {
                removeAll = delegate().removeAll(key);
            }
            return removeAll;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.SortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.SortedSet<V>
         arg types: [K, java.lang.Iterable<? extends V>]
         candidates:
          com.google.common.collect.SetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Set<V>
          com.google.common.collect.Multimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.Collection<V>
          com.google.common.collect.SortedSetMultimap.replaceValues(java.lang.Object, java.lang.Iterable):java.util.SortedSet<V> */
        public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
            SortedSet<V> replaceValues;
            synchronized (this.mutex) {
                replaceValues = delegate().replaceValues((Object) key, (Iterable) values);
            }
            return replaceValues;
        }

        public Comparator<? super V> valueComparator() {
            Comparator<? super V> valueComparator;
            synchronized (this.mutex) {
                valueComparator = delegate().valueComparator();
            }
            return valueComparator;
        }
    }

    /* access modifiers changed from: private */
    public static <E> Collection<E> typePreservingCollection(Collection<E> collection, @NullableDecl Object mutex) {
        if (collection instanceof SortedSet) {
            return sortedSet((SortedSet) collection, mutex);
        }
        if (collection instanceof Set) {
            return set((Set) collection, mutex);
        }
        if (collection instanceof List) {
            return list((List) collection, mutex);
        }
        return collection(collection, mutex);
    }

    /* access modifiers changed from: private */
    public static <E> Set<E> typePreservingSet(Set<E> set, @NullableDecl Object mutex) {
        if (set instanceof SortedSet) {
            return sortedSet((SortedSet) set, mutex);
        }
        return set(set, mutex);
    }

    private static class SynchronizedAsMapEntries<K, V> extends SynchronizedSet<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0;

        SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(super.iterator()) {
                /* access modifiers changed from: package-private */
                public Map.Entry<K, Collection<V>> transform(final Map.Entry<K, Collection<V>> entry) {
                    return new ForwardingMapEntry<K, Collection<V>>() {
                        /* access modifiers changed from: protected */
                        public Map.Entry<K, Collection<V>> delegate() {
                            return entry;
                        }

                        public Collection<V> getValue() {
                            return Synchronized.typePreservingCollection((Collection) entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                        }
                    };
                }
            };
        }

        public Object[] toArray() {
            Object[] arrayImpl;
            synchronized (this.mutex) {
                arrayImpl = ObjectArrays.toArrayImpl(delegate());
            }
            return arrayImpl;
        }

        public <T> T[] toArray(T[] array) {
            T[] arrayImpl;
            synchronized (this.mutex) {
                arrayImpl = ObjectArrays.toArrayImpl(delegate(), array);
            }
            return arrayImpl;
        }

        public boolean contains(Object o) {
            boolean containsEntryImpl;
            synchronized (this.mutex) {
                containsEntryImpl = Maps.containsEntryImpl(delegate(), o);
            }
            return containsEntryImpl;
        }

        public boolean containsAll(Collection<?> c) {
            boolean containsAllImpl;
            synchronized (this.mutex) {
                containsAllImpl = Collections2.containsAllImpl(delegate(), c);
            }
            return containsAllImpl;
        }

        public boolean equals(Object o) {
            boolean equalsImpl;
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                equalsImpl = Sets.equalsImpl(delegate(), o);
            }
            return equalsImpl;
        }

        public boolean remove(Object o) {
            boolean removeEntryImpl;
            synchronized (this.mutex) {
                removeEntryImpl = Maps.removeEntryImpl(delegate(), o);
            }
            return removeEntryImpl;
        }

        public boolean removeAll(Collection<?> c) {
            boolean removeAll;
            synchronized (this.mutex) {
                removeAll = Iterators.removeAll(delegate().iterator(), c);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> c) {
            boolean retainAll;
            synchronized (this.mutex) {
                retainAll = Iterators.retainAll(delegate().iterator(), c);
            }
            return retainAll;
        }
    }

    @VisibleForTesting
    static <K, V> Map<K, V> map(Map<K, V> map, @NullableDecl Object mutex) {
        return new SynchronizedMap(map, mutex);
    }

    private static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, V>> entrySet;
        @MonotonicNonNullDecl
        transient Set<K> keySet;
        @MonotonicNonNullDecl
        transient Collection<V> values;

        SynchronizedMap(Map<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Map<K, V> delegate() {
            return (Map) super.delegate();
        }

        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public boolean containsKey(Object key) {
            boolean containsKey;
            synchronized (this.mutex) {
                containsKey = delegate().containsKey(key);
            }
            return containsKey;
        }

        public boolean containsValue(Object value) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = delegate().containsValue(value);
            }
            return containsValue;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(delegate().entrySet(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        public V get(Object key) {
            V v;
            synchronized (this.mutex) {
                v = delegate().get(key);
            }
            return v;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = delegate().isEmpty();
            }
            return isEmpty;
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.set(delegate().keySet(), this.mutex);
                }
                set = this.keySet;
            }
            return set;
        }

        public V put(K key, V value) {
            V put;
            synchronized (this.mutex) {
                put = delegate().put(key, value);
            }
            return put;
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                delegate().putAll(map);
            }
        }

        public V remove(Object key) {
            V remove;
            synchronized (this.mutex) {
                remove = delegate().remove(key);
            }
            return remove;
        }

        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.values == null) {
                    this.values = Synchronized.collection(delegate().values(), this.mutex);
                }
                collection = this.values;
            }
            return collection;
        }

        public boolean equals(Object o) {
            boolean equals;
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(o);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }
    }

    static <K, V> SortedMap<K, V> sortedMap(SortedMap<K, V> sortedMap, @NullableDecl Object mutex) {
        return new SynchronizedSortedMap(sortedMap, mutex);
    }

    static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedMap(SortedMap<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public SortedMap<K, V> delegate() {
            return (SortedMap) super.delegate();
        }

        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        public K firstKey() {
            K firstKey;
            synchronized (this.mutex) {
                firstKey = delegate().firstKey();
            }
            return firstKey;
        }

        public SortedMap<K, V> headMap(K toKey) {
            SortedMap<K, V> sortedMap;
            synchronized (this.mutex) {
                sortedMap = Synchronized.sortedMap(delegate().headMap(toKey), this.mutex);
            }
            return sortedMap;
        }

        public K lastKey() {
            K lastKey;
            synchronized (this.mutex) {
                lastKey = delegate().lastKey();
            }
            return lastKey;
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            SortedMap<K, V> sortedMap;
            synchronized (this.mutex) {
                sortedMap = Synchronized.sortedMap(delegate().subMap(fromKey, toKey), this.mutex);
            }
            return sortedMap;
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            SortedMap<K, V> sortedMap;
            synchronized (this.mutex) {
                sortedMap = Synchronized.sortedMap(delegate().tailMap(fromKey), this.mutex);
            }
            return sortedMap;
        }
    }

    static <K, V> BiMap<K, V> biMap(BiMap<K, V> bimap, @NullableDecl Object mutex) {
        if ((bimap instanceof SynchronizedBiMap) || (bimap instanceof ImmutableBiMap)) {
            return bimap;
        }
        return new SynchronizedBiMap(bimap, mutex, null);
    }

    @VisibleForTesting
    static class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        @RetainedWith
        @MonotonicNonNullDecl
        private transient BiMap<V, K> inverse;
        @MonotonicNonNullDecl
        private transient Set<V> valueSet;

        private SynchronizedBiMap(BiMap<K, V> delegate, @NullableDecl Object mutex, @NullableDecl BiMap<V, K> inverse2) {
            super(delegate, mutex);
            this.inverse = inverse2;
        }

        /* access modifiers changed from: package-private */
        public BiMap<K, V> delegate() {
            return (BiMap) super.delegate();
        }

        public Set<V> values() {
            Set<V> set;
            synchronized (this.mutex) {
                if (this.valueSet == null) {
                    this.valueSet = Synchronized.set(delegate().values(), this.mutex);
                }
                set = this.valueSet;
            }
            return set;
        }

        public V forcePut(K key, V value) {
            V forcePut;
            synchronized (this.mutex) {
                forcePut = delegate().forcePut(key, value);
            }
            return forcePut;
        }

        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap;
            synchronized (this.mutex) {
                if (this.inverse == null) {
                    this.inverse = new SynchronizedBiMap(delegate().inverse(), this.mutex, this);
                }
                biMap = this.inverse;
            }
            return biMap;
        }
    }

    private static class SynchronizedAsMap<K, V> extends SynchronizedMap<K, Collection<V>> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, Collection<V>>> asMapEntrySet;
        @MonotonicNonNullDecl
        transient Collection<Collection<V>> asMapValues;

        SynchronizedAsMap(Map<K, Collection<V>> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        public Collection<V> get(Object key) {
            Collection<V> access$400;
            synchronized (this.mutex) {
                Collection<V> collection = (Collection) super.get(key);
                access$400 = collection == null ? null : Synchronized.typePreservingCollection(collection, this.mutex);
            }
            return access$400;
        }

        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Set<Map.Entry<K, Collection<V>>> set;
            synchronized (this.mutex) {
                if (this.asMapEntrySet == null) {
                    this.asMapEntrySet = new SynchronizedAsMapEntries(delegate().entrySet(), this.mutex);
                }
                set = this.asMapEntrySet;
            }
            return set;
        }

        public Collection<Collection<V>> values() {
            Collection<Collection<V>> collection;
            synchronized (this.mutex) {
                if (this.asMapValues == null) {
                    this.asMapValues = new SynchronizedAsMapValues(delegate().values(), this.mutex);
                }
                collection = this.asMapValues;
            }
            return collection;
        }

        public boolean containsValue(Object o) {
            return values().contains(o);
        }
    }

    private static class SynchronizedAsMapValues<V> extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0;

        SynchronizedAsMapValues(Collection<Collection<V>> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        public Iterator<Collection<V>> iterator() {
            return new TransformedIterator<Collection<V>, Collection<V>>(super.iterator()) {
                /* access modifiers changed from: package-private */
                public Collection<V> transform(Collection<V> from) {
                    return Synchronized.typePreservingCollection(from, SynchronizedAsMapValues.this.mutex);
                }
            };
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient NavigableSet<E> descendingSet;

        SynchronizedNavigableSet(NavigableSet<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public NavigableSet<E> delegate() {
            return (NavigableSet) super.delegate();
        }

        public E ceiling(E e) {
            E ceiling;
            synchronized (this.mutex) {
                ceiling = delegate().ceiling(e);
            }
            return ceiling;
        }

        public Iterator<E> descendingIterator() {
            return delegate().descendingIterator();
        }

        public NavigableSet<E> descendingSet() {
            synchronized (this.mutex) {
                if (this.descendingSet == null) {
                    NavigableSet<E> dS = Synchronized.navigableSet(delegate().descendingSet(), this.mutex);
                    this.descendingSet = dS;
                    return dS;
                }
                NavigableSet<E> dS2 = this.descendingSet;
                return dS2;
            }
        }

        public E floor(E e) {
            E floor;
            synchronized (this.mutex) {
                floor = delegate().floor(e);
            }
            return floor;
        }

        public NavigableSet<E> headSet(E toElement, boolean inclusive) {
            NavigableSet<E> navigableSet;
            synchronized (this.mutex) {
                navigableSet = Synchronized.navigableSet(delegate().headSet(toElement, inclusive), this.mutex);
            }
            return navigableSet;
        }

        public SortedSet<E> headSet(E toElement) {
            return headSet(toElement, false);
        }

        public E higher(E e) {
            E higher;
            synchronized (this.mutex) {
                higher = delegate().higher(e);
            }
            return higher;
        }

        public E lower(E e) {
            E lower;
            synchronized (this.mutex) {
                lower = delegate().lower(e);
            }
            return lower;
        }

        public E pollFirst() {
            E pollFirst;
            synchronized (this.mutex) {
                pollFirst = delegate().pollFirst();
            }
            return pollFirst;
        }

        public E pollLast() {
            E pollLast;
            synchronized (this.mutex) {
                pollLast = delegate().pollLast();
            }
            return pollLast;
        }

        public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            NavigableSet<E> navigableSet;
            synchronized (this.mutex) {
                navigableSet = Synchronized.navigableSet(delegate().subSet(fromElement, fromInclusive, toElement, toInclusive), this.mutex);
            }
            return navigableSet;
        }

        public SortedSet<E> subSet(E fromElement, E toElement) {
            return subSet(fromElement, true, toElement, false);
        }

        public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            NavigableSet<E> navigableSet;
            synchronized (this.mutex) {
                navigableSet = Synchronized.navigableSet(delegate().tailSet(fromElement, inclusive), this.mutex);
            }
            return navigableSet;
        }

        public SortedSet<E> tailSet(E fromElement) {
            return tailSet(fromElement, true);
        }
    }

    @GwtIncompatible
    static <E> NavigableSet<E> navigableSet(NavigableSet<E> navigableSet, @NullableDecl Object mutex) {
        return new SynchronizedNavigableSet(navigableSet, mutex);
    }

    @GwtIncompatible
    static <E> NavigableSet<E> navigableSet(NavigableSet<E> navigableSet) {
        return navigableSet(navigableSet, null);
    }

    @GwtIncompatible
    static <K, V> NavigableMap<K, V> navigableMap(NavigableMap<K, V> navigableMap) {
        return navigableMap(navigableMap, null);
    }

    @GwtIncompatible
    static <K, V> NavigableMap<K, V> navigableMap(NavigableMap<K, V> navigableMap, @NullableDecl Object mutex) {
        return new SynchronizedNavigableMap(navigableMap, mutex);
    }

    @GwtIncompatible
    @VisibleForTesting
    static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient NavigableSet<K> descendingKeySet;
        @MonotonicNonNullDecl
        transient NavigableMap<K, V> descendingMap;
        @MonotonicNonNullDecl
        transient NavigableSet<K> navigableKeySet;

        SynchronizedNavigableMap(NavigableMap<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public NavigableMap<K, V> delegate() {
            return (NavigableMap) super.delegate();
        }

        public Map.Entry<K, V> ceilingEntry(K key) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().ceilingEntry(key), this.mutex);
            }
            return access$700;
        }

        public K ceilingKey(K key) {
            K ceilingKey;
            synchronized (this.mutex) {
                ceilingKey = delegate().ceilingKey(key);
            }
            return ceilingKey;
        }

        public NavigableSet<K> descendingKeySet() {
            synchronized (this.mutex) {
                if (this.descendingKeySet == null) {
                    NavigableSet<K> navigableSet = Synchronized.navigableSet(delegate().descendingKeySet(), this.mutex);
                    this.descendingKeySet = navigableSet;
                    return navigableSet;
                }
                NavigableSet<K> navigableSet2 = this.descendingKeySet;
                return navigableSet2;
            }
        }

        public NavigableMap<K, V> descendingMap() {
            synchronized (this.mutex) {
                if (this.descendingMap == null) {
                    NavigableMap<K, V> navigableMap = Synchronized.navigableMap(delegate().descendingMap(), this.mutex);
                    this.descendingMap = navigableMap;
                    return navigableMap;
                }
                NavigableMap<K, V> navigableMap2 = this.descendingMap;
                return navigableMap2;
            }
        }

        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().firstEntry(), this.mutex);
            }
            return access$700;
        }

        public Map.Entry<K, V> floorEntry(K key) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().floorEntry(key), this.mutex);
            }
            return access$700;
        }

        public K floorKey(K key) {
            K floorKey;
            synchronized (this.mutex) {
                floorKey = delegate().floorKey(key);
            }
            return floorKey;
        }

        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            NavigableMap<K, V> navigableMap;
            synchronized (this.mutex) {
                navigableMap = Synchronized.navigableMap(delegate().headMap(toKey, inclusive), this.mutex);
            }
            return navigableMap;
        }

        public SortedMap<K, V> headMap(K toKey) {
            return headMap(toKey, false);
        }

        public Map.Entry<K, V> higherEntry(K key) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().higherEntry(key), this.mutex);
            }
            return access$700;
        }

        public K higherKey(K key) {
            K higherKey;
            synchronized (this.mutex) {
                higherKey = delegate().higherKey(key);
            }
            return higherKey;
        }

        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().lastEntry(), this.mutex);
            }
            return access$700;
        }

        public Map.Entry<K, V> lowerEntry(K key) {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().lowerEntry(key), this.mutex);
            }
            return access$700;
        }

        public K lowerKey(K key) {
            K lowerKey;
            synchronized (this.mutex) {
                lowerKey = delegate().lowerKey(key);
            }
            return lowerKey;
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            synchronized (this.mutex) {
                if (this.navigableKeySet == null) {
                    NavigableSet<K> navigableSet = Synchronized.navigableSet(delegate().navigableKeySet(), this.mutex);
                    this.navigableKeySet = navigableSet;
                    return navigableSet;
                }
                NavigableSet<K> navigableSet2 = this.navigableKeySet;
                return navigableSet2;
            }
        }

        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().pollFirstEntry(), this.mutex);
            }
            return access$700;
        }

        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> access$700;
            synchronized (this.mutex) {
                access$700 = Synchronized.nullableSynchronizedEntry(delegate().pollLastEntry(), this.mutex);
            }
            return access$700;
        }

        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            NavigableMap<K, V> navigableMap;
            synchronized (this.mutex) {
                navigableMap = Synchronized.navigableMap(delegate().subMap(fromKey, fromInclusive, toKey, toInclusive), this.mutex);
            }
            return navigableMap;
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            NavigableMap<K, V> navigableMap;
            synchronized (this.mutex) {
                navigableMap = Synchronized.navigableMap(delegate().tailMap(fromKey, inclusive), this.mutex);
            }
            return navigableMap;
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            return tailMap(fromKey, true);
        }
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static <K, V> Map.Entry<K, V> nullableSynchronizedEntry(@NullableDecl Map.Entry<K, V> entry, @NullableDecl Object mutex) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, mutex);
    }

    @GwtIncompatible
    private static class SynchronizedEntry<K, V> extends SynchronizedObject implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedEntry(Map.Entry<K, V> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Map.Entry<K, V> delegate() {
            return (Map.Entry) super.delegate();
        }

        public boolean equals(Object obj) {
            boolean equals;
            synchronized (this.mutex) {
                equals = delegate().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }

        public K getKey() {
            K key;
            synchronized (this.mutex) {
                key = delegate().getKey();
            }
            return key;
        }

        public V getValue() {
            V value;
            synchronized (this.mutex) {
                value = delegate().getValue();
            }
            return value;
        }

        public V setValue(V value) {
            V value2;
            synchronized (this.mutex) {
                value2 = delegate().setValue(value);
            }
            return value2;
        }
    }

    static <E> Queue<E> queue(Queue<E> queue, @NullableDecl Object mutex) {
        return queue instanceof SynchronizedQueue ? queue : new SynchronizedQueue(queue, mutex);
    }

    private static class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
        private static final long serialVersionUID = 0;

        SynchronizedQueue(Queue<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Queue<E> delegate() {
            return (Queue) super.delegate();
        }

        public E element() {
            E element;
            synchronized (this.mutex) {
                element = delegate().element();
            }
            return element;
        }

        public boolean offer(E e) {
            boolean offer;
            synchronized (this.mutex) {
                offer = delegate().offer(e);
            }
            return offer;
        }

        public E peek() {
            E peek;
            synchronized (this.mutex) {
                peek = delegate().peek();
            }
            return peek;
        }

        public E poll() {
            E poll;
            synchronized (this.mutex) {
                poll = delegate().poll();
            }
            return poll;
        }

        public E remove() {
            E remove;
            synchronized (this.mutex) {
                remove = delegate().remove();
            }
            return remove;
        }
    }

    static <E> Deque<E> deque(Deque<E> deque, @NullableDecl Object mutex) {
        return new SynchronizedDeque(deque, mutex);
    }

    private static final class SynchronizedDeque<E> extends SynchronizedQueue<E> implements Deque<E> {
        private static final long serialVersionUID = 0;

        SynchronizedDeque(Deque<E> delegate, @NullableDecl Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Deque<E> delegate() {
            return (Deque) super.delegate();
        }

        public void addFirst(E e) {
            synchronized (this.mutex) {
                delegate().addFirst(e);
            }
        }

        public void addLast(E e) {
            synchronized (this.mutex) {
                delegate().addLast(e);
            }
        }

        public boolean offerFirst(E e) {
            boolean offerFirst;
            synchronized (this.mutex) {
                offerFirst = delegate().offerFirst(e);
            }
            return offerFirst;
        }

        public boolean offerLast(E e) {
            boolean offerLast;
            synchronized (this.mutex) {
                offerLast = delegate().offerLast(e);
            }
            return offerLast;
        }

        public E removeFirst() {
            E removeFirst;
            synchronized (this.mutex) {
                removeFirst = delegate().removeFirst();
            }
            return removeFirst;
        }

        public E removeLast() {
            E removeLast;
            synchronized (this.mutex) {
                removeLast = delegate().removeLast();
            }
            return removeLast;
        }

        public E pollFirst() {
            E pollFirst;
            synchronized (this.mutex) {
                pollFirst = delegate().pollFirst();
            }
            return pollFirst;
        }

        public E pollLast() {
            E pollLast;
            synchronized (this.mutex) {
                pollLast = delegate().pollLast();
            }
            return pollLast;
        }

        public E getFirst() {
            E first;
            synchronized (this.mutex) {
                first = delegate().getFirst();
            }
            return first;
        }

        public E getLast() {
            E last;
            synchronized (this.mutex) {
                last = delegate().getLast();
            }
            return last;
        }

        public E peekFirst() {
            E peekFirst;
            synchronized (this.mutex) {
                peekFirst = delegate().peekFirst();
            }
            return peekFirst;
        }

        public E peekLast() {
            E peekLast;
            synchronized (this.mutex) {
                peekLast = delegate().peekLast();
            }
            return peekLast;
        }

        public boolean removeFirstOccurrence(Object o) {
            boolean removeFirstOccurrence;
            synchronized (this.mutex) {
                removeFirstOccurrence = delegate().removeFirstOccurrence(o);
            }
            return removeFirstOccurrence;
        }

        public boolean removeLastOccurrence(Object o) {
            boolean removeLastOccurrence;
            synchronized (this.mutex) {
                removeLastOccurrence = delegate().removeLastOccurrence(o);
            }
            return removeLastOccurrence;
        }

        public void push(E e) {
            synchronized (this.mutex) {
                delegate().push(e);
            }
        }

        public E pop() {
            E pop;
            synchronized (this.mutex) {
                pop = delegate().pop();
            }
            return pop;
        }

        public Iterator<E> descendingIterator() {
            Iterator<E> descendingIterator;
            synchronized (this.mutex) {
                descendingIterator = delegate().descendingIterator();
            }
            return descendingIterator;
        }
    }

    static <R, C, V> Table<R, C, V> table(Table<R, C, V> table, Object mutex) {
        return new SynchronizedTable(table, mutex);
    }

    private static final class SynchronizedTable<R, C, V> extends SynchronizedObject implements Table<R, C, V> {
        SynchronizedTable(Table<R, C, V> delegate, Object mutex) {
            super(delegate, mutex);
        }

        /* access modifiers changed from: package-private */
        public Table<R, C, V> delegate() {
            return (Table) super.delegate();
        }

        public boolean contains(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            boolean contains;
            synchronized (this.mutex) {
                contains = delegate().contains(rowKey, columnKey);
            }
            return contains;
        }

        public boolean containsRow(@NullableDecl Object rowKey) {
            boolean containsRow;
            synchronized (this.mutex) {
                containsRow = delegate().containsRow(rowKey);
            }
            return containsRow;
        }

        public boolean containsColumn(@NullableDecl Object columnKey) {
            boolean containsColumn;
            synchronized (this.mutex) {
                containsColumn = delegate().containsColumn(columnKey);
            }
            return containsColumn;
        }

        public boolean containsValue(@NullableDecl Object value) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = delegate().containsValue(value);
            }
            return containsValue;
        }

        public V get(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            V v;
            synchronized (this.mutex) {
                v = delegate().get(rowKey, columnKey);
            }
            return v;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = delegate().isEmpty();
            }
            return isEmpty;
        }

        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public V put(@NullableDecl R rowKey, @NullableDecl C columnKey, @NullableDecl V value) {
            V put;
            synchronized (this.mutex) {
                put = delegate().put(rowKey, columnKey, value);
            }
            return put;
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            synchronized (this.mutex) {
                delegate().putAll(table);
            }
        }

        public V remove(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            V remove;
            synchronized (this.mutex) {
                remove = delegate().remove(rowKey, columnKey);
            }
            return remove;
        }

        public Map<C, V> row(@NullableDecl R rowKey) {
            Map<C, V> map;
            synchronized (this.mutex) {
                map = Synchronized.map(delegate().row(rowKey), this.mutex);
            }
            return map;
        }

        public Map<R, V> column(@NullableDecl C columnKey) {
            Map<R, V> map;
            synchronized (this.mutex) {
                map = Synchronized.map(delegate().column(columnKey), this.mutex);
            }
            return map;
        }

        public Set<Table.Cell<R, C, V>> cellSet() {
            Set<Table.Cell<R, C, V>> set;
            synchronized (this.mutex) {
                set = Synchronized.set(delegate().cellSet(), this.mutex);
            }
            return set;
        }

        public Set<R> rowKeySet() {
            Set<R> set;
            synchronized (this.mutex) {
                set = Synchronized.set(delegate().rowKeySet(), this.mutex);
            }
            return set;
        }

        public Set<C> columnKeySet() {
            Set<C> set;
            synchronized (this.mutex) {
                set = Synchronized.set(delegate().columnKeySet(), this.mutex);
            }
            return set;
        }

        public Collection<V> values() {
            Collection<V> access$500;
            synchronized (this.mutex) {
                access$500 = Synchronized.collection(delegate().values(), this.mutex);
            }
            return access$500;
        }

        public Map<R, Map<C, V>> rowMap() {
            Map<R, Map<C, V>> map;
            synchronized (this.mutex) {
                map = Synchronized.map(Maps.transformValues(delegate().rowMap(), new Function<Map<C, V>, Map<C, V>>() {
                    public Map<C, V> apply(Map<C, V> t) {
                        return Synchronized.map(t, SynchronizedTable.this.mutex);
                    }
                }), this.mutex);
            }
            return map;
        }

        public Map<C, Map<R, V>> columnMap() {
            Map<C, Map<R, V>> map;
            synchronized (this.mutex) {
                map = Synchronized.map(Maps.transformValues(delegate().columnMap(), new Function<Map<R, V>, Map<R, V>>() {
                    public Map<R, V> apply(Map<R, V> t) {
                        return Synchronized.map(t, SynchronizedTable.this.mutex);
                    }
                }), this.mutex);
            }
            return map;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = delegate().hashCode();
            }
            return hashCode;
        }

        public boolean equals(@NullableDecl Object obj) {
            boolean equals;
            if (this == obj) {
                return true;
            }
            synchronized (this.mutex) {
                equals = delegate().equals(obj);
            }
            return equals;
        }
    }
}
