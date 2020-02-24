package com.google.android.libraries.stitch.util;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

public final class RtlCompat {
    private RtlCompat() {
    }

    public static boolean isRtlSupported() {
        return Build.VERSION.SDK_INT >= 17;
    }

    @TargetApi(17)
    public static boolean isViewLayoutDirectionRtl(View view) {
        return isRtlSupported() && view.getLayoutDirection() == 1;
    }

    public static void setCompoundDrawables(TextView textView, int start, int top, int end, int bottom) {
        if (isRtlSupported()) {
            setCompoundApi17AndAfter(textView, start, top, end, bottom);
        } else {
            setCompoundApi16AndBefore(textView, start, top, end, bottom);
        }
    }

    private static void setCompoundApi16AndBefore(TextView textView, int start, int top, int end, int bottom) {
        textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
    }

    @TargetApi(17)
    private static void setCompoundApi17AndAfter(TextView textView, int start, int top, int end, int bottom) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @TargetApi(17)
    public static Drawable[] getCompoundDrawables(TextView textView) {
        if (isRtlSupported()) {
            return textView.getCompoundDrawablesRelative();
        }
        return textView.getCompoundDrawables();
    }

    public static int getStart(View view) {
        return isViewLayoutDirectionRtl(view) ? view.getRight() : view.getLeft();
    }

    public static int getEnd(View view) {
        return isViewLayoutDirectionRtl(view) ? view.getLeft() : view.getRight();
    }
}
