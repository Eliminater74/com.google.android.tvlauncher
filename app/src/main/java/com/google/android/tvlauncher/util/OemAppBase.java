package com.google.android.tvlauncher.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class OemAppBase {
    private final String mAppName;
    private final String mBannerUri;
    private final String mCategory;
    private final String mDataUri;
    private final String mDescription;
    private final String mDeveloper;
    private final boolean mIsGame;
    private final boolean mIsVirtualApp;
    private final String mPackageName;
    private String mId = constructId();
    private List<String> mScreenshotUris;

    OemAppBase(Builder builder) {
        this.mAppName = builder.mAppName;
        this.mPackageName = builder.mPackageName;
        this.mBannerUri = builder.mBannerUri;
        this.mDataUri = builder.mDataUri;
        this.mDeveloper = builder.mDeveloper;
        this.mCategory = builder.mCategory;
        this.mDescription = builder.mDescription;
        this.mIsGame = builder.mIsGame;
        this.mIsVirtualApp = builder.mIsVirtualApp;
        this.mScreenshotUris = builder.mScreenshotUris;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getBannerUri() {
        return this.mBannerUri;
    }

    public String getDataUri() {
        return this.mDataUri;
    }

    public String getDeveloper() {
        return this.mDeveloper;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getId() {
        return this.mId;
    }

    public boolean isGame() {
        return this.mIsGame;
    }

    public boolean isVirtualApp() {
        return this.mIsVirtualApp;
    }

    public List<String> getScreenshotUris() {
        return Collections.unmodifiableList(this.mScreenshotUris);
    }

    public void addScreenshotUri(String uri) {
        this.mScreenshotUris.add(uri);
    }

    public void addScreenshotUris(List<String> uris) {
        this.mScreenshotUris.addAll(uris);
    }

    private String constructId() {
        if (this.mId == null) {
            this.mId = this.mPackageName;
            if (this.mIsVirtualApp) {
                this.mId = String.valueOf(this.mId).concat(":");
                if (this.mDataUri != null) {
                    String valueOf = String.valueOf(this.mId);
                    String valueOf2 = String.valueOf(this.mDataUri);
                    this.mId = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                }
            }
        }
        return this.mId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof OemAppBase) {
            return TextUtils.equals(getId(), ((OemAppBase) obj).getId());
        }
        return false;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    public static abstract class Builder<T extends Builder<T>> {
        String mAppName;
        String mBannerUri;
        String mCategory;
        String mDataUri;
        String mDescription;
        String mDeveloper;
        boolean mIsGame;
        boolean mIsVirtualApp;
        String mPackageName;
        List<String> mScreenshotUris = new ArrayList();

        public abstract OemAppBase build();

        public T setAppName(String appName) {
            this.mAppName = appName;
            return this;
        }

        public T setPackageName(String packageName) {
            this.mPackageName = packageName;
            return this;
        }

        public T setBannerUri(String bannerUri) {
            this.mBannerUri = bannerUri;
            return this;
        }

        public T setDataUri(String dataUri) {
            this.mDataUri = dataUri;
            return this;
        }

        public T setDeveloper(String developer) {
            this.mDeveloper = developer;
            return this;
        }

        public T setCategory(String category) {
            this.mCategory = category;
            return this;
        }

        public T setDescription(String description) {
            this.mDescription = description;
            return this;
        }

        public T setGame(boolean game) {
            this.mIsGame = game;
            return this;
        }

        public T setVirtualApp(boolean virtualApp) {
            this.mIsVirtualApp = virtualApp;
            return this;
        }

        public T setScreenshotUris(@NonNull List<String> screenshotUris) {
            this.mScreenshotUris = screenshotUris;
            return this;
        }
    }
}
