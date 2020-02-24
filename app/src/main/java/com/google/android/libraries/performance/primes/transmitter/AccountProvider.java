package com.google.android.libraries.performance.primes.transmitter;

import android.support.annotation.Nullable;

public interface AccountProvider {
    public static final AccountProvider NOOP_PROVIDER = new AccountProvider() {
        public String getAccountName() {
            return null;
        }
    };

    @Nullable
    String getAccountName();
}
