package com.google.android.libraries.performance.primes.hprof;

public final class Hprofs {
    public static final int DEBUGGER = 139;
    public static final int FINALIZING = 138;
    public static final int INTERNED_STRING = 137;
    public static final int JAVA_LOCAL = 3;
    public static final int JNI_GLOBAL = 1;
    public static final int JNI_LOCAL = 2;
    public static final int JNI_MONITOR = 142;
    public static final int MONITOR_USED = 7;
    public static final int NATIVE_STACK = 4;
    public static final int REFERENCE_CLEANUP = 140;
    public static final int STICKY_CLASS = 5;
    static final int TAG_CLASS_DUMP = 32;
    static final int TAG_CLASS_NAME = 2;
    static final int TAG_END_HEAP_SEGMENT = 44;
    static final int TAG_HEAP = 12;
    static final int TAG_HEAP_DUMP_INFO = 254;
    static final int TAG_HEAP_SEGMENT = 28;
    static final int TAG_INSTANCE_DUMP = 33;
    static final int TAG_OBJECT_ARRAY_DUMP = 34;
    static final int TAG_PRIMITIVE_ARRAY_DUMP = 35;
    static final int TAG_PRIMITIVE_ARRAY_NODATA = 195;
    static final int TAG_STACK_TRACE = 5;
    static final int TAG_STRING = 1;
    public static final int THREAD_BLOCK = 6;
    public static final int THREAD_OBJECT = 8;
    public static final int UNKNOWN = 255;
    public static final int UNREACHABLE = 144;
    public static final int VM_INTERNAL = 141;

    public interface RootTagSizeMapper {
        void addRootSize(int i, int i2);
    }

    private Hprofs() {
    }

    static int[] getTypesSizes(int idSize) {
        int[] typeSizes = new int[12];
        typeSizes[2] = idSize;
        typeSizes[4] = 1;
        typeSizes[5] = 2;
        typeSizes[6] = 4;
        typeSizes[7] = 8;
        typeSizes[8] = 1;
        typeSizes[9] = 2;
        typeSizes[10] = 4;
        typeSizes[11] = 8;
        return typeSizes;
    }

    static void addRootTagSizes(int idSize, RootTagSizeMapper mapper) {
        mapper.addRootSize(137, idSize);
        mapper.addRootSize(255, idSize);
        mapper.addRootSize(139, idSize);
        mapper.addRootSize(144, idSize);
        mapper.addRootSize(138, idSize);
        mapper.addRootSize(5, idSize);
        mapper.addRootSize(7, idSize);
        mapper.addRootSize(140, idSize);
        mapper.addRootSize(141, idSize);
        mapper.addRootSize(1, idSize + idSize);
        mapper.addRootSize(3, idSize + 8);
        mapper.addRootSize(2, idSize + 8);
        mapper.addRootSize(8, idSize + 8);
        mapper.addRootSize(142, idSize + 8);
        mapper.addRootSize(4, idSize + 4);
        mapper.addRootSize(6, idSize + 4);
    }
}
