package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

final class TrackSampleTable {
    public final long durationUs;
    public final int[] flags;
    public final int maximumSize;
    public final long[] offsets;
    public final int sampleCount;
    public final int[] sizes;
    public final long[] timestampsUs;
    public final Track track;

    public TrackSampleTable(Track track2, long[] offsets2, int[] sizes2, int maximumSize2, long[] timestampsUs2, int[] flags2, long durationUs2) {
        boolean z = false;
        Assertions.checkArgument(sizes2.length == timestampsUs2.length);
        Assertions.checkArgument(offsets2.length == timestampsUs2.length);
        Assertions.checkArgument(flags2.length == timestampsUs2.length ? true : z);
        this.track = track2;
        this.offsets = offsets2;
        this.sizes = sizes2;
        this.maximumSize = maximumSize2;
        this.timestampsUs = timestampsUs2;
        this.flags = flags2;
        this.durationUs = durationUs2;
        this.sampleCount = offsets2.length;
        if (flags2.length > 0) {
            int length = flags2.length - 1;
            flags2[length] = flags2[length] | 536870912;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int */
    public int getIndexOfEarlierOrEqualSynchronizationSample(long timeUs) {
        for (int i = Util.binarySearchFloor(this.timestampsUs, timeUs, true, false); i >= 0; i--) {
            if ((this.flags[i] & 1) != 0) {
                return i;
            }
        }
        return -1;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    public int getIndexOfLaterOrEqualSynchronizationSample(long timeUs) {
        for (int i = Util.binarySearchCeil(this.timestampsUs, timeUs, true, false); i < this.timestampsUs.length; i++) {
            if ((this.flags[i] & 1) != 0) {
                return i;
            }
        }
        return -1;
    }
}
