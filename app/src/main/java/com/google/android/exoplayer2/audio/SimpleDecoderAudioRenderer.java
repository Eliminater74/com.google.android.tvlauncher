package com.google.android.exoplayer2.audio;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.SimpleDecoder;
import com.google.android.exoplayer2.decoder.SimpleOutputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class SimpleDecoderAudioRenderer extends BaseRenderer implements MediaClock {
    private static final int REINITIALIZATION_STATE_NONE = 0;
    private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;
    /* access modifiers changed from: private */
    public final AudioRendererEventListener.EventDispatcher eventDispatcher;
    private final AudioSink audioSink;
    private final DrmSessionManager<ExoMediaCrypto> drmSessionManager;
    private final DecoderInputBuffer flagsOnlyBuffer;
    private final FormatHolder formatHolder;
    private final boolean playClearSamplesWithoutKeys;
    /* access modifiers changed from: private */
    public boolean allowPositionDiscontinuity;
    private boolean allowFirstBufferPositionDiscontinuity;
    private boolean audioTrackNeedsConfigure;
    private long currentPositionUs;
    private SimpleDecoder<DecoderInputBuffer, ? extends SimpleOutputBuffer, ? extends AudioDecoderException> decoder;
    private DecoderCounters decoderCounters;
    @Nullable
    private DrmSession<ExoMediaCrypto> decoderDrmSession;
    private boolean decoderReceivedBuffers;
    private int decoderReinitializationState;
    private int encoderDelay;
    private int encoderPadding;
    private DecoderInputBuffer inputBuffer;
    private Format inputFormat;
    private boolean inputStreamEnded;
    private SimpleOutputBuffer outputBuffer;
    private boolean outputStreamEnded;
    @Nullable
    private DrmSession<ExoMediaCrypto> sourceDrmSession;
    private boolean waitingForKeys;

    public SimpleDecoderAudioRenderer() {
        this((Handler) null, (AudioRendererEventListener) null, new AudioProcessor[0]);
    }

    public SimpleDecoderAudioRenderer(@Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, AudioProcessor... audioProcessors) {
        this(eventHandler, eventListener, null, null, false, audioProcessors);
    }

    public SimpleDecoderAudioRenderer(@Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, @Nullable AudioCapabilities audioCapabilities) {
        this(eventHandler, eventListener, audioCapabilities, null, false, new AudioProcessor[0]);
    }

    public SimpleDecoderAudioRenderer(@Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, @Nullable AudioCapabilities audioCapabilities, @Nullable DrmSessionManager<ExoMediaCrypto> drmSessionManager2, boolean playClearSamplesWithoutKeys2, AudioProcessor... audioProcessors) {
        this(eventHandler, eventListener, drmSessionManager2, playClearSamplesWithoutKeys2, new DefaultAudioSink(audioCapabilities, audioProcessors));
    }

    public SimpleDecoderAudioRenderer(@Nullable Handler eventHandler, @Nullable AudioRendererEventListener eventListener, @Nullable DrmSessionManager<ExoMediaCrypto> drmSessionManager2, boolean playClearSamplesWithoutKeys2, AudioSink audioSink2) {
        super(1);
        this.drmSessionManager = drmSessionManager2;
        this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys2;
        this.eventDispatcher = new AudioRendererEventListener.EventDispatcher(eventHandler, eventListener);
        this.audioSink = audioSink2;
        audioSink2.setListener(new AudioSinkListener());
        this.formatHolder = new FormatHolder();
        this.flagsOnlyBuffer = DecoderInputBuffer.newFlagsOnlyInstance();
        this.decoderReinitializationState = 0;
        this.audioTrackNeedsConfigure = true;
    }

    /* access modifiers changed from: protected */
    public abstract SimpleDecoder<DecoderInputBuffer, ? extends SimpleOutputBuffer, ? extends AudioDecoderException> createDecoder(Format format, ExoMediaCrypto exoMediaCrypto) throws AudioDecoderException;

    /* access modifiers changed from: protected */
    public abstract int supportsFormatInternal(DrmSessionManager<ExoMediaCrypto> drmSessionManager2, Format format);

    public MediaClock getMediaClock() {
        return this;
    }

    public final int supportsFormat(Format format) {
        int tunnelingSupport = 0;
        if (!MimeTypes.isAudio(format.sampleMimeType)) {
            return 0;
        }
        int formatSupport = supportsFormatInternal(this.drmSessionManager, format);
        if (formatSupport <= 2) {
            return formatSupport;
        }
        if (Util.SDK_INT >= 21) {
            tunnelingSupport = 32;
        }
        return tunnelingSupport | 8 | formatSupport;
    }

    /* access modifiers changed from: protected */
    public final boolean supportsOutput(int channelCount, int encoding) {
        return this.audioSink.supportsOutput(channelCount, encoding);
    }

    public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        if (this.outputStreamEnded) {
            try {
                this.audioSink.playToEndOfStream();
            } catch (AudioSink.WriteException e) {
                throw ExoPlaybackException.createForRenderer(e, getIndex());
            }
        } else {
            if (this.inputFormat == null) {
                this.flagsOnlyBuffer.clear();
                int result = readSource(this.formatHolder, this.flagsOnlyBuffer, true);
                if (result == -5) {
                    onInputFormatChanged(this.formatHolder.format);
                } else if (result == -4) {
                    Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                    this.inputStreamEnded = true;
                    processEndOfStream();
                    return;
                } else {
                    return;
                }
            }
            maybeInitDecoder();
            if (this.decoder != null) {
                try {
                    TraceUtil.beginSection("drainAndFeed");
                    while (drainOutputBuffer()) {
                    }
                    while (feedInputBuffer()) {
                    }
                    TraceUtil.endSection();
                    this.decoderCounters.ensureUpdated();
                } catch (AudioDecoderException | AudioSink.ConfigurationException | AudioSink.InitializationException | AudioSink.WriteException e2) {
                    throw ExoPlaybackException.createForRenderer(e2, getIndex());
                }
            }
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
    public Format getOutputFormat() {
        return Format.createAudioSampleFormat(null, MimeTypes.AUDIO_RAW, null, -1, -1, this.inputFormat.channelCount, this.inputFormat.sampleRate, 2, null, null, 0, null);
    }

    private boolean drainOutputBuffer() throws ExoPlaybackException, AudioDecoderException, AudioSink.ConfigurationException, AudioSink.InitializationException, AudioSink.WriteException {
        if (this.outputBuffer == null) {
            this.outputBuffer = (SimpleOutputBuffer) this.decoder.dequeueOutputBuffer();
            SimpleOutputBuffer simpleOutputBuffer = this.outputBuffer;
            if (simpleOutputBuffer == null) {
                return false;
            }
            if (simpleOutputBuffer.skippedOutputBufferCount > 0) {
                this.decoderCounters.skippedOutputBufferCount += this.outputBuffer.skippedOutputBufferCount;
                this.audioSink.handleDiscontinuity();
            }
        }
        if (this.outputBuffer.isEndOfStream()) {
            if (this.decoderReinitializationState == 2) {
                releaseDecoder();
                maybeInitDecoder();
                this.audioTrackNeedsConfigure = true;
            } else {
                this.outputBuffer.release();
                this.outputBuffer = null;
                processEndOfStream();
            }
            return false;
        }
        if (this.audioTrackNeedsConfigure) {
            Format outputFormat = getOutputFormat();
            this.audioSink.configure(outputFormat.pcmEncoding, outputFormat.channelCount, outputFormat.sampleRate, 0, null, this.encoderDelay, this.encoderPadding);
            this.audioTrackNeedsConfigure = false;
        }
        if (!this.audioSink.handleBuffer(this.outputBuffer.data, this.outputBuffer.timeUs)) {
            return false;
        }
        this.decoderCounters.renderedOutputBufferCount++;
        this.outputBuffer.release();
        this.outputBuffer = null;
        return true;
    }

    private boolean feedInputBuffer() throws AudioDecoderException, ExoPlaybackException {
        int result;
        SimpleDecoder<DecoderInputBuffer, ? extends SimpleOutputBuffer, ? extends AudioDecoderException> simpleDecoder = this.decoder;
        if (simpleDecoder == null || this.decoderReinitializationState == 2 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputBuffer == null) {
            this.inputBuffer = simpleDecoder.dequeueInputBuffer();
            if (this.inputBuffer == null) {
                return false;
            }
        }
        if (this.decoderReinitializationState == 1) {
            this.inputBuffer.setFlags(4);
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.inputBuffer = null;
            this.decoderReinitializationState = 2;
            return false;
        }
        if (this.waitingForKeys) {
            result = -4;
        } else {
            result = readSource(this.formatHolder, this.inputBuffer, false);
        }
        if (result == -3) {
            return false;
        }
        if (result == -5) {
            onInputFormatChanged(this.formatHolder.format);
            return true;
        } else if (this.inputBuffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.inputBuffer = null;
            return false;
        } else {
            this.waitingForKeys = shouldWaitForKeys(this.inputBuffer.isEncrypted());
            if (this.waitingForKeys) {
                return false;
            }
            this.inputBuffer.flip();
            onQueueInputBuffer(this.inputBuffer);
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.decoderReceivedBuffers = true;
            this.decoderCounters.inputBufferCount++;
            this.inputBuffer = null;
            return true;
        }
    }

    private boolean shouldWaitForKeys(boolean bufferEncrypted) throws ExoPlaybackException {
        if (this.decoderDrmSession == null || (!bufferEncrypted && this.playClearSamplesWithoutKeys)) {
            return false;
        }
        int drmSessionState = this.decoderDrmSession.getState();
        if (drmSessionState == 1) {
            throw ExoPlaybackException.createForRenderer(this.decoderDrmSession.getError(), getIndex());
        } else if (drmSessionState != 4) {
            return true;
        } else {
            return false;
        }
    }

    private void processEndOfStream() throws ExoPlaybackException {
        this.outputStreamEnded = true;
        try {
            this.audioSink.playToEndOfStream();
        } catch (AudioSink.WriteException e) {
            throw ExoPlaybackException.createForRenderer(e, getIndex());
        }
    }

    private void flushDecoder() throws ExoPlaybackException {
        this.waitingForKeys = false;
        if (this.decoderReinitializationState != 0) {
            releaseDecoder();
            maybeInitDecoder();
            return;
        }
        this.inputBuffer = null;
        SimpleOutputBuffer simpleOutputBuffer = this.outputBuffer;
        if (simpleOutputBuffer != null) {
            simpleOutputBuffer.release();
            this.outputBuffer = null;
        }
        this.decoder.flush();
        this.decoderReceivedBuffers = false;
    }

    public boolean isEnded() {
        return this.outputStreamEnded && this.audioSink.isEnded();
    }

    public boolean isReady() {
        return this.audioSink.hasPendingData() || (this.inputFormat != null && !this.waitingForKeys && (isSourceReady() || this.outputBuffer != null));
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
    public void onEnabled(boolean joining) throws ExoPlaybackException {
        this.decoderCounters = new DecoderCounters();
        this.eventDispatcher.enabled(this.decoderCounters);
        int tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        if (tunnelingAudioSessionId != 0) {
            this.audioSink.enableTunnelingV21(tunnelingAudioSessionId);
        } else {
            this.audioSink.disableTunneling();
        }
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        this.audioSink.flush();
        this.currentPositionUs = positionUs;
        this.allowFirstBufferPositionDiscontinuity = true;
        this.allowPositionDiscontinuity = true;
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        if (this.decoder != null) {
            flushDecoder();
        }
    }

    /* access modifiers changed from: protected */
    public void onStarted() {
        this.audioSink.play();
    }

    /* access modifiers changed from: protected */
    public void onStopped() {
        updateCurrentPosition();
        this.audioSink.pause();
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        this.inputFormat = null;
        this.audioTrackNeedsConfigure = true;
        this.waitingForKeys = false;
        try {
            setSourceDrmSession(null);
            releaseDecoder();
            this.audioSink.reset();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
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

    /* JADX INFO: Multiple debug info for r1v2 long: [D('codecInitializingTimestamp' long), D('drmError' com.google.android.exoplayer2.drm.DrmSession$DrmSessionException)] */
    private void maybeInitDecoder() throws ExoPlaybackException {
        if (this.decoder == null) {
            setDecoderDrmSession(this.sourceDrmSession);
            ExoMediaCrypto mediaCrypto = null;
            DrmSession<ExoMediaCrypto> drmSession = this.decoderDrmSession;
            if (drmSession == null || (mediaCrypto = drmSession.getMediaCrypto()) != null || this.decoderDrmSession.getError() != null) {
                try {
                    long codecInitializingTimestamp = SystemClock.elapsedRealtime();
                    TraceUtil.beginSection("createAudioDecoder");
                    this.decoder = createDecoder(this.inputFormat, mediaCrypto);
                    TraceUtil.endSection();
                    long codecInitializedTimestamp = SystemClock.elapsedRealtime();
                    this.eventDispatcher.decoderInitialized(this.decoder.getName(), codecInitializedTimestamp, codecInitializedTimestamp - codecInitializingTimestamp);
                    this.decoderCounters.decoderInitCount++;
                } catch (AudioDecoderException e) {
                    throw ExoPlaybackException.createForRenderer(e, getIndex());
                }
            }
        }
    }

    private void releaseDecoder() {
        this.inputBuffer = null;
        this.outputBuffer = null;
        this.decoderReinitializationState = 0;
        this.decoderReceivedBuffers = false;
        SimpleDecoder<DecoderInputBuffer, ? extends SimpleOutputBuffer, ? extends AudioDecoderException> simpleDecoder = this.decoder;
        if (simpleDecoder != null) {
            simpleDecoder.release();
            this.decoder = null;
            this.decoderCounters.decoderReleaseCount++;
        }
        setDecoderDrmSession(null);
    }

    private void setSourceDrmSession(@Nullable DrmSession<ExoMediaCrypto> session) {
        DrmSession<ExoMediaCrypto> previous = this.sourceDrmSession;
        this.sourceDrmSession = session;
        releaseDrmSessionIfUnused(previous);
    }

    private void setDecoderDrmSession(@Nullable DrmSession<ExoMediaCrypto> session) {
        DrmSession<ExoMediaCrypto> previous = this.decoderDrmSession;
        this.decoderDrmSession = session;
        releaseDrmSessionIfUnused(previous);
    }

    private void releaseDrmSessionIfUnused(@Nullable DrmSession<ExoMediaCrypto> session) {
        if (session != null && session != this.decoderDrmSession && session != this.sourceDrmSession) {
            this.drmSessionManager.releaseSession(session);
        }
    }

    private void onInputFormatChanged(Format newFormat) throws ExoPlaybackException {
        DrmInitData drmInitData;
        Format oldFormat = this.inputFormat;
        this.inputFormat = newFormat;
        DrmInitData drmInitData2 = this.inputFormat.drmInitData;
        if (oldFormat == null) {
            drmInitData = null;
        } else {
            drmInitData = oldFormat.drmInitData;
        }
        if (!Util.areEqual(drmInitData2, drmInitData)) {
            if (this.inputFormat.drmInitData != null) {
                DrmSessionManager<ExoMediaCrypto> drmSessionManager2 = this.drmSessionManager;
                if (drmSessionManager2 != null) {
                    DrmSession<ExoMediaCrypto> session = drmSessionManager2.acquireSession(Looper.myLooper(), newFormat.drmInitData);
                    if (session == this.decoderDrmSession || session == this.sourceDrmSession) {
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
        if (this.decoderReceivedBuffers) {
            this.decoderReinitializationState = 1;
        } else {
            releaseDecoder();
            maybeInitDecoder();
            this.audioTrackNeedsConfigure = true;
        }
        this.encoderDelay = newFormat.encoderDelay;
        this.encoderPadding = newFormat.encoderPadding;
        this.eventDispatcher.inputFormatChanged(newFormat);
    }

    private void onQueueInputBuffer(DecoderInputBuffer buffer) {
        if (this.allowFirstBufferPositionDiscontinuity && !buffer.isDecodeOnly()) {
            if (Math.abs(buffer.timeUs - this.currentPositionUs) > 500000) {
                this.currentPositionUs = buffer.timeUs;
            }
            this.allowFirstBufferPositionDiscontinuity = false;
        }
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

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface ReinitializationState {
    }

    private final class AudioSinkListener implements AudioSink.Listener {
        private AudioSinkListener() {
        }

        public void onAudioSessionId(int audioSessionId) {
            SimpleDecoderAudioRenderer.this.eventDispatcher.audioSessionId(audioSessionId);
            SimpleDecoderAudioRenderer.this.onAudioSessionId(audioSessionId);
        }

        public void onPositionDiscontinuity() {
            SimpleDecoderAudioRenderer.this.onAudioTrackPositionDiscontinuity();
            boolean unused = SimpleDecoderAudioRenderer.this.allowPositionDiscontinuity = true;
        }

        public void onUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            SimpleDecoderAudioRenderer.this.eventDispatcher.audioTrackUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
            SimpleDecoderAudioRenderer.this.onAudioTrackUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }
    }
}
