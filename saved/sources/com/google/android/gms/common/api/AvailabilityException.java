package com.google.android.gms.common.api;

import android.support.p001v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import java.util.ArrayList;

public class AvailabilityException extends Exception {
    private final ArrayMap<zzi<?>, ConnectionResult> zza;

    @Hide
    public AvailabilityException(ArrayMap<zzi<?>, ConnectionResult> arrayMap) {
        this.zza = arrayMap;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends Api.ApiOptions> googleApi) {
        zzi<? extends Api.ApiOptions> zzd = googleApi.zzd();
        zzau.zzb(this.zza.get(zzd) != null, "The given API was not part of the availability request.");
        return this.zza.get(zzd);
    }

    @Hide
    public final ArrayMap<zzi<?>, ConnectionResult> zza() {
        return this.zza;
    }

    public String getMessage() {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (zzi next : this.zza.keySet()) {
            ConnectionResult connectionResult = this.zza.get(next);
            if (connectionResult.isSuccess()) {
                z = false;
            }
            String zza2 = next.zza();
            String valueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(zza2).length() + 2 + String.valueOf(valueOf).length());
            sb.append(zza2);
            sb.append(": ");
            sb.append(valueOf);
            arrayList.add(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        if (z) {
            sb2.append("None of the queried APIs are available. ");
        } else {
            sb2.append("Some of the queried APIs are unavailable. ");
        }
        sb2.append(TextUtils.join("; ", arrayList));
        return sb2.toString();
    }
}
