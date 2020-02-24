package com.google.android.exoplayer2;

import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;

final class PlaybackInfo {
    private static final MediaSource.MediaPeriodId DUMMY_MEDIA_PERIOD_ID = new MediaSource.MediaPeriodId(new Object());
    public volatile long bufferedPositionUs;
    public final long contentPositionUs;
    public final boolean isLoading;
    public final MediaSource.MediaPeriodId loadingMediaPeriodId;
    @Nullable
    public final Object manifest;
    public final MediaSource.MediaPeriodId periodId;
    public final int playbackState;
    public volatile long positionUs;
    public final long startPositionUs;
    public final Timeline timeline;
    public volatile long totalBufferedDurationUs;
    public final TrackGroupArray trackGroups;
    public final TrackSelectorResult trackSelectorResult;

    public static PlaybackInfo createDummy(long startPositionUs2, TrackSelectorResult emptyTrackSelectorResult) {
        TrackSelectorResult trackSelectorResult2 = emptyTrackSelectorResult;
        return new PlaybackInfo(Timeline.EMPTY, null, DUMMY_MEDIA_PERIOD_ID, startPositionUs2, C0841C.TIME_UNSET, 1, false, TrackGroupArray.EMPTY, trackSelectorResult2, DUMMY_MEDIA_PERIOD_ID, startPositionUs2, 0, startPositionUs2);
    }

    public PlaybackInfo(Timeline timeline2, @Nullable Object manifest2, MediaSource.MediaPeriodId periodId2, long startPositionUs2, long contentPositionUs2, int playbackState2, boolean isLoading2, TrackGroupArray trackGroups2, TrackSelectorResult trackSelectorResult2, MediaSource.MediaPeriodId loadingMediaPeriodId2, long bufferedPositionUs2, long totalBufferedDurationUs2, long positionUs2) {
        this.timeline = timeline2;
        this.manifest = manifest2;
        this.periodId = periodId2;
        this.startPositionUs = startPositionUs2;
        this.contentPositionUs = contentPositionUs2;
        this.playbackState = playbackState2;
        this.isLoading = isLoading2;
        this.trackGroups = trackGroups2;
        this.trackSelectorResult = trackSelectorResult2;
        this.loadingMediaPeriodId = loadingMediaPeriodId2;
        this.bufferedPositionUs = bufferedPositionUs2;
        this.totalBufferedDurationUs = totalBufferedDurationUs2;
        this.positionUs = positionUs2;
    }

    public MediaSource.MediaPeriodId getDummyFirstMediaPeriodId(boolean shuffleModeEnabled, Timeline.Window window) {
        if (this.timeline.isEmpty()) {
            return DUMMY_MEDIA_PERIOD_ID;
        }
        Timeline timeline2 = this.timeline;
        return new MediaSource.MediaPeriodId(this.timeline.getUidOfPeriod(timeline2.getWindow(timeline2.getFirstWindowIndex(shuffleModeEnabled), window).firstPeriodIndex));
    }

    @CheckResult
    public PlaybackInfo resetToNewPosition(MediaSource.MediaPeriodId periodId2, long startPositionUs2, long contentPositionUs2) {
        return new PlaybackInfo(this.timeline, this.manifest, periodId2, startPositionUs2, periodId2.isAd() ? contentPositionUs2 : -9223372036854775807L, this.playbackState, this.isLoading, this.trackGroups, this.trackSelectorResult, periodId2, startPositionUs2, 0, startPositionUs2);
    }

    @CheckResult
    public PlaybackInfo copyWithNewPosition(MediaSource.MediaPeriodId periodId2, long positionUs2, long contentPositionUs2, long totalBufferedDurationUs2) {
        return new PlaybackInfo(this.timeline, this.manifest, periodId2, positionUs2, periodId2.isAd() ? contentPositionUs2 : -9223372036854775807L, this.playbackState, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, totalBufferedDurationUs2, positionUs2);
    }

    @CheckResult
    public PlaybackInfo copyWithTimeline(Timeline timeline2, Object manifest2) {
        return new PlaybackInfo(timeline2, manifest2, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
    }

    @CheckResult
    public PlaybackInfo copyWithPlaybackState(int playbackState2) {
        Timeline timeline2 = this.timeline;
        return new PlaybackInfo(timeline2, this.manifest, this.periodId, this.startPositionUs, this.contentPositionUs, playbackState2, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
    }

    @CheckResult
    public PlaybackInfo copyWithIsLoading(boolean isLoading2) {
        Timeline timeline2 = this.timeline;
        return new PlaybackInfo(timeline2, this.manifest, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, isLoading2, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
    }

    @CheckResult
    public PlaybackInfo copyWithTrackInfo(TrackGroupArray trackGroups2, TrackSelectorResult trackSelectorResult2) {
        Timeline timeline2 = this.timeline;
        return new PlaybackInfo(timeline2, this.manifest, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.isLoading, trackGroups2, trackSelectorResult2, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
    }

    @CheckResult
    public PlaybackInfo copyWithLoadingMediaPeriodId(MediaSource.MediaPeriodId loadingMediaPeriodId2) {
        Timeline timeline2 = this.timeline;
        return new PlaybackInfo(timeline2, this.manifest, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.isLoading, this.trackGroups, this.trackSelectorResult, loadingMediaPeriodId2, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
    }
}
