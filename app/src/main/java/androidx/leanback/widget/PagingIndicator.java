package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.leanback.C0364R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class PagingIndicator extends View {
    private static final TimeInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final Property<Dot, Float> DOT_ALPHA = new Property<Dot, Float>(Float.class, "alpha") {
        public Float get(Dot dot) {
            return Float.valueOf(dot.getAlpha());
        }

        public void set(Dot dot, Float value) {
            dot.setAlpha(value.floatValue());
        }
    };
    private static final Property<Dot, Float> DOT_DIAMETER = new Property<Dot, Float>(Float.class, "diameter") {
        public Float get(Dot dot) {
            return Float.valueOf(dot.getDiameter());
        }

        public void set(Dot dot, Float value) {
            dot.setDiameter(value.floatValue());
        }
    };
    private static final Property<Dot, Float> DOT_TRANSLATION_X = new Property<Dot, Float>(Float.class, "translation_x") {
        public Float get(Dot dot) {
            return Float.valueOf(dot.getTranslationX());
        }

        public void set(Dot dot, Float value) {
            dot.setTranslationX(value.floatValue());
        }
    };
    private static final long DURATION_ALPHA = 167;
    private static final long DURATION_DIAMETER = 417;
    private static final long DURATION_TRANSLATION_X = 417;
    final int mArrowDiameter;
    final int mArrowRadius;
    final Rect mArrowRect;
    final float mArrowToBgRatio;
    final Paint mBgPaint;
    final int mDotDiameter;
    final int mDotRadius;
    final Paint mFgPaint;
    private final AnimatorSet mAnimator;
    private final int mArrowGap;
    private final int mDotGap;
    private final AnimatorSet mHideAnimator;
    private final int mShadowRadius;
    private final AnimatorSet mShowAnimator;
    Bitmap mArrow;
    Paint mArrowPaint;
    int mDotCenterY;
    @ColorInt
    int mDotFgSelectColor;
    boolean mIsLtr;
    private int mCurrentPage;
    private int[] mDotSelectedNextX;
    private int[] mDotSelectedPrevX;
    private int[] mDotSelectedX;
    private Dot[] mDots;
    private int mPageCount;
    private int mPreviousPage;

    public PagingIndicator(Context context) {
        this(context, null, 0);
    }

    public PagingIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagingIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAnimator = new AnimatorSet();
        Resources res = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, C0364R.styleable.PagingIndicator, defStyle, 0);
        this.mDotRadius = getDimensionFromTypedArray(typedArray, C0364R.styleable.PagingIndicator_lbDotRadius, C0364R.dimen.lb_page_indicator_dot_radius);
        this.mDotDiameter = this.mDotRadius * 2;
        this.mArrowRadius = getDimensionFromTypedArray(typedArray, C0364R.styleable.PagingIndicator_arrowRadius, C0364R.dimen.lb_page_indicator_arrow_radius);
        this.mArrowDiameter = this.mArrowRadius * 2;
        this.mDotGap = getDimensionFromTypedArray(typedArray, C0364R.styleable.PagingIndicator_dotToDotGap, C0364R.dimen.lb_page_indicator_dot_gap);
        this.mArrowGap = getDimensionFromTypedArray(typedArray, C0364R.styleable.PagingIndicator_dotToArrowGap, C0364R.dimen.lb_page_indicator_arrow_gap);
        int dotBgColor = getColorFromTypedArray(typedArray, C0364R.styleable.PagingIndicator_dotBgColor, C0364R.color.lb_page_indicator_dot);
        this.mBgPaint = new Paint(1);
        this.mBgPaint.setColor(dotBgColor);
        this.mDotFgSelectColor = getColorFromTypedArray(typedArray, C0364R.styleable.PagingIndicator_arrowBgColor, C0364R.color.lb_page_indicator_arrow_background);
        if (this.mArrowPaint == null && typedArray.hasValue(C0364R.styleable.PagingIndicator_arrowColor)) {
            setArrowColor(typedArray.getColor(C0364R.styleable.PagingIndicator_arrowColor, 0));
        }
        typedArray.recycle();
        this.mIsLtr = res.getConfiguration().getLayoutDirection() == 0;
        int shadowColor = res.getColor(C0364R.color.lb_page_indicator_arrow_shadow);
        this.mShadowRadius = res.getDimensionPixelSize(C0364R.dimen.lb_page_indicator_arrow_shadow_radius);
        this.mFgPaint = new Paint(1);
        int shadowOffset = res.getDimensionPixelSize(C0364R.dimen.lb_page_indicator_arrow_shadow_offset);
        this.mFgPaint.setShadowLayer((float) this.mShadowRadius, (float) shadowOffset, (float) shadowOffset, shadowColor);
        this.mArrow = loadArrow();
        this.mArrowRect = new Rect(0, 0, this.mArrow.getWidth(), this.mArrow.getHeight());
        this.mArrowToBgRatio = ((float) this.mArrow.getWidth()) / ((float) this.mArrowDiameter);
        this.mShowAnimator = new AnimatorSet();
        this.mShowAnimator.playTogether(createDotAlphaAnimator(0.0f, 1.0f), createDotDiameterAnimator((float) (this.mDotRadius * 2), (float) (this.mArrowRadius * 2)), createDotTranslationXAnimator());
        this.mHideAnimator = new AnimatorSet();
        this.mHideAnimator.playTogether(createDotAlphaAnimator(1.0f, 0.0f), createDotDiameterAnimator((float) (this.mArrowRadius * 2), (float) (this.mDotRadius * 2)), createDotTranslationXAnimator());
        this.mAnimator.playTogether(this.mShowAnimator, this.mHideAnimator);
        setLayerType(1, null);
    }

    private int getDimensionFromTypedArray(TypedArray typedArray, int attr, int defaultId) {
        return typedArray.getDimensionPixelOffset(attr, getResources().getDimensionPixelOffset(defaultId));
    }

    private int getColorFromTypedArray(TypedArray typedArray, int attr, int defaultId) {
        return typedArray.getColor(attr, getResources().getColor(defaultId));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, boolean):android.graphics.Bitmap}
     arg types: [android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, int]
     candidates:
      ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int[], int, int, int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, boolean):android.graphics.Bitmap} */
    private Bitmap loadArrow() {
        Bitmap arrow = BitmapFactory.decodeResource(getResources(), C0364R.C0365drawable.lb_ic_nav_arrow);
        if (this.mIsLtr) {
            return arrow;
        }
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(arrow, 0, 0, arrow.getWidth(), arrow.getHeight(), matrix, false);
    }

    public void setArrowColor(@ColorInt int color) {
        if (this.mArrowPaint == null) {
            this.mArrowPaint = new Paint();
        }
        this.mArrowPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public void setDotBackgroundColor(@ColorInt int color) {
        this.mBgPaint.setColor(color);
    }

    public void setArrowBackgroundColor(@ColorInt int color) {
        this.mDotFgSelectColor = color;
    }

    private Animator createDotAlphaAnimator(float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat((Object) null, DOT_ALPHA, from, to);
        animator.setDuration((long) DURATION_ALPHA);
        animator.setInterpolator(DECELERATE_INTERPOLATOR);
        return animator;
    }

    private Animator createDotDiameterAnimator(float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat((Object) null, DOT_DIAMETER, from, to);
        animator.setDuration(417L);
        animator.setInterpolator(DECELERATE_INTERPOLATOR);
        return animator;
    }

    private Animator createDotTranslationXAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat((Object) null, DOT_TRANSLATION_X, (float) ((-this.mArrowGap) + this.mDotGap), 0.0f);
        animator.setDuration(417L);
        animator.setInterpolator(DECELERATE_INTERPOLATOR);
        return animator;
    }

    public void onPageSelected(int pageIndex, boolean withAnimation) {
        if (this.mCurrentPage != pageIndex) {
            if (this.mAnimator.isStarted()) {
                this.mAnimator.end();
            }
            this.mPreviousPage = this.mCurrentPage;
            if (withAnimation) {
                this.mHideAnimator.setTarget(this.mDots[this.mPreviousPage]);
                this.mShowAnimator.setTarget(this.mDots[pageIndex]);
                this.mAnimator.start();
            }
            setSelectedPage(pageIndex);
        }
    }

    private void calculateDotPositions() {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int requiredWidth = getRequiredWidth();
        int mid = (left + right) / 2;
        int i = this.mPageCount;
        this.mDotSelectedX = new int[i];
        this.mDotSelectedPrevX = new int[i];
        this.mDotSelectedNextX = new int[i];
        if (this.mIsLtr) {
            int startLeft = mid - (requiredWidth / 2);
            int[] iArr = this.mDotSelectedX;
            int i2 = this.mDotRadius;
            int i3 = this.mDotGap;
            int i4 = this.mArrowGap;
            iArr[0] = ((startLeft + i2) - i3) + i4;
            this.mDotSelectedPrevX[0] = startLeft + i2;
            this.mDotSelectedNextX[0] = ((i2 + startLeft) - (i3 * 2)) + (i4 * 2);
            for (int i5 = 1; i5 < this.mPageCount; i5++) {
                int[] iArr2 = this.mDotSelectedX;
                int[] iArr3 = this.mDotSelectedPrevX;
                int i6 = iArr3[i5 - 1];
                int i7 = this.mArrowGap;
                iArr2[i5] = i6 + i7;
                iArr3[i5] = iArr3[i5 - 1] + this.mDotGap;
                this.mDotSelectedNextX[i5] = iArr2[i5 - 1] + i7;
            }
        } else {
            int startRight = (requiredWidth / 2) + mid;
            int[] iArr4 = this.mDotSelectedX;
            int i8 = this.mDotRadius;
            int i9 = this.mDotGap;
            int i10 = this.mArrowGap;
            iArr4[0] = ((startRight - i8) + i9) - i10;
            this.mDotSelectedPrevX[0] = startRight - i8;
            this.mDotSelectedNextX[0] = ((startRight - i8) + (i9 * 2)) - (i10 * 2);
            for (int i11 = 1; i11 < this.mPageCount; i11++) {
                int[] iArr5 = this.mDotSelectedX;
                int[] iArr6 = this.mDotSelectedPrevX;
                int i12 = iArr6[i11 - 1];
                int i13 = this.mArrowGap;
                iArr5[i11] = i12 - i13;
                iArr6[i11] = iArr6[i11 - 1] - this.mDotGap;
                this.mDotSelectedNextX[i11] = iArr5[i11 - 1] - i13;
            }
        }
        this.mDotCenterY = this.mArrowRadius + top;
        adjustDotPosition();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getPageCount() {
        return this.mPageCount;
    }

    public void setPageCount(int pages) {
        if (pages > 0) {
            this.mPageCount = pages;
            this.mDots = new Dot[this.mPageCount];
            for (int i = 0; i < this.mPageCount; i++) {
                this.mDots[i] = new Dot();
            }
            calculateDotPositions();
            setSelectedPage(0);
            return;
        }
        throw new IllegalArgumentException("The page count should be a positive integer");
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int[] getDotSelectedX() {
        return this.mDotSelectedX;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int[] getDotSelectedLeftX() {
        return this.mDotSelectedPrevX;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int[] getDotSelectedRightX() {
        return this.mDotSelectedNextX;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int width;
        int desiredHeight = getDesiredHeight();
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode == Integer.MIN_VALUE) {
            height = Math.min(desiredHeight, View.MeasureSpec.getSize(heightMeasureSpec));
        } else if (mode != 1073741824) {
            height = desiredHeight;
        } else {
            height = View.MeasureSpec.getSize(heightMeasureSpec);
        }
        int desiredWidth = getDesiredWidth();
        int mode2 = View.MeasureSpec.getMode(widthMeasureSpec);
        if (mode2 == Integer.MIN_VALUE) {
            width = Math.min(desiredWidth, View.MeasureSpec.getSize(widthMeasureSpec));
        } else if (mode2 != 1073741824) {
            width = desiredWidth;
        } else {
            width = View.MeasureSpec.getSize(widthMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        setMeasuredDimension(width, height);
        calculateDotPositions();
    }

    private int getDesiredHeight() {
        return getPaddingTop() + this.mArrowDiameter + getPaddingBottom() + this.mShadowRadius;
    }

    private int getRequiredWidth() {
        return (this.mDotRadius * 2) + (this.mArrowGap * 2) + ((this.mPageCount - 3) * this.mDotGap);
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + getRequiredWidth() + getPaddingRight();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < this.mPageCount; i++) {
            this.mDots[i].draw(canvas);
        }
    }

    private void setSelectedPage(int now) {
        if (now != this.mCurrentPage) {
            this.mCurrentPage = now;
            adjustDotPosition();
        }
    }

    private void adjustDotPosition() {
        int i;
        float f;
        int i2 = 0;
        while (true) {
            i = this.mCurrentPage;
            f = -1.0f;
            if (i2 >= i) {
                break;
            }
            this.mDots[i2].deselect();
            Dot dot = this.mDots[i2];
            if (i2 != this.mPreviousPage) {
                f = 1.0f;
            }
            dot.mDirection = f;
            this.mDots[i2].mCenterX = (float) this.mDotSelectedPrevX[i2];
            i2++;
        }
        this.mDots[i].select();
        Dot[] dotArr = this.mDots;
        int i3 = this.mCurrentPage;
        Dot dot2 = dotArr[i3];
        if (this.mPreviousPage >= i3) {
            f = 1.0f;
        }
        dot2.mDirection = f;
        Dot[] dotArr2 = this.mDots;
        int i4 = this.mCurrentPage;
        dotArr2[i4].mCenterX = (float) this.mDotSelectedX[i4];
        while (true) {
            i4++;
            if (i4 < this.mPageCount) {
                this.mDots[i4].deselect();
                Dot[] dotArr3 = this.mDots;
                dotArr3[i4].mDirection = 1.0f;
                dotArr3[i4].mCenterX = (float) this.mDotSelectedNextX[i4];
            } else {
                return;
            }
        }
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        boolean isLtr = layoutDirection == 0;
        if (this.mIsLtr != isLtr) {
            this.mIsLtr = isLtr;
            this.mArrow = loadArrow();
            Dot[] dotArr = this.mDots;
            if (dotArr != null) {
                for (Dot dot : dotArr) {
                    dot.onRtlPropertiesChanged();
                }
            }
            calculateDotPositions();
            invalidate();
        }
    }

    public class Dot {
        static final float LEFT = -1.0f;
        static final float LTR = 1.0f;
        static final float RIGHT = 1.0f;
        static final float RTL = -1.0f;
        float mAlpha;
        float mArrowImageRadius;
        float mCenterX;
        float mDiameter;
        float mDirection = 1.0f;
        @ColorInt
        int mFgColor;
        float mLayoutDirection;
        float mRadius;
        float mTranslationX;

        public Dot() {
            float f = 1.0f;
            this.mLayoutDirection = !PagingIndicator.this.mIsLtr ? -1.0f : f;
        }

        /* access modifiers changed from: package-private */
        public void select() {
            this.mTranslationX = 0.0f;
            this.mCenterX = 0.0f;
            this.mDiameter = (float) PagingIndicator.this.mArrowDiameter;
            this.mRadius = (float) PagingIndicator.this.mArrowRadius;
            this.mArrowImageRadius = this.mRadius * PagingIndicator.this.mArrowToBgRatio;
            this.mAlpha = 1.0f;
            adjustAlpha();
        }

        /* access modifiers changed from: package-private */
        public void deselect() {
            this.mTranslationX = 0.0f;
            this.mCenterX = 0.0f;
            this.mDiameter = (float) PagingIndicator.this.mDotDiameter;
            this.mRadius = (float) PagingIndicator.this.mDotRadius;
            this.mArrowImageRadius = this.mRadius * PagingIndicator.this.mArrowToBgRatio;
            this.mAlpha = 0.0f;
            adjustAlpha();
        }

        public void adjustAlpha() {
            this.mFgColor = Color.argb(Math.round(this.mAlpha * 255.0f), Color.red(PagingIndicator.this.mDotFgSelectColor), Color.green(PagingIndicator.this.mDotFgSelectColor), Color.blue(PagingIndicator.this.mDotFgSelectColor));
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public void setAlpha(float alpha) {
            this.mAlpha = alpha;
            adjustAlpha();
            PagingIndicator.this.invalidate();
        }

        public float getTranslationX() {
            return this.mTranslationX;
        }

        public void setTranslationX(float translationX) {
            this.mTranslationX = this.mDirection * translationX * this.mLayoutDirection;
            PagingIndicator.this.invalidate();
        }

        public float getDiameter() {
            return this.mDiameter;
        }

        public void setDiameter(float diameter) {
            this.mDiameter = diameter;
            this.mRadius = diameter / 2.0f;
            this.mArrowImageRadius = (diameter / 2.0f) * PagingIndicator.this.mArrowToBgRatio;
            PagingIndicator.this.invalidate();
        }

        /* access modifiers changed from: package-private */
        public void draw(Canvas canvas) {
            float centerX = this.mCenterX + this.mTranslationX;
            canvas.drawCircle(centerX, (float) PagingIndicator.this.mDotCenterY, this.mRadius, PagingIndicator.this.mBgPaint);
            if (this.mAlpha > 0.0f) {
                PagingIndicator.this.mFgPaint.setColor(this.mFgColor);
                canvas.drawCircle(centerX, (float) PagingIndicator.this.mDotCenterY, this.mRadius, PagingIndicator.this.mFgPaint);
                Bitmap bitmap = PagingIndicator.this.mArrow;
                Rect rect = PagingIndicator.this.mArrowRect;
                float f = this.mArrowImageRadius;
                canvas.drawBitmap(bitmap, rect, new Rect((int) (centerX - this.mArrowImageRadius), (int) (((float) PagingIndicator.this.mDotCenterY) - f), (int) (f + centerX), (int) (((float) PagingIndicator.this.mDotCenterY) + this.mArrowImageRadius)), PagingIndicator.this.mArrowPaint);
            }
        }

        /* access modifiers changed from: package-private */
        public void onRtlPropertiesChanged() {
            this.mLayoutDirection = PagingIndicator.this.mIsLtr ? 1.0f : -1.0f;
        }
    }
}
