package com.google.android.libraries.gcoreclient.phenotype;

public interface GcoreConfiguration {
    String[] getDeleteFlags();

    int getFlagType();

    GcoreFlag[] getFlags();
}
