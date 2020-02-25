package com.google.protobuf;

import com.google.common.primitives.UnsignedBytes;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import libcore.io.Memory;
import sun.misc.Unsafe;

final class UnsafeUtil {
    static final boolean IS_BIG_ENDIAN = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    private static final int BYTE_ARRAY_ALIGNMENT = ((int) (BYTE_ARRAY_BASE_OFFSET & 7));
    private static final Class<?> MEMORY_CLASS = Android.getMemoryClass();
    private static final boolean IS_ANDROID_32 = determineAndroidSupportByAddressSize(Integer.TYPE);
    private static final boolean IS_ANDROID_64 = determineAndroidSupportByAddressSize(Long.TYPE);
    private static final long OBJECT_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(Object[].class));
    private static final long OBJECT_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(Object[].class));
    private static final int STRIDE = 8;
    private static final int STRIDE_ALIGNMENT_MASK = 7;
    private static final Unsafe UNSAFE = getUnsafe();
    private static final MemoryAccessor MEMORY_ACCESSOR = getMemoryAccessor();
    private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());
    private static final long INT_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(int[].class));
    private static final long INT_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(int[].class));
    private static final long LONG_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(long[].class));
    private static final long LONG_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(long[].class));
    private static final Logger logger = Logger.getLogger(UnsafeUtil.class.getName());
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
    private static final long BOOLEAN_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(boolean[].class));
    private static final long BOOLEAN_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(boolean[].class));
    static final long BYTE_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(byte[].class));
    private static final long DOUBLE_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(double[].class));
    private static final long DOUBLE_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(double[].class));
    private static final long FLOAT_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(float[].class));
    private static final long FLOAT_ARRAY_INDEX_SCALE = ((long) arrayIndexScale(float[].class));
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();

    private UnsafeUtil() {
    }

    static boolean hasUnsafeArrayOperations() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }

    static boolean hasUnsafeByteBufferOperations() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static boolean isAndroid64() {
        return IS_ANDROID_64;
    }

    static <T> T allocateInstance(Class<T> clazz) {
        try {
            return UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static long objectFieldOffset(Field field) {
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    private static int arrayBaseOffset(Class<?> clazz) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayBaseOffset(clazz);
        }
        return -1;
    }

    private static int arrayIndexScale(Class<?> clazz) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayIndexScale(clazz);
        }
        return -1;
    }

    static byte getByte(Object target, long offset) {
        return MEMORY_ACCESSOR.getByte(target, offset);
    }

    static void putByte(Object target, long offset, byte value) {
        MEMORY_ACCESSOR.putByte(target, offset, value);
    }

    static int getInt(Object target, long offset) {
        return MEMORY_ACCESSOR.getInt(target, offset);
    }

    static void putInt(Object target, long offset, int value) {
        MEMORY_ACCESSOR.putInt(target, offset, value);
    }

    static long getLong(Object target, long offset) {
        return MEMORY_ACCESSOR.getLong(target, offset);
    }

    static void putLong(Object target, long offset, long value) {
        MEMORY_ACCESSOR.putLong(target, offset, value);
    }

    static boolean getBoolean(Object target, long offset) {
        return MEMORY_ACCESSOR.getBoolean(target, offset);
    }

    static void putBoolean(Object target, long offset, boolean value) {
        MEMORY_ACCESSOR.putBoolean(target, offset, value);
    }

    static float getFloat(Object target, long offset) {
        return MEMORY_ACCESSOR.getFloat(target, offset);
    }

    static void putFloat(Object target, long offset, float value) {
        MEMORY_ACCESSOR.putFloat(target, offset, value);
    }

    static double getDouble(Object target, long offset) {
        return MEMORY_ACCESSOR.getDouble(target, offset);
    }

    static void putDouble(Object target, long offset, double value) {
        MEMORY_ACCESSOR.putDouble(target, offset, value);
    }

    static Object getObject(Object target, long offset) {
        return MEMORY_ACCESSOR.getObject(target, offset);
    }

    static void putObject(Object target, long offset, Object value) {
        MEMORY_ACCESSOR.putObject(target, offset, value);
    }

    static byte getByte(byte[] target, long index) {
        return MEMORY_ACCESSOR.getByte(target, BYTE_ARRAY_BASE_OFFSET + index);
    }

    static void putByte(byte[] target, long index, byte value) {
        MEMORY_ACCESSOR.putByte(target, BYTE_ARRAY_BASE_OFFSET + index, value);
    }

    static int getInt(int[] target, long index) {
        return MEMORY_ACCESSOR.getInt(target, INT_ARRAY_BASE_OFFSET + (INT_ARRAY_INDEX_SCALE * index));
    }

    static void putInt(int[] target, long index, int value) {
        MEMORY_ACCESSOR.putInt(target, INT_ARRAY_BASE_OFFSET + (INT_ARRAY_INDEX_SCALE * index), value);
    }

    static long getLong(long[] target, long index) {
        return MEMORY_ACCESSOR.getLong(target, LONG_ARRAY_BASE_OFFSET + (LONG_ARRAY_INDEX_SCALE * index));
    }

    static void putLong(long[] target, long index, long value) {
        MEMORY_ACCESSOR.putLong(target, (LONG_ARRAY_INDEX_SCALE * index) + LONG_ARRAY_BASE_OFFSET, value);
    }

    static boolean getBoolean(boolean[] target, long index) {
        return MEMORY_ACCESSOR.getBoolean(target, BOOLEAN_ARRAY_BASE_OFFSET + (BOOLEAN_ARRAY_INDEX_SCALE * index));
    }

    static void putBoolean(boolean[] target, long index, boolean value) {
        MEMORY_ACCESSOR.putBoolean(target, BOOLEAN_ARRAY_BASE_OFFSET + (BOOLEAN_ARRAY_INDEX_SCALE * index), value);
    }

    static float getFloat(float[] target, long index) {
        return MEMORY_ACCESSOR.getFloat(target, FLOAT_ARRAY_BASE_OFFSET + (FLOAT_ARRAY_INDEX_SCALE * index));
    }

    static void putFloat(float[] target, long index, float value) {
        MEMORY_ACCESSOR.putFloat(target, FLOAT_ARRAY_BASE_OFFSET + (FLOAT_ARRAY_INDEX_SCALE * index), value);
    }

    static double getDouble(double[] target, long index) {
        return MEMORY_ACCESSOR.getDouble(target, DOUBLE_ARRAY_BASE_OFFSET + (DOUBLE_ARRAY_INDEX_SCALE * index));
    }

    static void putDouble(double[] target, long index, double value) {
        MEMORY_ACCESSOR.putDouble(target, (DOUBLE_ARRAY_INDEX_SCALE * index) + DOUBLE_ARRAY_BASE_OFFSET, value);
    }

    static Object getObject(Object[] target, long index) {
        return MEMORY_ACCESSOR.getObject(target, OBJECT_ARRAY_BASE_OFFSET + (OBJECT_ARRAY_INDEX_SCALE * index));
    }

    static void putObject(Object[] target, long index, Object value) {
        MEMORY_ACCESSOR.putObject(target, OBJECT_ARRAY_BASE_OFFSET + (OBJECT_ARRAY_INDEX_SCALE * index), value);
    }

    static void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
        MEMORY_ACCESSOR.copyMemory(src, srcIndex, targetOffset, length);
    }

    static void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
        MEMORY_ACCESSOR.copyMemory(srcOffset, target, targetIndex, length);
    }

    static void copyMemory(byte[] src, long srcIndex, byte[] target, long targetIndex, long length) {
        System.arraycopy(src, (int) srcIndex, target, (int) targetIndex, (int) length);
    }

    static byte getByte(long address) {
        return MEMORY_ACCESSOR.getByte(address);
    }

    static void putByte(long address, byte value) {
        MEMORY_ACCESSOR.putByte(address, value);
    }

    static int getInt(long address) {
        return MEMORY_ACCESSOR.getInt(address);
    }

    static void putInt(long address, int value) {
        MEMORY_ACCESSOR.putInt(address, value);
    }

    static long getLong(long address) {
        return MEMORY_ACCESSOR.getLong(address);
    }

    static void putLong(long address, long value) {
        MEMORY_ACCESSOR.putLong(address, value);
    }

    static long addressOffset(ByteBuffer buffer) {
        return MEMORY_ACCESSOR.getLong(buffer, BUFFER_ADDRESS_OFFSET);
    }

    static Object getStaticObject(Field field) {
        return MEMORY_ACCESSOR.getStaticObject(field);
    }

    static Unsafe getUnsafe() {
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Class<Unsafe> k = Unsafe.class;
                    for (Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get(null);
                        if (k.isInstance(x)) {
                            return k.cast(x);
                        }
                    }
                    return null;
                }
            });
        } catch (Throwable th) {
            return null;
        }
    }

    private static MemoryAccessor getMemoryAccessor() {
        if (UNSAFE == null) {
            return null;
        }
        if (!Android.isOnAndroidDevice()) {
            return new JvmMemoryAccessor(UNSAFE);
        }
        if (IS_ANDROID_64) {
            return new Android64MemoryAccessor(UNSAFE);
        }
        if (IS_ANDROID_32) {
            return new Android32MemoryAccessor(UNSAFE);
        }
        return null;
    }

    private static boolean supportsUnsafeArrayOperations() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> clazz = unsafe.getClass();
            clazz.getMethod("objectFieldOffset", Field.class);
            clazz.getMethod("arrayBaseOffset", Class.class);
            clazz.getMethod("arrayIndexScale", Class.class);
            clazz.getMethod("getInt", Object.class, Long.TYPE);
            clazz.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            clazz.getMethod("getLong", Object.class, Long.TYPE);
            clazz.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            clazz.getMethod("getObject", Object.class, Long.TYPE);
            clazz.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (Android.isOnAndroidDevice()) {
                return true;
            }
            clazz.getMethod("getByte", Object.class, Long.TYPE);
            clazz.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            clazz.getMethod("getBoolean", Object.class, Long.TYPE);
            clazz.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            clazz.getMethod("getFloat", Object.class, Long.TYPE);
            clazz.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            clazz.getMethod("getDouble", Object.class, Long.TYPE);
            clazz.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable e) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> clazz = unsafe.getClass();
            clazz.getMethod("objectFieldOffset", Field.class);
            clazz.getMethod("getLong", Object.class, Long.TYPE);
            if (bufferAddressField() == null) {
                return false;
            }
            if (Android.isOnAndroidDevice()) {
                return true;
            }
            clazz.getMethod("getByte", Long.TYPE);
            clazz.getMethod("putByte", Long.TYPE, Byte.TYPE);
            clazz.getMethod("getInt", Long.TYPE);
            clazz.getMethod("putInt", Long.TYPE, Integer.TYPE);
            clazz.getMethod("getLong", Long.TYPE);
            clazz.getMethod("putLong", Long.TYPE, Long.TYPE);
            clazz.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            clazz.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable e) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static boolean determineAndroidSupportByAddressSize(Class<?> addressClass) {
        if (!Android.isOnAndroidDevice()) {
            return false;
        }
        try {
            Class<?> clazz = MEMORY_CLASS;
            clazz.getMethod("peekLong", addressClass, Boolean.TYPE);
            clazz.getMethod("pokeLong", addressClass, Long.TYPE, Boolean.TYPE);
            clazz.getMethod("pokeInt", addressClass, Integer.TYPE, Boolean.TYPE);
            clazz.getMethod("peekInt", addressClass, Boolean.TYPE);
            clazz.getMethod("pokeByte", addressClass, Byte.TYPE);
            clazz.getMethod("peekByte", addressClass);
            clazz.getMethod("pokeByteArray", addressClass, byte[].class, Integer.TYPE, Integer.TYPE);
            clazz.getMethod("peekByteArray", addressClass, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static Field bufferAddressField() {
        Field field;
        if (Android.isOnAndroidDevice() && (field = field(Buffer.class, "effectiveDirectAddress")) != null) {
            return field;
        }
        Field field2 = field(Buffer.class, "address");
        if (field2 == null || field2.getType() != Long.TYPE) {
            return null;
        }
        return field2;
    }

    private static int firstDifferingByteIndexNativeEndian(long left, long right) {
        int n;
        if (IS_BIG_ENDIAN) {
            n = Long.numberOfLeadingZeros(left ^ right);
        } else {
            n = Long.numberOfTrailingZeros(left ^ right);
        }
        return n >> 3;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.UnsafeUtil.getLong(java.lang.Object, long):long
     arg types: [byte[], long]
     candidates:
      com.google.protobuf.UnsafeUtil.getLong(long[], long):long
      com.google.protobuf.UnsafeUtil.getLong(java.lang.Object, long):long */
    static int mismatch(byte[] left, int leftOff, byte[] right, int rightOff, int length) {
        if (leftOff < 0 || rightOff < 0 || length < 0 || leftOff + length > left.length || rightOff + length > right.length) {
            throw new IndexOutOfBoundsException();
        }
        int index = 0;
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            int leftAlignment = (BYTE_ARRAY_ALIGNMENT + leftOff) & 7;
            while (index < length && (leftAlignment & 7) != 0) {
                if (left[leftOff + index] != right[rightOff + index]) {
                    return index;
                }
                index++;
                leftAlignment++;
            }
            int strideLength = ((length - index) & -8) + index;
            while (index < strideLength) {
                long leftLongWord = getLong((Object) left, BYTE_ARRAY_BASE_OFFSET + ((long) leftOff) + ((long) index));
                long rightLongWord = getLong((Object) right, BYTE_ARRAY_BASE_OFFSET + ((long) rightOff) + ((long) index));
                if (leftLongWord != rightLongWord) {
                    return firstDifferingByteIndexNativeEndian(leftLongWord, rightLongWord) + index;
                }
                index += 8;
            }
        }
        while (index < length) {
            if (left[leftOff + index] != right[rightOff + index]) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private static long fieldOffset(Field field) {
        MemoryAccessor memoryAccessor;
        if (field == null || (memoryAccessor = MEMORY_ACCESSOR) == null) {
            return -1;
        }
        return memoryAccessor.objectFieldOffset(field);
    }

    private static Field field(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static byte getByteBigEndian(Object target, long offset) {
        return (byte) ((getInt(target, -4 & offset) >>> ((int) (((-1 ^ offset) & 3) << 3))) & 255);
    }

    /* access modifiers changed from: private */
    public static byte getByteLittleEndian(Object target, long offset) {
        return (byte) ((getInt(target, -4 & offset) >>> ((int) ((3 & offset) << 3))) & 255);
    }

    /* access modifiers changed from: private */
    public static void putByteBigEndian(Object target, long offset, byte value) {
        int shift = ((((int) offset) ^ -1) & 3) << 3;
        long j = -4 & offset;
        putInt(target, j, (((255 << shift) ^ -1) & getInt(target, offset & -4)) | ((value & UnsignedBytes.MAX_VALUE) << shift));
    }

    /* access modifiers changed from: private */
    public static void putByteLittleEndian(Object target, long offset, byte value) {
        int shift = (((int) offset) & 3) << 3;
        long j = -4 & offset;
        putInt(target, j, (((255 << shift) ^ -1) & getInt(target, offset & -4)) | ((value & UnsignedBytes.MAX_VALUE) << shift));
    }

    /* access modifiers changed from: private */
    public static boolean getBooleanBigEndian(Object target, long offset) {
        return getByteBigEndian(target, offset) != 0;
    }

    /* access modifiers changed from: private */
    public static boolean getBooleanLittleEndian(Object target, long offset) {
        return getByteLittleEndian(target, offset) != 0;
    }

    /* access modifiers changed from: private */
    public static void putBooleanBigEndian(Object target, long offset, boolean value) {
        putByteBigEndian(target, offset, value ? (byte) 1 : 0);
    }

    /* access modifiers changed from: private */
    public static void putBooleanLittleEndian(Object target, long offset, boolean value) {
        putByteLittleEndian(target, offset, value ? (byte) 1 : 0);
    }

    private static abstract class MemoryAccessor {
        Unsafe unsafe;

        MemoryAccessor(Unsafe unsafe2) {
            this.unsafe = unsafe2;
        }

        public abstract void copyMemory(long j, byte[] bArr, long j2, long j3);

        public abstract void copyMemory(byte[] bArr, long j, long j2, long j3);

        public abstract boolean getBoolean(Object obj, long j);

        public abstract byte getByte(long j);

        public abstract byte getByte(Object obj, long j);

        public abstract double getDouble(Object obj, long j);

        public abstract float getFloat(Object obj, long j);

        public abstract int getInt(long j);

        public abstract long getLong(long j);

        public abstract Object getStaticObject(Field field);

        public abstract void putBoolean(Object obj, long j, boolean z);

        public abstract void putByte(long j, byte b);

        public abstract void putByte(Object obj, long j, byte b);

        public abstract void putDouble(Object obj, long j, double d);

        public abstract void putFloat(Object obj, long j, float f);

        public abstract void putInt(long j, int i);

        public abstract void putLong(long j, long j2);

        public final long objectFieldOffset(Field field) {
            return this.unsafe.objectFieldOffset(field);
        }

        public final int getInt(Object target, long offset) {
            return this.unsafe.getInt(target, offset);
        }

        public final void putInt(Object target, long offset, int value) {
            this.unsafe.putInt(target, offset, value);
        }

        public final long getLong(Object target, long offset) {
            return this.unsafe.getLong(target, offset);
        }

        public final void putLong(Object target, long offset, long value) {
            this.unsafe.putLong(target, offset, value);
        }

        public final Object getObject(Object target, long offset) {
            return this.unsafe.getObject(target, offset);
        }

        public final void putObject(Object target, long offset, Object value) {
            this.unsafe.putObject(target, offset, value);
        }

        public final int arrayBaseOffset(Class<?> clazz) {
            return this.unsafe.arrayBaseOffset(clazz);
        }

        public final int arrayIndexScale(Class<?> clazz) {
            return this.unsafe.arrayIndexScale(clazz);
        }
    }

    private static final class JvmMemoryAccessor extends MemoryAccessor {
        JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        public byte getByte(long address) {
            return this.unsafe.getByte(address);
        }

        public void putByte(long address, byte value) {
            this.unsafe.putByte(address, value);
        }

        public int getInt(long address) {
            return this.unsafe.getInt(address);
        }

        public void putInt(long address, int value) {
            this.unsafe.putInt(address, value);
        }

        public long getLong(long address) {
            return this.unsafe.getLong(address);
        }

        public void putLong(long address, long value) {
            this.unsafe.putLong(address, value);
        }

        public byte getByte(Object target, long offset) {
            return this.unsafe.getByte(target, offset);
        }

        public void putByte(Object target, long offset, byte value) {
            this.unsafe.putByte(target, offset, value);
        }

        public boolean getBoolean(Object target, long offset) {
            return this.unsafe.getBoolean(target, offset);
        }

        public void putBoolean(Object target, long offset, boolean value) {
            this.unsafe.putBoolean(target, offset, value);
        }

        public float getFloat(Object target, long offset) {
            return this.unsafe.getFloat(target, offset);
        }

        public void putFloat(Object target, long offset, float value) {
            this.unsafe.putFloat(target, offset, value);
        }

        public double getDouble(Object target, long offset) {
            return this.unsafe.getDouble(target, offset);
        }

        public void putDouble(Object target, long offset, double value) {
            this.unsafe.putDouble(target, offset, value);
        }

        public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
            this.unsafe.copyMemory((Object) null, srcOffset, target, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + targetIndex, length);
        }

        public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
            this.unsafe.copyMemory(src, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + srcIndex, (Object) null, targetOffset, length);
        }

        public Object getStaticObject(Field field) {
            return getObject(this.unsafe.staticFieldBase(field), this.unsafe.staticFieldOffset(field));
        }
    }

    private static final class Android64MemoryAccessor extends MemoryAccessor {
        Android64MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        public byte getByte(long address) {
            return Memory.peekByte(address);
        }

        public void putByte(long address, byte value) {
            Memory.pokeByte(address, value);
        }

        public int getInt(long address) {
            return Memory.peekInt(address, false);
        }

        public void putInt(long address, int value) {
            Memory.pokeInt(address, value, false);
        }

        public long getLong(long address) {
            return Memory.peekLong(address, false);
        }

        public void putLong(long address, long value) {
            Memory.pokeLong(address, value, false);
        }

        public byte getByte(Object target, long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return UnsafeUtil.getByteBigEndian(target, offset);
            }
            return UnsafeUtil.getByteLittleEndian(target, offset);
        }

        public void putByte(Object target, long offset, byte value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putByteBigEndian(target, offset, value);
            } else {
                UnsafeUtil.putByteLittleEndian(target, offset, value);
            }
        }

        public boolean getBoolean(Object target, long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return UnsafeUtil.getBooleanBigEndian(target, offset);
            }
            return UnsafeUtil.getBooleanLittleEndian(target, offset);
        }

        public void putBoolean(Object target, long offset, boolean value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putBooleanBigEndian(target, offset, value);
            } else {
                UnsafeUtil.putBooleanLittleEndian(target, offset, value);
            }
        }

        public float getFloat(Object target, long offset) {
            return Float.intBitsToFloat(getInt(target, offset));
        }

        public void putFloat(Object target, long offset, float value) {
            putInt(target, offset, Float.floatToIntBits(value));
        }

        public double getDouble(Object target, long offset) {
            return Double.longBitsToDouble(getLong(target, offset));
        }

        public void putDouble(Object target, long offset, double value) {
            putLong(target, offset, Double.doubleToLongBits(value));
        }

        public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
            Memory.peekByteArray(srcOffset, target, (int) targetIndex, (int) length);
        }

        public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
            Memory.pokeByteArray(targetOffset, src, (int) srcIndex, (int) length);
        }

        public Object getStaticObject(Field field) {
            try {
                return field.get(null);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
    }

    private static final class Android32MemoryAccessor extends MemoryAccessor {
        private static final long SMALL_ADDRESS_MASK = -1;

        Android32MemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        private static int smallAddress(long address) {
            return (int) (-1 & address);
        }

        public byte getByte(long address) {
            return Memory.peekByte(smallAddress(address));
        }

        public void putByte(long address, byte value) {
            Memory.pokeByte(smallAddress(address), value);
        }

        public int getInt(long address) {
            return Memory.peekInt(smallAddress(address), false);
        }

        public void putInt(long address, int value) {
            Memory.pokeInt(smallAddress(address), value, false);
        }

        public long getLong(long address) {
            return Memory.peekLong(smallAddress(address), false);
        }

        public void putLong(long address, long value) {
            Memory.pokeLong(smallAddress(address), value, false);
        }

        public byte getByte(Object target, long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return UnsafeUtil.getByteBigEndian(target, offset);
            }
            return UnsafeUtil.getByteLittleEndian(target, offset);
        }

        public void putByte(Object target, long offset, byte value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putByteBigEndian(target, offset, value);
            } else {
                UnsafeUtil.putByteLittleEndian(target, offset, value);
            }
        }

        public boolean getBoolean(Object target, long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return UnsafeUtil.getBooleanBigEndian(target, offset);
            }
            return UnsafeUtil.getBooleanLittleEndian(target, offset);
        }

        public void putBoolean(Object target, long offset, boolean value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                UnsafeUtil.putBooleanBigEndian(target, offset, value);
            } else {
                UnsafeUtil.putBooleanLittleEndian(target, offset, value);
            }
        }

        public float getFloat(Object target, long offset) {
            return Float.intBitsToFloat(getInt(target, offset));
        }

        public void putFloat(Object target, long offset, float value) {
            putInt(target, offset, Float.floatToIntBits(value));
        }

        public double getDouble(Object target, long offset) {
            return Double.longBitsToDouble(getLong(target, offset));
        }

        public void putDouble(Object target, long offset, double value) {
            putLong(target, offset, Double.doubleToLongBits(value));
        }

        public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
            Memory.peekByteArray(smallAddress(srcOffset), target, (int) targetIndex, (int) length);
        }

        public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
            Memory.pokeByteArray(smallAddress(targetOffset), src, (int) srcIndex, (int) length);
        }

        public Object getStaticObject(Field field) {
            try {
                return field.get(null);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
    }
}
