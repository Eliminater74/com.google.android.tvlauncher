package com.google.android.libraries.performance.primes;

public final class ShutdownWhitelistToken {
    private static final ShutdownWhitelistToken instance = new ShutdownWhitelistToken();

    private ShutdownWhitelistToken() {
    }

    public static ShutdownWhitelistToken getInstance() {
        return instance;
    }
}
