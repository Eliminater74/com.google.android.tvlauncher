package com.google.android.tvlauncher.appsview;

import android.view.View;
import com.google.android.tvlauncher.appsview.RowListAdapter;
import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;

final /* synthetic */ class RowListAdapter$AppViewHolder$$Lambda$0 implements ContextMenu.OnItemClickListener {
    private final RowListAdapter.AppViewHolder arg$1;
    private final LaunchItem arg$2;
    private final View arg$3;

    RowListAdapter$AppViewHolder$$Lambda$0(RowListAdapter.AppViewHolder appViewHolder, LaunchItem launchItem, View view) {
        this.arg$1 = appViewHolder;
        this.arg$2 = launchItem;
        this.arg$3 = view;
    }

    public void onItemClick(ContextMenuItem contextMenuItem) {
        this.arg$1.lambda$createOnItemClickListener$0$RowListAdapter$AppViewHolder(this.arg$2, this.arg$3, contextMenuItem);
    }
}
