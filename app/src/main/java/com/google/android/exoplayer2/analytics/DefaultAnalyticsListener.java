package com.google.android.exoplayer2.analytics;

import android.view.Surface;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.io.IOException;

@Deprecated
public abstract class DefaultAnalyticsListener implements AnalyticsListener {
    public void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        AnalyticsListener$$CC.onAudioAttributesChanged$$dflt$$(this, eventTime, audioAttributes);
    }

    public void onAudioSessionId(AnalyticsListener.EventTime eventTime, int i) {
        AnalyticsListener$$CC.onAudioSessionId$$dflt$$(this, eventTime, i);
    }

    public void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
        AnalyticsListener$$CC.onAudioUnderrun$$dflt$$(this, eventTime, i, j, j2);
    }

    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
        AnalyticsListener$$CC.onBandwidthEstimate$$dflt$$(this, eventTime, i, j, j2);
    }

    public void onDecoderDisabled(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
        AnalyticsListener$$CC.onDecoderDisabled$$dflt$$(this, eventTime, i, decoderCounters);
    }

    public void onDecoderEnabled(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
        AnalyticsListener$$CC.onDecoderEnabled$$dflt$$(this, eventTime, i, decoderCounters);
    }

    public void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int i, String str, long j) {
        AnalyticsListener$$CC.onDecoderInitialized$$dflt$$(this, eventTime, i, str, j);
    }

    public void onDecoderInputFormatChanged(AnalyticsListener.EventTime eventTime, int i, Format format) {
        AnalyticsListener$$CC.onDecoderInputFormatChanged$$dflt$$(this, eventTime, i, format);
    }

    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener$$CC.onDownstreamFormatChanged$$dflt$$(this, eventTime, mediaLoadData);
    }

    public void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onDrmKeysLoaded$$dflt$$(this, eventTime);
    }

    public void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onDrmKeysRemoved$$dflt$$(this, eventTime);
    }

    public void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onDrmKeysRestored$$dflt$$(this, eventTime);
    }

    public void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onDrmSessionAcquired$$dflt$$(this, eventTime);
    }

    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        AnalyticsListener$$CC.onDrmSessionManagerError$$dflt$$(this, eventTime, exc);
    }

    public void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onDrmSessionReleased$$dflt$$(this, eventTime);
    }

    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i, long j) {
        AnalyticsListener$$CC.onDroppedVideoFrames$$dflt$$(this, eventTime, i, j);
    }

    public void onLoadCanceled(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener$$CC.onLoadCanceled$$dflt$$(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public void onLoadCompleted(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener$$CC.onLoadCompleted$$dflt$$(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public void onLoadError(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        AnalyticsListener$$CC.onLoadError$$dflt$$(this, eventTime, loadEventInfo, mediaLoadData, iOException, z);
    }

    public void onLoadStarted(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener$$CC.onLoadStarted$$dflt$$(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        AnalyticsListener$$CC.onLoadingChanged$$dflt$$(this, eventTime, z);
    }

    public void onMediaPeriodCreated(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onMediaPeriodCreated$$dflt$$(this, eventTime);
    }

    public void onMediaPeriodReleased(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onMediaPeriodReleased$$dflt$$(this, eventTime);
    }

    public void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        AnalyticsListener$$CC.onMetadata$$dflt$$(this, eventTime, metadata);
    }

    public void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        AnalyticsListener$$CC.onPlaybackParametersChanged$$dflt$$(this, eventTime, playbackParameters);
    }

    public void onPlayerError(AnalyticsListener.EventTime eventTime, ExoPlaybackException exoPlaybackException) {
        AnalyticsListener$$CC.onPlayerError$$dflt$$(this, eventTime, exoPlaybackException);
    }

    public void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean z, int i) {
        AnalyticsListener$$CC.onPlayerStateChanged$$dflt$$(this, eventTime, z, i);
    }

    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int i) {
        AnalyticsListener$$CC.onPositionDiscontinuity$$dflt$$(this, eventTime, i);
    }

    public void onReadingStarted(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onReadingStarted$$dflt$$(this, eventTime);
    }

    public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Surface surface) {
        AnalyticsListener$$CC.onRenderedFirstFrame$$dflt$$(this, eventTime, surface);
    }

    public void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i) {
        AnalyticsListener$$CC.onRepeatModeChanged$$dflt$$(this, eventTime, i);
    }

    public void onSeekProcessed(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onSeekProcessed$$dflt$$(this, eventTime);
    }

    public void onSeekStarted(AnalyticsListener.EventTime eventTime) {
        AnalyticsListener$$CC.onSeekStarted$$dflt$$(this, eventTime);
    }

    public void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        AnalyticsListener$$CC.onShuffleModeChanged$$dflt$$(this, eventTime, z);
    }

    public void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2) {
        AnalyticsListener$$CC.onSurfaceSizeChanged$$dflt$$(this, eventTime, i, i2);
    }

    public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i) {
        AnalyticsListener$$CC.onTimelineChanged$$dflt$$(this, eventTime, i);
    }

    public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        AnalyticsListener$$CC.onTracksChanged$$dflt$$(this, eventTime, trackGroupArray, trackSelectionArray);
    }

    public void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener$$CC.onUpstreamDiscarded$$dflt$$(this, eventTime, mediaLoadData);
    }

    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2, int i3, float f) {
        AnalyticsListener$$CC.onVideoSizeChanged$$dflt$$(this, eventTime, i, i2, i3, f);
    }

    public void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f) {
        AnalyticsListener$$CC.onVolumeChanged$$dflt$$(this, eventTime, f);
    }
}
