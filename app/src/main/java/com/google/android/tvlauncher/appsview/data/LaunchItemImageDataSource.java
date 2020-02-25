package com.google.android.tvlauncher.appsview.data;

import com.google.android.tvlauncher.appsview.LaunchItem;

import java.util.Locale;
import java.util.Objects;

public final class LaunchItemImageDataSource extends PackageImageDataSource {
    private final LaunchItem mLaunchItem;

    public LaunchItemImageDataSource(LaunchItem item, PackageImageDataSource.ImageType imageType, Locale locale) {
        super(item.getPackageName(), item.getResolveInfo(), imageType, locale);
        this.mLaunchItem = item;
    }

    public LaunchItem getLaunchItem() {
        return this.mLaunchItem;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LaunchItemImageDataSource)) {
            return false;
        }
        LaunchItemImageDataSource source = (LaunchItemImageDataSource) obj;
        if (!this.mLaunchItem.equals(source.getLaunchItem()) || !getImageType().equals(source.getImageType()) || this.mLaunchItem.isInstalling() != source.getLaunchItem().isInstalling() || !getLocale().equals(source.getLocale()) || this.mLaunchItem.getLastUpdateTime() != source.getLaunchItem().getLastUpdateTime()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.mLaunchItem, getImageType(), Boolean.valueOf(this.mLaunchItem.isInstalling()), getLocale(), Long.valueOf(this.mLaunchItem.getLastUpdateTime()));
    }

    public String toString() {
        String launchItem = this.mLaunchItem.toString();
        String valueOf = String.valueOf(getImageType());
        boolean isInstalling = this.mLaunchItem.isInstalling();
        String valueOf2 = String.valueOf(getLocale());
        StringBuilder sb = new StringBuilder(String.valueOf(launchItem).length() + 48 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
        sb.append(launchItem);
        sb.append(", Image Type: ");
        sb.append(valueOf);
        sb.append(", Is Installing : ");
        sb.append(isInstalling);
        sb.append(", Locale : ");
        sb.append(valueOf2);
        return sb.toString();
    }
}
