package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@GwtCompatible
abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
    RegularImmutableTable() {
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(List<Table.Cell<R, C, V>> cells, @NullableDecl final Comparator<? super R> rowComparator, @NullableDecl final Comparator<? super C> columnComparator) {
        Preconditions.checkNotNull(cells);
        if (!(rowComparator == null && columnComparator == null)) {
            Collections.sort(cells, new Comparator<Table.Cell<R, C, V>>() {
                public int compare(Table.Cell<R, C, V> cell1, Table.Cell<R, C, V> cell2) {
                    int rowCompare;
                    Comparator comparator = rowComparator;
                    if (comparator == null) {
                        rowCompare = 0;
                    } else {
                        rowCompare = comparator.compare(cell1.getRowKey(), cell2.getRowKey());
                    }
                    if (rowCompare != 0) {
                        return rowCompare;
                    }
                    Comparator comparator2 = columnComparator;
                    if (comparator2 == null) {
                        return 0;
                    }
                    return comparator2.compare(cell1.getColumnKey(), cell2.getColumnKey());
                }
            });
        }
        return forCellsInternal(cells, rowComparator, columnComparator);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Table.Cell<R, C, V>> cells) {
        return forCellsInternal(cells, null, null);
    }

    private static <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(Iterable<Table.Cell<R, C, V>> cells, @NullableDecl Comparator<? super R> rowComparator, @NullableDecl Comparator<? super C> columnComparator) {
        ImmutableSet<R> rowSpace;
        ImmutableSet<C> columnSpace;
        Set<R> rowSpaceBuilder = new LinkedHashSet<>();
        Set<C> columnSpaceBuilder = new LinkedHashSet<>();
        ImmutableList<Table.Cell<R, C, V>> cellList = ImmutableList.copyOf(cells);
        for (Table.Cell<R, C, V> cell : cells) {
            rowSpaceBuilder.add(cell.getRowKey());
            columnSpaceBuilder.add(cell.getColumnKey());
        }
        if (rowComparator == null) {
            rowSpace = ImmutableSet.copyOf((Collection) rowSpaceBuilder);
        } else {
            rowSpace = ImmutableSet.copyOf((Collection) ImmutableList.sortedCopyOf(rowComparator, rowSpaceBuilder));
        }
        if (columnComparator == null) {
            columnSpace = ImmutableSet.copyOf((Collection) columnSpaceBuilder);
        } else {
            columnSpace = ImmutableSet.copyOf((Collection) ImmutableList.sortedCopyOf(columnComparator, columnSpaceBuilder));
        }
        return forOrderedComponents(cellList, rowSpace, columnSpace);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forOrderedComponents(ImmutableList<Table.Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace) {
        if (((long) cellList.size()) > (((long) rowSpace.size()) * ((long) columnSpace.size())) / 2) {
            return new DenseImmutableTable(cellList, rowSpace, columnSpace);
        }
        return new SparseImmutableTable(cellList, rowSpace, columnSpace);
    }

    /* access modifiers changed from: package-private */
    public abstract Table.Cell<R, C, V> getCell(int i);

    /* access modifiers changed from: package-private */
    public abstract V getValue(int i);

    /* access modifiers changed from: package-private */
    public final ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
        return isEmpty() ? ImmutableSet.m149of() : new CellSet();
    }

    /* access modifiers changed from: package-private */
    public final ImmutableCollection<V> createValues() {
        return isEmpty() ? ImmutableList.m107of() : new Values();
    }

    /* access modifiers changed from: package-private */
    public final void checkNoDuplicate(R rowKey, C columnKey, V existingValue, V newValue) {
        Preconditions.checkArgument(existingValue == null, "Duplicate key: (row=%s, column=%s), values: [%s, %s].", rowKey, columnKey, newValue, existingValue);
    }

    private final class CellSet extends IndexedImmutableSet<Table.Cell<R, C, V>> {
        private CellSet() {
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }

        /* access modifiers changed from: package-private */
        public Table.Cell<R, C, V> get(int index) {
            return RegularImmutableTable.this.getCell(index);
        }

        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof Table.Cell)) {
                return false;
            }
            Table.Cell<?, ?, ?> cell = (Table.Cell) object;
            Object value = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
            if (value == null || !value.equals(cell.getValue())) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return false;
        }
    }

    private final class Values extends ImmutableList<V> {
        private Values() {
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }

        public V get(int index) {
            return RegularImmutableTable.this.getValue(index);
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }
    }
}
