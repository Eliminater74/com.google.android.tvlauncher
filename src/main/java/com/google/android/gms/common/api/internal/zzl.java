package com.google.android.gms.common.api.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.zzp;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: BackgroundDetector */
public final class zzl implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final zzl zza = new zzl();
    private final AtomicBoolean zzb = new AtomicBoolean();
    private final AtomicBoolean zzc = new AtomicBoolean();
    private final ArrayList<zzm> zzd = new ArrayList<>();
    private boolean zze = false;

    private zzl() {
    }

    public static zzl zza() {
        return zza;
    }

    public static void zza(Application application) {
        synchronized (zza) {
            if (!zza.zze) {
                application.registerActivityLifecycleCallbacks(zza);
                application.registerComponentCallbacks(zza);
                zza.zze = true;
            }
        }
    }

    @TargetApi(16)
    public final boolean zza(boolean z) {
        if (!this.zzc.get()) {
            if (!zzp.zzb()) {
                return true;
            }
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzc.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzb.set(true);
            }
        }
        return this.zzb.get();
    }

    public final boolean zzb() {
        return this.zzb.get();
    }

    public final void zza(zzm zzm) {
        synchronized (zza) {
            this.zzd.add(zzm);
        }
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (compareAndSet) {
            zzb(false);
        }
    }

    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (compareAndSet) {
            zzb(false);
        }
    }

    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzb.compareAndSet(false, true)) {
            this.zzc.set(true);
            zzb(true);
        }
    }

    private final void zzb(boolean z) {
        synchronized (zza) {
            ArrayList arrayList = this.zzd;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzm) obj).zza(z);
            }
        }
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }
}
