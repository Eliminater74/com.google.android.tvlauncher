package com.google.android.gms.common.api.internal;

import android.util.SparseArray;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.internal.zzau;

/* compiled from: ResultStoreImpl */
public final class zzcx extends ResultStore {
    private final Object zza = new Object();
    private final SparseArray<PendingResult<?>> zzb = new SparseArray<>();
    private final SparseArray<ResultCallbacks<?>> zzc = new SparseArray<>();

    public final boolean hasPendingResult(int i) {
        boolean z;
        synchronized (this.zza) {
            z = this.zzb.get(i) != null;
        }
        return z;
    }

    public final <R extends Result> void zza(int i, PendingResult<R> pendingResult) {
        synchronized (this.zza) {
            boolean z = true;
            boolean z2 = this.zzb.get(i) == null;
            StringBuilder sb = new StringBuilder(96);
            sb.append("ResultStore ResultId must be unique within the current activity. Violating ResultId: ");
            sb.append(i);
            zzau.zzb(z2, sb.toString());
            if (pendingResult.zzb() != null) {
                z = false;
            }
            zzau.zzb(z, "PendingResult has already been saved.");
            pendingResult.zzb(i);
            this.zzb.put(i, pendingResult);
            ResultCallbacks resultCallbacks = this.zzc.get(i);
            if (resultCallbacks != null) {
                pendingResult.setResultCallback(resultCallbacks);
            }
        }
    }

    public final void remove(int i) {
        synchronized (this.zza) {
            PendingResult pendingResult = this.zzb.get(i);
            if (pendingResult != null) {
                this.zzb.remove(i);
                if (this.zzc.get(i) != null) {
                    pendingResult.setResultCallback(null);
                }
            }
        }
    }

    public final void setResultCallbacks(int i, ResultCallbacks resultCallbacks) {
        zzau.zza(resultCallbacks, "ResultCallbacks cannot be null.");
        synchronized (this.zza) {
            this.zzc.put(i, resultCallbacks);
            PendingResult pendingResult = this.zzb.get(i);
            if (pendingResult != null) {
                pendingResult.setResultCallback(resultCallbacks);
            }
        }
    }

    public final void zza() {
        synchronized (this.zza) {
            this.zzc.clear();
            for (int i = 0; i < this.zzb.size(); i++) {
                this.zzb.valueAt(i).setResultCallback(null);
            }
        }
    }

    public final void zzb(Object obj) {
        synchronized (this.zza) {
            for (int i = 0; i < this.zzb.size(); i++) {
                this.zzb.valueAt(i).cancel();
            }
        }
        zza(obj);
    }
}
