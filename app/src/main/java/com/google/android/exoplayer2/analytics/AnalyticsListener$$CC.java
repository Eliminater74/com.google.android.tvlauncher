package com.google.android.exoplayer2.analytics;

import android.support.annotation.Nullable;
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

public abstract /* synthetic */ class AnalyticsListener$$CC {
    public static void onPlayerStateChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, boolean playWhenReady, int playbackState) {
    }

    public static void onTimelineChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int reason) {
    }

    public static void onPositionDiscontinuity$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int reason) {
    }

    public static void onSeekStarted$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onSeekProcessed$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onPlaybackParametersChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
    }

    public static void onRepeatModeChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int repeatMode) {
    }

    public static void onShuffleModeChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, boolean shuffleModeEnabled) {
    }

    public static void onLoadingChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, boolean isLoading) {
    }

    public static void onPlayerError$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, ExoPlaybackException error) {
    }

    public static void onTracksChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    public static void onLoadStarted$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public static void onLoadCompleted$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public static void onLoadCanceled$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public static void onLoadError$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
    }

    public static void onDownstreamFormatChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public static void onUpstreamDiscarded$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public static void onMediaPeriodCreated$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onMediaPeriodReleased$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onReadingStarted$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onBandwidthEstimate$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
    }

    public static void onSurfaceSizeChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int width, int height) {
    }

    public static void onMetadata$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, Metadata metadata) {
    }

    public static void onDecoderEnabled$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
    }

    public static void onDecoderInitialized$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {
    }

    public static void onDecoderInputFormatChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int trackType, Format format) {
    }

    public static void onDecoderDisabled$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
    }

    public static void onAudioSessionId$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int audioSessionId) {
    }

    public static void onAudioAttributesChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
    }

    public static void onVolumeChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, float volume) {
    }

    public static void onAudioUnderrun$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    }

    public static void onDroppedVideoFrames$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int droppedFrames, long elapsedMs) {
    }

    public static void onVideoSizeChanged$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
    }

    public static void onRenderedFirstFrame$$dflt$$(AnalyticsListener analyticsListener, @Nullable AnalyticsListener.EventTime eventTime, Surface surface) {
    }

    public static void onDrmSessionAcquired$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onDrmKeysLoaded$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onDrmSessionManagerError$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime, Exception error) {
    }

    public static void onDrmKeysRestored$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onDrmKeysRemoved$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }

    public static void onDrmSessionReleased$$dflt$$(AnalyticsListener analyticsListener, AnalyticsListener.EventTime eventTime) {
    }
}
