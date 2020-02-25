package com.google.android.tvlauncher.home;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.application.TvLauncherApplication;
import com.google.android.tvlauncher.doubleclick.AdVideoTracker;
import com.google.android.tvlauncher.doubleclick.AdVideoTrackerFactory;
import com.google.android.tvlauncher.doubleclick.AdsManager;
import com.google.android.tvlauncher.doubleclick.AdsUtil;
import com.google.android.tvlauncher.doubleclick.DirectAdConfigSerializer;
import com.google.android.tvlauncher.doubleclick.DirectVideoAd;
import com.google.android.tvlauncher.doubleclick.DoubleClickAdConfigSerializer;
import com.google.android.tvlauncher.doubleclick.OutstreamVideoAd;
import com.google.android.tvlauncher.doubleclick.OutstreamVideoAdFactory;
import com.google.android.tvlauncher.doubleclick.VideoProgressPoller;
import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;
import com.google.android.tvlauncher.home.view.ProgramView;
import com.google.android.tvlauncher.instantvideo.widget.InstantVideoView;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.util.Clock;
import com.google.android.tvlauncher.util.IntentLaunchDispatcher;

class SponsoredProgramControllerHelper {
    private static final boolean DEBUG = false;
    private static final long START_TIME_UNINITIALIZED = -1;
    private static final String TAG = "SponsoredProgramControllerHelper";
    /* access modifiers changed from: private */
    public final ProgramView mView;
    private final InstantVideoView mPreviewVideo;
    /* access modifiers changed from: private */
    public AdsManager mAdsManager;
    /* access modifiers changed from: private */
    public AdsUtil mAdsUtil;
    /* access modifiers changed from: private */
    public Clock mClock;
    /* access modifiers changed from: private */
    public String mLastRequestedImpressionUrl;
    /* access modifiers changed from: private */
    public OutstreamVideoAd mOutstreamVideoAd;
    /* access modifiers changed from: private */
    public long mVisibleCheckStartTime;
    @VisibleForTesting
    Runnable mDoubleClickAdRefreshRunnable;
    @VisibleForTesting
    Runnable mDoubleClickAdVisibilityCheckRunnable;
    private String mAdId;
    private AdVideoTracker mAdVideoTracker;
    private DirectAdConfigSerializer mDirectAdConfigSerializer;
    private DoubleClickAdConfigSerializer mDoubleClickAdConfigSerializer;
    private IntentLaunchDispatcher mIntentLauncher;
    private boolean mIsAdFresh;
    private OutstreamVideoAdFactory mOutstreamVideoAdFactory;
    private long mProgramId;
    private int mProgramType;
    private VideoProgressPoller mVideoProgressPoller;

    SponsoredProgramControllerHelper(ProgramView v) {
        this(v, ((TvLauncherApplication) v.getContext().getApplicationContext()).getAdsManager(), ((TvLauncherApplication) v.getContext().getApplicationContext()).getOutstreamVideoAdFactory(), ((TvLauncherApplication) v.getContext().getApplicationContext()).getDoubleClickAdConfigSerializer(), ((TvLauncherApplication) v.getContext().getApplicationContext()).getDirectAdConfigSerializer(), new Clock(), ((TvLauncherApplication) v.getContext().getApplicationContext()).getIntentLauncher(), ((TvLauncherApplication) v.getContext().getApplicationContext()).getAdsUtil(), null, null);
    }

    @VisibleForTesting
    SponsoredProgramControllerHelper(ProgramView v, AdsManager adsManager, OutstreamVideoAdFactory outstreamVideoAdFactory, DoubleClickAdConfigSerializer doubleClickAdConfigSerializer, DirectAdConfigSerializer directAdConfigSerializer, Clock clock, IntentLaunchDispatcher intentLauncher, AdsUtil adsUtil, VideoProgressPoller videoProgressPoller, AdVideoTracker adVideoTracker) {
        VideoProgressPoller videoProgressPoller2;
        AdVideoTracker adVideoTracker2;
        this.mVisibleCheckStartTime = -1;
        this.mDoubleClickAdRefreshRunnable = new Runnable() {
            public void run() {
                SponsoredProgramControllerHelper.this.refreshDoubleClickAd();
            }
        };
        this.mDoubleClickAdVisibilityCheckRunnable = new Runnable() {
            public void run() {
                View candidateViewForImpression;
                if (SponsoredProgramControllerHelper.this.mView.getPreviewVideo().getVisibility() == 8) {
                    candidateViewForImpression = SponsoredProgramControllerHelper.this.mView.getPreviewImage();
                } else {
                    candidateViewForImpression = SponsoredProgramControllerHelper.this.mView.getPreviewVideo();
                }
                boolean shouldPostRunnable = true;
                if (!SponsoredProgramControllerHelper.this.mAdsUtil.isViewVisible(candidateViewForImpression)) {
                    long unused = SponsoredProgramControllerHelper.this.mVisibleCheckStartTime = -1;
                } else if (SponsoredProgramControllerHelper.this.mVisibleCheckStartTime == -1) {
                    SponsoredProgramControllerHelper sponsoredProgramControllerHelper = SponsoredProgramControllerHelper.this;
                    long unused2 = sponsoredProgramControllerHelper.mVisibleCheckStartTime = sponsoredProgramControllerHelper.mClock.currentTimeMillis();
                } else if (SponsoredProgramControllerHelper.this.mOutstreamVideoAd == null) {
                    Log.e(SponsoredProgramControllerHelper.TAG, "Outstream video ad is invalid but the runnable is  still running: ");
                    shouldPostRunnable = false;
                } else if (SponsoredProgramControllerHelper.this.mClock.currentTimeMillis() - SponsoredProgramControllerHelper.this.mVisibleCheckStartTime >= 3000) {
                    String newImpressionTrackingUrl = SponsoredProgramControllerHelper.this.mOutstreamVideoAd.getDisplayBannerImpressionTrackingUrls().get(0).getUrl();
                    SponsoredProgramControllerHelper.this.mAdsManager.recordImpression(SponsoredProgramControllerHelper.this.mLastRequestedImpressionUrl, newImpressionTrackingUrl);
                    String unused3 = SponsoredProgramControllerHelper.this.mLastRequestedImpressionUrl = newImpressionTrackingUrl;
                    shouldPostRunnable = false;
                }
                if (shouldPostRunnable) {
                    SponsoredProgramControllerHelper.this.mView.postDelayed(this, 1000);
                }
            }
        };
        this.mView = v;
        this.mPreviewVideo = (InstantVideoView) v.findViewById(C1188R.C1191id.preview_video_view);
        this.mClock = clock;
        this.mAdsManager = adsManager;
        this.mOutstreamVideoAdFactory = outstreamVideoAdFactory;
        this.mDoubleClickAdConfigSerializer = doubleClickAdConfigSerializer;
        this.mDirectAdConfigSerializer = directAdConfigSerializer;
        this.mIntentLauncher = intentLauncher;
        this.mAdsUtil = adsUtil;
        if (videoProgressPoller != null) {
            videoProgressPoller2 = videoProgressPoller;
        } else {
            videoProgressPoller2 = new VideoProgressPoller(this.mPreviewVideo);
        }
        this.mVideoProgressPoller = videoProgressPoller2;
        if (adVideoTracker != null) {
            adVideoTracker2 = adVideoTracker;
        } else {
            adVideoTracker2 = AdVideoTrackerFactory.createAdVideoTracker(v.getContext());
        }
        this.mAdVideoTracker = adVideoTracker2;
    }

    /* access modifiers changed from: package-private */
    public boolean bindDoubleClickAdProgram(@NonNull Program program) {
        this.mProgramId = program.getId();
        this.mProgramType = program.getType();
        this.mAdId = program.getAdId();
        cleanUpOldCallbacksAndTrackers();
        AdConfig.AdAsset adAsset = null;
        if (!TextUtils.isEmpty(this.mAdId)) {
            byte[] adConfigSerialized = program.getAdConfigSerialized();
            if (adConfigSerialized != null) {
                adAsset = this.mDoubleClickAdConfigSerializer.deserialize(adConfigSerialized);
            }
            this.mOutstreamVideoAd = this.mOutstreamVideoAdFactory.createOutstreamVideoAdFromAdAsset(adAsset);
            this.mIsAdFresh = refreshDoubleClickAd();
            if (this.mIsAdFresh) {
                this.mAdVideoTracker.resetTracking(this.mAdId, this.mOutstreamVideoAd.getVideoImpressionTrackingUrls());
                if (this.mOutstreamVideoAd.getDisplayBannerImpressionTrackingUrls().isEmpty() || TextUtils.isEmpty(this.mOutstreamVideoAd.getDisplayBannerImpressionTrackingUrls().get(0).getUrl())) {
                    String valueOf = String.valueOf(this.mOutstreamVideoAd);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                    sb.append("The ad has empty impression URLs: ");
                    sb.append(valueOf);
                    Log.e(TAG, sb.toString());
                } else if (!TextUtils.equals(this.mLastRequestedImpressionUrl, this.mOutstreamVideoAd.getDisplayBannerImpressionTrackingUrls().get(0).getUrl())) {
                    this.mView.post(this.mDoubleClickAdVisibilityCheckRunnable);
                }
            }
        } else {
            this.mOutstreamVideoAd = null;
            this.mIsAdFresh = false;
        }
        if (this.mOutstreamVideoAd != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean bindDirectAdProgram(@NonNull Program program) {
        AdConfig.AdAsset adAsset;
        this.mProgramId = program.getId();
        this.mProgramType = program.getType();
        this.mAdId = null;
        byte[] adConfigSerialized = program.getAdConfigSerialized();
        if (adConfigSerialized != null) {
            adAsset = this.mDirectAdConfigSerializer.deserialize(adConfigSerialized);
        } else {
            adAsset = null;
        }
        if (adAsset == null) {
            this.mOutstreamVideoAd = null;
        } else if (!adAsset.hasDirectAdConfig()) {
            Log.e(TAG, "AdAsset for a DirectAd does not contain any DirectAd configuration.");
            this.mOutstreamVideoAd = null;
        } else {
            this.mOutstreamVideoAd = DirectVideoAd.fromAdAsset(adAsset);
        }
        return this.mOutstreamVideoAd != null;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean refreshDoubleClickAd() {
        long currentTime = this.mClock.currentTimeMillis();
        OutstreamVideoAd outstreamVideoAd = this.mOutstreamVideoAd;
        if (outstreamVideoAd == null || !TextUtils.equals(this.mAdId, outstreamVideoAd.getAdUnitId()) || this.mOutstreamVideoAd.getExpirationMillis() <= currentTime) {
            this.mAdsManager.processAdRequest(this.mProgramId, this.mAdId);
            return false;
        }
        this.mView.removeCallbacks(this.mDoubleClickAdRefreshRunnable);
        this.mView.postDelayed(this.mDoubleClickAdRefreshRunnable, this.mOutstreamVideoAd.getExpirationMillis() - currentTime);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        cleanUpOldCallbacksAndTrackers();
    }

    private void cleanUpOldCallbacksAndTrackers() {
        stopRunnables();
        stopVideoTracking();
    }

    private void stopRunnables() {
        this.mView.removeCallbacks(this.mDoubleClickAdRefreshRunnable);
        this.mView.removeCallbacks(this.mDoubleClickAdVisibilityCheckRunnable);
    }

    /* access modifiers changed from: package-private */
    public String launchMediaIntent(String actionUrl) {
        int i = this.mProgramType;
        if (i == 1001) {
            return launchMediaIntentForDirectAd(actionUrl);
        }
        if (i != 1002) {
            return null;
        }
        return launchMediaIntentForDoubleClickAd();
    }

    /* access modifiers changed from: package-private */
    public boolean recordClickIfDoubleClickAd() {
        OutstreamVideoAd outstreamVideoAd = this.mOutstreamVideoAd;
        if (outstreamVideoAd == null) {
            return false;
        }
        String clickTrackingUrl = outstreamVideoAd.getClickTrackingUrl().getUrl();
        if (TextUtils.isEmpty(clickTrackingUrl)) {
            return false;
        }
        this.mAdsManager.recordClick(clickTrackingUrl);
        return true;
    }

    private String launchMediaIntentForDoubleClickAd() {
        String packageName = null;
        OutstreamVideoAd outstreamVideoAd = this.mOutstreamVideoAd;
        if (outstreamVideoAd != null) {
            packageName = outstreamVideoAd.getPackageName();
        }
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        return this.mIntentLauncher.launchMediaIntentForDoubleClickAd(packageName, this.mOutstreamVideoAd.getDeepLinkUrl(), this.mOutstreamVideoAd.getMarketUrl());
    }

    private String launchMediaIntentForDirectAd(String actionUrl) {
        String dataUrl;
        String packageName = null;
        OutstreamVideoAd outstreamVideoAd = this.mOutstreamVideoAd;
        if (outstreamVideoAd != null) {
            packageName = outstreamVideoAd.getPackageName();
            dataUrl = this.mOutstreamVideoAd.getDeepLinkUrl();
        } else {
            dataUrl = actionUrl;
        }
        if (!TextUtils.isEmpty(packageName) || !TextUtils.isEmpty(dataUrl)) {
            return this.mIntentLauncher.launchMediaIntentForDirectAd(packageName, dataUrl);
        }
        Log.e(TAG, "Error launching direct ad program - both package name and URLs are empty");
        return null;
    }

    /* access modifiers changed from: package-private */
    public void onVideoStarted() {
        if (this.mOutstreamVideoAd == null) {
            throw new IllegalStateException("OutstreamVideoAd is null for a video ad that's about to start playing.");
        } else if (canStartTrackingVideo()) {
            this.mVideoProgressPoller.addVideoProgressUpdateListener(this.mAdVideoTracker);
            this.mVideoProgressPoller.startTracking(this.mOutstreamVideoAd.getVideoDurationMillis());
        }
    }

    public void onVideoEnded() {
        if (canStartTrackingVideo()) {
            this.mVideoProgressPoller.onVideoEnded();
        }
    }

    public void onVideoStopped() {
        if (canStartTrackingVideo()) {
            stopVideoTracking();
        }
    }

    public void onVideoError() {
        if (canStartTrackingVideo()) {
            stopVideoTracking();
        }
    }

    private void stopVideoTracking() {
        if (canStartTrackingVideo()) {
            this.mVideoProgressPoller.onVideoStopped();
            this.mVideoProgressPoller.removeVideoProgressUpdateListener(this.mAdVideoTracker);
        }
    }

    private boolean canStartTrackingVideo() {
        OutstreamVideoAd outstreamVideoAd;
        return this.mIsAdFresh && (outstreamVideoAd = this.mOutstreamVideoAd) != null && outstreamVideoAd.supportsVideoTracking();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public OutstreamVideoAd getOutstreamVideoAd() {
        return this.mOutstreamVideoAd;
    }
}
