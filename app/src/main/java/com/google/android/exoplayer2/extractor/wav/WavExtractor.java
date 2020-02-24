package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;

public final class WavExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = WavExtractor$$Lambda$0.$instance;
    private static final int MAX_INPUT_SIZE = 32768;
    private int bytesPerFrame;
    private ExtractorOutput extractorOutput;
    private int pendingBytes;
    private TrackOutput trackOutput;
    private WavHeader wavHeader;

    static final /* synthetic */ Extractor[] lambda$static$0$WavExtractor() {
        return new Extractor[]{new WavExtractor()};
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return WavHeaderReader.peek(input) != null;
    }

    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
        this.trackOutput = output.track(0, 1);
        this.wavHeader = null;
        output.endTracks();
    }

    public void seek(long position, long timeUs) {
        this.pendingBytes = 0;
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        ExtractorInput extractorInput = input;
        if (this.wavHeader == null) {
            this.wavHeader = WavHeaderReader.peek(input);
            WavHeader wavHeader2 = this.wavHeader;
            if (wavHeader2 != null) {
                this.trackOutput.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_RAW, null, wavHeader2.getBitrate(), 32768, this.wavHeader.getNumChannels(), this.wavHeader.getSampleRateHz(), this.wavHeader.getEncoding(), null, null, 0, null));
                this.bytesPerFrame = this.wavHeader.getBytesPerFrame();
            } else {
                throw new ParserException("Unsupported or unrecognized wav header.");
            }
        }
        if (!this.wavHeader.hasDataBounds()) {
            WavHeaderReader.skipToData(extractorInput, this.wavHeader);
            this.extractorOutput.seekMap(this.wavHeader);
        }
        long dataLimit = this.wavHeader.getDataLimit();
        Assertions.checkState(dataLimit != -1);
        long bytesLeft = dataLimit - input.getPosition();
        if (bytesLeft <= 0) {
            return -1;
        }
        int bytesAppended = this.trackOutput.sampleData(extractorInput, (int) Math.min((long) (32768 - this.pendingBytes), bytesLeft), true);
        if (bytesAppended != -1) {
            this.pendingBytes += bytesAppended;
        }
        int pendingFrames = this.pendingBytes / this.bytesPerFrame;
        if (pendingFrames > 0) {
            long timeUs = this.wavHeader.getTimeUs(input.getPosition() - ((long) this.pendingBytes));
            int size = this.bytesPerFrame * pendingFrames;
            this.pendingBytes -= size;
            this.trackOutput.sampleMetadata(timeUs, 1, size, this.pendingBytes, null);
        }
        if (bytesAppended == -1) {
            return -1;
        }
        return 0;
    }
}
