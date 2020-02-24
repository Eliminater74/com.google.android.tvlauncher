package com.google.android.libraries.stitch.binder;

import android.content.Context;

public interface Module {
    void configure(Context context, Class<?> cls, Binder binder);

    void configure(Context context, Class<?> cls, Object obj, Binder binder);
}
