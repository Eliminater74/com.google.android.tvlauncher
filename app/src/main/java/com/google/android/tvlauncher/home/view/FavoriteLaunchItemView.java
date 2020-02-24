package com.google.android.tvlauncher.home.view;

import android.widget.ImageView;
import android.widget.TextView;

public interface FavoriteLaunchItemView {
    ImageView getBannerImage();

    float getBannerImageDimmingFactor();

    TextView getTitleView();

    int getTitleVisibility();

    void setBannerImageDimmed(boolean z);
}
