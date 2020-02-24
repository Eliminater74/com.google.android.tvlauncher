package com.google.android.exoplayer2.offline;

import android.support.annotation.Nullable;
import java.io.IOException;

public interface Downloader {

    public interface ProgressListener {
        void onProgress(long j, long j2, float f);
    }

    void cancel();

    void download(@Nullable ProgressListener progressListener) throws InterruptedException, IOException;

    void remove() throws InterruptedException;
}
