package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.graphics.ColorFilter;
import android.support.annotation.Nullable;
import android.support.p001v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.ColorUtils;

public class AddFavoriteAppCardView extends LinearLayout implements FavoriteLaunchItemView {
    private ImageView mBannerImage;
    private float mBannerImageCurrentDimmingFactor;
    private float mBannerImageDimmedFactorValue;
    private TextView mTitleView;
    private int mTitleVisibility;

    public AddFavoriteAppCardView(Context context) {
        this(context, null);
    }

    public AddFavoriteAppCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddFavoriteAppCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AddFavoriteAppCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(C1188R.C1191id.app_title);
        this.mTitleVisibility = this.mTitleView.getVisibility();
        this.mBannerImage = (ImageView) findViewById(C1188R.C1191id.banner_image);
        this.mBannerImageDimmedFactorValue = Util.getFloat(getResources(), C1188R.dimen.unfocused_channel_dimming_factor);
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    public int getTitleVisibility() {
        return this.mTitleVisibility;
    }

    public void setTitleVisibility(int titleVisibility) {
        this.mTitleVisibility = titleVisibility;
        this.mTitleView.setVisibility(titleVisibility);
    }

    public ImageView getBannerImage() {
        return this.mBannerImage;
    }

    public void setBannerImageDimmed(boolean dimmed) {
        if (dimmed) {
            this.mBannerImageCurrentDimmingFactor = this.mBannerImageDimmedFactorValue;
            this.mBannerImage.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, this.mBannerImageCurrentDimmingFactor));
            return;
        }
        this.mBannerImageCurrentDimmingFactor = 0.0f;
        this.mBannerImage.setColorFilter((ColorFilter) null);
    }

    public float getBannerImageDimmingFactor() {
        return this.mBannerImageCurrentDimmingFactor;
    }
}
