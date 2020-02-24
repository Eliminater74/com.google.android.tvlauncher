package com.google.android.gms.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* compiled from: PoolingByteArrayOutputStream */
public final class zzau extends ByteArrayOutputStream {
    private final zzak zza;

    public zzau(zzak zzak, int i) {
        this.zza = zzak;
        this.buf = this.zza.zza(Math.max(i, 256));
    }

    public final void close() throws IOException {
        this.zza.zza(this.buf);
        this.buf = null;
        super.close();
    }

    public final void finalize() {
        this.zza.zza(this.buf);
    }

    private final void zza(int i) {
        if (this.count + i > this.buf.length) {
            byte[] zza2 = this.zza.zza((this.count + i) << 1);
            System.arraycopy(this.buf, 0, zza2, 0, this.count);
            this.zza.zza(this.buf);
            this.buf = zza2;
        }
    }

    public final synchronized void write(byte[] bArr, int i, int i2) {
        zza(i2);
        super.write(bArr, i, i2);
    }

    public final synchronized void write(int i) {
        zza(1);
        super.write(i);
    }
}
