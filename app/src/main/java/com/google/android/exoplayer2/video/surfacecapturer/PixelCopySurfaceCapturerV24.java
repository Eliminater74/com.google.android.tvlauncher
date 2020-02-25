package com.google.android.exoplayer2.video.surfacecapturer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.PixelCopy;
import android.view.Surface;

import com.google.android.exoplayer2.util.EGLSurfaceTexture;

@TargetApi(24)
final class PixelCopySurfaceCapturerV24 extends SurfaceCapturer implements EGLSurfaceTexture.TextureImageListener, PixelCopy.OnPixelCopyFinishedListener {
    private final EGLSurfaceTexture eglSurfaceTexture;
    private final Surface decoderSurface = new Surface(this.eglSurfaceTexture.getSurfaceTexture());
    private final Handler handler;
    @Nullable
    private Bitmap bitmap;

    PixelCopySurfaceCapturerV24(SurfaceCapturer.Callback callback, int outputWidth, int outputHeight, Handler imageRenderingHandler) {
        super(callback, outputWidth, outputHeight);
        this.handler = imageRenderingHandler;
        this.eglSurfaceTexture = new EGLSurfaceTexture(imageRenderingHandler, this);
        this.eglSurfaceTexture.init(0);
    }

    public Surface getSurface() {
        return this.decoderSurface;
    }

    public void release() {
        this.eglSurfaceTexture.release();
        this.decoderSurface.release();
    }

    public void setDefaultSurfaceTextureBufferSize(int width, int height) {
        this.eglSurfaceTexture.getSurfaceTexture().setDefaultBufferSize(width, height);
    }

    public void onFrameAvailable() {
        this.bitmap = Bitmap.createBitmap(getOutputWidth(), getOutputHeight(), Bitmap.Config.ARGB_8888);
        PixelCopy.request(this.decoderSurface, this.bitmap, this, this.handler);
    }

    public void onPixelCopyFinished(int copyResult) {
        Bitmap bitmap2;
        SurfaceCapturer.Callback callback = getCallback();
        if (copyResult != 0 || (bitmap2 = this.bitmap) == null) {
            callback.onSurfaceCaptureError(new SurfaceCapturerException("Couldn't copy image from surface", copyResult));
        } else {
            callback.onSurfaceCaptured(bitmap2);
        }
    }

    public static final class SurfaceCapturerException extends Exception {
        public final int errorCode;

        public SurfaceCapturerException(String message, int errorCode2) {
            super(message);
            this.errorCode = errorCode2;
        }
    }
}
