package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class SingleSampleMediaChunk extends BaseMediaChunk {
    private boolean loadCompleted;
    private long nextLoadPosition;
    private final Format sampleFormat;
    private final int trackType;

    public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long startTimeUs, long endTimeUs, long chunkIndex, int trackType2, Format sampleFormat2) {
        super(dataSource, dataSpec, trackFormat, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs, C0841C.TIME_UNSET, C0841C.TIME_UNSET, chunkIndex);
        this.trackType = trackType2;
        this.sampleFormat = sampleFormat2;
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public void cancelLoad() {
    }

    /* JADX INFO: finally extract failed */
    public void load() throws IOException, InterruptedException {
        try {
            long length = this.dataSource.open(this.dataSpec.subrange(this.nextLoadPosition));
            if (length != -1) {
                length += this.nextLoadPosition;
            }
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, this.nextLoadPosition, length);
            BaseMediaChunkOutput output = getOutput();
            output.setSampleOffsetUs(0);
            TrackOutput trackOutput = output.track(0, this.trackType);
            trackOutput.format(this.sampleFormat);
            for (int result = 0; result != -1; result = trackOutput.sampleData(defaultExtractorInput, Integer.MAX_VALUE, true)) {
                this.nextLoadPosition += (long) result;
            }
            int sampleSize = (int) this.nextLoadPosition;
            int i = sampleSize;
            trackOutput.sampleMetadata(this.startTimeUs, 1, sampleSize, 0, null);
            Util.closeQuietly(this.dataSource);
            this.loadCompleted = true;
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
            throw th;
        }
    }
}
