package com.google.android.exoplayer2.offline;

import android.support.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public final class ActionFileUpgradeUtil {

    public interface DownloadIdProvider {
        String getId(DownloadRequest downloadRequest);
    }

    private ActionFileUpgradeUtil() {
    }

    public static void upgradeAndDelete(File actionFilePath, @Nullable DownloadIdProvider downloadIdProvider, DefaultDownloadIndex downloadIndex, boolean deleteOnFailure) throws IOException {
        ActionFile actionFile = new ActionFile(actionFilePath);
        if (actionFile.exists()) {
            boolean success = false;
            try {
                for (DownloadRequest request : actionFile.load()) {
                    if (downloadIdProvider != null) {
                        request = request.copyWithId(downloadIdProvider.getId(request));
                    }
                    mergeRequest(request, downloadIndex);
                }
                success = true;
            } finally {
                if (success || deleteOnFailure) {
                    actionFile.delete();
                }
            }
        }
    }

    static void mergeRequest(DownloadRequest request, DefaultDownloadIndex downloadIndex) throws IOException {
        Download download;
        DownloadRequest downloadRequest = request;
        DefaultDownloadIndex defaultDownloadIndex = downloadIndex;
        Download download2 = defaultDownloadIndex.getDownload(downloadRequest.f89id);
        if (download2 != null) {
            download = DownloadManager.mergeRequest(download2, downloadRequest, download2.stopReason);
        } else {
            long nowMs = System.currentTimeMillis();
            download = new Download(request, 0, nowMs, nowMs, -1, 0, 0);
        }
        defaultDownloadIndex.putDownload(download);
    }
}
