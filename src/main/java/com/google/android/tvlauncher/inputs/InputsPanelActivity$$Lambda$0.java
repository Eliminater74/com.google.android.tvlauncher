package com.google.android.tvlauncher.inputs;

final /* synthetic */ class InputsPanelActivity$$Lambda$0 implements Runnable {
    private final InputsPanelActivity arg$1;
    private final int arg$2;

    InputsPanelActivity$$Lambda$0(InputsPanelActivity inputsPanelActivity, int i) {
        this.arg$1 = inputsPanelActivity;
        this.arg$2 = i;
    }

    public void run() {
        this.arg$1.lambda$onInputClicked$0$InputsPanelActivity(this.arg$2);
    }
}
