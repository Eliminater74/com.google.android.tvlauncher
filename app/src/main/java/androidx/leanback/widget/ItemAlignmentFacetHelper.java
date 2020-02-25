package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

class ItemAlignmentFacetHelper {
    private static Rect sRect = new Rect();

    private ItemAlignmentFacetHelper() {
    }

    static int getAlignmentPosition(View itemView, ItemAlignmentFacet.ItemAlignmentDef facet, int orientation) {
        int alignPos;
        int alignPos2;
        int i;
        int i2;
        int i3;
        GridLayoutManager.LayoutParams p = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
        View view = itemView;
        if (facet.mViewId != 0 && (view = itemView.findViewById(facet.mViewId)) == null) {
            view = itemView;
        }
        int alignPos3 = facet.mOffset;
        if (orientation != 0) {
            if (facet.mOffsetWithPadding != 0) {
                if (facet.mOffsetPercent == 0.0f) {
                    alignPos3 += view.getPaddingTop();
                } else if (facet.mOffsetPercent == 100.0f) {
                    alignPos3 -= view.getPaddingBottom();
                }
            }
            if (facet.mOffsetPercent != -1.0f) {
                alignPos3 += (int) ((((float) (view == itemView ? p.getOpticalHeight(view) : view.getHeight())) * facet.mOffsetPercent) / 100.0f);
            }
            if (itemView != view) {
                Rect rect = sRect;
                rect.top = alignPos3;
                ((ViewGroup) itemView).offsetDescendantRectToMyCoords(view, rect);
                alignPos = sRect.top - p.getOpticalTopInset();
            } else {
                alignPos = alignPos3;
            }
            if (facet.isAlignedToTextViewBaseLine() != 0) {
                return alignPos + view.getBaseline();
            }
            return alignPos;
        } else if (itemView.getLayoutDirection() == 1) {
            if (view == itemView) {
                i2 = p.getOpticalWidth(view);
            } else {
                i2 = view.getWidth();
            }
            int alignPos4 = i2 - alignPos3;
            if (facet.mOffsetWithPadding != 0) {
                if (facet.mOffsetPercent == 0.0f) {
                    alignPos4 -= view.getPaddingRight();
                } else if (facet.mOffsetPercent == 100.0f) {
                    alignPos4 += view.getPaddingLeft();
                }
            }
            if (facet.mOffsetPercent != -1.0f) {
                if (view == itemView) {
                    i3 = p.getOpticalWidth(view);
                } else {
                    i3 = view.getWidth();
                }
                alignPos4 -= (int) ((((float) i3) * facet.mOffsetPercent) / 100.0f);
            }
            if (itemView == view) {
                return alignPos4;
            }
            Rect rect2 = sRect;
            rect2.right = alignPos4;
            ((ViewGroup) itemView).offsetDescendantRectToMyCoords(view, rect2);
            return sRect.right + p.getOpticalRightInset();
        } else {
            if (facet.mOffsetWithPadding != 0) {
                if (facet.mOffsetPercent == 0.0f) {
                    alignPos3 += view.getPaddingLeft();
                } else if (facet.mOffsetPercent == 100.0f) {
                    alignPos3 -= view.getPaddingRight();
                }
            }
            if (facet.mOffsetPercent != -1.0f) {
                if (view == itemView) {
                    i = p.getOpticalWidth(view);
                } else {
                    i = view.getWidth();
                }
                alignPos2 = alignPos3 + ((int) ((((float) i) * facet.mOffsetPercent) / 100.0f));
            } else {
                alignPos2 = alignPos3;
            }
            if (itemView == view) {
                return alignPos2;
            }
            Rect rect3 = sRect;
            rect3.left = alignPos2;
            ((ViewGroup) itemView).offsetDescendantRectToMyCoords(view, rect3);
            return sRect.left - p.getOpticalLeftInset();
        }
    }
}
