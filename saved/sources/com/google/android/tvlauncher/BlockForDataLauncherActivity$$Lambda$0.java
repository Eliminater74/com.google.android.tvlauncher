package com.google.android.tvlauncher;

import android.os.Bundle;
import com.google.android.tvlauncher.util.OemConfiguration;

final /* synthetic */ class BlockForDataLauncherActivity$$Lambda$0 implements OemConfiguration.OnDataLoadedListener {
    private final BlockForDataLauncherActivity arg$1;
    private final Bundle arg$2;

    BlockForDataLauncherActivity$$Lambda$0(BlockForDataLauncherActivity blockForDataLauncherActivity, Bundle bundle) {
        this.arg$1 = blockForDataLauncherActivity;
        this.arg$2 = bundle;
    }

    public void onDataLoaded() {
        this.arg$1.lambda$onCreate$0$BlockForDataLauncherActivity(this.arg$2);
    }
}
