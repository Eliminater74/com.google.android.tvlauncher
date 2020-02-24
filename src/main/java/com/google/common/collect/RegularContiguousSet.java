package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    private static final long serialVersionUID = 0;
    private final Range<C> range;

    RegularContiguousSet(Range<C> range2, DiscreteDomain<C> domain) {
        super(domain);
        this.range = range2;
    }

    private ContiguousSet<C> intersectionInCurrentDomain(Range<C> other) {
        if (this.range.isConnected(other)) {
            return ContiguousSet.create(this.range.intersection(other), this.domain);
        }
        return new EmptyContiguousSet(this.domain);
    }

    /* access modifiers changed from: package-private */
    public ContiguousSet<C> headSetImpl(C toElement, boolean inclusive) {
        return intersectionInCurrentDomain(Range.upTo(toElement, BoundType.forBoolean(inclusive)));
    }

    /* access modifiers changed from: package-private */
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public com.google.common.collect.ContiguousSet<C> subSetImpl(C r3, boolean r4, C r5, boolean r6) {
        /*
            r2 = this;
            int r0 = r3.compareTo(r5)
            if (r0 != 0) goto L_0x0012
            if (r4 != 0) goto L_0x0012
            if (r6 != 0) goto L_0x0012
            com.google.common.collect.EmptyContiguousSet r0 = new com.google.common.collect.EmptyContiguousSet
            com.google.common.collect.DiscreteDomain r1 = r2.domain
            r0.<init>(r1)
            return r0
        L_0x0012:
            com.google.common.collect.BoundType r0 = com.google.common.collect.BoundType.forBoolean(r4)
            com.google.common.collect.BoundType r1 = com.google.common.collect.BoundType.forBoolean(r6)
            com.google.common.collect.Range r0 = com.google.common.collect.Range.range(r3, r0, r5, r1)
            com.google.common.collect.ContiguousSet r0 = r2.intersectionInCurrentDomain(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularContiguousSet.subSetImpl(java.lang.Comparable, boolean, java.lang.Comparable, boolean):com.google.common.collect.ContiguousSet");
    }

    /* access modifiers changed from: package-private */
    public ContiguousSet<C> tailSetImpl(C fromElement, boolean inclusive) {
        return intersectionInCurrentDomain(Range.downTo(fromElement, BoundType.forBoolean(inclusive)));
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public int indexOf(Object target) {
        if (contains(target)) {
            return (int) this.domain.distance(first(), (Comparable) target);
        }
        return -1;
    }

    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(first()) {
            final C last = RegularContiguousSet.this.last();

            /* access modifiers changed from: protected */
            public C computeNext(C previous) {
                if (RegularContiguousSet.equalsOrThrow(previous, this.last)) {
                    return null;
                }
                return RegularContiguousSet.this.domain.next(previous);
            }
        };
    }

    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(last()) {
            final C first = RegularContiguousSet.this.first();

            /* access modifiers changed from: protected */
            public C computeNext(C previous) {
                if (RegularContiguousSet.equalsOrThrow(previous, this.first)) {
                    return null;
                }
                return RegularContiguousSet.this.domain.previous(previous);
            }
        };
    }

    /* access modifiers changed from: private */
    public static boolean equalsOrThrow(Comparable<?> left, @NullableDecl Comparable<?> right) {
        return right != null && Range.compareOrThrow(left, right) == 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    public C first() {
        return this.range.lowerBound.leastValueAbove(this.domain);
    }

    public C last() {
        return this.range.upperBound.greatestValueBelow(this.domain);
    }

    /* access modifiers changed from: package-private */
    public ImmutableList<C> createAsList() {
        return this.domain.supportsFastOffset ? new ImmutableAsList<C>() {
            /* access modifiers changed from: package-private */
            public ImmutableSortedSet<C> delegateCollection() {
                return RegularContiguousSet.this;
            }

            public C get(int i) {
                Preconditions.checkElementIndex(i, size());
                return RegularContiguousSet.this.domain.offset(RegularContiguousSet.this.first(), (long) i);
            }
        } : super.createAsList();
    }

    public int size() {
        long distance = this.domain.distance(first(), last());
        if (distance >= 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return ((int) distance) + 1;
    }

    public boolean contains(@NullableDecl Object object) {
        if (object == null) {
            return false;
        }
        try {
            return this.range.contains((Comparable) object);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> targets) {
        return Collections2.containsAllImpl(this, targets);
    }

    public boolean isEmpty() {
        return false;
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public com.google.common.collect.ContiguousSet<C> intersection(com.google.common.collect.ContiguousSet<C> r5) {
        /*
            r4 = this;
            com.google.common.base.Preconditions.checkNotNull(r5)
            com.google.common.collect.DiscreteDomain r0 = r4.domain
            com.google.common.collect.DiscreteDomain<C> r1 = r5.domain
            boolean r0 = r0.equals(r1)
            com.google.common.base.Preconditions.checkArgument(r0)
            boolean r0 = r5.isEmpty()
            if (r0 == 0) goto L_0x0015
            return r5
        L_0x0015:
            com.google.common.collect.Ordering r0 = com.google.common.collect.Ordering.natural()
            java.lang.Comparable r1 = r4.first()
            java.lang.Object r2 = r5.first()
            java.lang.Comparable r2 = (java.lang.Comparable) r2
            java.lang.Object r0 = r0.max(r1, r2)
            java.lang.Comparable r0 = (java.lang.Comparable) r0
            com.google.common.collect.Ordering r1 = com.google.common.collect.Ordering.natural()
            java.lang.Comparable r2 = r4.last()
            java.lang.Object r3 = r5.last()
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            java.lang.Object r1 = r1.min(r2, r3)
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            int r2 = r0.compareTo(r1)
            if (r2 > 0) goto L_0x004e
            com.google.common.collect.Range r2 = com.google.common.collect.Range.closed(r0, r1)
            com.google.common.collect.DiscreteDomain r3 = r4.domain
            com.google.common.collect.ContiguousSet r2 = com.google.common.collect.ContiguousSet.create(r2, r3)
            goto L_0x0055
        L_0x004e:
            com.google.common.collect.EmptyContiguousSet r2 = new com.google.common.collect.EmptyContiguousSet
            com.google.common.collect.DiscreteDomain r3 = r4.domain
            r2.<init>(r3)
        L_0x0055:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularContiguousSet.intersection(com.google.common.collect.ContiguousSet):com.google.common.collect.ContiguousSet");
    }

    public Range<C> range() {
        return range(BoundType.CLOSED, BoundType.CLOSED);
    }

    public Range<C> range(BoundType lowerBoundType, BoundType upperBoundType) {
        return Range.create(this.range.lowerBound.withLowerBoundType(lowerBoundType, this.domain), this.range.upperBound.withUpperBoundType(upperBoundType, this.domain));
    }

    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof RegularContiguousSet) {
            RegularContiguousSet<?> that = (RegularContiguousSet) object;
            if (this.domain.equals(that.domain)) {
                if (!first().equals(that.first()) || !last().equals(that.last())) {
                    return false;
                }
                return true;
            }
        }
        return super.equals(object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @GwtIncompatible
    private static final class SerializedForm<C extends Comparable> implements Serializable {
        final DiscreteDomain<C> domain;
        final Range<C> range;

        private SerializedForm(Range<C> range2, DiscreteDomain<C> domain2) {
            this.range = range2;
            this.domain = domain2;
        }

        private Object readResolve() {
            return new RegularContiguousSet(this.range, this.domain);
        }
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this.range, this.domain);
    }
}
