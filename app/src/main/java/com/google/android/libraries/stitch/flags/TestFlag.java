package com.google.android.libraries.stitch.flags;

import android.support.annotation.Size;
import com.google.common.annotations.VisibleForTesting;

public final class TestFlag extends Flag {
    private boolean defaultTestValue;

    public TestFlag(@Size(max = 31) String name) {
        this(name, true);
    }

    public TestFlag(@Size(max = 31) String name, boolean defaultTestValue2) {
        super(name);
        this.defaultTestValue = defaultTestValue2;
    }

    public boolean getDefaultTestValue() {
        return this.defaultTestValue;
    }

    @VisibleForTesting(productionVisibility = VisibleForTesting.Visibility.NONE)
    public void setForTesting(boolean defaultTestValue2) {
        this.defaultTestValue = defaultTestValue2;
    }
}
