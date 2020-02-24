package com.google.android.exoplayer2.p008ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.ui.SubtitleView */
public final class SubtitleView extends View implements TextOutput {
    public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08f;
    public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533f;
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private float bottomPaddingFraction;
    private List<Cue> cues;
    private final List<SubtitlePainter> painters;
    private CaptionStyleCompat style;
    private float textSize;
    private int textSizeType;

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.painters = new ArrayList();
        this.textSizeType = 0;
        this.textSize = 0.0533f;
        this.applyEmbeddedStyles = true;
        this.applyEmbeddedFontSizes = true;
        this.style = CaptionStyleCompat.DEFAULT;
        this.bottomPaddingFraction = 0.08f;
    }

    public void onCues(List<Cue> cues2) {
        setCues(cues2);
    }

    public void setCues(@Nullable List<Cue> cues2) {
        if (this.cues != cues2) {
            this.cues = cues2;
            int cueCount = cues2 == null ? 0 : cues2.size();
            while (this.painters.size() < cueCount) {
                this.painters.add(new SubtitlePainter(getContext()));
            }
            invalidate();
        }
    }

    public void setFixedTextSize(int unit, float size) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        setTextSize(2, TypedValue.applyDimension(unit, size, resources.getDisplayMetrics()));
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(0.0533f * ((Util.SDK_INT < 19 || isInEditMode()) ? 1.0f : getUserCaptionFontScaleV19()));
    }

    public void setFractionalTextSize(float fractionOfHeight) {
        setFractionalTextSize(fractionOfHeight, false);
    }

    public void setFractionalTextSize(float fractionOfHeight, boolean ignorePadding) {
        int i;
        if (ignorePadding) {
            i = 1;
        } else {
            i = 0;
        }
        setTextSize(i, fractionOfHeight);
    }

    private void setTextSize(int textSizeType2, float textSize2) {
        if (this.textSizeType != textSizeType2 || this.textSize != textSize2) {
            this.textSizeType = textSizeType2;
            this.textSize = textSize2;
            invalidate();
        }
    }

    public void setApplyEmbeddedStyles(boolean applyEmbeddedStyles2) {
        if (this.applyEmbeddedStyles != applyEmbeddedStyles2 || this.applyEmbeddedFontSizes != applyEmbeddedStyles2) {
            this.applyEmbeddedStyles = applyEmbeddedStyles2;
            this.applyEmbeddedFontSizes = applyEmbeddedStyles2;
            invalidate();
        }
    }

    public void setApplyEmbeddedFontSizes(boolean applyEmbeddedFontSizes2) {
        if (this.applyEmbeddedFontSizes != applyEmbeddedFontSizes2) {
            this.applyEmbeddedFontSizes = applyEmbeddedFontSizes2;
            invalidate();
        }
    }

    public void setUserDefaultStyle() {
        CaptionStyleCompat captionStyleCompat;
        if (Util.SDK_INT < 19 || !isCaptionManagerEnabled() || isInEditMode()) {
            captionStyleCompat = CaptionStyleCompat.DEFAULT;
        } else {
            captionStyleCompat = getUserCaptionStyleV19();
        }
        setStyle(captionStyleCompat);
    }

    public void setStyle(CaptionStyleCompat style2) {
        if (this.style != style2) {
            this.style = style2;
            invalidate();
        }
    }

    public void setBottomPaddingFraction(float bottomPaddingFraction2) {
        if (this.bottomPaddingFraction != bottomPaddingFraction2) {
            this.bottomPaddingFraction = bottomPaddingFraction2;
            invalidate();
        }
    }

    public void dispatchDraw(Canvas canvas) {
        List<Cue> list = this.cues;
        int cueCount = list == null ? 0 : list.size();
        int rawViewHeight = getHeight();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = rawViewHeight - getPaddingBottom();
        if (bottom <= top) {
            return;
        }
        if (right > left) {
            int viewHeightMinusPadding = bottom - top;
            float defaultViewTextSizePx = resolveTextSize(this.textSizeType, this.textSize, rawViewHeight, viewHeightMinusPadding);
            if (defaultViewTextSizePx > 0.0f) {
                int i = 0;
                while (i < cueCount) {
                    Cue cue = this.cues.get(i);
                    int bottom2 = bottom;
                    int right2 = right;
                    this.painters.get(i).draw(cue, this.applyEmbeddedStyles, this.applyEmbeddedFontSizes, this.style, defaultViewTextSizePx, resolveCueTextSize(cue, rawViewHeight, viewHeightMinusPadding), this.bottomPaddingFraction, canvas, left, top, right2, bottom2);
                    i++;
                    viewHeightMinusPadding = viewHeightMinusPadding;
                    bottom = bottom2;
                    right = right2;
                    top = top;
                    left = left;
                }
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(float, float):float}
     arg types: [float, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(long, long):long}
      ClspMth{java.lang.Math.max(float, float):float} */
    private float resolveCueTextSize(Cue cue, int rawViewHeight, int viewHeightMinusPadding) {
        if (cue.textSizeType == Integer.MIN_VALUE || cue.textSize == Float.MIN_VALUE) {
            return 0.0f;
        }
        return Math.max(resolveTextSize(cue.textSizeType, cue.textSize, rawViewHeight, viewHeightMinusPadding), 0.0f);
    }

    private float resolveTextSize(int textSizeType2, float textSize2, int rawViewHeight, int viewHeightMinusPadding) {
        if (textSizeType2 == 0) {
            return ((float) viewHeightMinusPadding) * textSize2;
        }
        if (textSizeType2 == 1) {
            return ((float) rawViewHeight) * textSize2;
        }
        if (textSizeType2 != 2) {
            return Float.MIN_VALUE;
        }
        return textSize2;
    }

    @TargetApi(19)
    private boolean isCaptionManagerEnabled() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).isEnabled();
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        return CaptionStyleCompat.createFromCaptionStyle(((CaptioningManager) getContext().getSystemService("captioning")).getUserStyle());
    }
}
