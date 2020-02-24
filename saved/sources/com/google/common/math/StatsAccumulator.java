package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

@GwtIncompatible
@Beta
public final class StatsAccumulator {
    private long count = 0;
    private double max = Double.NaN;
    private double mean = 0.0d;
    private double min = Double.NaN;
    private double sumOfSquaresOfDeltas = 0.0d;

    public void add(double value) {
        long j = this.count;
        if (j == 0) {
            this.count = 1;
            this.mean = value;
            this.min = value;
            this.max = value;
            if (!Doubles.isFinite(value)) {
                this.sumOfSquaresOfDeltas = Double.NaN;
                return;
            }
            return;
        }
        this.count = j + 1;
        if (!Doubles.isFinite(value) || !Doubles.isFinite(this.mean)) {
            this.mean = calculateNewMeanNonFinite(this.mean, value);
            this.sumOfSquaresOfDeltas = Double.NaN;
        } else {
            double d = this.mean;
            double delta = value - d;
            double d2 = (double) this.count;
            Double.isNaN(d2);
            this.mean = d + (delta / d2);
            this.sumOfSquaresOfDeltas += (value - this.mean) * delta;
        }
        this.min = Math.min(this.min, value);
        this.max = Math.max(this.max, value);
    }

    public void addAll(Iterable<? extends Number> values) {
        for (Number value : values) {
            add(value.doubleValue());
        }
    }

    public void addAll(Iterator<? extends Number> values) {
        while (values.hasNext()) {
            add(((Number) values.next()).doubleValue());
        }
    }

    public void addAll(double... values) {
        for (double value : values) {
            add(value);
        }
    }

    public void addAll(int... values) {
        for (int value : values) {
            add((double) value);
        }
    }

    public void addAll(long... values) {
        for (long value : values) {
            add((double) value);
        }
    }

    public void addAll(Stats values) {
        if (values.count() != 0) {
            long j = this.count;
            if (j == 0) {
                this.count = values.count();
                this.mean = values.mean();
                this.sumOfSquaresOfDeltas = values.sumOfSquaresOfDeltas();
                this.min = values.min();
                this.max = values.max();
                return;
            }
            this.count = j + values.count();
            if (!Doubles.isFinite(this.mean) || !Doubles.isFinite(values.mean())) {
                this.mean = calculateNewMeanNonFinite(this.mean, values.mean());
                this.sumOfSquaresOfDeltas = Double.NaN;
            } else {
                double mean2 = values.mean();
                double d = this.mean;
                double delta = mean2 - d;
                double count2 = (double) values.count();
                Double.isNaN(count2);
                double d2 = (double) this.count;
                Double.isNaN(d2);
                this.mean = d + ((count2 * delta) / d2);
                double d3 = this.sumOfSquaresOfDeltas;
                double sumOfSquaresOfDeltas2 = values.sumOfSquaresOfDeltas();
                double count3 = (double) values.count();
                Double.isNaN(count3);
                this.sumOfSquaresOfDeltas = d3 + sumOfSquaresOfDeltas2 + ((values.mean() - this.mean) * delta * count3);
            }
            this.min = Math.min(this.min, values.min());
            this.max = Math.max(this.max, values.max());
        }
    }

    public Stats snapshot() {
        return new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
    }

    public long count() {
        return this.count;
    }

    public double mean() {
        Preconditions.checkState(this.count != 0);
        return this.mean;
    }

    public final double sum() {
        double d = this.mean;
        double d2 = (double) this.count;
        Double.isNaN(d2);
        return d * d2;
    }

    public final double populationVariance() {
        Preconditions.checkState(this.count != 0);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count == 1) {
            return 0.0d;
        }
        double ensureNonNegative = DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas);
        double d = (double) this.count;
        Double.isNaN(d);
        return ensureNonNegative / d;
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(populationVariance());
    }

    public final double sampleVariance() {
        Preconditions.checkState(this.count > 1);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        double ensureNonNegative = DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas);
        double d = (double) (this.count - 1);
        Double.isNaN(d);
        return ensureNonNegative / d;
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(sampleVariance());
    }

    public double min() {
        Preconditions.checkState(this.count != 0);
        return this.min;
    }

    public double max() {
        Preconditions.checkState(this.count != 0);
        return this.max;
    }

    /* access modifiers changed from: package-private */
    public double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    static double calculateNewMeanNonFinite(double previousMean, double value) {
        if (Doubles.isFinite(previousMean)) {
            return value;
        }
        if (Doubles.isFinite(value) || previousMean == value) {
            return previousMean;
        }
        return Double.NaN;
    }
}
