package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.math.BigInteger;
import java.math.RoundingMode;

@GwtCompatible
@CanIgnoreReturnValue
final class MathPreconditions {
    private MathPreconditions() {
    }

    static int checkPositive(@NullableDecl String role, int x) {
        if (x > 0) {
            return x;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 26);
        sb.append(role);
        sb.append(" (");
        sb.append(x);
        sb.append(") must be > 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static long checkPositive(@NullableDecl String role, long x) {
        if (x > 0) {
            return x;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 35);
        sb.append(role);
        sb.append(" (");
        sb.append(x);
        sb.append(") must be > 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static BigInteger checkPositive(@NullableDecl String role, BigInteger x) {
        if (x.signum() > 0) {
            return x;
        }
        String valueOf = String.valueOf(x);
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 15 + String.valueOf(valueOf).length());
        sb.append(role);
        sb.append(" (");
        sb.append(valueOf);
        sb.append(") must be > 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static int checkNonNegative(@NullableDecl String role, int x) {
        if (x >= 0) {
            return x;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 27);
        sb.append(role);
        sb.append(" (");
        sb.append(x);
        sb.append(") must be >= 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static long checkNonNegative(@NullableDecl String role, long x) {
        if (x >= 0) {
            return x;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 36);
        sb.append(role);
        sb.append(" (");
        sb.append(x);
        sb.append(") must be >= 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static BigInteger checkNonNegative(@NullableDecl String role, BigInteger x) {
        if (x.signum() >= 0) {
            return x;
        }
        String valueOf = String.valueOf(x);
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 16 + String.valueOf(valueOf).length());
        sb.append(role);
        sb.append(" (");
        sb.append(valueOf);
        sb.append(") must be >= 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static double checkNonNegative(@NullableDecl String role, double x) {
        if (x >= 0.0d) {
            return x;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(role).length() + 40);
        sb.append(role);
        sb.append(" (");
        sb.append(x);
        sb.append(") must be >= 0");
        throw new IllegalArgumentException(sb.toString());
    }

    static void checkRoundingUnnecessary(boolean condition) {
        if (!condition) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    static void checkInRangeForRoundingInputs(boolean condition, double input, RoundingMode mode) {
        if (!condition) {
            String valueOf = String.valueOf(mode);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 83);
            sb.append("rounded value is out of range for input ");
            sb.append(input);
            sb.append(" and rounding mode ");
            sb.append(valueOf);
            throw new ArithmeticException(sb.toString());
        }
    }

    static void checkNoOverflow(boolean condition, String methodName, int a, int b) {
        if (!condition) {
            StringBuilder sb = new StringBuilder(String.valueOf(methodName).length() + 36);
            sb.append("overflow: ");
            sb.append(methodName);
            sb.append("(");
            sb.append(a);
            sb.append(", ");
            sb.append(b);
            sb.append(")");
            throw new ArithmeticException(sb.toString());
        }
    }

    static void checkNoOverflow(boolean condition, String methodName, long a, long b) {
        if (!condition) {
            StringBuilder sb = new StringBuilder(String.valueOf(methodName).length() + 54);
            sb.append("overflow: ");
            sb.append(methodName);
            sb.append("(");
            sb.append(a);
            sb.append(", ");
            sb.append(b);
            sb.append(")");
            throw new ArithmeticException(sb.toString());
        }
    }
}
