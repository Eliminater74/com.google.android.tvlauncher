package com.google.android.libraries.stitch.flags;

import android.support.annotation.Size;
import com.google.common.annotations.VisibleForTesting;

public final class DefaultFalseFlag extends Flag {
    private boolean defaultValue;

    public DefaultFalseFlag(@Size(max = 31) String name) {
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
