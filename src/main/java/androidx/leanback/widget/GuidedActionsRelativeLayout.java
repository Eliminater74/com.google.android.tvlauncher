package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.leanback.C0364R;

class GuidedActionsRelativeLayout extends RelativeLayout {
    private boolean mInOverride;
    private InterceptKeyEventListener mInterceptKeyEventListener;
    private float mKeyLinePercent;

    interface InterceptKeyEventListener {
        boolean onInterceptKeyEvent(KeyEvent keyEvent);
    }

    public GuidedActionsRelativeLayout(Context context) {
        this(context, null);
    }

    public GuidedActionsRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidedActionsRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInOverride = false;
        this.mKeyLinePercent = GuidanceStylingRelativeLayout.getKeyLinePercent(context);
    }

    private void init() {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(C0364R.styleable.LeanbackGuidedStepTheme);
        this.mKeyLinePercent = ta.getFloat(C0364R.styleable.LeanbackGuidedStepTheme_guidedStepKeyline, 40.0f);
        ta.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View view;
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightSize > 0 && (view = findViewById(C0364R.C0366id.guidedactions_sub_list)) != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (lp.topMargin < 0 && !this.mInOverride) {
                this.mInOverride = true;
            }
            if (this.mInOverride) {
                lp.topMargin = (int) ((this.mKeyLinePercent * ((float) heightSize)) / 100.0f);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mInOverride = false;
    }

    public void setInterceptKeyEventListener(InterceptKeyEventListener l) {
        this.mInterceptKeyEventListener = l;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        InterceptKeyEventListener interceptKeyEventListener = this.mInterceptKeyEventListener;
        if (interceptKeyEventListener == null || !interceptKeyEventListener.onInterceptKeyEvent(event)) {
            return super.dispatchKeyEvent(event);
        }
        return true;
    }
}
