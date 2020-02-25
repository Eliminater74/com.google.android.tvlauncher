package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzbmk;

@Hide
public class GoogleSignatureVerifier {
    private static GoogleSignatureVerifier zza;
    private final Context zzb;

    private GoogleSignatureVerifier(Context context) {
        this.zzb = context.getApplicationContext();
    }

    public static synchronized void resetForTests() {
        synchronized (GoogleSignatureVerifier.class) {
            zza = null;
        }
    }

    public static GoogleSignatureVerifier getInstance(Context context) {
        zzau.zza(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zza == null) {
                zze.zza(context);
                zza = new GoogleSignatureVerifier(context);
            }
        }
        return zza;
    }

    @Hide
    public static boolean zza(PackageInfo packageInfo, boolean z) {
        zzf zzf;
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (z) {
                zzf = zza(packageInfo, zzi.zza);
            } else {
                zzf = zza(packageInfo, zzi.zza[0]);
            }
            if (zzf != null) {
                return true;
            }
        }
        return false;
    }

    @Hide
    private static zzf zza(PackageInfo packageInfo, zzf... zzfArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzg zzg = new zzg(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zzfArr.length; i++) {
            if (zzfArr[i].equals(zzg)) {
                return zzfArr[i];
            }
        }
        return null;
    }

    @Hide
    @Deprecated
    public void verifyUidIsGoogleSigned(PackageManager packageManager, int i) throws SecurityException {
        verifyUidIsGoogleSigned(i);
    }

    @Hide
    public void verifyUidIsGoogleSigned(int i) throws SecurityException {
        zza(i).zzc();
    }

    @Hide
    @Deprecated
    public boolean isUidGoogleSigned(PackageManager packageManager, int i) {
        return isUidGoogleSigned(i);
    }

    @Hide
    public boolean isUidGoogleSigned(int i) {
        zzn zza2 = zza(i);
        zza2.zzd();
        return zza2.zza;
    }

    @Hide
    @Deprecated
    public void verifyPackageIsGoogleSigned(PackageManager packageManager, String str) throws SecurityException {
        verifyPackageIsGoogleSigned(str);
    }

    @Hide
    public void verifyPackageIsGoogleSigned(String str) throws SecurityException {
        zza(str).zzc();
    }

    @Hide
    @Deprecated
    public boolean isPackageGoogleSigned(PackageManager packageManager, String str) {
        return isPackageGoogleSigned(str);
    }

    @Hide
    public boolean isPackageGoogleSigned(String str) {
        zzn zza2 = zza(str);
        zza2.zzd();
        return zza2.zza;
    }

    @Hide
    @Deprecated
    public boolean isPackageGoogleSigned(PackageManager packageManager, PackageInfo packageInfo) {
        return isPackageGoogleSigned(packageInfo);
    }

    @Hide
    public boolean isPackageGoogleSigned(PackageInfo packageInfo) {
        zzn zzb2 = zzb(packageInfo);
        zzb2.zzd();
        return zzb2.zza;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, boolean):boolean
     arg types: [android.content.pm.PackageInfo, int]
     candidates:
      com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, com.google.android.gms.common.zzf[]):com.google.android.gms.common.zzf
      com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, boolean):boolean */
    @Hide
    public final boolean zza(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    private final zzn zza(int i) {
        String[] zza2 = zzbmk.zza(this.zzb).zza(i);
        if (zza2 == null || zza2.length == 0) {
            return zzn.zza("no pkgs");
        }
        zzn zzn = null;
        for (String zza3 : zza2) {
            zzn = zza(zza3);
            if (zzn.zza) {
                return zzn;
            }
        }
        return zzn;
    }

    private final zzn zza(String str) {
        try {
            return zzb(zzbmk.zza(this.zzb).zzb(str, 64));
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(str);
            return zzn.zza(valueOf.length() != 0 ? "no pkg ".concat(valueOf) : new String("no pkg "));
        }
    }

    private final zzn zzb(PackageInfo packageInfo) {
        boolean honorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb);
        if (packageInfo == null) {
            return zzn.zza("null pkg");
        }
        if (packageInfo.signatures.length != 1) {
            return zzn.zza("single cert required");
        }
        zzg zzg = new zzg(packageInfo.signatures[0].toByteArray());
        String str = packageInfo.packageName;
        zzn zza2 = zze.zza(str, zzg, honorsDebugCertificates);
        if (!zza2.zza || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == 0 || (honorsDebugCertificates && !zze.zza(str, zzg, false).zza)) {
            return zza2;
        }
        return zzn.zza("debuggable release cert app rejected");
    }
}
