package com.google.android.exoplayer2.text.webvtt;

import android.text.Layout;
import android.text.SpannableStringBuilder;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;

public final class WebvttCue extends Cue {
    public final long endTime;
    public final long startTime;

    public WebvttCue(CharSequence text) {
        this(0, 0, text);
    }

    public WebvttCue(long startTime2, long endTime2, CharSequence text) {
        this(startTime2, endTime2, text, null, Float.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE);
    }

    public WebvttCue(long startTime2, long endTime2, CharSequence text, Layout.Alignment textAlignment, float line, int lineType, int lineAnchor, float position, int positionAnchor, float width) {
        super(text, textAlignment, line, lineType, lineAnchor, position, positionAnchor, width);
        this.startTime = startTime2;
        this.endTime = endTime2;
    }

    public boolean isNormalCue() {
        return this.line == Float.MIN_VALUE && this.position == Float.MIN_VALUE;
    }

    public static class Builder {
        private static final String TAG = "WebvttCueBuilder";
        private long endTime;
        private float line;
        private int lineAnchor;
        private int lineType;
        private float position;
        private int positionAnchor;
        private long startTime;
        private SpannableStringBuilder text;
        private Layout.Alignment textAlignment;
        private float width;

        public Builder() {
            reset();
        }

        public void reset() {
            this.startTime = 0;
            this.endTime = 0;
            this.text = null;
            this.textAlignment = null;
            this.line = Float.MIN_VALUE;
            this.lineType = Integer.MIN_VALUE;
            this.lineAnchor = Integer.MIN_VALUE;
            this.position = Float.MIN_VALUE;
            this.positionAnchor = Integer.MIN_VALUE;
            this.width = Float.MIN_VALUE;
        }

        public WebvttCue build() {
            if (this.position != Float.MIN_VALUE && this.positionAnchor == Integer.MIN_VALUE) {
                derivePositionAnchorFromAlignment();
            }
            return new WebvttCue(this.startTime, this.endTime, this.text, this.textAlignment, this.line, this.lineType, this.lineAnchor, this.position, this.positionAnchor, this.width);
        }

        public Builder setStartTime(long time) {
            this.startTime = time;
            return this;
        }

        public Builder setEndTime(long time) {
            this.endTime = time;
            return this;
        }

        public Builder setText(SpannableStringBuilder aText) {
            this.text = aText;
            return this;
        }

        public Builder setTextAlignment(Layout.Alignment textAlignment2) {
            this.textAlignment = textAlignment2;
            return this;
        }

        public Builder setLine(float line2) {
            this.line = line2;
            return this;
        }

        public Builder setLineType(int lineType2) {
            this.lineType = lineType2;
            return this;
        }

        public Builder setLineAnchor(int lineAnchor2) {
            this.lineAnchor = lineAnchor2;
            return this;
        }

        public Builder setPosition(float position2) {
            this.position = position2;
            return this;
        }

        public Builder setPositionAnchor(int positionAnchor2) {
            this.positionAnchor = positionAnchor2;
            return this;
        }

        public Builder setWidth(float width2) {
            this.width = width2;
            return this;
        }

        private Builder derivePositionAnchorFromAlignment() {
            if (this.textAlignment == null) {
                this.positionAnchor = Integer.MIN_VALUE;
            } else {
                int i = C09181.$SwitchMap$android$text$Layout$Alignment[this.textAlignment.ordinal()];
                if (i == 1) {
                    this.positionAnchor = 0;
                } else if (i == 2) {
                    this.positionAnchor = 1;
                } else if (i != 3) {
                    String valueOf = String.valueOf(this.textAlignment);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                    sb.append("Unrecognized alignment: ");
                    sb.append(valueOf);
                    Log.m30w(TAG, sb.toString());
                    this.positionAnchor = 0;
                } else {
                    this.positionAnchor = 2;
                }
            }
            return this;
        }
    }

    /* renamed from: com.google.android.exoplayer2.text.webvtt.WebvttCue$1 */
    static /* synthetic */ class C09181 {
        static final /* synthetic */ int[] $SwitchMap$android$text$Layout$Alignment = new int[Layout.Alignment.values().length];

        static {
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_OPPOSITE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
