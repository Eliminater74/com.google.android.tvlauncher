package com.google.android.libraries.gcoreclient.common.api.impl;

import com.google.android.gms.common.api.Response;
import com.google.android.libraries.gcoreclient.common.api.GcoreResponse;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;

public class GcoreResponseImpl<GR extends GcoreResult, R extends Response<?>> implements GcoreResponse<GR> {
    protected final R response;

    public GcoreResponseImpl(R response2) {
        this.response = response2;
    }
}
