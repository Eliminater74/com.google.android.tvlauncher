package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.tvlauncher.C1188R;

public class ConfigureChannelsRowView extends LinearLayout {
    private TextView mButton;
    private TextView mDescriptionView;
    private int mDescriptionVisibility;

    public ConfigureChannelsRowView(Context context) {
        super(context);
    }

    public ConfigureChannelsRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConfigureChannelsRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mButton = (TextView) findViewById(C1188R.C1191id.button);
        this.mDescriptionView = (TextView) findViewById(C1188R.C1191id.description_text);
        this.mDescriptionVisibility = this.mDescriptionView.getVisibility();
        final int cornerRadius = getResources().getDimensionPixelSize(C1188R.dimen.home_configure_channels_button_corner_radius);
        this.mButton.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) cornerRadius);
            }
        });
        this.mButton.setClipToOutline(true);
    }

    public TextView getButton() {
        return this.mButton;
    }

    /* access modifiers changed from: package-private */
    public View getDescriptionView() {
        return this.mDescriptionView;
    }

    /* access modifiers changed from: package-private */
    public int getDescriptionVisibility() {
        return this.mDescriptionVisibility;
    }

    public void setDescriptionVisibility(int descriptionVisibility) {
        this.mDescriptionVisibility = descriptionVisibility;
        this.mDescriptionView.setVisibility(descriptionVisibility);
    }
}
