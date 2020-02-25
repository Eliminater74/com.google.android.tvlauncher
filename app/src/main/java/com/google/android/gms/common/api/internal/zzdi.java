package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.app.FragmentActivity;
import android.support.p001v4.util.ArrayMap;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: SupportLifecycleFragmentImpl */
public final class zzdi extends Fragment implements zzcf {
    private static WeakHashMap<FragmentActivity, WeakReference<zzdi>> zza = new WeakHashMap<>();
    /* access modifiers changed from: private */
    public int zzc = 0;
    /* access modifiers changed from: private */
    public Bundle zzd;
    private Map<String, LifecycleCallback> zzb = new ArrayMap();

    public static zzdi zza(FragmentActivity fragmentActivity) {
        zzdi zzdi;
        WeakReference weakReference = zza.get(fragmentActivity);
        if (weakReference != null && (zzdi = (zzdi) weakReference.get()) != null) {
            return zzdi;
        }
        try {
            zzdi zzdi2 = (zzdi) fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
            if (zzdi2 == null || zzdi2.isRemoving()) {
                zzdi2 = new zzdi();
                fragmentActivity.getSupportFragmentManager().beginTransaction().add(zzdi2, "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
            }
            zza.put(fragmentActivity, new WeakReference(zzdi2));
            return zzdi2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", e);
        }
    }

    public final <T extends LifecycleCallback> T zza(String str, Class<T> cls) {
        return (LifecycleCallback) cls.cast(this.zzb.get(str));
    }

    public final void zza(String str, @NonNull LifecycleCallback lifecycleCallback) {
        if (!this.zzb.containsKey(str)) {
            this.zzb.put(str, lifecycleCallback);
            if (this.zzc > 0) {
                new Handler(Looper.getMainLooper()).post(new zzdj(this, lifecycleCallback, str));
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 59);
        sb.append("LifecycleCallback with tag ");
        sb.append(str);
        sb.append(" already added to this fragment.");
        throw new IllegalArgumentException(sb.toString());
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzc = 1;
        this.zzd = bundle;
        for (Map.Entry next : this.zzb.entrySet()) {
            ((LifecycleCallback) next.getValue()).zza(bundle != null ? bundle.getBundle((String) next.getKey()) : null);
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzc = 2;
        for (LifecycleCallback zzb2 : this.zzb.values()) {
            zzb2.zzb();
        }
    }

    public final void onResume() {
        super.onResume();
        this.zzc = 3;
        for (LifecycleCallback zze : this.zzb.values()) {
            zze.zze();
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (LifecycleCallback zza2 : this.zzb.values()) {
            zza2.zza(i, i2, intent);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Map.Entry next : this.zzb.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((LifecycleCallback) next.getValue()).zzb(bundle2);
                bundle.putBundle((String) next.getKey(), bundle2);
            }
        }
    }

    public final void onStop() {
        super.onStop();
        this.zzc = 4;
        for (LifecycleCallback zza2 : this.zzb.values()) {
            zza2.zza();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzc = 5;
        for (LifecycleCallback zzh : this.zzb.values()) {
            zzh.zzh();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (LifecycleCallback zza2 : this.zzb.values()) {
            zza2.zza(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final /* synthetic */ Activity zza() {
        return getActivity();
    }
}
