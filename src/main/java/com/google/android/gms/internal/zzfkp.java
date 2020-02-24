package com.google.android.gms.internal;

import java.util.ListIterator;

/* compiled from: UnmodifiableListIterator */
public abstract class zzfkp<E> extends zzfko<E> implements ListIterator<E> {
    protected zzfkp() {
    }

    @Deprecated
    public final void add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void set(E e) {
        throw new UnsupportedOperationException();
    }
}
