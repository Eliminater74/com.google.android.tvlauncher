package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzcnc;

@Hide
@DynamiteApi
public class FlagProviderImpl extends zzcnc {
    private boolean zza = false;
    private SharedPreferences zzb;

    public void init(IObjectWrapper iObjectWrapper) {
        Context context = (Context) zzn.zza(iObjectWrapper);
        if (!this.zza) {
            try {
                this.zzb = zzj.zza(context.createPackageContext("com.google.android.gms", 0));
                this.zza = true;
            } catch (PackageManager.NameNotFoundException e) {
            } catch (Exception e2) {
                String valueOf = String.valueOf(e2.getMessage());
                Log.w("FlagProviderImpl", valueOf.length() != 0 ? "Could not retrieve sdk flags, continuing with defaults: ".concat(valueOf) : new String("Could not retrieve sdk flags, continuing with defaults: "));
            }
        }
    }

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        if (!this.zza) {
            return z;
        }
        return zzb.zza(this.zzb, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        if (!this.zza) {
            return i;
        }
        return zzd.zza(this.zzb, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        if (!this.zza) {
            return j;
        }
        return zzf.zza(this.zzb, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        if (!this.zza) {
            return str2;
        }
        return zzh.zza(this.zzb, str, str2);
    }
}
