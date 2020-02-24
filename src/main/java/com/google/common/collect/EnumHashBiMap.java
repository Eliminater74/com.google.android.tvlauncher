package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public final class EnumHashBiMap<K extends Enum<K>, V> extends AbstractBiMap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Class<K> keyType;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object remove(@NullableDecl Object obj) {
        return super.remove(obj);
    }

    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> keyType2) {
        return new EnumHashBiMap<>(keyType2);
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
        EnumHashBiMap<K, V> bimap = create(EnumBiMap.inferKeyType(map));
        bimap.putAll(map);
        return bimap;
    }

    private EnumHashBiMap(Class<K> keyType2) {
        super(new EnumMap(keyType2), Maps.newHashMapWithExpectedSize(((Enum[]) keyType2.getEnumConstants()).length));
        this.keyType = keyType2;
    }

    /* access modifiers changed from: package-private */
    public K checkKey(K key) {
        return (Enum) Preconditions.checkNotNull(key);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.AbstractBiMap.put(java.lang.Object, java.lang.Object):V
     arg types: [K, V]
     candidates:
      com.google.common.collect.EnumHashBiMap.put(java.lang.Enum, java.lang.Object):V
      com.google.common.collect.AbstractBiMap.put(java.lang.Object, java.lang.Object):V */
    @CanIgnoreReturnValue
    public V put(K key, @NullableDecl V value) {
        return super.put((Object) key, (Object) value);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.AbstractBiMap.forcePut(java.lang.Object, java.lang.Object):V
     arg types: [K, V]
     candidates:
      com.google.common.collect.EnumHashBiMap.forcePut(java.lang.Enum, java.lang.Object):V
      com.google.common.collect.AbstractBiMap.forcePut(java.lang.Object, java.lang.Object):V */
    @CanIgnoreReturnValue
    public V forcePut(K key, @NullableDecl V value) {
        return super.forcePut((Object) key, (Object) value);
    }

    public Class<K> keyType() {
        return this.keyType;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.keyType);
        Serialization.writeMap(this, stream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyType = (Class) stream.readObject();
        setDelegates(new EnumMap(this.keyType), new HashMap((((Enum[]) this.keyType.getEnumConstants()).length * 3) / 2));
        Serialization.populateMap(this, stream);
    }
}
