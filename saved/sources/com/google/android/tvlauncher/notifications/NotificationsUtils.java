package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.annotation.VisibleForTesting;
import com.google.android.tvrecommendations.shared.util.Constants;
import java.util.List;

public class NotificationsUtils {
    @VisibleForTesting(otherwise = 4)
    public static void dismissNotification(Context context, String key) {
        Intent dismiss = getIntent(context, NotificationsContract.ACTION_DISMISS_NOTIFICATION, "android.intent.action.DELETE");
        dismiss.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        dismiss.putExtra("sbn_key", key);
        context.sendBroadcast(dismiss);
    }

    @VisibleForTesting(otherwise = 4)
    public static void openNotification(Context context, String key) {
        Intent open = getIntent(context, NotificationsContract.ACTION_OPEN_NOTIFICATION, "android.intent.action.VIEW");
        open.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        open.putExtra("sbn_key", key);
        context.sendBroadcast(open);
    }

    @VisibleForTesting(otherwise = 4)
    public static void hideNotification(Context context, String key) {
        Intent mark = getIntent(context, NotificationsContract.ACTION_NOTIFICATION_HIDE, NotificationsContract.ACTION_NOTIFICATION_HIDE_ORIGINAL);
        mark.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        mark.putExtra("sbn_key", key);
        context.sendBroadcast(mark);
    }

    public static void showUnshownNotifications(Context context) {
        Intent show = getIntent(context, NotificationsContract.ACTION_SHOW_UNSHOWN_NOTIFICATIONS, NotificationsContract.ACTION_SHOW_UNSHOWN_NOTIFICATIONS_ORIGINAL);
        show.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        context.sendBroadcast(show);
    }

    @VisibleForTesting(otherwise = 4)
    public static void openNotificationPanel(Context context) {
        Intent openPanel = new Intent(NotificationsContract.ACTION_OPEN_NOTIFICATIONS_PANEL);
        openPanel.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        List<ResolveInfo> infos = context.getPackageManager().queryIntentActivities(openPanel, 0);
        if (infos == null || infos.size() <= 0) {
            context.startActivity(new Intent(context, NotificationsSidePanelActivity.class));
        } else {
            context.startActivity(openPanel);
        }
    }

    private static Intent getIntent(Context context, String intentAction, String defaultAction) {
        Intent action = new Intent(intentAction);
        List<ResolveInfo> infos = context.getPackageManager().queryBroadcastReceivers(action, 0);
        if (infos == null || infos.size() <= 0) {
            return new Intent(defaultAction);
        }
        return action;
    }

    public static boolean hasNowPlaying(Cursor cursor) {
        if (cursor == null || cursor.isClosed() || !cursor.moveToFirst()) {
            return false;
        }
        while (!NotificationsContract.NOW_PLAYING_NOTIF_TAG.equals(cursor.getString(14))) {
            if (!cursor.moveToNext()) {
                return false;
            }
        }
        return true;
    }

    public static MatrixCursor copyCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        MatrixCursor matrixCursor = new MatrixCursor(TvNotification.PROJECTION);
        if (cursor.isClosed() || !cursor.moveToFirst()) {
            return matrixCursor;
        }
        do {
            matrixCursor.addRow(TvNotification.getRowFromCursor(cursor));
        } while (cursor.moveToNext());
        return matrixCursor;
    }
}
