package com.google.android.tvlauncher.home.util;

public class HomeAppStateUtil {
    public static boolean isZoomedOutState(int state) {
        return state == 3 || state == 4 || state == 5 || state == 6 || state == 7;
    }

    public static String stateToString(int state) {
        String stateString;
        switch (state) {
            case 0:
                stateString = "DEFAULT";
                break;
            case 1:
                stateString = "DEFAULT_ABOVE_SELECTED_CHANNEL";
                break;
            case 2:
                stateString = "DEFAULT_SELECTED_CHANNEL";
                break;
            case 3:
                stateString = "ZOOMED_OUT";
                break;
            case 4:
                stateString = "ZOOMED_OUT_SELECTED_CHANNEL";
                break;
            case 5:
                stateString = "ZOOMED_OUT_TOP_ROW_SELECTED";
                break;
            case 6:
                stateString = "CHANNEL_ACTIONS";
                break;
            case 7:
                stateString = "MOVE_CHANNEL";
                break;
            default:
                stateString = "STATE_UNKNOWN";
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
