package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@GwtCompatible
public abstract class Ordering<T> implements Comparator<T> {
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;

    protected Ordering() {
    }

    @GwtCompatible(serializable = true)
    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> from(Comparator comparator) {
        if (comparator instanceof Ordering) {
            return (Ordering) comparator;
        }
        return new ComparatorOrdering(comparator);
    }

    @GwtCompatible(serializable = true)
    @Deprecated
    public static <T> Ordering<T> from(Ordering ordering) {
        return (Ordering) Preconditions.checkNotNull(ordering);
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(List<T> valuesInOrder) {
        return new ExplicitOrdering(valuesInOrder);
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(T leastValue, T... remainingValuesInOrder) {
        return explicit(Lists.asList(leastValue, remainingValuesInOrder));
    }

    @GwtCompatible(serializable = true)
    public static Ordering<Object> allEqual() {
        return AllEqualOrdering.INSTANCE;
    }

    @GwtCompatible(serializable = true)
    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }

    public static Ordering<Object> arbitrary() {
        return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
    }

    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> comparators) {
        return new CompoundOrdering(comparators);
    }

    @CanIgnoreReturnValue
    public abstract int compare(@NullableDecl Object obj, @NullableDecl Object obj2);

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering(this);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsFirst() {
        return new NullsFirstOrdering(this);
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsLast() {
        return new NullsLastOrdering(this);
    }

    @GwtCompatible(serializable = true)
    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return new ByFunctionOrdering(function, this);
    }

    /* access modifiers changed from: package-private */
    public <T2 extends T> Ordering<Map.Entry<T2, ?>> onKeys() {
        return onResultOf(Maps.keyFunction());
    }

    @GwtCompatible(serializable = true)
    public <U extends T> Ordering<U> compound(Comparator<? super U> secondaryComparator) {
        return new CompoundOrdering(this, (Comparator) Preconditions.checkNotNull(secondaryComparator));
    }

    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return new LexicographicalOrdering(this);
    }

    public <E extends T> E min(Iterator<E> iterator) {
        E minSoFar = iterator.next();
        while (iterator.hasNext()) {
            minSoFar = min(minSoFar, iterator.next());
        }
        return minSoFar;
    }

    public <E extends T> E min(Iterable<E> iterable) {
        return min(iterable.iterator());
    }

    public <E extends T> E min(@NullableDecl E a, @NullableDecl E b) {
        return compare(a, b) <= 0 ? a : b;
    }

    public <E extends T> E min(@NullableDecl E a, @NullableDecl E b, @NullableDecl E c, E... rest) {
        E minSoFar = min(min(a, b), c);
        for (E r : rest) {
            minSoFar = min(minSoFar, r);
        }
        return minSoFar;
    }

    public <E extends T> E max(Iterator<E> iterator) {
        E maxSoFar = iterator.next();
        while (iterator.hasNext()) {
            maxSoFar = max(maxSoFar, iterator.next());
        }
        return maxSoFar;
    }

    public <E extends T> E max(Iterable<E> iterable) {
        return max(iterable.iterator());
    }

    public <E extends T> E max(@NullableDecl E a, @NullableDecl E b) {
        return compare(a, b) >= 0 ? a : b;
    }

    public <E extends T> E max(@NullableDecl E a, @NullableDecl E b, @NullableDecl E c, E... rest) {
        E maxSoFar = max(max(a, b), c);
        for (E r : rest) {
            maxSoFar = max(maxSoFar, r);
        }
        return maxSoFar;
    }

    public <E extends T> List<E> leastOf(Iterable<E> iterable, int k) {
        if (iterable instanceof Collection) {
            Collection<E> collection = (Collection) iterable;
            if (((long) collection.size()) <= ((long) k) * 2) {
                E[] array = collection.toArray();
                Arrays.sort(array, this);
                if (array.length > k) {
                    array = Arrays.copyOf(array, k);
                }
                return Collections.unmodifiableList(Arrays.asList(array));
            }
        }
        return leastOf(iterable.iterator(), k);
    }

    /* JADX INFO: Multiple debug info for r0v4 com.google.common.collect.TopKSelector: [D('list' java.util.ArrayList<E>), D('selector' com.google.common.collect.TopKSelector<E>)] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.Iterator<E>, java.util.Iterator, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <E extends T> java.util.List<E> leastOf(java.util.Iterator<E> r3, int r4) {
        /*
            r2 = this;
            com.google.common.base.Preconditions.checkNotNull(r3)
            java.lang.String r0 = "k"
            com.google.common.collect.CollectPreconditions.checkNonnegative(r4, r0)
            if (r4 == 0) goto L_0x0042
            boolean r0 = r3.hasNext()
            if (r0 != 0) goto L_0x0011
            goto L_0x0042
        L_0x0011:
            r0 = 1073741823(0x3fffffff, float:1.9999999)
            if (r4 < r0) goto L_0x0036
            java.util.ArrayList r0 = com.google.common.collect.Lists.newArrayList(r3)
            java.util.Collections.sort(r0, r2)
            int r1 = r0.size()
            if (r1 <= r4) goto L_0x002e
            int r1 = r0.size()
            java.util.List r1 = r0.subList(r4, r1)
            r1.clear()
        L_0x002e:
            r0.trimToSize()
            java.util.List r1 = java.util.Collections.unmodifiableList(r0)
            return r1
        L_0x0036:
            com.google.common.collect.TopKSelector r0 = com.google.common.collect.TopKSelector.least(r4, r2)
            r0.offerAll(r3)
            java.util.List r1 = r0.topK()
            return r1
        L_0x0042:
            java.util.List r0 = java.util.Collections.emptyList()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Ordering.leastOf(java.util.Iterator, int):java.util.List");
    }

    public <E extends T> List<E> greatestOf(Iterable<E> iterable, int k) {
        return reverse().leastOf(iterable, k);
    }

    public <E extends T> List<E> greatestOf(Iterator<E> iterator, int k) {
        return reverse().leastOf(iterator, k);
    }

    public <E extends T> List<E> sortedCopy(Iterable<E> elements) {
        E[] array = Iterables.toArray(elements);
        Arrays.sort(array, this);
        return Lists.newArrayList(Arrays.asList(array));
    }

    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> elements) {
        return ImmutableList.sortedCopyOf(this, elements);
    }

    public boolean isOrdered(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        T prev = it.next();
        while (it.hasNext()) {
            T next = it.next();
            if (compare(prev, next) > 0) {
                return false;
            }
            prev = next;
        }
        return true;
    }

    public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        if (!it.hasNext()) {
            return true;
        }
        T prev = it.next();
        while (it.hasNext()) {
            T next = it.next();
            if (compare(prev, next) >= 0) {
                return false;
            }
            prev = next;
        }
        return true;
    }

    @Deprecated
    public int binarySearch(List<? extends T> sortedList, @NullableDecl T key) {
        return Collections.binarySearch(sortedList, key, this);
    }

    private static class ArbitraryOrderingHolder {
        static final Ordering<Object> ARBITRARY_ORDERING = new ArbitraryOrdering();

        private ArbitraryOrderingHolder() {
        }
    }

    @VisibleForTesting
    static class ArbitraryOrdering extends Ordering<Object> {
        private final AtomicInteger counter = new AtomicInteger(0);
        private final ConcurrentMap<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeMap();

        ArbitraryOrdering() {
        }

        private Integer getUid(Object obj) {
            Integer uid = this.uids.get(obj);
            if (uid != null) {
                return uid;
            }
            Integer uid2 = Integer.valueOf(this.counter.getAndIncrement());
            Integer alreadySet = this.uids.putIfAbsent(obj, uid2);
            if (alreadySet != null) {
                return alreadySet;
            }
            return uid2;
        }

        public int compare(Object left, Object right) {
            if (left == right) {
                return 0;
            }
            if (left == null) {
                return -1;
            }
            if (right == null) {
                return 1;
            }
            int leftCode = identityHashCode(left);
            int rightCode = identityHashCode(right);
            if (leftCode == rightCode) {
                int result = getUid(left).compareTo(getUid(right));
                if (result != 0) {
                    return result;
                }
                throw new AssertionError();
            } else if (leftCode < rightCode) {
                return -1;
            } else {
                return 1;
            }
        }

        public String toString() {
            return "Ordering.arbitrary()";
        }

        /* access modifiers changed from: package-private */
        public int identityHashCode(Object object) {
            return System.identityHashCode(object);
        }
    }

    @VisibleForTesting
    static class IncomparableValueException extends ClassCastException {
        private static final long serialVersionUID = 0;
        final Object value;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        IncomparableValueException(java.lang.Object r4) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 22
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Cannot compare value: "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                r3.<init>(r0)
                r3.value = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Ordering.IncomparableValueException.<init>(java.lang.Object):void");
        }
    }
}
