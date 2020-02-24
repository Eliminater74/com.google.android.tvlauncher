package com.google.android.tvlauncher.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class PartnerWidgetInfo {
    public static final String[] PROJECTION = {"icon", "title", "action"};
    private Drawable mIcon;
    private String mIntentUri;
    private String mTitle;

    public PartnerWidgetInfo(Drawable icon, String title, String intentUri) {
        this.mTitle = title;
        this.mIntentUri = intentUri;
        this.mIcon = icon;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getIntent() {
        return this.mIntentUri;
    }

    public boolean isComplete() {
        return (this.mIcon == null || TextUtils.isEmpty(this.mTitle) || this.mIntentUri == null) ? false : true;
    }
}
