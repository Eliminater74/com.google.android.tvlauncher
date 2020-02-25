package com.google.android.tvlauncher.doubleclick.customcreative;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.google.android.tvlauncher.doubleclick.Clock;
import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class CustomCreativeAdParser {
    static final long AD_VALIDITY_TTL_MS = 3600000;
    private static final String APP_INSTALL_AD_TEMPLATE_ID = "2";
    private static final String CLICK_ACTION_URL_TAG = "clickActionURL";
    private static final String CLICK_TRACKING_URL_TAG = "clickTrackingURL";
    private static final String CONTENT_AD_TEMPLATE_ID = "1";
    private static final String DEEPLINK_URL_TAG = "deeplinkURL";
    private static final String IMAGE_URL_TAG = "imageURL";
    private static final String IMPRESSION_TRACKING_URL_TAG = "impressionTrackingURL";
    private static final String MARKET_URL_TAG = "marketURL";
    private static final String PACKAGE_NAME_TAG = "packageName";
    private static final String TAG = "CustomCreativeAdParser";
    private static final String TEMPLATE_ID = "templateID";
    private static final String VIDEO_URL_TAG = "videoURL";
    private final Clock mClock;

    public CustomCreativeAdParser() {
        this(new SystemClock());
    }

    @VisibleForTesting
    CustomCreativeAdParser(Clock clock) {
        this.mClock = clock;
    }

    public AdConfig.AdAsset parse(String adUnitId, InputStream inputStream) {
        try {
            JSONObject jsonObject = parseJsonFromStream(inputStream);
            String templateId = jsonObject.getString(TEMPLATE_ID);
            AdConfig.AdAsset adAsset = new AdConfig.AdAsset();
            adAsset.expiration = getExpirationTimeInMillis();
            AdConfig.DoubleClickAdConfig doubleClickAdConfig = new AdConfig.DoubleClickAdConfig();
            doubleClickAdConfig.adUnitId = adUnitId;
            AdConfig.CustomCreative customCreative = new AdConfig.CustomCreative();
            customCreative.imageUri = jsonObject.getString(IMAGE_URL_TAG);
            customCreative.videoUri = jsonObject.getString(VIDEO_URL_TAG);
            customCreative.displayBannerImpressionTrackingUrl = jsonObject.getString(IMPRESSION_TRACKING_URL_TAG);
            customCreative.clickTrackingUrl = jsonObject.getString(CLICK_TRACKING_URL_TAG);
            if (APP_INSTALL_AD_TEMPLATE_ID.equals(templateId)) {
                AdConfig.AppInstallCreativeInfo appInstallCreativeInfo = new AdConfig.AppInstallCreativeInfo();
                appInstallCreativeInfo.packageName = jsonObject.getString(PACKAGE_NAME_TAG);
                appInstallCreativeInfo.deeplinkUrl = jsonObject.getString(DEEPLINK_URL_TAG);
                appInstallCreativeInfo.marketUrl = jsonObject.getString(MARKET_URL_TAG);
                customCreative.setAppInstallCreativeInfo(appInstallCreativeInfo);
                doubleClickAdConfig.setCustomCreative(customCreative);
                adAsset.setDoubleclickAdConfig(doubleClickAdConfig);
                return adAsset;
            } else if ("1".equals(templateId)) {
                throw new JSONException("Custom ContentAds are not currently supported");
            } else {
                String valueOf = String.valueOf(jsonObject);
                StringBuilder sb = new StringBuilder(String.valueOf(templateId).length() + 43 + String.valueOf(valueOf).length());
                sb.append("TemplateID: ");
                sb.append(templateId);
                sb.append(" is unknown when parsing json: ");
                sb.append(valueOf);
                throw new JSONException(sb.toString());
            }
        } catch (IOException | JSONException ex) {
            Log.e(TAG, "Problem with CustomCreative ad format parsing..returning null", ex);
            return null;
        }
    }

    private JSONObject parseJsonFromStream(InputStream inputStream) throws IOException, JSONException {
        byte[] bytes = new byte[4096];
        StringBuilder builder = new StringBuilder();
        while (true) {
            int read = inputStream.read(bytes);
            int numReads = read;
            if (read <= 0) {
                return new JSONObject(builder.toString());
            }
            builder.append(new String(bytes, 0, numReads));
        }
    }

    private long getExpirationTimeInMillis() {
        return this.mClock.getCurrentTimeMillis() + AD_VALIDITY_TTL_MS;
    }

    private static class SystemClock implements Clock {
        private SystemClock() {
        }

        public long getCurrentTimeMillis() {
            return System.currentTimeMillis();
        }

        public void sleep(long durationMillis) {
        }
    }
}
