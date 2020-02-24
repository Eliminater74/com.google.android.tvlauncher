package com.google.android.libraries.stitch.binder;

import java.util.Set;

public interface RootModule extends Module {
    void bindConstants(Binder binder);

    Set<String> getSourceModuleNames();
}
