package com.google.android.tvlauncher.settings;

import com.google.android.tvlauncher.settings.AppModel;
import java.util.Comparator;

final /* synthetic */ class ConfigureChannelsFragment$$Lambda$0 implements Comparator {
    private final ConfigureChannelsFragment arg$1;

    ConfigureChannelsFragment$$Lambda$0(ConfigureChannelsFragment configureChannelsFragment) {
        this.arg$1 = configureChannelsFragment;
    }

    public int compare(Object obj, Object obj2) {
        return this.arg$1.lambda$new$0$ConfigureChannelsFragment((AppModel.AppInfo) obj, (AppModel.AppInfo) obj2);
    }
}
