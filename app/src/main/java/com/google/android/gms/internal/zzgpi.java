package com.google.android.gms.internal;

/* compiled from: ManifestSchemaFactory */
final class zzgpi implements zzgqm {
    private static final zzgps zzb = new zzgpj();
    private final zzgps zza;

    public zzgpi() {
        this(new zzgpk(zzgoi.zza(), zza()));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T
     arg types: [com.google.android.gms.internal.zzgps, java.lang.String]
     candidates:
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.Object):java.lang.Object
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T */
    private zzgpi(zzgps zzgps) {
        this.zza = (zzgps) zzgon.zza((Object) zzgps, "messageInfoFactory");
    }

    private static boolean zza(zzgpr zzgpr) {
        return zzgpr.zza() == zzgoj.zzg.zzi;
    }

    private static zzgps zza() {
        try {
            return (zzgps) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzb;
        }
    }

    public final <T> zzgql<T> zza(Class<T> cls) {
        zzgqn.zza((Class<?>) cls);
        zzgpr zzb2 = this.zza.zzb(cls);
        if (zzb2.zzb()) {
            if (zzgoj.class.isAssignableFrom(cls)) {
                return zzgpz.zza(cls, zzgqn.zzc(), zzgnz.zza(), zzb2.zzc());
            }
            return zzgpz.zza(cls, zzgqn.zza(), zzgnz.zzb(), zzb2.zzc());
        } else if (zzgoj.class.isAssignableFrom(cls)) {
            if (zza(zzb2)) {
                return zzgpx.zza(cls, zzb2, zzgqd.zzb(), zzgpd.zzb(), zzgqn.zzc(), zzgnz.zza(), zzgpq.zzb());
            }
            return zzgpx.zza(cls, zzb2, zzgqd.zzb(), zzgpd.zzb(), zzgqn.zzc(), (zzgnw<?>) null, zzgpq.zzb());
        } else if (zza(zzb2)) {
            return zzgpx.zza(cls, zzb2, zzgqd.zza(), zzgpd.zza(), zzgqn.zza(), zzgnz.zzb(), zzgpq.zza());
        } else {
            return zzgpx.zza(cls, zzb2, zzgqd.zza(), zzgpd.zza(), zzgqn.zzb(), (zzgnw<?>) null, zzgpq.zza());
        }
    }
}
