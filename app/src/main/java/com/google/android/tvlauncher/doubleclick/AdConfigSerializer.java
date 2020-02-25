package com.google.android.tvlauncher.doubleclick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;

public interface AdConfigSerializer {
    @Nullable
    AdConfig.AdAsset deserialize(@NonNull byte[] bArr);

    @NonNull
    byte[] serialize(@NonNull AdConfig.AdAsset adAsset);
}
