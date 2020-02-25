package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class TeeAudioProcessor extends BaseAudioProcessor {
    private final AudioBufferSink audioBufferSink;

    public TeeAudioProcessor(AudioBufferSink audioBufferSink2) {
        this.audioBufferSink = (AudioBufferSink) Assertions.checkNotNull(audioBufferSink2);
    }

    public boolean configure(int sampleRateHz, int channelCount, int encoding) {
        return setInputFormat(sampleRateHz, channelCount, encoding);
    }

    public void queueInput(ByteBuffer inputBuffer) {
        int remaining = inputBuffer.remaining();
        if (remaining != 0) {
            this.audioBufferSink.handleBuffer(inputBuffer.asReadOnlyBuffer());
            replaceOutputBuffer(remaining).put(inputBuffer).flip();
        }
    }

    /* access modifiers changed from: protected */
    public void onFlush() {
        if (isActive()) {
            this.audioBufferSink.flush(this.sampleRateHz, this.channelCount, this.encoding);
        }
    }

    public interface AudioBufferSink {
        void flush(int i, int i2, int i3);

        void handleBuffer(ByteBuffer byteBuffer);
    }

    public static final class WavFileAudioBufferSink implements AudioBufferSink {
        private static final int FILE_SIZE_MINUS_44_OFFSET = 40;
        private static final int FILE_SIZE_MINUS_8_OFFSET = 4;
        private static final int HEADER_LENGTH = 44;
        private static final String TAG = "WaveFileAudioBufferSink";
        private final String outputFileNamePrefix;
        private final byte[] scratchBuffer = new byte[1024];
        private final ByteBuffer scratchByteBuffer = ByteBuffer.wrap(this.scratchBuffer).order(ByteOrder.LITTLE_ENDIAN);
        private int bytesWritten;
        private int channelCount;
        private int counter;
        private int encoding;
        @Nullable
        private RandomAccessFile randomAccessFile;
        private int sampleRateHz;

        public WavFileAudioBufferSink(String outputFileNamePrefix2) {
            this.outputFileNamePrefix = outputFileNamePrefix2;
        }

        public void flush(int sampleRateHz2, int channelCount2, int encoding2) {
            try {
                reset();
            } catch (IOException e) {
                Log.m27e(TAG, "Error resetting", e);
            }
            this.sampleRateHz = sampleRateHz2;
            this.channelCount = channelCount2;
            this.encoding = encoding2;
        }

        public void handleBuffer(ByteBuffer buffer) {
            try {
                maybePrepareFile();
                writeBuffer(buffer);
            } catch (IOException e) {
                Log.m27e(TAG, "Error writing data", e);
            }
        }

        private void maybePrepareFile() throws IOException {
            if (this.randomAccessFile == null) {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(getNextOutputFileName(), "rw");
                writeFileHeader(randomAccessFile2);
                this.randomAccessFile = randomAccessFile2;
                this.bytesWritten = 44;
            }
        }

        private void writeFileHeader(RandomAccessFile randomAccessFile2) throws IOException {
            randomAccessFile2.writeInt(WavUtil.RIFF_FOURCC);
            randomAccessFile2.writeInt(-1);
            randomAccessFile2.writeInt(WavUtil.WAVE_FOURCC);
            randomAccessFile2.writeInt(WavUtil.FMT_FOURCC);
            this.scratchByteBuffer.clear();
            this.scratchByteBuffer.putInt(16);
            this.scratchByteBuffer.putShort((short) WavUtil.getTypeForEncoding(this.encoding));
            this.scratchByteBuffer.putShort((short) this.channelCount);
            this.scratchByteBuffer.putInt(this.sampleRateHz);
            int bytesPerSample = Util.getPcmFrameSize(this.encoding, this.channelCount);
            this.scratchByteBuffer.putInt(this.sampleRateHz * bytesPerSample);
            this.scratchByteBuffer.putShort((short) bytesPerSample);
            this.scratchByteBuffer.putShort((short) ((bytesPerSample * 8) / this.channelCount));
            randomAccessFile2.write(this.scratchBuffer, 0, this.scratchByteBuffer.position());
            randomAccessFile2.writeInt(WavUtil.DATA_FOURCC);
            randomAccessFile2.writeInt(-1);
        }

        private void writeBuffer(ByteBuffer buffer) throws IOException {
            RandomAccessFile randomAccessFile2 = (RandomAccessFile) Assertions.checkNotNull(this.randomAccessFile);
            while (buffer.hasRemaining()) {
                int bytesToWrite = Math.min(buffer.remaining(), this.scratchBuffer.length);
                buffer.get(this.scratchBuffer, 0, bytesToWrite);
                randomAccessFile2.write(this.scratchBuffer, 0, bytesToWrite);
                this.bytesWritten += bytesToWrite;
            }
        }

        private void reset() throws IOException {
            RandomAccessFile randomAccessFile2 = this.randomAccessFile;
            if (randomAccessFile2 != null) {
                try {
                    this.scratchByteBuffer.clear();
                    this.scratchByteBuffer.putInt(this.bytesWritten - 8);
                    randomAccessFile2.seek(4);
                    randomAccessFile2.write(this.scratchBuffer, 0, 4);
                    this.scratchByteBuffer.clear();
                    this.scratchByteBuffer.putInt(this.bytesWritten - 44);
                    randomAccessFile2.seek(40);
                    randomAccessFile2.write(this.scratchBuffer, 0, 4);
                } catch (IOException e) {
                    Log.m31w(TAG, "Error updating file size", e);
                }
                try {
                    randomAccessFile2.close();
                } finally {
                    this.randomAccessFile = null;
                }
            }
        }

        private String getNextOutputFileName() {
            int i = this.counter;
            this.counter = i + 1;
            return Util.formatInvariant("%s-%04d.wav", this.outputFileNamePrefix, Integer.valueOf(i));
        }
    }
}
