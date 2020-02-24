package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.leanback.C0364R;

class GuidanceStylingRelativeLayout extends RelativeLayout {
    private float mTitleKeylinePercent;

    public GuidanceStylingRelativeLayout(Context context) {
        this(context, null);
    }

    public GuidanceStylingRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidanceStylingRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTitleKeylinePercent = getKeyLinePercent(context);
    }

    public static float getKeyLinePercent(Context context) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(C0364R.styleable.LeanbackGuidedStepTheme);
        float percent = ta.getFloat(C0364R.styleable.LeanbackGuidedStepTheme_guidedStepKeyline, 40.0f);
        ta.recycle();
        return percent;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View mTitleView = getRootView().findViewById(C0364R.C0366id.guidance_title);
        View mBreadcrumbView = getRootView().findViewById(C0364R.C0366id.guidance_breadcrumb);
        View mDescriptionView = getRootView().findViewById(C0364R.C0366id.guidance_description);
        ImageView mIconView = (ImageView) getRootView().findViewById(C0364R.C0366id.guidance_icon);
        int mTitleKeylinePixels = (int) ((((float) getMeasuredHeight()) * this.mTitleKeylinePercent) / 100.0f);
        if (mTitleView != null && mTitleView.getParent() == this) {
            int offset = (((mTitleKeylinePixels - mTitleView.getBaseline()) - mBreadcrumbView.getMeasuredHeight()) - mTitleView.getPaddingTop()) - mBreadcrumbView.getTop();
            if (mBreadcrumbView.getParent() == this) {
                mBreadcrumbView.offsetTopAndBottom(offset);
            }
            mTitleView.offsetTopAndBottom(offset);
            if (mDescriptionView != null && mDescriptionView.getParent() == this) {
                mDescriptionView.offsetTopAndBottom(offset);
            }
        }
        if (mIconView != null && mIconView.getParent() == this && mIconView.getDrawable() != null) {
            mIconView.offsetTopAndBottom(mTitleKeylinePixels - (mIconView.getMeasuredHeight() / 2));
        }
    }
}
