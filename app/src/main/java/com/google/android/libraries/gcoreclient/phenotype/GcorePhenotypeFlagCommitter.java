package com.google.android.libraries.gcoreclient.phenotype;

public interface GcorePhenotypeFlagCommitter {

    boolean commitForUser(String str);

    void setTimeoutMillis(long j);

    public interface ConfigurationsHandler {
        void handleConfigurations(GcoreConfigurations gcoreConfigurations);
    }
}
