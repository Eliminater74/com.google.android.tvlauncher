package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;

import java.lang.reflect.Field;

@Hide
public final class DynamiteModule {
    public static final zzd zza = new zzb();
    public static final zzd zzb = new zzd();
    public static final zzd zzc = new zze();
    public static final zzd zzd = new zzf();
    public static final zzd zze = new zzg();
    private static final ThreadLocal<zza> zzj = new ThreadLocal<>();
    private static final zzi zzk = new zza();
    private static final zzd zzl = new zzc();
    private static Boolean zzf;
    private static zzk zzg;
    private static zzm zzh;
    private static String zzi;
    private final Context zzm;

    private DynamiteModule(Context context) {
        this.zzm = (Context) zzau.zza(context);
    }

    public static DynamiteModule zza(Context context, zzd zzd2, String str) throws zzc {
        zzj zza2;
        zza zza3 = zzj.get();
        zza zza4 = new zza(null);
        zzj.set(zza4);
        try {
            zza2 = zzd2.zza(context, str, zzk);
            int i = zza2.zza;
            int i2 = zza2.zzb;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length());
            sb.append("Considering local module ");
            sb.append(str);
            sb.append(":");
            sb.append(i);
            sb.append(" and remote module ");
            sb.append(str);
            sb.append(":");
            sb.append(i2);
            Log.i("DynamiteModule", sb.toString());
            if (zza2.zzc == 0 || ((zza2.zzc == -1 && zza2.zza == 0) || (zza2.zzc == 1 && zza2.zzb == 0))) {
                int i3 = zza2.zza;
                int i4 = zza2.zzb;
                StringBuilder sb2 = new StringBuilder(91);
                sb2.append("No acceptable module found. Local version is ");
                sb2.append(i3);
                sb2.append(" and remote version is ");
                sb2.append(i4);
                sb2.append(".");
                throw new zzc(sb2.toString(), (zza) null);
            } else if (zza2.zzc == -1) {
                DynamiteModule zzc2 = zzc(context, str);
                if (zza4.zza != null) {
                    zza4.zza.close();
                }
                zzj.set(zza3);
                return zzc2;
            } else if (zza2.zzc == 1) {
                DynamiteModule zza5 = zza(context, str, zza2.zzb);
                if (zza4.zza != null) {
                    zza4.zza.close();
                }
                zzj.set(zza3);
                return zza5;
            } else {
                int i5 = zza2.zzc;
                StringBuilder sb3 = new StringBuilder(47);
                sb3.append("VersionPolicy returned invalid code:");
                sb3.append(i5);
                throw new zzc(sb3.toString(), (zza) null);
            }
        } catch (zzc e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.w("DynamiteModule", valueOf.length() != 0 ? "Failed to load remote module: ".concat(valueOf) : new String("Failed to load remote module: "));
            if (zza2.zza == 0 || zzd2.zza(context, str, new zzb(zza2.zza, 0)).zzc != -1) {
                throw new zzc("Remote load failed. No local fallback found.", e, null);
            }
            DynamiteModule zzc3 = zzc(context, str);
            if (zza4.zza != null) {
                zza4.zza.close();
            }
            zzj.set(zza3);
            return zzc3;
        } catch (Throwable th) {
            if (zza4.zza != null) {
                zza4.zza.close();
            }
            zzj.set(zza3);
            throw th;
        }
    }

    public static int zza(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 61);
            sb.append("com.google.android.gms.dynamite.descriptors.");
            sb.append(str);
            sb.append(".ModuleDescriptor");
            Class<?> loadClass = classLoader.loadClass(sb.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            String valueOf = String.valueOf(declaredField.get(null));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(str).length());
            sb2.append("Module descriptor id '");
            sb2.append(valueOf);
            sb2.append("' didn't match expected id '");
            sb2.append(str);
            sb2.append("'");
            Log.e("DynamiteModule", sb2.toString());
            return 0;
        } catch (ClassNotFoundException e) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e2) {
            String valueOf2 = String.valueOf(e2.getMessage());
            Log.e("DynamiteModule", valueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(valueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x00c6 A[SYNTHETIC, Splitter:B:57:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ed  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x0038=Splitter:B:18:0x0038, B:35:0x007f=Splitter:B:35:0x007f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int zza(android.content.Context r8, java.lang.String r9, boolean r10) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r0 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r0)
            java.lang.Boolean r1 = com.google.android.gms.dynamite.DynamiteModule.zzf     // Catch:{ all -> 0x00f2 }
            if (r1 != 0) goto L_0x00bf
            android.content.Context r1 = r8.getApplicationContext()     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader> r2 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class
            java.lang.String r2 = r2.getName()     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
            java.lang.Class r1 = r1.loadClass(r2)     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
            java.lang.String r2 = "sClassLoader"
            java.lang.reflect.Field r2 = r1.getDeclaredField(r2)     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
            monitor-enter(r1)     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
            r3 = 0
            java.lang.Object r4 = r2.get(r3)     // Catch:{ all -> 0x008f }
            java.lang.ClassLoader r4 = (java.lang.ClassLoader) r4     // Catch:{ all -> 0x008f }
            if (r4 == 0) goto L_0x003b
            java.lang.ClassLoader r2 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x008f }
            if (r4 != r2) goto L_0x0033
            java.lang.Boolean r2 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x008f }
            goto L_0x008c
        L_0x0033:
            zza(r4)     // Catch:{ zzc -> 0x0037 }
            goto L_0x0038
        L_0x0037:
            r2 = move-exception
        L_0x0038:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x008f }
            goto L_0x008c
        L_0x003b:
            java.lang.String r4 = "com.google.android.gms"
            android.content.Context r5 = r8.getApplicationContext()     // Catch:{ all -> 0x008f }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ all -> 0x008f }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x008f }
            if (r4 == 0) goto L_0x0055
            java.lang.ClassLoader r4 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x008f }
            r2.set(r3, r4)     // Catch:{ all -> 0x008f }
            java.lang.Boolean r2 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x008f }
            goto L_0x008c
        L_0x0055:
            int r4 = zzc(r8, r9, r10)     // Catch:{ zzc -> 0x0082 }
            java.lang.String r5 = com.google.android.gms.dynamite.DynamiteModule.zzi     // Catch:{ zzc -> 0x0082 }
            if (r5 == 0) goto L_0x007f
            java.lang.String r5 = com.google.android.gms.dynamite.DynamiteModule.zzi     // Catch:{ zzc -> 0x0082 }
            boolean r5 = r5.isEmpty()     // Catch:{ zzc -> 0x0082 }
            if (r5 == 0) goto L_0x0066
            goto L_0x007f
        L_0x0066:
            com.google.android.gms.dynamite.zzh r5 = new com.google.android.gms.dynamite.zzh     // Catch:{ zzc -> 0x0082 }
            java.lang.String r6 = com.google.android.gms.dynamite.DynamiteModule.zzi     // Catch:{ zzc -> 0x0082 }
            java.lang.ClassLoader r7 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ zzc -> 0x0082 }
            r5.<init>(r6, r7)     // Catch:{ zzc -> 0x0082 }
            zza(r5)     // Catch:{ zzc -> 0x0082 }
            r2.set(r3, r5)     // Catch:{ zzc -> 0x0082 }
            java.lang.Boolean r5 = java.lang.Boolean.TRUE     // Catch:{ zzc -> 0x0082 }
            com.google.android.gms.dynamite.DynamiteModule.zzf = r5     // Catch:{ zzc -> 0x0082 }
            monitor-exit(r1)     // Catch:{ all -> 0x008f }
            monitor-exit(r0)     // Catch:{ all -> 0x00f2 }
            return r4
        L_0x007f:
            monitor-exit(r1)     // Catch:{ all -> 0x008f }
            monitor-exit(r0)     // Catch:{ all -> 0x00f2 }
            return r4
        L_0x0082:
            r4 = move-exception
            java.lang.ClassLoader r4 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x008f }
            r2.set(r3, r4)     // Catch:{ all -> 0x008f }
            java.lang.Boolean r2 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x008f }
        L_0x008c:
            monitor-exit(r1)     // Catch:{ all -> 0x008f }
            r1 = r2
            goto L_0x00bd
        L_0x008f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x008f }
            throw r2     // Catch:{ ClassNotFoundException -> 0x0096, IllegalAccessException -> 0x0094, NoSuchFieldException -> 0x0092 }
        L_0x0092:
            r1 = move-exception
            goto L_0x0097
        L_0x0094:
            r1 = move-exception
            goto L_0x0097
        L_0x0096:
            r1 = move-exception
        L_0x0097:
            java.lang.String r2 = "DynamiteModule"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00f2 }
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00f2 }
            int r3 = r3.length()     // Catch:{ all -> 0x00f2 }
            int r3 = r3 + 30
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f2 }
            r4.<init>(r3)     // Catch:{ all -> 0x00f2 }
            java.lang.String r3 = "Failed to load module via V2: "
            r4.append(r3)     // Catch:{ all -> 0x00f2 }
            r4.append(r1)     // Catch:{ all -> 0x00f2 }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x00f2 }
            android.util.Log.w(r2, r1)     // Catch:{ all -> 0x00f2 }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00f2 }
        L_0x00bd:
            com.google.android.gms.dynamite.DynamiteModule.zzf = r1     // Catch:{ all -> 0x00f2 }
        L_0x00bf:
            monitor-exit(r0)     // Catch:{ all -> 0x00f2 }
            boolean r0 = r1.booleanValue()
            if (r0 == 0) goto L_0x00ed
            int r8 = zzc(r8, r9, r10)     // Catch:{ zzc -> 0x00cb }
            return r8
        L_0x00cb:
            r8 = move-exception
            java.lang.String r9 = "Failed to retrieve remote module version: "
            java.lang.String r8 = r8.getMessage()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            int r10 = r8.length()
            if (r10 == 0) goto L_0x00e1
            java.lang.String r8 = r9.concat(r8)
            goto L_0x00e6
        L_0x00e1:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r9)
        L_0x00e6:
            java.lang.String r9 = "DynamiteModule"
            android.util.Log.w(r9, r8)
            r8 = 0
            return r8
        L_0x00ed:
            int r8 = zzb(r8, r9, r10)
            return r8
        L_0x00f2:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00f2 }
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int");
    }

    private static int zzb(Context context, String str, boolean z) {
        zzk zza2 = zza(context);
        if (zza2 == null) {
            return 0;
        }
        try {
            return zza2.zza(zzn.zza(context), str, z);
        } catch (RemoteException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.w("DynamiteModule", valueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(valueOf) : new String("Failed to retrieve remote module version: "));
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzc(android.content.Context r8, java.lang.String r9, boolean r10) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            if (r10 == 0) goto L_0x000d
            java.lang.String r8 = "api_force_staging"
            goto L_0x000f
        L_0x000d:
            java.lang.String r8 = "api"
        L_0x000f:
            java.lang.String r10 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            int r10 = r10.length()     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            int r10 = r10 + 42
            java.lang.String r2 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            int r10 = r10 + r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            r2.<init>(r10)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            java.lang.String r10 = "content://com.google.android.gms.chimera/"
            r2.append(r10)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            r2.append(r8)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            java.lang.String r8 = "/"
            r2.append(r8)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            r2.append(r9)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            java.lang.String r8 = r2.toString()     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            android.net.Uri r2 = android.net.Uri.parse(r8)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0097, all -> 0x0095 }
            if (r8 == 0) goto L_0x007d
            boolean r9 = r8.moveToFirst()     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            if (r9 == 0) goto L_0x007d
            r9 = 0
            int r9 = r8.getInt(r9)     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            if (r9 <= 0) goto L_0x0077
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r10 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r10)     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            r1 = 2
            java.lang.String r1 = r8.getString(r1)     // Catch:{ all -> 0x0074 }
            com.google.android.gms.dynamite.DynamiteModule.zzi = r1     // Catch:{ all -> 0x0074 }
            monitor-exit(r10)     // Catch:{ all -> 0x0074 }
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r10 = com.google.android.gms.dynamite.DynamiteModule.zzj     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            java.lang.Object r10 = r10.get()     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            com.google.android.gms.dynamite.DynamiteModule$zza r10 = (com.google.android.gms.dynamite.DynamiteModule.zza) r10     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            if (r10 == 0) goto L_0x0077
            android.database.Cursor r1 = r10.zza     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            if (r1 != 0) goto L_0x0077
            r10.zza = r8     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            r8 = r0
            goto L_0x0077
        L_0x0074:
            r9 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0074 }
            throw r9     // Catch:{ Exception -> 0x0090, all -> 0x008c }
        L_0x0077:
            if (r8 == 0) goto L_0x007c
            r8.close()
        L_0x007c:
            return r9
        L_0x007d:
            java.lang.String r9 = "DynamiteModule"
            java.lang.String r10 = "Failed to retrieve remote module version."
            android.util.Log.w(r9, r10)     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            com.google.android.gms.dynamite.DynamiteModule$zzc r9 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            java.lang.String r10 = "Failed to connect to dynamite module ContentResolver."
            r9.<init>(r10, r0)     // Catch:{ Exception -> 0x0090, all -> 0x008c }
            throw r9     // Catch:{ Exception -> 0x0090, all -> 0x008c }
        L_0x008c:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto L_0x00a8
        L_0x0090:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
            goto L_0x0099
        L_0x0095:
            r8 = move-exception
            goto L_0x00a8
        L_0x0097:
            r8 = move-exception
            r9 = r0
        L_0x0099:
            boolean r10 = r8 instanceof com.google.android.gms.dynamite.DynamiteModule.zzc     // Catch:{ all -> 0x00a6 }
            if (r10 == 0) goto L_0x009e
            throw r8     // Catch:{ all -> 0x00a6 }
        L_0x009e:
            com.google.android.gms.dynamite.DynamiteModule$zzc r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ all -> 0x00a6 }
            java.lang.String r1 = "V2 version check failed"
            r10.<init>(r1, r8, r0)     // Catch:{ all -> 0x00a6 }
            throw r10     // Catch:{ all -> 0x00a6 }
        L_0x00a6:
            r8 = move-exception
            r0 = r9
        L_0x00a8:
            if (r0 == 0) goto L_0x00ad
            r0.close()
        L_0x00ad:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzc(android.content.Context, java.lang.String, boolean):int");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int
     arg types: [android.content.Context, java.lang.String, int]
     candidates:
      com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, com.google.android.gms.dynamite.DynamiteModule$zzd, java.lang.String):com.google.android.gms.dynamite.DynamiteModule
      com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, int):com.google.android.gms.dynamite.DynamiteModule
      com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int */
    public static int zzb(Context context, String str) {
        return zza(context, str, false);
    }

    private static DynamiteModule zzc(Context context, String str) {
        String valueOf = String.valueOf(str);
        Log.i("DynamiteModule", valueOf.length() != 0 ? "Selected local version of ".concat(valueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        Boolean bool;
        synchronized (DynamiteModule.class) {
            bool = zzf;
        }
        if (bool == null) {
            throw new zzc("Failed to determine which loading route to use.", (zza) null);
        } else if (bool.booleanValue()) {
            return zzc(context, str, i);
        } else {
            return zzb(context, str, i);
        }
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        zzk zza2 = zza(context);
        if (zza2 != null) {
            try {
                IObjectWrapper zza3 = zza2.zza(zzn.zza(context), str, i);
                if (zzn.zza(zza3) != null) {
                    return new DynamiteModule((Context) zzn.zza(zza3));
                }
                throw new zzc("Failed to load remote module.", (zza) null);
            } catch (RemoteException e) {
                throw new zzc("Failed to load remote module.", e, null);
            }
        } else {
            throw new zzc("Failed to create IDynamiteLoader.", (zza) null);
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.gms.dynamite.zzk zza(android.content.Context r5) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r0 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r0)
            com.google.android.gms.dynamite.zzk r1 = com.google.android.gms.dynamite.DynamiteModule.zzg     // Catch:{ all -> 0x0072 }
            if (r1 == 0) goto L_0x000b
            com.google.android.gms.dynamite.zzk r5 = com.google.android.gms.dynamite.DynamiteModule.zzg     // Catch:{ all -> 0x0072 }
            monitor-exit(r0)     // Catch:{ all -> 0x0072 }
            return r5
        L_0x000b:
            com.google.android.gms.common.GoogleApiAvailabilityLight r1 = com.google.android.gms.common.GoogleApiAvailabilityLight.getInstance()     // Catch:{ all -> 0x0072 }
            int r1 = r1.isGooglePlayServicesAvailable(r5)     // Catch:{ all -> 0x0072 }
            r2 = 0
            if (r1 == 0) goto L_0x0018
            monitor-exit(r0)     // Catch:{ all -> 0x0072 }
            return r2
        L_0x0018:
            java.lang.String r1 = "com.google.android.gms"
            r3 = 3
            android.content.Context r5 = r5.createPackageContext(r1, r3)     // Catch:{ Exception -> 0x0050 }
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ Exception -> 0x0050 }
            java.lang.String r1 = "com.google.android.gms.chimera.container.DynamiteLoaderImpl"
            java.lang.Class r5 = r5.loadClass(r1)     // Catch:{ Exception -> 0x0050 }
            java.lang.Object r5 = r5.newInstance()     // Catch:{ Exception -> 0x0050 }
            android.os.IBinder r5 = (android.os.IBinder) r5     // Catch:{ Exception -> 0x0050 }
            if (r5 != 0) goto L_0x0034
            r5 = r2
            goto L_0x0048
        L_0x0034:
            java.lang.String r1 = "com.google.android.gms.dynamite.IDynamiteLoader"
            android.os.IInterface r1 = r5.queryLocalInterface(r1)     // Catch:{ Exception -> 0x0050 }
            boolean r3 = r1 instanceof com.google.android.gms.dynamite.zzk     // Catch:{ Exception -> 0x0050 }
            if (r3 == 0) goto L_0x0042
            r5 = r1
            com.google.android.gms.dynamite.zzk r5 = (com.google.android.gms.dynamite.zzk) r5     // Catch:{ Exception -> 0x0050 }
            goto L_0x0048
        L_0x0042:
            com.google.android.gms.dynamite.zzl r1 = new com.google.android.gms.dynamite.zzl     // Catch:{ Exception -> 0x0050 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0050 }
            r5 = r1
        L_0x0048:
            if (r5 == 0) goto L_0x004f
            com.google.android.gms.dynamite.DynamiteModule.zzg = r5     // Catch:{ Exception -> 0x0050 }
            monitor-exit(r0)     // Catch:{ all -> 0x0072 }
            return r5
        L_0x004f:
            goto L_0x0070
        L_0x0050:
            r5 = move-exception
            java.lang.String r1 = "DynamiteModule"
            java.lang.String r3 = "Failed to load IDynamiteLoader from GmsCore: "
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x0072 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0072 }
            int r4 = r5.length()     // Catch:{ all -> 0x0072 }
            if (r4 == 0) goto L_0x0068
            java.lang.String r5 = r3.concat(r5)     // Catch:{ all -> 0x0072 }
            goto L_0x006d
        L_0x0068:
            java.lang.String r5 = new java.lang.String     // Catch:{ all -> 0x0072 }
            r5.<init>(r3)     // Catch:{ all -> 0x0072 }
        L_0x006d:
            android.util.Log.e(r1, r5)     // Catch:{ all -> 0x0072 }
        L_0x0070:
            monitor-exit(r0)     // Catch:{ all -> 0x0072 }
            return r2
        L_0x0072:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0072 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context):com.google.android.gms.dynamite.zzk");
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        zzm zzm2;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        synchronized (DynamiteModule.class) {
            zzm2 = zzh;
        }
        if (zzm2 != null) {
            zza zza2 = zzj.get();
            if (zza2 == null || zza2.zza == null) {
                throw new zzc("No result cursor", (zza) null);
            }
            Context zza3 = zza(context.getApplicationContext(), str, i, zza2.zza, zzm2);
            if (zza3 != null) {
                return new DynamiteModule(zza3);
            }
            throw new zzc("Failed to get module context", (zza) null);
        }
        throw new zzc("DynamiteLoaderV2 was not cached.", (zza) null);
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzm zzm2) {
        try {
            return (Context) zzn.zza(zzm2.zza(zzn.zza(context), str, i, zzn.zza(cursor)));
        } catch (Exception e) {
            String valueOf = String.valueOf(e.toString());
            Log.e("DynamiteModule", valueOf.length() != 0 ? "Failed to load DynamiteLoader: ".concat(valueOf) : new String("Failed to load DynamiteLoader: "));
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(java.lang.ClassLoader r3) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            r0 = 0
            java.lang.String r1 = "com.google.android.gms.dynamiteloader.DynamiteLoaderV2"
            java.lang.Class r3 = r3.loadClass(r1)     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            r1 = 0
            java.lang.Class[] r2 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            java.lang.reflect.Constructor r3 = r3.getConstructor(r2)     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            java.lang.Object r3 = r3.newInstance(r1)     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            android.os.IBinder r3 = (android.os.IBinder) r3     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            if (r3 != 0) goto L_0x001b
            r3 = r0
            goto L_0x002f
        L_0x001b:
            java.lang.String r1 = "com.google.android.gms.dynamite.IDynamiteLoaderV2"
            android.os.IInterface r1 = r3.queryLocalInterface(r1)     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            boolean r2 = r1 instanceof com.google.android.gms.dynamite.zzm     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            if (r2 == 0) goto L_0x0029
            r3 = r1
            com.google.android.gms.dynamite.zzm r3 = (com.google.android.gms.dynamite.zzm) r3     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            goto L_0x002f
        L_0x0029:
            com.google.android.gms.dynamite.zzn r1 = new com.google.android.gms.dynamite.zzn     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            r1.<init>(r3)     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            r3 = r1
        L_0x002f:
            com.google.android.gms.dynamite.DynamiteModule.zzh = r3     // Catch:{ ClassNotFoundException -> 0x003a, IllegalAccessException -> 0x0038, InstantiationException -> 0x0036, InvocationTargetException -> 0x0034, NoSuchMethodException -> 0x0032 }
            return
        L_0x0032:
            r3 = move-exception
            goto L_0x003b
        L_0x0034:
            r3 = move-exception
            goto L_0x003b
        L_0x0036:
            r3 = move-exception
            goto L_0x003b
        L_0x0038:
            r3 = move-exception
            goto L_0x003b
        L_0x003a:
            r3 = move-exception
        L_0x003b:
            com.google.android.gms.dynamite.DynamiteModule$zzc r1 = new com.google.android.gms.dynamite.DynamiteModule$zzc
            java.lang.String r2 = "Failed to instantiate dynamite loader"
            r1.<init>(r2, r3, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(java.lang.ClassLoader):void");
    }

    public final Context zza() {
        return this.zzm;
    }

    public final IBinder zza(String str) throws zzc {
        try {
            return (IBinder) this.zzm.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String valueOf = String.valueOf(str);
            throw new zzc(valueOf.length() != 0 ? "Failed to instantiate module class: ".concat(valueOf) : new String("Failed to instantiate module class: "), e, null);
        }
    }

    public interface zzd {
        zzj zza(Context context, String str, zzi zzi) throws zzc;
    }

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static class zza {
        public Cursor zza;

        private zza() {
        }

        /* synthetic */ zza(zza zza2) {
            this();
        }
    }

    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }

        /* synthetic */ zzc(String str, zza zza) {
            this(str);
        }

        /* synthetic */ zzc(String str, Throwable th, zza zza) {
            this(str, th);
        }
    }

    static class zzb implements zzi {
        private final int zza;
        private final int zzb = 0;

        public zzb(int i, int i2) {
            this.zza = i;
        }

        public final int zza(Context context, String str, boolean z) {
            return 0;
        }

        public final int zza(Context context, String str) {
            return this.zza;
        }
    }
}
