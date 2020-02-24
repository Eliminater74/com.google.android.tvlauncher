package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public class ContainerMediaChunk extends BaseMediaChunk {
    private static final PositionHolder DUMMY_POSITION_HOLDER = new PositionHolder();
    private final int chunkCount;
    private final ChunkExtractorWrapper extractorWrapper;
    private volatile boolean loadCanceled;
    private boolean loadCompleted;
    private long nextLoadPosition;
    private final long sampleOffsetUs;

    public ContainerMediaChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long startTimeUs, long endTimeUs, long clippedStartTimeUs, long clippedEndTimeUs, long chunkIndex, int chunkCount2, long sampleOffsetUs2, ChunkExtractorWrapper extractorWrapper2) {
        super(dataSource, dataSpec, trackFormat, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs, clippedStartTimeUs, clippedEndTimeUs, chunkIndex);
        this.chunkCount = chunkCount2;
        this.sampleOffsetUs = sampleOffsetUs2;
        this.extractorWrapper = extractorWrapper2;
    }

    public long getNextChunkIndex() {
        return this.chunkIndex + ((long) this.chunkCount);
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public final void cancelLoad() {
        this.loadCanceled = true;
    }

    public final void load() throws IOException, InterruptedException {
        ExtractorInput input;
        long j;
        DataSpec loadDataSpec = this.dataSpec.subrange(this.nextLoadPosition);
        try {
            input = new DefaultExtractorInput(this.dataSource, loadDataSpec.absoluteStreamPosition, this.dataSource.open(loadDataSpec));
            if (this.nextLoadPosition == 0) {
                BaseMediaChunkOutput output = getOutput();
                output.setSampleOffsetUs(this.sampleOffsetUs);
                ChunkExtractorWrapper chunkExtractorWrapper = this.extractorWrapper;
                ChunkExtractorWrapper.TrackOutputProvider trackOutputProvider = getTrackOutputProvider(output);
                long j2 = this.clippedStartTimeUs;
                long j3 = C0841C.TIME_UNSET;
                if (j2 == C0841C.TIME_UNSET) {
                    j = -9223372036854775807L;
                } else {
                    j = this.clippedStartTimeUs - this.sampleOffsetUs;
                }
                if (this.clippedEndTimeUs != C0841C.TIME_UNSET) {
                    j3 = this.clippedEndTimeUs - this.sampleOffsetUs;
                }
                chunkExtractorWrapper.init(trackOutputProvider, j, j3);
            }
            Extractor extractor = this.extractorWrapper.extractor;
            int result = 0;
            while (result == 0 && !this.loadCanceled) {
                result = extractor.read(input, DUMMY_POSITION_HOLDER);
            }
            Assertions.checkState(result != 1);
            this.nextLoadPosition = input.getPosition() - this.dataSpec.absoluteStreamPosition;
            Util.closeQuietly(this.dataSource);
            this.loadCompleted = true;
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public ChunkExtractorWrapper.TrackOutputProvider getTrackOutputProvider(BaseMediaChunkOutput baseMediaChunkOutput) {
        return baseMediaChunkOutput;
    }
}
