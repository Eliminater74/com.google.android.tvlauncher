package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.libraries.gcoreclient.common.api.GcoreReleasable;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;

public class GcoreResultImpl implements GcoreResult, GcoreReleasable {
    private GcoreStatusImpl gcoreStatus;
    private final Result result;

    public GcoreResultImpl(Result result2) {
        this.result = result2;
    }

    public GcoreStatus getStatus() {
        if (this.gcoreStatus == null) {
            this.gcoreStatus = new GcoreStatusImpl(this.result.getStatus());
        }
        return this.gcoreStatus;
    }

    public void release() {
        Result result2 = this.result;
        if (result2 instanceof Releasable) {
            ((Releasable) result2).release();
        }
    }

    public Result unwrap() {
        return this.result;
    }
}
