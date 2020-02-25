package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@GwtIncompatible
public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final transient ConcurrentMap<E, AtomicInteger> countMap;

    @VisibleForTesting
    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> countMap2) {
        Preconditions.checkArgument(countMap2.isEmpty(), "the backing map (%s) must be empty", countMap2);
        this.countMap = countMap2;
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset<>(new ConcurrentHashMap());
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
        ConcurrentHashMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    @Beta
    public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> countMap2) {
        return new ConcurrentHashMultiset<>(countMap2);
    }

    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj) {
        return super.contains(obj);
    }

    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public int count(@NullableDecl Object element) {
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return 0;
        }
        return existingCounter.get();
    }

    public int size() {
        long sum = 0;
        for (AtomicInteger value : this.countMap.values()) {
            sum += (long) value.get();
        }
        return Ints.saturatedCast(sum);
    }

    public Object[] toArray() {
        return snapshot().toArray();
    }

    public <T> T[] toArray(T[] array) {
        return snapshot().toArray(array);
    }

    private List<E> snapshot() {
        List<E> list = Lists.newArrayListWithExpectedSize(size());
        for (Multiset.Entry<E> entry : entrySet()) {
            E element = entry.getElement();
            for (int i = entry.getCount(); i > 0; i--) {
                list.add(element);
            }
        }
        return list;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0060, code lost:
        r3 = new java.util.concurrent.atomic.AtomicInteger(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006b, code lost:
        if (r6.countMap.putIfAbsent(r7, r3) == null) goto L_0x0077;
     */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int add(E r7, int r8) {
        /*
            r6 = this;
            com.google.common.base.Preconditions.checkNotNull(r7)
            if (r8 != 0) goto L_0x000a
            int r0 = r6.count(r7)
            return r0
        L_0x000a:
            java.lang.String r0 = "occurences"
            com.google.common.collect.CollectPreconditions.checkPositive(r8, r0)
        L_0x000f:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r0 = r6.countMap
            java.lang.Object r0 = com.google.common.collect.Maps.safeGet(r0, r7)
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            r1 = 0
            if (r0 != 0) goto L_0x002b
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r2 = r6.countMap
            java.util.concurrent.atomic.AtomicInteger r3 = new java.util.concurrent.atomic.AtomicInteger
            r3.<init>(r8)
            java.lang.Object r2 = r2.putIfAbsent(r7, r3)
            r0 = r2
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            if (r0 != 0) goto L_0x002b
            return r1
        L_0x002b:
            int r2 = r0.get()
            if (r2 == 0) goto L_0x0060
            int r3 = com.google.common.math.IntMath.checkedAdd(r2, r8)     // Catch:{ ArithmeticException -> 0x003e }
            boolean r4 = r0.compareAndSet(r2, r3)     // Catch:{ ArithmeticException -> 0x003e }
            if (r4 == 0) goto L_0x003c
            return r2
        L_0x003c:
            goto L_0x002b
        L_0x003e:
            r1 = move-exception
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r4 = 65
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Overflow adding "
            r5.append(r4)
            r5.append(r8)
            java.lang.String r4 = " occurrences to a count of "
            r5.append(r4)
            r5.append(r2)
            java.lang.String r4 = r5.toString()
            r3.<init>(r4)
            throw r3
        L_0x0060:
            java.util.concurrent.atomic.AtomicInteger r3 = new java.util.concurrent.atomic.AtomicInteger
            r3.<init>(r8)
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r6.countMap
            java.lang.Object r4 = r4.putIfAbsent(r7, r3)
            if (r4 == 0) goto L_0x0077
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r6.countMap
            boolean r4 = r4.replace(r7, r0, r3)
            if (r4 == 0) goto L_0x0076
            goto L_0x0077
        L_0x0076:
            goto L_0x000f
        L_0x0077:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.add(java.lang.Object, int):int");
    }

    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object element, int occurrences) {
        int oldValue;
        int newValue;
        if (occurrences == 0) {
            return count(element);
        }
        CollectPreconditions.checkPositive(occurrences, "occurences");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return 0;
        }
        do {
            oldValue = existingCounter.get();
            if (oldValue == 0) {
                return 0;
            }
            newValue = Math.max(0, oldValue - occurrences);
        } while (!existingCounter.compareAndSet(oldValue, newValue));
        if (newValue == 0) {
            this.countMap.remove(element, existingCounter);
        }
        return oldValue;
    }

    @CanIgnoreReturnValue
    public boolean removeExactly(@NullableDecl Object element, int occurrences) {
        int oldValue;
        int newValue;
        if (occurrences == 0) {
            return true;
        }
        CollectPreconditions.checkPositive(occurrences, "occurences");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return false;
        }
        do {
            oldValue = existingCounter.get();
            if (oldValue < occurrences) {
                return false;
            }
            newValue = oldValue - occurrences;
        } while (!existingCounter.compareAndSet(oldValue, newValue));
        if (newValue == 0) {
            this.countMap.remove(element, existingCounter);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002d, code lost:
        if (r7 != 0) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0030, code lost:
        r3 = new java.util.concurrent.atomic.AtomicInteger(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003b, code lost:
        if (r5.countMap.putIfAbsent(r6, r3) == null) goto L_0x0048;
     */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setCount(E r6, int r7) {
        /*
            r5 = this;
            com.google.common.base.Preconditions.checkNotNull(r6)
            java.lang.String r0 = "count"
            com.google.common.collect.CollectPreconditions.checkNonnegative(r7, r0)
        L_0x0008:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r0 = r5.countMap
            java.lang.Object r0 = com.google.common.collect.Maps.safeGet(r0, r6)
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            r1 = 0
            if (r0 != 0) goto L_0x0027
            if (r7 != 0) goto L_0x0016
            return r1
        L_0x0016:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r2 = r5.countMap
            java.util.concurrent.atomic.AtomicInteger r3 = new java.util.concurrent.atomic.AtomicInteger
            r3.<init>(r7)
            java.lang.Object r2 = r2.putIfAbsent(r6, r3)
            r0 = r2
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            if (r0 != 0) goto L_0x0027
            return r1
        L_0x0027:
            int r2 = r0.get()
            if (r2 != 0) goto L_0x0049
            if (r7 != 0) goto L_0x0030
            return r1
        L_0x0030:
            java.util.concurrent.atomic.AtomicInteger r3 = new java.util.concurrent.atomic.AtomicInteger
            r3.<init>(r7)
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r5.countMap
            java.lang.Object r4 = r4.putIfAbsent(r6, r3)
            if (r4 == 0) goto L_0x0048
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r5.countMap
            boolean r4 = r4.replace(r6, r0, r3)
            if (r4 == 0) goto L_0x0046
            goto L_0x0048
        L_0x0046:
            goto L_0x0008
        L_0x0048:
            return r1
        L_0x0049:
            boolean r3 = r0.compareAndSet(r2, r7)
            if (r3 == 0) goto L_0x0057
            if (r7 != 0) goto L_0x0056
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r1 = r5.countMap
            r1.remove(r6, r0)
        L_0x0056:
            return r2
        L_0x0057:
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.setCount(java.lang.Object, int):int");
    }

    @CanIgnoreReturnValue
    public boolean setCount(E element, int expectedOldCount, int newCount) {
        Preconditions.checkNotNull(element);
        CollectPreconditions.checkNonnegative(expectedOldCount, "oldCount");
        CollectPreconditions.checkNonnegative(newCount, "newCount");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter != null) {
            int oldValue = existingCounter.get();
            if (oldValue == expectedOldCount) {
                if (oldValue == 0) {
                    if (newCount == 0) {
                        this.countMap.remove(element, existingCounter);
                        return true;
                    }
                    AtomicInteger newCounter = new AtomicInteger(newCount);
                    if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter)) {
                        return true;
                    }
                    return false;
                } else if (existingCounter.compareAndSet(oldValue, newCount)) {
                    if (newCount == 0) {
                        this.countMap.remove(element, existingCounter);
                    }
                    return true;
                }
            }
            return false;
        } else if (expectedOldCount != 0) {
            return false;
        } else {
            if (newCount == 0 || this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public Set<E> createElementSet() {
        final Set<E> delegate = this.countMap.keySet();
        return new ForwardingSet<E>(this) {
            /* access modifiers changed from: protected */
            public Set<E> delegate() {
                return delegate;
            }

            public boolean contains(@NullableDecl Object object) {
                return object != null && Collections2.safeContains(delegate, object);
            }

            public boolean containsAll(Collection<?> collection) {
                return standardContainsAll(collection);
            }

            public boolean remove(Object object) {
                return object != null && Collections2.safeRemove(delegate, object);
            }

            public boolean removeAll(Collection<?> c) {
                return standardRemoveAll(c);
            }
        };
    }

    /* access modifiers changed from: package-private */
    public Iterator<E> elementIterator() {
        throw new AssertionError("should never be called");
    }

    @Deprecated
    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    /* access modifiers changed from: package-private */
    public int distinctElements() {
        return this.countMap.size();
    }

    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Multiset.Entry<E>> entryIterator() {
        final Iterator<Multiset.Entry<E>> readOnlyIterator = new AbstractIterator<Multiset.Entry<E>>() {
            private final Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();

            /* access modifiers changed from: protected */
            public Multiset.Entry<E> computeNext() {
                while (this.mapEntries.hasNext()) {
                    Map.Entry<E, AtomicInteger> mapEntry = this.mapEntries.next();
                    int count = mapEntry.getValue().get();
                    if (count != 0) {
                        return Multisets.immutableEntry(mapEntry.getKey(), count);
                    }
                }
                return (Multiset.Entry) endOfData();
            }
        };
        return new ForwardingIterator<Multiset.Entry<E>>() {
            @NullableDecl
            private Multiset.Entry<E> last;

            /* access modifiers changed from: protected */
            public Iterator<Multiset.Entry<E>> delegate() {
                return readOnlyIterator;
            }

            public Multiset.Entry<E> next() {
                this.last = (Multiset.Entry) super.next();
                return this.last;
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.last != null);
                ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
                this.last = null;
            }
        };
    }

    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    public void clear() {
        this.countMap.clear();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.countMap);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, (ConcurrentMap) stream.readObject());
    }

    private static class FieldSettersHolder {
        static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }

    private class EntrySet extends AbstractMultiset<E>.EntrySet {
        private EntrySet() {
            super();
        }

        /* access modifiers changed from: package-private */
        public ConcurrentHashMultiset<E> multiset() {
            return ConcurrentHashMultiset.this;
        }

        public Object[] toArray() {
            return snapshot().toArray();
        }

        public <T> T[] toArray(T[] array) {
            return snapshot().toArray(array);
        }

        private List<Multiset.Entry<E>> snapshot() {
            List<Multiset.Entry<E>> list = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(list, iterator());
            return list;
        }
    }
}
