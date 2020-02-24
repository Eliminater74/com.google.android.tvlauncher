package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.offline.DownloadService;

final /* synthetic */ class DownloadService$ForegroundNotificationUpdater$$Lambda$0 implements Runnable {
    private final DownloadService.ForegroundNotificationUpdater arg$1;

    DownloadService$ForegroundNotificationUpdater$$Lambda$0(DownloadService.ForegroundNotificationUpdater foregroundNotificationUpdater) {
        this.arg$1 = foregroundNotificationUpdater;
    }

    public void run() {
        this.arg$1.bridge$lambda$0$DownloadService$ForegroundNotificationUpdater();
    }
}
