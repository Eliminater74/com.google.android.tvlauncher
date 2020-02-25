package com.google.android.gms.phenotype;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

@Deprecated
public interface PhenotypeApi {

    PendingResult<Status> commitToConfiguration(GoogleApiClient googleApiClient, String str);

    PendingResult<FlagOverridesResult> deleteFlagOverrides(GoogleApiClient googleApiClient, String str, String str2, String str3);

    PendingResult<ConfigurationsResult> getAlternateConfigurationSnapshot(GoogleApiClient googleApiClient, String str, String str2, String str3, String str4);

    PendingResult<ConfigurationsResult> getCommittedConfiguration(GoogleApiClient googleApiClient, String str);

    @Deprecated
    PendingResult<ConfigurationsResult> getConfigurationSnapshot(GoogleApiClient googleApiClient, String str, String str2);

    PendingResult<ConfigurationsResult> getConfigurationSnapshot(GoogleApiClient googleApiClient, String str, String str2, String str3);

    PendingResult<DogfoodsTokenResult> getDogfoodsToken(GoogleApiClient googleApiClient);

    PendingResult<ExperimentTokensResult> getExperiments(GoogleApiClient googleApiClient, String str);

    PendingResult<ExperimentTokensResult> getExperimentsForLogging(GoogleApiClient googleApiClient, String str);

    PendingResult<FlagResult> getFlag(GoogleApiClient googleApiClient, String str, String str2, int i);

    PendingResult<FlagOverridesResult> listFlagOverrides(GoogleApiClient googleApiClient, String str, String str2, String str3);

    PendingResult<Status> register(GoogleApiClient googleApiClient, String str, int i, String[] strArr, byte[] bArr);

    PendingResult<ConfigurationsResult> registerSync(GoogleApiClient googleApiClient, String str, int i, String[] strArr, byte[] bArr, String str2, String str3);

    PendingResult<Status> setDogfoodsToken(GoogleApiClient googleApiClient, byte[] bArr);

    PendingResult<Status> setFlagOverride(GoogleApiClient googleApiClient, String str, String str2, String str3, int i, int i2, String str4);

    PendingResult<Status> syncAfter(GoogleApiClient googleApiClient, ServingVersion servingVersion);

    PendingResult<Status> unRegister(GoogleApiClient googleApiClient, String str);

    PendingResult<Status> weakRegister(GoogleApiClient googleApiClient, String str, int i, String[] strArr, int[] iArr, byte[] bArr);

    public interface ConfigurationsResult extends Result {
        Configurations getConfigurations();
    }

    public interface DogfoodsTokenResult extends Result {
        DogfoodsToken getDogfoodsToken();
    }

    public interface ExperimentTokensResult extends Result {
        ExperimentTokens getExperimentTokens();
    }

    public interface FlagOverridesResult extends Result {
        FlagOverrides getFlagOverrides();
    }

    public interface FlagResult extends Result {
        Flag getFlag();
    }
}
