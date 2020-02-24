package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.libraries.gcoreclient.common.api.GcoreCommonStatusCodes;

public final class GcoreCommonStatusCodesImpl implements GcoreCommonStatusCodes {
    public int getSuccessStatusCode() {
        return 0;
    }

    public int getErrorStatusCode() {
        return 13;
    }

    public int getTimeoutStatusCode() {
        return 15;
    }

    public int getNetworkErrorStatusCode() {
        return 7;
    }
}
