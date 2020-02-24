package com.google.android.exoplayer2.p008ui;

import com.google.android.exoplayer2.p008ui.TrackSelectionDialogBuilder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder$$Lambda$0 */
final /* synthetic */ class TrackSelectionDialogBuilder$$Lambda$0 implements TrackSelectionDialogBuilder.DialogCallback {
    private final DefaultTrackSelector arg$1;
    private final DefaultTrackSelector.Parameters arg$2;
    private final int arg$3;
    private final TrackGroupArray arg$4;

    TrackSelectionDialogBuilder$$Lambda$0(DefaultTrackSelector defaultTrackSelector, DefaultTrackSelector.Parameters parameters, int i, TrackGroupArray trackGroupArray) {
        this.arg$1 = defaultTrackSelector;
        this.arg$2 = parameters;
        this.arg$3 = i;
        this.arg$4 = trackGroupArray;
    }

    public void onTracksSelected(boolean z, List list) {
        TrackSelectionDialogBuilder.lambda$new$0$TrackSelectionDialogBuilder(this.arg$1, this.arg$2, this.arg$3, this.arg$4, z, list);
    }
}
