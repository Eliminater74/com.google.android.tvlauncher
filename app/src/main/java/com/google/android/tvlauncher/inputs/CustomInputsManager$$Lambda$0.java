package com.google.android.tvlauncher.inputs;

import com.google.android.tvlauncher.inputs.CustomInputsManager;
import java.util.List;

final /* synthetic */ class CustomInputsManager$$Lambda$0 implements CustomInputsManager.RefreshInputList.LoadedDataCallback {
    private final CustomInputsManager arg$1;

    CustomInputsManager$$Lambda$0(CustomInputsManager customInputsManager) {
        this.arg$1 = customInputsManager;
    }

    public void onDataLoaded(List list) {
        this.arg$1.lambda$new$0$CustomInputsManager(list);
    }
}
