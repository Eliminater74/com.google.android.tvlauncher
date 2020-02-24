package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0;
    private final Comparator<? super C> columnComparator;

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

    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.contains(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsColumn(@NullableDecl Object obj) {
        return super.containsColumn(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsRow(@NullableDecl Object obj) {
        return super.containsRow(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ Object get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.get(obj, obj2);
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

    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.remove(obj, obj2);
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

    private static class Factory<C, V> implements Supplier<TreeMap<C, V>>, Serializable {
        private static final long serialVersionUID = 0;
        final Comparator<? super C> comparator;

        Factory(Comparator<? super C> comparator2) {
            this.comparator = comparator2;
        }

        public TreeMap<C, V> get() {
            return new TreeMap<>(this.comparator);
        }
    }

    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable<>(Ordering.natural(), Ordering.natural());
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> rowComparator, Comparator<? super C> columnComparator2) {
        Preconditions.checkNotNull(rowComparator);
        Preconditions.checkNotNull(columnComparator2);
        return new TreeBasedTable<>(rowComparator, columnComparator2);
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> table) {
        TreeBasedTable<R, C, V> result = new TreeBasedTable<>(table.rowComparator(), table.columnComparator());
        result.putAll(table);
        return result;
    }

    TreeBasedTable(Comparator<? super R> rowComparator, Comparator<? super C> columnComparator2) {
        super(new TreeMap(rowComparator), new Factory(columnComparator2));
        this.columnComparator = columnComparator2;
    }

    @Deprecated
    public Comparator<? super R> rowComparator() {
        return rowKeySet().comparator();
    }

    @Deprecated
    public Comparator<? super C> columnComparator() {
        return this.columnComparator;
    }

    public SortedMap<C, V> row(R rowKey) {
        return new TreeRow(this, rowKey);
    }

    private class TreeRow extends StandardTable<R, C, V>.Row implements SortedMap<C, V> {
        @NullableDecl
        final C lowerBound;
        @NullableDecl
        final C upperBound;
        @NullableDecl
        transient SortedMap<C, V> wholeRow;

        TreeRow(TreeBasedTable treeBasedTable, R rowKey) {
            this(rowKey, null, null);
        }

        TreeRow(R rowKey, @NullableDecl C lowerBound2, @NullableDecl C upperBound2) {
            super(rowKey);
            this.lowerBound = lowerBound2;
            this.upperBound = upperBound2;
            Preconditions.checkArgument(lowerBound2 == null || upperBound2 == null || compare(lowerBound2, upperBound2) <= 0);
        }

        public SortedSet<C> keySet() {
            return new Maps.SortedKeySet(this);
        }

        public Comparator<? super C> comparator() {
            return TreeBasedTable.this.columnComparator();
        }

        /* access modifiers changed from: package-private */
        public int compare(Object a, Object b) {
            return comparator().compare(a, b);
        }

        /* access modifiers changed from: package-private */
        public boolean rangeContains(@NullableDecl Object o) {
            C c;
            C c2;
            return o != null && ((c = this.lowerBound) == null || compare(c, o) <= 0) && ((c2 = this.upperBound) == null || compare(c2, o) > 0);
        }

        public SortedMap<C, V> subMap(C fromKey, C toKey) {
            Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(fromKey)) && rangeContains(Preconditions.checkNotNull(toKey)));
            return new TreeRow(this.rowKey, fromKey, toKey);
        }

        public SortedMap<C, V> headMap(C toKey) {
            Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(toKey)));
            return new TreeRow(this.rowKey, this.lowerBound, toKey);
        }

        public SortedMap<C, V> tailMap(C fromKey) {
            Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(fromKey)));
            return new TreeRow(this.rowKey, fromKey, this.upperBound);
        }

        public C firstKey() {
            if (backingRowMap() != null) {
                return backingRowMap().firstKey();
            }
            throw new NoSuchElementException();
        }

        public C lastKey() {
            if (backingRowMap() != null) {
                return backingRowMap().lastKey();
            }
            throw new NoSuchElementException();
        }

        /* access modifiers changed from: package-private */
        public SortedMap<C, V> wholeRow() {
            SortedMap<C, V> sortedMap = this.wholeRow;
            if (sortedMap == null || (sortedMap.isEmpty() && TreeBasedTable.this.backingMap.containsKey(this.rowKey))) {
                this.wholeRow = (SortedMap) TreeBasedTable.this.backingMap.get(this.rowKey);
            }
            return this.wholeRow;
        }

        /* access modifiers changed from: package-private */
        public SortedMap<C, V> backingRowMap() {
            return (SortedMap) super.backingRowMap();
        }

        /* access modifiers changed from: package-private */
        public SortedMap<C, V> computeBackingRowMap() {
            SortedMap<C, V> map = wholeRow();
            if (map == null) {
                return null;
            }
            C c = this.lowerBound;
            if (c != null) {
                map = map.tailMap(c);
            }
            C c2 = this.upperBound;
            if (c2 != null) {
                return map.headMap(c2);
            }
            return map;
        }

        /* access modifiers changed from: package-private */
        public void maintainEmptyInvariant() {
            if (wholeRow() != null && this.wholeRow.isEmpty()) {
                TreeBasedTable.this.backingMap.remove(this.rowKey);
                this.wholeRow = null;
                this.backingRowMap = null;
            }
        }

        public boolean containsKey(Object key) {
            return rangeContains(key) && super.containsKey(key);
        }

        public V put(C key, V value) {
            Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(key)));
            return super.put(key, value);
        }
    }

    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }

    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }

    /* access modifiers changed from: package-private */
    public Iterator<C> createColumnKeyIterator() {
        final Comparator<? super C> comparator = columnComparator();
        final Iterator<C> merged = Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>(this) {
            public Iterator<C> apply(Map<C, V> input) {
                return input.keySet().iterator();
            }
        }), comparator);
        return new AbstractIterator<C>(this) {
            @NullableDecl
            C lastValue;

            /* access modifiers changed from: protected */
            public C computeNext() {
                boolean duplicate;
                while (merged.hasNext()) {
                    C next = merged.next();
                    C c = this.lastValue;
                    if (c == null || comparator.compare(next, c) != 0) {
                        duplicate = false;
                        continue;
                    } else {
                        duplicate = true;
                        continue;
                    }
                    if (!duplicate) {
                        this.lastValue = next;
                        return this.lastValue;
                    }
                }
                this.lastValue = null;
                return endOfData();
            }
        };
    }
}
