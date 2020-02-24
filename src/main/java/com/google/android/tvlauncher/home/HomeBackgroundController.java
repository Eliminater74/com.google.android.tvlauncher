package com.google.android.tvlauncher.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.view.View;
import androidx.palette.graphics.Palette;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.TestsBuildCompat;
import com.google.android.tvlauncher.view.BackgroundTransitionDrawable;
import com.google.android.tvrecommendations.shared.util.ColorUtils;

class HomeBackgroundController {
    private static final int BITMAP_HEIGHT = 540;
    private static final float BITMAP_SCALE = 0.5f;
    private static final int BITMAP_WIDTH = 960;
    private static final float DARK_MODE_COLOR_DARKEN_FACTOR = 0.5f;
    private static final boolean DEBUG = false;
    private static final int FALLBACK_COLOR = -16777216;
    private static final int RADIAL_GRADIENT_VERTICAL_SHIFT = -300;
    private static final float STANDARD_COLOR_DARKEN_FACTOR = 0.4f;
    private static final String TAG = "HomeBackground";
    private static final float TOP_GRADIENT_COLOR_MIX_AMOUNT = 0.2f;
    private static final int TRANSITION_DURATION_MILLIS = 600;
    @ColorInt
    private int mColor1;
    @ColorInt
    private int mColor2;
    @ColorInt
    private int mColor3;
    private final Context mContext;
    private boolean mDarkMode = false;
    /* access modifiers changed from: private */
    public GenerateBitmapTask mGenerateBitmapTask;
    private Paint mLinearGradientPaint;
    private Bitmap mOverlayBitmap;
    private Paint mOverlayPaint;
    private Paint mRadialGradientPaint;
    /* access modifiers changed from: private */
    public BackgroundTransitionDrawable mTransitionDrawable;

    HomeBackgroundController(View backgroundView) {
        this.mContext = backgroundView.getContext();
        this.mTransitionDrawable = new BackgroundTransitionDrawable(new BitmapDrawable[]{createBackgroundDrawable(), createBackgroundDrawable(), createBackgroundDrawable()});
        backgroundView.setBackground(this.mTransitionDrawable);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.Bitmap.createBitmap(int, int, android.graphics.Bitmap$Config, boolean):android.graphics.Bitmap}
     arg types: [int, int, android.graphics.Bitmap$Config, int]
     candidates:
      ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(int[], int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Picture, int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(int, int, android.graphics.Bitmap$Config, boolean):android.graphics.Bitmap} */
    private BitmapDrawable createBackgroundDrawable() {
        Bitmap backgroundBitmap;
        if (TestsBuildCompat.isAtLeastO()) {
            backgroundBitmap = Bitmap.createBitmap(960, 540, Bitmap.Config.ARGB_8888, false);
        } else {
            backgroundBitmap = Bitmap.createBitmap(960, 540, Bitmap.Config.ARGB_8888);
        }
        BitmapDrawable drawable = new BitmapDrawable(this.mContext.getResources(), backgroundBitmap);
        drawable.setAutoMirrored(true);
        return drawable;
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void updateBackground(@NonNull Palette palette) {
        updateBackground(ColorUtils.darkenColor(palette.getVibrantColor(palette.getMutedColor(-16777216)), STANDARD_COLOR_DARKEN_FACTOR), ColorUtils.darkenColor(palette.getDarkVibrantColor(palette.getDarkMutedColor(-16777216)), STANDARD_COLOR_DARKEN_FACTOR), ColorUtils.darkenColor(palette.getLightVibrantColor(palette.getLightMutedColor(-16777216)), STANDARD_COLOR_DARKEN_FACTOR));
    }

    @MainThread
    private void updateBackground(@ColorInt int color1, @ColorInt int color2, @ColorInt int color3) {
        this.mDarkMode = false;
        if (this.mColor1 != color1 || this.mColor2 != color2 || this.mColor3 != color3) {
            this.mColor1 = color1;
            this.mColor2 = color2;
            this.mColor3 = color3;
            GenerateBitmapTask generateBitmapTask = this.mGenerateBitmapTask;
            if (generateBitmapTask != null) {
                generateBitmapTask.cancel(true);
            }
            this.mGenerateBitmapTask = new GenerateBitmapTask();
            this.mGenerateBitmapTask.execute((BitmapDrawable) this.mTransitionDrawable.getBackBuffer());
        }
    }

    /* access modifiers changed from: package-private */
    public void enterDarkMode() {
        if (!this.mDarkMode) {
            updateBackground(ColorUtils.darkenColor(this.mColor1, 0.5f), ColorUtils.darkenColor(this.mColor2, 0.5f), ColorUtils.darkenColor(this.mColor3, 0.5f));
            this.mDarkMode = true;
        }
    }

    @ColorInt
    @WorkerThread
    private int mixColors(@ColorInt int color1, @ColorInt int color2, float amount) {
        float inverseAmount = 1.0f - amount;
        return Color.rgb((int) ((((float) Color.red(color1)) * amount) + (((float) Color.red(color2)) * inverseAmount)), (int) ((((float) Color.green(color1)) * amount) + (((float) Color.green(color2)) * inverseAmount)), (int) ((((float) Color.blue(color1)) * amount) + (((float) Color.blue(color2)) * inverseAmount)));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int, int, android.graphics.Shader$TileMode):void}
     arg types: [int, int, int, int, int, int, android.graphics.Shader$TileMode]
     candidates:
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long, long, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int, int, android.graphics.Shader$TileMode):void} */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.RadialGradient.<init>(float, float, float, int, int, android.graphics.Shader$TileMode):void}
     arg types: [int, int, float, int, int, android.graphics.Shader$TileMode]
     candidates:
      ClspMth{android.graphics.RadialGradient.<init>(float, float, float, long, long, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.RadialGradient.<init>(float, float, float, int[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.RadialGradient.<init>(float, float, float, long[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.RadialGradient.<init>(float, float, float, int, int, android.graphics.Shader$TileMode):void} */
    /* access modifiers changed from: private */
    @WorkerThread
    public void generateBitmap(BitmapDrawable bitmapDrawable) {
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 540.0f, mixColors(this.mColor2, this.mColor1, TOP_GRADIENT_COLOR_MIX_AMOUNT), this.mColor2, Shader.TileMode.CLAMP);
        if (this.mLinearGradientPaint == null) {
            this.mLinearGradientPaint = new Paint();
        }
        this.mLinearGradientPaint.setShader(linearGradient);
        RadialGradient radialGradient = new RadialGradient(960.0f, -300.0f, (float) ((int) Math.sqrt(1627200.0d)), this.mColor3, 0, Shader.TileMode.CLAMP);
        if (this.mRadialGradientPaint == null) {
            this.mRadialGradientPaint = new Paint();
        }
        this.mRadialGradientPaint.setShader(radialGradient);
        if (this.mOverlayBitmap == null) {
            this.mOverlayBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), C1188R.C1189drawable.home_background_overlay);
            this.mOverlayPaint = new Paint();
            this.mOverlayPaint.setShader(new BitmapShader(this.mOverlayBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        }
        Canvas c = new Canvas(bitmapDrawable.getBitmap());
        c.drawRect(0.0f, 0.0f, 960.0f, 540.0f, this.mLinearGradientPaint);
        c.drawRect(0.0f, 0.0f, 960.0f, 540.0f, this.mRadialGradientPaint);
        c.drawRect(0.0f, 0.0f, 960.0f, 540.0f, this.mOverlayPaint);
    }

    private class GenerateBitmapTask extends AsyncTask<BitmapDrawable, Void, Void> {
        private GenerateBitmapTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(BitmapDrawable... bitmapDrawable) {
            HomeBackgroundController.this.generateBitmap(bitmapDrawable[0]);
            return null;
        }

        /* access modifiers changed from: protected */
        @MainThread
        public void onPostExecute(Void result) {
            GenerateBitmapTask unused = HomeBackgroundController.this.mGenerateBitmapTask = null;
            HomeBackgroundController.this.mTransitionDrawable.animateFadeIn(600);
        }
    }
}
