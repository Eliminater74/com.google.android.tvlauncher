package com.google.android.exoplayer2.metadata.emsg;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class EventMessageDecoder implements MetadataDecoder {
    private static final String TAG = "EventMessageDecoder";

    public Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer buffer = inputBuffer.data;
        byte[] data = buffer.array();
        int size = buffer.limit();
        ParsableByteArray emsgData = new ParsableByteArray(data, size);
        String schemeIdUri = (String) Assertions.checkNotNull(emsgData.readNullTerminatedString());
        String value = (String) Assertions.checkNotNull(emsgData.readNullTerminatedString());
        long timescale = emsgData.readUnsignedInt();
        long presentationTimeDelta = emsgData.readUnsignedInt();
        if (presentationTimeDelta != 0) {
            StringBuilder sb = new StringBuilder(63);
            sb.append("Ignoring non-zero presentation_time_delta: ");
            sb.append(presentationTimeDelta);
            Log.m30w(TAG, sb.toString());
        }
        return new Metadata(new EventMessage(schemeIdUri, value, Util.scaleLargeTimestamp(emsgData.readUnsignedInt(), 1000, timescale), emsgData.readUnsignedInt(), Arrays.copyOfRange(data, emsgData.getPosition(), size)));
    }
}
