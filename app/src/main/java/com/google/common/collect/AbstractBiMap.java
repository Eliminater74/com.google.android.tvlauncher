package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
abstract class AbstractBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    @MonotonicNonNullDecl
    public transient Map<K, V> delegate;
    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> entrySet;
    @RetainedWith
    @MonotonicNonNullDecl
    transient AbstractBiMap<V, K> inverse;
    @MonotonicNonNullDecl
    private transient Set<K> keySet;
    @MonotonicNonNullDecl
    private transient Set<V> valueSet;

    AbstractBiMap(Map<K, V> forward, Map<V, K> backward) {
        setDelegates(forward, backward);
    }

    private AbstractBiMap(Map<K, V> backward, AbstractBiMap<V, K> forward) {
        this.delegate = backward;
        this.inverse = forward;
    }

    /* access modifiers changed from: protected */
    public Map<K, V> delegate() {
        return this.delegate;
    }

    /* access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    public K checkKey(@NullableDecl K key) {
        return key;
    }

    /* access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    public V checkValue(@NullableDecl V value) {
        return value;
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [java.util.Map, java.util.Map<V, K>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setDelegates(java.util.Map<K, V> r4, java.util.Map<V, K> r5) {
        /*
            r3 = this;
            java.util.Map<K, V> r0 = r3.delegate
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            com.google.common.base.Preconditions.checkState(r0)
            com.google.common.collect.AbstractBiMap<V, K> r0 = r3.inverse
            if (r0 != 0) goto L_0x0012
            r0 = 1
            goto L_0x0013
        L_0x0012:
            r0 = 0
        L_0x0013:
            com.google.common.base.Preconditions.checkState(r0)
            boolean r0 = r4.isEmpty()
            com.google.common.base.Preconditions.checkArgument(r0)
            boolean r0 = r5.isEmpty()
            com.google.common.base.Preconditions.checkArgument(r0)
            if (r4 == r5) goto L_0x0027
            goto L_0x0028
        L_0x0027:
            r1 = 0
        L_0x0028:
            com.google.common.base.Preconditions.checkArgument(r1)
            r3.delegate = r4
            com.google.common.collect.AbstractBiMap r0 = r3.makeInverse(r5)
            r3.inverse = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.AbstractBiMap.setDelegates(java.util.Map, java.util.Map):void");
    }

    /* access modifiers changed from: package-private */
    public AbstractBiMap<V, K> makeInverse(Map<V, K> backward) {
        return new Inverse(backward, this);
    }

    /* access modifiers changed from: package-private */
    public void setInverse(AbstractBiMap<V, K> inverse2) {
        this.inverse = inverse2;
    }

    public boolean containsValue(@NullableDecl Object value) {
        return this.inverse.containsKey(value);
    }

    @CanIgnoreReturnValue
    public V put(@NullableDecl K key, @NullableDecl V value) {
        return putInBothMaps(key, value, false);
    }

    @CanIgnoreReturnValue
    public V forcePut(@NullableDecl K key, @NullableDecl V value) {
        return putInBothMaps(key, value, true);
    }

    private V putInBothMaps(@NullableDecl K key, @NullableDecl V value, boolean force) {
        checkKey(key);
        checkValue(value);
        boolean containedKey = containsKey(key);
        if (containedKey && Objects.equal(value, get(key))) {
            return value;
        }
        if (force) {
            inverse().remove(value);
        } else {
            Preconditions.checkArgument(!containsValue(value), "value already present: %s", value);
        }
        V oldValue = this.delegate.put(key, value);
        updateInverseMap(key, containedKey, oldValue, value);
        return oldValue;
    }

    /* access modifiers changed from: private */
    public void updateInverseMap(K key, boolean containedKey, V oldValue, V newValue) {
        if (containedKey) {
            removeFromInverseMap(oldValue);
        }
        this.inverse.delegate.put(newValue, key);
    }

    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object key) {
        if (containsKey(key)) {
            return removeFromBothMaps(key);
        }
        return null;
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public V removeFromBothMaps(Object key) {
        V oldValue = this.delegate.remove(key);
        removeFromInverseMap(oldValue);
        return oldValue;
    }

    /* access modifiers changed from: private */
    public void removeFromInverseMap(V oldValue) {
        this.inverse.delegate.remove(oldValue);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        this.delegate.clear();
        this.inverse.delegate.clear();
    }

    public BiMap<V, K> inverse() {
        return this.inverse;
    }

    public Set<K> keySet() {
        Set<K> result = this.keySet;
        if (result != null) {
            return result;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    private class KeySet extends ForwardingSet<K> {
        private KeySet() {
        }

        /* access modifiers changed from: protected */
        public Set<K> delegate() {
            return AbstractBiMap.this.delegate.keySet();
        }

        public void clear() {
            AbstractBiMap.this.clear();
        }

        public boolean remove(Object key) {
            if (!contains(key)) {
                return false;
            }
            Object unused = AbstractBiMap.this.removeFromBothMaps(key);
            return true;
        }

        public boolean removeAll(Collection<?> keysToRemove) {
            return standardRemoveAll(keysToRemove);
        }

        public boolean retainAll(Collection<?> keysToRetain) {
            return standardRetainAll(keysToRetain);
        }

        public Iterator<K> iterator() {
            return Maps.keyIterator(AbstractBiMap.this.entrySet().iterator());
        }
    }

    public Set<V> values() {
        Set<V> result = this.valueSet;
        if (result != null) {
            return result;
        }
        ValueSet valueSet2 = new ValueSet();
        this.valueSet = valueSet2;
        return valueSet2;
    }

    private class ValueSet extends ForwardingSet<V> {
        final Set<V> valuesDelegate;

        private ValueSet() {
            this.valuesDelegate = AbstractBiMap.this.inverse.keySet();
        }

        /* access modifiers changed from: protected */
        public Set<V> delegate() {
            return this.valuesDelegate;
        }

        public Iterator<V> iterator() {
            return Maps.valueIterator(AbstractBiMap.this.entrySet().iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] array) {
            return standardToArray(array);
        }

        public String toString() {
            return standardToString();
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    class BiMapEntry extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> delegate;

        BiMapEntry(Map.Entry<K, V> delegate2) {
            this.delegate = delegate2;
        }

        /* access modifiers changed from: protected */
        public Map.Entry<K, V> delegate() {
            return this.delegate;
        }

        public V setValue(V value) {
            AbstractBiMap.this.checkValue(value);
            Preconditions.checkState(AbstractBiMap.this.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(value, getValue())) {
                return value;
            }
            Preconditions.checkArgument(!AbstractBiMap.this.containsValue(value), "value already present: %s", value);
            V oldValue = this.delegate.setValue(value);
            Preconditions.checkState(Objects.equal(value, AbstractBiMap.this.get(getKey())), "entry no longer in map");
            AbstractBiMap.this.updateInverseMap(getKey(), true, oldValue, value);
            return oldValue;
        }
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<K, V>> entrySetIterator() {
        final Iterator<Map.Entry<K, V>> iterator = this.delegate.entrySet().iterator();
        return new Iterator<Map.Entry<K, V>>() {
            @NullableDecl
            Map.Entry<K, V> entry;

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public Map.Entry<K, V> next() {
                this.entry = (Map.Entry) iterator.next();
                return new BiMapEntry(this.entry);
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.entry != null);
                V value = this.entry.getValue();
                iterator.remove();
                AbstractBiMap.this.removeFromInverseMap(value);
                this.entry = null;
            }
        };
    }

    private class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
        final Set<Map.Entry<K, V>> esDelegate;

        private EntrySet() {
            this.esDelegate = AbstractBiMap.this.delegate.entrySet();
        }

        /* access modifiers changed from: protected */
        public Set<Map.Entry<K, V>> delegate() {
            return this.esDelegate;
        }

        public void clear() {
            AbstractBiMap.this.clear();
        }

        public boolean remove(Object object) {
            if (!this.esDelegate.contains(object)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) object;
            AbstractBiMap.this.inverse.delegate.remove(entry.getValue());
            this.esDelegate.remove(entry);
            return true;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractBiMap.this.entrySetIterator();
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] array) {
            return standardToArray(array);
        }

        public boolean contains(Object o) {
            return Maps.containsEntryImpl(delegate(), o);
        }

        public boolean containsAll(Collection<?> c) {
            return standardContainsAll(c);
        }

        public boolean removeAll(Collection<?> c) {
            return standardRemoveAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return standardRetainAll(c);
        }
    }

    static class Inverse<K, V> extends AbstractBiMap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;

        Inverse(Map<K, V> backward, AbstractBiMap<V, K> forward) {
            super(backward, forward);
        }

        /* access modifiers changed from: package-private */
        public K checkKey(K key) {
            return this.inverse.checkValue(key);
        }

        /* access modifiers changed from: package-private */
        public V checkValue(V value) {
            return this.inverse.checkKey(value);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(inverse());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            setInverse((AbstractBiMap) stream.readObject());
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public Object readResolve() {
            return inverse().inverse();
        }
    }
}
