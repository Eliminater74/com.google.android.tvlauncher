package com.google.android.gms.clearcut;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.clearcut.internal.zzp;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzd;
import com.google.android.gms.internal.zzbkz;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class ClearcutLoggerDebugClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    public ClearcutLoggerDebugClient(Context context) {
        super(context, ClearcutLogger.API, (Api.ApiOptions) null, GoogleApi.zza.zza);
    }

    static class zza extends zzp {
        private final TaskCompletionSource zza;

        private zza(TaskCompletionSource taskCompletionSource) {
            this.zza = taskCompletionSource;
        }

        public final void zza(Status status) throws RemoteException {
            zzdm.zza(status, null, this.zza);
        }

        public final void zzb(Status status) throws RemoteException {
            zzdm.zza(status, null, this.zza);
        }

        public final void zza(Status status, long j) throws RemoteException {
            zzdm.zza(status, Long.valueOf(j), this.zza);
        }

        public final void zza(Status status, CollectForDebugParcelable collectForDebugParcelable) throws RemoteException {
            zzdm.zza(status, collectForDebugParcelable, this.zza);
        }

        public final void zzc(Status status) throws RemoteException {
            zzdm.zza(status, null, this.zza);
        }

        public final void zzb(Status status, long j) throws RemoteException {
            zzdm.zza(status, Long.valueOf(j), this.zza);
        }

        public final void zzb(Status status, CollectForDebugParcelable collectForDebugParcelable) throws RemoteException {
            zzdm.zza(status, collectForDebugParcelable, this.zza);
        }

        public final void zza(Status status, LogEventParcelable[] logEventParcelableArr) throws RemoteException {
            zzdm.zza(status, logEventParcelableArr, this.zza);
        }

        public final void zza(DataHolder dataHolder) {
            zzd zzd = new zzd(dataHolder, LogEventParcelable.CREATOR);
            LogEventParcelable[] logEventParcelableArr = new LogEventParcelable[zzd.getCount()];
            for (int i = 0; i < zzd.getCount(); i++) {
                logEventParcelableArr[i] = (LogEventParcelable) ((zzbkz) zzd.get(i));
            }
            zzd.release();
            zzdm.zza(new Status(dataHolder.getStatusCode()), logEventParcelableArr, this.zza);
        }

        /* synthetic */ zza(TaskCompletionSource taskCompletionSource, zzf zzf) {
            this(taskCompletionSource);
        }
    }

    public Task<Void> forceUpload() {
        return zzb(new zzf(this));
    }

    public Task<Long> startCollectForDebug() {
        return zzb(new zzg(this));
    }

    public Task<CollectForDebugParcelable> startCollectForDebugWithParcelable(CollectForDebugParcelable collectForDebugParcelable) {
        return zzb(new zzh(this, collectForDebugParcelable));
    }

    public Task<Void> stopCollectForDebug() {
        return zzb(new zzi(this));
    }

    public Task<Long> getCollectForDebugExpiryTime() {
        return zza(new zzj(this));
    }

    @Deprecated
    public Task<LogEventParcelable[]> getLogEventParcelables(String str) {
        return zza(new zzk(this, str));
    }

    public Task<CollectForDebugParcelable> getCollectForDebugParcelable() {
        return zza(new zzl(this));
    }

    public Task<LogEventParcelable[]> getLogEventParcelablesAndClear(String str) {
        return zza(new zzm(this, str));
    }
}
