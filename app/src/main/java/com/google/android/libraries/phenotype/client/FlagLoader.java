package com.google.android.libraries.phenotype.client;

import android.support.annotation.Nullable;

interface FlagLoader {

    @Nullable
    Object getFlag(String str);

    public interface BinderAwareFunction<V> {
        V execute();
    }
}
