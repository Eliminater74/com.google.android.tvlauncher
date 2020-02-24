package com.google.android.tvlauncher.doubleclick.customcreative;

import com.google.android.tvlauncher.doubleclick.OutstreamVideoAd;

public class CustomCreativeVideoAd extends OutstreamVideoAd {
    private CustomCreativeVideoAd(Builder builder) {
        super(builder);
    }

    public boolean supportsVideoTracking() {
        return false;
    }

    public static final class Builder extends OutstreamVideoAd.Builder<Builder> {
        public CustomCreativeVideoAd build() {
            return new CustomCreativeVideoAd(this);
        }
    }
}
