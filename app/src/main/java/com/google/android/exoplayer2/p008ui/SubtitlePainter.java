package com.google.android.exoplayer2.p008ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.p001v4.view.ViewCompat;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* renamed from: com.google.android.exoplayer2.ui.SubtitlePainter */
final class SubtitlePainter {
    private static final float INNER_PADDING_RATIO = 0.125f;
    private static final String TAG = "SubtitlePainter";
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private int backgroundColor;
    private Rect bitmapRect;
    private float bottomPaddingFraction;
    private Bitmap cueBitmap;
    private float cueBitmapHeight;
    private float cueLine;
    private int cueLineAnchor;
    private int cueLineType;
    private float cuePosition;
    private int cuePositionAnchor;
    private float cueSize;
    private CharSequence cueText;
    private Layout.Alignment cueTextAlignment;
    private float cueTextSizePx;
    private float defaultTextSizePx;
    private int edgeColor;
    private int edgeType;
    private int foregroundColor;
    private final float outlineWidth;
    private final Paint paint;
    private int parentBottom;
    private int parentLeft;
    private int parentRight;
    private int parentTop;
    private final float shadowOffset;
    private final float shadowRadius;
    private final float spacingAdd;
    private final float spacingMult;
    private StaticLayout textLayout;
    private int textLeft;
    private int textPaddingX;
    private final TextPaint textPaint = new TextPaint();
    private int textTop;
    private int windowColor;

    public SubtitlePainter(Context context) {
        TypedArray styledAttributes = context.obtainStyledAttributes(null, new int[]{16843287, 16843288}, 0, 0);
        this.spacingAdd = (float) styledAttributes.getDimensionPixelSize(0, 0);
        this.spacingMult = styledAttributes.getFloat(1, 1.0f);
        styledAttributes.recycle();
        int twoDpInPx = Math.round((((float) context.getResources().getDisplayMetrics().densityDpi) * 2.0f) / 160.0f);
        this.outlineWidth = (float) twoDpInPx;
        this.shadowRadius = (float) twoDpInPx;
        this.shadowOffset = (float) twoDpInPx;
        this.textPaint.setAntiAlias(true);
        this.textPaint.setSubpixelText(true);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
    }

    public void draw(Cue cue, boolean applyEmbeddedStyles2, boolean applyEmbeddedFontSizes2, CaptionStyleCompat style, float defaultTextSizePx2, float cueTextSizePx2, float bottomPaddingFraction2, Canvas canvas, int cueBoxLeft, int cueBoxTop, int cueBoxRight, int cueBoxBottom) {
        Canvas canvas2;
        Cue cue2 = cue;
        boolean z = applyEmbeddedStyles2;
        boolean z2 = applyEmbeddedFontSizes2;
        CaptionStyleCompat captionStyleCompat = style;
        float f = defaultTextSizePx2;
        float f2 = cueTextSizePx2;
        float f3 = bottomPaddingFraction2;
        int i = cueBoxLeft;
        int i2 = cueBoxTop;
        int i3 = cueBoxRight;
        int i4 = cueBoxBottom;
        boolean isTextCue = cue2.bitmap == null;
        int windowColor2 = ViewCompat.MEASURED_STATE_MASK;
        if (isTextCue) {
            if (!TextUtils.isEmpty(cue2.text)) {
                windowColor2 = (!cue2.windowColorSet || !z) ? captionStyleCompat.windowColor : cue2.windowColor;
            } else {
                return;
            }
        }
        if (!areCharSequencesEqual(this.cueText, cue2.text)) {
            canvas2 = canvas;
        } else if (!Util.areEqual(this.cueTextAlignment, cue2.textAlignment) || this.cueBitmap != cue2.bitmap || this.cueLine != cue2.line || this.cueLineType != cue2.lineType) {
            canvas2 = canvas;
        } else if (!Util.areEqual(Integer.valueOf(this.cueLineAnchor), Integer.valueOf(cue2.lineAnchor)) || this.cuePosition != cue2.position) {
            canvas2 = canvas;
        } else if (!Util.areEqual(Integer.valueOf(this.cuePositionAnchor), Integer.valueOf(cue2.positionAnchor)) || this.cueSize != cue2.size || this.cueBitmapHeight != cue2.bitmapHeight || this.applyEmbeddedStyles != z || this.applyEmbeddedFontSizes != z2 || this.foregroundColor != captionStyleCompat.foregroundColor || this.backgroundColor != captionStyleCompat.backgroundColor || this.windowColor != windowColor2 || this.edgeType != captionStyleCompat.edgeType || this.edgeColor != captionStyleCompat.edgeColor) {
            canvas2 = canvas;
        } else if (Util.areEqual(this.textPaint.getTypeface(), captionStyleCompat.typeface) && this.defaultTextSizePx == f && this.cueTextSizePx == f2 && this.bottomPaddingFraction == f3 && this.parentLeft == i && this.parentTop == i2 && this.parentRight == i3 && this.parentBottom == i4) {
            drawLayout(canvas, isTextCue);
            return;
        } else {
            canvas2 = canvas;
        }
        this.cueText = cue2.text;
        this.cueTextAlignment = cue2.textAlignment;
        this.cueBitmap = cue2.bitmap;
        this.cueLine = cue2.line;
        this.cueLineType = cue2.lineType;
        this.cueLineAnchor = cue2.lineAnchor;
        this.cuePosition = cue2.position;
        this.cuePositionAnchor = cue2.positionAnchor;
        this.cueSize = cue2.size;
        this.cueBitmapHeight = cue2.bitmapHeight;
        this.applyEmbeddedStyles = z;
        this.applyEmbeddedFontSizes = z2;
        this.foregroundColor = captionStyleCompat.foregroundColor;
        this.backgroundColor = captionStyleCompat.backgroundColor;
        this.windowColor = windowColor2;
        this.edgeType = captionStyleCompat.edgeType;
        this.edgeColor = captionStyleCompat.edgeColor;
        this.textPaint.setTypeface(captionStyleCompat.typeface);
        this.defaultTextSizePx = f;
        this.cueTextSizePx = f2;
        this.bottomPaddingFraction = f3;
        this.parentLeft = i;
        this.parentTop = i2;
        this.parentRight = i3;
        this.parentBottom = i4;
        if (isTextCue) {
            setupTextLayout();
        } else {
            setupBitmapLayout();
        }
        drawLayout(canvas2, isTextCue);
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    private void setupTextLayout() {
        CharSequence cueText2;
        int textLeft2;
        int anchorPosition;
        int textTop2;
        int firstLineHeight;
        int textLeft3;
        int parentWidth = this.parentRight - this.parentLeft;
        int parentHeight = this.parentBottom - this.parentTop;
        this.textPaint.setTextSize(this.defaultTextSizePx);
        int textPaddingX2 = (int) ((this.defaultTextSizePx * INNER_PADDING_RATIO) + 0.5f);
        int availableWidth = parentWidth - (textPaddingX2 * 2);
        float f = this.cueSize;
        if (f != Float.MIN_VALUE) {
            availableWidth = (int) (((float) availableWidth) * f);
        }
        if (availableWidth <= 0) {
            Log.m30w(TAG, "Skipped drawing subtitle cue (insufficient space)");
            return;
        }
        CharSequence cueText3 = this.cueText;
        if (!this.applyEmbeddedStyles) {
            cueText3 = cueText3.toString();
        } else if (!this.applyEmbeddedFontSizes) {
            SpannableStringBuilder newCueText = new SpannableStringBuilder(cueText3);
            int cueLength = newCueText.length();
            AbsoluteSizeSpan[] absSpans = (AbsoluteSizeSpan[]) newCueText.getSpans(0, cueLength, AbsoluteSizeSpan.class);
            RelativeSizeSpan[] relSpans = (RelativeSizeSpan[]) newCueText.getSpans(0, cueLength, RelativeSizeSpan.class);
            for (AbsoluteSizeSpan absSpan : absSpans) {
                newCueText.removeSpan(absSpan);
            }
            for (RelativeSizeSpan relSpan : relSpans) {
                newCueText.removeSpan(relSpan);
            }
            cueText3 = newCueText;
        } else if (this.cueTextSizePx > 0.0f) {
            SpannableStringBuilder newCueText2 = new SpannableStringBuilder(cueText3);
            newCueText2.setSpan(new AbsoluteSizeSpan((int) this.cueTextSizePx), 0, newCueText2.length(), 16711680);
            cueText3 = newCueText2;
        }
        if (Color.alpha(this.backgroundColor) > 0) {
            SpannableStringBuilder newCueText3 = new SpannableStringBuilder(cueText3);
            newCueText3.setSpan(new BackgroundColorSpan(this.backgroundColor), 0, newCueText3.length(), 16711680);
            cueText2 = newCueText3;
        } else {
            cueText2 = cueText3;
        }
        Layout.Alignment alignment = this.cueTextAlignment;
        if (alignment == null) {
            alignment = Layout.Alignment.ALIGN_CENTER;
        }
        Layout.Alignment textAlignment = alignment;
        StaticLayout staticLayout = r7;
        CharSequence cueText4 = cueText2;
        StaticLayout staticLayout2 = new StaticLayout(cueText2, this.textPaint, availableWidth, textAlignment, this.spacingMult, this.spacingAdd, true);
        this.textLayout = staticLayout;
        int textHeight = this.textLayout.getHeight();
        int textWidth = 0;
        int lineCount = this.textLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            textWidth = Math.max((int) Math.ceil((double) this.textLayout.getLineWidth(i)), textWidth);
        }
        if (this.cueSize != Float.MIN_VALUE && textWidth < availableWidth) {
            textWidth = availableWidth;
        }
        int textWidth2 = textWidth + (textPaddingX2 * 2);
        float f2 = this.cuePosition;
        if (f2 != Float.MIN_VALUE) {
            int anchorPosition2 = Math.round(((float) parentWidth) * f2) + this.parentLeft;
            int i2 = this.cuePositionAnchor;
            if (i2 == 2) {
                textLeft3 = anchorPosition2 - textWidth2;
            } else if (i2 == 1) {
                textLeft3 = ((anchorPosition2 * 2) - textWidth2) / 2;
            } else {
                textLeft3 = anchorPosition2;
            }
            textLeft2 = Math.max(textLeft3, this.parentLeft);
            anchorPosition = Math.min(textLeft2 + textWidth2, this.parentRight);
        } else {
            textLeft2 = ((parentWidth - textWidth2) / 2) + this.parentLeft;
            anchorPosition = textLeft2 + textWidth2;
        }
        int textWidth3 = anchorPosition - textLeft2;
        if (textWidth3 <= 0) {
            Log.m30w(TAG, "Skipped drawing subtitle cue (invalid horizontal positioning)");
            return;
        }
        float f3 = this.cueLine;
        if (f3 != Float.MIN_VALUE) {
            if (this.cueLineType == 0) {
                firstLineHeight = Math.round(((float) parentHeight) * f3) + this.parentTop;
            } else {
                int firstLineHeight2 = this.textLayout.getLineBottom(0) - this.textLayout.getLineTop(0);
                float f4 = this.cueLine;
                if (f4 >= 0.0f) {
                    firstLineHeight = Math.round(f4 * ((float) firstLineHeight2)) + this.parentTop;
                } else {
                    firstLineHeight = Math.round((f4 + 1.0f) * ((float) firstLineHeight2)) + this.parentBottom;
                }
            }
            int i3 = this.cueLineAnchor;
            if (i3 == 2) {
                textTop2 = firstLineHeight - textHeight;
            } else if (i3 == 1) {
                textTop2 = ((firstLineHeight * 2) - textHeight) / 2;
            } else {
                textTop2 = firstLineHeight;
            }
            int i4 = textTop2 + textHeight;
            int i5 = this.parentBottom;
            if (i4 > i5) {
                textTop2 = i5 - textHeight;
            } else if (textTop2 < this.parentTop) {
                textTop2 = this.parentTop;
            }
        } else {
            textTop2 = (this.parentBottom - textHeight) - ((int) (((float) parentHeight) * this.bottomPaddingFraction));
        }
        this.textLayout = new StaticLayout(cueText4, this.textPaint, textWidth3, textAlignment, this.spacingMult, this.spacingAdd, true);
        this.textLeft = textLeft2;
        this.textTop = textTop2;
        this.textPaddingX = textPaddingX2;
    }

    private void setupBitmapLayout() {
        int height;
        float f;
        float f2;
        int i = this.parentRight;
        int i2 = this.parentLeft;
        int parentWidth = i - i2;
        int i3 = this.parentBottom;
        int i4 = this.parentTop;
        int parentHeight = i3 - i4;
        float anchorX = ((float) i2) + (((float) parentWidth) * this.cuePosition);
        float anchorY = ((float) i4) + (((float) parentHeight) * this.cueLine);
        int width = Math.round(((float) parentWidth) * this.cueSize);
        float f3 = this.cueBitmapHeight;
        if (f3 != Float.MIN_VALUE) {
            height = Math.round(((float) parentHeight) * f3);
        } else {
            height = Math.round(((float) width) * (((float) this.cueBitmap.getHeight()) / ((float) this.cueBitmap.getWidth())));
        }
        int i5 = this.cueLineAnchor;
        if (i5 == 2) {
            f = anchorX - ((float) width);
        } else {
            f = i5 == 1 ? anchorX - ((float) (width / 2)) : anchorX;
        }
        int x = Math.round(f);
        int i6 = this.cuePositionAnchor;
        if (i6 == 2) {
            f2 = anchorY - ((float) height);
        } else {
            f2 = i6 == 1 ? anchorY - ((float) (height / 2)) : anchorY;
        }
        int y = Math.round(f2);
        this.bitmapRect = new Rect(x, y, x + width, y + height);
    }

    private void drawLayout(Canvas canvas, boolean isTextCue) {
        if (isTextCue) {
            drawTextLayout(canvas);
        } else {
            drawBitmapLayout(canvas);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.Paint.setShadowLayer(float, float, float, int):void}
     arg types: [int, int, int, int]
     candidates:
      ClspMth{android.graphics.Paint.setShadowLayer(float, float, float, long):void}
      ClspMth{android.graphics.Paint.setShadowLayer(float, float, float, int):void} */
    private void drawTextLayout(Canvas canvas) {
        StaticLayout layout = this.textLayout;
        if (layout != null) {
            int saveCount = canvas.save();
            canvas.translate((float) this.textLeft, (float) this.textTop);
            if (Color.alpha(this.windowColor) > 0) {
                this.paint.setColor(this.windowColor);
                canvas.drawRect((float) (-this.textPaddingX), 0.0f, (float) (layout.getWidth() + this.textPaddingX), (float) layout.getHeight(), this.paint);
            }
            int i = this.edgeType;
            boolean z = true;
            if (i == 1) {
                this.textPaint.setStrokeJoin(Paint.Join.ROUND);
                this.textPaint.setStrokeWidth(this.outlineWidth);
                this.textPaint.setColor(this.edgeColor);
                this.textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                layout.draw(canvas);
            } else if (i == 2) {
                TextPaint textPaint2 = this.textPaint;
                float f = this.shadowRadius;
                float f2 = this.shadowOffset;
                textPaint2.setShadowLayer(f, f2, f2, this.edgeColor);
            } else if (i == 3 || i == 4) {
                if (this.edgeType != 3) {
                    z = false;
                }
                boolean raised = z;
                int colorDown = -1;
                int colorUp = raised ? -1 : this.edgeColor;
                if (raised) {
                    colorDown = this.edgeColor;
                }
                float offset = this.shadowRadius / 2.0f;
                this.textPaint.setColor(this.foregroundColor);
                this.textPaint.setStyle(Paint.Style.FILL);
                this.textPaint.setShadowLayer(this.shadowRadius, -offset, -offset, colorUp);
                layout.draw(canvas);
                this.textPaint.setShadowLayer(this.shadowRadius, offset, offset, colorDown);
            }
            this.textPaint.setColor(this.foregroundColor);
            this.textPaint.setStyle(Paint.Style.FILL);
            layout.draw(canvas);
            this.textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            canvas.restoreToCount(saveCount);
        }
    }

    private void drawBitmapLayout(Canvas canvas) {
        canvas.drawBitmap(this.cueBitmap, (Rect) null, this.bitmapRect, (Paint) null);
    }

    private static boolean areCharSequencesEqual(CharSequence first, CharSequence second) {
        return first == second || (first != null && first.equals(second));
    }
}
