package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult;

/* compiled from: Batch */
final class zza implements PendingResult.zza {
    private final /* synthetic */ Batch zza;

    zza(Batch batch) {
        this.zza = batch;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0067, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.common.api.Status r5) {
        /*
            r4 = this;
            com.google.android.gms.common.api.Batch r0 = r4.zza
            java.lang.Object r0 = r0.zzf
            monitor-enter(r0)
            com.google.android.gms.common.api.Batch r1 = r4.zza     // Catch:{ all -> 0x0068 }
            boolean r1 = r1.isCanceled()     // Catch:{ all -> 0x0068 }
            if (r1 == 0) goto L_0x0011
            monitor-exit(r0)     // Catch:{ all -> 0x0068 }
            return
        L_0x0011:
            boolean r1 = r5.isCanceled()     // Catch:{ all -> 0x0068 }
            r2 = 1
            if (r1 == 0) goto L_0x001e
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            boolean unused = r5.zzd = true     // Catch:{ all -> 0x0068 }
            goto L_0x0029
        L_0x001e:
            boolean r5 = r5.isSuccess()     // Catch:{ all -> 0x0068 }
            if (r5 != 0) goto L_0x0029
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            boolean unused = r5.zzb = true     // Catch:{ all -> 0x0068 }
        L_0x0029:
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            com.google.android.gms.common.api.Batch.zzb(r5)     // Catch:{ all -> 0x0068 }
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            int r5 = r5.zza     // Catch:{ all -> 0x0068 }
            if (r5 != 0) goto L_0x0066
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            boolean r5 = r5.zzd     // Catch:{ all -> 0x0068 }
            if (r5 == 0) goto L_0x0044
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            com.google.android.gms.common.api.zza.super.cancel()     // Catch:{ all -> 0x0068 }
            goto L_0x0066
        L_0x0044:
            com.google.android.gms.common.api.Batch r5 = r4.zza     // Catch:{ all -> 0x0068 }
            boolean r5 = r5.zzb     // Catch:{ all -> 0x0068 }
            if (r5 == 0) goto L_0x0054
            com.google.android.gms.common.api.Status r5 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x0068 }
            r1 = 13
            r5.<init>(r1)     // Catch:{ all -> 0x0068 }
            goto L_0x0056
        L_0x0054:
            com.google.android.gms.common.api.Status r5 = com.google.android.gms.common.api.Status.zza     // Catch:{ all -> 0x0068 }
        L_0x0056:
            com.google.android.gms.common.api.Batch r1 = r4.zza     // Catch:{ all -> 0x0068 }
            com.google.android.gms.common.api.BatchResult r2 = new com.google.android.gms.common.api.BatchResult     // Catch:{ all -> 0x0068 }
            com.google.android.gms.common.api.Batch r3 = r4.zza     // Catch:{ all -> 0x0068 }
            com.google.android.gms.common.api.PendingResult[] r3 = r3.zze     // Catch:{ all -> 0x0068 }
            r2.<init>(r5, r3)     // Catch:{ all -> 0x0068 }
            r1.zza(r2)     // Catch:{ all -> 0x0068 }
        L_0x0066:
            monitor-exit(r0)     // Catch:{ all -> 0x0068 }
            return
        L_0x0068:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0068 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.zza.zza(com.google.android.gms.common.api.Status):void");
    }
}
