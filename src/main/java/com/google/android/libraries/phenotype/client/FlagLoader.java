package com.google.android.libraries.phenotype.client;

import android.support.annotation.Nullable;

interface FlagLoader {

    public interface BinderAwareFunction<V> {
        V execute();
    }

    @Nullable
    Object getFlag(String str);
}
