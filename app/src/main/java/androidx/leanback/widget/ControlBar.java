package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;

class ControlBar extends LinearLayout {
    private int mChildMarginFromCenter;
    boolean mDefaultFocusToMiddle = true;
    int mLastFocusIndex = -1;
    private OnChildFocusedListener mOnChildFocusedListener;

    public interface OnChildFocusedListener {
        void onChildFocusedListener(View view, View view2);
    }

    public ControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ControlBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: package-private */
    public void setDefaultFocusToMiddle(boolean defaultFocusToMiddle) {
        this.mDefaultFocusToMiddle = defaultFocusToMiddle;
    }

    /* access modifiers changed from: package-private */
    public int getDefaultFocusIndex() {
        if (this.mDefaultFocusToMiddle) {
            return getChildCount() / 2;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int index;
        if (getChildCount() > 0) {
            int i = this.mLastFocusIndex;
            if (i < 0 || i >= getChildCount()) {
                index = getDefaultFocusIndex();
            } else {
                index = this.mLastFocusIndex;
            }
            if (getChildAt(index).requestFocus(direction, previouslyFocusedRect)) {
                return true;
            }
        }
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (direction == 33 || direction == 130) {
            int i = this.mLastFocusIndex;
            if (i >= 0 && i < getChildCount()) {
                views.add(getChildAt(this.mLastFocusIndex));
            } else if (getChildCount() > 0) {
                views.add(getChildAt(getDefaultFocusIndex()));
            }
        } else {
            super.addFocusables(views, direction, focusableMode);
        }
    }

    public void setOnChildFocusedListener(OnChildFocusedListener listener) {
        this.mOnChildFocusedListener = listener;
    }

    public void setChildMarginFromCenter(int marginFromCenter) {
        this.mChildMarginFromCenter = marginFromCenter;
    }

    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        this.mLastFocusIndex = indexOfChild(child);
        OnChildFocusedListener onChildFocusedListener = this.mOnChildFocusedListener;
        if (onChildFocusedListener != null) {
            onChildFocusedListener.onChildFocusedListener(child, focused);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mChildMarginFromCenter > 0) {
            int totalExtraMargin = 0;
            for (int i = 0; i < getChildCount() - 1; i++) {
                View first = getChildAt(i);
                View second = getChildAt(i + 1);
                int marginStart = this.mChildMarginFromCenter - ((first.getMeasuredWidth() + second.getMeasuredWidth()) / 2);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) second.getLayoutParams();
                lp.setMarginStart(marginStart);
                second.setLayoutParams(lp);
                totalExtraMargin += marginStart - lp.getMarginStart();
            }
            setMeasuredDimension(getMeasuredWidth() + totalExtraMargin, getMeasuredHeight());
        }
    }
}
