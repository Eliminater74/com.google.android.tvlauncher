package androidx.leanback.widget;

import android.view.View;

class ItemAlignment {
    public final Axis horizontal = new Axis(0);
    public final Axis vertical = new Axis(1);
    private Axis mMainAxis = this.horizontal;
    private int mOrientation = 0;
    private Axis mSecondAxis = this.vertical;

    ItemAlignment() {
    }

    public final Axis mainAxis() {
        return this.mMainAxis;
    }

    public final Axis secondAxis() {
        return this.mSecondAxis;
    }

    public final int getOrientation() {
        return this.mOrientation;
    }

    public final void setOrientation(int orientation) {
        this.mOrientation = orientation;
        if (this.mOrientation == 0) {
            this.mMainAxis = this.horizontal;
            this.mSecondAxis = this.vertical;
            return;
        }
        this.mMainAxis = this.vertical;
        this.mSecondAxis = this.horizontal;
    }

    static final class Axis extends ItemAlignmentFacet.ItemAlignmentDef {
        private int mOrientation;

        Axis(int orientation) {
            this.mOrientation = orientation;
        }

        public int getAlignmentPosition(View itemView) {
            return ItemAlignmentFacetHelper.getAlignmentPosition(itemView, this, this.mOrientation);
        }
    }
}
