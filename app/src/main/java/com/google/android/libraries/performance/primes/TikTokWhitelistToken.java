package com.google.android.libraries.performance.primes;

public final class TikTokWhitelistToken {
    private static final TikTokWhitelistToken instance = new TikTokWhitelistToken();

    public static TikTokWhitelistToken getInstance() {
        return instance;
    }

    private TikTokWhitelistToken() {
    }
}
