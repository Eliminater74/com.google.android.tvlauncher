package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Util;

final class MediaPeriodInfo {
    public final long contentPositionUs;
    public final long durationUs;
    public final long endPositionUs;

    /* renamed from: id */
    public final MediaSource.MediaPeriodId f73id;
    public final boolean isFinal;
    public final boolean isLastInTimelinePeriod;
    public final long startPositionUs;

    MediaPeriodInfo(MediaSource.MediaPeriodId id, long startPositionUs2, long contentPositionUs2, long endPositionUs2, long durationUs2, boolean isLastInTimelinePeriod2, boolean isFinal2) {
        this.f73id = id;
        this.startPositionUs = startPositionUs2;
        this.contentPositionUs = contentPositionUs2;
        this.endPositionUs = endPositionUs2;
        this.durationUs = durationUs2;
        this.isLastInTimelinePeriod = isLastInTimelinePeriod2;
        this.isFinal = isFinal2;
    }

    public MediaPeriodInfo copyWithStartPositionUs(long startPositionUs2) {
        if (startPositionUs2 == this.startPositionUs) {
            return this;
        }
        return new MediaPeriodInfo(this.f73id, startPositionUs2, this.contentPositionUs, this.endPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
    }

    public MediaPeriodInfo copyWithContentPositionUs(long contentPositionUs2) {
        if (contentPositionUs2 == this.contentPositionUs) {
            return this;
        }
        return new MediaPeriodInfo(this.f73id, this.startPositionUs, contentPositionUs2, this.endPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaPeriodInfo that = (MediaPeriodInfo) o;
        if (this.startPositionUs == that.startPositionUs && this.contentPositionUs == that.contentPositionUs && this.endPositionUs == that.endPositionUs && this.durationUs == that.durationUs && this.isLastInTimelinePeriod == that.isLastInTimelinePeriod && this.isFinal == that.isFinal && Util.areEqual(this.f73id, that.f73id)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((17 * 31) + this.f73id.hashCode()) * 31) + ((int) this.startPositionUs)) * 31) + ((int) this.contentPositionUs)) * 31) + ((int) this.endPositionUs)) * 31) + ((int) this.durationUs)) * 31) + (this.isLastInTimelinePeriod ? 1 : 0)) * 31) + (this.isFinal ? 1 : 0);
    }
}
