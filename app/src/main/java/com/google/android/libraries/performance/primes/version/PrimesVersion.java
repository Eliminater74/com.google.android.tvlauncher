package com.google.android.libraries.performance.primes.version;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.libraries.performance.primes.PrimesLog;

public final class PrimesVersion {
    private static final String TAG = "PrimesVersion";

    private PrimesVersion() {
    }

    public static Long getPrimesVersion(Context applicationContext) {
        try {
            String primesVersionString = applicationContext.getResources().getString(C1157R.string.primes_version);
            try {
                return Long.valueOf(Long.parseLong(primesVersionString));
            } catch (NumberFormatException e) {
                String valueOf = String.valueOf(primesVersionString);
                PrimesLog.m54w(TAG, valueOf.length() != 0 ? "Couldn't parse Primes version number from ".concat(valueOf) : new String("Couldn't parse Primes version number from "), new Object[0]);
                return null;
            }
        } catch (Resources.NotFoundException e2) {
            PrimesLog.m54w(TAG, "Primes version number string not found", new Object[0]);
            return null;
        }
    }
}
