package com.google.android.gms.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import android.support.p001v4.p003os.BuildCompat;
import android.util.Log;

import com.google.android.gms.internal.zzbmk;
import com.google.android.gms.people.PeopleConstants;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: WorkSourceUtil */
public final class zzw {
    private static final int zza = Process.myUid();
    private static final Method zzb = zza();
    private static final Method zzc = zzb();
    private static final Method zzd = zzc();
    private static final Method zze = zzd();
    private static final Method zzf = zze();
    private static final Method zzg = zzf();
    private static final Method zzh = zzg();

    private static WorkSource zza(int i, String str) {
        WorkSource workSource = new WorkSource();
        zza(workSource, i, str);
        return workSource;
    }

    @Nullable
    public static WorkSource zza(Context context, @Nullable String str) {
        if (context == null || context.getPackageManager() == null || str == null) {
            return null;
        }
        try {
            ApplicationInfo zza2 = zzbmk.zza(context).zza(str, 0);
            if (zza2 != null) {
                return zza(zza2.uid, str);
            }
            String valueOf = String.valueOf(str);
            Log.e("WorkSourceUtil", valueOf.length() != 0 ? "Could not get applicationInfo from package: ".concat(valueOf) : new String("Could not get applicationInfo from package: "));
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf2 = String.valueOf(str);
            Log.e("WorkSourceUtil", valueOf2.length() != 0 ? "Could not find package: ".concat(valueOf2) : new String("Could not find package: "));
            return null;
        }
    }

    private static void zza(WorkSource workSource, int i, @Nullable String str) {
        if (zzc != null) {
            if (str == null) {
                str = "";
            }
            try {
                zzc.invoke(workSource, Integer.valueOf(i), str);
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        } else {
            Method method = zzb;
            if (method != null) {
                try {
                    method.invoke(workSource, Integer.valueOf(i));
                } catch (Exception e2) {
                    Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e2);
                }
            }
        }
    }

    private static int zzb(WorkSource workSource) {
        Method method = zzd;
        if (method != null) {
            try {
                return ((Integer) method.invoke(workSource, new Object[0])).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    @Nullable
    private static String zza(WorkSource workSource, int i) {
        Method method = zzf;
        if (method == null) {
            return null;
        }
        try {
            return (String) method.invoke(workSource, Integer.valueOf(i));
        } catch (Exception e) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            return null;
        }
    }

    public static List<String> zza(@Nullable WorkSource workSource) {
        int zzb2 = workSource == null ? 0 : zzb(workSource);
        if (zzb2 == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < zzb2; i++) {
            String zza2 = zza(workSource, i);
            if (!zzs.zza(zza2)) {
                arrayList.add(zza2);
            }
        }
        return arrayList;
    }

    public static boolean zza(Context context) {
        if (context == null || context.getPackageManager() == null || zzbmk.zza(context).zza("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    private static Method zza() {
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzb() {
        if (zzp.zzd()) {
            try {
                return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
            } catch (Exception e) {
            }
        }
        return null;
    }

    private static Method zzc() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzd() {
        try {
            return WorkSource.class.getMethod(PeopleConstants.Endpoints.ENDPOINT_GET, Integer.TYPE);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zze() {
        if (zzp.zzd()) {
            try {
                return WorkSource.class.getMethod("getName", Integer.TYPE);
            } catch (Exception e) {
            }
        }
        return null;
    }

    private static final Method zzf() {
        if (BuildCompat.isAtLeastP()) {
            try {
                return WorkSource.class.getMethod("createWorkChain", new Class[0]);
            } catch (Exception e) {
                Log.w("WorkSourceUtil", "Missing WorkChain API createWorkChain", e);
            }
        }
        return null;
    }

    @SuppressLint({"PrivateApi"})
    private static final Method zzg() {
        if (BuildCompat.isAtLeastP()) {
            try {
                return Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", Integer.TYPE, String.class);
            } catch (Exception e) {
                Log.w("WorkSourceUtil", "Missing WorkChain class", e);
            }
        }
        return null;
    }
}
