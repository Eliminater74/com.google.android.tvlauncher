package com.google.android.exoplayer2.extractor.flv;

import android.util.Pair;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.Collections;

final class AudioTagPayloadReader extends TagPayloadReader {
    private static final int AAC_PACKET_TYPE_AAC_RAW = 1;
    private static final int AAC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int AUDIO_FORMAT_AAC = 10;
    private static final int AUDIO_FORMAT_ALAW = 7;
    private static final int AUDIO_FORMAT_MP3 = 2;
    private static final int AUDIO_FORMAT_ULAW = 8;
    private static final int[] AUDIO_SAMPLING_RATE_TABLE = {5512, 11025, 22050, 44100};
    private int audioFormat;
    private boolean hasOutputFormat;
    private boolean hasParsedAudioDataHeader;

    public AudioTagPayloadReader(TrackOutput output) {
        super(output);
    }

    public void seek() {
    }

    /* access modifiers changed from: protected */
    public boolean parseHeader(ParsableByteArray data) throws TagPayloadReader.UnsupportedFormatException {
        if (!this.hasParsedAudioDataHeader) {
            int header = data.readUnsignedByte();
            this.audioFormat = (header >> 4) & 15;
            int i = this.audioFormat;
            if (i == 2) {
                this.output.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_MPEG, null, -1, -1, 1, AUDIO_SAMPLING_RATE_TABLE[(header >> 2) & 3], null, null, 0, null));
                this.hasOutputFormat = true;
            } else if (i == 7 || i == 8) {
                this.output.format(Format.createAudioSampleFormat(null, this.audioFormat == 7 ? MimeTypes.AUDIO_ALAW : MimeTypes.AUDIO_MLAW, null, -1, -1, 1, 8000, (header & 1) == 1 ? 2 : 3, null, null, 0, null));
                this.hasOutputFormat = true;
            } else if (i != 10) {
                StringBuilder sb = new StringBuilder(39);
                sb.append("Audio format not supported: ");
                sb.append(i);
                throw new TagPayloadReader.UnsupportedFormatException(sb.toString());
            }
            this.hasParsedAudioDataHeader = true;
        } else {
            data.skipBytes(1);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void parsePayload(ParsableByteArray data, long timeUs) throws ParserException {
        ParsableByteArray parsableByteArray = data;
        if (this.audioFormat == 2) {
            int sampleSize = data.bytesLeft();
            this.output.sampleData(parsableByteArray, sampleSize);
            this.output.sampleMetadata(timeUs, 1, sampleSize, 0, null);
            return;
        }
        int packetType = data.readUnsignedByte();
        if (packetType == 0 && !this.hasOutputFormat) {
            byte[] audioSpecificConfig = new byte[data.bytesLeft()];
            parsableByteArray.readBytes(audioSpecificConfig, 0, audioSpecificConfig.length);
            Pair<Integer, Integer> audioParams = CodecSpecificDataUtil.parseAacAudioSpecificConfig(audioSpecificConfig);
            this.output.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_AAC, null, -1, -1, ((Integer) audioParams.second).intValue(), ((Integer) audioParams.first).intValue(), Collections.singletonList(audioSpecificConfig), null, 0, null));
            this.hasOutputFormat = true;
        } else if (this.audioFormat != 10 || packetType == 1) {
            int sampleSize2 = data.bytesLeft();
            this.output.sampleData(parsableByteArray, sampleSize2);
            this.output.sampleMetadata(timeUs, 1, sampleSize2, 0, null);
        }
    }
}
