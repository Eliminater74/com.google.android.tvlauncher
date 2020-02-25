package com.google.android.exoplayer2.extractor;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.io.IOException;
import java.util.Arrays;

public interface TrackOutput {
    void format(Format format);

    int sampleData(ExtractorInput extractorInput, int i, boolean z) throws IOException, InterruptedException;

    void sampleData(ParsableByteArray parsableByteArray, int i);

    void sampleMetadata(long j, int i, int i2, int i3, @Nullable CryptoData cryptoData);

    public static final class CryptoData {
        public final int clearBlocks;
        public final int cryptoMode;
        public final int encryptedBlocks;
        public final byte[] encryptionKey;

        public CryptoData(int cryptoMode2, byte[] encryptionKey2, int encryptedBlocks2, int clearBlocks2) {
            this.cryptoMode = cryptoMode2;
            this.encryptionKey = encryptionKey2;
            this.encryptedBlocks = encryptedBlocks2;
            this.clearBlocks = clearBlocks2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CryptoData other = (CryptoData) obj;
            if (this.cryptoMode == other.cryptoMode && this.encryptedBlocks == other.encryptedBlocks && this.clearBlocks == other.clearBlocks && Arrays.equals(this.encryptionKey, other.encryptionKey)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((this.cryptoMode * 31) + Arrays.hashCode(this.encryptionKey)) * 31) + this.encryptedBlocks) * 31) + this.clearBlocks;
        }
    }
}
