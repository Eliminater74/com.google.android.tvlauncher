package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

/* compiled from: GoogleApiClientConnecting */
final class zzav extends zzaz {
    private final ArrayList<Api.Client> zza;
    private final /* synthetic */ zzap zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzav(zzap zzap, ArrayList<Api.Client> arrayList) {
        super(zzap, null);
        this.zzb = zzap;
        this.zza = arrayList;
    }

    @WorkerThread
    public final void zza() {
        this.zzb.zza.zzd.zzc = this.zzb.zzi();
        ArrayList arrayList = this.zza;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Api.Client) obj).getRemoteService(this.zzb.zzo, this.zzb.zza.zzd.zzc);
        }
    }
}
