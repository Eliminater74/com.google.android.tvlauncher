package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public final class Multimaps {
    private Multimaps() {
    }

    public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> factory) {
        return new CustomMultimap(map, factory);
    }

    private static class CustomMultimap<K, V> extends AbstractMapBasedMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends Collection<V>> factory;

        CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> factory2) {
            super(map);
            this.factory = (Supplier) Preconditions.checkNotNull(factory2);
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return createMaybeNavigableKeySet();
        }

        /* access modifiers changed from: package-private */
        public Map<K, Collection<V>> createAsMap() {
            return createMaybeNavigableAsMap();
        }

        /* access modifiers changed from: protected */
        public Collection<V> createCollection() {
            return (Collection) this.factory.get();
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [java.util.Collection<E>, java.util.Collection] */
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public <E> java.util.Collection<E> unmodifiableCollectionSubclass(java.util.Collection<E> r2) {
            /*
                r1 = this;
                boolean r0 = r2 instanceof java.util.NavigableSet
                if (r0 == 0) goto L_0x000c
                r0 = r2
                java.util.NavigableSet r0 = (java.util.NavigableSet) r0
                java.util.NavigableSet r0 = com.google.common.collect.Sets.unmodifiableNavigableSet(r0)
                return r0
            L_0x000c:
                boolean r0 = r2 instanceof java.util.SortedSet
                if (r0 == 0) goto L_0x0018
                r0 = r2
                java.util.SortedSet r0 = (java.util.SortedSet) r0
                java.util.SortedSet r0 = java.util.Collections.unmodifiableSortedSet(r0)
                return r0
            L_0x0018:
                boolean r0 = r2 instanceof java.util.Set
                if (r0 == 0) goto L_0x0024
                r0 = r2
                java.util.Set r0 = (java.util.Set) r0
                java.util.Set r0 = java.util.Collections.unmodifiableSet(r0)
                return r0
            L_0x0024:
                boolean r0 = r2 instanceof java.util.List
                if (r0 == 0) goto L_0x0030
                r0 = r2
                java.util.List r0 = (java.util.List) r0
                java.util.List r0 = java.util.Collections.unmodifiableList(r0)
                return r0
            L_0x0030:
                java.util.Collection r0 = java.util.Collections.unmodifiableCollection(r2)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.CustomMultimap.unmodifiableCollectionSubclass(java.util.Collection):java.util.Collection");
        }

        /* access modifiers changed from: package-private */
        public Collection<V> wrapCollection(K key, Collection<V> collection) {
            if (collection instanceof List) {
                return wrapList(key, (List) collection, null);
            }
            if (collection instanceof NavigableSet) {
                return new AbstractMapBasedMultimap.WrappedNavigableSet(key, (NavigableSet) collection, null);
            }
            if (collection instanceof SortedSet) {
                return new AbstractMapBasedMultimap.WrappedSortedSet(key, (SortedSet) collection, null);
            }
            if (collection instanceof Set) {
                return new AbstractMapBasedMultimap.WrappedSet(key, (Set) collection);
            }
            return new AbstractMapBasedMultimap.WrappedCollection(key, collection, null);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(backingMap());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier) stream.readObject();
            setMap((Map) stream.readObject());
        }
    }

    public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> factory) {
        return new CustomListMultimap(map, factory);
    }

    private static class CustomListMultimap<K, V> extends AbstractListMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends List<V>> factory;

        CustomListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> factory2) {
            super(map);
            this.factory = (Supplier) Preconditions.checkNotNull(factory2);
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return createMaybeNavigableKeySet();
        }

        /* access modifiers changed from: package-private */
        public Map<K, Collection<V>> createAsMap() {
            return createMaybeNavigableAsMap();
        }

        /* access modifiers changed from: protected */
        public List<V> createCollection() {
            return (List) this.factory.get();
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(backingMap());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier) stream.readObject();
            setMap((Map) stream.readObject());
        }
    }

    public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> factory) {
        return new CustomSetMultimap(map, factory);
    }

    private static class CustomSetMultimap<K, V> extends AbstractSetMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends Set<V>> factory;

        CustomSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> factory2) {
            super(map);
            this.factory = (Supplier) Preconditions.checkNotNull(factory2);
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return createMaybeNavigableKeySet();
        }

        /* access modifiers changed from: package-private */
        public Map<K, Collection<V>> createAsMap() {
            return createMaybeNavigableAsMap();
        }

        /* access modifiers changed from: protected */
        public Set<V> createCollection() {
            return (Set) this.factory.get();
        }

        /* access modifiers changed from: package-private */
        public <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet) collection);
            }
            if (collection instanceof SortedSet) {
                return Collections.unmodifiableSortedSet((SortedSet) collection);
            }
            return Collections.unmodifiableSet((Set) collection);
        }

        /* access modifiers changed from: package-private */
        public Collection<V> wrapCollection(K key, Collection<V> collection) {
            if (collection instanceof NavigableSet) {
                return new AbstractMapBasedMultimap.WrappedNavigableSet(key, (NavigableSet) collection, null);
            }
            if (collection instanceof SortedSet) {
                return new AbstractMapBasedMultimap.WrappedSortedSet(key, (SortedSet) collection, null);
            }
            return new AbstractMapBasedMultimap.WrappedSet(key, (Set) collection);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(backingMap());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier) stream.readObject();
            setMap((Map) stream.readObject());
        }
    }

    public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> factory) {
        return new CustomSortedSetMultimap(map, factory);
    }

    private static class CustomSortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends SortedSet<V>> factory;
        transient Comparator<? super V> valueComparator;

        CustomSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> factory2) {
            super(map);
            this.factory = (Supplier) Preconditions.checkNotNull(factory2);
            this.valueComparator = ((SortedSet) factory2.get()).comparator();
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return createMaybeNavigableKeySet();
        }

        /* access modifiers changed from: package-private */
        public Map<K, Collection<V>> createAsMap() {
            return createMaybeNavigableAsMap();
        }

        /* access modifiers changed from: protected */
        public SortedSet<V> createCollection() {
            return (SortedSet) this.factory.get();
        }

        public Comparator<? super V> valueComparator() {
            return this.valueComparator;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(backingMap());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier) stream.readObject();
            this.valueComparator = ((SortedSet) this.factory.get()).comparator();
            setMap((Map) stream.readObject());
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: M
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    public static <K, V, M extends com.google.common.collect.Multimap<K, V>> M invertFrom(com.google.common.collect.Multimap<? extends V, ? extends K> r4, M r5) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r5)
            java.util.Collection r0 = r4.entries()
            java.util.Iterator r0 = r0.iterator()
        L_0x000b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0023
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getValue()
            java.lang.Object r3 = r1.getKey()
            r5.put(r2, r3)
            goto L_0x000b
        L_0x0023:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.invertFrom(com.google.common.collect.Multimap, com.google.common.collect.Multimap):com.google.common.collect.Multimap");
    }

    public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) {
        return Synchronized.multimap(multimap, null);
    }

    public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> delegate) {
        if ((delegate instanceof UnmodifiableMultimap) || (delegate instanceof ImmutableMultimap)) {
            return delegate;
        }
        return new UnmodifiableMultimap(delegate);
    }

    @Deprecated
    public static <K, V> Multimap<K, V> unmodifiableMultimap(ImmutableMultimap<K, V> delegate) {
        return (Multimap) Preconditions.checkNotNull(delegate);
    }

    private static class UnmodifiableMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final Multimap<K, V> delegate;
        @MonotonicNonNullDecl
        transient Collection<Map.Entry<K, V>> entries;
        @MonotonicNonNullDecl
        transient Set<K> keySet;
        @MonotonicNonNullDecl
        transient Multiset<K> keys;
        @MonotonicNonNullDecl
        transient Map<K, Collection<V>> map;
        @MonotonicNonNullDecl
        transient Collection<V> values;

        UnmodifiableMultimap(Multimap<K, V> delegate2) {
            this.delegate = (Multimap) Preconditions.checkNotNull(delegate2);
        }

        /* access modifiers changed from: protected */
        public Multimap<K, V> delegate() {
            return this.delegate;
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> result = this.map;
            if (result != null) {
                return result;
            }
            Map<K, Collection<V>> result2 = Collections.unmodifiableMap(Maps.transformValues(this.delegate.asMap(), new Function<Collection<V>, Collection<V>>(this) {
                public Collection<V> apply(Collection<V> collection) {
                    return Multimaps.unmodifiableValueCollection(collection);
                }
            }));
            this.map = result2;
            return result2;
        }

        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> result = this.entries;
            if (result != null) {
                return result;
            }
            Collection<Map.Entry<K, V>> access$100 = Multimaps.unmodifiableEntries(this.delegate.entries());
            Collection<Map.Entry<K, V>> result2 = access$100;
            this.entries = access$100;
            return result2;
        }

        public Collection<V> get(K key) {
            return Multimaps.unmodifiableValueCollection(this.delegate.get(key));
        }

        public Multiset<K> keys() {
            Multiset<K> result = this.keys;
            if (result != null) {
                return result;
            }
            Multiset<K> unmodifiableMultiset = Multisets.unmodifiableMultiset(this.delegate.keys());
            Multiset<K> result2 = unmodifiableMultiset;
            this.keys = unmodifiableMultiset;
            return result2;
        }

        public Set<K> keySet() {
            Set<K> result = this.keySet;
            if (result != null) {
                return result;
            }
            Set<K> unmodifiableSet = Collections.unmodifiableSet(this.delegate.keySet());
            Set<K> result2 = unmodifiableSet;
            this.keySet = unmodifiableSet;
            return result2;
        }

        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> removeAll(Object key) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> values() {
            Collection<V> result = this.values;
            if (result != null) {
                return result;
            }
            Collection<V> unmodifiableCollection = Collections.unmodifiableCollection(this.delegate.values());
            Collection<V> result2 = unmodifiableCollection;
            this.values = unmodifiableCollection;
            return result2;
        }
    }

    private static class UnmodifiableListMultimap<K, V> extends UnmodifiableMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        UnmodifiableListMultimap(ListMultimap<K, V> delegate) {
            super(delegate);
        }

        public ListMultimap<K, V> delegate() {
            return (ListMultimap) super.delegate();
        }

        public List<V> get(K key) {
            return Collections.unmodifiableList(delegate().get((Object) key));
        }

        public List<V> removeAll(Object key) {
            throw new UnsupportedOperationException();
        }

        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableSetMultimap<K, V> extends UnmodifiableMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        UnmodifiableSetMultimap(SetMultimap<K, V> delegate) {
            super(delegate);
        }

        public SetMultimap<K, V> delegate() {
            return (SetMultimap) super.delegate();
        }

        public Set<V> get(K key) {
            return Collections.unmodifiableSet(delegate().get((Object) key));
        }

        public Set<Map.Entry<K, V>> entries() {
            return Maps.unmodifiableEntrySet(delegate().entries());
        }

        public Set<V> removeAll(Object key) {
            throw new UnsupportedOperationException();
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableSortedSetMultimap<K, V> extends UnmodifiableSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> delegate) {
            super(delegate);
        }

        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }

        public SortedSet<V> get(K key) {
            return Collections.unmodifiableSortedSet(delegate().get((Object) key));
        }

        public SortedSet<V> removeAll(Object key) {
            throw new UnsupportedOperationException();
        }

        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public Comparator<? super V> valueComparator() {
            return delegate().valueComparator();
        }
    }

    public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> multimap) {
        return Synchronized.setMultimap(multimap, null);
    }

    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> delegate) {
        if ((delegate instanceof UnmodifiableSetMultimap) || (delegate instanceof ImmutableSetMultimap)) {
            return delegate;
        }
        return new UnmodifiableSetMultimap(delegate);
    }

    @Deprecated
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(ImmutableSetMultimap<K, V> delegate) {
        return (SetMultimap) Preconditions.checkNotNull(delegate);
    }

    public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> multimap) {
        return Synchronized.sortedSetMultimap(multimap, null);
    }

    public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> delegate) {
        if (delegate instanceof UnmodifiableSortedSetMultimap) {
            return delegate;
        }
        return new UnmodifiableSortedSetMultimap(delegate);
    }

    public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> multimap) {
        return Synchronized.listMultimap(multimap, null);
    }

    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> delegate) {
        if ((delegate instanceof UnmodifiableListMultimap) || (delegate instanceof ImmutableListMultimap)) {
            return delegate;
        }
        return new UnmodifiableListMultimap(delegate);
    }

    @Deprecated
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ImmutableListMultimap<K, V> delegate) {
        return (ListMultimap) Preconditions.checkNotNull(delegate);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.Collection<V>, java.util.Collection] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <V> java.util.Collection<V> unmodifiableValueCollection(java.util.Collection<V> r1) {
        /*
            boolean r0 = r1 instanceof java.util.SortedSet
            if (r0 == 0) goto L_0x000c
            r0 = r1
            java.util.SortedSet r0 = (java.util.SortedSet) r0
            java.util.SortedSet r0 = java.util.Collections.unmodifiableSortedSet(r0)
            return r0
        L_0x000c:
            boolean r0 = r1 instanceof java.util.Set
            if (r0 == 0) goto L_0x0018
            r0 = r1
            java.util.Set r0 = (java.util.Set) r0
            java.util.Set r0 = java.util.Collections.unmodifiableSet(r0)
            return r0
        L_0x0018:
            boolean r0 = r1 instanceof java.util.List
            if (r0 == 0) goto L_0x0024
            r0 = r1
            java.util.List r0 = (java.util.List) r0
            java.util.List r0 = java.util.Collections.unmodifiableList(r0)
            return r0
        L_0x0024:
            java.util.Collection r0 = java.util.Collections.unmodifiableCollection(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.unmodifiableValueCollection(java.util.Collection):java.util.Collection");
    }

    /* access modifiers changed from: private */
    public static <K, V> Collection<Map.Entry<K, V>> unmodifiableEntries(Collection<Map.Entry<K, V>> entries) {
        if (entries instanceof Set) {
            return Maps.unmodifiableEntrySet((Set) entries);
        }
        return new Maps.UnmodifiableEntries(Collections.unmodifiableCollection(entries));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.common.collect.ListMultimap<K, V>, com.google.common.collect.ListMultimap] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @com.google.common.annotations.Beta
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <K, V> java.util.Map<K, java.util.List<V>> asMap(com.google.common.collect.ListMultimap<K, V> r1) {
        /*
            java.util.Map r0 = r1.asMap()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.asMap(com.google.common.collect.ListMultimap):java.util.Map");
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.common.collect.SetMultimap<K, V>, com.google.common.collect.SetMultimap] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @com.google.common.annotations.Beta
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <K, V> java.util.Map<K, java.util.Set<V>> asMap(com.google.common.collect.SetMultimap<K, V> r1) {
        /*
            java.util.Map r0 = r1.asMap()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.asMap(com.google.common.collect.SetMultimap):java.util.Map");
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.common.collect.SortedSetMultimap<K, V>, com.google.common.collect.SortedSetMultimap] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @com.google.common.annotations.Beta
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <K, V> java.util.Map<K, java.util.SortedSet<V>> asMap(com.google.common.collect.SortedSetMultimap<K, V> r1) {
        /*
            java.util.Map r0 = r1.asMap()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.asMap(com.google.common.collect.SortedSetMultimap):java.util.Map");
    }

    @Beta
    public static <K, V> Map<K, Collection<V>> asMap(Multimap<K, V> multimap) {
        return multimap.asMap();
    }

    public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) {
        return new MapMultimap(map);
    }

    private static class MapMultimap<K, V> extends AbstractMultimap<K, V> implements SetMultimap<K, V>, Serializable {
        private static final long serialVersionUID = 7845222491160860175L;
        final Map<K, V> map;

        MapMultimap(Map<K, V> map2) {
            this.map = (Map) Preconditions.checkNotNull(map2);
        }

        public int size() {
            return this.map.size();
        }

        public boolean containsKey(Object key) {
            return this.map.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return this.map.containsValue(value);
        }

        public boolean containsEntry(Object key, Object value) {
            return this.map.entrySet().contains(Maps.immutableEntry(key, value));
        }

        public Set<V> get(final K key) {
            return new Sets.ImprovedAbstractSet<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {

                        /* renamed from: i */
                        int f172i;

                        public boolean hasNext() {
                            return this.f172i == 0 && MapMultimap.this.map.containsKey(key);
                        }

                        public V next() {
                            if (hasNext()) {
                                this.f172i++;
                                return MapMultimap.this.map.get(key);
                            }
                            throw new NoSuchElementException();
                        }

                        public void remove() {
                            boolean z = true;
                            if (this.f172i != 1) {
                                z = false;
                            }
                            CollectPreconditions.checkRemove(z);
                            this.f172i = -1;
                            MapMultimap.this.map.remove(key);
                        }
                    };
                }

                public int size() {
                    return MapMultimap.this.map.containsKey(key) ? 1 : 0;
                }
            };
        }

        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object key, Object value) {
            return this.map.entrySet().remove(Maps.immutableEntry(key, value));
        }

        public Set<V> removeAll(Object key) {
            Set<V> values = new HashSet<>(2);
            if (!this.map.containsKey(key)) {
                return values;
            }
            values.add(this.map.remove(key));
            return values;
        }

        public void clear() {
            this.map.clear();
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return this.map.keySet();
        }

        /* access modifiers changed from: package-private */
        public Collection<V> createValues() {
            return this.map.values();
        }

        public Set<Map.Entry<K, V>> entries() {
            return this.map.entrySet();
        }

        /* access modifiers changed from: package-private */
        public Collection<Map.Entry<K, V>> createEntries() {
            throw new AssertionError("unreachable");
        }

        /* access modifiers changed from: package-private */
        public Multiset<K> createKeys() {
            return new Keys(this);
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return this.map.entrySet().iterator();
        }

        /* access modifiers changed from: package-private */
        public Map<K, Collection<V>> createAsMap() {
            return new AsMap(this);
        }

        public int hashCode() {
            return this.map.hashCode();
        }
    }

    public static <K, V1, V2> Multimap<K, V2> transformValues(Multimap<K, V1> fromMultimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return transformEntries(fromMultimap, Maps.asEntryTransformer(function));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.transformEntries(com.google.common.collect.ListMultimap, com.google.common.collect.Maps$EntryTransformer):com.google.common.collect.ListMultimap<K, V2>
     arg types: [com.google.common.collect.ListMultimap<K, V1>, com.google.common.collect.Maps$EntryTransformer<K, V1, V2>]
     candidates:
      com.google.common.collect.Multimaps.transformEntries(com.google.common.collect.Multimap, com.google.common.collect.Maps$EntryTransformer):com.google.common.collect.Multimap<K, V2>
      com.google.common.collect.Multimaps.transformEntries(com.google.common.collect.ListMultimap, com.google.common.collect.Maps$EntryTransformer):com.google.common.collect.ListMultimap<K, V2> */
    public static <K, V1, V2> ListMultimap<K, V2> transformValues(ListMultimap<K, V1> fromMultimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return transformEntries((ListMultimap) fromMultimap, (Maps.EntryTransformer) Maps.asEntryTransformer(function));
    }

    public static <K, V1, V2> Multimap<K, V2> transformEntries(Multimap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesMultimap(fromMap, transformer);
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformEntries(ListMultimap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesListMultimap(fromMap, transformer);
    }

    private static class TransformedEntriesMultimap<K, V1, V2> extends AbstractMultimap<K, V2> {
        final Multimap<K, V1> fromMultimap;
        final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;

        TransformedEntriesMultimap(Multimap<K, V1> fromMultimap2, Maps.EntryTransformer<? super K, ? super V1, V2> transformer2) {
            this.fromMultimap = (Multimap) Preconditions.checkNotNull(fromMultimap2);
            this.transformer = (Maps.EntryTransformer) Preconditions.checkNotNull(transformer2);
        }

        /* JADX WARN: Type inference failed for: r4v0, types: [java.util.Collection, java.util.Collection<V1>] */
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.Collection<V2> transform(K r3, java.util.Collection<V1> r4) {
            /*
                r2 = this;
                com.google.common.collect.Maps$EntryTransformer<? super K, ? super V1, V2> r0 = r2.transformer
                com.google.common.base.Function r0 = com.google.common.collect.Maps.asValueToValueFunction(r0, r3)
                boolean r1 = r4 instanceof java.util.List
                if (r1 == 0) goto L_0x0012
                r1 = r4
                java.util.List r1 = (java.util.List) r1
                java.util.List r1 = com.google.common.collect.Lists.transform(r1, r0)
                return r1
            L_0x0012:
                java.util.Collection r1 = com.google.common.collect.Collections2.transform(r4, r0)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Multimaps.TransformedEntriesMultimap.transform(java.lang.Object, java.util.Collection):java.util.Collection");
        }

        /* access modifiers changed from: package-private */
        public Map<K, Collection<V2>> createAsMap() {
            return Maps.transformEntries(this.fromMultimap.asMap(), new Maps.EntryTransformer<K, Collection<V1>, Collection<V2>>() {
                public Collection<V2> transformEntry(K key, Collection<V1> value) {
                    return TransformedEntriesMultimap.this.transform(key, value);
                }
            });
        }

        public void clear() {
            this.fromMultimap.clear();
        }

        public boolean containsKey(Object key) {
            return this.fromMultimap.containsKey(key);
        }

        /* access modifiers changed from: package-private */
        public Collection<Map.Entry<K, V2>> createEntries() {
            return new AbstractMultimap.Entries();
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V2>> entryIterator() {
            return Iterators.transform(this.fromMultimap.entries().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        public Collection<V2> get(K key) {
            return transform(key, this.fromMultimap.get(key));
        }

        public boolean isEmpty() {
            return this.fromMultimap.isEmpty();
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return this.fromMultimap.keySet();
        }

        /* access modifiers changed from: package-private */
        public Multiset<K> createKeys() {
            return this.fromMultimap.keys();
        }

        public boolean put(K k, V2 v2) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(Multimap<? extends K, ? extends V2> multimap) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object key, Object value) {
            return get(key).remove(value);
        }

        public Collection<V2> removeAll(Object key) {
            return transform(key, this.fromMultimap.removeAll(key));
        }

        public Collection<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.fromMultimap.size();
        }

        /* access modifiers changed from: package-private */
        public Collection<V2> createValues() {
            return Collections2.transform(this.fromMultimap.entries(), Maps.asEntryToValueFunction(this.transformer));
        }
    }

    private static final class TransformedEntriesListMultimap<K, V1, V2> extends TransformedEntriesMultimap<K, V1, V2> implements ListMultimap<K, V2> {
        TransformedEntriesListMultimap(ListMultimap<K, V1> fromMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
            super(fromMultimap, transformer);
        }

        /* access modifiers changed from: package-private */
        public List<V2> transform(K key, Collection<V1> values) {
            return Lists.transform((List) values, Maps.asValueToValueFunction(this.transformer, key));
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Multimaps.TransformedEntriesListMultimap.transform(java.lang.Object, java.util.Collection):java.util.List<V2>
         arg types: [K, java.util.Collection]
         candidates:
          com.google.common.collect.Multimaps.TransformedEntriesListMultimap.transform(java.lang.Object, java.util.Collection):java.util.Collection
          com.google.common.collect.Multimaps.TransformedEntriesMultimap.transform(java.lang.Object, java.util.Collection):java.util.Collection<V2>
          com.google.common.collect.Multimaps.TransformedEntriesListMultimap.transform(java.lang.Object, java.util.Collection):java.util.List<V2> */
        public List<V2> get(K key) {
            return transform((Object) key, this.fromMultimap.get(key));
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Multimaps.TransformedEntriesListMultimap.transform(java.lang.Object, java.util.Collection):java.util.List<V2>
         arg types: [java.lang.Object, java.util.Collection]
         candidates:
          com.google.common.collect.Multimaps.TransformedEntriesListMultimap.transform(java.lang.Object, java.util.Collection):java.util.Collection
          com.google.common.collect.Multimaps.TransformedEntriesMultimap.transform(java.lang.Object, java.util.Collection):java.util.Collection<V2>
          com.google.common.collect.Multimaps.TransformedEntriesListMultimap.transform(java.lang.Object, java.util.Collection):java.util.List<V2> */
        public List<V2> removeAll(Object key) {
            return transform(key, this.fromMultimap.removeAll(key));
        }

        public List<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> values, Function<? super V, K> keyFunction) {
        return index(values.iterator(), keyFunction);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder
      com.google.common.collect.ImmutableMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap$Builder<K, V>
      com.google.common.collect.ImmutableListMultimap.Builder.put(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap$Builder<K, V> */
    public static <K, V> ImmutableListMultimap<K, V> index(Iterator<V> values, Function<? super V, K> keyFunction) {
        Preconditions.checkNotNull(keyFunction);
        ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
        while (values.hasNext()) {
            V value = values.next();
            Preconditions.checkNotNull(value, values);
            builder.put((Object) keyFunction.apply(value), (Object) value);
        }
        return builder.build();
    }

    static class Keys<K, V> extends AbstractMultiset<K> {
        @Weak
        final Multimap<K, V> multimap;

        Keys(Multimap<K, V> multimap2) {
            this.multimap = multimap2;
        }

        /* access modifiers changed from: package-private */
        public Iterator<Multiset.Entry<K>> entryIterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Multiset.Entry<K>>(this, this.multimap.asMap().entrySet().iterator()) {
                /* access modifiers changed from: package-private */
                public Multiset.Entry<K> transform(final Map.Entry<K, Collection<V>> backingEntry) {
                    return new Multisets.AbstractEntry<K>(this) {
                        public K getElement() {
                            return backingEntry.getKey();
                        }

                        public int getCount() {
                            return ((Collection) backingEntry.getValue()).size();
                        }
                    };
                }
            };
        }

        /* access modifiers changed from: package-private */
        public int distinctElements() {
            return this.multimap.asMap().size();
        }

        public int size() {
            return this.multimap.size();
        }

        public boolean contains(@NullableDecl Object element) {
            return this.multimap.containsKey(element);
        }

        public Iterator<K> iterator() {
            return Maps.keyIterator(this.multimap.entries().iterator());
        }

        public int count(@NullableDecl Object element) {
            Collection<V> values = (Collection) Maps.safeGet(this.multimap.asMap(), element);
            if (values == null) {
                return 0;
            }
            return values.size();
        }

        public int remove(@NullableDecl Object element, int occurrences) {
            CollectPreconditions.checkNonnegative(occurrences, "occurrences");
            if (occurrences == 0) {
                return count(element);
            }
            Collection<V> values = (Collection) Maps.safeGet(this.multimap.asMap(), element);
            if (values == null) {
                return 0;
            }
            int oldCount = values.size();
            if (occurrences >= oldCount) {
                values.clear();
            } else {
                Iterator<V> iterator = values.iterator();
                for (int i = 0; i < occurrences; i++) {
                    iterator.next();
                    iterator.remove();
                }
            }
            return oldCount;
        }

        public void clear() {
            this.multimap.clear();
        }

        public Set<K> elementSet() {
            return this.multimap.keySet();
        }

        /* access modifiers changed from: package-private */
        public Iterator<K> elementIterator() {
            throw new AssertionError("should never be called");
        }
    }

    static abstract class Entries<K, V> extends AbstractCollection<Map.Entry<K, V>> {
        /* access modifiers changed from: package-private */
        public abstract Multimap<K, V> multimap();

        Entries() {
        }

        public int size() {
            return multimap().size();
        }

        public boolean contains(@NullableDecl Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            return multimap().containsEntry(entry.getKey(), entry.getValue());
        }

        public boolean remove(@NullableDecl Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            return multimap().remove(entry.getKey(), entry.getValue());
        }

        public void clear() {
            multimap().clear();
        }
    }

    static final class AsMap<K, V> extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        /* access modifiers changed from: private */
        @Weak
        public final Multimap<K, V> multimap;

        AsMap(Multimap<K, V> multimap2) {
            this.multimap = (Multimap) Preconditions.checkNotNull(multimap2);
        }

        public int size() {
            return this.multimap.keySet().size();
        }

        /* access modifiers changed from: protected */
        public Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new EntrySet();
        }

        /* access modifiers changed from: package-private */
        public void removeValuesForKey(Object key) {
            this.multimap.keySet().remove(key);
        }

        class EntrySet extends Maps.EntrySet<K, Collection<V>> {
            EntrySet() {
            }

            /* access modifiers changed from: package-private */
            public Map<K, Collection<V>> map() {
                return AsMap.this;
            }

            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return Maps.asMapEntryIterator(AsMap.this.multimap.keySet(), new Function<K, Collection<V>>() {
                    public Collection<V> apply(K key) {
                        return AsMap.this.multimap.get(key);
                    }
                });
            }

            public boolean remove(Object o) {
                if (!contains(o)) {
                    return false;
                }
                AsMap.this.removeValuesForKey(((Map.Entry) o).getKey());
                return true;
            }
        }

        public Collection<V> get(Object key) {
            if (containsKey(key)) {
                return this.multimap.get(key);
            }
            return null;
        }

        public Collection<V> remove(Object key) {
            if (containsKey(key)) {
                return this.multimap.removeAll(key);
            }
            return null;
        }

        public Set<K> keySet() {
            return this.multimap.keySet();
        }

        public boolean isEmpty() {
            return this.multimap.isEmpty();
        }

        public boolean containsKey(Object key) {
            return this.multimap.containsKey(key);
        }

        public void clear() {
            this.multimap.clear();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
     arg types: [com.google.common.collect.SetMultimap, com.google.common.base.Predicate<? super K>]
     candidates:
      com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.ListMultimap, com.google.common.base.Predicate):com.google.common.collect.ListMultimap<K, V>
      com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
      com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.ListMultimap, com.google.common.base.Predicate):com.google.common.collect.ListMultimap<K, V>
     arg types: [com.google.common.collect.ListMultimap, com.google.common.base.Predicate<? super K>]
     candidates:
      com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
      com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
      com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.ListMultimap, com.google.common.base.Predicate):com.google.common.collect.ListMultimap<K, V> */
    public static <K, V> Multimap<K, V> filterKeys(Multimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        if (unfiltered instanceof SetMultimap) {
            return filterKeys((SetMultimap) unfiltered, (Predicate) keyPredicate);
        }
        if (unfiltered instanceof ListMultimap) {
            return filterKeys((ListMultimap) unfiltered, (Predicate) keyPredicate);
        }
        if (unfiltered instanceof FilteredKeyMultimap) {
            FilteredKeyMultimap<K, V> prev = (FilteredKeyMultimap) unfiltered;
            return new FilteredKeyMultimap(prev.unfiltered, Predicates.and(prev.keyPredicate, keyPredicate));
        } else if (unfiltered instanceof FilteredMultimap) {
            return filterFiltered((FilteredMultimap) unfiltered, Maps.keyPredicateOnEntries(keyPredicate));
        } else {
            return new FilteredKeyMultimap(unfiltered, keyPredicate);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.filterFiltered(com.google.common.collect.FilteredSetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
     arg types: [com.google.common.collect.FilteredSetMultimap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<K, ?>>]
     candidates:
      com.google.common.collect.Multimaps.filterFiltered(com.google.common.collect.FilteredMultimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
      com.google.common.collect.Multimaps.filterFiltered(com.google.common.collect.FilteredSetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
    public static <K, V> SetMultimap<K, V> filterKeys(SetMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        if (unfiltered instanceof FilteredKeySetMultimap) {
            FilteredKeySetMultimap<K, V> prev = (FilteredKeySetMultimap) unfiltered;
            return new FilteredKeySetMultimap(prev.unfiltered(), Predicates.and(prev.keyPredicate, keyPredicate));
        } else if (unfiltered instanceof FilteredSetMultimap) {
            return filterFiltered((FilteredSetMultimap) ((FilteredSetMultimap) unfiltered), (Predicate) Maps.keyPredicateOnEntries(keyPredicate));
        } else {
            return new FilteredKeySetMultimap(unfiltered, keyPredicate);
        }
    }

    public static <K, V> ListMultimap<K, V> filterKeys(ListMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        if (!(unfiltered instanceof FilteredKeyListMultimap)) {
            return new FilteredKeyListMultimap(unfiltered, keyPredicate);
        }
        FilteredKeyListMultimap<K, V> prev = (FilteredKeyListMultimap) unfiltered;
        return new FilteredKeyListMultimap(prev.unfiltered(), Predicates.and(prev.keyPredicate, keyPredicate));
    }

    public static <K, V> Multimap<K, V> filterValues(Multimap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
        return filterEntries(unfiltered, Maps.valuePredicateOnEntries(valuePredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.filterEntries(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
     arg types: [com.google.common.collect.SetMultimap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<?, V>>]
     candidates:
      com.google.common.collect.Multimaps.filterEntries(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
      com.google.common.collect.Multimaps.filterEntries(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
    public static <K, V> SetMultimap<K, V> filterValues(SetMultimap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
        return filterEntries((SetMultimap) unfiltered, (Predicate) Maps.valuePredicateOnEntries(valuePredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.filterEntries(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
     arg types: [com.google.common.collect.SetMultimap, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
     candidates:
      com.google.common.collect.Multimaps.filterEntries(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
      com.google.common.collect.Multimaps.filterEntries(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
    public static <K, V> Multimap<K, V> filterEntries(Multimap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        if (unfiltered instanceof SetMultimap) {
            return filterEntries((SetMultimap) unfiltered, (Predicate) entryPredicate);
        }
        if (unfiltered instanceof FilteredMultimap) {
            return filterFiltered((FilteredMultimap) unfiltered, entryPredicate);
        }
        return new FilteredEntryMultimap((Multimap) Preconditions.checkNotNull(unfiltered), entryPredicate);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Multimaps.filterFiltered(com.google.common.collect.FilteredSetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
     arg types: [com.google.common.collect.FilteredSetMultimap, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
     candidates:
      com.google.common.collect.Multimaps.filterFiltered(com.google.common.collect.FilteredMultimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
      com.google.common.collect.Multimaps.filterFiltered(com.google.common.collect.FilteredSetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
    public static <K, V> SetMultimap<K, V> filterEntries(SetMultimap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        if (unfiltered instanceof FilteredSetMultimap) {
            return filterFiltered((FilteredSetMultimap) unfiltered, (Predicate) entryPredicate);
        }
        return new FilteredEntrySetMultimap((SetMultimap) Preconditions.checkNotNull(unfiltered), entryPredicate);
    }

    private static <K, V> Multimap<K, V> filterFiltered(FilteredMultimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return new FilteredEntryMultimap(multimap.unfiltered(), Predicates.and(multimap.entryPredicate(), entryPredicate));
    }

    private static <K, V> SetMultimap<K, V> filterFiltered(FilteredSetMultimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return new FilteredEntrySetMultimap(multimap.unfiltered(), Predicates.and(multimap.entryPredicate(), entryPredicate));
    }

    static boolean equalsImpl(Multimap<?, ?> multimap, @NullableDecl Object object) {
        if (object == multimap) {
            return true;
        }
        if (object instanceof Multimap) {
            return multimap.asMap().equals(((Multimap) object).asMap());
        }
        return false;
    }
}
