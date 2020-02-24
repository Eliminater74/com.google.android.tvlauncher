package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
class FilteredEntryMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    final Predicate<? super Map.Entry<K, V>> predicate;
    final Multimap<K, V> unfiltered;

    FilteredEntryMultimap(Multimap<K, V> unfiltered2, Predicate<? super Map.Entry<K, V>> predicate2) {
        this.unfiltered = (Multimap) Preconditions.checkNotNull(unfiltered2);
        this.predicate = (Predicate) Preconditions.checkNotNull(predicate2);
    }

    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return this.predicate;
    }

    public int size() {
        return entries().size();
    }

    /* access modifiers changed from: private */
    public boolean satisfies(K key, V value) {
        return this.predicate.apply(Maps.immutableEntry(key, value));
    }

    final class ValuePredicate implements Predicate<V> {
        private final K key;

        ValuePredicate(K key2) {
            this.key = key2;
        }

        public boolean apply(@NullableDecl V value) {
            return FilteredEntryMultimap.this.satisfies(this.key, value);
        }
    }

    static <E> Collection<E> filterCollection(Collection<E> collection, Predicate<? super E> predicate2) {
        if (collection instanceof Set) {
            return Sets.filter((Set) collection, predicate2);
        }
        return Collections2.filter(collection, predicate2);
    }

    public boolean containsKey(@NullableDecl Object key) {
        return asMap().get(key) != null;
    }

    public Collection<V> removeAll(@NullableDecl Object key) {
        return (Collection) MoreObjects.firstNonNull((Collection) asMap().remove(key), unmodifiableEmptyCollection());
    }

    /* access modifiers changed from: package-private */
    public Collection<V> unmodifiableEmptyCollection() {
        if (this.unfiltered instanceof SetMultimap) {
            return Collections.emptySet();
        }
        return Collections.emptyList();
    }

    public void clear() {
        entries().clear();
    }

    public Collection<V> get(K key) {
        return filterCollection(this.unfiltered.get(key), new ValuePredicate(key));
    }

    /* access modifiers changed from: package-private */
    public Collection<Map.Entry<K, V>> createEntries() {
        return filterCollection(this.unfiltered.entries(), this.predicate);
    }

    /* access modifiers changed from: package-private */
    public Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError("should never be called");
    }

    /* access modifiers changed from: package-private */
    public Map<K, Collection<V>> createAsMap() {
        return new AsMap();
    }

    /* access modifiers changed from: package-private */
    public Set<K> createKeySet() {
        return asMap().keySet();
    }

    /* access modifiers changed from: package-private */
    public boolean removeEntriesIf(Predicate<? super Map.Entry<K, Collection<V>>> predicate2) {
        Iterator<Map.Entry<K, Collection<V>>> entryIterator = this.unfiltered.asMap().entrySet().iterator();
        boolean changed = false;
        while (entryIterator.hasNext()) {
            Map.Entry<K, Collection<V>> entry = entryIterator.next();
            K key = entry.getKey();
            Collection<V> collection = filterCollection(entry.getValue(), new ValuePredicate(key));
            if (!collection.isEmpty() && predicate2.apply(Maps.immutableEntry(key, collection))) {
                if (collection.size() == entry.getValue().size()) {
                    entryIterator.remove();
                } else {
                    collection.clear();
                }
                changed = true;
            }
        }
        return changed;
    }

    class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        AsMap() {
        }

        public boolean containsKey(@NullableDecl Object key) {
            return get(key) != null;
        }

        public void clear() {
            FilteredEntryMultimap.this.clear();
        }

        public Collection<V> get(@NullableDecl Object key) {
            Collection<V> result = FilteredEntryMultimap.this.unfiltered.asMap().get(key);
            if (result == null) {
                return null;
            }
            Collection<V> result2 = FilteredEntryMultimap.filterCollection(result, new ValuePredicate(key));
            if (result2.isEmpty()) {
                return null;
            }
            return result2;
        }

        public Collection<V> remove(@NullableDecl Object key) {
            Collection<V> collection = FilteredEntryMultimap.this.unfiltered.asMap().get(key);
            if (collection == null) {
                return null;
            }
            Object obj = key;
            List<V> result = Lists.newArrayList();
            Iterator<V> itr = collection.iterator();
            while (itr.hasNext()) {
                V v = itr.next();
                if (FilteredEntryMultimap.this.satisfies(obj, v)) {
                    itr.remove();
                    result.add(v);
                }
            }
            if (result.isEmpty()) {
                return null;
            }
            if (FilteredEntryMultimap.this.unfiltered instanceof SetMultimap) {
                return Collections.unmodifiableSet(Sets.newLinkedHashSet(result));
            }
            return Collections.unmodifiableList(result);
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return new Maps.KeySet<K, Collection<V>>() {
                public boolean removeAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.m84in(c)));
                }

                public boolean retainAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.m84in(c))));
                }

                public boolean remove(@NullableDecl Object o) {
                    return AsMap.this.remove(o) != null;
                }
            };
        }

        /* access modifiers changed from: package-private */
        public Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new Maps.EntrySet<K, Collection<V>>() {
                /* access modifiers changed from: package-private */
                public Map<K, Collection<V>> map() {
                    return AsMap.this;
                }

                public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                    return new AbstractIterator<Map.Entry<K, Collection<V>>>() {
                        final Iterator<Map.Entry<K, Collection<V>>> backingIterator = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();

                        /* access modifiers changed from: protected */
                        public Map.Entry<K, Collection<V>> computeNext() {
                            while (this.backingIterator.hasNext()) {
                                Map.Entry<K, Collection<V>> entry = this.backingIterator.next();
                                K key = entry.getKey();
                                Collection<V> collection = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(key));
                                if (!collection.isEmpty()) {
                                    return Maps.immutableEntry(key, collection);
                                }
                            }
                            return (Map.Entry) endOfData();
                        }
                    };
                }

                public boolean removeAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Predicates.m84in(c));
                }

                public boolean retainAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Predicates.not(Predicates.m84in(c)));
                }

                public int size() {
                    return Iterators.size(iterator());
                }
            };
        }

        /* access modifiers changed from: package-private */
        public Collection<Collection<V>> createValues() {
            return new Maps.Values<K, Collection<V>>() {
                public boolean remove(@NullableDecl Object o) {
                    if (!(o instanceof Collection)) {
                        return false;
                    }
                    Collection<?> c = (Collection) o;
                    Iterator<Map.Entry<K, Collection<V>>> entryIterator = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();
                    while (entryIterator.hasNext()) {
                        Map.Entry<K, Collection<V>> entry = entryIterator.next();
                        Collection<V> collection = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(entry.getKey()));
                        if (!collection.isEmpty() && c.equals(collection)) {
                            if (collection.size() == entry.getValue().size()) {
                                entryIterator.remove();
                                return true;
                            }
                            collection.clear();
                            return true;
                        }
                    }
                    return false;
                }

                public boolean removeAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.m84in(c)));
                }

                public boolean retainAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.m84in(c))));
                }
            };
        }
    }

    /* access modifiers changed from: package-private */
    public Multiset<K> createKeys() {
        return new Keys();
    }

    class Keys extends Multimaps.Keys<K, V> {
        Keys() {
            super(FilteredEntryMultimap.this);
        }

        public int remove(@NullableDecl Object key, int occurrences) {
            CollectPreconditions.checkNonnegative(occurrences, "occurrences");
            if (occurrences == 0) {
                return count(key);
            }
            Collection<V> collection = FilteredEntryMultimap.this.unfiltered.asMap().get(key);
            if (collection == null) {
                return 0;
            }
            Object obj = key;
            int oldCount = 0;
            Iterator<V> itr = collection.iterator();
            while (itr.hasNext()) {
                if (FilteredEntryMultimap.this.satisfies(obj, itr.next()) && (oldCount = oldCount + 1) <= occurrences) {
                    itr.remove();
                }
            }
            return oldCount;
        }

        public Set<Multiset.Entry<K>> entrySet() {
            return new Multisets.EntrySet<K>() {
                /* access modifiers changed from: package-private */
                public Multiset<K> multiset() {
                    return Keys.this;
                }

                public Iterator<Multiset.Entry<K>> iterator() {
                    return Keys.this.entryIterator();
                }

                public int size() {
                    return FilteredEntryMultimap.this.keySet().size();
                }

                private boolean removeEntriesIf(final Predicate<? super Multiset.Entry<K>> predicate) {
                    return FilteredEntryMultimap.this.removeEntriesIf(new Predicate<Map.Entry<K, Collection<V>>>(this) {
                        public boolean apply(Map.Entry<K, Collection<V>> entry) {
                            return predicate.apply(Multisets.immutableEntry(entry.getKey(), entry.getValue().size()));
                        }
                    });
                }

                public boolean removeAll(Collection<?> c) {
                    return removeEntriesIf(Predicates.m84in(c));
                }

                public boolean retainAll(Collection<?> c) {
                    return removeEntriesIf(Predicates.not(Predicates.m84in(c)));
                }
            };
        }
    }
}
