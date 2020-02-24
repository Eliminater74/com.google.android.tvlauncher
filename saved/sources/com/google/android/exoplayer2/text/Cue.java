package com.google.android.exoplayer2.text;

import android.graphics.Bitmap;
import android.support.p001v4.view.ViewCompat;
import android.text.Layout;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Cue {
    public static final int ANCHOR_TYPE_END = 2;
    public static final int ANCHOR_TYPE_MIDDLE = 1;
    public static final int ANCHOR_TYPE_START = 0;
    public static final float DIMEN_UNSET = Float.MIN_VALUE;
    public static final int LINE_TYPE_FRACTION = 0;
    public static final int LINE_TYPE_NUMBER = 1;
    public static final int TEXT_SIZE_TYPE_ABSOLUTE = 2;
    public static final int TEXT_SIZE_TYPE_FRACTIONAL = 0;
    public static final int TEXT_SIZE_TYPE_FRACTIONAL_IGNORE_PADDING = 1;
    public static final int TYPE_UNSET = Integer.MIN_VALUE;
    public final Bitmap bitmap;
    public final float bitmapHeight;
    public final float line;
    public final int lineAnchor;
    public final int lineType;
    public final float position;
    public final int positionAnchor;
    public final float size;
    public final CharSequence text;
    public final Layout.Alignment textAlignment;
    public final float textSize;
    public final int textSizeType;
    public final int windowColor;
    public final boolean windowColorSet;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnchorType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface LineType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSizeType {
    }

    public Cue(Bitmap bitmap2, float horizontalPosition, int horizontalPositionAnchor, float verticalPosition, int verticalPositionAnchor, float width, float height) {
        this(null, null, bitmap2, verticalPosition, 0, verticalPositionAnchor, horizontalPosition, horizontalPositionAnchor, Integer.MIN_VALUE, Float.MIN_VALUE, width, height, false, ViewCompat.MEASURED_STATE_MASK);
    }

    public Cue(CharSequence text2) {
        this(text2, null, Float.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.text.Cue.<init>(java.lang.CharSequence, android.text.Layout$Alignment, float, int, int, float, int, float, boolean, int):void
     arg types: [java.lang.CharSequence, android.text.Layout$Alignment, float, int, int, float, int, float, int, ?]
     candidates:
      com.google.android.exoplayer2.text.Cue.<init>(java.lang.CharSequence, android.text.Layout$Alignment, float, int, int, float, int, float, int, float):void
      com.google.android.exoplayer2.text.Cue.<init>(java.lang.CharSequence, android.text.Layout$Alignment, float, int, int, float, int, float, boolean, int):void */
    public Cue(CharSequence text2, Layout.Alignment textAlignment2, float line2, int lineType2, int lineAnchor2, float position2, int positionAnchor2, float size2) {
        this(text2, textAlignment2, line2, lineType2, lineAnchor2, position2, positionAnchor2, size2, false, (int) ViewCompat.MEASURED_STATE_MASK);
    }

    public Cue(CharSequence text2, Layout.Alignment textAlignment2, float line2, int lineType2, int lineAnchor2, float position2, int positionAnchor2, float size2, int textSizeType2, float textSize2) {
        this(text2, textAlignment2, null, line2, lineType2, lineAnchor2, position2, positionAnchor2, textSizeType2, textSize2, size2, Float.MIN_VALUE, false, ViewCompat.MEASURED_STATE_MASK);
    }

    public Cue(CharSequence text2, Layout.Alignment textAlignment2, float line2, int lineType2, int lineAnchor2, float position2, int positionAnchor2, float size2, boolean windowColorSet2, int windowColor2) {
        this(text2, textAlignment2, null, line2, lineType2, lineAnchor2, position2, positionAnchor2, Integer.MIN_VALUE, Float.MIN_VALUE, size2, Float.MIN_VALUE, windowColorSet2, windowColor2);
    }

    private Cue(CharSequence text2, Layout.Alignment textAlignment2, Bitmap bitmap2, float line2, int lineType2, int lineAnchor2, float position2, int positionAnchor2, int textSizeType2, float textSize2, float size2, float bitmapHeight2, boolean windowColorSet2, int windowColor2) {
        this.text = text2;
        this.textAlignment = textAlignment2;
        this.bitmap = bitmap2;
        this.line = line2;
        this.lineType = lineType2;
        this.lineAnchor = lineAnchor2;
        this.position = position2;
        this.positionAnchor = positionAnchor2;
        this.size = size2;
        this.bitmapHeight = bitmapHeight2;
        this.windowColorSet = windowColorSet2;
        this.windowColor = windowColor2;
        this.textSizeType = textSizeType2;
        this.textSize = textSize2;
    }
}
