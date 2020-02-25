package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.google.android.tvlauncher.C1188R;

public class NotificationDismissButton extends ImageButton {
    private int mButtonFocusedIconColor;
    private Drawable mIcon;
    private int mNotificationFocusedColor;
    private int mNotificationUnfocusedColor;

    public NotificationDismissButton(@NonNull Context context) {
        super(context, null);
    }

    public NotificationDismissButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources res = getResources();
        this.mNotificationFocusedColor = res.getColor(C1188R.color.reference_white_40, null);
        this.mButtonFocusedIconColor = res.getColor(C1188R.color.notification_panel_background, null);
        this.mNotificationUnfocusedColor = res.getColor(C1188R.color.reference_white_20, null);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = getDrawable();
        this.mIcon.mutate();
    }

    public void bind(boolean notifSelected) {
        if (hasFocus()) {
            this.mIcon.setTint(this.mButtonFocusedIconColor);
        } else {
            this.mIcon.setTint(notifSelected ? this.mNotificationFocusedColor : this.mNotificationUnfocusedColor);
        }
    }
}
