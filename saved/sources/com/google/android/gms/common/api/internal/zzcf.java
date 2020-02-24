package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/* compiled from: LifecycleFragment */
public interface zzcf {
    void startActivityForResult(Intent intent, int i);

    Activity zza();

    <T extends LifecycleCallback> T zza(String str, Class<T> cls);

    void zza(String str, @NonNull LifecycleCallback lifecycleCallback);
}
