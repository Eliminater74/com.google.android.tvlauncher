package androidx.leanback.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.p001v4.content.ContextCompat;
import android.support.p001v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.leanback.C0364R;
import androidx.leanback.widget.BackgroundHelper;
import java.lang.ref.WeakReference;

public final class BackgroundManager {
    private static final int CHANGE_BG_DELAY_MS = 500;
    static final boolean DEBUG = false;
    private static final int FADE_DURATION = 500;
    private static final String FRAGMENT_TAG = BackgroundManager.class.getCanonicalName();
    static final int FULL_ALPHA = 255;
    static final String TAG = "BackgroundManager";
    private final Interpolator mAccelerateInterpolator;
    private final Animator.AnimatorListener mAnimationListener = new Animator.AnimatorListener() {
        final Runnable mRunnable = new Runnable() {
            public void run() {
                BackgroundManager.this.postChangeRunnable();
            }
        };

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            if (BackgroundManager.this.mLayerDrawable != null) {
                BackgroundManager.this.mLayerDrawable.clearDrawable(C0364R.C0366id.background_imageout, BackgroundManager.this.mContext);
            }
            BackgroundManager.this.mHandler.post(this.mRunnable);
        }

        public void onAnimationCancel(Animator animation) {
        }
    };
    private final ValueAnimator.AnimatorUpdateListener mAnimationUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            int fadeInAlpha = ((Integer) animation.getAnimatedValue()).intValue();
            if (BackgroundManager.this.mImageInWrapperIndex != -1) {
                BackgroundManager.this.mLayerDrawable.setWrapperAlpha(BackgroundManager.this.mImageInWrapperIndex, fadeInAlpha);
            }
        }
    };
    final ValueAnimator mAnimator;
    boolean mAttached;
    private boolean mAutoReleaseOnStop = true;
    int mBackgroundColor;
    Drawable mBackgroundDrawable;
    private View mBgView;
    ChangeBackgroundRunnable mChangeRunnable;
    private boolean mChangeRunnablePending;
    Activity mContext;
    private final Interpolator mDecelerateInterpolator;
    private BackgroundFragment mFragmentState;
    Handler mHandler;
    private int mHeightPx;
    int mImageInWrapperIndex;
    int mImageOutWrapperIndex;
    private long mLastSetTime;
    TranslucentLayerDrawable mLayerDrawable;
    private BackgroundContinuityService mService;
    private int mThemeDrawableResourceId;
    private int mWidthPx;

    static class BitmapDrawable extends Drawable {
        boolean mMutated;
        ConstantState mState;

        static final class ConstantState extends Drawable.ConstantState {
            final Bitmap mBitmap;
            final Matrix mMatrix;
            final Paint mPaint = new Paint();

            ConstantState(Bitmap bitmap, Matrix matrix) {
                this.mBitmap = bitmap;
                this.mMatrix = matrix != null ? matrix : new Matrix();
                this.mPaint.setFilterBitmap(true);
            }

            ConstantState(ConstantState copyFrom) {
                Matrix matrix;
                this.mBitmap = copyFrom.mBitmap;
                if (copyFrom.mMatrix == null) {
                    matrix = new Matrix();
                }
                this.mMatrix = matrix;
                if (copyFrom.mPaint.getAlpha() != 255) {
                    this.mPaint.setAlpha(copyFrom.mPaint.getAlpha());
                }
                if (copyFrom.mPaint.getColorFilter() != null) {
                    this.mPaint.setColorFilter(copyFrom.mPaint.getColorFilter());
                }
                this.mPaint.setFilterBitmap(true);
            }

            public Drawable newDrawable() {
                return new BitmapDrawable(this);
            }

            public int getChangingConfigurations() {
                return 0;
            }
        }

        BitmapDrawable(Resources resources, Bitmap bitmap) {
            this(resources, bitmap, null);
        }

        BitmapDrawable(Resources resources, Bitmap bitmap, Matrix matrix) {
            this.mState = new ConstantState(bitmap, matrix);
        }

        BitmapDrawable(ConstantState state) {
            this.mState = state;
        }

        /* access modifiers changed from: package-private */
        public Bitmap getBitmap() {
            return this.mState.mBitmap;
        }

        public void draw(Canvas canvas) {
            if (this.mState.mBitmap != null) {
                if (this.mState.mPaint.getAlpha() >= 255 || this.mState.mPaint.getColorFilter() == null) {
                    canvas.drawBitmap(this.mState.mBitmap, this.mState.mMatrix, this.mState.mPaint);
                    return;
                }
                throw new IllegalStateException("Can't draw with translucent alpha and color filter");
            }
        }

        public int getOpacity() {
            return -3;
        }

        public void setAlpha(int alpha) {
            mutate();
            if (this.mState.mPaint.getAlpha() != alpha) {
                this.mState.mPaint.setAlpha(alpha);
                invalidateSelf();
            }
        }

        public void setColorFilter(ColorFilter cf) {
            mutate();
            this.mState.mPaint.setColorFilter(cf);
            invalidateSelf();
        }

        public ColorFilter getColorFilter() {
            return this.mState.mPaint.getColorFilter();
        }

        public ConstantState getConstantState() {
            return this.mState;
        }

        @NonNull
        public Drawable mutate() {
            if (!this.mMutated) {
                this.mMutated = true;
                this.mState = new ConstantState(this.mState);
            }
            return this;
        }
    }

    static final class DrawableWrapper {
        int mAlpha = 255;
        final Drawable mDrawable;

        public DrawableWrapper(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public DrawableWrapper(DrawableWrapper wrapper, Drawable drawable) {
            this.mDrawable = drawable;
            this.mAlpha = wrapper.mAlpha;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public void setColor(int color) {
            ((ColorDrawable) this.mDrawable).setColor(color);
        }
    }

    static final class TranslucentLayerDrawable extends LayerDrawable {
        int mAlpha = 255;
        WeakReference<BackgroundManager> mManagerWeakReference;
        boolean mSuspendInvalidation;
        DrawableWrapper[] mWrapper;

        TranslucentLayerDrawable(BackgroundManager manager, Drawable[] drawables) {
            super(drawables);
            this.mManagerWeakReference = new WeakReference<>(manager);
            int count = drawables.length;
            this.mWrapper = new DrawableWrapper[count];
            for (int i = 0; i < count; i++) {
                this.mWrapper[i] = new DrawableWrapper(drawables[i]);
            }
        }

        public void setAlpha(int alpha) {
            if (this.mAlpha != alpha) {
                this.mAlpha = alpha;
                invalidateSelf();
                BackgroundManager manager = this.mManagerWeakReference.get();
                if (manager != null) {
                    manager.postChangeRunnable();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setWrapperAlpha(int wrapperIndex, int alpha) {
            DrawableWrapper[] drawableWrapperArr = this.mWrapper;
            if (drawableWrapperArr[wrapperIndex] != null) {
                drawableWrapperArr[wrapperIndex].mAlpha = alpha;
                invalidateSelf();
            }
        }

        public int getAlpha() {
            return this.mAlpha;
        }

        public Drawable mutate() {
            Drawable drawable = super.mutate();
            int count = getNumberOfLayers();
            for (int i = 0; i < count; i++) {
                DrawableWrapper[] drawableWrapperArr = this.mWrapper;
                if (drawableWrapperArr[i] != null) {
                    drawableWrapperArr[i] = new DrawableWrapper(drawableWrapperArr[i], getDrawable(i));
                }
            }
            return drawable;
        }

        public int getOpacity() {
            return -3;
        }

        public boolean setDrawableByLayerId(int id, Drawable drawable) {
            return updateDrawable(id, drawable) != null;
        }

        public DrawableWrapper updateDrawable(int id, Drawable drawable) {
            super.setDrawableByLayerId(id, drawable);
            for (int i = 0; i < getNumberOfLayers(); i++) {
                if (getId(i) == id) {
                    this.mWrapper[i] = new DrawableWrapper(drawable);
                    invalidateSelf();
                    return this.mWrapper[i];
                }
            }
            return null;
        }

        public void clearDrawable(int id, Context context) {
            for (int i = 0; i < getNumberOfLayers(); i++) {
                if (getId(i) == id) {
                    this.mWrapper[i] = null;
                    if (!(getDrawable(i) instanceof EmptyDrawable)) {
                        super.setDrawableByLayerId(id, BackgroundManager.createEmptyDrawable(context));
                        return;
                    }
                    return;
                }
            }
        }

        public int findWrapperIndexById(int id) {
            for (int i = 0; i < getNumberOfLayers(); i++) {
                if (getId(i) == id) {
                    return i;
                }
            }
            return -1;
        }

        public void invalidateDrawable(Drawable who) {
            if (!this.mSuspendInvalidation) {
                super.invalidateDrawable(who);
            }
        }

        /* JADX INFO: finally extract failed */
        public void draw(Canvas canvas) {
            int i = 0;
            while (true) {
                DrawableWrapper[] drawableWrapperArr = this.mWrapper;
                if (i < drawableWrapperArr.length) {
                    if (drawableWrapperArr[i] != null) {
                        Drawable drawable = drawableWrapperArr[i].getDrawable();
                        Drawable d = drawable;
                        if (drawable != null) {
                            int alpha = Build.VERSION.SDK_INT >= 19 ? DrawableCompat.getAlpha(d) : 255;
                            int savedAlpha = alpha;
                            int multiple = 0;
                            int i2 = this.mAlpha;
                            if (i2 < 255) {
                                alpha *= i2;
                                multiple = 0 + 1;
                            }
                            if (this.mWrapper[i].mAlpha < 255) {
                                alpha *= this.mWrapper[i].mAlpha;
                                multiple++;
                            }
                            if (multiple == 0) {
                                d.draw(canvas);
                            } else {
                                if (multiple == 1) {
                                    alpha /= 255;
                                } else if (multiple == 2) {
                                    alpha /= OggPageHeader.MAX_PAGE_PAYLOAD;
                                }
                                try {
                                    this.mSuspendInvalidation = true;
                                    d.setAlpha(alpha);
                                    d.draw(canvas);
                                    d.setAlpha(savedAlpha);
                                    this.mSuspendInvalidation = false;
                                } catch (Throwable th) {
                                    this.mSuspendInvalidation = false;
                                    throw th;
                                }
                            }
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public TranslucentLayerDrawable createTranslucentLayerDrawable(LayerDrawable layerDrawable) {
        int numChildren = layerDrawable.getNumberOfLayers();
        Drawable[] drawables = new Drawable[numChildren];
        for (int i = 0; i < numChildren; i++) {
            drawables[i] = layerDrawable.getDrawable(i);
        }
        TranslucentLayerDrawable result = new TranslucentLayerDrawable(this, drawables);
        for (int i2 = 0; i2 < numChildren; i2++) {
            result.setId(i2, layerDrawable.getId(i2));
        }
        return result;
    }

    private static class BackgroundContinuityService {
        private static final boolean DEBUG = false;
        private static final String TAG = "BackgroundContinuity";
        private static BackgroundContinuityService sService = new BackgroundContinuityService();
        private int mColor;
        private int mCount;
        private Drawable mDrawable;
        private int mLastThemeDrawableId;
        private WeakReference<Drawable.ConstantState> mLastThemeDrawableState;

        private BackgroundContinuityService() {
            reset();
        }

        private void reset() {
            this.mColor = 0;
            this.mDrawable = null;
        }

        public static BackgroundContinuityService getInstance() {
            BackgroundContinuityService backgroundContinuityService = sService;
            backgroundContinuityService.mCount++;
            return backgroundContinuityService;
        }

        public void unref() {
            int i = this.mCount;
            if (i > 0) {
                int i2 = i - 1;
                this.mCount = i2;
                if (i2 == 0) {
                    reset();
                    return;
                }
                return;
            }
            throw new IllegalStateException("Can't unref, count " + this.mCount);
        }

        public int getColor() {
            return this.mColor;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public void setColor(int color) {
            this.mColor = color;
            this.mDrawable = null;
        }

        public void setDrawable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public Drawable getThemeDrawable(Context context, int themeDrawableId) {
            Drawable.ConstantState drawableState;
            Drawable drawable = null;
            WeakReference<Drawable.ConstantState> weakReference = this.mLastThemeDrawableState;
            if (!(weakReference == null || this.mLastThemeDrawableId != themeDrawableId || (drawableState = weakReference.get()) == null)) {
                drawable = drawableState.newDrawable();
            }
            if (drawable != null) {
                return drawable;
            }
            Drawable drawable2 = ContextCompat.getDrawable(context, themeDrawableId);
            this.mLastThemeDrawableState = new WeakReference<>(drawable2.getConstantState());
            this.mLastThemeDrawableId = themeDrawableId;
            return drawable2;
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable getDefaultDrawable() {
        int i = this.mBackgroundColor;
        if (i != 0) {
            return new ColorDrawable(i);
        }
        return getThemeDrawable();
    }

    private Drawable getThemeDrawable() {
        Drawable drawable = null;
        int i = this.mThemeDrawableResourceId;
        if (i != -1) {
            drawable = this.mService.getThemeDrawable(this.mContext, i);
        }
        if (drawable == null) {
            return createEmptyDrawable(this.mContext);
        }
        return drawable;
    }

    public static BackgroundManager getInstance(Activity activity) {
        BackgroundManager manager;
        BackgroundFragment fragment = (BackgroundFragment) activity.getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null || (manager = fragment.getBackgroundManager()) == null) {
            return new BackgroundManager(activity);
        }
        return manager;
    }

    private BackgroundManager(Activity activity) {
        this.mContext = activity;
        this.mService = BackgroundContinuityService.getInstance();
        this.mHeightPx = this.mContext.getResources().getDisplayMetrics().heightPixels;
        this.mWidthPx = this.mContext.getResources().getDisplayMetrics().widthPixels;
        this.mHandler = new Handler();
        Interpolator defaultInterpolator = new FastOutLinearInInterpolator();
        this.mAccelerateInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17432581);
        this.mDecelerateInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17432582);
        this.mAnimator = ValueAnimator.ofInt(0, 255);
        this.mAnimator.addListener(this.mAnimationListener);
        this.mAnimator.addUpdateListener(this.mAnimationUpdateListener);
        this.mAnimator.setInterpolator(defaultInterpolator);
        TypedArray ta = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
        this.mThemeDrawableResourceId = ta.getResourceId(0, -1);
        int i = this.mThemeDrawableResourceId;
        ta.recycle();
        createFragment(activity);
    }

    private void createFragment(Activity activity) {
        BackgroundFragment fragment = (BackgroundFragment) activity.getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new BackgroundFragment();
            activity.getFragmentManager().beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        } else if (fragment.getBackgroundManager() != null) {
            throw new IllegalStateException("Created duplicated BackgroundManager for same activity, please use getInstance() instead");
        }
        fragment.setBackgroundManager(this);
        this.mFragmentState = fragment;
    }

    /* access modifiers changed from: package-private */
    public DrawableWrapper getImageInWrapper() {
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable == null) {
            return null;
        }
        return translucentLayerDrawable.mWrapper[this.mImageInWrapperIndex];
    }

    /* access modifiers changed from: package-private */
    public DrawableWrapper getImageOutWrapper() {
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable == null) {
            return null;
        }
        return translucentLayerDrawable.mWrapper[this.mImageOutWrapperIndex];
    }

    /* access modifiers changed from: package-private */
    public void onActivityStart() {
        updateImmediate();
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        if (isAutoReleaseOnStop()) {
            release();
        }
    }

    /* access modifiers changed from: package-private */
    public void onResume() {
        postChangeRunnable();
    }

    private void syncWithService() {
        Drawable drawable;
        int color = this.mService.getColor();
        Drawable drawable2 = this.mService.getDrawable();
        this.mBackgroundColor = color;
        if (drawable2 == null) {
            drawable = null;
        } else {
            drawable = drawable2.getConstantState().newDrawable().mutate();
        }
        this.mBackgroundDrawable = drawable;
        updateImmediate();
    }

    public void attach(Window window) {
        attachToViewInternal(window.getDecorView());
    }

    public void setThemeDrawableResourceId(int resourceId) {
        this.mThemeDrawableResourceId = resourceId;
    }

    public void attachToView(View sceneRoot) {
        attachToViewInternal(sceneRoot);
        this.mContext.getWindow().getDecorView().setBackground(Build.VERSION.SDK_INT >= 26 ? null : new ColorDrawable(0));
    }

    /* access modifiers changed from: package-private */
    public void attachToViewInternal(View sceneRoot) {
        if (!this.mAttached) {
            this.mBgView = sceneRoot;
            this.mAttached = true;
            syncWithService();
            return;
        }
        throw new IllegalStateException("Already attached to " + this.mBgView);
    }

    public boolean isAttached() {
        return this.mAttached;
    }

    /* access modifiers changed from: package-private */
    public void detach() {
        release();
        this.mBgView = null;
        this.mAttached = false;
        BackgroundContinuityService backgroundContinuityService = this.mService;
        if (backgroundContinuityService != null) {
            backgroundContinuityService.unref();
            this.mService = null;
        }
    }

    public void release() {
        ChangeBackgroundRunnable changeBackgroundRunnable = this.mChangeRunnable;
        if (changeBackgroundRunnable != null) {
            this.mHandler.removeCallbacks(changeBackgroundRunnable);
            this.mChangeRunnable = null;
        }
        if (this.mAnimator.isStarted()) {
            this.mAnimator.cancel();
        }
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable != null) {
            translucentLayerDrawable.clearDrawable(C0364R.C0366id.background_imagein, this.mContext);
            this.mLayerDrawable.clearDrawable(C0364R.C0366id.background_imageout, this.mContext);
            this.mLayerDrawable = null;
        }
        this.mBackgroundDrawable = null;
    }

    @Deprecated
    public void setDimLayer(Drawable drawable) {
    }

    @Deprecated
    public Drawable getDimLayer() {
        return null;
    }

    @Deprecated
    public Drawable getDefaultDimLayer() {
        return ContextCompat.getDrawable(this.mContext, C0364R.color.lb_background_protection);
    }

    /* access modifiers changed from: package-private */
    public void postChangeRunnable() {
        if (this.mChangeRunnable != null && this.mChangeRunnablePending && !this.mAnimator.isStarted() && this.mFragmentState.isResumed() && this.mLayerDrawable.getAlpha() >= 255) {
            long delayMs = getRunnableDelay();
            this.mLastSetTime = System.currentTimeMillis();
            this.mHandler.postDelayed(this.mChangeRunnable, delayMs);
            this.mChangeRunnablePending = false;
        }
    }

    private void lazyInit() {
        if (this.mLayerDrawable == null) {
            this.mLayerDrawable = createTranslucentLayerDrawable((LayerDrawable) ContextCompat.getDrawable(this.mContext, C0364R.C0365drawable.lb_background).mutate());
            this.mImageInWrapperIndex = this.mLayerDrawable.findWrapperIndexById(C0364R.C0366id.background_imagein);
            this.mImageOutWrapperIndex = this.mLayerDrawable.findWrapperIndexById(C0364R.C0366id.background_imageout);
            BackgroundHelper.setBackgroundPreservingAlpha(this.mBgView, this.mLayerDrawable);
        }
    }

    private void updateImmediate() {
        if (this.mAttached) {
            lazyInit();
            if (this.mBackgroundDrawable == null) {
                this.mLayerDrawable.updateDrawable(C0364R.C0366id.background_imagein, getDefaultDrawable());
            } else {
                this.mLayerDrawable.updateDrawable(C0364R.C0366id.background_imagein, this.mBackgroundDrawable);
            }
            this.mLayerDrawable.clearDrawable(C0364R.C0366id.background_imageout, this.mContext);
        }
    }

    public void setColor(@ColorInt int color) {
        this.mService.setColor(color);
        this.mBackgroundColor = color;
        this.mBackgroundDrawable = null;
        if (this.mLayerDrawable != null) {
            setDrawableInternal(getDefaultDrawable());
        }
    }

    public void setDrawable(Drawable drawable) {
        this.mService.setDrawable(drawable);
        this.mBackgroundDrawable = drawable;
        if (this.mLayerDrawable != null) {
            if (drawable == null) {
                setDrawableInternal(getDefaultDrawable());
            } else {
                setDrawableInternal(drawable);
            }
        }
    }

    public void clearDrawable() {
        setDrawable(null);
    }

    private void setDrawableInternal(Drawable drawable) {
        if (this.mAttached) {
            ChangeBackgroundRunnable changeBackgroundRunnable = this.mChangeRunnable;
            if (changeBackgroundRunnable != null) {
                if (!sameDrawable(drawable, changeBackgroundRunnable.mDrawable)) {
                    this.mHandler.removeCallbacks(this.mChangeRunnable);
                    this.mChangeRunnable = null;
                } else {
                    return;
                }
            }
            this.mChangeRunnable = new ChangeBackgroundRunnable(drawable);
            this.mChangeRunnablePending = true;
            postChangeRunnable();
            return;
        }
        throw new IllegalStateException("Must attach before setting background drawable");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    private long getRunnableDelay() {
        return Math.max(0L, (this.mLastSetTime + 500) - System.currentTimeMillis());
    }

    public void setBitmap(Bitmap bitmap) {
        float scale;
        if (bitmap == null) {
            setDrawable(null);
        } else if (bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
            Matrix matrix = null;
            if (!(bitmap.getWidth() == this.mWidthPx && bitmap.getHeight() == this.mHeightPx)) {
                int dwidth = bitmap.getWidth();
                int dheight = bitmap.getHeight();
                int i = this.mHeightPx;
                int i2 = dwidth * i;
                int i3 = this.mWidthPx;
                if (i2 > i3 * dheight) {
                    scale = ((float) i) / ((float) dheight);
                } else {
                    scale = ((float) i3) / ((float) dwidth);
                }
                int dx = Math.max(0, (dwidth - Math.min((int) (((float) this.mWidthPx) / scale), dwidth)) / 2);
                matrix = new Matrix();
                matrix.setScale(scale, scale);
                matrix.preTranslate((float) (-dx), 0.0f);
            }
            setDrawable(new BitmapDrawable(this.mContext.getResources(), bitmap, matrix));
        }
    }

    public void setAutoReleaseOnStop(boolean autoReleaseOnStop) {
        this.mAutoReleaseOnStop = autoReleaseOnStop;
    }

    public boolean isAutoReleaseOnStop() {
        return this.mAutoReleaseOnStop;
    }

    @ColorInt
    public final int getColor() {
        return this.mBackgroundColor;
    }

    public Drawable getDrawable() {
        return this.mBackgroundDrawable;
    }

    /* access modifiers changed from: package-private */
    public boolean sameDrawable(Drawable first, Drawable second) {
        if (first == null || second == null) {
            return false;
        }
        if (first == second) {
            return true;
        }
        if ((first instanceof BitmapDrawable) && (second instanceof BitmapDrawable) && ((BitmapDrawable) first).getBitmap().sameAs(((BitmapDrawable) second).getBitmap())) {
            return true;
        }
        if (!(first instanceof ColorDrawable) || !(second instanceof ColorDrawable) || ((ColorDrawable) first).getColor() != ((ColorDrawable) second).getColor()) {
            return false;
        }
        return true;
    }

    final class ChangeBackgroundRunnable implements Runnable {
        final Drawable mDrawable;

        ChangeBackgroundRunnable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public void run() {
            runTask();
            BackgroundManager.this.mChangeRunnable = null;
        }

        private void runTask() {
            if (BackgroundManager.this.mLayerDrawable != null) {
                DrawableWrapper imageInWrapper = BackgroundManager.this.getImageInWrapper();
                if (imageInWrapper != null) {
                    if (!BackgroundManager.this.sameDrawable(this.mDrawable, imageInWrapper.getDrawable())) {
                        BackgroundManager.this.mLayerDrawable.clearDrawable(C0364R.C0366id.background_imagein, BackgroundManager.this.mContext);
                        BackgroundManager.this.mLayerDrawable.updateDrawable(C0364R.C0366id.background_imageout, imageInWrapper.getDrawable());
                    } else {
                        return;
                    }
                }
                applyBackgroundChanges();
            }
        }

        /* access modifiers changed from: package-private */
        public void applyBackgroundChanges() {
            if (BackgroundManager.this.mAttached) {
                if (BackgroundManager.this.getImageInWrapper() == null && this.mDrawable != null) {
                    DrawableWrapper imageInWrapper = BackgroundManager.this.mLayerDrawable.updateDrawable(C0364R.C0366id.background_imagein, this.mDrawable);
                    BackgroundManager.this.mLayerDrawable.setWrapperAlpha(BackgroundManager.this.mImageInWrapperIndex, 0);
                }
                BackgroundManager.this.mAnimator.setDuration(500L);
                BackgroundManager.this.mAnimator.start();
            }
        }
    }

    static class EmptyDrawable extends BitmapDrawable {
        EmptyDrawable(Resources res) {
            super(res, null);
        }
    }

    static Drawable createEmptyDrawable(Context context) {
        return new EmptyDrawable(context.getResources());
    }
}
