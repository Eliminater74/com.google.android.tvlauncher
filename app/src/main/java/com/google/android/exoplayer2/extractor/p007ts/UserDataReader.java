package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.text.cea.CeaUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.List;

/* renamed from: com.google.android.exoplayer2.extractor.ts.UserDataReader */
final class UserDataReader {
    private static final int USER_DATA_START_CODE = 434;
    private final List<Format> closedCaptionFormats;
    private final TrackOutput[] outputs;

    public UserDataReader(List<Format> closedCaptionFormats2) {
        this.closedCaptionFormats = closedCaptionFormats2;
        this.outputs = new TrackOutput[closedCaptionFormats2.size()];
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        for (int i = 0; i < this.outputs.length; i++) {
            idGenerator.generateNewId();
            TrackOutput output = extractorOutput.track(idGenerator.getTrackId(), 3);
            Format channelFormat = this.closedCaptionFormats.get(i);
            String channelMimeType = channelFormat.sampleMimeType;
            boolean z = MimeTypes.APPLICATION_CEA608.equals(channelMimeType) || MimeTypes.APPLICATION_CEA708.equals(channelMimeType);
            String valueOf = String.valueOf(channelMimeType);
            Assertions.checkArgument(z, valueOf.length() != 0 ? "Invalid closed caption mime type provided: ".concat(valueOf) : new String("Invalid closed caption mime type provided: "));
            output.format(Format.createTextSampleFormat(idGenerator.getFormatId(), channelMimeType, null, -1, channelFormat.selectionFlags, channelFormat.language, channelFormat.accessibilityChannel, null, Long.MAX_VALUE, channelFormat.initializationData));
            this.outputs[i] = output;
        }
    }

    public void consume(long pesTimeUs, ParsableByteArray userDataPayload) {
        if (userDataPayload.bytesLeft() >= 9) {
            int userDataStartCode = userDataPayload.readInt();
            int userDataIdentifier = userDataPayload.readInt();
            int userDataTypeCode = userDataPayload.readUnsignedByte();
            if (userDataStartCode == 434 && userDataIdentifier == CeaUtil.USER_DATA_IDENTIFIER_GA94 && userDataTypeCode == 3) {
                CeaUtil.consumeCcData(pesTimeUs, userDataPayload, this.outputs);
            }
        }
    }
}
