package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.UnsignedBytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class OpusReader extends StreamReader {
    private static final int DEFAULT_SEEK_PRE_ROLL_SAMPLES = 3840;
    private static final int OPUS_CODE = Util.getIntegerCodeForString("Opus");
    private static final byte[] OPUS_SIGNATURE = {79, 112, 117, 115, 72, 101, 97, 100};
    private static final int SAMPLE_RATE = 48000;
    private boolean headerRead;

    OpusReader() {
    }

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        int bytesLeft = data.bytesLeft();
        byte[] bArr = OPUS_SIGNATURE;
        if (bytesLeft < bArr.length) {
            return false;
        }
        byte[] header = new byte[bArr.length];
        data.readBytes(header, 0, bArr.length);
        return Arrays.equals(header, OPUS_SIGNATURE);
    }

    /* access modifiers changed from: protected */
    public void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.headerRead = false;
        }
    }

    /* access modifiers changed from: protected */
    public long preparePayload(ParsableByteArray packet) {
        return convertTimeToGranule(getPacketDurationUs(packet.data));
    }

    /* access modifiers changed from: protected */
    public boolean readHeaders(ParsableByteArray packet, long position, StreamReader.SetupData setupData) {
        ParsableByteArray parsableByteArray = packet;
        boolean z = true;
        if (!this.headerRead) {
            byte[] metadata = Arrays.copyOf(parsableByteArray.data, packet.limit());
            byte b = ((metadata[11] & UnsignedBytes.MAX_VALUE) << 8) | (metadata[10] & UnsignedBytes.MAX_VALUE);
            ArrayList arrayList = new ArrayList(3);
            arrayList.add(metadata);
            putNativeOrderLong(arrayList, b);
            putNativeOrderLong(arrayList, DEFAULT_SEEK_PRE_ROLL_SAMPLES);
            setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_OPUS, null, -1, -1, metadata[9] & 255, SAMPLE_RATE, arrayList, null, 0, null);
            this.headerRead = true;
            return true;
        }
        if (packet.readInt() != OPUS_CODE) {
            z = false;
        }
        boolean headerPacket = z;
        parsableByteArray.setPosition(0);
        return headerPacket;
    }

    private void putNativeOrderLong(List<byte[]> initializationData, int samples) {
        initializationData.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong((((long) samples) * C0841C.NANOS_PER_SECOND) / 48000).array());
    }

    private long getPacketDurationUs(byte[] packet) {
        int frames;
        int length;
        int toc = packet[0] & 255;
        int i = toc & 3;
        if (i == 0) {
            frames = 1;
        } else if (i == 1 || i == 2) {
            frames = 2;
        } else {
            frames = packet[1] & 63;
        }
        int config = toc >> 3;
        int length2 = config & 3;
        if (config >= 16) {
            length = 2500 << length2;
        } else if (config >= 12) {
            length = 10000 << (length2 & 1);
        } else if (length2 == 3) {
            length = 60000;
        } else {
            length = 10000 << length2;
        }
        return ((long) frames) * ((long) length);
    }
}
