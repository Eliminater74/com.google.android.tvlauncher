package com.google.android.gms.ads.identifier;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.internal.zzgf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Hide
public abstract class AdvertisingIdListenerService extends Service {
    /* access modifiers changed from: private */
    public final Object zzd = new Object();
    /* access modifiers changed from: private */
    public ExecutorService zzb;
    /* access modifiers changed from: private */
    public boolean zze;
    private volatile int zza = -1;
    private IBinder zzc;

    public abstract void onAdvertisingIdInfoChanged(AdvertisingIdClient.Info info);

    public void onCreate() {
        super.onCreate();
        this.zzb = Executors.newSingleThreadExecutor();
        this.zzc = new zza();
    }

    public void onDestroy() {
        synchronized (this.zzd) {
            this.zze = true;
            this.zzb.shutdown();
        }
        super.onDestroy();
    }

    public final IBinder onBind(Intent intent) {
        if ("com.google.android.gms.ads.identifier.BIND_LISTENER".equals(intent.getAction())) {
            return this.zzc;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public final void zza() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.zza) {
            if (zzu.zza(this, callingUid)) {
                this.zza = callingUid;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices.");
        }
    }

    class zza extends zzgf {
        private zza() {
        }

        public final void zza(Bundle bundle) {
            synchronized (AdvertisingIdListenerService.this.zzd) {
                if (!AdvertisingIdListenerService.this.zze) {
                    AdvertisingIdListenerService.this.zza();
                    AdvertisingIdListenerService.this.zzb.execute(new zzc(this, bundle));
                }
            }
        }
    }
}
