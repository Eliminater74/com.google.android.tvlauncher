package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

@GwtCompatible
@Beta
public final class EvictingQueue<E> extends ForwardingQueue<E> implements Serializable {
    private static final long serialVersionUID = 0;
    @VisibleForTesting
    final int maxSize;
    private final Queue<E> delegate;

    private EvictingQueue(int maxSize2) {
        Preconditions.checkArgument(maxSize2 >= 0, "maxSize (%s) must >= 0", maxSize2);
        this.delegate = new ArrayDeque(maxSize2);
        this.maxSize = maxSize2;
    }

    public static <E> EvictingQueue<E> create(int maxSize2) {
        return new EvictingQueue<>(maxSize2);
    }

    public int remainingCapacity() {
        return this.maxSize - size();
    }

    /* access modifiers changed from: protected */
    public Queue<E> delegate() {
        return this.delegate;
    }

    @CanIgnoreReturnValue
    public boolean offer(E e) {
        return add(e);
    }

    @CanIgnoreReturnValue
    public boolean add(E e) {
        Preconditions.checkNotNull(e);
        if (this.maxSize == 0) {
            return true;
        }
        if (size() == this.maxSize) {
            this.delegate.remove();
        }
        this.delegate.add(e);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        int size = collection.size();
        if (size < this.maxSize) {
            return standardAddAll(collection);
        }
        clear();
        return Iterables.addAll(this, Iterables.skip(collection, size - this.maxSize));
    }

    public boolean contains(Object object) {
        return delegate().contains(Preconditions.checkNotNull(object));
    }

    @CanIgnoreReturnValue
    public boolean remove(Object object) {
        return delegate().remove(Preconditions.checkNotNull(object));
    }
}
