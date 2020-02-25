package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.zzv;

import java.util.ArrayList;
import java.util.Map;

/* compiled from: GoogleApiClientConnecting */
final class zzas extends zzaz {
    final /* synthetic */ zzap zza;
    private final Map<Api.Client, zzar> zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzas(zzap zzap, Map<Api.Client, zzar> map) {
        super(zzap, null);
        this.zza = zzap;
        this.zzb = map;
    }

    @WorkerThread
    public final void zza() {
        zzv zzv = new zzv(this.zza.zzd);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client next : this.zzb.keySet()) {
            if (!next.requiresGooglePlayServices() || this.zzb.get(next).zzc) {
                arrayList2.add(next);
            } else {
                arrayList.add(next);
            }
        }
        int i = -1;
        int i2 = 0;
        if (!arrayList.isEmpty()) {
            ArrayList arrayList3 = arrayList;
            int size = arrayList3.size();
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                i = zzv.zza(this.zza.zzc, (Api.Client) obj);
                if (i != 0) {
                    break;
                }
            }
        } else {
            ArrayList arrayList4 = arrayList2;
            int size2 = arrayList4.size();
            while (i2 < size2) {
                Object obj2 = arrayList4.get(i2);
                i2++;
                i = zzv.zza(this.zza.zzc, (Api.Client) obj2);
                if (i == 0) {
                    break;
                }
            }
        }
        if (i != 0) {
            this.zza.zza.zza(new zzat(this, this.zza, new ConnectionResult(i, null)));
            return;
        }
        if (this.zza.zzm) {
            this.zza.zzk.zzd();
        }
        for (Api.Client next2 : this.zzb.keySet()) {
            BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks = this.zzb.get(next2);
            if (!next2.requiresGooglePlayServices() || zzv.zza(this.zza.zzc, next2) == 0) {
                next2.connect(connectionProgressReportCallbacks);
            } else {
                this.zza.zza.zza(new zzau(this, this.zza, connectionProgressReportCallbacks));
            }
        }
    }
}
