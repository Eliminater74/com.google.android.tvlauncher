package com.google.android.libraries.performance.primes;

import android.support.annotation.VisibleForTesting;

public final class Config {
    private static boolean isGerrit = false;

    public static boolean isGerrit() {
        return isGerrit;
    }

    @VisibleForTesting
    public static void setGerritForTest(boolean b) {
        isGerrit = b;
    }

    private Config() {
    }
}
