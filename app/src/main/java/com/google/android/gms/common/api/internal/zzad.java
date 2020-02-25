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
final class zzad implements OnCompleteListener<Map<zzi<?>, String>> {
    private final /* synthetic */ zzab zza;

    private zzad(zzab zzab) {
        this.zza = zzab;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzab.zza(com.google.android.gms.common.api.internal.zzab, boolean):boolean
     arg types: [com.google.android.gms.common.api.internal.zzab, int]
     candidates:
      com.google.android.gms.common.api.internal.zzab.zza(com.google.android.gms.common.api.internal.zzab, com.google.android.gms.common.ConnectionResult):com.google.android.gms.common.ConnectionResult
      com.google.android.gms.common.api.internal.zzab.zza(com.google.android.gms.common.api.internal.zzab, java.util.Map):java.util.Map
      com.google.android.gms.common.api.internal.zzab.zza(com.google.android.gms.common.api.internal.zzaa<?>, com.google.android.gms.common.ConnectionResult):boolean
      com.google.android.gms.common.api.internal.zzab.zza(long, java.util.concurrent.TimeUnit):com.google.android.gms.common.ConnectionResult
      com.google.android.gms.common.api.internal.zzcc.zza(long, java.util.concurrent.TimeUnit):com.google.android.gms.common.ConnectionResult
      com.google.android.gms.common.api.internal.zzab.zza(com.google.android.gms.common.api.internal.zzab, boolean):boolean */
    public final void onComplete(@NonNull Task<Map<zzi<?>, String>> task) {
        this.zza.zzf.lock();
        try {
            if (this.zza.zzn) {
                if (task.isSuccessful()) {
                    Map unused = this.zza.zzo = new ArrayMap(this.zza.zza.size());
                    for (zzaa zzd : this.zza.zza.values()) {
                        this.zza.zzo.put(zzd.zzd(), ConnectionResult.zza);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zza.zzl) {
                        Map unused2 = this.zza.zzo = new ArrayMap(this.zza.zza.size());
                        for (zzaa zzaa : this.zza.zza.values()) {
                            zzi zzd2 = zzaa.zzd();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult(zzaa);
                            if (this.zza.zza(zzaa, connectionResult)) {
                                this.zza.zzo.put(zzd2, new ConnectionResult(16));
                            } else {
                                this.zza.zzo.put(zzd2, connectionResult);
                            }
                        }
                    } else {
                        Map unused3 = this.zza.zzo = availabilityException.zza();
                    }
                    ConnectionResult unused4 = this.zza.zzr = this.zza.zzk();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    Map unused5 = this.zza.zzo = Collections.emptyMap();
                    ConnectionResult unused6 = this.zza.zzr = new ConnectionResult(8);
                }
                if (this.zza.zzp != null) {
                    this.zza.zzo.putAll(this.zza.zzp);
                    ConnectionResult unused7 = this.zza.zzr = this.zza.zzk();
                }
                if (this.zza.zzr == null) {
                    this.zza.zzi();
                    this.zza.zzj();
                } else {
                    boolean unused8 = this.zza.zzn = false;
                    this.zza.zze.zza(this.zza.zzr);
                }
                this.zza.zzi.signalAll();
                this.zza.zzf.unlock();
            }
        } finally {
            this.zza.zzf.unlock();
        }
    }
}
