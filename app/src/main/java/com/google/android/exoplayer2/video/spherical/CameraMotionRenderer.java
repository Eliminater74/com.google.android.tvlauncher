package com.google.android.exoplayer2.video.spherical;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.nio.ByteBuffer;

public class CameraMotionRenderer extends BaseRenderer {
    private static final int SAMPLE_WINDOW_DURATION_US = 100000;
    private final DecoderInputBuffer buffer = new DecoderInputBuffer(1);
    private final FormatHolder formatHolder = new FormatHolder();
    private final ParsableByteArray scratch = new ParsableByteArray();
    private long lastTimestampUs;
    @Nullable
    private CameraMotionListener listener;
    private long offsetUs;

    public CameraMotionRenderer() {
        super(5);
    }

    public int supportsFormat(Format format) {
        if (MimeTypes.APPLICATION_CAMERA_MOTION.equals(format.sampleMimeType)) {
            return 4;
        }
        return 0;
    }

    public void handleMessage(int messageType, @Nullable Object message) throws ExoPlaybackException {
        if (messageType == 7) {
            this.listener = (CameraMotionListener) message;
        } else {
            super.handleMessage(messageType, message);
        }
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formats, long offsetUs2) throws ExoPlaybackException {
        this.offsetUs = offsetUs2;
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        resetListener();
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        resetListener();
    }

    public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        float[] rotation;
        while (!hasReadStreamToEnd() && this.lastTimestampUs < 100000 + positionUs) {
            this.buffer.clear();
            if (readSource(this.formatHolder, this.buffer, false) == -4 && !this.buffer.isEndOfStream()) {
                this.buffer.flip();
                this.lastTimestampUs = this.buffer.timeUs;
                if (!(this.listener == null || (rotation = parseMetadata(this.buffer.data)) == null)) {
                    ((CameraMotionListener) Util.castNonNull(this.listener)).onCameraMotion(this.lastTimestampUs - this.offsetUs, rotation);
                }
            } else {
                return;
            }
        }
    }

    public boolean isEnded() {
        return hasReadStreamToEnd();
    }

    public boolean isReady() {
        return true;
    }

    @Nullable
    private float[] parseMetadata(ByteBuffer data) {
        if (data.remaining() != 16) {
            return null;
        }
        this.scratch.reset(data.array(), data.limit());
        this.scratch.setPosition(data.arrayOffset() + 4);
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = Float.intBitsToFloat(this.scratch.readLittleEndianInt());
        }
        return result;
    }

    private void resetListener() {
        this.lastTimestampUs = 0;
        CameraMotionListener cameraMotionListener = this.listener;
        if (cameraMotionListener != null) {
            cameraMotionListener.onCameraMotionReset();
        }
    }
}
