package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import java.util.Queue;

@GwtCompatible
public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E> {
    /* access modifiers changed from: protected */
    public abstract Queue<E> delegate();

    protected ForwardingQueue() {
    }

    @CanIgnoreReturnValue
    public boolean offer(E o) {
        return delegate().offer(o);
    }

    @CanIgnoreReturnValue
    public E poll() {
        return delegate().poll();
    }

    @CanIgnoreReturnValue
    public E remove() {
        return delegate().remove();
    }

    public E peek() {
        return delegate().peek();
    }

    public E element() {
        return delegate().element();
    }

    /* access modifiers changed from: protected */
    public boolean standardOffer(E e) {
        try {
            return add(e);
        } catch (IllegalStateException e2) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public E standardPeek() {
        try {
            return element();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public E standardPoll() {
        try {
            return remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
