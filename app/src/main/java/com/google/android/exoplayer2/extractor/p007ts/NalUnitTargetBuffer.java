package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.util.Assertions;

import java.util.Arrays;

/* renamed from: com.google.android.exoplayer2.extractor.ts.NalUnitTargetBuffer */
final class NalUnitTargetBuffer {
    private final int targetType;
    public byte[] nalData;
    public int nalLength;
    private boolean isCompleted;
    private boolean isFilling;

    public NalUnitTargetBuffer(int targetType2, int initialCapacity) {
        this.targetType = targetType2;
        this.nalData = new byte[(initialCapacity + 3)];
        this.nalData[2] = 1;
    }

    public void reset() {
        this.isFilling = false;
        this.isCompleted = false;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void startNalUnit(int type) {
        boolean z = true;
        Assertions.checkState(!this.isFilling);
        if (type != this.targetType) {
            z = false;
        }
        this.isFilling = z;
        if (this.isFilling) {
            this.nalLength = 3;
            this.isCompleted = false;
        }
    }

    public void appendToNalUnit(byte[] data, int offset, int limit) {
        if (this.isFilling) {
            int readLength = limit - offset;
            byte[] bArr = this.nalData;
            int length = bArr.length;
            int i = this.nalLength;
            if (length < i + readLength) {
                this.nalData = Arrays.copyOf(bArr, (i + readLength) * 2);
            }
            System.arraycopy(data, offset, this.nalData, this.nalLength, readLength);
            this.nalLength += readLength;
        }
    }

    public boolean endNalUnit(int discardPadding) {
        if (!this.isFilling) {
            return false;
        }
        this.nalLength -= discardPadding;
        this.isFilling = false;
        this.isCompleted = true;
        return true;
    }
}
