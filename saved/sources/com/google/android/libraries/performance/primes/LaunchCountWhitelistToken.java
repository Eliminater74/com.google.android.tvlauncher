package com.google.android.libraries.performance.primes;

public final class LaunchCountWhitelistToken {

    private static final class Holder {
        /* access modifiers changed from: private */
        public static final LaunchCountWhitelistToken token = new LaunchCountWhitelistToken();

        private Holder() {
        }
    }

    public static LaunchCountWhitelistToken getInstance() {
        return Holder.token;
    }

    private LaunchCountWhitelistToken() {
    }
}
