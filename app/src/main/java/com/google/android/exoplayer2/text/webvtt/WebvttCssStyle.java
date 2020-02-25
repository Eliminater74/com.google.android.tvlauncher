package com.google.android.exoplayer2.text.webvtt;

import android.text.Layout;

import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class WebvttCssStyle {
    public static final int FONT_SIZE_UNIT_EM = 2;
    public static final int FONT_SIZE_UNIT_PERCENT = 3;
    public static final int FONT_SIZE_UNIT_PIXEL = 1;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_BOLD_ITALIC = 3;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_NORMAL = 0;
    public static final int UNSPECIFIED = -1;
    private static final int OFF = 0;
    /* renamed from: ON */
    private static final int f104ON = 1;
    private int backgroundColor;
    private int bold;
    private int fontColor;
    private String fontFamily;
    private float fontSize;
    private int fontSizeUnit;
    private boolean hasBackgroundColor;
    private boolean hasFontColor;
    private int italic;
    private int linethrough;
    private List<String> targetClasses;
    private String targetId;
    private String targetTag;
    private String targetVoice;
    private Layout.Alignment textAlign;
    private int underline;

    public WebvttCssStyle() {
        reset();
    }

    private static int updateScoreForMatch(int currentScore, String target, String actual, int score) {
        if (target.isEmpty() || currentScore == -1) {
            return currentScore;
        }
        if (target.equals(actual)) {
            return currentScore + score;
        }
        return -1;
    }

    public void reset() {
        this.targetId = "";
        this.targetTag = "";
        this.targetClasses = Collections.emptyList();
        this.targetVoice = "";
        this.fontFamily = null;
        this.hasFontColor = false;
        this.hasBackgroundColor = false;
        this.linethrough = -1;
        this.underline = -1;
        this.bold = -1;
        this.italic = -1;
        this.fontSizeUnit = -1;
        this.textAlign = null;
    }

    public void setTargetId(String targetId2) {
        this.targetId = targetId2;
    }

    public void setTargetTagName(String targetTag2) {
        this.targetTag = targetTag2;
    }

    public void setTargetClasses(String[] targetClasses2) {
        this.targetClasses = Arrays.asList(targetClasses2);
    }

    public void setTargetVoice(String targetVoice2) {
        this.targetVoice = targetVoice2;
    }

    public int getSpecificityScore(String id, String tag, String[] classes, String voice) {
        if (this.targetId.isEmpty() && this.targetTag.isEmpty() && this.targetClasses.isEmpty() && this.targetVoice.isEmpty()) {
            return tag.isEmpty() ? 1 : 0;
        }
        int score = updateScoreForMatch(updateScoreForMatch(updateScoreForMatch(0, this.targetId, id, 1073741824), this.targetTag, tag, 2), this.targetVoice, voice, 4);
        if (score == -1 || !Arrays.asList(classes).containsAll(this.targetClasses)) {
            return 0;
        }
        return score + (this.targetClasses.size() * 4);
    }

    public int getStyle() {
        if (this.bold == -1 && this.italic == -1) {
            return -1;
        }
        int i = 0;
        int i2 = this.bold == 1 ? 1 : 0;
        if (this.italic == 1) {
            i = 2;
        }
        return i2 | i;
    }

    public boolean isLinethrough() {
        return this.linethrough == 1;
    }

    public WebvttCssStyle setLinethrough(boolean linethrough2) {
        this.linethrough = linethrough2;
        return this;
    }

    public boolean isUnderline() {
        return this.underline == 1;
    }

    public WebvttCssStyle setUnderline(boolean underline2) {
        this.underline = underline2;
        return this;
    }

    public WebvttCssStyle setBold(boolean bold2) {
        this.bold = bold2;
        return this;
    }

    public WebvttCssStyle setItalic(boolean italic2) {
        this.italic = italic2;
        return this;
    }

    public String getFontFamily() {
        return this.fontFamily;
    }

    public WebvttCssStyle setFontFamily(String fontFamily2) {
        this.fontFamily = Util.toLowerInvariant(fontFamily2);
        return this;
    }

    public int getFontColor() {
        if (this.hasFontColor) {
            return this.fontColor;
        }
        throw new IllegalStateException("Font color not defined");
    }

    public WebvttCssStyle setFontColor(int color) {
        this.fontColor = color;
        this.hasFontColor = true;
        return this;
    }

    public boolean hasFontColor() {
        return this.hasFontColor;
    }

    public int getBackgroundColor() {
        if (this.hasBackgroundColor) {
            return this.backgroundColor;
        }
        throw new IllegalStateException("Background color not defined.");
    }

    public WebvttCssStyle setBackgroundColor(int backgroundColor2) {
        this.backgroundColor = backgroundColor2;
        this.hasBackgroundColor = true;
        return this;
    }

    public boolean hasBackgroundColor() {
        return this.hasBackgroundColor;
    }

    public Layout.Alignment getTextAlign() {
        return this.textAlign;
    }

    public WebvttCssStyle setTextAlign(Layout.Alignment textAlign2) {
        this.textAlign = textAlign2;
        return this;
    }

    public int getFontSizeUnit() {
        return this.fontSizeUnit;
    }

    public WebvttCssStyle setFontSizeUnit(short unit) {
        this.fontSizeUnit = unit;
        return this;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public WebvttCssStyle setFontSize(float fontSize2) {
        this.fontSize = fontSize2;
        return this;
    }

    public void cascadeFrom(WebvttCssStyle style) {
        if (style.hasFontColor) {
            setFontColor(style.fontColor);
        }
        int i = style.bold;
        if (i != -1) {
            this.bold = i;
        }
        int i2 = style.italic;
        if (i2 != -1) {
            this.italic = i2;
        }
        String str = style.fontFamily;
        if (str != null) {
            this.fontFamily = str;
        }
        if (this.linethrough == -1) {
            this.linethrough = style.linethrough;
        }
        if (this.underline == -1) {
            this.underline = style.underline;
        }
        if (this.textAlign == null) {
            this.textAlign = style.textAlign;
        }
        if (this.fontSizeUnit == -1) {
            this.fontSizeUnit = style.fontSizeUnit;
            this.fontSize = style.fontSize;
        }
        if (style.hasBackgroundColor) {
            setBackgroundColor(style.backgroundColor);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontSizeUnit {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface OptionalBoolean {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface StyleFlags {
    }
}
