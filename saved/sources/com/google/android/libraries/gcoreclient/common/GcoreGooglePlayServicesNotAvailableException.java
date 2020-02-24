package com.google.android.libraries.gcoreclient.common;

public class GcoreGooglePlayServicesNotAvailableException extends Exception {
    public final int errorCode;

    public GcoreGooglePlayServicesNotAvailableException(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public GcoreGooglePlayServicesNotAvailableException(int errorCode2, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode2;
    }

    public GcoreGooglePlayServicesNotAvailableException(int errorCode2, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode2;
    }
}
