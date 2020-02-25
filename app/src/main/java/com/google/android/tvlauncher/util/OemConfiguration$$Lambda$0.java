package com.google.android.tvlauncher.util;

final /* synthetic */ class OemConfiguration$$Lambda$0 implements OemConfiguration.OnDataLoadedListener {
    static final OemConfiguration.OnDataLoadedListener $instance = new OemConfiguration$$Lambda$0();

    private OemConfiguration$$Lambda$0() {
    }

    public void onDataLoaded() {
        OemConfiguration.sOemConfiguration.onOemConfigurationFetched();
    }
}
