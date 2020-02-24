package com.google.android.tvlauncher.home;

import android.view.View;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.home.FavoriteLaunchItemsAdapter;
import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;

final /* synthetic */ class FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$0 implements ContextMenu.OnItemClickListener {
    private final FavoriteLaunchItemsAdapter.AppViewHolder arg$1;
    private final LaunchItem arg$2;
    private final View arg$3;

    FavoriteLaunchItemsAdapter$AppViewHolder$$Lambda$0(FavoriteLaunchItemsAdapter.AppViewHolder appViewHolder, LaunchItem launchItem, View view) {
        this.arg$1 = appViewHolder;
        this.arg$2 = launchItem;
        this.arg$3 = view;
    }

    public void onItemClick(ContextMenuItem contextMenuItem) {
        this.arg$1.mo21377x708b30d3(this.arg$2, this.arg$3, contextMenuItem);
    }
}
