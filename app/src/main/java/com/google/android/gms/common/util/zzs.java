package com.google.android.gms.common.util;

import android.support.annotation.Nullable;

import java.util.regex.Pattern;

/* compiled from: Strings */
public final class zzs {
    private static final Pattern zza = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zza(@Nullable String str) {
        return str == null || str.trim().isEmpty();
    }
}
