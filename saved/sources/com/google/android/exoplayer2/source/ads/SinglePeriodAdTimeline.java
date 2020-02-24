package com.google.android.exoplayer2.source.ads;

import android.support.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.util.Assertions;

@VisibleForTesting(otherwise = 3)
public final class SinglePeriodAdTimeline extends ForwardingTimeline {
    private final AdPlaybackState adPlaybackState;

    public SinglePeriodAdTimeline(Timeline contentTimeline, AdPlaybackState adPlaybackState2) {
        super(contentTimeline);
        boolean z = false;
        Assertions.checkState(contentTimeline.getPeriodCount() == 1);
        Assertions.checkState(contentTimeline.getWindowCount() == 1 ? true : z);
        this.adPlaybackState = adPlaybackState2;
    }

    public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
        this.timeline.getPeriod(periodIndex, period, setIds);
        period.set(period.f74id, period.uid, period.windowIndex, period.durationUs, period.getPositionInWindowUs(), this.adPlaybackState);
        return period;
    }

    public Timeline.Window getWindow(int windowIndex, Timeline.Window window, boolean setTag, long defaultPositionProjectionUs) {
        Timeline.Window window2 = super.getWindow(windowIndex, window, setTag, defaultPositionProjectionUs);
        if (window2.durationUs == C0841C.TIME_UNSET) {
            window2.durationUs = this.adPlaybackState.contentDurationUs;
        }
        return window2;
    }
}
