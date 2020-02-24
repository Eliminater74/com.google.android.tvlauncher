package com.google.android.tvlauncher.inputs;

import android.app.Fragment;

final /* synthetic */ class InputsPanelActivity$$Lambda$2 implements Runnable {
    private final InputsPanelActivity arg$1;
    private final Fragment arg$2;

    InputsPanelActivity$$Lambda$2(InputsPanelActivity inputsPanelActivity, Fragment fragment) {
        this.arg$1 = inputsPanelActivity;
        this.arg$2 = fragment;
    }

    public void run() {
        this.arg$1.lambda$transitionOut$2$InputsPanelActivity(this.arg$2);
    }
}
