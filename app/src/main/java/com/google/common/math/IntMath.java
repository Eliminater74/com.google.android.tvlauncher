package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.math.RoundingMode;

@GwtCompatible(emulated = true)
public final class IntMath {
    @VisibleForTesting
    static final int FLOOR_SQRT_MAX_INT = 46340;
    @VisibleForTesting
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
    @VisibleForTesting
    static final int MAX_SIGNED_POWER_OF_TWO = 1073741824;
    @VisibleForTesting
    static final int[] halfPowersOf10 = {3, 31, ClientAnalytics.LogRequest.LogSource.NAZDEEK_CAB_ANDROID_PRIMES_VALUE, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = {9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    @VisibleForTesting
    static final int[] powersOf10 = {1, 10, 100, 1000, 10000, DefaultOggSeeker.MATCH_BYTE_RANGE, 1000000, 10000000, 100000000, 1000000000};
    private static final int[] factorials = {1, 1, 2, 6, 24, 120, ClientAnalytics.LogRequest.LogSource.CARRIER_SETUP_VALUE, 5040, 40320, 362880, 3628800, 39916800, 479001600};
    @VisibleForTesting
    static int[] biggestBinomials = {Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, ClientAnalytics.LogRequest.LogSource.TANGO_CORE_VALUE, ClientAnalytics.LogRequest.LogSource.GMSCORE_ANDROID_PRIMES_VALUE, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    private IntMath() {
    }

    @Beta
    public static int ceilingPowerOfTwo(int x) {
        MathPreconditions.checkPositive("x", x);
        if (x <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(x - 1));
        }
        StringBuilder sb = new StringBuilder(58);
        sb.append("ceilingPowerOfTwo(");
        sb.append(x);
        sb.append(") not representable as an int");
        throw new ArithmeticException(sb.toString());
    }

    @Beta
    public static int floorPowerOfTwo(int x) {
        MathPreconditions.checkPositive("x", x);
        return Integer.highestOneBit(x);
    }

    public static boolean isPowerOfTwo(int x) {
        boolean z = false;
        boolean z2 = x > 0;
        if (((x - 1) & x) == 0) {
            z = true;
        }
        return z & z2;
    }

    @VisibleForTesting
    static int lessThanBranchFree(int x, int y) {
        return (((x - y) ^ -1) ^ -1) >>> 31;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int log2(int x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        switch (C17421.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(x - 1);
            case 6:
            case 7:
            case 8:
                int leadingZeros = Integer.numberOfLeadingZeros(x);
                return lessThanBranchFree(MAX_POWER_OF_SQRT2_UNSIGNED >>> leadingZeros, x) + (31 - leadingZeros);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static int log10(int x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        int logFloor = log10Floor(x);
        int floorPow = powersOf10[logFloor];
        switch (C17421.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(x == floorPow);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return lessThanBranchFree(floorPow, x) + logFloor;
            case 6:
            case 7:
            case 8:
                return lessThanBranchFree(halfPowersOf10[logFloor], x) + logFloor;
            default:
                throw new AssertionError();
        }
        return logFloor;
    }

    private static int log10Floor(int x) {
        byte b = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(x)];
        return b - lessThanBranchFree(x, powersOf10[b]);
    }

    @GwtIncompatible
    public static int pow(int b, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        if (b != -2) {
            if (b != -1) {
                if (b != 0) {
                    if (b == 1) {
                        return 1;
                    }
                    if (b != 2) {
                        int accum = 1;
                        while (k != 0) {
                            if (k == 1) {
                                return b * accum;
                            }
                            accum *= (k & 1) == 0 ? 1 : b;
                            b *= b;
                            k >>= 1;
                        }
                        return accum;
                    } else if (k < 32) {
                        return 1 << k;
                    } else {
                        return 0;
                    }
                } else if (k == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else if ((k & 1) == 0) {
                return 1;
            } else {
                return -1;
            }
        } else if (k < 32) {
            return (k & 1) == 0 ? 1 << k : -(1 << k);
        } else {
            return 0;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static int sqrt(int x, RoundingMode mode) {
        MathPreconditions.checkNonNegative("x", x);
        int sqrtFloor = sqrtFloor(x);
        switch (C17421.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(sqrtFloor * sqrtFloor == x);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return lessThanBranchFree(sqrtFloor * sqrtFloor, x) + sqrtFloor;
            case 6:
            case 7:
            case 8:
                return lessThanBranchFree((sqrtFloor * sqrtFloor) + sqrtFloor, x) + sqrtFloor;
            default:
                throw new AssertionError();
        }
        return sqrtFloor;
    }

    private static int sqrtFloor(int x) {
        return (int) Math.sqrt((double) x);
    }

    public static int divide(int p, int q, RoundingMode mode) {
        Preconditions.checkNotNull(mode);
        if (q != 0) {
            int div = p / q;
            int rem = p - (q * div);
            if (rem == 0) {
                return div;
            }
            boolean increment = true;
            int signum = ((p ^ q) >> 31) | 1;
            switch (C17421.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
                case 1:
                    if (rem != 0) {
                        increment = false;
                    }
                    MathPreconditions.checkRoundingUnnecessary(increment);
                case 2:
                    increment = false;
                    break;
                case 3:
                    if (signum >= 0) {
                        increment = false;
                        break;
                    }
                    break;
                case 4:
                    increment = true;
                    break;
                case 5:
                    if (signum <= 0) {
                        increment = false;
                        break;
                    }
                    break;
                case 6:
                case 7:
                case 8:
                    int absRem = Math.abs(rem);
                    int cmpRemToHalfDivisor = absRem - (Math.abs(q) - absRem);
                    if (cmpRemToHalfDivisor != 0) {
                        if (cmpRemToHalfDivisor <= 0) {
                            increment = false;
                            break;
                        }
                    } else if (mode != RoundingMode.HALF_UP) {
                        if (!(mode == RoundingMode.HALF_EVEN) || !((div & 1) != 0)) {
                            increment = false;
                            break;
                        }
                    }
                    break;
                default:
                    throw new AssertionError();
            }
            return increment ? div + signum : div;
        }
        throw new ArithmeticException("/ by zero");
    }

    public static int mod(int x, int m) {
        if (m > 0) {
            int result = x % m;
            return result >= 0 ? result : result + m;
        }
        StringBuilder sb = new StringBuilder(31);
        sb.append("Modulus ");
        sb.append(m);
        sb.append(" must be > 0");
        throw new ArithmeticException(sb.toString());
    }

    public static int gcd(int a, int b) {
        MathPreconditions.checkNonNegative("a", a);
        MathPreconditions.checkNonNegative("b", b);
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        int aTwos = Integer.numberOfTrailingZeros(a);
        int a2 = a >> aTwos;
        int bTwos = Integer.numberOfTrailingZeros(b);
        int b2 = b >> bTwos;
        while (a2 != b2) {
            int delta = a2 - b2;
            int minDeltaOrZero = (delta >> 31) & delta;
            int a3 = (delta - minDeltaOrZero) - minDeltaOrZero;
            b2 += minDeltaOrZero;
            a2 = a3 >> Integer.numberOfTrailingZeros(a3);
        }
        return a2 << Math.min(aTwos, bTwos);
    }

    public static int checkedAdd(int a, int b) {
        long result = ((long) a) + ((long) b);
        MathPreconditions.checkNoOverflow(result == ((long) ((int) result)), "checkedAdd", a, b);
        return (int) result;
    }

    public static int checkedSubtract(int a, int b) {
        long result = ((long) a) - ((long) b);
        MathPreconditions.checkNoOverflow(result == ((long) ((int) result)), "checkedSubtract", a, b);
        return (int) result;
    }

    public static int checkedMultiply(int a, int b) {
        long result = ((long) a) * ((long) b);
        MathPreconditions.checkNoOverflow(result == ((long) ((int) result)), "checkedMultiply", a, b);
        return (int) result;
    }

    public static int checkedPow(int b, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        boolean z = false;
        if (b == -2) {
            if (k < 32) {
                z = true;
            }
            MathPreconditions.checkNoOverflow(z, "checkedPow", b, k);
            return (k & 1) == 0 ? 1 << k : -1 << k;
        } else if (b != -1) {
            if (b != 0) {
                if (b == 1) {
                    return 1;
                }
                if (b != 2) {
                    int accum = 1;
                    while (k != 0) {
                        if (k == 1) {
                            return checkedMultiply(accum, b);
                        }
                        if ((k & 1) != 0) {
                            accum = checkedMultiply(accum, b);
                        }
                        k >>= 1;
                        if (k > 0) {
                            MathPreconditions.checkNoOverflow((-46340 <= b) & (b <= FLOOR_SQRT_MAX_INT), "checkedPow", b, k);
                            b *= b;
                        }
                    }
                    return accum;
                }
                if (k < 31) {
                    z = true;
                }
                MathPreconditions.checkNoOverflow(z, "checkedPow", b, k);
                return 1 << k;
            } else if (k == 0) {
                return 1;
            } else {
                return 0;
            }
        } else if ((k & 1) == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    @Beta
    public static int saturatedAdd(int a, int b) {
        return Ints.saturatedCast(((long) a) + ((long) b));
    }

    @Beta
    public static int saturatedSubtract(int a, int b) {
        return Ints.saturatedCast(((long) a) - ((long) b));
    }

    @Beta
    public static int saturatedMultiply(int a, int b) {
        return Ints.saturatedCast(((long) a) * ((long) b));
    }

    @Beta
    public static int saturatedPow(int b, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        if (b != -2) {
            if (b != -1) {
                if (b != 0) {
                    if (b == 1) {
                        return 1;
                    }
                    if (b != 2) {
                        int accum = 1;
                        int limit = ((b >>> 31) & k & 1) + Integer.MAX_VALUE;
                        while (k != 0) {
                            if (k == 1) {
                                return saturatedMultiply(accum, b);
                            }
                            if ((k & 1) != 0) {
                                accum = saturatedMultiply(accum, b);
                            }
                            k >>= 1;
                            if (k > 0) {
                                if ((-46340 > b) || (b > FLOOR_SQRT_MAX_INT)) {
                                    return limit;
                                }
                                b *= b;
                            }
                        }
                        return accum;
                    } else if (k >= 31) {
                        return Integer.MAX_VALUE;
                    } else {
                        return 1 << k;
                    }
                } else if (k == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else if ((k & 1) == 0) {
                return 1;
            } else {
                return -1;
            }
        } else if (k >= 32) {
            return (k & 1) + Integer.MAX_VALUE;
        } else {
            return (k & 1) == 0 ? 1 << k : -1 << k;
        }
    }

    public static int factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        int[] iArr = factorials;
        if (n < iArr.length) {
            return iArr[n];
        }
        return Integer.MAX_VALUE;
    }

    public static int binomial(int n, int k) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", k);
        Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", k, n);
        if (k > (n >> 1)) {
            k = n - k;
        }
        int[] iArr = biggestBinomials;
        if (k >= iArr.length || n > iArr[k]) {
            return Integer.MAX_VALUE;
        }
        if (k == 0) {
            return 1;
        }
        if (k == 1) {
            return n;
        }
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = (result * ((long) (n - i))) / ((long) (i + 1));
        }
        return (int) result;
    }

    public static int mean(int x, int y) {
        return (x & y) + ((x ^ y) >> 1);
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(int n) {
        return LongMath.isPrime((long) n);
    }

    /* renamed from: com.google.common.math.IntMath$1 */
    static /* synthetic */ class C17421 {
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
}
