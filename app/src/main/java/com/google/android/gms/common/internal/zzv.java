package com.google.android.gms.common.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;

/* compiled from: GoogleApiAvailabilityCache */
public final class zzv {
    private final SparseIntArray zza;
    private GoogleApiAvailabilityLight zzb;

    public zzv() {
        this(GoogleApiAvailability.getInstance());
    }

    public zzv(@NonNull GoogleApiAvailabilityLight googleApiAvailabilityLight) {
        this.zza = new SparseIntArray();
        zzau.zza(googleApiAvailabilityLight);
        this.zzb = googleApiAvailabilityLight;
    }

    public final int zza(@NonNull Context context, @NonNull Api.Client client) {
        zzau.zza(context);
        zzau.zza(client);
        if (!client.requiresGooglePlayServices()) {
            return 0;
        }
        int zza2 = client.zza();
        int i = this.zza.get(zza2, -1);
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        while (true) {
            if (i2 < this.zza.size()) {
                int keyAt = this.zza.keyAt(i2);
                if (keyAt > zza2 && this.zza.get(keyAt) == 0) {
                    i = 0;
                    break;
                }
                i2++;
            } else {
                break;
            }
        }
        if (i == -1) {
            i = this.zzb.isGooglePlayServicesAvailable(context, zza2);
        }
        this.zza.put(zza2, i);
        return i;
    }

    public final void zza() {
        this.zza.clear();
    }
}
