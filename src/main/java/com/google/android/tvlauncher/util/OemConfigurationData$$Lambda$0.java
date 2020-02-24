package com.google.android.tvlauncher.util;

import com.google.android.tvlauncher.util.OemConfigurationData;

final /* synthetic */ class OemConfigurationData$$Lambda$0 implements Runnable {
    private final OemConfigurationData arg$1;
    private final OemConfigurationData.OemConfigurationDataLoadingTask arg$2;

    OemConfigurationData$$Lambda$0(OemConfigurationData oemConfigurationData, OemConfigurationData.OemConfigurationDataLoadingTask oemConfigurationDataLoadingTask) {
        this.arg$1 = oemConfigurationData;
        this.arg$2 = oemConfigurationDataLoadingTask;
    }

    public void run() {
        this.arg$1.lambda$loadData$0$OemConfigurationData(this.arg$2);
    }
}
