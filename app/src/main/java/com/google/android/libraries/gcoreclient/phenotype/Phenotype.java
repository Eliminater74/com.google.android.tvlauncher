package com.google.android.libraries.gcoreclient.phenotype;

import android.content.SharedPreferences;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;

public interface Phenotype {
    public static final int ERROR_STALE_SNAPSHOT_TOKEN = 29501;
    public static final String LOGGED_OUT_USER = "";

    public interface Factory {
        Phenotype create();
    }

    public interface GcoreConfigurationsResult extends GcoreResult {
        GcoreConfigurations getConfigurations();
    }

    GcorePendingResult<GcoreStatus> commitToConfiguration(GcoreGoogleApiClient gcoreGoogleApiClient, String str);

    GcorePendingResult<GcoreConfigurationsResult> getConfigurationSnapshot(GcoreGoogleApiClient gcoreGoogleApiClient, String str, String str2);

    GcorePendingResult<GcoreConfigurationsResult> getConfigurationSnapshot(GcoreGoogleApiClient gcoreGoogleApiClient, String str, String str2, String str3);

    GcorePendingResult<GcoreExperimentTokensResult> getExperiments(GcoreGoogleApiClient gcoreGoogleApiClient, String str);

    String getServerTokenFromPrefs(SharedPreferences sharedPreferences);

    String getSnapshotTokenFromPrefs(SharedPreferences sharedPreferences);

    GcorePendingResult<GcoreStatus> register(GcoreGoogleApiClient gcoreGoogleApiClient, String str, int i, String[] strArr, byte[] bArr);

    GcorePendingResult<GcoreConfigurationsResult> registerSync(GcoreGoogleApiClient gcoreGoogleApiClient, String str, int i, String[] strArr, byte[] bArr, String str2, String str3);

    GcorePendingResult<GcoreStatus> unRegister(GcoreGoogleApiClient gcoreGoogleApiClient, String str);

    GcorePendingResult<GcoreStatus> weakRegister(GcoreGoogleApiClient gcoreGoogleApiClient, String str, int i, String[] strArr, int[] iArr, byte[] bArr);

    void writeToSharedPrefs(SharedPreferences sharedPreferences, GcoreConfigurations gcoreConfigurations);
}
