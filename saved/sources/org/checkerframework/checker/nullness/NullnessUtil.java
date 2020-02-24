package org.checkerframework.checker.nullness;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public final class NullnessUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private NullnessUtil() {
        throw new AssertionError("shouldn't be instantiated");
    }

    @EnsuresNonNull({"#1"})
    public static <T> T castNonNull(T ref) {
        return ref;
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[] castNonNullDeep(T[] arr) {
        return castNonNullArray(arr);
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][] castNonNullDeep(T[][] arr) {
        return (Object[][]) castNonNullArray(arr);
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][][] castNonNullDeep(T[][][] arr) {
        return (Object[][][]) castNonNullArray(arr);
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][][][] castNonNullDeep(T[][][][] arr) {
        return (Object[][][][]) castNonNullArray(arr);
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][][][][] castNonNullDeep(T[][][][][] arr) {
        return (Object[][][][][]) castNonNullArray(arr);
    }

    private static <T> T[] castNonNullArray(T[] arr) {
        for (T checkIfArray : arr) {
            checkIfArray(checkIfArray);
        }
        return (Object[]) arr;
    }

    private static void checkIfArray(Object ref) {
        Class<?> comp = ref.getClass().getComponentType();
        if (comp != null && !comp.isPrimitive()) {
            castNonNullArray((Object[]) ref);
        }
    }
}
