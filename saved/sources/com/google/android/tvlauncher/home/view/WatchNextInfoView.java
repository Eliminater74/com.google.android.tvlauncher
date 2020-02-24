package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;

public class WatchNextInfoView extends FrameLayout {
    private String mButtonActionDescription;
    private View mContainer;
    private ImageView mIcon;
    private TextView mMessage;
    private TextView mTitle;

    public WatchNextInfoView(@NonNull Context context) {
        super(context);
    }

    public WatchNextInfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WatchNextInfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WatchNextInfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContainer = findViewById(C1188R.C1191id.watch_next_info_container);
        this.mIcon = (ImageView) findViewById(C1188R.C1191id.watch_next_info_icon);
        this.mTitle = (TextView) findViewById(C1188R.C1191id.watch_next_info_title);
        this.mMessage = (TextView) findViewById(C1188R.C1191id.watch_next_info_message);
        this.mButtonActionDescription = getResources().getString(C1188R.string.watch_next_row_acknowledgment_action_description);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, this.mButtonActionDescription));
    }

    public View getContainer() {
        return this.mContainer;
    }

    public ImageView getIcon() {
        return this.mIcon;
    }

    public TextView getTitle() {
        return this.mTitle;
    }

    public TextView getMessage() {
        return this.mMessage;
    }

    public View getIconTitleContainer() {
        return (View) this.mIcon.getParent();
    }
}
