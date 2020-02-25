package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;

import java.util.ArrayList;
import java.util.List;

public final class Batch extends BasePendingResult<BatchResult> {
    /* access modifiers changed from: private */
    public final PendingResult<?>[] zze;
    /* access modifiers changed from: private */
    public final Object zzf;
    /* access modifiers changed from: private */
    public int zza;
    /* access modifiers changed from: private */
    public boolean zzb;
    /* access modifiers changed from: private */
    public boolean zzd;

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzf = new Object();
        this.zza = list.size();
        this.zze = new PendingResult[this.zza];
        if (list.isEmpty()) {
            zza(new BatchResult(Status.zza, this.zze));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult<?> pendingResult = list.get(i);
            this.zze[i] = pendingResult;
            pendingResult.zza(new zza(this));
        }
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zza zza2) {
        this(list, googleApiClient);
    }

    static /* synthetic */ int zzb(Batch batch) {
        int i = batch.zza;
        batch.zza = i - 1;
        return i;
    }

    public final void cancel() {
        super.cancel();
        for (PendingResult<?> cancel : this.zze) {
            cancel.cancel();
        }
    }

    /* renamed from: createFailedResult */
    public final BatchResult zza(Status status) {
        return new BatchResult(status, this.zze);
    }

    public static final class Builder {
        private List<PendingResult<?>> zza = new ArrayList();
        private GoogleApiClient zzb;

        public Builder(GoogleApiClient googleApiClient) {
            this.zzb = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.zza.size());
            this.zza.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zza, this.zzb, null);
        }
    }
}
