package com.google.android.tvlauncher.home.util;

public class ChannelStateSettings {
    private int mChannelItemsTitleMarginBottom;
    private int mChannelItemsTitleMarginTop;
    private int mChannelLogoAlignmentOriginMargin;
    private int mChannelLogoHeight;
    private int mChannelLogoKeylineOffset;
    private int mChannelLogoMarginEnd;
    private int mChannelLogoMarginStart;
    private int mChannelLogoTitleMarginBottom;
    private int mChannelLogoWidth;
    private int mEmptyChannelMessageMarginTop;
    private int mItemHeight;
    private int mItemMarginBottom;
    private int mItemMarginTop;
    private int mMarginBottom;
    private int mMarginTop;

    private ChannelStateSettings(Builder builder) {
        this.mItemHeight = builder.mItemHeight;
        this.mItemMarginTop = builder.mItemMarginTop;
        this.mItemMarginBottom = builder.mItemMarginBottom;
        this.mMarginTop = builder.mMarginTop;
        this.mMarginBottom = builder.mMarginBottom;
        this.mChannelLogoAlignmentOriginMargin = builder.mChannelLogoAlignmentOriginMargin;
        this.mChannelLogoWidth = builder.mChannelLogoWidth;
        this.mChannelLogoHeight = builder.mChannelLogoHeight;
        this.mChannelLogoMarginStart = builder.mChannelLogoMarginStart;
        this.mChannelLogoMarginEnd = builder.mChannelLogoMarginEnd;
        this.mChannelLogoKeylineOffset = builder.mChannelLogoKeylineOffset;
        this.mChannelLogoTitleMarginBottom = builder.mChannelLogoTitleMarginBottom;
        this.mChannelItemsTitleMarginTop = builder.mChannelItemsTitleMarginTop;
        this.mChannelItemsTitleMarginBottom = builder.mChannelItemsTitleMarginBottom;
        this.mEmptyChannelMessageMarginTop = builder.mEmptyChannelMessageMarginTop;
    }

    ChannelStateSettings(ChannelStateSettings copy) {
        this.mItemHeight = copy.getItemHeight();
        this.mItemMarginTop = copy.getItemMarginTop();
        this.mItemMarginBottom = copy.getItemMarginBottom();
        this.mMarginTop = copy.getMarginTop();
        this.mMarginBottom = copy.getMarginBottom();
        this.mChannelLogoAlignmentOriginMargin = copy.getChannelLogoAlignmentOriginMargin();
        this.mChannelLogoWidth = copy.getChannelLogoWidth();
        this.mChannelLogoHeight = copy.getChannelLogoHeight();
        this.mChannelLogoMarginStart = copy.getChannelLogoMarginStart();
        this.mChannelLogoMarginEnd = copy.getChannelLogoMarginEnd();
        this.mChannelLogoKeylineOffset = copy.getChannelLogoKeylineOffset();
        this.mChannelLogoTitleMarginBottom = copy.getChannelLogoTitleMarginBottom();
        this.mChannelItemsTitleMarginTop = copy.getChannelItemsTitleMarginTop();
        this.mChannelItemsTitleMarginBottom = copy.getChannelItemsTitleMarginBottom();
        this.mEmptyChannelMessageMarginTop = copy.getEmptyChannelMessageMarginTop();
    }

    public int getItemHeight() {
        return this.mItemHeight;
    }

    /* access modifiers changed from: package-private */
    public void setItemHeight(int itemHeight) {
        this.mItemHeight = itemHeight;
    }

    public int getItemMarginTop() {
        return this.mItemMarginTop;
    }

    /* access modifiers changed from: package-private */
    public void setItemMarginTop(int itemMarginTop) {
        this.mItemMarginTop = itemMarginTop;
    }

    public int getItemMarginBottom() {
        return this.mItemMarginBottom;
    }

    /* access modifiers changed from: package-private */
    public void setItemMarginBottom(int itemMarginBottom) {
        this.mItemMarginBottom = itemMarginBottom;
    }

    public int getMarginTop() {
        return this.mMarginTop;
    }

    public int getMarginBottom() {
        return this.mMarginBottom;
    }

    public int getChannelLogoAlignmentOriginMargin() {
        return this.mChannelLogoAlignmentOriginMargin;
    }

    /* access modifiers changed from: package-private */
    public void setChannelLogoAlignmentOriginMargin(int channelLogoAlignmentOriginMargin) {
        this.mChannelLogoAlignmentOriginMargin = channelLogoAlignmentOriginMargin;
    }

    public int getChannelLogoWidth() {
        return this.mChannelLogoWidth;
    }

    /* access modifiers changed from: package-private */
    public void setChannelLogoWidth(int channelLogoWidth) {
        this.mChannelLogoWidth = channelLogoWidth;
    }

    public int getChannelLogoHeight() {
        return this.mChannelLogoHeight;
    }

    /* access modifiers changed from: package-private */
    public void setChannelLogoHeight(int channelLogoHeight) {
        this.mChannelLogoHeight = channelLogoHeight;
    }

    public int getChannelLogoMarginStart() {
        return this.mChannelLogoMarginStart;
    }

    /* access modifiers changed from: package-private */
    public void setChannelLogoMarginStart(int channelLogoMarginStart) {
        this.mChannelLogoMarginStart = channelLogoMarginStart;
    }

    public int getChannelLogoMarginEnd() {
        return this.mChannelLogoMarginEnd;
    }

    /* access modifiers changed from: package-private */
    public void setChannelLogoMarginEnd(int channelLogoMarginEnd) {
        this.mChannelLogoMarginEnd = channelLogoMarginEnd;
    }

    public int getChannelLogoKeylineOffset() {
        return this.mChannelLogoKeylineOffset;
    }

    public int getChannelLogoTitleMarginBottom() {
        return this.mChannelLogoTitleMarginBottom;
    }

    public int getEmptyChannelMessageMarginTop() {
        return this.mEmptyChannelMessageMarginTop;
    }

    /* access modifiers changed from: package-private */
    public void setEmptyChannelMessageMarginTop(int emptyChannelMessageMarginTop) {
        this.mEmptyChannelMessageMarginTop = emptyChannelMessageMarginTop;
    }

    public int getChannelItemsTitleMarginTop() {
        return this.mChannelItemsTitleMarginTop;
    }

    /* access modifiers changed from: package-private */
    public void setChannelItemsTitleMarginTop(int channelItemsTitleMarginTop) {
        this.mChannelItemsTitleMarginTop = channelItemsTitleMarginTop;
    }

    public int getChannelItemsTitleMarginBottom() {
        return this.mChannelItemsTitleMarginBottom;
    }

    /* access modifiers changed from: package-private */
    public void setChannelItemsTitleMarginBottom(int channelItemsTitleMarginBottom) {
        this.mChannelItemsTitleMarginBottom = channelItemsTitleMarginBottom;
    }

    static class Builder {
        /* access modifiers changed from: private */
        public int mChannelItemsTitleMarginBottom;
        /* access modifiers changed from: private */
        public int mChannelItemsTitleMarginTop;
        /* access modifiers changed from: private */
        public int mChannelLogoAlignmentOriginMargin;
        /* access modifiers changed from: private */
        public int mChannelLogoHeight;
        /* access modifiers changed from: private */
        public int mChannelLogoKeylineOffset;
        /* access modifiers changed from: private */
        public int mChannelLogoMarginEnd;
        /* access modifiers changed from: private */
        public int mChannelLogoMarginStart;
        /* access modifiers changed from: private */
        public int mChannelLogoTitleMarginBottom;
        /* access modifiers changed from: private */
        public int mChannelLogoWidth;
        /* access modifiers changed from: private */
        public int mEmptyChannelMessageMarginTop;
        /* access modifiers changed from: private */
        public int mItemHeight;
        /* access modifiers changed from: private */
        public int mItemMarginBottom;
        /* access modifiers changed from: private */
        public int mItemMarginTop;
        /* access modifiers changed from: private */
        public int mMarginBottom;
        /* access modifiers changed from: private */
        public int mMarginTop;

        Builder() {
        }

        /* access modifiers changed from: package-private */
        public Builder setItemHeight(int itemHeight) {
            this.mItemHeight = itemHeight;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setItemMarginTop(int itemMarginTop) {
            this.mItemMarginTop = itemMarginTop;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setItemMarginBottom(int itemMarginBottom) {
            this.mItemMarginBottom = itemMarginBottom;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setMarginTop(int marginTop) {
            this.mMarginTop = marginTop;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setMarginBottom(int marginBottom) {
            this.mMarginBottom = marginBottom;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoAlignmentOriginMargin(int channelLogoAlignmentOriginMargin) {
            this.mChannelLogoAlignmentOriginMargin = channelLogoAlignmentOriginMargin;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoWidth(int channelLogoWidth) {
            this.mChannelLogoWidth = channelLogoWidth;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoHeight(int channelLogoHeight) {
            this.mChannelLogoHeight = channelLogoHeight;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoMarginStart(int channelLogoMarginStart) {
            this.mChannelLogoMarginStart = channelLogoMarginStart;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoMarginEnd(int channelLogoMarginEnd) {
            this.mChannelLogoMarginEnd = channelLogoMarginEnd;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoKeylineOffset(int channelLogoKeylineOffset) {
            this.mChannelLogoKeylineOffset = channelLogoKeylineOffset;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelLogoTitleMarginBottom(int channelLogoTitleMarginBottom) {
            this.mChannelLogoTitleMarginBottom = channelLogoTitleMarginBottom;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelItemsTitleMarginTop(int channelItemsTitleMarginTop) {
            this.mChannelItemsTitleMarginTop = channelItemsTitleMarginTop;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setChannelItemsTitleMarginBottom(int channelItemsTitleMarginBottom) {
            this.mChannelItemsTitleMarginBottom = channelItemsTitleMarginBottom;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder setEmptyChannelMessageMarginTop(int emptyChannelMessageMarginTop) {
            this.mEmptyChannelMessageMarginTop = emptyChannelMessageMarginTop;
            return this;
        }

        /* access modifiers changed from: package-private */
        public ChannelStateSettings build() {
            return new ChannelStateSettings(this);
        }
    }
}
