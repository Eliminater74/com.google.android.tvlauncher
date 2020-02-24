package com.google.android.tvlauncher.doubleclick;

import android.content.Context;
import com.google.android.tvlauncher.doubleclick.vast.VastVideoTracker;

public class AdVideoTrackerFactory {
    public static AdVideoTracker createAdVideoTracker(Context context) {
        return new VastVideoTracker(context);
    }
}
