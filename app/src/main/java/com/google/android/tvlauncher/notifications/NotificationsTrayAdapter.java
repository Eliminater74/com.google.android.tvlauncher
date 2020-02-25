package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.database.Cursor;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LogEventParameters;

public class NotificationsTrayAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<NotificationsTrayViewHolder> {
    public static final String TAG = "NotifsTrayAdapter";
    private static final boolean DEBUG = false;
    private final Context mContext;
    private final EventLogger mEventLogger;
    private Cursor mCursor;

    public NotificationsTrayAdapter(Context context, EventLogger eventLogger, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        this.mEventLogger = eventLogger;
        setHasStableIds(true);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public NotificationsTrayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationsTrayViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.notification_tray_item, parent, false));
    }

    public void onBindViewHolder(NotificationsTrayViewHolder holder, int position) {
        if (this.mCursor.moveToPosition(position)) {
            holder.setNotification(TvNotification.fromCursor(this.mCursor), this.mEventLogger);
            return;
        }
        StringBuilder sb = new StringBuilder(43);
        sb.append("Index out of bounds for cursor: ");
        sb.append(position);
        throw new IllegalStateException(sb.toString());
    }

    public int getItemCount() {
        Cursor cursor = this.mCursor;
        if (cursor == null || cursor.isClosed()) {
            return 0;
        }
        return this.mCursor.getCount();
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

    public void changeCursor(Cursor newCursor) {
        this.mCursor = newCursor;
        notifyDataSetChanged();
        LogEvent event = new LogEventParameters(TvlauncherLogEnum.TvLauncherEventCode.OPEN_HOME, LogEventParameters.TRAY_NOTIFICATION_COUNT);
        Cursor cursor = this.mCursor;
        if (!(cursor == null || cursor.getCount() == 0)) {
            event.getNotificationCollection().setMaxPriorityCount(this.mCursor.getCount());
        }
        this.mEventLogger.log(event);
    }

    public static class NotificationsTrayViewHolder extends RecyclerView.ViewHolder {
        public NotificationsTrayViewHolder(View itemView) {
            super(itemView);
        }

        public void setNotification(TvNotification notification, EventLogger eventLogger) {
            ((NotificationsTrayItemView) this.itemView).setNotification(notification, eventLogger);
        }
    }
}
