package com.google.android.tvlauncher.home;

final /* synthetic */ class HomeController$$Lambda$7 implements Runnable {
    private final HomeController arg$1;
    private final long arg$2;

    HomeController$$Lambda$7(HomeController homeController, long j) {
        this.arg$1 = homeController;
        this.arg$2 = j;
    }

    public void run() {
        this.arg$1.lambda$setSelectedPosition$7$HomeController(this.arg$2);
    }
}
