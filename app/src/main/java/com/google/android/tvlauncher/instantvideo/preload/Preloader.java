package com.google.android.tvlauncher.instantvideo.preload;

public abstract class Preloader {

    public abstract void startPreload(OnPreloadFinishedListener onPreloadFinishedListener);

    public abstract void stopPreload();

    public interface OnPreloadFinishedListener {
        void onPreloadFinishedListener();
    }
}
