package com.google.common.util.concurrent.internal;

public final class InternalFutures {
    public static Throwable tryInternalFastPathGetFailure(InternalFutureFailureAccess future) {
        return future.tryInternalFastPathGetFailure();
    }

    private InternalFutures() {
    }
}
