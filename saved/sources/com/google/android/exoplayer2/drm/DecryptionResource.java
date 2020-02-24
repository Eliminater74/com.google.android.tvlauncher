package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.drm.DecryptionResource;

public abstract class DecryptionResource<T extends DecryptionResource<T>> {
    private final Owner<T> owner;
    private int referenceCount = 0;

    public interface Owner<T extends DecryptionResource<T>> {
        void onLastReferenceReleased(T t);
    }

    public DecryptionResource(Owner<T> owner2) {
        this.owner = owner2;
    }

    public void acquireReference() {
        this.referenceCount++;
    }

    public void releaseReference() {
        int i = this.referenceCount - 1;
        this.referenceCount = i;
        if (i == 0) {
            this.owner.onLastReferenceReleased(this);
        } else if (this.referenceCount < 0) {
            throw new IllegalStateException("Illegal release of resource.");
        }
    }
}
