package com.google.android.gms.internal;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

/* compiled from: NetworkDispatcher */
public final class zzn extends Thread {
    private final BlockingQueue<zzr<?>> zza;
    private final zzm zzb;
    private final zzb zzc;
    private final zzaa zzd;
    private volatile boolean zze = false;

    public zzn(BlockingQueue<zzr<?>> blockingQueue, zzm zzm, zzb zzb2, zzaa zzaa) {
        this.zza = blockingQueue;
        this.zzb = zzm;
        this.zzc = zzb2;
        this.zzd = zzaa;
    }

    public final void zza() {
        this.zze = true;
        interrupt();
    }

    public final void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                zzb();
            } catch (InterruptedException e) {
                if (this.zze) {
                    return;
                }
            }
        }
    }

    private final void zzb() throws InterruptedException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        zzr take = this.zza.take();
        try {
            take.zza("network-queue-take");
            take.zze();
            TrafficStats.setThreadStatsTag(take.zzb());
            zzp zza2 = this.zzb.zza(take);
            take.zza("network-http-complete");
            if (!zza2.zze || !take.zzl()) {
                zzx zza3 = take.zza(zza2);
                take.zza("network-parse-complete");
                if (take.zzh() && zza3.zzb != null) {
                    this.zzc.zza(take.zzc(), zza3.zzb);
                    take.zza("network-cache-written");
                }
                take.zzk();
                this.zzd.zza(take, zza3);
                take.zza((zzx<?>) zza3);
                return;
            }
            take.zzb("not-modified");
            take.zzm();
        } catch (zzae e) {
            e.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzd.zza(take, e);
            take.zzm();
        } catch (Exception e2) {
            zzaf.zza(e2, "Unhandled exception %s", e2.toString());
            zzae zzae = new zzae(e2);
            zzae.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzd.zza(take, zzae);
            take.zzm();
        }
    }
}
