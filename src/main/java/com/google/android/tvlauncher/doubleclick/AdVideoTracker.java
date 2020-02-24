package com.google.android.tvlauncher.doubleclick;

import com.google.android.tvlauncher.doubleclick.VideoProgressPoller;
import java.util.List;
import javax.annotation.Nonnull;

public abstract class AdVideoTracker implements VideoProgressPoller.OnVideoProgressUpdateListener {
    public abstract void onVideoProgressUpdate(long j);

    public abstract void resetTracking(String str, @Nonnull List<TrackingUrl> list);
}
