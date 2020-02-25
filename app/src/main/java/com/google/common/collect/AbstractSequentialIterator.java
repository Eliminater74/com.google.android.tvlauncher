package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.NoSuchElementException;

@GwtCompatible
public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {
    @NullableDecl
    private T nextOrNull;

    protected AbstractSequentialIterator(@NullableDecl T firstOrNull) {
        this.nextOrNull = firstOrNull;
    }

    /* access modifiers changed from: protected */
    @NullableDecl
    public abstract T computeNext(T t);

    public final boolean hasNext() {
        return this.nextOrNull != null;
    }

    public final T next() {
        if (hasNext()) {
            try {
                return this.nextOrNull;
            } finally {
                this.nextOrNull = computeNext(this.nextOrNull);
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}
