package com.google.android.libraries.gcoreclient.phenotype;

import java.util.Map;

public interface GcoreConfigurations {
    Map<Integer, GcoreConfiguration> getConfigurationMap();

    GcoreConfiguration[] getConfigurations();

    byte[] getExperimentToken();

    String getServerToken();

    String getSnapshotToken();
}
