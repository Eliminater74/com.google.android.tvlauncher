package com.google.android.exoplayer2.audio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.Surface;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.mediacodec.MediaFormatUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gsf.TalkContract;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public class MediaCodecAudioRenderer extends MediaCodecRenderer implements MediaClock {
    private static final int MAX_PENDING_STREAM_CHANGE_COUNT = 10;
    private static final String TAG = "MediaCodecAudioRenderer";
    private boolean allowFirstBufferPositionDiscontinuity;
    /* access modifiers changed from: private */
    public boolean allowPositionDiscontinuity;
    private final AudioSink audioSink;
    private int channelCount;
    private int codecMaxInputSize;
    private boolean codecNeedsDiscardChannelsWorkaround;
    private boolean codecNeedsEosBufferTimestampWorkaround;
    private final Context context;
    private long currentPositionUs;
    private int encoderDelay;
    private int encoderPadding;
    /* access modifiers changed from: private */
    public final AudioRendererEventListener.EventDispatcher eventDispatcher;
    private long lastInputTimeUs;
    private boolean passthroughEnabled;
    private MediaFormat passthroughMediaFormat;
    private int pcmEncoding;
    private int pendingStreamChangeCount;
    private final long[] pendingStreamChangeTimesUs;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.audio.MediaCodecAudioRenderer.<init>(android.content.Context, com.google.android.exoplayer2.mediacodec.MediaCodecSelector, com.google.android.exoplayer2.drm.DrmSessionManager<com.google.android.exoplayer2.drm.FrameworkMediaCrypto>, boolean):void
     arg types: [android.content.Context, com.google.android.exoplayer2.mediacodec.MediaCodecSelector, ?[OBJECT, ARRAY], int]
     candidates:
      com.google.android.exoplayer2.audio.MediaCodecAudioRenderer.<init>(android.content.Context, com.google.android.exoplayer2.mediacodec.MediaCodecSelector, android.os.Handler, com.google.android.exoplayer2.audio.AudioRendererEventListener):void
      com.google.android.exoplayer2.audio.MediaCodecAudioRenderer.<init>(android.content.Context, com.google.android.exoplayer2.mediacodec.MediaCodecSelector, com.google.android.exoplayer2.drm.DrmSessionManager<com.google.android.exoplayer2.drm.FrameworkMediaCrypto>, boolean):void */
    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector) {
        this(context2, mediaCodecSelector, (DrmSessionManager<FrameworkMediaCrypto>) null, false);
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys) {
        this(context2, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, null, null);
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener) {
        this(context2, mediaCodecSelector, null, false, eventHandler, eventListener);
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener) {
        this(context2, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener, null, new AudioProcessor[0]);
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, @Nullable AudioCapabilities audioCapabilities, AudioProcessor... audioProcessors) {
        this(context2, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, eventHandler, eventListener, new DefaultAudioSink(audioCapabilities, audioProcessors));
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, AudioSink audioSink2) {
        super(1, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys, false, 44100.0f);
        this.context = context2.getApplicationContext();
        this.audioSink = audioSink2;
        this.lastInputTimeUs = C0841C.TIME_UNSET;
        this.pendingStreamChangeTimesUs = new long[10];
        this.eventDispatcher = new AudioRendererEventListener.EventDispatcher(eventHandler, eventListener);
        audioSink2.setListener(new AudioSinkListener());
    }

    /* access modifiers changed from: protected */
    public int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws MediaCodecUtil.DecoderQueryException {
        int adaptiveSupport;
        String mimeType = format.sampleMimeType;
        if (!MimeTypes.isAudio(mimeType)) {
            return 0;
        }
        int tunnelingSupport = Util.SDK_INT >= 21 ? 32 : 0;
        boolean supportsFormatDrm = supportsFormatDrm(drmSessionManager, format.drmInitData);
        int formatSupport = 4;
        if (supportsFormatDrm && allowPassthrough(format.channelCount, mimeType) && mediaCodecSelector.getPassthroughDecoderInfo() != null) {
            return tunnelingSupport | 8 | 4;
        }
        if ((MimeTypes.AUDIO_RAW.equals(mimeType) && !this.audioSink.supportsOutput(format.channelCount, format.pcmEncoding)) || !this.audioSink.supportsOutput(format.channelCount, 2)) {
            return 1;
        }
        boolean requiresSecureDecryption = false;
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData != null) {
            for (int i = 0; i < drmInitData.schemeDataCount; i++) {
                requiresSecureDecryption |= drmInitData.get(i).requiresSecureDecryption;
            }
        }
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mediaCodecSelector, format, requiresSecureDecryption);
        if (decoderInfos.isEmpty()) {
            if (!requiresSecureDecryption || mediaCodecSelector.getDecoderInfos(format.sampleMimeType, false, false).isEmpty()) {
                return 1;
            }
            return 2;
        } else if (!supportsFormatDrm) {
            return 2;
        } else {
            MediaCodecInfo decoderInfo = decoderInfos.get(0);
            boolean isFormatSupported = decoderInfo.isFormatSupported(format);
            if (!isFormatSupported || !decoderInfo.isSeamlessAdaptationSupported(format)) {
                adaptiveSupport = 8;
            } else {
                adaptiveSupport = 16;
            }
            if (!isFormatSupported) {
                formatSupport = 3;
            }
            return adaptiveSupport | tunnelingSupport | formatSupport;
        }
    }

    /* access modifiers changed from: protected */
    public List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder) throws MediaCodecUtil.DecoderQueryException {
        MediaCodecInfo passthroughDecoderInfo;
        if (allowPassthrough(format.channelCount, format.sampleMimeType) && (passthroughDecoderInfo = mediaCodecSelector.getPassthroughDecoderInfo()) != null) {
            return Collections.singletonList(passthroughDecoderInfo);
        }
        List<MediaCodecInfo> decoderInfos = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(format.sampleMimeType, requiresSecureDecoder, false), format);
        if (MimeTypes.AUDIO_E_AC3_JOC.equals(format.sampleMimeType)) {
            decoderInfos.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.AUDIO_E_AC3, requiresSecureDecoder, false));
        }
        return Collections.unmodifiableList(decoderInfos);
    }

    /* access modifiers changed from: protected */
    public boolean allowPassthrough(int channelCount2, String mimeType) {
        return this.audioSink.supportsOutput(channelCount2, MimeTypes.getEncoding(mimeType));
    }

    /* access modifiers changed from: protected */
    public void configureCodec(MediaCodecInfo codecInfo, MediaCodec codec, Format format, MediaCrypto crypto, float codecOperatingRate) {
        this.codecMaxInputSize = getCodecMaxInputSize(codecInfo, format, getStreamFormats());
        this.codecNeedsDiscardChannelsWorkaround = codecNeedsDiscardChannelsWorkaround(codecInfo.name);
        this.codecNeedsEosBufferTimestampWorkaround = codecNeedsEosBufferTimestampWorkaround(codecInfo.name);
        this.passthroughEnabled = codecInfo.passthrough;
        MediaFormat mediaFormat = getMediaFormat(format, this.passthroughEnabled ? MimeTypes.AUDIO_RAW : codecInfo.mimeType, this.codecMaxInputSize, codecOperatingRate);
        codec.configure(mediaFormat, (Surface) null, crypto, 0);
        if (this.passthroughEnabled) {
            this.passthroughMediaFormat = mediaFormat;
            this.passthroughMediaFormat.setString("mime", format.sampleMimeType);
            return;
        }
        this.passthroughMediaFormat = null;
    }

    /* access modifiers changed from: protected */
    public int canKeepCodec(MediaCodec codec, MediaCodecInfo codecInfo, Format oldFormat, Format newFormat) {
        if (getCodecMaxInputSize(codecInfo, newFormat) > this.codecMaxInputSize || oldFormat.encoderDelay != 0 || oldFormat.encoderPadding != 0 || newFormat.encoderDelay != 0 || newFormat.encoderPadding != 0) {
            return 0;
        }
        if (codecInfo.isSeamlessAdaptationSupported(oldFormat, newFormat, true)) {
            return 3;
        }
        if (areCodecConfigurationCompatible(oldFormat, newFormat)) {
            return 1;
        }
        return 0;
    }

    public MediaClock getMediaClock() {
        return this;
    }

    /* access modifiers changed from: protected */
    public float getCodecOperatingRateV23(float operatingRate, Format format, Format[] streamFormats) {
        int maxSampleRate = -1;
        for (Format streamFormat : streamFormats) {
            int streamSampleRate = streamFormat.sampleRate;
            if (streamSampleRate != -1) {
                maxSampleRate = Math.max(maxSampleRate, streamSampleRate);
            }
        }
        if (maxSampleRate == -1) {
            return -1.0f;
        }
        return ((float) maxSampleRate) * operatingRate;
    }

    /* access modifiers changed from: protected */
    public void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
        this.eventDispatcher.decoderInitialized(name, initializedTimestampMs, initializationDurationMs);
    }

    /* access modifiers changed from: protected */
    public void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        int i;
        super.onInputFormatChanged(formatHolder);
        Format newFormat = formatHolder.format;
        this.eventDispatcher.inputFormatChanged(newFormat);
        if (MimeTypes.AUDIO_RAW.equals(newFormat.sampleMimeType)) {
            i = newFormat.pcmEncoding;
        } else {
            i = 2;
        }
        this.pcmEncoding = i;
        this.channelCount = newFormat.channelCount;
        this.encoderDelay = newFormat.encoderDelay;
        this.encoderPadding = newFormat.encoderPadding;
    }

    /* access modifiers changed from: protected */
    public void onOutputFormatChanged(MediaCodec codec, MediaFormat outputFormat) throws ExoPlaybackException {
        MediaFormat format;
        int encoding;
        int[] channelMap;
        int i;
        MediaFormat mediaFormat = this.passthroughMediaFormat;
        if (mediaFormat != null) {
            encoding = MimeTypes.getEncoding(mediaFormat.getString("mime"));
            format = this.passthroughMediaFormat;
        } else {
            encoding = this.pcmEncoding;
            format = outputFormat;
        }
        int channelCount2 = format.getInteger("channel-count");
        int sampleRate = format.getInteger("sample-rate");
        if (!this.codecNeedsDiscardChannelsWorkaround || channelCount2 != 6 || (i = this.channelCount) >= 6) {
            channelMap = null;
        } else {
            int[] channelMap2 = new int[i];
            for (int i2 = 0; i2 < this.channelCount; i2++) {
                channelMap2[i2] = i2;
            }
            channelMap = channelMap2;
        }
        try {
            this.audioSink.configure(encoding, channelCount2, sampleRate, 0, channelMap, this.encoderDelay, this.encoderPadding);
        } catch (AudioSink.ConfigurationException e) {
            throw ExoPlaybackException.createForRenderer(e, getIndex());
        }
    }

    /* access modifiers changed from: protected */
    public void onAudioSessionId(int audioSessionId) {
    }

    /* access modifiers changed from: protected */
    public void onAudioTrackPositionDiscontinuity() {
    }

    /* access modifiers changed from: protected */
    public void onAudioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean joining) throws ExoPlaybackException {
        super.onEnabled(joining);
        this.eventDispatcher.enabled(this.decoderCounters);
        int tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        if (tunnelingAudioSessionId != 0) {
            this.audioSink.enableTunnelingV21(tunnelingAudioSessionId);
        } else {
            this.audioSink.disableTunneling();
        }
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formats, long offsetUs) throws ExoPlaybackException {
        super.onStreamChanged(formats, offsetUs);
        if (this.lastInputTimeUs != C0841C.TIME_UNSET) {
            int i = this.pendingStreamChangeCount;
            long[] jArr = this.pendingStreamChangeTimesUs;
            if (i == jArr.length) {
                long j = jArr[i - 1];
                StringBuilder sb = new StringBuilder(67);
                sb.append("Too many stream changes, so dropping change at ");
                sb.append(j);
                Log.m30w(TAG, sb.toString());
            } else {
                this.pendingStreamChangeCount = i + 1;
            }
            this.pendingStreamChangeTimesUs[this.pendingStreamChangeCount - 1] = this.lastInputTimeUs;
        }
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        super.onPositionReset(positionUs, joining);
        this.audioSink.flush();
        this.currentPositionUs = positionUs;
        this.allowFirstBufferPositionDiscontinuity = true;
        this.allowPositionDiscontinuity = true;
        this.lastInputTimeUs = C0841C.TIME_UNSET;
        this.pendingStreamChangeCount = 0;
    }

    /* access modifiers changed from: protected */
    public void onStarted() {
        super.onStarted();
        this.audioSink.play();
    }

    /* access modifiers changed from: protected */
    public void onStopped() {
        updateCurrentPosition();
        this.audioSink.pause();
        super.onStopped();
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        try {
            this.lastInputTimeUs = C0841C.TIME_UNSET;
            this.pendingStreamChangeCount = 0;
            this.audioSink.flush();
            try {
                super.onDisabled();
            } finally {
                this.eventDispatcher.disabled(this.decoderCounters);
            }
        } catch (Throwable th) {
            super.onDisabled();
            throw th;
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        try {
            super.onReset();
        } finally {
            this.audioSink.reset();
        }
    }

    public boolean isEnded() {
        return super.isEnded() && this.audioSink.isEnded();
    }

    public boolean isReady() {
        return this.audioSink.hasPendingData() || super.isReady();
    }

    public long getPositionUs() {
        if (getState() == 2) {
            updateCurrentPosition();
        }
        return this.currentPositionUs;
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        return this.audioSink.setPlaybackParameters(playbackParameters);
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.audioSink.getPlaybackParameters();
    }

    /* access modifiers changed from: protected */
    public void onQueueInputBuffer(DecoderInputBuffer buffer) {
        if (this.allowFirstBufferPositionDiscontinuity && !buffer.isDecodeOnly()) {
            if (Math.abs(buffer.timeUs - this.currentPositionUs) > 500000) {
                this.currentPositionUs = buffer.timeUs;
            }
            this.allowFirstBufferPositionDiscontinuity = false;
        }
        this.lastInputTimeUs = Math.max(buffer.timeUs, this.lastInputTimeUs);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onProcessedOutputBuffer(long presentationTimeUs) {
        while (this.pendingStreamChangeCount != 0 && presentationTimeUs >= this.pendingStreamChangeTimesUs[0]) {
            this.audioSink.handleDiscontinuity();
            this.pendingStreamChangeCount--;
            long[] jArr = this.pendingStreamChangeTimesUs;
            System.arraycopy(jArr, 1, jArr, 0, this.pendingStreamChangeCount);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void}
     arg types: [int, int]
     candidates:
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, long):void}
      ClspMth{android.media.MediaCodec.releaseOutputBuffer(int, boolean):void} */
    /* access modifiers changed from: protected */
    public boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodec codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, long bufferPresentationTimeUs, boolean shouldSkip, Format format) throws ExoPlaybackException {
        long bufferPresentationTimeUs2;
        int i = bufferIndex;
        if (!this.codecNeedsEosBufferTimestampWorkaround || bufferPresentationTimeUs != 0 || (bufferFlags & 4) == 0 || this.lastInputTimeUs == C0841C.TIME_UNSET) {
            bufferPresentationTimeUs2 = bufferPresentationTimeUs;
        } else {
            bufferPresentationTimeUs2 = this.lastInputTimeUs;
        }
        if (this.passthroughEnabled && (bufferFlags & 2) != 0) {
            codec.releaseOutputBuffer(i, false);
            return true;
        } else if (shouldSkip) {
            codec.releaseOutputBuffer(i, false);
            this.decoderCounters.skippedOutputBufferCount++;
            this.audioSink.handleDiscontinuity();
            return true;
        } else {
            try {
                try {
                    if (!this.audioSink.handleBuffer(buffer, bufferPresentationTimeUs2)) {
                        return false;
                    }
                    codec.releaseOutputBuffer(i, false);
                    this.decoderCounters.renderedOutputBufferCount++;
                    return true;
                } catch (AudioSink.InitializationException | AudioSink.WriteException e) {
                    e = e;
                    throw ExoPlaybackException.createForRenderer(e, getIndex());
                }
            } catch (AudioSink.InitializationException | AudioSink.WriteException e2) {
                e = e2;
                throw ExoPlaybackException.createForRenderer(e, getIndex());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void renderToEndOfStream() throws ExoPlaybackException {
        try {
            this.audioSink.playToEndOfStream();
        } catch (AudioSink.WriteException e) {
            throw ExoPlaybackException.createForRenderer(e, getIndex());
        }
    }

    public void handleMessage(int messageType, @Nullable Object message) throws ExoPlaybackException {
        if (messageType == 2) {
            this.audioSink.setVolume(((Float) message).floatValue());
        } else if (messageType == 3) {
            this.audioSink.setAudioAttributes((AudioAttributes) message);
        } else if (messageType != 5) {
            super.handleMessage(messageType, message);
        } else {
            this.audioSink.setAuxEffectInfo((AuxEffectInfo) message);
        }
    }

    /* access modifiers changed from: protected */
    public int getCodecMaxInputSize(MediaCodecInfo codecInfo, Format format, Format[] streamFormats) {
        int maxInputSize = getCodecMaxInputSize(codecInfo, format);
        if (streamFormats.length == 1) {
            return maxInputSize;
        }
        int maxInputSize2 = maxInputSize;
        for (Format streamFormat : streamFormats) {
            if (codecInfo.isSeamlessAdaptationSupported(format, streamFormat, false)) {
                maxInputSize2 = Math.max(maxInputSize2, getCodecMaxInputSize(codecInfo, streamFormat));
            }
        }
        return maxInputSize2;
    }

    private int getCodecMaxInputSize(MediaCodecInfo codecInfo, Format format) {
        if (!"OMX.google.raw.decoder".equals(codecInfo.name) || Util.SDK_INT >= 24 || (Util.SDK_INT == 23 && Util.isTv(this.context))) {
            return format.maxInputSize;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public boolean areCodecConfigurationCompatible(Format oldFormat, Format newFormat) {
        return Util.areEqual(oldFormat.sampleMimeType, newFormat.sampleMimeType) && oldFormat.channelCount == newFormat.channelCount && oldFormat.sampleRate == newFormat.sampleRate && oldFormat.initializationDataEquals(newFormat);
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"InlinedApi"})
    public MediaFormat getMediaFormat(Format format, String codecMimeType, int codecMaxInputSize2, float codecOperatingRate) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", codecMimeType);
        mediaFormat.setInteger("channel-count", format.channelCount);
        mediaFormat.setInteger("sample-rate", format.sampleRate);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxInputSize2);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(TalkContract.CommonPresenceColumns.PRIORITY, 0);
            if (codecOperatingRate != -1.0f) {
                mediaFormat.setFloat("operating-rate", codecOperatingRate);
            }
        }
        if (Util.SDK_INT <= 28 && MimeTypes.AUDIO_AC4.equals(format.sampleMimeType)) {
            mediaFormat.setInteger("ac4-is-sync", 1);
        }
        return mediaFormat;
    }

    private void updateCurrentPosition() {
        long j;
        long newCurrentPositionUs = this.audioSink.getCurrentPositionUs(isEnded());
        if (newCurrentPositionUs != Long.MIN_VALUE) {
            if (this.allowPositionDiscontinuity) {
                j = newCurrentPositionUs;
            } else {
                j = Math.max(this.currentPositionUs, newCurrentPositionUs);
            }
            this.currentPositionUs = j;
            this.allowPositionDiscontinuity = false;
        }
    }

    private static boolean codecNeedsDiscardChannelsWorkaround(String codecName) {
        return Util.SDK_INT < 24 && "OMX.SEC.aac.dec".equals(codecName) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("herolte") || Util.DEVICE.startsWith("heroqlte"));
    }

    private static boolean codecNeedsEosBufferTimestampWorkaround(String codecName) {
        return Util.SDK_INT < 21 && "OMX.SEC.mp3.dec".equals(codecName) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("baffin") || Util.DEVICE.startsWith("grand") || Util.DEVICE.startsWith("fortuna") || Util.DEVICE.startsWith("gprimelte") || Util.DEVICE.startsWith("j2y18lte") || Util.DEVICE.startsWith("ms01"));
    }

    private final class AudioSinkListener implements AudioSink.Listener {
        private AudioSinkListener() {
        }

        public void onAudioSessionId(int audioSessionId) {
            MediaCodecAudioRenderer.this.eventDispatcher.audioSessionId(audioSessionId);
            MediaCodecAudioRenderer.this.onAudioSessionId(audioSessionId);
        }

        public void onPositionDiscontinuity() {
            MediaCodecAudioRenderer.this.onAudioTrackPositionDiscontinuity();
            boolean unused = MediaCodecAudioRenderer.this.allowPositionDiscontinuity = true;
        }

        public void onUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            MediaCodecAudioRenderer.this.eventDispatcher.audioTrackUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
            MediaCodecAudioRenderer.this.onAudioTrackUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }
    }
}
