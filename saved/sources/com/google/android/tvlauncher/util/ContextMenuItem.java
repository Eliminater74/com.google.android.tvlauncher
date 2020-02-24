package com.google.android.tvlauncher.util;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class ContextMenuItem {
    private boolean mAutoDismiss = true;
    private boolean mEnabled;
    private Drawable mIcon;
    private int mId;
    private boolean mIsLinkedWithTriangle;
    private OnMenuItemChangedListener mOnMenuItemChangedListener;
    private String mTitle;
    private boolean mVisible;

    interface OnMenuItemChangedListener {
        void onMenuItemChanged(ContextMenuItem contextMenuItem);
    }

    public ContextMenuItem(int id, String title, Drawable icon) {
        this.mId = id;
        this.mTitle = title;
        this.mIcon = icon;
        this.mEnabled = true;
        this.mVisible = true;
        this.mIsLinkedWithTriangle = false;
    }

    public int getId() {
        return this.mId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public void setTitle(String title) {
        if (!TextUtils.equals(this.mTitle, title)) {
            this.mTitle = title;
            notifyMenuItemChanged();
        }
    }

    public void setIcon(Drawable icon) {
        if (this.mIcon != icon) {
            this.mIcon = icon;
            notifyMenuItemChanged();
        }
    }

    public void setEnabled(boolean isEnabled) {
        if (this.mEnabled != isEnabled) {
            this.mEnabled = isEnabled;
            notifyMenuItemChanged();
        }
    }

    public void setVisible(boolean isVisible) {
        if (this.mVisible != isVisible) {
            this.mVisible = isVisible;
            notifyMenuItemChanged();
        }
    }

    public void setAutoDismiss(boolean autoDismiss) {
        this.mAutoDismiss = autoDismiss;
    }

    /* access modifiers changed from: package-private */
    public boolean isLinkedWithTriangle() {
        return this.mIsLinkedWithTriangle;
    }

    /* access modifiers changed from: package-private */
    public boolean isAutoDismiss() {
        return this.mAutoDismiss;
    }

    /* access modifiers changed from: package-private */
    public void setLinkedWithTriangle(boolean linkedWithTriangle) {
        this.mIsLinkedWithTriangle = linkedWithTriangle;
    }

    /* access modifiers changed from: package-private */
    public void setOnMenuItemChangedListener(OnMenuItemChangedListener onMenuItemChangedListener) {
        this.mOnMenuItemChangedListener = onMenuItemChangedListener;
    }

    private void notifyMenuItemChanged() {
        OnMenuItemChangedListener onMenuItemChangedListener = this.mOnMenuItemChangedListener;
        if (onMenuItemChangedListener != null) {
            onMenuItemChangedListener.onMenuItemChanged(this);
        }
    }
}
