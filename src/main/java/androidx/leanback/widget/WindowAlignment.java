package androidx.leanback.widget;

class WindowAlignment {
    public final Axis horizontal = new Axis("horizontal");
    private Axis mMainAxis = this.horizontal;
    private int mOrientation = 0;
    private Axis mSecondAxis = this.vertical;
    public final Axis vertical = new Axis("vertical");

    WindowAlignment() {
    }

    public static class Axis {
        static final int PF_KEYLINE_OVER_HIGH_EDGE = 2;
        static final int PF_KEYLINE_OVER_LOW_EDGE = 1;
        private int mMaxEdge;
        private int mMaxScroll;
        private int mMinEdge;
        private int mMinScroll;
        private String mName;
        private int mPaddingMax;
        private int mPaddingMin;
        private int mPreferredKeyLine = 2;
        private boolean mReversedFlow;
        private int mSize;
        private int mWindowAlignment = 3;
        private int mWindowAlignmentOffset = 0;
        private float mWindowAlignmentOffsetPercent = 50.0f;

        public Axis(String name) {
            reset();
            this.mName = name;
        }

        public final int getWindowAlignment() {
            return this.mWindowAlignment;
        }

        public final void setWindowAlignment(int windowAlignment) {
            this.mWindowAlignment = windowAlignment;
        }

        /* access modifiers changed from: package-private */
        public final void setPreferKeylineOverLowEdge(boolean keylineOverLowEdge) {
            int i = this.mPreferredKeyLine;
            this.mPreferredKeyLine = keylineOverLowEdge ? i | 1 : i & -2;
        }

        /* access modifiers changed from: package-private */
        public final void setPreferKeylineOverHighEdge(boolean keylineOverHighEdge) {
            int i = this.mPreferredKeyLine;
            this.mPreferredKeyLine = keylineOverHighEdge ? i | 2 : i & -3;
        }

        /* access modifiers changed from: package-private */
        public final boolean isPreferKeylineOverHighEdge() {
            return (this.mPreferredKeyLine & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public final boolean isPreferKeylineOverLowEdge() {
            return (this.mPreferredKeyLine & 1) != 0;
        }

        public final int getWindowAlignmentOffset() {
            return this.mWindowAlignmentOffset;
        }

        public final void setWindowAlignmentOffset(int offset) {
            this.mWindowAlignmentOffset = offset;
        }

        public final void setWindowAlignmentOffsetPercent(float percent) {
            if ((percent < 0.0f || percent > 100.0f) && percent != -1.0f) {
                throw new IllegalArgumentException();
            }
            this.mWindowAlignmentOffsetPercent = percent;
        }

        public final float getWindowAlignmentOffsetPercent() {
            return this.mWindowAlignmentOffsetPercent;
        }

        public final int getMinScroll() {
            return this.mMinScroll;
        }

        public final void invalidateScrollMin() {
            this.mMinEdge = Integer.MIN_VALUE;
            this.mMinScroll = Integer.MIN_VALUE;
        }

        public final int getMaxScroll() {
            return this.mMaxScroll;
        }

        public final void invalidateScrollMax() {
            this.mMaxEdge = Integer.MAX_VALUE;
            this.mMaxScroll = Integer.MAX_VALUE;
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.mMinEdge = Integer.MIN_VALUE;
            this.mMaxEdge = Integer.MAX_VALUE;
        }

        public final boolean isMinUnknown() {
            return this.mMinEdge == Integer.MIN_VALUE;
        }

        public final boolean isMaxUnknown() {
            return this.mMaxEdge == Integer.MAX_VALUE;
        }

        public final void setSize(int size) {
            this.mSize = size;
        }

        public final int getSize() {
            return this.mSize;
        }

        public final void setPadding(int paddingMin, int paddingMax) {
            this.mPaddingMin = paddingMin;
            this.mPaddingMax = paddingMax;
        }

        public final int getPaddingMin() {
            return this.mPaddingMin;
        }

        public final int getPaddingMax() {
            return this.mPaddingMax;
        }

        public final int getClientSize() {
            return (this.mSize - this.mPaddingMin) - this.mPaddingMax;
        }

        /* access modifiers changed from: package-private */
        public final int calculateKeyline() {
            int keyLine;
            int keyLine2;
            if (!this.mReversedFlow) {
                int i = this.mWindowAlignmentOffset;
                if (i >= 0) {
                    keyLine2 = this.mWindowAlignmentOffset;
                } else {
                    keyLine2 = i + this.mSize;
                }
                float f = this.mWindowAlignmentOffsetPercent;
                if (f != -1.0f) {
                    return keyLine2 + ((int) ((((float) this.mSize) * f) / 100.0f));
                }
                return keyLine2;
            }
            int keyLine3 = this.mWindowAlignmentOffset;
            if (keyLine3 >= 0) {
                keyLine = this.mSize - keyLine3;
            } else {
                keyLine = -keyLine3;
            }
            float f2 = this.mWindowAlignmentOffsetPercent;
            if (f2 != -1.0f) {
                return keyLine - ((int) ((((float) this.mSize) * f2) / 100.0f));
            }
            return keyLine;
        }

        /* access modifiers changed from: package-private */
        public final int calculateScrollToKeyLine(int viewCenterPosition, int keyLine) {
            return viewCenterPosition - keyLine;
        }

        public final void updateMinMax(int minEdge, int maxEdge, int minChildViewCenter, int maxChildViewCenter) {
            this.mMinEdge = minEdge;
            this.mMaxEdge = maxEdge;
            int clientSize = getClientSize();
            int keyLine = calculateKeyline();
            boolean isMinUnknown = isMinUnknown();
            boolean isMaxUnknown = isMaxUnknown();
            if (!isMinUnknown) {
                if (!this.mReversedFlow) {
                    this.mMinScroll = calculateScrollToKeyLine(minChildViewCenter, keyLine);
                } else {
                    this.mMinScroll = calculateScrollToKeyLine(minChildViewCenter, keyLine);
                }
                this.mMinScroll = this.mMinEdge - this.mPaddingMin;
            }
            if (!isMaxUnknown) {
                if (!this.mReversedFlow) {
                    this.mMaxScroll = calculateScrollToKeyLine(maxChildViewCenter, keyLine);
                } else {
                    this.mMaxScroll = calculateScrollToKeyLine(maxChildViewCenter, keyLine);
                }
                this.mMaxScroll = (this.mMaxEdge - this.mPaddingMin) - clientSize;
            }
            if (!isMaxUnknown && !isMinUnknown) {
                if (!this.mReversedFlow) {
                    int i = this.mWindowAlignment;
                    if ((i & 1) != 0) {
                        if (isPreferKeylineOverLowEdge()) {
                            this.mMinScroll = Math.min(this.mMinScroll, calculateScrollToKeyLine(maxChildViewCenter, keyLine));
                        }
                        this.mMaxScroll = Math.max(this.mMinScroll, this.mMaxScroll);
                    } else if ((i & 2) != 0) {
                        if (isPreferKeylineOverHighEdge()) {
                            this.mMaxScroll = Math.max(this.mMaxScroll, calculateScrollToKeyLine(minChildViewCenter, keyLine));
                        }
                        this.mMinScroll = Math.min(this.mMinScroll, this.mMaxScroll);
                    }
                } else {
                    int i2 = this.mWindowAlignment;
                    if ((i2 & 1) != 0) {
                        if (isPreferKeylineOverLowEdge()) {
                            this.mMaxScroll = Math.max(this.mMaxScroll, calculateScrollToKeyLine(minChildViewCenter, keyLine));
                        }
                        this.mMinScroll = Math.min(this.mMinScroll, this.mMaxScroll);
                    } else if ((i2 & 2) != 0) {
                        if (isPreferKeylineOverHighEdge()) {
                            this.mMinScroll = Math.min(this.mMinScroll, calculateScrollToKeyLine(maxChildViewCenter, keyLine));
                        }
                        this.mMaxScroll = Math.max(this.mMinScroll, this.mMaxScroll);
                    }
                }
            }
        }

        public final int getScroll(int viewCenter) {
            int size = getSize();
            int keyLine = calculateKeyline();
            boolean isMinUnknown = isMinUnknown();
            boolean isMaxUnknown = isMaxUnknown();
            if (!isMinUnknown) {
                int keyLineToMinEdge = keyLine - this.mPaddingMin;
                if (this.mReversedFlow ? (this.mWindowAlignment & 2) != 0 : (this.mWindowAlignment & 1) != 0) {
                    int i = this.mMinEdge;
                    if (viewCenter - i <= keyLineToMinEdge) {
                        int alignToMin = i - this.mPaddingMin;
                        if (isMaxUnknown || alignToMin <= this.mMaxScroll) {
                            return alignToMin;
                        }
                        return this.mMaxScroll;
                    }
                }
            }
            if (!isMaxUnknown) {
                int keyLineToMaxEdge = (size - keyLine) - this.mPaddingMax;
                if (this.mReversedFlow ? (this.mWindowAlignment & 1) != 0 : (this.mWindowAlignment & 2) != 0) {
                    int i2 = this.mMaxEdge;
                    if (i2 - viewCenter <= keyLineToMaxEdge) {
                        int alignToMax = i2 - (size - this.mPaddingMax);
                        if (isMinUnknown || alignToMax >= this.mMinScroll) {
                            return alignToMax;
                        }
                        return this.mMinScroll;
                    }
                }
            }
            return calculateScrollToKeyLine(viewCenter, keyLine);
        }

        public final void setReversedFlow(boolean reversedFlow) {
            this.mReversedFlow = reversedFlow;
        }

        public String toString() {
            return " min:" + this.mMinEdge + " " + this.mMinScroll + " max:" + this.mMaxEdge + " " + this.mMaxScroll;
        }
    }

    public final Axis mainAxis() {
        return this.mMainAxis;
    }

    public final Axis secondAxis() {
        return this.mSecondAxis;
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

    public final int getOrientation() {
        return this.mOrientation;
    }

    public final void reset() {
        mainAxis().reset();
    }

    public String toString() {
        return "horizontal=" + this.horizontal + "; vertical=" + this.vertical;
    }
}
