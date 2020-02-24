package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Surface;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.Assertions;

public interface VideoRendererEventListener {
    void onDroppedFrames(int i, long j);

    void onRenderedFirstFrame(@Nullable Surface surface);

    void onVideoDecoderInitialized(String str, long j, long j2);

    void onVideoDisabled(DecoderCounters decoderCounters);

    void onVideoEnabled(DecoderCounters decoderCounters);

    void onVideoInputFormatChanged(Format format);

    void onVideoSizeChanged(int i, int i2, int i3, float f);

    public static final class EventDispatcher {
        @Nullable
        private final Handler handler;
        @Nullable
        private final VideoRendererEventListener listener;

        public EventDispatcher(@Nullable Handler handler2, @Nullable VideoRendererEventListener listener2) {
            this.handler = listener2 != null ? (Handler) Assertions.checkNotNull(handler2) : null;
            this.listener = listener2;
        }

        public void enabled(DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$0(this, decoderCounters));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$enabled$0$VideoRendererEventListener$EventDispatcher(DecoderCounters decoderCounters) {
            this.listener.onVideoEnabled(decoderCounters);
        }

        public void decoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$1(this, decoderName, initializedTimestampMs, initializationDurationMs));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$decoderInitialized$1$VideoRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo15870x9a08f997(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            this.listener.onVideoDecoderInitialized(decoderName, initializedTimestampMs, initializationDurationMs);
        }

        public void inputFormatChanged(Format format) {
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$2(this, format));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$inputFormatChanged$2$VideoRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo15874xe7570b3(Format format) {
            this.listener.onVideoInputFormatChanged(format);
        }

        public void droppedFrames(int droppedFrameCount, long elapsedMs) {
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$3(this, droppedFrameCount, elapsedMs));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$droppedFrames$3$VideoRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo15872xf7e95759(int droppedFrameCount, long elapsedMs) {
            this.listener.onDroppedFrames(droppedFrameCount, elapsedMs);
        }

        public void videoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$4(this, width, height, unappliedRotationDegrees, pixelWidthHeightRatio));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$videoSizeChanged$4$VideoRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo15876x6ff94f6c(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            this.listener.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
        }

        public void renderedFirstFrame(@Nullable Surface surface) {
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$5(this, surface));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$renderedFirstFrame$5$VideoRendererEventListener$EventDispatcher */
        public final /* synthetic */ void mo15875x44bb7f11(Surface surface) {
            this.listener.onRenderedFirstFrame(surface);
        }

        public void disabled(DecoderCounters counters) {
            counters.ensureUpdated();
            if (this.listener != null) {
                this.handler.post(new VideoRendererEventListener$EventDispatcher$$Lambda$6(this, counters));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$disabled$6$VideoRendererEventListener$EventDispatcher(DecoderCounters counters) {
            counters.ensureUpdated();
            this.listener.onVideoDisabled(counters);
        }
    }
}
