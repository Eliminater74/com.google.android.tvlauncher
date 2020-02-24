package com.google.android.libraries.performance.primes;

public final class WhitelistToken {
    private static final WhitelistToken instance = new WhitelistToken();

    public static WhitelistToken getInstance() {
        return instance;
    }

    private WhitelistToken() {
    }
}
