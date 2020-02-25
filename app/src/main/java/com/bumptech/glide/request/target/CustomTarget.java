package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.util.Util;

public abstract class CustomTarget<T> implements Target<T> {
    private final int height;
    private final int width;
    @Nullable
    private Request request;

    public CustomTarget() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public CustomTarget(int width2, int height2) {
        if (Util.isValidDimensions(width2, height2)) {
            this.width = width2;
            this.height = height2;
            return;
        }
        StringBuilder sb = new StringBuilder(111);
        sb.append("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: ");
        sb.append(width2);
        sb.append(" and height: ");
        sb.append(height2);
        throw new IllegalArgumentException(sb.toString());
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onLoadStarted(@Nullable Drawable placeholder) {
    }

    public void onLoadFailed(@Nullable Drawable errorDrawable) {
    }

    public final void getSize(@NonNull SizeReadyCallback cb) {
        cb.onSizeReady(this.width, this.height);
    }

    public final void removeCallback(@NonNull SizeReadyCallback cb) {
    }

    @Nullable
    public final Request getRequest() {
        return this.request;
    }

    public final void setRequest(@Nullable Request request2) {
        this.request = request2;
    }
}
