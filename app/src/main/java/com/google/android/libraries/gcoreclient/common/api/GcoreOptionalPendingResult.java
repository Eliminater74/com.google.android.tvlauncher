package com.google.android.libraries.gcoreclient.common.api;

public interface GcoreOptionalPendingResult<GR extends GcoreResult> extends GcorePendingResult<GR> {
    GR get();

    boolean isDone();
}
