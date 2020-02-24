package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.Timeline;

public abstract class ForwardingTimeline extends Timeline {
    protected final Timeline timeline;

    public ForwardingTimeline(Timeline timeline2) {
        this.timeline = timeline2;
    }

    public int getWindowCount() {
        return this.timeline.getWindowCount();
    }

    public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        return this.timeline.getNextWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
    }

    public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        return this.timeline.getPreviousWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
    }

    public int getLastWindowIndex(boolean shuffleModeEnabled) {
        return this.timeline.getLastWindowIndex(shuffleModeEnabled);
    }

    public int getFirstWindowIndex(boolean shuffleModeEnabled) {
        return this.timeline.getFirstWindowIndex(shuffleModeEnabled);
    }

    public Timeline.Window getWindow(int windowIndex, Timeline.Window window, boolean setTag, long defaultPositionProjectionUs) {
        return this.timeline.getWindow(windowIndex, window, setTag, defaultPositionProjectionUs);
    }

    public int getPeriodCount() {
        return this.timeline.getPeriodCount();
    }

    public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
        return this.timeline.getPeriod(periodIndex, period, setIds);
    }

    public int getIndexOfPeriod(Object uid) {
        return this.timeline.getIndexOfPeriod(uid);
    }

    public Object getUidOfPeriod(int periodIndex) {
        return this.timeline.getUidOfPeriod(periodIndex);
    }
}
