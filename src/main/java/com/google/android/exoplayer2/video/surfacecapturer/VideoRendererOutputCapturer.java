package com.google.android.exoplayer2.video.surfacecapturer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Surface;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.surfacecapturer.SurfaceCapturer;

public final class VideoRendererOutputCapturer implements Handler.Callback {
    private static final int MSG_RELEASE = 2;
    private static final int MSG_SET_OUTPUT = 1;
    private final EventDispatcher eventDispatcher;
    private final ExoPlayer exoPlayer;
    private final Handler handler;
    private final HandlerThread handlerThread = new HandlerThread("VideoRendererOutputCapturer");
    private volatile boolean released;
    private final Renderer renderer;
    @Nullable
    private SurfaceCapturer surfaceCapturer;

    public interface Callback extends SurfaceCapturer.Callback {
        void onOutputSizeSet(int i, int i2);
    }

    public VideoRendererOutputCapturer(Callback callback, Handler callbackHandler, SingleFrameMediaCodecVideoRenderer videoRenderer, ExoPlayer exoPlayer2) {
        this.renderer = (Renderer) Assertions.checkNotNull(videoRenderer);
        this.exoPlayer = (ExoPlayer) Assertions.checkNotNull(exoPlayer2);
        this.eventDispatcher = new EventDispatcher(callbackHandler, callback);
        this.handlerThread.start();
        this.handler = Util.createHandler(this.handlerThread.getLooper(), this);
    }

    public void setOutputSize(int width, int height) {
        this.handler.obtainMessage(1, width, height).sendToTarget();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void release() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.released     // Catch:{ all -> 0x002f }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            android.os.HandlerThread r0 = r2.handlerThread     // Catch:{ all -> 0x002f }
            r0.interrupt()     // Catch:{ all -> 0x002f }
            android.os.Handler r0 = r2.handler     // Catch:{ all -> 0x002f }
            r1 = 0
            r0.removeCallbacksAndMessages(r1)     // Catch:{ all -> 0x002f }
            android.os.Handler r0 = r2.handler     // Catch:{ all -> 0x002f }
            r1 = 2
            r0.sendEmptyMessage(r1)     // Catch:{ all -> 0x002f }
            r0 = 0
        L_0x0019:
            boolean r1 = r2.released     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x0024
            r2.wait()     // Catch:{ InterruptedException -> 0x0021 }
            goto L_0x0019
        L_0x0021:
            r1 = move-exception
            r0 = 1
            goto L_0x0019
        L_0x0024:
            if (r0 == 0) goto L_0x002d
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x002f }
            r1.interrupt()     // Catch:{ all -> 0x002f }
        L_0x002d:
            monitor-exit(r2)
            return
        L_0x002f:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.surfacecapturer.VideoRendererOutputCapturer.release():void");
    }

    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            handleSetOutput(message.arg1, message.arg2);
            return true;
        } else if (i != 2) {
            return false;
        } else {
            handleRelease();
            return true;
        }
    }

    private void handleSetOutput(int width, int height) {
        SurfaceCapturer surfaceCapturer2 = this.surfaceCapturer;
        if (!(surfaceCapturer2 != null && surfaceCapturer2.getOutputWidth() == width && this.surfaceCapturer.getOutputHeight() == height)) {
            updateSurfaceCapturer(width, height);
        }
        this.eventDispatcher.onOutputSizeSet(width, height);
    }

    private void updateSurfaceCapturer(int width, int height) {
        SurfaceCapturer oldSurfaceCapturer = this.surfaceCapturer;
        if (oldSurfaceCapturer != null) {
            blockingSetRendererSurface(null);
            oldSurfaceCapturer.release();
        }
        this.surfaceCapturer = createSurfaceCapturer(width, height);
        blockingSetRendererSurface(this.surfaceCapturer.getSurface());
    }

    private SurfaceCapturer createSurfaceCapturer(int width, int height) {
        if (Util.SDK_INT >= 24) {
            return createSurfaceCapturerV24(width, height);
        }
        throw new UnsupportedOperationException("Creating Surface Capturer is not supported for API < 24 yet");
    }

    @TargetApi(24)
    private SurfaceCapturer createSurfaceCapturerV24(int width, int height) {
        return new PixelCopySurfaceCapturerV24(this.eventDispatcher, width, height, this.handler);
    }

    private void blockingSetRendererSurface(@Nullable Surface surface) {
        try {
            this.exoPlayer.createMessage(this.renderer).setType(1).setPayload(surface).send().blockUntilDelivered();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleRelease() {
        this.eventDispatcher.release();
        this.handler.removeCallbacksAndMessages(null);
        SurfaceCapturer surfaceCapturer2 = this.surfaceCapturer;
        if (surfaceCapturer2 != null) {
            surfaceCapturer2.release();
        }
        this.handlerThread.quit();
        synchronized (this) {
            this.released = true;
            notifyAll();
        }
    }

    private static final class EventDispatcher implements Callback {
        private final Callback callback;
        private final Handler callbackHandler;
        private volatile boolean released;

        private EventDispatcher(Handler callbackHandler2, Callback callback2) {
            this.callbackHandler = callbackHandler2;
            this.callback = callback2;
        }

        public void onOutputSizeSet(int width, int height) {
            this.callbackHandler.post(new VideoRendererOutputCapturer$EventDispatcher$$Lambda$0(this, width, height));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onOutputSizeSet$0$VideoRendererOutputCapturer$EventDispatcher */
        public final /* synthetic */ void mo15902xf0df60d4(int width, int height) {
            if (!this.released) {
                this.callback.onOutputSizeSet(width, height);
            }
        }

        public void onSurfaceCaptured(Bitmap bitmap) {
            this.callbackHandler.post(new VideoRendererOutputCapturer$EventDispatcher$$Lambda$1(this, bitmap));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onSurfaceCaptured$1$VideoRendererOutputCapturer$EventDispatcher */
        public final /* synthetic */ void mo15904x73e61860(Bitmap bitmap) {
            if (!this.released) {
                this.callback.onSurfaceCaptured(bitmap);
            }
        }

        public void onSurfaceCaptureError(Exception exception) {
            this.callbackHandler.post(new VideoRendererOutputCapturer$EventDispatcher$$Lambda$2(this, exception));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onSurfaceCaptureError$2$VideoRendererOutputCapturer$EventDispatcher */
        public final /* synthetic */ void mo15903xcbe77e5(Exception exception) {
            if (!this.released) {
                this.callback.onSurfaceCaptureError(exception);
            }
        }

        public void release() {
            this.released = true;
        }
    }
}
