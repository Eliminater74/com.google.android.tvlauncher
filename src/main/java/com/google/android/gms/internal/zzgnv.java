package com.google.android.gms.internal;

import android.support.p001v4.internal.view.SupportMenu;
import com.google.android.gms.internal.zzgoj;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ExtensionRegistryLite */
public class zzgnv {
    static final zzgnv zza = new zzgnv(true);
    private static volatile boolean zzb = false;
    private static final Class<?> zzc = zzd();
    private static volatile zzgnv zzd;
    private final Map<zza, zzgoj.zzf<?, ?>> zze;

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /* compiled from: ExtensionRegistryLite */
    static final class zza {
        private final Object zza;
        private final int zzb;

        zza(Object obj, int i) {
            this.zza = obj;
            this.zzb = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zza) * SupportMenu.USER_MASK) + this.zzb;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza2 = (zza) obj;
            if (this.zza == zza2.zza && this.zzb == zza2.zzb) {
                return true;
            }
            return false;
        }
    }

    public static zzgnv zza() {
        return zzgnu.zza();
    }

    public static zzgnv zzb() {
        zzgnv zzgnv = zzd;
        if (zzgnv == null) {
            synchronized (zzgnv.class) {
                zzgnv = zzd;
                if (zzgnv == null) {
                    zzgnv = zzgnu.zzb();
                    zzd = zzgnv;
                }
            }
        }
        return zzgnv;
    }

    static zzgnv zzc() {
        return zzgoh.zza(zzgnv.class);
    }

    public final <ContainingType extends zzgpt> zzgoj.zzf<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return this.zze.get(new zza(containingtype, i));
    }

    zzgnv() {
        this.zze = new HashMap();
    }

    private zzgnv(boolean z) {
        this.zze = Collections.emptyMap();
    }
}
