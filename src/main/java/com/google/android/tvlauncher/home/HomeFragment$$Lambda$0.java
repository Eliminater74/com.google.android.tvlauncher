package com.google.android.tvlauncher.home;

import com.google.android.tvlauncher.util.ExtendableTimer;

final /* synthetic */ class HomeFragment$$Lambda$0 implements ExtendableTimer.Listener {
    private final HomeFragment arg$1;

    HomeFragment$$Lambda$0(HomeFragment homeFragment) {
        this.arg$1 = homeFragment;
    }

    public void onTimerFired(ExtendableTimer extendableTimer) {
        this.arg$1.lambda$onCreate$0$HomeFragment(extendableTimer);
    }
}
