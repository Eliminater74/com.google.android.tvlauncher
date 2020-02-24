package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.Context;
import com.google.android.gsf.GoogleSettingsContract;

public abstract class GservicesValue<T> {
    /* access modifiers changed from: private */
    public static ContentResolver sContentResolver = null;
    protected final T mDefaultValue;
    protected final String mKey;

    /* access modifiers changed from: protected */
    public abstract T retrieve(String str);

    public static void init(Context context) {
        sContentResolver = context.getContentResolver();
    }

    protected GservicesValue(String key, T defaultValue) {
        this.mKey = key;
        this.mDefaultValue = defaultValue;
    }

    public final T get() {
        return retrieve(this.mKey);
    }

    public static GservicesValue<Boolean> value(String key, boolean defaultValue) {
        return new GservicesValue<Boolean>(key, Boolean.valueOf(defaultValue)) {
            /* access modifiers changed from: protected */
            public Boolean retrieve(String key) {
                return Boolean.valueOf(Gservices.getBoolean(GservicesValue.sContentResolver, this.mKey, ((Boolean) this.mDefaultValue).booleanValue()));
            }
        };
    }

    public static GservicesValue<Long> value(String key, Long defaultValue) {
        return new GservicesValue<Long>(key, defaultValue) {
            /* access modifiers changed from: protected */
            public Long retrieve(String key) {
                return Long.valueOf(Gservices.getLong(GservicesValue.sContentResolver, this.mKey, ((Long) this.mDefaultValue).longValue()));
            }
        };
    }

    public static GservicesValue<Integer> value(String key, Integer defaultValue) {
        return new GservicesValue<Integer>(key, defaultValue) {
            /* access modifiers changed from: protected */
            public Integer retrieve(String key) {
                return Integer.valueOf(Gservices.getInt(GservicesValue.sContentResolver, this.mKey, ((Integer) this.mDefaultValue).intValue()));
            }
        };
    }

    public static GservicesValue<Float> value(String key, Float defaultValue) {
        return new GservicesValue<Float>(key, defaultValue) {
            /* access modifiers changed from: protected */
            public Float retrieve(String key) {
                return Float.valueOf(Gservices.getFloat(GservicesValue.sContentResolver, this.mKey, ((Float) this.mDefaultValue).floatValue()));
            }
        };
    }

    public static GservicesValue<String> value(String key, String defaultValue) {
        return new GservicesValue<String>(key, defaultValue) {
            /* access modifiers changed from: protected */
            public String retrieve(String key) {
                return Gservices.getString(GservicesValue.sContentResolver, this.mKey, (String) this.mDefaultValue);
            }
        };
    }

    public static GservicesValue<String> partnerSetting(String key, String defaultValue) {
        return new GservicesValue<String>(key, defaultValue) {
            /* access modifiers changed from: protected */
            public String retrieve(String key) {
                return GoogleSettingsContract.Partner.getString(GservicesValue.sContentResolver, this.mKey, (String) this.mDefaultValue);
            }
        };
    }
}
