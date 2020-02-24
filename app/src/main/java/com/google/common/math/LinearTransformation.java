package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

@GwtIncompatible
@Beta
public abstract class LinearTransformation {
    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double d);

    public static LinearTransformationBuilder mapping(double x1, double y1) {
        Preconditions.checkArgument(DoubleUtils.isFinite(x1) && DoubleUtils.isFinite(y1));
        return new LinearTransformationBuilder(x1, y1);
    }

    public static final class LinearTransformationBuilder {

        /* renamed from: x1 */
        private final double f180x1;

        /* renamed from: y1 */
        private final double f181y1;

        private LinearTransformationBuilder(double x1, double y1) {
            this.f180x1 = x1;
            this.f181y1 = y1;
        }

        public LinearTransformation and(double x2, double y2) {
            boolean z = true;
            Preconditions.checkArgument(DoubleUtils.isFinite(x2) && DoubleUtils.isFinite(y2));
            double d = this.f180x1;
            if (x2 != d) {
                return withSlope((y2 - this.f181y1) / (x2 - d));
            }
            if (y2 == this.f181y1) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return new VerticalLinearTransformation(this.f180x1);
        }

        public LinearTransformation withSlope(double slope) {
            Preconditions.checkArgument(!Double.isNaN(slope));
            if (DoubleUtils.isFinite(slope)) {
                return new RegularLinearTransformation(slope, this.f181y1 - (this.f180x1 * slope));
            }
            return new VerticalLinearTransformation(this.f180x1);
        }
    }

    public static LinearTransformation vertical(double x) {
        Preconditions.checkArgument(DoubleUtils.isFinite(x));
        return new VerticalLinearTransformation(x);
    }

    public static LinearTransformation horizontal(double y) {
        Preconditions.checkArgument(DoubleUtils.isFinite(y));
        return new RegularLinearTransformation(0.0d, y);
    }

    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.INSTANCE;
    }

    private static final class RegularLinearTransformation extends LinearTransformation {
        @LazyInit
        LinearTransformation inverse;
        final double slope;
        final double yIntercept;

        RegularLinearTransformation(double slope2, double yIntercept2) {
            this.slope = slope2;
            this.yIntercept = yIntercept2;
            this.inverse = null;
        }

        RegularLinearTransformation(double slope2, double yIntercept2, LinearTransformation inverse2) {
            this.slope = slope2;
            this.yIntercept = yIntercept2;
            this.inverse = inverse2;
        }

        public boolean isVertical() {
            return false;
        }

        public boolean isHorizontal() {
            return this.slope == 0.0d;
        }

        public double slope() {
            return this.slope;
        }

        public double transform(double x) {
            return (this.slope * x) + this.yIntercept;
        }

        public LinearTransformation inverse() {
            LinearTransformation result = this.inverse;
            if (result != null) {
                return result;
            }
            LinearTransformation createInverse = createInverse();
            this.inverse = createInverse;
            return createInverse;
        }

        public String toString() {
            return String.format("y = %g * x + %g", Double.valueOf(this.slope), Double.valueOf(this.yIntercept));
        }

        private LinearTransformation createInverse() {
            double d = this.slope;
            if (d != 0.0d) {
                return new RegularLinearTransformation(1.0d / d, (this.yIntercept * -1.0d) / d, this);
            }
            return new VerticalLinearTransformation(this.yIntercept, this);
        }
    }

    private static final class VerticalLinearTransformation extends LinearTransformation {
        @LazyInit
        LinearTransformation inverse;

        /* renamed from: x */
        final double f182x;

        VerticalLinearTransformation(double x) {
            this.f182x = x;
            this.inverse = null;
        }

        VerticalLinearTransformation(double x, LinearTransformation inverse2) {
            this.f182x = x;
            this.inverse = inverse2;
        }

        public boolean isVertical() {
            return true;
        }

        public boolean isHorizontal() {
            return false;
        }

        public double slope() {
            throw new IllegalStateException();
        }

        public double transform(double x) {
            throw new IllegalStateException();
        }

        public LinearTransformation inverse() {
            LinearTransformation result = this.inverse;
            if (result != null) {
                return result;
            }
            LinearTransformation createInverse = createInverse();
            this.inverse = createInverse;
            return createInverse;
        }

        public String toString() {
            return String.format("x = %g", Double.valueOf(this.f182x));
        }

        private LinearTransformation createInverse() {
            return new RegularLinearTransformation(0.0d, this.f182x, this);
        }
    }

    private static final class NaNLinearTransformation extends LinearTransformation {
        static final NaNLinearTransformation INSTANCE = new NaNLinearTransformation();

        private NaNLinearTransformation() {
        }

        public boolean isVertical() {
            return false;
        }

        public boolean isHorizontal() {
            return false;
        }

        public double slope() {
            return Double.NaN;
        }

        public double transform(double x) {
            return Double.NaN;
        }

        public LinearTransformation inverse() {
            return this;
        }

        public String toString() {
            return "NaN";
        }
    }
}
