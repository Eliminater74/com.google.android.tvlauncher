package com.google.android.exoplayer2.text.pgs;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import java.util.List;

final class PgsSubtitle implements Subtitle {
    private final List<Cue> cues;

    public PgsSubtitle(List<Cue> cues2) {
        this.cues = cues2;
    }

    public int getNextEventTimeIndex(long timeUs) {
        return -1;
    }

    public int getEventTimeCount() {
        return 1;
    }

    public long getEventTime(int index) {
        return 0;
    }

    public List<Cue> getCues(long timeUs) {
        return this.cues;
    }
}
