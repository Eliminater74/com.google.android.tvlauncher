package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* compiled from: GooglePlayServicesUpdatedReceiver */
public final class zzby extends BroadcastReceiver {
    private Context zza;
    private final zzbz zzb;

    public zzby(zzbz zzbz) {
        this.zzb = zzbz;
    }

    public final void zza(Context context) {
        this.zza = context;
    }

    public final synchronized void zza() {
        if (this.zza != null) {
            this.zza.unregisterReceiver(this);
        }
        this.zza = null;
    }

    public final void onReceive(Context context, Intent intent) {
        String str;
        Uri data = intent.getData();
        if (data != null) {
            str = data.getSchemeSpecificPart();
        } else {
            str = null;
        }
        if ("com.google.android.gms".equals(str)) {
            this.zzb.zza();
            zza();
        }
    }
}
