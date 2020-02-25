package com.google.android.tvlauncher.util;

final /* synthetic */ class LaunchOnBootCompletedHelper$$Lambda$0 implements LaunchOnBootCompletedHelper.SetLoadedDataCallback {
    private final LaunchOnBootCompletedHelper arg$1;

    LaunchOnBootCompletedHelper$$Lambda$0(LaunchOnBootCompletedHelper launchOnBootCompletedHelper) {
        this.arg$1 = launchOnBootCompletedHelper;
    }

    public void setLoadedData(boolean z, boolean z2, boolean z3, String str) {
        this.arg$1.lambda$loadLaunchOnBootFlagsAsync$0$LaunchOnBootCompletedHelper(z, z2, z3, str);
    }
}
