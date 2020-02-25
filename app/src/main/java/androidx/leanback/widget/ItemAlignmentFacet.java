package androidx.leanback.widget;

public final class ItemAlignmentFacet {
    public static final float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    private ItemAlignmentDef[] mAlignmentDefs = {new ItemAlignmentDef()};

    public boolean isMultiAlignment() {
        return this.mAlignmentDefs.length > 1;
    }

    public ItemAlignmentDef[] getAlignmentDefs() {
        return this.mAlignmentDefs;
    }

    public void setAlignmentDefs(ItemAlignmentDef[] defs) {
        if (defs == null || defs.length < 1) {
            throw new IllegalArgumentException();
        }
        this.mAlignmentDefs = defs;
    }

    public static class ItemAlignmentDef {
        int mFocusViewId = -1;
        int mOffset = 0;
        float mOffsetPercent = 50.0f;
        boolean mOffsetWithPadding = false;
        int mViewId = -1;
        private boolean mAlignToBaseline;

        public final int getItemAlignmentOffset() {
            return this.mOffset;
        }

        public final void setItemAlignmentOffset(int offset) {
            this.mOffset = offset;
        }

        public final boolean isItemAlignmentOffsetWithPadding() {
            return this.mOffsetWithPadding;
        }

        public final void setItemAlignmentOffsetWithPadding(boolean withPadding) {
            this.mOffsetWithPadding = withPadding;
        }

        public final float getItemAlignmentOffsetPercent() {
            return this.mOffsetPercent;
        }

        public final void setItemAlignmentOffsetPercent(float percent) {
            if ((percent < 0.0f || percent > 100.0f) && percent != -1.0f) {
                throw new IllegalArgumentException();
            }
            this.mOffsetPercent = percent;
        }

        public final int getItemAlignmentViewId() {
            return this.mViewId;
        }

        public final void setItemAlignmentViewId(int viewId) {
            this.mViewId = viewId;
        }

        public final int getItemAlignmentFocusViewId() {
            int i = this.mFocusViewId;
            return i != -1 ? i : this.mViewId;
        }

        public final void setItemAlignmentFocusViewId(int viewId) {
            this.mFocusViewId = viewId;
        }

        public final void setAlignedToTextViewBaseline(boolean alignToBaseline) {
            this.mAlignToBaseline = alignToBaseline;
        }

        public boolean isAlignedToTextViewBaseLine() {
            return this.mAlignToBaseline;
        }
    }
}
