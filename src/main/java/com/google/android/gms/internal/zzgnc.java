package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: ByteString */
final class zzgnc implements Iterator {
    private int zza = 0;
    private final int zzb = this.zzc.zza();
    private final /* synthetic */ zzgnb zzc;

    zzgnc(zzgnb zzgnb) {
        this.zzc = zzgnb;
    }

    public final boolean hasNext() {
        return this.zza < this.zzb;
    }

    private final byte zza() {
        try {
            zzgnb zzgnb = this.zzc;
            int i = this.zza;
            this.zza = i + 1;
            return zzgnb.zza(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return Byte.valueOf(zza());
    }
}
