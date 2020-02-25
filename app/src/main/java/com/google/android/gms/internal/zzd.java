package com.google.android.gms.internal;

import android.os.Process;

import java.util.concurrent.BlockingQueue;

/* compiled from: CacheDispatcher */
public final class zzd extends Thread {
    private static final boolean zza = zzaf.zza;
    /* access modifiers changed from: private */
    public final BlockingQueue<zzr<?>> zzc;
    /* access modifiers changed from: private */
    public final zzaa zze;
    private final BlockingQueue<zzr<?>> zzb;
    private final zzb zzd;
    private final zzf zzg;
    private volatile boolean zzf = false;

    public zzd(BlockingQueue<zzr<?>> blockingQueue, BlockingQueue<zzr<?>> blockingQueue2, zzb zzb2, zzaa zzaa) {
        this.zzb = blockingQueue;
        this.zzc = blockingQueue2;
        this.zzd = zzb2;
        this.zze = zzaa;
        this.zzg = new zzf(this);
    }

    public final void zza() {
        this.zzf = true;
        interrupt();
    }

    public final void run() {
        if (zza) {
            zzaf.zza("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.zzd.zza();
        while (true) {
            try {
                zzb();
            } catch (InterruptedException e) {
                if (this.zzf) {
                    return;
                }
            }
        }
    }

    private final void zzb() throws InterruptedException {
        zzr take = this.zzb.take();
        take.zza("cache-queue-take");
        take.zze();
        zzc zza2 = this.zzd.zza(take.zzc());
        if (zza2 == null) {
            take.zza("cache-miss");
            if (!this.zzg.zzb(take)) {
                this.zzc.put(take);
            }
        } else if (zza2.zza()) {
            take.zza("cache-hit-expired");
            take.zza(zza2);
            if (!this.zzg.zzb(take)) {
                this.zzc.put(take);
            }
        } else {
            take.zza("cache-hit");
            zzx zza3 = take.zza(new zzp(zza2.zza, zza2.zzg));
            take.zza("cache-hit-parsed");
            if (zza2.zzf < System.currentTimeMillis()) {
                take.zza("cache-hit-refresh-needed");
                take.zza(zza2);
                zza3.zzd = true;
                if (!this.zzg.zzb(take)) {
                    this.zze.zza(take, zza3, new zze(this, take));
                    return;
                }
            }
            this.zze.zza(take, zza3);
        }
    }
}
