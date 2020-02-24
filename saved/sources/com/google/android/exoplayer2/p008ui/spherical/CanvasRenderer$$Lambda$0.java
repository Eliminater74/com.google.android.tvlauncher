package com.google.android.exoplayer2.p008ui.spherical;

import android.graphics.SurfaceTexture;

/* renamed from: com.google.android.exoplayer2.ui.spherical.CanvasRenderer$$Lambda$0 */
final /* synthetic */ class CanvasRenderer$$Lambda$0 implements SurfaceTexture.OnFrameAvailableListener {
    private final CanvasRenderer arg$1;

    CanvasRenderer$$Lambda$0(CanvasRenderer canvasRenderer) {
        this.arg$1 = canvasRenderer;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.arg$1.lambda$init$0$CanvasRenderer(surfaceTexture);
    }
}
