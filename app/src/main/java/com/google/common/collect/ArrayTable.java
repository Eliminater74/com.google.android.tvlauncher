package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
@Beta
public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    private final V[][] array;
    /* access modifiers changed from: private */
    public final ImmutableMap<C, Integer> columnKeyToIndex;
    /* access modifiers changed from: private */
    public final ImmutableList<C> columnList;
    @MonotonicNonNullDecl
    private transient ArrayTable<R, C, V>.ColumnMap columnMap;
    /* access modifiers changed from: private */
    public final ImmutableMap<R, Integer> rowKeyToIndex;
    /* access modifiers changed from: private */
    public final ImmutableList<R> rowList;
    @MonotonicNonNullDecl
    private transient ArrayTable<R, C, V>.RowMap rowMap;

    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
        return new ArrayTable<>(rowKeys, columnKeys);
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
        if (table instanceof ArrayTable) {
            return new ArrayTable<>((ArrayTable) table);
        }
        return new ArrayTable<>(table);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Iterable<? extends R>, java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Iterable<? extends C>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private ArrayTable(java.lang.Iterable<? extends R> r3, java.lang.Iterable<? extends C> r4) {
        /*
            r2 = this;
            r2.<init>()
            com.google.common.collect.ImmutableList r0 = com.google.common.collect.ImmutableList.copyOf(r3)
            r2.rowList = r0
            com.google.common.collect.ImmutableList r0 = com.google.common.collect.ImmutableList.copyOf(r4)
            r2.columnList = r0
            com.google.common.collect.ImmutableList<R> r0 = r2.rowList
            boolean r0 = r0.isEmpty()
            com.google.common.collect.ImmutableList<C> r1 = r2.columnList
            boolean r1 = r1.isEmpty()
            if (r0 != r1) goto L_0x001f
            r0 = 1
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            com.google.common.base.Preconditions.checkArgument(r0)
            com.google.common.collect.ImmutableList<R> r0 = r2.rowList
            com.google.common.collect.ImmutableMap r0 = com.google.common.collect.Maps.indexMap(r0)
            r2.rowKeyToIndex = r0
            com.google.common.collect.ImmutableList<C> r0 = r2.columnList
            com.google.common.collect.ImmutableMap r0 = com.google.common.collect.Maps.indexMap(r0)
            r2.columnKeyToIndex = r0
            com.google.common.collect.ImmutableList<R> r0 = r2.rowList
            int r0 = r0.size()
            com.google.common.collect.ImmutableList<C> r1 = r2.columnList
            int r1 = r1.size()
            int[] r0 = new int[]{r0, r1}
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r1, r0)
            java.lang.Object[][] r0 = (java.lang.Object[][]) r0
            r2.array = r0
            r2.eraseAll()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ArrayTable.<init>(java.lang.Iterable, java.lang.Iterable):void");
    }

    private ArrayTable(Table<R, C, V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        putAll(table);
    }

    private ArrayTable(ArrayTable<R, C, V> table) {
        this.rowList = table.rowList;
        this.columnList = table.columnList;
        this.rowKeyToIndex = table.rowKeyToIndex;
        this.columnKeyToIndex = table.columnKeyToIndex;
        V[][] copy = (Object[][]) Array.newInstance(Object.class, this.rowList.size(), this.columnList.size());
        this.array = copy;
        for (int i = 0; i < this.rowList.size(); i++) {
            V[][] vArr = table.array;
            System.arraycopy(vArr[i], 0, copy[i], 0, vArr[i].length);
        }
    }

    private static abstract class ArrayMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> {
        private final ImmutableMap<K, Integer> keyIndex;

        /* access modifiers changed from: package-private */
        public abstract String getKeyRole();

        /* access modifiers changed from: package-private */
        @NullableDecl
        public abstract V getValue(int i);

        /* access modifiers changed from: package-private */
        @NullableDecl
        public abstract V setValue(int i, V v);

        private ArrayMap(ImmutableMap<K, Integer> keyIndex2) {
            this.keyIndex = keyIndex2;
        }

        public Set<K> keySet() {
            return this.keyIndex.keySet();
        }

        /* access modifiers changed from: package-private */
        public K getKey(int index) {
            return this.keyIndex.keySet().asList().get(index);
        }

        public int size() {
            return this.keyIndex.size();
        }

        public boolean isEmpty() {
            return this.keyIndex.isEmpty();
        }

        /* access modifiers changed from: package-private */
        public Map.Entry<K, V> getEntry(final int index) {
            Preconditions.checkElementIndex(index, size());
            return new AbstractMapEntry<K, V>() {
                public K getKey() {
                    return ArrayMap.this.getKey(index);
                }

                public V getValue() {
                    return ArrayMap.this.getValue(index);
                }

                public V setValue(V value) {
                    return ArrayMap.this.setValue(index, value);
                }
            };
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return new AbstractIndexedListIterator<Map.Entry<K, V>>(size()) {
                /* access modifiers changed from: protected */
                public Map.Entry<K, V> get(int index) {
                    return ArrayMap.this.getEntry(index);
                }
            };
        }

        public boolean containsKey(@NullableDecl Object key) {
            return this.keyIndex.containsKey(key);
        }

        public V get(@NullableDecl Object key) {
            Integer index = this.keyIndex.get(key);
            if (index == null) {
                return null;
            }
            return getValue(index.intValue());
        }

        public V put(K key, V value) {
            Integer index = this.keyIndex.get(key);
            if (index != null) {
                return setValue(index.intValue(), value);
            }
            String keyRole = getKeyRole();
            String valueOf = String.valueOf(key);
            String valueOf2 = String.valueOf(this.keyIndex.keySet());
            StringBuilder sb = new StringBuilder(String.valueOf(keyRole).length() + 9 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
            sb.append(keyRole);
            sb.append(" ");
            sb.append(valueOf);
            sb.append(" not in ");
            sb.append(valueOf2);
            throw new IllegalArgumentException(sb.toString());
        }

        public V remove(Object key) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }

    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }

    /* renamed from: at */
    public V mo23586at(int rowIndex, int columnIndex) {
        Preconditions.checkElementIndex(rowIndex, this.rowList.size());
        Preconditions.checkElementIndex(columnIndex, this.columnList.size());
        return this.array[rowIndex][columnIndex];
    }

    @CanIgnoreReturnValue
    public V set(int rowIndex, int columnIndex, @NullableDecl V value) {
        Preconditions.checkElementIndex(rowIndex, this.rowList.size());
        Preconditions.checkElementIndex(columnIndex, this.columnList.size());
        V[][] vArr = this.array;
        V oldValue = vArr[rowIndex][columnIndex];
        vArr[rowIndex][columnIndex] = value;
        return oldValue;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.reflect.Array.newInstance(java.lang.Class<?>, int[]):java.lang.Object VARARG throws java.lang.IllegalArgumentException, java.lang.NegativeArraySizeException}
     arg types: [java.lang.Class<V>, int[]]
     candidates:
      ClspMth{java.lang.reflect.Array.newInstance(java.lang.Class<?>, int):java.lang.Object throws java.lang.NegativeArraySizeException}
      ClspMth{java.lang.reflect.Array.newInstance(java.lang.Class<?>, int[]):java.lang.Object VARARG throws java.lang.IllegalArgumentException, java.lang.NegativeArraySizeException} */
    @GwtIncompatible
    public V[][] toArray(Class<V> valueClass) {
        V[][] copy = (Object[][]) Array.newInstance((Class<?>) valueClass, this.rowList.size(), this.columnList.size());
        for (int i = 0; i < this.rowList.size(); i++) {
            V[][] vArr = this.array;
            System.arraycopy(vArr[i], 0, copy[i], 0, vArr[i].length);
        }
        return copy;
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void eraseAll() {
        for (V[] row : this.array) {
            Arrays.fill(row, (Object) null);
        }
    }

    public boolean contains(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        return containsRow(rowKey) && containsColumn(columnKey);
    }

    public boolean containsColumn(@NullableDecl Object columnKey) {
        return this.columnKeyToIndex.containsKey(columnKey);
    }

    public boolean containsRow(@NullableDecl Object rowKey) {
        return this.rowKeyToIndex.containsKey(rowKey);
    }

    public boolean containsValue(@NullableDecl Object value) {
        for (V[] row : this.array) {
            for (V element : r0[r3]) {
                if (Objects.equal(value, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public V get(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        if (rowIndex == null || columnIndex == null) {
            return null;
        }
        return mo23586at(rowIndex.intValue(), columnIndex.intValue());
    }

    public boolean isEmpty() {
        return this.rowList.isEmpty() || this.columnList.isEmpty();
    }

    @CanIgnoreReturnValue
    public V put(R rowKey, C columnKey, @NullableDecl V value) {
        Preconditions.checkNotNull(rowKey);
        Preconditions.checkNotNull(columnKey);
        Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        boolean z = true;
        Preconditions.checkArgument(rowIndex != null, "Row %s not in %s", rowKey, this.rowList);
        Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        if (columnIndex == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "Column %s not in %s", columnKey, this.columnList);
        return set(rowIndex.intValue(), columnIndex.intValue(), value);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public V remove(Object rowKey, Object columnKey) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public V erase(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        if (rowIndex == null || columnIndex == null) {
            return null;
        }
        return set(rowIndex.intValue(), columnIndex.intValue(), null);
    }

    public int size() {
        return this.rowList.size() * this.columnList.size();
    }

    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(size()) {
            /* access modifiers changed from: protected */
            public Table.Cell<R, C, V> get(int index) {
                return ArrayTable.this.getCell(index);
            }
        };
    }

    /* access modifiers changed from: private */
    public Table.Cell<R, C, V> getCell(final int index) {
        return new Tables.AbstractCell<R, C, V>() {
            final int columnIndex = (index % ArrayTable.this.columnList.size());
            final int rowIndex = (index / ArrayTable.this.columnList.size());

            public R getRowKey() {
                return ArrayTable.this.rowList.get(this.rowIndex);
            }

            public C getColumnKey() {
                return ArrayTable.this.columnList.get(this.columnIndex);
            }

            public V getValue() {
                return ArrayTable.this.mo23586at(this.rowIndex, this.columnIndex);
            }
        };
    }

    /* access modifiers changed from: private */
    public V getValue(int index) {
        return mo23586at(index / this.columnList.size(), index % this.columnList.size());
    }

    public Map<R, V> column(C columnKey) {
        Preconditions.checkNotNull(columnKey);
        Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        return columnIndex == null ? ImmutableMap.m126of() : new Column(columnIndex.intValue());
    }

    private class Column extends ArrayMap<R, V> {
        final int columnIndex;

        Column(int columnIndex2) {
            super(ArrayTable.this.rowKeyToIndex);
            this.columnIndex = columnIndex2;
        }

        /* access modifiers changed from: package-private */
        public String getKeyRole() {
            return "Row";
        }

        /* access modifiers changed from: package-private */
        public V getValue(int index) {
            return ArrayTable.this.mo23586at(index, this.columnIndex);
        }

        /* access modifiers changed from: package-private */
        public V setValue(int index, V newValue) {
            return ArrayTable.this.set(index, this.columnIndex, newValue);
        }
    }

    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }

    public Map<C, Map<R, V>> columnMap() {
        ArrayTable<R, C, V>.ColumnMap map = this.columnMap;
        if (map != null) {
            return map;
        }
        ArrayTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.columnMap = columnMap2;
        return columnMap2;
    }

    private class ColumnMap extends ArrayMap<C, Map<R, V>> {
        private ColumnMap() {
            super(ArrayTable.this.columnKeyToIndex);
        }

        /* access modifiers changed from: package-private */
        public String getKeyRole() {
            return "Column";
        }

        /* access modifiers changed from: package-private */
        public Map<R, V> getValue(int index) {
            return new Column(index);
        }

        /* access modifiers changed from: package-private */
        public Map<R, V> setValue(int index, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }

        public Map<R, V> put(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public Map<C, V> row(R rowKey) {
        Preconditions.checkNotNull(rowKey);
        Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        return rowIndex == null ? ImmutableMap.m126of() : new Row(rowIndex.intValue());
    }

    private class Row extends ArrayMap<C, V> {
        final int rowIndex;

        Row(int rowIndex2) {
            super(ArrayTable.this.columnKeyToIndex);
            this.rowIndex = rowIndex2;
        }

        /* access modifiers changed from: package-private */
        public String getKeyRole() {
            return "Column";
        }

        /* access modifiers changed from: package-private */
        public V getValue(int index) {
            return ArrayTable.this.mo23586at(this.rowIndex, index);
        }

        /* access modifiers changed from: package-private */
        public V setValue(int index, V newValue) {
            return ArrayTable.this.set(this.rowIndex, index, newValue);
        }
    }

    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }

    public Map<R, Map<C, V>> rowMap() {
        ArrayTable<R, C, V>.RowMap map = this.rowMap;
        if (map != null) {
            return map;
        }
        ArrayTable<R, C, V>.RowMap rowMap2 = new RowMap();
        this.rowMap = rowMap2;
        return rowMap2;
    }

    private class RowMap extends ArrayMap<R, Map<C, V>> {
        private RowMap() {
            super(ArrayTable.this.rowKeyToIndex);
        }

        /* access modifiers changed from: package-private */
        public String getKeyRole() {
            return "Row";
        }

        /* access modifiers changed from: package-private */
        public Map<C, V> getValue(int index) {
            return new Row(index);
        }

        /* access modifiers changed from: package-private */
        public Map<C, V> setValue(int index, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }

        public Map<C, V> put(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public Collection<V> values() {
        return super.values();
    }

    /* access modifiers changed from: package-private */
    public Iterator<V> valuesIterator() {
        return new AbstractIndexedListIterator<V>(size()) {
            /* access modifiers changed from: protected */
            public V get(int index) {
                return ArrayTable.this.getValue(index);
            }
        };
    }
}
