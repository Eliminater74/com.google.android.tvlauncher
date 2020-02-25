package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.android.gsf.Gservices;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* compiled from: Gservices */
public class zzfij {
    public static final Pattern zza = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzb = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    /* access modifiers changed from: private */
    public static final AtomicBoolean zze = new AtomicBoolean();
    private static final Uri zzc = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzd = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static final HashMap<String, Boolean> zzg = new HashMap<>();
    private static final HashMap<String, Integer> zzh = new HashMap<>();
    private static final HashMap<String, Long> zzi = new HashMap<>();
    private static final HashMap<String, Float> zzj = new HashMap<>();
    private static HashMap<String, String> zzf;
    private static Object zzk;
    private static boolean zzl;
    private static String[] zzm = new String[0];

    private static void zza(ContentResolver contentResolver) {
        if (zzf == null) {
            zze.set(false);
            zzf = new HashMap<>();
            zzk = new Object();
            zzl = false;
            contentResolver.registerContentObserver(zzc, true, new zzfik(null));
        } else if (zze.getAndSet(false)) {
            zzf.clear();
            zzg.clear();
            zzh.clear();
            zzi.clear();
            zzj.clear();
            zzk = new Object();
            zzl = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005d, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005f, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0064, code lost:
        r13 = r13.query(com.google.android.gms.internal.zzfij.zzc, null, null, new java.lang.String[]{r14}, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0072, code lost:
        if (r13 == null) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0078, code lost:
        if (r13.moveToFirst() != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007b, code lost:
        r15 = r13.getString(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007f, code lost:
        if (r15 == null) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0085, code lost:
        if (r15.equals(null) == false) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0087, code lost:
        r15 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0088, code lost:
        zza(r0, r14, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x008b, code lost:
        if (r15 == null) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x008e, code lost:
        r15 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x008f, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0093, code lost:
        return r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        zza(r0, r14, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0097, code lost:
        if (r13 == null) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0099, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009c, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x009d, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x009e, code lost:
        if (r13 != null) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a0, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00a3, code lost:
        throw r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r13, java.lang.String r14, java.lang.String r15) {
        /*
            java.lang.Class<com.google.android.gms.internal.zzfij> r15 = com.google.android.gms.internal.zzfij.class
            monitor-enter(r15)
            zza(r13)     // Catch:{ all -> 0x00a4 }
            java.lang.Object r0 = com.google.android.gms.internal.zzfij.zzk     // Catch:{ all -> 0x00a4 }
            java.util.HashMap<java.lang.String, java.lang.String> r1 = com.google.android.gms.internal.zzfij.zzf     // Catch:{ all -> 0x00a4 }
            boolean r1 = r1.containsKey(r14)     // Catch:{ all -> 0x00a4 }
            r2 = 0
            if (r1 == 0) goto L_0x001f
            java.util.HashMap<java.lang.String, java.lang.String> r13 = com.google.android.gms.internal.zzfij.zzf     // Catch:{ all -> 0x00a4 }
            java.lang.Object r13 = r13.get(r14)     // Catch:{ all -> 0x00a4 }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ all -> 0x00a4 }
            if (r13 == 0) goto L_0x001c
            goto L_0x001d
        L_0x001c:
            r13 = r2
        L_0x001d:
            monitor-exit(r15)     // Catch:{ all -> 0x00a4 }
            return r13
        L_0x001f:
            java.lang.String[] r1 = com.google.android.gms.internal.zzfij.zzm     // Catch:{ all -> 0x00a4 }
            int r3 = r1.length     // Catch:{ all -> 0x00a4 }
            r4 = 0
            r5 = 0
        L_0x0024:
            r6 = 1
            if (r5 >= r3) goto L_0x0063
            r7 = r1[r5]     // Catch:{ all -> 0x00a4 }
            boolean r7 = r14.startsWith(r7)     // Catch:{ all -> 0x00a4 }
            if (r7 == 0) goto L_0x0060
            boolean r0 = com.google.android.gms.internal.zzfij.zzl     // Catch:{ all -> 0x00a4 }
            if (r0 == 0) goto L_0x003b
            java.util.HashMap<java.lang.String, java.lang.String> r0 = com.google.android.gms.internal.zzfij.zzf     // Catch:{ all -> 0x00a4 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x00a4 }
            if (r0 == 0) goto L_0x005e
        L_0x003b:
            java.lang.String[] r0 = com.google.android.gms.internal.zzfij.zzm     // Catch:{ all -> 0x00a4 }
            java.util.HashMap<java.lang.String, java.lang.String> r1 = com.google.android.gms.internal.zzfij.zzf     // Catch:{ all -> 0x00a4 }
            java.util.Map r13 = zza(r13, r0)     // Catch:{ all -> 0x00a4 }
            r1.putAll(r13)     // Catch:{ all -> 0x00a4 }
            com.google.android.gms.internal.zzfij.zzl = r6     // Catch:{ all -> 0x00a4 }
            java.util.HashMap<java.lang.String, java.lang.String> r13 = com.google.android.gms.internal.zzfij.zzf     // Catch:{ all -> 0x00a4 }
            boolean r13 = r13.containsKey(r14)     // Catch:{ all -> 0x00a4 }
            if (r13 == 0) goto L_0x005e
            java.util.HashMap<java.lang.String, java.lang.String> r13 = com.google.android.gms.internal.zzfij.zzf     // Catch:{ all -> 0x00a4 }
            java.lang.Object r13 = r13.get(r14)     // Catch:{ all -> 0x00a4 }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ all -> 0x00a4 }
            if (r13 == 0) goto L_0x005b
            goto L_0x005c
        L_0x005b:
            r13 = r2
        L_0x005c:
            monitor-exit(r15)     // Catch:{ all -> 0x00a4 }
            return r13
        L_0x005e:
            monitor-exit(r15)     // Catch:{ all -> 0x00a4 }
            return r2
        L_0x0060:
            int r5 = r5 + 1
            goto L_0x0024
        L_0x0063:
            monitor-exit(r15)     // Catch:{ all -> 0x00a4 }
            android.net.Uri r8 = com.google.android.gms.internal.zzfij.zzc
            r9 = 0
            r10 = 0
            java.lang.String[] r11 = new java.lang.String[r6]
            r11[r4] = r14
            r12 = 0
            r7 = r13
            android.database.Cursor r13 = r7.query(r8, r9, r10, r11, r12)
            if (r13 == 0) goto L_0x0094
            boolean r15 = r13.moveToFirst()     // Catch:{ all -> 0x009d }
            if (r15 != 0) goto L_0x007b
            goto L_0x0094
        L_0x007b:
            java.lang.String r15 = r13.getString(r6)     // Catch:{ all -> 0x009d }
            if (r15 == 0) goto L_0x0088
            boolean r1 = r15.equals(r2)     // Catch:{ all -> 0x009d }
            if (r1 == 0) goto L_0x0088
            r15 = r2
        L_0x0088:
            zza(r0, r14, r15)     // Catch:{ all -> 0x009d }
            if (r15 == 0) goto L_0x008e
            goto L_0x008f
        L_0x008e:
            r15 = r2
        L_0x008f:
            r13.close()
            return r15
        L_0x0094:
            zza(r0, r14, r2)     // Catch:{ all -> 0x009d }
            if (r13 == 0) goto L_0x009c
            r13.close()
        L_0x009c:
            return r2
        L_0x009d:
            r14 = move-exception
            if (r13 == 0) goto L_0x00a3
            r13.close()
        L_0x00a3:
            throw r14
        L_0x00a4:
            r13 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x00a4 }
            throw r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfij.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzfij.class) {
            if (obj == zzk) {
                zzf.put(str, str2);
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzfij.zza(java.util.HashMap, java.lang.String, java.lang.Object):T
     arg types: [java.util.HashMap<java.lang.String, java.lang.Long>, java.lang.String, long]
     candidates:
      com.google.android.gms.internal.zzfij.zza(android.content.ContentResolver, java.lang.String, long):long
      com.google.android.gms.internal.zzfij.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String
      com.google.android.gms.internal.zzfij.zza(java.lang.Object, java.lang.String, java.lang.String):void
      com.google.android.gms.internal.zzfij.zza(android.content.ContentResolver, java.lang.String, boolean):boolean
      com.google.android.gms.internal.zzfij.zza(java.util.HashMap, java.lang.String, java.lang.Object):T */
    public static long zza(ContentResolver contentResolver, String str, long j) {
        Object zzb2 = zzb(contentResolver);
        long j2 = 0;
        Long l = (Long) zza((HashMap) zzi, str, (Object) 0L);
        if (l != null) {
            return l.longValue();
        }
        String zza2 = zza(contentResolver, str, (String) null);
        if (zza2 != null) {
            try {
                long parseLong = Long.parseLong(zza2);
                l = Long.valueOf(parseLong);
                j2 = parseLong;
            } catch (NumberFormatException e) {
            }
        }
        zza(zzb2, zzi, str, l);
        return j2;
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object zzb2 = zzb(contentResolver);
        Boolean bool = (Boolean) zza(zzg, str, Boolean.valueOf(z));
        if (bool != null) {
            return bool.booleanValue();
        }
        String zza2 = zza(contentResolver, str, (String) null);
        if (zza2 != null && !zza2.equals("")) {
            if (zza.matcher(zza2).matches()) {
                bool = true;
                z = true;
            } else if (zzb.matcher(zza2).matches()) {
                bool = false;
                z = false;
            } else {
                Log.w(Gservices.TAG, "attempt to read gservices key " + str + " (value \"" + zza2 + "\") as boolean");
            }
        }
        zza(zzb2, zzg, str, bool);
        return z;
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzd, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (query == null) {
            return treeMap;
        }
        while (query.moveToNext()) {
            try {
                treeMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return treeMap;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzfij.class) {
            zza(contentResolver);
            obj = zzk;
        }
        return obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0012, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T zza(java.util.HashMap r2, java.lang.String r3, java.lang.Object r4) {
        /*
            java.lang.Class<com.google.android.gms.internal.zzfij> r0 = com.google.android.gms.internal.zzfij.class
            monitor-enter(r0)
            boolean r1 = r2.containsKey(r3)     // Catch:{ all -> 0x0016 }
            if (r1 == 0) goto L_0x0013
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0016 }
            if (r2 == 0) goto L_0x0010
            goto L_0x0011
        L_0x0010:
            r2 = r4
        L_0x0011:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return r2
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            r2 = 0
            return r2
        L_0x0016:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfij.zza(java.util.HashMap, java.lang.String, java.lang.Object):java.lang.Object");
    }

    private static <T> void zza(Object obj, HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzfij.class) {
            if (obj == zzk) {
                hashMap.put(str, t);
                zzf.remove(str);
            }
        }
    }
}
