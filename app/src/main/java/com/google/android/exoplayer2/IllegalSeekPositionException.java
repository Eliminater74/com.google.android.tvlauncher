package com.google.android.exoplayer2;

public final class IllegalSeekPositionException extends IllegalStateException {
    public final long positionMs;
    public final Timeline timeline;
    public final int windowIndex;

    public IllegalSeekPositionException(Timeline timeline2, int windowIndex2, long positionMs2) {
        this.timeline = timeline2;
        this.windowIndex = windowIndex2;
        this.positionMs = positionMs2;
    }
}
