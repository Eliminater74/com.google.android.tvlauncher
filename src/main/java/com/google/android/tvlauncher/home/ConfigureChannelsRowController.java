package com.google.android.tvlauncher.home;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.home.view.ConfigureChannelsRowView;
import com.google.android.tvlauncher.util.ScaleFocusHandler;
import com.google.android.tvlauncher.util.Util;

class ConfigureChannelsRowController implements HomeRow {
    private final View mButton;
    private final int mButtonSelectedBackgroundColor;
    private final float mButtonSelectedScale;
    private final int mButtonUnselectedBackgroundColor;
    private final int mChannelActionsStartMargin;
    private final int mDefaultBelowAppsRowTopMargin;
    private final int mDefaultStartMargin;
    private final int mDefaultTopMargin;
    private final int mMoveChannelStartMargin;
    private OnHomeRowSelectedListener mOnHomeRowSelectedListener;
    private final ConfigureChannelsRowView mView;
    private final int mZoomedOutStartMargin;
    private final int mZoomedOutTopMargin;

    ConfigureChannelsRowController(ConfigureChannelsRowView v) {
        this.mView = v;
        this.mButton = v.getButton();
        this.mButton.setOnClickListener(ConfigureChannelsRowController$$Lambda$0.$instance);
        Resources resources = v.getContext().getResources();
        this.mButtonSelectedScale = resources.getFraction(C1188R.fraction.home_configure_channels_button_focused_scale, 1, 1);
        View.OnFocusChangeListener buttonOnFocusChangeListener = new ConfigureChannelsRowController$$Lambda$1(this);
        if (Util.areHomeScreenAnimationsEnabled(this.mView.getContext())) {
            this.mButton.setOnFocusChangeListener(buttonOnFocusChangeListener);
        } else {
            ScaleFocusHandler focusHandler = new ScaleFocusHandler(resources.getInteger(C1188R.integer.home_configure_channels_button_focused_animation_duration_ms), this.mButtonSelectedScale, 0.0f, 1);
            focusHandler.setView(this.mButton);
            focusHandler.setOnFocusChangeListener(buttonOnFocusChangeListener);
        }
        this.mDefaultTopMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_row_margin_top);
        this.mDefaultBelowAppsRowTopMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_row_below_apps_row_margin_top);
        this.mZoomedOutTopMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_row_zoomed_out_margin_top);
        this.mDefaultStartMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_button_margin_default);
        this.mZoomedOutStartMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_button_margin_zoomed_out);
        this.mChannelActionsStartMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_button_margin_channel_actions);
        this.mMoveChannelStartMargin = resources.getDimensionPixelSize(C1188R.dimen.home_configure_channels_button_margin_move_channel);
        this.mButtonSelectedBackgroundColor = v.getContext().getColor(C1188R.color.home_configure_channels_button_focused_background_color);
        this.mButtonUnselectedBackgroundColor = v.getContext().getColor(C1188R.color.home_configure_channels_button_unfocused_background_color);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$1$ConfigureChannelsRowController(View view, boolean hasFocus) {
        if (hasFocus) {
            this.mOnHomeRowSelectedListener.onHomeRowSelected(this);
        }
    }

    public void setOnHomeStateChangeListener(OnHomeStateChangeListener listener) {
    }

    public void setOnHomeRowRemovedListener(OnHomeRowRemovedListener listener) {
    }

    public void setHomeIsFastScrolling(boolean homeIsFastScrolling) {
    }

    public void setOnHomeRowSelectedListener(OnHomeRowSelectedListener listener) {
        this.mOnHomeRowSelectedListener = listener;
    }

    public View getView() {
        return this.mView;
    }

    /* access modifiers changed from: package-private */
    public void bind(int homeState, boolean selected, boolean isBelowAppsRow) {
        int marginStart = this.mDefaultStartMargin;
        int marginTop = this.mDefaultTopMargin;
        if (homeState == 0) {
            marginStart = this.mDefaultStartMargin;
            marginTop = isBelowAppsRow ? this.mDefaultBelowAppsRowTopMargin : this.mDefaultTopMargin;
        } else if (homeState == 1) {
            marginStart = this.mZoomedOutStartMargin;
            marginTop = this.mZoomedOutTopMargin;
        } else if (homeState == 2) {
            marginStart = this.mChannelActionsStartMargin;
            marginTop = this.mZoomedOutTopMargin;
        } else if (homeState == 3) {
            marginStart = this.mMoveChannelStartMargin;
            marginTop = this.mZoomedOutTopMargin;
        }
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mView.getLayoutParams();
        lp.setMarginStart(marginStart);
        this.mView.setLayoutParams(lp);
        ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) this.mButton.getLayoutParams();
        lp2.topMargin = marginTop;
        this.mButton.setLayoutParams(lp2);
        if (selected) {
            this.mButton.setBackgroundColor(this.mButtonSelectedBackgroundColor);
            this.mButton.setScaleX(this.mButtonSelectedScale);
            this.mButton.setScaleY(this.mButtonSelectedScale);
        } else {
            this.mButton.setBackgroundColor(this.mButtonUnselectedBackgroundColor);
            this.mButton.setScaleX(1.0f);
            this.mButton.setScaleY(1.0f);
        }
        int width = this.mButton.getLayoutParams().width;
        int height = this.mButton.getLayoutParams().height;
        if (width <= 0 || height <= 0) {
            width = this.mButton.getWidth();
            height = this.mButton.getHeight();
        }
        if (width > 0 && height > 0) {
            if (this.mButton.getLayoutDirection() == 1) {
                this.mButton.setPivotX((float) width);
            } else {
                this.mButton.setPivotX(0.0f);
            }
            this.mButton.setPivotY((float) height);
        }
        this.mView.setDescriptionVisibility(selected ? 0 : 4);
    }
}
