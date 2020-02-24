package androidx.leanback.animation;

import android.animation.TimeInterpolator;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class LogAccelerateInterpolator implements TimeInterpolator {
    int mBase;
    int mDrift;
    final float mLogScale = (1.0f / computeLog(1.0f, this.mBase, this.mDrift));

    public LogAccelerateInterpolator(int base, int drift) {
        this.mBase = base;
        this.mDrift = drift;
    }

    static float computeLog(float t, int base, int drift) {
        return ((float) (-Math.pow((double) base, (double) (-t)))) + 1.0f + (((float) drift) * t);
    }

    public float getInterpolation(float t) {
        return 1.0f - (computeLog(1.0f - t, this.mBase, this.mDrift) * this.mLogScale);
    }
}
