package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class NotificationsPanelController {
    public static final String NOTIFS_SEEN = "notifs_seen";
    public static final String NOTIF_PANEL_SEEN_STATE = "notif_panel_seen_state";
    private final EventLogger mEventLogger;
    private Context mContext;
    private int mNotifCount = 0;
    private NotificationsPanelButtonView mPanelButtonView;
    private boolean mSeen;

    public NotificationsPanelController(Context context, EventLogger eventLogger) {
        this.mEventLogger = eventLogger;
        this.mContext = context;
        this.mSeen = this.mContext.getSharedPreferences(NOTIF_PANEL_SEEN_STATE, 0).getBoolean(NOTIFS_SEEN, true);
    }

    public void setView(NotificationsPanelButtonView view) {
        if (view != null) {
            this.mPanelButtonView = view;
            this.mPanelButtonView.setOnClickListener(new NotificationsPanelController$$Lambda$0(this));
            updateView();
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$setView$0$NotificationsPanelController(View v) {
        logClick();
        NotificationsUtils.openNotificationPanel(this.mContext);
        this.mPanelButtonView.setSeenState(true);
    }

    public void updateNotificationsCount(Cursor data) {
        if (data != null && data.moveToFirst()) {
            data.moveToFirst();
            int oldCount = this.mNotifCount;
            this.mNotifCount = data.getInt(data.getColumnIndex(NotificationsContract.COLUMN_COUNT));
            boolean oldSeenState = this.mSeen;
            this.mSeen = oldCount >= this.mNotifCount;
            if (oldSeenState != this.mSeen) {
                storeSeenState();
            }
            LogEvent event = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME, LogEventParameters.NOTIFICATION_INDICATOR_TOTAL);
            event.getNotificationCollection().setCount(this.mNotifCount).setHasNewNotifications(true ^ this.mSeen);
            this.mEventLogger.log(event);
        }
        updateView();
    }

    public void updateView() {
        NotificationsPanelButtonView notificationsPanelButtonView = this.mPanelButtonView;
        if (notificationsPanelButtonView == null) {
            return;
        }
        if (this.mNotifCount == 0) {
            notificationsPanelButtonView.setVisibility(8);
            return;
        }
        notificationsPanelButtonView.setVisibility(0);
        this.mPanelButtonView.setCount(this.mNotifCount);
        this.mPanelButtonView.setSeenState(this.mSeen);
    }

    private void storeSeenState() {
        this.mContext.getSharedPreferences(NOTIF_PANEL_SEEN_STATE, 0).edit().putBoolean(NOTIFS_SEEN, this.mSeen).apply();
    }

    private void logClick() {
        LogEvent event = new ClickEvent(null).setVisualElementTag(TvLauncherConstants.NOTIFICATIONS_INDICATOR_BUTTON);
        event.getNotificationCollection().setCount(this.mNotifCount).setHasNewNotifications(!this.mSeen);
        this.mEventLogger.log(event);
    }
}
