package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.p001v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

/* compiled from: ConnectionlessGoogleApiClient */
final class zzae implements OnCompleteListener<Map<zzi<?>, String>> {
    private zzda zza;
    private final /* synthetic */ zzab zzb;

    zzae(zzab zzab, zzda zzda) {
        this.zzb = zzab;
        this.zza = zzda;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        this.zza.zza();
    }

    public final void onComplete(@NonNull Task<Map<zzi<?>, String>> task) {
        this.zzb.zzf.lock();
        try {
            if (!this.zzb.zzn) {
                this.zza.zza();
                return;
            }
            if (task.isSuccessful()) {
                Map unused = this.zzb.zzp = new ArrayMap(this.zzb.zzb.size());
                for (zzaa zzd : this.zzb.zzb.values()) {
                    this.zzb.zzp.put(zzd.zzd(), ConnectionResult.zza);
                }
            } else if (task.getException() instanceof AvailabilityException) {
                AvailabilityException availabilityException = (AvailabilityException) task.getException();
                if (this.zzb.zzl) {
                    Map unused2 = this.zzb.zzp = new ArrayMap(this.zzb.zzb.size());
                    for (zzaa zzaa : this.zzb.zzb.values()) {
                        zzi zzd2 = zzaa.zzd();
                        ConnectionResult connectionResult = availabilityException.getConnectionResult(zzaa);
                        if (this.zzb.zza(zzaa, connectionResult)) {
                            this.zzb.zzp.put(zzd2, new ConnectionResult(16));
                        } else {
                            this.zzb.zzp.put(zzd2, connectionResult);
                        }
                    }
                } else {
                    Map unused3 = this.zzb.zzp = availabilityException.zza();
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                Map unused4 = this.zzb.zzp = Collections.emptyMap();
            }
            if (this.zzb.zzd()) {
                this.zzb.zzo.putAll(this.zzb.zzp);
                if (this.zzb.zzk() == null) {
                    this.zzb.zzi();
                    this.zzb.zzj();
                    this.zzb.zzi.signalAll();
                }
            }
            this.zza.zza();
            this.zzb.zzf.unlock();
        } finally {
            this.zzb.zzf.unlock();
        }
    }
}
