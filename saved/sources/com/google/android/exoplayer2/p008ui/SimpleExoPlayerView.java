package com.google.android.exoplayer2.p008ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.google.android.exoplayer2.SimpleExoPlayer;

@Deprecated
/* renamed from: com.google.android.exoplayer2.ui.SimpleExoPlayerView */
public final class SimpleExoPlayerView extends PlayerView {
    public SimpleExoPlayerView(Context context) {
        super(context);
    }

    public SimpleExoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleExoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Deprecated
    public static void switchTargetView(@NonNull SimpleExoPlayer player, @Nullable SimpleExoPlayerView oldPlayerView, @Nullable SimpleExoPlayerView newPlayerView) {
        PlayerView.switchTargetView(player, oldPlayerView, newPlayerView);
    }
}
