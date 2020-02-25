package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

@GwtCompatible
public final class Tables {
    private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER = new Function<Map<Object, Object>, Map<Object, Object>>() {
        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            return apply((Map<Object, Object>) ((Map) obj));
        }

        public Map<Object, Object> apply(Map<Object, Object> input) {
            return Collections.unmodifiableMap(input);
        }
    };

    private Tables() {
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(@NullableDecl R rowKey, @NullableDecl C columnKey, @NullableDecl V value) {
        return new ImmutableCell(rowKey, columnKey, value);
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        if (table instanceof TransposeTable) {
            return ((TransposeTable) table).original;
        }
        return new TransposeTable(table);
    }

    @Beta
    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> backingMap, Supplier<? extends Map<C, V>> factory) {
        Preconditions.checkArgument(backingMap.isEmpty());
        Preconditions.checkNotNull(factory);
        return new StandardTable(backingMap, factory);
    }

    @Beta
    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
        return new TransformedTable(fromTable, function);
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new UnmodifiableTable(table);
    }

    @Beta
    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> table) {
        return new UnmodifiableRowSortedMap(table);
    }

    /* access modifiers changed from: private */
    public static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
        return UNMODIFIABLE_WRAPPER;
    }

    public static <R, C, V> Table<R, C, V> synchronizedTable(Table<R, C, V> table) {
        return Synchronized.table(table, null);
    }

    static boolean equalsImpl(Table<?, ?, ?> table, @NullableDecl Object obj) {
        if (obj == table) {
            return true;
        }
        if (obj instanceof Table) {
            return table.cellSet().equals(((Table) obj).cellSet());
        }
        return false;
    }

    static final class ImmutableCell<R, C, V> extends AbstractCell<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        private final C columnKey;
        @NullableDecl
        private final R rowKey;
        @NullableDecl
        private final V value;

        ImmutableCell(@NullableDecl R rowKey2, @NullableDecl C columnKey2, @NullableDecl V value2) {
            this.rowKey = rowKey2;
            this.columnKey = columnKey2;
            this.value = value2;
        }

        public R getRowKey() {
            return this.rowKey;
        }

        public C getColumnKey() {
            return this.columnKey;
        }

        public V getValue() {
            return this.value;
        }
    }

    static abstract class AbstractCell<R, C, V> implements Table.Cell<R, C, V> {
        AbstractCell() {
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell<?, ?, ?> other = (Table.Cell) obj;
            if (!Objects.equal(getRowKey(), other.getRowKey()) || !Objects.equal(getColumnKey(), other.getColumnKey()) || !Objects.equal(getValue(), other.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(getRowKey(), getColumnKey(), getValue());
        }

        public String toString() {
            String valueOf = String.valueOf(getRowKey());
            String valueOf2 = String.valueOf(getColumnKey());
            String valueOf3 = String.valueOf(getValue());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
            sb.append("(");
            sb.append(valueOf);
            sb.append(",");
            sb.append(valueOf2);
            sb.append(")=");
            sb.append(valueOf3);
            return sb.toString();
        }
    }

    private static class TransposeTable<C, R, V> extends AbstractTable<C, R, V> {
        private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>() {
            public /* bridge */ /* synthetic */ Object apply(Object obj) {
                return apply((Table.Cell<?, ?, ?>) ((Table.Cell) obj));
            }

            public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        };
        final Table<R, C, V> original;

        TransposeTable(Table<R, C, V> original2) {
            this.original = (Table) Preconditions.checkNotNull(original2);
        }

        public void clear() {
            this.original.clear();
        }

        public Map<C, V> column(R columnKey) {
            return this.original.row(columnKey);
        }

        public Set<R> columnKeySet() {
            return this.original.rowKeySet();
        }

        public Map<R, Map<C, V>> columnMap() {
            return this.original.rowMap();
        }

        public boolean contains(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            return this.original.contains(columnKey, rowKey);
        }

        public boolean containsColumn(@NullableDecl Object columnKey) {
            return this.original.containsRow(columnKey);
        }

        public boolean containsRow(@NullableDecl Object rowKey) {
            return this.original.containsColumn(rowKey);
        }

        public boolean containsValue(@NullableDecl Object value) {
            return this.original.containsValue(value);
        }

        public V get(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            return this.original.get(columnKey, rowKey);
        }

        public V put(C rowKey, R columnKey, V value) {
            return this.original.put(columnKey, rowKey, value);
        }

        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.original.putAll(Tables.transpose(table));
        }

        public V remove(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            return this.original.remove(columnKey, rowKey);
        }

        public Map<R, V> row(C rowKey) {
            return this.original.column(rowKey);
        }

        public Set<C> rowKeySet() {
            return this.original.columnKeySet();
        }

        public Map<C, Map<R, V>> rowMap() {
            return this.original.columnMap();
        }

        public int size() {
            return this.original.size();
        }

        public Collection<V> values() {
            return this.original.values();
        }

        /* access modifiers changed from: package-private */
        public Iterator<Table.Cell<C, R, V>> cellIterator() {
            return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
        }
    }

    private static class TransformedTable<R, C, V1, V2> extends AbstractTable<R, C, V2> {
        final Table<R, C, V1> fromTable;
        final Function<? super V1, V2> function;

        TransformedTable(Table<R, C, V1> fromTable2, Function<? super V1, V2> function2) {
            this.fromTable = (Table) Preconditions.checkNotNull(fromTable2);
            this.function = (Function) Preconditions.checkNotNull(function2);
        }

        public boolean contains(Object rowKey, Object columnKey) {
            return this.fromTable.contains(rowKey, columnKey);
        }

        public V2 get(Object rowKey, Object columnKey) {
            if (contains(rowKey, columnKey)) {
                return this.function.apply(this.fromTable.get(rowKey, columnKey));
            }
            return null;
        }

        public int size() {
            return this.fromTable.size();
        }

        public void clear() {
            this.fromTable.clear();
        }

        public V2 put(R r, C c, V2 v2) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        public V2 remove(Object rowKey, Object columnKey) {
            if (contains(rowKey, columnKey)) {
                return this.function.apply(this.fromTable.remove(rowKey, columnKey));
            }
            return null;
        }

        public Map<C, V2> row(R rowKey) {
            return Maps.transformValues(this.fromTable.row(rowKey), this.function);
        }

        public Map<R, V2> column(C columnKey) {
            return Maps.transformValues(this.fromTable.column(columnKey), this.function);
        }

        /* access modifiers changed from: package-private */
        public Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction() {
            return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>() {
                public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.function.apply(cell.getValue()));
                }
            };
        }

        /* access modifiers changed from: package-private */
        public Iterator<Table.Cell<R, C, V2>> cellIterator() {
            return Iterators.transform(this.fromTable.cellSet().iterator(), cellFunction());
        }

        public Set<R> rowKeySet() {
            return this.fromTable.rowKeySet();
        }

        public Set<C> columnKeySet() {
            return this.fromTable.columnKeySet();
        }

        /* access modifiers changed from: package-private */
        public Collection<V2> createValues() {
            return Collections2.transform(this.fromTable.values(), this.function);
        }

        public Map<R, Map<C, V2>> rowMap() {
            return Maps.transformValues(this.fromTable.rowMap(), new Function<Map<C, V1>, Map<C, V2>>() {
                public Map<C, V2> apply(Map<C, V1> row) {
                    return Maps.transformValues(row, TransformedTable.this.function);
                }
            });
        }

        public Map<C, Map<R, V2>> columnMap() {
            return Maps.transformValues(this.fromTable.columnMap(), new Function<Map<R, V1>, Map<R, V2>>() {
                public Map<R, V2> apply(Map<R, V1> column) {
                    return Maps.transformValues(column, TransformedTable.this.function);
                }
            });
        }
    }

    private static class UnmodifiableTable<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final Table<? extends R, ? extends C, ? extends V> delegate;

        UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> delegate2) {
            this.delegate = (Table) Preconditions.checkNotNull(delegate2);
        }

        /* access modifiers changed from: protected */
        public Table<R, C, V> delegate() {
            return this.delegate;
        }

        public Set<Table.Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Map<R, V> column(@NullableDecl C columnKey) {
            return Collections.unmodifiableMap(super.column(columnKey));
        }

        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        public Map<C, Map<R, V>> columnMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), Tables.unmodifiableWrapper()));
        }

        public V put(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        public V remove(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
            throw new UnsupportedOperationException();
        }

        public Map<C, V> row(@NullableDecl R rowKey) {
            return Collections.unmodifiableMap(super.row(rowKey));
        }

        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        public Map<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), Tables.unmodifiableWrapper()));
        }

        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }
    }

    static final class UnmodifiableRowSortedMap<R, C, V> extends UnmodifiableTable<R, C, V> implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> delegate) {
            super(delegate);
        }

        /* access modifiers changed from: protected */
        public RowSortedTable<R, C, V> delegate() {
            return (RowSortedTable) super.delegate();
        }

        public SortedMap<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableSortedMap(Maps.transformValues(delegate().rowMap(), Tables.unmodifiableWrapper()));
        }

        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(delegate().rowKeySet());
        }
    }
}
