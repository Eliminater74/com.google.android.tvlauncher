package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    private static final long serialVersionUID = 0;
    @NullableDecl
    final C endpoint;

    /* access modifiers changed from: package-private */
    public abstract void describeAsLowerBound(StringBuilder sb);

    /* access modifiers changed from: package-private */
    public abstract void describeAsUpperBound(StringBuilder sb);

    /* access modifiers changed from: package-private */
    public abstract C greatestValueBelow(DiscreteDomain<C> discreteDomain);

    public abstract int hashCode();

    /* access modifiers changed from: package-private */
    public abstract boolean isLessThan(C c);

    /* access modifiers changed from: package-private */
    public abstract C leastValueAbove(DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: package-private */
    public abstract BoundType typeAsLowerBound();

    /* access modifiers changed from: package-private */
    public abstract BoundType typeAsUpperBound();

    /* access modifiers changed from: package-private */
    public abstract Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: package-private */
    public abstract Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain);

    Cut(@NullableDecl C endpoint2) {
        this.endpoint = endpoint2;
    }

    /* access modifiers changed from: package-private */
    public Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    public int compareTo(Cut cut) {
        if (cut == belowAll()) {
            return 1;
        }
        if (cut == aboveAll()) {
            return -1;
        }
        int result = Range.compareOrThrow(this.endpoint, cut.endpoint);
        if (result != 0) {
            return result;
        }
        return Booleans.compare(this instanceof AboveValue, cut instanceof AboveValue);
    }

    /* access modifiers changed from: package-private */
    public C endpoint() {
        return this.endpoint;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Cut) {
            try {
                if (compareTo((Cut) ((Cut) obj)) == 0) {
                    return true;
                }
                return false;
            } catch (ClassCastException e) {
            }
        }
        return false;
    }

    static <C extends Comparable> Cut<C> belowAll() {
        return BelowAll.INSTANCE;
    }

    private static final class BelowAll extends Cut<Comparable<?>> {
        /* access modifiers changed from: private */
        public static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0;

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return compareTo((Cut<Comparable<?>>) ((Cut) obj));
        }

        private BelowAll() {
            super(null);
        }

        /* access modifiers changed from: package-private */
        public Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        /* access modifiers changed from: package-private */
        public boolean isLessThan(Comparable<?> comparable) {
            return true;
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsUpperBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: package-private */
        public Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: package-private */
        public Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: package-private */
        public void describeAsLowerBound(StringBuilder sb) {
            sb.append("(-∞");
        }

        /* access modifiers changed from: package-private */
        public void describeAsUpperBound(StringBuilder sb) {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
            return domain.minValue();
        }

        /* access modifiers changed from: package-private */
        public Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> domain) {
            try {
                return Cut.belowValue(domain.minValue());
            } catch (NoSuchElementException e) {
                return this;
            }
        }

        public int compareTo(Cut<Comparable<?>> o) {
            return o == this ? 0 : -1;
        }

        public int hashCode() {
            return System.identityHashCode(this);
        }

        public String toString() {
            return "-∞";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    static <C extends Comparable> Cut<C> aboveAll() {
        return AboveAll.INSTANCE;
    }

    private static final class AboveAll extends Cut<Comparable<?>> {
        /* access modifiers changed from: private */
        public static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0;

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return compareTo((Cut<Comparable<?>>) ((Cut) obj));
        }

        private AboveAll() {
            super(null);
        }

        /* access modifiers changed from: package-private */
        public Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        /* access modifiers changed from: package-private */
        public boolean isLessThan(Comparable<?> comparable) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsLowerBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: package-private */
        public Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: package-private */
        public Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: package-private */
        public void describeAsLowerBound(StringBuilder sb) {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public void describeAsUpperBound(StringBuilder sb) {
            sb.append("+∞)");
        }

        /* access modifiers changed from: package-private */
        public Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
            return domain.maxValue();
        }

        public int compareTo(Cut<Comparable<?>> o) {
            return o == this ? 0 : 1;
        }

        public int hashCode() {
            return System.identityHashCode(this);
        }

        public String toString() {
            return "+∞";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    static <C extends Comparable> Cut<C> belowValue(C endpoint2) {
        return new BelowValue(endpoint2);
    }

    private static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        BelowValue(C endpoint) {
            super((Comparable) Preconditions.checkNotNull(endpoint));
        }

        /* access modifiers changed from: package-private */
        public boolean isLessThan(C value) {
            return Range.compareOrThrow(this.endpoint, value) <= 0;
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }

        /* access modifiers changed from: package-private */
        public Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = C15351.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                return this;
            }
            if (i == 2) {
                C previous = domain.previous(this.endpoint);
                return previous == null ? Cut.belowAll() : new AboveValue(previous);
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = C15351.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                C previous = domain.previous(this.endpoint);
                return previous == null ? Cut.aboveAll() : new AboveValue(previous);
            } else if (i == 2) {
                return this;
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public void describeAsLowerBound(StringBuilder sb) {
            sb.append('[');
            sb.append(this.endpoint);
        }

        /* access modifiers changed from: package-private */
        public void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(')');
        }

        /* access modifiers changed from: package-private */
        public C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return this.endpoint;
        }

        /* access modifiers changed from: package-private */
        public C greatestValueBelow(DiscreteDomain<C> domain) {
            return domain.previous(this.endpoint);
        }

        public int hashCode() {
            return this.endpoint.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.endpoint);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 2);
            sb.append("\\");
            sb.append(valueOf);
            sb.append("/");
            return sb.toString();
        }
    }

    /* renamed from: com.google.common.collect.Cut$1 */
    static /* synthetic */ class C15351 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType = new int[BoundType.values().length];

        static {
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.CLOSED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static <C extends Comparable> Cut<C> aboveValue(C endpoint2) {
        return new AboveValue(endpoint2);
    }

    private static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        AboveValue(C endpoint) {
            super((Comparable) Preconditions.checkNotNull(endpoint));
        }

        /* access modifiers changed from: package-private */
        public boolean isLessThan(C value) {
            return Range.compareOrThrow(this.endpoint, value) < 0;
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }

        /* access modifiers changed from: package-private */
        public BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }

        /* access modifiers changed from: package-private */
        public Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = C15351.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                C next = domain.next(this.endpoint);
                return next == null ? Cut.belowAll() : belowValue(next);
            } else if (i == 2) {
                return this;
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = C15351.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                return this;
            }
            if (i == 2) {
                C next = domain.next(this.endpoint);
                return next == null ? Cut.aboveAll() : belowValue(next);
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public void describeAsLowerBound(StringBuilder sb) {
            sb.append('(');
            sb.append(this.endpoint);
        }

        /* access modifiers changed from: package-private */
        public void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(']');
        }

        /* access modifiers changed from: package-private */
        public C leastValueAbove(DiscreteDomain<C> domain) {
            return domain.next(this.endpoint);
        }

        /* access modifiers changed from: package-private */
        public C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return this.endpoint;
        }

        /* access modifiers changed from: package-private */
        public Cut<C> canonical(DiscreteDomain<C> domain) {
            C next = leastValueAbove(domain);
            return next != null ? belowValue(next) : Cut.aboveAll();
        }

        public int hashCode() {
            return this.endpoint.hashCode() ^ -1;
        }

        public String toString() {
            String valueOf = String.valueOf(this.endpoint);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 2);
            sb.append("/");
            sb.append(valueOf);
            sb.append("\\");
            return sb.toString();
        }
    }
}
