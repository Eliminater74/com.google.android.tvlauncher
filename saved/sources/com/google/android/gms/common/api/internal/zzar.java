package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.zzau;
import java.lang.ref.WeakReference;

/* compiled from: GoogleApiClientConnecting */
final class zzar implements BaseGmsClient.ConnectionProgressReportCallbacks {
    private final WeakReference<zzap> zza;
    private final Api<?> zzb;
    /* access modifiers changed from: private */
    public final boolean zzc;

    public zzar(zzap zzap, Api<?> api, boolean z) {
        this.zza = new WeakReference<>(zzap);
        this.zzb = api;
        this.zzc = z;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
        zzap zzap = this.zza.get();
        if (zzap != null) {
            zzau.zza(Looper.myLooper() == zzap.zza.zzd.zzc(), (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzap.zzb.lock();
            try {
                if (zzap.zzb(0)) {
                    if (!connectionResult.isSuccess()) {
                        zzap.zzb(connectionResult, this.zzb, this.zzc);
                    }
                    if (zzap.zzd()) {
                        zzap.zze();
                    }
                    zzap.zzb.unlock();
                }
            } finally {
                zzap.zzb.unlock();
            }
        }
    }
}
