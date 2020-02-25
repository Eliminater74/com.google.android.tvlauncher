package com.google.android.exoplayer2.video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.Surface;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.mediacodec.MediaFormatUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gsf.TalkContract;
import com.google.protos.datapol.SemanticAnnotations;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {1920, SemanticAnnotations.SemanticType.ST_ANONYMOUS_DATA_VALUE, 1440, 1280, ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE, ClientAnalytics.LogRequest.LogSource.LENS_STANDALONE_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.GMSCORE_BACKEND_COUNTERS_VALUE, ClientAnalytics.LogRequest.LogSource.CORP_IOS_LATIOS_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private final Context context;
    private final boolean deviceNeedsNoPostProcessWorkaround;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;
    private final VideoFrameReleaseTimeHelper frameReleaseTimeHelper;
    private final int maxDroppedFramesToNotify;
    private final long[] pendingOutputStreamOffsetsUs;
    private final long[] pendingOutputStreamSwitchTimesUs;
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;
    private int buffersInCodecCount;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private Surface dummySurface;
    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastInputTimeUs;
    private long lastRenderTimeUs;
    private long outputStreamOffsetUs;
    private int pendingOutputStreamOffsetCount;
    private float pendingPixelWidthHeightRatio;
    private int pendingRotationDegrees;
    private boolean renderedFirstFrame;
    private int reportedHeight;
    private float reportedPixelWidthHeightRatio;
    private int reportedUnappliedRotationDegrees;
    private int reportedWidth;
    private int scalingMode;
    private Surface surface;
    private boolean tunneling;
    private int tunnelingAudioSessionId;

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector) {
        this(context2, mediaCodecSelector, 0);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs2) {
        this(context2, mediaCodecSelector, allowedJoiningTimeMs2, null, null, -1);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs2, @Nullable Handler eventHandler, @Nullable VideoRendererEventListener eventListener, int maxDroppedFramesToNotify2) {
        this(context2, mediaCodecSelector, allowedJoiningTimeMs2, null, false, eventHandler, eventListener, maxDroppedFramesToNotify2);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs2, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler, @Nullable VideoRendererEventListener eventListener, int maxDroppedFramesToNotify2) {
        this(context2, mediaCodecSelector, allowedJoiningTimeMs2, drmSessionManager, playClearSamplesWithoutKeys, false, eventHandler, eventListener, maxDroppedFramesToNotify2);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs2, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, boolean enableDecoderFallback, @Nullable Handler eventHandler, @Nullable VideoRendererEventListener eventListener, int maxDroppedFramesToNotify2) {
        super(2, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, enableDecoderFallback, 30.0f);
        this.allowedJoiningTimeMs = allowedJoiningTimeMs2;
        this.maxDroppedFramesToNotify = maxDroppedFramesToNotify2;
        this.context = context2.getApplicationContext();
        this.frameReleaseTimeHelper = new VideoFrameReleaseTimeHelper(this.context);
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(eventHandler, eventListener);
        this.deviceNeedsNoPostProcessWorkaround = deviceNeedsNoPostProcessWorkaround();
        this.pendingOutputStreamOffsetsUs = new long[10];
        this.pendingOutputStreamSwitchTimesUs = new long[10];
        this.outputStreamOffsetUs = C0841C.TIME_UNSET;
        this.lastInputTimeUs = C0841C.TIME_UNSET;
        this.joiningDeadlineMs = C0841C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        clearReportedVideoSize();
    }

    private static List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder, boolean requiresTunnelingDecoder) throws MediaCodecUtil.DecoderQueryException {
        Pair<Integer, Integer> codecProfileAndLevel;
        List<MediaCodecInfo> decoderInfos = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(format.sampleMimeType, requiresSecureDecoder, requiresTunnelingDecoder), format);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format.codecs)) != null) {
            int profile = ((Integer) codecProfileAndLevel.first).intValue();
            if (profile == 4 || profile == 8) {
                decoderInfos.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H265, requiresSecureDecoder, requiresTunnelingDecoder));
            } else if (profile == 9) {
                decoderInfos.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H264, requiresSecureDecoder, requiresTunnelingDecoder));
            }
        }
        return Collections.unmodifiableList(decoderInfos);
    }

    private static boolean isBufferLate(long earlyUs) {
        return earlyUs < -30000;
    }

    private static boolean isBufferVeryLate(long earlyUs) {
        return earlyUs < -500000;
    }

    @TargetApi(23)
    private static void setOutputSurfaceV23(MediaCodec codec, Surface surface2) {
        codec.setOutputSurface(surface2);
    }

    @TargetApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int tunnelingAudioSessionId2) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", tunnelingAudioSessionId2);
    }

    private static Point getCodecMaxSize(MediaCodecInfo codecInfo, Format format) throws MediaCodecUtil.DecoderQueryException {
        float aspectRatio;
        int formatShortEdgePx;
        MediaCodecInfo mediaCodecInfo = codecInfo;
        Format format2 = format;
        int i = 0;
        boolean isVerticalVideo = format2.height > format2.width;
        int formatLongEdgePx = isVerticalVideo ? format2.height : format2.width;
        int formatShortEdgePx2 = isVerticalVideo ? format2.width : format2.height;
        float aspectRatio2 = ((float) formatShortEdgePx2) / ((float) formatLongEdgePx);
        int[] iArr = STANDARD_LONG_EDGE_VIDEO_PX;
        int length = iArr.length;
        while (i < length) {
            int longEdgePx = iArr[i];
            int shortEdgePx = (int) (((float) longEdgePx) * aspectRatio2);
            if (longEdgePx > formatLongEdgePx) {
                if (shortEdgePx > formatShortEdgePx2) {
                    if (Util.SDK_INT >= 21) {
                        Point alignedSize = mediaCodecInfo.alignVideoSizeV21(isVerticalVideo ? shortEdgePx : longEdgePx, isVerticalVideo ? longEdgePx : shortEdgePx);
                        formatShortEdgePx = formatShortEdgePx2;
                        aspectRatio = aspectRatio2;
                        if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(alignedSize.x, alignedSize.y, (double) format2.frameRate)) {
                            return alignedSize;
                        }
                    } else {
                        formatShortEdgePx = formatShortEdgePx2;
                        aspectRatio = aspectRatio2;
                        int longEdgePx2 = Util.ceilDivide(longEdgePx, 16) * 16;
                        int shortEdgePx2 = Util.ceilDivide(shortEdgePx, 16) * 16;
                        if (longEdgePx2 * shortEdgePx2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                            return new Point(isVerticalVideo ? shortEdgePx2 : longEdgePx2, isVerticalVideo ? longEdgePx2 : shortEdgePx2);
                        }
                    }
                    i++;
                    formatShortEdgePx2 = formatShortEdgePx;
                    aspectRatio2 = aspectRatio;
                }
            }
            return null;
        }
        return null;
    }

    private static int getMaxInputSize(MediaCodecInfo codecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return getCodecMaxInputSize(codecInfo, format.sampleMimeType, format.width, format.height);
        }
        int totalInitializationDataSize = 0;
        int initializationDataCount = format.initializationData.size();
        for (int i = 0; i < initializationDataCount; i++) {
            totalInitializationDataSize += format.initializationData.get(i).length;
        }
        return format.maxInputSize + totalInitializationDataSize;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getCodecMaxInputSize(MediaCodecInfo codecInfo, String sampleMimeType, int width, int height) {
        char c;
        int minCompressionRatio;
        int maxPixels;
        if (width == -1 || height == -1) {
            return -1;
        }
        switch (sampleMimeType.hashCode()) {
            case -1664118616:
                if (sampleMimeType.equals(MimeTypes.VIDEO_H263)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1662541442:
                if (sampleMimeType.equals(MimeTypes.VIDEO_H265)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1187890754:
                if (sampleMimeType.equals(MimeTypes.VIDEO_MP4V)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1331836730:
                if (sampleMimeType.equals(MimeTypes.VIDEO_H264)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1599127256:
                if (sampleMimeType.equals(MimeTypes.VIDEO_VP8)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1599127257:
                if (sampleMimeType.equals(MimeTypes.VIDEO_VP9)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0 || c == 1) {
            maxPixels = width * height;
            minCompressionRatio = 2;
        } else if (c != 2) {
            if (c == 3) {
                maxPixels = width * height;
                minCompressionRatio = 2;
            } else if (c != 4 && c != 5) {
                return -1;
            } else {
                maxPixels = width * height;
                minCompressionRatio = 4;
            }
        } else if ("BRAVIA 4K 2015".equals(Util.MODEL) || ("Amazon".equals(Util.MANUFACTURER) && ("KFSOWI".equals(Util.MODEL) || ("AFTS".equals(Util.MODEL) && codecInfo.secure)))) {
            return -1;
        } else {
            maxPixels = Util.ceilDivide(width, 16) * Util.ceilDivide(height, 16) * 16 * 16;
            minCompressionRatio = 2;
        }
        return (maxPixels * 3) / (minCompressionRatio * 2);
    }

    private static boolean deviceNeedsNoPostProcessWorkaround() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* access modifiers changed from: protected */
    public int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws MediaCodecUtil.DecoderQueryException {
        int adaptiveSupport;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return 0;
        }
        boolean requiresSecureDecryption = false;
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData != null) {
            for (int i = 0; i < drmInitData.schemeDataCount; i++) {
                requiresSecureDecryption |= drmInitData.get(i).requiresSecureDecryption;
            }
        }
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mediaCodecSelector, format, requiresSecureDecryption, false);
        if (decoderInfos.isEmpty()) {
            if (!requiresSecureDecryption || getDecoderInfos(mediaCodecSelector, format, false, false).isEmpty()) {
                return 1;
            }
            return 2;
        } else if (!supportsFormatDrm(drmSessionManager, drmInitData)) {
            return 2;
        } else {
            MediaCodecInfo decoderInfo = decoderInfos.get(0);
            boolean isFormatSupported = decoderInfo.isFormatSupported(format);
            if (decoderInfo.isSeamlessAdaptationSupported(format)) {
                adaptiveSupport = 16;
            } else {
                adaptiveSupport = 8;
            }
            int tunnelingSupport = 0;
            if (isFormatSupported) {
                List<MediaCodecInfo> tunnelingDecoderInfos = getDecoderInfos(mediaCodecSelector, format, requiresSecureDecryption, true);
                if (!tunnelingDecoderInfos.isEmpty()) {
                    MediaCodecInfo tunnelingDecoderInfo = tunnelingDecoderInfos.get(0);
                    if (tunnelingDecoderInfo.isFormatSupported(format) && tunnelingDecoderInfo.isSeamlessAdaptationSupported(format)) {
                        tunnelingSupport = 32;
                    }
                }
            }
            return adaptiveSupport | tunnelingSupport | (isFormatSupported ? 4 : 3);
        }
    }

    /* access modifiers changed from: protected */
    public List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException {
        return getDecoderInfos(mediaCodecSelector, format, requiresSecureDecoder, this.tunneling);
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean joining) throws ExoPlaybackException {
        super.onEnabled(joining);
        int oldTunnelingAudioSessionId = this.tunnelingAudioSessionId;
        this.tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        this.tunneling = this.tunnelingAudioSessionId != 0;
        if (this.tunnelingAudioSessionId != oldTunnelingAudioSessionId) {
            releaseCodec();
        }
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseTimeHelper.enable();
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formats, long offsetUs) throws ExoPlaybackException {
        if (this.outputStreamOffsetUs == C0841C.TIME_UNSET) {
            this.outputStreamOffsetUs = offsetUs;
        } else {
            int i = this.pendingOutputStreamOffsetCount;
            long[] jArr = this.pendingOutputStreamOffsetsUs;
            if (i == jArr.length) {
                long j = jArr[i - 1];
                StringBuilder sb = new StringBuilder(65);
                sb.append("Too many stream changes, so dropping offset: ");
                sb.append(j);
                Log.m30w(TAG, sb.toString());
            } else {
                this.pendingOutputStreamOffsetCount = i + 1;
            }
            long[] jArr2 = this.pendingOutputStreamOffsetsUs;
            int i2 = this.pendingOutputStreamOffsetCount;
            jArr2[i2 - 1] = offsetUs;
            this.pendingOutputStreamSwitchTimesUs[i2 - 1] = this.lastInputTimeUs;
        }
        super.onStreamChanged(formats, offsetUs);
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        super.onPositionReset(positionUs, joining);
        clearRenderedFirstFrame();
        this.initialPositionUs = C0841C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        this.lastInputTimeUs = C0841C.TIME_UNSET;
        int i = this.pendingOutputStreamOffsetCount;
        if (i != 0) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[i - 1];
            this.pendingOutputStreamOffsetCount = 0;
        }
        if (joining) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C0841C.TIME_UNSET;
        }
    }

    public boolean isReady() {
        Surface surface2;
        if (super.isReady() && (this.renderedFirstFrame || (((surface2 = this.dummySurface) != null && this.surface == surface2) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C0841C.TIME_UNSET;
            return true;
        } else if (this.joiningDeadlineMs == C0841C.TIME_UNSET) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
                return true;
            }
            this.joiningDeadlineMs = C0841C.TIME_UNSET;
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
    }

    /* access modifiers changed from: protected */
    public void onStopped() {
        this.joiningDeadlineMs = C0841C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        super.onStopped();
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        this.lastInputTimeUs = C0841C.TIME_UNSET;
        this.outputStreamOffsetUs = C0841C.TIME_UNSET;
        this.pendingOutputStreamOffsetCount = 0;
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.frameReleaseTimeHelper.disable();
        this.tunnelingOnFrameRenderedListener = null;
        try {
            super.onDisabled();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        try {
            super.onReset();
        } finally {
            Surface surface2 = this.dummySurface;
            if (surface2 != null) {
                if (this.surface == surface2) {
                    this.surface = null;
                }
                this.dummySurface.release();
                this.dummySurface = null;
            }
        }
    }

    public void handleMessage(int messageType, @Nullable Object message) throws ExoPlaybackException {
        if (messageType == 1) {
            setSurface((Surface) message);
        } else if (messageType == 4) {
            this.scalingMode = ((Integer) message).intValue();
            MediaCodec codec = getCodec();
            if (codec != null) {
                codec.setVideoScalingMode(this.scalingMode);
            }
        } else if (messageType == 6) {
            this.frameMetadataListener = (VideoFrameMetadataListener) message;
        } else {
            super.handleMessage(messageType, message);
        }
    }

    private void setSurface(Surface surface2) throws ExoPlaybackException {
        if (surface2 == null) {
            if (this.dummySurface != null) {
                surface2 = this.dummySurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo)) {
                    this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    surface2 = this.dummySurface;
                }
            }
        }
        if (this.surface != surface2) {
            this.surface = surface2;
            int state = getState();
            MediaCodec codec = getCodec();
            if (codec != null) {
                if (Util.SDK_INT < 23 || surface2 == null || this.codecNeedsSetOutputSurfaceWorkaround) {
                    releaseCodec();
                    maybeInitCodec();
                } else {
                    setOutputSurfaceV23(codec, surface2);
                }
            }
            if (surface2 == null || surface2 == this.dummySurface) {
                clearReportedVideoSize();
                clearRenderedFirstFrame();
                return;
            }
            maybeRenotifyVideoSizeChanged();
            clearRenderedFirstFrame();
            if (state == 2) {
                setJoiningDeadlineMs();
            }
        } else if (surface2 != null && surface2 != this.dummySurface) {
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldInitCodec(MediaCodecInfo codecInfo) {
        return this.surface != null || shouldUseDummySurface(codecInfo);
    }

    /* access modifiers changed from: protected */
    public boolean getCodecNeedsEosPropagation() {
        return this.tunneling;
    }

    /* access modifiers changed from: protected */
    public void configureCodec(MediaCodecInfo codecInfo, MediaCodec codec, Format format, MediaCrypto crypto, float codecOperatingRate) throws MediaCodecUtil.DecoderQueryException {
        this.codecMaxValues = getCodecMaxValues(codecInfo, format, getStreamFormats());
        MediaFormat mediaFormat = getMediaFormat(format, this.codecMaxValues, codecOperatingRate, this.deviceNeedsNoPostProcessWorkaround, this.tunnelingAudioSessionId);
        if (this.surface == null) {
            Assertions.checkState(shouldUseDummySurface(codecInfo));
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        codec.configure(mediaFormat, this.surface, crypto, 0);
        if (Util.SDK_INT >= 23 && this.tunneling) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
        }
    }

    /* access modifiers changed from: protected */
    public int canKeepCodec(MediaCodec codec, MediaCodecInfo codecInfo, Format oldFormat, Format newFormat) {
        if (!codecInfo.isSeamlessAdaptationSupported(oldFormat, newFormat, true) || newFormat.width > this.codecMaxValues.width || newFormat.height > this.codecMaxValues.height || getMaxInputSize(codecInfo, newFormat) > this.codecMaxValues.inputSize) {
            return 0;
        }
        if (oldFormat.initializationDataEquals(newFormat)) {
            return 3;
        }
        return 2;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void releaseCodec() {
        try {
            super.releaseCodec();
        } finally {
            this.buffersInCodecCount = 0;
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public boolean flushOrReleaseCodec() {
        try {
            return super.flushOrReleaseCodec();
        } finally {
            this.buffersInCodecCount = 0;
        }
    }

    /* access modifiers changed from: protected */
    public float getCodecOperatingRateV23(float operatingRate, Format format, Format[] streamFormats) {
        float maxFrameRate = -1.0f;
        for (Format streamFormat : streamFormats) {
            float streamFrameRate = streamFormat.frameRate;
            if (streamFrameRate != -1.0f) {
                maxFrameRate = Math.max(maxFrameRate, streamFrameRate);
            }
        }
        if (maxFrameRate == -1.0f) {
            return -1.0f;
        }
        return maxFrameRate * operatingRate;
    }

    /* access modifiers changed from: protected */
    public void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
        this.eventDispatcher.decoderInitialized(name, initializedTimestampMs, initializationDurationMs);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(name);
    }

    /* access modifiers changed from: protected */
    public void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        super.onInputFormatChanged(formatHolder);
        Format newFormat = formatHolder.format;
        this.eventDispatcher.inputFormatChanged(newFormat);
        this.pendingPixelWidthHeightRatio = newFormat.pixelWidthHeightRatio;
        this.pendingRotationDegrees = newFormat.rotationDegrees;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onQueueInputBuffer(DecoderInputBuffer buffer) {
        this.buffersInCodecCount++;
        this.lastInputTimeUs = Math.max(buffer.timeUs, this.lastInputTimeUs);
        if (Util.SDK_INT < 23 && this.tunneling) {
            onProcessedTunneledBuffer(buffer.timeUs);
        }
    }

    /* access modifiers changed from: protected */
    public void onOutputFormatChanged(MediaCodec codec, MediaFormat outputFormat) {
        int width;
        int height;
        boolean hasCrop = outputFormat.containsKey(KEY_CROP_RIGHT) && outputFormat.containsKey(KEY_CROP_LEFT) && outputFormat.containsKey(KEY_CROP_BOTTOM) && outputFormat.containsKey(KEY_CROP_TOP);
        if (hasCrop) {
            width = (outputFormat.getInteger(KEY_CROP_RIGHT) - outputFormat.getInteger(KEY_CROP_LEFT)) + 1;
        } else {
            width = outputFormat.getInteger("width");
        }
        if (hasCrop) {
            height = (outputFormat.getInteger(KEY_CROP_BOTTOM) - outputFormat.getInteger(KEY_CROP_TOP)) + 1;
        } else {
            height = outputFormat.getInteger("height");
        }
        processOutputFormat(codec, width, height);
    }

    /* access modifiers changed from: protected */
    public boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodec codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, long bufferPresentationTimeUs, boolean shouldSkip, Format format) throws ExoPlaybackException {
        long presentationTimeUs;
        long presentationTimeUs2;
        long unadjustedFrameReleaseTimeNs;
        long j = positionUs;
        long j2 = elapsedRealtimeUs;
        MediaCodec mediaCodec = codec;
        int i = bufferIndex;
        long j3 = bufferPresentationTimeUs;
        if (this.initialPositionUs == C0841C.TIME_UNSET) {
            this.initialPositionUs = j;
        }
        long presentationTimeUs3 = j3 - this.outputStreamOffsetUs;
        if (shouldSkip) {
            skipOutputBuffer(mediaCodec, i, presentationTimeUs3);
            return true;
        }
        long earlyUs = j3 - j;
        if (this.surface != this.dummySurface) {
            long elapsedRealtimeNowUs = SystemClock.elapsedRealtime() * 1000;
            boolean isStarted = getState() == 2;
            if (!this.renderedFirstFrame) {
                presentationTimeUs = presentationTimeUs3;
            } else if (!isStarted || !shouldForceRenderOutputBuffer(earlyUs, elapsedRealtimeNowUs - this.lastRenderTimeUs)) {
                if (isStarted) {
                    if (j != this.initialPositionUs) {
                        long systemTimeNs = System.nanoTime();
                        long adjustedReleaseTimeNs = this.frameReleaseTimeHelper.adjustReleaseTime(j3, systemTimeNs + ((earlyUs - (elapsedRealtimeNowUs - j2)) * 1000));
                        long earlyUs2 = (adjustedReleaseTimeNs - systemTimeNs) / 1000;
                        if (shouldDropBuffersToKeyframe(earlyUs2, j2)) {
                            unadjustedFrameReleaseTimeNs = earlyUs2;
                            presentationTimeUs2 = presentationTimeUs3;
                            if (maybeDropBuffersToKeyframe(codec, bufferIndex, presentationTimeUs3, positionUs)) {
                                return false;
                            }
                        } else {
                            presentationTimeUs2 = presentationTimeUs3;
                            unadjustedFrameReleaseTimeNs = earlyUs2;
                        }
                        if (shouldDropOutputBuffer(unadjustedFrameReleaseTimeNs, j2)) {
                            dropOutputBuffer(mediaCodec, i, presentationTimeUs2);
                            return true;
                        }
                        long presentationTimeUs4 = presentationTimeUs2;
                        if (Util.SDK_INT < 21) {
                            long presentationTimeUs5 = presentationTimeUs4;
                            if (unadjustedFrameReleaseTimeNs < 30000) {
                                if (unadjustedFrameReleaseTimeNs > 11000) {
                                    try {
                                        Thread.sleep((unadjustedFrameReleaseTimeNs - 10000) / 1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        return false;
                                    }
                                }
                                notifyFrameMetadataListener(presentationTimeUs5, adjustedReleaseTimeNs, format);
                                renderOutputBuffer(mediaCodec, i, presentationTimeUs5);
                                return true;
                            }
                        } else if (unadjustedFrameReleaseTimeNs < 50000) {
                            notifyFrameMetadataListener(presentationTimeUs4, adjustedReleaseTimeNs, format);
                            renderOutputBufferV21(codec, bufferIndex, presentationTimeUs4, adjustedReleaseTimeNs);
                            return true;
                        }
                        return false;
                    }
                }
                return false;
            } else {
                presentationTimeUs = presentationTimeUs3;
            }
            long releaseTimeNs = System.nanoTime();
            long presentationTimeUs6 = presentationTimeUs;
            notifyFrameMetadataListener(presentationTimeUs, releaseTimeNs, format);
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(codec, bufferIndex, presentationTimeUs6, releaseTimeNs);
                return true;
            }
            renderOutputBuffer(mediaCodec, i, presentationTimeUs6);
            return true;
        } else if (!isBufferLate(earlyUs)) {
            return false;
        } else {
            skipOutputBuffer(mediaCodec, i, presentationTimeUs3);
            return true;
        }
    }

    private void processOutputFormat(MediaCodec codec, int width, int height) {
        this.currentWidth = width;
        this.currentHeight = height;
        this.currentPixelWidthHeightRatio = this.pendingPixelWidthHeightRatio;
        if (Util.SDK_INT >= 21) {
            int i = this.pendingRotationDegrees;
            if (i == 90 || i == 270) {
                int rotatedHeight = this.currentWidth;
                this.currentWidth = this.currentHeight;
                this.currentHeight = rotatedHeight;
                this.currentPixelWidthHeightRatio = 1.0f / this.currentPixelWidthHeightRatio;
            }
        } else {
            this.currentUnappliedRotationDegrees = this.pendingRotationDegrees;
        }
        codec.setVideoScalingMode(this.scalingMode);
    }

    private void notifyFrameMetadataListener(long presentationTimeUs, long releaseTimeNs, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(presentationTimeUs, releaseTimeNs, format);
        }
    }

    /* access modifiers changed from: protected */
    public long getOutputStreamOffsetUs() {
        return this.outputStreamOffsetUs;
    }

    /* access modifiers changed from: protected */
    public void onProcessedTunneledBuffer(long presentationTimeUs) {
        Format format = updateOutputFormatForTime(presentationTimeUs);
        if (format != null) {
            processOutputFormat(getCodec(), format.width, format.height);
        }
        maybeNotifyVideoSizeChanged();
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(presentationTimeUs);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onProcessedOutputBuffer(long presentationTimeUs) {
        this.buffersInCodecCount--;
        while (true) {
            int i = this.pendingOutputStreamOffsetCount;
            if (i != 0 && presentationTimeUs >= this.pendingOutputStreamSwitchTimesUs[0]) {
                long[] jArr = this.pendingOutputStreamOffsetsUs;
                this.outputStreamOffsetUs = jArr[0];
                this.pendingOutputStreamOffsetCount = i - 1;
                System.arraycopy(jArr, 1, jArr, 0, this.pendingOutputStreamOffsetCount);
                long[] jArr2 = this.pendingOutputStreamSwitchTimesUs;
                System.arraycopy(jArr2, 1, jArr2, 0, this.pendingOutputStreamOffsetCount);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldDropOutputBuffer(long earlyUs, long elapsedRealtimeUs) {
        return isBufferLate(earlyUs);
    }

    /* access modifiers changed from: protected */
    public boolean shouldDropBuffersToKeyframe(long earlyUs, long elapsedRealtimeUs) {
        return isBufferVeryLate(earlyUs);
    }

    /* access modifiers changed from: protected */
    public boolean shouldForceRenderOutputBuffer(long earlyUs, long elapsedSinceLastRenderUs) {
        return isBufferLate(earlyUs) && elapsedSinceLastRenderUs > 100000;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void}
     arg types: [int, int]
     candidates:
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, long):void}
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void} */
    /* access modifiers changed from: protected */
    public void skipOutputBuffer(MediaCodec codec, int index, long presentationTimeUs) {
        TraceUtil.beginSection("skipVideoBuffer");
        codec.releaseOutputBuffer(index, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void}
     arg types: [int, int]
     candidates:
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, long):void}
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void} */
    /* access modifiers changed from: protected */
    public void dropOutputBuffer(MediaCodec codec, int index, long presentationTimeUs) {
        TraceUtil.beginSection("dropVideoBuffer");
        codec.releaseOutputBuffer(index, false);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    /* access modifiers changed from: protected */
    public boolean maybeDropBuffersToKeyframe(MediaCodec codec, int index, long presentationTimeUs, long positionUs) throws ExoPlaybackException {
        int droppedSourceBufferCount = skipSource(positionUs);
        if (droppedSourceBufferCount == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        updateDroppedBufferCounters(this.buffersInCodecCount + droppedSourceBufferCount);
        flushOrReinitializeCodec();
        return true;
    }

    /* access modifiers changed from: protected */
    public void updateDroppedBufferCounters(int droppedBufferCount) {
        this.decoderCounters.droppedBufferCount += droppedBufferCount;
        this.droppedFrames += droppedBufferCount;
        this.consecutiveDroppedFrameCount += droppedBufferCount;
        this.decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.consecutiveDroppedFrameCount, this.decoderCounters.maxConsecutiveDroppedBufferCount);
        int i = this.maxDroppedFramesToNotify;
        if (i > 0 && this.droppedFrames >= i) {
            maybeNotifyDroppedFrames();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void}
     arg types: [int, int]
     candidates:
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, long):void}
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void} */
    /* access modifiers changed from: protected */
    public void renderOutputBuffer(MediaCodec codec, int index, long presentationTimeUs) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        codec.releaseOutputBuffer(index, true);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    /* access modifiers changed from: protected */
    @TargetApi(21)
    public void renderOutputBufferV21(MediaCodec codec, int index, long presentationTimeUs, long releaseTimeNs) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        codec.releaseOutputBuffer(index, releaseTimeNs);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    private boolean shouldUseDummySurface(MediaCodecInfo codecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !codecNeedsSetOutputSurfaceWorkaround(codecInfo.name) && (!codecInfo.secure || DummySurface.isSecureSupported(this.context));
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C0841C.TIME_UNSET;
    }

    private void clearRenderedFirstFrame() {
        MediaCodec codec;
        this.renderedFirstFrame = false;
        if (Util.SDK_INT >= 23 && this.tunneling && (codec = getCodec()) != null) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
        }
    }

    /* access modifiers changed from: package-private */
    public void maybeNotifyRenderedFirstFrame() {
        if (!this.renderedFirstFrame) {
            this.renderedFirstFrame = true;
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.renderedFirstFrame) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void clearReportedVideoSize() {
        this.reportedWidth = -1;
        this.reportedHeight = -1;
        this.reportedPixelWidthHeightRatio = -1.0f;
        this.reportedUnappliedRotationDegrees = -1;
    }

    private void maybeNotifyVideoSizeChanged() {
        if (this.currentWidth != -1 || this.currentHeight != -1) {
            if (this.reportedWidth != this.currentWidth || this.reportedHeight != this.currentHeight || this.reportedUnappliedRotationDegrees != this.currentUnappliedRotationDegrees || this.reportedPixelWidthHeightRatio != this.currentPixelWidthHeightRatio) {
                this.eventDispatcher.videoSizeChanged(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
                this.reportedWidth = this.currentWidth;
                this.reportedHeight = this.currentHeight;
                this.reportedUnappliedRotationDegrees = this.currentUnappliedRotationDegrees;
                this.reportedPixelWidthHeightRatio = this.currentPixelWidthHeightRatio;
            }
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        if (this.reportedWidth != -1 || this.reportedHeight != -1) {
            this.eventDispatcher.videoSizeChanged(this.reportedWidth, this.reportedHeight, this.reportedUnappliedRotationDegrees, this.reportedPixelWidthHeightRatio);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long now = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, now - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = now;
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"InlinedApi"})
    public MediaFormat getMediaFormat(Format format, CodecMaxValues codecMaxValues2, float codecOperatingRate, boolean deviceNeedsNoPostProcessWorkaround2, int tunnelingAudioSessionId2) {
        Pair<Integer, Integer> codecProfileAndLevel;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", format.sampleMimeType);
        mediaFormat.setInteger("width", format.width);
        mediaFormat.setInteger("height", format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format.codecs)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, "profile", ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues2.width);
        mediaFormat.setInteger("max-height", codecMaxValues2.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues2.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(TalkContract.CommonPresenceColumns.PRIORITY, 0);
            if (codecOperatingRate != -1.0f) {
                mediaFormat.setFloat("operating-rate", codecOperatingRate);
            }
        }
        if (deviceNeedsNoPostProcessWorkaround2) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (tunnelingAudioSessionId2 != 0) {
            configureTunnelingV21(mediaFormat, tunnelingAudioSessionId2);
        }
        return mediaFormat;
    }

    /* access modifiers changed from: protected */
    public CodecMaxValues getCodecMaxValues(MediaCodecInfo codecInfo, Format format, Format[] streamFormats) throws MediaCodecUtil.DecoderQueryException {
        int codecMaxInputSize;
        int maxWidth = format.width;
        int maxHeight = format.height;
        int maxInputSize = getMaxInputSize(codecInfo, format);
        if (streamFormats.length == 1) {
            if (!(maxInputSize == -1 || (codecMaxInputSize = getCodecMaxInputSize(codecInfo, format.sampleMimeType, format.width, format.height)) == -1)) {
                maxInputSize = Math.min((int) (((float) maxInputSize) * INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR), codecMaxInputSize);
            }
            return new CodecMaxValues(maxWidth, maxHeight, maxInputSize);
        }
        boolean haveUnknownDimensions = false;
        int maxInputSize2 = maxInputSize;
        int maxHeight2 = maxHeight;
        int maxWidth2 = maxWidth;
        for (Format streamFormat : streamFormats) {
            if (codecInfo.isSeamlessAdaptationSupported(format, streamFormat, false)) {
                haveUnknownDimensions |= streamFormat.width == -1 || streamFormat.height == -1;
                maxWidth2 = Math.max(maxWidth2, streamFormat.width);
                maxHeight2 = Math.max(maxHeight2, streamFormat.height);
                maxInputSize2 = Math.max(maxInputSize2, getMaxInputSize(codecInfo, streamFormat));
            }
        }
        if (haveUnknownDimensions) {
            StringBuilder sb = new StringBuilder(66);
            sb.append("Resolutions unknown. Codec max resolution: ");
            sb.append(maxWidth2);
            sb.append("x");
            sb.append(maxHeight2);
            Log.m30w(TAG, sb.toString());
            Point codecMaxSize = getCodecMaxSize(codecInfo, format);
            if (codecMaxSize != null) {
                maxWidth2 = Math.max(maxWidth2, codecMaxSize.x);
                maxHeight2 = Math.max(maxHeight2, codecMaxSize.y);
                maxInputSize2 = Math.max(maxInputSize2, getCodecMaxInputSize(codecInfo, format.sampleMimeType, maxWidth2, maxHeight2));
                StringBuilder sb2 = new StringBuilder(57);
                sb2.append("Codec max resolution adjusted to: ");
                sb2.append(maxWidth2);
                sb2.append("x");
                sb2.append(maxHeight2);
                Log.m30w(TAG, sb2.toString());
            }
        }
        return new CodecMaxValues(maxWidth2, maxHeight2, maxInputSize2);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:410:0x0639 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean codecNeedsSetOutputSurfaceWorkaround(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "OMX.google"
            boolean r0 = r8.startsWith(r0)
            r1 = 0
            if (r0 == 0) goto L_0x000a
            return r1
        L_0x000a:
            java.lang.Class<com.google.android.exoplayer2.video.MediaCodecVideoRenderer> r0 = com.google.android.exoplayer2.video.MediaCodecVideoRenderer.class
            monitor-enter(r0)
            boolean r2 = com.google.android.exoplayer2.video.MediaCodecVideoRenderer.evaluatedDeviceNeedsSetOutputSurfaceWorkaround     // Catch:{ all -> 0x0645 }
            if (r2 != 0) goto L_0x0641
            int r2 = com.google.android.exoplayer2.util.Util.SDK_INT     // Catch:{ all -> 0x0645 }
            r3 = 27
            r4 = 1
            if (r2 > r3) goto L_0x0030
            java.lang.String r2 = "dangal"
            java.lang.String r5 = com.google.android.exoplayer2.util.Util.DEVICE     // Catch:{ all -> 0x0645 }
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x0645 }
            if (r2 != 0) goto L_0x002c
            java.lang.String r2 = "HWEML"
            java.lang.String r5 = com.google.android.exoplayer2.util.Util.DEVICE     // Catch:{ all -> 0x0645 }
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0030
        L_0x002c:
            com.google.android.exoplayer2.video.MediaCodecVideoRenderer.deviceNeedsSetOutputSurfaceWorkaround = r4     // Catch:{ all -> 0x0645 }
            goto L_0x063f
        L_0x0030:
            int r2 = com.google.android.exoplayer2.util.Util.SDK_INT     // Catch:{ all -> 0x0645 }
            if (r2 < r3) goto L_0x0036
            goto L_0x063f
        L_0x0036:
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.DEVICE     // Catch:{ all -> 0x0645 }
            int r5 = r2.hashCode()     // Catch:{ all -> 0x0645 }
            r6 = -1
            switch(r5) {
                case -2144781245: goto L_0x05ff;
                case -2144781185: goto L_0x05f4;
                case -2144781160: goto L_0x05e9;
                case -2097309513: goto L_0x05de;
                case -2022874474: goto L_0x05d3;
                case -1978993182: goto L_0x05c8;
                case -1978990237: goto L_0x05bd;
                case -1936688988: goto L_0x05b2;
                case -1936688066: goto L_0x05a7;
                case -1936688065: goto L_0x059b;
                case -1931988508: goto L_0x058f;
                case -1696512866: goto L_0x0583;
                case -1680025915: goto L_0x0577;
                case -1615810839: goto L_0x056b;
                case -1554255044: goto L_0x055e;
                case -1481772737: goto L_0x0552;
                case -1481772730: goto L_0x0546;
                case -1481772729: goto L_0x053a;
                case -1320080169: goto L_0x052e;
                case -1217592143: goto L_0x0522;
                case -1180384755: goto L_0x0516;
                case -1139198265: goto L_0x050a;
                case -1052835013: goto L_0x04fe;
                case -993250464: goto L_0x04f3;
                case -965403638: goto L_0x04e6;
                case -958336948: goto L_0x04da;
                case -879245230: goto L_0x04cd;
                case -842500323: goto L_0x04c1;
                case -821392978: goto L_0x04b6;
                case -797483286: goto L_0x04aa;
                case -794946968: goto L_0x049d;
                case -788334647: goto L_0x0490;
                case -782144577: goto L_0x0484;
                case -575125681: goto L_0x0478;
                case -521118391: goto L_0x046c;
                case -430914369: goto L_0x0460;
                case -290434366: goto L_0x0453;
                case -282781963: goto L_0x0447;
                case -277133239: goto L_0x043b;
                case -173639913: goto L_0x042f;
                case -56598463: goto L_0x0422;
                case 2126: goto L_0x0416;
                case 2564: goto L_0x040a;
                case 2715: goto L_0x03fe;
                case 2719: goto L_0x03f2;
                case 3483: goto L_0x03e6;
                case 73405: goto L_0x03da;
                case 75739: goto L_0x03ce;
                case 76779: goto L_0x03c2;
                case 78669: goto L_0x03b6;
                case 79305: goto L_0x03aa;
                case 80618: goto L_0x039e;
                case 88274: goto L_0x0392;
                case 98846: goto L_0x0386;
                case 98848: goto L_0x037a;
                case 99329: goto L_0x036e;
                case 101481: goto L_0x0362;
                case 1513190: goto L_0x0357;
                case 1514184: goto L_0x034c;
                case 1514185: goto L_0x0341;
                case 2436959: goto L_0x0335;
                case 2463773: goto L_0x0329;
                case 2464648: goto L_0x031d;
                case 2689555: goto L_0x0311;
                case 3154429: goto L_0x0305;
                case 3284551: goto L_0x02f9;
                case 3351335: goto L_0x02ed;
                case 3386211: goto L_0x02e1;
                case 41325051: goto L_0x02d5;
                case 55178625: goto L_0x02c9;
                case 61542055: goto L_0x02be;
                case 65355429: goto L_0x02b2;
                case 66214468: goto L_0x02a6;
                case 66214470: goto L_0x029a;
                case 66214473: goto L_0x028e;
                case 66215429: goto L_0x0282;
                case 66215431: goto L_0x0276;
                case 66215433: goto L_0x026a;
                case 66216390: goto L_0x025e;
                case 76402249: goto L_0x0252;
                case 76404105: goto L_0x0246;
                case 76404911: goto L_0x023a;
                case 80963634: goto L_0x022e;
                case 82882791: goto L_0x0222;
                case 98715550: goto L_0x0216;
                case 102844228: goto L_0x020a;
                case 165221241: goto L_0x01ff;
                case 182191441: goto L_0x01f3;
                case 245388979: goto L_0x01e7;
                case 287431619: goto L_0x01db;
                case 307593612: goto L_0x01cf;
                case 308517133: goto L_0x01c3;
                case 316215098: goto L_0x01b7;
                case 316215116: goto L_0x01ab;
                case 316246811: goto L_0x019f;
                case 316246818: goto L_0x0193;
                case 407160593: goto L_0x0187;
                case 507412548: goto L_0x017b;
                case 793982701: goto L_0x016f;
                case 794038622: goto L_0x0163;
                case 794040393: goto L_0x0157;
                case 835649806: goto L_0x014b;
                case 917340916: goto L_0x0140;
                case 958008161: goto L_0x0134;
                case 1060579533: goto L_0x0128;
                case 1150207623: goto L_0x011c;
                case 1176899427: goto L_0x0110;
                case 1280332038: goto L_0x0104;
                case 1306947716: goto L_0x00f8;
                case 1349174697: goto L_0x00ec;
                case 1522194893: goto L_0x00df;
                case 1691543273: goto L_0x00d3;
                case 1709443163: goto L_0x00c7;
                case 1865889110: goto L_0x00ba;
                case 1906253259: goto L_0x00ae;
                case 1977196784: goto L_0x00a2;
                case 2006372676: goto L_0x0096;
                case 2029784656: goto L_0x008a;
                case 2030379515: goto L_0x007e;
                case 2033393791: goto L_0x0072;
                case 2047190025: goto L_0x0066;
                case 2047252157: goto L_0x005a;
                case 2048319463: goto L_0x004e;
                case 2048855701: goto L_0x0042;
                default: goto L_0x0040;
            }     // Catch:{ all -> 0x0645 }
        L_0x0040:
            goto L_0x060a
        L_0x0042:
            java.lang.String r3 = "HWWAS-H"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 54
            goto L_0x060b
        L_0x004e:
            java.lang.String r3 = "HWVNS-H"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 53
            goto L_0x060b
        L_0x005a:
            java.lang.String r5 = "ELUGA_Prim"
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 27
            goto L_0x060b
        L_0x0066:
            java.lang.String r3 = "ELUGA_Note"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 26
            goto L_0x060b
        L_0x0072:
            java.lang.String r3 = "ASUS_X00AD_2"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 11
            goto L_0x060b
        L_0x007e:
            java.lang.String r3 = "HWCAM-H"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 52
            goto L_0x060b
        L_0x008a:
            java.lang.String r3 = "HWBLN-H"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 51
            goto L_0x060b
        L_0x0096:
            java.lang.String r3 = "BRAVIA_ATV3_4K"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 15
            goto L_0x060b
        L_0x00a2:
            java.lang.String r3 = "Infinix-X572"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 57
            goto L_0x060b
        L_0x00ae:
            java.lang.String r3 = "PB2-670M"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 85
            goto L_0x060b
        L_0x00ba:
            java.lang.String r3 = "santoni"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 101(0x65, float:1.42E-43)
            goto L_0x060b
        L_0x00c7:
            java.lang.String r3 = "iball8735_9806"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 56
            goto L_0x060b
        L_0x00d3:
            java.lang.String r3 = "CPH1609"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 19
            goto L_0x060b
        L_0x00df:
            java.lang.String r3 = "woods_f"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 117(0x75, float:1.64E-43)
            goto L_0x060b
        L_0x00ec:
            java.lang.String r3 = "htc_e56ml_dtul"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 49
            goto L_0x060b
        L_0x00f8:
            java.lang.String r3 = "EverStar_S"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 29
            goto L_0x060b
        L_0x0104:
            java.lang.String r3 = "hwALE-H"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 50
            goto L_0x060b
        L_0x0110:
            java.lang.String r3 = "itel_S41"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 59
            goto L_0x060b
        L_0x011c:
            java.lang.String r3 = "LS-5017"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 65
            goto L_0x060b
        L_0x0128:
            java.lang.String r3 = "panell_d"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 81
            goto L_0x060b
        L_0x0134:
            java.lang.String r3 = "j2xlteins"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 60
            goto L_0x060b
        L_0x0140:
            java.lang.String r3 = "A7000plus"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 7
            goto L_0x060b
        L_0x014b:
            java.lang.String r3 = "manning"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 67
            goto L_0x060b
        L_0x0157:
            java.lang.String r3 = "GIONEE_WBL7519"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 47
            goto L_0x060b
        L_0x0163:
            java.lang.String r3 = "GIONEE_WBL7365"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 46
            goto L_0x060b
        L_0x016f:
            java.lang.String r3 = "GIONEE_WBL5708"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 45
            goto L_0x060b
        L_0x017b:
            java.lang.String r3 = "QM16XE_U"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 99
            goto L_0x060b
        L_0x0187:
            java.lang.String r3 = "Pixi5-10_4G"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 91
            goto L_0x060b
        L_0x0193:
            java.lang.String r3 = "TB3-850M"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 109(0x6d, float:1.53E-43)
            goto L_0x060b
        L_0x019f:
            java.lang.String r3 = "TB3-850F"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 108(0x6c, float:1.51E-43)
            goto L_0x060b
        L_0x01ab:
            java.lang.String r3 = "TB3-730X"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 107(0x6b, float:1.5E-43)
            goto L_0x060b
        L_0x01b7:
            java.lang.String r3 = "TB3-730F"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 106(0x6a, float:1.49E-43)
            goto L_0x060b
        L_0x01c3:
            java.lang.String r3 = "A7020a48"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 9
            goto L_0x060b
        L_0x01cf:
            java.lang.String r3 = "A7010a48"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 8
            goto L_0x060b
        L_0x01db:
            java.lang.String r3 = "griffin"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 48
            goto L_0x060b
        L_0x01e7:
            java.lang.String r3 = "marino_f"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 68
            goto L_0x060b
        L_0x01f3:
            java.lang.String r3 = "CPY83_I00"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 20
            goto L_0x060b
        L_0x01ff:
            java.lang.String r3 = "A2016a40"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 5
            goto L_0x060b
        L_0x020a:
            java.lang.String r3 = "le_x6"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 64
            goto L_0x060b
        L_0x0216:
            java.lang.String r3 = "i9031"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 55
            goto L_0x060b
        L_0x0222:
            java.lang.String r3 = "X3_HK"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 119(0x77, float:1.67E-43)
            goto L_0x060b
        L_0x022e:
            java.lang.String r3 = "V23GB"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 112(0x70, float:1.57E-43)
            goto L_0x060b
        L_0x023a:
            java.lang.String r3 = "Q4310"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 97
            goto L_0x060b
        L_0x0246:
            java.lang.String r3 = "Q4260"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 95
            goto L_0x060b
        L_0x0252:
            java.lang.String r3 = "PRO7S"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 93
            goto L_0x060b
        L_0x025e:
            java.lang.String r3 = "F3311"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 36
            goto L_0x060b
        L_0x026a:
            java.lang.String r3 = "F3215"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 35
            goto L_0x060b
        L_0x0276:
            java.lang.String r3 = "F3213"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 34
            goto L_0x060b
        L_0x0282:
            java.lang.String r3 = "F3211"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 33
            goto L_0x060b
        L_0x028e:
            java.lang.String r3 = "F3116"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 32
            goto L_0x060b
        L_0x029a:
            java.lang.String r3 = "F3113"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 31
            goto L_0x060b
        L_0x02a6:
            java.lang.String r3 = "F3111"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 30
            goto L_0x060b
        L_0x02b2:
            java.lang.String r3 = "E5643"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 24
            goto L_0x060b
        L_0x02be:
            java.lang.String r3 = "A1601"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 4
            goto L_0x060b
        L_0x02c9:
            java.lang.String r3 = "Aura_Note_2"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 12
            goto L_0x060b
        L_0x02d5:
            java.lang.String r3 = "MEIZU_M5"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 69
            goto L_0x060b
        L_0x02e1:
            java.lang.String r3 = "p212"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 78
            goto L_0x060b
        L_0x02ed:
            java.lang.String r3 = "mido"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 71
            goto L_0x060b
        L_0x02f9:
            java.lang.String r3 = "kate"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 63
            goto L_0x060b
        L_0x0305:
            java.lang.String r3 = "fugu"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 38
            goto L_0x060b
        L_0x0311:
            java.lang.String r3 = "XE2X"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 120(0x78, float:1.68E-43)
            goto L_0x060b
        L_0x031d:
            java.lang.String r3 = "Q427"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 96
            goto L_0x060b
        L_0x0329:
            java.lang.String r3 = "Q350"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 94
            goto L_0x060b
        L_0x0335:
            java.lang.String r3 = "P681"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 79
            goto L_0x060b
        L_0x0341:
            java.lang.String r3 = "1714"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 2
            goto L_0x060b
        L_0x034c:
            java.lang.String r3 = "1713"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 1
            goto L_0x060b
        L_0x0357:
            java.lang.String r3 = "1601"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 0
            goto L_0x060b
        L_0x0362:
            java.lang.String r3 = "flo"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 37
            goto L_0x060b
        L_0x036e:
            java.lang.String r3 = "deb"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 23
            goto L_0x060b
        L_0x037a:
            java.lang.String r3 = "cv3"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 22
            goto L_0x060b
        L_0x0386:
            java.lang.String r3 = "cv1"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 21
            goto L_0x060b
        L_0x0392:
            java.lang.String r3 = "Z80"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 123(0x7b, float:1.72E-43)
            goto L_0x060b
        L_0x039e:
            java.lang.String r3 = "QX1"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 100
            goto L_0x060b
        L_0x03aa:
            java.lang.String r3 = "PLE"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 92
            goto L_0x060b
        L_0x03b6:
            java.lang.String r3 = "P85"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 80
            goto L_0x060b
        L_0x03c2:
            java.lang.String r3 = "MX6"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 72
            goto L_0x060b
        L_0x03ce:
            java.lang.String r3 = "M5c"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 66
            goto L_0x060b
        L_0x03da:
            java.lang.String r3 = "JGZ"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 61
            goto L_0x060b
        L_0x03e6:
            java.lang.String r3 = "mh"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 70
            goto L_0x060b
        L_0x03f2:
            java.lang.String r3 = "V5"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 113(0x71, float:1.58E-43)
            goto L_0x060b
        L_0x03fe:
            java.lang.String r3 = "V1"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 111(0x6f, float:1.56E-43)
            goto L_0x060b
        L_0x040a:
            java.lang.String r3 = "Q5"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 98
            goto L_0x060b
        L_0x0416:
            java.lang.String r3 = "C1"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 16
            goto L_0x060b
        L_0x0422:
            java.lang.String r3 = "woods_fn"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 118(0x76, float:1.65E-43)
            goto L_0x060b
        L_0x042f:
            java.lang.String r3 = "ELUGA_A3_Pro"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 25
            goto L_0x060b
        L_0x043b:
            java.lang.String r3 = "Z12_PRO"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 122(0x7a, float:1.71E-43)
            goto L_0x060b
        L_0x0447:
            java.lang.String r3 = "BLACK-1X"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 13
            goto L_0x060b
        L_0x0453:
            java.lang.String r3 = "taido_row"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 105(0x69, float:1.47E-43)
            goto L_0x060b
        L_0x0460:
            java.lang.String r3 = "Pixi4-7_3G"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 90
            goto L_0x060b
        L_0x046c:
            java.lang.String r3 = "GIONEE_GBL7360"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 41
            goto L_0x060b
        L_0x0478:
            java.lang.String r3 = "GiONEE_CBL7513"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 39
            goto L_0x060b
        L_0x0484:
            java.lang.String r3 = "OnePlus5T"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 77
            goto L_0x060b
        L_0x0490:
            java.lang.String r3 = "whyred"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 116(0x74, float:1.63E-43)
            goto L_0x060b
        L_0x049d:
            java.lang.String r3 = "watson"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 115(0x73, float:1.61E-43)
            goto L_0x060b
        L_0x04aa:
            java.lang.String r3 = "SVP-DTV15"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 103(0x67, float:1.44E-43)
            goto L_0x060b
        L_0x04b6:
            java.lang.String r3 = "A7000-a"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 6
            goto L_0x060b
        L_0x04c1:
            java.lang.String r3 = "nicklaus_f"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 74
            goto L_0x060b
        L_0x04cd:
            java.lang.String r3 = "tcl_eu"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 110(0x6e, float:1.54E-43)
            goto L_0x060b
        L_0x04da:
            java.lang.String r3 = "ELUGA_Ray_X"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 28
            goto L_0x060b
        L_0x04e6:
            java.lang.String r3 = "s905x018"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 104(0x68, float:1.46E-43)
            goto L_0x060b
        L_0x04f3:
            java.lang.String r3 = "A10-70F"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 3
            goto L_0x060b
        L_0x04fe:
            java.lang.String r3 = "namath"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 73
            goto L_0x060b
        L_0x050a:
            java.lang.String r3 = "Slate_Pro"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 102(0x66, float:1.43E-43)
            goto L_0x060b
        L_0x0516:
            java.lang.String r3 = "iris60"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 58
            goto L_0x060b
        L_0x0522:
            java.lang.String r3 = "BRAVIA_ATV2"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 14
            goto L_0x060b
        L_0x052e:
            java.lang.String r3 = "GiONEE_GBL7319"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 40
            goto L_0x060b
        L_0x053a:
            java.lang.String r3 = "panell_dt"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 84
            goto L_0x060b
        L_0x0546:
            java.lang.String r3 = "panell_ds"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 83
            goto L_0x060b
        L_0x0552:
            java.lang.String r3 = "panell_dl"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 82
            goto L_0x060b
        L_0x055e:
            java.lang.String r3 = "vernee_M5"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 114(0x72, float:1.6E-43)
            goto L_0x060b
        L_0x056b:
            java.lang.String r3 = "Phantom6"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 89
            goto L_0x060b
        L_0x0577:
            java.lang.String r3 = "ComioS1"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 17
            goto L_0x060b
        L_0x0583:
            java.lang.String r3 = "XT1663"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 121(0x79, float:1.7E-43)
            goto L_0x060b
        L_0x058f:
            java.lang.String r3 = "AquaPowerM"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 10
            goto L_0x060b
        L_0x059b:
            java.lang.String r3 = "PGN611"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 88
            goto L_0x060b
        L_0x05a7:
            java.lang.String r3 = "PGN610"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 87
            goto L_0x060b
        L_0x05b2:
            java.lang.String r3 = "PGN528"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 86
            goto L_0x060b
        L_0x05bd:
            java.lang.String r3 = "NX573J"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 76
            goto L_0x060b
        L_0x05c8:
            java.lang.String r3 = "NX541J"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 75
            goto L_0x060b
        L_0x05d3:
            java.lang.String r3 = "CP8676_I02"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 18
            goto L_0x060b
        L_0x05de:
            java.lang.String r3 = "K50a40"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 62
            goto L_0x060b
        L_0x05e9:
            java.lang.String r3 = "GIONEE_SWW1631"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 44
            goto L_0x060b
        L_0x05f4:
            java.lang.String r3 = "GIONEE_SWW1627"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 43
            goto L_0x060b
        L_0x05ff:
            java.lang.String r3 = "GIONEE_SWW1609"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0040
            r2 = 42
            goto L_0x060b
        L_0x060a:
            r2 = -1
        L_0x060b:
            switch(r2) {
                case 0: goto L_0x060f;
                case 1: goto L_0x060f;
                case 2: goto L_0x060f;
                case 3: goto L_0x060f;
                case 4: goto L_0x060f;
                case 5: goto L_0x060f;
                case 6: goto L_0x060f;
                case 7: goto L_0x060f;
                case 8: goto L_0x060f;
                case 9: goto L_0x060f;
                case 10: goto L_0x060f;
                case 11: goto L_0x060f;
                case 12: goto L_0x060f;
                case 13: goto L_0x060f;
                case 14: goto L_0x060f;
                case 15: goto L_0x060f;
                case 16: goto L_0x060f;
                case 17: goto L_0x060f;
                case 18: goto L_0x060f;
                case 19: goto L_0x060f;
                case 20: goto L_0x060f;
                case 21: goto L_0x060f;
                case 22: goto L_0x060f;
                case 23: goto L_0x060f;
                case 24: goto L_0x060f;
                case 25: goto L_0x060f;
                case 26: goto L_0x060f;
                case 27: goto L_0x060f;
                case 28: goto L_0x060f;
                case 29: goto L_0x060f;
                case 30: goto L_0x060f;
                case 31: goto L_0x060f;
                case 32: goto L_0x060f;
                case 33: goto L_0x060f;
                case 34: goto L_0x060f;
                case 35: goto L_0x060f;
                case 36: goto L_0x060f;
                case 37: goto L_0x060f;
                case 38: goto L_0x060f;
                case 39: goto L_0x060f;
                case 40: goto L_0x060f;
                case 41: goto L_0x060f;
                case 42: goto L_0x060f;
                case 43: goto L_0x060f;
                case 44: goto L_0x060f;
                case 45: goto L_0x060f;
                case 46: goto L_0x060f;
                case 47: goto L_0x060f;
                case 48: goto L_0x060f;
                case 49: goto L_0x060f;
                case 50: goto L_0x060f;
                case 51: goto L_0x060f;
                case 52: goto L_0x060f;
                case 53: goto L_0x060f;
                case 54: goto L_0x060f;
                case 55: goto L_0x060f;
                case 56: goto L_0x060f;
                case 57: goto L_0x060f;
                case 58: goto L_0x060f;
                case 59: goto L_0x060f;
                case 60: goto L_0x060f;
                case 61: goto L_0x060f;
                case 62: goto L_0x060f;
                case 63: goto L_0x060f;
                case 64: goto L_0x060f;
                case 65: goto L_0x060f;
                case 66: goto L_0x060f;
                case 67: goto L_0x060f;
                case 68: goto L_0x060f;
                case 69: goto L_0x060f;
                case 70: goto L_0x060f;
                case 71: goto L_0x060f;
                case 72: goto L_0x060f;
                case 73: goto L_0x060f;
                case 74: goto L_0x060f;
                case 75: goto L_0x060f;
                case 76: goto L_0x060f;
                case 77: goto L_0x060f;
                case 78: goto L_0x060f;
                case 79: goto L_0x060f;
                case 80: goto L_0x060f;
                case 81: goto L_0x060f;
                case 82: goto L_0x060f;
                case 83: goto L_0x060f;
                case 84: goto L_0x060f;
                case 85: goto L_0x060f;
                case 86: goto L_0x060f;
                case 87: goto L_0x060f;
                case 88: goto L_0x060f;
                case 89: goto L_0x060f;
                case 90: goto L_0x060f;
                case 91: goto L_0x060f;
                case 92: goto L_0x060f;
                case 93: goto L_0x060f;
                case 94: goto L_0x060f;
                case 95: goto L_0x060f;
                case 96: goto L_0x060f;
                case 97: goto L_0x060f;
                case 98: goto L_0x060f;
                case 99: goto L_0x060f;
                case 100: goto L_0x060f;
                case 101: goto L_0x060f;
                case 102: goto L_0x060f;
                case 103: goto L_0x060f;
                case 104: goto L_0x060f;
                case 105: goto L_0x060f;
                case 106: goto L_0x060f;
                case 107: goto L_0x060f;
                case 108: goto L_0x060f;
                case 109: goto L_0x060f;
                case 110: goto L_0x060f;
                case 111: goto L_0x060f;
                case 112: goto L_0x060f;
                case 113: goto L_0x060f;
                case 114: goto L_0x060f;
                case 115: goto L_0x060f;
                case 116: goto L_0x060f;
                case 117: goto L_0x060f;
                case 118: goto L_0x060f;
                case 119: goto L_0x060f;
                case 120: goto L_0x060f;
                case 121: goto L_0x060f;
                case 122: goto L_0x060f;
                case 123: goto L_0x060f;
                default: goto L_0x060e;
            }     // Catch:{ all -> 0x0645 }
        L_0x060e:
            goto L_0x0612
        L_0x060f:
            com.google.android.exoplayer2.video.MediaCodecVideoRenderer.deviceNeedsSetOutputSurfaceWorkaround = r4     // Catch:{ all -> 0x0645 }
        L_0x0612:
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.MODEL     // Catch:{ all -> 0x0645 }
            int r3 = r2.hashCode()     // Catch:{ all -> 0x0645 }
            r5 = 2006354(0x1e9d52, float:2.811501E-39)
            if (r3 == r5) goto L_0x062d
            r1 = 2006367(0x1e9d5f, float:2.811519E-39)
            if (r3 == r1) goto L_0x0623
        L_0x0622:
            goto L_0x0636
        L_0x0623:
            java.lang.String r1 = "AFTN"
            boolean r1 = r2.equals(r1)     // Catch:{ all -> 0x0645 }
            if (r1 == 0) goto L_0x0622
            r1 = 1
            goto L_0x0637
        L_0x062d:
            java.lang.String r3 = "AFTA"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0645 }
            if (r2 == 0) goto L_0x0622
            goto L_0x0637
        L_0x0636:
            r1 = -1
        L_0x0637:
            if (r1 == 0) goto L_0x063c
            if (r1 == r4) goto L_0x063c
            goto L_0x063f
        L_0x063c:
            com.google.android.exoplayer2.video.MediaCodecVideoRenderer.deviceNeedsSetOutputSurfaceWorkaround = r4     // Catch:{ all -> 0x0645 }
        L_0x063f:
            com.google.android.exoplayer2.video.MediaCodecVideoRenderer.evaluatedDeviceNeedsSetOutputSurfaceWorkaround = r4     // Catch:{ all -> 0x0645 }
        L_0x0641:
            monitor-exit(r0)     // Catch:{ all -> 0x0645 }
            boolean r0 = com.google.android.exoplayer2.video.MediaCodecVideoRenderer.deviceNeedsSetOutputSurfaceWorkaround
            return r0
        L_0x0645:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0645 }
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.codecNeedsSetOutputSurfaceWorkaround(java.lang.String):boolean");
    }

    protected static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int width2, int height2, int inputSize2) {
            this.width = width2;
            this.height = height2;
            this.inputSize = inputSize2;
        }
    }

    @TargetApi(23)
    private final class OnFrameRenderedListenerV23 implements MediaCodec.OnFrameRenderedListener {
        private OnFrameRenderedListenerV23(MediaCodec codec) {
            codec.setOnFrameRenderedListener(this, new Handler());
        }

        public void onFrameRendered(@NonNull MediaCodec codec, long presentationTimeUs, long nanoTime) {
            if (this == MediaCodecVideoRenderer.this.tunnelingOnFrameRenderedListener) {
                MediaCodecVideoRenderer.this.onProcessedTunneledBuffer(presentationTimeUs);
            }
        }
    }
}
