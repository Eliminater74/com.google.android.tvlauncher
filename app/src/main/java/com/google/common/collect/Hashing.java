package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
final class Hashing {

    /* renamed from: C1 */
    private static final long f170C1 = -862048943;

    /* renamed from: C2 */
    private static final long f171C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 1073741824;

    private Hashing() {
    }

    static int smear(int hashCode) {
        return (int) (((long) Integer.rotateLeft((int) (((long) hashCode) * f170C1), 15)) * f171C2);
    }

    static int smearedHash(@NullableDecl Object o) {
        return smear(o == null ? 0 : o.hashCode());
    }

    static int closedTableSize(int expectedEntries, double loadFactor) {
        int expectedEntries2 = Math.max(expectedEntries, 2);
        int tableSize = Integer.highestOneBit(expectedEntries2);
        double d = (double) tableSize;
        Double.isNaN(d);
        if (expectedEntries2 <= ((int) (d * loadFactor))) {
            return tableSize;
        }
        int tableSize2 = tableSize << 1;
        if (tableSize2 > 0) {
            return tableSize2;
        }
        return 1073741824;
    }

    static boolean needsResizing(int size, int tableSize, double loadFactor) {
        double d = (double) tableSize;
        Double.isNaN(d);
        return ((double) size) > d * loadFactor && tableSize < 1073741824;
    }
}
