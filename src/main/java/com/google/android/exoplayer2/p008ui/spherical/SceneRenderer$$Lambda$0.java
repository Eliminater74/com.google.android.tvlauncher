package com.google.android.exoplayer2.p008ui.spherical;

import android.graphics.SurfaceTexture;

/* renamed from: com.google.android.exoplayer2.ui.spherical.SceneRenderer$$Lambda$0 */
final /* synthetic */ class SceneRenderer$$Lambda$0 implements SurfaceTexture.OnFrameAvailableListener {
    private final SceneRenderer arg$1;

    SceneRenderer$$Lambda$0(SceneRenderer sceneRenderer) {
        this.arg$1 = sceneRenderer;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.arg$1.lambda$init$0$SceneRenderer(surfaceTexture);
    }
}
