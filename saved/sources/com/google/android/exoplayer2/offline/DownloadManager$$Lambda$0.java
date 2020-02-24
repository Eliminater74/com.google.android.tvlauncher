package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.scheduler.RequirementsWatcher;

final /* synthetic */ class DownloadManager$$Lambda$0 implements RequirementsWatcher.Listener {
    private final DownloadManager arg$1;

    DownloadManager$$Lambda$0(DownloadManager downloadManager) {
        this.arg$1 = downloadManager;
    }

    public void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i) {
        this.arg$1.bridge$lambda$0$DownloadManager(requirementsWatcher, i);
    }
}
