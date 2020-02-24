package com.google.protobuf.nano;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MessageLite;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.util.Map;

public final class CodedOutputByteBufferNano {
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int LITTLE_ENDIAN_64_SIZE = 8;
    private static final int MAX_UTF8_EXPANSION = 3;
    private final ByteBuffer buffer;
    private CodedOutputStream codedOutputStream;
    private int codedOutputStreamPosition;

    private CodedOutputByteBufferNano(byte[] buffer2, int offset, int length) {
        this(ByteBuffer.wrap(buffer2, offset, length));
    }

    private CodedOutputByteBufferNano(ByteBuffer buffer2) {
        this.buffer = buffer2;
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static CodedOutputByteBufferNano newInstance(byte[] flatArray) {
        return newInstance(flatArray, 0, flatArray.length);
    }

    public static CodedOutputByteBufferNano newInstance(byte[] flatArray, int offset, int length) {
        return new CodedOutputByteBufferNano(flatArray, offset, length);
    }

    private CodedOutputStream getCodedOutputStream() throws IOException {
        if (this.codedOutputStream == null) {
            this.codedOutputStream = CodedOutputStream.newInstance(this.buffer);
            this.codedOutputStreamPosition = this.buffer.position();
        } else if (this.codedOutputStreamPosition != this.buffer.position()) {
            this.codedOutputStream.write(this.buffer.array(), this.codedOutputStreamPosition, this.buffer.position() - this.codedOutputStreamPosition);
            this.codedOutputStreamPosition = this.buffer.position();
        }
        return this.codedOutputStream;
    }

    public void writeDouble(int fieldNumber, double value) throws IOException {
        writeTag(fieldNumber, 1);
        writeDoubleNoTag(value);
    }

    public void writeFloat(int fieldNumber, float value) throws IOException {
        writeTag(fieldNumber, 5);
        writeFloatNoTag(value);
    }

    public void writeUInt64(int fieldNumber, long value) throws IOException {
        writeTag(fieldNumber, 0);
        writeUInt64NoTag(value);
    }

    public void writeInt64(int fieldNumber, long value) throws IOException {
        writeTag(fieldNumber, 0);
        writeInt64NoTag(value);
    }

    public void writeInt32(int fieldNumber, int value) throws IOException {
        writeTag(fieldNumber, 0);
        writeInt32NoTag(value);
    }

    public void writeFixed64(int fieldNumber, long value) throws IOException {
        writeTag(fieldNumber, 1);
        writeFixed64NoTag(value);
    }

    public void writeFixed32(int fieldNumber, int value) throws IOException {
        writeTag(fieldNumber, 5);
        writeFixed32NoTag(value);
    }

    public void writeBool(int fieldNumber, boolean value) throws IOException {
        writeTag(fieldNumber, 0);
        writeBoolNoTag(value);
    }

    public void writeString(int fieldNumber, String value) throws IOException {
        writeTag(fieldNumber, 2);
        writeStringNoTag(value);
    }

    public void writeGroup(int fieldNumber, MessageNano value) throws IOException {
        writeTag(fieldNumber, 3);
        writeGroupNoTag(value);
        writeTag(fieldNumber, 4);
    }

    public void writeGroupLite(int fieldNumber, MessageLite value) throws IOException {
        CodedOutputStream codedOutputStream2 = getCodedOutputStream();
        codedOutputStream2.writeGroup(fieldNumber, value);
        codedOutputStream2.flush();
        this.codedOutputStreamPosition = this.buffer.position();
    }

    public void writeMessage(int fieldNumber, MessageNano value) throws IOException {
        writeTag(fieldNumber, 2);
        writeMessageNoTag(value);
    }

    public void writeMessageLite(int fieldNumber, MessageLite value) throws IOException {
        CodedOutputStream codedOutputStream2 = getCodedOutputStream();
        codedOutputStream2.writeMessage(fieldNumber, value);
        codedOutputStream2.flush();
        this.codedOutputStreamPosition = this.buffer.position();
    }

    public <K, V> void writeMapEntry(int fieldNumber, MapEntryLite<K, V> defaultEntry, Map.Entry<K, V> entry) throws IOException {
        CodedOutputStream codedOutputStream2 = getCodedOutputStream();
        defaultEntry.serializeTo(codedOutputStream2, fieldNumber, entry.getKey(), entry.getValue());
        codedOutputStream2.flush();
        this.codedOutputStreamPosition = this.buffer.position();
    }

    public void writeBytes(int fieldNumber, byte[] value) throws IOException {
        writeTag(fieldNumber, 2);
        writeBytesNoTag(value);
    }

    public void writeUInt32(int fieldNumber, int value) throws IOException {
        writeTag(fieldNumber, 0);
        writeUInt32NoTag(value);
    }

    public void writeEnum(int fieldNumber, int value) throws IOException {
        writeTag(fieldNumber, 0);
        writeEnumNoTag(value);
    }

    public void writeSFixed32(int fieldNumber, int value) throws IOException {
        writeTag(fieldNumber, 5);
        writeSFixed32NoTag(value);
    }

    public void writeSFixed64(int fieldNumber, long value) throws IOException {
        writeTag(fieldNumber, 1);
        writeSFixed64NoTag(value);
    }

    public void writeSInt32(int fieldNumber, int value) throws IOException {
        writeTag(fieldNumber, 0);
        writeSInt32NoTag(value);
    }

    public void writeSInt64(int fieldNumber, long value) throws IOException {
        writeTag(fieldNumber, 0);
        writeSInt64NoTag(value);
    }

    public void writeMessageSetExtension(int fieldNumber, MessageNano value) throws IOException {
        writeTag(1, 3);
        writeUInt32(2, fieldNumber);
        writeMessage(3, value);
        writeTag(1, 4);
    }

    public void writeRawMessageSetExtension(int fieldNumber, byte[] value) throws IOException {
        writeTag(1, 3);
        writeUInt32(2, fieldNumber);
        writeTag(3, 2);
        writeRawBytes(value);
        writeTag(1, 4);
    }

    public void writeDoubleNoTag(double value) throws IOException {
        writeRawLittleEndian64(Double.doubleToLongBits(value));
    }

    public void writeFloatNoTag(float value) throws IOException {
        writeRawLittleEndian32(Float.floatToIntBits(value));
    }

    public void writeUInt64NoTag(long value) throws IOException {
        writeRawVarint64(value);
    }

    public void writeInt64NoTag(long value) throws IOException {
        writeRawVarint64(value);
    }

    public void writeInt32NoTag(int value) throws IOException {
        if (value >= 0) {
            writeRawVarint32(value);
        } else {
            writeRawVarint64((long) value);
        }
    }

    public void writeFixed64NoTag(long value) throws IOException {
        writeRawLittleEndian64(value);
    }

    public void writeFixed32NoTag(int value) throws IOException {
        writeRawLittleEndian32(value);
    }

    public void writeBoolNoTag(boolean value) throws IOException {
        writeRawByte((int) (value));
    }

    public void writeStringNoTag(String value) throws IOException {
        try {
            int minLengthVarIntSize = computeRawVarint32Size(value.length());
            if (minLengthVarIntSize == computeRawVarint32Size(value.length() * 3)) {
                int oldPosition = this.buffer.position();
                if (this.buffer.remaining() >= minLengthVarIntSize) {
                    this.buffer.position(oldPosition + minLengthVarIntSize);
                    encode(value, this.buffer);
                    int newPosition = this.buffer.position();
                    this.buffer.position(oldPosition);
                    writeRawVarint32((newPosition - oldPosition) - minLengthVarIntSize);
                    this.buffer.position(newPosition);
                    return;
                }
                throw new OutOfSpaceException(oldPosition + minLengthVarIntSize, this.buffer.limit());
            }
            writeRawVarint32(encodedLength(value));
            encode(value, this.buffer);
        } catch (BufferOverflowException e) {
            OutOfSpaceException outOfSpaceException = new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
            outOfSpaceException.initCause(e);
            throw outOfSpaceException;
        }
    }

    private static int encodedLength(CharSequence sequence) {
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;
        while (i < utf16Length && sequence.charAt(i) < 128) {
            i++;
        }
        while (true) {
            if (i < utf16Length) {
                char c = sequence.charAt(i);
                if (c >= 2048) {
                    utf8Length += encodedLengthGeneral(sequence, i);
                    break;
                }
                utf8Length += (127 - c) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (utf8Length >= utf16Length) {
            return utf8Length;
        }
        StringBuilder sb = new StringBuilder(54);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(((long) utf8Length) + 4294967296L);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        int i = start;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 2048) {
                utf8Length += (127 - c) >>> 31;
            } else {
                utf8Length += 2;
                if (55296 <= c && c <= 57343) {
                    if (Character.codePointAt(sequence, i) >= 65536) {
                        i++;
                    } else {
                        StringBuilder sb = new StringBuilder(39);
                        sb.append("Unpaired surrogate at index ");
                        sb.append(i);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }
            i++;
        }
        return utf8Length;
    }

    private static void encode(CharSequence sequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(encode(sequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException boe = new BufferOverflowException();
                boe.initCause(e);
                throw boe;
            }
        } else {
            encodeDirect(sequence, byteBuffer);
        }
    }

    private static void encodeDirect(CharSequence sequence, ByteBuffer byteBuffer) {
        int utf16Length = sequence.length();
        int i = 0;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 128) {
                byteBuffer.put((byte) c);
            } else if (c < 2048) {
                byteBuffer.put((byte) ((c >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE));
                byteBuffer.put((byte) (128 | (c & '?')));
            } else if (c < 55296 || 57343 < c) {
                byteBuffer.put((byte) ((c >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE));
                byteBuffer.put((byte) (((c >>> 6) & 63) | 128));
                byteBuffer.put((byte) (128 | (c & '?')));
            } else {
                if (i + 1 != sequence.length()) {
                    i++;
                    char charAt = sequence.charAt(i);
                    char low = charAt;
                    if (Character.isSurrogatePair(c, charAt)) {
                        int codePoint = Character.toCodePoint(c, low);
                        byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                        byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                        byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                        byteBuffer.put((byte) (128 | (codePoint & 63)));
                    }
                }
                StringBuilder sb = new StringBuilder(39);
                sb.append("Unpaired surrogate at index ");
                sb.append(i - 1);
                throw new IllegalArgumentException(sb.toString());
            }
            i++;
        }
    }

    private static int encode(CharSequence sequence, byte[] bytes, int offset, int length) {
        int utf16Length = sequence.length();
        int j = offset;
        int i = 0;
        int limit = offset + length;
        while (i < utf16Length && i + j < limit) {
            char charAt = sequence.charAt(i);
            char c = charAt;
            if (charAt >= 128) {
                break;
            }
            bytes[j + i] = (byte) c;
            i++;
        }
        if (i == utf16Length) {
            return j + utf16Length;
        }
        int j2 = j + i;
        while (i < utf16Length) {
            char c2 = sequence.charAt(i);
            if (c2 < 128 && j2 < limit) {
                bytes[j2] = (byte) c2;
                j2++;
            } else if (c2 < 2048 && j2 <= limit - 2) {
                int j3 = j2 + 1;
                bytes[j2] = (byte) ((c2 >>> 6) | ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE);
                j2 = j3 + 1;
                bytes[j3] = (byte) ((c2 & '?') | 128);
            } else if ((c2 < 55296 || 57343 < c2) && j2 <= limit - 3) {
                int j4 = j2 + 1;
                bytes[j2] = (byte) ((c2 >>> 12) | ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE);
                int j5 = j4 + 1;
                bytes[j4] = (byte) (((c2 >>> 6) & 63) | 128);
                bytes[j5] = (byte) ((c2 & '?') | 128);
                j2 = j5 + 1;
            } else if (j2 <= limit - 4) {
                if (i + 1 != sequence.length()) {
                    i++;
                    char charAt2 = sequence.charAt(i);
                    char low = charAt2;
                    if (Character.isSurrogatePair(c2, charAt2)) {
                        int codePoint = Character.toCodePoint(c2, low);
                        int j6 = j2 + 1;
                        bytes[j2] = (byte) ((codePoint >>> 18) | 240);
                        int j7 = j6 + 1;
                        bytes[j6] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int j8 = j7 + 1;
                        bytes[j7] = (byte) (((codePoint >>> 6) & 63) | 128);
                        j2 = j8 + 1;
                        bytes[j8] = (byte) ((codePoint & 63) | 128);
                    }
                }
                StringBuilder sb = new StringBuilder(39);
                sb.append("Unpaired surrogate at index ");
                sb.append(i - 1);
                throw new IllegalArgumentException(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Failed writing ");
                sb2.append(c2);
                sb2.append(" at index ");
                sb2.append(j2);
                throw new ArrayIndexOutOfBoundsException(sb2.toString());
            }
            i++;
        }
        return j2;
    }

    public void writeGroupNoTag(MessageNano value) throws IOException {
        value.writeTo(this);
    }

    public void writeGroupNoTag(MessageLite value) throws IOException {
        CodedOutputStream codedOutputStream2 = getCodedOutputStream();
        value.writeTo(codedOutputStream2);
        codedOutputStream2.flush();
        this.codedOutputStreamPosition = this.buffer.position();
    }

    public void writeMessageNoTag(MessageNano value) throws IOException {
        writeRawVarint32(value.getCachedSize());
        value.writeTo(this);
    }

    public void writeMessageNoTag(MessageLite value) throws IOException {
        writeRawVarint32(value.getSerializedSize());
        CodedOutputStream codedOutputStream2 = getCodedOutputStream();
        value.writeTo(codedOutputStream2);
        codedOutputStream2.flush();
        this.codedOutputStreamPosition = this.buffer.position();
    }

    public void writeBytesNoTag(byte[] value) throws IOException {
        writeRawVarint32(value.length);
        writeRawBytes(value);
    }

    public void writeUInt32NoTag(int value) throws IOException {
        writeRawVarint32(value);
    }

    public void writeEnumNoTag(int value) throws IOException {
        writeRawVarint32(value);
    }

    public void writeSFixed32NoTag(int value) throws IOException {
        writeRawLittleEndian32(value);
    }

    public void writeSFixed64NoTag(long value) throws IOException {
        writeRawLittleEndian64(value);
    }

    public void writeSInt32NoTag(int value) throws IOException {
        writeRawVarint32(encodeZigZag32(value));
    }

    public void writeSInt64NoTag(long value) throws IOException {
        writeRawVarint64(encodeZigZag64(value));
    }

    public static int computeDoubleSize(int fieldNumber, double value) {
        return computeTagSize(fieldNumber) + computeDoubleSizeNoTag(value);
    }

    public static int computeFloatSize(int fieldNumber, float value) {
        return computeTagSize(fieldNumber) + computeFloatSizeNoTag(value);
    }

    public static int computeUInt64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeUInt64SizeNoTag(value);
    }

    public static int computeInt64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeInt64SizeNoTag(value);
    }

    public static int computeInt32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeInt32SizeNoTag(value);
    }

    public static int computeFixed64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeFixed64SizeNoTag(value);
    }

    public static int computeFixed32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeFixed32SizeNoTag(value);
    }

    public static int computeBoolSize(int fieldNumber, boolean value) {
        return computeTagSize(fieldNumber) + computeBoolSizeNoTag(value);
    }

    public static int computeStringSize(int fieldNumber, String value) {
        return computeTagSize(fieldNumber) + computeStringSizeNoTag(value);
    }

    public static int computeGroupSize(int fieldNumber, MessageNano value) {
        return (computeTagSize(fieldNumber) * 2) + computeGroupSizeNoTag(value);
    }

    public static int computeMessageSize(int fieldNumber, MessageNano value) {
        return computeTagSize(fieldNumber) + computeMessageSizeNoTag(value);
    }

    public static int computeBytesSize(int fieldNumber, byte[] value) {
        return computeTagSize(fieldNumber) + computeBytesSizeNoTag(value);
    }

    public static int computeUInt32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeUInt32SizeNoTag(value);
    }

    public static int computeEnumSize(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeEnumSizeNoTag(value);
    }

    public static int computeSFixed32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeSFixed32SizeNoTag(value);
    }

    public static int computeSFixed64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeSFixed64SizeNoTag(value);
    }

    public static int computeSInt32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeSInt32SizeNoTag(value);
    }

    public static int computeSInt64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeSInt64SizeNoTag(value);
    }

    public static int computeMessageSetExtensionSize(int fieldNumber, MessageNano value) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, fieldNumber) + computeMessageSize(3, value);
    }

    public static int computeRawMessageSetExtensionSize(int fieldNumber, byte[] value) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, fieldNumber) + computeTagSize(3) + value.length;
    }

    public static int computeDoubleSizeNoTag(double value) {
        return 8;
    }

    public static int computeFloatSizeNoTag(float value) {
        return 4;
    }

    public static int computeUInt64SizeNoTag(long value) {
        return computeRawVarint64Size(value);
    }

    public static int computeInt64SizeNoTag(long value) {
        return computeRawVarint64Size(value);
    }

    public static int computeInt32SizeNoTag(int value) {
        if (value >= 0) {
            return computeRawVarint32Size(value);
        }
        return 10;
    }

    public static int computeFixed64SizeNoTag(long value) {
        return 8;
    }

    public static int computeFixed32SizeNoTag(int value) {
        return 4;
    }

    public static int computeBoolSizeNoTag(boolean value) {
        return 1;
    }

    public static int computeStringSizeNoTag(String value) {
        int length = encodedLength(value);
        return computeRawVarint32Size(length) + length;
    }

    public static int computeGroupSizeNoTag(MessageNano value) {
        return value.getSerializedSize();
    }

    public static int computeMessageSizeNoTag(MessageNano value) {
        int size = value.getSerializedSize();
        return computeRawVarint32Size(size) + size;
    }

    public static int computeBytesSizeNoTag(byte[] value) {
        return computeRawVarint32Size(value.length) + value.length;
    }

    public static int computeUInt32SizeNoTag(int value) {
        return computeRawVarint32Size(value);
    }

    public static int computeEnumSizeNoTag(int value) {
        return computeRawVarint32Size(value);
    }

    public static int computeSFixed32SizeNoTag(int value) {
        return 4;
    }

    public static int computeSFixed64SizeNoTag(long value) {
        return 8;
    }

    public static int computeSInt32SizeNoTag(int value) {
        return computeRawVarint32Size(encodeZigZag32(value));
    }

    public static int computeSInt64SizeNoTag(long value) {
        return computeRawVarint64Size(encodeZigZag64(value));
    }

    public int spaceLeft() {
        return this.buffer.remaining();
    }

    public void checkNoSpaceLeft() {
        if (spaceLeft() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(spaceLeft())));
        }
    }

    public int position() {
        return this.buffer.position();
    }

    public void reset() {
        this.buffer.clear();
    }

    public static class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        OutOfSpaceException(int r3, int r4) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 108(0x6c, float:1.51E-43)
                r0.<init>(r1)
                java.lang.String r1 = "CodedOutputStream was writing to a flat byte array and ran out of space (pos "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = " limit "
                r0.append(r1)
                r0.append(r4)
                java.lang.String r1 = ")."
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException.<init>(int, int):void");
        }
    }

    public void writeRawByte(byte value) throws IOException {
        if (this.buffer.hasRemaining()) {
            this.buffer.put(value);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeRawByte(int value) throws IOException {
        writeRawByte((byte) value);
    }

    public void writeRawBytes(byte[] value) throws IOException {
        writeRawBytes(value, 0, value.length);
    }

    public void writeRawBytes(byte[] value, int offset, int length) throws IOException {
        if (this.buffer.remaining() >= length) {
            this.buffer.put(value, offset, length);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeTag(int fieldNumber, int wireType) throws IOException {
        writeRawVarint32(WireFormatNano.makeTag(fieldNumber, wireType));
    }

    public static int computeTagSize(int fieldNumber) {
        return computeRawVarint32Size(WireFormatNano.makeTag(fieldNumber, 0));
    }

    public void writeRawVarint32(int value) throws IOException {
        while ((value & -128) != 0) {
            writeRawByte((value & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128);
            value >>>= 7;
        }
        writeRawByte(value);
    }

    public static int computeRawVarint32Size(int value) {
        if ((value & -128) == 0) {
            return 1;
        }
        if ((value & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & value) == 0) {
            return 3;
        }
        if ((-268435456 & value) == 0) {
            return 4;
        }
        return 5;
    }

    public void writeRawVarint64(long value) throws IOException {
        while ((-128 & value) != 0) {
            writeRawByte((((int) value) & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE) | 128);
            value >>>= 7;
        }
        writeRawByte((int) value);
    }

    public static int computeRawVarint64Size(long value) {
        if ((-128 & value) == 0) {
            return 1;
        }
        if ((-16384 & value) == 0) {
            return 2;
        }
        if ((-2097152 & value) == 0) {
            return 3;
        }
        if ((-268435456 & value) == 0) {
            return 4;
        }
        if ((-34359738368L & value) == 0) {
            return 5;
        }
        if ((-4398046511104L & value) == 0) {
            return 6;
        }
        if ((-562949953421312L & value) == 0) {
            return 7;
        }
        if ((-72057594037927936L & value) == 0) {
            return 8;
        }
        if ((Long.MIN_VALUE & value) == 0) {
            return 9;
        }
        return 10;
    }

    public void writeRawLittleEndian32(int value) throws IOException {
        if (this.buffer.remaining() >= 4) {
            this.buffer.putInt(value);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeRawLittleEndian64(long value) throws IOException {
        if (this.buffer.remaining() >= 8) {
            this.buffer.putLong(value);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public static int encodeZigZag32(int n) {
        return (n << 1) ^ (n >> 31);
    }

    public static long encodeZigZag64(long n) {
        return (n << 1) ^ (n >> 63);
    }
}
