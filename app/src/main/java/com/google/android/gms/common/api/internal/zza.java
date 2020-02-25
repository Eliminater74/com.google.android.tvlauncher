package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ActivityLifecycleObserverImpl */
public final class zza extends ActivityLifecycleObserver {
    private final WeakReference<C2021zza> zza;

    public zza(Activity activity) {
        this(C2021zza.zzc(activity));
    }

    @VisibleForTesting(otherwise = 2)
    private zza(C2021zza zza2) {
        this.zza = new WeakReference<>(zza2);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C2021zza zza2 = this.zza.get();
        if (zza2 != null) {
            zza2.zza(runnable);
            return this;
        }
        throw new IllegalStateException("The target activity has already been GC'd");
    }

    @VisibleForTesting(otherwise = 2)
    /* renamed from: com.google.android.gms.common.api.internal.zza$zza  reason: collision with other inner class name */
    /* compiled from: ActivityLifecycleObserverImpl */
    static class C2021zza extends LifecycleCallback {
        private List<Runnable> zza = new ArrayList();

        private C2021zza(zzcf zzcf) {
            super(zzcf);
            this.zzd.zza("LifecycleObserverOnStop", this);
        }

        /* access modifiers changed from: private */
        public static C2021zza zzc(Activity activity) {
            C2021zza zza2;
            synchronized (activity) {
                zzcf zzb = zzb(activity);
                zza2 = (C2021zza) zzb.zza("LifecycleObserverOnStop", C2021zza.class);
                if (zza2 == null) {
                    zza2 = new C2021zza(zzb);
                }
            }
            return zza2;
        }

        /* access modifiers changed from: private */
        public final synchronized void zza(Runnable runnable) {
            this.zza.add(runnable);
        }

        @MainThread
        public final void zza() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zza;
                this.zza = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }
}
