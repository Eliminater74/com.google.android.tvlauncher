package com.google.android.exoplayer2.source.dash.manifest;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class Period {
    public final List<AdaptationSet> adaptationSets;
    public final List<EventStream> eventStreams;
    @Nullable

    /* renamed from: id */
    public final String f97id;
    public final long startMs;

    public Period(@Nullable String id, long startMs2, List<AdaptationSet> adaptationSets2) {
        this(id, startMs2, adaptationSets2, Collections.emptyList());
    }

    public Period(@Nullable String id, long startMs2, List<AdaptationSet> adaptationSets2, List<EventStream> eventStreams2) {
        this.f97id = id;
        this.startMs = startMs2;
        this.adaptationSets = Collections.unmodifiableList(adaptationSets2);
        this.eventStreams = Collections.unmodifiableList(eventStreams2);
    }

    public int getAdaptationSetIndex(int type) {
        int adaptationCount = this.adaptationSets.size();
        for (int i = 0; i < adaptationCount; i++) {
            if (this.adaptationSets.get(i).type == type) {
                return i;
            }
        }
        return -1;
    }
}
