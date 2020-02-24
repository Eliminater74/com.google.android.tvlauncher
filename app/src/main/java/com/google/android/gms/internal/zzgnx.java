package com.google.android.gms.internal;

import com.google.android.gms.internal.zzgoj;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

/* compiled from: ExtensionSchemaLite */
final class zzgnx extends zzgnw<zzgoj.zze> {
    zzgnx() {
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(Class<?> cls) {
        return zzgoj.zzd.class.isAssignableFrom(cls);
    }

    /* access modifiers changed from: package-private */
    public final zzgoa<zzgoj.zze> zza(Object obj) {
        return ((zzgoj.zzd) obj).zzd;
    }

    /* access modifiers changed from: package-private */
    public final void zza(Object obj, zzgoa<zzgoj.zze> zzgoa) {
        ((zzgoj.zzd) obj).zzd = zzgoa;
    }

    /* access modifiers changed from: package-private */
    public final zzgoa<zzgoj.zze> zzb(Object obj) {
        zzgoa<zzgoj.zze> zza = zza(obj);
        if (!zza.zzd()) {
            return zza;
        }
        zzgoa<zzgoj.zze> zzgoa = (zzgoa) zza.clone();
        zza(obj, zzgoa);
        return zzgoa;
    }

    /* access modifiers changed from: package-private */
    public final void zzc(Object obj) {
        zza(obj).zzc();
    }

    /* access modifiers changed from: package-private */
    public final <UT, UB> UB zza(zzgqk zzgqk, Object obj, zzgnv zzgnv, zzgoa<zzgoj.zze> zzgoa, UB ub, zzgrd<UT, UB> zzgrd) throws IOException {
        Object obj2;
        Object zza;
        zzgoj.zzf zzf = (zzgoj.zzf) obj;
        int i = zzf.zzb.zzb;
        if (zzf.zzb.zzc != zzgrr.ENUM) {
            switch (zzgny.zza[zzf.zzb.zzc.ordinal()]) {
                case 1:
                    obj2 = Double.valueOf(zzgqk.zzd());
                    break;
                case 2:
                    obj2 = Float.valueOf(zzgqk.zze());
                    break;
                case 3:
                    obj2 = Long.valueOf(zzgqk.zzg());
                    break;
                case 4:
                    obj2 = Long.valueOf(zzgqk.zzf());
                    break;
                case 5:
                    obj2 = Integer.valueOf(zzgqk.zzh());
                    break;
                case 6:
                    obj2 = Long.valueOf(zzgqk.zzi());
                    break;
                case 7:
                    obj2 = Integer.valueOf(zzgqk.zzj());
                    break;
                case 8:
                    obj2 = Boolean.valueOf(zzgqk.zzk());
                    break;
                case 9:
                    obj2 = Integer.valueOf(zzgqk.zzo());
                    break;
                case 10:
                    obj2 = Integer.valueOf(zzgqk.zzq());
                    break;
                case 11:
                    obj2 = Long.valueOf(zzgqk.zzr());
                    break;
                case 12:
                    obj2 = Integer.valueOf(zzgqk.zzs());
                    break;
                case 13:
                    obj2 = Long.valueOf(zzgqk.zzt());
                    break;
                case 14:
                    throw new IllegalStateException("Shouldn't reach here.");
                case 15:
                    obj2 = zzgqk.zzn();
                    break;
                case 16:
                    obj2 = zzgqk.zzl();
                    break;
                case 17:
                    obj2 = zzgqk.zzb(zzf.zza.getClass(), zzgnv);
                    break;
                case 18:
                    obj2 = zzgqk.zza(zzf.zza.getClass(), zzgnv);
                    break;
                default:
                    obj2 = null;
                    break;
            }
        } else {
            int zzh = zzgqk.zzh();
            if (zzf.zzb.zza.zza(zzh) == null) {
                return zzgqn.zza(i, zzh, ub, zzgrd);
            }
            obj2 = Integer.valueOf(zzh);
        }
        int i2 = zzgny.zza[zzf.zzb.zzc.ordinal()];
        if ((i2 == 17 || i2 == 18) && (zza = zzgoa.zza(zzf.zzb)) != null) {
            obj2 = zzgon.zza(zza, obj2);
        }
        zzgoa.zza(zzf.zzb, obj2);
        return ub;
    }

    /* access modifiers changed from: package-private */
    public final int zza(Map.Entry<?, ?> entry) {
        return ((zzgoj.zze) entry.getKey()).zzb;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgrx zzgrx, Map.Entry<?, ?> entry) {
        zzgoj.zze zze = (zzgoj.zze) entry.getKey();
        switch (zzgny.zza[zze.zzc.ordinal()]) {
            case 1:
                zzgrx.zza(zze.zzb, ((Double) entry.getValue()).doubleValue());
                return;
            case 2:
                zzgrx.zza(zze.zzb, ((Float) entry.getValue()).floatValue());
                return;
            case 3:
                zzgrx.zza(zze.zzb, ((Long) entry.getValue()).longValue());
                return;
            case 4:
                zzgrx.zzc(zze.zzb, ((Long) entry.getValue()).longValue());
                return;
            case 5:
                zzgrx.zzc(zze.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case 6:
                zzgrx.zzd(zze.zzb, ((Long) entry.getValue()).longValue());
                return;
            case 7:
                zzgrx.zzd(zze.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case 8:
                zzgrx.zza(zze.zzb, ((Boolean) entry.getValue()).booleanValue());
                return;
            case 9:
                zzgrx.zze(zze.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case 10:
                zzgrx.zza(zze.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case 11:
                zzgrx.zzb(zze.zzb, ((Long) entry.getValue()).longValue());
                return;
            case 12:
                zzgrx.zzf(zze.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case 13:
                zzgrx.zze(zze.zzb, ((Long) entry.getValue()).longValue());
                return;
            case 14:
                zzgrx.zzc(zze.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case 15:
                zzgrx.zza(zze.zzb, (zzgnb) entry.getValue());
                return;
            case 16:
                zzgrx.zza(zze.zzb, (String) entry.getValue());
                return;
            case 17:
                zzgrx.zzb(zze.zzb, entry.getValue());
                return;
            case 18:
                zzgrx.zza(zze.zzb, entry.getValue());
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public final Object zza(zzgnv zzgnv, zzgpt zzgpt, int i) {
        return zzgnv.zza(zzgpt, i);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgqk zzgqk, Object obj, zzgnv zzgnv, zzgoa<zzgoj.zze> zzgoa) throws IOException {
        zzgoj.zzf zzf = (zzgoj.zzf) obj;
        zzgoa.zza(zzf.zzb, zzgqk.zza(zzf.zza.getClass(), zzgnv));
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgnb zzgnb, Object obj, zzgnv zzgnv, zzgoa<zzgoj.zze> zzgoa) throws IOException {
        zzgoj.zzf zzf = (zzgoj.zzf) obj;
        zzgpt zze = zzf.zza.zzq().zze();
        ByteBuffer wrap = ByteBuffer.wrap(zzgnb.zzc());
        if (wrap.hasArray()) {
            zzgmy zzgmy = new zzgmy(wrap, true);
            zzgqf.zza().zza(zze).zza(zze, zzgmy, zzgnv);
            zzgoa.zza(zzf.zzb, zze);
            if (zzgmy.zza() != Integer.MAX_VALUE) {
                throw zzgot.zze();
            }
            return;
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }
}
