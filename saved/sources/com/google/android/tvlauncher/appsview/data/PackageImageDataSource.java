package com.google.android.tvlauncher.appsview.data;

import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.util.Locale;
import java.util.Objects;

public class PackageImageDataSource {
    private final ImageType mImageType;
    private final Locale mLocale;
    private final String mPackageName;
    private final ResolveInfo mResolveInfo;

    public enum ImageType {
        ICON,
        BANNER
    }

    public PackageImageDataSource(String packageName, ResolveInfo resolveInfo, ImageType imageType, Locale locale) {
        this.mPackageName = packageName;
        this.mResolveInfo = resolveInfo;
        this.mImageType = imageType;
        this.mLocale = locale;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public ImageType getImageType() {
        return this.mImageType;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PackageImageDataSource)) {
            return false;
        }
        PackageImageDataSource source = (PackageImageDataSource) obj;
        if (!TextUtils.equals(this.mPackageName, source.getPackageName()) || !this.mImageType.equals(source.getImageType()) || !this.mLocale.equals(source.getLocale())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mImageType, this.mLocale);
    }

    public String toString() {
        String str = this.mPackageName;
        String valueOf = String.valueOf(this.mImageType);
        String valueOf2 = String.valueOf(getLocale());
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 25 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
        sb.append(str);
        sb.append(", Image Type: ");
        sb.append(valueOf);
        sb.append(", Locale : ");
        sb.append(valueOf2);
        return sb.toString();
    }
}
