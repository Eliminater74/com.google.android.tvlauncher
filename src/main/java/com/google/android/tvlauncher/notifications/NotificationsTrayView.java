package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.leanback.widget.HorizontalGridView;
import com.google.android.tvlauncher.C1188R;

public class NotificationsTrayView extends FrameLayout {
    private HorizontalGridView mNotificationsRow;

    public NotificationsTrayView(Context context) {
        super(context);
    }

    public NotificationsTrayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mNotificationsRow = (HorizontalGridView) findViewById(C1188R.C1191id.notifications_row);
        this.mNotificationsRow.setWindowAlignment(0);
        this.mNotificationsRow.setWindowAlignmentOffsetPercent(0.0f);
        this.mNotificationsRow.setWindowAlignmentOffset(getContext().getResources().getDimensionPixelSize(C1188R.dimen.notifications_list_padding_start));
        this.mNotificationsRow.setItemAlignmentOffsetPercent(0.0f);
    }

    public void updateVisibility() {
        setVisibility((this.mNotificationsRow.getAdapter() == null || this.mNotificationsRow.getAdapter().getItemCount() <= 0) ? 8 : 0);
    }

    public void setTrayAdapter(NotificationsTrayAdapter adapter) {
        this.mNotificationsRow.setAdapter(adapter);
        updateVisibility();
    }

    public NotificationsTrayAdapter getTrayAdapter() {
        HorizontalGridView horizontalGridView = this.mNotificationsRow;
        if (horizontalGridView != null) {
            return (NotificationsTrayAdapter) horizontalGridView.getAdapter();
        }
        return null;
    }
}
