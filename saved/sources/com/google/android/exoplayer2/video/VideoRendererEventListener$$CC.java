package com.google.android.exoplayer2.video;

import android.support.annotation.Nullable;
import android.view.Surface;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;

public abstract /* synthetic */ class VideoRendererEventListener$$CC {
    public static void onVideoEnabled$$dflt$$(VideoRendererEventListener videoRendererEventListener, DecoderCounters counters) {
    }

    public static void onVideoDecoderInitialized$$dflt$$(VideoRendererEventListener videoRendererEventListener, String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    }

    public static void onVideoInputFormatChanged$$dflt$$(VideoRendererEventListener videoRendererEventListener, Format format) {
    }

    public static void onDroppedFrames$$dflt$$(VideoRendererEventListener videoRendererEventListener, int count, long elapsedMs) {
    }

    public static void onVideoSizeChanged$$dflt$$(VideoRendererEventListener videoRendererEventListener, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
    }

    public static void onRenderedFirstFrame$$dflt$$(@Nullable VideoRendererEventListener videoRendererEventListener, Surface surface) {
    }

    public static void onVideoDisabled$$dflt$$(VideoRendererEventListener videoRendererEventListener, DecoderCounters counters) {
    }
}
