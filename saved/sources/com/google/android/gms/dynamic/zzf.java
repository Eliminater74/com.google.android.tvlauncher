package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/* compiled from: DeferredLifecycleHelper */
final class zzf implements View.OnClickListener {
    private final /* synthetic */ Context zza;
    private final /* synthetic */ Intent zzb;

    zzf(Context context, Intent intent) {
        this.zza = context;
        this.zzb = intent;
    }

    public final void onClick(View view) {
        try {
            this.zza.startActivity(this.zzb);
        } catch (ActivityNotFoundException e) {
            Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e);
        }
    }
}
