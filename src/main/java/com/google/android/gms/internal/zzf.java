package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: CacheDispatcher */
final class zzf implements zzt {
    private final Map<String, List<zzr<?>>> zza = new HashMap();
    private final zzd zzb;

    zzf(zzd zzd) {
        this.zzb = zzd;
    }

    public final void zza(zzr<?> zzr, zzx<?> zzx) {
        List<zzr> remove;
        if (zzx.zzb == null || zzx.zzb.zza()) {
            zza(zzr);
            return;
        }
        String zzc = zzr.zzc();
        synchronized (this) {
            remove = this.zza.remove(zzc);
        }
        if (remove != null) {
            if (zzaf.zza) {
                zzaf.zza("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(remove.size()), zzc);
            }
            for (zzr zza2 : remove) {
                this.zzb.zze.zza(zza2, zzx);
            }
        }
    }

    public final synchronized void zza(zzr<?> zzr) {
        String zzc = zzr.zzc();
        List remove = this.zza.remove(zzc);
        if (remove != null && !remove.isEmpty()) {
            if (zzaf.zza) {
                zzaf.zza("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(remove.size()), zzc);
            }
            zzr zzr2 = (zzr) remove.remove(0);
            this.zza.put(zzc, remove);
            zzr2.zza((zzt) this);
            try {
                this.zzb.zzc.put(zzr2);
            } catch (InterruptedException e) {
                zzaf.zzc("Couldn't add request to queue. %s", e.toString());
                Thread.currentThread().interrupt();
                this.zzb.zza();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003c, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0054, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean zzb(com.google.android.gms.internal.zzr<?> r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.lang.String r0 = r6.zzc()     // Catch:{ all -> 0x0055 }
            java.util.Map<java.lang.String, java.util.List<com.google.android.gms.internal.zzr<?>>> r1 = r5.zza     // Catch:{ all -> 0x0055 }
            boolean r1 = r1.containsKey(r0)     // Catch:{ all -> 0x0055 }
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x003d
            java.util.Map<java.lang.String, java.util.List<com.google.android.gms.internal.zzr<?>>> r1 = r5.zza     // Catch:{ all -> 0x0055 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x0055 }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x0055 }
            if (r1 != 0) goto L_0x0020
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0055 }
            r1.<init>()     // Catch:{ all -> 0x0055 }
        L_0x0020:
            java.lang.String r4 = "waiting-for-response"
            r6.zza(r4)     // Catch:{ all -> 0x0055 }
            r1.add(r6)     // Catch:{ all -> 0x0055 }
            java.util.Map<java.lang.String, java.util.List<com.google.android.gms.internal.zzr<?>>> r6 = r5.zza     // Catch:{ all -> 0x0055 }
            r6.put(r0, r1)     // Catch:{ all -> 0x0055 }
            boolean r6 = com.google.android.gms.internal.zzaf.zza     // Catch:{ all -> 0x0055 }
            if (r6 == 0) goto L_0x003b
            java.lang.String r6 = "Request for cacheKey=%s is in flight, putting on hold."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x0055 }
            r1[r3] = r0     // Catch:{ all -> 0x0055 }
            com.google.android.gms.internal.zzaf.zzb(r6, r1)     // Catch:{ all -> 0x0055 }
        L_0x003b:
            monitor-exit(r5)
            return r2
        L_0x003d:
            java.util.Map<java.lang.String, java.util.List<com.google.android.gms.internal.zzr<?>>> r1 = r5.zza     // Catch:{ all -> 0x0055 }
            r4 = 0
            r1.put(r0, r4)     // Catch:{ all -> 0x0055 }
            r6.zza(r5)     // Catch:{ all -> 0x0055 }
            boolean r6 = com.google.android.gms.internal.zzaf.zza     // Catch:{ all -> 0x0055 }
            if (r6 == 0) goto L_0x0053
            java.lang.String r6 = "new request, sending to network %s"
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x0055 }
            r1[r3] = r0     // Catch:{ all -> 0x0055 }
            com.google.android.gms.internal.zzaf.zzb(r6, r1)     // Catch:{ all -> 0x0055 }
        L_0x0053:
            monitor-exit(r5)
            return r3
        L_0x0055:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzf.zzb(com.google.android.gms.internal.zzr):boolean");
    }
}
