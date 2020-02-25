package com.google.android.tvlauncher.doubleclick.customcreative;

import android.net.Uri;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.tvlauncher.doubleclick.DoubleClickAdRequest;

public class CustomCreativeAdRequest implements DoubleClickAdRequest {
    public static final String SIZE_1X1 = "1x1";
    private static final String DFP_DOMAIN = "pubads.g.doubleclick.net";
    private static final String DFP_PATH = "/gampad/adx";
    private static final String SCHEME_HTTPS = "https";
    private static final String TEST_ATV_AD_INVENTORY_UNIT = "/21672112449/POC1";
    static final CustomCreativeAdTagModel TEST_DFP_AD_DATA1 = new CustomCreativeAdTagModel.Builder().setCorrelator(String.valueOf(System.currentTimeMillis())).setInventoryUnit(TEST_ATV_AD_INVENTORY_UNIT).setShouldUseDelayedImpression(true).setSize(SIZE_1X1).setTile(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).build();
    private final Uri mDfpRequestUri;

    public CustomCreativeAdRequest(CustomCreativeAdTagModel customCreativeAdTagModel) {
        this.mDfpRequestUri = buildDfpRequestUri(customCreativeAdTagModel);
    }

    private static Uri buildDfpRequestUri(CustomCreativeAdTagModel customCreativeAdTagModel) {
        return new Uri.Builder().scheme(SCHEME_HTTPS).authority(DFP_DOMAIN).path(DFP_PATH).appendQueryParameter("iu", customCreativeAdTagModel.getInventoryUnit()).appendQueryParameter("sz", customCreativeAdTagModel.getSize()).appendQueryParameter("d_imp", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).appendQueryParameter("c", customCreativeAdTagModel.getCorrelator()).appendQueryParameter("tile", customCreativeAdTagModel.getTile()).build();
    }

    public Uri getDfpRequestUri() {
        return this.mDfpRequestUri;
    }
}
