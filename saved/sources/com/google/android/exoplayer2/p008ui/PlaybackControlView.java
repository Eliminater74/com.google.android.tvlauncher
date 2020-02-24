package com.google.android.exoplayer2.p008ui;

import android.content.Context;
import android.util.AttributeSet;

@Deprecated
/* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView */
public class PlaybackControlView extends PlayerControlView {
    @Deprecated
    public static final ControlDispatcher DEFAULT_CONTROL_DISPATCHER = new DefaultControlDispatcher();

    @Deprecated
    /* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView$ControlDispatcher */
    public interface ControlDispatcher extends com.google.android.exoplayer2.ControlDispatcher {
    }

    @Deprecated
    /* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView$DefaultControlDispatcher */
    private static final class DefaultControlDispatcher extends com.google.android.exoplayer2.DefaultControlDispatcher implements ControlDispatcher {
        private DefaultControlDispatcher() {
        }
    }

    public PlaybackControlView(Context context) {
        super(context);
    }

    public PlaybackControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr, AttributeSet playbackAttrs) {
        super(context, attrs, defStyleAttr, playbackAttrs);
    }
}
