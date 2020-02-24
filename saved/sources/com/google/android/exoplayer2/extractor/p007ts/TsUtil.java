package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* renamed from: com.google.android.exoplayer2.extractor.ts.TsUtil */
public final class TsUtil {
    public static int findSyncBytePosition(byte[] data, int startPosition, int limitPosition) {
        int position = startPosition;
        while (position < limitPosition && data[position] != 71) {
            position++;
        }
        return position;
    }

    public static long readPcrFromPacket(ParsableByteArray packetBuffer, int startOfPacket, int pcrPid) {
        packetBuffer.setPosition(startOfPacket);
        if (packetBuffer.bytesLeft() < 5) {
            return C0841C.TIME_UNSET;
        }
        int tsPacketHeader = packetBuffer.readInt();
        if ((8388608 & tsPacketHeader) != 0 || ((2096896 & tsPacketHeader) >> 8) != pcrPid) {
            return C0841C.TIME_UNSET;
        }
        boolean pcrFlagSet = true;
        if (((tsPacketHeader & 32) != 0) && packetBuffer.readUnsignedByte() >= 7 && packetBuffer.bytesLeft() >= 7) {
            if ((packetBuffer.readUnsignedByte() & 16) != 16) {
                pcrFlagSet = false;
            }
            if (pcrFlagSet) {
                byte[] pcrBytes = new byte[6];
                packetBuffer.readBytes(pcrBytes, 0, pcrBytes.length);
                return readPcrValueFromPcrBytes(pcrBytes);
            }
        }
        return C0841C.TIME_UNSET;
    }

    private static long readPcrValueFromPcrBytes(byte[] pcrBytes) {
        return ((((long) pcrBytes[0]) & 255) << 25) | ((((long) pcrBytes[1]) & 255) << 17) | ((((long) pcrBytes[2]) & 255) << 9) | ((((long) pcrBytes[3]) & 255) << 1) | ((255 & ((long) pcrBytes[4])) >> 7);
    }

    private TsUtil() {
    }
}
