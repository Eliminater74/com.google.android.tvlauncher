package com.google.android.libraries.stitch.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.ClipboardManager;
import android.view.View;

public final class PlatformUtils {
    private static boolean isRunningLReleaseOrLater() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean canSetAlpha() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean canUseViewPropertyAnimator() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean canCancelViewPropertyAnimation() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean canAnimateWithLayer() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean hasOverflowMenu() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean canSetSystemUiVisibility() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean isFullscreenSupported() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean isClipDataSupported() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean isTranslucentStatusSupported() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean canUseSharedElementTransition() {
        return isRunningLReleaseOrLater();
    }

    public static boolean canUseElevationAnimation() {
        return isRunningLReleaseOrLater();
    }

    public static boolean canUseRippleDrawable() {
        return isRunningLReleaseOrLater();
    }

    public static boolean supportsMaterialDesign() {
        return isRunningLReleaseOrLater();
    }

    public static boolean canSetPrivateNotification() {
        return isRunningLReleaseOrLater();
    }

    @TargetApi(19)
    public static boolean isLowRamDevice(Context context) {
        return Build.VERSION.SDK_INT < 11 || (Build.VERSION.SDK_INT >= 19 && ((ActivityManager) context.getSystemService("activity")).isLowRamDevice());
    }

    public static boolean canGetAudioSessionId() {
        return Build.VERSION.SDK_INT >= 18;
    }

    @TargetApi(11)
    public static boolean supportsClipPath(View view) {
        return Build.VERSION.SDK_INT <= 10 || !view.isHardwareAccelerated() || Build.VERSION.SDK_INT >= 18;
    }

    @TargetApi(11)
    public static void setAlphaIfSupported(View view, float alpha) {
        if (canSetAlpha()) {
            view.setAlpha(alpha);
        }
    }

    @SuppressLint({"NewApi"})
    public static void copyTextToClipboard(Context context, CharSequence text) {
        if (Build.VERSION.SDK_INT < 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setText(text);
        } else {
            ((android.content.ClipboardManager) context.getSystemService("clipboard")).setText(text);
        }
    }
}
