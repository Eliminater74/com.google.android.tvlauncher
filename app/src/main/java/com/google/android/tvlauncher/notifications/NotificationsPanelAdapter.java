package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.p004v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.VerticalGridView;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

public class NotificationsPanelAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<NotificationPanelViewHolder> implements EventLogger, NotificationsPanelView.OnFocusChangedListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "NotifsPanelAdapter";
    private final EventLogger mEventLogger;
    private final Runnable mNotifySelectionChangedRunnable = new NotificationsPanelAdapter$$Lambda$0(this);
    private Cursor mCursor;
    private Handler mHandler = new Handler();
    private VerticalGridView mList;

    public NotificationsPanelAdapter(Context context, Cursor cursor, EventLogger logger) {
        this.mCursor = cursor;
        this.mEventLogger = logger;
        setHasStableIds(true);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$NotificationsPanelAdapter() {
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    public int getItemCount() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public NotificationPanelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationPanelViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.notification_panel_item_view, parent, false));
    }

    public void onBindViewHolder(NotificationPanelViewHolder holder, int position) {
        if (this.mCursor.moveToPosition(position)) {
            onBindViewHolder(holder, this.mCursor);
            return;
        }
        StringBuilder sb = new StringBuilder(41);
        sb.append("Can't move cursor to position ");
        sb.append(position);
        throw new IllegalStateException(sb.toString());
    }

    public long getItemId(int position) {
        if (this.mCursor.moveToPosition(position)) {
            return (long) this.mCursor.getString(0).hashCode();
        }
        StringBuilder sb = new StringBuilder(41);
        sb.append("Can't move cursor to position ");
        sb.append(position);
        Log.wtf(TAG, sb.toString());
        return -1;
    }

    public void onBindViewHolder(NotificationPanelViewHolder viewHolder, Cursor cursor) {
        viewHolder.setNotification(TvNotification.fromCursor(cursor), this);
    }

    public void onFocusChanged() {
        this.mHandler.removeCallbacks(this.mNotifySelectionChangedRunnable);
        if (this.mList.isComputingLayout()) {
            Log.w(TAG, "onFocusChanged: still computing layout => scheduling");
            this.mHandler.post(this.mNotifySelectionChangedRunnable);
            return;
        }
        this.mNotifySelectionChangedRunnable.run();
    }

    public void changeCursor(Cursor newCursor) {
        this.mCursor = newCursor;
        notifyDataSetChanged();
        if (this.mCursor != null) {
            logDataLoadedEvent();
        }
    }

    public void log(LogEvent event) {
        this.mEventLogger.log(event);
    }

    private void logDataLoadedEvent() {
        int count = this.mCursor.getCount();
        LogEvent event = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.OPEN_NOTIFICATION_PANEL);
        TvlauncherClientLog.NotificationCollection.Builder collection = event.getNotificationCollection();
        int maxImportanceCount = 0;
        if (this.mCursor.moveToFirst()) {
            do {
                int channelImportance = this.mCursor.getInt(7);
                if (channelImportance == 5) {
                    maxImportanceCount++;
                }
                TvlauncherClientLog.Notification.Builder notification = TvlauncherClientLog.Notification.newBuilder();
                notification.setPackageName(this.mCursor.getString(1)).setImportance(LogEvent.notificationImportance(channelImportance));
                String title = this.mCursor.getString(2);
                if (!TextUtils.isEmpty(title)) {
                    notification.setSummary(title);
                }
                collection.addNotifications(notification);
            } while (this.mCursor.moveToNext());
        }
        collection.setCount(count).setMaxPriorityCount(maxImportanceCount);
        this.mEventLogger.log(event);
    }

    /* access modifiers changed from: protected */
    public void setList(VerticalGridView list) {
        this.mList = list;
        this.mList.setItemAnimator(new NotificationPanelItemAnimator());
    }

    public static class NotificationPanelViewHolder extends RecyclerView.ViewHolder {
        public NotificationPanelViewHolder(View itemView) {
            super(itemView);
        }

        public void setNotification(TvNotification notification, EventLogger logger) {
            ((NotificationPanelItemView) this.itemView).setNotification(notification, logger);
        }
    }
}
