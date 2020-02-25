package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;

final class MediaPeriodQueue {
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;
    private final Timeline.Period period = new Timeline.Period();
    private final Timeline.Window window = new Timeline.Window();
    private int length;
    @Nullable
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;
    @Nullable
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;
    @Nullable
    private MediaPeriodHolder playing;
    @Nullable
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private Timeline timeline = Timeline.EMPTY;

    public void setTimeline(Timeline timeline2) {
        this.timeline = timeline2;
    }

    public boolean updateRepeatMode(int repeatMode2) {
        this.repeatMode = repeatMode2;
        return updateForPlaybackModeChange();
    }

    public boolean updateShuffleModeEnabled(boolean shuffleModeEnabled2) {
        this.shuffleModeEnabled = shuffleModeEnabled2;
        return updateForPlaybackModeChange();
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder != null && mediaPeriodHolder.mediaPeriod == mediaPeriod;
    }

    public void reevaluateBuffer(long rendererPositionUs) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder != null) {
            mediaPeriodHolder.reevaluateBuffer(rendererPositionUs);
        }
    }

    public boolean shouldLoadNextMediaPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder == null || (!mediaPeriodHolder.info.isFinal && this.loading.isFullyBuffered() && this.loading.info.durationUs != C0841C.TIME_UNSET && this.length < 100);
    }

    @Nullable
    public MediaPeriodInfo getNextMediaPeriodInfo(long rendererPositionUs, PlaybackInfo playbackInfo) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder == null) {
            return getFirstMediaPeriodInfo(playbackInfo);
        }
        return getFollowingMediaPeriodInfo(mediaPeriodHolder, rendererPositionUs);
    }

    public MediaPeriod enqueueNextMediaPeriod(RendererCapabilities[] rendererCapabilities, TrackSelector trackSelector, Allocator allocator, MediaSource mediaSource, MediaPeriodInfo info) {
        long rendererPositionOffsetUs;
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder == null) {
            rendererPositionOffsetUs = info.startPositionUs;
        } else {
            rendererPositionOffsetUs = mediaPeriodHolder.getRendererOffset() + this.loading.info.durationUs;
        }
        MediaPeriodHolder mediaPeriodHolder2 = new MediaPeriodHolder(rendererCapabilities, rendererPositionOffsetUs, trackSelector, allocator, mediaSource, info);
        if (this.loading != null) {
            Assertions.checkState(hasPlayingPeriod());
            this.loading.setNext(mediaPeriodHolder2);
        }
        this.oldFrontPeriodUid = null;
        this.loading = mediaPeriodHolder2;
        this.length++;
        return mediaPeriodHolder2.mediaPeriod;
    }

    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    public MediaPeriodHolder getFrontPeriod() {
        return hasPlayingPeriod() ? this.playing : this.loading;
    }

    public boolean hasPlayingPeriod() {
        return this.playing != null;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.reading;
        Assertions.checkState((mediaPeriodHolder == null || mediaPeriodHolder.getNext() == null) ? false : true);
        this.reading = this.reading.getNext();
        return this.reading;
    }

    public MediaPeriodHolder advancePlayingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder != null) {
            if (mediaPeriodHolder == this.reading) {
                this.reading = mediaPeriodHolder.getNext();
            }
            this.playing.release();
            this.length--;
            if (this.length == 0) {
                this.loading = null;
                this.oldFrontPeriodUid = this.playing.uid;
                this.oldFrontPeriodWindowSequenceNumber = this.playing.info.f73id.windowSequenceNumber;
            }
            this.playing = this.playing.getNext();
        } else {
            MediaPeriodHolder mediaPeriodHolder2 = this.loading;
            this.playing = mediaPeriodHolder2;
            this.reading = mediaPeriodHolder2;
        }
        return this.playing;
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        Assertions.checkState(mediaPeriodHolder != null);
        boolean removedReading = false;
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.getNext() != null) {
            mediaPeriodHolder = mediaPeriodHolder.getNext();
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                removedReading = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.setNext(null);
        return removedReading;
    }

    public void clear(boolean keepFrontPeriodUid) {
        MediaPeriodHolder front = getFrontPeriod();
        if (front != null) {
            this.oldFrontPeriodUid = keepFrontPeriodUid ? front.uid : null;
            this.oldFrontPeriodWindowSequenceNumber = front.info.f73id.windowSequenceNumber;
            front.release();
            removeAfter(front);
        } else if (!keepFrontPeriodUid) {
            this.oldFrontPeriodUid = null;
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = 0;
    }

    public boolean updateQueuedPeriods(long rendererPositionUs, long maxRendererReadPositionUs) {
        MediaPeriodInfo newPeriodInfo;
        long newDurationInRendererTime;
        MediaPeriodHolder previousPeriodHolder = null;
        MediaPeriodHolder periodHolder = getFrontPeriod();
        while (periodHolder != null) {
            MediaPeriodInfo oldPeriodInfo = periodHolder.info;
            if (previousPeriodHolder == null) {
                newPeriodInfo = getUpdatedMediaPeriodInfo(oldPeriodInfo);
            } else {
                newPeriodInfo = getFollowingMediaPeriodInfo(previousPeriodHolder, rendererPositionUs);
                if (newPeriodInfo == null) {
                    return true ^ removeAfter(previousPeriodHolder);
                }
                if (!canKeepMediaPeriodHolder(oldPeriodInfo, newPeriodInfo)) {
                    return true ^ removeAfter(previousPeriodHolder);
                }
            }
            periodHolder.info = newPeriodInfo.copyWithContentPositionUs(oldPeriodInfo.contentPositionUs);
            if (!areDurationsCompatible(oldPeriodInfo.durationUs, newPeriodInfo.durationUs)) {
                if (newPeriodInfo.durationUs == C0841C.TIME_UNSET) {
                    newDurationInRendererTime = Long.MAX_VALUE;
                } else {
                    newDurationInRendererTime = periodHolder.toRendererTime(newPeriodInfo.durationUs);
                }
                boolean isReadingAndReadBeyondNewDuration = periodHolder == this.reading && (maxRendererReadPositionUs == Long.MIN_VALUE || maxRendererReadPositionUs >= newDurationInRendererTime);
                if (removeAfter(periodHolder) || isReadingAndReadBeyondNewDuration) {
                    return false;
                }
                return true;
            }
            previousPeriodHolder = periodHolder;
            periodHolder = periodHolder.getNext();
        }
        return true;
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo info) {
        long durationUs;
        MediaPeriodInfo mediaPeriodInfo = info;
        MediaSource.MediaPeriodId id = mediaPeriodInfo.f73id;
        boolean isLastInPeriod = isLastInPeriod(id);
        boolean isLastInTimeline = isLastInTimeline(id, isLastInPeriod);
        this.timeline.getPeriodByUid(mediaPeriodInfo.f73id.periodUid, this.period);
        if (id.isAd()) {
            durationUs = this.period.getAdDurationUs(id.adGroupIndex, id.adIndexInAdGroup);
        } else if (mediaPeriodInfo.endPositionUs == C0841C.TIME_UNSET || mediaPeriodInfo.endPositionUs == Long.MIN_VALUE) {
            durationUs = this.period.getDurationUs();
        } else {
            durationUs = mediaPeriodInfo.endPositionUs;
        }
        return new MediaPeriodInfo(id, mediaPeriodInfo.startPositionUs, mediaPeriodInfo.contentPositionUs, mediaPeriodInfo.endPositionUs, durationUs, isLastInPeriod, isLastInTimeline);
    }

    public MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Object periodUid, long positionUs) {
        return resolveMediaPeriodIdForAds(periodUid, positionUs, resolvePeriodIndexToWindowSequenceNumber(periodUid));
    }

    private MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Object periodUid, long positionUs, long windowSequenceNumber) {
        this.timeline.getPeriodByUid(periodUid, this.period);
        int adGroupIndex = this.period.getAdGroupIndexForPositionUs(positionUs);
        if (adGroupIndex == -1) {
            return new MediaSource.MediaPeriodId(periodUid, windowSequenceNumber, this.period.getAdGroupIndexAfterPositionUs(positionUs));
        }
        return new MediaSource.MediaPeriodId(periodUid, adGroupIndex, this.period.getFirstAdIndexToPlay(adGroupIndex), windowSequenceNumber);
    }

    private long resolvePeriodIndexToWindowSequenceNumber(Object periodUid) {
        int oldFrontPeriodIndex;
        int windowIndex = this.timeline.getPeriodByUid(periodUid, this.period).windowIndex;
        Object obj = this.oldFrontPeriodUid;
        if (obj != null && (oldFrontPeriodIndex = this.timeline.getIndexOfPeriod(obj)) != -1 && this.timeline.getPeriod(oldFrontPeriodIndex, this.period).windowIndex == windowIndex) {
            return this.oldFrontPeriodWindowSequenceNumber;
        }
        for (MediaPeriodHolder mediaPeriodHolder = getFrontPeriod(); mediaPeriodHolder != null; mediaPeriodHolder = mediaPeriodHolder.getNext()) {
            if (mediaPeriodHolder.uid.equals(periodUid)) {
                return mediaPeriodHolder.info.f73id.windowSequenceNumber;
            }
        }
        for (MediaPeriodHolder mediaPeriodHolder2 = getFrontPeriod(); mediaPeriodHolder2 != null; mediaPeriodHolder2 = mediaPeriodHolder2.getNext()) {
            int indexOfHolderInTimeline = this.timeline.getIndexOfPeriod(mediaPeriodHolder2.uid);
            if (indexOfHolderInTimeline != -1 && this.timeline.getPeriod(indexOfHolderInTimeline, this.period).windowIndex == windowIndex) {
                return mediaPeriodHolder2.info.f73id.windowSequenceNumber;
            }
        }
        long j = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j;
        return j;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodInfo oldInfo, MediaPeriodInfo newInfo) {
        return oldInfo.startPositionUs == newInfo.startPositionUs && oldInfo.f73id.equals(newInfo.f73id);
    }

    private boolean areDurationsCompatible(long previousDurationUs, long newDurationUs) {
        return previousDurationUs == C0841C.TIME_UNSET || previousDurationUs == newDurationUs;
    }

    private boolean updateForPlaybackModeChange() {
        MediaPeriodHolder lastValidPeriodHolder = getFrontPeriod();
        if (lastValidPeriodHolder == null) {
            return true;
        }
        int currentPeriodIndex = this.timeline.getIndexOfPeriod(lastValidPeriodHolder.uid);
        while (true) {
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(currentPeriodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (lastValidPeriodHolder.getNext() != null && !lastValidPeriodHolder.info.isLastInTimelinePeriod) {
                lastValidPeriodHolder = lastValidPeriodHolder.getNext();
            }
            MediaPeriodHolder nextMediaPeriodHolder = lastValidPeriodHolder.getNext();
            if (nextPeriodIndex == -1 || nextMediaPeriodHolder == null || this.timeline.getIndexOfPeriod(nextMediaPeriodHolder.uid) != nextPeriodIndex) {
                boolean readingPeriodRemoved = removeAfter(lastValidPeriodHolder);
                lastValidPeriodHolder.info = getUpdatedMediaPeriodInfo(lastValidPeriodHolder.info);
            } else {
                lastValidPeriodHolder = nextMediaPeriodHolder;
                currentPeriodIndex = nextPeriodIndex;
            }
        }
        boolean readingPeriodRemoved2 = removeAfter(lastValidPeriodHolder);
        lastValidPeriodHolder.info = getUpdatedMediaPeriodInfo(lastValidPeriodHolder.info);
        if (!readingPeriodRemoved2 || !hasPlayingPeriod()) {
            return true;
        }
        return false;
    }

    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.periodId, playbackInfo.contentPositionUs, playbackInfo.startPositionUs);
    }

    /* JADX INFO: Multiple debug info for r13v0 com.google.android.exoplayer2.source.MediaSource$MediaPeriodId: [D('startPositionUs' long), D('currentPeriodId' com.google.android.exoplayer2.source.MediaSource$MediaPeriodId)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    @Nullable
    private MediaPeriodInfo getFollowingMediaPeriodInfo(MediaPeriodHolder mediaPeriodHolder, long rendererPositionUs) {
        long startPositionUs;
        long windowSequenceNumber;
        Object nextPeriodUid;
        long startPositionUs2;
        long windowSequenceNumber2;
        MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolder.info;
        long bufferedDurationUs = (mediaPeriodHolder.getRendererOffset() + mediaPeriodInfo.durationUs) - rendererPositionUs;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(this.timeline.getIndexOfPeriod(mediaPeriodInfo.f73id.periodUid), this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int nextWindowIndex = this.timeline.getPeriod(nextPeriodIndex, this.period, true).windowIndex;
            Object nextPeriodUid2 = this.period.uid;
            long windowSequenceNumber3 = mediaPeriodInfo.f73id.windowSequenceNumber;
            if (this.timeline.getWindow(nextWindowIndex, this.window).firstPeriodIndex == nextPeriodIndex) {
                Pair<Object, Long> defaultPosition = this.timeline.getPeriodPosition(this.window, this.period, nextWindowIndex, C0841C.TIME_UNSET, Math.max(0L, bufferedDurationUs));
                if (defaultPosition == null) {
                    return null;
                }
                Object nextPeriodUid3 = defaultPosition.first;
                startPositionUs2 = ((Long) defaultPosition.second).longValue();
                MediaPeriodHolder nextMediaPeriodHolder = mediaPeriodHolder.getNext();
                if (nextMediaPeriodHolder == null || !nextMediaPeriodHolder.uid.equals(nextPeriodUid3)) {
                    nextPeriodUid = nextPeriodUid3;
                    long j = this.nextWindowSequenceNumber;
                    this.nextWindowSequenceNumber = j + 1;
                    windowSequenceNumber2 = j;
                } else {
                    windowSequenceNumber2 = nextMediaPeriodHolder.info.f73id.windowSequenceNumber;
                    nextPeriodUid = nextPeriodUid3;
                }
                windowSequenceNumber = windowSequenceNumber2;
            } else {
                windowSequenceNumber = windowSequenceNumber3;
                nextPeriodUid = nextPeriodUid2;
                startPositionUs2 = 0;
            }
            long j2 = startPositionUs2;
            return getMediaPeriodInfo(resolveMediaPeriodIdForAds(nextPeriodUid, j2, windowSequenceNumber), j2, startPositionUs2);
        }
        MediaSource.MediaPeriodId currentPeriodId = mediaPeriodInfo.f73id;
        this.timeline.getPeriodByUid(currentPeriodId.periodUid, this.period);
        if (currentPeriodId.isAd()) {
            int adGroupIndex = currentPeriodId.adGroupIndex;
            int adCountInCurrentAdGroup = this.period.getAdCountInAdGroup(adGroupIndex);
            if (adCountInCurrentAdGroup == -1) {
                return null;
            }
            int nextAdIndexInAdGroup = this.period.getNextAdIndexToPlay(adGroupIndex, currentPeriodId.adIndexInAdGroup);
            if (nextAdIndexInAdGroup >= adCountInCurrentAdGroup) {
                long startPositionUs3 = mediaPeriodInfo.contentPositionUs;
                if (this.period.getAdGroupCount() == 1 && this.period.getAdGroupTimeUs(0) == 0) {
                    Timeline timeline2 = this.timeline;
                    Timeline.Window window2 = this.window;
                    Timeline.Period period2 = this.period;
                    Pair<Object, Long> defaultPosition2 = timeline2.getPeriodPosition(window2, period2, period2.windowIndex, C0841C.TIME_UNSET, Math.max(0L, bufferedDurationUs));
                    if (defaultPosition2 == null) {
                        return null;
                    }
                    startPositionUs = ((Long) defaultPosition2.second).longValue();
                } else {
                    startPositionUs = startPositionUs3;
                }
                return getMediaPeriodInfoForContent(currentPeriodId.periodUid, startPositionUs, currentPeriodId.windowSequenceNumber);
            } else if (!this.period.isAdAvailable(adGroupIndex, nextAdIndexInAdGroup)) {
                return null;
            } else {
                return getMediaPeriodInfoForAd(currentPeriodId.periodUid, adGroupIndex, nextAdIndexInAdGroup, mediaPeriodInfo.contentPositionUs, currentPeriodId.windowSequenceNumber);
            }
        } else {
            int nextAdGroupIndex = this.period.getAdGroupIndexForPositionUs(mediaPeriodInfo.endPositionUs);
            if (nextAdGroupIndex == -1) {
                return getMediaPeriodInfoForContent(currentPeriodId.periodUid, mediaPeriodInfo.durationUs, currentPeriodId.windowSequenceNumber);
            }
            int adIndexInAdGroup = this.period.getFirstAdIndexToPlay(nextAdGroupIndex);
            if (!this.period.isAdAvailable(nextAdGroupIndex, adIndexInAdGroup)) {
                return null;
            }
            return getMediaPeriodInfoForAd(currentPeriodId.periodUid, nextAdGroupIndex, adIndexInAdGroup, mediaPeriodInfo.durationUs, currentPeriodId.windowSequenceNumber);
        }
    }

    private MediaPeriodInfo getMediaPeriodInfo(MediaSource.MediaPeriodId id, long contentPositionUs, long startPositionUs) {
        this.timeline.getPeriodByUid(id.periodUid, this.period);
        if (!id.isAd()) {
            return getMediaPeriodInfoForContent(id.periodUid, startPositionUs, id.windowSequenceNumber);
        } else if (!this.period.isAdAvailable(id.adGroupIndex, id.adIndexInAdGroup)) {
            return null;
        } else {
            return getMediaPeriodInfoForAd(id.periodUid, id.adGroupIndex, id.adIndexInAdGroup, contentPositionUs, id.windowSequenceNumber);
        }
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(Object periodUid, int adGroupIndex, int adIndexInAdGroup, long contentPositionUs, long windowSequenceNumber) {
        long startPositionUs;
        MediaSource.MediaPeriodId id = new MediaSource.MediaPeriodId(periodUid, adGroupIndex, adIndexInAdGroup, windowSequenceNumber);
        long durationUs = this.timeline.getPeriodByUid(id.periodUid, this.period).getAdDurationUs(id.adGroupIndex, id.adIndexInAdGroup);
        if (adIndexInAdGroup == this.period.getFirstAdIndexToPlay(adGroupIndex)) {
            startPositionUs = this.period.getAdResumePositionUs();
        } else {
            startPositionUs = 0;
        }
        return new MediaPeriodInfo(id, startPositionUs, contentPositionUs, C0841C.TIME_UNSET, durationUs, false, false);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(Object periodUid, long startPositionUs, long windowSequenceNumber) {
        long j;
        long durationUs;
        int nextAdGroupIndex = this.period.getAdGroupIndexAfterPositionUs(startPositionUs);
        MediaSource.MediaPeriodId id = new MediaSource.MediaPeriodId(periodUid, windowSequenceNumber, nextAdGroupIndex);
        boolean isLastInPeriod = isLastInPeriod(id);
        boolean isLastInTimeline = isLastInTimeline(id, isLastInPeriod);
        if (nextAdGroupIndex != -1) {
            j = this.period.getAdGroupTimeUs(nextAdGroupIndex);
        } else {
            j = -9223372036854775807L;
        }
        long endPositionUs = j;
        if (endPositionUs == C0841C.TIME_UNSET || endPositionUs == Long.MIN_VALUE) {
            durationUs = this.period.durationUs;
        } else {
            durationUs = endPositionUs;
        }
        return new MediaPeriodInfo(id, startPositionUs, C0841C.TIME_UNSET, endPositionUs, durationUs, isLastInPeriod, isLastInTimeline);
    }

    private boolean isLastInPeriod(MediaSource.MediaPeriodId id) {
        return !id.isAd() && id.nextAdGroupIndex == -1;
    }

    private boolean isLastInTimeline(MediaSource.MediaPeriodId id, boolean isLastMediaPeriodInPeriod) {
        int periodIndex = this.timeline.getIndexOfPeriod(id.periodUid);
        if (!this.timeline.getWindow(this.timeline.getPeriod(periodIndex, this.period).windowIndex, this.window).isDynamic) {
            return this.timeline.isLastPeriod(periodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) && isLastMediaPeriodInPeriod;
        }
    }
}
