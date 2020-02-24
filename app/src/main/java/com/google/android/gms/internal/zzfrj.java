package com.google.android.gms.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* compiled from: ThrowableExtension */
final class zzfrj extends WeakReference<Throwable> {
    private final int zza;

    public zzfrj(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th != null) {
            this.zza = System.identityHashCode(th);
            return;
        }
        throw new NullPointerException("The referent cannot be null");
    }

    public final int hashCode() {
        return this.zza;
    }

    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzfrj zzfrj = (zzfrj) obj;
        if (this.zza == zzfrj.zza && get() == zzfrj.get()) {
            return true;
        }
        return false;
    }
}
