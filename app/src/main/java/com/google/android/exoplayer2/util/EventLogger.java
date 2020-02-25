package com.google.android.exoplayer2.util;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.Surface;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.AnalyticsListener$$CC;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.gtalkservice.GTalkServiceConstants;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class EventLogger implements AnalyticsListener {
    private static final String DEFAULT_TAG = "EventLogger";
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final NumberFormat TIME_FORMAT = NumberFormat.getInstance(Locale.US);

    static {
        TIME_FORMAT.setMinimumFractionDigits(2);
        TIME_FORMAT.setMaximumFractionDigits(2);
        TIME_FORMAT.setGroupingUsed(false);
    }

    private final Timeline.Period period;
    private final long startTimeMs;
    private final String tag;
    @Nullable
    private final MappingTrackSelector trackSelector;
    private final Timeline.Window window;

    public EventLogger(@Nullable MappingTrackSelector trackSelector2) {
        this(trackSelector2, DEFAULT_TAG);
    }

    public EventLogger(@Nullable MappingTrackSelector trackSelector2, String tag2) {
        this.trackSelector = trackSelector2;
        this.tag = tag2;
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        this.startTimeMs = SystemClock.elapsedRealtime();
    }

    private static String getTimeString(long timeMs) {
        return timeMs == C0841C.TIME_UNSET ? "?" : TIME_FORMAT.format((double) (((float) timeMs) / 1000.0f));
    }

    private static String getStateString(int state) {
        if (state == 1) {
            return "IDLE";
        }
        if (state == 2) {
            return "BUFFERING";
        }
        if (state == 3) {
            return "READY";
        }
        if (state != 4) {
            return "?";
        }
        return "ENDED";
    }

    private static String getFormatSupportString(int formatSupport) {
        if (formatSupport == 0) {
            return "NO";
        }
        if (formatSupport == 1) {
            return "NO_UNSUPPORTED_TYPE";
        }
        if (formatSupport == 2) {
            return "NO_UNSUPPORTED_DRM";
        }
        if (formatSupport == 3) {
            return "NO_EXCEEDS_CAPABILITIES";
        }
        if (formatSupport != 4) {
            return "?";
        }
        return "YES";
    }

    private static String getAdaptiveSupportString(int trackCount, int adaptiveSupport) {
        if (trackCount < 2) {
            return "N/A";
        }
        if (adaptiveSupport == 0) {
            return "NO";
        }
        if (adaptiveSupport == 8) {
            return "YES_NOT_SEAMLESS";
        }
        if (adaptiveSupport != 16) {
            return "?";
        }
        return "YES";
    }

    private static String getTrackStatusString(@Nullable TrackSelection selection, TrackGroup group, int trackIndex) {
        return getTrackStatusString((selection == null || selection.getTrackGroup() != group || selection.indexOf(trackIndex) == -1) ? false : true);
    }

    private static String getTrackStatusString(boolean enabled) {
        return enabled ? "[X]" : "[ ]";
    }

    private static String getRepeatModeString(int repeatMode) {
        if (repeatMode == 0) {
            return "OFF";
        }
        if (repeatMode == 1) {
            return "ONE";
        }
        if (repeatMode != 2) {
            return "?";
        }
        return "ALL";
    }

    private static String getDiscontinuityReasonString(int reason) {
        if (reason == 0) {
            return "PERIOD_TRANSITION";
        }
        if (reason == 1) {
            return "SEEK";
        }
        if (reason == 2) {
            return "SEEK_ADJUSTMENT";
        }
        if (reason == 3) {
            return "AD_INSERTION";
        }
        if (reason != 4) {
            return "?";
        }
        return "INTERNAL";
    }

    private static String getTimelineChangeReasonString(int reason) {
        if (reason == 0) {
            return "PREPARED";
        }
        if (reason == 1) {
            return "RESET";
        }
        if (reason != 2) {
            return "?";
        }
        return "DYNAMIC";
    }

    private static String getTrackTypeString(int trackType) {
        switch (trackType) {
            case 0:
                return "default";
            case 1:
                return "audio";
            case 2:
                return "video";
            case 3:
                return "text";
            case 4:
                return TtmlNode.TAG_METADATA;
            case 5:
                return "camera motion";
            case 6:
                return "none";
            default:
                if (trackType < 10000) {
                    return "?";
                }
                StringBuilder sb = new StringBuilder(20);
                sb.append("custom (");
                sb.append(trackType);
                sb.append(")");
                return sb.toString();
        }
    }

    public void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        AnalyticsListener$$CC.onAudioAttributesChanged$$dflt$$(this, eventTime, audioAttributes);
    }

    public void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f) {
        AnalyticsListener$$CC.onVolumeChanged$$dflt$$(this, eventTime, f);
    }

    public void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean isLoading) {
        logd(eventTime, "loading", Boolean.toString(isLoading));
    }

    public void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean playWhenReady, int state) {
        String stateString = getStateString(state);
        StringBuilder sb = new StringBuilder(String.valueOf(stateString).length() + 7);
        sb.append(playWhenReady);
        sb.append(", ");
        sb.append(stateString);
        logd(eventTime, GTalkServiceConstants.EXTRA_INTENT_STATE, sb.toString());
    }

    public void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int repeatMode) {
        logd(eventTime, "repeatMode", getRepeatModeString(repeatMode));
    }

    public void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean shuffleModeEnabled) {
        logd(eventTime, "shuffleModeEnabled", Boolean.toString(shuffleModeEnabled));
    }

    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int reason) {
        logd(eventTime, "positionDiscontinuity", getDiscontinuityReasonString(reason));
    }

    public void onSeekStarted(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "seekStarted");
    }

    public void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        logd(eventTime, "playbackParameters", Util.formatInvariant("speed=%.2f, pitch=%.2f, skipSilence=%s", Float.valueOf(playbackParameters.speed), Float.valueOf(playbackParameters.pitch), Boolean.valueOf(playbackParameters.skipSilence)));
    }

    public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int reason) {
        int periodCount = eventTime.timeline.getPeriodCount();
        int windowCount = eventTime.timeline.getWindowCount();
        String eventTimeString = getEventTimeString(eventTime);
        String timelineChangeReasonString = getTimelineChangeReasonString(reason);
        StringBuilder sb = new StringBuilder(String.valueOf(eventTimeString).length() + 76 + String.valueOf(timelineChangeReasonString).length());
        sb.append("timelineChanged [");
        sb.append(eventTimeString);
        sb.append(", periodCount=");
        sb.append(periodCount);
        sb.append(", windowCount=");
        sb.append(windowCount);
        sb.append(", reason=");
        sb.append(timelineChangeReasonString);
        logd(sb.toString());
        for (int i = 0; i < Math.min(periodCount, 3); i++) {
            eventTime.timeline.getPeriod(i, this.period);
            String timeString = getTimeString(this.period.getDurationMs());
            StringBuilder sb2 = new StringBuilder(String.valueOf(timeString).length() + 11);
            sb2.append("  period [");
            sb2.append(timeString);
            sb2.append("]");
            logd(sb2.toString());
        }
        if (periodCount > 3) {
            logd("  ...");
        }
        for (int i2 = 0; i2 < Math.min(windowCount, 3); i2++) {
            eventTime.timeline.getWindow(i2, this.window);
            String timeString2 = getTimeString(this.window.getDurationMs());
            boolean z = this.window.isSeekable;
            boolean z2 = this.window.isDynamic;
            StringBuilder sb3 = new StringBuilder(String.valueOf(timeString2).length() + 25);
            sb3.append("  window [");
            sb3.append(timeString2);
            sb3.append(", ");
            sb3.append(z);
            sb3.append(", ");
            sb3.append(z2);
            sb3.append("]");
            logd(sb3.toString());
        }
        if (windowCount > 3) {
            logd("  ...");
        }
        logd("]");
    }

    public void onPlayerError(AnalyticsListener.EventTime eventTime, ExoPlaybackException e) {
        loge(eventTime, "playerFailed", e);
    }

    /* JADX INFO: Multiple debug info for r1v5 java.lang.String: [D('mappedTrackInfo' com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo), D('formatSupport' java.lang.String)] */
    /* JADX INFO: Multiple debug info for r9v9 java.lang.String: [D('adaptiveSupport' java.lang.String), D('formatSupport' java.lang.String)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo.getAdaptiveSupport(int, int, boolean):int
     arg types: [int, int, int]
     candidates:
      com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo.getAdaptiveSupport(int, int, int[]):int
      com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo.getAdaptiveSupport(int, int, boolean):int */
    public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray ignored, TrackSelectionArray trackSelections) {
        String str;
        String str2;
        String str3;
        MappingTrackSelector mappingTrackSelector = this.trackSelector;
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = mappingTrackSelector != null ? mappingTrackSelector.getCurrentMappedTrackInfo() : null;
        if (mappedTrackInfo == null) {
            logd(eventTime, "tracksChanged", "[]");
            return;
        }
        String eventTimeString = getEventTimeString(eventTime);
        StringBuilder sb = new StringBuilder(String.valueOf(eventTimeString).length() + 17);
        sb.append("tracksChanged [");
        sb.append(eventTimeString);
        sb.append(", ");
        logd(sb.toString());
        int rendererCount = mappedTrackInfo.getRendererCount();
        int rendererIndex = 0;
        while (true) {
            str = "    Group:";
            str2 = "  ]";
            str3 = " [";
            if (rendererIndex >= rendererCount) {
                break;
            }
            TrackGroupArray rendererTrackGroups = mappedTrackInfo.getTrackGroups(rendererIndex);
            TrackSelection trackSelection = trackSelections.get(rendererIndex);
            int rendererCount2 = rendererCount;
            if (rendererTrackGroups.length > 0) {
                StringBuilder sb2 = new StringBuilder(24);
                sb2.append("  Renderer:");
                sb2.append(rendererIndex);
                sb2.append(str3);
                logd(sb2.toString());
                int groupIndex = 0;
                while (groupIndex < rendererTrackGroups.length) {
                    TrackGroup trackGroup = rendererTrackGroups.get(groupIndex);
                    TrackGroupArray rendererTrackGroups2 = rendererTrackGroups;
                    String str4 = str2;
                    String adaptiveSupport = getAdaptiveSupportString(trackGroup.length, mappedTrackInfo.getAdaptiveSupport(rendererIndex, groupIndex, false));
                    StringBuilder sb3 = new StringBuilder(String.valueOf(adaptiveSupport).length() + 44);
                    sb3.append(str);
                    sb3.append(groupIndex);
                    sb3.append(", adaptive_supported=");
                    sb3.append(adaptiveSupport);
                    sb3.append(str3);
                    logd(sb3.toString());
                    int trackIndex = 0;
                    while (trackIndex < trackGroup.length) {
                        String status = getTrackStatusString(trackSelection, trackGroup, trackIndex);
                        String adaptiveSupport2 = adaptiveSupport;
                        String formatSupport = getFormatSupportString(mappedTrackInfo.getTrackSupport(rendererIndex, groupIndex, trackIndex));
                        TrackGroup trackGroup2 = trackGroup;
                        String logString = Format.toLogString(trackGroup.getFormat(trackIndex));
                        String str5 = str;
                        StringBuilder sb4 = new StringBuilder(String.valueOf(status).length() + 38 + String.valueOf(logString).length() + String.valueOf(formatSupport).length());
                        sb4.append("      ");
                        sb4.append(status);
                        sb4.append(" Track:");
                        sb4.append(trackIndex);
                        sb4.append(", ");
                        sb4.append(logString);
                        sb4.append(", supported=");
                        sb4.append(formatSupport);
                        logd(sb4.toString());
                        trackIndex++;
                        str = str5;
                        adaptiveSupport = adaptiveSupport2;
                        trackGroup = trackGroup2;
                        str3 = str3;
                    }
                    logd("    ]");
                    groupIndex++;
                    rendererTrackGroups = rendererTrackGroups2;
                    str2 = str4;
                }
                String str6 = str2;
                if (trackSelection != null) {
                    int selectionIndex = 0;
                    while (true) {
                        if (selectionIndex >= trackSelection.length()) {
                            break;
                        }
                        Metadata metadata = trackSelection.getFormat(selectionIndex).metadata;
                        if (metadata != null) {
                            logd("    Metadata [");
                            printMetadata(metadata, "      ");
                            logd("    ]");
                            break;
                        }
                        selectionIndex++;
                    }
                }
                logd(str6);
            }
            rendererIndex++;
            rendererCount = rendererCount2;
        }
        String str7 = str;
        String str8 = str2;
        String str9 = str3;
        TrackGroupArray unassociatedTrackGroups = mappedTrackInfo.getUnmappedTrackGroups();
        if (unassociatedTrackGroups.length > 0) {
            logd("  Renderer:None [");
            int groupIndex2 = 0;
            while (groupIndex2 < unassociatedTrackGroups.length) {
                StringBuilder sb5 = new StringBuilder(23);
                String str10 = str7;
                sb5.append(str10);
                sb5.append(groupIndex2);
                String str11 = str9;
                sb5.append(str11);
                logd(sb5.toString());
                TrackGroup trackGroup3 = unassociatedTrackGroups.get(groupIndex2);
                int trackIndex2 = 0;
                while (trackIndex2 < trackGroup3.length) {
                    String status2 = getTrackStatusString(false);
                    MappingTrackSelector.MappedTrackInfo mappedTrackInfo2 = mappedTrackInfo;
                    String formatSupport2 = getFormatSupportString(0);
                    String logString2 = Format.toLogString(trackGroup3.getFormat(trackIndex2));
                    String str12 = str10;
                    StringBuilder sb6 = new StringBuilder(String.valueOf(status2).length() + 38 + String.valueOf(logString2).length() + String.valueOf(formatSupport2).length());
                    sb6.append("      ");
                    sb6.append(status2);
                    sb6.append(" Track:");
                    sb6.append(trackIndex2);
                    sb6.append(", ");
                    sb6.append(logString2);
                    sb6.append(", supported=");
                    sb6.append(formatSupport2);
                    logd(sb6.toString());
                    trackIndex2++;
                    mappedTrackInfo = mappedTrackInfo2;
                    str10 = str12;
                    unassociatedTrackGroups = unassociatedTrackGroups;
                }
                str7 = str10;
                logd("    ]");
                groupIndex2++;
                str9 = str11;
            }
            logd(str8);
        }
        logd("]");
    }

    public void onSeekProcessed(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "seekProcessed");
    }

    public void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        String eventTimeString = getEventTimeString(eventTime);
        StringBuilder sb = new StringBuilder(String.valueOf(eventTimeString).length() + 12);
        sb.append("metadata [");
        sb.append(eventTimeString);
        sb.append(", ");
        logd(sb.toString());
        printMetadata(metadata, "  ");
        logd("]");
    }

    public void onDecoderEnabled(AnalyticsListener.EventTime eventTime, int trackType, DecoderCounters counters) {
        logd(eventTime, "decoderEnabled", getTrackTypeString(trackType));
    }

    public void onAudioSessionId(AnalyticsListener.EventTime eventTime, int audioSessionId) {
        logd(eventTime, "audioSessionId", Integer.toString(audioSessionId));
    }

    public void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {
        String trackTypeString = getTrackTypeString(trackType);
        StringBuilder sb = new StringBuilder(String.valueOf(trackTypeString).length() + 2 + String.valueOf(decoderName).length());
        sb.append(trackTypeString);
        sb.append(", ");
        sb.append(decoderName);
        logd(eventTime, "decoderInitialized", sb.toString());
    }

    public void onDecoderInputFormatChanged(AnalyticsListener.EventTime eventTime, int trackType, Format format) {
        String trackTypeString = getTrackTypeString(trackType);
        String logString = Format.toLogString(format);
        StringBuilder sb = new StringBuilder(String.valueOf(trackTypeString).length() + 2 + String.valueOf(logString).length());
        sb.append(trackTypeString);
        sb.append(", ");
        sb.append(logString);
        logd(eventTime, "decoderInputFormatChanged", sb.toString());
    }

    public void onDecoderDisabled(AnalyticsListener.EventTime eventTime, int trackType, DecoderCounters counters) {
        logd(eventTime, "decoderDisabled", getTrackTypeString(trackType));
    }

    public void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        StringBuilder sb = new StringBuilder(56);
        sb.append(bufferSize);
        sb.append(", ");
        sb.append(bufferSizeMs);
        sb.append(", ");
        sb.append(elapsedSinceLastFeedMs);
        sb.append("]");
        loge(eventTime, "audioTrackUnderrun", sb.toString(), null);
    }

    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int count, long elapsedMs) {
        logd(eventTime, "droppedFrames", Integer.toString(count));
    }

    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        StringBuilder sb = new StringBuilder(24);
        sb.append(width);
        sb.append(", ");
        sb.append(height);
        logd(eventTime, "videoSizeChanged", sb.toString());
    }

    public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, @Nullable Surface surface) {
        logd(eventTime, "renderedFirstFrame", String.valueOf(surface));
    }

    public void onMediaPeriodCreated(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "mediaPeriodCreated");
    }

    public void onMediaPeriodReleased(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "mediaPeriodReleased");
    }

    public void onLoadStarted(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onLoadError(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        printInternalError(eventTime, "loadError", error);
    }

    public void onLoadCanceled(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onLoadCompleted(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
    }

    public void onReadingStarted(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "mediaPeriodReadingStarted");
    }

    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
    }

    public void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int width, int height) {
        StringBuilder sb = new StringBuilder(24);
        sb.append(width);
        sb.append(", ");
        sb.append(height);
        logd(eventTime, "surfaceSizeChanged", sb.toString());
    }

    public void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        logd(eventTime, "upstreamDiscarded", Format.toLogString(mediaLoadData.trackFormat));
    }

    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        logd(eventTime, "downstreamFormatChanged", Format.toLogString(mediaLoadData.trackFormat));
    }

    public void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmSessionAcquired");
    }

    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception e) {
        printInternalError(eventTime, "drmSessionManagerError", e);
    }

    public void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysRestored");
    }

    public void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysRemoved");
    }

    public void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysLoaded");
    }

    public void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmSessionReleased");
    }

    /* access modifiers changed from: protected */
    public void logd(String msg) {
        Log.m24d(this.tag, msg);
    }

    /* access modifiers changed from: protected */
    public void loge(String msg, @Nullable Throwable tr) {
        Log.m27e(this.tag, msg, tr);
    }

    private void logd(AnalyticsListener.EventTime eventTime, String eventName) {
        logd(getEventString(eventTime, eventName));
    }

    private void logd(AnalyticsListener.EventTime eventTime, String eventName, String eventDescription) {
        logd(getEventString(eventTime, eventName, eventDescription));
    }

    private void loge(AnalyticsListener.EventTime eventTime, String eventName, @Nullable Throwable throwable) {
        loge(getEventString(eventTime, eventName), throwable);
    }

    private void loge(AnalyticsListener.EventTime eventTime, String eventName, String eventDescription, @Nullable Throwable throwable) {
        loge(getEventString(eventTime, eventName, eventDescription), throwable);
    }

    private void printInternalError(AnalyticsListener.EventTime eventTime, String type, Exception e) {
        loge(eventTime, "internalError", type, e);
    }

    private void printMetadata(Metadata metadata, String prefix) {
        for (int i = 0; i < metadata.length(); i++) {
            String valueOf = String.valueOf(metadata.get(i));
            StringBuilder sb = new StringBuilder(String.valueOf(prefix).length() + String.valueOf(valueOf).length());
            sb.append(prefix);
            sb.append(valueOf);
            logd(sb.toString());
        }
    }

    private String getEventString(AnalyticsListener.EventTime eventTime, String eventName) {
        String eventTimeString = getEventTimeString(eventTime);
        StringBuilder sb = new StringBuilder(String.valueOf(eventName).length() + 3 + String.valueOf(eventTimeString).length());
        sb.append(eventName);
        sb.append(" [");
        sb.append(eventTimeString);
        sb.append("]");
        return sb.toString();
    }

    private String getEventString(AnalyticsListener.EventTime eventTime, String eventName, String eventDescription) {
        String eventTimeString = getEventTimeString(eventTime);
        StringBuilder sb = new StringBuilder(String.valueOf(eventName).length() + 5 + String.valueOf(eventTimeString).length() + String.valueOf(eventDescription).length());
        sb.append(eventName);
        sb.append(" [");
        sb.append(eventTimeString);
        sb.append(", ");
        sb.append(eventDescription);
        sb.append("]");
        return sb.toString();
    }

    private String getEventTimeString(AnalyticsListener.EventTime eventTime) {
        int i = eventTime.windowIndex;
        StringBuilder sb = new StringBuilder(18);
        sb.append("window=");
        sb.append(i);
        String windowPeriodString = sb.toString();
        if (eventTime.mediaPeriodId != null) {
            String valueOf = String.valueOf(windowPeriodString);
            int indexOfPeriod = eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 20);
            sb2.append(valueOf);
            sb2.append(", period=");
            sb2.append(indexOfPeriod);
            windowPeriodString = sb2.toString();
            if (eventTime.mediaPeriodId.isAd()) {
                String valueOf2 = String.valueOf(windowPeriodString);
                int i2 = eventTime.mediaPeriodId.adGroupIndex;
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 21);
                sb3.append(valueOf2);
                sb3.append(", adGroup=");
                sb3.append(i2);
                String valueOf3 = String.valueOf(sb3.toString());
                int i3 = eventTime.mediaPeriodId.adIndexInAdGroup;
                StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf3).length() + 16);
                sb4.append(valueOf3);
                sb4.append(", ad=");
                sb4.append(i3);
                windowPeriodString = sb4.toString();
            }
        }
        String timeString = getTimeString(eventTime.realtimeMs - this.startTimeMs);
        String timeString2 = getTimeString(eventTime.currentPlaybackPositionMs);
        StringBuilder sb5 = new StringBuilder(String.valueOf(timeString).length() + 4 + String.valueOf(timeString2).length() + String.valueOf(windowPeriodString).length());
        sb5.append(timeString);
        sb5.append(", ");
        sb5.append(timeString2);
        sb5.append(", ");
        sb5.append(windowPeriodString);
        return sb5.toString();
    }
}
