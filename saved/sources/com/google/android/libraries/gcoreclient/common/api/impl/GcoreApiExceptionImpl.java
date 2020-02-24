package com.google.android.libraries.gcoreclient.common.api.impl;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.gcoreclient.common.api.GcoreApiException;

public final class GcoreApiExceptionImpl extends GcoreApiException {
    private final ApiException apiException;

    public GcoreApiExceptionImpl(ApiException apiException2, Throwable cause) {
        super(apiException2.getMessage(), cause);
        this.apiException = apiException2;
    }

    public int getStatusCode() {
        return this.apiException.getStatusCode();
    }

    public String getStatusMessage() {
        return this.apiException.getStatusMessage();
    }
}
