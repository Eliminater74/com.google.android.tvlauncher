package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;

public abstract /* synthetic */ class AudioRendererEventListener$$CC {
    public static void onAudioEnabled$$dflt$$(AudioRendererEventListener audioRendererEventListener, DecoderCounters counters) {
    }

    public static void onAudioSessionId$$dflt$$(AudioRendererEventListener audioRendererEventListener, int audioSessionId) {
    }

    public static void onAudioDecoderInitialized$$dflt$$(AudioRendererEventListener audioRendererEventListener, String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    }

    public static void onAudioInputFormatChanged$$dflt$$(AudioRendererEventListener audioRendererEventListener, Format format) {
    }

    public static void onAudioSinkUnderrun$$dflt$$(AudioRendererEventListener audioRendererEventListener, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    }

    public static void onAudioDisabled$$dflt$$(AudioRendererEventListener audioRendererEventListener, DecoderCounters counters) {
    }
}
