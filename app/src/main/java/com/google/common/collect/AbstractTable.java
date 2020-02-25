package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@GwtCompatible
abstract class AbstractTable<R, C, V> implements Table<R, C, V> {
    @MonotonicNonNullDecl
    private transient Set<Table.Cell<R, C, V>> cellSet;
    @MonotonicNonNullDecl
    private transient Collection<V> values;

    AbstractTable() {
    }

    /* access modifiers changed from: package-private */
    public abstract Iterator<Table.Cell<R, C, V>> cellIterator();

    public boolean containsRow(@NullableDecl Object rowKey) {
        return Maps.safeContainsKey(rowMap(), rowKey);
    }

    public boolean containsColumn(@NullableDecl Object columnKey) {
        return Maps.safeContainsKey(columnMap(), columnKey);
    }

    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    public Set<C> columnKeySet() {
        return columnMap().keySet();
    }

    public boolean containsValue(@NullableDecl Object value) {
        for (Map<C, V> row : rowMap().values()) {
            if (row.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        Map<C, V> row = (Map) Maps.safeGet(rowMap(), rowKey);
        return row != null && Maps.safeContainsKey(row, columnKey);
    }

    public V get(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        Map<C, V> row = (Map) Maps.safeGet(rowMap(), rowKey);
        if (row == null) {
            return null;
        }
        return Maps.safeGet(row, columnKey);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        Iterators.clear(cellSet().iterator());
    }

    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        Map<C, V> row = (Map) Maps.safeGet(rowMap(), rowKey);
        if (row == null) {
            return null;
        }
        return Maps.safeRemove(row, columnKey);
    }

    @CanIgnoreReturnValue
    public V put(R rowKey, C columnKey, V value) {
        return row(rowKey).put(columnKey, value);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
            put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }

    public Set<Table.Cell<R, C, V>> cellSet() {
        Set<Table.Cell<R, C, V>> result = this.cellSet;
        if (result != null) {
            return result;
        }
        Set<Table.Cell<R, C, V>> createCellSet = createCellSet();
        this.cellSet = createCellSet;
        return createCellSet;
    }

    /* access modifiers changed from: package-private */
    public Set<Table.Cell<R, C, V>> createCellSet() {
        return new CellSet();
    }

    public Collection<V> values() {
        Collection<V> result = this.values;
        if (result != null) {
            return result;
        }
        Collection<V> createValues = createValues();
        this.values = createValues;
        return createValues;
    }

    /* access modifiers changed from: package-private */
    public Collection<V> createValues() {
        return new Values();
    }

    /* access modifiers changed from: package-private */
    public Iterator<V> valuesIterator() {
        return new TransformedIterator<Table.Cell<R, C, V>, V>(this, cellSet().iterator()) {
            /* access modifiers changed from: package-private */
            public V transform(Table.Cell<R, C, V> cell) {
                return cell.getValue();
            }
        };
    }

    public boolean equals(@NullableDecl Object obj) {
        return Tables.equalsImpl(this, obj);
    }

    public int hashCode() {
        return cellSet().hashCode();
    }

    public String toString() {
        return rowMap().toString();
    }

    class CellSet extends AbstractSet<Table.Cell<R, C, V>> {
        CellSet() {
        }

        public boolean contains(Object o) {
            if (!(o instanceof Table.Cell)) {
                return false;
            }
            Table.Cell<?, ?, ?> cell = (Table.Cell) o;
            Map<C, V> row = (Map) Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
            if (row == null || !Collections2.safeContains(row.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()))) {
                return false;
            }
            return true;
        }

        public boolean remove(@NullableDecl Object o) {
            if (!(o instanceof Table.Cell)) {
                return false;
            }
            Table.Cell<?, ?, ?> cell = (Table.Cell) o;
            Map<C, V> row = (Map) Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
            if (row == null || !Collections2.safeRemove(row.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()))) {
                return false;
            }
            return true;
        }

        public void clear() {
            AbstractTable.this.clear();
        }

        public Iterator<Table.Cell<R, C, V>> iterator() {
            return AbstractTable.this.cellIterator();
        }

        public int size() {
            return AbstractTable.this.size();
        }
    }

    class Values extends AbstractCollection<V> {
        Values() {
        }

        public Iterator<V> iterator() {
            return AbstractTable.this.valuesIterator();
        }

        public boolean contains(Object o) {
            return AbstractTable.this.containsValue(o);
        }

        public void clear() {
            AbstractTable.this.clear();
        }

        public int size() {
            return AbstractTable.this.size();
        }
    }
}
