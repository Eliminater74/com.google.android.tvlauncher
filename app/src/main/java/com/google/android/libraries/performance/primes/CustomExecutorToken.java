package com.google.android.libraries.performance.primes;

public final class CustomExecutorToken {
    private static final CustomExecutorToken instance = new CustomExecutorToken();

    private CustomExecutorToken() {
    }

    public static CustomExecutorToken getInstance() {
        return instance;
    }
}
