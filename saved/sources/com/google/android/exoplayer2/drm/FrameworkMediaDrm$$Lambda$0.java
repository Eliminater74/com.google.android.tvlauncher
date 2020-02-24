package com.google.android.exoplayer2.drm;

import android.media.MediaDrm;
import com.google.android.exoplayer2.drm.ExoMediaDrm;

final /* synthetic */ class FrameworkMediaDrm$$Lambda$0 implements MediaDrm.OnEventListener {
    private final FrameworkMediaDrm arg$1;
    private final ExoMediaDrm.OnEventListener arg$2;

    FrameworkMediaDrm$$Lambda$0(FrameworkMediaDrm frameworkMediaDrm, ExoMediaDrm.OnEventListener onEventListener) {
        this.arg$1 = frameworkMediaDrm;
        this.arg$2 = onEventListener;
    }

    public void onEvent(MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
        this.arg$1.lambda$setOnEventListener$0$FrameworkMediaDrm(this.arg$2, mediaDrm, bArr, i, i2, bArr2);
    }
}
