package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbmk;

@Hide
/* compiled from: ClientLibraryUtils */
public final class zzd {
    public static int zza(Context context, String str) {
        Bundle bundle;
        PackageInfo zzc = zzc(context, str);
        if (zzc == null || zzc.applicationInfo == null || (bundle = zzc.applicationInfo.metaData) == null) {
            return -1;
        }
        return bundle.getInt("com.google.android.gms.version", -1);
    }

    @Nullable
    private static PackageInfo zzc(Context context, String str) {
        try {
            return zzbmk.zza(context).zzb(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean zzb(Context context, String str) {
        "com.google.android.gms".equals(str);
        try {
            if ((zzbmk.zza(context).zza(str, 0).flags & 2097152) != 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
