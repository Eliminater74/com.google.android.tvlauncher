package com.google.android.libraries.stitch.util;

import android.support.p001v4.media.session.PlaybackStateCompat;

public enum StorageUnit {
    TERABYTES(1099511627776L) {
        public long convert(long quantity, StorageUnit sourceUnit) {
            return sourceUnit.toTerabytes(quantity);
        }
    },
    GIGABYTES(1073741824) {
        public long convert(long quantity, StorageUnit sourceUnit) {
            return sourceUnit.toGigabytes(quantity);
        }
    },
    MEGABYTES(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
        public long convert(long quantity, StorageUnit sourceUnit) {
            return sourceUnit.toMegabytes(quantity);
        }
    },
    KILOBYTES(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
        public long convert(long quantity, StorageUnit sourceUnit) {
            return sourceUnit.toKilobytes(quantity);
        }
    },
    BYTES(1) {
        public long convert(long quantity, StorageUnit sourceUnit) {
            return sourceUnit.toBytes(quantity);
        }
    };
    
    long numBytes;

    public abstract long convert(long j, StorageUnit storageUnit);

    private StorageUnit(long numBytes2) {
        this.numBytes = numBytes2;
    }

    public long toBytes(long quantity) {
        return this.numBytes * quantity;
    }

    public long toKilobytes(long quantity) {
        return (this.numBytes * quantity) / KILOBYTES.numBytes;
    }

    public long toMegabytes(long quantity) {
        return (this.numBytes * quantity) / MEGABYTES.numBytes;
    }

    public long toGigabytes(long quantity) {
        return (this.numBytes * quantity) / GIGABYTES.numBytes;
    }

    public long toTerabytes(long quantity) {
        return (this.numBytes * quantity) / TERABYTES.numBytes;
    }
}
