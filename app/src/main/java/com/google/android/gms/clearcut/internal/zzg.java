package com.google.android.gms.clearcut.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzgsh;

/* compiled from: ClearcutLoggerApiImpl */
final class zzg extends zzn<Status, zzi> {
    private final LogEventParcelable zza;

    zzg(LogEventParcelable logEventParcelable, GoogleApiClient googleApiClient) {
        super(ClearcutLogger.API, googleApiClient);
        this.zza = logEventParcelable;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzi zzi = (zzi) zzb;
        zzh zzh = new zzh(this);
        try {
            LogEventParcelable logEventParcelable = this.zza;
            if (logEventParcelable.extensionProducer != null && logEventParcelable.logEvent.zzf.length == 0) {
                logEventParcelable.logEvent.zzf = logEventParcelable.extensionProducer.toProtoBytes();
            }
            if (logEventParcelable.clientVisualElementsProducer != null && logEventParcelable.logEvent.zzh.length == 0) {
                logEventParcelable.logEvent.zzh = logEventParcelable.clientVisualElementsProducer.toProtoBytes();
            }
            logEventParcelable.logEventBytes = zzgsh.toByteArray(logEventParcelable.logEvent);
            ((zzq) zzi.zzag()).zza(zzh, this.zza);
        } catch (RuntimeException e) {
            Log.e("ClearcutLoggerApiImpl", "derived ClearcutLogger.MessageProducer ", e);
            zzc(new Status(10, "MessageProducer"));
        }
    }

    @Hide
    public final /* bridge */ /* synthetic */ void zza(Object obj) {
        super.zza((Result) ((Status) obj));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zza(Status status) {
        return status;
    }
}
