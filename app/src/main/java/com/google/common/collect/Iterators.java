package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

@GwtCompatible(emulated = true)
public final class Iterators {
    private Iterators() {
    }

    static <T> UnmodifiableIterator<T> emptyIterator() {
        return emptyListIterator();
    }

    static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return ArrayItr.EMPTY;
    }

    static <T> Iterator<T> emptyModifiableIterator() {
        return EmptyModifiableIterator.INSTANCE;
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator it) {
        Preconditions.checkNotNull(it);
        if (it instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator) it;
        }
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public T next() {
                return it.next();
            }
        };
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator unmodifiableIterator) {
        return (UnmodifiableIterator) Preconditions.checkNotNull(unmodifiableIterator);
    }

    public static int size(Iterator<?> iterator) {
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return Ints.saturatedCast(count);
    }

    public static boolean contains(Iterator<?> iterator, @NullableDecl Object element) {
        if (element == null) {
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    return true;
                }
            }
            return false;
        }
        while (iterator.hasNext()) {
            if (element.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    @CanIgnoreReturnValue
    public static boolean removeAll(Iterator<?> removeFrom, Collection<?> elementsToRemove) {
        Preconditions.checkNotNull(elementsToRemove);
        boolean result = false;
        while (removeFrom.hasNext()) {
            if (elementsToRemove.contains(removeFrom.next())) {
                removeFrom.remove();
                result = true;
            }
        }
        return result;
    }

    @CanIgnoreReturnValue
    public static <T> boolean removeIf(Iterator<T> removeFrom, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (predicate.apply(removeFrom.next())) {
                removeFrom.remove();
                modified = true;
            }
        }
        return modified;
    }

    @CanIgnoreReturnValue
    public static boolean retainAll(Iterator<?> removeFrom, Collection<?> elementsToRetain) {
        Preconditions.checkNotNull(elementsToRetain);
        boolean result = false;
        while (removeFrom.hasNext()) {
            if (!elementsToRetain.contains(removeFrom.next())) {
                removeFrom.remove();
                result = true;
            }
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0006  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean elementsEqual(java.util.Iterator<?> r4, java.util.Iterator<?> r5) {
        /*
        L_0x0000:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x001e
            boolean r0 = r5.hasNext()
            r1 = 0
            if (r0 != 0) goto L_0x000e
            return r1
        L_0x000e:
            java.lang.Object r0 = r4.next()
            java.lang.Object r2 = r5.next()
            boolean r3 = com.google.common.base.Objects.equal(r0, r2)
            if (r3 != 0) goto L_0x001d
            return r1
        L_0x001d:
            goto L_0x0000
        L_0x001e:
            boolean r0 = r5.hasNext()
            r0 = r0 ^ 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.elementsEqual(java.util.Iterator, java.util.Iterator):boolean");
    }

    public static String toString(Iterator<?> iterator) {
        StringBuilder sb = new StringBuilder().append('[');
        boolean first = true;
        while (iterator.hasNext()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(iterator.next());
        }
        sb.append(']');
        return sb.toString();
    }

    public static <T> T getOnlyElement(Iterator<T> iterator) {
        T first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <");
        StringBuilder sb2 = sb.append((Object) first);
        for (int i = 0; i < 4 && iterator.hasNext(); i++) {
            sb2.append(", ");
            sb2.append((Object) iterator.next());
        }
        if (iterator.hasNext() != 0) {
            sb2.append(", ...");
        }
        sb2.append('>');
        throw new IllegalArgumentException(sb2.toString());
    }

    @NullableDecl
    public static <T> T getOnlyElement(Iterator<? extends T> iterator, @NullableDecl T defaultValue) {
        return iterator.hasNext() ? getOnlyElement(iterator) : defaultValue;
    }

    @GwtIncompatible
    public static <T> T[] toArray(Iterator<? extends T> iterator, Class<T> type) {
        return Iterables.toArray(Lists.newArrayList(iterator), type);
    }

    @CanIgnoreReturnValue
    public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        Preconditions.checkNotNull(addTo);
        Preconditions.checkNotNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    public static int frequency(Iterator<?> iterator, @NullableDecl Object element) {
        int count = 0;
        while (contains(iterator, element)) {
            count++;
        }
        return count;
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>() {
            Iterator<T> iterator = Iterators.emptyModifiableIterator();

            public boolean hasNext() {
                return this.iterator.hasNext() || iterable.iterator().hasNext();
            }

            public T next() {
                if (!this.iterator.hasNext()) {
                    this.iterator = iterable.iterator();
                    if (!this.iterator.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return this.iterator.next();
            }

            public void remove() {
                this.iterator.remove();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterator<T> cycle(T... elements) {
        return cycle(Lists.newArrayList(elements));
    }

    private static <T> Iterator<T> consumingForArray(final T... elements) {
        return new UnmodifiableIterator<T>() {
            int index = 0;

            public boolean hasNext() {
                return this.index < elements.length;
            }

            public T next() {
                if (hasNext()) {
                    T[] tArr = elements;
                    int i = this.index;
                    T result = tArr[i];
                    tArr[i] = null;
                    this.index = i + 1;
                    return result;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        return concat(consumingForArray(a, b));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        return concat(consumingForArray(a, b, c));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c, Iterator<? extends T> d) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(d);
        return concat(consumingForArray(a, b, c, d));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T>... inputs) {
        return concatNoDefensiveCopy((Iterator[]) Arrays.copyOf(inputs, inputs.length));
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> inputs) {
        return new ConcatenatedIterator(inputs);
    }

    static <T> Iterator<T> concatNoDefensiveCopy(Iterator<? extends T>... inputs) {
        for (Iterator<? extends T> input : (Iterator[]) Preconditions.checkNotNull(inputs)) {
            Preconditions.checkNotNull(input);
        }
        return concat(consumingForArray(inputs));
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator, int size) {
        return partitionImpl(iterator, size, false);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator, int size) {
        return partitionImpl(iterator, size, true);
    }

    private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator, final int size, final boolean pad) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(size > 0);
        return new UnmodifiableIterator<List<T>>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public List<T> next() {
                if (hasNext()) {
                    Object[] array = new Object[size];
                    int count = 0;
                    while (count < size && iterator.hasNext()) {
                        array[count] = iterator.next();
                        count++;
                    }
                    for (int i = count; i < size; i++) {
                        array[i] = null;
                    }
                    List<T> list = Collections.unmodifiableList(Arrays.asList(array));
                    return (pad || count == size) ? list : list.subList(0, count);
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator it, final Predicate predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>() {
            /* access modifiers changed from: protected */
            public T computeNext() {
                while (it.hasNext()) {
                    T element = it.next();
                    if (predicate.apply(element)) {
                        return element;
                    }
                }
                return endOfData();
            }
        };
    }

    @GwtIncompatible
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> unfiltered, Class cls) {
        return filter(unfiltered, Predicates.instanceOf(cls));
    }

    public static <T> boolean any(Iterator<T> iterator, Predicate<? super T> predicate) {
        return indexOf(iterator, predicate) != -1;
    }

    public static <T> boolean all(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            if (!predicate.apply(iterator.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (predicate.apply(t)) {
                return t;
            }
        }
        throw new NoSuchElementException();
    }

    @NullableDecl
    public static <T> T find(Iterator<? extends T> iterator, Predicate<? super T> predicate, @NullableDecl T defaultValue) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (predicate.apply(t)) {
                return t;
            }
        }
        return defaultValue;
    }

    public static <T> Optional<T> tryFind(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (predicate.apply(t)) {
                return Optional.m80of(t);
            }
        }
        return Optional.absent();
    }

    public static <T> int indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i = 0;
        while (iterator.hasNext()) {
            if (predicate.apply(iterator.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <F, T> Iterator<T> transform(Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator<F, T>(fromIterator) {
            /* access modifiers changed from: package-private */
            public T transform(F from) {
                return function.apply(from);
            }
        };
    }

    public static <T> T get(Iterator<T> iterator, int position) {
        checkNonnegative(position);
        int skipped = advance(iterator, position);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        StringBuilder sb = new StringBuilder(91);
        sb.append("position (");
        sb.append(position);
        sb.append(") must be less than the number of elements that remained (");
        sb.append(skipped);
        sb.append(")");
        throw new IndexOutOfBoundsException(sb.toString());
    }

    @NullableDecl
    public static <T> T get(Iterator<? extends T> iterator, int position, @NullableDecl T defaultValue) {
        checkNonnegative(position);
        advance(iterator, position);
        return getNext(iterator, defaultValue);
    }

    static void checkNonnegative(int position) {
        if (position < 0) {
            StringBuilder sb = new StringBuilder(43);
            sb.append("position (");
            sb.append(position);
            sb.append(") must not be negative");
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    @NullableDecl
    public static <T> T getNext(Iterator<? extends T> iterator, @NullableDecl T defaultValue) {
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    public static <T> T getLast(Iterator<T> iterator) {
        T current;
        do {
            current = iterator.next();
        } while (iterator.hasNext());
        return current;
    }

    @NullableDecl
    public static <T> T getLast(Iterator<? extends T> iterator, @NullableDecl T defaultValue) {
        return iterator.hasNext() ? getLast(iterator) : defaultValue;
    }

    @CanIgnoreReturnValue
    public static int advance(Iterator<?> iterator, int numberToAdvance) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(numberToAdvance >= 0, "numberToAdvance must be nonnegative");
        int i = 0;
        while (i < numberToAdvance && iterator.hasNext()) {
            iterator.next();
            i++;
        }
        return i;
    }

    public static <T> Iterator<T> limit(final Iterator<T> iterator, final int limitSize) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(limitSize >= 0, "limit is negative");
        return new Iterator<T>() {
            private int count;

            public boolean hasNext() {
                return this.count < limitSize && iterator.hasNext();
            }

            public T next() {
                if (hasNext()) {
                    this.count++;
                    return iterator.next();
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                iterator.remove();
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public T next() {
                T next = iterator.next();
                iterator.remove();
                return next;
            }

            public String toString() {
                return "Iterators.consumingIterator(...)";
            }
        };
    }

    @NullableDecl
    static <T> T pollNext(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return null;
        }
        T result = iterator.next();
        iterator.remove();
        return result;
    }

    static void clear(Iterator<?> iterator) {
        Preconditions.checkNotNull(iterator);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T... array) {
        return forArray(array, 0, array.length, 0);
    }

    static <T> UnmodifiableListIterator<T> forArray(T[] array, int offset, int length, int index) {
        Preconditions.checkArgument(length >= 0);
        Preconditions.checkPositionIndexes(offset, offset + length, array.length);
        Preconditions.checkPositionIndex(index, length);
        if (length == 0) {
            return emptyListIterator();
        }
        return new ArrayItr(array, offset, length, index);
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@NullableDecl final T value) {
        return new UnmodifiableIterator<T>() {
            boolean done;

            public boolean hasNext() {
                return !this.done;
            }

            public T next() {
                if (!this.done) {
                    this.done = true;
                    return value;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            public T next() {
                return enumeration.nextElement();
            }
        };
    }

    public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new Enumeration<T>() {
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            public T nextElement() {
                return iterator.next();
            }
        };
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator) {
        if (iterator instanceof PeekingImpl) {
            return (PeekingImpl) iterator;
        }
        return new PeekingImpl(iterator);
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> iterator) {
        return (PeekingIterator) Preconditions.checkNotNull(iterator);
    }

    @Beta
    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterators, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterators, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator(iterators, comparator);
    }

    static <T> ListIterator<T> cast(Iterator<T> iterator) {
        return (ListIterator) iterator;
    }

    private enum EmptyModifiableIterator implements Iterator<Object> {
        INSTANCE;

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.checkRemove(false);
        }
    }

    private static final class ArrayItr<T> extends AbstractIndexedListIterator<T> {
        static final UnmodifiableListIterator<Object> EMPTY = new ArrayItr(new Object[0], 0, 0, 0);
        private final T[] array;
        private final int offset;

        ArrayItr(T[] array2, int offset2, int length, int index) {
            super(length, index);
            this.array = array2;
            this.offset = offset2;
        }

        /* access modifiers changed from: protected */
        public T get(int index) {
            return this.array[this.offset + index];
        }
    }

    private static class PeekingImpl<E> implements PeekingIterator<E> {
        private final Iterator<? extends E> iterator;
        private boolean hasPeeked;
        @NullableDecl
        private E peekedElement;

        public PeekingImpl(Iterator<? extends E> iterator2) {
            this.iterator = (Iterator) Preconditions.checkNotNull(iterator2);
        }

        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }

        public E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E result = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return result;
        }

        public void remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }

        public E peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }
    }

    private static class MergingIterator<T> extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> queue;

        public MergingIterator(Iterable<? extends Iterator<? extends T>> iterators, final Comparator<? super T> itemComparator) {
            this.queue = new PriorityQueue(2, new Comparator<PeekingIterator<T>>(this) {
                public int compare(PeekingIterator<T> o1, PeekingIterator<T> o2) {
                    return itemComparator.compare(o1.peek(), o2.peek());
                }
            });
            for (Iterator<? extends T> iterator : iterators) {
                if (iterator.hasNext()) {
                    this.queue.add(Iterators.peekingIterator(iterator));
                }
            }
        }

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public T next() {
            PeekingIterator<T> nextIter = this.queue.remove();
            T next = nextIter.next();
            if (nextIter.hasNext()) {
                this.queue.add(nextIter);
            }
            return next;
        }
    }

    private static class ConcatenatedIterator<T> implements Iterator<T> {
        private Iterator<? extends T> iterator = Iterators.emptyIterator();
        @NullableDecl
        private Deque<Iterator<? extends Iterator<? extends T>>> metaIterators;
        @NullableDecl
        private Iterator<? extends T> toRemove;
        private Iterator<? extends Iterator<? extends T>> topMetaIterator;

        ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> metaIterator) {
            this.topMetaIterator = (Iterator) Preconditions.checkNotNull(metaIterator);
        }

        @NullableDecl
        private Iterator<? extends Iterator<? extends T>> getTopMetaIterator() {
            while (true) {
                Iterator<? extends Iterator<? extends T>> it = this.topMetaIterator;
                if (it != null && it.hasNext()) {
                    return this.topMetaIterator;
                }
                Deque<Iterator<? extends Iterator<? extends T>>> deque = this.metaIterators;
                if (deque == null || deque.isEmpty()) {
                    return null;
                }
                this.topMetaIterator = this.metaIterators.removeFirst();
            }
        }

        public boolean hasNext() {
            while (!((Iterator) Preconditions.checkNotNull(this.iterator)).hasNext()) {
                this.topMetaIterator = getTopMetaIterator();
                Iterator<? extends Iterator<? extends T>> it = this.topMetaIterator;
                if (it == null) {
                    return false;
                }
                this.iterator = (Iterator) it.next();
                Iterator<? extends T> it2 = this.iterator;
                if (it2 instanceof ConcatenatedIterator) {
                    ConcatenatedIterator<T> topConcat = (ConcatenatedIterator) it2;
                    this.iterator = topConcat.iterator;
                    if (this.metaIterators == null) {
                        this.metaIterators = new ArrayDeque();
                    }
                    this.metaIterators.addFirst(this.topMetaIterator);
                    if (topConcat.metaIterators != null) {
                        while (!topConcat.metaIterators.isEmpty()) {
                            this.metaIterators.addFirst(topConcat.metaIterators.removeLast());
                        }
                    }
                    this.topMetaIterator = topConcat.topMetaIterator;
                }
            }
            return true;
        }

        public T next() {
            if (hasNext()) {
                Iterator<? extends T> it = this.iterator;
                this.toRemove = it;
                return it.next();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.checkRemove(this.toRemove != null);
            this.toRemove.remove();
            this.toRemove = null;
        }
    }
}
