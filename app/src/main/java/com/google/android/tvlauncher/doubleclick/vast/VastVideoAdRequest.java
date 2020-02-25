package com.google.android.tvlauncher.doubleclick.vast;

import android.net.Uri;
import android.util.Size;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.tvlauncher.doubleclick.DoubleClickAdRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VastVideoAdRequest implements DoubleClickAdRequest {
    private static final String DFP_DOMAIN = "pubads.g.doubleclick.net";
    private static final String DFP_PATH = "/gampad/ads";
    private static final String SCHEME_HTTPS = "https";
    private final Uri mDfpRequestUri;

    public VastVideoAdRequest(VastVideoAdTagModel vastVideoAdTagModel) {
        this.mDfpRequestUri = buildAdRequest(vastVideoAdTagModel);
    }

    private static String joinSizes(Set<Size> sizes, char delimtier) {
        StringBuilder sizesBuilder = new StringBuilder();
        Iterator<Size> iterator = sizes.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        Size nextSize = iterator.next();
        sizesBuilder.append(nextSize.getWidth());
        sizesBuilder.append('x');
        sizesBuilder.append(nextSize.getHeight());
        while (iterator.hasNext()) {
            Size nextSize2 = iterator.next();
            sizesBuilder.append(delimtier);
            sizesBuilder.append(nextSize2.getWidth());
            sizesBuilder.append('x');
            sizesBuilder.append(nextSize2.getHeight());
        }
        return sizesBuilder.toString();
    }

    public Uri getDfpRequestUri() {
        return this.mDfpRequestUri;
    }

    private Uri buildAdRequest(VastVideoAdTagModel vastVideoAdTagModel) {
        return new Uri.Builder().scheme(SCHEME_HTTPS).authority(DFP_DOMAIN).path(DFP_PATH).appendQueryParameter("sz", buildSizes(vastVideoAdTagModel.getAdUnitSizes())).appendQueryParameter("ciu_szs", buildCompanionSizes(vastVideoAdTagModel.getCompanionAdSizes())).appendQueryParameter("iu", vastVideoAdTagModel.getAdUnitId()).appendQueryParameter("impl", "s").appendQueryParameter("gdfp_req", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).appendQueryParameter("env", "vp").appendQueryParameter("output", "vast").appendQueryParameter("unviewed_position_start", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).appendQueryParameter("url", vastVideoAdTagModel.getUrl()).appendQueryParameter("description_url", vastVideoAdTagModel.getDescriptionUrl()).appendQueryParameter("correlator", vastVideoAdTagModel.getCorrelator()).appendQueryParameter("cust_params", buildTargettingParameters(vastVideoAdTagModel.getTargeting())).build();
    }

    private String buildSizes(Set<Size> adUnitSizes) {
        String sizes = joinSizes(adUnitSizes, '|');
        if (sizes != null) {
            return sizes;
        }
        throw new IllegalArgumentException("Ad Unit sizes cannot be empty");
    }

    private String buildCompanionSizes(Set<Size> companionSizes) {
        String sizes = joinSizes(companionSizes, ',');
        if (sizes != null) {
            return sizes;
        }
        throw new IllegalArgumentException("Ad companion sizes cannot be empty");
    }

    private String buildTargettingParameters(Map<String, String> targeting) {
        StringBuilder targetingBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = targeting.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, String> nextEntry = iterator.next();
            String str = (String) nextEntry.getKey();
            String str2 = (String) nextEntry.getValue();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
            sb.append(str);
            sb.append("=");
            sb.append(str2);
            targetingBuilder.append(sb.toString());
            while (iterator.hasNext()) {
                Map.Entry<String, String> nextEntry2 = iterator.next();
                targetingBuilder.append("&");
                targetingBuilder.append((String) nextEntry2.getKey());
                targetingBuilder.append("=");
                targetingBuilder.append((String) nextEntry2.getValue());
            }
        }
        return targetingBuilder.toString();
    }
}
