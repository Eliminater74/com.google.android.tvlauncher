package com.google.android.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MediaCodecRenderer extends BaseRenderer {
    protected static final float CODEC_OPERATING_RATE_UNSET = -1.0f;
    protected static final int KEEP_CODEC_RESULT_NO = 0;
    protected static final int KEEP_CODEC_RESULT_YES_WITHOUT_RECONFIGURATION = 3;
    protected static final int KEEP_CODEC_RESULT_YES_WITH_FLUSH = 1;
    protected static final int KEEP_CODEC_RESULT_YES_WITH_RECONFIGURATION = 2;
    private static final byte[] ADAPTATION_WORKAROUND_BUFFER = Util.getBytesFromHexString("0000016742C00BDA259000000168CE0F13200000016588840DCE7118A0002FBF1C31C3275D78");
    private static final int ADAPTATION_WORKAROUND_MODE_ALWAYS = 2;
    private static final int ADAPTATION_WORKAROUND_MODE_NEVER = 0;
    private static final int ADAPTATION_WORKAROUND_MODE_SAME_RESOLUTION = 1;
    private static final int ADAPTATION_WORKAROUND_SLICE_WIDTH_HEIGHT = 32;
    private static final int DRAIN_ACTION_FLUSH = 1;
    private static final int DRAIN_ACTION_NONE = 0;
    private static final int DRAIN_ACTION_REINITIALIZE = 3;
    private static final int DRAIN_ACTION_UPDATE_DRM_SESSION = 2;
    private static final int DRAIN_STATE_NONE = 0;
    private static final int DRAIN_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int DRAIN_STATE_WAIT_END_OF_STREAM = 2;
    private static final long MAX_CODEC_HOTSWAP_TIME_MS = 1000;
    private static final int RECONFIGURATION_STATE_NONE = 0;
    private static final int RECONFIGURATION_STATE_QUEUE_PENDING = 2;
    private static final int RECONFIGURATION_STATE_WRITE_PENDING = 1;
    private static final String TAG = "MediaCodecRenderer";
    private final float assumedMinimumCodecOperatingRate;
    private final DecoderInputBuffer buffer = new DecoderInputBuffer(0);
    private final ArrayList<Long> decodeOnlyPresentationTimestamps = new ArrayList<>();
    @Nullable
    private final DrmSessionManager<FrameworkMediaCrypto> drmSessionManager;
    private final boolean enableDecoderFallback;
    private final DecoderInputBuffer flagsOnlyBuffer = DecoderInputBuffer.newFlagsOnlyInstance();
    private final FormatHolder formatHolder = new FormatHolder();
    private final TimedValueQueue<Format> formatQueue = new TimedValueQueue<>();
    private final MediaCodecSelector mediaCodecSelector;
    private final MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
    private final boolean playClearSamplesWithoutKeys;
    protected DecoderCounters decoderCounters;
    @Nullable
    private ArrayDeque<MediaCodecInfo> availableCodecInfos;
    @Nullable
    private MediaCodec codec;
    private int codecAdaptationWorkaroundMode;
    private int codecDrainAction = 0;
    private int codecDrainState = 0;
    @Nullable
    private DrmSession<FrameworkMediaCrypto> codecDrmSession;
    @Nullable
    private Format codecFormat;
    private long codecHotswapDeadlineMs;
    @Nullable
    private MediaCodecInfo codecInfo;
    private boolean codecNeedsAdaptationWorkaroundBuffer;
    private boolean codecNeedsDiscardToSpsWorkaround;
    private boolean codecNeedsEosFlushWorkaround;
    private boolean codecNeedsEosOutputExceptionWorkaround;
    private boolean codecNeedsEosPropagation;
    private boolean codecNeedsFlushWorkaround;
    private boolean codecNeedsMonoChannelCountWorkaround;
    private boolean codecNeedsReconfigureWorkaround;
    private float codecOperatingRate = -1.0f;
    private boolean codecReceivedBuffers;
    private boolean codecReceivedEos;
    private int codecReconfigurationState = 0;
    private boolean codecReconfigured;
    private ByteBuffer[] inputBuffers;
    @Nullable
    private Format inputFormat;
    private int inputIndex;
    private boolean inputStreamEnded;
    @Nullable
    private MediaCrypto mediaCrypto;
    private boolean mediaCryptoRequiresSecureDecoder;
    private ByteBuffer outputBuffer;
    private ByteBuffer[] outputBuffers;
    private Format outputFormat;
    private int outputIndex;
    private boolean outputStreamEnded;
    @Nullable
    private DecoderInitializationException preferredDecoderInitializationException;
    private long renderTimeLimitMs = C0841C.TIME_UNSET;
    private float rendererOperatingRate = 1.0f;
    private boolean shouldSkipAdaptationWorkaroundOutputBuffer;
    private boolean shouldSkipOutputBuffer;
    @Nullable
    private DrmSession<FrameworkMediaCrypto> sourceDrmSession;
    private boolean waitingForFirstSampleInFormat;
    private boolean waitingForFirstSyncSample;
    private boolean waitingForKeys;

    public MediaCodecRenderer(int trackType, MediaCodecSelector mediaCodecSelector2, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager2, boolean playClearSamplesWithoutKeys2, boolean enableDecoderFallback2, float assumedMinimumCodecOperatingRate2) {
        super(trackType);
        this.mediaCodecSelector = (MediaCodecSelector) Assertions.checkNotNull(mediaCodecSelector2);
        this.drmSessionManager = drmSessionManager2;
        this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys2;
        this.enableDecoderFallback = enableDecoderFallback2;
        this.assumedMinimumCodecOperatingRate = assumedMinimumCodecOperatingRate2;
    }

    private static MediaCodec.CryptoInfo getFrameworkCryptoInfo(DecoderInputBuffer buffer2, int adaptiveReconfigurationBytes) {
        MediaCodec.CryptoInfo cryptoInfo = buffer2.cryptoInfo.getFrameworkCryptoInfo();
        if (adaptiveReconfigurationBytes == 0) {
            return cryptoInfo;
        }
        if (cryptoInfo.numBytesOfClearData == null) {
            cryptoInfo.numBytesOfClearData = new int[1];
        }
        int[] iArr = cryptoInfo.numBytesOfClearData;
        iArr[0] = iArr[0] + adaptiveReconfigurationBytes;
        return cryptoInfo;
    }

    private static boolean codecNeedsFlushWorkaround(String name) {
        return Util.SDK_INT < 18 || (Util.SDK_INT == 18 && ("OMX.SEC.avc.dec".equals(name) || "OMX.SEC.avc.dec.secure".equals(name))) || (Util.SDK_INT == 19 && Util.MODEL.startsWith("SM-G800") && ("OMX.Exynos.avc.dec".equals(name) || "OMX.Exynos.avc.dec.secure".equals(name)));
    }

    private static boolean codecNeedsReconfigureWorkaround(String name) {
        return Util.MODEL.startsWith("SM-T230") && "OMX.MARVELL.VIDEO.HW.CODA7542DECODER".equals(name);
    }

    private static boolean codecNeedsDiscardToSpsWorkaround(String name, Format format) {
        return Util.SDK_INT < 21 && format.initializationData.isEmpty() && "OMX.MTK.VIDEO.DECODER.AVC".equals(name);
    }

    private static boolean codecNeedsEosPropagationWorkaround(MediaCodecInfo codecInfo2) {
        String name = codecInfo2.name;
        return (Util.SDK_INT <= 17 && ("OMX.rk.video_decoder.avc".equals(name) || "OMX.allwinner.video.decoder.avc".equals(name))) || ("Amazon".equals(Util.MANUFACTURER) && "AFTS".equals(Util.MODEL) && codecInfo2.secure);
    }

    private static boolean codecNeedsEosFlushWorkaround(String name) {
        return (Util.SDK_INT <= 23 && "OMX.google.vorbis.decoder".equals(name)) || (Util.SDK_INT <= 19 && (("hb2000".equals(Util.DEVICE) || "stvm8".equals(Util.DEVICE)) && ("OMX.amlogic.avc.decoder.awesome".equals(name) || "OMX.amlogic.avc.decoder.awesome.secure".equals(name))));
    }

    private static boolean codecNeedsEosOutputExceptionWorkaround(String name) {
        return Util.SDK_INT == 21 && "OMX.google.aac.decoder".equals(name);
    }

    private static boolean codecNeedsMonoChannelCountWorkaround(String name, Format format) {
        if (Util.SDK_INT > 18 || format.channelCount != 1 || !"OMX.MTK.AUDIO.DECODER.MP3".equals(name)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public abstract void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto2, float f) throws MediaCodecUtil.DecoderQueryException;

    /* access modifiers changed from: protected */
    public abstract List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector2, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException;

    /* access modifiers changed from: protected */
    public abstract boolean processOutputBuffer(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, int i, int i2, long j3, boolean z, Format format) throws ExoPlaybackException;

    /* access modifiers changed from: protected */
    public abstract int supportsFormat(MediaCodecSelector mediaCodecSelector2, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager2, Format format) throws MediaCodecUtil.DecoderQueryException;

    public void experimental_setRenderTimeLimitMs(long renderTimeLimitMs2) {
        this.renderTimeLimitMs = renderTimeLimitMs2;
    }

    public final int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    public final int supportsFormat(Format format) throws ExoPlaybackException {
        try {
            return supportsFormat(this.mediaCodecSelector, this.drmSessionManager, format);
        } catch (MediaCodecUtil.DecoderQueryException e) {
            throw ExoPlaybackException.createForRenderer(e, getIndex());
        }
    }

    /* access modifiers changed from: protected */
    public final void maybeInitCodec() throws ExoPlaybackException {
        if (this.codec == null && this.inputFormat != null) {
            setCodecDrmSession(this.sourceDrmSession);
            String mimeType = this.inputFormat.sampleMimeType;
            DrmSession<FrameworkMediaCrypto> drmSession = this.codecDrmSession;
            if (drmSession != null) {
                if (this.mediaCrypto == null) {
                    FrameworkMediaCrypto sessionMediaCrypto = drmSession.getMediaCrypto();
                    if (sessionMediaCrypto != null) {
                        try {
                            this.mediaCrypto = new MediaCrypto(sessionMediaCrypto.uuid, sessionMediaCrypto.sessionId);
                            this.mediaCryptoRequiresSecureDecoder = !sessionMediaCrypto.forceAllowInsecureDecoderComponents && this.mediaCrypto.requiresSecureDecoderComponent(mimeType);
                        } catch (MediaCryptoException e) {
                            throw ExoPlaybackException.createForRenderer(e, getIndex());
                        }
                    } else if (this.codecDrmSession.getError() == null) {
                        return;
                    }
                }
                if (deviceNeedsDrmKeysToConfigureCodecWorkaround()) {
                    int drmSessionState = this.codecDrmSession.getState();
                    if (drmSessionState == 1) {
                        throw ExoPlaybackException.createForRenderer(this.codecDrmSession.getError(), getIndex());
                    } else if (drmSessionState != 4) {
                        return;
                    }
                }
            }
            try {
                maybeInitCodecWithFallback(this.mediaCrypto, this.mediaCryptoRequiresSecureDecoder);
            } catch (DecoderInitializationException e2) {
                throw ExoPlaybackException.createForRenderer(e2, getIndex());
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldInitCodec(MediaCodecInfo codecInfo2) {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean getCodecNeedsEosPropagation() {
        return false;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final Format updateOutputFormatForTime(long presentationTimeUs) {
        Format format = this.formatQueue.pollFloor(presentationTimeUs);
        if (format != null) {
            this.outputFormat = format;
        }
        return format;
    }

    /* access modifiers changed from: protected */
    public final MediaCodec getCodec() {
        return this.codec;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final MediaCodecInfo getCodecInfo() {
        return this.codecInfo;
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean joining) throws ExoPlaybackException {
        this.decoderCounters = new DecoderCounters();
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        flushOrReinitializeCodec();
        this.formatQueue.clear();
    }

    public final void setOperatingRate(float operatingRate) throws ExoPlaybackException {
        this.rendererOperatingRate = operatingRate;
        if (this.codec != null && this.codecDrainAction != 3 && getState() != 0) {
            updateCodecOperatingRate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        this.inputFormat = null;
        if (this.sourceDrmSession == null && this.codecDrmSession == null) {
            flushOrReleaseCodec();
        } else {
            onReset();
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        try {
            releaseCodec();
        } finally {
            setSourceDrmSession(null);
        }
    }

    /* access modifiers changed from: protected */
    public void releaseCodec() {
        this.availableCodecInfos = null;
        this.codecInfo = null;
        this.codecFormat = null;
        resetInputBuffer();
        resetOutputBuffer();
        resetCodecBuffers();
        this.waitingForKeys = false;
        this.codecHotswapDeadlineMs = C0841C.TIME_UNSET;
        this.decodeOnlyPresentationTimestamps.clear();
        try {
            if (this.codec != null) {
                this.decoderCounters.decoderReleaseCount++;
                this.codec.stop();
                this.codec.release();
            }
            this.codec = null;
            try {
                if (this.mediaCrypto != null) {
                    this.mediaCrypto.release();
                }
            } finally {
                this.mediaCrypto = null;
                this.mediaCryptoRequiresSecureDecoder = false;
                setCodecDrmSession(null);
            }
        } catch (Throwable th) {
            this.codec = null;
            try {
                if (this.mediaCrypto != null) {
                    this.mediaCrypto.release();
                }
                throw th;
            } finally {
                this.mediaCrypto = null;
                this.mediaCryptoRequiresSecureDecoder = false;
                setCodecDrmSession(null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStarted() {
    }

    /* access modifiers changed from: protected */
    public void onStopped() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0031  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void render(long r4, long r6) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r3 = this;
            boolean r0 = r3.outputStreamEnded
            if (r0 == 0) goto L_0x0008
            r3.renderToEndOfStream()
            return
        L_0x0008:
            com.google.android.exoplayer2.Format r0 = r3.inputFormat
            if (r0 != 0) goto L_0x0014
            r0 = 1
            boolean r0 = r3.readToFlagsOnlyBuffer(r0)
            if (r0 != 0) goto L_0x0014
            return
        L_0x0014:
            r3.maybeInitCodec()
            android.media.MediaCodec r0 = r3.codec
            if (r0 == 0) goto L_0x003c
            long r0 = android.os.SystemClock.elapsedRealtime()
            java.lang.String r2 = "drainAndFeed"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r2)
        L_0x0024:
            boolean r2 = r3.drainOutputBuffer(r4, r6)
            if (r2 == 0) goto L_0x002b
            goto L_0x0024
        L_0x002b:
            boolean r2 = r3.feedInputBuffer()
            if (r2 == 0) goto L_0x0038
            boolean r2 = r3.shouldContinueFeeding(r0)
            if (r2 == 0) goto L_0x0038
            goto L_0x002b
        L_0x0038:
            com.google.android.exoplayer2.util.TraceUtil.endSection()
            goto L_0x004b
        L_0x003c:
            com.google.android.exoplayer2.decoder.DecoderCounters r0 = r3.decoderCounters
            int r1 = r0.skippedInputBufferCount
            int r2 = r3.skipSource(r4)
            int r1 = r1 + r2
            r0.skippedInputBufferCount = r1
            r0 = 0
            r3.readToFlagsOnlyBuffer(r0)
        L_0x004b:
            com.google.android.exoplayer2.decoder.DecoderCounters r0 = r3.decoderCounters
            r0.ensureUpdated()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.render(long, long):void");
    }

    /* access modifiers changed from: protected */
    public final boolean flushOrReinitializeCodec() throws ExoPlaybackException {
        boolean released = flushOrReleaseCodec();
        if (released) {
            maybeInitCodec();
        }
        return released;
    }

    /* access modifiers changed from: protected */
    public boolean flushOrReleaseCodec() {
        if (this.codec == null) {
            return false;
        }
        if (this.codecDrainAction == 3 || this.codecNeedsFlushWorkaround || (this.codecNeedsEosFlushWorkaround && this.codecReceivedEos)) {
            releaseCodec();
            return true;
        }
        this.codec.flush();
        resetInputBuffer();
        resetOutputBuffer();
        this.codecHotswapDeadlineMs = C0841C.TIME_UNSET;
        this.codecReceivedEos = false;
        this.codecReceivedBuffers = false;
        this.waitingForFirstSyncSample = true;
        this.codecNeedsAdaptationWorkaroundBuffer = false;
        this.shouldSkipAdaptationWorkaroundOutputBuffer = false;
        this.shouldSkipOutputBuffer = false;
        this.waitingForKeys = false;
        this.decodeOnlyPresentationTimestamps.clear();
        this.codecDrainState = 0;
        this.codecDrainAction = 0;
        this.codecReconfigurationState = this.codecReconfigured ? 1 : 0;
        return false;
    }

    private boolean readToFlagsOnlyBuffer(boolean requireFormat) throws ExoPlaybackException {
        this.flagsOnlyBuffer.clear();
        int result = readSource(this.formatHolder, this.flagsOnlyBuffer, requireFormat);
        if (result == -5) {
            onInputFormatChanged(this.formatHolder);
            return true;
        } else if (result != -4 || !this.flagsOnlyBuffer.isEndOfStream()) {
            return false;
        } else {
            this.inputStreamEnded = true;
            processEndOfStream();
            return false;
        }
    }

    private void maybeInitCodecWithFallback(MediaCrypto crypto, boolean mediaCryptoRequiresSecureDecoder2) throws DecoderInitializationException {
        if (this.availableCodecInfos == null) {
            try {
                List<MediaCodecInfo> allAvailableCodecInfos = getAvailableCodecInfos(mediaCryptoRequiresSecureDecoder2);
                if (this.enableDecoderFallback) {
                    this.availableCodecInfos = new ArrayDeque<>(allAvailableCodecInfos);
                } else {
                    this.availableCodecInfos = new ArrayDeque<>(Collections.singletonList(allAvailableCodecInfos.get(0)));
                }
                this.preferredDecoderInitializationException = null;
            } catch (MediaCodecUtil.DecoderQueryException e) {
                throw new DecoderInitializationException(this.inputFormat, e, mediaCryptoRequiresSecureDecoder2, -49998);
            }
        }
        if (!this.availableCodecInfos.isEmpty()) {
            while (this.codec == null) {
                MediaCodecInfo codecInfo2 = this.availableCodecInfos.peekFirst();
                if (shouldInitCodec(codecInfo2)) {
                    try {
                        initCodec(codecInfo2, crypto);
                    } catch (Exception e2) {
                        String valueOf = String.valueOf(codecInfo2);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30);
                        sb.append("Failed to initialize decoder: ");
                        sb.append(valueOf);
                        Log.m31w(TAG, sb.toString(), e2);
                        this.availableCodecInfos.removeFirst();
                        DecoderInitializationException exception = new DecoderInitializationException(this.inputFormat, e2, mediaCryptoRequiresSecureDecoder2, codecInfo2.name);
                        DecoderInitializationException decoderInitializationException = this.preferredDecoderInitializationException;
                        if (decoderInitializationException == null) {
                            this.preferredDecoderInitializationException = exception;
                        } else {
                            this.preferredDecoderInitializationException = decoderInitializationException.copyWithFallbackException(exception);
                        }
                        if (this.availableCodecInfos.isEmpty()) {
                            throw this.preferredDecoderInitializationException;
                        }
                    }
                } else {
                    return;
                }
            }
            this.availableCodecInfos = null;
            return;
        }
        throw new DecoderInitializationException(this.inputFormat, (Throwable) null, mediaCryptoRequiresSecureDecoder2, -49999);
    }

    private List<MediaCodecInfo> getAvailableCodecInfos(boolean mediaCryptoRequiresSecureDecoder2) throws MediaCodecUtil.DecoderQueryException {
        List<MediaCodecInfo> codecInfos = getDecoderInfos(this.mediaCodecSelector, this.inputFormat, mediaCryptoRequiresSecureDecoder2);
        if (codecInfos.isEmpty() && mediaCryptoRequiresSecureDecoder2) {
            codecInfos = getDecoderInfos(this.mediaCodecSelector, this.inputFormat, false);
            if (!codecInfos.isEmpty()) {
                String str = this.inputFormat.sampleMimeType;
                String valueOf = String.valueOf(codecInfos);
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 99 + String.valueOf(valueOf).length());
                sb.append("Drm session requires secure decoder for ");
                sb.append(str);
                sb.append(", but no secure decoder available. Trying to proceed with ");
                sb.append(valueOf);
                sb.append(".");
                Log.m30w(TAG, sb.toString());
            }
        }
        return codecInfos;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initCodec(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r19, android.media.MediaCrypto r20) throws java.lang.Exception {
        /*
            r18 = this;
            r7 = r18
            r8 = r19
            r1 = 0
            java.lang.String r9 = r8.name
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r2 = 23
            if (r0 >= r2) goto L_0x0010
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            goto L_0x001c
        L_0x0010:
            float r0 = r7.rendererOperatingRate
            com.google.android.exoplayer2.Format r2 = r7.inputFormat
            com.google.android.exoplayer2.Format[] r3 = r18.getStreamFormats()
            float r0 = r7.getCodecOperatingRateV23(r0, r2, r3)
        L_0x001c:
            float r2 = r7.assumedMinimumCodecOperatingRate
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 > 0) goto L_0x0027
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r10 = r0
            goto L_0x0028
        L_0x0027:
            r10 = r0
        L_0x0028:
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x010c }
            r11 = r2
            java.lang.String r0 = "createCodec:"
            java.lang.String r2 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x010c }
            int r3 = r2.length()     // Catch:{ Exception -> 0x010c }
            if (r3 == 0) goto L_0x003e
            java.lang.String r0 = r0.concat(r2)     // Catch:{ Exception -> 0x010c }
            goto L_0x0044
        L_0x003e:
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x010c }
            r2.<init>(r0)     // Catch:{ Exception -> 0x010c }
            r0 = r2
        L_0x0044:
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r0)     // Catch:{ Exception -> 0x010c }
            android.media.MediaCodec r0 = android.media.MediaCodec.createByCodecName(r9)     // Catch:{ Exception -> 0x010c }
            r13 = r0
            com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ Exception -> 0x010a }
            java.lang.String r0 = "configureCodec"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r0)     // Catch:{ Exception -> 0x010a }
            com.google.android.exoplayer2.Format r4 = r7.inputFormat     // Catch:{ Exception -> 0x010a }
            r1 = r18
            r2 = r19
            r3 = r13
            r5 = r20
            r6 = r10
            r1.configureCodec(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x010a }
            com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ Exception -> 0x010a }
            java.lang.String r0 = "startCodec"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r0)     // Catch:{ Exception -> 0x010a }
            r13.start()     // Catch:{ Exception -> 0x010a }
            com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ Exception -> 0x010a }
            long r0 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x010a }
            r14 = r0
            r7.getCodecBuffers(r13)     // Catch:{ Exception -> 0x010a }
            r7.codec = r13
            r7.codecInfo = r8
            r7.codecOperatingRate = r10
            com.google.android.exoplayer2.Format r0 = r7.inputFormat
            r7.codecFormat = r0
            int r0 = r7.codecAdaptationWorkaroundMode(r9)
            r7.codecAdaptationWorkaroundMode = r0
            boolean r0 = codecNeedsReconfigureWorkaround(r9)
            r7.codecNeedsReconfigureWorkaround = r0
            com.google.android.exoplayer2.Format r0 = r7.codecFormat
            boolean r0 = codecNeedsDiscardToSpsWorkaround(r9, r0)
            r7.codecNeedsDiscardToSpsWorkaround = r0
            boolean r0 = codecNeedsFlushWorkaround(r9)
            r7.codecNeedsFlushWorkaround = r0
            boolean r0 = codecNeedsEosFlushWorkaround(r9)
            r7.codecNeedsEosFlushWorkaround = r0
            boolean r0 = codecNeedsEosOutputExceptionWorkaround(r9)
            r7.codecNeedsEosOutputExceptionWorkaround = r0
            com.google.android.exoplayer2.Format r0 = r7.codecFormat
            boolean r0 = codecNeedsMonoChannelCountWorkaround(r9, r0)
            r7.codecNeedsMonoChannelCountWorkaround = r0
            boolean r0 = codecNeedsEosPropagationWorkaround(r19)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x00c3
            boolean r0 = r18.getCodecNeedsEosPropagation()
            if (r0 == 0) goto L_0x00c1
            goto L_0x00c3
        L_0x00c1:
            r0 = 0
            goto L_0x00c4
        L_0x00c3:
            r0 = 1
        L_0x00c4:
            r7.codecNeedsEosPropagation = r0
            r18.resetInputBuffer()
            r18.resetOutputBuffer()
            int r0 = r18.getState()
            r3 = 2
            if (r0 != r3) goto L_0x00dc
            long r3 = android.os.SystemClock.elapsedRealtime()
            r5 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 + r5
            goto L_0x00e1
        L_0x00dc:
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x00e1:
            r7.codecHotswapDeadlineMs = r3
            r7.codecReconfigured = r2
            r7.codecReconfigurationState = r2
            r7.codecReceivedEos = r2
            r7.codecReceivedBuffers = r2
            r7.codecDrainState = r2
            r7.codecDrainAction = r2
            r7.codecNeedsAdaptationWorkaroundBuffer = r2
            r7.shouldSkipAdaptationWorkaroundOutputBuffer = r2
            r7.shouldSkipOutputBuffer = r2
            r7.waitingForFirstSyncSample = r1
            com.google.android.exoplayer2.decoder.DecoderCounters r0 = r7.decoderCounters
            int r2 = r0.decoderInitCount
            int r2 = r2 + r1
            r0.decoderInitCount = r2
            long r16 = r14 - r11
            r1 = r18
            r2 = r9
            r3 = r14
            r5 = r16
            r1.onCodecInitialized(r2, r3, r5)
            return
        L_0x010a:
            r0 = move-exception
            goto L_0x010e
        L_0x010c:
            r0 = move-exception
            r13 = r1
        L_0x010e:
            if (r13 == 0) goto L_0x0116
            r18.resetCodecBuffers()
            r13.release()
        L_0x0116:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.initCodec(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, android.media.MediaCrypto):void");
    }

    private boolean shouldContinueFeeding(long drainStartTimeMs) {
        return this.renderTimeLimitMs == C0841C.TIME_UNSET || SystemClock.elapsedRealtime() - drainStartTimeMs < this.renderTimeLimitMs;
    }

    private void getCodecBuffers(MediaCodec codec2) {
        if (Util.SDK_INT < 21) {
            this.inputBuffers = codec2.getInputBuffers();
            this.outputBuffers = codec2.getOutputBuffers();
        }
    }

    private void resetCodecBuffers() {
        if (Util.SDK_INT < 21) {
            this.inputBuffers = null;
            this.outputBuffers = null;
        }
    }

    private ByteBuffer getInputBuffer(int inputIndex2) {
        if (Util.SDK_INT >= 21) {
            return this.codec.getInputBuffer(inputIndex2);
        }
        return this.inputBuffers[inputIndex2];
    }

    private ByteBuffer getOutputBuffer(int outputIndex2) {
        if (Util.SDK_INT >= 21) {
            return this.codec.getOutputBuffer(outputIndex2);
        }
        return this.outputBuffers[outputIndex2];
    }

    private boolean hasOutputBuffer() {
        return this.outputIndex >= 0;
    }

    private void resetInputBuffer() {
        this.inputIndex = -1;
        this.buffer.data = null;
    }

    private void resetOutputBuffer() {
        this.outputIndex = -1;
        this.outputBuffer = null;
    }

    private void setSourceDrmSession(@Nullable DrmSession<FrameworkMediaCrypto> session) {
        DrmSession<FrameworkMediaCrypto> previous = this.sourceDrmSession;
        this.sourceDrmSession = session;
        releaseDrmSessionIfUnused(previous);
    }

    private void setCodecDrmSession(@Nullable DrmSession<FrameworkMediaCrypto> session) {
        DrmSession<FrameworkMediaCrypto> previous = this.codecDrmSession;
        this.codecDrmSession = session;
        releaseDrmSessionIfUnused(previous);
    }

    private void releaseDrmSessionIfUnused(@Nullable DrmSession<FrameworkMediaCrypto> session) {
        if (session != null && session != this.sourceDrmSession && session != this.codecDrmSession) {
            this.drmSessionManager.releaseSession(session);
        }
    }

    private boolean feedInputBuffer() throws ExoPlaybackException {
        int result;
        MediaCodec mediaCodec = this.codec;
        if (mediaCodec == null || this.codecDrainState == 2 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputIndex < 0) {
            this.inputIndex = mediaCodec.dequeueInputBuffer(0);
            int i = this.inputIndex;
            if (i < 0) {
                return false;
            }
            this.buffer.data = getInputBuffer(i);
            this.buffer.clear();
        }
        if (this.codecDrainState == 1) {
            if (!this.codecNeedsEosPropagation) {
                this.codecReceivedEos = true;
                this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0, 4);
                resetInputBuffer();
            }
            this.codecDrainState = 2;
            return false;
        } else if (this.codecNeedsAdaptationWorkaroundBuffer) {
            this.codecNeedsAdaptationWorkaroundBuffer = false;
            this.buffer.data.put(ADAPTATION_WORKAROUND_BUFFER);
            this.codec.queueInputBuffer(this.inputIndex, 0, ADAPTATION_WORKAROUND_BUFFER.length, 0, 0);
            resetInputBuffer();
            this.codecReceivedBuffers = true;
            return true;
        } else {
            int adaptiveReconfigurationBytes = 0;
            if (this.waitingForKeys) {
                result = -4;
            } else {
                if (this.codecReconfigurationState == 1) {
                    for (int i2 = 0; i2 < this.codecFormat.initializationData.size(); i2++) {
                        this.buffer.data.put(this.codecFormat.initializationData.get(i2));
                    }
                    this.codecReconfigurationState = 2;
                }
                adaptiveReconfigurationBytes = this.buffer.data.position();
                result = readSource(this.formatHolder, this.buffer, false);
            }
            if (result == -3) {
                return false;
            }
            if (result == -5) {
                if (this.codecReconfigurationState == 2) {
                    this.buffer.clear();
                    this.codecReconfigurationState = 1;
                }
                onInputFormatChanged(this.formatHolder);
                return true;
            } else if (this.buffer.isEndOfStream()) {
                if (this.codecReconfigurationState == 2) {
                    this.buffer.clear();
                    this.codecReconfigurationState = 1;
                }
                this.inputStreamEnded = true;
                if (!this.codecReceivedBuffers) {
                    processEndOfStream();
                    return false;
                }
                try {
                    if (!this.codecNeedsEosPropagation) {
                        this.codecReceivedEos = true;
                        this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0, 4);
                        resetInputBuffer();
                    }
                    return false;
                } catch (MediaCodec.CryptoException e) {
                    throw ExoPlaybackException.createForRenderer(e, getIndex());
                }
            } else if (!this.waitingForFirstSyncSample || this.buffer.isKeyFrame()) {
                this.waitingForFirstSyncSample = false;
                boolean bufferEncrypted = this.buffer.isEncrypted();
                this.waitingForKeys = shouldWaitForKeys(bufferEncrypted);
                if (this.waitingForKeys) {
                    return false;
                }
                if (this.codecNeedsDiscardToSpsWorkaround && !bufferEncrypted) {
                    NalUnitUtil.discardToSps(this.buffer.data);
                    if (this.buffer.data.position() == 0) {
                        return true;
                    }
                    this.codecNeedsDiscardToSpsWorkaround = false;
                }
                try {
                    long presentationTimeUs = this.buffer.timeUs;
                    if (this.buffer.isDecodeOnly()) {
                        this.decodeOnlyPresentationTimestamps.add(Long.valueOf(presentationTimeUs));
                    }
                    if (this.waitingForFirstSampleInFormat) {
                        this.formatQueue.add(presentationTimeUs, this.inputFormat);
                        this.waitingForFirstSampleInFormat = false;
                    }
                    this.buffer.flip();
                    onQueueInputBuffer(this.buffer);
                    if (bufferEncrypted) {
                        this.codec.queueSecureInputBuffer(this.inputIndex, 0, getFrameworkCryptoInfo(this.buffer, adaptiveReconfigurationBytes), presentationTimeUs, 0);
                    } else {
                        this.codec.queueInputBuffer(this.inputIndex, 0, this.buffer.data.limit(), presentationTimeUs, 0);
                    }
                    resetInputBuffer();
                    this.codecReceivedBuffers = true;
                    this.codecReconfigurationState = 0;
                    this.decoderCounters.inputBufferCount++;
                    return true;
                } catch (MediaCodec.CryptoException e2) {
                    throw ExoPlaybackException.createForRenderer(e2, getIndex());
                }
            } else {
                this.buffer.clear();
                if (this.codecReconfigurationState == 2) {
                    this.codecReconfigurationState = 1;
                }
                return true;
            }
        }
    }

    private boolean shouldWaitForKeys(boolean bufferEncrypted) throws ExoPlaybackException {
        if (this.codecDrmSession == null || (!bufferEncrypted && this.playClearSamplesWithoutKeys)) {
            return false;
        }
        int drmSessionState = this.codecDrmSession.getState();
        if (drmSessionState == 1) {
            throw ExoPlaybackException.createForRenderer(this.codecDrmSession.getError(), getIndex());
        } else if (drmSessionState != 4) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
    }

    /* access modifiers changed from: protected */
    public void onInputFormatChanged(FormatHolder formatHolder2) throws ExoPlaybackException {
        Format oldFormat = this.inputFormat;
        Format newFormat = formatHolder2.format;
        this.inputFormat = newFormat;
        boolean z = true;
        this.waitingForFirstSampleInFormat = true;
        if (!Util.areEqual(newFormat.drmInitData, oldFormat == null ? null : oldFormat.drmInitData)) {
            if (newFormat.drmInitData != null) {
                DrmSessionManager<FrameworkMediaCrypto> drmSessionManager2 = this.drmSessionManager;
                if (drmSessionManager2 != null) {
                    DrmSession<FrameworkMediaCrypto> session = drmSessionManager2.acquireSession(Looper.myLooper(), newFormat.drmInitData);
                    if (session == this.sourceDrmSession || session == this.codecDrmSession) {
                        this.drmSessionManager.releaseSession(session);
                    }
                    setSourceDrmSession(session);
                } else {
                    throw ExoPlaybackException.createForRenderer(new IllegalStateException("Media requires a DrmSessionManager"), getIndex());
                }
            } else {
                setSourceDrmSession(null);
            }
        }
        if (this.codec == null) {
            maybeInitCodec();
        } else if ((this.sourceDrmSession != null || this.codecDrmSession == null) && ((this.sourceDrmSession == null || this.codecDrmSession != null) && ((this.sourceDrmSession == null || this.codecInfo.secure) && (Util.SDK_INT >= 23 || this.sourceDrmSession == this.codecDrmSession)))) {
            int canKeepCodec = canKeepCodec(this.codec, this.codecInfo, this.codecFormat, newFormat);
            if (canKeepCodec == 0) {
                drainAndReinitializeCodec();
            } else if (canKeepCodec == 1) {
                this.codecFormat = newFormat;
                updateCodecOperatingRate();
                if (this.sourceDrmSession != this.codecDrmSession) {
                    drainAndUpdateCodecDrmSession();
                } else {
                    drainAndFlushCodec();
                }
            } else if (canKeepCodec != 2) {
                if (canKeepCodec == 3) {
                    this.codecFormat = newFormat;
                    updateCodecOperatingRate();
                    if (this.sourceDrmSession != this.codecDrmSession) {
                        drainAndUpdateCodecDrmSession();
                        return;
                    }
                    return;
                }
                throw new IllegalStateException();
            } else if (this.codecNeedsReconfigureWorkaround) {
                drainAndReinitializeCodec();
            } else {
                this.codecReconfigured = true;
                this.codecReconfigurationState = 1;
                int i = this.codecAdaptationWorkaroundMode;
                if (!(i == 2 || (i == 1 && newFormat.width == this.codecFormat.width && newFormat.height == this.codecFormat.height))) {
                    z = false;
                }
                this.codecNeedsAdaptationWorkaroundBuffer = z;
                this.codecFormat = newFormat;
                updateCodecOperatingRate();
                if (this.sourceDrmSession != this.codecDrmSession) {
                    drainAndUpdateCodecDrmSession();
                }
            }
        } else {
            drainAndReinitializeCodec();
        }
    }

    /* access modifiers changed from: protected */
    public void onOutputFormatChanged(MediaCodec codec2, MediaFormat outputFormat2) throws ExoPlaybackException {
    }

    /* access modifiers changed from: protected */
    public void onQueueInputBuffer(DecoderInputBuffer buffer2) {
    }

    /* access modifiers changed from: protected */
    public void onProcessedOutputBuffer(long presentationTimeUs) {
    }

    /* access modifiers changed from: protected */
    public int canKeepCodec(MediaCodec codec2, MediaCodecInfo codecInfo2, Format oldFormat, Format newFormat) {
        return 0;
    }

    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    public boolean isReady() {
        return this.inputFormat != null && !this.waitingForKeys && (isSourceReady() || hasOutputBuffer() || (this.codecHotswapDeadlineMs != C0841C.TIME_UNSET && SystemClock.elapsedRealtime() < this.codecHotswapDeadlineMs));
    }

    /* access modifiers changed from: protected */
    public long getDequeueOutputBufferTimeoutUs() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public float getCodecOperatingRateV23(float operatingRate, Format format, Format[] streamFormats) {
        return -1.0f;
    }

    private void updateCodecOperatingRate() throws ExoPlaybackException {
        if (Util.SDK_INT >= 23) {
            float newCodecOperatingRate = getCodecOperatingRateV23(this.rendererOperatingRate, this.codecFormat, getStreamFormats());
            float f = this.codecOperatingRate;
            if (f != newCodecOperatingRate) {
                if (newCodecOperatingRate == -1.0f) {
                    drainAndReinitializeCodec();
                } else if (f != -1.0f || newCodecOperatingRate > this.assumedMinimumCodecOperatingRate) {
                    Bundle codecParameters = new Bundle();
                    codecParameters.putFloat("operating-rate", newCodecOperatingRate);
                    this.codec.setParameters(codecParameters);
                    this.codecOperatingRate = newCodecOperatingRate;
                }
            }
        }
    }

    private void drainAndFlushCodec() {
        if (this.codecReceivedBuffers) {
            this.codecDrainState = 1;
            this.codecDrainAction = 1;
        }
    }

    private void drainAndUpdateCodecDrmSession() throws ExoPlaybackException {
        if (Util.SDK_INT < 23) {
            drainAndReinitializeCodec();
        } else if (this.codecReceivedBuffers) {
            this.codecDrainState = 1;
            this.codecDrainAction = 2;
        } else {
            updateDrmSessionOrReinitializeCodecV23();
        }
    }

    private void drainAndReinitializeCodec() throws ExoPlaybackException {
        if (this.codecReceivedBuffers) {
            this.codecDrainState = 1;
            this.codecDrainAction = 3;
            return;
        }
        reinitializeCodec();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void}
     arg types: [int, int]
     candidates:
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, long):void}
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00dc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean drainOutputBuffer(long r18, long r20) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r17 = this;
            r14 = r17
            boolean r0 = r17.hasOutputBuffer()
            r15 = 1
            r13 = 0
            if (r0 != 0) goto L_0x00a8
            boolean r0 = r14.codecNeedsEosOutputExceptionWorkaround
            if (r0 == 0) goto L_0x002b
            boolean r0 = r14.codecReceivedEos
            if (r0 == 0) goto L_0x002b
            android.media.MediaCodec r0 = r14.codec     // Catch:{ IllegalStateException -> 0x001f }
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo     // Catch:{ IllegalStateException -> 0x001f }
            long r2 = r17.getDequeueOutputBufferTimeoutUs()     // Catch:{ IllegalStateException -> 0x001f }
            int r0 = r0.dequeueOutputBuffer(r1, r2)     // Catch:{ IllegalStateException -> 0x001f }
            goto L_0x0037
        L_0x001f:
            r0 = move-exception
            r17.processEndOfStream()
            boolean r1 = r14.outputStreamEnded
            if (r1 == 0) goto L_0x002a
            r17.releaseCodec()
        L_0x002a:
            return r13
        L_0x002b:
            android.media.MediaCodec r0 = r14.codec
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            long r2 = r17.getDequeueOutputBufferTimeoutUs()
            int r0 = r0.dequeueOutputBuffer(r1, r2)
        L_0x0037:
            if (r0 >= 0) goto L_0x0058
            r1 = -2
            if (r0 != r1) goto L_0x0040
            r17.processOutputFormat()
            return r15
        L_0x0040:
            r1 = -3
            if (r0 != r1) goto L_0x0047
            r17.processOutputBuffersChanged()
            return r15
        L_0x0047:
            boolean r1 = r14.codecNeedsEosPropagation
            if (r1 == 0) goto L_0x0057
            boolean r1 = r14.inputStreamEnded
            if (r1 != 0) goto L_0x0054
            int r1 = r14.codecDrainState
            r2 = 2
            if (r1 != r2) goto L_0x0057
        L_0x0054:
            r17.processEndOfStream()
        L_0x0057:
            return r13
        L_0x0058:
            boolean r1 = r14.shouldSkipAdaptationWorkaroundOutputBuffer
            if (r1 == 0) goto L_0x0064
            r14.shouldSkipAdaptationWorkaroundOutputBuffer = r13
            android.media.MediaCodec r1 = r14.codec
            r1.releaseOutputBuffer(r0, r13)
            return r15
        L_0x0064:
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            int r1 = r1.size
            if (r1 != 0) goto L_0x0076
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            int r1 = r1.flags
            r1 = r1 & 4
            if (r1 == 0) goto L_0x0076
            r17.processEndOfStream()
            return r13
        L_0x0076:
            r14.outputIndex = r0
            java.nio.ByteBuffer r1 = r14.getOutputBuffer(r0)
            r14.outputBuffer = r1
            java.nio.ByteBuffer r1 = r14.outputBuffer
            if (r1 == 0) goto L_0x0097
            android.media.MediaCodec$BufferInfo r2 = r14.outputBufferInfo
            int r2 = r2.offset
            r1.position(r2)
            java.nio.ByteBuffer r1 = r14.outputBuffer
            android.media.MediaCodec$BufferInfo r2 = r14.outputBufferInfo
            int r2 = r2.offset
            android.media.MediaCodec$BufferInfo r3 = r14.outputBufferInfo
            int r3 = r3.size
            int r2 = r2 + r3
            r1.limit(r2)
        L_0x0097:
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            long r1 = r1.presentationTimeUs
            boolean r1 = r14.shouldSkipOutputBuffer(r1)
            r14.shouldSkipOutputBuffer = r1
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            long r1 = r1.presentationTimeUs
            r14.updateOutputFormatForTime(r1)
        L_0x00a8:
            boolean r0 = r14.codecNeedsEosOutputExceptionWorkaround
            if (r0 == 0) goto L_0x00e0
            boolean r0 = r14.codecReceivedEos
            if (r0 == 0) goto L_0x00e0
            android.media.MediaCodec r6 = r14.codec     // Catch:{ IllegalStateException -> 0x00d2 }
            java.nio.ByteBuffer r7 = r14.outputBuffer     // Catch:{ IllegalStateException -> 0x00d2 }
            int r8 = r14.outputIndex     // Catch:{ IllegalStateException -> 0x00d2 }
            android.media.MediaCodec$BufferInfo r0 = r14.outputBufferInfo     // Catch:{ IllegalStateException -> 0x00d2 }
            int r9 = r0.flags     // Catch:{ IllegalStateException -> 0x00d2 }
            android.media.MediaCodec$BufferInfo r0 = r14.outputBufferInfo     // Catch:{ IllegalStateException -> 0x00d2 }
            long r10 = r0.presentationTimeUs     // Catch:{ IllegalStateException -> 0x00d2 }
            boolean r12 = r14.shouldSkipOutputBuffer     // Catch:{ IllegalStateException -> 0x00d2 }
            com.google.android.exoplayer2.Format r0 = r14.outputFormat     // Catch:{ IllegalStateException -> 0x00d2 }
            r1 = r17
            r2 = r18
            r4 = r20
            r16 = 0
            r13 = r0
            boolean r0 = r1.processOutputBuffer(r2, r4, r6, r7, r8, r9, r10, r12, r13)     // Catch:{ IllegalStateException -> 0x00d0 }
            goto L_0x00fe
        L_0x00d0:
            r0 = move-exception
            goto L_0x00d5
        L_0x00d2:
            r0 = move-exception
            r16 = 0
        L_0x00d5:
            r17.processEndOfStream()
            boolean r1 = r14.outputStreamEnded
            if (r1 == 0) goto L_0x00df
            r17.releaseCodec()
        L_0x00df:
            return r16
        L_0x00e0:
            r16 = 0
            android.media.MediaCodec r6 = r14.codec
            java.nio.ByteBuffer r7 = r14.outputBuffer
            int r8 = r14.outputIndex
            android.media.MediaCodec$BufferInfo r0 = r14.outputBufferInfo
            int r9 = r0.flags
            android.media.MediaCodec$BufferInfo r0 = r14.outputBufferInfo
            long r10 = r0.presentationTimeUs
            boolean r12 = r14.shouldSkipOutputBuffer
            com.google.android.exoplayer2.Format r13 = r14.outputFormat
            r1 = r17
            r2 = r18
            r4 = r20
            boolean r0 = r1.processOutputBuffer(r2, r4, r6, r7, r8, r9, r10, r12, r13)
        L_0x00fe:
            if (r0 == 0) goto L_0x011b
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            long r1 = r1.presentationTimeUs
            r14.onProcessedOutputBuffer(r1)
            android.media.MediaCodec$BufferInfo r1 = r14.outputBufferInfo
            int r1 = r1.flags
            r1 = r1 & 4
            if (r1 == 0) goto L_0x0111
            r1 = 1
            goto L_0x0112
        L_0x0111:
            r1 = 0
        L_0x0112:
            r17.resetOutputBuffer()
            if (r1 != 0) goto L_0x0118
            return r15
        L_0x0118:
            r17.processEndOfStream()
        L_0x011b:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.drainOutputBuffer(long, long):boolean");
    }

    private void processOutputFormat() throws ExoPlaybackException {
        MediaFormat format = this.codec.getOutputFormat();
        if (this.codecAdaptationWorkaroundMode != 0 && format.getInteger("width") == 32 && format.getInteger("height") == 32) {
            this.shouldSkipAdaptationWorkaroundOutputBuffer = true;
            return;
        }
        if (this.codecNeedsMonoChannelCountWorkaround) {
            format.setInteger("channel-count", 1);
        }
        onOutputFormatChanged(this.codec, format);
    }

    private void processOutputBuffersChanged() {
        if (Util.SDK_INT < 21) {
            this.outputBuffers = this.codec.getOutputBuffers();
        }
    }

    /* access modifiers changed from: protected */
    public void renderToEndOfStream() throws ExoPlaybackException {
    }

    private void processEndOfStream() throws ExoPlaybackException {
        int i = this.codecDrainAction;
        if (i == 1) {
            flushOrReinitializeCodec();
        } else if (i == 2) {
            updateDrmSessionOrReinitializeCodecV23();
        } else if (i != 3) {
            this.outputStreamEnded = true;
            renderToEndOfStream();
        } else {
            reinitializeCodec();
        }
    }

    private void reinitializeCodec() throws ExoPlaybackException {
        releaseCodec();
        maybeInitCodec();
    }

    @TargetApi(23)
    private void updateDrmSessionOrReinitializeCodecV23() throws ExoPlaybackException {
        FrameworkMediaCrypto sessionMediaCrypto = this.sourceDrmSession.getMediaCrypto();
        if (sessionMediaCrypto == null) {
            reinitializeCodec();
        } else if (C0841C.PLAYREADY_UUID.equals(sessionMediaCrypto.uuid)) {
            reinitializeCodec();
        } else if (!flushOrReinitializeCodec()) {
            try {
                this.mediaCrypto.setMediaDrmSession(sessionMediaCrypto.sessionId);
                setCodecDrmSession(this.sourceDrmSession);
                this.codecDrainState = 0;
                this.codecDrainAction = 0;
            } catch (MediaCryptoException e) {
                throw ExoPlaybackException.createForRenderer(e, getIndex());
            }
        }
    }

    private boolean shouldSkipOutputBuffer(long presentationTimeUs) {
        int size = this.decodeOnlyPresentationTimestamps.size();
        for (int i = 0; i < size; i++) {
            if (this.decodeOnlyPresentationTimestamps.get(i).longValue() == presentationTimeUs) {
                this.decodeOnlyPresentationTimestamps.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean deviceNeedsDrmKeysToConfigureCodecWorkaround() {
        return "Amazon".equals(Util.MANUFACTURER) && ("AFTM".equals(Util.MODEL) || "AFTB".equals(Util.MODEL));
    }

    private int codecAdaptationWorkaroundMode(String name) {
        if (Util.SDK_INT <= 25 && "OMX.Exynos.avc.dec.secure".equals(name) && (Util.MODEL.startsWith("SM-T585") || Util.MODEL.startsWith("SM-A510") || Util.MODEL.startsWith("SM-A520") || Util.MODEL.startsWith("SM-J700"))) {
            return 2;
        }
        if (Util.SDK_INT >= 24) {
            return 0;
        }
        if (!"OMX.Nvidia.h264.decode".equals(name) && !"OMX.Nvidia.h264.decode.secure".equals(name)) {
            return 0;
        }
        if ("flounder".equals(Util.DEVICE) || "flounder_lte".equals(Util.DEVICE) || "grouper".equals(Util.DEVICE) || "tilapia".equals(Util.DEVICE)) {
            return 1;
        }
        return 0;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface AdaptationWorkaroundMode {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface DrainAction {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface DrainState {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    protected @interface KeepCodecResult {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface ReconfigurationState {
    }

    public static class DecoderInitializationException extends Exception {
        private static final int CUSTOM_ERROR_CODE_BASE = -50000;
        private static final int DECODER_QUERY_ERROR = -49998;
        private static final int NO_SUITABLE_DECODER_ERROR = -49999;
        public final String decoderName;
        public final String diagnosticInfo;
        @Nullable
        public final DecoderInitializationException fallbackDecoderInitializationException;
        public final String mimeType;
        public final boolean secureDecoderRequired;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public DecoderInitializationException(com.google.android.exoplayer2.Format r12, java.lang.Throwable r13, boolean r14, int r15) {
            /*
                r11 = this;
                java.lang.String r0 = java.lang.String.valueOf(r12)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 36
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Decoder init failed: ["
                r2.append(r1)
                r2.append(r15)
                java.lang.String r1 = "], "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r4 = r2.toString()
                java.lang.String r6 = r12.sampleMimeType
                java.lang.String r9 = buildCustomDiagnosticInfo(r15)
                r8 = 0
                r10 = 0
                r3 = r11
                r5 = r13
                r7 = r14
                r3.<init>(r4, r5, r6, r7, r8, r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException.<init>(com.google.android.exoplayer2.Format, java.lang.Throwable, boolean, int):void");
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public DecoderInitializationException(com.google.android.exoplayer2.Format r12, java.lang.Throwable r13, boolean r14, java.lang.String r15) {
            /*
                r11 = this;
                java.lang.String r0 = java.lang.String.valueOf(r12)
                java.lang.String r1 = java.lang.String.valueOf(r15)
                int r1 = r1.length()
                int r1 = r1 + 23
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r1 = r1 + r2
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Decoder init failed: "
                r2.append(r1)
                r2.append(r15)
                java.lang.String r1 = ", "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r4 = r2.toString()
                java.lang.String r6 = r12.sampleMimeType
                int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
                r1 = 21
                if (r0 < r1) goto L_0x003d
                java.lang.String r0 = getDiagnosticInfoV21(r13)
                goto L_0x003e
            L_0x003d:
                r0 = 0
            L_0x003e:
                r9 = r0
                r10 = 0
                r3 = r11
                r5 = r13
                r7 = r14
                r8 = r15
                r3.<init>(r4, r5, r6, r7, r8, r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException.<init>(com.google.android.exoplayer2.Format, java.lang.Throwable, boolean, java.lang.String):void");
        }

        private DecoderInitializationException(String message, Throwable cause, String mimeType2, boolean secureDecoderRequired2, @Nullable String decoderName2, @Nullable String diagnosticInfo2, @Nullable DecoderInitializationException fallbackDecoderInitializationException2) {
            super(message, cause);
            this.mimeType = mimeType2;
            this.secureDecoderRequired = secureDecoderRequired2;
            this.decoderName = decoderName2;
            this.diagnosticInfo = diagnosticInfo2;
            this.fallbackDecoderInitializationException = fallbackDecoderInitializationException2;
        }

        @TargetApi(21)
        private static String getDiagnosticInfoV21(Throwable cause) {
            if (cause instanceof MediaCodec.CodecException) {
                return ((MediaCodec.CodecException) cause).getDiagnosticInfo();
            }
            return null;
        }

        private static String buildCustomDiagnosticInfo(int errorCode) {
            String sign = errorCode < 0 ? "neg_" : "";
            int abs = Math.abs(errorCode);
            StringBuilder sb = new StringBuilder(String.valueOf(sign).length() + 64);
            sb.append("com.google.android.exoplayer.MediaCodecTrackRenderer_");
            sb.append(sign);
            sb.append(abs);
            return sb.toString();
        }

        /* access modifiers changed from: private */
        @CheckResult
        public DecoderInitializationException copyWithFallbackException(DecoderInitializationException fallbackException) {
            return new DecoderInitializationException(getMessage(), getCause(), this.mimeType, this.secureDecoderRequired, this.decoderName, this.diagnosticInfo, fallbackException);
        }
    }
}
