package com.google.android.exoplayer2.offline;

import android.os.Handler;
import android.os.Message;

final /* synthetic */ class DownloadManager$$Lambda$1 implements Handler.Callback {
    private final DownloadManager arg$1;

    DownloadManager$$Lambda$1(DownloadManager downloadManager) {
        this.arg$1 = downloadManager;
    }

    public boolean handleMessage(Message message) {
        return this.arg$1.bridge$lambda$1$DownloadManager(message);
    }
}
