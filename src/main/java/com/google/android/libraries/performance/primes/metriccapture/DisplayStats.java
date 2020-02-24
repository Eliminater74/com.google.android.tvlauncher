package com.google.android.libraries.performance.primes.metriccapture;

import android.app.Application;
import android.view.WindowManager;

public final class DisplayStats {
    private static final int DEFAULT_REFRESH_RATE = 60;
    private static volatile int maxFrameRenderTimeMs;
    private static volatile int refreshRate;

    public static int maxAcceptedFrameRenderTimeMs(Application application) {
        if (maxFrameRenderTimeMs == 0) {
            synchronized (DisplayStats.class) {
                if (maxFrameRenderTimeMs == 0) {
                    int refreshRate2 = getRefreshRate(application);
                    if (refreshRate2 < 10 || refreshRate2 > 60) {
                        refreshRate2 = 60;
                    }
                    double d = (double) refreshRate2;
                    Double.isNaN(d);
                    maxFrameRenderTimeMs = (int) Math.ceil(1000.0d / d);
                }
            }
        }
        return maxFrameRenderTimeMs;
    }

    public static int getRefreshRate(Application application) {
        if (refreshRate == 0) {
            synchronized (DisplayStats.class) {
                if (refreshRate == 0) {
                    refreshRate = Math.round(((WindowManager) application.getApplicationContext().getSystemService("window")).getDefaultDisplay().getRefreshRate());
                }
            }
        }
        return refreshRate;
    }
}
