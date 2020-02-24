package com.google.android.gms.ads.identifier;

import android.os.Bundle;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdListenerService;
import com.google.android.tvrecommendations.shared.util.Constants;

/* compiled from: AdvertisingIdListenerService */
final class zzc implements Runnable {
    private final /* synthetic */ Bundle zza;
    private final /* synthetic */ AdvertisingIdListenerService.zza zzb;

    zzc(AdvertisingIdListenerService.zza zza2, Bundle bundle) {
        this.zzb = zza2;
        this.zza = bundle;
    }

    public final void run() {
        AdvertisingIdListenerService.this.onAdvertisingIdInfoChanged(new AdvertisingIdClient.Info(this.zza.getString(Constants.COLUMN_AD_ID), this.zza.getBoolean("lat_enabled")));
    }
}
