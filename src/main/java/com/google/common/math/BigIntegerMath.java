package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@GwtCompatible(emulated = true)
public final class BigIntegerMath {
    private static final double LN_10 = Math.log(10.0d);
    private static final double LN_2 = Math.log(2.0d);
    @VisibleForTesting
    static final BigInteger SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    @VisibleForTesting
    static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;

    @Beta
    public static BigInteger ceilingPowerOfTwo(BigInteger x) {
        return BigInteger.ZERO.setBit(log2(x, RoundingMode.CEILING));
    }

    @Beta
    public static BigInteger floorPowerOfTwo(BigInteger x) {
        return BigInteger.ZERO.setBit(log2(x, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger x) {
        Preconditions.checkNotNull(x);
        return x.signum() > 0 && x.getLowestSetBit() == x.bitLength() - 1;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int log2(BigInteger x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", (BigInteger) Preconditions.checkNotNull(x));
        int logFloor = x.bitLength() - 1;
        switch (C17401.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return isPowerOfTwo(x) ? logFloor : logFloor + 1;
            case 6:
            case 7:
            case 8:
                if (logFloor >= 256) {
                    return x.pow(2).bitLength() + -1 < (logFloor * 2) + 1 ? logFloor : logFloor + 1;
                }
                if (x.compareTo(SQRT2_PRECOMPUTED_BITS.shiftRight(256 - logFloor)) <= 0) {
                    return logFloor;
                }
                return logFloor + 1;
            default:
                throw new AssertionError();
        }
        return logFloor;
    }

    /* renamed from: com.google.common.math.BigIntegerMath$1 */
    static /* synthetic */ class C17401 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode = new int[RoundingMode.values().length];

        static {
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* JADX INFO: Multiple debug info for r3v0 int: [D('floorLog' int), D('nextPow' java.math.BigInteger)] */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static int log10(BigInteger x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        if (fitsInLong(x)) {
            return LongMath.log10(x.longValue(), mode);
        }
        double log2 = (double) log2(x, RoundingMode.FLOOR);
        double d = LN_2;
        Double.isNaN(log2);
        int approxLog10 = (int) ((log2 * d) / LN_10);
        BigInteger approxPow = BigInteger.TEN.pow(approxLog10);
        int approxCmp = approxPow.compareTo(x);
        if (approxCmp > 0) {
            do {
                approxLog10--;
                approxPow = approxPow.divide(BigInteger.TEN);
                approxCmp = approxPow.compareTo(x);
            } while (approxCmp > 0);
        } else {
            BigInteger nextPow = BigInteger.TEN.multiply(approxPow);
            int nextCmp = nextPow.compareTo(x);
            while (nextCmp <= 0) {
                approxLog10++;
                approxPow = nextPow;
                approxCmp = nextCmp;
                nextPow = BigInteger.TEN.multiply(approxPow);
                nextCmp = nextPow.compareTo(x);
            }
        }
        int floorLog = approxLog10;
        BigInteger floorPow = approxPow;
        int floorCmp = approxCmp;
        switch (C17401.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(floorCmp == 0);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return floorPow.equals(x) ? floorLog : floorLog + 1;
            case 6:
            case 7:
            case 8:
                return x.pow(2).compareTo(floorPow.pow(2).multiply(BigInteger.TEN)) <= 0 ? floorLog : floorLog + 1;
            default:
                throw new AssertionError();
        }
        return floorLog;
    }

    /* JADX INFO: Multiple debug info for r1v4 int: [D('halfSquare' java.math.BigInteger), D('sqrtFloorInt' int)] */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static BigInteger sqrt(BigInteger x, RoundingMode mode) {
        MathPreconditions.checkNonNegative("x", x);
        if (fitsInLong(x)) {
            return BigInteger.valueOf(LongMath.sqrt(x.longValue(), mode));
        }
        BigInteger sqrtFloor = sqrtFloor(x);
        switch (C17401.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(sqrtFloor.pow(2).equals(x));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                int sqrtFloorInt = sqrtFloor.intValue();
                return sqrtFloorInt * sqrtFloorInt == x.intValue() && sqrtFloor.pow(2).equals(x) ? sqrtFloor : sqrtFloor.add(BigInteger.ONE);
            case 6:
            case 7:
            case 8:
                return sqrtFloor.pow(2).add(sqrtFloor).compareTo(x) >= 0 ? sqrtFloor : sqrtFloor.add(BigInteger.ONE);
            default:
                throw new AssertionError();
        }
        return sqrtFloor;
    }

    @GwtIncompatible
    private static BigInteger sqrtFloor(BigInteger x) {
        BigInteger sqrt0;
        BigInteger sqrt02;
        int log2 = log2(x, RoundingMode.FLOOR);
        if (log2 < 1023) {
            sqrt0 = sqrtApproxWithDoubles(x);
        } else {
            int shift = (log2 - 52) & -2;
            sqrt0 = sqrtApproxWithDoubles(x.shiftRight(shift)).shiftLeft(shift >> 1);
        }
        BigInteger sqrt1 = sqrt0.add(x.divide(sqrt0)).shiftRight(1);
        if (sqrt0.equals(sqrt1)) {
            return sqrt0;
        }
        do {
            sqrt02 = sqrt1;
            sqrt1 = sqrt02.add(x.divide(sqrt02)).shiftRight(1);
        } while (sqrt1.compareTo(sqrt02) < 0);
        return sqrt02;
    }

    @GwtIncompatible
    private static BigInteger sqrtApproxWithDoubles(BigInteger x) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(x)), RoundingMode.HALF_EVEN);
    }

    @GwtIncompatible
    public static BigInteger divide(BigInteger p, BigInteger q, RoundingMode mode) {
        return new BigDecimal(p).divide(new BigDecimal(q), 0, mode).toBigIntegerExact();
    }

    public static BigInteger factorial(int n) {
        int i = n;
        MathPreconditions.checkNonNegative("n", i);
        if (i < LongMath.factorials.length) {
            return BigInteger.valueOf(LongMath.factorials[i]);
        }
        ArrayList<BigInteger> bignums = new ArrayList<>(IntMath.divide(IntMath.log2(i, RoundingMode.CEILING) * i, 64, RoundingMode.CEILING));
        int startingNumber = LongMath.factorials.length;
        long product = LongMath.factorials[startingNumber - 1];
        int shift = Long.numberOfTrailingZeros(product);
        long product2 = product >> shift;
        int productBits = LongMath.log2(product2, RoundingMode.FLOOR) + 1;
        int bits = LongMath.log2((long) startingNumber, RoundingMode.FLOOR) + 1;
        int nextPowerOfTwo = 1 << (bits - 1);
        for (long num = (long) startingNumber; num <= ((long) i); num++) {
            if ((((long) nextPowerOfTwo) & num) != 0) {
                nextPowerOfTwo <<= 1;
                bits++;
            }
            int tz = Long.numberOfTrailingZeros(num);
            long normalizedNum = num >> tz;
            shift += tz;
            if ((bits - tz) + productBits >= 64) {
                bignums.add(BigInteger.valueOf(product2));
                product2 = 1;
            }
            product2 *= normalizedNum;
            productBits = LongMath.log2(product2, RoundingMode.FLOOR) + 1;
        }
        if (product2 > 1) {
            bignums.add(BigInteger.valueOf(product2));
        }
        return listProduct(bignums).shiftLeft(shift);
    }

    static BigInteger listProduct(List<BigInteger> nums) {
        return listProduct(nums, 0, nums.size());
    }

    static BigInteger listProduct(List<BigInteger> nums, int start, int end) {
        int i = end - start;
        if (i == 0) {
            return BigInteger.ONE;
        }
        if (i == 1) {
            return nums.get(start);
        }
        if (i == 2) {
            return nums.get(start).multiply(nums.get(start + 1));
        }
        if (i == 3) {
            return nums.get(start).multiply(nums.get(start + 1)).multiply(nums.get(start + 2));
        }
        int m = (end + start) >>> 1;
        return listProduct(nums, start, m).multiply(listProduct(nums, m, end));
    }

    public static BigInteger binomial(int n, int k) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", k);
        Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", k, n);
        if (k > (n >> 1)) {
            k = n - k;
        }
        if (k < LongMath.biggestBinomials.length && n <= LongMath.biggestBinomials[k]) {
            return BigInteger.valueOf(LongMath.binomial(n, k));
        }
        BigInteger accum = BigInteger.ONE;
        long numeratorAccum = (long) n;
        long denominatorAccum = 1;
        int bits = LongMath.log2((long) n, RoundingMode.CEILING);
        int numeratorBits = bits;
        for (int i = 1; i < k; i++) {
            int p = n - i;
            int q = i + 1;
            if (numeratorBits + bits >= 63) {
                accum = accum.multiply(BigInteger.valueOf(numeratorAccum)).divide(BigInteger.valueOf(denominatorAccum));
                numeratorAccum = (long) p;
                denominatorAccum = (long) q;
                numeratorBits = bits;
            } else {
                numeratorAccum *= (long) p;
                denominatorAccum *= (long) q;
                numeratorBits += bits;
            }
        }
        return accum.multiply(BigInteger.valueOf(numeratorAccum)).divide(BigInteger.valueOf(denominatorAccum));
    }

    @GwtIncompatible
    static boolean fitsInLong(BigInteger x) {
        return x.bitLength() <= 63;
    }

    private BigIntegerMath() {
    }
}
