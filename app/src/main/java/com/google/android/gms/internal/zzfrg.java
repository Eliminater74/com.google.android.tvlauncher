package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

import java.io.PrintStream;
import java.io.PrintWriter;

/* compiled from: ThrowableExtension */
public final class zzfrg {
    private static final zzfrh zza;
    private static final int zzb;

    /* JADX WARNING: Removed duplicated region for block: B:18:0x006a  */
    static {
        /*
            r0 = 1
            java.lang.Integer r1 = zza()     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x0016
            int r2 = r1.intValue()     // Catch:{ all -> 0x002b }
            r3 = 19
            if (r2 < r3) goto L_0x0016
            com.google.android.gms.internal.zzfrl r2 = new com.google.android.gms.internal.zzfrl     // Catch:{ all -> 0x002b }
            r2.<init>()     // Catch:{ all -> 0x002b }
            goto L_0x0065
        L_0x0016:
            java.lang.String r2 = "com.google.devtools.build.android.desugar.runtime.twr_disable_mimic"
            boolean r2 = java.lang.Boolean.getBoolean(r2)     // Catch:{ all -> 0x002b }
            r2 = r2 ^ r0
            if (r2 == 0) goto L_0x0025
            com.google.android.gms.internal.zzfrk r2 = new com.google.android.gms.internal.zzfrk     // Catch:{ all -> 0x002b }
            r2.<init>()     // Catch:{ all -> 0x002b }
            goto L_0x0065
        L_0x0025:
            com.google.android.gms.internal.zzfrg$zza r2 = new com.google.android.gms.internal.zzfrg$zza     // Catch:{ all -> 0x002b }
            r2.<init>()     // Catch:{ all -> 0x002b }
            goto L_0x0065
        L_0x002b:
            r2 = move-exception
            goto L_0x002f
        L_0x002d:
            r2 = move-exception
            r1 = 0
        L_0x002f:
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.Class<com.google.android.gms.internal.zzfrg$zza> r4 = com.google.android.gms.internal.zzfrg.zza.class
            java.lang.String r4 = r4.getName()
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r5 = r5.length()
            int r5 = r5 + 132
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "An error has occured when initializing the try-with-resources desuguring strategy. The default strategy "
            r6.append(r5)
            r6.append(r4)
            java.lang.String r4 = "will be used. The error is: "
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r3.println(r4)
            java.io.PrintStream r3 = java.lang.System.err
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2, r3)
            com.google.android.gms.internal.zzfrg$zza r2 = new com.google.android.gms.internal.zzfrg$zza
            r2.<init>()
        L_0x0065:
            com.google.android.gms.internal.zzfrg.zza = r2
            if (r1 != 0) goto L_0x006a
            goto L_0x006e
        L_0x006a:
            int r0 = r1.intValue()
        L_0x006e:
            com.google.android.gms.internal.zzfrg.zzb = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfrg.<clinit>():void");
    }

    public static void zza(Throwable th, Throwable th2) {
        zza.zza(th, th2);
    }

    public static void zza(Throwable th) {
        zza.zza(th);
    }

    public static void zza(Throwable th, PrintWriter printWriter) {
        zza.zza(th, printWriter);
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zza.zza(th, printStream);
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            ThrowableExtension.printStackTrace(e, System.err);
            return null;
        }
    }

    /* compiled from: ThrowableExtension */
    static final class zza extends zzfrh {
        zza() {
        }

        public final void zza(Throwable th, Throwable th2) {
        }

        public final void zza(Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }

        public final void zza(Throwable th, PrintStream printStream) {
            ThrowableExtension.printStackTrace(th, printStream);
        }

        public final void zza(Throwable th, PrintWriter printWriter) {
            ThrowableExtension.printStackTrace(th, printWriter);
        }
    }
}
