package com.google.android.gms.phenotype;

import android.content.SharedPreferences;

import com.google.android.gms.common.api.GoogleApiClient;

public class PhenotypeFlagSharedPrefsCommitter extends PhenotypeFlagCommitter {
    private final SharedPreferences zza;

    @Deprecated
    public PhenotypeFlagSharedPrefsCommitter(GoogleApiClient googleApiClient, String str, SharedPreferences sharedPreferences) {
        super(googleApiClient, str);
        this.zza = sharedPreferences;
    }

    @Deprecated
    public PhenotypeFlagSharedPrefsCommitter(GoogleApiClient googleApiClient, PhenotypeApi phenotypeApi, String str, SharedPreferences sharedPreferences) {
        super(googleApiClient, phenotypeApi, str);
        this.zza = sharedPreferences;
    }

    public PhenotypeFlagSharedPrefsCommitter(PhenotypeClient phenotypeClient, String str, SharedPreferences sharedPreferences) {
        super(phenotypeClient, str);
        this.zza = sharedPreferences;
    }

    /* access modifiers changed from: protected */
    public void handleConfigurations(Configurations configurations) {
        writeToSharedPrefs(this.zza, configurations);
    }

    /* access modifiers changed from: protected */
    public String getSnapshotToken() {
        return this.zza.getString("__phenotype_snapshot_token", null);
    }
}
