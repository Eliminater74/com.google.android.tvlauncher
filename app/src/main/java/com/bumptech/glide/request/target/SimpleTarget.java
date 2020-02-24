package com.bumptech.glide.request.target;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

@Deprecated
public abstract class SimpleTarget<Z> extends BaseTarget<Z> {
    private final int height;
    private final int width;

    public SimpleTarget() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public SimpleTarget(int width2, int height2) {
        this.width = width2;
        this.height = height2;
    }

    public final void getSize(@NonNull SizeReadyCallback cb) {
        if (Util.isValidDimensions(this.width, this.height)) {
            cb.onSizeReady(this.width, this.height);
            return;
        }
        int i = this.width;
        int i2 = this.height;
        StringBuilder sb = new StringBuilder((int) ClientAnalytics.LogRequest.LogSource.ANDROID_DIALER_VALUE);
        sb.append("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: ");
        sb.append(i);
        sb.append(" and height: ");
        sb.append(i2);
        sb.append(", either provide dimensions in the constructor or call override()");
        throw new IllegalArgumentException(sb.toString());
    }

    public void removeCallback(@NonNull SizeReadyCallback cb) {
    }
}
