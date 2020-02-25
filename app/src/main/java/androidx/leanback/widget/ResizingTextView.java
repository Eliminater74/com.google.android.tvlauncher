package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.p001v4.widget.TextViewCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.TextView;

import androidx.leanback.C0364R;

class ResizingTextView extends TextView {
    public static final int TRIGGER_MAX_LINES = 1;
    private float mDefaultLineSpacingExtra;
    private int mDefaultPaddingBottom;
    private int mDefaultPaddingTop;
    private int mDefaultTextSize;
    private boolean mDefaultsInitialized;
    private boolean mIsResized;
    private boolean mMaintainLineSpacing;
    private int mResizedPaddingAdjustmentBottom;
    private int mResizedPaddingAdjustmentTop;
    private int mResizedTextSize;
    private int mTriggerConditions;

    public ResizingTextView(Context ctx, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(ctx, attrs, defStyleAttr);
        this.mIsResized = false;
        this.mDefaultsInitialized = false;
        TypedArray a = ctx.obtainStyledAttributes(attrs, C0364R.styleable.lbResizingTextView, defStyleAttr, defStyleRes);
        try {
            this.mTriggerConditions = a.getInt(C0364R.styleable.lbResizingTextView_resizeTrigger, 1);
            this.mResizedTextSize = a.getDimensionPixelSize(C0364R.styleable.lbResizingTextView_resizedTextSize, -1);
            this.mMaintainLineSpacing = a.getBoolean(C0364R.styleable.lbResizingTextView_maintainLineSpacing, false);
            this.mResizedPaddingAdjustmentTop = a.getDimensionPixelOffset(C0364R.styleable.lbResizingTextView_resizedPaddingAdjustmentTop, 0);
            this.mResizedPaddingAdjustmentBottom = a.getDimensionPixelOffset(C0364R.styleable.lbResizingTextView_resizedPaddingAdjustmentBottom, 0);
        } finally {
            a.recycle();
        }
    }

    public ResizingTextView(Context ctx, AttributeSet attrs, int defStyleAttr) {
        this(ctx, attrs, defStyleAttr, 0);
    }

    public ResizingTextView(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, 16842884);
    }

    public ResizingTextView(Context ctx) {
        this(ctx, null);
    }

    public int getTriggerConditions() {
        return this.mTriggerConditions;
    }

    public void setTriggerConditions(int conditions) {
        if (this.mTriggerConditions != conditions) {
            this.mTriggerConditions = conditions;
            requestLayout();
        }
    }

    public int getResizedTextSize() {
        return this.mResizedTextSize;
    }

    public void setResizedTextSize(int size) {
        if (this.mResizedTextSize != size) {
            this.mResizedTextSize = size;
            resizeParamsChanged();
        }
    }

    public boolean getMaintainLineSpacing() {
        return this.mMaintainLineSpacing;
    }

    public void setMaintainLineSpacing(boolean maintain) {
        if (this.mMaintainLineSpacing != maintain) {
            this.mMaintainLineSpacing = maintain;
            resizeParamsChanged();
        }
    }

    public int getResizedPaddingAdjustmentTop() {
        return this.mResizedPaddingAdjustmentTop;
    }

    public void setResizedPaddingAdjustmentTop(int adjustment) {
        if (this.mResizedPaddingAdjustmentTop != adjustment) {
            this.mResizedPaddingAdjustmentTop = adjustment;
            resizeParamsChanged();
        }
    }

    public int getResizedPaddingAdjustmentBottom() {
        return this.mResizedPaddingAdjustmentBottom;
    }

    public void setResizedPaddingAdjustmentBottom(int adjustment) {
        if (this.mResizedPaddingAdjustmentBottom != adjustment) {
            this.mResizedPaddingAdjustmentBottom = adjustment;
            resizeParamsChanged();
        }
    }

    private void resizeParamsChanged() {
        if (this.mIsResized) {
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        boolean z = true;
        if (!this.mDefaultsInitialized) {
            this.mDefaultTextSize = (int) getTextSize();
            this.mDefaultLineSpacingExtra = getLineSpacingExtra();
            this.mDefaultPaddingTop = getPaddingTop();
            this.mDefaultPaddingBottom = getPaddingBottom();
            this.mDefaultsInitialized = true;
        }
        setTextSize(0, (float) this.mDefaultTextSize);
        setLineSpacing(this.mDefaultLineSpacingExtra, getLineSpacingMultiplier());
        setPaddingTopAndBottom(this.mDefaultPaddingTop, this.mDefaultPaddingBottom);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        boolean resizeText = false;
        Layout layout = getLayout();
        if (layout != null && (this.mTriggerConditions & 1) > 0) {
            int lineCount = layout.getLineCount();
            int maxLines = getMaxLines();
            if (maxLines > 1) {
                if (lineCount != maxLines) {
                    z = false;
                }
                resizeText = z;
            }
        }
        int currentSizePx = (int) getTextSize();
        boolean remeasure = false;
        if (resizeText) {
            int i2 = this.mResizedTextSize;
            if (!(i2 == -1 || currentSizePx == i2)) {
                setTextSize(0, (float) i2);
                remeasure = true;
            }
            float targetLineSpacingExtra = (this.mDefaultLineSpacingExtra + ((float) this.mDefaultTextSize)) - ((float) this.mResizedTextSize);
            if (this.mMaintainLineSpacing && getLineSpacingExtra() != targetLineSpacingExtra) {
                setLineSpacing(targetLineSpacingExtra, getLineSpacingMultiplier());
                remeasure = true;
            }
            int paddingTop = this.mDefaultPaddingTop + this.mResizedPaddingAdjustmentTop;
            int paddingBottom = this.mDefaultPaddingBottom + this.mResizedPaddingAdjustmentBottom;
            if (!(getPaddingTop() == paddingTop && getPaddingBottom() == paddingBottom)) {
                setPaddingTopAndBottom(paddingTop, paddingBottom);
                remeasure = true;
            }
        } else {
            if (!(this.mResizedTextSize == -1 || currentSizePx == (i = this.mDefaultTextSize))) {
                setTextSize(0, (float) i);
                remeasure = true;
            }
            if (this.mMaintainLineSpacing) {
                float lineSpacingExtra = getLineSpacingExtra();
                float f = this.mDefaultLineSpacingExtra;
                if (lineSpacingExtra != f) {
                    setLineSpacing(f, getLineSpacingMultiplier());
                    remeasure = true;
                }
            }
            if (!(getPaddingTop() == this.mDefaultPaddingTop && getPaddingBottom() == this.mDefaultPaddingBottom)) {
                setPaddingTopAndBottom(this.mDefaultPaddingTop, this.mDefaultPaddingBottom);
                remeasure = true;
            }
        }
        this.mIsResized = resizeText;
        if (remeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void setPaddingTopAndBottom(int paddingTop, int paddingBottom) {
        if (isPaddingRelative()) {
            setPaddingRelative(getPaddingStart(), paddingTop, getPaddingEnd(), paddingBottom);
        } else {
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }
}
