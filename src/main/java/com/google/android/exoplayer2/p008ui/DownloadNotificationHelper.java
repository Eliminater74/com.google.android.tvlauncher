package com.google.android.exoplayer2.p008ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p001v4.app.NotificationCompat;
import com.google.android.exoplayer2.offline.Download;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.DownloadNotificationHelper */
public final class DownloadNotificationHelper {
    @StringRes
    private static final int NULL_STRING_ID = 0;
    private final Context context;
    private final NotificationCompat.Builder notificationBuilder;

    public DownloadNotificationHelper(Context context2, String channelId) {
        Context context3 = context2.getApplicationContext();
        this.context = context3;
        this.notificationBuilder = new NotificationCompat.Builder(context3, channelId);
    }

    public Notification buildProgressNotification(@DrawableRes int smallIcon, @Nullable PendingIntent contentIntent, @Nullable String message, List<Download> downloads) {
        boolean indeterminate;
        int titleStringId;
        float totalPercentage = 0.0f;
        int downloadTaskCount = 0;
        boolean allDownloadPercentagesUnknown = true;
        boolean haveDownloadedBytes = false;
        boolean haveDownloadTasks = false;
        boolean haveRemoveTasks = false;
        int i = 0;
        while (true) {
            indeterminate = false;
            if (i >= downloads.size()) {
                break;
            }
            Download download = downloads.get(i);
            if (download.state == 5) {
                haveRemoveTasks = true;
            } else if (download.state == 7 || download.state == 2) {
                haveDownloadTasks = true;
                float downloadPercentage = download.getPercentDownloaded();
                if (downloadPercentage != -1.0f) {
                    allDownloadPercentagesUnknown = false;
                    totalPercentage += downloadPercentage;
                }
                if (download.getBytesDownloaded() > 0) {
                    indeterminate = true;
                }
                haveDownloadedBytes |= indeterminate;
                downloadTaskCount++;
            }
            i++;
        }
        if (haveDownloadTasks) {
            titleStringId = C0931R.string.exo_download_downloading;
        } else {
            titleStringId = haveRemoveTasks ? C0931R.string.exo_download_removing : 0;
        }
        int progress = 0;
        if (haveDownloadTasks) {
            progress = (int) (totalPercentage / ((float) downloadTaskCount));
            if (allDownloadPercentagesUnknown && haveDownloadedBytes) {
                indeterminate = true;
            }
        } else {
            indeterminate = true;
        }
        return buildNotification(smallIcon, contentIntent, message, titleStringId, 100, progress, indeterminate, true, false);
    }

    public Notification buildDownloadCompletedNotification(@DrawableRes int smallIcon, @Nullable PendingIntent contentIntent, @Nullable String message) {
        return buildEndStateNotification(smallIcon, contentIntent, message, C0931R.string.exo_download_completed);
    }

    public Notification buildDownloadFailedNotification(@DrawableRes int smallIcon, @Nullable PendingIntent contentIntent, @Nullable String message) {
        return buildEndStateNotification(smallIcon, contentIntent, message, C0931R.string.exo_download_failed);
    }

    private Notification buildEndStateNotification(@DrawableRes int smallIcon, @Nullable PendingIntent contentIntent, @Nullable String message, @StringRes int titleStringId) {
        return buildNotification(smallIcon, contentIntent, message, titleStringId, 0, 0, false, false, true);
    }

    private Notification buildNotification(@DrawableRes int smallIcon, @Nullable PendingIntent contentIntent, @Nullable String message, @StringRes int titleStringId, int maxProgress, int currentProgress, boolean indeterminateProgress, boolean ongoing, boolean showWhen) {
        this.notificationBuilder.setSmallIcon(smallIcon);
        NotificationCompat.BigTextStyle bigTextStyle = null;
        this.notificationBuilder.setContentTitle(titleStringId == 0 ? null : this.context.getResources().getString(titleStringId));
        this.notificationBuilder.setContentIntent(contentIntent);
        NotificationCompat.Builder builder = this.notificationBuilder;
        if (message != null) {
            bigTextStyle = new NotificationCompat.BigTextStyle().bigText(message);
        }
        builder.setStyle(bigTextStyle);
        this.notificationBuilder.setProgress(maxProgress, currentProgress, indeterminateProgress);
        this.notificationBuilder.setOngoing(ongoing);
        this.notificationBuilder.setShowWhen(showWhen);
        return this.notificationBuilder.build();
    }
}
