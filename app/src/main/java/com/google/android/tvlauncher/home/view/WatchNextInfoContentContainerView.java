package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.tvlauncher.C1188R;

public class WatchNextInfoContentContainerView extends ViewGroup {
    private View mMessage;
    private int mSelectedCardHeight;
    private int mSelectedMessageWidth;
    private View mTitleIconContainer;

    public WatchNextInfoContentContainerView(Context context) {
        super(context);
    }

    public WatchNextInfoContentContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WatchNextInfoContentContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WatchNextInfoContentContainerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleIconContainer = findViewById(C1188R.C1191id.watch_next_info_icon_title_container);
        this.mMessage = findViewById(C1188R.C1191id.watch_next_info_message);
        Resources res = getContext().getResources();
        this.mSelectedMessageWidth = res.getDimensionPixelSize(C1188R.dimen.watch_next_info_card_selected_message_width);
        this.mSelectedCardHeight = res.getDimensionPixelSize(C1188R.dimen.program_default_height);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mTitleIconContainer.measure(widthMeasureSpec, heightMeasureSpec);
        int titleIconContainerHeight = this.mTitleIconContainer.getMeasuredHeight();
        int messageWidthSpec = View.MeasureSpec.makeMeasureSpec(this.mSelectedMessageWidth, 1073741824);
        int messageMarginTop = ((ViewGroup.MarginLayoutParams) this.mMessage.getLayoutParams()).topMargin;
        this.mMessage.measure(messageWidthSpec, View.MeasureSpec.makeMeasureSpec((this.mSelectedCardHeight - titleIconContainerHeight) - messageMarginTop, Integer.MIN_VALUE));
        int childState = combineMeasuredStates(combineMeasuredStates(0, this.mTitleIconContainer.getMeasuredState()), this.mMessage.getMeasuredState());
        setMeasuredDimension(resolveSizeAndState(this.mTitleIconContainer.getMeasuredWidth(), widthMeasureSpec, childState), resolveSizeAndState(titleIconContainerHeight + messageMarginTop + ((int) (((float) this.mMessage.getMeasuredHeight()) * this.mMessage.getScaleX())), heightMeasureSpec, childState << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int titleHeight = this.mTitleIconContainer.getMeasuredHeight();
        View view = this.mTitleIconContainer;
        view.layout(0, 0, view.getMeasuredWidth(), titleHeight);
        int containerWidth = getMeasuredWidth();
        int messageTop = ((ViewGroup.MarginLayoutParams) this.mMessage.getLayoutParams()).topMargin + titleHeight;
        int messageWidth = this.mMessage.getMeasuredWidth();
        int messageHeight = this.mMessage.getMeasuredHeight();
        if (getLayoutDirection() == 1) {
            this.mMessage.layout(containerWidth - messageWidth, messageTop, containerWidth, messageTop + messageHeight);
        } else {
            this.mMessage.layout(0, messageTop, messageWidth, messageTop + messageHeight);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ViewGroup.MarginLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof ViewGroup.MarginLayoutParams;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new ViewGroup.MarginLayoutParams(lp);
    }

    public CharSequence getAccessibilityClassName() {
        return WatchNextInfoContentContainerView.class.getName();
    }
}
