package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

@GwtCompatible(emulated = true)
public final class DoubleMath {
    private static final double LN_2 = Math.log(2.0d);
    @VisibleForTesting
    static final int MAX_FACTORIAL = 170;
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9d;
    private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18d;
    private static final double MIN_INT_AS_DOUBLE = -2.147483648E9d;
    private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18d;
    @VisibleForTesting
    static final double[] everySixteenthFactorial = {1.0d, 2.0922789888E13d, 2.631308369336935E35d, 1.2413915592536073E61d, 1.2688693218588417E89d, 7.156945704626381E118d, 9.916779348709496E149d, 1.974506857221074E182d, 3.856204823625804E215d, 5.5502938327393044E249d, 4.7147236359920616E284d};

    @GwtIncompatible
    static double roundIntermediate(double x, RoundingMode mode) {
        if (DoubleUtils.isFinite(x)) {
            switch (C17411.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
                case 1:
                    MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(x));
                    return x;
                case 2:
                    if (x >= LN_2 || isMathematicalInteger(x)) {
                        return x;
                    }
                    return (double) (((long) x) - 1);
                case 3:
                    if (x <= LN_2 || isMathematicalInteger(x)) {
                        return x;
                    }
                    return (double) (((long) x) + 1);
                case 4:
                    return x;
                case 5:
                    if (isMathematicalInteger(x)) {
                        return x;
                    }
                    return (double) (((long) x) + ((long) (x > LN_2 ? 1 : -1)));
                case 6:
                    return Math.rint(x);
                case 7:
                    double z = Math.rint(x);
                    if (Math.abs(x - z) == 0.5d) {
                        return Math.copySign(0.5d, x) + x;
                    }
                    return z;
                case 8:
                    double z2 = Math.rint(x);
                    if (Math.abs(x - z2) == 0.5d) {
                        return x;
                    }
                    return z2;
                default:
                    throw new AssertionError();
            }
        } else {
            throw new ArithmeticException("input is infinite or NaN");
        }
    }

    /* renamed from: com.google.common.math.DoubleMath$1 */
    static /* synthetic */ class C17411 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode = new int[RoundingMode.values().length];

        static {
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    @GwtIncompatible
    public static int roundToInt(double x, RoundingMode mode) {
        double z = roundIntermediate(x, mode);
        boolean z2 = true;
        boolean z3 = z > -2.147483649E9d;
        if (z >= 2.147483648E9d) {
            z2 = false;
        }
        MathPreconditions.checkInRangeForRoundingInputs(z2 & z3, x, mode);
        return (int) z;
    }

    @GwtIncompatible
    public static long roundToLong(double x, RoundingMode mode) {
        double z = roundIntermediate(x, mode);
        boolean z2 = true;
        boolean z3 = MIN_LONG_AS_DOUBLE - z < 1.0d;
        if (z >= MAX_LONG_AS_DOUBLE_PLUS_ONE) {
            z2 = false;
        }
        MathPreconditions.checkInRangeForRoundingInputs(z3 & z2, x, mode);
        return (long) z;
    }

    @GwtIncompatible
    public static BigInteger roundToBigInteger(double x, RoundingMode mode) {
        double x2 = roundIntermediate(x, mode);
        boolean z = true;
        boolean z2 = MIN_LONG_AS_DOUBLE - x2 < 1.0d;
        if (x2 >= MAX_LONG_AS_DOUBLE_PLUS_ONE) {
            z = false;
        }
        if (z2 && z) {
            return BigInteger.valueOf((long) x2);
        }
        BigInteger result = BigInteger.valueOf(DoubleUtils.getSignificand(x2)).shiftLeft(Math.getExponent(x2) - 52);
        return x2 < LN_2 ? result.negate() : result;
    }

    @GwtIncompatible
    public static boolean isPowerOfTwo(double x) {
        if (x <= LN_2 || !DoubleUtils.isFinite(x)) {
            return false;
        }
        long significand = DoubleUtils.getSignificand(x);
        if (((significand - 1) & significand) == 0) {
            return true;
        }
        return false;
    }

    public static double log2(double x) {
        return Math.log(x) / LN_2;
    }

    @GwtIncompatible
    public static int log2(double x, RoundingMode mode) {
        boolean increment = false;
        Preconditions.checkArgument(x > LN_2 && DoubleUtils.isFinite(x), "x must be positive and finite");
        int exponent = Math.getExponent(x);
        if (!DoubleUtils.isNormal(x)) {
            return log2(4.503599627370496E15d * x, mode) - 52;
        }
        switch (C17411.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
            case 2:
                increment = false;
                break;
            case 3:
                increment = !isPowerOfTwo(x);
                break;
            case 4:
                if (exponent < 0) {
                    increment = true;
                }
                increment &= true ^ isPowerOfTwo(x);
                break;
            case 5:
                if (exponent >= 0) {
                    increment = true;
                }
                increment &= true ^ isPowerOfTwo(x);
                break;
            case 6:
            case 7:
            case 8:
                double xScaled = DoubleUtils.scaleNormalize(x);
                if (xScaled * xScaled > 2.0d) {
                    increment = true;
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        return increment ? exponent + 1 : exponent;
    }

    @GwtIncompatible
    public static boolean isMathematicalInteger(double x) {
        return DoubleUtils.isFinite(x) && (x == LN_2 || 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(x)) <= Math.getExponent(x));
    }

    public static double factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double accum = 1.0d;
        for (int i = (n & -16) + 1; i <= n; i++) {
            double d = (double) i;
            Double.isNaN(d);
            accum *= d;
        }
        return everySixteenthFactorial[n >> 4] * accum;
    }

    public static boolean fuzzyEquals(double a, double b, double tolerance) {
        MathPreconditions.checkNonNegative("tolerance", tolerance);
        return Math.copySign(a - b, 1.0d) <= tolerance || a == b || (Double.isNaN(a) && Double.isNaN(b));
    }

    public static int fuzzyCompare(double a, double b, double tolerance) {
        if (fuzzyEquals(a, b, tolerance)) {
            return 0;
        }
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        return Booleans.compare(Double.isNaN(a), Double.isNaN(b));
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(double... values) {
        Preconditions.checkArgument(values.length > 0, "Cannot take mean of 0 values");
        long count = 1;
        double mean = checkFinite(values[0]);
        for (int index = 1; index < values.length; index++) {
            checkFinite(values[index]);
            count++;
            double d = (double) count;
            Double.isNaN(d);
            mean += (values[index] - mean) / d;
        }
        return mean;
    }

    @Deprecated
    public static double mean(int... values) {
        Preconditions.checkArgument(values.length > 0, "Cannot take mean of 0 values");
        long sum = 0;
        for (int i : values) {
            sum += (long) i;
        }
        double d = (double) sum;
        double length = (double) values.length;
        Double.isNaN(d);
        Double.isNaN(length);
        return d / length;
    }

    @Deprecated
    public static double mean(long... values) {
        Preconditions.checkArgument(values.length > 0, "Cannot take mean of 0 values");
        long count = 1;
        double mean = (double) values[0];
        for (int index = 1; index < values.length; index++) {
            count++;
            double d = (double) values[index];
            Double.isNaN(d);
            double d2 = (double) count;
            Double.isNaN(d2);
            mean += (d - mean) / d2;
        }
        return mean;
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterable<? extends Number> values) {
        return mean(values.iterator());
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterator<? extends Number> values) {
        Preconditions.checkArgument(values.hasNext(), "Cannot take mean of 0 values");
        long count = 1;
        double mean = checkFinite(((Number) values.next()).doubleValue());
        while (values.hasNext()) {
            count++;
            double d = (double) count;
            Double.isNaN(d);
            mean += (checkFinite(((Number) values.next()).doubleValue()) - mean) / d;
        }
        return mean;
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    private static double checkFinite(double argument) {
        Preconditions.checkArgument(DoubleUtils.isFinite(argument));
        return argument;
    }

    private DoubleMath() {
    }
}
