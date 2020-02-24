package com.google.android.tvlauncher.doubleclick.vast;

import com.google.android.tvlauncher.doubleclick.OutstreamVideoAd;

class VastVideoAd extends OutstreamVideoAd {
    private VastVideoAd(Builder builder) {
        super(builder);
    }

    public boolean supportsVideoTracking() {
        return true;
    }

    static final class Builder extends OutstreamVideoAd.Builder<Builder> {
        Builder() {
        }

        public VastVideoAd build() {
            return new VastVideoAd(this);
        }
    }
}
