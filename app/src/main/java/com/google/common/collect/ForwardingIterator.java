package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.Iterator;

@GwtCompatible
public abstract class ForwardingIterator<T> extends ForwardingObject implements Iterator<T> {
    protected ForwardingIterator() {
    }

    /* access modifiers changed from: protected */
    public abstract Iterator<T> delegate();

    public boolean hasNext() {
        return delegate().hasNext();
    }

    @CanIgnoreReturnValue
    public T next() {
        return delegate().next();
    }

    public void remove() {
        delegate().remove();
    }
}
