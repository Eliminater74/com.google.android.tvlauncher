package com.google.android.exoplayer2.p008ui.spherical;

import android.graphics.SurfaceTexture;

/* renamed from: com.google.android.exoplayer2.ui.spherical.SphericalSurfaceView$$Lambda$1 */
final /* synthetic */ class SphericalSurfaceView$$Lambda$1 implements Runnable {
    private final SphericalSurfaceView arg$1;
    private final SurfaceTexture arg$2;

    SphericalSurfaceView$$Lambda$1(SphericalSurfaceView sphericalSurfaceView, SurfaceTexture surfaceTexture) {
        this.arg$1 = sphericalSurfaceView;
        this.arg$2 = surfaceTexture;
    }

    public void run() {
        this.arg$1.lambda$onSurfaceTextureAvailable$1$SphericalSurfaceView(this.arg$2);
    }
}
