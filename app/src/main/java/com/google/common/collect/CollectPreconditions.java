package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
final class CollectPreconditions {
    CollectPreconditions() {
    }

    static void checkEntryNotNull(Object key, Object value) {
        if (key == null) {
            String valueOf = String.valueOf(value);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
            sb.append("null key in entry: null=");
            sb.append(valueOf);
            throw new NullPointerException(sb.toString());
        } else if (value == null) {
            String valueOf2 = String.valueOf(key);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 26);
            sb2.append("null value in entry: ");
            sb2.append(valueOf2);
            sb2.append("=null");
            throw new NullPointerException(sb2.toString());
        }
    }

    @CanIgnoreReturnValue
    static int checkNonnegative(int value, String name) {
        if (value >= 0) {
            return value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 40);
        sb.append(name);
        sb.append(" cannot be negative but was: ");
        sb.append(value);
        throw new IllegalArgumentException(sb.toString());
    }

    @CanIgnoreReturnValue
    static long checkNonnegative(long value, String name) {
        if (value >= 0) {
            return value;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 49);
        sb.append(name);
        sb.append(" cannot be negative but was: ");
        sb.append(value);
        throw new IllegalArgumentException(sb.toString());
    }

    static void checkPositive(int value, String name) {
        if (value <= 0) {
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 38);
            sb.append(name);
            sb.append(" must be positive but was: ");
            sb.append(value);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static void checkRemove(boolean canRemove) {
        Preconditions.checkState(canRemove, "no calls to next() since the last call to remove()");
    }
}
