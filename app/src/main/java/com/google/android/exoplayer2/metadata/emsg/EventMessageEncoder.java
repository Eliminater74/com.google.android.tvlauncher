package com.google.android.exoplayer2.metadata.emsg;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class EventMessageEncoder {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
    private final DataOutputStream dataOutputStream = new DataOutputStream(this.byteArrayOutputStream);

    private static void writeNullTerminatedString(DataOutputStream dataOutputStream2, String value) throws IOException {
        dataOutputStream2.writeBytes(value);
        dataOutputStream2.writeByte(0);
    }

    private static void writeUnsignedInt(DataOutputStream outputStream, long value) throws IOException {
        outputStream.writeByte(((int) (value >>> 24)) & 255);
        outputStream.writeByte(((int) (value >>> 16)) & 255);
        outputStream.writeByte(((int) (value >>> 8)) & 255);
        outputStream.writeByte(((int) value) & 255);
    }

    @Nullable
    public byte[] encode(EventMessage eventMessage) {
        this.byteArrayOutputStream.reset();
        try {
            writeNullTerminatedString(this.dataOutputStream, eventMessage.schemeIdUri);
            writeNullTerminatedString(this.dataOutputStream, eventMessage.value != null ? eventMessage.value : "");
            writeUnsignedInt(this.dataOutputStream, 1000);
            writeUnsignedInt(this.dataOutputStream, 0);
            writeUnsignedInt(this.dataOutputStream, eventMessage.durationMs);
            writeUnsignedInt(this.dataOutputStream, eventMessage.f79id);
            this.dataOutputStream.write(eventMessage.messageData);
            this.dataOutputStream.flush();
            return this.byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
