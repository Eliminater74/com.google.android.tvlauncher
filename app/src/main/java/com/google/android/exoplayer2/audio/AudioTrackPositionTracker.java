package com.google.android.exoplayer2.audio;

import android.media.AudioTrack;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

final class AudioTrackPositionTracker {
    private static final long FORCE_RESET_WORKAROUND_TIMEOUT_MS = 200;
    private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000;
    private static final long MAX_LATENCY_US = 5000000;
    private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
    private static final int MIN_LATENCY_SAMPLE_INTERVAL_US = 500000;
    private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
    private static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PLAYING = 3;
    private static final int PLAYSTATE_STOPPED = 1;
    private final Listener listener;
    private final long[] playheadOffsets;
    @Nullable
    private AudioTimestampPoller audioTimestampPoller;
    @Nullable
    private AudioTrack audioTrack;
    private int bufferSize;
    private long bufferSizeUs;
    private long endPlaybackHeadPosition;
    private long forceResetWorkaroundTimeMs;
    @Nullable
    private Method getLatencyMethod;
    private boolean hasData;
    private boolean isOutputPcm;
    private long lastLatencySampleTimeUs;
    private long lastPlayheadSampleTimeUs;
    private long lastRawPlaybackHeadPosition;
    private long latencyUs;
    private boolean needsPassthroughWorkarounds;
    private int nextPlayheadOffsetIndex;
    private int outputPcmFrameSize;
    private int outputSampleRate;
    private long passthroughWorkaroundPauseOffset;
    private int playheadOffsetCount;
    private long rawPlaybackHeadWrapCount;
    private long smoothedPlayheadOffsetUs;
    private long stopPlaybackHeadPosition;
    private long stopTimestampUs;

    public AudioTrackPositionTracker(Listener listener2) {
        this.listener = (Listener) Assertions.checkNotNull(listener2);
        if (Util.SDK_INT >= 18) {
            try {
                this.getLatencyMethod = AudioTrack.class.getMethod("getLatency", null);
            } catch (NoSuchMethodException e) {
            }
        }
        this.playheadOffsets = new long[10];
    }

    private static boolean needsPassthroughWorkarounds(int outputEncoding) {
        return Util.SDK_INT < 23 && (outputEncoding == 5 || outputEncoding == 6);
    }

    public void setAudioTrack(AudioTrack audioTrack2, int outputEncoding, int outputPcmFrameSize2, int bufferSize2) {
        this.audioTrack = audioTrack2;
        this.outputPcmFrameSize = outputPcmFrameSize2;
        this.bufferSize = bufferSize2;
        this.audioTimestampPoller = new AudioTimestampPoller(audioTrack2);
        this.outputSampleRate = audioTrack2.getSampleRate();
        this.needsPassthroughWorkarounds = needsPassthroughWorkarounds(outputEncoding);
        this.isOutputPcm = Util.isEncodingLinearPcm(outputEncoding);
        this.bufferSizeUs = this.isOutputPcm ? framesToDurationUs((long) (bufferSize2 / outputPcmFrameSize2)) : -9223372036854775807L;
        this.lastRawPlaybackHeadPosition = 0;
        this.rawPlaybackHeadWrapCount = 0;
        this.passthroughWorkaroundPauseOffset = 0;
        this.hasData = false;
        this.stopTimestampUs = C0841C.TIME_UNSET;
        this.forceResetWorkaroundTimeMs = C0841C.TIME_UNSET;
        this.latencyUs = 0;
    }

    public long getCurrentPositionUs(boolean sourceEnded) {
        long positionUs;
        if (((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3) {
            maybeSampleSyncParams();
        }
        long systemTimeUs = System.nanoTime() / 1000;
        AudioTimestampPoller audioTimestampPoller2 = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        if (audioTimestampPoller2.hasTimestamp()) {
            long timestampPositionUs = framesToDurationUs(audioTimestampPoller2.getTimestampPositionFrames());
            if (!audioTimestampPoller2.isTimestampAdvancing()) {
                return timestampPositionUs;
            }
            return timestampPositionUs + (systemTimeUs - audioTimestampPoller2.getTimestampSystemTimeUs());
        }
        if (this.playheadOffsetCount == 0) {
            positionUs = getPlaybackHeadPositionUs();
        } else {
            positionUs = this.smoothedPlayheadOffsetUs + systemTimeUs;
        }
        if (!sourceEnded) {
            return positionUs - this.latencyUs;
        }
        return positionUs;
    }

    public void start() {
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
    }

    public boolean isPlaying() {
        return ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3;
    }

    public boolean mayHandleBuffer(long writtenFrames) {
        Listener listener2;
        int playState = ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState();
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2) {
                this.hasData = false;
                return false;
            } else if (playState == 1 && getPlaybackHeadPosition() == 0) {
                return false;
            }
        }
        boolean hadData = this.hasData;
        this.hasData = hasPendingData(writtenFrames);
        if (hadData && !this.hasData && playState != 1 && (listener2 = this.listener) != null) {
            listener2.onUnderrun(this.bufferSize, C0841C.usToMs(this.bufferSizeUs));
        }
        return true;
    }

    public int getAvailableBufferSize(long writtenBytes) {
        return this.bufferSize - ((int) (writtenBytes - (getPlaybackHeadPosition() * ((long) this.outputPcmFrameSize))));
    }

    public boolean isStalled(long writtenFrames) {
        return this.forceResetWorkaroundTimeMs != C0841C.TIME_UNSET && writtenFrames > 0 && SystemClock.elapsedRealtime() - this.forceResetWorkaroundTimeMs >= FORCE_RESET_WORKAROUND_TIMEOUT_MS;
    }

    public void handleEndOfStream(long writtenFrames) {
        this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
        this.stopTimestampUs = SystemClock.elapsedRealtime() * 1000;
        this.endPlaybackHeadPosition = writtenFrames;
    }

    public boolean hasPendingData(long writtenFrames) {
        return writtenFrames > getPlaybackHeadPosition() || forceHasPendingData();
    }

    public boolean pause() {
        resetSyncParams();
        if (this.stopTimestampUs != C0841C.TIME_UNSET) {
            return false;
        }
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
        return true;
    }

    public void reset() {
        resetSyncParams();
        this.audioTrack = null;
        this.audioTimestampPoller = null;
    }

    private void maybeSampleSyncParams() {
        long playbackPositionUs = getPlaybackHeadPositionUs();
        if (playbackPositionUs != 0) {
            long systemTimeUs = System.nanoTime() / 1000;
            if (systemTimeUs - this.lastPlayheadSampleTimeUs >= 30000) {
                long[] jArr = this.playheadOffsets;
                int i = this.nextPlayheadOffsetIndex;
                jArr[i] = playbackPositionUs - systemTimeUs;
                this.nextPlayheadOffsetIndex = (i + 1) % 10;
                int i2 = this.playheadOffsetCount;
                if (i2 < 10) {
                    this.playheadOffsetCount = i2 + 1;
                }
                this.lastPlayheadSampleTimeUs = systemTimeUs;
                this.smoothedPlayheadOffsetUs = 0;
                int i3 = 0;
                while (true) {
                    int i4 = this.playheadOffsetCount;
                    if (i3 >= i4) {
                        break;
                    }
                    this.smoothedPlayheadOffsetUs += this.playheadOffsets[i3] / ((long) i4);
                    i3++;
                }
            }
            if (this.needsPassthroughWorkarounds == 0) {
                maybePollAndCheckTimestamp(systemTimeUs, playbackPositionUs);
                maybeUpdateLatency(systemTimeUs);
            }
        }
    }

    private void maybePollAndCheckTimestamp(long systemTimeUs, long playbackPositionUs) {
        long j = systemTimeUs;
        AudioTimestampPoller audioTimestampPoller2 = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        if (audioTimestampPoller2.maybePollTimestamp(j)) {
            long audioTimestampSystemTimeUs = audioTimestampPoller2.getTimestampSystemTimeUs();
            long audioTimestampPositionFrames = audioTimestampPoller2.getTimestampPositionFrames();
            if (Math.abs(audioTimestampSystemTimeUs - j) > 5000000) {
                this.listener.onSystemTimeUsMismatch(audioTimestampPositionFrames, audioTimestampSystemTimeUs, systemTimeUs, playbackPositionUs);
                audioTimestampPoller2.rejectTimestamp();
                return;
            }
            long audioTimestampPositionFrames2 = audioTimestampPositionFrames;
            if (Math.abs(framesToDurationUs(audioTimestampPositionFrames2) - playbackPositionUs) > 5000000) {
                this.listener.onPositionFramesMismatch(audioTimestampPositionFrames2, audioTimestampSystemTimeUs, systemTimeUs, playbackPositionUs);
                audioTimestampPoller2.rejectTimestamp();
                return;
            }
            audioTimestampPoller2.acceptTimestamp();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    private void maybeUpdateLatency(long systemTimeUs) {
        Method method;
        if (this.isOutputPcm && (method = this.getLatencyMethod) != null && systemTimeUs - this.lastLatencySampleTimeUs >= 500000) {
            try {
                this.latencyUs = (((long) ((Integer) Util.castNonNull((Integer) method.invoke(Assertions.checkNotNull(this.audioTrack), new Object[0]))).intValue()) * 1000) - this.bufferSizeUs;
                this.latencyUs = Math.max(this.latencyUs, 0L);
                if (this.latencyUs > 5000000) {
                    this.listener.onInvalidLatency(this.latencyUs);
                    this.latencyUs = 0;
                }
            } catch (Exception e) {
                this.getLatencyMethod = null;
            }
            this.lastLatencySampleTimeUs = systemTimeUs;
        }
    }

    private long framesToDurationUs(long frameCount) {
        return (1000000 * frameCount) / ((long) this.outputSampleRate);
    }

    private void resetSyncParams() {
        this.smoothedPlayheadOffsetUs = 0;
        this.playheadOffsetCount = 0;
        this.nextPlayheadOffsetIndex = 0;
        this.lastPlayheadSampleTimeUs = 0;
    }

    private boolean forceHasPendingData() {
        return this.needsPassthroughWorkarounds && ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 2 && getPlaybackHeadPosition() == 0;
    }

    private long getPlaybackHeadPositionUs() {
        return framesToDurationUs(getPlaybackHeadPosition());
    }

    /* JADX INFO: Multiple debug info for r1v1 int: [D('elapsedTimeSinceStopUs' long), D('state' int)] */
    private long getPlaybackHeadPosition() {
        AudioTrack audioTrack2 = (AudioTrack) Assertions.checkNotNull(this.audioTrack);
        if (this.stopTimestampUs != C0841C.TIME_UNSET) {
            return Math.min(this.endPlaybackHeadPosition, this.stopPlaybackHeadPosition + ((((long) this.outputSampleRate) * ((SystemClock.elapsedRealtime() * 1000) - this.stopTimestampUs)) / 1000000));
        }
        int state = audioTrack2.getPlayState();
        if (state == 1) {
            return 0;
        }
        long rawPlaybackHeadPosition = 4294967295L & ((long) audioTrack2.getPlaybackHeadPosition());
        if (this.needsPassthroughWorkarounds) {
            if (state == 2 && rawPlaybackHeadPosition == 0) {
                this.passthroughWorkaroundPauseOffset = this.lastRawPlaybackHeadPosition;
            }
            rawPlaybackHeadPosition += this.passthroughWorkaroundPauseOffset;
        }
        if (Util.SDK_INT <= 29) {
            if (rawPlaybackHeadPosition == 0 && this.lastRawPlaybackHeadPosition > 0 && state == 3) {
                if (this.forceResetWorkaroundTimeMs == C0841C.TIME_UNSET) {
                    this.forceResetWorkaroundTimeMs = SystemClock.elapsedRealtime();
                }
                return this.lastRawPlaybackHeadPosition;
            }
            this.forceResetWorkaroundTimeMs = C0841C.TIME_UNSET;
        }
        if (this.lastRawPlaybackHeadPosition > rawPlaybackHeadPosition) {
            this.rawPlaybackHeadWrapCount++;
        }
        this.lastRawPlaybackHeadPosition = rawPlaybackHeadPosition;
        return (this.rawPlaybackHeadWrapCount << 32) + rawPlaybackHeadPosition;
    }

    public interface Listener {
        void onInvalidLatency(long j);

        void onPositionFramesMismatch(long j, long j2, long j3, long j4);

        void onSystemTimeUsMismatch(long j, long j2, long j3, long j4);

        void onUnderrun(int i, long j);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface PlayState {
    }
}
