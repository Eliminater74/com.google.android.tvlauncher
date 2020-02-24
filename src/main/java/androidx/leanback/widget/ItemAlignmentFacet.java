package androidx.leanback.widget;

public final class ItemAlignmentFacet {
    public static final float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    private ItemAlignmentDef[] mAlignmentDefs = {new ItemAlignmentDef()};

    public static class ItemAlignmentDef {
        private boolean mAlignToBaseline;
        int mFocusViewId = -1;
        int mOffset = 0;
        float mOffsetPercent = 50.0f;
        boolean mOffsetWithPadding = false;
        int mViewId = -1;

        public final void setItemAlignmentOffset(int offset) {
            this.mOffset = offset;
        }

        public final int getItemAlignmentOffset() {
            return this.mOffset;
        }

        public final void setItemAlignmentOffsetWithPadding(boolean withPadding) {
            this.mOffsetWithPadding = withPadding;
        }

        public final boolean isItemAlignmentOffsetWithPadding() {
            return this.mOffsetWithPadding;
        }

        public final void setItemAlignmentOffsetPercent(float percent) {
            if ((percent < 0.0f || percent > 100.0f) && percent != -1.0f) {
                throw new IllegalArgumentException();
            }
            this.mOffsetPercent = percent;
        }

        public final float getItemAlignmentOffsetPercent() {
            return this.mOffsetPercent;
        }

        public final void setItemAlignmentViewId(int viewId) {
            this.mViewId = viewId;
        }

        public final int getItemAlignmentViewId() {
            return this.mViewId;
        }

        public final void setItemAlignmentFocusViewId(int viewId) {
            this.mFocusViewId = viewId;
        }

        public final int getItemAlignmentFocusViewId() {
            int i = this.mFocusViewId;
            return i != -1 ? i : this.mViewId;
        }

        public final void setAlignedToTextViewBaseline(boolean alignToBaseline) {
            this.mAlignToBaseline = alignToBaseline;
        }

        public boolean isAlignedToTextViewBaseLine() {
            return this.mAlignToBaseline;
        }
    }

    public boolean isMultiAlignment() {
        return this.mAlignmentDefs.length > 1;
    }

    public void setAlignmentDefs(ItemAlignmentDef[] defs) {
        if (defs == null || defs.length < 1) {
            throw new IllegalArgumentException();
        }
        this.mAlignmentDefs = defs;
    }

    public ItemAlignmentDef[] getAlignmentDefs() {
        return this.mAlignmentDefs;
    }
}
