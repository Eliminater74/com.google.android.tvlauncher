package com.google.android.exoplayer2.p008ui.spherical;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.FrameRotationQueue;
import com.google.android.exoplayer2.video.spherical.Projection;
import com.google.android.exoplayer2.video.spherical.ProjectionDecoder;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.exoplayer2.ui.spherical.SceneRenderer */
public final class SceneRenderer implements VideoFrameMetadataListener, CameraMotionListener {
    private volatile int defaultStereoMode = 0;
    private final AtomicBoolean frameAvailable = new AtomicBoolean();
    private final FrameRotationQueue frameRotationQueue = new FrameRotationQueue();
    @Nullable
    private byte[] lastProjectionData;
    private int lastStereoMode = -1;
    private final TimedValueQueue<Projection> projectionQueue = new TimedValueQueue<>();
    private final ProjectionRenderer projectionRenderer = new ProjectionRenderer();
    private final AtomicBoolean resetRotationAtNextFrame = new AtomicBoolean(true);
    private final float[] rotationMatrix = new float[16];
    private final TimedValueQueue<Long> sampleTimestampQueue = new TimedValueQueue<>();
    private SurfaceTexture surfaceTexture;
    private final float[] tempMatrix = new float[16];
    private int textureId;

    public void setDefaultStereoMode(int stereoMode) {
        this.defaultStereoMode = stereoMode;
    }

    public SurfaceTexture init() {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GlUtil.checkGlError();
        this.projectionRenderer.init();
        GlUtil.checkGlError();
        this.textureId = GlUtil.createExternalTexture();
        this.surfaceTexture = new SurfaceTexture(this.textureId);
        this.surfaceTexture.setOnFrameAvailableListener(new SceneRenderer$$Lambda$0(this));
        return this.surfaceTexture;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$init$0$SceneRenderer(SurfaceTexture surfaceTexture2) {
        this.frameAvailable.set(true);
    }

    public void drawFrame(float[] viewProjectionMatrix, boolean rightEye) {
        GLES20.glClear(16384);
        GlUtil.checkGlError();
        if (this.frameAvailable.compareAndSet(true, false)) {
            ((SurfaceTexture) Assertions.checkNotNull(this.surfaceTexture)).updateTexImage();
            GlUtil.checkGlError();
            if (this.resetRotationAtNextFrame.compareAndSet(true, false)) {
                Matrix.setIdentityM(this.rotationMatrix, 0);
            }
            long lastFrameTimestampNs = this.surfaceTexture.getTimestamp();
            Long sampleTimestampUs = this.sampleTimestampQueue.poll(lastFrameTimestampNs);
            if (sampleTimestampUs != null) {
                this.frameRotationQueue.pollRotationMatrix(this.rotationMatrix, sampleTimestampUs.longValue());
            }
            Projection projection = this.projectionQueue.pollFloor(lastFrameTimestampNs);
            if (projection != null) {
                this.projectionRenderer.setProjection(projection);
            }
        }
        Matrix.multiplyMM(this.tempMatrix, 0, viewProjectionMatrix, 0, this.rotationMatrix, 0);
        this.projectionRenderer.draw(this.textureId, this.tempMatrix, rightEye);
    }

    public void shutdown() {
        this.projectionRenderer.shutdown();
    }

    public void onVideoFrameAboutToBeRendered(long presentationTimeUs, long releaseTimeNs, Format format) {
        this.sampleTimestampQueue.add(releaseTimeNs, Long.valueOf(presentationTimeUs));
        setProjection(format.projectionData, format.stereoMode, releaseTimeNs);
    }

    public void onCameraMotion(long timeUs, float[] rotation) {
        this.frameRotationQueue.setRotation(timeUs, rotation);
    }

    public void onCameraMotionReset() {
        this.sampleTimestampQueue.clear();
        this.frameRotationQueue.reset();
        this.resetRotationAtNextFrame.set(true);
    }

    private void setProjection(@Nullable byte[] projectionData, int stereoMode, long timeNs) {
        Projection projection;
        byte[] oldProjectionData = this.lastProjectionData;
        int oldStereoMode = this.lastStereoMode;
        this.lastProjectionData = projectionData;
        this.lastStereoMode = stereoMode == -1 ? this.defaultStereoMode : stereoMode;
        if (oldStereoMode != this.lastStereoMode || !Arrays.equals(oldProjectionData, this.lastProjectionData)) {
            Projection projectionFromData = null;
            byte[] bArr = this.lastProjectionData;
            if (bArr != null) {
                projectionFromData = ProjectionDecoder.decode(bArr, this.lastStereoMode);
            }
            if (projectionFromData == null || !ProjectionRenderer.isSupported(projectionFromData)) {
                projection = Projection.createEquirectangular(this.lastStereoMode);
            } else {
                projection = projectionFromData;
            }
            this.projectionQueue.add(timeNs, projection);
        }
    }
}
