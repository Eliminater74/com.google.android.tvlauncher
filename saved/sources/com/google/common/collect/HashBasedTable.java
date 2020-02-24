package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
public class HashBasedTable<R, C, V> extends StandardTable<R, C, V> {
    private static final long serialVersionUID = 0;

    public /* bridge */ /* synthetic */ Set cellSet() {
        return super.cellSet();
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Map column(Object obj) {
        return super.column(obj);
    }

    public /* bridge */ /* synthetic */ Set columnKeySet() {
        return super.columnKeySet();
    }

    public /* bridge */ /* synthetic */ Map columnMap() {
        return super.columnMap();
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2, Object obj3) {
        return super.put(obj, obj2, obj3);
    }

    public /* bridge */ /* synthetic */ void putAll(Table table) {
        super.putAll(table);
    }

    public /* bridge */ /* synthetic */ Map row(Object obj) {
        return super.row(obj);
    }

    public /* bridge */ /* synthetic */ Set rowKeySet() {
        return super.rowKeySet();
    }

    public /* bridge */ /* synthetic */ Map rowMap() {
        return super.rowMap();
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    private static class Factory<C, V> implements Supplier<Map<C, V>>, Serializable {
        private static final long serialVersionUID = 0;
        final int expectedSize;

        Factory(int expectedSize2) {
            this.expectedSize = expectedSize2;
        }

        public Map<C, V> get() {
            return Maps.newLinkedHashMapWithExpectedSize(this.expectedSize);
        }
    }

    public static <R, C, V> HashBasedTable<R, C, V> create() {
        return new HashBasedTable<>(new LinkedHashMap(), new Factory(0));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(int expectedRows, int expectedCellsPerRow) {
        CollectPreconditions.checkNonnegative(expectedCellsPerRow, "expectedCellsPerRow");
        return new HashBasedTable<>(Maps.newLinkedHashMapWithExpectedSize(expectedRows), new Factory(expectedCellsPerRow));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> table) {
        HashBasedTable<R, C, V> result = create();
        result.putAll(table);
        return result;
    }

    HashBasedTable(Map<R, Map<C, V>> backingMap, Factory<C, V> factory) {
        super(backingMap, factory);
    }

    public boolean contains(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        return super.contains(rowKey, columnKey);
    }

    public boolean containsColumn(@NullableDecl Object columnKey) {
        return super.containsColumn(columnKey);
    }

    public boolean containsRow(@NullableDecl Object rowKey) {
        return super.containsRow(rowKey);
    }

    public boolean containsValue(@NullableDecl Object value) {
        return super.containsValue(value);
    }

    public V get(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        return super.get(rowKey, columnKey);
    }

    public boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        return super.remove(rowKey, columnKey);
    }
}
