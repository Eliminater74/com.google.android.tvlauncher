package com.google.android.tvlauncher.doubleclick.customcreative;

public class CustomCreativeAdTagModel {
    private String mCorrelator;
    private String mInventoryUnit;
    private boolean mShouldUseDelayedImpression;
    private String mSize;
    private String mTile;

    private CustomCreativeAdTagModel(Builder builder) {
        this.mSize = builder.mSize;
        this.mInventoryUnit = builder.mInventoryUnit;
        this.mShouldUseDelayedImpression = builder.mShouldUseDelayedImpression;
        this.mCorrelator = builder.mCorrelator;
        this.mTile = builder.mTile;
    }

    /* access modifiers changed from: package-private */
    public String getSize() {
        return this.mSize;
    }

    /* access modifiers changed from: package-private */
    public String getInventoryUnit() {
        return this.mInventoryUnit;
    }

    /* access modifiers changed from: package-private */
    public String getCorrelator() {
        return this.mCorrelator;
    }

    /* access modifiers changed from: package-private */
    public String getTile() {
        return this.mTile;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String mCorrelator;
        /* access modifiers changed from: private */
        public String mInventoryUnit;
        /* access modifiers changed from: private */
        public boolean mShouldUseDelayedImpression;
        /* access modifiers changed from: private */
        public String mSize;
        /* access modifiers changed from: private */
        public String mTile;

        public Builder setSize(String size) {
            this.mSize = size;
            return this;
        }

        public Builder setInventoryUnit(String inventoryUnit) {
            this.mInventoryUnit = inventoryUnit;
            return this;
        }

        public Builder setShouldUseDelayedImpression(boolean shouldUseDelayedImpression) {
            this.mShouldUseDelayedImpression = shouldUseDelayedImpression;
            return this;
        }

        public Builder setCorrelator(String correlator) {
            this.mCorrelator = correlator;
            return this;
        }

        public Builder setTile(String tile) {
            this.mTile = tile;
            return this;
        }

        public CustomCreativeAdTagModel build() {
            return new CustomCreativeAdTagModel(this);
        }
    }
}
