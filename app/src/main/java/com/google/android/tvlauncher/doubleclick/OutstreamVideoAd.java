package com.google.android.tvlauncher.doubleclick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;

import java.util.List;

public abstract class OutstreamVideoAd {
    private final AdConfig.AdAsset mAdAsset;
    private final TrackingUrl mClickTrackingUrl;
    private final String mDeeplinkUrl;
    private final List<TrackingUrl> mDisplayBannerImpressionTrackingUrls;
    private final String mImageUri;
    private final String mMarketUrl;
    private final String mPackageName;
    private final List<TrackingUrl> mVideoImpressionTrackingUrls;
    private final String mVideoUri;
    private long mVideoDurationMillis;

    public OutstreamVideoAd(Builder builder) {
        this.mImageUri = builder.mImageUri;
        this.mVideoUri = builder.mVideoUri;
        this.mDisplayBannerImpressionTrackingUrls = builder.mDisplayBannerImpressionTrackingUrls;
        this.mVideoImpressionTrackingUrls = builder.mVideoImpressionTrackingUrls;
        this.mClickTrackingUrl = builder.mClickTrackingUrl;
        this.mPackageName = builder.mPackageName;
        this.mMarketUrl = builder.mMarketUrl;
        this.mDeeplinkUrl = builder.mDeeplinkUrl;
        this.mVideoDurationMillis = builder.mVideoDurationMillis;
        this.mAdAsset = builder.mAdAsset;
    }

    public String getImageUri() {
        return this.mImageUri;
    }

    public String getVideoUri() {
        return this.mVideoUri;
    }

    @NonNull
    public List<TrackingUrl> getDisplayBannerImpressionTrackingUrls() {
        return this.mDisplayBannerImpressionTrackingUrls;
    }

    @NonNull
    public List<TrackingUrl> getVideoImpressionTrackingUrls() {
        return this.mVideoImpressionTrackingUrls;
    }

    @Nullable
    public TrackingUrl getClickTrackingUrl() {
        return this.mClickTrackingUrl;
    }

    @Nullable
    public String getPackageName() {
        return this.mPackageName;
    }

    @Nullable
    public String getMarketUrl() {
        return this.mMarketUrl;
    }

    @Nullable
    public String getDeepLinkUrl() {
        return this.mDeeplinkUrl;
    }

    public long getVideoDurationMillis() {
        return this.mVideoDurationMillis;
    }

    @Nullable
    public AdConfig.AdAsset getAdAsset() {
        return this.mAdAsset;
    }

    @Nullable
    public String getAdUnitId() {
        AdConfig.AdAsset adAsset = this.mAdAsset;
        if (adAsset == null || !adAsset.hasDoubleclickAdConfig()) {
            return null;
        }
        return this.mAdAsset.getDoubleclickAdConfig().adUnitId;
    }

    public long getExpirationMillis() {
        AdConfig.AdAsset adAsset = this.mAdAsset;
        if (adAsset == null) {
            return -1;
        }
        return adAsset.expiration;
    }

    public boolean supportsVideoTracking() {
        return false;
    }

    public String toString() {
        return String.format("OutstreamVideoAd: [imageUri=%s, videoUri=%s, displayTrackingUrls=%s, videoTrackingUrls=%s, clickTrackingUrl=%s, packageName=%s, marketUrl=%s, deeplinkUrl=%s, adAsset=%s]", getImageUri(), getVideoUri(), getDisplayBannerImpressionTrackingUrls(), getVideoImpressionTrackingUrls(), getClickTrackingUrl(), getPackageName(), getMarketUrl(), getDeepLinkUrl(), getAdAsset());
    }

    public static abstract class Builder<T extends Builder<T>> {
        /* access modifiers changed from: private */
        public AdConfig.AdAsset mAdAsset;
        /* access modifiers changed from: private */
        public TrackingUrl mClickTrackingUrl;
        /* access modifiers changed from: private */
        public String mDeeplinkUrl;
        /* access modifiers changed from: private */
        public List<TrackingUrl> mDisplayBannerImpressionTrackingUrls;
        /* access modifiers changed from: private */
        public String mImageUri;
        /* access modifiers changed from: private */
        public String mMarketUrl;
        /* access modifiers changed from: private */
        public String mPackageName;
        /* access modifiers changed from: private */
        public long mVideoDurationMillis;
        /* access modifiers changed from: private */
        public List<TrackingUrl> mVideoImpressionTrackingUrls;
        /* access modifiers changed from: private */
        public String mVideoUri;

        private T self() {
            return this;
        }

        public T setImageUri(String imageUri) {
            this.mImageUri = imageUri;
            return self();
        }

        public T setVideoUri(String videoUri) {
            this.mVideoUri = videoUri;
            return self();
        }

        public T setDisplayBannerImpressionTrackingUrls(List<TrackingUrl> displayBannerImpressionTrackingUrls) {
            this.mDisplayBannerImpressionTrackingUrls = displayBannerImpressionTrackingUrls;
            return self();
        }

        public T setVideoImpressionTrackingUrls(List<TrackingUrl> videoImpressionTrackingUrls) {
            this.mVideoImpressionTrackingUrls = videoImpressionTrackingUrls;
            return self();
        }

        public T setClickTrackingUrl(TrackingUrl clickTrackingUrl) {
            this.mClickTrackingUrl = clickTrackingUrl;
            return self();
        }

        public T setPackageName(String packageName) {
            this.mPackageName = packageName;
            return self();
        }

        public T setMarketUrl(String marketUrl) {
            this.mMarketUrl = marketUrl;
            return self();
        }

        public T setDeeplinkUrl(String deeplinkUrl) {
            this.mDeeplinkUrl = deeplinkUrl;
            return self();
        }

        public T setVideoDurationMillis(long videoDurationMillis) {
            this.mVideoDurationMillis = videoDurationMillis;
            return self();
        }

        public T setAdAsset(AdConfig.AdAsset adAsset) {
            this.mAdAsset = adAsset;
            return self();
        }
    }
}
