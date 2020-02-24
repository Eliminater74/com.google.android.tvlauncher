package com.google.android.tvlauncher.util;

import com.google.android.tvlauncher.util.OemAppBase;

public class OemPromotionApp extends OemAppBase {
    private OemPromotionApp(Builder builder) {
        super(builder);
    }

    public static final class Builder extends OemAppBase.Builder<Builder> {
        public OemPromotionApp build() {
            return new OemPromotionApp(this);
        }
    }
}
