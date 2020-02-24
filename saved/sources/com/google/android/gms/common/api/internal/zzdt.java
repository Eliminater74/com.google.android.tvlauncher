package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import com.google.android.gms.common.api.ResultStore;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/* compiled from: UnconsumedApiCalls */
final class zzdt implements IBinder.DeathRecipient, zzdu {
    private final WeakReference<BasePendingResult<?>> zza;
    private final WeakReference<ResultStore> zzb;
    private final WeakReference<IBinder> zzc;

    private zzdt(BasePendingResult<?> basePendingResult, ResultStore resultStore, IBinder iBinder) {
        this.zzb = new WeakReference<>(resultStore);
        this.zza = new WeakReference<>(basePendingResult);
        this.zzc = new WeakReference<>(iBinder);
    }

    public final void zza(BasePendingResult<?> basePendingResult) {
        zza();
    }

    public final void binderDied() {
        zza();
    }

    private final void zza() {
        BasePendingResult basePendingResult = this.zza.get();
        ResultStore resultStore = this.zzb.get();
        if (!(resultStore == null || basePendingResult == null)) {
            resultStore.remove(basePendingResult.zzb().intValue());
        }
        IBinder iBinder = this.zzc.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
            }
        }
    }

    /* synthetic */ zzdt(BasePendingResult basePendingResult, ResultStore resultStore, IBinder iBinder, zzds zzds) {
        this(basePendingResult, resultStore, iBinder);
    }
}
