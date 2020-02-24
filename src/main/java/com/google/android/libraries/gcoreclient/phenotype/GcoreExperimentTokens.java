package com.google.android.libraries.gcoreclient.phenotype;

public interface GcoreExperimentTokens {
    byte[][] getAdditionalDirectExperimentTokens();

    byte[][] getAlwaysCrossExperimentTokens();

    byte[] getDirectExperimentToken();

    byte[][] getGaiaCrossExperimentTokens();

    byte[][] getOtherCrossExperimentTokens();

    byte[][] getPseudonymousCrossExperimentTokens();

    String getUser();

    int[] getWeakExperimentIds();
}
