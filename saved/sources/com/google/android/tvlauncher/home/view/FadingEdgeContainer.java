package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.TestsBuildCompat;

public class FadingEdgeContainer extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final int[] FADE_COLORS_LTR = new int[21];
    private static final int[] FADE_COLORS_RTL = new int[21];
    private static final float[] FADE_COLOR_POSITIONS = new float[21];
    private static final int GRADIENT_CURVE_STEEPNESS = 100;
    private static final int GRADIENT_STEPS = 20;
    private static final String TAG = "FadingEdgeContainer";
    private boolean mFadeEnabled = true;
    private int mFadeWidth;
    private Paint mGradientPaint;
    private Rect mGradientRect;

    static {
        FADE_COLORS_LTR[0] = 0;
        for (int i = 1; i <= 20; i++) {
            double d = (double) i;
            Double.isNaN(d);
            float alpha = (float) Math.pow(100.0d, (d / 20.0d) - 1.0d);
            if (TestsBuildCompat.isAtLeastO()) {
                FADE_COLORS_LTR[i] = Color.argb(alpha, 0.0f, 0.0f, 0.0f);
            } else {
                FADE_COLORS_LTR[i] = Color.rgb(0, 0, 0);
            }
            FADE_COLOR_POSITIONS[i] = ((float) i) / 20.0f;
        }
        int i2 = 0;
        while (true) {
            int[] iArr = FADE_COLORS_LTR;
            if (i2 < iArr.length) {
                FADE_COLORS_RTL[(iArr.length - i2) - 1] = iArr[i2];
                i2++;
            } else {
                return;
            }
        }
    }

    public FadingEdgeContainer(@NonNull Context context) {
        super(context);
        init();
    }

    public FadingEdgeContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FadingEdgeContainer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mFadeWidth = getContext().getResources().getDimensionPixelOffset(C1188R.dimen.channel_items_list_fade_width);
        this.mGradientRect = new Rect();
    }

    public void setFadeEnabled(boolean enabled) {
        this.mFadeEnabled = enabled;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void}
     arg types: [int, int, float, int, int[], float[], android.graphics.Shader$TileMode]
     candidates:
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long, long, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int, int, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void} */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void}
     arg types: [float, int, float, int, int[], float[], android.graphics.Shader$TileMode]
     candidates:
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long, long, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int, int, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void} */
    private void setUpPaint(int layoutWidth) {
        LinearGradient gradient;
        this.mGradientPaint = new Paint();
        if (getLayoutDirection() == 0) {
            gradient = new LinearGradient(0.0f, 0.0f, (float) this.mFadeWidth, 0.0f, FADE_COLORS_LTR, FADE_COLOR_POSITIONS, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient((float) (layoutWidth - this.mFadeWidth), 0.0f, (float) layoutWidth, 0.0f, FADE_COLORS_RTL, FADE_COLOR_POSITIONS, Shader.TileMode.CLAMP);
        }
        this.mGradientPaint.setShader(gradient);
        this.mGradientPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        if (this.mFadeEnabled && this.mGradientPaint == null) {
            setUpPaint(width);
        }
        if (getLayoutDirection() == 0) {
            if (this.mFadeEnabled) {
                canvas.saveLayer(0.0f, 0.0f, (float) this.mFadeWidth, (float) height, null);
                super.dispatchDraw(canvas);
                this.mGradientRect.set(0, 0, this.mFadeWidth, height);
                canvas.drawRect(this.mGradientRect, this.mGradientPaint);
                canvas.restore();
            }
            canvas.clipRect(this.mFadeWidth, 0, width, height);
            super.dispatchDraw(canvas);
            return;
        }
        if (this.mFadeEnabled) {
            canvas.saveLayer(0.0f, 0.0f, (float) width, (float) height, null);
            canvas.clipRect(width - this.mFadeWidth, 0, width, height);
            super.dispatchDraw(canvas);
            this.mGradientRect.set(width - this.mFadeWidth, 0, width, height);
            canvas.drawRect(this.mGradientRect, this.mGradientPaint);
            canvas.restore();
        }
        canvas.clipRect(0, 0, width - this.mFadeWidth, height);
        super.dispatchDraw(canvas);
    }
}
