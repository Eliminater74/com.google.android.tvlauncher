package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* compiled from: DeferredLifecycleHelper */
final class zze implements zzi {
    private final /* synthetic */ FrameLayout zza;
    private final /* synthetic */ LayoutInflater zzb;
    private final /* synthetic */ ViewGroup zzc;
    private final /* synthetic */ Bundle zzd;
    private final /* synthetic */ zza zze;

    zze(zza zza2, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zze = zza2;
        this.zza = frameLayout;
        this.zzb = layoutInflater;
        this.zzc = viewGroup;
        this.zzd = bundle;
    }

    public final int zza() {
        return 2;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zza.removeAllViews();
        this.zza.addView(this.zze.zza.onCreateView(this.zzb, this.zzc, this.zzd));
    }
}
