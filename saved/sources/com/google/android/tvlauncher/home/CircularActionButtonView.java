package com.google.android.tvlauncher.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.support.annotation.Nullable;
import android.support.p004v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.ScaleFocusHandler;

public class CircularActionButtonView extends AppCompatImageView {
    public CircularActionButtonView(Context context) {
        super(context);
    }

    public CircularActionButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularActionButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            Resources r = getResources();
            new ScaleFocusHandler(r.getInteger(C1188R.integer.channel_action_button_focused_animation_duration_ms), r.getFraction(C1188R.fraction.channel_action_button_focused_scale, 1, 1), r.getDimension(C1188R.dimen.channel_action_button_focused_elevation)).setView(this);
        }
        setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
        setClipToOutline(true);
    }
}
