package com.google.android.exoplayer2.audio;

import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.Assertions;

public interface AudioRendererEventListener {
    void onAudioDecoderInitialized(String str, long j, long j2);

    void onAudioDisabled(DecoderCounters decoderCounters);

    void onAudioEnabled(DecoderCounters decoderCounters);

    void onAudioInputFormatChanged(Format format);

    void onAudioSessionId(int i);

    void onAudioSinkUnderrun(int i, long j, long j2);

    public static final class EventDispatcher {
        @Nullable
        private final Handler handler;
        @Nullable
        private final AudioRendererEventListener listener;

        public EventDispatcher(@Nullable Handler handler2, @Nullable AudioRendererEventListener listener2) {
            this.handler = listener2 != null ? (Handler) Assertions.checkNotNull(handler2) : null;
            this.listener = listener2;
        }

        public void enabled(DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new AudioRendererEventListener$EventDispatcher$$Lambda$0(this, decoderCounters));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$enabled$0$AudioRendererEventListener$EventDispatcher(DecoderCounters decoderCounters) {
            this.listener.onAudioEnabled(decoderCounters);
        }

        public void decoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            if (this.listener != null) {
                this.handler.post(new AudioRendererEventListener$EventDispatcher$$Lambda$1(this, decoderName, initializedTimestampMs, initializationDurationMs));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$decoderInitialized$1$AudioRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo13434xba417f1c(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            this.listener.onAudioDecoderInitialized(decoderName, initializedTimestampMs, initializationDurationMs);
        }

        public void inputFormatChanged(Format format) {
            if (this.listener != null) {
                this.handler.post(new AudioRendererEventListener$EventDispatcher$$Lambda$2(this, format));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$inputFormatChanged$2$AudioRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo13437x2eadf638(Format format) {
            this.listener.onAudioInputFormatChanged(format);
        }

        public void audioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            if (this.listener != null) {
                this.handler.post(new AudioRendererEventListener$EventDispatcher$$Lambda$3(this, bufferSize, bufferSizeMs, elapsedSinceLastFeedMs));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$audioTrackUnderrun$3$AudioRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo13433xe45e91e2(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            this.listener.onAudioSinkUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }

        public void disabled(DecoderCounters counters) {
            counters.ensureUpdated();
            if (this.listener != null) {
                this.handler.post(new AudioRendererEventListener$EventDispatcher$$Lambda$4(this, counters));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$disabled$4$AudioRendererEventListener$EventDispatcher(DecoderCounters counters) {
            counters.ensureUpdated();
            this.listener.onAudioDisabled(counters);
        }

        public void audioSessionId(int audioSessionId) {
            if (this.listener != null) {
                this.handler.post(new AudioRendererEventListener$EventDispatcher$$Lambda$5(this, audioSessionId));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$audioSessionId$5$AudioRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo13432xc1c634cd(int audioSessionId) {
            this.listener.onAudioSessionId(audioSessionId);
        }
    }
}
