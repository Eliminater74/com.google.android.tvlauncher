package com.google.android.libraries.performance.primes;

public final class WhitelistAggregationToken {
    private static final WhitelistAggregationToken instance = new WhitelistAggregationToken();

    public static WhitelistAggregationToken getInstance() {
        return instance;
    }

    private WhitelistAggregationToken() {
    }
}
