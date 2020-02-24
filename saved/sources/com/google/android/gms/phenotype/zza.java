package com.google.android.gms.phenotype;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: ConfigurationContentLoader */
final class zza extends ContentObserver {
    private final /* synthetic */ ConfigurationContentLoader zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zza(ConfigurationContentLoader configurationContentLoader, Handler handler) {
        super(null);
        this.zza = configurationContentLoader;
    }

    public final void onChange(boolean z) {
        this.zza.invalidateCache();
    }
}
