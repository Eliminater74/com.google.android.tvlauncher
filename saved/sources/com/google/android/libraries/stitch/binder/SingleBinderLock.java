package com.google.android.libraries.stitch.binder;

final class SingleBinderLock implements BinderLocks {
    private final Object lock = new Object();

    SingleBinderLock() {
    }

    public Object getLock(Object key) {
        return this.lock;
    }
}
