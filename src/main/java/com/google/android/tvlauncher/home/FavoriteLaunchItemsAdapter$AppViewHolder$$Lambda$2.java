package com.google.android.tvlauncher.home;

import com.google.android.tvlauncher.home.FavoriteLaunchItemsAdapter;
import com.google.android.tvlauncher.util.ContextMenu;

final /* synthetic */ class FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$2 implements ContextMenu.OnDismissListener {
    private final FavoriteLaunchItemsAdapter.AppViewHolder arg$1;

    FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$2(FavoriteLaunchItemsAdapter.AppViewHolder appViewHolder) {
        this.arg$1 = appViewHolder;
    }

    public void onDismiss() {
        this.arg$1.onExitEditModeView();
    }
}
