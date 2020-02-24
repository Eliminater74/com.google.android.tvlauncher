package com.google.android.libraries.performance.primes.leak;

import com.google.android.libraries.stitch.util.Preconditions;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public final class GarbageReference extends PhantomReference<Object> {
    final String name;
    GarbageReference next;
    GarbageReference prev;

    public GarbageReference(Object o, String name2, ReferenceQueue<? super Object> referenceQueue) {
        super(o, referenceQueue);
        this.name = name2;
    }

    public void insertAfter(GarbageReference ref) {
        Preconditions.checkNotNull(ref);
        this.prev = ref;
        this.next = ref.next;
        GarbageReference garbageReference = this.next;
        if (garbageReference != null) {
            garbageReference.prev = this;
        }
        ref.next = this;
    }

    public GarbageReference removeSelf() {
        GarbageReference garbageReference = this.prev;
        if (garbageReference != null) {
            garbageReference.next = this.next;
        }
        GarbageReference garbageReference2 = this.next;
        if (garbageReference2 != null) {
            garbageReference2.prev = this.prev;
        }
        this.next = null;
        this.prev = null;
        return this;
    }
}
