package com.google.android.tvlauncher;

import android.os.ConditionVariable;
import com.google.android.tvlauncher.util.LaunchOnBootCompletedHelper;

final /* synthetic */ class MainActivity$$Lambda$1 implements LaunchOnBootCompletedHelper.OnDataLoadCompleteListener {
    private final ConditionVariable arg$1;

    private MainActivity$$Lambda$1(ConditionVariable conditionVariable) {
        this.arg$1 = conditionVariable;
    }

    static LaunchOnBootCompletedHelper.OnDataLoadCompleteListener get$Lambda(ConditionVariable conditionVariable) {
        return new MainActivity$$Lambda$1(conditionVariable);
    }

    public void onDataLoadComplete() {
        this.arg$1.open();
    }
}
