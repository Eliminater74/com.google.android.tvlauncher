package com.google.android.libraries.gcoreclient.common.api;

public interface GcoreScope {

    public interface Builder {
        GcoreScope build();

        Builder setScopeUri(String str);
    }
}
