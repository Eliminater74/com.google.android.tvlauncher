package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.zzau;

import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    /* access modifiers changed from: private */
    public static final GoogleApiAvailabilityLight zza = GoogleApiAvailabilityLight.getInstance();
    private static final Object zzb = new Object();
    private static Method zzc = null;

    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzau.zza(context, "Context must not be null");
        GoogleApiAvailabilityLight.zzb(context);
        Context remoteContext = GooglePlayServicesUtilLight.getRemoteContext(context);
        if (remoteContext != null) {
            synchronized (zzb) {
                try {
                    if (zzc == null) {
                        zzc = remoteContext.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", Context.class);
                    }
                    zzc.invoke(null, remoteContext);
                } catch (Exception e) {
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e("ProviderInstaller", valueOf.length() != 0 ? "Failed to install provider: ".concat(valueOf) : new String("Failed to install provider: "));
                    throw new GooglePlayServicesNotAvailableException(8);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        Log.e("ProviderInstaller", "Failed to get remote context");
        throw new GooglePlayServicesNotAvailableException(8);
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        zzau.zza(context, "Context must not be null");
        zzau.zza(providerInstallListener, "Listener must not be null");
        zzau.zzb("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute(new Void[0]);
    }

    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent);

        void onProviderInstalled();
    }
}
