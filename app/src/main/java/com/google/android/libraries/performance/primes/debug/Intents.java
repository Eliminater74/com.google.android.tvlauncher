package com.google.android.libraries.performance.primes.debug;

import android.content.Context;
import android.content.Intent;

import com.google.android.exoplayer2.C0841C;

public final class Intents {
    private Intents() {
    }

    public static Intent createPrimesEventDebugActivityIntent(Context context) {
        Intent intent = new Intent("com.google.android.primes.action.DEBUG_PRIMES_EVENTS");
        intent.setPackage(context.getPackageName());
        intent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
        return intent;
    }
}
