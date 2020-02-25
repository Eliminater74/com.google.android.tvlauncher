package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.drm.DecryptionResource;

public final class FormatHolder {
    @Nullable
    public DecryptionResource<?> decryptionResource;
    public boolean decryptionResourceIsProvided;
    @Nullable
    public Format format;
}
