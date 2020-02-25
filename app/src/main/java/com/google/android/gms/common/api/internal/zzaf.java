package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: ConnectionlessInProgressCalls */
public final class zzaf {
    /* access modifiers changed from: private */
    public final Map<BasePendingResult<?>, Boolean> zza = Collections.synchronizedMap(new WeakHashMap());
    /* access modifiers changed from: private */
    public final Map<TaskCompletionSource<?>, Boolean> zzb = Collections.synchronizedMap(new WeakHashMap());

    /* access modifiers changed from: package-private */
    public final void zza(BasePendingResult<? extends Result> basePendingResult, boolean z) {
        this.zza.put(basePendingResult, Boolean.valueOf(z));
        basePendingResult.zza(new zzag(this, basePendingResult));
    }

    /* access modifiers changed from: package-private */
    public final <TResult> void zza(TaskCompletionSource<TResult> taskCompletionSource, boolean z) {
        this.zzb.put(taskCompletionSource, Boolean.valueOf(z));
        taskCompletionSource.getTask().addOnCompleteListener(new zzah(this, taskCompletionSource));
    }

    /* access modifiers changed from: package-private */
    public final boolean zza() {
        return !this.zza.isEmpty() || !this.zzb.isEmpty();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzaf.zza(boolean, com.google.android.gms.common.api.Status):void
     arg types: [int, com.google.android.gms.common.api.Status]
     candidates:
      com.google.android.gms.common.api.internal.zzaf.zza(com.google.android.gms.common.api.internal.BasePendingResult<? extends com.google.android.gms.common.api.Result>, boolean):void
      com.google.android.gms.common.api.internal.zzaf.zza(com.google.android.gms.tasks.TaskCompletionSource, boolean):void
      com.google.android.gms.common.api.internal.zzaf.zza(boolean, com.google.android.gms.common.api.Status):void */
    public final void zzb() {
        zza(false, zzbn.zza);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzaf.zza(boolean, com.google.android.gms.common.api.Status):void
     arg types: [int, com.google.android.gms.common.api.Status]
     candidates:
      com.google.android.gms.common.api.internal.zzaf.zza(com.google.android.gms.common.api.internal.BasePendingResult<? extends com.google.android.gms.common.api.Result>, boolean):void
      com.google.android.gms.common.api.internal.zzaf.zza(com.google.android.gms.tasks.TaskCompletionSource, boolean):void
      com.google.android.gms.common.api.internal.zzaf.zza(boolean, com.google.android.gms.common.api.Status):void */
    public final void zzc() {
        zza(true, zzdr.zza);
    }

    private final void zza(boolean z, Status status) {
        HashMap hashMap;
        HashMap hashMap2;
        synchronized (this.zza) {
            hashMap = new HashMap(this.zza);
        }
        synchronized (this.zzb) {
            hashMap2 = new HashMap(this.zzb);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((BasePendingResult) entry.getKey()).zzd(status);
            }
        }
        for (Map.Entry entry2 : hashMap2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }
}
