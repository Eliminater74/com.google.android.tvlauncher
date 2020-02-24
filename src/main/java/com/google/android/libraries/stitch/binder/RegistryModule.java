package com.google.android.libraries.stitch.binder;

import android.content.Context;

@Deprecated
public final class RegistryModule implements Module {
    private final AndroidManifestModule delegate;

    public void configure(Context context, Class cls, Object obj, Binder binder) {
        Module$$CC.configure$$dflt$$(this, context, cls, obj, binder);
    }

    public RegistryModule(Context context) {
        this.delegate = new AndroidManifestModule(context);
    }

    public void configure(Context context, Class<?> type, Binder binder) {
        this.delegate.configure(context, type, binder);
    }
}
