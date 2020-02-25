package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Beta
public interface Service {

    void addListener(Listener listener, Executor executor);

    void awaitRunning();

    void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException;

    void awaitTerminated();

    void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException;

    Throwable failureCause();

    boolean isRunning();

    @CanIgnoreReturnValue
    Service startAsync();

    State state();

    @CanIgnoreReturnValue
    Service stopAsync();

    @Beta
    public enum State {
        NEW {
            /* access modifiers changed from: package-private */
            public boolean isTerminal() {
                return false;
            }
        },
        STARTING {
            /* access modifiers changed from: package-private */
            public boolean isTerminal() {
                return false;
            }
        },
        RUNNING {
            /* access modifiers changed from: package-private */
            public boolean isTerminal() {
                return false;
            }
        },
        STOPPING {
            /* access modifiers changed from: package-private */
            public boolean isTerminal() {
                return false;
            }
        },
        TERMINATED {
            /* access modifiers changed from: package-private */
            public boolean isTerminal() {
                return true;
            }
        },
        FAILED {
            /* access modifiers changed from: package-private */
            public boolean isTerminal() {
                return true;
            }
        };

        /* access modifiers changed from: package-private */
        public abstract boolean isTerminal();
    }

    @Beta
    public static abstract class Listener {
        public void starting() {
        }

        public void running() {
        }

        public void stopping(State from) {
        }

        public void terminated(State from) {
        }

        public void failed(State from, Throwable failure) {
        }
    }
}
