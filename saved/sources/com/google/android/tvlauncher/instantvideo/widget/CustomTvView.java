package com.google.android.tvlauncher.instantvideo.widget;

import android.content.Context;
import android.media.tv.TvView;
import android.util.AttributeSet;

public class CustomTvView extends TvView {
    public CustomTvView(Context context) {
        super(context);
    }

    public CustomTvView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTvView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (getChildCount() > 0) {
            getChildAt(0).layout(0, 0, right - left, bottom - top);
        }
    }
}
