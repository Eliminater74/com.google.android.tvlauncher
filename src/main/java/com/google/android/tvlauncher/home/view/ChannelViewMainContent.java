package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.tvlauncher.C1188R;

public class ChannelViewMainContent extends FrameLayout {
    private View mActionsHint;
    private int mChannelLogoKeylineOffset;
    private View mChannelViewMainLinearLayout;
    private boolean mIsBranded = true;
    private boolean mIsSponsored;
    private View mLogo;
    private View mLogoTitle;
    private View mSponsoredChannelBackground;

    public ChannelViewMainContent(@NonNull Context context) {
        super(context);
    }

    public ChannelViewMainContent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelViewMainContent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLogo = findViewById(C1188R.C1191id.channel_logo);
        this.mLogoTitle = findViewById(C1188R.C1191id.logo_title);
        this.mActionsHint = findViewById(C1188R.C1191id.actions_hint);
        this.mSponsoredChannelBackground = findViewById(C1188R.C1191id.sponsored_channel_background);
        this.mChannelViewMainLinearLayout = findViewById(C1188R.C1191id.main_linear_layout);
    }

    /* access modifiers changed from: package-private */
    public void setIsSponsored(boolean sponsored) {
        this.mIsSponsored = sponsored;
    }

    /* access modifiers changed from: package-private */
    public void setIsBranded(boolean branded) {
        this.mIsBranded = branded;
    }

    public void setChannelLogoKeylineOffset(int channelLogoKeylineOffset) {
        this.mChannelLogoKeylineOffset = channelLogoKeylineOffset;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int hintLeft;
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup.MarginLayoutParams titleLayoutParams = (ViewGroup.MarginLayoutParams) this.mLogoTitle.getLayoutParams();
        if (this.mIsSponsored) {
            alignSponsoredBackgroundCenterToKeyLine();
            if (this.mIsBranded) {
                attachSponsoredLogoTitleAboveLogo(titleLayoutParams);
            } else {
                int logoTitleTop = (int) (((((float) this.mLogo.getTop()) + (((float) this.mLogo.getMeasuredHeight()) / 2.0f)) - (((float) this.mLogoTitle.getHeight()) / 2.0f)) - ((float) this.mChannelLogoKeylineOffset));
                View view = this.mLogoTitle;
                view.layout(view.getLeft(), logoTitleTop, this.mLogoTitle.getRight(), this.mLogoTitle.getMeasuredHeight() + logoTitleTop);
            }
        } else {
            int titleTop = this.mLogo.getBottom() + titleLayoutParams.topMargin;
            View view2 = this.mLogoTitle;
            view2.layout(view2.getLeft(), titleTop, this.mLogoTitle.getRight(), this.mLogoTitle.getMeasuredHeight() + titleTop);
        }
        ViewGroup.MarginLayoutParams actionsHintLayoutParams = (ViewGroup.MarginLayoutParams) this.mActionsHint.getLayoutParams();
        int hintTop = (((this.mLogo.getTop() + (this.mLogo.getHeight() / 2)) - (this.mActionsHint.getHeight() / 2)) - this.mChannelLogoKeylineOffset) + actionsHintLayoutParams.topMargin;
        if (getLayoutDirection() == 1) {
            hintLeft = this.mLogo.getRight() + actionsHintLayoutParams.getMarginEnd();
        } else {
            hintLeft = ((this.mChannelViewMainLinearLayout.getLeft() + this.mLogo.getLeft()) - this.mActionsHint.getWidth()) - actionsHintLayoutParams.getMarginEnd();
        }
        View view3 = this.mActionsHint;
        view3.layout(hintLeft, hintTop, view3.getWidth() + hintLeft, this.mActionsHint.getHeight() + hintTop);
    }

    private void alignSponsoredBackgroundCenterToKeyLine() {
        int sponsoredBackgroundTop = (int) (((((float) this.mLogo.getTop()) + (((float) this.mLogo.getHeight()) / 2.0f)) - (((float) this.mSponsoredChannelBackground.getHeight()) / 2.0f)) - ((float) this.mChannelLogoKeylineOffset));
        View view = this.mSponsoredChannelBackground;
        view.layout(view.getLeft(), sponsoredBackgroundTop, this.mSponsoredChannelBackground.getRight(), this.mSponsoredChannelBackground.getHeight() + sponsoredBackgroundTop);
    }

    private void attachSponsoredLogoTitleAboveLogo(ViewGroup.MarginLayoutParams titleLayoutParams) {
        int titleLeft = ((this.mChannelViewMainLinearLayout.getLeft() + this.mLogo.getLeft()) + (this.mLogo.getMeasuredWidth() / 2)) - (this.mLogoTitle.getMeasuredWidth() / 2);
        int titleTop = (this.mLogo.getTop() - titleLayoutParams.bottomMargin) - this.mLogoTitle.getMeasuredHeight();
        View view = this.mLogoTitle;
        view.layout(titleLeft, titleTop, view.getMeasuredWidth() + titleLeft, this.mLogoTitle.getMeasuredHeight() + titleTop);
    }
}
