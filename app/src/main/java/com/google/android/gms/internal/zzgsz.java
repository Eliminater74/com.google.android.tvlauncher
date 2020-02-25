package com.google.android.gms.internal;

import com.google.android.gms.internal.zzgoj.zza;

import java.io.IOException;

/* compiled from: ClientAnalytics */
public final class zzgsz {

    /* compiled from: ClientAnalytics */
    public static final class zza extends zzgoj<zza, C2022zza> implements zzgpv {
        /* access modifiers changed from: private */
        public static final zza zzh;
        private static volatile zzgqe<zza> zzi;

        static {
            zza zza = new zza();
            zzh = zza;
            zza.zzl();
            zzgoj.zza(zza.class, zzh);
        }

        private int zzd;
        private int zze;
        private String zzf = "";
        private String zzg = "";

        private zza() {
        }

        public static zzgqe<zza> zzc() {
            return (zzgqe) zzh.zza(zzgoj.zzg.zzh, (Object) null, (Object) null);
        }

        public final void zza(zzgnp zzgnp) throws IOException {
            if ((this.zzd & 1) == 1) {
                zzgnp.zzb(1, this.zze);
            }
            if ((this.zzd & 2) == 2) {
                zzgnp.zza(2, this.zzf);
            }
            if ((this.zzd & 4) == 4) {
                zzgnp.zza(3, this.zzg);
            }
            this.zzb.zza(zzgnp);
        }

        public final int zza() {
            int i = this.zzc;
            if (i != -1) {
                return i;
            }
            int i2 = 0;
            if ((this.zzd & 1) == 1) {
                i2 = 0 + zzgnp.zzf(1, this.zze);
            }
            if ((this.zzd & 2) == 2) {
                i2 += zzgnp.zzb(2, this.zzf);
            }
            if ((this.zzd & 4) == 4) {
                i2 += zzgnp.zzb(3, this.zzg);
            }
            int zze2 = i2 + this.zzb.zze();
            this.zzc = zze2;
            return zze2;
        }

        /* access modifiers changed from: protected */
        public final Object zza(int i, Object obj, Object obj2) {
            switch (zzgta.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return zzh;
                case 3:
                    return null;
                case 4:
                    return new C2022zza(null);
                case 5:
                    return zzh;
                case 6:
                    if (zzi == null) {
                        synchronized (zza.class) {
                            if (zzi == null) {
                                zzi = new zzgoj.zzb(zzh);
                            }
                        }
                    }
                    return zzi;
                case 7:
                    return (byte) 1;
                case 8:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        /* access modifiers changed from: protected */
        public final Object zzb() throws Exception {
            return zza(zzh, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001\u0004\u0000\u0002\b\u0001\u0003\b\u0002", new Object[]{"zzd", "zze", "zzf", "zzg"});
        }

        /* renamed from: com.google.android.gms.internal.zzgsz$zza$zza  reason: collision with other inner class name */
        /* compiled from: ClientAnalytics */
        public static final class C2022zza extends zzgoj.zza<zza, C2022zza> implements zzgpv {
            private C2022zza() {
                super(zza.zzh);
            }

            /* synthetic */ C2022zza(zzgta zzgta) {
                this();
            }
        }
    }
}
