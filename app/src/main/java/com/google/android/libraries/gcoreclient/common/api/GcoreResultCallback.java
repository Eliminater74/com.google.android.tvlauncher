package com.google.android.libraries.gcoreclient.common.api;

public interface GcoreResultCallback<R extends GcoreResult> {
    void onResult(R r);
}
