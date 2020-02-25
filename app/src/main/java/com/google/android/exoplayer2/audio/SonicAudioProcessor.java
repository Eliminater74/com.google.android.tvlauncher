package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;
import android.support.p001v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public final class SonicAudioProcessor implements AudioProcessor {
    public static final float MAXIMUM_PITCH = 8.0f;
    public static final float MAXIMUM_SPEED = 8.0f;
    public static final float MINIMUM_PITCH = 0.1f;
    public static final float MINIMUM_SPEED = 0.1f;
    public static final int SAMPLE_RATE_NO_CHANGE = -1;
    private static final float CLOSE_THRESHOLD = 0.01f;
    private static final int MIN_BYTES_FOR_SPEEDUP_CALCULATION = 1024;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private int channelCount = -1;
    private long inputBytes;
    private boolean inputEnded;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private long outputBytes;
    private int outputSampleRateHz = -1;
    private int pendingOutputSampleRateHz = -1;
    private boolean pendingSonicRecreation;
    private float pitch = 1.0f;
    private int sampleRateHz = -1;
    private ShortBuffer shortBuffer = this.buffer.asShortBuffer();
    @Nullable
    private Sonic sonic;
    private float speed = 1.0f;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float
     arg types: [float, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.constrainValue(int, int, int):int
      com.google.android.exoplayer2.util.Util.constrainValue(long, long, long):long
      com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float */
    public float setSpeed(float speed2) {
        float speed3 = Util.constrainValue(speed2, 0.1f, 8.0f);
        if (this.speed != speed3) {
            this.speed = speed3;
            this.pendingSonicRecreation = true;
        }
        flush();
        return speed3;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float
     arg types: [float, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.constrainValue(int, int, int):int
      com.google.android.exoplayer2.util.Util.constrainValue(long, long, long):long
      com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float */
    public float setPitch(float pitch2) {
        float pitch3 = Util.constrainValue(pitch2, 0.1f, 8.0f);
        if (this.pitch != pitch3) {
            this.pitch = pitch3;
            this.pendingSonicRecreation = true;
        }
        flush();
        return pitch3;
    }

    public long scaleDurationForSpeedup(long duration) {
        long j = this.outputBytes;
        if (j >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            int i = this.outputSampleRateHz;
            int i2 = this.sampleRateHz;
            if (i == i2) {
                return Util.scaleLargeTimestamp(duration, this.inputBytes, j);
            }
            return Util.scaleLargeTimestamp(duration, this.inputBytes * ((long) i), j * ((long) i2));
        }
        double d = (double) this.speed;
        double d2 = (double) duration;
        Double.isNaN(d);
        Double.isNaN(d2);
        return (long) (d * d2);
    }

    public boolean configure(int sampleRateHz2, int channelCount2, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (encoding == 2) {
            int outputSampleRateHz2 = this.pendingOutputSampleRateHz;
            if (outputSampleRateHz2 == -1) {
                outputSampleRateHz2 = sampleRateHz2;
            }
            if (this.sampleRateHz == sampleRateHz2 && this.channelCount == channelCount2 && this.outputSampleRateHz == outputSampleRateHz2) {
                return false;
            }
            this.sampleRateHz = sampleRateHz2;
            this.channelCount = channelCount2;
            this.outputSampleRateHz = outputSampleRateHz2;
            this.pendingSonicRecreation = true;
            return true;
        }
        throw new AudioProcessor.UnhandledFormatException(sampleRateHz2, channelCount2, encoding);
    }

    public boolean isActive() {
        return this.sampleRateHz != -1 && (Math.abs(this.speed - 1.0f) >= CLOSE_THRESHOLD || Math.abs(this.pitch - 1.0f) >= CLOSE_THRESHOLD || this.outputSampleRateHz != this.sampleRateHz);
    }

    public int getOutputChannelCount() {
        return this.channelCount;
    }

    public int getOutputEncoding() {
        return 2;
    }

    public int getOutputSampleRateHz() {
        return this.outputSampleRateHz;
    }

    public void setOutputSampleRateHz(int sampleRateHz2) {
        this.pendingOutputSampleRateHz = sampleRateHz2;
    }

    public void queueInput(ByteBuffer inputBuffer) {
        Sonic sonic2 = (Sonic) Assertions.checkNotNull(this.sonic);
        if (inputBuffer.hasRemaining()) {
            ShortBuffer shortBuffer2 = inputBuffer.asShortBuffer();
            int inputSize = inputBuffer.remaining();
            this.inputBytes += (long) inputSize;
            sonic2.queueInput(shortBuffer2);
            inputBuffer.position(inputBuffer.position() + inputSize);
        }
        int outputSize = sonic2.getFramesAvailable() * this.channelCount * 2;
        if (outputSize > 0) {
            if (this.buffer.capacity() < outputSize) {
                this.buffer = ByteBuffer.allocateDirect(outputSize).order(ByteOrder.nativeOrder());
                this.shortBuffer = this.buffer.asShortBuffer();
            } else {
                this.buffer.clear();
                this.shortBuffer.clear();
            }
            sonic2.getOutput(this.shortBuffer);
            this.outputBytes += (long) outputSize;
            this.buffer.limit(outputSize);
            this.outputBuffer = this.buffer;
        }
    }

    public void queueEndOfStream() {
        Sonic sonic2 = this.sonic;
        if (sonic2 != null) {
            sonic2.queueEndOfStream();
        }
        this.inputEnded = true;
    }

    public ByteBuffer getOutput() {
        ByteBuffer outputBuffer2 = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return outputBuffer2;
    }

    public boolean isEnded() {
        Sonic sonic2;
        return this.inputEnded && ((sonic2 = this.sonic) == null || sonic2.getFramesAvailable() == 0);
    }

    public void flush() {
        if (isActive()) {
            if (this.pendingSonicRecreation) {
                this.sonic = new Sonic(this.sampleRateHz, this.channelCount, this.speed, this.pitch, this.outputSampleRateHz);
            } else {
                Sonic sonic2 = this.sonic;
                if (sonic2 != null) {
                    sonic2.flush();
                }
            }
        }
        this.outputBuffer = EMPTY_BUFFER;
        this.inputBytes = 0;
        this.outputBytes = 0;
        this.inputEnded = false;
    }

    public void reset() {
        this.speed = 1.0f;
        this.pitch = 1.0f;
        this.channelCount = -1;
        this.sampleRateHz = -1;
        this.outputSampleRateHz = -1;
        this.buffer = EMPTY_BUFFER;
        this.shortBuffer = this.buffer.asShortBuffer();
        this.outputBuffer = EMPTY_BUFFER;
        this.pendingOutputSampleRateHz = -1;
        this.pendingSonicRecreation = false;
        this.sonic = null;
        this.inputBytes = 0;
        this.outputBytes = 0;
        this.inputEnded = false;
    }
}
