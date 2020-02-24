package dagger.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public final class DaggerCollections {
    private static final int MAX_POWER_OF_TWO = 1073741824;

    private DaggerCollections() {
    }

    public static <T> List<T> presizedList(int size) {
        if (size == 0) {
            return Collections.emptyList();
        }
        return new ArrayList(size);
    }

    public static boolean hasDuplicates(List<?> list) {
        if (list.size() < 2) {
            return false;
        }
        if (list.size() != new HashSet<>(list).size()) {
            return true;
        }
        return false;
    }

    static <T> HashSet<T> newHashSetWithExpectedSize(int expectedSize) {
        return new HashSet<>(calculateInitialCapacity(expectedSize));
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int expectedSize) {
        return new LinkedHashMap<>(calculateInitialCapacity(expectedSize));
    }

    private static int calculateInitialCapacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824) {
            return (int) ((((float) expectedSize) / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }
}
