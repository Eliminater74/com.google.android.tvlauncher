package com.google.android.tvlauncher.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.leanback.C0364R;

import com.google.android.tvlauncher.C1188R;

public class SearchOrb extends FrameLayout {
    private View mFocusIndicator;
    private float mFocusedZoom;
    private ImageView mIcon;
    private int mIndicatorFocusedColor;
    private int mIndicatorUnfocusedColor;

    public SearchOrb(@NonNull Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, com.google.android.tvlauncher.view.SearchOrb, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public SearchOrb(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources resources = getContext().getResources();
        View rootView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C1188R.layout.search_orb_view, (ViewGroup) this, true);
        this.mFocusIndicator = rootView.findViewById(C1188R.C1191id.button_background);
        this.mIcon = (ImageView) rootView.findViewById(C1188R.C1191id.search_icon);
        this.mFocusedZoom = resources.getFraction(C0364R.fraction.lb_search_orb_focused_zoom, 1, 1);
        this.mIndicatorUnfocusedColor = ContextCompat.getColor(context, C1188R.color.search_orb_bg_dim_color);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mFocusIndicator.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
        this.mFocusIndicator.setClipToOutline(true);
    }

    public void setFocusedOrbColor(int color) {
        this.mIndicatorFocusedColor = color;
    }

    public View getFocusIndicator() {
        return this.mFocusIndicator;
    }

    /* access modifiers changed from: package-private */
    public void bind() {
        if (hasFocus()) {
            this.mFocusIndicator.setBackgroundColor(this.mIndicatorFocusedColor);
            setScaleX(this.mFocusedZoom);
            setScaleY(this.mFocusedZoom);
            return;
        }
        this.mFocusIndicator.setBackgroundColor(this.mIndicatorUnfocusedColor);
        setScaleX(1.0f);
        setScaleY(1.0f);
    }

    @VisibleForTesting
    public Drawable getOrbIcon() {
        return this.mIcon.getDrawable();
    }

    public void setOrbIcon(Drawable icon) {
        this.mIcon.setImageDrawable(icon);
    }
}
