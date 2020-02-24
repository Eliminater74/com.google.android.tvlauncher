package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.decoder.OutputBuffer;
import java.util.List;

public abstract class SubtitleOutputBuffer extends OutputBuffer implements Subtitle {
    private long subsampleOffsetUs;
    private Subtitle subtitle;

    public abstract void release();

    public void setContent(long timeUs, Subtitle subtitle2, long subsampleOffsetUs2) {
        long j;
        this.timeUs = timeUs;
        this.subtitle = subtitle2;
        if (subsampleOffsetUs2 == Long.MAX_VALUE) {
            j = this.timeUs;
        } else {
            j = subsampleOffsetUs2;
        }
        this.subsampleOffsetUs = j;
    }

    public int getEventTimeCount() {
        return this.subtitle.getEventTimeCount();
    }

    public long getEventTime(int index) {
        return this.subtitle.getEventTime(index) + this.subsampleOffsetUs;
    }

    public int getNextEventTimeIndex(long timeUs) {
        return this.subtitle.getNextEventTimeIndex(timeUs - this.subsampleOffsetUs);
    }

    public List<Cue> getCues(long timeUs) {
        return this.subtitle.getCues(timeUs - this.subsampleOffsetUs);
    }

    public void clear() {
        super.clear();
        this.subtitle = null;
    }
}
