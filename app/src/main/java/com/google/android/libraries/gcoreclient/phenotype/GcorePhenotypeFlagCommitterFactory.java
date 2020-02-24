package com.google.android.libraries.gcoreclient.phenotype;

import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.phenotype.GcorePhenotypeFlagCommitter;

public interface GcorePhenotypeFlagCommitterFactory {
    GcorePhenotypeFlagCommitter create(GcoreGoogleApiClient gcoreGoogleApiClient, GcorePhenotypeFlagCommitter.ConfigurationsHandler configurationsHandler, String str);
}
