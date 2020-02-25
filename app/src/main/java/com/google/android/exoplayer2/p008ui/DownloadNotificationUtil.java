package com.google.android.exoplayer2.p008ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.offline.Download;

import java.util.List;

@Deprecated
/* renamed from: com.google.android.exoplayer2.ui.DownloadNotificationUtil */
public final class DownloadNotificationUtil {
    private DownloadNotificationUtil() {
    }

    public static Notification buildProgressNotification(Context context, @DrawableRes int smallIcon, String channelId, @Nullable PendingIntent contentIntent, @Nullable String message, List<Download> downloads) {
        return new DownloadNotificationHelper(context, channelId).buildProgressNotification(smallIcon, contentIntent, message, downloads);
    }

    public static Notification buildDownloadCompletedNotification(Context context, @DrawableRes int smallIcon, String channelId, @Nullable PendingIntent contentIntent, @Nullable String message) {
        return new DownloadNotificationHelper(context, channelId).buildDownloadCompletedNotification(smallIcon, contentIntent, message);
    }

    public static Notification buildDownloadFailedNotification(Context context, @DrawableRes int smallIcon, String channelId, @Nullable PendingIntent contentIntent, @Nullable String message) {
        return new DownloadNotificationHelper(context, channelId).buildDownloadFailedNotification(smallIcon, contentIntent, message);
    }
}
