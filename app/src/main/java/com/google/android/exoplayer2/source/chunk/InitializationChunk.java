package com.google.android.exoplayer2.source.chunk;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

public final class InitializationChunk extends Chunk {
    private static final PositionHolder DUMMY_POSITION_HOLDER = new PositionHolder();
    private final ChunkExtractorWrapper extractorWrapper;
    private volatile boolean loadCanceled;
    private long nextLoadPosition;

    public InitializationChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, @Nullable Object trackSelectionData, ChunkExtractorWrapper extractorWrapper2) {
        super(dataSource, dataSpec, 2, trackFormat, trackSelectionReason, trackSelectionData, C0841C.TIME_UNSET, C0841C.TIME_UNSET);
        this.extractorWrapper = extractorWrapper2;
    }

    public void cancelLoad() {
        this.loadCanceled = true;
    }

    public void load() throws IOException, InterruptedException {
        ExtractorInput input;
        DataSpec loadDataSpec = this.dataSpec.subrange(this.nextLoadPosition);
        try {
            input = new DefaultExtractorInput(this.dataSource, loadDataSpec.absoluteStreamPosition, this.dataSource.open(loadDataSpec));
            if (this.nextLoadPosition == 0) {
                this.extractorWrapper.init(null, C0841C.TIME_UNSET, C0841C.TIME_UNSET);
            }
            Extractor extractor = this.extractorWrapper.extractor;
            int result = 0;
            while (result == 0 && !this.loadCanceled) {
                result = extractor.read(input, DUMMY_POSITION_HOLDER);
            }
            boolean z = true;
            if (result == 1) {
                z = false;
            }
            Assertions.checkState(z);
            this.nextLoadPosition = input.getPosition() - this.dataSpec.absoluteStreamPosition;
            Util.closeQuietly(this.dataSource);
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
            throw th;
        }
    }
}
