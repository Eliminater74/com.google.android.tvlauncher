package com.google.android.tvlauncher.appsview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.leanback.widget.VerticalGridView;

public class LaunchItemsRowView extends LinearLayout {
    public static final int MAX_APPS = 4;

    public LaunchItemsRowView(Context context) {
        this(context, null);
    }

    public LaunchItemsRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LaunchItemsRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LaunchItemsRowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOneTimeFocusPosition(int position) {
        if (position < getChildCount()) {
            getChildAt(position).requestFocus();
        }
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        View focusedChild;
        ViewGroup focusedRow = (ViewGroup) ((VerticalGridView) getParent()).getFocusedChild();
        if (focusedRow == null || (focusedChild = focusedRow.getFocusedChild()) == null) {
            return super.requestFocus(direction, previouslyFocusedRect);
        }
        int colIndex = focusedRow.indexOfChild(focusedChild);
        if (colIndex < getChildCount()) {
            getChildAt(colIndex).requestFocus();
        } else {
            getChildAt(getChildCount() - 1).requestFocus();
        }
        return true;
    }

    public void recycle() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof BannerView) {
                ((BannerView) child).recycle();
            }
        }
    }
}
