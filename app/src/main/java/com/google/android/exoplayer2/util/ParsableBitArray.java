package com.google.android.exoplayer2.util;

import android.support.p001v4.view.MotionEventCompat;

import com.google.common.primitives.UnsignedBytes;

public final class ParsableBitArray {
    public byte[] data;
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;

    public ParsableBitArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    public ParsableBitArray(byte[] data2) {
        this(data2, data2.length);
    }

    public ParsableBitArray(byte[] data2, int limit) {
        this.data = data2;
        this.byteLimit = limit;
    }

    public void reset(byte[] data2) {
        reset(data2, data2.length);
    }

    public void reset(ParsableByteArray parsableByteArray) {
        reset(parsableByteArray.data, parsableByteArray.limit());
        setPosition(parsableByteArray.getPosition() * 8);
    }

    public void reset(byte[] data2, int limit) {
        this.data = data2;
        this.byteOffset = 0;
        this.bitOffset = 0;
        this.byteLimit = limit;
    }

    public int bitsLeft() {
        return ((this.byteLimit - this.byteOffset) * 8) - this.bitOffset;
    }

    public int getPosition() {
        return (this.byteOffset * 8) + this.bitOffset;
    }

    public void setPosition(int position) {
        this.byteOffset = position / 8;
        this.bitOffset = position - (this.byteOffset * 8);
        assertValidOffset();
    }

    public int getBytePosition() {
        Assertions.checkState(this.bitOffset == 0);
        return this.byteOffset;
    }

    public void skipBit() {
        int i = this.bitOffset + 1;
        this.bitOffset = i;
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset++;
        }
        assertValidOffset();
    }

    public void skipBits(int numBits) {
        int numBytes = numBits / 8;
        this.byteOffset += numBytes;
        this.bitOffset += numBits - (numBytes * 8);
        int i = this.bitOffset;
        if (i > 7) {
            this.byteOffset++;
            this.bitOffset = i - 8;
        }
        assertValidOffset();
    }

    public boolean readBit() {
        boolean returnValue = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return returnValue;
    }

    public int readBits(int numBits) {
        int i;
        if (numBits == 0) {
            return 0;
        }
        int returnValue = 0;
        this.bitOffset += numBits;
        while (true) {
            i = this.bitOffset;
            if (i <= 8) {
                break;
            }
            this.bitOffset = i - 8;
            byte[] bArr = this.data;
            int i2 = this.byteOffset;
            this.byteOffset = i2 + 1;
            returnValue |= (bArr[i2] & UnsignedBytes.MAX_VALUE) << this.bitOffset;
        }
        byte[] bArr2 = this.data;
        int i3 = this.byteOffset;
        int returnValue2 = (returnValue | ((bArr2[i3] & UnsignedBytes.MAX_VALUE) >> (8 - i))) & (-1 >>> (32 - numBits));
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset = i3 + 1;
        }
        assertValidOffset();
        return returnValue2;
    }

    /* JADX INFO: Multiple debug info for r1v2 int: [D('i' int), D('bitsLeft' int)] */
    public void readBits(byte[] buffer, int offset, int numBits) {
        int to = (numBits >> 3) + offset;
        for (int i = offset; i < to; i++) {
            byte[] bArr = this.data;
            int i2 = this.byteOffset;
            this.byteOffset = i2 + 1;
            byte b = bArr[i2];
            int i3 = this.bitOffset;
            buffer[i] = (byte) (b << i3);
            buffer[i] = (byte) (((255 & bArr[this.byteOffset]) >> (8 - i3)) | buffer[i]);
        }
        int bitsLeft = numBits & 7;
        if (bitsLeft != 0) {
            buffer[to] = (byte) (buffer[to] & (255 >> bitsLeft));
            int i4 = this.bitOffset;
            if (i4 + bitsLeft > 8) {
                byte b2 = buffer[to];
                byte[] bArr2 = this.data;
                int i5 = this.byteOffset;
                this.byteOffset = i5 + 1;
                buffer[to] = (byte) (b2 | ((bArr2[i5] & UnsignedBytes.MAX_VALUE) << i4));
                this.bitOffset = i4 - 8;
            }
            this.bitOffset += bitsLeft;
            byte[] bArr3 = this.data;
            int i6 = this.byteOffset;
            byte b3 = 255 & bArr3[i6];
            int i7 = this.bitOffset;
            buffer[to] = (byte) (buffer[to] | ((byte) ((b3 >> (8 - i7)) << (8 - bitsLeft))));
            if (i7 == 8) {
                this.bitOffset = 0;
                this.byteOffset = i6 + 1;
            }
            assertValidOffset();
        }
    }

    public void byteAlign() {
        if (this.bitOffset != 0) {
            this.bitOffset = 0;
            this.byteOffset++;
            assertValidOffset();
        }
    }

    public void readBytes(byte[] buffer, int offset, int length) {
        Assertions.checkState(this.bitOffset == 0);
        System.arraycopy(this.data, this.byteOffset, buffer, offset, length);
        this.byteOffset += length;
        assertValidOffset();
    }

    public void skipBytes(int length) {
        Assertions.checkState(this.bitOffset == 0);
        this.byteOffset += length;
        assertValidOffset();
    }

    public void putInt(int value, int numBits) {
        int remainingBitsToRead = numBits;
        if (numBits < 32) {
            value &= (1 << numBits) - 1;
        }
        int firstByteReadSize = Math.min(8 - this.bitOffset, numBits);
        int i = this.bitOffset;
        int firstByteRightPaddingSize = (8 - i) - firstByteReadSize;
        int firstByteBitmask = (MotionEventCompat.ACTION_POINTER_INDEX_MASK >> i) | ((1 << firstByteRightPaddingSize) - 1);
        byte[] bArr = this.data;
        int i2 = this.byteOffset;
        bArr[i2] = (byte) (bArr[i2] & firstByteBitmask);
        bArr[i2] = (byte) (bArr[i2] | ((value >>> (numBits - firstByteReadSize)) << firstByteRightPaddingSize));
        int remainingBitsToRead2 = remainingBitsToRead - firstByteReadSize;
        int currentByteIndex = i2 + 1;
        while (remainingBitsToRead2 > 8) {
            this.data[currentByteIndex] = (byte) (value >>> (remainingBitsToRead2 - 8));
            remainingBitsToRead2 -= 8;
            currentByteIndex++;
        }
        int lastByteRightPaddingSize = 8 - remainingBitsToRead2;
        byte[] bArr2 = this.data;
        bArr2[currentByteIndex] = (byte) (bArr2[currentByteIndex] & ((1 << lastByteRightPaddingSize) - 1));
        bArr2[currentByteIndex] = (byte) (bArr2[currentByteIndex] | ((value & ((1 << remainingBitsToRead2) - 1)) << lastByteRightPaddingSize));
        skipBits(numBits);
        assertValidOffset();
    }

    private void assertValidOffset() {
        int i;
        int i2 = this.byteOffset;
        Assertions.checkState(i2 >= 0 && (i2 < (i = this.byteLimit) || (i2 == i && this.bitOffset == 0)));
    }
}
