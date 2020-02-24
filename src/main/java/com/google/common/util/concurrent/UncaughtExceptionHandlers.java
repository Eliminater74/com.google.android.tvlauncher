package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.lang.Thread;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
public final class UncaughtExceptionHandlers {
    private UncaughtExceptionHandlers() {
    }

    public static Thread.UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }

    @VisibleForTesting
    static final class Exiter implements Thread.UncaughtExceptionHandler {
        private static final Logger logger = Logger.getLogger(Exiter.class.getName());
        private final Runtime runtime;

        Exiter(Runtime runtime2) {
            this.runtime = runtime2;
        }

        public void uncaughtException(Thread t, Throwable e) {
            try {
                logger.logp(Level.SEVERE, "com.google.common.util.concurrent.UncaughtExceptionHandlers$Exiter", "uncaughtException", String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", t), e);
            } catch (Throwable th) {
                this.runtime.exit(1);
                throw th;
            }
            this.runtime.exit(1);
        }
    }
}
