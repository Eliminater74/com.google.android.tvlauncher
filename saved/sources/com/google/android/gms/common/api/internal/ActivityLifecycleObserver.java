package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.Hide;

@Hide
@KeepForSdkWithMembers
public abstract class ActivityLifecycleObserver {
    public abstract ActivityLifecycleObserver onStopCallOnce(Runnable runnable);

    /* renamed from: of */
    public static final ActivityLifecycleObserver m40of(Activity activity) {
        return new zza(activity);
    }
}
