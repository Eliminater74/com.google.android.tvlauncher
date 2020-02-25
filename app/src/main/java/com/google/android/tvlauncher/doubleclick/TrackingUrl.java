package com.google.android.tvlauncher.doubleclick;

import android.text.TextUtils;

import java.util.Objects;

public class TrackingUrl {
    private long mOffsetMillis;
    private String mUrl;

    public TrackingUrl(String url, long offsetMillis) {
        this.mUrl = url;
        this.mOffsetMillis = offsetMillis;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public long getOffsetMillis() {
        return this.mOffsetMillis;
    }

    public String toString() {
        return String.format("TrackingUrl: [url=%s, offsetMillis=%s]", this.mUrl, Long.valueOf(this.mOffsetMillis));
    }

    public int hashCode() {
        return Objects.hash(this.mUrl, Long.valueOf(this.mOffsetMillis));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TrackingUrl)) {
            return false;
        }
        TrackingUrl otherTrackingUrl = (TrackingUrl) obj;
        if (!TextUtils.equals(this.mUrl, otherTrackingUrl.mUrl) || this.mOffsetMillis != otherTrackingUrl.mOffsetMillis) {
            return false;
        }
        return true;
    }
}
