package com.google.android.tvlauncher.application;

import android.os.Build;

class DefaultUncaughtExceptionHandlerVerifier {
    DefaultUncaughtExceptionHandlerVerifier() {
    }

    static void assertHandlerClass(String expected) {
        Build.TYPE.equals("unknown");
    }
}
