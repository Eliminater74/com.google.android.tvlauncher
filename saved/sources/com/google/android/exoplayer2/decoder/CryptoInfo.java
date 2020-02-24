package com.google.android.exoplayer2.decoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import com.google.android.exoplayer2.util.Util;

public final class CryptoInfo {
    public int clearBlocks;
    public int encryptedBlocks;
    private final MediaCodec.CryptoInfo frameworkCryptoInfo = new MediaCodec.CryptoInfo();

    /* renamed from: iv */
    public byte[] f75iv;
    public byte[] key;
    public int mode;
    public int[] numBytesOfClearData;
    public int[] numBytesOfEncryptedData;
    public int numSubSamples;
    private final PatternHolderV24 patternHolder;

    public CryptoInfo() {
        this.patternHolder = Util.SDK_INT >= 24 ? new PatternHolderV24(this.frameworkCryptoInfo) : null;
    }

    public void set(int numSubSamples2, int[] numBytesOfClearData2, int[] numBytesOfEncryptedData2, byte[] key2, byte[] iv, int mode2, int encryptedBlocks2, int clearBlocks2) {
        this.numSubSamples = numSubSamples2;
        this.numBytesOfClearData = numBytesOfClearData2;
        this.numBytesOfEncryptedData = numBytesOfEncryptedData2;
        this.key = key2;
        this.f75iv = iv;
        this.mode = mode2;
        this.encryptedBlocks = encryptedBlocks2;
        this.clearBlocks = clearBlocks2;
        MediaCodec.CryptoInfo cryptoInfo = this.frameworkCryptoInfo;
        cryptoInfo.numSubSamples = numSubSamples2;
        cryptoInfo.numBytesOfClearData = numBytesOfClearData2;
        cryptoInfo.numBytesOfEncryptedData = numBytesOfEncryptedData2;
        cryptoInfo.key = key2;
        cryptoInfo.iv = iv;
        cryptoInfo.mode = mode2;
        if (Util.SDK_INT >= 24) {
            this.patternHolder.set(encryptedBlocks2, clearBlocks2);
        }
    }

    public MediaCodec.CryptoInfo getFrameworkCryptoInfo() {
        return this.frameworkCryptoInfo;
    }

    @Deprecated
    public MediaCodec.CryptoInfo getFrameworkCryptoInfoV16() {
        return getFrameworkCryptoInfo();
    }

    @TargetApi(24)
    private static final class PatternHolderV24 {
        private final MediaCodec.CryptoInfo frameworkCryptoInfo;
        private final MediaCodec.CryptoInfo.Pattern pattern;

        private PatternHolderV24(MediaCodec.CryptoInfo frameworkCryptoInfo2) {
            this.frameworkCryptoInfo = frameworkCryptoInfo2;
            this.pattern = new MediaCodec.CryptoInfo.Pattern(0, 0);
        }

        /* access modifiers changed from: private */
        public void set(int encryptedBlocks, int clearBlocks) {
            this.pattern.set(encryptedBlocks, clearBlocks);
            this.frameworkCryptoInfo.setPattern(this.pattern);
        }
    }
}
