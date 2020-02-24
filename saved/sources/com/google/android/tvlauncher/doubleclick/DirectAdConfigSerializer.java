package com.google.android.tvlauncher.doubleclick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.Arrays;

public class DirectAdConfigSerializer implements AdConfigSerializer {
    private static final String TAG = "DirectAdConfigSerializer";

    @NonNull
    public byte[] serialize(@NonNull AdConfig.AdAsset adAsset) {
        return new byte[0];
    }

    @Nullable
    public AdConfig.AdAsset deserialize(@NonNull byte[] serialized) {
        try {
            AdConfig.AdAsset adAsset = new AdConfig.AdAsset();
            adAsset.setDirectAdConfig((AdConfig.DirectAdConfig) MessageNano.mergeFrom(new AdConfig.DirectAdConfig(), serialized));
            return adAsset;
        } catch (InvalidProtocolBufferNanoException ex) {
            String arrays = Arrays.toString(serialized);
            StringBuilder sb = new StringBuilder(String.valueOf(arrays).length() + 47);
            sb.append("Could not deserialize: ");
            sb.append(arrays);
            sb.append(" into AdConfig.AdAsset: ");
            Log.e(TAG, sb.toString(), ex);
            return null;
        }
    }
}
