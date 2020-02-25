package com.google.android.tvlauncher.doubleclick;

import android.util.Log;

import com.google.android.tvlauncher.doubleclick.customcreative.CustomCreativeVideoAdFactory;
import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;
import com.google.android.tvlauncher.doubleclick.vast.VastVideoAdFactory;

import java.io.InputStream;

import javax.annotation.Nonnull;

public class OutstreamVideoAdFactory {
    private static final String TAG = "OutstreamVideoAdFactory";
    private CustomCreativeVideoAdFactory mCustomCreativeVideoAdFactory = new CustomCreativeVideoAdFactory();
    private VastVideoAdFactory mVastVideoAdFactory = new VastVideoAdFactory();

    public OutstreamVideoAd createOutstreamVideoAdFromAdAsset(AdConfig.AdAsset adAsset) {
        if (adAsset == null || !adAsset.hasDoubleclickAdConfig()) {
            return null;
        }
        AdConfig.DoubleClickAdConfig doubleClickAdConfig = adAsset.getDoubleclickAdConfig();
        if (doubleClickAdConfig.hasCustomCreative()) {
            return this.mCustomCreativeVideoAdFactory.createCustomCreativeVideoAdFromAdAsset(adAsset);
        }
        if (doubleClickAdConfig.hasVast()) {
            return this.mVastVideoAdFactory.createVastVideoAdFromAdAsset(adAsset);
        }
        String valueOf = String.valueOf(adAsset);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 56);
        sb.append("DoubleClickAdConfig does not have any known format set: ");
        sb.append(valueOf);
        Log.e(TAG, sb.toString());
        return null;
    }

    /* access modifiers changed from: package-private */
    public OutstreamVideoAd createOutstreamVideoAdFromAdResponse(String adId, @Nonnull InputStream inputStream) {
        return this.mVastVideoAdFactory.createVastVideoAdFromAdResponse(adId, inputStream);
    }
}
