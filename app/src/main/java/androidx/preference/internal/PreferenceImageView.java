package androidx.preference.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.preference.C0731R;

@SuppressLint({"AppCompatCustomView"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class PreferenceImageView extends ImageView {
    private int mMaxHeight;
    private int mMaxWidth;

    public PreferenceImageView(Context context) {
        this(context, null);
    }

    public PreferenceImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        TypedArray a = context.obtainStyledAttributes(attrs, C0731R.styleable.PreferenceImageView, defStyleAttr, 0);
        setMaxWidth(a.getDimensionPixelSize(C0731R.styleable.PreferenceImageView_maxWidth, Integer.MAX_VALUE));
        setMaxHeight(a.getDimensionPixelSize(C0731R.styleable.PreferenceImageView_maxHeight, Integer.MAX_VALUE));
        a.recycle();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.mMaxWidth = maxWidth;
        super.setMaxWidth(maxWidth);
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        super.setMaxHeight(maxHeight);
    }

    /* JADX INFO: Multiple debug info for r3v0 int: [D('heightMode' int), D('widthSize' int)] */
    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == Integer.MIN_VALUE || widthMode == 0) {
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int maxWidth = getMaxWidth();
            if (maxWidth != Integer.MAX_VALUE && (maxWidth < widthSize || widthMode == 0)) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, Integer.MIN_VALUE);
            }
        }
        int widthSize2 = View.MeasureSpec.getMode(heightMeasureSpec);
        if (widthSize2 == Integer.MIN_VALUE || widthSize2 == 0) {
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            int maxHeight = getMaxHeight();
            if (maxHeight != Integer.MAX_VALUE && (maxHeight < heightSize || widthSize2 == 0)) {
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, Integer.MIN_VALUE);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
