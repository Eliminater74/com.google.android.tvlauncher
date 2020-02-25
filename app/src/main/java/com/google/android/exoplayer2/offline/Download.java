package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.util.Assertions;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Download {
    public static final int FAILURE_REASON_NONE = 0;
    public static final int FAILURE_REASON_UNKNOWN = 1;
    public static final int STATE_COMPLETED = 3;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_FAILED = 4;
    public static final int STATE_QUEUED = 0;
    public static final int STATE_REMOVING = 5;
    public static final int STATE_RESTARTING = 7;
    public static final int STATE_STOPPED = 1;
    public static final int STOP_REASON_NONE = 0;
    public final long contentLength;
    public final int failureReason;
    public final DownloadRequest request;
    public final long startTimeMs;
    public final int state;
    public final int stopReason;
    public final long updateTimeMs;
    final DownloadProgress progress;

    public Download(DownloadRequest request2, int state2, long startTimeMs2, long updateTimeMs2, long contentLength2, int stopReason2, int failureReason2) {
        this(request2, state2, startTimeMs2, updateTimeMs2, contentLength2, stopReason2, failureReason2, new DownloadProgress());
    }

    public Download(DownloadRequest request2, int state2, long startTimeMs2, long updateTimeMs2, long contentLength2, int stopReason2, int failureReason2, DownloadProgress progress2) {
        Assertions.checkNotNull(progress2);
        boolean z = true;
        Assertions.checkState((failureReason2 == 0) == (state2 != 4));
        if (stopReason2 != 0) {
            Assertions.checkState((state2 == 2 || state2 == 0) ? false : z);
        }
        this.request = request2;
        this.state = state2;
        this.startTimeMs = startTimeMs2;
        this.updateTimeMs = updateTimeMs2;
        this.contentLength = contentLength2;
        this.stopReason = stopReason2;
        this.failureReason = failureReason2;
        this.progress = progress2;
    }

    public static String getStateString(int state2) {
        if (state2 == 0) {
            return "QUEUED";
        }
        if (state2 == 1) {
            return "STOPPED";
        }
        if (state2 == 2) {
            return "DOWNLOADING";
        }
        if (state2 == 3) {
            return "COMPLETED";
        }
        if (state2 == 4) {
            return "FAILED";
        }
        if (state2 == 5) {
            return "REMOVING";
        }
        if (state2 == 7) {
            return "RESTARTING";
        }
        throw new IllegalStateException();
    }

    public boolean isTerminalState() {
        int i = this.state;
        return i == 3 || i == 4;
    }

    public long getBytesDownloaded() {
        return this.progress.bytesDownloaded;
    }

    public float getPercentDownloaded() {
        return this.progress.percentDownloaded;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }
}
