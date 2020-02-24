package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;

final class ConstantBitrateSeeker extends ConstantBitrateSeekMap implements Mp3Extractor.Seeker {
    public ConstantBitrateSeeker(long inputLength, long firstFramePosition, MpegAudioHeader mpegAudioHeader) {
        super(inputLength, firstFramePosition, mpegAudioHeader.bitrate, mpegAudioHeader.frameSize);
    }

    public long getTimeUs(long position) {
        return getTimeUsAtPosition(position);
    }

    public long getDataEndPosition() {
        return -1;
    }
}
