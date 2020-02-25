package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.api.Api;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutApi;
import com.google.android.libraries.gcoreclient.common.api.support.BaseGcoreApi;

public final class GcoreClearcutApiImpl implements GcoreClearcutApi<BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreNoOptions>, BaseGcoreApi<BaseGcoreApi.BaseGcoreApiOptions.BaseGcoreNoOptions> {

    private GcoreClearcutApiImpl() {
    }

    public Api getApi() {
        return ClearcutLogger.API;
    }

    public static class Builder implements GcoreClearcutApi.Builder {
        GcoreClearcutApiImpl api = new GcoreClearcutApiImpl();

        public GcoreClearcutApi build() {
            return this.api;
        }
    }
}
