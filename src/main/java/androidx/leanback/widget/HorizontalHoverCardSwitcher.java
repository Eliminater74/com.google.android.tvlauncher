package androidx.leanback.widget;

import android.graphics.Rect;
import android.support.p001v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

public final class HorizontalHoverCardSwitcher extends PresenterSwitcher {
    int mCardLeft;
    int mCardRight;
    private int[] mTmpOffsets = new int[2];
    private Rect mTmpRect = new Rect();

    /* access modifiers changed from: protected */
    public void insertView(View view) {
        getParentViewGroup().addView(view);
    }

    /* access modifiers changed from: protected */
    public void onViewSelected(View view) {
        int rightLimit = getParentViewGroup().getWidth() - getParentViewGroup().getPaddingRight();
        int leftLimit = getParentViewGroup().getPaddingLeft();
        boolean isRtl = false;
        view.measure(0, 0);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (ViewCompat.getLayoutDirection(view) == 1) {
            isRtl = true;
        }
        if (!isRtl && this.mCardLeft + view.getMeasuredWidth() > rightLimit) {
            params.leftMargin = rightLimit - view.getMeasuredWidth();
        } else if (isRtl && this.mCardLeft < leftLimit) {
            params.leftMargin = leftLimit;
        } else if (isRtl) {
            params.leftMargin = this.mCardRight - view.getMeasuredWidth();
        } else {
            params.leftMargin = this.mCardLeft;
        }
        view.requestLayout();
    }

    public void select(HorizontalGridView gridView, View childView, Object object) {
        ViewGroup parent = getParentViewGroup();
        gridView.getViewSelectedOffsets(childView, this.mTmpOffsets);
        this.mTmpRect.set(0, 0, childView.getWidth(), childView.getHeight());
        parent.offsetDescendantRectToMyCoords(childView, this.mTmpRect);
        this.mCardLeft = this.mTmpRect.left - this.mTmpOffsets[0];
        this.mCardRight = this.mTmpRect.right - this.mTmpOffsets[0];
        select(object);
    }
}
