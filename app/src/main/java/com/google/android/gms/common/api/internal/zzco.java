package com.google.android.gms.common.api.internal;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.concurrent.CancellationException;

/* compiled from: MakeGmsCoreAvailableLifecycleHelper */
public class zzco extends zzp {
    private TaskCompletionSource<Void> zze = new TaskCompletionSource<>();

    private zzco(zzcf zzcf) {
        super(zzcf);
        this.zzd.zza("GmsAvailabilityHelper", this);
    }

    public static zzco zza(Activity activity) {
        zzcf zzb = zzb(activity);
        zzco zzco = (zzco) zzb.zza("GmsAvailabilityHelper", zzco.class);
        if (zzco == null) {
            return new zzco(zzb);
        }
        if (zzco.zze.getTask().isComplete()) {
            zzco.zze = new TaskCompletionSource<>();
        }
        return zzco;
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        this.zze.setException(zzb.zza(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    /* access modifiers changed from: protected */
    public final void zzc() {
        int isGooglePlayServicesAvailable = this.zzc.isGooglePlayServicesAvailable(this.zzd.zza());
        if (isGooglePlayServicesAvailable == 0) {
            this.zze.setResult(null);
        } else if (!this.zze.getTask().isComplete()) {
            zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        }
    }

    public final void zzh() {
        super.zzh();
        this.zze.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    public final Task<Void> zzf() {
        return this.zze.getTask();
    }
}
