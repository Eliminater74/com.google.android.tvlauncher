package com.google.android.gms.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: Protobuf */
final class zzgqf {
    private static final zzgqf zza = new zzgqf();
    private final zzgqm zzb;
    private final ConcurrentMap<Class<?>, zzgql<?>> zzc = new ConcurrentHashMap();

    private zzgqf() {
        String[] strArr = {"com.google.protobuf.AndroidProto3SchemaFactory"};
        zzgqm zzgqm = null;
        for (int i = 0; i <= 0; i++) {
            zzgqm = zza(strArr[0]);
            if (zzgqm != null) {
                break;
            }
        }
        this.zzb = zzgqm == null ? new zzgpi() : zzgqm;
    }

    public static zzgqf zza() {
        return zza;
    }

    private static zzgqm zza(String str) {
        try {
            return (zzgqm) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T
     arg types: [java.lang.Class, java.lang.String]
     candidates:
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.Object):java.lang.Object
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T
     arg types: [com.google.android.gms.internal.zzgql<T>, java.lang.String]
     candidates:
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.Object):java.lang.Object
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T */
    public final <T> zzgql<T> zza(Class cls) {
        zzgon.zza((Object) cls, "messageType");
        zzgql<T> zzgql = this.zzc.get(cls);
        if (zzgql != null) {
            return zzgql;
        }
        zzgql<T> zza2 = this.zzb.zza(cls);
        zzgon.zza((Object) cls, "messageType");
        zzgon.zza((Object) zza2, "schema");
        zzgql<T> putIfAbsent = this.zzc.putIfAbsent(cls, zza2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        return zza2;
    }

    public final <T> zzgql<T> zza(Object obj) {
        return zza((Class) obj.getClass());
    }
}
