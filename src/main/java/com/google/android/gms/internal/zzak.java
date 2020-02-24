package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* compiled from: ByteArrayPool */
public final class zzak {
    private static final Comparator<byte[]> zze = new zzal();
    private final List<byte[]> zza = new LinkedList();
    private final List<byte[]> zzb = new ArrayList(64);
    private int zzc = 0;
    private final int zzd = 4096;

    public zzak(int i) {
    }

    public final synchronized byte[] zza(int i) {
        for (int i2 = 0; i2 < this.zzb.size(); i2++) {
            byte[] bArr = this.zzb.get(i2);
            if (bArr.length >= i) {
                this.zzc -= bArr.length;
                this.zzb.remove(i2);
                this.zza.remove(bArr);
                return bArr;
            }
        }
        return new byte[i];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void zza(byte[] r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x002e
            int r0 = r3.length     // Catch:{ all -> 0x002b }
            int r1 = r2.zzd     // Catch:{ all -> 0x002b }
            if (r0 <= r1) goto L_0x0009
            goto L_0x002e
        L_0x0009:
            java.util.List<byte[]> r0 = r2.zza     // Catch:{ all -> 0x002b }
            r0.add(r3)     // Catch:{ all -> 0x002b }
            java.util.List<byte[]> r0 = r2.zzb     // Catch:{ all -> 0x002b }
            java.util.Comparator<byte[]> r1 = com.google.android.gms.internal.zzak.zze     // Catch:{ all -> 0x002b }
            int r0 = java.util.Collections.binarySearch(r0, r3, r1)     // Catch:{ all -> 0x002b }
            if (r0 >= 0) goto L_0x001b
            int r0 = -r0
            int r0 = r0 + -1
        L_0x001b:
            java.util.List<byte[]> r1 = r2.zzb     // Catch:{ all -> 0x002b }
            r1.add(r0, r3)     // Catch:{ all -> 0x002b }
            int r0 = r2.zzc     // Catch:{ all -> 0x002b }
            int r3 = r3.length     // Catch:{ all -> 0x002b }
            int r0 = r0 + r3
            r2.zzc = r0     // Catch:{ all -> 0x002b }
            r2.zza()     // Catch:{ all -> 0x002b }
            monitor-exit(r2)
            return
        L_0x002b:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        L_0x002e:
            monitor-exit(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzak.zza(byte[]):void");
    }

    private final synchronized void zza() {
        while (this.zzc > this.zzd) {
            byte[] remove = this.zza.remove(0);
            this.zzb.remove(remove);
            this.zzc -= remove.length;
        }
    }
}
