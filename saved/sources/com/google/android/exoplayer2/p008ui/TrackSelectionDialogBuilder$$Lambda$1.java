package com.google.android.exoplayer2.p008ui;

import android.content.DialogInterface;

/* renamed from: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder$$Lambda$1 */
final /* synthetic */ class TrackSelectionDialogBuilder$$Lambda$1 implements DialogInterface.OnClickListener {
    private final TrackSelectionDialogBuilder arg$1;
    private final TrackSelectionView arg$2;

    TrackSelectionDialogBuilder$$Lambda$1(TrackSelectionDialogBuilder trackSelectionDialogBuilder, TrackSelectionView trackSelectionView) {
        this.arg$1 = trackSelectionDialogBuilder;
        this.arg$2 = trackSelectionView;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$build$1$TrackSelectionDialogBuilder(this.arg$2, dialogInterface, i);
    }
}
