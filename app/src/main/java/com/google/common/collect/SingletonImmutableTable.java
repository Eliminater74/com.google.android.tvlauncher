package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

import java.util.Map;

@GwtCompatible
class SingletonImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
    final C singleColumnKey;
    final R singleRowKey;
    final V singleValue;

    SingletonImmutableTable(R rowKey, C columnKey, V value) {
        this.singleRowKey = Preconditions.checkNotNull(rowKey);
        this.singleColumnKey = Preconditions.checkNotNull(columnKey);
        this.singleValue = Preconditions.checkNotNull(value);
    }

    SingletonImmutableTable(Table.Cell<R, C, V> cell) {
        this(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
    }

    public ImmutableMap<R, V> column(C columnKey) {
        Preconditions.checkNotNull(columnKey);
        if (containsColumn(columnKey)) {
            return ImmutableMap.m127of(this.singleRowKey, this.singleValue);
        }
        return ImmutableMap.m126of();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.m127of(this.singleColumnKey, ImmutableMap.m127of(this.singleRowKey, this.singleValue));
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.m127of(this.singleRowKey, ImmutableMap.m127of(this.singleColumnKey, this.singleValue));
    }

    public int size() {
        return 1;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
        return ImmutableSet.m150of(cellOf(this.singleRowKey, this.singleColumnKey, this.singleValue));
    }

    /* access modifiers changed from: package-private */
    public ImmutableCollection<V> createValues() {
        return ImmutableSet.m150of(this.singleValue);
    }

    /* access modifiers changed from: package-private */
    public ImmutableTable.SerializedForm createSerializedForm() {
        return ImmutableTable.SerializedForm.create(this, new int[]{0}, new int[]{0});
    }
}
