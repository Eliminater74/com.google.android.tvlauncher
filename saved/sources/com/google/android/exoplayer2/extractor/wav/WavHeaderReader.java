package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

final class WavHeaderReader {
    private static final String TAG = "WavHeaderReader";

    public static WavHeader peek(ExtractorInput input) throws IOException, InterruptedException {
        ExtractorInput extractorInput = input;
        Assertions.checkNotNull(input);
        ParsableByteArray scratch = new ParsableByteArray(16);
        if (ChunkHeader.peek(extractorInput, scratch).f78id != WavUtil.RIFF_FOURCC) {
            return null;
        }
        extractorInput.peekFully(scratch.data, 0, 4);
        scratch.setPosition(0);
        int riffFormat = scratch.readInt();
        if (riffFormat != WavUtil.WAVE_FOURCC) {
            StringBuilder sb = new StringBuilder(36);
            sb.append("Unsupported RIFF format: ");
            sb.append(riffFormat);
            Log.m26e(TAG, sb.toString());
            return null;
        }
        ChunkHeader chunkHeader = ChunkHeader.peek(extractorInput, scratch);
        while (chunkHeader.f78id != WavUtil.FMT_FOURCC) {
            extractorInput.advancePeekPosition((int) chunkHeader.size);
            chunkHeader = ChunkHeader.peek(extractorInput, scratch);
        }
        Assertions.checkState(chunkHeader.size >= 16);
        extractorInput.peekFully(scratch.data, 0, 16);
        scratch.setPosition(0);
        int type = scratch.readLittleEndianUnsignedShort();
        int numChannels = scratch.readLittleEndianUnsignedShort();
        int sampleRateHz = scratch.readLittleEndianUnsignedIntToInt();
        int averageBytesPerSecond = scratch.readLittleEndianUnsignedIntToInt();
        int blockAlignment = scratch.readLittleEndianUnsignedShort();
        int bitsPerSample = scratch.readLittleEndianUnsignedShort();
        int expectedBlockAlignment = (numChannels * bitsPerSample) / 8;
        if (blockAlignment == expectedBlockAlignment) {
            int encoding = WavUtil.getEncodingForType(type, bitsPerSample);
            if (encoding == 0) {
                StringBuilder sb2 = new StringBuilder(64);
                sb2.append("Unsupported WAV format: ");
                sb2.append(bitsPerSample);
                sb2.append(" bit/sample, type ");
                sb2.append(type);
                Log.m26e(TAG, sb2.toString());
                return null;
            }
            extractorInput.advancePeekPosition(((int) chunkHeader.size) - 16);
            return new WavHeader(numChannels, sampleRateHz, averageBytesPerSecond, blockAlignment, bitsPerSample, encoding);
        }
        StringBuilder sb3 = new StringBuilder(55);
        sb3.append("Expected block alignment: ");
        sb3.append(expectedBlockAlignment);
        sb3.append("; got: ");
        sb3.append(blockAlignment);
        throw new ParserException(sb3.toString());
    }

    public static void skipToData(ExtractorInput input, WavHeader wavHeader) throws IOException, InterruptedException {
        Assertions.checkNotNull(input);
        Assertions.checkNotNull(wavHeader);
        input.resetPeekPosition();
        ParsableByteArray scratch = new ParsableByteArray(8);
        ChunkHeader chunkHeader = ChunkHeader.peek(input, scratch);
        while (chunkHeader.f78id != Util.getIntegerCodeForString("data")) {
            int i = chunkHeader.f78id;
            StringBuilder sb = new StringBuilder(39);
            sb.append("Ignoring unknown WAV chunk: ");
            sb.append(i);
            Log.m30w(TAG, sb.toString());
            long bytesToSkip = chunkHeader.size + 8;
            if (chunkHeader.f78id == Util.getIntegerCodeForString("RIFF")) {
                bytesToSkip = 12;
            }
            if (bytesToSkip <= 2147483647L) {
                input.skipFully((int) bytesToSkip);
                chunkHeader = ChunkHeader.peek(input, scratch);
            } else {
                int i2 = chunkHeader.f78id;
                StringBuilder sb2 = new StringBuilder(51);
                sb2.append("Chunk is too large (~2GB+) to skip; id: ");
                sb2.append(i2);
                throw new ParserException(sb2.toString());
            }
        }
        input.skipFully(8);
        wavHeader.setDataBounds(input.getPosition(), chunkHeader.size);
    }

    private WavHeaderReader() {
    }

    private static final class ChunkHeader {
        public static final int SIZE_IN_BYTES = 8;

        /* renamed from: id */
        public final int f78id;
        public final long size;

        private ChunkHeader(int id, long size2) {
            this.f78id = id;
            this.size = size2;
        }

        public static ChunkHeader peek(ExtractorInput input, ParsableByteArray scratch) throws IOException, InterruptedException {
            input.peekFully(scratch.data, 0, 8);
            scratch.setPosition(0);
            return new ChunkHeader(scratch.readInt(), scratch.readLittleEndianUnsignedInt());
        }
    }
}
