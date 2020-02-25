package com.google.android.tvlauncher.doubleclick;

import android.util.Size;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.tvlauncher.doubleclick.customcreative.CustomCreativeAdRequest;
import com.google.android.tvlauncher.doubleclick.customcreative.CustomCreativeAdTagModel;
import com.google.android.tvlauncher.doubleclick.vast.VastVideoAdRequest;
import com.google.android.tvlauncher.doubleclick.vast.VastVideoAdTagModel;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class DoubleClickAdRequestFactory {
    private static final int VAST_AD_UNIT_HEIGHT = 504;
    private static final Set<Size> VAST_AD_UNIT_SIZES = new HashSet(Collections.singletonList(new Size(ClientAnalytics.LogRequest.LogSource.EASTWORLD_STATS_VALUE, ClientAnalytics.LogRequest.LogSource.SESAME_CAMERA_LAUNCH_VALUE)));
    private static final int VAST_AD_UNIT_WIDTH = 896;
    private static final int VAST_COMPANION_HEIGHT = 504;
    private static final Set<Size> VAST_COMPANION_SIZES = new HashSet(Collections.singletonList(new Size(ClientAnalytics.LogRequest.LogSource.EASTWORLD_STATS_VALUE, ClientAnalytics.LogRequest.LogSource.SESAME_CAMERA_LAUNCH_VALUE)));
    private static final int VAST_COMPANION_WIDTH = 896;
    private static final String VAST_DESCRIPTION_URL = "www.google.com";
    private static final Map<String, String> VAST_TARGETING = new HashMap();
    private static final String VAST_TARGETING_KEY = "vast_example";
    private static final String VAST_TARGETING_VALUE = "companion,parameter";
    private static final String VAST_URL = "www.google.com";

    static {
        VAST_TARGETING.put(VAST_TARGETING_KEY, VAST_TARGETING_VALUE);
    }

    DoubleClickAdRequestFactory() {
    }

    /* access modifiers changed from: package-private */
    public DoubleClickAdRequest createCustomCreativeAdRequest(String adUnitId) {
        return new CustomCreativeAdRequest(new CustomCreativeAdTagModel.Builder().setCorrelator(String.valueOf(System.currentTimeMillis())).setInventoryUnit(adUnitId).setShouldUseDelayedImpression(true).setSize(CustomCreativeAdRequest.SIZE_1X1).setTile(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).build());
    }

    /* access modifiers changed from: package-private */
    public DoubleClickAdRequest createVastVideoAdRequest(String adUnitId) {
        return new VastVideoAdRequest(new VastVideoAdTagModel.Builder().setAdUnitId(adUnitId).setUrl("www.google.com").setDescriptionUrl("www.google.com").setAdUnitSizes(VAST_AD_UNIT_SIZES).setCompanionAdSizes(VAST_COMPANION_SIZES).setCorrelator(String.valueOf(System.currentTimeMillis())).setTargeting(VAST_TARGETING).build());
    }
}
