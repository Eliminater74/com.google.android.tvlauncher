package com.google.android.tvlauncher.home;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ProgramState {
    public static final int CHANNEL_ACTIONS = 8;
    public static final int CHANNEL_ACTIONS_SELECTED_CHANNEL = 9;
    public static final int DEFAULT = 0;
    public static final int DEFAULT_ABOVE_SELECTED_LAST_ROW = 12;
    public static final int DEFAULT_APPS_ROW_SELECTED = 2;
    public static final int DEFAULT_FAST_SCROLLING = 4;
    public static final int DEFAULT_SELECTED_CHANNEL = 3;
    public static final int DEFAULT_TOP_ROW_SELECTED = 1;
    public static final int MOVE_CHANNEL = 10;
    public static final int MOVE_CHANNEL_SELECTED_CHANNEL = 11;
    public static final int ZOOMED_OUT = 5;
    public static final int ZOOMED_OUT_SELECTED_CHANNEL = 6;
    public static final int ZOOMED_OUT_TOP_ROW_SELECTED = 7;
}
