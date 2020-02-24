package com.google.android.libraries.phenotype.client;

import android.content.SharedPreferences;

final /* synthetic */ class SharedPreferencesLoader$$Lambda$0 implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final SharedPreferencesLoader arg$1;

    SharedPreferencesLoader$$Lambda$0(SharedPreferencesLoader sharedPreferencesLoader) {
        this.arg$1 = sharedPreferencesLoader;
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        this.arg$1.lambda$new$0$SharedPreferencesLoader(sharedPreferences, str);
    }
}
