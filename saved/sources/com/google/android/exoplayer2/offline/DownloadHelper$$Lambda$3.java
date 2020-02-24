package com.google.android.exoplayer2.offline;

import java.io.IOException;

final /* synthetic */ class DownloadHelper$$Lambda$3 implements Runnable {
    private final DownloadHelper arg$1;
    private final IOException arg$2;

    DownloadHelper$$Lambda$3(DownloadHelper downloadHelper, IOException iOException) {
        this.arg$1 = downloadHelper;
        this.arg$2 = iOException;
    }

    public void run() {
        this.arg$1.lambda$onMediaPreparationFailed$3$DownloadHelper(this.arg$2);
    }
}
