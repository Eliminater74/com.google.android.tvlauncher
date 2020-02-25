package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;

public abstract class Timeline {
    public static final Timeline EMPTY = new Timeline() {
        public int getWindowCount() {
            return 0;
        }

        public Window getWindow(int windowIndex, Window window, boolean setTag, long defaultPositionProjectionUs) {
            throw new IndexOutOfBoundsException();
        }

        public int getPeriodCount() {
            return 0;
        }

        public Period getPeriod(int periodIndex, Period period, boolean setIds) {
            throw new IndexOutOfBoundsException();
        }

        public int getIndexOfPeriod(Object uid) {
            return -1;
        }

        public Object getUidOfPeriod(int periodIndex) {
            throw new IndexOutOfBoundsException();
        }
    };

    public abstract int getIndexOfPeriod(Object obj);

    public abstract Period getPeriod(int i, Period period, boolean z);

    public abstract int getPeriodCount();

    public abstract Object getUidOfPeriod(int i);

    public abstract Window getWindow(int i, Window window, boolean z, long j);

    public abstract int getWindowCount();

    public final boolean isEmpty() {
        return getWindowCount() == 0;
    }

    public int getNextWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        if (repeatMode != 0) {
            if (repeatMode == 1) {
                return windowIndex;
            }
            if (repeatMode == 2) {
                return windowIndex == getLastWindowIndex(shuffleModeEnabled) ? getFirstWindowIndex(shuffleModeEnabled) : windowIndex + 1;
            }
            throw new IllegalStateException();
        } else if (windowIndex == getLastWindowIndex(shuffleModeEnabled)) {
            return -1;
        } else {
            return windowIndex + 1;
        }
    }

    public int getPreviousWindowIndex(int windowIndex, int repeatMode, boolean shuffleModeEnabled) {
        if (repeatMode != 0) {
            if (repeatMode == 1) {
                return windowIndex;
            }
            if (repeatMode == 2) {
                return windowIndex == getFirstWindowIndex(shuffleModeEnabled) ? getLastWindowIndex(shuffleModeEnabled) : windowIndex - 1;
            }
            throw new IllegalStateException();
        } else if (windowIndex == getFirstWindowIndex(shuffleModeEnabled)) {
            return -1;
        } else {
            return windowIndex - 1;
        }
    }

    public int getLastWindowIndex(boolean shuffleModeEnabled) {
        if (isEmpty()) {
            return -1;
        }
        return getWindowCount() - 1;
    }

    public int getFirstWindowIndex(boolean shuffleModeEnabled) {
        return isEmpty() ? -1 : 0;
    }

    public final Window getWindow(int windowIndex, Window window) {
        return getWindow(windowIndex, window, false);
    }

    public final Window getWindow(int windowIndex, Window window, boolean setTag) {
        return getWindow(windowIndex, window, setTag, 0);
    }

    public final int getNextPeriodIndex(int periodIndex, Period period, Window window, int repeatMode, boolean shuffleModeEnabled) {
        int windowIndex = getPeriod(periodIndex, period).windowIndex;
        if (getWindow(windowIndex, window).lastPeriodIndex != periodIndex) {
            return periodIndex + 1;
        }
        int nextWindowIndex = getNextWindowIndex(windowIndex, repeatMode, shuffleModeEnabled);
        if (nextWindowIndex == -1) {
            return -1;
        }
        return getWindow(nextWindowIndex, window).firstPeriodIndex;
    }

    public final boolean isLastPeriod(int periodIndex, Period period, Window window, int repeatMode, boolean shuffleModeEnabled) {
        return getNextPeriodIndex(periodIndex, period, window, repeatMode, shuffleModeEnabled) == -1;
    }

    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int windowIndex, long windowPositionUs) {
        return (Pair) Assertions.checkNotNull(getPeriodPosition(window, period, windowIndex, windowPositionUs, 0));
    }

    @Nullable
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int windowIndex, long windowPositionUs, long defaultPositionProjectionUs) {
        long windowPositionUs2;
        Window window2 = window;
        Period period2 = period;
        Assertions.checkIndex(windowIndex, 0, getWindowCount());
        getWindow(windowIndex, window, false, defaultPositionProjectionUs);
        if (windowPositionUs == C0841C.TIME_UNSET) {
            windowPositionUs2 = window.getDefaultPositionUs();
            if (windowPositionUs2 == C0841C.TIME_UNSET) {
                return null;
            }
        } else {
            windowPositionUs2 = windowPositionUs;
        }
        int periodIndex = window2.firstPeriodIndex;
        long periodPositionUs = window.getPositionInFirstPeriodUs() + windowPositionUs2;
        long periodDurationUs = getPeriod(periodIndex, period2, true).getDurationUs();
        while (periodDurationUs != C0841C.TIME_UNSET && periodPositionUs >= periodDurationUs && periodIndex < window2.lastPeriodIndex) {
            periodPositionUs -= periodDurationUs;
            periodIndex++;
            periodDurationUs = getPeriod(periodIndex, period2, true).getDurationUs();
        }
        return Pair.create(Assertions.checkNotNull(period2.uid), Long.valueOf(periodPositionUs));
    }

    public Period getPeriodByUid(Object periodUid, Period period) {
        return getPeriod(getIndexOfPeriod(periodUid), period, true);
    }

    public final Period getPeriod(int periodIndex, Period period) {
        return getPeriod(periodIndex, period, false);
    }

    public static final class Window {
        public long defaultPositionUs;
        public long durationUs;
        public int firstPeriodIndex;
        public boolean isDynamic;
        public boolean isSeekable;
        public int lastPeriodIndex;
        public long positionInFirstPeriodUs;
        public long presentationStartTimeMs;
        @Nullable
        public Object tag;
        public long windowStartTimeMs;

        public Window set(@Nullable Object tag2, long presentationStartTimeMs2, long windowStartTimeMs2, boolean isSeekable2, boolean isDynamic2, long defaultPositionUs2, long durationUs2, int firstPeriodIndex2, int lastPeriodIndex2, long positionInFirstPeriodUs2) {
            this.tag = tag2;
            this.presentationStartTimeMs = presentationStartTimeMs2;
            this.windowStartTimeMs = windowStartTimeMs2;
            this.isSeekable = isSeekable2;
            this.isDynamic = isDynamic2;
            this.defaultPositionUs = defaultPositionUs2;
            this.durationUs = durationUs2;
            this.firstPeriodIndex = firstPeriodIndex2;
            this.lastPeriodIndex = lastPeriodIndex2;
            this.positionInFirstPeriodUs = positionInFirstPeriodUs2;
            return this;
        }

        public long getDefaultPositionMs() {
            return C0841C.usToMs(this.defaultPositionUs);
        }

        public long getDefaultPositionUs() {
            return this.defaultPositionUs;
        }

        public long getDurationMs() {
            return C0841C.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInFirstPeriodMs() {
            return C0841C.usToMs(this.positionInFirstPeriodUs);
        }

        public long getPositionInFirstPeriodUs() {
            return this.positionInFirstPeriodUs;
        }
    }

    public static final class Period {
        public long durationUs;
        @Nullable

        /* renamed from: id */
        public Object f74id;
        @Nullable
        public Object uid;
        public int windowIndex;
        private AdPlaybackState adPlaybackState = AdPlaybackState.NONE;
        private long positionInWindowUs;

        public Period set(@Nullable Object id, @Nullable Object uid2, int windowIndex2, long durationUs2, long positionInWindowUs2) {
            return set(id, uid2, windowIndex2, durationUs2, positionInWindowUs2, AdPlaybackState.NONE);
        }

        public Period set(@Nullable Object id, @Nullable Object uid2, int windowIndex2, long durationUs2, long positionInWindowUs2, AdPlaybackState adPlaybackState2) {
            this.f74id = id;
            this.uid = uid2;
            this.windowIndex = windowIndex2;
            this.durationUs = durationUs2;
            this.positionInWindowUs = positionInWindowUs2;
            this.adPlaybackState = adPlaybackState2;
            return this;
        }

        public long getDurationMs() {
            return C0841C.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInWindowMs() {
            return C0841C.usToMs(this.positionInWindowUs);
        }

        public long getPositionInWindowUs() {
            return this.positionInWindowUs;
        }

        public int getAdGroupCount() {
            return this.adPlaybackState.adGroupCount;
        }

        public long getAdGroupTimeUs(int adGroupIndex) {
            return this.adPlaybackState.adGroupTimesUs[adGroupIndex];
        }

        public int getFirstAdIndexToPlay(int adGroupIndex) {
            return this.adPlaybackState.adGroups[adGroupIndex].getFirstAdIndexToPlay();
        }

        public int getNextAdIndexToPlay(int adGroupIndex, int lastPlayedAdIndex) {
            return this.adPlaybackState.adGroups[adGroupIndex].getNextAdIndexToPlay(lastPlayedAdIndex);
        }

        public boolean hasPlayedAdGroup(int adGroupIndex) {
            return !this.adPlaybackState.adGroups[adGroupIndex].hasUnplayedAds();
        }

        public int getAdGroupIndexForPositionUs(long positionUs) {
            return this.adPlaybackState.getAdGroupIndexForPositionUs(positionUs);
        }

        public int getAdGroupIndexAfterPositionUs(long positionUs) {
            return this.adPlaybackState.getAdGroupIndexAfterPositionUs(positionUs, this.durationUs);
        }

        public int getAdCountInAdGroup(int adGroupIndex) {
            return this.adPlaybackState.adGroups[adGroupIndex].count;
        }

        public boolean isAdAvailable(int adGroupIndex, int adIndexInAdGroup) {
            AdPlaybackState.AdGroup adGroup = this.adPlaybackState.adGroups[adGroupIndex];
            return (adGroup.count == -1 || adGroup.states[adIndexInAdGroup] == 0) ? false : true;
        }

        public long getAdDurationUs(int adGroupIndex, int adIndexInAdGroup) {
            AdPlaybackState.AdGroup adGroup = this.adPlaybackState.adGroups[adGroupIndex];
            return adGroup.count != -1 ? adGroup.durationsUs[adIndexInAdGroup] : C0841C.TIME_UNSET;
        }

        public long getAdResumePositionUs() {
            return this.adPlaybackState.adResumePositionUs;
        }
    }
}
