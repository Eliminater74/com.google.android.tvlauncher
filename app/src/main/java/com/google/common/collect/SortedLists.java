package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
@Beta
final class SortedLists {

    enum KeyAbsentBehavior {
        NEXT_LOWER {
            /* access modifiers changed from: package-private */
            public int resultIndex(int higherIndex) {
                return higherIndex - 1;
            }
        },
        NEXT_HIGHER {
            public int resultIndex(int higherIndex) {
                return higherIndex;
            }
        },
        INVERTED_INSERTION_INDEX {
            public int resultIndex(int higherIndex) {
                return higherIndex ^ -1;
            }
        };

        /* access modifiers changed from: package-private */
        public abstract int resultIndex(int i);
    }

    enum KeyPresentBehavior {
        ANY_PRESENT {
            /* access modifiers changed from: package-private */
            public <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int foundIndex) {
                return foundIndex;
            }
        },
        LAST_PRESENT {
            /* access modifiers changed from: package-private */
            public <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
                int lower = foundIndex;
                int upper = list.size() - 1;
                while (lower < upper) {
                    int middle = ((lower + upper) + 1) >>> 1;
                    if (comparator.compare(list.get(middle), key) > 0) {
                        upper = middle - 1;
                    } else {
                        lower = middle;
                    }
                }
                return lower;
            }
        },
        FIRST_PRESENT {
            /* access modifiers changed from: package-private */
            public <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
                int lower = 0;
                int upper = foundIndex;
                while (lower < upper) {
                    int middle = (lower + upper) >>> 1;
                    if (comparator.compare(list.get(middle), key) < 0) {
                        lower = middle + 1;
                    } else {
                        upper = middle;
                    }
                }
                return lower;
            }
        },
        FIRST_AFTER {
            public <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
                return LAST_PRESENT.resultIndex(comparator, key, list, foundIndex) + 1;
            }
        },
        LAST_BEFORE {
            public <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
                return FIRST_PRESENT.resultIndex(comparator, key, list, foundIndex) - 1;
            }
        };

        /* access modifiers changed from: package-private */
        public abstract <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int i);
    }

    private SortedLists() {
    }

    public static <E extends Comparable> int binarySearch(List<? extends E> list, E e, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        Preconditions.checkNotNull(e);
        return binarySearch(list, e, Ordering.natural(), presentBehavior, absentBehavior);
    }

    public static <E, K extends Comparable> int binarySearch(List list, Function function, @NullableDecl Comparable comparable, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        return binarySearch(list, function, comparable, Ordering.natural(), presentBehavior, absentBehavior);
    }

    public static <E, K> int binarySearch(List<E> list, Function<? super E, K> keyFunction, @NullableDecl K key, Comparator<? super K> keyComparator, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        return binarySearch(Lists.transform(list, keyFunction), key, keyComparator, presentBehavior, absentBehavior);
    }

    public static <E> int binarySearch(List list, @NullableDecl Object obj, Comparator comparator, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(presentBehavior);
        Preconditions.checkNotNull(absentBehavior);
        if (!(list instanceof RandomAccess)) {
            list = Lists.newArrayList(list);
        }
        int lower = 0;
        int upper = list.size() - 1;
        while (lower <= upper) {
            int middle = (lower + upper) >>> 1;
            int c = comparator.compare(obj, list.get(middle));
            if (c < 0) {
                upper = middle - 1;
            } else if (c <= 0) {
                return presentBehavior.resultIndex(comparator, obj, list.subList(lower, upper + 1), middle - lower) + lower;
            } else {
                lower = middle + 1;
            }
        }
        return absentBehavior.resultIndex(lower);
    }
}
