package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: Gservices */
final class zzfik extends ContentObserver {
    zzfik(Handler handler) {
        super(null);
    }

    public final void onChange(boolean z) {
        zzfij.zze.set(true);
    }
}
