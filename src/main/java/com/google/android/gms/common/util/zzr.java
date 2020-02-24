package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* compiled from: ProcessUtils */
public final class zzr {
    private static String zza = null;
    private static int zzb = 0;

    public static String zza() {
        if (zza == null) {
            if (zzb == 0) {
                zzb = Process.myPid();
            }
            zza = zza(zzb);
        }
        return zza;
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zza(int r4) {
        /*
            r0 = 0
            if (r4 > 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 25
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            r2.<init>(r1)     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            java.lang.String r1 = "/proc/"
            r2.append(r1)     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            r2.append(r4)     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            java.lang.String r4 = "/cmdline"
            r2.append(r4)     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            java.lang.String r4 = r2.toString()     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            java.io.BufferedReader r4 = zza(r4)     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x0033, all -> 0x002e }
            java.lang.String r0 = r1.trim()     // Catch:{ IOException -> 0x0033, all -> 0x002e }
            com.google.android.gms.common.util.IOUtils.closeQuietly(r4)
            goto L_0x0040
        L_0x002e:
            r0 = move-exception
            r3 = r0
            r0 = r4
            r4 = r3
            goto L_0x0036
        L_0x0033:
            r1 = move-exception
            goto L_0x003c
        L_0x0035:
            r4 = move-exception
        L_0x0036:
            com.google.android.gms.common.util.IOUtils.closeQuietly(r0)
            throw r4
        L_0x003a:
            r4 = move-exception
            r4 = r0
        L_0x003c:
            com.google.android.gms.common.util.IOUtils.closeQuietly(r4)
        L_0x0040:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzr.zza(int):java.lang.String");
    }

    private static BufferedReader zza(String str) throws IOException {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return new BufferedReader(new FileReader(str));
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
