package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.extractor.ts.DvbSubtitleReader */
public final class DvbSubtitleReader implements ElementaryStreamReader {
    private final TrackOutput[] outputs;
    private final List<TsPayloadReader.DvbSubtitleInfo> subtitleInfos;
    private int bytesToCheck;
    private int sampleBytesWritten;
    private long sampleTimeUs;
    private boolean writingSample;

    public DvbSubtitleReader(List<TsPayloadReader.DvbSubtitleInfo> subtitleInfos2) {
        this.subtitleInfos = subtitleInfos2;
        this.outputs = new TrackOutput[subtitleInfos2.size()];
    }

    public void seek() {
        this.writingSample = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        for (int i = 0; i < this.outputs.length; i++) {
            TsPayloadReader.DvbSubtitleInfo subtitleInfo = this.subtitleInfos.get(i);
            idGenerator.generateNewId();
            TrackOutput output = extractorOutput.track(idGenerator.getTrackId(), 3);
            output.format(Format.createImageSampleFormat(idGenerator.getFormatId(), MimeTypes.APPLICATION_DVBSUBS, null, -1, 0, Collections.singletonList(subtitleInfo.initializationData), subtitleInfo.language, null));
            this.outputs[i] = output;
        }
    }

    public void packetStarted(long pesTimeUs, int flags) {
        if ((flags & 4) != 0) {
            this.writingSample = true;
            this.sampleTimeUs = pesTimeUs;
            this.sampleBytesWritten = 0;
            this.bytesToCheck = 2;
        }
    }

    public void packetFinished() {
        if (this.writingSample) {
            for (TrackOutput output : this.outputs) {
                output.sampleMetadata(this.sampleTimeUs, 1, this.sampleBytesWritten, 0, null);
            }
            this.writingSample = false;
        }
    }

    public void consume(ParsableByteArray data) {
        if (!this.writingSample) {
            return;
        }
        if (this.bytesToCheck != 2 || checkNextByte(data, 32)) {
            if (this.bytesToCheck != 1 || checkNextByte(data, 0)) {
                int dataPosition = data.getPosition();
                int bytesAvailable = data.bytesLeft();
                for (TrackOutput output : this.outputs) {
                    data.setPosition(dataPosition);
                    output.sampleData(data, bytesAvailable);
                }
                this.sampleBytesWritten += bytesAvailable;
            }
        }
    }

    private boolean checkNextByte(ParsableByteArray data, int expectedValue) {
        if (data.bytesLeft() == 0) {
            return false;
        }
        if (data.readUnsignedByte() != expectedValue) {
            this.writingSample = false;
        }
        this.bytesToCheck--;
        return this.writingSample;
    }
}
