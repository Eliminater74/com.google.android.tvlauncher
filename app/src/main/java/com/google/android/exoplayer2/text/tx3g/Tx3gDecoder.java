package com.google.android.exoplayer2.text.tx3g;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

import java.nio.charset.Charset;
import java.util.List;

public final class Tx3gDecoder extends SimpleSubtitleDecoder {
    private static final char BOM_UTF16_BE = '﻿';
    private static final char BOM_UTF16_LE = '￾';
    private static final int DEFAULT_COLOR = -1;
    private static final int DEFAULT_FONT_FACE = 0;
    private static final String DEFAULT_FONT_FAMILY = "sans-serif";
    private static final float DEFAULT_VERTICAL_PLACEMENT = 0.85f;
    private static final int FONT_FACE_BOLD = 1;
    private static final int FONT_FACE_ITALIC = 2;
    private static final int FONT_FACE_UNDERLINE = 4;
    private static final int SIZE_ATOM_HEADER = 8;
    private static final int SIZE_BOM_UTF16 = 2;
    private static final int SIZE_SHORT = 2;
    private static final int SIZE_STYLE_RECORD = 12;
    private static final int SPAN_PRIORITY_HIGH = 0;
    private static final int SPAN_PRIORITY_LOW = 16711680;
    private static final String TX3G_SERIF = "Serif";
    private static final int TYPE_STYL = Util.getIntegerCodeForString("styl");
    private static final int TYPE_TBOX = Util.getIntegerCodeForString("tbox");
    private final ParsableByteArray parsableByteArray = new ParsableByteArray();
    private int calculatedVideoTrackHeight;
    private boolean customVerticalPlacement;
    private int defaultColorRgba;
    private int defaultFontFace;
    private String defaultFontFamily;
    private float defaultVerticalPlacement;

    public Tx3gDecoder(List<byte[]> initializationData) {
        super("Tx3gDecoder");
        decodeInitializationData(initializationData);
    }

    private static String readSubtitleText(ParsableByteArray parsableByteArray2) throws SubtitleDecoderException {
        char firstChar;
        assertTrue(parsableByteArray2.bytesLeft() >= 2);
        int textLength = parsableByteArray2.readUnsignedShort();
        if (textLength == 0) {
            return "";
        }
        if (parsableByteArray2.bytesLeft() < 2 || ((firstChar = parsableByteArray2.peekChar()) != 65279 && firstChar != 65534)) {
            return parsableByteArray2.readString(textLength, Charset.forName("UTF-8"));
        }
        return parsableByteArray2.readString(textLength, Charset.forName(C0841C.UTF16_NAME));
    }

    private static void attachFontFace(SpannableStringBuilder cueText, int fontFace, int defaultFontFace2, int start, int end, int spanPriority) {
        if (fontFace != defaultFontFace2) {
            int flags = spanPriority | 33;
            boolean isUnderlined = true;
            boolean isBold = (fontFace & 1) != 0;
            boolean isItalic = (fontFace & 2) != 0;
            if (isBold) {
                if (isItalic) {
                    cueText.setSpan(new StyleSpan(3), start, end, flags);
                } else {
                    cueText.setSpan(new StyleSpan(1), start, end, flags);
                }
            } else if (isItalic) {
                cueText.setSpan(new StyleSpan(2), start, end, flags);
            }
            if ((fontFace & 4) == 0) {
                isUnderlined = false;
            }
            if (isUnderlined) {
                cueText.setSpan(new UnderlineSpan(), start, end, flags);
            }
            if (!isUnderlined && !isBold && !isItalic) {
                cueText.setSpan(new StyleSpan(0), start, end, flags);
            }
        }
    }

    private static void attachColor(SpannableStringBuilder cueText, int colorRgba, int defaultColorRgba2, int start, int end, int spanPriority) {
        if (colorRgba != defaultColorRgba2) {
            cueText.setSpan(new ForegroundColorSpan(((colorRgba & 255) << 24) | (colorRgba >>> 8)), start, end, spanPriority | 33);
        }
    }

    private static void attachFontFamily(SpannableStringBuilder cueText, String fontFamily, String defaultFontFamily2, int start, int end, int spanPriority) {
        if (fontFamily != defaultFontFamily2) {
            cueText.setSpan(new TypefaceSpan(fontFamily), start, end, spanPriority | 33);
        }
    }

    private static void assertTrue(boolean checkValue) throws SubtitleDecoderException {
        if (!checkValue) {
            throw new SubtitleDecoderException("Unexpected subtitle format.");
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float
     arg types: [float, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.constrainValue(int, int, int):int
      com.google.android.exoplayer2.util.Util.constrainValue(long, long, long):long
      com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float */
    private void decodeInitializationData(List<byte[]> initializationData) {
        String str = "sans-serif";
        boolean z = false;
        if (initializationData != null && initializationData.size() == 1 && (initializationData.get(0).length == 48 || initializationData.get(0).length == 53)) {
            byte[] initializationBytes = initializationData.get(0);
            this.defaultFontFace = initializationBytes[24];
            this.defaultColorRgba = ((initializationBytes[26] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((initializationBytes[27] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((initializationBytes[28] & UnsignedBytes.MAX_VALUE) << 8) | (initializationBytes[29] & UnsignedBytes.MAX_VALUE);
            if (TX3G_SERIF.equals(Util.fromUtf8Bytes(initializationBytes, 43, initializationBytes.length - 43))) {
                str = C0841C.SERIF_NAME;
            }
            this.defaultFontFamily = str;
            this.calculatedVideoTrackHeight = initializationBytes[25] * Ascii.DC4;
            if ((initializationBytes[0] & 32) != 0) {
                z = true;
            }
            this.customVerticalPlacement = z;
            if (this.customVerticalPlacement) {
                this.defaultVerticalPlacement = ((float) (((initializationBytes[10] & UnsignedBytes.MAX_VALUE) << 8) | (initializationBytes[11] & 255))) / ((float) this.calculatedVideoTrackHeight);
                this.defaultVerticalPlacement = Util.constrainValue(this.defaultVerticalPlacement, 0.0f, 0.95f);
                return;
            }
            this.defaultVerticalPlacement = DEFAULT_VERTICAL_PLACEMENT;
            return;
        }
        this.defaultFontFace = 0;
        this.defaultColorRgba = -1;
        this.defaultFontFamily = str;
        this.customVerticalPlacement = false;
        this.defaultVerticalPlacement = DEFAULT_VERTICAL_PLACEMENT;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float
     arg types: [float, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.constrainValue(int, int, int):int
      com.google.android.exoplayer2.util.Util.constrainValue(long, long, long):long
      com.google.android.exoplayer2.util.Util.constrainValue(float, float, float):float */
    /* access modifiers changed from: protected */
    public Subtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.parsableByteArray.reset(bytes, length);
        String cueTextString = readSubtitleText(this.parsableByteArray);
        if (cueTextString.isEmpty()) {
            return Tx3gSubtitle.EMPTY;
        }
        SpannableStringBuilder cueText = new SpannableStringBuilder(cueTextString);
        SpannableStringBuilder spannableStringBuilder = cueText;
        attachFontFace(spannableStringBuilder, this.defaultFontFace, 0, 0, cueText.length(), SPAN_PRIORITY_LOW);
        attachColor(spannableStringBuilder, this.defaultColorRgba, -1, 0, cueText.length(), SPAN_PRIORITY_LOW);
        attachFontFamily(spannableStringBuilder, this.defaultFontFamily, "sans-serif", 0, cueText.length(), SPAN_PRIORITY_LOW);
        float verticalPlacement = this.defaultVerticalPlacement;
        while (this.parsableByteArray.bytesLeft() >= 8) {
            int position = this.parsableByteArray.getPosition();
            int atomSize = this.parsableByteArray.readInt();
            int atomType = this.parsableByteArray.readInt();
            boolean z = false;
            if (atomType == TYPE_STYL) {
                if (this.parsableByteArray.bytesLeft() >= 2) {
                    z = true;
                }
                assertTrue(z);
                int styleRecordCount = this.parsableByteArray.readUnsignedShort();
                for (int i = 0; i < styleRecordCount; i++) {
                    applyStyleRecord(this.parsableByteArray, cueText);
                }
            } else if (atomType == TYPE_TBOX && this.customVerticalPlacement) {
                if (this.parsableByteArray.bytesLeft() >= 2) {
                    z = true;
                }
                assertTrue(z);
                verticalPlacement = Util.constrainValue(((float) this.parsableByteArray.readUnsignedShort()) / ((float) this.calculatedVideoTrackHeight), 0.0f, 0.95f);
            }
            this.parsableByteArray.setPosition(position + atomSize);
        }
        Cue cue = r5;
        Cue cue2 = new Cue(cueText, null, verticalPlacement, 0, 0, Float.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE);
        return new Tx3gSubtitle(cue);
    }

    private void applyStyleRecord(ParsableByteArray parsableByteArray2, SpannableStringBuilder cueText) throws SubtitleDecoderException {
        assertTrue(parsableByteArray2.bytesLeft() >= 12);
        int start = parsableByteArray2.readUnsignedShort();
        int end = parsableByteArray2.readUnsignedShort();
        parsableByteArray2.skipBytes(2);
        int fontFace = parsableByteArray2.readUnsignedByte();
        parsableByteArray2.skipBytes(1);
        int colorRgba = parsableByteArray2.readInt();
        SpannableStringBuilder spannableStringBuilder = cueText;
        int i = start;
        int i2 = end;
        attachFontFace(spannableStringBuilder, fontFace, this.defaultFontFace, i, i2, 0);
        attachColor(spannableStringBuilder, colorRgba, this.defaultColorRgba, i, i2, 0);
    }
}
