package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: FieldSet */
final class zzgoa<FieldDescriptorType extends zzgoc<FieldDescriptorType>> {
    private static final zzgoa zzd = new zzgoa(true);
    private final zzgqo<FieldDescriptorType, Object> zza = zzgqo.zza(16);
    private boolean zzb;
    private boolean zzc = false;

    private zzgoa() {
    }

    private zzgoa(boolean z) {
        zzc();
    }

    public static <T extends zzgoc<T>> zzgoa<T> zza() {
        return zzd;
    }

    private static void zza(zzgrr zzgrr, Object obj) {
        zzgon.zza(obj);
        boolean z = false;
        switch (zzgob.zza[zzgrr.zza().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if ((obj instanceof zzgnb) || (obj instanceof byte[])) {
                    z = true;
                    break;
                }
            case 8:
                if ((obj instanceof Integer) || (obj instanceof zzgoo)) {
                    z = true;
                    break;
                }
            case 9:
                if ((obj instanceof zzgpt) || (obj instanceof zzgow)) {
                    z = true;
                    break;
                }
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private static boolean zza(Map.Entry<zzgoj.zze, Object> entry) {
        zzgoc key = entry.getKey();
        if (key.zzc() == zzgrw.MESSAGE) {
            if (key.zzd()) {
                for (zzgpt zzm : (List) entry.getValue()) {
                    if (!zzm.zzm()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzgpt) {
                    if (!((zzgpt) value).zzm()) {
                        return false;
                    }
                } else if (value instanceof zzgow) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzgqa) {
            return ((zzgqa) obj).zzb();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    static void zza(zzgnp zzgnp, zzgrr zzgrr, int i, Object obj) throws IOException {
        if (zzgrr == zzgrr.GROUP) {
            zzgpt zzgpt = (zzgpt) obj;
            zzgon.zza(zzgpt);
            zzgnp.zze(i, zzgpt);
            return;
        }
        zzgnp.zza(i, zzgrr.zzb());
        zza(zzgnp, zzgrr, obj);
    }

    private static void zza(zzgnp zzgnp, zzgrr zzgrr, Object obj) throws IOException {
        switch (zzgob.zzb[zzgrr.ordinal()]) {
            case 1:
                zzgnp.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzgnp.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzgnp.zza(((Long) obj).longValue());
                return;
            case 4:
                zzgnp.zza(((Long) obj).longValue());
                return;
            case 5:
                zzgnp.zza(((Integer) obj).intValue());
                return;
            case 6:
                zzgnp.zzc(((Long) obj).longValue());
                return;
            case 7:
                zzgnp.zzd(((Integer) obj).intValue());
                return;
            case 8:
                zzgnp.zza(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzgpt) obj).zza(zzgnp);
                return;
            case 10:
                zzgnp.zza((zzgpt) obj);
                return;
            case 11:
                if (obj instanceof zzgnb) {
                    zzgnp.zza((zzgnb) obj);
                    return;
                } else {
                    zzgnp.zza((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzgnb) {
                    zzgnp.zza((zzgnb) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzgnp.zzc(bArr, 0, bArr.length);
                return;
            case 13:
                zzgnp.zzb(((Integer) obj).intValue());
                return;
            case 14:
                zzgnp.zzd(((Integer) obj).intValue());
                return;
            case 15:
                zzgnp.zzc(((Long) obj).longValue());
                return;
            case 16:
                zzgnp.zzc(((Integer) obj).intValue());
                return;
            case 17:
                zzgnp.zzb(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzgoo) {
                    zzgnp.zza(((zzgoo) obj).zza());
                    return;
                } else {
                    zzgnp.zza(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public static void zza(zzgoc<?> zzgoc, Object obj, zzgnp zzgnp) throws IOException {
        zzgrr zzb2 = zzgoc.zzb();
        int zza2 = zzgoc.zza();
        if (zzgoc.zzd()) {
            List<Object> list = (List) obj;
            if (zzgoc.zze()) {
                zzgnp.zza(zza2, 2);
                int i = 0;
                for (Object zzb3 : list) {
                    i += zzb(zzb2, zzb3);
                }
                zzgnp.zzb(i);
                for (Object zza3 : list) {
                    zza(zzgnp, zzb2, zza3);
                }
                return;
            }
            for (Object zza4 : list) {
                zza(zzgnp, zzb2, zza2, zza4);
            }
        } else if (obj instanceof zzgow) {
            zza(zzgnp, zzb2, zza2, zzgow.zza());
        } else {
            zza(zzgnp, zzb2, zza2, obj);
        }
    }

    private static int zzc(Map.Entry<FieldDescriptorType, Object> entry) {
        zzgoc zzgoc = (zzgoc) entry.getKey();
        Object value = entry.getValue();
        if (zzgoc.zzc() != zzgrw.MESSAGE || zzgoc.zzd() || zzgoc.zze()) {
            return zzb(zzgoc, value);
        }
        if (value instanceof zzgow) {
            return zzgnp.zzb(((zzgoc) entry.getKey()).zza(), (zzgow) value);
        }
        return zzgnp.zzd(((zzgoc) entry.getKey()).zza(), (zzgpt) value);
    }

    static int zza(zzgrr zzgrr, int i, Object obj) {
        int zze = zzgnp.zze(i);
        if (zzgrr == zzgrr.GROUP) {
            zzgon.zza((zzgpt) obj);
            zze <<= 1;
        }
        return zze + zzb(zzgrr, obj);
    }

    private static int zzb(zzgrr zzgrr, Object obj) {
        switch (zzgob.zzb[zzgrr.ordinal()]) {
            case 1:
                return zzgnp.zzb(((Double) obj).doubleValue());
            case 2:
                return zzgnp.zzb(((Float) obj).floatValue());
            case 3:
                return zzgnp.zzd(((Long) obj).longValue());
            case 4:
                return zzgnp.zze(((Long) obj).longValue());
            case 5:
                return zzgnp.zzf(((Integer) obj).intValue());
            case 6:
                return zzgnp.zzg(((Long) obj).longValue());
            case 7:
                return zzgnp.zzi(((Integer) obj).intValue());
            case 8:
                return zzgnp.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzgnp.zzc((zzgpt) obj);
            case 10:
                if (obj instanceof zzgow) {
                    return zzgnp.zza((zzgow) obj);
                }
                return zzgnp.zzb((zzgpt) obj);
            case 11:
                if (obj instanceof zzgnb) {
                    return zzgnp.zzb((zzgnb) obj);
                }
                return zzgnp.zzb((String) obj);
            case 12:
                if (obj instanceof zzgnb) {
                    return zzgnp.zzb((zzgnb) obj);
                }
                return zzgnp.zzb((byte[]) obj);
            case 13:
                return zzgnp.zzg(((Integer) obj).intValue());
            case 14:
                return zzgnp.zzj(((Integer) obj).intValue());
            case 15:
                return zzgnp.zzh(((Long) obj).longValue());
            case 16:
                return zzgnp.zzh(((Integer) obj).intValue());
            case 17:
                return zzgnp.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzgoo) {
                    return zzgnp.zzk(((zzgoo) obj).zza());
                }
                return zzgnp.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static int zzb(zzgoc<?> zzgoc, Object obj) {
        zzgrr zzb2 = zzgoc.zzb();
        int zza2 = zzgoc.zza();
        if (!zzgoc.zzd()) {
            return zza(zzb2, zza2, obj);
        }
        int i = 0;
        if (zzgoc.zze()) {
            for (Object zzb3 : (List) obj) {
                i += zzb(zzb2, zzb3);
            }
            return zzgnp.zze(zza2) + i + zzgnp.zzm(i);
        }
        for (Object zza3 : (List) obj) {
            i += zza(zzb2, zza2, zza3);
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzb() {
        return this.zza.isEmpty();
    }

    public final void zzc() {
        if (!this.zzb) {
            this.zza.zza();
            this.zzb = true;
        }
    }

    public final boolean zzd() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgoa)) {
            return false;
        }
        return this.zza.equals(((zzgoa) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> zze() {
        if (this.zzc) {
            return new zzgoz(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    /* access modifiers changed from: package-private */
    public final Iterator<Map.Entry<FieldDescriptorType, Object>> zzf() {
        if (this.zzc) {
            return new zzgoz(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    public final Object zza(zzgoj.zze zze) {
        Object obj = this.zza.get(zze);
        if (obj instanceof zzgow) {
            return zzgow.zza();
        }
        return obj;
    }

    public final void zza(zzgoj.zze zze, Object obj) {
        if (!zze.zzd()) {
            zza(zze.zzb(), obj);
        } else if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(zze.zzb(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzgow) {
            this.zzc = true;
        }
        this.zza.put(zze, obj);
    }

    public final boolean zzg() {
        for (int i = 0; i < this.zza.zzc(); i++) {
            if (!zza((Map.Entry<zzgoj.zze, Object>) this.zza.zzb(i))) {
                return false;
            }
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.zza.zzd()) {
            if (!zza((Map.Entry<zzgoj.zze, Object>) entry)) {
                return false;
            }
        }
        return true;
    }

    public final void zza(zzgoa<zzgoj.zze> zzgoa) {
        for (int i = 0; i < zzgoa.zza.zzc(); i++) {
            zzb(zzgoa.zza.zzb(i));
        }
        for (Map.Entry<FieldDescriptorType, Object> zzb2 : zzgoa.zza.zzd()) {
            zzb(zzb2);
        }
    }

    private final void zzb(Map.Entry<FieldDescriptorType, Object> entry) {
        Object obj;
        zzgoc zzgoc = (zzgoc) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzgow) {
            value = zzgow.zza();
        }
        if (zzgoc.zzd()) {
            Object zza2 = zza((zzgoj.zze) zzgoc);
            if (zza2 == null) {
                zza2 = new ArrayList();
            }
            for (Object zza3 : (List) value) {
                ((List) zza2).add(zza(zza3));
            }
            this.zza.put(zzgoc, zza2);
        } else if (zzgoc.zzc() == zzgrw.MESSAGE) {
            Object zza4 = zza((zzgoj.zze) zzgoc);
            if (zza4 == null) {
                this.zza.put(zzgoc, zza(value));
                return;
            }
            if (zza4 instanceof zzgqa) {
                obj = zzgoc.zza((zzgqa) zza4, (zzgqa) value);
            } else {
                obj = zzgoc.zza(((zzgpt) zza4).zzp(), (zzgpt) value).zzf();
            }
            this.zza.put(zzgoc, obj);
        } else {
            this.zza.put(zzgoc, zza(value));
        }
    }

    public final int zzh() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzc(); i2++) {
            Map.Entry<FieldDescriptorType, Object> zzb2 = this.zza.zzb(i2);
            i += zzb((zzgoc) zzb2.getKey(), zzb2.getValue());
        }
        for (Map.Entry next : this.zza.zzd()) {
            i += zzb((zzgoc) next.getKey(), next.getValue());
        }
        return i;
    }

    public final int zzi() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzc(); i2++) {
            i += zzc(this.zza.zzb(i2));
        }
        for (Map.Entry<FieldDescriptorType, Object> zzc2 : this.zza.zzd()) {
            i += zzc(zzc2);
        }
        return i;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgoa.zza(com.google.android.gms.internal.zzgoj$zze, java.lang.Object):void
     arg types: [com.google.android.gms.internal.zzgoc, java.lang.Object]
     candidates:
      com.google.android.gms.internal.zzgoa.zza(com.google.android.gms.internal.zzgrr, java.lang.Object):void
      com.google.android.gms.internal.zzgoa.zza(com.google.android.gms.internal.zzgoj$zze, java.lang.Object):void */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzgoa zzgoa = new zzgoa();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<FieldDescriptorType, Object> zzb2 = this.zza.zzb(i);
            zzgoa.zza((zzgoj.zze) ((zzgoc) zzb2.getKey()), zzb2.getValue());
        }
        for (Map.Entry next : this.zza.zzd()) {
            zzgoa.zza((zzgoj.zze) ((zzgoc) next.getKey()), next.getValue());
        }
        zzgoa.zzc = this.zzc;
        return zzgoa;
    }
}
