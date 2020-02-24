package com.google.android.libraries.performance.primes.tracing;

import java.util.AbstractList;

final class NoopList<E> extends AbstractList<E> {
    private static final NoopList NOOP_LIST = new NoopList();

    NoopList() {
    }

    static <E> NoopList<E> getInstance() {
        return NOOP_LIST;
    }

    public int size() {
        return 0;
    }

    public boolean add(E e) {
        return true;
    }

    public E get(int i) {
        return null;
    }
}
