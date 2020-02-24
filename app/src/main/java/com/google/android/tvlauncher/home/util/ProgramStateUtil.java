package com.google.android.tvlauncher.home.util;

public class ProgramStateUtil {
    public static boolean isZoomedOutState(int state) {
        return state == 5 || state == 6 || state == 7 || state == 8 || state == 9 || state == 10 || state == 11;
    }

    public static String stateToString(int state) {
        String stateString = null;
        switch (state) {
            case 0:
                stateString = "DEFAULT";
                break;
            case 1:
                stateString = "DEFAULT_TOP_ROW_SELECTED";
                break;
            case 2:
                stateString = "DEFAULT_APPS_ROW_SELECTED";
                break;
            case 3:
                stateString = "DEFAULT_SELECTED_CHANNEL";
                break;
            case 4:
                stateString = "DEFAULT_FAST_SCROLLING";
                break;
            case 5:
                stateString = "ZOOMED_OUT";
                break;
            case 6:
                stateString = "ZOOMED_OUT_SELECTED_CHANNEL";
                break;
            case 7:
                stateString = "ZOOMED_OUT_TOP_ROW_SELECTED";
                break;
            case 8:
                stateString = "CHANNEL_ACTIONS";
                break;
            case 9:
                stateString = "CHANNEL_ACTIONS_SELECTED_CHANNEL";
                break;
            case 10:
                stateString = "MOVE_CHANNEL";
                break;
            case 11:
                stateString = "MOVE_CHANNEL_SELECTED_CHANNEL";
                break;
            case 12:
                stateString = "DEFAULT_ABOVE_SELECTED_LAST_ROW";
                break;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(stateString).length() + 14);
        sb.append(stateString);
        sb.append(" (");
        sb.append(state);
        sb.append(")");
        return sb.toString();
    }
}
