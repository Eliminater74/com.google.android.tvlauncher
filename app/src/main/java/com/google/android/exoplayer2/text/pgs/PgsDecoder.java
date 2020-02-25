package com.google.android.exoplayer2.text.pgs;

import android.graphics.Bitmap;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.Inflater;

public final class PgsDecoder extends SimpleSubtitleDecoder {
    private static final byte INFLATE_HEADER = 120;
    private static final int SECTION_TYPE_BITMAP_PICTURE = 21;
    private static final int SECTION_TYPE_END = 128;
    private static final int SECTION_TYPE_IDENTIFIER = 22;
    private static final int SECTION_TYPE_PALETTE = 20;
    private final ParsableByteArray buffer = new ParsableByteArray();
    private final CueBuilder cueBuilder = new CueBuilder();
    private final ParsableByteArray inflatedBuffer = new ParsableByteArray();
    private Inflater inflater;

    public PgsDecoder() {
        super("PgsDecoder");
    }

    private static Cue readNextSection(ParsableByteArray buffer2, CueBuilder cueBuilder2) {
        int limit = buffer2.limit();
        int sectionType = buffer2.readUnsignedByte();
        int sectionLength = buffer2.readUnsignedShort();
        int nextSectionPosition = buffer2.getPosition() + sectionLength;
        if (nextSectionPosition > limit) {
            buffer2.setPosition(limit);
            return null;
        }
        Cue cue = null;
        if (sectionType != 128) {
            switch (sectionType) {
                case 20:
                    cueBuilder2.parsePaletteSection(buffer2, sectionLength);
                    break;
                case 21:
                    cueBuilder2.parseBitmapSection(buffer2, sectionLength);
                    break;
                case 22:
                    cueBuilder2.parseIdentifierSection(buffer2, sectionLength);
                    break;
            }
        } else {
            cue = cueBuilder2.build();
            cueBuilder2.reset();
        }
        buffer2.setPosition(nextSectionPosition);
        return cue;
    }

    /* access modifiers changed from: protected */
    public Subtitle decode(byte[] data, int size, boolean reset) throws SubtitleDecoderException {
        this.buffer.reset(data, size);
        maybeInflateData(this.buffer);
        this.cueBuilder.reset();
        ArrayList<Cue> cues = new ArrayList<>();
        while (this.buffer.bytesLeft() >= 3) {
            Cue cue = readNextSection(this.buffer, this.cueBuilder);
            if (cue != null) {
                cues.add(cue);
            }
        }
        return new PgsSubtitle(Collections.unmodifiableList(cues));
    }

    private void maybeInflateData(ParsableByteArray buffer2) {
        if (buffer2.bytesLeft() > 0 && buffer2.peekUnsignedByte() == 120) {
            if (this.inflater == null) {
                this.inflater = new Inflater();
            }
            if (Util.inflate(buffer2, this.inflatedBuffer, this.inflater)) {
                buffer2.reset(this.inflatedBuffer.data, this.inflatedBuffer.limit());
            }
        }
    }

    private static final class CueBuilder {
        private final ParsableByteArray bitmapData = new ParsableByteArray();
        private final int[] colors = new int[256];
        private int bitmapHeight;
        private int bitmapWidth;
        private int bitmapX;
        private int bitmapY;
        private boolean colorsSet;
        private int planeHeight;
        private int planeWidth;

        /* access modifiers changed from: private */
        public void parsePaletteSection(ParsableByteArray buffer, int sectionLength) {
            if (sectionLength % 5 == 2) {
                buffer.skipBytes(2);
                Arrays.fill(this.colors, 0);
                int i = 0;
                for (int entryCount = sectionLength / 5; i < entryCount; entryCount = entryCount) {
                    int index = buffer.readUnsignedByte();
                    int y = buffer.readUnsignedByte();
                    int cr = buffer.readUnsignedByte();
                    int cb = buffer.readUnsignedByte();
                    int a = buffer.readUnsignedByte();
                    double d = (double) y;
                    double d2 = (double) (cr - 128);
                    Double.isNaN(d2);
                    Double.isNaN(d);
                    double d3 = (double) y;
                    double d4 = (double) (cb - 128);
                    Double.isNaN(d4);
                    Double.isNaN(d3);
                    double d5 = (double) (cr - 128);
                    Double.isNaN(d5);
                    int g = (int) ((d3 - (d4 * 0.34414d)) - (d5 * 0.71414d));
                    double d6 = (double) y;
                    double d7 = (double) (cb - 128);
                    Double.isNaN(d7);
                    Double.isNaN(d6);
                    this.colors[index] = (a << 24) | (Util.constrainValue((int) (d + (d2 * 1.402d)), 0, 255) << 16) | (Util.constrainValue(g, 0, 255) << 8) | Util.constrainValue((int) (d6 + (d7 * 1.772d)), 0, 255);
                    i++;
                }
                this.colorsSet = true;
            }
        }

        /* access modifiers changed from: private */
        public void parseBitmapSection(ParsableByteArray buffer, int sectionLength) {
            int totalLength;
            if (sectionLength >= 4) {
                buffer.skipBytes(3);
                int sectionLength2 = sectionLength - 4;
                if ((buffer.readUnsignedByte() & 128) != 0) {
                    if (sectionLength2 >= 7 && (totalLength = buffer.readUnsignedInt24()) >= 4) {
                        this.bitmapWidth = buffer.readUnsignedShort();
                        this.bitmapHeight = buffer.readUnsignedShort();
                        this.bitmapData.reset(totalLength - 4);
                        sectionLength2 -= 7;
                    } else {
                        return;
                    }
                }
                int position = this.bitmapData.getPosition();
                int limit = this.bitmapData.limit();
                if (position < limit && sectionLength2 > 0) {
                    int bytesToRead = Math.min(sectionLength2, limit - position);
                    buffer.readBytes(this.bitmapData.data, position, bytesToRead);
                    this.bitmapData.setPosition(position + bytesToRead);
                }
            }
        }

        /* access modifiers changed from: private */
        public void parseIdentifierSection(ParsableByteArray buffer, int sectionLength) {
            if (sectionLength >= 19) {
                this.planeWidth = buffer.readUnsignedShort();
                this.planeHeight = buffer.readUnsignedShort();
                buffer.skipBytes(11);
                this.bitmapX = buffer.readUnsignedShort();
                this.bitmapY = buffer.readUnsignedShort();
            }
        }

        public Cue build() {
            int runLength;
            if (this.planeWidth == 0 || this.planeHeight == 0 || this.bitmapWidth == 0 || this.bitmapHeight == 0 || this.bitmapData.limit() == 0 || this.bitmapData.getPosition() != this.bitmapData.limit() || !this.colorsSet) {
                return null;
            }
            this.bitmapData.setPosition(0);
            int[] argbBitmapData = new int[(this.bitmapWidth * this.bitmapHeight)];
            int argbBitmapDataIndex = 0;
            while (argbBitmapDataIndex < argbBitmapData.length) {
                int colorIndex = this.bitmapData.readUnsignedByte();
                if (colorIndex != 0) {
                    argbBitmapData[argbBitmapDataIndex] = this.colors[colorIndex];
                    argbBitmapDataIndex++;
                } else {
                    int switchBits = this.bitmapData.readUnsignedByte();
                    if (switchBits != 0) {
                        if ((switchBits & 64) == 0) {
                            runLength = switchBits & 63;
                        } else {
                            runLength = ((switchBits & 63) << 8) | this.bitmapData.readUnsignedByte();
                        }
                        Arrays.fill(argbBitmapData, argbBitmapDataIndex, argbBitmapDataIndex + runLength, (switchBits & 128) == 0 ? 0 : this.colors[this.bitmapData.readUnsignedByte()]);
                        argbBitmapDataIndex += runLength;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(argbBitmapData, this.bitmapWidth, this.bitmapHeight, Bitmap.Config.ARGB_8888);
            int i = this.planeWidth;
            float f = ((float) this.bitmapX) / ((float) i);
            int i2 = this.planeHeight;
            return new Cue(bitmap, f, 0, ((float) this.bitmapY) / ((float) i2), 0, ((float) this.bitmapWidth) / ((float) i), ((float) this.bitmapHeight) / ((float) i2));
        }

        public void reset() {
            this.planeWidth = 0;
            this.planeHeight = 0;
            this.bitmapX = 0;
            this.bitmapY = 0;
            this.bitmapWidth = 0;
            this.bitmapHeight = 0;
            this.bitmapData.reset(0);
            this.colorsSet = false;
        }
    }
}
