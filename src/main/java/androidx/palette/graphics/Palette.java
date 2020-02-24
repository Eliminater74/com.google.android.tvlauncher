package androidx.palette.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.C0013Px;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.graphics.ColorUtils;
import android.support.p001v4.util.ArrayMap;
import android.support.p001v4.view.ViewCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TimingLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Palette {
    static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    static final Filter DEFAULT_FILTER = new Filter() {
        private static final float BLACK_MAX_LIGHTNESS = 0.05f;
        private static final float WHITE_MIN_LIGHTNESS = 0.95f;

        public boolean isAllowed(int rgb, float[] hsl) {
            return !isWhite(hsl) && !isBlack(hsl) && !isNearRedILine(hsl);
        }

        private boolean isBlack(float[] hslColor) {
            return hslColor[2] <= BLACK_MAX_LIGHTNESS;
        }

        private boolean isWhite(float[] hslColor) {
            return hslColor[2] >= WHITE_MIN_LIGHTNESS;
        }

        private boolean isNearRedILine(float[] hslColor) {
            return hslColor[0] >= 10.0f && hslColor[0] <= 37.0f && hslColor[1] <= 0.82f;
        }
    };
    static final int DEFAULT_RESIZE_BITMAP_AREA = 12544;
    static final String LOG_TAG = "Palette";
    static final boolean LOG_TIMINGS = false;
    static final float MIN_CONTRAST_BODY_TEXT = 4.5f;
    static final float MIN_CONTRAST_TITLE_TEXT = 3.0f;
    @Nullable
    private final Swatch mDominantSwatch = findDominantSwatch();
    private final Map<Target, Swatch> mSelectedSwatches = new ArrayMap();
    private final List<Swatch> mSwatches;
    private final List<Target> mTargets;
    private final SparseBooleanArray mUsedColors = new SparseBooleanArray();

    public interface Filter {
        boolean isAllowed(@ColorInt int i, @NonNull float[] fArr);
    }

    public interface PaletteAsyncListener {
        void onGenerated(@Nullable Palette palette);
    }

    @NonNull
    public static Builder from(@NonNull Bitmap bitmap) {
        return new Builder(bitmap);
    }

    @NonNull
    public static Palette from(@NonNull List<Swatch> swatches) {
        return new Builder(swatches).generate();
    }

    @Deprecated
    @NonNull
    public static Palette generate(@NonNull Bitmap bitmap) {
        return from(bitmap).generate();
    }

    @Deprecated
    @NonNull
    public static Palette generate(@NonNull Bitmap bitmap, int numColors) {
        return from(bitmap).maximumColorCount(numColors).generate();
    }

    @Deprecated
    @NonNull
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(@NonNull Bitmap bitmap, @NonNull PaletteAsyncListener listener) {
        return from(bitmap).generate(listener);
    }

    @Deprecated
    @NonNull
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(@NonNull Bitmap bitmap, int numColors, @NonNull PaletteAsyncListener listener) {
        return from(bitmap).maximumColorCount(numColors).generate(listener);
    }

    Palette(List<Swatch> swatches, List<Target> targets) {
        this.mSwatches = swatches;
        this.mTargets = targets;
    }

    @NonNull
    public List<Swatch> getSwatches() {
        return Collections.unmodifiableList(this.mSwatches);
    }

    @NonNull
    public List<Target> getTargets() {
        return Collections.unmodifiableList(this.mTargets);
    }

    @Nullable
    public Swatch getVibrantSwatch() {
        return getSwatchForTarget(Target.VIBRANT);
    }

    @Nullable
    public Swatch getLightVibrantSwatch() {
        return getSwatchForTarget(Target.LIGHT_VIBRANT);
    }

    @Nullable
    public Swatch getDarkVibrantSwatch() {
        return getSwatchForTarget(Target.DARK_VIBRANT);
    }

    @Nullable
    public Swatch getMutedSwatch() {
        return getSwatchForTarget(Target.MUTED);
    }

    @Nullable
    public Swatch getLightMutedSwatch() {
        return getSwatchForTarget(Target.LIGHT_MUTED);
    }

    @Nullable
    public Swatch getDarkMutedSwatch() {
        return getSwatchForTarget(Target.DARK_MUTED);
    }

    @ColorInt
    public int getVibrantColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.VIBRANT, defaultColor);
    }

    @ColorInt
    public int getLightVibrantColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.LIGHT_VIBRANT, defaultColor);
    }

    @ColorInt
    public int getDarkVibrantColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.DARK_VIBRANT, defaultColor);
    }

    @ColorInt
    public int getMutedColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.MUTED, defaultColor);
    }

    @ColorInt
    public int getLightMutedColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.LIGHT_MUTED, defaultColor);
    }

    @ColorInt
    public int getDarkMutedColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.DARK_MUTED, defaultColor);
    }

    @Nullable
    public Swatch getSwatchForTarget(@NonNull Target target) {
        return this.mSelectedSwatches.get(target);
    }

    @ColorInt
    public int getColorForTarget(@NonNull Target target, @ColorInt int defaultColor) {
        Swatch swatch = getSwatchForTarget(target);
        return swatch != null ? swatch.getRgb() : defaultColor;
    }

    @Nullable
    public Swatch getDominantSwatch() {
        return this.mDominantSwatch;
    }

    @ColorInt
    public int getDominantColor(@ColorInt int defaultColor) {
        Swatch swatch = this.mDominantSwatch;
        return swatch != null ? swatch.getRgb() : defaultColor;
    }

    /* access modifiers changed from: package-private */
    public void generate() {
        int count = this.mTargets.size();
        for (int i = 0; i < count; i++) {
            Target target = this.mTargets.get(i);
            target.normalizeWeights();
            this.mSelectedSwatches.put(target, generateScoredTarget(target));
        }
        this.mUsedColors.clear();
    }

    @Nullable
    private Swatch generateScoredTarget(Target target) {
        Swatch maxScoreSwatch = getMaxScoredSwatchForTarget(target);
        if (maxScoreSwatch != null && target.isExclusive()) {
            this.mUsedColors.append(maxScoreSwatch.getRgb(), true);
        }
        return maxScoreSwatch;
    }

    @Nullable
    private Swatch getMaxScoredSwatchForTarget(Target target) {
        float maxScore = 0.0f;
        Swatch maxScoreSwatch = null;
        int count = this.mSwatches.size();
        for (int i = 0; i < count; i++) {
            Swatch swatch = this.mSwatches.get(i);
            if (shouldBeScoredForTarget(swatch, target)) {
                float score = generateScore(swatch, target);
                if (maxScoreSwatch == null || score > maxScore) {
                    maxScoreSwatch = swatch;
                    maxScore = score;
                }
            }
        }
        return maxScoreSwatch;
    }

    private boolean shouldBeScoredForTarget(Swatch swatch, Target target) {
        float[] hsl = swatch.getHsl();
        if (hsl[1] < target.getMinimumSaturation() || hsl[1] > target.getMaximumSaturation() || hsl[2] < target.getMinimumLightness() || hsl[2] > target.getMaximumLightness() || this.mUsedColors.get(swatch.getRgb())) {
            return false;
        }
        return true;
    }

    private float generateScore(Swatch swatch, Target target) {
        float[] hsl = swatch.getHsl();
        float saturationScore = 0.0f;
        float luminanceScore = 0.0f;
        float populationScore = 0.0f;
        Swatch swatch2 = this.mDominantSwatch;
        int maxPopulation = swatch2 != null ? swatch2.getPopulation() : 1;
        if (target.getSaturationWeight() > 0.0f) {
            saturationScore = target.getSaturationWeight() * (1.0f - Math.abs(hsl[1] - target.getTargetSaturation()));
        }
        if (target.getLightnessWeight() > 0.0f) {
            luminanceScore = target.getLightnessWeight() * (1.0f - Math.abs(hsl[2] - target.getTargetLightness()));
        }
        if (target.getPopulationWeight() > 0.0f) {
            populationScore = target.getPopulationWeight() * (((float) swatch.getPopulation()) / ((float) maxPopulation));
        }
        return saturationScore + luminanceScore + populationScore;
    }

    @Nullable
    private Swatch findDominantSwatch() {
        int maxPop = Integer.MIN_VALUE;
        Swatch maxSwatch = null;
        int count = this.mSwatches.size();
        for (int i = 0; i < count; i++) {
            Swatch swatch = this.mSwatches.get(i);
            if (swatch.getPopulation() > maxPop) {
                maxSwatch = swatch;
                maxPop = swatch.getPopulation();
            }
        }
        return maxSwatch;
    }

    public static final class Swatch {
        private final int mBlue;
        private int mBodyTextColor;
        private boolean mGeneratedTextColors;
        private final int mGreen;
        @Nullable
        private float[] mHsl;
        private final int mPopulation;
        private final int mRed;
        private final int mRgb;
        private int mTitleTextColor;

        public Swatch(@ColorInt int color, int population) {
            this.mRed = Color.red(color);
            this.mGreen = Color.green(color);
            this.mBlue = Color.blue(color);
            this.mRgb = color;
            this.mPopulation = population;
        }

        @ColorInt
        public int getRgb() {
            return this.mRgb;
        }

        @NonNull
        public float[] getHsl() {
            if (this.mHsl == null) {
                this.mHsl = new float[3];
            }
            ColorUtils.RGBToHSL(this.mRed, this.mGreen, this.mBlue, this.mHsl);
            return this.mHsl;
        }

        public int getPopulation() {
            return this.mPopulation;
        }

        @ColorInt
        public int getTitleTextColor() {
            ensureTextColorsGenerated();
            return this.mTitleTextColor;
        }

        @ColorInt
        public int getBodyTextColor() {
            ensureTextColorsGenerated();
            return this.mBodyTextColor;
        }

        private void ensureTextColorsGenerated() {
            int i;
            int i2;
            if (!this.mGeneratedTextColors) {
                int lightBodyAlpha = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, Palette.MIN_CONTRAST_BODY_TEXT);
                int lightTitleAlpha = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, Palette.MIN_CONTRAST_TITLE_TEXT);
                if (lightBodyAlpha == -1 || lightTitleAlpha == -1) {
                    int darkBodyAlpha = ColorUtils.calculateMinimumAlpha(ViewCompat.MEASURED_STATE_MASK, this.mRgb, Palette.MIN_CONTRAST_BODY_TEXT);
                    int darkTitleAlpha = ColorUtils.calculateMinimumAlpha(ViewCompat.MEASURED_STATE_MASK, this.mRgb, Palette.MIN_CONTRAST_TITLE_TEXT);
                    if (darkBodyAlpha == -1 || darkTitleAlpha == -1) {
                        if (lightBodyAlpha != -1) {
                            i = ColorUtils.setAlphaComponent(-1, lightBodyAlpha);
                        } else {
                            i = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkBodyAlpha);
                        }
                        this.mBodyTextColor = i;
                        if (lightTitleAlpha != -1) {
                            i2 = ColorUtils.setAlphaComponent(-1, lightTitleAlpha);
                        } else {
                            i2 = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkTitleAlpha);
                        }
                        this.mTitleTextColor = i2;
                        this.mGeneratedTextColors = true;
                        return;
                    }
                    this.mBodyTextColor = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkBodyAlpha);
                    this.mTitleTextColor = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkTitleAlpha);
                    this.mGeneratedTextColors = true;
                    return;
                }
                this.mBodyTextColor = ColorUtils.setAlphaComponent(-1, lightBodyAlpha);
                this.mTitleTextColor = ColorUtils.setAlphaComponent(-1, lightTitleAlpha);
                this.mGeneratedTextColors = true;
            }
        }

        @NonNull
        public String toString() {
            return getClass().getSimpleName() + " [RGB: #" + Integer.toHexString(getRgb()) + ']' + " [HSL: " + Arrays.toString(getHsl()) + ']' + " [Population: " + this.mPopulation + ']' + " [Title Text: #" + Integer.toHexString(getTitleTextColor()) + ']' + " [Body Text: #" + Integer.toHexString(getBodyTextColor()) + ']';
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Swatch swatch = (Swatch) o;
            if (this.mPopulation == swatch.mPopulation && this.mRgb == swatch.mRgb) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.mRgb * 31) + this.mPopulation;
        }
    }

    public static final class Builder {
        @Nullable
        private final Bitmap mBitmap;
        private final List<Filter> mFilters = new ArrayList();
        private int mMaxColors = 16;
        @Nullable
        private Rect mRegion;
        private int mResizeArea = Palette.DEFAULT_RESIZE_BITMAP_AREA;
        private int mResizeMaxDimension = -1;
        @Nullable
        private final List<Swatch> mSwatches;
        private final List<Target> mTargets = new ArrayList();

        public Builder(@NonNull Bitmap bitmap) {
            if (bitmap == null || bitmap.isRecycled()) {
                throw new IllegalArgumentException("Bitmap is not valid");
            }
            this.mFilters.add(Palette.DEFAULT_FILTER);
            this.mBitmap = bitmap;
            this.mSwatches = null;
            this.mTargets.add(Target.LIGHT_VIBRANT);
            this.mTargets.add(Target.VIBRANT);
            this.mTargets.add(Target.DARK_VIBRANT);
            this.mTargets.add(Target.LIGHT_MUTED);
            this.mTargets.add(Target.MUTED);
            this.mTargets.add(Target.DARK_MUTED);
        }

        public Builder(@NonNull List<Swatch> swatches) {
            if (swatches == null || swatches.isEmpty()) {
                throw new IllegalArgumentException("List of Swatches is not valid");
            }
            this.mFilters.add(Palette.DEFAULT_FILTER);
            this.mSwatches = swatches;
            this.mBitmap = null;
        }

        @NonNull
        public Builder maximumColorCount(int colors) {
            this.mMaxColors = colors;
            return this;
        }

        @Deprecated
        @NonNull
        public Builder resizeBitmapSize(int maxDimension) {
            this.mResizeMaxDimension = maxDimension;
            this.mResizeArea = -1;
            return this;
        }

        @NonNull
        public Builder resizeBitmapArea(int area) {
            this.mResizeArea = area;
            this.mResizeMaxDimension = -1;
            return this;
        }

        @NonNull
        public Builder clearFilters() {
            this.mFilters.clear();
            return this;
        }

        @NonNull
        public Builder addFilter(@NonNull Filter filter) {
            if (filter != null) {
                this.mFilters.add(filter);
            }
            return this;
        }

        @NonNull
        public Builder setRegion(@C0013Px int left, @C0013Px int top, @C0013Px int right, @C0013Px int bottom) {
            if (this.mBitmap != null) {
                if (this.mRegion == null) {
                    this.mRegion = new Rect();
                }
                this.mRegion.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                if (!this.mRegion.intersect(left, top, right, bottom)) {
                    throw new IllegalArgumentException("The given region must intersect with the Bitmap's dimensions.");
                }
            }
            return this;
        }

        @NonNull
        public Builder clearRegion() {
            this.mRegion = null;
            return this;
        }

        @NonNull
        public Builder addTarget(@NonNull Target target) {
            if (!this.mTargets.contains(target)) {
                this.mTargets.add(target);
            }
            return this;
        }

        @NonNull
        public Builder clearTargets() {
            List<Target> list = this.mTargets;
            if (list != null) {
                list.clear();
            }
            return this;
        }

        @NonNull
        public Palette generate() {
            List<Swatch> swatches;
            Filter[] filterArr;
            TimingLogger logger = null;
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                Bitmap bitmap2 = scaleBitmapDown(bitmap);
                if (logger != null) {
                    logger.addSplit("Processed Bitmap");
                }
                Rect region = this.mRegion;
                if (!(bitmap2 == this.mBitmap || region == null)) {
                    double width = (double) bitmap2.getWidth();
                    double width2 = (double) this.mBitmap.getWidth();
                    Double.isNaN(width);
                    Double.isNaN(width2);
                    double scale = width / width2;
                    double d = (double) region.left;
                    Double.isNaN(d);
                    region.left = (int) Math.floor(d * scale);
                    double d2 = (double) region.top;
                    Double.isNaN(d2);
                    region.top = (int) Math.floor(d2 * scale);
                    double d3 = (double) region.right;
                    Double.isNaN(d3);
                    region.right = Math.min((int) Math.ceil(d3 * scale), bitmap2.getWidth());
                    double d4 = (double) region.bottom;
                    Double.isNaN(d4);
                    region.bottom = Math.min((int) Math.ceil(d4 * scale), bitmap2.getHeight());
                }
                int[] pixelsFromBitmap = getPixelsFromBitmap(bitmap2);
                int i = this.mMaxColors;
                if (this.mFilters.isEmpty()) {
                    filterArr = null;
                } else {
                    List<Filter> list = this.mFilters;
                    filterArr = (Filter[]) list.toArray(new Filter[list.size()]);
                }
                ColorCutQuantizer quantizer = new ColorCutQuantizer(pixelsFromBitmap, i, filterArr);
                if (bitmap2 != this.mBitmap) {
                    bitmap2.recycle();
                }
                swatches = quantizer.getQuantizedColors();
                if (logger != null) {
                    logger.addSplit("Color quantization completed");
                }
            } else if (this.mSwatches != null) {
                swatches = this.mSwatches;
            } else {
                throw new AssertionError();
            }
            Palette p = new Palette(swatches, this.mTargets);
            p.generate();
            if (logger != null) {
                logger.addSplit("Created Palette");
                logger.dumpToLog();
            }
            return p;
        }

        @NonNull
        public AsyncTask<Bitmap, Void, Palette> generate(@NonNull final PaletteAsyncListener listener) {
            if (listener != null) {
                return new AsyncTask<Bitmap, Void, Palette>() {
                    /* access modifiers changed from: protected */
                    @Nullable
                    public Palette doInBackground(Bitmap... params) {
                        try {
                            return Builder.this.generate();
                        } catch (Exception e) {
                            Log.e(Palette.LOG_TAG, "Exception thrown during async generate", e);
                            return null;
                        }
                    }

                    /* access modifiers changed from: protected */
                    public void onPostExecute(@Nullable Palette colorExtractor) {
                        listener.onGenerated(colorExtractor);
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.mBitmap);
            }
            throw new IllegalArgumentException("listener can not be null");
        }

        private int[] getPixelsFromBitmap(Bitmap bitmap) {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            int[] pixels = new int[(bitmapWidth * bitmapHeight)];
            bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
            Rect rect = this.mRegion;
            if (rect == null) {
                return pixels;
            }
            int regionWidth = rect.width();
            int regionHeight = this.mRegion.height();
            int[] subsetPixels = new int[(regionWidth * regionHeight)];
            for (int row = 0; row < regionHeight; row++) {
                System.arraycopy(pixels, ((this.mRegion.top + row) * bitmapWidth) + this.mRegion.left, subsetPixels, row * regionWidth, regionWidth);
            }
            return subsetPixels;
        }

        private Bitmap scaleBitmapDown(Bitmap bitmap) {
            int maxDimension;
            int i;
            double scaleRatio = -1.0d;
            if (this.mResizeArea > 0) {
                int bitmapArea = bitmap.getWidth() * bitmap.getHeight();
                int i2 = this.mResizeArea;
                if (bitmapArea > i2) {
                    double d = (double) i2;
                    double d2 = (double) bitmapArea;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    scaleRatio = Math.sqrt(d / d2);
                }
            } else if (this.mResizeMaxDimension > 0 && (maxDimension = Math.max(bitmap.getWidth(), bitmap.getHeight())) > (i = this.mResizeMaxDimension)) {
                double d3 = (double) i;
                double d4 = (double) maxDimension;
                Double.isNaN(d3);
                Double.isNaN(d4);
                scaleRatio = d3 / d4;
            }
            if (scaleRatio <= 0.0d) {
                return bitmap;
            }
            double width = (double) bitmap.getWidth();
            Double.isNaN(width);
            double height = (double) bitmap.getHeight();
            Double.isNaN(height);
            return Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(width * scaleRatio), (int) Math.ceil(height * scaleRatio), false);
        }
    }
}
