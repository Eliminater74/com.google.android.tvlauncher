package com.google.android.exoplayer2.p008ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout */
public final class AspectRatioFrameLayout extends FrameLayout {
    public static final int RESIZE_MODE_FILL = 3;
    public static final int RESIZE_MODE_FIT = 0;
    public static final int RESIZE_MODE_FIXED_HEIGHT = 2;
    public static final int RESIZE_MODE_FIXED_WIDTH = 1;
    public static final int RESIZE_MODE_ZOOM = 4;
    private static final float MAX_ASPECT_RATIO_DEFORMATION_FRACTION = 0.01f;
    private final AspectRatioUpdateDispatcher aspectRatioUpdateDispatcher;
    /* access modifiers changed from: private */
    public AspectRatioListener aspectRatioListener;
    private int resizeMode;
    private float videoAspectRatio;

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.resizeMode = 0;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, C0931R.styleable.AspectRatioFrameLayout, 0, 0);
            try {
                this.resizeMode = a.getInt(C0931R.styleable.AspectRatioFrameLayout_resize_mode, 0);
            } finally {
                a.recycle();
            }
        }
        this.aspectRatioUpdateDispatcher = new AspectRatioUpdateDispatcher();
    }

    public void setAspectRatio(float widthHeightRatio) {
        if (this.videoAspectRatio != widthHeightRatio) {
            this.videoAspectRatio = widthHeightRatio;
            requestLayout();
        }
    }

    public void setAspectRatioListener(AspectRatioListener listener) {
        this.aspectRatioListener = listener;
    }

    public int getResizeMode() {
        return this.resizeMode;
    }

    public void setResizeMode(int resizeMode2) {
        if (this.resizeMode != resizeMode2) {
            this.resizeMode = resizeMode2;
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.videoAspectRatio > 0.0f) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            float viewAspectRatio = ((float) width) / ((float) height);
            float aspectDeformation = (this.videoAspectRatio / viewAspectRatio) - 1.0f;
            if (Math.abs(aspectDeformation) <= MAX_ASPECT_RATIO_DEFORMATION_FRACTION) {
                this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, viewAspectRatio, false);
                return;
            }
            int i = this.resizeMode;
            if (i != 0) {
                if (i == 1) {
                    height = (int) (((float) width) / this.videoAspectRatio);
                } else if (i == 2) {
                    width = (int) (((float) height) * this.videoAspectRatio);
                } else if (i == 4) {
                    if (aspectDeformation > 0.0f) {
                        width = (int) (((float) height) * this.videoAspectRatio);
                    } else {
                        height = (int) (((float) width) / this.videoAspectRatio);
                    }
                }
            } else if (aspectDeformation > 0.0f) {
                height = (int) (((float) width) / this.videoAspectRatio);
            } else {
                width = (int) (((float) height) * this.videoAspectRatio);
            }
            this.aspectRatioUpdateDispatcher.scheduleUpdate(this.videoAspectRatio, viewAspectRatio, true);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
        }
    }

    /* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout$AspectRatioListener */
    public interface AspectRatioListener {
        void onAspectRatioUpdated(float f, float f2, boolean z);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout$ResizeMode */
    public @interface ResizeMode {
    }

    /* renamed from: com.google.android.exoplayer2.ui.AspectRatioFrameLayout$AspectRatioUpdateDispatcher */
    private final class AspectRatioUpdateDispatcher implements Runnable {
        private boolean aspectRatioMismatch;
        private boolean isScheduled;
        private float naturalAspectRatio;
        private float targetAspectRatio;

        private AspectRatioUpdateDispatcher() {
        }

        public void scheduleUpdate(float targetAspectRatio2, float naturalAspectRatio2, boolean aspectRatioMismatch2) {
            this.targetAspectRatio = targetAspectRatio2;
            this.naturalAspectRatio = naturalAspectRatio2;
            this.aspectRatioMismatch = aspectRatioMismatch2;
            if (!this.isScheduled) {
                this.isScheduled = true;
                AspectRatioFrameLayout.this.post(this);
            }
        }

        public void run() {
            this.isScheduled = false;
            if (AspectRatioFrameLayout.this.aspectRatioListener != null) {
                AspectRatioFrameLayout.this.aspectRatioListener.onAspectRatioUpdated(this.targetAspectRatio, this.naturalAspectRatio, this.aspectRatioMismatch);
            }
        }
    }
}
