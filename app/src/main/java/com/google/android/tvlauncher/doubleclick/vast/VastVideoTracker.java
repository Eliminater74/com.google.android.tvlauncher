package com.google.android.tvlauncher.doubleclick.vast;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import com.google.android.tvlauncher.application.TvLauncherApplication;
import com.google.android.tvlauncher.doubleclick.AdVideoTracker;
import com.google.android.tvlauncher.doubleclick.AdsManager;
import com.google.android.tvlauncher.doubleclick.TrackingUrl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

public class VastVideoTracker extends AdVideoTracker {
    private AdsManager mAdsManager;
    private boolean mCanSafelyRemovePreviouslyRecordedVideoImpression;
    private String mCurrentAdId;
    private Set<String> mCurrentlyRecordedVideoImpressions;
    private long mLastTimeMillis;
    private List<TrackingUrl> mVastTrackingUrls;

    public VastVideoTracker(Context context) {
        this(((TvLauncherApplication) context.getApplicationContext()).getAdsManager());
    }

    @VisibleForTesting
    VastVideoTracker(AdsManager adsManager) {
        this.mCurrentlyRecordedVideoImpressions = new HashSet();
        this.mLastTimeMillis = -1;
        this.mAdsManager = adsManager;
    }

    public void resetTracking(String adId, @Nonnull List<TrackingUrl> vastTrackingUrls) {
        this.mCanSafelyRemovePreviouslyRecordedVideoImpression = TextUtils.equals(this.mCurrentAdId, adId) && !vastTrackingUrls.equals(this.mVastTrackingUrls);
        if (this.mCanSafelyRemovePreviouslyRecordedVideoImpression) {
            this.mLastTimeMillis = -1;
        }
        this.mVastTrackingUrls = vastTrackingUrls;
        this.mCurrentAdId = adId;
    }

    public void onVideoProgressUpdate(long currentProgressMillis) {
        List<String> intersectingCues = findCrossedCuesNotPreviouslyImpressioned(this.mLastTimeMillis, currentProgressMillis);
        if (!intersectingCues.isEmpty()) {
            if (this.mCanSafelyRemovePreviouslyRecordedVideoImpression && !this.mCurrentlyRecordedVideoImpressions.isEmpty()) {
                this.mAdsManager.removeRequestedTrackingUrls(this.mCurrentlyRecordedVideoImpressions);
                this.mCanSafelyRemovePreviouslyRecordedVideoImpression = false;
                this.mCurrentlyRecordedVideoImpressions.clear();
            }
            this.mAdsManager.recordImpressionsInBatch(intersectingCues);
            this.mLastTimeMillis = currentProgressMillis;
            this.mCurrentlyRecordedVideoImpressions.addAll(intersectingCues);
        }
    }

    private List<String> findCrossedCuesNotPreviouslyImpressioned(long lastTimeMillis, long currentTimeMillis) {
        List<String> intersectingCues = new ArrayList<>();
        for (TrackingUrl trackingUrl : this.mVastTrackingUrls) {
            if (hasCuePointCrossed(trackingUrl, lastTimeMillis, currentTimeMillis)) {
                intersectingCues.add(trackingUrl.getUrl());
            }
        }
        return intersectingCues;
    }

    private boolean hasCuePointCrossed(TrackingUrl trackingUrl, long lastTimeMillis, long currentTimeMillis) {
        long cueTimeMillis = trackingUrl.getOffsetMillis();
        return cueTimeMillis > lastTimeMillis && cueTimeMillis <= currentTimeMillis;
    }
}
