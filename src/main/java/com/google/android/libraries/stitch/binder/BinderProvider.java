package com.google.android.libraries.stitch.binder;

import android.content.Context;

public final class BinderProvider {
    private final boolean attachRootBinder;
    private volatile Binder binder;
    private final Object binderInitLock;
    private final Initializer initializer;

    public interface Initializer {
        void initBinder(Context context, Binder binder);
    }

    public BinderProvider(Initializer initializer2) {
        this(true, initializer2);
    }

    BinderProvider(boolean attachRootBinder2, Initializer initializer2) {
        this.binderInitLock = new Object();
        this.attachRootBinder = attachRootBinder2;
        this.initializer = initializer2;
    }

    public Binder get(Context appContext) {
        if (this.binder == null) {
            synchronized (this.binderInitLock) {
                if (this.binder == null) {
                    Binder binder2 = new Binder(appContext);
                    if (this.attachRootBinder) {
                        binder2.attachParent(Binder.getRootBinder(appContext));
                    }
                    if (this.initializer != null) {
                        this.initializer.initBinder(appContext, binder2);
                    }
                    this.binder = binder2;
                }
            }
        }
        return this.binder;
    }
}
