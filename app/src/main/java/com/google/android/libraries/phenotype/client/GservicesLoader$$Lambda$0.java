package com.google.android.libraries.phenotype.client;

import com.google.android.libraries.phenotype.client.FlagLoader;

final /* synthetic */ class GservicesLoader$$Lambda$0 implements FlagLoader.BinderAwareFunction {
    private final GservicesLoader arg$1;
    private final String arg$2;

    GservicesLoader$$Lambda$0(GservicesLoader gservicesLoader, String str) {
        this.arg$1 = gservicesLoader;
        this.arg$2 = str;
    }

    public Object execute() {
        return this.arg$1.lambda$getFlag$0$GservicesLoader(this.arg$2);
    }
}
