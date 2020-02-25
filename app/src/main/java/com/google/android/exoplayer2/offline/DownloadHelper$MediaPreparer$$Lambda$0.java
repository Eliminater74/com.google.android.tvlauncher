package com.google.android.exoplayer2.offline;

import android.os.Handler;
import android.os.Message;

final /* synthetic */ class DownloadHelper$MediaPreparer$$Lambda$0 implements Handler.Callback {
    private final DownloadHelper.MediaPreparer arg$1;

    DownloadHelper$MediaPreparer$$Lambda$0(DownloadHelper.MediaPreparer mediaPreparer) {
        this.arg$1 = mediaPreparer;
    }

    public boolean handleMessage(Message message) {
        return this.arg$1.bridge$lambda$0$DownloadHelper$MediaPreparer(message);
    }
}
