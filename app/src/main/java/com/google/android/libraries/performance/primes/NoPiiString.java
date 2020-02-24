package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.errorprone.annotations.CompileTimeConstant;
import com.google.errorprone.annotations.Immutable;

@Immutable
@javax.annotation.concurrent.Immutable
public final class NoPiiString {
    private final String value;

    public static NoPiiString fromConstant(@CompileTimeConstant String value2) {
        return new NoPiiString((String) Preconditions.checkNotNull(value2));
    }

    public static NoPiiString fromConstant(@CompileTimeConstant String value1, @CompileTimeConstant String value2) {
        String valueOf = String.valueOf((String) Preconditions.checkNotNull(value1));
        String valueOf2 = String.valueOf((String) Preconditions.checkNotNull(value2));
        return new NoPiiString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static NoPiiString fromClass(Class<?> clazz) {
        return fromClass(null, clazz);
    }

    public static NoPiiString fromClass(@CompileTimeConstant String prefix, Class<?> clazz) {
        if (TextUtils.isEmpty(prefix)) {
            return new NoPiiString(clazz.getSimpleName());
        }
        String valueOf = String.valueOf(prefix);
        String valueOf2 = String.valueOf(clazz.getSimpleName());
        return new NoPiiString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static NoPiiString fromClassToFullyQualifiedName(Class<?> clazz) {
        return new NoPiiString(clazz.getName());
    }

    public static NoPiiString fromEnum(Enum<?> enumValue) {
        return fromEnum(null, enumValue);
    }

    public static NoPiiString fromEnum(@CompileTimeConstant String prefix, Enum<?> enumValue) {
        if (TextUtils.isEmpty(prefix)) {
            return new NoPiiString(enumValue.name());
        }
        String valueOf = String.valueOf(prefix);
        String valueOf2 = String.valueOf(enumValue.name());
        return new NoPiiString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static NoPiiString concat(NoPiiString left, NoPiiString right) {
        String valueOf = String.valueOf(left.value);
        String valueOf2 = String.valueOf(right.value);
        return new NoPiiString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static boolean isNullOrEmpty(@Nullable NoPiiString noPiiString) {
        return noPiiString == null || noPiiString.toString().isEmpty();
    }

    @VisibleForTesting(otherwise = 5)
    public static NoPiiString forTesting(TestOnlyToken token, String value2) {
        Preconditions.checkNotNull(token, "Requires TestOnlyToken");
        return new NoPiiString(value2);
    }

    private NoPiiString(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    @Nullable
    public static String safeToString(@Nullable NoPiiString noPiiString) {
        if (noPiiString == null) {
            return null;
        }
        return noPiiString.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof NoPiiString) {
            return this.value.equals(((NoPiiString) obj).value);
        }
        return false;
    }

    public int hashCode() {
        return this.value.hashCode();
    }
}
