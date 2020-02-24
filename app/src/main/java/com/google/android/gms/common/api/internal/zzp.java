package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: BaseLifecycleHelper */
public abstract class zzp extends LifecycleCallback implements DialogInterface.OnCancelListener {
    protected volatile boolean zza;
    protected final AtomicReference<zzq> zzb;
    protected final GoogleApiAvailability zzc;
    private final Handler zze;

    protected zzp(zzcf zzcf) {
        this(zzcf, GoogleApiAvailability.getInstance());
    }

    /* access modifiers changed from: protected */
    public abstract void zza(ConnectionResult connectionResult, int i);

    /* access modifiers changed from: protected */
    public abstract void zzc();

    private zzp(zzcf zzcf, GoogleApiAvailability googleApiAvailability) {
        super(zzcf);
        this.zzb = new AtomicReference<>(null);
        this.zze = new Handler(Looper.getMainLooper());
        this.zzc = googleApiAvailability;
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), zza(this.zzb.get()));
        zzd();
    }

    public final void zza(Bundle bundle) {
        zzq zzq;
        super.zza(bundle);
        if (bundle != null) {
            AtomicReference<zzq> atomicReference = this.zzb;
            if (bundle.getBoolean("resolving_error", false)) {
                zzq = new zzq(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1));
            } else {
                zzq = null;
            }
            atomicReference.set(zzq);
        }
    }

    public final void zzb(Bundle bundle) {
        super.zzb(bundle);
        zzq zzq = this.zzb.get();
        if (zzq != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zzq.zza());
            bundle.putInt("failed_status", zzq.zzb().getErrorCode());
            bundle.putParcelable("failed_resolution", zzq.zzb().getResolution());
        }
    }

    public void zzb() {
        super.zzb();
        this.zza = true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0061  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(int r4, int r5, android.content.Intent r6) {
        /*
            r3 = this;
            java.util.concurrent.atomic.AtomicReference<com.google.android.gms.common.api.internal.zzq> r0 = r3.zzb
            java.lang.Object r0 = r0.get()
            com.google.android.gms.common.api.internal.zzq r0 = (com.google.android.gms.common.api.internal.zzq) r0
            r1 = 1
            r2 = 0
            if (r4 == r1) goto L_0x0031
            r5 = 2
            if (r4 == r5) goto L_0x0011
            goto L_0x005a
        L_0x0011:
            com.google.android.gms.common.GoogleApiAvailability r4 = r3.zzc
            android.app.Activity r5 = r3.zzg()
            int r4 = r4.isGooglePlayServicesAvailable(r5)
            if (r4 != 0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r1 = 0
        L_0x001f:
            if (r0 != 0) goto L_0x0022
            return
        L_0x0022:
            com.google.android.gms.common.ConnectionResult r5 = r0.zzb()
            int r5 = r5.getErrorCode()
            r6 = 18
            if (r5 != r6) goto L_0x005b
            if (r4 != r6) goto L_0x005b
            return
        L_0x0031:
            r4 = -1
            if (r5 != r4) goto L_0x0035
            goto L_0x005b
        L_0x0035:
            if (r5 != 0) goto L_0x005a
            r4 = 13
            if (r6 == 0) goto L_0x0043
            java.lang.String r5 = "<<ResolutionFailureErrorDetail>>"
            int r4 = r6.getIntExtra(r5, r4)
        L_0x0043:
            com.google.android.gms.common.api.internal.zzq r5 = new com.google.android.gms.common.api.internal.zzq
            com.google.android.gms.common.ConnectionResult r6 = new com.google.android.gms.common.ConnectionResult
            r1 = 0
            r6.<init>(r4, r1)
            int r4 = zza(r0)
            r5.<init>(r6, r4)
            java.util.concurrent.atomic.AtomicReference<com.google.android.gms.common.api.internal.zzq> r4 = r3.zzb
            r4.set(r5)
            r0 = r5
            r1 = 0
            goto L_0x005b
        L_0x005a:
            r1 = 0
        L_0x005b:
            if (r1 == 0) goto L_0x0061
            r3.zzd()
            return
        L_0x0061:
            if (r0 == 0) goto L_0x006f
            com.google.android.gms.common.ConnectionResult r4 = r0.zzb()
            int r5 = r0.zza()
            r3.zza(r4, r5)
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzp.zza(int, int, android.content.Intent):void");
    }

    public void zza() {
        super.zza();
        this.zza = false;
    }

    /* access modifiers changed from: protected */
    public final void zzd() {
        this.zzb.set(null);
        zzc();
    }

    public final void zzb(ConnectionResult connectionResult, int i) {
        zzq zzq = new zzq(connectionResult, i);
        if (this.zzb.compareAndSet(null, zzq)) {
            this.zze.post(new zzr(this, zzq));
        }
    }

    private static int zza(@Nullable zzq zzq) {
        if (zzq == null) {
            return -1;
        }
        return zzq.zza();
    }
}
