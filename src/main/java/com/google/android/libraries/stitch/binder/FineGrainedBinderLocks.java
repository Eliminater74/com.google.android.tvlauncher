package com.google.android.libraries.stitch.binder;

import java.util.concurrent.ConcurrentHashMap;

final class FineGrainedBinderLocks implements BinderLocks {
    private final ConcurrentHashMap<Object, Object> typeLocks = new ConcurrentHashMap<>();

    FineGrainedBinderLocks() {
    }

    public Object getLock(Object key) {
        Object result = this.typeLocks.get(key);
        if (result != null) {
            return result;
        }
        this.typeLocks.putIfAbsent(key, new Object());
        return this.typeLocks.get(key);
    }
}
