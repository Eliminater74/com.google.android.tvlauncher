package com.google.android.libraries.performance.primes;

public final class WhitelistNetworkToken {
    private static final WhitelistNetworkToken instance = new WhitelistNetworkToken();

    public static WhitelistNetworkToken getInstance() {
        return instance;
    }

    private WhitelistNetworkToken() {
    }
}
