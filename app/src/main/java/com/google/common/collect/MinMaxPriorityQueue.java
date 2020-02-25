package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

@GwtCompatible
@Beta
public final class MinMaxPriorityQueue<E> extends AbstractQueue<E> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final int EVEN_POWERS_OF_TWO = 1431655765;
    private static final int ODD_POWERS_OF_TWO = -1431655766;
    @VisibleForTesting
    final int maximumSize;
    private final MinMaxPriorityQueue<E>.Heap maxHeap;
    private final MinMaxPriorityQueue<E>.Heap minHeap;
    /* access modifiers changed from: private */
    public int modCount;
    /* access modifiers changed from: private */
    public Object[] queue;
    /* access modifiers changed from: private */
    public int size;

    private MinMaxPriorityQueue(Builder<? super E> builder, int queueSize) {
        Ordering<E> ordering = builder.ordering();
        this.minHeap = new Heap(ordering);
        this.maxHeap = new Heap(ordering.reverse());
        MinMaxPriorityQueue<E>.Heap heap = this.minHeap;
        MinMaxPriorityQueue<E>.Heap heap2 = this.maxHeap;
        heap.otherHeap = heap2;
        heap2.otherHeap = heap;
        this.maximumSize = builder.maximumSize;
        this.queue = new Object[queueSize];
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
        return new Builder(Ordering.natural()).create();
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> initialContents) {
        return new Builder(Ordering.natural()).create(initialContents);
    }

    public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
        return new Builder<>(comparator);
    }

    public static Builder<Comparable> expectedSize(int expectedSize) {
        return new Builder(Ordering.natural()).expectedSize(expectedSize);
    }

    public static Builder<Comparable> maximumSize(int maximumSize2) {
        return new Builder(Ordering.natural()).maximumSize(maximumSize2);
    }

    @VisibleForTesting
    static boolean isEvenLevel(int index) {
        int oneBased = ((index + 1) ^ -1) ^ -1;
        Preconditions.checkState(oneBased > 0, "negative index");
        if ((EVEN_POWERS_OF_TWO & oneBased) > (ODD_POWERS_OF_TWO & oneBased)) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    static int initialQueueSize(int configuredExpectedSize, int maximumSize2, Iterable<?> initialContents) {
        int result;
        if (configuredExpectedSize == -1) {
            result = 11;
        } else {
            result = configuredExpectedSize;
        }
        if (initialContents instanceof Collection) {
            result = Math.max(result, ((Collection) initialContents).size());
        }
        return capAtMaximumSize(result, maximumSize2);
    }

    private static int capAtMaximumSize(int queueSize, int maximumSize2) {
        return Math.min(queueSize - 1, maximumSize2) + 1;
    }

    public int size() {
        return this.size;
    }

    @CanIgnoreReturnValue
    public boolean add(E element) {
        offer(element);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> newElements) {
        boolean modified = false;
        for (E element : newElements) {
            offer(element);
            modified = true;
        }
        return modified;
    }

    @CanIgnoreReturnValue
    public boolean offer(E element) {
        Preconditions.checkNotNull(element);
        this.modCount++;
        int insertIndex = this.size;
        this.size = insertIndex + 1;
        growIfNeeded();
        heapForIndex(insertIndex).bubbleUp(insertIndex, element);
        if (this.size <= this.maximumSize || pollLast() != element) {
            return true;
        }
        return false;
    }

    @CanIgnoreReturnValue
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        return removeAndGet(0);
    }

    /* access modifiers changed from: package-private */
    public E elementData(int index) {
        return this.queue[index];
    }

    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return elementData(0);
    }

    private int getMaxElementIndex() {
        int i = this.size;
        if (i == 1) {
            return 0;
        }
        if (i == 2 || this.maxHeap.compareElements(1, 2) <= 0) {
            return 1;
        }
        return 2;
    }

    @CanIgnoreReturnValue
    public E pollFirst() {
        return poll();
    }

    @CanIgnoreReturnValue
    public E removeFirst() {
        return remove();
    }

    public E peekFirst() {
        return peek();
    }

    @CanIgnoreReturnValue
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        return removeAndGet(getMaxElementIndex());
    }

    @CanIgnoreReturnValue
    public E removeLast() {
        if (!isEmpty()) {
            return removeAndGet(getMaxElementIndex());
        }
        throw new NoSuchElementException();
    }

    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return elementData(getMaxElementIndex());
    }

    /* access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    @VisibleForTesting
    public MoveDesc<E> removeAt(int index) {
        Preconditions.checkPositionIndex(index, this.size);
        this.modCount++;
        this.size--;
        int i = this.size;
        if (i == index) {
            this.queue[i] = null;
            return null;
        }
        E actualLastElement = elementData(i);
        int lastElementAt = heapForIndex(this.size).swapWithConceptuallyLastElement(actualLastElement);
        if (lastElementAt == index) {
            this.queue[this.size] = null;
            return null;
        }
        E toTrickle = elementData(this.size);
        this.queue[this.size] = null;
        MoveDesc<E> changes = fillHole(index, toTrickle);
        if (lastElementAt >= index) {
            return changes;
        }
        if (changes == null) {
            return new MoveDesc<>(actualLastElement, toTrickle);
        }
        return new MoveDesc<>(actualLastElement, changes.replaced);
    }

    private MoveDesc<E> fillHole(int index, E toTrickle) {
        MinMaxPriorityQueue<E>.Heap heap = heapForIndex(index);
        int vacated = heap.fillHoleAt(index);
        int bubbledTo = heap.bubbleUpAlternatingLevels(vacated, toTrickle);
        if (bubbledTo == vacated) {
            return heap.tryCrossOverAndBubbleUp(index, vacated, toTrickle);
        }
        if (bubbledTo < index) {
            return new MoveDesc<>(toTrickle, elementData(index));
        }
        return null;
    }

    private E removeAndGet(int index) {
        E value = elementData(index);
        removeAt(index);
        return value;
    }

    private MinMaxPriorityQueue<E>.Heap heapForIndex(int i) {
        return isEvenLevel(i) ? this.minHeap : this.maxHeap;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isIntact() {
        for (int i = 1; i < this.size; i++) {
            if (!heapForIndex(i).verifyIndex(i)) {
                return false;
            }
        }
        return true;
    }

    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.queue[i] = null;
        }
        this.size = 0;
    }

    public Object[] toArray() {
        int i = this.size;
        Object[] copyTo = new Object[i];
        System.arraycopy(this.queue, 0, copyTo, 0, i);
        return copyTo;
    }

    public Comparator<? super E> comparator() {
        return this.minHeap.ordering;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int capacity() {
        return this.queue.length;
    }

    private void growIfNeeded() {
        if (this.size > this.queue.length) {
            Object[] newQueue = new Object[calculateNewCapacity()];
            Object[] objArr = this.queue;
            System.arraycopy(objArr, 0, newQueue, 0, objArr.length);
            this.queue = newQueue;
        }
    }

    private int calculateNewCapacity() {
        int oldCapacity = this.queue.length;
        return capAtMaximumSize(oldCapacity < 64 ? (oldCapacity + 1) * 2 : IntMath.checkedMultiply(oldCapacity / 2, 3), this.maximumSize);
    }

    @Beta
    public static final class Builder<B> {
        private static final int UNSET_EXPECTED_SIZE = -1;
        private final Comparator<B> comparator;
        /* access modifiers changed from: private */
        public int maximumSize;
        private int expectedSize;

        private Builder(Comparator<B> comparator2) {
            this.expectedSize = -1;
            this.maximumSize = Integer.MAX_VALUE;
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator2);
        }

        @CanIgnoreReturnValue
        public Builder<B> expectedSize(int expectedSize2) {
            Preconditions.checkArgument(expectedSize2 >= 0);
            this.expectedSize = expectedSize2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<B> maximumSize(int maximumSize2) {
            Preconditions.checkArgument(maximumSize2 > 0);
            this.maximumSize = maximumSize2;
            return this;
        }

        public <T extends B> MinMaxPriorityQueue<T> create() {
            return create(Collections.emptySet());
        }

        public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> initialContents) {
            MinMaxPriorityQueue<T> queue = new MinMaxPriorityQueue<>(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, initialContents));
            for (T element : initialContents) {
                queue.offer(element);
            }
            return queue;
        }

        /* access modifiers changed from: private */
        public <T extends B> Ordering<T> ordering() {
            return Ordering.from(this.comparator);
        }
    }

    static class MoveDesc<E> {
        final E replaced;
        final E toTrickle;

        MoveDesc(E toTrickle2, E replaced2) {
            this.toTrickle = toTrickle2;
            this.replaced = replaced2;
        }
    }

    private class Heap {
        final Ordering<E> ordering;
        @MonotonicNonNullDecl
        @Weak
        MinMaxPriorityQueue<E>.Heap otherHeap;

        Heap(Ordering<E> ordering2) {
            this.ordering = ordering2;
        }

        /* access modifiers changed from: package-private */
        public int compareElements(int a, int b) {
            return this.ordering.compare(MinMaxPriorityQueue.this.elementData(a), MinMaxPriorityQueue.this.elementData(b));
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: E
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
        com.google.common.collect.MinMaxPriorityQueue.MoveDesc<E> tryCrossOverAndBubbleUp(int r5, int r6, E r7) {
            /*
                r4 = this;
                int r0 = r4.crossOver(r6, r7)
                r1 = 0
                if (r0 != r6) goto L_0x0008
                return r1
            L_0x0008:
                if (r0 >= r5) goto L_0x0011
                com.google.common.collect.MinMaxPriorityQueue r2 = com.google.common.collect.MinMaxPriorityQueue.this
                java.lang.Object r2 = r2.elementData(r5)
                goto L_0x001b
            L_0x0011:
                com.google.common.collect.MinMaxPriorityQueue r2 = com.google.common.collect.MinMaxPriorityQueue.this
                int r3 = r4.getParentIndex(r5)
                java.lang.Object r2 = r2.elementData(r3)
            L_0x001b:
                com.google.common.collect.MinMaxPriorityQueue<E>$Heap r3 = r4.otherHeap
                int r3 = r3.bubbleUpAlternatingLevels(r0, r7)
                if (r3 >= r5) goto L_0x0029
                com.google.common.collect.MinMaxPriorityQueue$MoveDesc r1 = new com.google.common.collect.MinMaxPriorityQueue$MoveDesc
                r1.<init>(r7, r2)
                return r1
            L_0x0029:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MinMaxPriorityQueue.Heap.tryCrossOverAndBubbleUp(int, int, java.lang.Object):com.google.common.collect.MinMaxPriorityQueue$MoveDesc");
        }

        /* access modifiers changed from: package-private */
        public void bubbleUp(int index, E x) {
            Heap heap;
            int crossOver = crossOverUp(index, x);
            if (crossOver == index) {
                heap = this;
            } else {
                index = crossOver;
                heap = this.otherHeap;
            }
            heap.bubbleUpAlternatingLevels(index, x);
        }

        /* access modifiers changed from: package-private */
        @CanIgnoreReturnValue
        public int bubbleUpAlternatingLevels(int index, E x) {
            while (index > 2) {
                int grandParentIndex = getGrandparentIndex(index);
                E e = MinMaxPriorityQueue.this.elementData(grandParentIndex);
                if (this.ordering.compare(e, x) <= 0) {
                    break;
                }
                MinMaxPriorityQueue.this.queue[index] = e;
                index = grandParentIndex;
            }
            MinMaxPriorityQueue.this.queue[index] = x;
            return index;
        }

        /* access modifiers changed from: package-private */
        public int findMin(int index, int len) {
            if (index >= MinMaxPriorityQueue.this.size) {
                return -1;
            }
            Preconditions.checkState(index > 0);
            int limit = Math.min(index, MinMaxPriorityQueue.this.size - len) + len;
            int minIndex = index;
            for (int i = index + 1; i < limit; i++) {
                if (compareElements(i, minIndex) < 0) {
                    minIndex = i;
                }
            }
            return minIndex;
        }

        /* access modifiers changed from: package-private */
        public int findMinChild(int index) {
            return findMin(getLeftChildIndex(index), 2);
        }

        /* access modifiers changed from: package-private */
        public int findMinGrandChild(int index) {
            int leftChildIndex = getLeftChildIndex(index);
            if (leftChildIndex < 0) {
                return -1;
            }
            return findMin(getLeftChildIndex(leftChildIndex), 4);
        }

        /* access modifiers changed from: package-private */
        public int crossOverUp(int index, E x) {
            int uncleIndex;
            if (index == 0) {
                MinMaxPriorityQueue.this.queue[0] = x;
                return 0;
            }
            int parentIndex = getParentIndex(index);
            E parentElement = MinMaxPriorityQueue.this.elementData(parentIndex);
            if (!(parentIndex == 0 || (uncleIndex = getRightChildIndex(getParentIndex(parentIndex))) == parentIndex || getLeftChildIndex(uncleIndex) < MinMaxPriorityQueue.this.size)) {
                E uncleElement = MinMaxPriorityQueue.this.elementData(uncleIndex);
                if (this.ordering.compare(uncleElement, parentElement) < 0) {
                    parentIndex = uncleIndex;
                    parentElement = uncleElement;
                }
            }
            if (this.ordering.compare(parentElement, x) < 0) {
                MinMaxPriorityQueue.this.queue[index] = parentElement;
                MinMaxPriorityQueue.this.queue[parentIndex] = x;
                return parentIndex;
            }
            MinMaxPriorityQueue.this.queue[index] = x;
            return index;
        }

        /* access modifiers changed from: package-private */
        public int swapWithConceptuallyLastElement(E actualLastElement) {
            int uncleIndex;
            int parentIndex = getParentIndex(MinMaxPriorityQueue.this.size);
            if (!(parentIndex == 0 || (uncleIndex = getRightChildIndex(getParentIndex(parentIndex))) == parentIndex || getLeftChildIndex(uncleIndex) < MinMaxPriorityQueue.this.size)) {
                E uncleElement = MinMaxPriorityQueue.this.elementData(uncleIndex);
                if (this.ordering.compare(uncleElement, actualLastElement) < 0) {
                    MinMaxPriorityQueue.this.queue[uncleIndex] = actualLastElement;
                    MinMaxPriorityQueue.this.queue[MinMaxPriorityQueue.this.size] = uncleElement;
                    return uncleIndex;
                }
            }
            return MinMaxPriorityQueue.this.size;
        }

        /* access modifiers changed from: package-private */
        public int crossOver(int index, E x) {
            int minChildIndex = findMinChild(index);
            if (minChildIndex <= 0 || this.ordering.compare(MinMaxPriorityQueue.this.elementData(minChildIndex), x) >= 0) {
                return crossOverUp(index, x);
            }
            MinMaxPriorityQueue.this.queue[index] = MinMaxPriorityQueue.this.elementData(minChildIndex);
            MinMaxPriorityQueue.this.queue[minChildIndex] = x;
            return minChildIndex;
        }

        /* access modifiers changed from: package-private */
        public int fillHoleAt(int index) {
            while (true) {
                int findMinGrandChild = findMinGrandChild(index);
                int minGrandchildIndex = findMinGrandChild;
                if (findMinGrandChild <= 0) {
                    return index;
                }
                MinMaxPriorityQueue.this.queue[index] = MinMaxPriorityQueue.this.elementData(minGrandchildIndex);
                index = minGrandchildIndex;
            }
        }

        /* access modifiers changed from: private */
        public boolean verifyIndex(int i) {
            if (getLeftChildIndex(i) < MinMaxPriorityQueue.this.size && compareElements(i, getLeftChildIndex(i)) > 0) {
                return false;
            }
            if (getRightChildIndex(i) < MinMaxPriorityQueue.this.size && compareElements(i, getRightChildIndex(i)) > 0) {
                return false;
            }
            if (i > 0 && compareElements(i, getParentIndex(i)) > 0) {
                return false;
            }
            if (i <= 2 || compareElements(getGrandparentIndex(i), i) <= 0) {
                return true;
            }
            return false;
        }

        private int getLeftChildIndex(int i) {
            return (i * 2) + 1;
        }

        private int getRightChildIndex(int i) {
            return (i * 2) + 2;
        }

        private int getParentIndex(int i) {
            return (i - 1) / 2;
        }

        private int getGrandparentIndex(int i) {
            return getParentIndex(getParentIndex(i));
        }
    }

    private class QueueIterator implements Iterator<E> {
        private boolean canRemove;
        private int cursor;
        private int expectedModCount;
        @MonotonicNonNullDecl
        private Queue<E> forgetMeNot;
        @NullableDecl
        private E lastFromForgetMeNot;
        private int nextCursor;
        @MonotonicNonNullDecl
        private List<E> skipMe;

        private QueueIterator() {
            this.cursor = -1;
            this.nextCursor = -1;
            this.expectedModCount = MinMaxPriorityQueue.this.modCount;
        }

        public boolean hasNext() {
            checkModCount();
            nextNotInSkipMe(this.cursor + 1);
            if (this.nextCursor < MinMaxPriorityQueue.this.size()) {
                return true;
            }
            Queue<E> queue = this.forgetMeNot;
            if (queue == null || queue.isEmpty()) {
                return false;
            }
            return true;
        }

        public E next() {
            checkModCount();
            nextNotInSkipMe(this.cursor + 1);
            if (this.nextCursor < MinMaxPriorityQueue.this.size()) {
                this.cursor = this.nextCursor;
                this.canRemove = true;
                return MinMaxPriorityQueue.this.elementData(this.cursor);
            }
            if (this.forgetMeNot != null) {
                this.cursor = MinMaxPriorityQueue.this.size();
                this.lastFromForgetMeNot = this.forgetMeNot.poll();
                E e = this.lastFromForgetMeNot;
                if (e != null) {
                    this.canRemove = true;
                    return e;
                }
            }
            throw new NoSuchElementException("iterator moved past last element in queue.");
        }

        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            checkModCount();
            this.canRemove = false;
            this.expectedModCount++;
            if (this.cursor < MinMaxPriorityQueue.this.size()) {
                MoveDesc<E> moved = MinMaxPriorityQueue.this.removeAt(this.cursor);
                if (moved != null) {
                    if (this.forgetMeNot == null) {
                        this.forgetMeNot = new ArrayDeque();
                        this.skipMe = new ArrayList(3);
                    }
                    if (!foundAndRemovedExactReference(this.skipMe, moved.toTrickle)) {
                        this.forgetMeNot.add(moved.toTrickle);
                    }
                    if (!foundAndRemovedExactReference(this.forgetMeNot, moved.replaced)) {
                        this.skipMe.add(moved.replaced);
                    }
                }
                this.cursor--;
                this.nextCursor--;
                return;
            }
            Preconditions.checkState(removeExact(this.lastFromForgetMeNot));
            this.lastFromForgetMeNot = null;
        }

        private boolean foundAndRemovedExactReference(Iterable<E> elements, E target) {
            Iterator<E> it = elements.iterator();
            while (it.hasNext()) {
                if (it.next() == target) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        private boolean removeExact(Object target) {
            for (int i = 0; i < MinMaxPriorityQueue.this.size; i++) {
                if (MinMaxPriorityQueue.this.queue[i] == target) {
                    MinMaxPriorityQueue.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }

        private void checkModCount() {
            if (MinMaxPriorityQueue.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void nextNotInSkipMe(int c) {
            if (this.nextCursor < c) {
                if (this.skipMe != null) {
                    while (c < MinMaxPriorityQueue.this.size() && foundAndRemovedExactReference(this.skipMe, MinMaxPriorityQueue.this.elementData(c))) {
                        c++;
                    }
                }
                this.nextCursor = c;
            }
        }
    }
}
