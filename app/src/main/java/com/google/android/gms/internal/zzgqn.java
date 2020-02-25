package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: SchemaUtil */
final class zzgqn {
    private static final Class<?> zza = zzd();
    private static final zzgrd<?, ?> zzb = zza(false);
    private static final zzgrd<?, ?> zzc = zza(true);
    private static final zzgrd<?, ?> zzd = new zzgrf();

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzgoj.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zza(i, list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzb(i, list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzgrx zzgrx, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzgrx zzgrx) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zza(i, list);
        }
    }

    public static void zzb(int i, List<zzgnb> list, zzgrx zzgrx) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzb(i, list);
        }
    }

    public static void zzc(int i, List<?> list, zzgrx zzgrx) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzc(i, list);
        }
    }

    public static void zzd(int i, List<?> list, zzgrx zzgrx) {
        if (list != null && !list.isEmpty()) {
            zzgrx.zzd(i, list);
        }
    }

    static int zza(int i, List<Long> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zzd(zzgph.zzb(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zzd(list.get(i3).longValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zzb(int i, List<Long> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zze(zzgph.zzb(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zze(list.get(i3).longValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zzc(int i, List<Long> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zzf(zzgph.zzb(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zzf(list.get(i3).longValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zzd(int i, List<Integer> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zzk(zzgom.zzc(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zzk(list.get(i3).intValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zze(int i, List<Integer> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zzf(zzgom.zzc(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zzf(list.get(i3).intValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zzf(int i, List<Integer> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zzg(zzgom.zzc(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zzg(list.get(i3).intValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zzg(int i, List<Integer> list, boolean z) {
        int i2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            i2 = 0;
            while (i3 < size) {
                i2 += zzgnp.zzh(zzgom.zzc(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < size) {
                i4 = i2 + zzgnp.zzh(list.get(i3).intValue());
                i3++;
            }
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(i2);
        }
        return i2 + (size * zzgnp.zze(i));
    }

    static int zzh(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(size << 2);
        }
        return size * zzgnp.zzi(i, 0);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzg(int, long):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzg(int, int):int
      com.google.android.gms.internal.zzgnp.zzg(int, long):int */
    static int zzi(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(size << 3);
        }
        return size * zzgnp.zzg(i, 0L);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzb(int, boolean):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzb(int, double):int
      com.google.android.gms.internal.zzgnp.zzb(int, float):int
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpa):int
      com.google.android.gms.internal.zzgnp.zzb(int, java.lang.String):int
      com.google.android.gms.internal.zzgnp.zzb(int, int):void
      com.google.android.gms.internal.zzgnp.zzb(int, long):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgnb):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpt):void
      com.google.android.gms.internal.zzgnp.zzb(int, boolean):int */
    static int zzj(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return zzgnp.zze(i) + zzgnp.zzl(size);
        }
        return size * zzgnp.zzb(i, true);
    }

    static int zza(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int zze = zzgnp.zze(i) * size;
        if (list instanceof zzgpc) {
            zzgpc zzgpc = (zzgpc) list;
            while (i4 < size) {
                Object zzb2 = zzgpc.zzb(i4);
                if (zzb2 instanceof zzgnb) {
                    i3 = zzgnp.zzb((zzgnb) zzb2);
                } else {
                    i3 = zzgnp.zzb((String) zzb2);
                }
                zze += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof zzgnb) {
                    i2 = zzgnp.zzb((zzgnb) obj);
                } else {
                    i2 = zzgnp.zzb((String) obj);
                }
                zze += i2;
                i4++;
            }
        }
        return zze;
    }

    static int zza(int i, Object obj) {
        if (obj instanceof zzgpa) {
            return zzgnp.zza(i, (zzgpa) obj);
        }
        return zzgnp.zzc(i, (zzgpt) obj);
    }

    static int zzb(int i, List<?> list) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zze = zzgnp.zze(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof zzgpa) {
                i2 = zzgnp.zza((zzgpa) obj);
            } else {
                i2 = zzgnp.zzb((zzgpt) obj);
            }
            zze += i2;
        }
        return zze;
    }

    static int zzc(int i, List<zzgnb> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zze = size * zzgnp.zze(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zze += zzgnp.zzb(list.get(i2));
        }
        return zze;
    }

    static int zzd(int i, List<zzgpt> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzgnp.zzf(i, list.get(i3));
        }
        return i2;
    }

    public static boolean zza(int i, int i2, int i3) {
        if (i2 < 40) {
            return true;
        }
        long j = (long) i3;
        if ((((long) i2) - ((long) i)) + 1 + 9 <= (2 * j) + 3 + ((j + 3) * 3)) {
            return true;
        }
        return false;
    }

    public static zzgrd<?, ?> zza() {
        return zzb;
    }

    public static zzgrd<?, ?> zzb() {
        return zzc;
    }

    public static zzgrd<?, ?> zzc() {
        return zzd;
    }

    private static zzgrd<?, ?> zza(boolean z) {
        try {
            Class<?> zze = zze();
            if (zze == null) {
                return null;
            }
            return (zzgrd) zze.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zza(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzgpo zzgpo, Object obj, Object obj2, long j) {
        zzgrj.zza(obj, j, zzgpo.zza(zzgrj.zzf(obj, j), zzgrj.zzf(obj2, j)));
    }

    static <T, FT extends zzgoc<FT>> void zza(zzgnw<FT> zzgnw, T t, T t2) {
        zzgoa<FT> zza2 = zzgnw.zza((Object) t2);
        if (!zza2.zzb()) {
            zzgnw.zzb(t).zza((zzgoa<zzgoj.zze>) zza2);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrd.zza(java.lang.Object, java.lang.Object):void
     arg types: [T, UT]
     candidates:
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgqk):boolean
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, java.lang.Object):void */
    static <T, UT, UB> void zza(zzgrd<UT, UB> zzgrd, T t, T t2) {
        zzgrd.zza((Object) t, (Object) zzgrd.zzc(zzgrd.zzb(t), zzgrd.zzb(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzgop<?> zzgop, UB ub, zzgrd<UT, UB> zzgrd) {
        UB ub2;
        if (zzgop == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            ub2 = ub;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (zzgop.zza(intValue) != null) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub2 = zza(i, intValue, ub2, zzgrd);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            ub2 = ub;
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (zzgop.zza(intValue2) == null) {
                    UB zza2 = zza(i, intValue2, ub2, zzgrd);
                    it.remove();
                    ub2 = zza2;
                }
            }
        }
        return ub2;
    }

    static <UT, UB> UB zza(int i, int i2, Object obj, zzgrd zzgrd) {
        if (obj == null) {
            obj = zzgrd.zza();
        }
        zzgrd.zza(obj, i, (long) i2);
        return obj;
    }
}
