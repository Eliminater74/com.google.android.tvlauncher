package com.google.common.collect;

import com.google.android.libraries.performance.primes.PrimesBatteryConfigurations;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public abstract class FluentIterable<E> implements Iterable<E> {
    private final Optional<Iterable<E>> iterableDelegate;

    protected FluentIterable() {
        this.iterableDelegate = Optional.absent();
    }

    FluentIterable(Iterable<E> iterable) {
        Preconditions.checkNotNull(iterable);
        this.iterableDelegate = Optional.fromNullable(this != iterable ? iterable : null);
    }

    private Iterable<E> getDelegate() {
        return this.iterableDelegate.mo22987or((PrimesBatteryConfigurations) this);
    }

    public static <E> FluentIterable<E> from(final Iterable<E> iterable) {
        if (iterable instanceof FluentIterable) {
            return (FluentIterable) iterable;
        }
        return new FluentIterable<E>(iterable) {
            public Iterator<E> iterator() {
                return iterable.iterator();
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> from(E[] elements) {
        return from(Arrays.asList(elements));
    }

    @Deprecated
    public static <E> FluentIterable<E> from(FluentIterable<E> iterable) {
        return (FluentIterable) Preconditions.checkNotNull(iterable);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b) {
        return concatNoDefensiveCopy(a, b);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c) {
        return concatNoDefensiveCopy(a, b, c);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c, Iterable<? extends T> d) {
        return concatNoDefensiveCopy(a, b, c, d);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable... iterableArr) {
        return concatNoDefensiveCopy((Iterable[]) Arrays.copyOf(iterableArr, iterableArr.length));
    }

    @Beta
    public static <T> FluentIterable<T> concat(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.concat(Iterators.transform(iterable.iterator(), Iterables.toIterator()));
            }
        };
    }

    private static <T> FluentIterable<T> concatNoDefensiveCopy(final Iterable<? extends T>... inputs) {
        for (Iterable<? extends T> input : inputs) {
            Preconditions.checkNotNull(input);
        }
        return new FluentIterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.concat(new AbstractIndexedListIterator<Iterator<? extends T>>(inputs.length) {
                    public Iterator<? extends T> get(int i) {
                        return inputs[i].iterator();
                    }
                });
            }
        };
    }

    @Beta
    /* renamed from: of */
    public static <E> FluentIterable<E> m97of() {
        return from(ImmutableList.m107of());
    }

    @Beta
    /* renamed from: of */
    public static <E> FluentIterable<E> m98of(@NullableDecl E element, E... elements) {
        return from(Lists.asList(element, elements));
    }

    public String toString() {
        return Iterables.toString(getDelegate());
    }

    public final int size() {
        return Iterables.size(getDelegate());
    }

    public final boolean contains(@NullableDecl Object target) {
        return Iterables.contains(getDelegate(), target);
    }

    public final FluentIterable<E> cycle() {
        return from(Iterables.cycle(getDelegate()));
    }

    @Beta
    public final FluentIterable<E> append(Iterable<? extends E> other) {
        return concat(getDelegate(), other);
    }

    @Beta
    public final FluentIterable<E> append(E... elements) {
        return concat(getDelegate(), Arrays.asList(elements));
    }

    public final FluentIterable<E> filter(Predicate<? super E> predicate) {
        return from(Iterables.filter(getDelegate(), predicate));
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Class, java.lang.Class<T>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @com.google.common.annotations.GwtIncompatible
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> com.google.common.collect.FluentIterable<T> filter(java.lang.Class<T> r2) {
        /*
            r1 = this;
            java.lang.Iterable r0 = r1.getDelegate()
            java.lang.Iterable r0 = com.google.common.collect.Iterables.filter(r0, r2)
            com.google.common.collect.FluentIterable r0 = from(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.FluentIterable.filter(java.lang.Class):com.google.common.collect.FluentIterable");
    }

    public final boolean anyMatch(Predicate<? super E> predicate) {
        return Iterables.any(getDelegate(), predicate);
    }

    public final boolean allMatch(Predicate<? super E> predicate) {
        return Iterables.all(getDelegate(), predicate);
    }

    public final Optional<E> firstMatch(Predicate<? super E> predicate) {
        return Iterables.tryFind(getDelegate(), predicate);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.common.base.Function<? super E, T>, com.google.common.base.Function] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> com.google.common.collect.FluentIterable<T> transform(com.google.common.base.Function<? super E, T> r2) {
        /*
            r1 = this;
            java.lang.Iterable r0 = r1.getDelegate()
            java.lang.Iterable r0 = com.google.common.collect.Iterables.transform(r0, r2)
            com.google.common.collect.FluentIterable r0 = from(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.FluentIterable.transform(com.google.common.base.Function):com.google.common.collect.FluentIterable");
    }

    public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> function) {
        return concat(transform(function));
    }

    public final Optional<E> first() {
        Iterator<E> iterator = getDelegate().iterator();
        return iterator.hasNext() ? Optional.m80of(iterator.next()) : Optional.absent();
    }

    /* JADX INFO: Multiple debug info for r1v1 java.util.Iterator<E>: [D('iterator' java.util.Iterator<E>), D('list' java.util.List<E>)] */
    /* JADX INFO: Multiple debug info for r2v2 E: [D('sortedSet' java.util.SortedSet<E>), D('current' E)] */
    public final Optional<E> last() {
        E current;
        Iterable<E> iterable = getDelegate();
        if (iterable instanceof List) {
            List<E> list = (List) iterable;
            if (list.isEmpty()) {
                return Optional.absent();
            }
            return Optional.m80of(list.get(list.size() - 1));
        }
        Iterator<E> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return Optional.absent();
        }
        if (iterable instanceof SortedSet) {
            return Optional.m80of(((SortedSet) iterable).last());
        }
        do {
            current = iterator.next();
        } while (iterator.hasNext());
        return Optional.m80of(current);
    }

    public final FluentIterable<E> skip(int numberToSkip) {
        return from(Iterables.skip(getDelegate(), numberToSkip));
    }

    public final FluentIterable<E> limit(int maxSize) {
        return from(Iterables.limit(getDelegate(), maxSize));
    }

    public final boolean isEmpty() {
        return !getDelegate().iterator().hasNext();
    }

    public final ImmutableList<E> toList() {
        return ImmutableList.copyOf(getDelegate());
    }

    public final ImmutableList<E> toSortedList(Comparator<? super E> comparator) {
        return Ordering.from(comparator).immutableSortedCopy(getDelegate());
    }

    public final ImmutableSet<E> toSet() {
        return ImmutableSet.copyOf(getDelegate());
    }

    public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return ImmutableSortedSet.copyOf(comparator, getDelegate());
    }

    public final ImmutableMultiset<E> toMultiset() {
        return ImmutableMultiset.copyOf(getDelegate());
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.common.base.Function, com.google.common.base.Function<? super E, V>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <V> com.google.common.collect.ImmutableMap<E, V> toMap(com.google.common.base.Function<? super E, V> r2) {
        /*
            r1 = this;
            java.lang.Iterable r0 = r1.getDelegate()
            com.google.common.collect.ImmutableMap r0 = com.google.common.collect.Maps.toMap(r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.FluentIterable.toMap(com.google.common.base.Function):com.google.common.collect.ImmutableMap");
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.common.base.Function, com.google.common.base.Function<? super E, K>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <K> com.google.common.collect.ImmutableListMultimap<K, E> index(com.google.common.base.Function<? super E, K> r2) {
        /*
            r1 = this;
            java.lang.Iterable r0 = r1.getDelegate()
            com.google.common.collect.ImmutableListMultimap r0 = com.google.common.collect.Multimaps.index(r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.FluentIterable.index(com.google.common.base.Function):com.google.common.collect.ImmutableListMultimap");
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.common.base.Function, com.google.common.base.Function<? super E, K>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <K> com.google.common.collect.ImmutableMap<K, E> uniqueIndex(com.google.common.base.Function<? super E, K> r2) {
        /*
            r1 = this;
            java.lang.Iterable r0 = r1.getDelegate()
            com.google.common.collect.ImmutableMap r0 = com.google.common.collect.Maps.uniqueIndex(r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.FluentIterable.uniqueIndex(com.google.common.base.Function):com.google.common.collect.ImmutableMap");
    }

    @GwtIncompatible
    public final E[] toArray(Class<E> type) {
        return Iterables.toArray(getDelegate(), type);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: C
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
    public final <C extends java.util.Collection<? super E>> C copyInto(C r4) {
        /*
            r3 = this;
            com.google.common.base.Preconditions.checkNotNull(r4)
            java.lang.Iterable r0 = r3.getDelegate()
            boolean r1 = r0 instanceof java.util.Collection
            if (r1 == 0) goto L_0x0013
            java.util.Collection r1 = com.google.common.collect.Collections2.cast(r0)
            r4.addAll(r1)
            goto L_0x0025
        L_0x0013:
            java.util.Iterator r1 = r0.iterator()
        L_0x0017:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0025
            java.lang.Object r2 = r1.next()
            r4.add(r2)
            goto L_0x0017
        L_0x0025:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.FluentIterable.copyInto(java.util.Collection):java.util.Collection");
    }

    @Beta
    public final String join(Joiner joiner) {
        return joiner.join(this);
    }

    public final E get(int position) {
        return Iterables.get(getDelegate(), position);
    }

    private static class FromIterableFunction<E> implements Function<Iterable<E>, FluentIterable<E>> {
        private FromIterableFunction() {
        }

        public FluentIterable<E> apply(Iterable<E> fromObject) {
            return FluentIterable.from(fromObject);
        }
    }
}
