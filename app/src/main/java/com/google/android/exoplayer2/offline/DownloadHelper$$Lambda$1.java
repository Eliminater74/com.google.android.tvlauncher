package com.google.android.exoplayer2.offline;

final /* synthetic */ class DownloadHelper$$Lambda$1 implements Runnable {
    private final DownloadHelper arg$1;
    private final DownloadHelper.Callback arg$2;

    DownloadHelper$$Lambda$1(DownloadHelper downloadHelper, DownloadHelper.Callback callback) {
        this.arg$1 = downloadHelper;
        this.arg$2 = callback;
    }

    public void run() {
        this.arg$1.lambda$prepare$1$DownloadHelper(this.arg$2);
    }
}
