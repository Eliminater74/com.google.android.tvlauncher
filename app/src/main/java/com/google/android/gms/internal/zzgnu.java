package com.google.android.gms.internal;

/* compiled from: ExtensionRegistryFactory */
final class zzgnu {
    private static final Class<?> zza = zzc();

    private static Class<?> zzc() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzgnv zza() {
        if (zza != null) {
            try {
                return zza("getEmptyRegistry");
            } catch (Exception e) {
            }
        }
        return zzgnv.zza;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0016  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0010  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.android.gms.internal.zzgnv zzb() {
        /*
            java.lang.Class<?> r0 = com.google.android.gms.internal.zzgnu.zza
            if (r0 == 0) goto L_0x000d
            java.lang.String r0 = "loadGeneratedRegistry"
            com.google.android.gms.internal.zzgnv r0 = zza(r0)     // Catch:{ Exception -> 0x000c }
            goto L_0x000e
        L_0x000c:
            r0 = move-exception
        L_0x000d:
            r0 = 0
        L_0x000e:
            if (r0 != 0) goto L_0x0014
            com.google.android.gms.internal.zzgnv r0 = com.google.android.gms.internal.zzgnv.zzc()
        L_0x0014:
            if (r0 != 0) goto L_0x001a
            com.google.android.gms.internal.zzgnv r0 = zza()
        L_0x001a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgnu.zzb():com.google.android.gms.internal.zzgnv");
    }

    private static final zzgnv zza(String str) throws Exception {
        return (zzgnv) zza.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
