package android.support.p001v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ConcurrentModificationException;

/* renamed from: android.support.v4.util.SimpleArrayMap */
public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    @Nullable
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    @Nullable
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    private static int binarySearchHashes(int[] hashes, int N, int hash) {
        try {
            return ContainerHelpers.binarySearch(hashes, N, hash);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    /* access modifiers changed from: package-private */
    public int indexOf(Object key, int hash) {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearchHashes(this.mHashes, N, hash);
        if (index < 0 || key.equals(this.mArray[index << 1])) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == hash) {
            if (key.equals(this.mArray[end << 1])) {
                return end;
            }
            end++;
        }
        int i = index - 1;
        while (i >= 0 && this.mHashes[i] == hash) {
            if (key.equals(this.mArray[i << 1])) {
                return i;
            }
            i--;
        }
        return end ^ -1;
    }

    /* access modifiers changed from: package-private */
    public int indexOfNull() {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearchHashes(this.mHashes, N, 0);
        if (index < 0 || this.mArray[index << 1] == null) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == 0) {
            if (this.mArray[end << 1] == null) {
                return end;
            }
            end++;
        }
        int i = index - 1;
        while (i >= 0 && this.mHashes[i] == 0) {
            if (this.mArray[i << 1] == null) {
                return i;
            }
            i--;
        }
        return end ^ -1;
    }

    private void allocArrays(int size) {
        if (size == 8) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Object[] array = mTwiceBaseCache;
                    this.mArray = array;
                    mTwiceBaseCache = (Object[]) array[0];
                    this.mHashes = (int[]) array[1];
                    array[1] = null;
                    array[0] = null;
                    mTwiceBaseCacheSize--;
                    return;
                }
            }
        } else if (size == 4) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCache != null) {
                    Object[] array2 = mBaseCache;
                    this.mArray = array2;
                    mBaseCache = (Object[]) array2[0];
                    this.mHashes = (int[]) array2[1];
                    array2[1] = null;
                    array2[0] = null;
                    mBaseCacheSize--;
                    return;
                }
            }
        }
        this.mHashes = new int[size];
        this.mArray = new Object[(size << 1)];
    }

    private static void freeArrays(int[] hashes, Object[] array, int size) {
        if (hashes.length == 8) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCacheSize < 10) {
                    array[0] = mTwiceBaseCache;
                    array[1] = hashes;
                    for (int i = (size << 1) - 1; i >= 2; i--) {
                        array[i] = null;
                    }
                    mTwiceBaseCache = array;
                    mTwiceBaseCacheSize++;
                }
            }
        } else if (hashes.length == 4) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCacheSize < 10) {
                    array[0] = mBaseCache;
                    array[1] = hashes;
                    for (int i2 = (size << 1) - 1; i2 >= 2; i2--) {
                        array[i2] = null;
                    }
                    mBaseCache = array;
                    mBaseCacheSize++;
                }
            }
        }
    }

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public SimpleArrayMap(int capacity) {
        if (capacity == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(capacity);
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap simpleArrayMap) {
        this();
        if (simpleArrayMap != null) {
            putAll(simpleArrayMap);
        }
    }

    public void clear() {
        if (this.mSize > 0) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            int osize = this.mSize;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            freeArrays(ohashes, oarray, osize);
        }
        if (this.mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int minimumCapacity) {
        int osize = this.mSize;
        if (this.mHashes.length < minimumCapacity) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(minimumCapacity);
            if (this.mSize > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, osize);
                System.arraycopy(oarray, 0, this.mArray, 0, osize << 1);
            }
            freeArrays(ohashes, oarray, osize);
        }
        if (this.mSize != osize) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(@Nullable Object key) {
        return indexOfKey(key) >= 0;
    }

    public int indexOfKey(@Nullable Object key) {
        return key == null ? indexOfNull() : indexOf(key, key.hashCode());
    }

    /* access modifiers changed from: package-private */
    public int indexOfValue(Object value) {
        int N = this.mSize * 2;
        Object[] array = this.mArray;
        if (value == null) {
            for (int i = 1; i < N; i += 2) {
                if (array[i] == null) {
                    return i >> 1;
                }
            }
            return -1;
        }
        for (int i2 = 1; i2 < N; i2 += 2) {
            if (value.equals(array[i2])) {
                return i2 >> 1;
            }
        }
        return -1;
    }

    public boolean containsValue(Object value) {
        return indexOfValue(value) >= 0;
    }

    @Nullable
    public V get(Object key) {
        return getOrDefault(key, null);
    }

    public V getOrDefault(Object key, V defaultValue) {
        int index = indexOfKey(key);
        return index >= 0 ? this.mArray[(index << 1) + 1] : defaultValue;
    }

    public K keyAt(int index) {
        return this.mArray[index << 1];
    }

    public V valueAt(int index) {
        return this.mArray[(index << 1) + 1];
    }

    public V setValueAt(int index, V value) {
        int index2 = (index << 1) + 1;
        V[] vArr = this.mArray;
        V old = vArr[index2];
        vArr[index2] = value;
        return old;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Nullable
    public V put(K key, V value) {
        int index;
        int hash;
        int osize = this.mSize;
        if (key == null) {
            hash = 0;
            index = indexOfNull();
        } else {
            hash = key.hashCode();
            index = indexOf(key, hash);
        }
        if (index >= 0) {
            int index2 = (index << 1) + 1;
            V[] vArr = this.mArray;
            V old = vArr[index2];
            vArr[index2] = value;
            return old;
        }
        int index3 = index ^ -1;
        if (osize >= this.mHashes.length) {
            int n = 4;
            if (osize >= 8) {
                n = (osize >> 1) + osize;
            } else if (osize >= 4) {
                n = 8;
            }
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(n);
            if (osize == this.mSize) {
                int[] iArr = this.mHashes;
                if (iArr.length > 0) {
                    System.arraycopy(ohashes, 0, iArr, 0, ohashes.length);
                    System.arraycopy(oarray, 0, this.mArray, 0, oarray.length);
                }
                freeArrays(ohashes, oarray, osize);
            } else {
                throw new ConcurrentModificationException();
            }
        }
        if (index3 < osize) {
            int[] iArr2 = this.mHashes;
            System.arraycopy(iArr2, index3, iArr2, index3 + 1, osize - index3);
            Object[] objArr = this.mArray;
            System.arraycopy(objArr, index3 << 1, objArr, (index3 + 1) << 1, (this.mSize - index3) << 1);
        }
        int i = this.mSize;
        if (osize == i) {
            int[] iArr3 = this.mHashes;
            if (index3 < iArr3.length) {
                iArr3[index3] = hash;
                Object[] objArr2 = this.mArray;
                objArr2[index3 << 1] = key;
                objArr2[(index3 << 1) + 1] = value;
                this.mSize = i + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void putAll(@NonNull SimpleArrayMap<? extends K, ? extends V> array) {
        int N = array.mSize;
        ensureCapacity(this.mSize + N);
        if (this.mSize != 0) {
            for (int i = 0; i < N; i++) {
                put(array.keyAt(i), array.valueAt(i));
            }
        } else if (N > 0) {
            System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
            System.arraycopy(array.mArray, 0, this.mArray, 0, N << 1);
            this.mSize = N;
        }
    }

    @Nullable
    public V putIfAbsent(K key, V value) {
        V mapValue = get(key);
        if (mapValue == null) {
            return put(key, value);
        }
        return mapValue;
    }

    @Nullable
    public V remove(Object key) {
        int index = indexOfKey(key);
        if (index >= 0) {
            return removeAt(index);
        }
        return null;
    }

    public boolean remove(Object key, Object value) {
        int index = indexOfKey(key);
        if (index < 0) {
            return false;
        }
        V mapValue = valueAt(index);
        if (value != mapValue && (value == null || !value.equals(mapValue))) {
            return false;
        }
        removeAt(index);
        return true;
    }

    public V removeAt(int index) {
        int nsize;
        Object[] objArr = this.mArray;
        Object old = objArr[(index << 1) + 1];
        int osize = this.mSize;
        if (osize <= 1) {
            freeArrays(this.mHashes, objArr, osize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            nsize = 0;
        } else {
            nsize = osize - 1;
            int[] iArr = this.mHashes;
            int i = 8;
            if (iArr.length <= 8 || this.mSize >= iArr.length / 3) {
                if (index < nsize) {
                    int[] iArr2 = this.mHashes;
                    System.arraycopy(iArr2, index + 1, iArr2, index, nsize - index);
                    Object[] objArr2 = this.mArray;
                    System.arraycopy(objArr2, (index + 1) << 1, objArr2, index << 1, (nsize - index) << 1);
                }
                Object[] objArr3 = this.mArray;
                objArr3[nsize << 1] = null;
                objArr3[(nsize << 1) + 1] = null;
            } else {
                if (osize > 8) {
                    i = osize + (osize >> 1);
                }
                int n = i;
                int[] ohashes = this.mHashes;
                Object[] oarray = this.mArray;
                allocArrays(n);
                if (osize == this.mSize) {
                    if (index > 0) {
                        System.arraycopy(ohashes, 0, this.mHashes, 0, index);
                        System.arraycopy(oarray, 0, this.mArray, 0, index << 1);
                    }
                    if (index < nsize) {
                        System.arraycopy(ohashes, index + 1, this.mHashes, index, nsize - index);
                        System.arraycopy(oarray, (index + 1) << 1, this.mArray, index << 1, (nsize - index) << 1);
                    }
                } else {
                    throw new ConcurrentModificationException();
                }
            }
        }
        if (osize == this.mSize) {
            this.mSize = nsize;
            return old;
        }
        throw new ConcurrentModificationException();
    }

    @Nullable
    public V replace(K key, V value) {
        int index = indexOfKey(key);
        if (index >= 0) {
            return setValueAt(index, value);
        }
        return null;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: V
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public boolean replace(K r4, V r5, V r6) {
        /*
            r3 = this;
            int r0 = r3.indexOfKey(r4)
            if (r0 < 0) goto L_0x0019
            java.lang.Object r1 = r3.valueAt(r0)
            if (r1 == r5) goto L_0x0014
            if (r5 == 0) goto L_0x0019
            boolean r2 = r5.equals(r1)
            if (r2 == 0) goto L_0x0019
        L_0x0014:
            r3.setValueAt(r0, r6)
            r2 = 1
            return r2
        L_0x0019:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.util.SimpleArrayMap.replace(java.lang.Object, java.lang.Object, java.lang.Object):boolean");
    }

    public int size() {
        return this.mSize;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: V
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public boolean equals(java.lang.Object r9) {
        /*
            r8 = this;
            r0 = 1
            if (r8 != r9) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r9 instanceof android.support.p001v4.util.SimpleArrayMap
            r2 = 0
            if (r1 == 0) goto L_0x0043
            r1 = r9
            android.support.v4.util.SimpleArrayMap r1 = (android.support.p001v4.util.SimpleArrayMap) r1
            int r3 = r8.size()
            int r4 = r1.size()
            if (r3 == r4) goto L_0x0017
            return r2
        L_0x0017:
            r3 = r2
        L_0x0018:
            int r4 = r8.mSize     // Catch:{ NullPointerException -> 0x0041, ClassCastException -> 0x003f }
            if (r3 >= r4) goto L_0x003d
            java.lang.Object r4 = r8.keyAt(r3)     // Catch:{ NullPointerException -> 0x0041, ClassCastException -> 0x003f }
            java.lang.Object r5 = r8.valueAt(r3)     // Catch:{ NullPointerException -> 0x0041, ClassCastException -> 0x003f }
            java.lang.Object r6 = r1.get(r4)     // Catch:{ NullPointerException -> 0x0041, ClassCastException -> 0x003f }
            if (r5 != 0) goto L_0x0033
            if (r6 != 0) goto L_0x0032
            boolean r7 = r1.containsKey(r4)     // Catch:{ NullPointerException -> 0x0041, ClassCastException -> 0x003f }
            if (r7 != 0) goto L_0x003a
        L_0x0032:
            return r2
        L_0x0033:
            boolean r7 = r5.equals(r6)     // Catch:{ NullPointerException -> 0x0041, ClassCastException -> 0x003f }
            if (r7 != 0) goto L_0x003a
            return r2
        L_0x003a:
            int r3 = r3 + 1
            goto L_0x0018
        L_0x003d:
            return r0
        L_0x003f:
            r0 = move-exception
            return r2
        L_0x0041:
            r0 = move-exception
            return r2
        L_0x0043:
            boolean r1 = r9 instanceof java.util.Map
            if (r1 == 0) goto L_0x0081
            r1 = r9
            java.util.Map r1 = (java.util.Map) r1
            int r3 = r8.size()
            int r4 = r1.size()
            if (r3 == r4) goto L_0x0055
            return r2
        L_0x0055:
            r3 = r2
        L_0x0056:
            int r4 = r8.mSize     // Catch:{ NullPointerException -> 0x007f, ClassCastException -> 0x007d }
            if (r3 >= r4) goto L_0x007b
            java.lang.Object r4 = r8.keyAt(r3)     // Catch:{ NullPointerException -> 0x007f, ClassCastException -> 0x007d }
            java.lang.Object r5 = r8.valueAt(r3)     // Catch:{ NullPointerException -> 0x007f, ClassCastException -> 0x007d }
            java.lang.Object r6 = r1.get(r4)     // Catch:{ NullPointerException -> 0x007f, ClassCastException -> 0x007d }
            if (r5 != 0) goto L_0x0071
            if (r6 != 0) goto L_0x0070
            boolean r7 = r1.containsKey(r4)     // Catch:{ NullPointerException -> 0x007f, ClassCastException -> 0x007d }
            if (r7 != 0) goto L_0x0078
        L_0x0070:
            return r2
        L_0x0071:
            boolean r7 = r5.equals(r6)     // Catch:{ NullPointerException -> 0x007f, ClassCastException -> 0x007d }
            if (r7 != 0) goto L_0x0078
            return r2
        L_0x0078:
            int r3 = r3 + 1
            goto L_0x0056
        L_0x007b:
            return r0
        L_0x007d:
            r0 = move-exception
            return r2
        L_0x007f:
            r0 = move-exception
            return r2
        L_0x0081:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.util.SimpleArrayMap.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int[] hashes = this.mHashes;
        Object[] array = this.mArray;
        int result = 0;
        int i = 0;
        int v = 1;
        int s = this.mSize;
        while (i < s) {
            Object value = array[v];
            result += hashes[i] ^ (value == null ? 0 : value.hashCode());
            i++;
            v += 2;
        }
        return result;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 28);
        buffer.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            Object key = keyAt(i);
            if (key != this) {
                buffer.append(key);
            } else {
                buffer.append("(this Map)");
            }
            buffer.append('=');
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(value);
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }
}
