package com.google.android.gms.phenotype;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.UserManager;
import android.support.p001v4.content.PermissionChecker;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzfij;
import com.google.android.gms.internal.zzfja;
import com.google.android.gsf.Gservices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@KeepForSdk
@Deprecated
public abstract class PhenotypeFlag<T> {
    private static final Object zzb = new Object();
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzc = null;
    private static boolean zzd = false;
    private static Collection<PhenotypeFlag<?>> zze;
    private static Boolean zzf = null;
    final String zza;
    private final Factory zzg;
    private final String zzh;
    private final T zzi;
    private T zzj;

    public interface BytesConverter<T> {
        T fromBytes(byte[] bArr) throws IOException;
    }

    interface zza<V> {
        V zza();
    }

    public static void init(Context context) {
        zzfja.zza(context);
        synchronized (zzb) {
            if (Build.VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
            }
            if (zzc != context) {
                zzf = null;
            }
            zzc = context;
        }
        zzd = false;
    }

    public abstract T fromSharedPreferences(SharedPreferences sharedPreferences);

    public abstract T fromString(String str);

    @KeepForSdk
    public static class Factory {
        /* access modifiers changed from: private */
        public final String zza;
        /* access modifiers changed from: private */
        public final Uri zzb;
        /* access modifiers changed from: private */
        public final String zzc;
        /* access modifiers changed from: private */
        public final String zzd;
        /* access modifiers changed from: private */
        public final boolean zze;
        /* access modifiers changed from: private */
        public final boolean zzf;

        public Factory(String str) {
            this(str, null, "", "", false, false);
        }

        @KeepForSdk
        public Factory(Uri uri) {
            this(null, uri, "", "", false, false);
        }

        private Factory(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
            this.zza = str;
            this.zzb = uri;
            this.zzc = str2;
            this.zzd = str3;
            this.zze = z;
            this.zzf = z2;
        }

        @KeepForSdk
        public Factory withGservicePrefix(String str) {
            boolean z = this.zze;
            if (!z) {
                return new Factory(this.zza, this.zzb, str, this.zzd, z, this.zzf);
            }
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }

        @KeepForSdk
        public Factory withPhenotypePrefix(String str) {
            return new Factory(this.zza, this.zzb, this.zzc, str, this.zze, this.zzf);
        }

        public Factory skipGservices() {
            if (this.zzc.isEmpty()) {
                return new Factory(this.zza, this.zzb, this.zzc, this.zzd, true, this.zzf);
            }
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }

        public PhenotypeFlag<Long> createFlag(String str, long j) {
            return PhenotypeFlag.zzb(this, str, j);
        }

        public PhenotypeFlag<Boolean> createFlag(String str, boolean z) {
            return PhenotypeFlag.zzb(this, str, z);
        }

        public PhenotypeFlag<Integer> createFlag(String str, int i) {
            return PhenotypeFlag.zzb(this, str, i);
        }

        public PhenotypeFlag<Double> createFlag(String str, double d) {
            return PhenotypeFlag.zzb(this, str, d);
        }

        public PhenotypeFlag<Float> createFloatFlag(String str, float f) {
            return PhenotypeFlag.zzb(this, str, f);
        }

        @KeepForSdk
        public PhenotypeFlag<String> createFlag(String str, String str2) {
            return PhenotypeFlag.zzb(this, str, str2);
        }

        public PhenotypeFlag<byte[]> createFlag(String str, byte[] bArr) {
            return PhenotypeFlag.zzb(this, str, bArr);
        }

        public <T> PhenotypeFlag<T> createFlag(String str, T t, BytesConverter<T> bytesConverter) {
            return PhenotypeFlag.zzb(this, str, t, bytesConverter);
        }
    }

    public static void initForTest() {
        zzfja.zza();
        setTestMode(true);
    }

    @KeepForSdk
    public static void maybeInit(Context context) {
        zzfja.zzb(context);
        if (zzc == null && !zzd) {
            init(context);
        }
    }

    public static void setTestMode(boolean z) {
        zzfja.zza(z);
        zzd = z;
    }

    private PhenotypeFlag(Factory factory, String str, T t) {
        this.zzj = null;
        if (factory.zza == null && factory.zzb == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        } else if (factory.zza == null || factory.zzb == null) {
            this.zzg = factory;
            String valueOf = String.valueOf(factory.zzc);
            String valueOf2 = String.valueOf(str);
            this.zzh = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            String valueOf3 = String.valueOf(factory.zzd);
            String valueOf4 = String.valueOf(str);
            this.zza = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
            this.zzi = t;
        } else {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
    }

    public String getMendelFlagName() {
        return this.zza;
    }

    public T getDefaultValue() {
        return this.zzi;
    }

    @KeepForSdk
    public T get() {
        T t = this.zzj;
        if (t != null) {
            return t;
        }
        if (zzd) {
            String valueOf = String.valueOf(this.zza);
            Log.w("PhenotypeFlag", valueOf.length() != 0 ? "Test mode, using default for flag: ".concat(valueOf) : new String("Test mode, using default for flag: "));
            return this.zzi;
        } else if (zzc != null) {
            if (this.zzg.zzf) {
                T zzc2 = zzc();
                if (zzc2 != null) {
                    return zzc2;
                }
                T zzb2 = zzb();
                if (zzb2 != null) {
                    return zzb2;
                }
            } else {
                T zzb3 = zzb();
                if (zzb3 != null) {
                    return zzb3;
                }
                T zzc3 = zzc();
                if (zzc3 != null) {
                    return zzc3;
                }
            }
            return this.zzi;
        } else {
            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
        }
    }

    @TargetApi(24)
    private final T zzb() {
        if (zza("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            String valueOf = String.valueOf(this.zza);
            Log.w("PhenotypeFlag", valueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(valueOf) : new String("Bypass reading Phenotype values for flag: "));
        } else if (this.zzg.zzb != null) {
            String str = (String) zza(new zzaj(this, ConfigurationContentLoader.getLoader(zzc.getContentResolver(), this.zzg.zzb)));
            if (str != null) {
                return fromString(str);
            }
        } else if (this.zzg.zza == null || (Build.VERSION.SDK_INT >= 24 && !zzc.isDeviceProtectedStorage() && !((UserManager) zzc.getSystemService(UserManager.class)).isUserUnlocked())) {
            return null;
        } else {
            SharedPreferences sharedPreferences = zzc.getSharedPreferences(this.zzg.zza, 0);
            if (sharedPreferences.contains(this.zza)) {
                return fromSharedPreferences(sharedPreferences);
            }
        }
        return null;
    }

    private final T zzc() {
        String str;
        if (this.zzg.zze || !zzd() || (str = (String) zza(new zzak(this))) == null) {
            return null;
        }
        return fromString(str);
    }

    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    public void override(T t) {
        this.zzj = t;
        if (zze == null) {
            zze = new ArrayList();
        }
        zze.add(this);
    }

    public void resetOverride() {
        this.zzj = null;
    }

    public static void resetAllOverrides() {
        zzfja.zzc();
        Collection<PhenotypeFlag<?>> collection = zze;
        if (collection != null) {
            for (PhenotypeFlag<?> resetOverride : collection) {
                resetOverride.resetOverride();
            }
            zze.clear();
        }
    }

    private static <V> V zza(zza<V> zza2) {
        long clearCallingIdentity;
        try {
            return zza2.zza();
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            V zza3 = zza2.zza();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return zza3;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    static boolean zza(String str, boolean z) {
        if (zzd()) {
            return ((Boolean) zza(new zzal(str, false))).booleanValue();
        }
        return false;
    }

    private static boolean zzd() {
        if (zzf == null) {
            Context context = zzc;
            boolean z = false;
            if (context == null) {
                return false;
            }
            if (PermissionChecker.checkCallingOrSelfPermission(context, Gservices.PERMISSION_READ_GSERVICES) == 0) {
                z = true;
            }
            zzf = Boolean.valueOf(z);
        }
        return zzf.booleanValue();
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Long> zzb(Factory factory, String str, long j) {
        return new zzam(factory, str, Long.valueOf(j));
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Integer> zzb(Factory factory, String str, int i) {
        return new zzan(factory, str, Integer.valueOf(i));
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Boolean> zzb(Factory factory, String str, boolean z) {
        return new zzao(factory, str, Boolean.valueOf(z));
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Double> zzb(Factory factory, String str, double d) {
        return new zzap(factory, str, Double.valueOf(d));
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<Float> zzb(Factory factory, String str, float f) {
        return new zzaq(factory, str, Float.valueOf(f));
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<String> zzb(Factory factory, String str, String str2) {
        return new zzar(factory, str, str2);
    }

    /* access modifiers changed from: private */
    public static PhenotypeFlag<byte[]> zzb(Factory factory, String str, byte[] bArr) {
        return new zzas(factory, str, bArr);
    }

    /* access modifiers changed from: private */
    public static <T> PhenotypeFlag<T> zzb(Factory factory, String str, T t, BytesConverter<T> bytesConverter) {
        return new zzat(factory, str, t, bytesConverter);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ String zza() {
        return zzfij.zza(zzc.getContentResolver(), this.zzh, (String) null);
    }

    /* synthetic */ PhenotypeFlag(Factory factory, String str, Object obj, zzam zzam) {
        this(factory, str, obj);
    }
}
