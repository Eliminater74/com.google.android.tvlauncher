package com.google.android.libraries.stitch.flags;

import android.support.annotation.Size;

public abstract class Flag {
    private final String name;

    protected Flag(@Size(max = 31) String name2) {
        this.name = name2;
    }

    /* access modifiers changed from: protected */
    public final String getName() {
        return this.name;
    }
}
