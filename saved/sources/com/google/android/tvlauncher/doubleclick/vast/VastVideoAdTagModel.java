package com.google.android.tvlauncher.doubleclick.vast;

import android.util.Size;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VastVideoAdTagModel {
    private final String mAdUnitId;
    private final Set<Size> mAdUnitSizes;
    private final Set<Size> mCompanionAdSizes;
    private final String mCorrelator;
    private final String mDescriptionUrl;
    private Map<String, String> mTargeting;
    private final String mUrl;

    private VastVideoAdTagModel(Builder builder) {
        this.mAdUnitId = builder.mAdUnitId;
        this.mUrl = builder.mUrl;
        this.mDescriptionUrl = builder.mDescriptionUrl;
        this.mAdUnitSizes = builder.mAdUnitSizes;
        this.mCompanionAdSizes = builder.mCompanionAdSizes;
        this.mCorrelator = builder.mCorrelator;
        this.mTargeting = builder.mTargeting;
    }

    /* access modifiers changed from: package-private */
    public String getAdUnitId() {
        return this.mAdUnitId;
    }

    /* access modifiers changed from: package-private */
    public String getUrl() {
        return this.mUrl;
    }

    /* access modifiers changed from: package-private */
    public String getDescriptionUrl() {
        return this.mDescriptionUrl;
    }

    /* access modifiers changed from: package-private */
    public Set<Size> getAdUnitSizes() {
        return this.mAdUnitSizes;
    }

    /* access modifiers changed from: package-private */
    public Set<Size> getCompanionAdSizes() {
        return this.mCompanionAdSizes;
    }

    /* access modifiers changed from: package-private */
    public String getCorrelator() {
        return this.mCorrelator;
    }

    /* access modifiers changed from: package-private */
    public Map<String, String> getTargeting() {
        return this.mTargeting;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String mAdUnitId;
        /* access modifiers changed from: private */
        public Set<Size> mAdUnitSizes = new HashSet();
        /* access modifiers changed from: private */
        public Set<Size> mCompanionAdSizes = new HashSet();
        /* access modifiers changed from: private */
        public String mCorrelator;
        /* access modifiers changed from: private */
        public String mDescriptionUrl;
        /* access modifiers changed from: private */
        public Map<String, String> mTargeting;
        /* access modifiers changed from: private */
        public String mUrl;

        public Builder setAdUnitId(String adUnitId) {
            this.mAdUnitId = adUnitId;
            return this;
        }

        public Builder setUrl(String url) {
            this.mUrl = url;
            return this;
        }

        public Builder setDescriptionUrl(String descriptionUrl) {
            this.mDescriptionUrl = descriptionUrl;
            return this;
        }

        public Builder setAdUnitSizes(Set<Size> adUnitSizes) {
            this.mAdUnitSizes = adUnitSizes;
            return this;
        }

        public Builder setCompanionAdSizes(Set<Size> companionAdSizes) {
            this.mCompanionAdSizes = companionAdSizes;
            return this;
        }

        public Builder setCorrelator(String correlator) {
            this.mCorrelator = correlator;
            return this;
        }

        public Builder setTargeting(Map<String, String> targeting) {
            this.mTargeting = targeting;
            return this;
        }

        public VastVideoAdTagModel build() {
            return new VastVideoAdTagModel(this);
        }
    }
}
