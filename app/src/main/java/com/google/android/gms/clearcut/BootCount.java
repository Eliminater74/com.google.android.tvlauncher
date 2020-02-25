package com.google.android.gms.clearcut;

import android.content.Context;

public final class BootCount {
    private BootCount() {
    }

    public static BootCountClient getInstance(Context context) {
        return new BootCountClient(context);
    }
}
