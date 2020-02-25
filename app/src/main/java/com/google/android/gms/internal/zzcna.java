package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;

@Hide
/* compiled from: FlagValueProvider */
public final class zzcna {
    private boolean zza = false;
    private zzcnb zzb = null;

    public final void zza(Context context) {
        synchronized (this) {
            if (!this.zza) {
                try {
                    this.zzb = zzcnc.asInterface(DynamiteModule.zza(context, DynamiteModule.zze, ModuleDescriptor.MODULE_ID).zza("com.google.android.gms.flags.impl.FlagProviderImpl"));
                    this.zzb.init(zzn.zza(context));
                    this.zza = true;
                } catch (RemoteException | DynamiteModule.zzc e) {
                    Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
                }
            }
        }
    }

    public final <T> T zza(zzcmt<T> zzcmt) {
        synchronized (this) {
            if (this.zza) {
                return zzcmt.zza(this.zzb);
            }
            T zzb2 = zzcmt.zzb();
            return zzb2;
        }
    }
}
