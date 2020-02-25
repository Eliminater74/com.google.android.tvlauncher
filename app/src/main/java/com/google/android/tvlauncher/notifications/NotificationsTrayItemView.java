package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class NotificationsTrayItemView extends LinearLayout {
    /* access modifiers changed from: private */
    public NotificationTrayButton mDismissButton;
    /* access modifiers changed from: private */
    public String mNotificationKey;
    /* access modifiers changed from: private */
    public NotificationTrayButton mSeeMoreButton;
    /* access modifiers changed from: private */
    public TextView mText;
    /* access modifiers changed from: private */
    public TextView mTitle;
    private ImageView mBigPicture;
    private EventLogger mEventLogger;
    private ViewTreeObserver.OnGlobalFocusChangeListener mGlobalFocusChangeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            boolean childGainedFocus = NotificationsTrayItemView.this.getFocusedChild() == newFocus;
            boolean childLostFocus = NotificationsTrayItemView.this.isFocusableChild(oldFocus);
            if (childGainedFocus && !childLostFocus) {
                NotificationsTrayItemView.this.mText.setSelected(true);
                NotificationsTrayItemView.this.mTitle.setSelected(true);
                NotificationsTrayItemView.this.setSelected(true);
                NotificationsTrayItemView.this.mDismissButton.setIsNotificationSelected(true);
                NotificationsTrayItemView.this.mSeeMoreButton.setIsNotificationSelected(true);
            } else if (!childGainedFocus && childLostFocus) {
                NotificationsTrayItemView.this.mText.setSelected(false);
                NotificationsTrayItemView.this.mTitle.setSelected(false);
                NotificationsTrayItemView.this.setSelected(false);
                NotificationsTrayItemView.this.mDismissButton.setIsNotificationSelected(false);
                NotificationsTrayItemView.this.mSeeMoreButton.setIsNotificationSelected(false);
            }
        }
    };
    private ImageView mIcon;
    private TvNotification mNotification;
    private View mNowPlayingIndicator;

    public NotificationsTrayItemView(Context context) {
        super(context);
    }

    public NotificationsTrayItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotificationsTrayItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.mGlobalFocusChangeListener);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.mGlobalFocusChangeListener);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBigPicture = (ImageView) findViewById(C1188R.C1191id.big_picture);
        this.mIcon = (ImageView) findViewById(C1188R.C1191id.small_icon);
        this.mTitle = (TextView) findViewById(C1188R.C1191id.notif_title);
        this.mText = (TextView) findViewById(C1188R.C1191id.notif_text);
        this.mDismissButton = (NotificationTrayButton) findViewById(C1188R.C1191id.tray_dismiss);
        this.mSeeMoreButton = (NotificationTrayButton) findViewById(C1188R.C1191id.tray_see_more);
        this.mNowPlayingIndicator = findViewById(C1188R.C1191id.now_playing_bars);
        setClipToOutline(true);
    }

    /* access modifiers changed from: private */
    public boolean isFocusableChild(View v) {
        if (v != null && v.getParent() == this) {
            return true;
        }
        return false;
    }

    public void setNotification(TvNotification notif, EventLogger eventLogger) {
        this.mNotification = notif;
        this.mNotificationKey = notif.getNotificationKey();
        this.mEventLogger = eventLogger;
        AccessibilityManager am = (AccessibilityManager) getContext().getSystemService("accessibility");
        if (am == null || !am.isEnabled()) {
            setFocusable(false);
        } else {
            setFocusable(true);
        }
        if (NotificationsContract.NOW_PLAYING_NOTIF_TAG.equals(notif.getTag()) && notif.getBigPicture() != null) {
            this.mBigPicture.setImageBitmap(notif.getBigPicture());
            this.mBigPicture.setVisibility(0);
            this.mNowPlayingIndicator.setVisibility(0);
            this.mIcon.setVisibility(8);
        } else if (NotificationsContract.PIP_NOTIF_TAG.equals(notif.getTag())) {
            this.mNowPlayingIndicator.setVisibility(8);
            Icon icon = notif.getSmallIcon();
            icon.setTint(getResources().getColor(C1188R.color.notification_icon_tint, null));
            this.mIcon.setImageIcon(icon);
            this.mIcon.setVisibility(0);
            this.mBigPicture.setVisibility(8);
        } else {
            this.mNowPlayingIndicator.setVisibility(8);
            this.mBigPicture.setVisibility(8);
            this.mIcon.setVisibility(8);
        }
        String title = notif.getTitle();
        if (TextUtils.isEmpty(title) || !title.equals(this.mTitle.getText())) {
            this.mTitle.setText(title);
        }
        String text = notif.getText();
        if (TextUtils.isEmpty(text)) {
            this.mText.setVisibility(8);
        } else {
            if (!text.equals(this.mText.getText())) {
                this.mText.setText(text);
            }
            this.mText.setVisibility(0);
        }
        if (TextUtils.isEmpty(title)) {
            setContentDescription(text);
        } else if (!TextUtils.isEmpty(text)) {
            setContentDescription(String.format(getResources().getString(C1188R.string.notification_content_description_format), title, text));
        } else {
            setContentDescription(title);
        }
        if (notif.hasContentIntent()) {
            this.mSeeMoreButton.setVisibility(0);
            this.mSeeMoreButton.setText(notif.getContentButtonLabel());
            this.mSeeMoreButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    NotificationsTrayItemView.this.logClickEvent(TvlauncherLogEnum.TvLauncherEventCode.OPEN_NOTIFICATION);
                    NotificationsUtils.openNotification(NotificationsTrayItemView.this.getContext(), NotificationsTrayItemView.this.mNotificationKey);
                }
            });
        } else {
            this.mSeeMoreButton.setVisibility(8);
        }
        this.mDismissButton.setText(notif.getDismissButtonLabel());
        if (!notif.isDismissible() || notif.isOngoing()) {
            this.mDismissButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    NotificationsTrayItemView.this.logClickEvent(TvlauncherLogEnum.TvLauncherEventCode.HIDE_NOTIFICATION);
                    NotificationsUtils.hideNotification(view.getContext(), NotificationsTrayItemView.this.mNotificationKey);
                }
            });
        } else {
            this.mDismissButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    NotificationsTrayItemView.this.logClickEvent(TvlauncherLogEnum.TvLauncherEventCode.DISMISS_NOTIFICATION);
                    NotificationsUtils.dismissNotification(view.getContext(), NotificationsTrayItemView.this.mNotificationKey);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void logClickEvent(TvlauncherLogEnum.TvLauncherEventCode eventCode) {
        LogEvent event = new ClickEvent(eventCode).setVisualElementTag(TvLauncherConstants.NOTIFICATION);
        event.getNotification().setPackageName(this.mNotification.getPackageName()).setImportance(LogEvent.notificationImportance(this.mNotification.getChannel()));
        if (!TextUtils.isEmpty(this.mNotification.getTitle())) {
            event.getNotification().setSummary(this.mNotification.getTitle());
        }
        this.mEventLogger.log(event);
    }
}
