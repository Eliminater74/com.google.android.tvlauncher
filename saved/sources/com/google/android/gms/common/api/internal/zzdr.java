package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: UnconsumedApiCalls */
public final class zzdr {
    public static final Status zza = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zzc = new BasePendingResult[0];
    final Set<BasePendingResult<?>> zzb = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzdu zzd = new zzds(this);
    /* access modifiers changed from: private */
    public ResultStore zze;
    private final Map<Api.zzc<?>, Api.Client> zzf;

    public zzdr(Map<Api.zzc<?>, Api.Client> map) {
        this.zzf = map;
    }

    /* access modifiers changed from: package-private */
    public final void zza(BasePendingResult<? extends Result> basePendingResult) {
        this.zzb.add(basePendingResult);
        basePendingResult.zza(this.zzd);
    }

    public final void zza() {
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.zzb.toArray(zzc)) {
            basePendingResult.zza((zzdu) null);
            if (basePendingResult.zzb() != null) {
                basePendingResult.setResultCallback(null);
                IBinder serviceBrokerBinder = this.zzf.get(((zzn) basePendingResult).zzc()).getServiceBrokerBinder();
                ResultStore resultStore = this.zze;
                if (basePendingResult.zze()) {
                    basePendingResult.zza(new zzdt(basePendingResult, resultStore, serviceBrokerBinder, null));
                } else if (serviceBrokerBinder == null || !serviceBrokerBinder.isBinderAlive()) {
                    basePendingResult.zza((zzdu) null);
                    basePendingResult.cancel();
                    resultStore.remove(basePendingResult.zzb().intValue());
                } else {
                    zzdt zzdt = new zzdt(basePendingResult, resultStore, serviceBrokerBinder, null);
                    basePendingResult.zza(zzdt);
                    try {
                        serviceBrokerBinder.linkToDeath(zzdt, 0);
                    } catch (RemoteException e) {
                        basePendingResult.cancel();
                        resultStore.remove(basePendingResult.zzb().intValue());
                    }
                }
                this.zzb.remove(basePendingResult);
            } else if (basePendingResult.zzf()) {
                this.zzb.remove(basePendingResult);
            }
        }
    }

    public final void zzb() {
        for (BasePendingResult zzd2 : (BasePendingResult[]) this.zzb.toArray(zzc)) {
            zzd2.zzd(zza);
        }
    }

    public final void zza(ResultStore resultStore) {
        this.zze = resultStore;
    }
}
