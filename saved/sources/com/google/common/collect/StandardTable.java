package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    @GwtTransient
    final Map<R, Map<C, V>> backingMap;
    @MonotonicNonNullDecl
    private transient Set<C> columnKeySet;
    @MonotonicNonNullDecl
    private transient StandardTable<R, C, V>.ColumnMap columnMap;
    @GwtTransient
    final Supplier<? extends Map<C, V>> factory;
    @MonotonicNonNullDecl
    private transient Map<R, Map<C, V>> rowMap;

    StandardTable(Map<R, Map<C, V>> backingMap2, Supplier<? extends Map<C, V>> factory2) {
        this.backingMap = backingMap2;
        this.factory = factory2;
    }

    public boolean contains(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        return (rowKey == null || columnKey == null || !super.contains(rowKey, columnKey)) ? false : true;
    }

    public boolean containsColumn(@NullableDecl Object columnKey) {
        if (columnKey == null) {
            return false;
        }
        for (Map<C, V> map : this.backingMap.values()) {
            if (Maps.safeContainsKey(map, columnKey)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsRow(@NullableDecl Object rowKey) {
        return rowKey != null && Maps.safeContainsKey(this.backingMap, rowKey);
    }

    public boolean containsValue(@NullableDecl Object value) {
        return value != null && super.containsValue(value);
    }

    public V get(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        if (rowKey == null || columnKey == null) {
            return null;
        }
        return super.get(rowKey, columnKey);
    }

    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    public int size() {
        int size = 0;
        for (Map<C, V> map : this.backingMap.values()) {
            size += map.size();
        }
        return size;
    }

    public void clear() {
        this.backingMap.clear();
    }

    private Map<C, V> getOrCreate(R rowKey) {
        Map<C, V> map = this.backingMap.get(rowKey);
        if (map != null) {
            return map;
        }
        Map<C, V> map2 = (Map) this.factory.get();
        this.backingMap.put(rowKey, map2);
        return map2;
    }

    @CanIgnoreReturnValue
    public V put(R rowKey, C columnKey, V value) {
        Preconditions.checkNotNull(rowKey);
        Preconditions.checkNotNull(columnKey);
        Preconditions.checkNotNull(value);
        return getOrCreate(rowKey).put(columnKey, value);
    }

    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object rowKey, @NullableDecl Object columnKey) {
        Map<C, V> map;
        if (rowKey == null || columnKey == null || (map = (Map) Maps.safeGet(this.backingMap, rowKey)) == null) {
            return null;
        }
        V value = map.remove(columnKey);
        if (map.isEmpty()) {
            this.backingMap.remove(rowKey);
        }
        return value;
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Map<R, V> removeColumn(Object column) {
        Map<R, V> output = new LinkedHashMap<>();
        Iterator<Map.Entry<R, Map<C, V>>> iterator = this.backingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<R, Map<C, V>> entry = iterator.next();
            V value = entry.getValue().remove(column);
            if (value != null) {
                output.put(entry.getKey(), value);
                if (entry.getValue().isEmpty()) {
                    iterator.remove();
                }
            }
        }
        return output;
    }

    /* access modifiers changed from: private */
    public boolean containsMapping(Object rowKey, Object columnKey, Object value) {
        return value != null && value.equals(get(rowKey, columnKey));
    }

    /* access modifiers changed from: private */
    public boolean removeMapping(Object rowKey, Object columnKey, Object value) {
        if (!containsMapping(rowKey, columnKey, value)) {
            return false;
        }
        remove(rowKey, columnKey);
        return true;
    }

    private abstract class TableSet<T> extends Sets.ImprovedAbstractSet<T> {
        private TableSet() {
        }

        public boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }

        public void clear() {
            StandardTable.this.backingMap.clear();
        }
    }

    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new CellIterator();
    }

    private class CellIterator implements Iterator<Table.Cell<R, C, V>> {
        Iterator<Map.Entry<C, V>> columnIterator;
        @NullableDecl
        Map.Entry<R, Map<C, V>> rowEntry;
        final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;

        private CellIterator() {
            this.rowIterator = StandardTable.this.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.emptyModifiableIterator();
        }

        public boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }

        public Table.Cell<R, C, V> next() {
            if (!this.columnIterator.hasNext()) {
                this.rowEntry = this.rowIterator.next();
                this.columnIterator = this.rowEntry.getValue().entrySet().iterator();
            }
            Map.Entry<C, V> columnEntry = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), columnEntry.getKey(), columnEntry.getValue());
        }

        public void remove() {
            this.columnIterator.remove();
            if (this.rowEntry.getValue().isEmpty()) {
                this.rowIterator.remove();
                this.rowEntry = null;
            }
        }
    }

    public Map<C, V> row(R rowKey) {
        return new Row(rowKey);
    }

    class Row extends Maps.IteratorBasedAbstractMap<C, V> {
        @NullableDecl
        Map<C, V> backingRowMap;
        final R rowKey;

        Row(R rowKey2) {
            this.rowKey = Preconditions.checkNotNull(rowKey2);
        }

        /* access modifiers changed from: package-private */
        public Map<C, V> backingRowMap() {
            Map<C, V> map = this.backingRowMap;
            if (map != null && (!map.isEmpty() || !StandardTable.this.backingMap.containsKey(this.rowKey))) {
                return this.backingRowMap;
            }
            Map<C, V> computeBackingRowMap = computeBackingRowMap();
            this.backingRowMap = computeBackingRowMap;
            return computeBackingRowMap;
        }

        /* access modifiers changed from: package-private */
        public Map<C, V> computeBackingRowMap() {
            return StandardTable.this.backingMap.get(this.rowKey);
        }

        /* access modifiers changed from: package-private */
        public void maintainEmptyInvariant() {
            if (backingRowMap() != null && this.backingRowMap.isEmpty()) {
                StandardTable.this.backingMap.remove(this.rowKey);
                this.backingRowMap = null;
            }
        }

        public boolean containsKey(Object key) {
            Map<C, V> backingRowMap2 = backingRowMap();
            return (key == null || backingRowMap2 == null || !Maps.safeContainsKey(backingRowMap2, key)) ? false : true;
        }

        public V get(Object key) {
            Map<C, V> backingRowMap2 = backingRowMap();
            if (key == null || backingRowMap2 == null) {
                return null;
            }
            return Maps.safeGet(backingRowMap2, key);
        }

        public V put(C key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            Map<C, V> map = this.backingRowMap;
            if (map == null || map.isEmpty()) {
                return StandardTable.this.put(this.rowKey, key, value);
            }
            return this.backingRowMap.put(key, value);
        }

        public V remove(Object key) {
            Map<C, V> backingRowMap2 = backingRowMap();
            if (backingRowMap2 == null) {
                return null;
            }
            V result = Maps.safeRemove(backingRowMap2, key);
            maintainEmptyInvariant();
            return result;
        }

        public void clear() {
            Map<C, V> backingRowMap2 = backingRowMap();
            if (backingRowMap2 != null) {
                backingRowMap2.clear();
            }
            maintainEmptyInvariant();
        }

        public int size() {
            Map<C, V> map = backingRowMap();
            if (map == null) {
                return 0;
            }
            return map.size();
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<C, V>> entryIterator() {
            Map<C, V> map = backingRowMap();
            if (map == null) {
                return Iterators.emptyModifiableIterator();
            }
            final Iterator<Map.Entry<C, V>> iterator = map.entrySet().iterator();
            return new Iterator<Map.Entry<C, V>>() {
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                public Map.Entry<C, V> next() {
                    return Row.this.wrapEntry((Map.Entry) iterator.next());
                }

                public void remove() {
                    iterator.remove();
                    Row.this.maintainEmptyInvariant();
                }
            };
        }

        /* access modifiers changed from: package-private */
        public Map.Entry<C, V> wrapEntry(final Map.Entry<C, V> entry) {
            return new ForwardingMapEntry<C, V>(this) {
                /* access modifiers changed from: protected */
                public Map.Entry<C, V> delegate() {
                    return entry;
                }

                public V setValue(V value) {
                    return super.setValue(Preconditions.checkNotNull(value));
                }

                public boolean equals(Object object) {
                    return standardEquals(object);
                }
            };
        }
    }

    public Map<R, V> column(C columnKey) {
        return new Column(columnKey);
    }

    private class Column extends Maps.ViewCachingAbstractMap<R, V> {
        final C columnKey;

        Column(C columnKey2) {
            this.columnKey = Preconditions.checkNotNull(columnKey2);
        }

        public V put(R key, V value) {
            return StandardTable.this.put(key, this.columnKey, value);
        }

        public V get(Object key) {
            return StandardTable.this.get(key, this.columnKey);
        }

        public boolean containsKey(Object key) {
            return StandardTable.this.contains(key, this.columnKey);
        }

        public V remove(Object key) {
            return StandardTable.this.remove(key, this.columnKey);
        }

        /* access modifiers changed from: package-private */
        @CanIgnoreReturnValue
        public boolean removeFromColumnIf(Predicate<? super Map.Entry<R, V>> predicate) {
            boolean changed = false;
            Iterator<Map.Entry<R, Map<C, V>>> iterator = StandardTable.this.backingMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<R, Map<C, V>> entry = iterator.next();
                Map<C, V> map = entry.getValue();
                V value = map.get(this.columnKey);
                if (value != null && predicate.apply(Maps.immutableEntry(entry.getKey(), value))) {
                    map.remove(this.columnKey);
                    changed = true;
                    if (map.isEmpty()) {
                        iterator.remove();
                    }
                }
            }
            return changed;
        }

        /* access modifiers changed from: package-private */
        public Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet();
        }

        private class EntrySet extends Sets.ImprovedAbstractSet<Map.Entry<R, V>> {
            private EntrySet() {
            }

            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            public int size() {
                int size = 0;
                for (Map<C, V> map : StandardTable.this.backingMap.values()) {
                    if (map.containsKey(Column.this.columnKey)) {
                        size++;
                    }
                }
                return size;
            }

            public boolean isEmpty() {
                return !StandardTable.this.containsColumn(Column.this.columnKey);
            }

            public void clear() {
                Column.this.removeFromColumnIf(Predicates.alwaysTrue());
            }

            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) o;
                return StandardTable.this.containsMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            }

            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                return StandardTable.this.removeMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            }

            public boolean retainAll(Collection<?> c) {
                return Column.this.removeFromColumnIf(Predicates.not(Predicates.m84in(c)));
            }
        }

        private class EntrySetIterator extends AbstractIterator<Map.Entry<R, V>> {
            final Iterator<Map.Entry<R, Map<C, V>>> iterator;

            private EntrySetIterator() {
                this.iterator = StandardTable.this.backingMap.entrySet().iterator();
            }

            /* access modifiers changed from: protected */
            public Map.Entry<R, V> computeNext() {
                while (this.iterator.hasNext()) {
                    final Map.Entry<R, Map<C, V>> entry = this.iterator.next();
                    if (entry.getValue().containsKey(Column.this.columnKey)) {
                        return new AbstractMapEntry<R, V>() {
                            public R getKey() {
                                return entry.getKey();
                            }

                            public V getValue() {
                                return ((Map) entry.getValue()).get(Column.this.columnKey);
                            }

                            public V setValue(V value) {
                                return ((Map) entry.getValue()).put(Column.this.columnKey, Preconditions.checkNotNull(value));
                            }
                        };
                    }
                }
                return (Map.Entry) endOfData();
            }
        }

        /* access modifiers changed from: package-private */
        public Set<R> createKeySet() {
            return new KeySet();
        }

        private class KeySet extends Maps.KeySet<R, V> {
            KeySet() {
                super(Column.this);
            }

            public boolean contains(Object obj) {
                return StandardTable.this.contains(obj, Column.this.columnKey);
            }

            public boolean remove(Object obj) {
                return StandardTable.this.remove(obj, Column.this.columnKey) != null;
            }

            public boolean retainAll(Collection<?> c) {
                return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.m84in(c))));
            }
        }

        /* access modifiers changed from: package-private */
        public Collection<V> createValues() {
            return new Values();
        }

        private class Values extends Maps.Values<R, V> {
            Values() {
                super(Column.this);
            }

            public boolean remove(Object obj) {
                return obj != null && Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(obj)));
            }

            public boolean removeAll(Collection<?> c) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.m84in(c)));
            }

            public boolean retainAll(Collection<?> c) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.m84in(c))));
            }
        }
    }

    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    public Set<C> columnKeySet() {
        Set<C> result = this.columnKeySet;
        if (result != null) {
            return result;
        }
        ColumnKeySet columnKeySet2 = new ColumnKeySet();
        this.columnKeySet = columnKeySet2;
        return columnKeySet2;
    }

    private class ColumnKeySet extends StandardTable<R, C, V>.TableSet<C> {
        private ColumnKeySet() {
            super();
        }

        public Iterator<C> iterator() {
            return StandardTable.this.createColumnKeyIterator();
        }

        public int size() {
            return Iterators.size(iterator());
        }

        public boolean remove(Object obj) {
            if (obj == null) {
                return false;
            }
            boolean changed = false;
            Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
            while (iterator.hasNext()) {
                Map<C, V> map = iterator.next();
                if (map.keySet().remove(obj)) {
                    changed = true;
                    if (map.isEmpty()) {
                        iterator.remove();
                    }
                }
            }
            return changed;
        }

        public boolean removeAll(Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
            while (iterator.hasNext()) {
                Map<C, V> map = iterator.next();
                if (Iterators.removeAll(map.keySet().iterator(), c)) {
                    changed = true;
                    if (map.isEmpty()) {
                        iterator.remove();
                    }
                }
            }
            return changed;
        }

        public boolean retainAll(Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
            while (iterator.hasNext()) {
                Map<C, V> map = iterator.next();
                if (map.keySet().retainAll(c)) {
                    changed = true;
                    if (map.isEmpty()) {
                        iterator.remove();
                    }
                }
            }
            return changed;
        }

        public boolean contains(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }
    }

    /* access modifiers changed from: package-private */
    public Iterator<C> createColumnKeyIterator() {
        return new ColumnKeyIterator();
    }

    private class ColumnKeyIterator extends AbstractIterator<C> {
        Iterator<Map.Entry<C, V>> entryIterator;
        final Iterator<Map<C, V>> mapIterator;
        final Map<C, V> seen;

        private ColumnKeyIterator() {
            this.seen = (Map) StandardTable.this.factory.get();
            this.mapIterator = StandardTable.this.backingMap.values().iterator();
            this.entryIterator = Iterators.emptyIterator();
        }

        /* access modifiers changed from: protected */
        public C computeNext() {
            while (true) {
                if (this.entryIterator.hasNext()) {
                    Map.Entry<C, V> entry = this.entryIterator.next();
                    if (!this.seen.containsKey(entry.getKey())) {
                        this.seen.put(entry.getKey(), entry.getValue());
                        return entry.getKey();
                    }
                } else if (!this.mapIterator.hasNext()) {
                    return endOfData();
                } else {
                    this.entryIterator = this.mapIterator.next().entrySet().iterator();
                }
            }
        }
    }

    public Collection<V> values() {
        return super.values();
    }

    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> result = this.rowMap;
        if (result != null) {
            return result;
        }
        Map<R, Map<C, V>> createRowMap = createRowMap();
        this.rowMap = createRowMap;
        return createRowMap;
    }

    /* access modifiers changed from: package-private */
    public Map<R, Map<C, V>> createRowMap() {
        return new RowMap();
    }

    class RowMap extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {
        RowMap() {
        }

        public boolean containsKey(Object key) {
            return StandardTable.this.containsRow(key);
        }

        public Map<C, V> get(Object key) {
            if (StandardTable.this.containsRow(key)) {
                return StandardTable.this.row(key);
            }
            return null;
        }

        public Map<C, V> remove(Object key) {
            if (key == null) {
                return null;
            }
            return StandardTable.this.backingMap.remove(key);
        }

        /* access modifiers changed from: protected */
        public Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet();
        }

        class EntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<R, Map<C, V>>> {
            EntrySet() {
                super();
            }

            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function<R, Map<C, V>>() {
                    public Map<C, V> apply(R rowKey) {
                        return StandardTable.this.row(rowKey);
                    }
                });
            }

            public int size() {
                return StandardTable.this.backingMap.size();
            }

            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                if (entry.getKey() == null || !(entry.getValue() instanceof Map) || !Collections2.safeContains(StandardTable.this.backingMap.entrySet(), entry)) {
                    return false;
                }
                return true;
            }

            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                if (entry.getKey() == null || !(entry.getValue() instanceof Map) || !StandardTable.this.backingMap.entrySet().remove(entry)) {
                    return false;
                }
                return true;
            }
        }
    }

    public Map<C, Map<R, V>> columnMap() {
        StandardTable<R, C, V>.ColumnMap result = this.columnMap;
        if (result != null) {
            return result;
        }
        StandardTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.columnMap = columnMap2;
        return columnMap2;
    }

    private class ColumnMap extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {
        private ColumnMap() {
        }

        public Map<R, V> get(Object key) {
            if (StandardTable.this.containsColumn(key)) {
                return StandardTable.this.column(key);
            }
            return null;
        }

        public boolean containsKey(Object key) {
            return StandardTable.this.containsColumn(key);
        }

        public Map<R, V> remove(Object key) {
            if (StandardTable.this.containsColumn(key)) {
                return StandardTable.this.removeColumn(key);
            }
            return null;
        }

        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet();
        }

        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }

        /* access modifiers changed from: package-private */
        public Collection<Map<R, V>> createValues() {
            return new ColumnMapValues();
        }

        class ColumnMapEntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<C, Map<R, V>>> {
            ColumnMapEntrySet() {
                super();
            }

            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function<C, Map<R, V>>() {
                    public Map<R, V> apply(C columnKey) {
                        return StandardTable.this.column(columnKey);
                    }
                });
            }

            public int size() {
                return StandardTable.this.columnKeySet().size();
            }

            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                if (!StandardTable.this.containsColumn(entry.getKey())) {
                    return false;
                }
                return ColumnMap.this.get(entry.getKey()).equals(entry.getValue());
            }

            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                Map unused = StandardTable.this.removeColumn(((Map.Entry) obj).getKey());
                return true;
            }

            public boolean removeAll(Collection<?> c) {
                Preconditions.checkNotNull(c);
                return Sets.removeAllImpl(this, c.iterator());
            }

            public boolean retainAll(Collection<?> c) {
                Preconditions.checkNotNull(c);
                boolean changed = false;
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                while (it.hasNext()) {
                    C columnKey = it.next();
                    if (!c.contains(Maps.immutableEntry(columnKey, StandardTable.this.column(columnKey)))) {
                        Map unused = StandardTable.this.removeColumn(columnKey);
                        changed = true;
                    }
                }
                return changed;
            }
        }

        private class ColumnMapValues extends Maps.Values<C, Map<R, V>> {
            ColumnMapValues() {
                super(ColumnMap.this);
            }

            public boolean remove(Object obj) {
                for (Map.Entry<C, Map<R, V>> entry : ColumnMap.this.entrySet()) {
                    if (entry.getValue().equals(obj)) {
                        Map unused = StandardTable.this.removeColumn(entry.getKey());
                        return true;
                    }
                }
                return false;
            }

            public boolean removeAll(Collection<?> c) {
                Preconditions.checkNotNull(c);
                boolean changed = false;
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                while (it.hasNext()) {
                    C columnKey = it.next();
                    if (c.contains(StandardTable.this.column(columnKey))) {
                        Map unused = StandardTable.this.removeColumn(columnKey);
                        changed = true;
                    }
                }
                return changed;
            }

            public boolean retainAll(Collection<?> c) {
                Preconditions.checkNotNull(c);
                boolean changed = false;
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                while (it.hasNext()) {
                    C columnKey = it.next();
                    if (!c.contains(StandardTable.this.column(columnKey))) {
                        Map unused = StandardTable.this.removeColumn(columnKey);
                        changed = true;
                    }
                }
                return changed;
            }
        }
    }
}
