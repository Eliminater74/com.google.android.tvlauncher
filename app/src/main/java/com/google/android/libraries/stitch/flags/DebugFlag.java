package com.google.android.libraries.stitch.flags;

import android.support.annotation.Size;

import com.google.common.annotations.VisibleForTesting;

public final class DebugFlag extends Flag {
    private boolean defaultDebugValue;

    public DebugFlag(@Size(max = 31) String name) {
        this(name, true);
    }

    public DebugFlag(@Size(max = 31) String name, boolean defaultDebugValue2) {
        super(name);
        this.defaultDebugValue = defaultDebugValue2;
    }

    public boolean getDefaultDebugValue() {
        return this.defaultDebugValue;
    }

    @VisibleForTesting(productionVisibility = VisibleForTesting.Visibility.NONE)
    public void setForTesting(boolean defaultDebugValue2) {
        this.defaultDebugValue = defaultDebugValue2;
    }
}
