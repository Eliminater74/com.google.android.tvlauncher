package com.google.android.libraries.stitch.flags;

import android.support.annotation.Size;

import com.google.common.annotations.VisibleForTesting;

public final class DefaultTrueFlag extends Flag {
    private boolean defaultValue = true;

    public DefaultTrueFlag(@Size(max = 31) String name) {
        super(name);
    }

    public boolean getDefaultValue() {
        return this.defaultValue;
    }

    @VisibleForTesting(productionVisibility = VisibleForTesting.Visibility.NONE)
    public void setForTesting(boolean defaultValue2) {
        this.defaultValue = defaultValue2;
    }
}
