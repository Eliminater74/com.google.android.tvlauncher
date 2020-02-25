package com.google.android.gms.internal;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;

import libcore.io.Memory;
import sun.misc.Unsafe;

/* compiled from: UnsafeUtil */
final class zzgrj {
    /* access modifiers changed from: private */
    public static final boolean zzw = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    private static final Logger zza = Logger.getLogger(zzgrj.class.getName());
    private static final Unsafe zzb = zzc();
    private static final Class<?> zzc = zzgmt.zzb();
    private static final boolean zzd = zzc(Long.TYPE);
    private static final boolean zze = zzc(Integer.TYPE);
    private static final zzd zzf;
    private static final boolean zzg = zzg();
    private static final boolean zzh = zzf();
    /* access modifiers changed from: private */
    public static final long zzi = ((long) zza(byte[].class));
    private static final long zzj = ((long) zza(boolean[].class));
    private static final long zzk = ((long) zzb(boolean[].class));
    private static final long zzl = ((long) zza(int[].class));
    private static final long zzm = ((long) zzb(int[].class));
    private static final long zzn = ((long) zza(long[].class));
    private static final long zzo = ((long) zzb(long[].class));
    private static final long zzp = ((long) zza(float[].class));
    private static final long zzq = ((long) zzb(float[].class));
    private static final long zzr = ((long) zza(double[].class));
    private static final long zzs = ((long) zzb(double[].class));
    private static final long zzt = ((long) zza(Object[].class));
    private static final long zzu = ((long) zzb(Object[].class));
    private static final long zzv;

    static {
        Field field;
        zzd zzd2;
        zzd zzd3 = null;
        if (zzb != null) {
            if (!zzgmt.zza()) {
                zzd3 = new zzc(zzb);
            } else if (zzd) {
                zzd3 = new zzb(zzb);
            } else if (zze) {
                zzd3 = new zza(zzb);
            }
        }
        zzf = zzd3;
        if (!zzgmt.zza() || (field = zza(Buffer.class, "effectiveDirectAddress")) == null) {
            field = zza(Buffer.class, "address");
        }
        zzv = (field == null || (zzd2 = zzf) == null) ? -1 : zzd2.zza(field);
    }

    private zzgrj() {
    }

    static boolean zza() {
        return zzh;
    }

    static boolean zzb() {
        return zzg;
    }

    static long zza(Field field) {
        return zzf.zza(field);
    }

    private static int zza(Class<?> cls) {
        if (zzh) {
            return zzf.zza.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzb(Class<?> cls) {
        if (zzh) {
            return zzf.zza.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zza(Object obj, long j) {
        return zzf.zze(obj, j);
    }

    static void zza(Object obj, long j, int i) {
        zzf.zza(obj, j, i);
    }

    static long zzb(Object obj, long j) {
        return zzf.zzf(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzf.zza(obj, j, j2);
    }

    static boolean zzc(Object obj, long j) {
        return zzf.zzb(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzf.zza(obj, j, z);
    }

    static float zzd(Object obj, long j) {
        return zzf.zzc(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzf.zza(obj, j, f);
    }

    static double zze(Object obj, long j) {
        return zzf.zzd(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzf.zza(obj, j, d);
    }

    static Object zzf(Object obj, long j) {
        return zzf.zza.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzf.zza.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzf.zza(bArr, zzi + j);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, byte):void
     arg types: [byte[], long, byte]
     candidates:
      com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zzd.zza(java.lang.Object, long, byte):void */
    static void zza(byte[] bArr, long j, byte b) {
        zzf.zza((Object) bArr, zzi + j, b);
    }

    static void zza(byte[] bArr, long j, long j2, long j3) {
        zzf.zza(bArr, j, j2, j3);
    }

    static void zza(long j, byte b) {
        zzf.zza(j, b);
    }

    static long zza(ByteBuffer byteBuffer) {
        return zzf.zzf(byteBuffer, zzv);
    }

    static Unsafe zzc() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzgrk());
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean zzf() {
        Unsafe unsafe = zzb;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzgmt.zza()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger = zza;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzg() {
        Unsafe unsafe = zzb;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzgmt.zza()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger = zza;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzc(Class<?> cls) {
        if (!zzgmt.zza()) {
            return false;
        }
        try {
            Class<?> cls2 = zzc;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static byte zzk(Object obj, long j) {
        return (byte) (zza(obj, -4 & j) >>> ((int) (((j ^ -1) & 3) << 3)));
    }

    /* access modifiers changed from: private */
    public static byte zzl(Object obj, long j) {
        return (byte) (zza(obj, -4 & j) >>> ((int) ((j & 3) << 3)));
    }

    /* access modifiers changed from: private */
    public static void zzc(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = ((((int) j) ^ -1) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zza(obj, j2) & ((255 << i) ^ -1)));
    }

    /* access modifiers changed from: private */
    public static void zzd(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zza(obj, j2) & ((255 << i) ^ -1)));
    }

    /* access modifiers changed from: private */
    public static boolean zzm(Object obj, long j) {
        return zzk(obj, j) != 0;
    }

    /* access modifiers changed from: private */
    public static boolean zzn(Object obj, long j) {
        return zzl(obj, j) != 0;
    }

    /* access modifiers changed from: private */
    public static void zzd(Object obj, long j, boolean z) {
        zzc(obj, j, z ? (byte) 1 : 0);
    }

    /* access modifiers changed from: private */
    public static void zze(Object obj, long j, boolean z) {
        zzd(obj, j, z ? (byte) 1 : 0);
    }

    /* compiled from: UnsafeUtil */
    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte((int) (j & -1), b);
        }

        public final byte zza(Object obj, long j) {
            if (zzgrj.zzw) {
                return zzgrj.zzk(obj, j);
            }
            return zzgrj.zzl(obj, j);
        }

        public final void zza(Object obj, long j, byte b) {
            if (zzgrj.zzw) {
                zzgrj.zzc(obj, j, b);
            } else {
                zzgrj.zzd(obj, j, b);
            }
        }

        public final boolean zzb(Object obj, long j) {
            if (zzgrj.zzw) {
                return zzgrj.zzm(obj, j);
            }
            return zzgrj.zzn(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzgrj.zzw) {
                zzgrj.zzd(obj, j, z);
            } else {
                zzgrj.zze(obj, j, z);
            }
        }

        public final float zzc(Object obj, long j) {
            return Float.intBitsToFloat(zze(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zza(obj, j, Float.floatToIntBits(f));
        }

        public final double zzd(Object obj, long j) {
            return Double.longBitsToDouble(zzf(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray((int) (j2 & -1), bArr, (int) j, (int) j3);
        }
    }

    /* compiled from: UnsafeUtil */
    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte(j, b);
        }

        public final byte zza(Object obj, long j) {
            if (zzgrj.zzw) {
                return zzgrj.zzk(obj, j);
            }
            return zzgrj.zzl(obj, j);
        }

        public final void zza(Object obj, long j, byte b) {
            if (zzgrj.zzw) {
                zzgrj.zzc(obj, j, b);
            } else {
                zzgrj.zzd(obj, j, b);
            }
        }

        public final boolean zzb(Object obj, long j) {
            if (zzgrj.zzw) {
                return zzgrj.zzm(obj, j);
            }
            return zzgrj.zzn(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzgrj.zzw) {
                zzgrj.zzd(obj, j, z);
            } else {
                zzgrj.zze(obj, j, z);
            }
        }

        public final float zzc(Object obj, long j) {
            return Float.intBitsToFloat(zze(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zza(obj, j, Float.floatToIntBits(f));
        }

        public final double zzd(Object obj, long j) {
            return Double.longBitsToDouble(zzf(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray(j2, bArr, (int) j, (int) j3);
        }
    }

    /* compiled from: UnsafeUtil */
    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            this.zza.putByte(j, b);
        }

        public final byte zza(Object obj, long j) {
            return this.zza.getByte(obj, j);
        }

        public final void zza(Object obj, long j, byte b) {
            this.zza.putByte(obj, j, b);
        }

        public final boolean zzb(Object obj, long j) {
            return this.zza.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zza.putBoolean(obj, j, z);
        }

        public final float zzc(Object obj, long j) {
            return this.zza.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zza.putFloat(obj, j, f);
        }

        public final double zzd(Object obj, long j) {
            return this.zza.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zza.putDouble(obj, j, d);
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            this.zza.copyMemory(bArr, zzgrj.zzi + j, (Object) null, j2, j3);
        }
    }

    /* compiled from: UnsafeUtil */
    static abstract class zzd {
        Unsafe zza;

        zzd(Unsafe unsafe) {
            this.zza = unsafe;
        }

        public abstract byte zza(Object obj, long j);

        public abstract void zza(long j, byte b);

        public abstract void zza(Object obj, long j, byte b);

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zza(byte[] bArr, long j, long j2, long j3);

        public abstract boolean zzb(Object obj, long j);

        public abstract float zzc(Object obj, long j);

        public abstract double zzd(Object obj, long j);

        public final long zza(Field field) {
            return this.zza.objectFieldOffset(field);
        }

        public final int zze(Object obj, long j) {
            return this.zza.getInt(obj, j);
        }

        public final void zza(Object obj, long j, int i) {
            this.zza.putInt(obj, j, i);
        }

        public final long zzf(Object obj, long j) {
            return this.zza.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zza.putLong(obj, j, j2);
        }
    }
}
