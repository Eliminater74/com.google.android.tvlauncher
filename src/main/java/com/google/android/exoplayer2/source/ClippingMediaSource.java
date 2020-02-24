package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class ClippingMediaSource extends CompositeMediaSource<Void> {
    private final boolean allowDynamicClippingUpdates;
    private IllegalClippingException clippingError;
    private ClippingTimeline clippingTimeline;
    private final boolean enableInitialDiscontinuity;
    private final long endUs;
    @Nullable
    private Object manifest;
    private final ArrayList<ClippingMediaPeriod> mediaPeriods;
    private final MediaSource mediaSource;
    private long periodEndUs;
    private long periodStartUs;
    private final boolean relativeToDefaultPosition;
    private final long startUs;
    private final Timeline.Window window;

    public static final class IllegalClippingException extends IOException {
        public static final int REASON_INVALID_PERIOD_COUNT = 0;
        public static final int REASON_NOT_SEEKABLE_TO_START = 1;
        public static final int REASON_START_EXCEEDS_END = 2;
        public final int reason;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public IllegalClippingException(int r4) {
            /*
                r3 = this;
                java.lang.String r0 = getReasonDescription(r4)
                java.lang.String r0 = java.lang.String.valueOf(r0)
                int r1 = r0.length()
                java.lang.String r2 = "Illegal clipping: "
                if (r1 == 0) goto L_0x0015
                java.lang.String r0 = r2.concat(r0)
                goto L_0x001a
            L_0x0015:
                java.lang.String r0 = new java.lang.String
                r0.<init>(r2)
            L_0x001a:
                r3.<init>(r0)
                r3.reason = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaSource.IllegalClippingException.<init>(int):void");
        }

        private static String getReasonDescription(int reason2) {
            if (reason2 == 0) {
                return "invalid period count";
            }
            if (reason2 == 1) {
                return "not seekable to start";
            }
            if (reason2 != 2) {
                return "unknown";
            }
            return "start exceeds end";
        }
    }

    public ClippingMediaSource(MediaSource mediaSource2, long startPositionUs, long endPositionUs) {
        this(mediaSource2, startPositionUs, endPositionUs, true, false, false);
    }

    public ClippingMediaSource(MediaSource mediaSource2, long durationUs) {
        this(mediaSource2, 0, durationUs, true, false, true);
    }

    public ClippingMediaSource(MediaSource mediaSource2, long startPositionUs, long endPositionUs, boolean enableInitialDiscontinuity2, boolean allowDynamicClippingUpdates2, boolean relativeToDefaultPosition2) {
        Assertions.checkArgument(startPositionUs >= 0);
        this.mediaSource = (MediaSource) Assertions.checkNotNull(mediaSource2);
        this.startUs = startPositionUs;
        this.endUs = endPositionUs;
        this.enableInitialDiscontinuity = enableInitialDiscontinuity2;
        this.allowDynamicClippingUpdates = allowDynamicClippingUpdates2;
        this.relativeToDefaultPosition = relativeToDefaultPosition2;
        this.mediaPeriods = new ArrayList<>();
        this.window = new Timeline.Window();
    }

    @Nullable
    public Object getTag() {
        return this.mediaSource.getTag();
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        super.prepareSourceInternal(mediaTransferListener);
        prepareChildSource(null, this.mediaSource);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalClippingException illegalClippingException = this.clippingError;
        if (illegalClippingException == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw illegalClippingException;
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        ClippingMediaPeriod mediaPeriod = new ClippingMediaPeriod(this.mediaSource.createPeriod(id, allocator, startPositionUs), this.enableInitialDiscontinuity, this.periodStartUs, this.periodEndUs);
        this.mediaPeriods.add(mediaPeriod);
        return mediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.mediaPeriods.remove(mediaPeriod));
        this.mediaSource.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
        if (this.mediaPeriods.isEmpty() && !this.allowDynamicClippingUpdates) {
            refreshClippedTimeline(this.clippingTimeline.timeline);
        }
    }

    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.clippingError = null;
        this.clippingTimeline = null;
    }

    /* access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Void id, MediaSource mediaSource2, Timeline timeline, @Nullable Object manifest2) {
        if (this.clippingError == null) {
            this.manifest = manifest2;
            refreshClippedTimeline(timeline);
        }
    }

    private void refreshClippedTimeline(Timeline timeline) {
        long windowEndUs;
        long windowStartUs;
        timeline.getWindow(0, this.window);
        long windowPositionInPeriodUs = this.window.getPositionInFirstPeriodUs();
        long j = Long.MIN_VALUE;
        if (this.clippingTimeline == null || this.mediaPeriods.isEmpty() || this.allowDynamicClippingUpdates) {
            long windowStartUs2 = this.startUs;
            long windowEndUs2 = this.endUs;
            if (this.relativeToDefaultPosition) {
                long windowDefaultPositionUs = this.window.getDefaultPositionUs();
                windowStartUs2 += windowDefaultPositionUs;
                windowEndUs2 += windowDefaultPositionUs;
            }
            this.periodStartUs = windowPositionInPeriodUs + windowStartUs2;
            if (this.endUs != Long.MIN_VALUE) {
                j = windowPositionInPeriodUs + windowEndUs2;
            }
            this.periodEndUs = j;
            int count = this.mediaPeriods.size();
            for (int i = 0; i < count; i++) {
                this.mediaPeriods.get(i).updateClipping(this.periodStartUs, this.periodEndUs);
            }
            windowStartUs = windowStartUs2;
            windowEndUs = windowEndUs2;
        } else {
            long windowStartUs3 = this.periodStartUs - windowPositionInPeriodUs;
            if (this.endUs != Long.MIN_VALUE) {
                j = this.periodEndUs - windowPositionInPeriodUs;
            }
            windowEndUs = j;
            windowStartUs = windowStartUs3;
        }
        try {
            this.clippingTimeline = new ClippingTimeline(timeline, windowStartUs, windowEndUs);
            refreshSourceInfo(this.clippingTimeline, this.manifest);
        } catch (IllegalClippingException e) {
            this.clippingError = e;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    /* access modifiers changed from: protected */
    public long getMediaTimeForChildMediaTime(Void id, long mediaTimeMs) {
        if (mediaTimeMs == C0841C.TIME_UNSET) {
            return C0841C.TIME_UNSET;
        }
        long startMs = C0841C.usToMs(this.startUs);
        long clippedTimeMs = Math.max(0L, mediaTimeMs - startMs);
        long j = this.endUs;
        if (j != Long.MIN_VALUE) {
            return Math.min(C0841C.usToMs(j) - startMs, clippedTimeMs);
        }
        return clippedTimeMs;
    }

    private static final class ClippingTimeline extends ForwardingTimeline {
        private final long durationUs;
        private final long endUs;
        private final boolean isDynamic;
        private final long startUs;

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.Math.max(long, long):long}
         arg types: [int, long]
         candidates:
          ClspMth{java.lang.Math.max(double, double):double}
          ClspMth{java.lang.Math.max(int, int):int}
          ClspMth{java.lang.Math.max(float, float):float}
          ClspMth{java.lang.Math.max(long, long):long} */
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ClippingTimeline(Timeline timeline, long startUs2, long endUs2) throws IllegalClippingException {
            super(timeline);
            long j = endUs2;
            boolean z = false;
            if (timeline.getPeriodCount() == 1) {
                Timeline.Window window = timeline.getWindow(0, new Timeline.Window());
                long startUs3 = Math.max(0L, startUs2);
                long resolvedEndUs = j == Long.MIN_VALUE ? window.durationUs : Math.max(0L, j);
                if (window.durationUs != C0841C.TIME_UNSET) {
                    resolvedEndUs = resolvedEndUs > window.durationUs ? window.durationUs : resolvedEndUs;
                    if (startUs3 != 0 && !window.isSeekable) {
                        throw new IllegalClippingException(1);
                    } else if (startUs3 > resolvedEndUs) {
                        throw new IllegalClippingException(2);
                    }
                }
                this.startUs = startUs3;
                this.endUs = resolvedEndUs;
                this.durationUs = resolvedEndUs == C0841C.TIME_UNSET ? -9223372036854775807L : resolvedEndUs - startUs3;
                if (window.isDynamic && (resolvedEndUs == C0841C.TIME_UNSET || (window.durationUs != C0841C.TIME_UNSET && resolvedEndUs == window.durationUs))) {
                    z = true;
                }
                this.isDynamic = z;
                return;
            }
            throw new IllegalClippingException(0);
        }

        public Timeline.Window getWindow(int windowIndex, Timeline.Window window, boolean setTag, long defaultPositionProjectionUs) {
            long j;
            this.timeline.getWindow(0, window, setTag, 0);
            window.positionInFirstPeriodUs += this.startUs;
            window.durationUs = this.durationUs;
            window.isDynamic = this.isDynamic;
            if (window.defaultPositionUs != C0841C.TIME_UNSET) {
                window.defaultPositionUs = Math.max(window.defaultPositionUs, this.startUs);
                if (this.endUs == C0841C.TIME_UNSET) {
                    j = window.defaultPositionUs;
                } else {
                    j = Math.min(window.defaultPositionUs, this.endUs);
                }
                window.defaultPositionUs = j;
                window.defaultPositionUs -= this.startUs;
            }
            long startMs = C0841C.usToMs(this.startUs);
            if (window.presentationStartTimeMs != C0841C.TIME_UNSET) {
                window.presentationStartTimeMs += startMs;
            }
            if (window.windowStartTimeMs != C0841C.TIME_UNSET) {
                window.windowStartTimeMs += startMs;
            }
            return window;
        }

        public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
            this.timeline.getPeriod(0, period, setIds);
            long positionInClippedWindowUs = period.getPositionInWindowUs() - this.startUs;
            long j = this.durationUs;
            return period.set(period.f74id, period.uid, 0, j == C0841C.TIME_UNSET ? -9223372036854775807L : j - positionInClippedWindowUs, positionInClippedWindowUs);
        }
    }
}
