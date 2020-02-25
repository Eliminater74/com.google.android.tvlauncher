package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;

class EngineResource<Z> implements Resource<Z> {
    private final boolean isMemoryCacheable;
    private final boolean isRecyclable;
    private final Key key;
    private final ResourceListener listener;
    private final Resource<Z> resource;
    private int acquired;
    private boolean isRecycled;

    EngineResource(Resource<Z> toWrap, boolean isMemoryCacheable2, boolean isRecyclable2, Key key2, ResourceListener listener2) {
        this.resource = (Resource) Preconditions.checkNotNull(toWrap);
        this.isMemoryCacheable = isMemoryCacheable2;
        this.isRecyclable = isRecyclable2;
        this.key = key2;
        this.listener = (ResourceListener) Preconditions.checkNotNull(listener2);
    }

    /* access modifiers changed from: package-private */
    public Resource<Z> getResource() {
        return this.resource;
    }

    /* access modifiers changed from: package-private */
    public boolean isMemoryCacheable() {
        return this.isMemoryCacheable;
    }

    @NonNull
    public Class<Z> getResourceClass() {
        return this.resource.getResourceClass();
    }

    @NonNull
    public Z get() {
        return this.resource.get();
    }

    public int getSize() {
        return this.resource.getSize();
    }

    public synchronized void recycle() {
        if (this.acquired > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        } else if (!this.isRecycled) {
            this.isRecycled = true;
            if (this.isRecyclable) {
                this.resource.recycle();
            }
        } else {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void acquire() {
        if (!this.isRecycled) {
            this.acquired++;
        } else {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        }
    }

    /* access modifiers changed from: package-private */
    public void release() {
        synchronized (this.listener) {
            synchronized (this) {
                if (this.acquired > 0) {
                    int i = this.acquired - 1;
                    this.acquired = i;
                    if (i == 0) {
                        this.listener.onResourceReleased(this.key, this);
                    }
                } else {
                    throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
                }
            }
        }
    }

    public synchronized String toString() {
        StringBuilder sb;
        boolean z = this.isMemoryCacheable;
        String valueOf = String.valueOf(this.listener);
        String valueOf2 = String.valueOf(this.key);
        int i = this.acquired;
        boolean z2 = this.isRecycled;
        String valueOf3 = String.valueOf(this.resource);
        sb = new StringBuilder(String.valueOf(valueOf).length() + 107 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append("EngineResource{isMemoryCacheable=");
        sb.append(z);
        sb.append(", listener=");
        sb.append(valueOf);
        sb.append(", key=");
        sb.append(valueOf2);
        sb.append(", acquired=");
        sb.append(i);
        sb.append(", isRecycled=");
        sb.append(z2);
        sb.append(", resource=");
        sb.append(valueOf3);
        sb.append('}');
        return sb.toString();
    }

    interface ResourceListener {
        void onResourceReleased(Key key, EngineResource<?> engineResource);
    }
}
