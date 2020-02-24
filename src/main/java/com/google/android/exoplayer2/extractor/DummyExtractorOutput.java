package com.google.android.exoplayer2.extractor;

public final class DummyExtractorOutput implements ExtractorOutput {
    public TrackOutput track(int id, int type) {
        return new DummyTrackOutput();
    }

    public void endTracks() {
    }

    public void seekMap(SeekMap seekMap) {
    }
}
