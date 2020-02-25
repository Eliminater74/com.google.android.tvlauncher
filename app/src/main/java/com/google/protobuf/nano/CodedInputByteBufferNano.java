package com.google.protobuf.nano;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.Parser;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;

public final class CodedInputByteBufferNano {
    private static final int DEFAULT_RECURSION_LIMIT = 64;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private final int bufferSize;
    private final int bufferStart;
    private int bufferPos;
    private int bufferSizeAfterLimit;
    private CodedInputStream codedInputStream;
    private int currentLimit = Integer.MAX_VALUE;
    private int lastTag;
    private int maybeLimitedBufferSize;
    private int recursionDepth;
    private int recursionLimit = 64;
    private int sizeLimit = DEFAULT_SIZE_LIMIT;

    private CodedInputByteBufferNano(byte[] buffer2, int off, int len) {
        this.buffer = buffer2;
        this.bufferStart = off;
        int i = off + len;
        this.maybeLimitedBufferSize = i;
        this.bufferSize = i;
        this.bufferPos = off;
    }

    public static CodedInputByteBufferNano newInstance(byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }

    public static CodedInputByteBufferNano newInstance(byte[] buf, int off, int len) {
        return new CodedInputByteBufferNano(buf, off, len);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        int i = this.lastTag;
        if (i != 0) {
            return i;
        }
        throw InvalidProtocolBufferNanoException.invalidTag();
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferNanoException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferNanoException.invalidEndTag();
        }
    }

    public boolean skipField(int tag) throws IOException {
        int tagWireType = WireFormatNano.getTagWireType(tag);
        if (tagWireType == 0) {
            readInt32();
            return true;
        } else if (tagWireType == 1) {
            readRawLittleEndian64();
            return true;
        } else if (tagWireType == 2) {
            skipRawBytes(readRawVarint32());
            return true;
        } else if (tagWireType == 3) {
            skipMessage();
            checkLastTagWas(WireFormatNano.makeTag(WireFormatNano.getTagFieldNumber(tag), 4));
            return true;
        } else if (tagWireType == 4) {
            return false;
        } else {
            if (tagWireType == 5) {
                readRawLittleEndian32();
                return true;
            }
            throw InvalidProtocolBufferNanoException.invalidWireType();
        }
    }

    public void skipMessage() throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag));
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return readRawVarint64();
    }

    public long readInt64() throws IOException {
        return readRawVarint64();
    }

    public int readInt32() throws IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return readRawVarint32() != 0;
    }

    public String readString() throws IOException {
        int size = readRawVarint32();
        if (size >= 0) {
            int i = this.maybeLimitedBufferSize;
            int i2 = this.bufferPos;
            if (size <= i - i2) {
                String result = new String(this.buffer, i2, size, InternalNano.UTF_8);
                this.bufferPos += size;
                return result;
            }
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        throw InvalidProtocolBufferNanoException.negativeSize();
    }

    public void readGroup(MessageNano msg, int fieldNumber) throws IOException {
        int i = this.recursionDepth;
        if (i < this.recursionLimit) {
            this.recursionDepth = i + 1;
            msg.mergeFrom(this);
            checkLastTagWas(WireFormatNano.makeTag(fieldNumber, 4));
            this.recursionDepth--;
            return;
        }
        throw InvalidProtocolBufferNanoException.recursionLimitExceeded();
    }

    public void readMessage(MessageNano msg) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth < this.recursionLimit) {
            int oldLimit = pushLimit(length);
            this.recursionDepth++;
            msg.mergeFrom(this);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(oldLimit);
            return;
        }
        throw InvalidProtocolBufferNanoException.recursionLimitExceeded();
    }

    public byte[] readBytes() throws IOException {
        int size = readRawVarint32();
        if (size < 0) {
            throw InvalidProtocolBufferNanoException.negativeSize();
        } else if (size == 0) {
            return WireFormatNano.EMPTY_BYTES;
        } else {
            int i = this.maybeLimitedBufferSize;
            int i2 = this.bufferPos;
            if (size <= i - i2) {
                byte[] result = new byte[size];
                System.arraycopy(this.buffer, i2, result, 0, size);
                this.bufferPos += size;
                return result;
            }
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
    }

    public int readUInt32() throws IOException {
        return readRawVarint32();
    }

    public int readEnum() throws IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return decodeZigZag64(readRawVarint64());
    }

    public int readRawVarint32() throws IOException {
        int tmp = readRawByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
        byte readRawByte = readRawByte();
        byte tmp2 = readRawByte;
        if (readRawByte >= 0) {
            return result | (tmp2 << 7);
        }
        int result2 = result | ((tmp2 & Ascii.DEL) << 7);
        byte readRawByte2 = readRawByte();
        byte tmp3 = readRawByte2;
        if (readRawByte2 >= 0) {
            return result2 | (tmp3 << Ascii.f157SO);
        }
        int result3 = result2 | ((tmp3 & Ascii.DEL) << Ascii.f157SO);
        byte readRawByte3 = readRawByte();
        byte tmp4 = readRawByte3;
        if (readRawByte3 >= 0) {
            return result3 | (tmp4 << Ascii.NAK);
        }
        int result4 = result3 | ((tmp4 & Ascii.DEL) << Ascii.NAK);
        byte readRawByte4 = readRawByte();
        byte tmp5 = readRawByte4;
        int result5 = result4 | (readRawByte4 << Ascii.f150FS);
        if (tmp5 >= 0) {
            return result5;
        }
        for (int i = 0; i < 5; i++) {
            if (readRawByte() >= 0) {
                return result5;
            }
        }
        throw InvalidProtocolBufferNanoException.malformedVarint();
    }

    public long readRawVarint64() throws IOException {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = readRawByte();
            result |= ((long) (b & Ascii.DEL)) << shift;
            if ((b & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferNanoException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        return (readRawByte() & UnsignedBytes.MAX_VALUE) | ((readRawByte() & UnsignedBytes.MAX_VALUE) << 8) | ((readRawByte() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((readRawByte() & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
    }

    public long readRawLittleEndian64() throws IOException {
        return (((long) readRawByte()) & 255) | ((((long) readRawByte()) & 255) << 8) | ((((long) readRawByte()) & 255) << 16) | ((((long) readRawByte()) & 255) << 24) | ((((long) readRawByte()) & 255) << 32) | ((((long) readRawByte()) & 255) << 40) | ((((long) readRawByte()) & 255) << 48) | ((255 & ((long) readRawByte())) << 56);
    }

    private CodedInputStream getCodedInputStream() throws IOException {
        if (this.codedInputStream == null) {
            this.codedInputStream = CodedInputStream.newInstance(this.buffer, this.bufferStart, this.bufferSize);
        }
        int liteBytesRead = this.codedInputStream.getTotalBytesRead();
        int nanoBytesRead = this.bufferPos - this.bufferStart;
        if (liteBytesRead <= nanoBytesRead) {
            this.codedInputStream.skipRawBytes(nanoBytesRead - liteBytesRead);
            this.codedInputStream.setRecursionLimit(this.recursionLimit - this.recursionDepth);
            return this.codedInputStream;
        }
        throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", Integer.valueOf(liteBytesRead), Integer.valueOf(nanoBytesRead)));
    }

    public <T extends GeneratedMessageLite<T, ?>> T readMessageLite(Parser<T> parser) throws IOException {
        try {
            T result = (GeneratedMessageLite) getCodedInputStream().readMessage(parser, ExtensionRegistryLite.getGeneratedRegistry());
            skipField(this.lastTag);
            return result;
        } catch (InvalidProtocolBufferException e) {
            throw new InvalidProtocolBufferNanoException("", e);
        }
    }

    public <T extends GeneratedMessageLite<T, ?>> T readGroupLite(Parser<T> parser, int fieldNumber) throws IOException {
        try {
            T result = (GeneratedMessageLite) getCodedInputStream().readGroup(fieldNumber, parser, ExtensionRegistryLite.getGeneratedRegistry());
            skipField(this.lastTag);
            return result;
        } catch (InvalidProtocolBufferException e) {
            throw new InvalidProtocolBufferNanoException("", e);
        }
    }

    public <K, V> void readMapEntryInto(MapFieldLite<K, V> map, MapEntryLite<K, V> defaultEntry) throws IOException {
        try {
            defaultEntry.parseInto(map, getCodedInputStream(), ExtensionRegistryLite.getGeneratedRegistry());
            skipField(this.lastTag);
        } catch (InvalidProtocolBufferException e) {
            throw new InvalidProtocolBufferNanoException("", e);
        }
    }

    public int setRecursionLimit(int limit) {
        if (limit >= 0) {
            int oldLimit = this.recursionLimit;
            this.recursionLimit = limit;
            return oldLimit;
        }
        StringBuilder sb = new StringBuilder(47);
        sb.append("Recursion limit cannot be negative: ");
        sb.append(limit);
        throw new IllegalArgumentException(sb.toString());
    }

    public int setSizeLimit(int limit) {
        if (limit >= 0) {
            int oldLimit = this.sizeLimit;
            this.sizeLimit = limit;
            return oldLimit;
        }
        StringBuilder sb = new StringBuilder(42);
        sb.append("Size limit cannot be negative: ");
        sb.append(limit);
        throw new IllegalArgumentException(sb.toString());
    }

    public void resetSizeCounter() {
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferNanoException {
        if (byteLimit >= 0) {
            int byteLimit2 = byteLimit + this.bufferPos;
            int oldLimit = this.currentLimit;
            if (byteLimit2 <= oldLimit) {
                this.currentLimit = byteLimit2;
                recomputeBufferSizeAfterLimit();
                return oldLimit;
            }
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        throw InvalidProtocolBufferNanoException.negativeSize();
    }

    private void recomputeBufferSizeAfterLimit() {
        this.maybeLimitedBufferSize += this.bufferSizeAfterLimit;
        int bufferEnd = this.maybeLimitedBufferSize;
        int i = this.currentLimit;
        if (bufferEnd > i) {
            this.bufferSizeAfterLimit = bufferEnd - i;
            this.maybeLimitedBufferSize -= this.bufferSizeAfterLimit;
            return;
        }
        this.bufferSizeAfterLimit = 0;
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        int i = this.currentLimit;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - this.bufferPos;
    }

    public boolean isAtEnd() {
        return this.bufferPos == this.maybeLimitedBufferSize;
    }

    public int getPosition() {
        return this.bufferPos - this.bufferStart;
    }

    public byte[] getData(int offset, int length) {
        if (length == 0) {
            return WireFormatNano.EMPTY_BYTES;
        }
        byte[] copy = new byte[length];
        System.arraycopy(this.buffer, this.bufferStart + offset, copy, 0, length);
        return copy;
    }

    public void rewindToPosition(int position) {
        rewindToPositionAndTag(position, this.lastTag);
    }

    /* access modifiers changed from: package-private */
    public void rewindToPositionAndTag(int position, int tag) {
        int i = this.bufferPos;
        int i2 = this.bufferStart;
        if (position > i - i2) {
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(position);
            sb.append(" is beyond current ");
            sb.append(i - i2);
            throw new IllegalArgumentException(sb.toString());
        } else if (position >= 0) {
            this.bufferPos = i2 + position;
            this.lastTag = tag;
        } else {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(position);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public byte readRawByte() throws IOException {
        int i = this.bufferPos;
        if (i != this.maybeLimitedBufferSize) {
            byte[] bArr = this.buffer;
            this.bufferPos = i + 1;
            return bArr[i];
        }
        throw InvalidProtocolBufferNanoException.truncatedMessage();
    }

    public byte[] readRawBytes(int size) throws IOException {
        if (size >= 0) {
            int i = this.bufferPos;
            int i2 = i + size;
            int i3 = this.currentLimit;
            if (i2 > i3) {
                skipRawBytes(i3 - i);
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            } else if (size <= this.maybeLimitedBufferSize - i) {
                byte[] bytes = new byte[size];
                System.arraycopy(this.buffer, i, bytes, 0, size);
                this.bufferPos += size;
                return bytes;
            } else {
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            }
        } else {
            throw InvalidProtocolBufferNanoException.negativeSize();
        }
    }

    public void skipRawBytes(int size) throws IOException {
        if (size >= 0) {
            int i = this.bufferPos;
            int i2 = i + size;
            int i3 = this.currentLimit;
            if (i2 > i3) {
                skipRawBytes(i3 - i);
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            } else if (size <= this.maybeLimitedBufferSize - i) {
                this.bufferPos = i + size;
            } else {
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            }
        } else {
            throw InvalidProtocolBufferNanoException.negativeSize();
        }
    }
}
