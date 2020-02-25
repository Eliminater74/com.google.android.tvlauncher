package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@GwtCompatible
final class TopKSelector<T> {
    private final T[] buffer;
    private final Comparator<? super T> comparator;
    /* renamed from: k */
    private final int f174k;
    private int bufferSize;
    @NullableDecl
    private T threshold;

    private TopKSelector(Comparator<? super T> comparator2, int k) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator2, "comparator");
        this.f174k = k;
        Preconditions.checkArgument(k >= 0, "k must be nonnegative, was %s", k);
        this.buffer = new Object[(k * 2)];
        this.bufferSize = 0;
        this.threshold = null;
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int k) {
        return least(k, Ordering.natural());
    }

    public static <T> TopKSelector<T> least(int k, Comparator<? super T> comparator2) {
        return new TopKSelector<>(comparator2, k);
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int k) {
        return greatest(k, Ordering.natural());
    }

    public static <T> TopKSelector<T> greatest(int k, Comparator<? super T> comparator2) {
        return new TopKSelector<>(Ordering.from(comparator2).reverse(), k);
    }

    public void offer(@NullableDecl T elem) {
        int i = this.f174k;
        if (i != 0) {
            int i2 = this.bufferSize;
            if (i2 == 0) {
                this.buffer[0] = elem;
                this.threshold = elem;
                this.bufferSize = 1;
            } else if (i2 < i) {
                T[] tArr = this.buffer;
                this.bufferSize = i2 + 1;
                tArr[i2] = elem;
                if (this.comparator.compare(elem, this.threshold) > 0) {
                    this.threshold = elem;
                }
            } else if (this.comparator.compare(elem, this.threshold) < 0) {
                T[] tArr2 = this.buffer;
                int i3 = this.bufferSize;
                this.bufferSize = i3 + 1;
                tArr2[i3] = elem;
                if (this.bufferSize == this.f174k * 2) {
                    trim();
                }
            }
        }
    }

    private void trim() {
        int left = 0;
        int right = (this.f174k * 2) - 1;
        int minThresholdPosition = 0;
        int iterations = 0;
        int maxIterations = IntMath.log2(right - 0, RoundingMode.CEILING) * 3;
        while (true) {
            if (left < right) {
                int pivotNewIndex = partition(left, right, ((left + right) + 1) >>> 1);
                int i = this.f174k;
                if (pivotNewIndex <= i) {
                    if (pivotNewIndex >= i) {
                        break;
                    }
                    left = Math.max(pivotNewIndex, left + 1);
                    minThresholdPosition = pivotNewIndex;
                } else {
                    right = pivotNewIndex - 1;
                }
                iterations++;
                if (iterations >= maxIterations) {
                    Arrays.sort(this.buffer, left, right, this.comparator);
                    break;
                }
            } else {
                break;
            }
        }
        this.bufferSize = this.f174k;
        this.threshold = this.buffer[minThresholdPosition];
        for (int i2 = minThresholdPosition + 1; i2 < this.f174k; i2++) {
            if (this.comparator.compare(this.buffer[i2], this.threshold) > 0) {
                this.threshold = this.buffer[i2];
            }
        }
    }

    private int partition(int left, int right, int pivotIndex) {
        T[] tArr = this.buffer;
        T pivotValue = tArr[pivotIndex];
        tArr[pivotIndex] = tArr[right];
        int pivotNewIndex = left;
        for (int i = left; i < right; i++) {
            if (this.comparator.compare(this.buffer[i], pivotValue) < 0) {
                swap(pivotNewIndex, i);
                pivotNewIndex++;
            }
        }
        T[] tArr2 = this.buffer;
        tArr2[right] = tArr2[pivotNewIndex];
        tArr2[pivotNewIndex] = pivotValue;
        return pivotNewIndex;
    }

    private void swap(int i, int j) {
        T[] tArr = this.buffer;
        T tmp = tArr[i];
        tArr[i] = tArr[j];
        tArr[j] = tmp;
    }

    public void offerAll(Iterable iterable) {
        offerAll(iterable.iterator());
    }

    public void offerAll(Iterator it) {
        while (it.hasNext()) {
            offer(it.next());
        }
    }

    public List<T> topK() {
        Arrays.sort(this.buffer, 0, this.bufferSize, this.comparator);
        int i = this.bufferSize;
        int i2 = this.f174k;
        if (i > i2) {
            T[] tArr = this.buffer;
            Arrays.fill(tArr, i2, tArr.length, (Object) null);
            int i3 = this.f174k;
            this.bufferSize = i3;
            this.threshold = this.buffer[i3 - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
    }
}
