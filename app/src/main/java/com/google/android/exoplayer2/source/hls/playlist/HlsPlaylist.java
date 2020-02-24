package com.google.android.exoplayer2.source.hls.playlist;

import com.google.android.exoplayer2.offline.FilterableManifest;
import java.util.Collections;
import java.util.List;

public abstract class HlsPlaylist implements FilterableManifest<HlsPlaylist> {
    public final String baseUri;
    public final boolean hasIndependentSegments;
    public final List<String> tags;

    protected HlsPlaylist(String baseUri2, List<String> tags2, boolean hasIndependentSegments2) {
        this.baseUri = baseUri2;
        this.tags = Collections.unmodifiableList(tags2);
        this.hasIndependentSegments = hasIndependentSegments2;
    }
}
