package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class MapFieldLite<K, V> extends LinkedHashMap<K, V> {
    private static final MapFieldLite EMPTY_MAP_FIELD = new MapFieldLite();
    private boolean isMutable = true;

    private MapFieldLite() {
    }

    private MapFieldLite(Map<K, V> mapData) {
        super(mapData);
    }

    static {
        EMPTY_MAP_FIELD.makeImmutable();
    }

    public static <K, V> MapFieldLite<K, V> emptyMapField() {
        return EMPTY_MAP_FIELD;
    }

    public void mergeFrom(MapFieldLite<K, V> other) {
        ensureMutable();
        if (!other.isEmpty()) {
            putAll(other);
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    public void clear() {
        ensureMutable();
        super.clear();
    }

    public V put(K key, V value) {
        ensureMutable();
        Internal.checkNotNull(key);
        Internal.checkNotNull(value);
        return super.put(key, value);
    }

    public V put(Map.Entry<K, V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        ensureMutable();
        checkForNullKeysAndValues(m);
        super.putAll(m);
    }

    public V remove(Object key) {
        ensureMutable();
        return super.remove(key);
    }

    private static void checkForNullKeysAndValues(Map<?, ?> m) {
        for (Object key : m.keySet()) {
            Internal.checkNotNull(key);
            Internal.checkNotNull(m.get(key));
        }
    }

    private static boolean equals(Object a, Object b) {
        if (!(a instanceof byte[]) || !(b instanceof byte[])) {
            return a.equals(b);
        }
        return Arrays.equals((byte[]) a, (byte[]) b);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.MapFieldLite.equals(java.lang.Object, java.lang.Object):boolean
     arg types: [V, V]
     candidates:
      com.google.protobuf.MapFieldLite.equals(java.util.Map, java.util.Map):boolean
      com.google.protobuf.MapFieldLite.equals(java.lang.Object, java.lang.Object):boolean */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <K, V> boolean equals(java.util.Map<K, V> r6, java.util.Map<K, V> r7) {
        /*
            r0 = 1
            if (r6 != r7) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r6.size()
            int r2 = r7.size()
            r3 = 0
            if (r1 == r2) goto L_0x0010
            return r3
        L_0x0010:
            java.util.Set r1 = r6.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0018:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0043
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r4 = r2.getKey()
            boolean r4 = r7.containsKey(r4)
            if (r4 != 0) goto L_0x002f
            return r3
        L_0x002f:
            java.lang.Object r4 = r2.getValue()
            java.lang.Object r5 = r2.getKey()
            java.lang.Object r5 = r7.get(r5)
            boolean r4 = equals(r4, r5)
            if (r4 != 0) goto L_0x0042
            return r3
        L_0x0042:
            goto L_0x0018
        L_0x0043:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MapFieldLite.equals(java.util.Map, java.util.Map):boolean");
    }

    public boolean equals(Object object) {
        return (object instanceof Map) && equals(this, (Map) object);
    }

    private static int calculateHashCodeForObject(Object a) {
        if (a instanceof byte[]) {
            return Internal.hashCode((byte[]) a);
        }
        if (!(a instanceof Internal.EnumLite)) {
            return a.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    static <K, V> int calculateHashCodeForMap(Map<K, V> a) {
        int result = 0;
        for (Map.Entry<K, V> entry : a.entrySet()) {
            result += calculateHashCodeForObject(entry.getKey()) ^ calculateHashCodeForObject(entry.getValue());
        }
        return result;
    }

    public int hashCode() {
        return calculateHashCodeForMap(this);
    }

    private static Object copy(Object object) {
        if (object instanceof byte[]) {
            byte[] data = (byte[]) object;
            return Arrays.copyOf(data, data.length);
        } else if (object instanceof MutableMessageLite) {
            return ((MutableMessageLite) object).clone();
        } else {
            return object;
        }
    }

    static <K, V> Map<K, V> copy(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.put(entry.getKey(), copy((Object) entry.getValue()));
        }
        return result;
    }

    public MapFieldLite<K, V> mutableCopy() {
        return isEmpty() ? new MapFieldLite<>() : new MapFieldLite<>(this);
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    public boolean isMutable() {
        return this.isMutable;
    }

    private void ensureMutable() {
        if (!isMutable()) {
            throw new UnsupportedOperationException();
        }
    }
}
