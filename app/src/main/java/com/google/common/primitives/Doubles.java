package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.regex.Pattern;

@GwtCompatible(emulated = true)
public final class Doubles {
    public static final int BYTES = 8;
    @GwtIncompatible
    static final Pattern FLOATING_POINT_PATTERN = fpPattern();

    private Doubles() {
    }

    public static int hashCode(double value) {
        return Double.valueOf(value).hashCode();
    }

    public static int compare(double a, double b) {
        return Double.compare(a, b);
    }

    public static boolean isFinite(double value) {
        return Double.NEGATIVE_INFINITY < value && value < Double.POSITIVE_INFINITY;
    }

    public static boolean contains(double[] array, double target) {
        for (double value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(double[] array, double target) {
        return indexOf(array, target, 0, array.length);
    }

    /* access modifiers changed from: private */
    public static int indexOf(double[] array, double target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(double[] array, double[] target) {
        Preconditions.checkNotNull(array, "array");
        Preconditions.checkNotNull(target, "target");
        if (target.length == 0) {
            return 0;
        }
        int i = 0;
        while (i < (array.length - target.length) + 1) {
            int j = 0;
            while (j < target.length) {
                if (array[i + j] != target[j]) {
                    i++;
                } else {
                    j++;
                }
            }
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(double[] array, double target) {
        return lastIndexOf(array, target, 0, array.length);
    }

    /* access modifiers changed from: private */
    public static int lastIndexOf(double[] array, double target, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static double min(double... array) {
        Preconditions.checkArgument(array.length > 0);
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            min = Math.min(min, array[i]);
        }
        return min;
    }

    public static double max(double... array) {
        Preconditions.checkArgument(array.length > 0);
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        return max;
    }

    @Beta
    public static double constrainToRange(double value, double min, double max) {
        Preconditions.checkArgument(min <= max, "min (%s) must be less than or equal to max (%s)", Double.valueOf(min), Double.valueOf(max));
        return Math.min(Math.max(value, min), max);
    }

    public static double[] concat(double[]... arrays) {
        int length = 0;
        for (double[] array : arrays) {
            length += array.length;
        }
        double[] result = new double[length];
        int pos = 0;
        for (double[] array2 : arrays) {
            System.arraycopy(array2, 0, result, pos, array2.length);
            pos += array2.length;
        }
        return result;
    }

    @Beta
    public static Converter<String, Double> stringConverter() {
        return DoubleConverter.INSTANCE;
    }

    public static double[] ensureCapacity(double[] array, int minLength, int padding) {
        boolean z = true;
        Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
        if (padding < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", padding);
        return array.length < minLength ? Arrays.copyOf(array, minLength + padding) : array;
    }

    public static String join(String separator, double... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * 12);
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(array[i]);
        }
        return builder.toString();
    }

    public static Comparator<double[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static void sortDescending(double[] array) {
        Preconditions.checkNotNull(array);
        sortDescending(array, 0, array.length);
    }

    public static void sortDescending(double[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        Arrays.sort(array, fromIndex, toIndex);
        reverse(array, fromIndex, toIndex);
    }

    public static void reverse(double[] array) {
        Preconditions.checkNotNull(array);
        reverse(array, 0, array.length);
    }

    public static void reverse(double[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        int i = fromIndex;
        for (int j = toIndex - 1; i < j; j--) {
            double tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
            i++;
        }
    }

    public static double[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof DoubleArrayAsList) {
            return ((DoubleArrayAsList) collection).toDoubleArray();
        }
        Object[] boxedArray = collection.toArray();
        int len = boxedArray.length;
        double[] array = new double[len];
        for (int i = 0; i < len; i++) {
            array[i] = ((Number) Preconditions.checkNotNull(boxedArray[i])).doubleValue();
        }
        return array;
    }

    public static List<Double> asList(double... backingArray) {
        if (backingArray.length == 0) {
            return Collections.emptyList();
        }
        return new DoubleArrayAsList(backingArray);
    }

    @GwtIncompatible
    private static Pattern fpPattern() {
        String completeDec = String.valueOf("(?:\\d+#(?:\\.\\d*#)?|\\.\\d+#)").concat("(?:[eE][+-]?\\d+#)?[fFdD]?");
        StringBuilder sb = new StringBuilder(String.valueOf("(?:[0-9a-fA-F]+#(?:\\.[0-9a-fA-F]*#)?|\\.[0-9a-fA-F]+#)").length() + 25);
        sb.append("0[xX]");
        sb.append("(?:[0-9a-fA-F]+#(?:\\.[0-9a-fA-F]*#)?|\\.[0-9a-fA-F]+#)");
        sb.append("[pP][+-]?\\d+#[fFdD]?");
        String completeHex = sb.toString();
        StringBuilder sb2 = new StringBuilder(String.valueOf(completeDec).length() + 23 + String.valueOf(completeHex).length());
        sb2.append("[+-]?(?:NaN|Infinity|");
        sb2.append(completeDec);
        sb2.append("|");
        sb2.append(completeHex);
        sb2.append(")");
        return Pattern.compile(sb2.toString().replace("#", "+"));
    }

    @NullableDecl
    @GwtIncompatible
    @Beta
    public static Double tryParse(String string) {
        if (!FLOATING_POINT_PATTERN.matcher(string).matches()) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(string));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private enum LexicographicalComparator implements Comparator<double[]> {
        INSTANCE;

        public int compare(double[] left, double[] right) {
            int minLength = Math.min(left.length, right.length);
            for (int i = 0; i < minLength; i++) {
                int result = Double.compare(left[i], right[i]);
                if (result != 0) {
                    return result;
                }
            }
            return left.length - right.length;
        }

        public String toString() {
            return "Doubles.lexicographicalComparator()";
        }
    }

    private static final class DoubleConverter extends Converter<String, Double> implements Serializable {
        static final DoubleConverter INSTANCE = new DoubleConverter();
        private static final long serialVersionUID = 1;

        private DoubleConverter() {
        }

        /* access modifiers changed from: protected */
        public Double doForward(String value) {
            return Double.valueOf(value);
        }

        /* access modifiers changed from: protected */
        public String doBackward(Double value) {
            return value.toString();
        }

        public String toString() {
            return "Doubles.stringConverter()";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    @GwtCompatible
    private static class DoubleArrayAsList extends AbstractList<Double> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        final double[] array;
        final int end;
        final int start;

        DoubleArrayAsList(double[] array2) {
            this(array2, 0, array2.length);
        }

        DoubleArrayAsList(double[] array2, int start2, int end2) {
            this.array = array2;
            this.start = start2;
            this.end = end2;
        }

        public int size() {
            return this.end - this.start;
        }

        public boolean isEmpty() {
            return false;
        }

        public Double get(int index) {
            Preconditions.checkElementIndex(index, size());
            return Double.valueOf(this.array[this.start + index]);
        }

        public boolean contains(Object target) {
            return (target instanceof Double) && Doubles.indexOf(this.array, ((Double) target).doubleValue(), this.start, this.end) != -1;
        }

        public int indexOf(Object target) {
            int i;
            if (!(target instanceof Double) || (i = Doubles.indexOf(this.array, ((Double) target).doubleValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return i - this.start;
        }

        public int lastIndexOf(Object target) {
            int i;
            if (!(target instanceof Double) || (i = Doubles.lastIndexOf(this.array, ((Double) target).doubleValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return i - this.start;
        }

        public Double set(int index, Double element) {
            Preconditions.checkElementIndex(index, size());
            double[] dArr = this.array;
            int i = this.start;
            double oldValue = dArr[i + index];
            dArr[i + index] = ((Double) Preconditions.checkNotNull(element)).doubleValue();
            return Double.valueOf(oldValue);
        }

        public List<Double> subList(int fromIndex, int toIndex) {
            Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
            if (fromIndex == toIndex) {
                return Collections.emptyList();
            }
            double[] dArr = this.array;
            int i = this.start;
            return new DoubleArrayAsList(dArr, i + fromIndex, i + toIndex);
        }

        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof DoubleArrayAsList)) {
                return super.equals(object);
            }
            DoubleArrayAsList that = (DoubleArrayAsList) object;
            int size = size();
            if (that.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.array[this.start + i] != that.array[that.start + i]) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int result = 1;
            for (int i = this.start; i < this.end; i++) {
                result = (result * 31) + Doubles.hashCode(this.array[i]);
            }
            return result;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(size() * 12);
            builder.append('[');
            builder.append(this.array[this.start]);
            int i = this.start;
            while (true) {
                i++;
                if (i < this.end) {
                    builder.append(", ");
                    builder.append(this.array[i]);
                } else {
                    builder.append(']');
                    return builder.toString();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public double[] toDoubleArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }
    }
}
