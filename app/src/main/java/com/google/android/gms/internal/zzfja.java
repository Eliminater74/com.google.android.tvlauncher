package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.UserManager;
import android.support.p001v4.content.PermissionChecker;
import android.util.Log;

import com.google.android.gsf.Gservices;

/* compiled from: PhenotypeFlag */
public abstract class zzfja<T> {
    private static final Object zzb = new Object();
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzc = null;
    private static boolean zzd = false;
    private static volatile Boolean zze = null;
    private static volatile Boolean zzf = null;
    final String zza;
    private final zzfjh zzg;
    private final String zzh;
    private final T zzi;
    private T zzj;
    private volatile zzfiy zzk;
    private volatile SharedPreferences zzl;

    private zzfja(zzfjh zzfjh, String str, T t) {
        this.zzj = null;
        this.zzk = null;
        this.zzl = null;
        if (zzfjh.zza == null && zzfjh.zzb == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        } else if (zzfjh.zza == null || zzfjh.zzb == null) {
            this.zzg = zzfjh;
            String valueOf = String.valueOf(zzfjh.zzc);
            String valueOf2 = String.valueOf(str);
            this.zzh = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            String valueOf3 = String.valueOf(zzfjh.zzd);
            String valueOf4 = String.valueOf(str);
            this.zza = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
            this.zzi = t;
        } else {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
    }

    /* synthetic */ zzfja(zzfjh zzfjh, String str, Object obj, zzfje zzfje) {
        this(zzfjh, str, obj);
    }

    public static void zza(Context context) {
        synchronized (zzb) {
            if (Build.VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
            }
            if (zzc != context) {
                zze = null;
            }
            zzc = context;
        }
        zzd = false;
    }

    public static void zza() {
        zzd = true;
    }

    public static void zzb(Context context) {
        if (zzc == null && !zzd) {
            zza(context);
        }
    }

    public static void zza(boolean z) {
        zzd = z;
    }

    public static void zzc() {
    }

    private static <V> V zza(zzfjg<V> zzfjg) {
        long clearCallingIdentity;
        try {
            return zzfjg.zza();
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            V zza2 = zzfjg.zza();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return zza2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    static boolean zza(String str, boolean z) {
        if (zzg()) {
            return ((Boolean) zza(new zzfjd(str, false))).booleanValue();
        }
        return false;
    }

    private static boolean zzg() {
        if (zze == null) {
            Context context = zzc;
            boolean z = false;
            if (context == null) {
                return false;
            }
            if (PermissionChecker.checkCallingOrSelfPermission(context, Gservices.PERMISSION_READ_GSERVICES) == 0) {
                z = true;
            }
            zze = Boolean.valueOf(z);
        }
        return zze.booleanValue();
    }

    /* access modifiers changed from: private */
    public static zzfja<String> zzb(zzfjh zzfjh, String str, String str2) {
        return new zzfjf(zzfjh, str, str2);
    }

    /* access modifiers changed from: protected */
    public abstract T zza(SharedPreferences sharedPreferences);

    /* access modifiers changed from: protected */
    public abstract T zza(String str);

    public final T zzb() {
        if (zzd) {
            return this.zzi;
        }
        if (zzc != null) {
            if (this.zzg.zzf) {
                T zzf2 = zzf();
                if (zzf2 != null) {
                    return zzf2;
                }
                T zze2 = zze();
                if (zze2 != null) {
                    return zze2;
                }
            } else {
                T zze3 = zze();
                if (zze3 != null) {
                    return zze3;
                }
                T zzf3 = zzf();
                if (zzf3 != null) {
                    return zzf3;
                }
            }
            return this.zzi;
        }
        throw new IllegalStateException("Must call PhenotypeFlag.init() first");
    }

    @TargetApi(24)
    private final T zze() {
        boolean z;
        if (zza("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            String valueOf = String.valueOf(this.zza);
            Log.w("PhenotypeFlag", valueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(valueOf) : new String("Bypass reading Phenotype values for flag: "));
        } else if (this.zzg.zzb != null) {
            if (this.zzk == null) {
                this.zzk = zzfiy.zza(zzc.getContentResolver(), this.zzg.zzb);
            }
            String str = (String) zza(new zzfjb(this, this.zzk));
            if (str != null) {
                return zza(str);
            }
        } else if (this.zzg.zza != null) {
            if (Build.VERSION.SDK_INT < 24 || zzc.isDeviceProtectedStorage()) {
                z = true;
            } else {
                if (zzf == null || !zzf.booleanValue()) {
                    zzf = Boolean.valueOf(((UserManager) zzc.getSystemService(UserManager.class)).isUserUnlocked());
                }
                z = zzf.booleanValue();
            }
            if (!z) {
                return null;
            }
            if (this.zzl == null) {
                this.zzl = zzc.getSharedPreferences(this.zzg.zza, 0);
            }
            SharedPreferences sharedPreferences = this.zzl;
            if (sharedPreferences.contains(this.zza)) {
                return zza(sharedPreferences);
            }
        }
        return null;
    }

    private final T zzf() {
        String str;
        if (this.zzg.zze || !zzg() || (str = (String) zza(new zzfjc(this))) == null) {
            return null;
        }
        return zza(str);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ String zzd() {
        return zzfij.zza(zzc.getContentResolver(), this.zzh, (String) null);
    }
}
