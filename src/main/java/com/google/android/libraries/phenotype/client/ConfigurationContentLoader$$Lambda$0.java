package com.google.android.libraries.phenotype.client;

import com.google.android.libraries.phenotype.client.FlagLoader;

final /* synthetic */ class ConfigurationContentLoader$$Lambda$0 implements FlagLoader.BinderAwareFunction {
    private final ConfigurationContentLoader arg$1;

    ConfigurationContentLoader$$Lambda$0(ConfigurationContentLoader configurationContentLoader) {
        this.arg$1 = configurationContentLoader;
    }

    public Object execute() {
        return this.arg$1.lambda$readFlagsFromContentProvider$0$ConfigurationContentLoader();
    }
}
