package com.google.protobuf;

import com.google.protobuf.FieldSet;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class SmallSortedMap<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    /* access modifiers changed from: private */
    public List<SmallSortedMap<K, V>.Entry> entryList;
    private boolean isImmutable;
    private volatile SmallSortedMap<K, V>.DescendingEntrySet lazyDescendingEntrySet;
    private volatile SmallSortedMap<K, V>.EntrySet lazyEntrySet;
    private final int maxArraySize;
    /* access modifiers changed from: private */
    public Map<K, V> overflowEntries;
    /* access modifiers changed from: private */
    public Map<K, V> overflowEntriesDescending;

    static <FieldDescriptorType extends FieldSet.FieldDescriptorLite<FieldDescriptorType>> SmallSortedMap<FieldDescriptorType, Object> newFieldMap(int arraySize) {
        return new SmallSortedMap<FieldDescriptorType, Object>(arraySize) {
            public void makeImmutable() {
                if (!isImmutable()) {
                    for (int i = 0; i < getNumArrayEntries(); i++) {
                        Map.Entry<FieldDescriptorType, Object> entry = getArrayEntryAt(i);
                        if (((FieldSet.FieldDescriptorLite) entry.getKey()).isRepeated()) {
                            entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                        }
                    }
                    for (Map.Entry<FieldDescriptorType, Object> entry2 : getOverflowEntries()) {
                        if (((FieldSet.FieldDescriptorLite) entry2.getKey()).isRepeated()) {
                            entry2.setValue(Collections.unmodifiableList((List) entry2.getValue()));
                        }
                    }
                }
                SmallSortedMap.super.makeImmutable();
            }
        };
    }

    static <K extends Comparable<K>, V> SmallSortedMap<K, V> newInstanceForTest(int arraySize) {
        return new SmallSortedMap<>(arraySize);
    }

    private SmallSortedMap(int arraySize) {
        this.maxArraySize = arraySize;
        this.entryList = Collections.emptyList();
        this.overflowEntries = Collections.emptyMap();
        this.overflowEntriesDescending = Collections.emptyMap();
    }

    public void makeImmutable() {
        Map<K, V> map;
        Map<K, V> map2;
        if (!this.isImmutable) {
            if (this.overflowEntries.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = Collections.unmodifiableMap(this.overflowEntries);
            }
            this.overflowEntries = map;
            if (this.overflowEntriesDescending.isEmpty()) {
                map2 = Collections.emptyMap();
            } else {
                map2 = Collections.unmodifiableMap(this.overflowEntriesDescending);
            }
            this.overflowEntriesDescending = map2;
            this.isImmutable = true;
        }
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public int getNumArrayEntries() {
        return this.entryList.size();
    }

    public Map.Entry<K, V> getArrayEntryAt(int index) {
        return this.entryList.get(index);
    }

    public int getNumOverflowEntries() {
        return this.overflowEntries.size();
    }

    public Iterable<Map.Entry<K, V>> getOverflowEntries() {
        if (this.overflowEntries.isEmpty()) {
            return EmptySet.iterable();
        }
        return this.overflowEntries.entrySet();
    }

    /* access modifiers changed from: package-private */
    public Iterable<Map.Entry<K, V>> getOverflowEntriesDescending() {
        if (this.overflowEntriesDescending.isEmpty()) {
            return EmptySet.iterable();
        }
        return this.overflowEntriesDescending.entrySet();
    }

    public int size() {
        return this.entryList.size() + this.overflowEntries.size();
    }

    public boolean containsKey(Object o) {
        K key = (Comparable) o;
        return binarySearchInArray(key) >= 0 || this.overflowEntries.containsKey(key);
    }

    public V get(Object o) {
        K key = (Comparable) o;
        int index = binarySearchInArray(key);
        if (index >= 0) {
            return this.entryList.get(index).getValue();
        }
        return this.overflowEntries.get(key);
    }

    public V put(Comparable comparable, Object obj) {
        checkMutable();
        int index = binarySearchInArray(comparable);
        if (index >= 0) {
            return this.entryList.get(index).setValue(obj);
        }
        ensureEntryArrayMutable();
        int insertionPoint = -(index + 1);
        if (insertionPoint >= this.maxArraySize) {
            return getOverflowEntriesMutable().put(comparable, obj);
        }
        int size = this.entryList.size();
        int i = this.maxArraySize;
        if (size == i) {
            SmallSortedMap<K, V>.Entry lastEntryInArray = this.entryList.remove(i - 1);
            getOverflowEntriesMutable().put(lastEntryInArray.getKey(), lastEntryInArray.getValue());
        }
        this.entryList.add(insertionPoint, new Entry(comparable, obj));
        return null;
    }

    public void clear() {
        checkMutable();
        if (!this.entryList.isEmpty()) {
            this.entryList.clear();
        }
        if (!this.overflowEntries.isEmpty()) {
            this.overflowEntries.clear();
        }
    }

    public V remove(Object o) {
        checkMutable();
        K key = (Comparable) o;
        int index = binarySearchInArray(key);
        if (index >= 0) {
            return removeArrayEntryAt(index);
        }
        if (this.overflowEntries.isEmpty()) {
            return null;
        }
        return this.overflowEntries.remove(key);
    }

    /* access modifiers changed from: private */
    public V removeArrayEntryAt(int index) {
        checkMutable();
        V removed = this.entryList.remove(index).getValue();
        if (!this.overflowEntries.isEmpty()) {
            Iterator<Map.Entry<K, V>> iterator = getOverflowEntriesMutable().entrySet().iterator();
            this.entryList.add(new Entry(this, iterator.next()));
            iterator.remove();
        }
        return removed;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: K
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    private int binarySearchInArray(K r5) {
        /*
            r4 = this;
            r0 = 0
            java.util.List<com.google.protobuf.SmallSortedMap<K, V>$Entry> r1 = r4.entryList
            int r1 = r1.size()
            int r1 = r1 + -1
            if (r1 < 0) goto L_0x0024
            java.util.List<com.google.protobuf.SmallSortedMap<K, V>$Entry> r2 = r4.entryList
            java.lang.Object r2 = r2.get(r1)
            com.google.protobuf.SmallSortedMap$Entry r2 = (com.google.protobuf.SmallSortedMap.Entry) r2
            java.lang.Comparable r2 = r2.getKey()
            int r2 = r5.compareTo(r2)
            if (r2 <= 0) goto L_0x0021
            int r3 = r1 + 2
            int r3 = -r3
            return r3
        L_0x0021:
            if (r2 != 0) goto L_0x0024
            return r1
        L_0x0024:
            if (r0 > r1) goto L_0x0045
            int r2 = r0 + r1
            int r2 = r2 / 2
            java.util.List<com.google.protobuf.SmallSortedMap<K, V>$Entry> r3 = r4.entryList
            java.lang.Object r3 = r3.get(r2)
            com.google.protobuf.SmallSortedMap$Entry r3 = (com.google.protobuf.SmallSortedMap.Entry) r3
            java.lang.Comparable r3 = r3.getKey()
            int r3 = r5.compareTo(r3)
            if (r3 >= 0) goto L_0x003f
            int r1 = r2 + -1
            goto L_0x0043
        L_0x003f:
            if (r3 <= 0) goto L_0x0044
            int r0 = r2 + 1
        L_0x0043:
            goto L_0x0024
        L_0x0044:
            return r2
        L_0x0045:
            int r2 = r0 + 1
            int r2 = -r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.SmallSortedMap.binarySearchInArray(java.lang.Comparable):int");
    }

    public Set<Map.Entry<K, V>> entrySet() {
        if (this.lazyEntrySet == null) {
            this.lazyEntrySet = new EntrySet();
        }
        return this.lazyEntrySet;
    }

    /* access modifiers changed from: package-private */
    public Set<Map.Entry<K, V>> descendingEntrySet() {
        if (this.lazyDescendingEntrySet == null) {
            this.lazyDescendingEntrySet = new DescendingEntrySet();
        }
        return this.lazyDescendingEntrySet;
    }

    /* access modifiers changed from: private */
    public void checkMutable() {
        if (this.isImmutable) {
            throw new UnsupportedOperationException();
        }
    }

    private SortedMap<K, V> getOverflowEntriesMutable() {
        checkMutable();
        if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
            this.overflowEntries = new TreeMap();
            this.overflowEntriesDescending = ((TreeMap) this.overflowEntries).descendingMap();
        }
        return (SortedMap) this.overflowEntries;
    }

    private void ensureEntryArrayMutable() {
        checkMutable();
        if (this.entryList.isEmpty() && !(this.entryList instanceof ArrayList)) {
            this.entryList = new ArrayList(this.maxArraySize);
        }
    }

    private class Entry implements Map.Entry<K, V>, Comparable<SmallSortedMap<K, V>.Entry> {
        private final K key;
        private V value;

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return compareTo((SmallSortedMap<K, V>.Entry) ((Entry) obj));
        }

        Entry(SmallSortedMap smallSortedMap, Map.Entry<K, V> copy) {
            this((Comparable) copy.getKey(), copy.getValue());
        }

        Entry(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public int compareTo(SmallSortedMap<K, V>.Entry other) {
            return getKey().compareTo(other.getKey());
        }

        public V setValue(V newValue) {
            SmallSortedMap.this.checkMutable();
            V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> other = (Map.Entry) o;
            if (!equals(this.key, other.getKey()) || !equals(this.value, other.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            K k = this.key;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.value;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            String valueOf = String.valueOf(this.key);
            String valueOf2 = String.valueOf(this.value);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append("=");
            sb.append(valueOf2);
            return sb.toString();
        }

        private boolean equals(Object o1, Object o2) {
            if (o1 == null) {
                return o2 == null;
            }
            return o1.equals(o2);
        }
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public int size() {
            return SmallSortedMap.this.size();
        }

        public boolean contains(Object o) {
            Map.Entry<K, V> entry = (Map.Entry) o;
            V existing = SmallSortedMap.this.get(entry.getKey());
            V value = entry.getValue();
            return existing == value || (existing != null && existing.equals(value));
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V
         arg types: [java.lang.Comparable, V]
         candidates:
          com.google.protobuf.SmallSortedMap.put(java.lang.Object, java.lang.Object):java.lang.Object
          ClspMth{java.util.AbstractMap.put(java.lang.Object, java.lang.Object):V}
          ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
          com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V */
        public boolean add(Map.Entry<K, V> entry) {
            if (contains(entry)) {
                return false;
            }
            SmallSortedMap.this.put((Comparable) entry.getKey(), (Object) entry.getValue());
            return true;
        }

        public boolean remove(Object o) {
            Map.Entry<K, V> entry = (Map.Entry) o;
            if (!contains(entry)) {
                return false;
            }
            SmallSortedMap.this.remove(entry.getKey());
            return true;
        }

        public void clear() {
            SmallSortedMap.this.clear();
        }
    }

    private class DescendingEntrySet extends SmallSortedMap<K, V>.EntrySet {
        private DescendingEntrySet() {
            super();
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new DescendingEntryIterator();
        }
    }

    private class EntryIterator implements Iterator<Map.Entry<K, V>> {
        private Iterator<Map.Entry<K, V>> lazyOverflowIterator;
        private boolean nextCalledBeforeRemove;
        private int pos;

        private EntryIterator() {
            this.pos = -1;
        }

        public boolean hasNext() {
            if (this.pos + 1 < SmallSortedMap.this.entryList.size()) {
                return true;
            }
            if (SmallSortedMap.this.overflowEntries.isEmpty() || !getOverflowIterator().hasNext()) {
                return false;
            }
            return true;
        }

        public Map.Entry<K, V> next() {
            this.nextCalledBeforeRemove = true;
            int i = this.pos + 1;
            this.pos = i;
            if (i < SmallSortedMap.this.entryList.size()) {
                return (Map.Entry) SmallSortedMap.this.entryList.get(this.pos);
            }
            return (Map.Entry) getOverflowIterator().next();
        }

        public void remove() {
            if (this.nextCalledBeforeRemove) {
                this.nextCalledBeforeRemove = false;
                SmallSortedMap.this.checkMutable();
                if (this.pos < SmallSortedMap.this.entryList.size()) {
                    SmallSortedMap smallSortedMap = SmallSortedMap.this;
                    int i = this.pos;
                    this.pos = i - 1;
                    Object unused = smallSortedMap.removeArrayEntryAt(i);
                    return;
                }
                getOverflowIterator().remove();
                return;
            }
            throw new IllegalStateException("remove() was called before next()");
        }

        private Iterator<Map.Entry<K, V>> getOverflowIterator() {
            if (this.lazyOverflowIterator == null) {
                this.lazyOverflowIterator = SmallSortedMap.this.overflowEntries.entrySet().iterator();
            }
            return this.lazyOverflowIterator;
        }
    }

    private class DescendingEntryIterator implements Iterator<Map.Entry<K, V>> {
        private Iterator<Map.Entry<K, V>> lazyOverflowIterator;
        private int pos;

        private DescendingEntryIterator() {
            this.pos = SmallSortedMap.this.entryList.size();
        }

        public boolean hasNext() {
            int i = this.pos;
            return (i > 0 && i <= SmallSortedMap.this.entryList.size()) || getOverflowIterator().hasNext();
        }

        public Map.Entry<K, V> next() {
            if (getOverflowIterator().hasNext()) {
                return (Map.Entry) getOverflowIterator().next();
            }
            List access$600 = SmallSortedMap.this.entryList;
            int i = this.pos - 1;
            this.pos = i;
            return (Map.Entry) access$600.get(i);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Iterator<Map.Entry<K, V>> getOverflowIterator() {
            if (this.lazyOverflowIterator == null) {
                this.lazyOverflowIterator = SmallSortedMap.this.overflowEntriesDescending.entrySet().iterator();
            }
            return this.lazyOverflowIterator;
        }
    }

    private static class EmptySet {
        private static final Iterable<Object> ITERABLE = new Iterable<Object>() {
            public Iterator<Object> iterator() {
                return EmptySet.ITERATOR;
            }
        };
        /* access modifiers changed from: private */
        public static final Iterator<Object> ITERATOR = new Iterator<Object>() {
            public boolean hasNext() {
                return false;
            }

            public Object next() {
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        private EmptySet() {
        }

        static <T> Iterable<T> iterable() {
            return ITERABLE;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmallSortedMap)) {
            return super.equals(o);
        }
        SmallSortedMap<?, ?> other = (SmallSortedMap) o;
        int size = size();
        if (size != other.size()) {
            return false;
        }
        int numArrayEntries = getNumArrayEntries();
        if (numArrayEntries != other.getNumArrayEntries()) {
            return entrySet().equals(other.entrySet());
        }
        for (int i = 0; i < numArrayEntries; i++) {
            if (!getArrayEntryAt(i).equals(other.getArrayEntryAt(i))) {
                return false;
            }
        }
        if (numArrayEntries != size) {
            return this.overflowEntries.equals(other.overflowEntries);
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        int listSize = getNumArrayEntries();
        for (int i = 0; i < listSize; i++) {
            h += this.entryList.get(i).hashCode();
        }
        if (getNumOverflowEntries() > 0) {
            return h + this.overflowEntries.hashCode();
        }
        return h;
    }
}
