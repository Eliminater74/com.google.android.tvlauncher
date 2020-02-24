package com.google.android.tvlauncher.home;

import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;

final /* synthetic */ class ChannelRowController$$Lambda$0 implements ContextMenu.OnItemClickListener {
    private final ChannelRowController arg$1;

    ChannelRowController$$Lambda$0(ChannelRowController channelRowController) {
        this.arg$1 = channelRowController;
    }

    public void onItemClick(ContextMenuItem contextMenuItem) {
        this.arg$1.lambda$showAccessibilityMenu$0$ChannelRowController(contextMenuItem);
    }
}
