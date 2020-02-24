package com.google.android.libraries.gcoreclient.phenotype;

public interface GcorePhenotypeFlagCommitter {

    public interface ConfigurationsHandler {
        void handleConfigurations(GcoreConfigurations gcoreConfigurations);
    }

    boolean commitForUser(String str);

    void setTimeoutMillis(long j);
}
