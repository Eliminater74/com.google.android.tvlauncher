package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class BaseTrackSelection implements TrackSelection {
    private final long[] blacklistUntilTimes;
    private final Format[] formats;
    protected final TrackGroup group;
    private int hashCode;
    protected final int length;
    protected final int[] tracks;

    public void onDiscontinuity() {
        TrackSelection$$CC.onDiscontinuity$$dflt$$(this);
    }

    public void updateSelectedTrack(long j, long j2, long j3) {
        TrackSelection$$CC.updateSelectedTrack$$dflt$$(this, j, j2, j3);
    }

    public void updateSelectedTrack(long j, long j2, long j3, List list, MediaChunkIterator[] mediaChunkIteratorArr) {
        TrackSelection$$CC.updateSelectedTrack$$dflt$$(this, j, j2, j3, list, mediaChunkIteratorArr);
    }

    public BaseTrackSelection(TrackGroup group2, int... tracks2) {
        Assertions.checkState(tracks2.length > 0);
        this.group = (TrackGroup) Assertions.checkNotNull(group2);
        this.length = tracks2.length;
        this.formats = new Format[this.length];
        for (int i = 0; i < tracks2.length; i++) {
            this.formats[i] = group2.getFormat(tracks2[i]);
        }
        Arrays.sort(this.formats, new DecreasingBandwidthComparator());
        this.tracks = new int[this.length];
        int i2 = 0;
        while (true) {
            int i3 = this.length;
            if (i2 < i3) {
                this.tracks[i2] = group2.indexOf(this.formats[i2]);
                i2++;
            } else {
                this.blacklistUntilTimes = new long[i3];
                return;
            }
        }
    }

    public void enable() {
    }

    public void disable() {
    }

    public final TrackGroup getTrackGroup() {
        return this.group;
    }

    public final int length() {
        return this.tracks.length;
    }

    public final Format getFormat(int index) {
        return this.formats[index];
    }

    public final int getIndexInTrackGroup(int index) {
        return this.tracks[index];
    }

    public final int indexOf(Format format) {
        for (int i = 0; i < this.length; i++) {
            if (this.formats[i] == format) {
                return i;
            }
        }
        return -1;
    }

    public final int indexOf(int indexInTrackGroup) {
        for (int i = 0; i < this.length; i++) {
            if (this.tracks[i] == indexInTrackGroup) {
                return i;
            }
        }
        return -1;
    }

    public final Format getSelectedFormat() {
        return this.formats[getSelectedIndex()];
    }

    public final int getSelectedIndexInTrackGroup() {
        return this.tracks[getSelectedIndex()];
    }

    public void onPlaybackSpeed(float playbackSpeed) {
    }

    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        return queue.size();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0024 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean blacklist(int r16, long r17) {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            long r8 = android.os.SystemClock.elapsedRealtime()
            boolean r2 = r15.isBlacklisted(r1, r8)
            r3 = 0
            r10 = r2
        L_0x000d:
            int r2 = r0.length
            r4 = 0
            r11 = 1
            if (r3 >= r2) goto L_0x0022
            if (r10 != 0) goto L_0x0022
            if (r3 == r1) goto L_0x001e
            boolean r2 = r15.isBlacklisted(r3, r8)
            if (r2 != 0) goto L_0x001e
            r4 = 1
        L_0x001e:
            r10 = r4
            int r3 = r3 + 1
            goto L_0x000d
        L_0x0022:
            if (r10 != 0) goto L_0x0025
            return r4
        L_0x0025:
            long[] r12 = r0.blacklistUntilTimes
            r13 = r12[r1]
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r2 = r8
            r4 = r17
            long r2 = com.google.android.exoplayer2.util.Util.addWithOverflowDefault(r2, r4, r6)
            long r2 = java.lang.Math.max(r13, r2)
            r12[r1] = r2
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.BaseTrackSelection.blacklist(int, long):boolean");
    }

    /* access modifiers changed from: protected */
    public final boolean isBlacklisted(int index, long nowMs) {
        return this.blacklistUntilTimes[index] > nowMs;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (System.identityHashCode(this.group) * 31) + Arrays.hashCode(this.tracks);
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseTrackSelection other = (BaseTrackSelection) obj;
        if (this.group != other.group || !Arrays.equals(this.tracks, other.tracks)) {
            return false;
        }
        return true;
    }

    private static final class DecreasingBandwidthComparator implements Comparator<Format> {
        private DecreasingBandwidthComparator() {
        }

        public int compare(Format a, Format b) {
            return b.bitrate - a.bitrate;
        }
    }
}
