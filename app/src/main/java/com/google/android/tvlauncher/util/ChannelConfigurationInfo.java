package com.google.android.tvlauncher.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChannelConfigurationInfo {
    private static final String CAN_HIDE = "canHideChannel";
    private static final String CAN_MOVE = "canMoveChannel";
    private static final String CHANNEL_POSITION = "chanPos";
    private static final String IS_GOOGLE_CONFIG = "isGoogleConfig";
    private static final String IS_SPONSORED = "isSponsored";
    private static final String PACKAGE_NAME = "pkgName";
    private static final String SYSTEM_CHANNEL_KEY = "sysChannelKey";
    private static final String TAG = "ChannelInfo";
    private boolean mCanHide;
    private boolean mCanMove;
    private int mChannelPosition;
    private boolean mIsGoogleConfig;
    private boolean mIsSponsored;
    private String mPackageName;
    private String mSystemChannelKey;

    public ChannelConfigurationInfo(String packageName, String key) {
        this.mCanMove = true;
        this.mCanHide = true;
        this.mPackageName = packageName;
        if (TextUtils.isEmpty(key)) {
            this.mSystemChannelKey = null;
        } else {
            this.mSystemChannelKey = key;
        }
    }

    private ChannelConfigurationInfo(Builder builder) {
        this.mCanMove = true;
        this.mCanHide = true;
        this.mPackageName = builder.mPackageName;
        if (TextUtils.isEmpty(builder.mSystemChannelKey)) {
            this.mSystemChannelKey = null;
        } else {
            this.mSystemChannelKey = builder.mSystemChannelKey;
        }
        this.mChannelPosition = builder.mChannelPosition;
        this.mIsSponsored = builder.mIsSponsored;
        this.mIsGoogleConfig = builder.mIsGoogleConfig;
        this.mCanMove = builder.mCanMove;
        this.mCanHide = builder.mCanHide;
    }

    public static String getUniqueKey(String packageName, String systemChannelKey) {
        if (TextUtils.isEmpty(systemChannelKey)) {
            return packageName;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 1 + String.valueOf(systemChannelKey).length());
        sb.append(packageName);
        sb.append(":");
        sb.append(systemChannelKey);
        return sb.toString();
    }

    static JSONArray toJsonArray(List<ChannelConfigurationInfo> channelConfigurationInfoList) {
        JSONArray jsonArray = new JSONArray();
        for (ChannelConfigurationInfo channelConfigurationInfo : channelConfigurationInfoList) {
            jsonArray.put(toJsonObject(channelConfigurationInfo));
        }
        return jsonArray;
    }

    @NonNull
    static List<Builder> fromJsonArrayString(String jsonArrayStr) {
        List<Builder> results = new ArrayList<>();
        if (!TextUtils.isEmpty(jsonArrayStr)) {
            try {
                JSONArray jsonArray = new JSONArray(jsonArrayStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    results.add(fromJsonObject(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                String valueOf = String.valueOf(jsonArrayStr);
                Log.e(TAG, valueOf.length() != 0 ? "JSONException in fromJson. Could not deserialize from jsonArrayStr: ".concat(valueOf) : new String("JSONException in fromJson. Could not deserialize from jsonArrayStr: "));
            }
        }
        return results;
    }

    private static JSONObject toJsonObject(ChannelConfigurationInfo channelConfigurationInfo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(PACKAGE_NAME, channelConfigurationInfo.getPackageName());
            jsonObject.put(SYSTEM_CHANNEL_KEY, channelConfigurationInfo.getSystemChannelKey());
            jsonObject.put(CHANNEL_POSITION, channelConfigurationInfo.getChannelPosition());
            jsonObject.put(IS_SPONSORED, channelConfigurationInfo.isSponsored());
            jsonObject.put(IS_GOOGLE_CONFIG, channelConfigurationInfo.isGoogleConfig());
            jsonObject.put(CAN_MOVE, channelConfigurationInfo.canMove());
            jsonObject.put(CAN_HIDE, channelConfigurationInfo.canHide());
        } catch (JSONException e) {
            String valueOf = String.valueOf(channelConfigurationInfo);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb.append("Could not serialize ChannelInfo: ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString());
        }
        return jsonObject;
    }

    private static Builder fromJsonObject(JSONObject jsonObject) {
        try {
            String packageName = jsonObject.getString(PACKAGE_NAME);
            String systemChannelKey = jsonObject.optString(SYSTEM_CHANNEL_KEY, null);
            int channelPos = jsonObject.getInt(CHANNEL_POSITION);
            boolean isSponsored = jsonObject.getBoolean(IS_SPONSORED);
            boolean isGoogleConfig = jsonObject.optBoolean(IS_GOOGLE_CONFIG, false);
            boolean canMoveChannel = jsonObject.getBoolean(CAN_MOVE);
            return new Builder().setPackageName(packageName).setSystemChannelKey(systemChannelKey).setChannelPosition(channelPos).setSponsored(isSponsored).setIsGoogleConfig(isGoogleConfig).setCanMove(canMoveChannel).setCanHide(jsonObject.getBoolean(CAN_HIDE));
        } catch (JSONException e) {
            String valueOf = String.valueOf(jsonObject);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("JSONException. Could not deserialize jsonObject: ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString());
            return null;
        }
    }

    static String computeHashCode(@NonNull List<ChannelConfigurationInfo> channelConfigurationList) {
        if (channelConfigurationList.size() == 0) {
            return null;
        }
        try {
            MessageDigest sh1Digest = MessageDigest.getInstance("SHA-1");
            JSONArray jsonArray = toJsonArray(channelConfigurationList);
            if (jsonArray.length() != 0) {
                return new String(sh1Digest.digest(jsonArray.toString().getBytes("UTF-8")));
            }
            throw new RuntimeException("Cannot compute checksum. JsonArray returned 0 values.");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            String valueOf = String.valueOf(channelConfigurationList);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 79);
            sb.append("Exception while computing MessageDigest in computeHashCode for channelConfigs: ");
            sb.append(valueOf);
            throw new RuntimeException(sb.toString(), ex);
        }
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getSystemChannelKey() {
        return this.mSystemChannelKey;
    }

    public int getChannelPosition() {
        return this.mChannelPosition;
    }

    public boolean isSponsored() {
        return this.mIsSponsored;
    }

    public boolean isGoogleConfig() {
        return this.mIsGoogleConfig;
    }

    public boolean canMove() {
        return this.mCanMove;
    }

    public boolean canHide() {
        return this.mCanHide;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mSystemChannelKey);
    }

    public String toString() {
        String str = this.mPackageName;
        String str2 = this.mSystemChannelKey;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append("-");
        sb.append(str2);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ChannelConfigurationInfo)) {
            return false;
        }
        ChannelConfigurationInfo channelConfigurationInfo = (ChannelConfigurationInfo) obj;
        if (!TextUtils.equals(this.mPackageName, channelConfigurationInfo.getPackageName()) || !TextUtils.equals(this.mSystemChannelKey, channelConfigurationInfo.getSystemChannelKey())) {
            return false;
        }
        return true;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean mCanHide = true;
        /* access modifiers changed from: private */
        public boolean mCanMove = true;
        /* access modifiers changed from: private */
        public int mChannelPosition;
        /* access modifiers changed from: private */
        public boolean mIsGoogleConfig;
        /* access modifiers changed from: private */
        public boolean mIsSponsored;
        /* access modifiers changed from: private */
        public String mPackageName;
        /* access modifiers changed from: private */
        public String mSystemChannelKey;

        public String getPackageName() {
            return this.mPackageName;
        }

        public Builder setPackageName(String packageName) {
            this.mPackageName = packageName;
            return this;
        }

        public String getSystemChannelKey() {
            return this.mSystemChannelKey;
        }

        public Builder setSystemChannelKey(String systemChannelKey) {
            this.mSystemChannelKey = systemChannelKey;
            return this;
        }

        public int getChannelPosition() {
            return this.mChannelPosition;
        }

        public Builder setChannelPosition(int channelPosition) {
            this.mChannelPosition = channelPosition;
            return this;
        }

        public boolean isSponsored() {
            return this.mIsSponsored;
        }

        public Builder setSponsored(boolean sponsored) {
            this.mIsSponsored = sponsored;
            return this;
        }

        public Builder setIsGoogleConfig(boolean isGoogleConfig) {
            this.mIsGoogleConfig = isGoogleConfig;
            return this;
        }

        public Builder setCanMove(boolean canMove) {
            this.mCanMove = canMove;
            return this;
        }

        /* access modifiers changed from: package-private */
        public boolean canMove() {
            return this.mCanMove;
        }

        public Builder setCanHide(boolean canHide) {
            this.mCanHide = canHide;
            return this;
        }

        /* access modifiers changed from: package-private */
        public boolean canHide() {
            return this.mCanHide;
        }

        public ChannelConfigurationInfo build() {
            return new ChannelConfigurationInfo(this);
        }
    }
}
