package com.google.android.tvlauncher.doubleclick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.tvlauncher.doubleclick.proto.nano.AdConfig;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;

import java.util.Arrays;

public class DoubleClickAdConfigSerializer implements AdConfigSerializer {
    private static final String TAG = "DoubleClickAdConfigSerializer";

    @NonNull
    public byte[] serialize(@NonNull AdConfig.AdAsset adAsset) {
        return MessageNano.toByteArray(adAsset);
    }

    @Nullable
    public AdConfig.AdAsset deserialize(@NonNull byte[] serialized) {
        try {
            return (AdConfig.AdAsset) MessageNano.mergeFrom(new AdConfig.AdAsset(), serialized);
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
