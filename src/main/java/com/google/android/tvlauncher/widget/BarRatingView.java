package com.google.android.tvlauncher.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.tvlauncher.C1188R;

public class BarRatingView extends View {
    private static final int MAX_RATING = 5;
    private static final float STEP_SIZE = 0.25f;
    private float mFillPortion;
    private RectF mFilledItemRect;
    private Paint mFilledPaint;
    private Bitmap mItemFilledBitmap;
    private Bitmap mItemUnfilledBitmap;
    private int mLayoutHeight;
    private int mLayoutWidth;
    private final int mOverallScore;
    private float mRating;
    private RectF mUnfilledItemRect;
    private Paint mUnfilledPaint;

    public BarRatingView(Context context) {
        this(context, null);
    }

    public BarRatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        int itemWidth = context.getResources().getDimensionPixelSize(C1188R.dimen.program_meta_rating_size);
        this.mLayoutHeight = itemWidth;
        this.mLayoutWidth = itemWidth * 5;
        this.mItemFilledBitmap = convertToBitmap(context.getDrawable(C1188R.C1189drawable.ic_channel_view_filled_item_active), itemWidth, this.mLayoutHeight);
        this.mItemUnfilledBitmap = convertToBitmap(context.getDrawable(C1188R.C1189drawable.ic_channel_view_filled_item_inactive), itemWidth, this.mLayoutHeight);
        this.mFilledPaint = new Paint();
        this.mFilledPaint.setShader(new BitmapShader(this.mItemFilledBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        this.mUnfilledPaint = new Paint();
        this.mUnfilledPaint.setShader(new BitmapShader(this.mItemUnfilledBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        this.mOverallScore = 20;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.mLayoutWidth, this.mLayoutHeight);
    }

    private Bitmap convertToBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setRating(float rating) {
        int roundedScore;
        this.mRating = rating;
        float score = (rating / 5.0f) * ((float) this.mOverallScore);
        if (rating < 0.0f) {
            roundedScore = 0;
        } else if (rating > 5.0f) {
            roundedScore = this.mOverallScore;
        } else if (score <= 0.0f || score >= 1.0f) {
            int roundedScore2 = this.mOverallScore;
            if (score <= ((float) (roundedScore2 - 1)) || score >= ((float) roundedScore2)) {
                roundedScore = Math.round(score);
            } else {
                roundedScore = roundedScore2 - 1;
            }
        } else {
            roundedScore = 1;
        }
        this.mFillPortion = (((float) roundedScore) * 1.0f) / ((float) this.mOverallScore);
        if (getLayoutDirection() == 1) {
            int i = this.mLayoutWidth;
            this.mFilledItemRect = new RectF((1.0f - this.mFillPortion) * ((float) i), 0.0f, (float) i, (float) this.mLayoutHeight);
            this.mUnfilledItemRect = new RectF(0.0f, 0.0f, (1.0f - this.mFillPortion) * ((float) this.mLayoutWidth), (float) this.mLayoutHeight);
        } else {
            this.mFilledItemRect = new RectF(0.0f, 0.0f, this.mFillPortion * ((float) this.mLayoutWidth), (float) this.mLayoutHeight);
            float f = this.mFillPortion;
            int i2 = this.mLayoutWidth;
            this.mUnfilledItemRect = new RectF(f * ((float) i2), 0.0f, (float) i2, (float) this.mLayoutHeight);
        }
        invalidate();
    }

    @VisibleForTesting
    public float getRating() {
        return this.mRating;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawRect(this.mUnfilledItemRect, this.mUnfilledPaint);
        canvas.drawRect(this.mFilledItemRect, this.mFilledPaint);
    }
}
