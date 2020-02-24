package com.google.android.tvlauncher.home;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface HomeAppState {
    public static final int CHANNEL_ACTIONS = 6;
    public static final int DEFAULT = 0;
    public static final int DEFAULT_ABOVE_SELECTED_CHANNEL = 1;
    public static final int DEFAULT_SELECTED_CHANNEL = 2;
    public static final int MOVE_CHANNEL = 7;
    public static final int ZOOMED_OUT = 3;
    public static final int ZOOMED_OUT_SELECTED_CHANNEL = 4;
    public static final int ZOOMED_OUT_TOP_ROW_SELECTED = 5;
}
