package com.google.android.exoplayer2.drm;

import android.media.MediaDrm;

import java.util.List;

final /* synthetic */ class FrameworkMediaDrm$$Lambda$1 implements MediaDrm.OnKeyStatusChangeListener {
    private final FrameworkMediaDrm arg$1;
    private final ExoMediaDrm.OnKeyStatusChangeListener arg$2;

    FrameworkMediaDrm$$Lambda$1(FrameworkMediaDrm frameworkMediaDrm, ExoMediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener) {
        this.arg$1 = frameworkMediaDrm;
        this.arg$2 = onKeyStatusChangeListener;
    }

    public void onKeyStatusChange(MediaDrm mediaDrm, byte[] bArr, List list, boolean z) {
        this.arg$1.lambda$setOnKeyStatusChangeListener$1$FrameworkMediaDrm(this.arg$2, mediaDrm, bArr, list, z);
    }
}
