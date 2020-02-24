package com.google.android.tvlauncher.data;

public abstract class HomeChannelsObserver {
    public abstract void onChannelEmptyStatusChange(int i);

    public abstract void onChannelMove(int i, int i2);

    public abstract void onChannelsChange();

    public abstract void onEmptyChannelRemove(int i);
}
