package com.google.android.libraries.performance.primes;

public final class TestOnlyToken {
    private TestOnlyToken() {
    }

    public static TestOnlyToken create() {
        return new TestOnlyToken();
    }
}
