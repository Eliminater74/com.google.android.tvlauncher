package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;

import com.google.android.gms.common.util.zzp;

/* compiled from: PackageManagerWrapper */
public final class zzbmj {
    private final Context zza;

    public zzbmj(Context context) {
        this.zza = context;
    }

    public final ApplicationInfo zza(String str, int i) throws PackageManager.NameNotFoundException {
        return this.zza.getPackageManager().getApplicationInfo(str, i);
    }

    public final PackageInfo zzb(String str, int i) throws PackageManager.NameNotFoundException {
        return this.zza.getPackageManager().getPackageInfo(str, i);
    }

    public final String[] zza(int i) {
        return this.zza.getPackageManager().getPackagesForUid(i);
    }

    @TargetApi(19)
    public final boolean zza(int i, String str) {
        if (zzp.zze()) {
            try {
                ((AppOpsManager) this.zza.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException e) {
                return false;
            }
        } else {
            String[] packagesForUid = this.zza.getPackageManager().getPackagesForUid(i);
            if (!(str == null || packagesForUid == null)) {
                for (String equals : packagesForUid) {
                    if (str.equals(equals)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public final int zza(String str) {
        return this.zza.checkCallingOrSelfPermission(str);
    }

    public final int zza(String str, String str2) {
        return this.zza.getPackageManager().checkPermission(str, str2);
    }

    public final CharSequence zzb(String str) throws PackageManager.NameNotFoundException {
        return this.zza.getPackageManager().getApplicationLabel(this.zza.getPackageManager().getApplicationInfo(str, 0));
    }

    public final boolean zza() {
        String nameForUid;
        if (Binder.getCallingUid() == Process.myUid()) {
            return zzbmi.zza(this.zza);
        }
        if (!zzp.zzk() || (nameForUid = this.zza.getPackageManager().getNameForUid(Binder.getCallingUid())) == null) {
            return false;
        }
        return this.zza.getPackageManager().isInstantApp(nameForUid);
    }
}
