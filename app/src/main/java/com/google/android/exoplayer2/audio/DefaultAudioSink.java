package com.google.android.exoplayer2.audio;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.ConditionVariable;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class DefaultAudioSink implements AudioSink {
    private static final int AC3_BUFFER_MULTIPLICATION_FACTOR = 2;
    private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
    private static final int ERROR_BAD_VALUE = -2;
    private static final long MAX_BUFFER_DURATION_US = 750000;
    private static final long MIN_BUFFER_DURATION_US = 250000;
    private static final int MODE_STATIC = 0;
    private static final int MODE_STREAM = 1;
    private static final long PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final int START_IN_SYNC = 1;
    private static final int START_NEED_SYNC = 2;
    private static final int START_NOT_SET = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final String TAG = "AudioTrack";
    @SuppressLint({"InlinedApi"})
    private static final int WRITE_NON_BLOCKING = 1;
    public static boolean enablePreV21AudioSessionWorkaround = false;
    public static boolean failOnSpuriousAudioTimestamp = false;
    /* access modifiers changed from: private */
    public final ConditionVariable releasingConditionVariable;
    @Nullable
    private final AudioCapabilities audioCapabilities;
    private final AudioProcessorChain audioProcessorChain;
    private final AudioTrackPositionTracker audioTrackPositionTracker;
    private final ChannelMappingAudioProcessor channelMappingAudioProcessor;
    private final boolean enableConvertHighResIntPcmToFloat;
    private final ArrayDeque<PlaybackParametersCheckpoint> playbackParametersCheckpoints;
    private final AudioProcessor[] toFloatPcmAvailableAudioProcessors;
    private final AudioProcessor[] toIntPcmAvailableAudioProcessors;
    private final TrimmingAudioProcessor trimmingAudioProcessor;
    /* access modifiers changed from: private */
    public long lastFeedElapsedRealtimeMs;
    /* access modifiers changed from: private */
    @Nullable
    public AudioSink.Listener listener;
    private AudioProcessor[] activeAudioProcessors;
    @Nullable
    private PlaybackParameters afterDrainPlaybackParameters;
    private AudioAttributes audioAttributes;
    private int audioSessionId;
    private AudioTrack audioTrack;
    private AuxEffectInfo auxEffectInfo;
    @Nullable
    private ByteBuffer avSyncHeader;
    private int bytesUntilNextAvSync;
    private Configuration configuration;
    private int drainingAudioProcessorIndex;
    private int framesPerEncodedSample;
    private boolean handledEndOfStream;
    @Nullable
    private ByteBuffer inputBuffer;
    @Nullable
    private AudioTrack keepSessionIdAudioTrack;
    @Nullable
    private ByteBuffer outputBuffer;
    private ByteBuffer[] outputBuffers;
    @Nullable
    private Configuration pendingConfiguration;
    private PlaybackParameters playbackParameters;
    private long playbackParametersOffsetUs;
    private long playbackParametersPositionUs;
    private boolean playing;
    private byte[] preV21OutputBuffer;
    private int preV21OutputBufferOffset;
    private int startMediaTimeState;
    private long startMediaTimeUs;
    private long submittedEncodedFrames;
    private long submittedPcmBytes;
    private boolean tunneling;
    private float volume;
    private long writtenEncodedFrames;
    private long writtenPcmBytes;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.audio.DefaultAudioSink.<init>(com.google.android.exoplayer2.audio.AudioCapabilities, com.google.android.exoplayer2.audio.AudioProcessor[], boolean):void
     arg types: [com.google.android.exoplayer2.audio.AudioCapabilities, com.google.android.exoplayer2.audio.AudioProcessor[], int]
     candidates:
      com.google.android.exoplayer2.audio.DefaultAudioSink.<init>(com.google.android.exoplayer2.audio.AudioCapabilities, com.google.android.exoplayer2.audio.DefaultAudioSink$AudioProcessorChain, boolean):void
      com.google.android.exoplayer2.audio.DefaultAudioSink.<init>(com.google.android.exoplayer2.audio.AudioCapabilities, com.google.android.exoplayer2.audio.AudioProcessor[], boolean):void */
    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities2, AudioProcessor[] audioProcessors) {
        this(audioCapabilities2, audioProcessors, false);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities2, AudioProcessor[] audioProcessors, boolean enableConvertHighResIntPcmToFloat2) {
        this(audioCapabilities2, new DefaultAudioProcessorChain(audioProcessors), enableConvertHighResIntPcmToFloat2);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities2, AudioProcessorChain audioProcessorChain2, boolean enableConvertHighResIntPcmToFloat2) {
        this.audioCapabilities = audioCapabilities2;
        this.audioProcessorChain = (AudioProcessorChain) Assertions.checkNotNull(audioProcessorChain2);
        this.enableConvertHighResIntPcmToFloat = enableConvertHighResIntPcmToFloat2;
        this.releasingConditionVariable = new ConditionVariable(true);
        this.audioTrackPositionTracker = new AudioTrackPositionTracker(new PositionTrackerListener());
        this.channelMappingAudioProcessor = new ChannelMappingAudioProcessor();
        this.trimmingAudioProcessor = new TrimmingAudioProcessor();
        ArrayList<AudioProcessor> toIntPcmAudioProcessors = new ArrayList<>();
        Collections.addAll(toIntPcmAudioProcessors, new ResamplingAudioProcessor(), this.channelMappingAudioProcessor, this.trimmingAudioProcessor);
        Collections.addAll(toIntPcmAudioProcessors, audioProcessorChain2.getAudioProcessors());
        this.toIntPcmAvailableAudioProcessors = (AudioProcessor[]) toIntPcmAudioProcessors.toArray(new AudioProcessor[0]);
        this.toFloatPcmAvailableAudioProcessors = new AudioProcessor[]{new FloatResamplingAudioProcessor()};
        this.volume = 1.0f;
        this.startMediaTimeState = 0;
        this.audioAttributes = AudioAttributes.DEFAULT;
        this.audioSessionId = 0;
        this.auxEffectInfo = new AuxEffectInfo(0, 0.0f);
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.drainingAudioProcessorIndex = -1;
        this.activeAudioProcessors = new AudioProcessor[0];
        this.outputBuffers = new ByteBuffer[0];
        this.playbackParametersCheckpoints = new ArrayDeque<>();
    }

    private static AudioTrack initializeKeepSessionIdAudioTrack(int audioSessionId2) {
        return new AudioTrack(3, 4000, 4, 2, 2, 0, audioSessionId2);
    }

    private static int getChannelConfig(int channelCount, boolean isInputPcm) {
        if (Util.SDK_INT <= 28 && !isInputPcm) {
            if (channelCount == 7) {
                channelCount = 8;
            } else if (channelCount == 3 || channelCount == 4 || channelCount == 5) {
                channelCount = 6;
            }
        }
        if (Util.SDK_INT <= 26 && "fugu".equals(Util.DEVICE) && !isInputPcm && channelCount == 1) {
            channelCount = 2;
        }
        return Util.getAudioTrackChannelConfig(channelCount);
    }

    /* access modifiers changed from: private */
    public static int getMaximumEncodedRateBytesPerSecond(int encoding) {
        if (encoding == 5) {
            return 80000;
        }
        if (encoding == 6) {
            return 768000;
        }
        if (encoding == 7) {
            return 192000;
        }
        if (encoding == 8) {
            return 2250000;
        }
        if (encoding == 14) {
            return 3062500;
        }
        if (encoding == 17) {
            return 336000;
        }
        throw new IllegalArgumentException();
    }

    private static int getFramesPerEncodedSample(int encoding, ByteBuffer buffer) {
        if (encoding == 7 || encoding == 8) {
            return DtsUtil.parseDtsAudioSampleCount(buffer);
        }
        if (encoding == 5) {
            return Ac3Util.getAc3SyncframeAudioSampleCount();
        }
        if (encoding == 6) {
            return Ac3Util.parseEAc3SyncframeAudioSampleCount(buffer);
        }
        if (encoding == 17) {
            return Ac4Util.parseAc4SyncframeAudioSampleCount(buffer);
        }
        if (encoding == 14) {
            int syncframeOffset = Ac3Util.findTrueHdSyncframeOffset(buffer);
            if (syncframeOffset == -1) {
                return 0;
            }
            return Ac3Util.parseTrueHdSyncframeAudioSampleCount(buffer, syncframeOffset) * 16;
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append("Unexpected audio encoding: ");
        sb.append(encoding);
        throw new IllegalStateException(sb.toString());
    }

    @TargetApi(21)
    private static int writeNonBlockingV21(AudioTrack audioTrack2, ByteBuffer buffer, int size) {
        return audioTrack2.write(buffer, size, 1);
    }

    @TargetApi(21)
    private static void setVolumeInternalV21(AudioTrack audioTrack2, float volume2) {
        audioTrack2.setVolume(volume2);
    }

    private static void setVolumeInternalV3(AudioTrack audioTrack2, float volume2) {
        audioTrack2.setStereoVolume(volume2, volume2);
    }

    public void setListener(AudioSink.Listener listener2) {
        this.listener = listener2;
    }

    public boolean supportsOutput(int channelCount, int encoding) {
        if (!Util.isEncodingLinearPcm(encoding)) {
            AudioCapabilities audioCapabilities2 = this.audioCapabilities;
            if (audioCapabilities2 == null || !audioCapabilities2.supportsEncoding(encoding) || (channelCount != -1 && channelCount > this.audioCapabilities.getMaxChannelCount())) {
                return false;
            }
            return true;
        } else if (encoding != 4 || Util.SDK_INT >= 21) {
            return true;
        } else {
            return false;
        }
    }

    public long getCurrentPositionUs(boolean sourceEnded) {
        if (!isInitialized() || this.startMediaTimeState == 0) {
            return Long.MIN_VALUE;
        }
        return this.startMediaTimeUs + applySkipping(applySpeedup(Math.min(this.audioTrackPositionTracker.getCurrentPositionUs(sourceEnded), this.configuration.framesToDurationUs(getWrittenFrames()))));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00fa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void configure(int r26, int r27, int r28, int r29, @android.support.annotation.Nullable int[] r30, int r31, int r32) throws com.google.android.exoplayer2.audio.AudioSink.ConfigurationException {
        /*
            r25 = this;
            r1 = r25
            r2 = r27
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r3 = 21
            if (r0 >= r3) goto L_0x001e
            r0 = 8
            if (r2 != r0) goto L_0x001e
            if (r30 != 0) goto L_0x001e
            r0 = 6
            int[] r0 = new int[r0]
            r3 = 0
        L_0x0014:
            int r4 = r0.length
            if (r3 >= r4) goto L_0x001c
            r0[r3] = r3
            int r3 = r3 + 1
            goto L_0x0014
        L_0x001c:
            r3 = r0
            goto L_0x0020
        L_0x001e:
            r3 = r30
        L_0x0020:
            boolean r15 = com.google.android.exoplayer2.util.Util.isEncodingLinearPcm(r26)
            r0 = 4
            r5 = 1
            if (r15 == 0) goto L_0x002e
            r13 = r26
            if (r13 == r0) goto L_0x0030
            r6 = 1
            goto L_0x0031
        L_0x002e:
            r13 = r26
        L_0x0030:
            r6 = 0
        L_0x0031:
            r16 = r6
            r6 = r28
            r7 = r27
            r8 = r26
            boolean r9 = r1.enableConvertHighResIntPcmToFloat
            if (r9 == 0) goto L_0x004b
            boolean r0 = r1.supportsOutput(r2, r0)
            if (r0 == 0) goto L_0x004b
            boolean r0 = com.google.android.exoplayer2.util.Util.isEncodingHighResolutionIntegerPcm(r26)
            if (r0 == 0) goto L_0x004b
            r0 = 1
            goto L_0x004c
        L_0x004b:
            r0 = 0
        L_0x004c:
            r17 = r0
            if (r17 == 0) goto L_0x0053
            com.google.android.exoplayer2.audio.AudioProcessor[] r0 = r1.toFloatPcmAvailableAudioProcessors
            goto L_0x0055
        L_0x0053:
            com.google.android.exoplayer2.audio.AudioProcessor[] r0 = r1.toIntPcmAvailableAudioProcessors
        L_0x0055:
            r12 = r0
            r0 = 0
            if (r16 == 0) goto L_0x009b
            com.google.android.exoplayer2.audio.TrimmingAudioProcessor r9 = r1.trimmingAudioProcessor
            r11 = r31
            r10 = r32
            r9.setTrimFrameCount(r11, r10)
            com.google.android.exoplayer2.audio.ChannelMappingAudioProcessor r9 = r1.channelMappingAudioProcessor
            r9.setChannelMap(r3)
            int r9 = r12.length
            r14 = r6
            r6 = r0
            r0 = 0
        L_0x006b:
            if (r0 >= r9) goto L_0x0094
            r4 = r12[r0]
            boolean r18 = r4.configure(r14, r7, r8)     // Catch:{ UnhandledFormatException -> 0x008b }
            r6 = r6 | r18
            boolean r18 = r4.isActive()
            if (r18 == 0) goto L_0x0088
            int r7 = r4.getOutputChannelCount()
            int r14 = r4.getOutputSampleRateHz()
            int r8 = r4.getOutputEncoding()
        L_0x0088:
            int r0 = r0 + 1
            goto L_0x006b
        L_0x008b:
            r0 = move-exception
            r5 = r0
            r0 = r5
            com.google.android.exoplayer2.audio.AudioSink$ConfigurationException r5 = new com.google.android.exoplayer2.audio.AudioSink$ConfigurationException
            r5.<init>(r0)
            throw r5
        L_0x0094:
            r19 = r6
            r0 = r7
            r9 = r8
            r18 = r14
            goto L_0x00a5
        L_0x009b:
            r11 = r31
            r10 = r32
            r19 = r0
            r18 = r6
            r0 = r7
            r9 = r8
        L_0x00a5:
            int r20 = getChannelConfig(r0, r15)
            if (r20 == 0) goto L_0x00fa
            r4 = -1
            if (r15 == 0) goto L_0x00b3
            int r6 = com.google.android.exoplayer2.util.Util.getPcmFrameSize(r26, r27)
            goto L_0x00b4
        L_0x00b3:
            r6 = -1
        L_0x00b4:
            if (r15 == 0) goto L_0x00bc
            int r4 = com.google.android.exoplayer2.util.Util.getPcmFrameSize(r9, r0)
            r8 = r4
            goto L_0x00bd
        L_0x00bc:
            r8 = -1
        L_0x00bd:
            if (r16 == 0) goto L_0x00c3
            if (r17 != 0) goto L_0x00c3
            r14 = 1
            goto L_0x00c4
        L_0x00c3:
            r14 = 0
        L_0x00c4:
            com.google.android.exoplayer2.audio.DefaultAudioSink$Configuration r21 = new com.google.android.exoplayer2.audio.DefaultAudioSink$Configuration
            r4 = r21
            r5 = r15
            r7 = r28
            r22 = r9
            r9 = r18
            r10 = r20
            r11 = r22
            r23 = r12
            r12 = r29
            r13 = r16
            r24 = r15
            r15 = r23
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            boolean r5 = r25.isInitialized()
            if (r5 == 0) goto L_0x00f7
            com.google.android.exoplayer2.audio.DefaultAudioSink$Configuration r5 = r1.configuration
            boolean r5 = r4.canReuseAudioTrack(r5)
            if (r5 != 0) goto L_0x00f2
            r25.flush()
            goto L_0x00f7
        L_0x00f2:
            if (r19 == 0) goto L_0x00f7
            r1.pendingConfiguration = r4
            return
        L_0x00f7:
            r1.configuration = r4
            return
        L_0x00fa:
            com.google.android.exoplayer2.audio.AudioSink$ConfigurationException r4 = new com.google.android.exoplayer2.audio.AudioSink$ConfigurationException
            r5 = 38
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Unsupported channel count: "
            r6.append(r5)
            r6.append(r0)
            java.lang.String r5 = r6.toString()
            r4.<init>(r5)
            throw r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.configure(int, int, int, int, int[], int, int):void");
    }

    private void setupAudioProcessors() {
        AudioProcessor[] audioProcessors = this.configuration.availableAudioProcessors;
        ArrayList<AudioProcessor> newAudioProcessors = new ArrayList<>();
        for (AudioProcessor audioProcessor : audioProcessors) {
            if (audioProcessor.isActive()) {
                newAudioProcessors.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int count = newAudioProcessors.size();
        this.activeAudioProcessors = (AudioProcessor[]) newAudioProcessors.toArray(new AudioProcessor[count]);
        this.outputBuffers = new ByteBuffer[count];
        flushAudioProcessors();
    }

    private void flushAudioProcessors() {
        int i = 0;
        while (true) {
            AudioProcessor[] audioProcessorArr = this.activeAudioProcessors;
            if (i < audioProcessorArr.length) {
                AudioProcessor audioProcessor = audioProcessorArr[i];
                audioProcessor.flush();
                this.outputBuffers[i] = audioProcessor.getOutput();
                i++;
            } else {
                return;
            }
        }
    }

    private void initialize() throws AudioSink.InitializationException {
        PlaybackParameters playbackParameters2;
        this.releasingConditionVariable.block();
        this.audioTrack = ((Configuration) Assertions.checkNotNull(this.configuration)).buildAudioTrack(this.tunneling, this.audioAttributes, this.audioSessionId);
        int audioSessionId2 = this.audioTrack.getAudioSessionId();
        if (enablePreV21AudioSessionWorkaround && Util.SDK_INT < 21) {
            AudioTrack audioTrack2 = this.keepSessionIdAudioTrack;
            if (!(audioTrack2 == null || audioSessionId2 == audioTrack2.getAudioSessionId())) {
                releaseKeepSessionIdAudioTrack();
            }
            if (this.keepSessionIdAudioTrack == null) {
                this.keepSessionIdAudioTrack = initializeKeepSessionIdAudioTrack(audioSessionId2);
            }
        }
        if (this.audioSessionId != audioSessionId2) {
            this.audioSessionId = audioSessionId2;
            AudioSink.Listener listener2 = this.listener;
            if (listener2 != null) {
                listener2.onAudioSessionId(audioSessionId2);
            }
        }
        if (this.configuration.canApplyPlaybackParameters) {
            playbackParameters2 = this.audioProcessorChain.applyPlaybackParameters(this.playbackParameters);
        } else {
            playbackParameters2 = PlaybackParameters.DEFAULT;
        }
        this.playbackParameters = playbackParameters2;
        setupAudioProcessors();
        this.audioTrackPositionTracker.setAudioTrack(this.audioTrack, this.configuration.outputEncoding, this.configuration.outputPcmFrameSize, this.configuration.bufferSize);
        setVolumeInternal();
        if (this.auxEffectInfo.effectId != 0) {
            this.audioTrack.attachAuxEffect(this.auxEffectInfo.effectId);
            this.audioTrack.setAuxEffectSendLevel(this.auxEffectInfo.sendLevel);
        }
    }

    public void play() {
        this.playing = true;
        if (isInitialized()) {
            this.audioTrackPositionTracker.start();
            this.audioTrack.play();
        }
    }

    public void handleDiscontinuity() {
        if (this.startMediaTimeState == 1) {
            this.startMediaTimeState = 2;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    public boolean handleBuffer(ByteBuffer buffer, long presentationTimeUs) throws AudioSink.InitializationException, AudioSink.WriteException {
        String str;
        String str2;
        PlaybackParameters playbackParameters2;
        ByteBuffer byteBuffer = buffer;
        long j = presentationTimeUs;
        ByteBuffer byteBuffer2 = this.inputBuffer;
        Assertions.checkArgument(byteBuffer2 == null || byteBuffer == byteBuffer2);
        if (this.pendingConfiguration != null) {
            if (!drainAudioProcessorsToEndOfStream()) {
                return false;
            }
            this.configuration = this.pendingConfiguration;
            this.pendingConfiguration = null;
            if (this.configuration.canApplyPlaybackParameters) {
                playbackParameters2 = this.audioProcessorChain.applyPlaybackParameters(this.playbackParameters);
            } else {
                playbackParameters2 = PlaybackParameters.DEFAULT;
            }
            this.playbackParameters = playbackParameters2;
            setupAudioProcessors();
        }
        if (!isInitialized()) {
            initialize();
            if (this.playing) {
                play();
            }
        }
        if (!this.audioTrackPositionTracker.mayHandleBuffer(getWrittenFrames())) {
            return false;
        }
        if (this.inputBuffer != null) {
            str = TAG;
        } else if (!buffer.hasRemaining()) {
            return true;
        } else {
            if (!this.configuration.isInputPcm && this.framesPerEncodedSample == 0) {
                this.framesPerEncodedSample = getFramesPerEncodedSample(this.configuration.outputEncoding, byteBuffer);
                if (this.framesPerEncodedSample == 0) {
                    return true;
                }
            }
            if (this.afterDrainPlaybackParameters == null) {
                str2 = TAG;
            } else if (!drainAudioProcessorsToEndOfStream()) {
                return false;
            } else {
                PlaybackParameters newPlaybackParameters = this.afterDrainPlaybackParameters;
                this.afterDrainPlaybackParameters = null;
                PlaybackParameters newPlaybackParameters2 = this.audioProcessorChain.applyPlaybackParameters(newPlaybackParameters);
                ArrayDeque<PlaybackParametersCheckpoint> arrayDeque = this.playbackParametersCheckpoints;
                long max = Math.max(0L, j);
                Configuration configuration2 = this.configuration;
                str2 = TAG;
                PlaybackParametersCheckpoint playbackParametersCheckpoint = r12;
                PlaybackParametersCheckpoint playbackParametersCheckpoint2 = new PlaybackParametersCheckpoint(newPlaybackParameters2, max, configuration2.framesToDurationUs(getWrittenFrames()));
                arrayDeque.add(playbackParametersCheckpoint);
                setupAudioProcessors();
            }
            if (this.startMediaTimeState == 0) {
                this.startMediaTimeUs = Math.max(0L, j);
                this.startMediaTimeState = 1;
                str = str2;
            } else {
                long expectedPresentationTimeUs = this.startMediaTimeUs + this.configuration.inputFramesToDurationUs(getSubmittedFrames() - this.trimmingAudioProcessor.getTrimmedFrameCount());
                if (this.startMediaTimeState != 1) {
                    str = str2;
                } else if (Math.abs(expectedPresentationTimeUs - j) > 200000) {
                    StringBuilder sb = new StringBuilder(80);
                    sb.append("Discontinuity detected [expected ");
                    sb.append(expectedPresentationTimeUs);
                    sb.append(", got ");
                    sb.append(j);
                    sb.append("]");
                    String sb2 = sb.toString();
                    str = str2;
                    Log.m26e(str, sb2);
                    this.startMediaTimeState = 2;
                } else {
                    str = str2;
                }
                if (this.startMediaTimeState == 2) {
                    long adjustmentUs = j - expectedPresentationTimeUs;
                    this.startMediaTimeUs += adjustmentUs;
                    this.startMediaTimeState = 1;
                    AudioSink.Listener listener2 = this.listener;
                    if (!(listener2 == null || adjustmentUs == 0)) {
                        listener2.onPositionDiscontinuity();
                    }
                }
            }
            if (this.configuration.isInputPcm) {
                this.submittedPcmBytes += (long) buffer.remaining();
            } else {
                this.submittedEncodedFrames += (long) this.framesPerEncodedSample;
            }
            this.inputBuffer = byteBuffer;
        }
        if (this.configuration.processingEnabled) {
            processBuffers(j);
        } else {
            writeBuffer(this.inputBuffer, j);
        }
        if (!this.inputBuffer.hasRemaining()) {
            this.inputBuffer = null;
            return true;
        } else if (!this.audioTrackPositionTracker.isStalled(getWrittenFrames())) {
            return false;
        } else {
            Log.m30w(str, "Resetting stalled audio track");
            flush();
            return true;
        }
    }

    private void processBuffers(long avSyncPresentationTimeUs) throws AudioSink.WriteException {
        ByteBuffer input;
        int count = this.activeAudioProcessors.length;
        int index = count;
        while (index >= 0) {
            if (index > 0) {
                input = this.outputBuffers[index - 1];
            } else {
                input = this.inputBuffer;
                if (input == null) {
                    input = AudioProcessor.EMPTY_BUFFER;
                }
            }
            if (index == count) {
                writeBuffer(input, avSyncPresentationTimeUs);
            } else {
                AudioProcessor audioProcessor = this.activeAudioProcessors[index];
                audioProcessor.queueInput(input);
                ByteBuffer output = audioProcessor.getOutput();
                this.outputBuffers[index] = output;
                if (output.hasRemaining()) {
                    index++;
                }
            }
            if (!input.hasRemaining()) {
                index--;
            } else {
                return;
            }
        }
    }

    private void writeBuffer(ByteBuffer buffer, long avSyncPresentationTimeUs) throws AudioSink.WriteException {
        if (buffer.hasRemaining()) {
            ByteBuffer byteBuffer = this.outputBuffer;
            boolean z = true;
            if (byteBuffer != null) {
                Assertions.checkArgument(byteBuffer == buffer);
            } else {
                this.outputBuffer = buffer;
                if (Util.SDK_INT < 21) {
                    int bytesRemaining = buffer.remaining();
                    byte[] bArr = this.preV21OutputBuffer;
                    if (bArr == null || bArr.length < bytesRemaining) {
                        this.preV21OutputBuffer = new byte[bytesRemaining];
                    }
                    int originalPosition = buffer.position();
                    buffer.get(this.preV21OutputBuffer, 0, bytesRemaining);
                    buffer.position(originalPosition);
                    this.preV21OutputBufferOffset = 0;
                }
            }
            int bytesRemaining2 = buffer.remaining();
            int bytesWritten = 0;
            if (Util.SDK_INT < 21) {
                int bytesToWrite = this.audioTrackPositionTracker.getAvailableBufferSize(this.writtenPcmBytes);
                if (bytesToWrite > 0 && (bytesWritten = this.audioTrack.write(this.preV21OutputBuffer, this.preV21OutputBufferOffset, Math.min(bytesRemaining2, bytesToWrite))) > 0) {
                    this.preV21OutputBufferOffset += bytesWritten;
                    buffer.position(buffer.position() + bytesWritten);
                }
            } else if (this.tunneling) {
                if (avSyncPresentationTimeUs == C0841C.TIME_UNSET) {
                    z = false;
                }
                Assertions.checkState(z);
                bytesWritten = writeNonBlockingWithAvSyncV21(this.audioTrack, buffer, bytesRemaining2, avSyncPresentationTimeUs);
            } else {
                bytesWritten = writeNonBlockingV21(this.audioTrack, buffer, bytesRemaining2);
            }
            this.lastFeedElapsedRealtimeMs = SystemClock.elapsedRealtime();
            if (bytesWritten >= 0) {
                if (this.configuration.isInputPcm) {
                    this.writtenPcmBytes += (long) bytesWritten;
                }
                if (bytesWritten == bytesRemaining2) {
                    if (!this.configuration.isInputPcm) {
                        this.writtenEncodedFrames += (long) this.framesPerEncodedSample;
                    }
                    this.outputBuffer = null;
                    return;
                }
                return;
            }
            throw new AudioSink.WriteException(bytesWritten);
        }
    }

    public void playToEndOfStream() throws AudioSink.WriteException {
        if (!this.handledEndOfStream && isInitialized() && drainAudioProcessorsToEndOfStream()) {
            this.audioTrackPositionTracker.handleEndOfStream(getWrittenFrames());
            this.audioTrack.stop();
            this.bytesUntilNextAvSync = 0;
            this.handledEndOfStream = true;
        }
    }

    private boolean drainAudioProcessorsToEndOfStream() throws AudioSink.WriteException {
        boolean audioProcessorNeedsEndOfStream = false;
        if (this.drainingAudioProcessorIndex == -1) {
            this.drainingAudioProcessorIndex = this.configuration.processingEnabled ? 0 : this.activeAudioProcessors.length;
            audioProcessorNeedsEndOfStream = true;
        }
        while (true) {
            int i = this.drainingAudioProcessorIndex;
            AudioProcessor[] audioProcessorArr = this.activeAudioProcessors;
            if (i < audioProcessorArr.length) {
                AudioProcessor audioProcessor = audioProcessorArr[i];
                if (audioProcessorNeedsEndOfStream) {
                    audioProcessor.queueEndOfStream();
                }
                processBuffers(C0841C.TIME_UNSET);
                if (!audioProcessor.isEnded()) {
                    return false;
                }
                audioProcessorNeedsEndOfStream = true;
                this.drainingAudioProcessorIndex++;
            } else {
                ByteBuffer byteBuffer = this.outputBuffer;
                if (byteBuffer != null) {
                    writeBuffer(byteBuffer, C0841C.TIME_UNSET);
                    if (this.outputBuffer != null) {
                        return false;
                    }
                }
                this.drainingAudioProcessorIndex = -1;
                return true;
            }
        }
    }

    public boolean isEnded() {
        return !isInitialized() || (this.handledEndOfStream && !hasPendingData());
    }

    public boolean hasPendingData() {
        return isInitialized() && this.audioTrackPositionTracker.hasPendingData(getWrittenFrames());
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters2) {
        Configuration configuration2 = this.configuration;
        if (configuration2 == null || configuration2.canApplyPlaybackParameters) {
            PlaybackParameters lastSetPlaybackParameters = this.afterDrainPlaybackParameters;
            if (lastSetPlaybackParameters == null) {
                if (!this.playbackParametersCheckpoints.isEmpty()) {
                    lastSetPlaybackParameters = this.playbackParametersCheckpoints.getLast().playbackParameters;
                } else {
                    lastSetPlaybackParameters = this.playbackParameters;
                }
            }
            if (!playbackParameters2.equals(lastSetPlaybackParameters)) {
                if (isInitialized()) {
                    this.afterDrainPlaybackParameters = playbackParameters2;
                } else {
                    this.playbackParameters = this.audioProcessorChain.applyPlaybackParameters(playbackParameters2);
                }
            }
            return this.playbackParameters;
        }
        this.playbackParameters = PlaybackParameters.DEFAULT;
        return this.playbackParameters;
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public void setAudioAttributes(AudioAttributes audioAttributes2) {
        if (!this.audioAttributes.equals(audioAttributes2)) {
            this.audioAttributes = audioAttributes2;
            if (!this.tunneling) {
                flush();
                this.audioSessionId = 0;
            }
        }
    }

    public void setAudioSessionId(int audioSessionId2) {
        if (this.audioSessionId != audioSessionId2) {
            this.audioSessionId = audioSessionId2;
            flush();
        }
    }

    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo2) {
        if (!this.auxEffectInfo.equals(auxEffectInfo2)) {
            int effectId = auxEffectInfo2.effectId;
            float sendLevel = auxEffectInfo2.sendLevel;
            if (this.audioTrack != null) {
                if (this.auxEffectInfo.effectId != effectId) {
                    this.audioTrack.attachAuxEffect(effectId);
                }
                if (effectId != 0) {
                    this.audioTrack.setAuxEffectSendLevel(sendLevel);
                }
            }
            this.auxEffectInfo = auxEffectInfo2;
        }
    }

    public void enableTunnelingV21(int tunnelingAudioSessionId) {
        Assertions.checkState(Util.SDK_INT >= 21);
        if (!this.tunneling || this.audioSessionId != tunnelingAudioSessionId) {
            this.tunneling = true;
            this.audioSessionId = tunnelingAudioSessionId;
            flush();
        }
    }

    public void disableTunneling() {
        if (this.tunneling) {
            this.tunneling = false;
            this.audioSessionId = 0;
            flush();
        }
    }

    public void setVolume(float volume2) {
        if (this.volume != volume2) {
            this.volume = volume2;
            setVolumeInternal();
        }
    }

    private void setVolumeInternal() {
        if (isInitialized()) {
            if (Util.SDK_INT >= 21) {
                setVolumeInternalV21(this.audioTrack, this.volume);
            } else {
                setVolumeInternalV3(this.audioTrack, this.volume);
            }
        }
    }

    public void pause() {
        this.playing = false;
        if (isInitialized() && this.audioTrackPositionTracker.pause()) {
            this.audioTrack.pause();
        }
    }

    public void flush() {
        if (isInitialized()) {
            this.submittedPcmBytes = 0;
            this.submittedEncodedFrames = 0;
            this.writtenPcmBytes = 0;
            this.writtenEncodedFrames = 0;
            this.framesPerEncodedSample = 0;
            PlaybackParameters playbackParameters2 = this.afterDrainPlaybackParameters;
            if (playbackParameters2 != null) {
                this.playbackParameters = playbackParameters2;
                this.afterDrainPlaybackParameters = null;
            } else if (!this.playbackParametersCheckpoints.isEmpty()) {
                this.playbackParameters = this.playbackParametersCheckpoints.getLast().playbackParameters;
            }
            this.playbackParametersCheckpoints.clear();
            this.playbackParametersOffsetUs = 0;
            this.playbackParametersPositionUs = 0;
            this.trimmingAudioProcessor.resetTrimmedFrameCount();
            flushAudioProcessors();
            this.inputBuffer = null;
            this.outputBuffer = null;
            this.handledEndOfStream = false;
            this.drainingAudioProcessorIndex = -1;
            this.avSyncHeader = null;
            this.bytesUntilNextAvSync = 0;
            this.startMediaTimeState = 0;
            if (this.audioTrackPositionTracker.isPlaying()) {
                this.audioTrack.pause();
            }
            final AudioTrack toRelease = this.audioTrack;
            this.audioTrack = null;
            Configuration configuration2 = this.pendingConfiguration;
            if (configuration2 != null) {
                this.configuration = configuration2;
                this.pendingConfiguration = null;
            }
            this.audioTrackPositionTracker.reset();
            this.releasingConditionVariable.close();
            new Thread() {
                public void run() {
                    try {
                        toRelease.flush();
                        toRelease.release();
                    } finally {
                        DefaultAudioSink.this.releasingConditionVariable.open();
                    }
                }
            }.start();
        }
    }

    public void reset() {
        flush();
        releaseKeepSessionIdAudioTrack();
        for (AudioProcessor audioProcessor : this.toIntPcmAvailableAudioProcessors) {
            audioProcessor.reset();
        }
        for (AudioProcessor audioProcessor2 : this.toFloatPcmAvailableAudioProcessors) {
            audioProcessor2.reset();
        }
        this.audioSessionId = 0;
        this.playing = false;
    }

    private void releaseKeepSessionIdAudioTrack() {
        if (this.keepSessionIdAudioTrack != null) {
            final AudioTrack toRelease = this.keepSessionIdAudioTrack;
            this.keepSessionIdAudioTrack = null;
            new Thread(this) {
                public void run() {
                    toRelease.release();
                }
            }.start();
        }
    }

    private long applySpeedup(long positionUs) {
        PlaybackParametersCheckpoint checkpoint = null;
        while (!this.playbackParametersCheckpoints.isEmpty() && positionUs >= this.playbackParametersCheckpoints.getFirst().positionUs) {
            checkpoint = this.playbackParametersCheckpoints.remove();
        }
        if (checkpoint != null) {
            this.playbackParameters = checkpoint.playbackParameters;
            this.playbackParametersPositionUs = checkpoint.positionUs;
            this.playbackParametersOffsetUs = checkpoint.mediaTimeUs - this.startMediaTimeUs;
        }
        if (this.playbackParameters.speed == 1.0f) {
            return (this.playbackParametersOffsetUs + positionUs) - this.playbackParametersPositionUs;
        }
        if (this.playbackParametersCheckpoints.isEmpty()) {
            return this.playbackParametersOffsetUs + this.audioProcessorChain.getMediaDuration(positionUs - this.playbackParametersPositionUs);
        }
        return this.playbackParametersOffsetUs + Util.getMediaDurationForPlayoutDuration(positionUs - this.playbackParametersPositionUs, this.playbackParameters.speed);
    }

    private long applySkipping(long positionUs) {
        return this.configuration.framesToDurationUs(this.audioProcessorChain.getSkippedOutputFrameCount()) + positionUs;
    }

    private boolean isInitialized() {
        return this.audioTrack != null;
    }

    /* access modifiers changed from: private */
    public long getSubmittedFrames() {
        if (this.configuration.isInputPcm) {
            return this.submittedPcmBytes / ((long) this.configuration.inputPcmFrameSize);
        }
        return this.submittedEncodedFrames;
    }

    /* access modifiers changed from: private */
    public long getWrittenFrames() {
        if (this.configuration.isInputPcm) {
            return this.writtenPcmBytes / ((long) this.configuration.outputPcmFrameSize);
        }
        return this.writtenEncodedFrames;
    }

    @TargetApi(21)
    private int writeNonBlockingWithAvSyncV21(AudioTrack audioTrack2, ByteBuffer buffer, int size, long presentationTimeUs) {
        if (this.avSyncHeader == null) {
            this.avSyncHeader = ByteBuffer.allocate(16);
            this.avSyncHeader.order(ByteOrder.BIG_ENDIAN);
            this.avSyncHeader.putInt(1431633921);
        }
        if (this.bytesUntilNextAvSync == 0) {
            this.avSyncHeader.putInt(4, size);
            this.avSyncHeader.putLong(8, 1000 * presentationTimeUs);
            this.avSyncHeader.position(0);
            this.bytesUntilNextAvSync = size;
        }
        int avSyncHeaderBytesRemaining = this.avSyncHeader.remaining();
        if (avSyncHeaderBytesRemaining > 0) {
            int result = audioTrack2.write(this.avSyncHeader, avSyncHeaderBytesRemaining, 1);
            if (result < 0) {
                this.bytesUntilNextAvSync = 0;
                return result;
            } else if (result < avSyncHeaderBytesRemaining) {
                return 0;
            }
        }
        int result2 = writeNonBlockingV21(audioTrack2, buffer, size);
        if (result2 < 0) {
            this.bytesUntilNextAvSync = 0;
            return result2;
        }
        this.bytesUntilNextAvSync -= result2;
        return result2;
    }

    public interface AudioProcessorChain {
        PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

        AudioProcessor[] getAudioProcessors();

        long getMediaDuration(long j);

        long getSkippedOutputFrameCount();
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface StartMediaTimeState {
    }

    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        private InvalidAudioTrackTimestampException(String message) {
            super(message);
        }
    }

    public static class DefaultAudioProcessorChain implements AudioProcessorChain {
        private final AudioProcessor[] audioProcessors;
        private final SilenceSkippingAudioProcessor silenceSkippingAudioProcessor = new SilenceSkippingAudioProcessor();
        private final SonicAudioProcessor sonicAudioProcessor = new SonicAudioProcessor();

        public DefaultAudioProcessorChain(AudioProcessor... audioProcessors2) {
            this.audioProcessors = (AudioProcessor[]) Arrays.copyOf(audioProcessors2, audioProcessors2.length + 2);
            AudioProcessor[] audioProcessorArr = this.audioProcessors;
            audioProcessorArr[audioProcessors2.length] = this.silenceSkippingAudioProcessor;
            audioProcessorArr[audioProcessors2.length + 1] = this.sonicAudioProcessor;
        }

        public AudioProcessor[] getAudioProcessors() {
            return this.audioProcessors;
        }

        public PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters) {
            this.silenceSkippingAudioProcessor.setEnabled(playbackParameters.skipSilence);
            return new PlaybackParameters(this.sonicAudioProcessor.setSpeed(playbackParameters.speed), this.sonicAudioProcessor.setPitch(playbackParameters.pitch), playbackParameters.skipSilence);
        }

        public long getMediaDuration(long playoutDuration) {
            return this.sonicAudioProcessor.scaleDurationForSpeedup(playoutDuration);
        }

        public long getSkippedOutputFrameCount() {
            return this.silenceSkippingAudioProcessor.getSkippedFrames();
        }
    }

    private static final class PlaybackParametersCheckpoint {
        /* access modifiers changed from: private */
        public final long mediaTimeUs;
        /* access modifiers changed from: private */
        public final PlaybackParameters playbackParameters;
        /* access modifiers changed from: private */
        public final long positionUs;

        private PlaybackParametersCheckpoint(PlaybackParameters playbackParameters2, long mediaTimeUs2, long positionUs2) {
            this.playbackParameters = playbackParameters2;
            this.mediaTimeUs = mediaTimeUs2;
            this.positionUs = positionUs2;
        }
    }

    private static final class Configuration {
        public final AudioProcessor[] availableAudioProcessors;
        public final int bufferSize;
        public final boolean canApplyPlaybackParameters;
        public final int inputPcmFrameSize;
        public final int inputSampleRate;
        public final boolean isInputPcm;
        public final int outputChannelConfig;
        public final int outputEncoding;
        public final int outputPcmFrameSize;
        public final int outputSampleRate;
        public final boolean processingEnabled;

        public Configuration(boolean isInputPcm2, int inputPcmFrameSize2, int inputSampleRate2, int outputPcmFrameSize2, int outputSampleRate2, int outputChannelConfig2, int outputEncoding2, int specifiedBufferSize, boolean processingEnabled2, boolean canApplyPlaybackParameters2, AudioProcessor[] availableAudioProcessors2) {
            this.isInputPcm = isInputPcm2;
            this.inputPcmFrameSize = inputPcmFrameSize2;
            this.inputSampleRate = inputSampleRate2;
            this.outputPcmFrameSize = outputPcmFrameSize2;
            this.outputSampleRate = outputSampleRate2;
            this.outputChannelConfig = outputChannelConfig2;
            this.outputEncoding = outputEncoding2;
            this.bufferSize = specifiedBufferSize != 0 ? specifiedBufferSize : getDefaultBufferSize();
            this.processingEnabled = processingEnabled2;
            this.canApplyPlaybackParameters = canApplyPlaybackParameters2;
            this.availableAudioProcessors = availableAudioProcessors2;
        }

        public boolean canReuseAudioTrack(Configuration audioTrackConfiguration) {
            return audioTrackConfiguration.outputEncoding == this.outputEncoding && audioTrackConfiguration.outputSampleRate == this.outputSampleRate && audioTrackConfiguration.outputChannelConfig == this.outputChannelConfig;
        }

        public long inputFramesToDurationUs(long frameCount) {
            return (1000000 * frameCount) / ((long) this.inputSampleRate);
        }

        public long framesToDurationUs(long frameCount) {
            return (1000000 * frameCount) / ((long) this.outputSampleRate);
        }

        public long durationUsToFrames(long durationUs) {
            return (((long) this.outputSampleRate) * durationUs) / 1000000;
        }

        public AudioTrack buildAudioTrack(boolean tunneling, AudioAttributes audioAttributes, int audioSessionId) throws AudioSink.InitializationException {
            AudioTrack audioTrack;
            if (Util.SDK_INT >= 21) {
                audioTrack = createAudioTrackV21(tunneling, audioAttributes, audioSessionId);
            } else {
                int streamType = Util.getStreamTypeForAudioUsage(audioAttributes.usage);
                if (audioSessionId == 0) {
                    audioTrack = new AudioTrack(streamType, this.outputSampleRate, this.outputChannelConfig, this.outputEncoding, this.bufferSize, 1);
                } else {
                    audioTrack = new AudioTrack(streamType, this.outputSampleRate, this.outputChannelConfig, this.outputEncoding, this.bufferSize, 1, audioSessionId);
                }
            }
            int state = audioTrack.getState();
            if (state == 1) {
                return audioTrack;
            }
            try {
                audioTrack.release();
            } catch (Exception e) {
            }
            throw new AudioSink.InitializationException(state, this.outputSampleRate, this.outputChannelConfig, this.bufferSize);
        }

        @TargetApi(21)
        private AudioTrack createAudioTrackV21(boolean tunneling, AudioAttributes audioAttributes, int audioSessionId) {
            AudioAttributes attributes;
            int i;
            if (tunneling) {
                attributes = new AudioAttributes.Builder().setContentType(3).setFlags(16).setUsage(1).build();
            } else {
                attributes = audioAttributes.getAudioAttributesV21();
            }
            AudioFormat format = new AudioFormat.Builder().setChannelMask(this.outputChannelConfig).setEncoding(this.outputEncoding).setSampleRate(this.outputSampleRate).build();
            int i2 = this.bufferSize;
            if (audioSessionId != 0) {
                i = audioSessionId;
            } else {
                i = 0;
            }
            return new AudioTrack(attributes, format, i2, 1, i);
        }

        private int getDefaultBufferSize() {
            if (this.isInputPcm) {
                int minBufferSize = AudioTrack.getMinBufferSize(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding);
                Assertions.checkState(minBufferSize != -2);
                return Util.constrainValue(minBufferSize * 4, ((int) durationUsToFrames(250000)) * this.outputPcmFrameSize, (int) Math.max((long) minBufferSize, durationUsToFrames(DefaultAudioSink.MAX_BUFFER_DURATION_US) * ((long) this.outputPcmFrameSize)));
            }
            int rate = DefaultAudioSink.getMaximumEncodedRateBytesPerSecond(this.outputEncoding);
            if (this.outputEncoding == 5) {
                rate *= 2;
            }
            return (int) ((((long) rate) * 250000) / 1000000);
        }
    }

    private final class PositionTrackerListener implements AudioTrackPositionTracker.Listener {
        private PositionTrackerListener() {
        }

        public void onPositionFramesMismatch(long audioTimestampPositionFrames, long audioTimestampSystemTimeUs, long systemTimeUs, long playbackPositionUs) {
            long access$600 = DefaultAudioSink.this.getSubmittedFrames();
            long access$700 = DefaultAudioSink.this.getWrittenFrames();
            StringBuilder sb = new StringBuilder((int) ClientAnalytics.LogRequest.LogSource.NOTIFICATION_STATS_VALUE);
            sb.append("Spurious audio timestamp (frame position mismatch): ");
            sb.append(audioTimestampPositionFrames);
            sb.append(", ");
            sb.append(audioTimestampSystemTimeUs);
            sb.append(", ");
            sb.append(systemTimeUs);
            sb.append(", ");
            sb.append(playbackPositionUs);
            sb.append(", ");
            sb.append(access$600);
            sb.append(", ");
            sb.append(access$700);
            String message = sb.toString();
            if (!DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                Log.m30w(DefaultAudioSink.TAG, message);
                return;
            }
            throw new InvalidAudioTrackTimestampException(message);
        }

        public void onSystemTimeUsMismatch(long audioTimestampPositionFrames, long audioTimestampSystemTimeUs, long systemTimeUs, long playbackPositionUs) {
            long access$600 = DefaultAudioSink.this.getSubmittedFrames();
            long access$700 = DefaultAudioSink.this.getWrittenFrames();
            StringBuilder sb = new StringBuilder((int) ClientAnalytics.LogRequest.LogSource.INBOX_ANDROID_PRIMES_VALUE);
            sb.append("Spurious audio timestamp (system clock mismatch): ");
            sb.append(audioTimestampPositionFrames);
            sb.append(", ");
            sb.append(audioTimestampSystemTimeUs);
            sb.append(", ");
            sb.append(systemTimeUs);
            sb.append(", ");
            sb.append(playbackPositionUs);
            sb.append(", ");
            sb.append(access$600);
            sb.append(", ");
            sb.append(access$700);
            String message = sb.toString();
            if (!DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                Log.m30w(DefaultAudioSink.TAG, message);
                return;
            }
            throw new InvalidAudioTrackTimestampException(message);
        }

        public void onInvalidLatency(long latencyUs) {
            StringBuilder sb = new StringBuilder(61);
            sb.append("Ignoring impossibly large audio latency: ");
            sb.append(latencyUs);
            Log.m30w(DefaultAudioSink.TAG, sb.toString());
        }

        public void onUnderrun(int bufferSize, long bufferSizeMs) {
            if (DefaultAudioSink.this.listener != null) {
                DefaultAudioSink.this.listener.onUnderrun(bufferSize, bufferSizeMs, SystemClock.elapsedRealtime() - DefaultAudioSink.this.lastFeedElapsedRealtimeMs);
            }
        }
    }
}
