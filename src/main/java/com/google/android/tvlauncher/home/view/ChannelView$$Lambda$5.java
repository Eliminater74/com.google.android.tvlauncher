package com.google.android.tvlauncher.home.view;

import android.view.View;
import android.view.ViewTreeObserver;

final /* synthetic */ class ChannelView$$Lambda$5 implements ViewTreeObserver.OnGlobalFocusChangeListener {
    private final ChannelView arg$1;

    ChannelView$$Lambda$5(ChannelView channelView) {
        this.arg$1 = channelView;
    }

    public void onGlobalFocusChanged(View view, View view2) {
        this.arg$1.lambda$onFinishInflate$5$ChannelView(view, view2);
    }
}
