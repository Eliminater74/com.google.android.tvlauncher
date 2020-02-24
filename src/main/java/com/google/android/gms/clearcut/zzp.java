package com.google.android.gms.clearcut;

import java.util.Comparator;

/* compiled from: Counters */
final class zzp implements Comparator<byte[]> {
    zzp() {
    }

    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException("what are you doing?");
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = (byte[]) obj2;
        if (bArr == null && bArr2 == null) {
            return 0;
        }
        if (bArr == null) {
            return -1;
        }
        if (bArr2 == null) {
            return 1;
        }
        int min = Math.min(bArr.length, bArr2.length);
        for (int i = 0; i < min; i++) {
            if (bArr[i] != bArr2[i]) {
                return bArr[i] - bArr2[i];
            }
        }
        return bArr.length - bArr2.length;
    }
}
