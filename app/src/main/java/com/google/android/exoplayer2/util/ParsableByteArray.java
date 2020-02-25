package com.google.android.exoplayer2.util;

import android.support.annotation.Nullable;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class ParsableByteArray {
    public byte[] data;
    private int limit;
    private int position;

    public ParsableByteArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    public ParsableByteArray(int limit2) {
        this.data = new byte[limit2];
        this.limit = limit2;
    }

    public ParsableByteArray(byte[] data2) {
        this.data = data2;
        this.limit = data2.length;
    }

    public ParsableByteArray(byte[] data2, int limit2) {
        this.data = data2;
        this.limit = limit2;
    }

    public void reset() {
        this.position = 0;
        this.limit = 0;
    }

    public void reset(int limit2) {
        reset(capacity() < limit2 ? new byte[limit2] : this.data, limit2);
    }

    public void reset(byte[] data2) {
        reset(data2, data2.length);
    }

    public void reset(byte[] data2, int limit2) {
        this.data = data2;
        this.limit = limit2;
        this.position = 0;
    }

    public int bytesLeft() {
        return this.limit - this.position;
    }

    public int limit() {
        return this.limit;
    }

    public void setLimit(int limit2) {
        Assertions.checkArgument(limit2 >= 0 && limit2 <= this.data.length);
        this.limit = limit2;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        Assertions.checkArgument(position2 >= 0 && position2 <= this.limit);
        this.position = position2;
    }

    public int capacity() {
        return this.data.length;
    }

    public void skipBytes(int bytes) {
        setPosition(this.position + bytes);
    }

    public void readBytes(ParsableBitArray bitArray, int length) {
        readBytes(bitArray.data, 0, length);
        bitArray.setPosition(0);
    }

    public void readBytes(byte[] buffer, int offset, int length) {
        System.arraycopy(this.data, this.position, buffer, offset, length);
        this.position += length;
    }

    public void readBytes(ByteBuffer buffer, int length) {
        buffer.put(this.data, this.position, length);
        this.position += length;
    }

    public int peekUnsignedByte() {
        return this.data[this.position] & UnsignedBytes.MAX_VALUE;
    }

    public char peekChar() {
        byte[] bArr = this.data;
        int i = this.position;
        return (char) ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << 8));
    }

    public int readUnsignedByte() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        return bArr[i] & UnsignedBytes.MAX_VALUE;
    }

    public int readUnsignedShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        return (bArr[i2] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << 8);
    }

    public int readLittleEndianUnsignedShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i] & UnsignedBytes.MAX_VALUE;
        int i2 = this.position;
        this.position = i2 + 1;
        return ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8) | b;
    }

    public short readShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        return (short) ((bArr[i2] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << 8));
    }

    public short readLittleEndianShort() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i] & UnsignedBytes.MAX_VALUE;
        int i2 = this.position;
        this.position = i2 + 1;
        return (short) (((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8) | b);
    }

    public int readUnsignedInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = (bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.DLE;
        int i3 = this.position;
        this.position = i3 + 1;
        byte b = i2 | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8);
        int i4 = this.position;
        this.position = i4 + 1;
        return (bArr[i4] & UnsignedBytes.MAX_VALUE) | b;
    }

    public int readInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        byte b = (((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) >> 8) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        return (bArr[i3] & UnsignedBytes.MAX_VALUE) | b;
    }

    public int readLittleEndianInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i] & UnsignedBytes.MAX_VALUE;
        int i2 = this.position;
        this.position = i2 + 1;
        byte b2 = b | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        return ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | b2;
    }

    public int readLittleEndianUnsignedInt24() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i] & UnsignedBytes.MAX_VALUE;
        int i2 = this.position;
        this.position = i2 + 1;
        byte b2 = b | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        return ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | b2;
    }

    public long readUnsignedInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = ((((long) bArr[i]) & 255) << 24) | ((((long) bArr[i2]) & 255) << 16);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((((long) bArr[i3]) & 255) << 8);
        int i4 = this.position;
        this.position = i4 + 1;
        return j2 | (255 & ((long) bArr[i4]));
    }

    public long readLittleEndianUnsignedInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = (((long) bArr[i]) & 255) | ((((long) bArr[i2]) & 255) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((((long) bArr[i3]) & 255) << 16);
        int i4 = this.position;
        this.position = i4 + 1;
        return j2 | ((255 & ((long) bArr[i4])) << 24);
    }

    public int readInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = (bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN;
        int i3 = this.position;
        this.position = i3 + 1;
        byte b = i2 | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        int i4 = this.position;
        this.position = i4 + 1;
        byte b2 = b | ((bArr[i4] & UnsignedBytes.MAX_VALUE) << 8);
        int i5 = this.position;
        this.position = i5 + 1;
        return (bArr[i5] & UnsignedBytes.MAX_VALUE) | b2;
    }

    public int readLittleEndianInt() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        byte b = bArr[i] & UnsignedBytes.MAX_VALUE;
        int i2 = this.position;
        this.position = i2 + 1;
        byte b2 = b | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        byte b3 = b2 | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        int i4 = this.position;
        this.position = i4 + 1;
        return ((bArr[i4] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | b3;
    }

    public long readLong() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i2]) & 255) << 48);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((((long) bArr[i3]) & 255) << 40);
        int i4 = this.position;
        this.position = i4 + 1;
        long j3 = j2 | ((((long) bArr[i4]) & 255) << 32);
        int i5 = this.position;
        this.position = i5 + 1;
        long j4 = j3 | ((((long) bArr[i5]) & 255) << 24);
        int i6 = this.position;
        this.position = i6 + 1;
        long j5 = j4 | ((((long) bArr[i6]) & 255) << 16);
        int i7 = this.position;
        this.position = i7 + 1;
        long j6 = j5 | ((((long) bArr[i7]) & 255) << 8);
        int i8 = this.position;
        this.position = i8 + 1;
        return j6 | (255 & ((long) bArr[i8]));
    }

    public long readLittleEndianLong() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        long j = (((long) bArr[i]) & 255) | ((((long) bArr[i2]) & 255) << 8);
        int i3 = this.position;
        this.position = i3 + 1;
        long j2 = j | ((((long) bArr[i3]) & 255) << 16);
        int i4 = this.position;
        this.position = i4 + 1;
        long j3 = j2 | ((((long) bArr[i4]) & 255) << 24);
        int i5 = this.position;
        this.position = i5 + 1;
        long j4 = j3 | ((((long) bArr[i5]) & 255) << 32);
        int i6 = this.position;
        this.position = i6 + 1;
        long j5 = j4 | ((((long) bArr[i6]) & 255) << 40);
        int i7 = this.position;
        this.position = i7 + 1;
        long j6 = j5 | ((((long) bArr[i7]) & 255) << 48);
        int i8 = this.position;
        this.position = i8 + 1;
        return j6 | ((255 & ((long) bArr[i8])) << 56);
    }

    public int readUnsignedFixedPoint1616() {
        byte[] bArr = this.data;
        int i = this.position;
        this.position = i + 1;
        int i2 = this.position;
        this.position = i2 + 1;
        int result = (bArr[i2] & 255) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << 8);
        this.position += 2;
        return result;
    }

    public int readSynchSafeInt() {
        return (readUnsignedByte() << 21) | (readUnsignedByte() << 14) | (readUnsignedByte() << 7) | readUnsignedByte();
    }

    public int readUnsignedIntToInt() {
        int result = readInt();
        if (result >= 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Top bit not zero: ");
        sb.append(result);
        throw new IllegalStateException(sb.toString());
    }

    public int readLittleEndianUnsignedIntToInt() {
        int result = readLittleEndianInt();
        if (result >= 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Top bit not zero: ");
        sb.append(result);
        throw new IllegalStateException(sb.toString());
    }

    public long readUnsignedLongToLong() {
        long result = readLong();
        if (result >= 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append("Top bit not zero: ");
        sb.append(result);
        throw new IllegalStateException(sb.toString());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public String readString(int length) {
        return readString(length, Charset.forName("UTF-8"));
    }

    public String readString(int length, Charset charset) {
        String result = new String(this.data, this.position, length, charset);
        this.position += length;
        return result;
    }

    public String readNullTerminatedString(int length) {
        if (length == 0) {
            return "";
        }
        int stringLength = length;
        int lastIndex = (this.position + length) - 1;
        if (lastIndex < this.limit && this.data[lastIndex] == 0) {
            stringLength--;
        }
        String result = Util.fromUtf8Bytes(this.data, this.position, stringLength);
        this.position += length;
        return result;
    }

    @Nullable
    public String readNullTerminatedString() {
        if (bytesLeft() == 0) {
            return null;
        }
        int stringLimit = this.position;
        while (stringLimit < this.limit && this.data[stringLimit] != 0) {
            stringLimit++;
        }
        byte[] bArr = this.data;
        int i = this.position;
        String string = Util.fromUtf8Bytes(bArr, i, stringLimit - i);
        this.position = stringLimit;
        int i2 = this.position;
        if (i2 < this.limit) {
            this.position = i2 + 1;
        }
        return string;
    }

    @Nullable
    public String readLine() {
        if (bytesLeft() == 0) {
            return null;
        }
        int lineLimit = this.position;
        while (lineLimit < this.limit && !Util.isLinebreak(this.data[lineLimit])) {
            lineLimit++;
        }
        int i = this.position;
        if (lineLimit - i >= 3) {
            byte[] bArr = this.data;
            if (bArr[i] == -17 && bArr[i + 1] == -69 && bArr[i + 2] == -65) {
                this.position = i + 3;
            }
        }
        byte[] bArr2 = this.data;
        int i2 = this.position;
        String line = Util.fromUtf8Bytes(bArr2, i2, lineLimit - i2);
        this.position = lineLimit;
        int i3 = this.position;
        int i4 = this.limit;
        if (i3 == i4) {
            return line;
        }
        if (this.data[i3] == 13) {
            this.position = i3 + 1;
            if (this.position == i4) {
                return line;
            }
        }
        byte[] bArr3 = this.data;
        int i5 = this.position;
        if (bArr3[i5] == 10) {
            this.position = i5 + 1;
        }
        return line;
    }

    public long readUtf8EncodedLong() {
        int length = 0;
        long value = (long) this.data[this.position];
        int j = 7;
        while (true) {
            if (j < 0) {
                break;
            } else if ((((long) (1 << j)) & value) != 0) {
                j--;
            } else if (j < 6) {
                value &= (long) ((1 << j) - 1);
                length = 7 - j;
            } else if (j == 7) {
                length = 1;
            }
        }
        if (length != 0) {
            int i = 1;
            while (i < length) {
                byte b = this.data[this.position + i];
                if ((b & 192) == 128) {
                    value = (value << 6) | ((long) (b & 63));
                    i++;
                } else {
                    StringBuilder sb = new StringBuilder(62);
                    sb.append("Invalid UTF-8 sequence continuation byte: ");
                    sb.append(value);
                    throw new NumberFormatException(sb.toString());
                }
            }
            this.position += length;
            return value;
        }
        StringBuilder sb2 = new StringBuilder(55);
        sb2.append("Invalid UTF-8 sequence first byte: ");
        sb2.append(value);
        throw new NumberFormatException(sb2.toString());
    }
}
