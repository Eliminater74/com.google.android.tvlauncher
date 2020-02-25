package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Mp4WebvttDecoder extends SimpleSubtitleDecoder {
    private static final int BOX_HEADER_SIZE = 8;
    private static final int TYPE_payl = Util.getIntegerCodeForString("payl");
    private static final int TYPE_sttg = Util.getIntegerCodeForString("sttg");
    private static final int TYPE_vttc = Util.getIntegerCodeForString("vttc");
    private final WebvttCue.Builder builder = new WebvttCue.Builder();
    private final ParsableByteArray sampleData = new ParsableByteArray();

    public Mp4WebvttDecoder() {
        super("Mp4WebvttDecoder");
    }

    private static Cue parseVttCueBox(ParsableByteArray sampleData2, WebvttCue.Builder builder2, int remainingCueBoxBytes) throws SubtitleDecoderException {
        builder2.reset();
        while (remainingCueBoxBytes > 0) {
            if (remainingCueBoxBytes >= 8) {
                int boxSize = sampleData2.readInt();
                int boxType = sampleData2.readInt();
                int payloadLength = boxSize - 8;
                String boxPayload = Util.fromUtf8Bytes(sampleData2.data, sampleData2.getPosition(), payloadLength);
                sampleData2.skipBytes(payloadLength);
                remainingCueBoxBytes = (remainingCueBoxBytes - 8) - payloadLength;
                if (boxType == TYPE_sttg) {
                    WebvttCueParser.parseCueSettingsList(boxPayload, builder2);
                } else if (boxType == TYPE_payl) {
                    WebvttCueParser.parseCueText(null, boxPayload.trim(), builder2, Collections.emptyList());
                }
            } else {
                throw new SubtitleDecoderException("Incomplete vtt cue box header found.");
            }
        }
        return builder2.build();
    }

    /* access modifiers changed from: protected */
    public Mp4WebvttSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.sampleData.reset(bytes, length);
        List<Cue> resultingCueList = new ArrayList<>();
        while (this.sampleData.bytesLeft() > 0) {
            if (this.sampleData.bytesLeft() >= 8) {
                int boxSize = this.sampleData.readInt();
                if (this.sampleData.readInt() == TYPE_vttc) {
                    resultingCueList.add(parseVttCueBox(this.sampleData, this.builder, boxSize - 8));
                } else {
                    this.sampleData.skipBytes(boxSize - 8);
                }
            } else {
                throw new SubtitleDecoderException("Incomplete Mp4Webvtt Top Level box header found.");
            }
        }
        return new Mp4WebvttSubtitle(resultingCueList);
    }
}
