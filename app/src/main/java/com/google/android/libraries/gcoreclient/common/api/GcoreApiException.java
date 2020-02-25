package com.google.android.libraries.gcoreclient.common.api;

public abstract class GcoreApiException extends Exception {
    protected GcoreApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public abstract String getStatusMessage();
}
