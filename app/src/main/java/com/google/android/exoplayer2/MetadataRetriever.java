package com.google.android.exoplayer2;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.Assertions;

public interface MetadataRetriever {

    Timeline getCurrentTimeline();

    Object getFrameAtTime(long j, FrameCallback frameCallback);

    Object getFrameAtTime(long j, Options options, FrameCallback frameCallback);

    Object getMetadata(long j, MetadataCallback metadataCallback);

    Object getMetadata(MetadataCallback metadataCallback);

    SeekParameters getSeekParameters();

    void setSeekParameters(SeekParameters seekParameters);

    long getWindowDurationMs();

    int getWindowIndex();

    void setWindowIndex(int i);

    void prepare(MediaSource mediaSource, MediaSourceCallback mediaSourceCallback);

    void release();

    void stop();

    public interface FrameCallback {
        void onBitmapAvailable(Object obj, Bitmap bitmap, Timeline timeline, int i, long j);

        void onBitmapUnavailable(Object obj, Exception exc);
    }

    public interface MediaSourceCallback {
        void onTimelineUnavailable(Exception exc);

        void onTimelineUpdated(Timeline timeline, @Nullable Object obj, int i);
    }

    public interface MetadataCallback {
        void onMetadataAvailable(Object obj, TrackGroupArray trackGroupArray, Timeline timeline, int i, int i2);

        void onMetadataUnavailable(Object obj, Exception exc);
    }

    public static final class Options {
        public final int height;
        public final int width;

        private Options(int width2, int height2) {
            this.width = width2;
            this.height = height2;
        }

        public static final class Builder {
            private int height = -1;
            private int width = -1;

            public Builder setOutputWidthHeight(int width2, int height2) {
                boolean z = false;
                Assertions.checkArgument(width2 > 0 || width2 == -1);
                if (height2 > 0 || height2 == -1) {
                    z = true;
                }
                Assertions.checkArgument(z);
                this.width = width2;
                this.height = height2;
                return this;
            }

            public Options build() {
                return new Options(this.width, this.height);
            }
        }
    }
}
