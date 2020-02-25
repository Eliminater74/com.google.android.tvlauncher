package com.google.android.exoplayer2.analytics;

import android.support.annotation.Nullable;
import android.view.Surface;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import org.checkerframework.checker.nullness.qual.RequiresNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class AnalyticsCollector implements Player.EventListener, MetadataOutput, AudioRendererEventListener, VideoRendererEventListener, MediaSourceEventListener, BandwidthMeter.EventListener, DefaultDrmSessionEventListener, VideoListener, AudioListener {
    private final Clock clock;
    private final CopyOnWriteArraySet<AnalyticsListener> listeners;
    private final MediaPeriodQueueTracker mediaPeriodQueueTracker;
    private final Timeline.Window window;
    private Player player;

    protected AnalyticsCollector(@Nullable Player player2, Clock clock2) {
        if (player2 != null) {
            this.player = player2;
        }
        this.clock = (Clock) Assertions.checkNotNull(clock2);
        this.listeners = new CopyOnWriteArraySet<>();
        this.mediaPeriodQueueTracker = new MediaPeriodQueueTracker();
        this.window = new Timeline.Window();
    }

    public void addListener(AnalyticsListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(AnalyticsListener listener) {
        this.listeners.remove(listener);
    }

    public void setPlayer(Player player2) {
        Assertions.checkState(this.player == null || this.mediaPeriodQueueTracker.mediaPeriodInfoQueue.isEmpty());
        this.player = (Player) Assertions.checkNotNull(player2);
    }

    public final void notifySeekStarted() {
        if (!this.mediaPeriodQueueTracker.isSeeking()) {
            AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
            this.mediaPeriodQueueTracker.onSeekStarted();
            Iterator<AnalyticsListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onSeekStarted(eventTime);
            }
        }
    }

    public final void resetForNewMediaSource() {
        for (MediaPeriodInfo mediaPeriodInfo : new ArrayList<>(this.mediaPeriodQueueTracker.mediaPeriodInfoQueue)) {
            onMediaPeriodReleased(mediaPeriodInfo.windowIndex, mediaPeriodInfo.mediaPeriodId);
        }
    }

    public final void onMetadata(Metadata metadata) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onMetadata(eventTime, metadata);
        }
    }

    public final void onAudioEnabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderEnabled(eventTime, 1, counters);
        }
    }

    public final void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderInitialized(eventTime, 1, decoderName, initializationDurationMs);
        }
    }

    public final void onAudioInputFormatChanged(Format format) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderInputFormatChanged(eventTime, 1, format);
        }
    }

    public final void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioUnderrun(eventTime, bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }
    }

    public final void onAudioDisabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderDisabled(eventTime, 1, counters);
        }
    }

    public final void onAudioSessionId(int audioSessionId) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioSessionId(eventTime, audioSessionId);
        }
    }

    public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioAttributesChanged(eventTime, audioAttributes);
        }
    }

    public void onVolumeChanged(float audioVolume) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(eventTime, audioVolume);
        }
    }

    public final void onVideoEnabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderEnabled(eventTime, 2, counters);
        }
    }

    public final void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderInitialized(eventTime, 2, decoderName, initializationDurationMs);
        }
    }

    public final void onVideoInputFormatChanged(Format format) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderInputFormatChanged(eventTime, 2, format);
        }
    }

    public final void onDroppedFrames(int count, long elapsedMs) {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDroppedVideoFrames(eventTime, count, elapsedMs);
        }
    }

    public final void onVideoDisabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDecoderDisabled(eventTime, 2, counters);
        }
    }

    public final void onRenderedFirstFrame(@Nullable Surface surface) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onRenderedFirstFrame(eventTime, surface);
        }
    }

    public final void onRenderedFirstFrame() {
    }

    public final void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoSizeChanged(eventTime, width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
        }
    }

    public void onSurfaceSizeChanged(int width, int height) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onSurfaceSizeChanged(eventTime, width, height);
        }
    }

    public final void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onMediaPeriodCreated(windowIndex, mediaPeriodId);
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onMediaPeriodCreated(eventTime);
        }
    }

    public final void onMediaPeriodReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        if (this.mediaPeriodQueueTracker.onMediaPeriodReleased(mediaPeriodId)) {
            Iterator<AnalyticsListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onMediaPeriodReleased(eventTime);
            }
        }
    }

    public final void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onLoadStarted(eventTime, loadEventInfo, mediaLoadData);
        }
    }

    public final void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onLoadCompleted(eventTime, loadEventInfo, mediaLoadData);
        }
    }

    public final void onLoadCanceled(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onLoadCanceled(eventTime, loadEventInfo, mediaLoadData);
        }
    }

    public final void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onLoadError(eventTime, loadEventInfo, mediaLoadData, error, wasCanceled);
        }
    }

    public final void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onReadingStarted(mediaPeriodId);
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onReadingStarted(eventTime);
        }
    }

    public final void onUpstreamDiscarded(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onUpstreamDiscarded(eventTime, mediaLoadData);
        }
    }

    public final void onDownstreamFormatChanged(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateMediaPeriodEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDownstreamFormatChanged(eventTime, mediaLoadData);
        }
    }

    public final void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        this.mediaPeriodQueueTracker.onTimelineChanged(timeline);
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onTimelineChanged(eventTime, reason);
        }
    }

    public final void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onTracksChanged(eventTime, trackGroups, trackSelections);
        }
    }

    public final void onLoadingChanged(boolean isLoading) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onLoadingChanged(eventTime, isLoading);
        }
    }

    public final void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPlayerStateChanged(eventTime, playWhenReady, playbackState);
        }
    }

    public final void onRepeatModeChanged(int repeatMode) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onRepeatModeChanged(eventTime, repeatMode);
        }
    }

    public final void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onShuffleModeChanged(eventTime, shuffleModeEnabled);
        }
    }

    public final void onPlayerError(ExoPlaybackException error) {
        AnalyticsListener.EventTime eventTime;
        if (error.type == 0) {
            eventTime = generateLoadingMediaPeriodEventTime();
        } else {
            eventTime = generatePlayingMediaPeriodEventTime();
        }
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPlayerError(eventTime, error);
        }
    }

    public final void onPositionDiscontinuity(int reason) {
        this.mediaPeriodQueueTracker.onPositionDiscontinuity(reason);
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPositionDiscontinuity(eventTime, reason);
        }
    }

    public final void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onPlaybackParametersChanged(eventTime, playbackParameters);
        }
    }

    public final void onSeekProcessed() {
        if (this.mediaPeriodQueueTracker.isSeeking()) {
            this.mediaPeriodQueueTracker.onSeekProcessed();
            AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
            Iterator<AnalyticsListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onSeekProcessed(eventTime);
            }
        }
    }

    public final void onBandwidthSample(int elapsedMs, long bytes, long bitrate) {
        AnalyticsListener.EventTime eventTime = generateLoadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onBandwidthEstimate(eventTime, elapsedMs, bytes, bitrate);
        }
    }

    public final void onDrmSessionAcquired() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDrmSessionAcquired(eventTime);
        }
    }

    public final void onDrmKeysLoaded() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDrmKeysLoaded(eventTime);
        }
    }

    public final void onDrmSessionManagerError(Exception error) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDrmSessionManagerError(eventTime, error);
        }
    }

    public final void onDrmKeysRestored() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDrmKeysRestored(eventTime);
        }
    }

    public final void onDrmKeysRemoved() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDrmKeysRemoved(eventTime);
        }
    }

    public final void onDrmSessionReleased() {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDrmSessionReleased(eventTime);
        }
    }

    /* access modifiers changed from: protected */
    public Set<AnalyticsListener> getListeners() {
        return Collections.unmodifiableSet(this.listeners);
    }

    /* JADX INFO: Multiple debug info for r1v16 long: [D('isCurrentAd' boolean), D('eventPositionMs' long)] */
    /* access modifiers changed from: protected */
    @RequiresNonNull({"player"})
    public AnalyticsListener.EventTime generateEventTime(Timeline timeline, int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaSource.MediaPeriodId mediaPeriodId2;
        long eventPositionMs;
        Timeline timeline2 = timeline;
        int i = windowIndex;
        if (timeline.isEmpty()) {
            mediaPeriodId2 = null;
        } else {
            mediaPeriodId2 = mediaPeriodId;
        }
        long realtimeMs = this.clock.elapsedRealtime();
        boolean isCurrentAd = true;
        boolean isInCurrentWindow = timeline2 == this.player.getCurrentTimeline() && i == this.player.getCurrentWindowIndex();
        long eventPositionMs2 = 0;
        if (mediaPeriodId2 != null && mediaPeriodId2.isAd()) {
            if (!(isInCurrentWindow && this.player.getCurrentAdGroupIndex() == mediaPeriodId2.adGroupIndex && this.player.getCurrentAdIndexInAdGroup() == mediaPeriodId2.adIndexInAdGroup)) {
                isCurrentAd = false;
            }
            if (isCurrentAd) {
                eventPositionMs2 = this.player.getCurrentPosition();
            }
            eventPositionMs = eventPositionMs2;
        } else if (isInCurrentWindow) {
            eventPositionMs = this.player.getContentPosition();
        } else {
            if (!timeline.isEmpty()) {
                eventPositionMs2 = timeline2.getWindow(i, this.window).getDefaultPositionMs();
            }
            eventPositionMs = eventPositionMs2;
        }
        return new AnalyticsListener.EventTime(realtimeMs, timeline, windowIndex, mediaPeriodId2, eventPositionMs, this.player.getCurrentPosition(), this.player.getTotalBufferedDuration());
    }

    private AnalyticsListener.EventTime generateEventTime(@Nullable MediaPeriodInfo mediaPeriodInfo) {
        int windowIndex;
        Assertions.checkNotNull(this.player);
        if (mediaPeriodInfo != null || (mediaPeriodInfo = this.mediaPeriodQueueTracker.tryResolveWindowIndex((windowIndex = this.player.getCurrentWindowIndex()))) != null) {
            return generateEventTime(mediaPeriodInfo.timeline, mediaPeriodInfo.windowIndex, mediaPeriodInfo.mediaPeriodId);
        }
        Timeline timeline = this.player.getCurrentTimeline();
        return generateEventTime(windowIndex < timeline.getWindowCount() ? timeline : Timeline.EMPTY, windowIndex, null);
    }

    private AnalyticsListener.EventTime generateLastReportedPlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLastReportedPlayingMediaPeriod());
    }

    private AnalyticsListener.EventTime generatePlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getPlayingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateReadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getReadingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateLoadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLoadingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateMediaPeriodEventTime(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        Assertions.checkNotNull(this.player);
        if (mediaPeriodId != null) {
            MediaPeriodInfo mediaPeriodInfo = this.mediaPeriodQueueTracker.getMediaPeriodInfo(mediaPeriodId);
            if (mediaPeriodInfo != null) {
                return generateEventTime(mediaPeriodInfo);
            }
            return generateEventTime(Timeline.EMPTY, windowIndex, mediaPeriodId);
        }
        Timeline timeline = this.player.getCurrentTimeline();
        return generateEventTime(windowIndex < timeline.getWindowCount() ? timeline : Timeline.EMPTY, windowIndex, null);
    }

    public static class Factory {
        public AnalyticsCollector createAnalyticsCollector(@Nullable Player player, Clock clock) {
            return new AnalyticsCollector(player, clock);
        }
    }

    private static final class MediaPeriodQueueTracker {
        /* access modifiers changed from: private */
        public final ArrayList<MediaPeriodInfo> mediaPeriodInfoQueue = new ArrayList<>();
        private final HashMap<MediaSource.MediaPeriodId, MediaPeriodInfo> mediaPeriodIdToInfo = new HashMap<>();
        private final Timeline.Period period = new Timeline.Period();
        private boolean isSeeking;
        @Nullable
        private MediaPeriodInfo lastReportedPlayingMediaPeriod;
        @Nullable
        private MediaPeriodInfo readingMediaPeriod;
        private Timeline timeline = Timeline.EMPTY;

        @Nullable
        public MediaPeriodInfo getPlayingMediaPeriod() {
            if (this.mediaPeriodInfoQueue.isEmpty() || this.timeline.isEmpty() || this.isSeeking) {
                return null;
            }
            return this.mediaPeriodInfoQueue.get(0);
        }

        @Nullable
        public MediaPeriodInfo getLastReportedPlayingMediaPeriod() {
            return this.lastReportedPlayingMediaPeriod;
        }

        @Nullable
        public MediaPeriodInfo getReadingMediaPeriod() {
            return this.readingMediaPeriod;
        }

        @Nullable
        public MediaPeriodInfo getLoadingMediaPeriod() {
            if (this.mediaPeriodInfoQueue.isEmpty()) {
                return null;
            }
            ArrayList<MediaPeriodInfo> arrayList = this.mediaPeriodInfoQueue;
            return arrayList.get(arrayList.size() - 1);
        }

        @Nullable
        public MediaPeriodInfo getMediaPeriodInfo(MediaSource.MediaPeriodId mediaPeriodId) {
            return this.mediaPeriodIdToInfo.get(mediaPeriodId);
        }

        public boolean isSeeking() {
            return this.isSeeking;
        }

        @Nullable
        public MediaPeriodInfo tryResolveWindowIndex(int windowIndex) {
            MediaPeriodInfo match = null;
            for (int i = 0; i < this.mediaPeriodInfoQueue.size(); i++) {
                MediaPeriodInfo info = this.mediaPeriodInfoQueue.get(i);
                int periodIndex = this.timeline.getIndexOfPeriod(info.mediaPeriodId.periodUid);
                if (periodIndex != -1 && this.timeline.getPeriod(periodIndex, this.period).windowIndex == windowIndex) {
                    if (match != null) {
                        return null;
                    }
                    match = info;
                }
            }
            return match;
        }

        public void onPositionDiscontinuity(int reason) {
            updateLastReportedPlayingMediaPeriod();
        }

        public void onTimelineChanged(Timeline timeline2) {
            for (int i = 0; i < this.mediaPeriodInfoQueue.size(); i++) {
                MediaPeriodInfo newMediaPeriodInfo = updateMediaPeriodInfoToNewTimeline(this.mediaPeriodInfoQueue.get(i), timeline2);
                this.mediaPeriodInfoQueue.set(i, newMediaPeriodInfo);
                this.mediaPeriodIdToInfo.put(newMediaPeriodInfo.mediaPeriodId, newMediaPeriodInfo);
            }
            MediaPeriodInfo mediaPeriodInfo = this.readingMediaPeriod;
            if (mediaPeriodInfo != null) {
                this.readingMediaPeriod = updateMediaPeriodInfoToNewTimeline(mediaPeriodInfo, timeline2);
            }
            this.timeline = timeline2;
            updateLastReportedPlayingMediaPeriod();
        }

        public void onSeekStarted() {
            this.isSeeking = true;
        }

        public void onSeekProcessed() {
            this.isSeeking = false;
            updateLastReportedPlayingMediaPeriod();
        }

        public void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            MediaPeriodInfo mediaPeriodInfo = new MediaPeriodInfo(mediaPeriodId, this.timeline.getIndexOfPeriod(mediaPeriodId.periodUid) != -1 ? this.timeline : Timeline.EMPTY, windowIndex);
            this.mediaPeriodInfoQueue.add(mediaPeriodInfo);
            this.mediaPeriodIdToInfo.put(mediaPeriodId, mediaPeriodInfo);
            if (this.mediaPeriodInfoQueue.size() == 1 && !this.timeline.isEmpty()) {
                updateLastReportedPlayingMediaPeriod();
            }
        }

        public boolean onMediaPeriodReleased(MediaSource.MediaPeriodId mediaPeriodId) {
            MediaPeriodInfo mediaPeriodInfo = this.mediaPeriodIdToInfo.remove(mediaPeriodId);
            if (mediaPeriodInfo == null) {
                return false;
            }
            this.mediaPeriodInfoQueue.remove(mediaPeriodInfo);
            MediaPeriodInfo mediaPeriodInfo2 = this.readingMediaPeriod;
            if (mediaPeriodInfo2 == null || !mediaPeriodId.equals(mediaPeriodInfo2.mediaPeriodId)) {
                return true;
            }
            this.readingMediaPeriod = this.mediaPeriodInfoQueue.isEmpty() ? null : this.mediaPeriodInfoQueue.get(0);
            return true;
        }

        public void onReadingStarted(MediaSource.MediaPeriodId mediaPeriodId) {
            this.readingMediaPeriod = this.mediaPeriodIdToInfo.get(mediaPeriodId);
        }

        private void updateLastReportedPlayingMediaPeriod() {
            if (!this.mediaPeriodInfoQueue.isEmpty()) {
                this.lastReportedPlayingMediaPeriod = this.mediaPeriodInfoQueue.get(0);
            }
        }

        private MediaPeriodInfo updateMediaPeriodInfoToNewTimeline(MediaPeriodInfo info, Timeline newTimeline) {
            int newPeriodIndex = newTimeline.getIndexOfPeriod(info.mediaPeriodId.periodUid);
            if (newPeriodIndex == -1) {
                return info;
            }
            return new MediaPeriodInfo(info.mediaPeriodId, newTimeline, newTimeline.getPeriod(newPeriodIndex, this.period).windowIndex);
        }
    }

    private static final class MediaPeriodInfo {
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final Timeline timeline;
        public final int windowIndex;

        public MediaPeriodInfo(MediaSource.MediaPeriodId mediaPeriodId2, Timeline timeline2, int windowIndex2) {
            this.mediaPeriodId = mediaPeriodId2;
            this.timeline = timeline2;
            this.windowIndex = windowIndex2;
        }
    }
}
