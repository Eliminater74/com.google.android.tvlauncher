package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.PlaybackParameters;

import java.nio.ByteBuffer;

public interface AudioSink {
    public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;

    void configure(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, int i6) throws ConfigurationException;

    void disableTunneling();

    void enableTunnelingV21(int i);

    void flush();

    long getCurrentPositionUs(boolean z);

    PlaybackParameters getPlaybackParameters();

    boolean handleBuffer(ByteBuffer byteBuffer, long j) throws InitializationException, WriteException;

    void handleDiscontinuity();

    boolean hasPendingData();

    boolean isEnded();

    void pause();

    void play();

    void playToEndOfStream() throws WriteException;

    void reset();

    void setAudioAttributes(AudioAttributes audioAttributes);

    void setAudioSessionId(int i);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setListener(Listener listener);

    PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters);

    void setVolume(float f);

    boolean supportsOutput(int i, int i2);

    public interface Listener {
        void onAudioSessionId(int i);

        void onPositionDiscontinuity();

        void onUnderrun(int i, long j, long j2);
    }

    public static final class ConfigurationException extends Exception {
        public ConfigurationException(Throwable cause) {
            super(cause);
        }

        public ConfigurationException(String message) {
            super(message);
        }
    }

    public static final class InitializationException extends Exception {
        public final int audioTrackState;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public InitializationException(int r3, int r4, int r5, int r6) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 82
                r0.<init>(r1)
                java.lang.String r1 = "AudioTrack init failed: "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = ", Config("
                r0.append(r1)
                r0.append(r4)
                java.lang.String r1 = ", "
                r0.append(r1)
                r0.append(r5)
                r0.append(r1)
                r0.append(r6)
                java.lang.String r1 = ")"
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                r2.audioTrackState = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioSink.InitializationException.<init>(int, int, int, int):void");
        }
    }

    public static final class WriteException extends Exception {
        public final int errorCode;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public WriteException(int r3) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 36
                r0.<init>(r1)
                java.lang.String r1 = "AudioTrack write failed: "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                r2.errorCode = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioSink.WriteException.<init>(int):void");
        }
    }
}
