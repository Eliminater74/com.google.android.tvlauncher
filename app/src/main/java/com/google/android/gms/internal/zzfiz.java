package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: ConfigurationContentLoader */
final class zzfiz extends ContentObserver {
    private final /* synthetic */ zzfiy zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzfiz(zzfiy zzfiy, Handler handler) {
        super(null);
        this.zza = zzfiy;
    }

    public final void onChange(boolean z) {
        this.zza.zzb();
    }
}
