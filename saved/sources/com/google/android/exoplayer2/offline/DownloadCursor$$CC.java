package com.google.android.exoplayer2.offline;

public abstract /* synthetic */ class DownloadCursor$$CC {
    public static boolean moveToFirst$$dflt$$(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(0);
    }

    public static boolean moveToLast$$dflt$$(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(downloadCursor.getCount() - 1);
    }

    public static boolean moveToNext$$dflt$$(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(downloadCursor.getPosition() + 1);
    }

    public static boolean moveToPrevious$$dflt$$(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(downloadCursor.getPosition() - 1);
    }

    public static boolean isFirst$$dflt$$(DownloadCursor downloadCursor) {
        return downloadCursor.getPosition() == 0 && downloadCursor.getCount() != 0;
    }

    public static boolean isLast$$dflt$$(DownloadCursor downloadCursor) {
        int count = downloadCursor.getCount();
        return downloadCursor.getPosition() == count + -1 && count != 0;
    }

    public static boolean isBeforeFirst$$dflt$$(DownloadCursor downloadCursor) {
        if (downloadCursor.getCount() == 0 || downloadCursor.getPosition() == -1) {
            return true;
        }
        return false;
    }

    public static boolean isAfterLast$$dflt$$(DownloadCursor downloadCursor) {
        if (downloadCursor.getCount() == 0 || downloadCursor.getPosition() == downloadCursor.getCount()) {
            return true;
        }
        return false;
    }
}
