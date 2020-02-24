package com.google.android.exoplayer2.text.ttml;

final class TtmlRegion {

    /* renamed from: id */
    public final String f101id;
    public final float line;
    public final int lineAnchor;
    public final int lineType;
    public final float position;
    public final float textSize;
    public final int textSizeType;
    public final float width;

    public TtmlRegion(String id) {
        this(id, Float.MIN_VALUE, Float.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE);
    }

    public TtmlRegion(String id, float position2, float line2, int lineType2, int lineAnchor2, float width2, int textSizeType2, float textSize2) {
        this.f101id = id;
        this.position = position2;
        this.line = line2;
        this.lineType = lineType2;
        this.lineAnchor = lineAnchor2;
        this.width = width2;
        this.textSizeType = textSizeType2;
        this.textSize = textSize2;
    }
}
