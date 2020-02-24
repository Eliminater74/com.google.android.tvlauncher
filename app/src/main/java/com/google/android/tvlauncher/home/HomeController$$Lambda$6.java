package com.google.android.tvlauncher.home;

import com.google.android.tvlauncher.home.RecyclerViewFastScrollingManager;

final /* synthetic */ class HomeController$$Lambda$6 implements RecyclerViewFastScrollingManager.OnFastScrollingChangedListener {
    private final HomeController arg$1;

    HomeController$$Lambda$6(HomeController homeController) {
        this.arg$1 = homeController;
    }

    public void onFastScrollingChanged(boolean z) {
        this.arg$1.lambda$setList$6$HomeController(z);
    }
}
