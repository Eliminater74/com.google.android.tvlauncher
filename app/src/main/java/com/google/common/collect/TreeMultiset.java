package com.google.common.collect;

import com.google.android.tvlauncher.notifications.NotificationsContract;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;

@GwtCompatible(emulated = true)
public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final transient AvlNode<E> header;
    /* access modifiers changed from: private */
    public final transient GeneralRange<E> range;
    private final transient Reference<AvlNode<E>> rootReference;

    TreeMultiset(Reference<AvlNode<E>> rootReference2, GeneralRange<E> range2, AvlNode<E> endLink) {
        super(range2.comparator());
        this.rootReference = rootReference2;
        this.range = range2;
        this.header = endLink;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.range = GeneralRange.all(comparator);
        this.header = new AvlNode<>(null, 1);
        AvlNode<E> avlNode = this.header;
        successor(avlNode, avlNode);
        this.rootReference = new Reference<>();
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<>(Ordering.natural());
    }

    public static <E> TreeMultiset<E> create(@NullableDecl Comparator<? super E> comparator) {
        if (comparator == null) {
            return new TreeMultiset<>(Ordering.natural());
        }
        return new TreeMultiset<>(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> elements) {
        TreeMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    static int distinctElements(@NullableDecl AvlNode<?> node) {
        if (node == null) {
            return 0;
        }
        return node.distinctElements;
    }

    /* access modifiers changed from: private */
    public static <T> void successor(AvlNode<T> a, AvlNode<T> b) {
        AvlNode unused = a.succ = b;
        AvlNode unused2 = b.pred = a;
    }

    /* access modifiers changed from: private */
    public static <T> void successor(AvlNode<T> a, AvlNode<T> b, AvlNode<T> c) {
        successor(a, b);
        successor(b, c);
    }

    public /* bridge */ /* synthetic */ Comparator comparator() {
        return super.comparator();
    }

    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj) {
        return super.contains(obj);
    }

    public /* bridge */ /* synthetic */ SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    public /* bridge */ /* synthetic */ NavigableSet elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ Multiset.Entry firstEntry() {
        return super.firstEntry();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Multiset.Entry lastEntry() {
        return super.lastEntry();
    }

    public /* bridge */ /* synthetic */ Multiset.Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    public /* bridge */ /* synthetic */ Multiset.Entry pollLastEntry() {
        return super.pollLastEntry();
    }

    public /* bridge */ /* synthetic */ SortedMultiset subMultiset(@NullableDecl Object obj, BoundType boundType, @NullableDecl Object obj2, BoundType boundType2) {
        return super.subMultiset(obj, boundType, obj2, boundType2);
    }

    private long aggregateForEntries(Aggregate aggr) {
        AvlNode<E> root = this.rootReference.get();
        long total = aggr.treeAggregate(root);
        if (this.range.hasLowerBound()) {
            total -= aggregateBelowRange(aggr, root);
        }
        if (this.range.hasUpperBound()) {
            return total - aggregateAboveRange(aggr, root);
        }
        return total;
    }

    private long aggregateBelowRange(Aggregate aggr, @NullableDecl AvlNode<E> node) {
        if (node == null) {
            return 0;
        }
        int cmp = comparator().compare(this.range.getLowerEndpoint(), node.elem);
        if (cmp < 0) {
            return aggregateBelowRange(aggr, node.left);
        }
        if (cmp != 0) {
            return aggr.treeAggregate(node.left) + ((long) aggr.nodeAggregate(node)) + aggregateBelowRange(aggr, node.right);
        }
        int i = C17134.$SwitchMap$com$google$common$collect$BoundType[this.range.getLowerBoundType().ordinal()];
        if (i == 1) {
            return ((long) aggr.nodeAggregate(node)) + aggr.treeAggregate(node.left);
        }
        if (i == 2) {
            return aggr.treeAggregate(node.left);
        }
        throw new AssertionError();
    }

    private long aggregateAboveRange(Aggregate aggr, @NullableDecl AvlNode<E> node) {
        if (node == null) {
            return 0;
        }
        int cmp = comparator().compare(this.range.getUpperEndpoint(), node.elem);
        if (cmp > 0) {
            return aggregateAboveRange(aggr, node.right);
        }
        if (cmp != 0) {
            return aggr.treeAggregate(node.right) + ((long) aggr.nodeAggregate(node)) + aggregateAboveRange(aggr, node.left);
        }
        int i = C17134.$SwitchMap$com$google$common$collect$BoundType[this.range.getUpperBoundType().ordinal()];
        if (i == 1) {
            return ((long) aggr.nodeAggregate(node)) + aggr.treeAggregate(node.right);
        }
        if (i == 2) {
            return aggr.treeAggregate(node.right);
        }
        throw new AssertionError();
    }

    public int size() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.SIZE));
    }

    /* access modifiers changed from: package-private */
    public int distinctElements() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.DISTINCT));
    }

    public int count(@NullableDecl Object element) {
        Object obj = element;
        try {
            AvlNode<E> root = this.rootReference.get();
            if (this.range.contains(obj)) {
                if (root != null) {
                    return root.count(comparator(), obj);
                }
            }
            return 0;
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    @CanIgnoreReturnValue
    public int add(@NullableDecl E element, int occurrences) {
        CollectPreconditions.checkNonnegative(occurrences, "occurrences");
        if (occurrences == 0) {
            return count(element);
        }
        Preconditions.checkArgument(this.range.contains(element));
        AvlNode<E> root = this.rootReference.get();
        if (root == null) {
            comparator().compare(element, element);
            AvlNode<E> newRoot = new AvlNode<>(element, occurrences);
            AvlNode<E> avlNode = this.header;
            successor(avlNode, newRoot, avlNode);
            this.rootReference.checkAndSet(root, newRoot);
            return 0;
        }
        int[] result = new int[1];
        this.rootReference.checkAndSet(root, root.add(comparator(), element, occurrences, result));
        return result[0];
    }

    /* JADX INFO: Multiple debug info for r2v2 com.google.common.collect.TreeMultiset$AvlNode<E>: [D('e' E), D('newRoot' com.google.common.collect.TreeMultiset$AvlNode<E>)] */
    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object element, int occurrences) {
        CollectPreconditions.checkNonnegative(occurrences, "occurrences");
        if (occurrences == 0) {
            return count(element);
        }
        AvlNode<E> root = this.rootReference.get();
        int[] result = new int[1];
        Object obj = element;
        try {
            if (this.range.contains(obj)) {
                if (root != null) {
                    this.rootReference.checkAndSet(root, root.remove(comparator(), obj, occurrences, result));
                    return result[0];
                }
            }
            return 0;
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    @CanIgnoreReturnValue
    public int setCount(@NullableDecl E element, int count) {
        CollectPreconditions.checkNonnegative(count, NotificationsContract.COLUMN_COUNT);
        boolean z = true;
        if (!this.range.contains(element)) {
            if (count != 0) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return 0;
        }
        AvlNode<E> root = this.rootReference.get();
        if (root == null) {
            if (count > 0) {
                add(element, count);
            }
            return 0;
        }
        int[] result = new int[1];
        this.rootReference.checkAndSet(root, root.setCount(comparator(), element, count, result));
        return result[0];
    }

    @CanIgnoreReturnValue
    public boolean setCount(@NullableDecl E element, int oldCount, int newCount) {
        CollectPreconditions.checkNonnegative(newCount, "newCount");
        CollectPreconditions.checkNonnegative(oldCount, "oldCount");
        Preconditions.checkArgument(this.range.contains(element));
        AvlNode<E> root = this.rootReference.get();
        if (root != null) {
            int[] result = new int[1];
            this.rootReference.checkAndSet(root, root.setCount(comparator(), element, oldCount, newCount, result));
            if (result[0] == oldCount) {
                return true;
            }
            return false;
        } else if (oldCount != 0) {
            return false;
        } else {
            if (newCount > 0) {
                add(element, newCount);
            }
            return true;
        }
    }

    public void clear() {
        if (this.range.hasLowerBound() || this.range.hasUpperBound()) {
            Iterators.clear(entryIterator());
            return;
        }
        AvlNode<E> current = this.header.succ;
        while (true) {
            AvlNode<E> avlNode = this.header;
            if (current != avlNode) {
                AvlNode<E> next = current.succ;
                int unused = current.elemCount = 0;
                AvlNode unused2 = current.left = null;
                AvlNode unused3 = current.right = null;
                AvlNode unused4 = current.pred = null;
                AvlNode unused5 = current.succ = null;
                current = next;
            } else {
                successor(avlNode, avlNode);
                this.rootReference.clear();
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public Multiset.Entry<E> wrapEntry(final AvlNode<E> baseEntry) {
        return new Multisets.AbstractEntry<E>() {
            public E getElement() {
                return baseEntry.getElement();
            }

            public int getCount() {
                int result = baseEntry.getCount();
                if (result == 0) {
                    return TreeMultiset.this.count(getElement());
                }
                return result;
            }
        };
    }

    /* access modifiers changed from: private */
    @NullableDecl
    public AvlNode<E> firstNode() {
        AvlNode<E> endpoint;
        if (this.rootReference.get() == null) {
            return null;
        }
        if (this.range.hasLowerBound()) {
            E endpoint2 = this.range.getLowerEndpoint();
            AvlNode<E> node = this.rootReference.get().ceiling(comparator(), endpoint2);
            if (node == null) {
                return null;
            }
            if (this.range.getLowerBoundType() == BoundType.OPEN && comparator().compare(endpoint2, node.getElement()) == 0) {
                node = node.succ;
            }
            endpoint = node;
        } else {
            endpoint = this.header.succ;
        }
        if (endpoint == this.header || !this.range.contains(endpoint.getElement())) {
            return null;
        }
        return endpoint;
    }

    /* access modifiers changed from: private */
    @NullableDecl
    public AvlNode<E> lastNode() {
        AvlNode<E> endpoint;
        if (this.rootReference.get() == null) {
            return null;
        }
        if (this.range.hasUpperBound()) {
            E endpoint2 = this.range.getUpperEndpoint();
            AvlNode<E> node = this.rootReference.get().floor(comparator(), endpoint2);
            if (node == null) {
                return null;
            }
            if (this.range.getUpperBoundType() == BoundType.OPEN && comparator().compare(endpoint2, node.getElement()) == 0) {
                node = node.pred;
            }
            endpoint = node;
        } else {
            endpoint = this.header.pred;
        }
        if (endpoint == this.header || !this.range.contains(endpoint.getElement())) {
            return null;
        }
        return endpoint;
    }

    /* access modifiers changed from: package-private */
    public Iterator<E> elementIterator() {
        return Multisets.elementIterator(entryIterator());
    }

    /* access modifiers changed from: package-private */
    public Iterator<Multiset.Entry<E>> entryIterator() {
        return new Iterator<Multiset.Entry<E>>() {
            AvlNode<E> current = TreeMultiset.this.firstNode();
            @NullableDecl
            Multiset.Entry<E> prevEntry;

            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooHigh(this.current.getElement())) {
                    return true;
                }
                this.current = null;
                return false;
            }

            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    Multiset.Entry<E> result = TreeMultiset.this.wrapEntry(this.current);
                    this.prevEntry = result;
                    if (this.current.succ == TreeMultiset.this.header) {
                        this.current = null;
                    } else {
                        this.current = this.current.succ;
                    }
                    return result;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    /* access modifiers changed from: package-private */
    public Iterator<Multiset.Entry<E>> descendingEntryIterator() {
        return new Iterator<Multiset.Entry<E>>() {
            AvlNode<E> current = TreeMultiset.this.lastNode();
            Multiset.Entry<E> prevEntry = null;

            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooLow(this.current.getElement())) {
                    return true;
                }
                this.current = null;
                return false;
            }

            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    Multiset.Entry<E> result = TreeMultiset.this.wrapEntry(this.current);
                    this.prevEntry = result;
                    if (this.current.pred == TreeMultiset.this.header) {
                        this.current = null;
                    } else {
                        this.current = this.current.pred;
                    }
                    return result;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    public SortedMultiset<E> headMultiset(@NullableDecl E upperBound, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.upTo(comparator(), upperBound, boundType)), this.header);
    }

    public SortedMultiset<E> tailMultiset(@NullableDecl E lowerBound, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.downTo(comparator(), lowerBound, boundType)), this.header);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(elementSet().comparator());
        Serialization.writeMultiset(this, stream);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.TreeMultiset, java.util.Comparator<? super E>]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.TreeMultiset, com.google.common.collect.GeneralRange<? super E>]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.TreeMultiset, com.google.common.collect.TreeMultiset$Reference]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void
     arg types: [com.google.common.collect.TreeMultiset, com.google.common.collect.TreeMultiset$AvlNode<E>]
     candidates:
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, int):void
      com.google.common.collect.Serialization.FieldSetter.set(com.google.common.collect.ConcurrentHashMultiset, java.lang.Object):void */
    @GwtIncompatible
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Comparator<? super E> comparator = (Comparator) stream.readObject();
        Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set((ConcurrentHashMultiset) this, (Object) comparator);
        Serialization.getFieldSetter(TreeMultiset.class, "range").set((ConcurrentHashMultiset) this, (Object) GeneralRange.all(comparator));
        Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set((ConcurrentHashMultiset) this, (Object) new Reference());
        AvlNode<E> header2 = new AvlNode<>(null, 1);
        Serialization.getFieldSetter(TreeMultiset.class, "header").set((ConcurrentHashMultiset) this, (Object) header2);
        successor(header2, header2);
        Serialization.populateMultiset(this, stream);
    }

    private enum Aggregate {
        SIZE {
            /* access modifiers changed from: package-private */
            public int nodeAggregate(AvlNode<?> node) {
                return node.elemCount;
            }

            /* access modifiers changed from: package-private */
            public long treeAggregate(@NullableDecl AvlNode<?> root) {
                if (root == null) {
                    return 0;
                }
                return root.totalCount;
            }
        },
        DISTINCT {
            /* access modifiers changed from: package-private */
            public int nodeAggregate(AvlNode<?> avlNode) {
                return 1;
            }

            /* access modifiers changed from: package-private */
            public long treeAggregate(@NullableDecl AvlNode<?> root) {
                if (root == null) {
                    return 0;
                }
                return (long) root.distinctElements;
            }
        };

        /* access modifiers changed from: package-private */
        public abstract int nodeAggregate(AvlNode<?> avlNode);

        /* access modifiers changed from: package-private */
        public abstract long treeAggregate(@NullableDecl AvlNode<?> avlNode);
    }

    /* renamed from: com.google.common.collect.TreeMultiset$4 */
    static /* synthetic */ class C17134 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType = new int[BoundType.values().length];

        static {
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static final class Reference<T> {
        @NullableDecl
        private T value;

        private Reference() {
        }

        @NullableDecl
        public T get() {
            return this.value;
        }

        public void checkAndSet(@NullableDecl T expected, T newValue) {
            if (this.value == expected) {
                this.value = newValue;
                return;
            }
            throw new ConcurrentModificationException();
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.value = null;
        }
    }

    private static final class AvlNode<E> {
        /* access modifiers changed from: private */
        @NullableDecl
        public final E elem;
        /* access modifiers changed from: private */
        public int distinctElements;
        /* access modifiers changed from: private */
        public int elemCount;
        /* access modifiers changed from: private */
        @NullableDecl
        public AvlNode<E> left;
        /* access modifiers changed from: private */
        @NullableDecl
        public AvlNode<E> pred;
        /* access modifiers changed from: private */
        @NullableDecl
        public AvlNode<E> right;
        /* access modifiers changed from: private */
        @NullableDecl
        public AvlNode<E> succ;
        /* access modifiers changed from: private */
        public long totalCount;
        private int height;

        AvlNode(@NullableDecl E elem2, int elemCount2) {
            Preconditions.checkArgument(elemCount2 > 0);
            this.elem = elem2;
            this.elemCount = elemCount2;
            this.totalCount = (long) elemCount2;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        private static long totalCount(@NullableDecl AvlNode<?> node) {
            if (node == null) {
                return 0;
            }
            return node.totalCount;
        }

        private static int height(@NullableDecl AvlNode<?> node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        public int count(Comparator<? super E> comparator, E e) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    return 0;
                }
                return avlNode.count(comparator, e);
            } else if (cmp <= 0) {
                return this.elemCount;
            } else {
                AvlNode<E> avlNode2 = this.right;
                if (avlNode2 == null) {
                    return 0;
                }
                return avlNode2.count(comparator, e);
            }
        }

        private AvlNode<E> addRightChild(E e, int count) {
            this.right = new AvlNode<>(e, count);
            TreeMultiset.successor(this, this.right, this.succ);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += (long) count;
            return this;
        }

        private AvlNode<E> addLeftChild(E e, int count) {
            this.left = new AvlNode<>(e, count);
            TreeMultiset.successor(this.pred, this.left, this);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += (long) count;
            return this;
        }

        /* access modifiers changed from: package-private */
        public AvlNode<E> add(Comparator<? super E> comparator, @NullableDecl E e, int count, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            boolean z = true;
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    return addLeftChild(e, count);
                }
                int initHeight = initLeft.height;
                this.left = initLeft.add(comparator, e, count, result);
                if (result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) count;
                return this.left.height == initHeight ? this : rebalance();
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    return addRightChild(e, count);
                }
                int initHeight2 = initRight.height;
                this.right = initRight.add(comparator, e, count, result);
                if (result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) count;
                return this.right.height == initHeight2 ? this : rebalance();
            } else {
                int i = this.elemCount;
                result[0] = i;
                if (((long) i) + ((long) count) > 2147483647L) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                this.elemCount += count;
                this.totalCount += (long) count;
                return this;
            }
        }

        /* access modifiers changed from: package-private */
        public AvlNode<E> remove(Comparator<? super E> comparator, @NullableDecl E e, int count, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    return this;
                }
                this.left = initLeft.remove(comparator, e, count, result);
                if (result[0] > 0) {
                    if (count >= result[0]) {
                        this.distinctElements--;
                        this.totalCount -= (long) result[0];
                    } else {
                        this.totalCount -= (long) count;
                    }
                }
                return result[0] == 0 ? this : rebalance();
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    return this;
                }
                this.right = initRight.remove(comparator, e, count, result);
                if (result[0] > 0) {
                    if (count >= result[0]) {
                        this.distinctElements--;
                        this.totalCount -= (long) result[0];
                    } else {
                        this.totalCount -= (long) count;
                    }
                }
                return rebalance();
            } else {
                int i = this.elemCount;
                result[0] = i;
                if (count >= i) {
                    return deleteMe();
                }
                this.elemCount = i - count;
                this.totalCount -= (long) count;
                return this;
            }
        }

        /* access modifiers changed from: package-private */
        public AvlNode<E> setCount(Comparator<? super E> comparator, @NullableDecl E e, int count, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    return count > 0 ? addLeftChild(e, count) : this;
                }
                this.left = initLeft.setCount(comparator, e, count, result);
                if (count == 0 && result[0] != 0) {
                    this.distinctElements--;
                } else if (count > 0 && result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) (count - result[0]);
                return rebalance();
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    return count > 0 ? addRightChild(e, count) : this;
                }
                this.right = initRight.setCount(comparator, e, count, result);
                if (count == 0 && result[0] != 0) {
                    this.distinctElements--;
                } else if (count > 0 && result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) (count - result[0]);
                return rebalance();
            } else {
                int i = this.elemCount;
                result[0] = i;
                if (count == 0) {
                    return deleteMe();
                }
                this.totalCount += (long) (count - i);
                this.elemCount = count;
                return this;
            }
        }

        /* access modifiers changed from: package-private */
        public AvlNode<E> setCount(Comparator<? super E> comparator, @NullableDecl E e, int expectedCount, int newCount, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    if (expectedCount != 0 || newCount <= 0) {
                        return this;
                    }
                    return addLeftChild(e, newCount);
                }
                this.left = initLeft.setCount(comparator, e, expectedCount, newCount, result);
                if (result[0] == expectedCount) {
                    if (newCount == 0 && result[0] != 0) {
                        this.distinctElements--;
                    } else if (newCount > 0 && result[0] == 0) {
                        this.distinctElements++;
                    }
                    this.totalCount += (long) (newCount - result[0]);
                }
                return rebalance();
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    if (expectedCount != 0 || newCount <= 0) {
                        return this;
                    }
                    return addRightChild(e, newCount);
                }
                this.right = initRight.setCount(comparator, e, expectedCount, newCount, result);
                if (result[0] == expectedCount) {
                    if (newCount == 0 && result[0] != 0) {
                        this.distinctElements--;
                    } else if (newCount > 0 && result[0] == 0) {
                        this.distinctElements++;
                    }
                    this.totalCount += (long) (newCount - result[0]);
                }
                return rebalance();
            } else {
                int i = this.elemCount;
                result[0] = i;
                if (expectedCount == i) {
                    if (newCount == 0) {
                        return deleteMe();
                    }
                    this.totalCount += (long) (newCount - i);
                    this.elemCount = newCount;
                }
                return this;
            }
        }

        private AvlNode<E> deleteMe() {
            int oldElemCount = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.successor(this.pred, this.succ);
            AvlNode<E> avlNode = this.left;
            if (avlNode == null) {
                return this.right;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return avlNode;
            }
            if (avlNode.height >= avlNode2.height) {
                AvlNode<E> newTop = this.pred;
                newTop.left = avlNode.removeMax(newTop);
                newTop.right = this.right;
                newTop.distinctElements = this.distinctElements - 1;
                newTop.totalCount = this.totalCount - ((long) oldElemCount);
                return newTop.rebalance();
            }
            AvlNode<E> newTop2 = this.succ;
            newTop2.right = avlNode2.removeMin(newTop2);
            newTop2.left = this.left;
            newTop2.distinctElements = this.distinctElements - 1;
            newTop2.totalCount = this.totalCount - ((long) oldElemCount);
            return newTop2.rebalance();
        }

        private AvlNode<E> removeMin(AvlNode<E> node) {
            AvlNode<E> avlNode = this.left;
            if (avlNode == null) {
                return this.right;
            }
            this.left = avlNode.removeMin(node);
            this.distinctElements--;
            this.totalCount -= (long) node.elemCount;
            return rebalance();
        }

        private AvlNode<E> removeMax(AvlNode<E> node) {
            AvlNode<E> avlNode = this.right;
            if (avlNode == null) {
                return this.left;
            }
            this.right = avlNode.removeMax(node);
            this.distinctElements--;
            this.totalCount -= (long) node.elemCount;
            return rebalance();
        }

        private void recomputeMultiset() {
            this.distinctElements = TreeMultiset.distinctElements(this.left) + 1 + TreeMultiset.distinctElements(this.right);
            this.totalCount = ((long) this.elemCount) + totalCount(this.left) + totalCount(this.right);
        }

        private void recomputeHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        private void recompute() {
            recomputeMultiset();
            recomputeHeight();
        }

        private AvlNode<E> rebalance() {
            int balanceFactor = balanceFactor();
            if (balanceFactor == -2) {
                if (this.right.balanceFactor() > 0) {
                    this.right = this.right.rotateRight();
                }
                return rotateLeft();
            } else if (balanceFactor != 2) {
                recomputeHeight();
                return this;
            } else {
                if (this.left.balanceFactor() < 0) {
                    this.left = this.left.rotateLeft();
                }
                return rotateRight();
            }
        }

        private int balanceFactor() {
            return height(this.left) - height(this.right);
        }

        private AvlNode<E> rotateLeft() {
            Preconditions.checkState(this.right != null);
            AvlNode<E> newTop = this.right;
            this.right = newTop.left;
            newTop.left = this;
            newTop.totalCount = this.totalCount;
            newTop.distinctElements = this.distinctElements;
            recompute();
            newTop.recomputeHeight();
            return newTop;
        }

        private AvlNode<E> rotateRight() {
            Preconditions.checkState(this.left != null);
            AvlNode<E> newTop = this.left;
            this.left = newTop.right;
            newTop.right = this;
            newTop.totalCount = this.totalCount;
            newTop.distinctElements = this.distinctElements;
            recompute();
            newTop.recomputeHeight();
            return newTop;
        }

        /* access modifiers changed from: private */
        @NullableDecl
        public AvlNode<E> ceiling(Comparator<? super E> comparator, E e) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> avlNode = this.left;
                return avlNode == null ? this : (AvlNode) MoreObjects.firstNonNull(avlNode.ceiling(comparator, e), this);
            } else if (cmp == 0) {
                return this;
            } else {
                AvlNode<E> avlNode2 = this.right;
                if (avlNode2 == null) {
                    return null;
                }
                return avlNode2.ceiling(comparator, e);
            }
        }

        /* access modifiers changed from: private */
        @NullableDecl
        public AvlNode<E> floor(Comparator<? super E> comparator, E e) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp > 0) {
                AvlNode<E> avlNode = this.right;
                return avlNode == null ? this : (AvlNode) MoreObjects.firstNonNull(avlNode.floor(comparator, e), this);
            } else if (cmp == 0) {
                return this;
            } else {
                AvlNode<E> avlNode2 = this.left;
                if (avlNode2 == null) {
                    return null;
                }
                return avlNode2.floor(comparator, e);
            }
        }

        /* access modifiers changed from: package-private */
        public E getElement() {
            return this.elem;
        }

        /* access modifiers changed from: package-private */
        public int getCount() {
            return this.elemCount;
        }

        public String toString() {
            return Multisets.immutableEntry(getElement(), getCount()).toString();
        }
    }
}
