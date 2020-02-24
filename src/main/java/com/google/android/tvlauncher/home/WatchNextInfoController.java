package com.google.android.tvlauncher.home;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.home.util.ProgramSettings;
import com.google.android.tvlauncher.home.util.ProgramStateUtil;
import com.google.android.tvlauncher.home.util.ProgramUtil;
import com.google.android.tvlauncher.home.view.WatchNextInfoView;
import com.google.android.tvlauncher.util.Util;

class WatchNextInfoController {
    private View mContainer = this.mView.getContainer();
    private final float mContentContainerFocusedScale;
    private int mDefaultContainerMarginHorizontal;
    private int mDefaultIconSize;
    private int mDefaultMessageMarginTop;
    private int mDefaultMessageWidth;
    private int mDefaultTitleMarginStart;
    private float mDefaultTitleTextSize;
    private int mDefaultTitleWidth;
    private final int mDimmedColor;
    private final int mFocusedColor;
    private ImageView mIcon = this.mView.getIcon();
    private final float mIconDimmedAlpha;
    private final float mIconFocusedAlpha;
    private final float mIconUnfocusedAlpha;
    private boolean mIsItemSelected;
    private TextView mMessage = this.mView.getMessage();
    private final ProgramSettings mProgramSettings;
    private int mProgramState;
    private int mSelectedChannelContainerVisualOffset;
    private int mSelectedChannelIconSize;
    private int mSelectedChannelInfoViewIgnoredWidthChangeThreshold;
    private int mSelectedChannelMessageMarginTop;
    private int mSelectedChannelMessageWidth;
    private int mSelectedChannelTitleMarginStart;
    private float mSelectedChannelTitleTextSize;
    private int mSelectedChannelTitleWidth;
    private TextView mTitle = this.mView.getTitle();
    private final int mUnfocusedColor;
    private WatchNextInfoView mView;
    private int mZoomedOutIconSize;
    private int mZoomedOutMessageMarginTop;
    private int mZoomedOutMessageWidth;
    private int mZoomedOutTitleMarginStart;
    private float mZoomedOutTitleTextSize;
    private int mZoomedOutTitleWidth;

    WatchNextInfoController(WatchNextInfoView view, ProgramSettings programSettings) {
        this.mView = view;
        this.mProgramSettings = programSettings;
        Context context = view.getContext();
        Resources resources = context.getResources();
        this.mDefaultContainerMarginHorizontal = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_container_default_margin_horizontal);
        this.mDefaultIconSize = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_default_icon_size);
        this.mDefaultTitleMarginStart = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_default_title_margin_start);
        this.mDefaultTitleWidth = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_default_title_width);
        this.mDefaultTitleTextSize = resources.getDimension(C1188R.dimen.watch_next_info_card_default_title_text_size);
        this.mDefaultMessageMarginTop = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_default_message_margin_top);
        this.mDefaultMessageWidth = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_default_message_width);
        this.mSelectedChannelInfoViewIgnoredWidthChangeThreshold = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_container_width_change_ignored_threshold);
        this.mSelectedChannelContainerVisualOffset = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_container_selected_margin_offset);
        this.mSelectedChannelIconSize = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_selected_icon_size);
        this.mSelectedChannelTitleMarginStart = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_selected_title_margin_start);
        this.mSelectedChannelTitleWidth = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_selected_title_width);
        this.mSelectedChannelTitleTextSize = resources.getDimension(C1188R.dimen.watch_next_info_card_selected_title_text_size);
        this.mSelectedChannelMessageMarginTop = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_selected_message_margin_top);
        this.mSelectedChannelMessageWidth = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_selected_message_width);
        this.mZoomedOutIconSize = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_zoomed_out_icon_size);
        this.mZoomedOutTitleMarginStart = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_zoomed_out_title_margin_start);
        this.mZoomedOutTitleWidth = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_zoomed_out_title_width);
        this.mZoomedOutTitleTextSize = resources.getDimension(C1188R.dimen.watch_next_info_card_zoomed_out_title_text_size);
        this.mZoomedOutMessageMarginTop = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_zoomed_out_message_margin_top);
        this.mZoomedOutMessageWidth = resources.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_zoomed_out_message_width);
        this.mContentContainerFocusedScale = resources.getFraction(C1188R.fraction.watch_next_info_card_content_container_focused_scale, 1, 1);
        this.mFocusedColor = context.getColor(C1188R.color.watch_next_info_card_focused_color);
        this.mUnfocusedColor = context.getColor(C1188R.color.watch_next_info_card_unfocused_color);
        this.mDimmedColor = context.getColor(C1188R.color.watch_next_info_card_dimmed_color);
        this.mIconFocusedAlpha = Util.getFloat(context.getResources(), C1188R.dimen.watch_next_info_icon_focused_alpha);
        this.mIconUnfocusedAlpha = Util.getFloat(context.getResources(), C1188R.dimen.watch_next_info_icon_unfocused_alpha);
        this.mIconDimmedAlpha = Util.getFloat(context.getResources(), C1188R.dimen.watch_next_info_icon_dimmed_alpha);
    }

    /* access modifiers changed from: package-private */
    public boolean isSelected() {
        return this.mIsItemSelected;
    }

    /* access modifiers changed from: package-private */
    public void bindState(int programState) {
        this.mProgramState = programState;
        ProgramUtil.updateSize(this.mView, this.mProgramState, 1.0d, this.mProgramSettings);
        ViewGroup.MarginLayoutParams itemViewLayoutParams = (ViewGroup.MarginLayoutParams) this.mView.getLayoutParams();
        if (ProgramStateUtil.isZoomedOutState(this.mProgramState)) {
            itemViewLayoutParams.width = 0;
            itemViewLayoutParams.setMarginEnd(0);
        } else {
            itemViewLayoutParams.width = -2;
        }
        this.mView.setLayoutParams(itemViewLayoutParams);
        ViewGroup.MarginLayoutParams iconLayoutParams = (ViewGroup.MarginLayoutParams) this.mIcon.getLayoutParams();
        ViewGroup.MarginLayoutParams titleLayoutParams = (ViewGroup.MarginLayoutParams) this.mTitle.getLayoutParams();
        ViewGroup.MarginLayoutParams messageLayoutParams = (ViewGroup.MarginLayoutParams) this.mMessage.getLayoutParams();
        int messageViewVisualWidth = 0;
        int i = this.mProgramState;
        switch (i) {
            case 0:
            case 3:
            case 4:
            case 12:
                iconLayoutParams.width = this.mSelectedChannelIconSize;
                titleLayoutParams.setMarginStart(this.mSelectedChannelTitleMarginStart);
                titleLayoutParams.width = this.mSelectedChannelTitleWidth;
                this.mTitle.setTextSize(0, this.mSelectedChannelTitleTextSize);
                messageLayoutParams.topMargin = this.mSelectedChannelMessageMarginTop;
                messageViewVisualWidth = this.mSelectedChannelMessageWidth;
                break;
            case 1:
            case 2:
                iconLayoutParams.width = this.mDefaultIconSize;
                titleLayoutParams.setMarginStart(this.mDefaultTitleMarginStart);
                titleLayoutParams.width = this.mDefaultTitleWidth;
                this.mTitle.setTextSize(0, this.mDefaultTitleTextSize);
                messageLayoutParams.topMargin = this.mDefaultMessageMarginTop;
                messageViewVisualWidth = this.mDefaultMessageWidth;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                iconLayoutParams.width = this.mZoomedOutIconSize;
                titleLayoutParams.setMarginStart(this.mZoomedOutTitleMarginStart);
                titleLayoutParams.width = this.mZoomedOutTitleWidth;
                this.mTitle.setTextSize(0, this.mZoomedOutTitleTextSize);
                messageLayoutParams.topMargin = this.mZoomedOutMessageMarginTop;
                messageViewVisualWidth = this.mZoomedOutMessageWidth;
                break;
            case 9:
            case 11:
                String valueOf = String.valueOf(ProgramStateUtil.stateToString(i));
                throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported Watch Next program state: ".concat(valueOf) : new String("Unsupported Watch Next program state: "));
        }
        iconLayoutParams.height = iconLayoutParams.width;
        this.mIcon.setLayoutParams(iconLayoutParams);
        this.mTitle.setLayoutParams(titleLayoutParams);
        this.mMessage.setLayoutParams(messageLayoutParams);
        float ratio = (((float) messageViewVisualWidth) * 1.0f) / ((float) this.mSelectedChannelMessageWidth);
        this.mMessage.setScaleX(ratio);
        this.mMessage.setScaleY(ratio);
        if (Util.isRtl(this.mView.getContext())) {
            this.mMessage.setPivotX((float) this.mSelectedChannelMessageWidth);
            this.mTitle.setPivotX((float) titleLayoutParams.width);
        } else {
            this.mMessage.setPivotX(0.0f);
            this.mTitle.setPivotX(0.0f);
        }
        this.mMessage.setPivotY(0.0f);
        this.mTitle.setPivotY(0.0f);
    }

    /* access modifiers changed from: package-private */
    public void updateFocusedState(float unfocusedSelectedStartOffset) {
        this.mIsItemSelected = this.mProgramState == 3 && this.mView.isFocused();
        ViewGroup.MarginLayoutParams containerLayoutParams = (ViewGroup.MarginLayoutParams) this.mContainer.getLayoutParams();
        int i = this.mProgramState;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 12:
                containerLayoutParams.setMarginStart(this.mDefaultContainerMarginHorizontal);
                containerLayoutParams.setMarginEnd(this.mDefaultContainerMarginHorizontal);
                this.mContainer.setScaleX(1.0f);
                this.mContainer.setScaleY(1.0f);
                this.mContainer.setAlpha(1.0f);
                break;
            case 3:
            case 4:
                float unfocusedMarginStart = ((float) this.mSelectedChannelContainerVisualOffset) + unfocusedSelectedStartOffset;
                if (this.mIsItemSelected) {
                    containerLayoutParams.setMarginStart(this.mDefaultContainerMarginHorizontal);
                    float marginEndOffset = ((float) this.mSelectedChannelMessageWidth) * (this.mContentContainerFocusedScale - 1.0f);
                    float totalWidthChange = (((float) this.mDefaultContainerMarginHorizontal) - unfocusedMarginStart) + marginEndOffset;
                    if (Math.abs(totalWidthChange) < ((float) this.mSelectedChannelInfoViewIgnoredWidthChangeThreshold)) {
                        marginEndOffset -= totalWidthChange;
                    }
                    containerLayoutParams.setMarginEnd((int) (((float) this.mDefaultContainerMarginHorizontal) + marginEndOffset));
                    this.mContainer.setScaleX(this.mContentContainerFocusedScale);
                    this.mContainer.setScaleY(this.mContentContainerFocusedScale);
                } else {
                    containerLayoutParams.setMarginStart((int) unfocusedMarginStart);
                    containerLayoutParams.setMarginEnd(this.mDefaultContainerMarginHorizontal);
                    this.mContainer.setScaleX(1.0f);
                    this.mContainer.setScaleY(1.0f);
                }
                this.mContainer.setAlpha(1.0f);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                containerLayoutParams.setMarginStart(0);
                containerLayoutParams.setMarginEnd(0);
                this.mContainer.setScaleX(1.0f);
                this.mContainer.setScaleY(1.0f);
                this.mContainer.setAlpha(0.0f);
                break;
            case 9:
            case 11:
                String valueOf = String.valueOf(ProgramStateUtil.stateToString(i));
                throw new IllegalStateException(valueOf.length() != 0 ? "Unsupported Watch Next program state: ".concat(valueOf) : new String("Unsupported Watch Next program state: "));
        }
        this.mContainer.setLayoutParams(containerLayoutParams);
        int i2 = this.mProgramState;
        if (i2 == 0 || i2 == 12 || i2 == 1 || i2 == 7) {
            this.mIcon.setAlpha(this.mIconDimmedAlpha);
            this.mTitle.setTextColor(this.mDimmedColor);
            this.mMessage.setTextColor(this.mDimmedColor);
        } else if (this.mIsItemSelected) {
            this.mIcon.setAlpha(this.mIconFocusedAlpha);
            this.mTitle.setTextColor(this.mFocusedColor);
            this.mMessage.setTextColor(this.mFocusedColor);
        } else {
            this.mIcon.setAlpha(this.mIconUnfocusedAlpha);
            this.mTitle.setTextColor(this.mUnfocusedColor);
            this.mMessage.setTextColor(this.mUnfocusedColor);
        }
    }
}
