package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.AbstractMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    private static final byte ABSENT = -1;
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 128;
    static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap(null, new Object[0], 0);
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 32768;
    private static final long serialVersionUID = 0;
    @VisibleForTesting
    final transient Object[] alternatingKeysAndValues;
    private final transient Object hashTable;
    private final transient int size;

    static <K, V> RegularImmutableMap<K, V> create(int n, Object[] alternatingKeysAndValues2) {
        if (n == 0) {
            return (RegularImmutableMap) EMPTY;
        }
        if (n == 1) {
            CollectPreconditions.checkEntryNotNull(alternatingKeysAndValues2[0], alternatingKeysAndValues2[1]);
            return new RegularImmutableMap<>(null, alternatingKeysAndValues2, 1);
        }
        Preconditions.checkPositionIndex(n, alternatingKeysAndValues2.length >> 1);
        return new RegularImmutableMap<>(createHashTable(alternatingKeysAndValues2, n, ImmutableSet.chooseTableSize(n), 0), alternatingKeysAndValues2, n);
    }

    /* JADX INFO: Multiple debug info for r1v2 int[]: [D('hashTable' short[]), D('hashTable' int[])] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.Arrays.fill(short[], short):void}
     arg types: [short[], int]
     candidates:
      ClspMth{java.util.Arrays.fill(double[], double):void}
      ClspMth{java.util.Arrays.fill(byte[], byte):void}
      ClspMth{java.util.Arrays.fill(long[], long):void}
      ClspMth{java.util.Arrays.fill(boolean[], boolean):void}
      ClspMth{java.util.Arrays.fill(char[], char):void}
      ClspMth{java.util.Arrays.fill(java.lang.Object[], java.lang.Object):void}
      ClspMth{java.util.Arrays.fill(int[], int):void}
      ClspMth{java.util.Arrays.fill(float[], float):void}
      ClspMth{java.util.Arrays.fill(short[], short):void} */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0039, code lost:
        r1[r6] = (byte) r3;
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007b, code lost:
        r1[r6] = (short) r3;
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b4, code lost:
        r1[r7] = r4;
        r3 = r3 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object createHashTable(java.lang.Object[] r10, int r11, int r12, int r13) {
        /*
            r0 = 1
            if (r11 != r0) goto L_0x000e
            r0 = r10[r13]
            r1 = r13 ^ 1
            r1 = r10[r1]
            com.google.common.collect.CollectPreconditions.checkEntryNotNull(r0, r1)
            r0 = 0
            return r0
        L_0x000e:
            int r0 = r12 + -1
            r1 = 128(0x80, float:1.794E-43)
            r2 = -1
            if (r12 > r1) goto L_0x0051
            byte[] r1 = new byte[r12]
            java.util.Arrays.fill(r1, r2)
            r2 = 0
        L_0x001b:
            if (r2 >= r11) goto L_0x0050
            int r3 = r2 * 2
            int r3 = r3 + r13
            r4 = r10[r3]
            r5 = r3 ^ 1
            r5 = r10[r5]
            com.google.common.collect.CollectPreconditions.checkEntryNotNull(r4, r5)
            int r6 = r4.hashCode()
            int r6 = com.google.common.collect.Hashing.smear(r6)
        L_0x0031:
            r6 = r6 & r0
            byte r7 = r1[r6]
            r8 = 255(0xff, float:3.57E-43)
            r7 = r7 & r8
            if (r7 != r8) goto L_0x0040
            byte r8 = (byte) r3
            r1[r6] = r8
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0040:
            r8 = r10[r7]
            boolean r8 = r8.equals(r4)
            if (r8 != 0) goto L_0x004b
            int r6 = r6 + 1
            goto L_0x0031
        L_0x004b:
            java.lang.IllegalArgumentException r8 = duplicateKeyException(r4, r5, r10, r7)
            throw r8
        L_0x0050:
            return r1
        L_0x0051:
            r1 = 32768(0x8000, float:4.5918E-41)
            if (r12 > r1) goto L_0x0093
            short[] r1 = new short[r12]
            java.util.Arrays.fill(r1, r2)
            r2 = 0
        L_0x005c:
            if (r2 >= r11) goto L_0x0092
            int r3 = r2 * 2
            int r3 = r3 + r13
            r4 = r10[r3]
            r5 = r3 ^ 1
            r5 = r10[r5]
            com.google.common.collect.CollectPreconditions.checkEntryNotNull(r4, r5)
            int r6 = r4.hashCode()
            int r6 = com.google.common.collect.Hashing.smear(r6)
        L_0x0072:
            r6 = r6 & r0
            short r7 = r1[r6]
            r8 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r8
            if (r7 != r8) goto L_0x0082
            short r8 = (short) r3
            r1[r6] = r8
            int r2 = r2 + 1
            goto L_0x005c
        L_0x0082:
            r8 = r10[r7]
            boolean r8 = r8.equals(r4)
            if (r8 != 0) goto L_0x008d
            int r6 = r6 + 1
            goto L_0x0072
        L_0x008d:
            java.lang.IllegalArgumentException r8 = duplicateKeyException(r4, r5, r10, r7)
            throw r8
        L_0x0092:
            return r1
        L_0x0093:
            int[] r1 = new int[r12]
            java.util.Arrays.fill(r1, r2)
            r3 = 0
        L_0x0099:
            if (r3 >= r11) goto L_0x00ca
            int r4 = r3 * 2
            int r4 = r4 + r13
            r5 = r10[r4]
            r6 = r4 ^ 1
            r6 = r10[r6]
            com.google.common.collect.CollectPreconditions.checkEntryNotNull(r5, r6)
            int r7 = r5.hashCode()
            int r7 = com.google.common.collect.Hashing.smear(r7)
        L_0x00af:
            r7 = r7 & r0
            r8 = r1[r7]
            if (r8 != r2) goto L_0x00ba
            r1[r7] = r4
            int r3 = r3 + 1
            goto L_0x0099
        L_0x00ba:
            r9 = r10[r8]
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L_0x00c5
            int r7 = r7 + 1
            goto L_0x00af
        L_0x00c5:
            java.lang.IllegalArgumentException r2 = duplicateKeyException(r5, r6, r10, r8)
            throw r2
        L_0x00ca:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableMap.createHashTable(java.lang.Object[], int, int, int):java.lang.Object");
    }

    private static IllegalArgumentException duplicateKeyException(Object key, Object value, Object[] alternatingKeysAndValues2, int previousKeyIndex) {
        String valueOf = String.valueOf(key);
        String valueOf2 = String.valueOf(value);
        String valueOf3 = String.valueOf(alternatingKeysAndValues2[previousKeyIndex]);
        String valueOf4 = String.valueOf(alternatingKeysAndValues2[previousKeyIndex ^ 1]);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 39 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length());
        sb.append("Multiple entries with same key: ");
        sb.append(valueOf);
        sb.append("=");
        sb.append(valueOf2);
        sb.append(" and ");
        sb.append(valueOf3);
        sb.append("=");
        sb.append(valueOf4);
        return new IllegalArgumentException(sb.toString());
    }

    private RegularImmutableMap(Object hashTable2, Object[] alternatingKeysAndValues2, int size2) {
        this.hashTable = hashTable2;
        this.alternatingKeysAndValues = alternatingKeysAndValues2;
        this.size = size2;
    }

    public int size() {
        return this.size;
    }

    @NullableDecl
    public V get(@NullableDecl Object key) {
        return get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, key);
    }

    static Object get(@NullableDecl Object hashTableObject, @NullableDecl Object[] alternatingKeysAndValues2, int size2, int keyOffset, @NullableDecl Object key) {
        if (key == null) {
            return null;
        }
        if (size2 == 1) {
            if (alternatingKeysAndValues2[keyOffset].equals(key)) {
                return alternatingKeysAndValues2[keyOffset ^ 1];
            }
            return null;
        } else if (hashTableObject == null) {
            return null;
        } else {
            if (hashTableObject instanceof byte[]) {
                byte[] hashTable2 = (byte[]) hashTableObject;
                int mask = hashTable2.length - 1;
                int h = Hashing.smear(key.hashCode());
                while (true) {
                    int h2 = h & mask;
                    int keyIndex = hashTable2[h2] & 255;
                    if (keyIndex == 255) {
                        return null;
                    }
                    if (alternatingKeysAndValues2[keyIndex].equals(key)) {
                        return alternatingKeysAndValues2[keyIndex ^ 1];
                    }
                    h = h2 + 1;
                }
            } else if (hashTableObject instanceof short[]) {
                short[] hashTable3 = (short[]) hashTableObject;
                int mask2 = hashTable3.length - 1;
                int h3 = Hashing.smear(key.hashCode());
                while (true) {
                    int h4 = h3 & mask2;
                    int keyIndex2 = hashTable3[h4] & 65535;
                    if (keyIndex2 == 65535) {
                        return null;
                    }
                    if (alternatingKeysAndValues2[keyIndex2].equals(key)) {
                        return alternatingKeysAndValues2[keyIndex2 ^ 1];
                    }
                    h3 = h4 + 1;
                }
            } else {
                int[] hashTable4 = (int[]) hashTableObject;
                int mask3 = hashTable4.length - 1;
                int h5 = Hashing.smear(key.hashCode());
                while (true) {
                    int h6 = h5 & mask3;
                    int keyIndex3 = hashTable4[h6];
                    if (keyIndex3 == -1) {
                        return null;
                    }
                    if (alternatingKeysAndValues2[keyIndex3].equals(key)) {
                        return alternatingKeysAndValues2[keyIndex3 ^ 1];
                    }
                    h5 = h6 + 1;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
    }

    static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        /* access modifiers changed from: private */
        public final transient Object[] alternatingKeysAndValues;
        /* access modifiers changed from: private */
        public final transient int keyOffset;
        private final transient ImmutableMap<K, V> map;
        /* access modifiers changed from: private */
        public final transient int size;

        EntrySet(ImmutableMap<K, V> map2, Object[] alternatingKeysAndValues2, int keyOffset2, int size2) {
            this.map = map2;
            this.alternatingKeysAndValues = alternatingKeysAndValues2;
            this.keyOffset = keyOffset2;
            this.size = size2;
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }

        /* access modifiers changed from: package-private */
        public int copyIntoArray(Object[] dst, int offset) {
            return asList().copyIntoArray(dst, offset);
        }

        /* access modifiers changed from: package-private */
        public ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>() {
                public Map.Entry<K, V> get(int index) {
                    Preconditions.checkElementIndex(index, EntrySet.this.size);
                    return new AbstractMap.SimpleImmutableEntry(EntrySet.this.alternatingKeysAndValues[(index * 2) + EntrySet.this.keyOffset], EntrySet.this.alternatingKeysAndValues[(index * 2) + (EntrySet.this.keyOffset ^ 1)]);
                }

                public int size() {
                    return EntrySet.this.size;
                }

                public boolean isPartialView() {
                    return true;
                }
            };
        }

        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !v.equals(this.map.get(k))) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.size;
        }
    }

    /* access modifiers changed from: package-private */
    public ImmutableSet<K> createKeySet() {
        return new KeySet(this, new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
    }

    static final class KeysOrValuesAsList extends ImmutableList<Object> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int offset;
        private final transient int size;

        KeysOrValuesAsList(Object[] alternatingKeysAndValues2, int offset2, int size2) {
            this.alternatingKeysAndValues = alternatingKeysAndValues2;
            this.offset = offset2;
            this.size = size2;
        }

        public Object get(int index) {
            Preconditions.checkElementIndex(index, this.size);
            return this.alternatingKeysAndValues[(index * 2) + this.offset];
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.size;
        }
    }

    static final class KeySet<K> extends ImmutableSet<K> {
        private final transient ImmutableList<K> list;
        private final transient ImmutableMap<K, ?> map;

        KeySet(ImmutableMap<K, ?> map2, ImmutableList<K> list2) {
            this.map = map2;
            this.list = list2;
        }

        public UnmodifiableIterator<K> iterator() {
            return asList().iterator();
        }

        /* access modifiers changed from: package-private */
        public int copyIntoArray(Object[] dst, int offset) {
            return asList().copyIntoArray(dst, offset);
        }

        public ImmutableList<K> asList() {
            return this.list;
        }

        public boolean contains(@NullableDecl Object object) {
            return this.map.get(object) != null;
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.map.size();
        }
    }

    /* access modifiers changed from: package-private */
    public ImmutableCollection<V> createValues() {
        return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }
}
