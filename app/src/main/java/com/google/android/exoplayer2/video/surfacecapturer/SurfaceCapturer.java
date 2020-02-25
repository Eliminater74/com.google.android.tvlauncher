package com.google.android.exoplayer2.video.surfacecapturer;

import android.graphics.Bitmap;
import android.view.Surface;

public abstract class SurfaceCapturer {
    private final Callback callback;
    private final int outputHeight;
    private final int outputWidth;

    protected SurfaceCapturer(Callback callback2, int outputWidth2, int outputHeight2) {
        this.callback = callback2;
        this.outputWidth = outputWidth2;
        this.outputHeight = outputHeight2;
    }

    public abstract Surface getSurface();

    public abstract void release();

    /* access modifiers changed from: protected */
    public Callback getCallback() {
        return this.callback;
    }

    public int getOutputWidth() {
        return this.outputWidth;
    }

    public int getOutputHeight() {
        return this.outputHeight;
    }

    public interface Callback {
        void onSurfaceCaptureError(Exception exc);

        void onSurfaceCaptured(Bitmap bitmap);
    }
}
