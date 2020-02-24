package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class ComparisonChain {
    /* access modifiers changed from: private */
    public static final ComparisonChain ACTIVE = new ComparisonChain() {
        public ComparisonChain compare(Comparable left, Comparable right) {
            return classify(left.compareTo(right));
        }

        public <T> ComparisonChain compare(@NullableDecl T left, @NullableDecl T right, Comparator<T> comparator) {
            return classify(comparator.compare(left, right));
        }

        public ComparisonChain compare(int left, int right) {
            return classify(Ints.compare(left, right));
        }

        public ComparisonChain compare(long left, long right) {
            return classify(Longs.compare(left, right));
        }

        public ComparisonChain compare(float left, float right) {
            return classify(Float.compare(left, right));
        }

        public ComparisonChain compare(double left, double right) {
            return classify(Double.compare(left, right));
        }

        public ComparisonChain compareTrueFirst(boolean left, boolean right) {
            return classify(Booleans.compare(right, left));
        }

        public ComparisonChain compareFalseFirst(boolean left, boolean right) {
            return classify(Booleans.compare(left, right));
        }

        /* access modifiers changed from: package-private */
        public ComparisonChain classify(int result) {
            if (result < 0) {
                return ComparisonChain.LESS;
            }
            return result > 0 ? ComparisonChain.GREATER : ComparisonChain.ACTIVE;
        }

        public int result() {
            return 0;
        }
    };
    /* access modifiers changed from: private */
    public static final ComparisonChain GREATER = new InactiveComparisonChain(1);
    /* access modifiers changed from: private */
    public static final ComparisonChain LESS = new InactiveComparisonChain(-1);

    public abstract ComparisonChain compare(double d, double d2);

    public abstract ComparisonChain compare(float f, float f2);

    public abstract ComparisonChain compare(int i, int i2);

    public abstract ComparisonChain compare(long j, long j2);

    public abstract ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2);

    public abstract <T> ComparisonChain compare(@NullableDecl T t, @NullableDecl T t2, Comparator<T> comparator);

    public abstract ComparisonChain compareFalseFirst(boolean z, boolean z2);

    public abstract ComparisonChain compareTrueFirst(boolean z, boolean z2);

    public abstract int result();

    private ComparisonChain() {
    }

    public static ComparisonChain start() {
        return ACTIVE;
    }

    private static final class InactiveComparisonChain extends ComparisonChain {
        final int result;

        InactiveComparisonChain(int result2) {
            super();
            this.result = result2;
        }

        public ComparisonChain compare(@NullableDecl Comparable left, @NullableDecl Comparable right) {
            return this;
        }

        public <T> ComparisonChain compare(@NullableDecl T t, @NullableDecl T t2, @NullableDecl Comparator<T> comparator) {
            return this;
        }

        public ComparisonChain compare(int left, int right) {
            return this;
        }

        public ComparisonChain compare(long left, long right) {
            return this;
        }

        public ComparisonChain compare(float left, float right) {
            return this;
        }

        public ComparisonChain compare(double left, double right) {
            return this;
        }

        public ComparisonChain compareTrueFirst(boolean left, boolean right) {
            return this;
        }

        public ComparisonChain compareFalseFirst(boolean left, boolean right) {
            return this;
        }

        public int result() {
            return this.result;
        }
    }

    @Deprecated
    public final ComparisonChain compare(Boolean left, Boolean right) {
        return compareFalseFirst(left.booleanValue(), right.booleanValue());
    }
}
