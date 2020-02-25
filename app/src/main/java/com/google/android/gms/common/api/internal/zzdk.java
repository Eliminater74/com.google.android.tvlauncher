package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.p001v4.app.Fragment;

import com.google.android.gms.common.api.ResultStore;

/* compiled from: SupportLifecycleTrackingFragment */
public final class zzdk extends Fragment {
    private zzcx zza = new zzcx();

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.zza.zza();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zza.zzb(getActivity());
    }

    public final ResultStore zza() {
        return this.zza;
    }
}
