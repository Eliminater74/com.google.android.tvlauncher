package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzgh;
import com.google.android.gtalkservice.GTalkServiceConstants;
import com.google.android.tvlauncher.notifications.NotificationsContract;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@KeepForSdk
public class AdvertisingIdClient {
    @Nullable
    private com.google.android.gms.common.zza zza;
    @Nullable
    private zzgg zzb;
    private boolean zzc;
    private final Object zzd;
    @Nullable
    private zza zze;
    private final Context zzf;
    private final boolean zzg;
    private final long zzh;

    @Hide
    @KeepForSdk
    public AdvertisingIdClient(Context context) {
        this(context, 30000, false, false);
    }

    @Hide
    private AdvertisingIdClient(Context context, long j, boolean z, boolean z2) {
        this.zzd = new Object();
        zzau.zza(context);
        if (z) {
            Context applicationContext = context.getApplicationContext();
            this.zzf = applicationContext != null ? applicationContext : context;
        } else {
            this.zzf = context;
        }
        this.zzc = false;
        this.zzh = j;
        this.zzg = z2;
    }

    @KeepForSdkWithMembers
    public static final class Info {
        private final String zza;
        private final boolean zzb;

        public Info(String str, boolean z) {
            this.zza = str;
            this.zzb = z;
        }

        public final String getId() {
            return this.zza;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzb;
        }

        public final String toString() {
            String str = this.zza;
            boolean z = this.zzb;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 7);
            sb.append("{");
            sb.append(str);
            sb.append("}");
            sb.append(z);
            return sb.toString();
        }
    }

    @Hide
    static class zza extends Thread {
        CountDownLatch zza = new CountDownLatch(1);
        boolean zzb = false;
        private WeakReference<AdvertisingIdClient> zzc;
        private long zzd;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzc = new WeakReference<>(advertisingIdClient);
            this.zzd = j;
            start();
        }

        private final void zza() {
            AdvertisingIdClient advertisingIdClient = this.zzc.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.zza();
                this.zzb = true;
            }
        }

        public final void run() {
            try {
                if (!this.zza.await(this.zzd, TimeUnit.MILLISECONDS)) {
                    zza();
                }
            } catch (InterruptedException e) {
                zza();
            }
        }
    }

    @Hide
    @KeepForSdk
    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zza(true);
    }

    @Hide
    private final void zza(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzau.zzc("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzc) {
                zza();
            }
            this.zza = zza(this.zzf, this.zzg);
            this.zzb = zza(this.zzf, this.zza);
            this.zzc = true;
            if (z) {
                zzb();
            }
        }
    }

    @Hide
    @KeepForSdk
    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    private final void zzb() {
        synchronized (this.zzd) {
            if (this.zze != null) {
                this.zze.zza.countDown();
                try {
                    this.zze.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzh > 0) {
                this.zze = new zza(this, this.zzh);
            }
        }
    }

    @Hide
    @KeepForSdk
    public Info getInfo() throws IOException {
        Info info;
        zzau.zzc("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzc) {
                synchronized (this.zzd) {
                    if (this.zze == null || !this.zze.zzb) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zza(false);
                    if (!this.zzc) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (RemoteException e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Exception e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzau.zza(this.zza);
            zzau.zza(this.zzb);
            info = new Info(this.zzb.zza(), this.zzb.zza(true));
        }
        zzb();
        return info;
    }

    @Hide
    private final boolean zzc() throws IOException {
        boolean zzb2;
        zzau.zzc("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzc) {
                synchronized (this.zzd) {
                    if (this.zze == null || !this.zze.zzb) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zza(false);
                    if (!this.zzc) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (RemoteException e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Exception e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzau.zza(this.zza);
            zzau.zza(this.zzb);
            zzb2 = this.zzb.zzb();
        }
        zzb();
        return zzb2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
        return;
     */
    @com.google.android.gms.common.internal.Hide
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza() {
        /*
            r3 = this;
            java.lang.String r0 = "Calling this from your main thread can lead to deadlock"
            com.google.android.gms.common.internal.zzau.zzc(r0)
            monitor-enter(r3)
            android.content.Context r0 = r3.zzf     // Catch:{ all -> 0x0032 }
            if (r0 == 0) goto L_0x0030
            com.google.android.gms.common.zza r0 = r3.zza     // Catch:{ all -> 0x0032 }
            if (r0 != 0) goto L_0x000f
            goto L_0x0030
        L_0x000f:
            boolean r0 = r3.zzc     // Catch:{ all -> 0x001e }
            if (r0 == 0) goto L_0x001d
            com.google.android.gms.common.stats.zza.zza()     // Catch:{ all -> 0x001e }
            android.content.Context r0 = r3.zzf     // Catch:{ all -> 0x001e }
            com.google.android.gms.common.zza r1 = r3.zza     // Catch:{ all -> 0x001e }
            r0.unbindService(r1)     // Catch:{ all -> 0x001e }
        L_0x001d:
            goto L_0x0026
        L_0x001e:
            r0 = move-exception
            java.lang.String r1 = "AdvertisingIdClient"
            java.lang.String r2 = "AdvertisingIdClient unbindService failed."
            android.util.Log.i(r1, r2, r0)     // Catch:{ all -> 0x0032 }
        L_0x0026:
            r0 = 0
            r3.zzc = r0     // Catch:{ all -> 0x0032 }
            r0 = 0
            r3.zzb = r0     // Catch:{ all -> 0x0032 }
            r3.zza = r0     // Catch:{ all -> 0x0032 }
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            return
        L_0x0030:
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            return
        L_0x0032:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.zza():void");
    }

    /* access modifiers changed from: protected */
    @Hide
    public void finalize() throws Throwable {
        zza();
        super.finalize();
    }

    private static com.google.android.gms.common.zza zza(Context context, boolean z) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            int isGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context);
            if (isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2) {
                String str = z ? "com.google.android.gms.ads.identifier.service.PERSISTENT_START" : "com.google.android.gms.ads.identifier.service.START";
                com.google.android.gms.common.zza zza2 = new com.google.android.gms.common.zza();
                Intent intent = new Intent(str);
                intent.setPackage("com.google.android.gms");
                try {
                    if (com.google.android.gms.common.stats.zza.zza().zza(context, intent, zza2, 1)) {
                        return zza2;
                    }
                    throw new IOException("Connection failure");
                } catch (Throwable th) {
                    throw new IOException(th);
                }
            } else {
                throw new IOException("Google Play services not available");
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, boolean):boolean
     arg types: [java.lang.String, int]
     candidates:
      com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, float):float
      com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, java.lang.String):java.lang.String
      com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, boolean):boolean */
    @KeepForSdk
    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzd zzd2 = new zzd(context);
        boolean zza2 = zzd2.zza("gads:ad_id_app_context:enabled", false);
        float zza3 = zzd2.zza("gads:ad_id_app_context:ping_ratio", 0.0f);
        String zza4 = zzd2.zza("gads:ad_id_use_shared_preference:experiment_id", "");
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1, zza2, zzd2.zza("gads:ad_id_use_persistent_service:enabled", false));
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            advertisingIdClient.zza(false);
            Info info = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info, zza2, zza3, SystemClock.elapsedRealtime() - elapsedRealtime, zza4, null);
            advertisingIdClient.zza();
            return info;
        } catch (Throwable th) {
            advertisingIdClient.zza();
            throw th;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, boolean):boolean
     arg types: [java.lang.String, int]
     candidates:
      com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, float):float
      com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, java.lang.String):java.lang.String
      com.google.android.gms.ads.identifier.zzd.zza(java.lang.String, boolean):boolean */
    @Hide
    @KeepForSdk
    public static boolean getIsAdIdFakeForDebugLogging(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzd zzd2 = new zzd(context);
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1, zzd2.zza("gads:ad_id_app_context:enabled", false), zzd2.zza("com.google.android.gms.ads.identifier.service.PERSISTENT_START", false));
        try {
            advertisingIdClient.zza(false);
            return advertisingIdClient.zzc();
        } finally {
            advertisingIdClient.zza();
        }
    }

    private final boolean zza(Info info, boolean z, float f, long j, String str, Throwable th) {
        if (Math.random() > ((double) f)) {
            return false;
        }
        HashMap hashMap = new HashMap();
        String str2 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
        hashMap.put("app_context", z ? str2 : "0");
        if (info != null) {
            if (!info.isLimitAdTrackingEnabled()) {
                str2 = "0";
            }
            hashMap.put("limit_ad_tracking", str2);
        }
        if (!(info == null || info.getId() == null)) {
            hashMap.put("ad_id_size", Integer.toString(info.getId().length()));
        }
        if (th != null) {
            hashMap.put(GTalkServiceConstants.EXTRA_ERROR, th.getClass().getName());
        }
        if (str != null && !str.isEmpty()) {
            hashMap.put("experiment_id", str);
        }
        hashMap.put(NotificationsContract.COLUMN_TAG, "AdvertisingIdClient");
        hashMap.put("time_spent", Long.toString(j));
        new zza(this, hashMap).start();
        return true;
    }

    @Hide
    private static zzgg zza(Context context, com.google.android.gms.common.zza zza2) throws IOException {
        try {
            return zzgh.zza(zza2.zza(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }
}
