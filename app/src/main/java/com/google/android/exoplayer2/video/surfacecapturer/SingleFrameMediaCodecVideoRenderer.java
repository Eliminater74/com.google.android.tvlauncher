package com.google.android.exoplayer2.video.surfacecapturer;

import android.content.Context;
import android.media.MediaCodec;
import android.support.annotation.Nullable;
import android.view.Surface;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;

import java.nio.ByteBuffer;

public class SingleFrameMediaCodecVideoRenderer extends MediaCodecVideoRenderer {
    private boolean hasRenderedFirstFrame;
    @Nullable
    private Surface surface;

    public SingleFrameMediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        super(context, mediaCodecSelector);
    }

    public void handleMessage(int messageType, @Nullable Object message) throws ExoPlaybackException {
        if (messageType == 1) {
            this.surface = (Surface) message;
        }
        super.handleMessage(messageType, message);
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean joining) throws ExoPlaybackException {
        this.hasRenderedFirstFrame = false;
        super.onEnabled(joining);
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        this.hasRenderedFirstFrame = false;
        super.onPositionReset(positionUs, joining);
    }

    /* access modifiers changed from: protected */
    public boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodec codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, long bufferPresentationTimeUs, boolean shouldSkip, Format format) throws ExoPlaybackException {
        MediaCodec mediaCodec = codec;
        int i = bufferIndex;
        long presentationTimeUs = bufferPresentationTimeUs - getOutputStreamOffsetUs();
        if (shouldSkip) {
            skipOutputBuffer(mediaCodec, i, presentationTimeUs);
            return true;
        } else if (this.surface == null || this.hasRenderedFirstFrame) {
            return false;
        } else {
            this.hasRenderedFirstFrame = true;
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(codec, bufferIndex, presentationTimeUs, System.nanoTime());
            } else {
                renderOutputBuffer(mediaCodec, i, presentationTimeUs);
            }
            return true;
        }
    }
}
