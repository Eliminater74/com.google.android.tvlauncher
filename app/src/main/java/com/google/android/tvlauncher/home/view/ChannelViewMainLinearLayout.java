package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.tvlauncher.C1188R;

public class ChannelViewMainLinearLayout extends LinearLayout {
    int mChannelLogoKeylineOffset;
    boolean mIsSponsored;
    private View mLogo;
    private View mZoomedOutLogoTitle;
    private boolean mZoomedOutState = false;

    public ChannelViewMainLinearLayout(Context context) {
        super(context);
    }

    public ChannelViewMainLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelViewMainLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLogo = findViewById(C1188R.C1191id.channel_logo);
        this.mZoomedOutLogoTitle = findViewById(C1188R.C1191id.logo_title_zoomed_out);
    }

    /* access modifiers changed from: package-private */
    public void setIsSponsored(boolean isSponsored) {
        this.mIsSponsored = isSponsored;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.mZoomedOutState) {
            int titleTop = (((this.mLogo.getTop() + (this.mLogo.getHeight() / 2)) - (this.mZoomedOutLogoTitle.getHeight() / 2)) - this.mChannelLogoKeylineOffset) + ((ViewGroup.MarginLayoutParams) this.mZoomedOutLogoTitle.getLayoutParams()).topMargin;
            View view = this.mZoomedOutLogoTitle;
            view.layout(view.getLeft(), titleTop, this.mZoomedOutLogoTitle.getRight(), this.mZoomedOutLogoTitle.getHeight() + titleTop);
        }
    }

    public void setZoomedOutState(boolean zoomedOutState) {
        this.mZoomedOutState = zoomedOutState;
    }

    public void setChannelLogoKeylineOffset(int channelLogoKeylineOffset) {
        this.mChannelLogoKeylineOffset = channelLogoKeylineOffset;
    }
}
