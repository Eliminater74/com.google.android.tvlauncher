package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.util.Log;

public final class BaseMediaChunkOutput implements ChunkExtractorWrapper.TrackOutputProvider {
    private static final String TAG = "BaseMediaChunkOutput";
    private final SampleQueue[] sampleQueues;
    private final int[] trackTypes;

    public BaseMediaChunkOutput(int[] trackTypes2, SampleQueue[] sampleQueues2) {
        this.trackTypes = trackTypes2;
        this.sampleQueues = sampleQueues2;
    }

    public TrackOutput track(int id, int type) {
        int i = 0;
        while (true) {
            int[] iArr = this.trackTypes;
            if (i >= iArr.length) {
                StringBuilder sb = new StringBuilder(36);
                sb.append("Unmatched track of type: ");
                sb.append(type);
                Log.m26e(TAG, sb.toString());
                return new DummyTrackOutput();
            } else if (type == iArr[i]) {
                return this.sampleQueues[i];
            } else {
                i++;
            }
        }
    }

    public int[] getWriteIndices() {
        int[] writeIndices = new int[this.sampleQueues.length];
        int i = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            if (i >= sampleQueueArr.length) {
                return writeIndices;
            }
            if (sampleQueueArr[i] != null) {
                writeIndices[i] = sampleQueueArr[i].getWriteIndex();
            }
            i++;
        }
    }

    public void setSampleOffsetUs(long sampleOffsetUs) {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            if (sampleQueue != null) {
                sampleQueue.setSampleOffsetUs(sampleOffsetUs);
            }
        }
    }
}
