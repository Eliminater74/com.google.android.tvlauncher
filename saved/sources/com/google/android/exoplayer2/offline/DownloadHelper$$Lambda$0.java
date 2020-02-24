package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.trackselection.TrackSelector;

final /* synthetic */ class DownloadHelper$$Lambda$0 implements TrackSelector.InvalidationListener {
    static final TrackSelector.InvalidationListener $instance = new DownloadHelper$$Lambda$0();

    private DownloadHelper$$Lambda$0() {
    }

    public void onTrackSelectionsInvalidated() {
        DownloadHelper.lambda$new$0$DownloadHelper();
    }
}
