package com.google.android.exoplayer2.source.chunk;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;

import java.util.List;
import java.util.Map;

public abstract class Chunk implements Loader.Loadable {
    public final DataSpec dataSpec;
    public final long endTimeUs;
    public final long startTimeUs;
    public final Format trackFormat;
    @Nullable
    public final Object trackSelectionData;
    public final int trackSelectionReason;
    public final int type;
    protected final StatsDataSource dataSource;

    public Chunk(DataSource dataSource2, DataSpec dataSpec2, int type2, Format trackFormat2, int trackSelectionReason2, @Nullable Object trackSelectionData2, long startTimeUs2, long endTimeUs2) {
        this.dataSource = new StatsDataSource(dataSource2);
        this.dataSpec = (DataSpec) Assertions.checkNotNull(dataSpec2);
        this.type = type2;
        this.trackFormat = trackFormat2;
        this.trackSelectionReason = trackSelectionReason2;
        this.trackSelectionData = trackSelectionData2;
        this.startTimeUs = startTimeUs2;
        this.endTimeUs = endTimeUs2;
    }

    public final long getDurationUs() {
        return this.endTimeUs - this.startTimeUs;
    }

    public final long bytesLoaded() {
        return this.dataSource.getBytesRead();
    }

    public final Uri getUri() {
        return this.dataSource.getLastOpenedUri();
    }

    public final Map<String, List<String>> getResponseHeaders() {
        return this.dataSource.getLastResponseHeaders();
    }
}
