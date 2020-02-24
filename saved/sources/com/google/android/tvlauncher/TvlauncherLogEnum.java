package com.google.android.tvlauncher;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Internal;

public final class TvlauncherLogEnum {
    private TvlauncherLogEnum() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum TvLauncherEventCode implements Internal.EnumLite {
        OPEN_HOME(1),
        ENTER_ZOOMED_OUT_MODE(2),
        EXIT_ZOOMED_OUT_MODE(3),
        ENTER_CHANNEL_ACTIONS_MODE(4),
        EXIT_CHANNEL_ACTIONS_MODE(5),
        ENTER_MOVE_CHANNEL_MODE(6),
        EXIT_MOVE_CHANNEL_MODE(7),
        START_PROGRAM(8),
        WATCH_PREVIEW(9),
        OPEN_NOTIFICATION(10),
        HIDE_NOTIFICATION(11),
        DISMISS_NOTIFICATION(12),
        OPEN_NOTIFICATION_PANEL(13),
        OPEN_APPS_VIEW(14),
        ENTER_EDIT_APPS_MODE(15),
        EXIT_EDIT_APPS_MODE(16),
        UNINSTALL_APP(17),
        GET_APP_INFO(18),
        FAVORITE_APP(19),
        UNFAVORITE_APP(20),
        OPEN_MANAGE_CHANNELS(21),
        OPEN_MANAGE_APP_CHANNELS(22),
        ADD_CHANNEL(23),
        REMOVE_CHANNEL(24),
        START_APP(25),
        MOVE_CHANNEL_UP(26),
        MOVE_CHANNEL_DOWN(27),
        REMOVE_CHANNEL_FROM_HOME(28),
        ADD_PROGRAM_TO_WATCH_NEXT(29),
        REMOVE_PROGRAM_FROM_WATCH_NEXT(30),
        REMOVE_PROGRAM_FROM_CHANNEL(31),
        START_SETTINGS(32),
        OPEN_INPUTS_VIEW(33),
        SELECT_INPUT(34),
        APPROVE_NEW_CHANNEL(35),
        DENY_NEW_CHANNEL(36),
        CONTENT_METRICS(37),
        DEVICE_STATUS(38),
        APPROVE_ADD_APP_LINK(39),
        DENY_ADD_APP_LINK(40),
        APPROVE_REMOVE_APP_LINK(41),
        DENY_REMOVE_APP_LINK(42),
        MOVE_LAUNCH_ITEM_UP(43),
        MOVE_LAUNCH_ITEM_DOWN(44),
        MOVE_LAUNCH_ITEM_LEFT(45),
        MOVE_LAUNCH_ITEM_RIGHT(46),
        USER_ACTION(47),
        START_VOICE_SEARCH(48),
        START_KEYBOARD_SEARCH(49);
        
        public static final int ADD_CHANNEL_VALUE = 23;
        public static final int ADD_PROGRAM_TO_WATCH_NEXT_VALUE = 29;
        public static final int APPROVE_ADD_APP_LINK_VALUE = 39;
        public static final int APPROVE_NEW_CHANNEL_VALUE = 35;
        public static final int APPROVE_REMOVE_APP_LINK_VALUE = 41;
        public static final int CONTENT_METRICS_VALUE = 37;
        public static final int DENY_ADD_APP_LINK_VALUE = 40;
        public static final int DENY_NEW_CHANNEL_VALUE = 36;
        public static final int DENY_REMOVE_APP_LINK_VALUE = 42;
        public static final int DEVICE_STATUS_VALUE = 38;
        public static final int DISMISS_NOTIFICATION_VALUE = 12;
        public static final int ENTER_CHANNEL_ACTIONS_MODE_VALUE = 4;
        public static final int ENTER_EDIT_APPS_MODE_VALUE = 15;
        public static final int ENTER_MOVE_CHANNEL_MODE_VALUE = 6;
        public static final int ENTER_ZOOMED_OUT_MODE_VALUE = 2;
        public static final int EXIT_CHANNEL_ACTIONS_MODE_VALUE = 5;
        public static final int EXIT_EDIT_APPS_MODE_VALUE = 16;
        public static final int EXIT_MOVE_CHANNEL_MODE_VALUE = 7;
        public static final int EXIT_ZOOMED_OUT_MODE_VALUE = 3;
        public static final int FAVORITE_APP_VALUE = 19;
        public static final int GET_APP_INFO_VALUE = 18;
        public static final int HIDE_NOTIFICATION_VALUE = 11;
        public static final int MOVE_CHANNEL_DOWN_VALUE = 27;
        public static final int MOVE_CHANNEL_UP_VALUE = 26;
        public static final int MOVE_LAUNCH_ITEM_DOWN_VALUE = 44;
        public static final int MOVE_LAUNCH_ITEM_LEFT_VALUE = 45;
        public static final int MOVE_LAUNCH_ITEM_RIGHT_VALUE = 46;
        public static final int MOVE_LAUNCH_ITEM_UP_VALUE = 43;
        public static final int OPEN_APPS_VIEW_VALUE = 14;
        public static final int OPEN_HOME_VALUE = 1;
        public static final int OPEN_INPUTS_VIEW_VALUE = 33;
        public static final int OPEN_MANAGE_APP_CHANNELS_VALUE = 22;
        public static final int OPEN_MANAGE_CHANNELS_VALUE = 21;
        public static final int OPEN_NOTIFICATION_PANEL_VALUE = 13;
        public static final int OPEN_NOTIFICATION_VALUE = 10;
        public static final int REMOVE_CHANNEL_FROM_HOME_VALUE = 28;
        public static final int REMOVE_CHANNEL_VALUE = 24;
        public static final int REMOVE_PROGRAM_FROM_CHANNEL_VALUE = 31;
        public static final int REMOVE_PROGRAM_FROM_WATCH_NEXT_VALUE = 30;
        public static final int SELECT_INPUT_VALUE = 34;
        public static final int START_APP_VALUE = 25;
        public static final int START_KEYBOARD_SEARCH_VALUE = 49;
        public static final int START_PROGRAM_VALUE = 8;
        public static final int START_SETTINGS_VALUE = 32;
        public static final int START_VOICE_SEARCH_VALUE = 48;
        public static final int UNFAVORITE_APP_VALUE = 20;
        public static final int UNINSTALL_APP_VALUE = 17;
        public static final int USER_ACTION_VALUE = 47;
        public static final int WATCH_PREVIEW_VALUE = 9;
        private static final Internal.EnumLiteMap<TvLauncherEventCode> internalValueMap = new Internal.EnumLiteMap<TvLauncherEventCode>() {
            public TvLauncherEventCode findValueByNumber(int number) {
                return TvLauncherEventCode.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static TvLauncherEventCode forNumber(int value2) {
            switch (value2) {
                case 1:
                    return OPEN_HOME;
                case 2:
                    return ENTER_ZOOMED_OUT_MODE;
                case 3:
                    return EXIT_ZOOMED_OUT_MODE;
                case 4:
                    return ENTER_CHANNEL_ACTIONS_MODE;
                case 5:
                    return EXIT_CHANNEL_ACTIONS_MODE;
                case 6:
                    return ENTER_MOVE_CHANNEL_MODE;
                case 7:
                    return EXIT_MOVE_CHANNEL_MODE;
                case 8:
                    return START_PROGRAM;
                case 9:
                    return WATCH_PREVIEW;
                case 10:
                    return OPEN_NOTIFICATION;
                case 11:
                    return HIDE_NOTIFICATION;
                case 12:
                    return DISMISS_NOTIFICATION;
                case 13:
                    return OPEN_NOTIFICATION_PANEL;
                case 14:
                    return OPEN_APPS_VIEW;
                case 15:
                    return ENTER_EDIT_APPS_MODE;
                case 16:
                    return EXIT_EDIT_APPS_MODE;
                case 17:
                    return UNINSTALL_APP;
                case 18:
                    return GET_APP_INFO;
                case 19:
                    return FAVORITE_APP;
                case 20:
                    return UNFAVORITE_APP;
                case 21:
                    return OPEN_MANAGE_CHANNELS;
                case 22:
                    return OPEN_MANAGE_APP_CHANNELS;
                case 23:
                    return ADD_CHANNEL;
                case 24:
                    return REMOVE_CHANNEL;
                case 25:
                    return START_APP;
                case 26:
                    return MOVE_CHANNEL_UP;
                case 27:
                    return MOVE_CHANNEL_DOWN;
                case 28:
                    return REMOVE_CHANNEL_FROM_HOME;
                case 29:
                    return ADD_PROGRAM_TO_WATCH_NEXT;
                case 30:
                    return REMOVE_PROGRAM_FROM_WATCH_NEXT;
                case 31:
                    return REMOVE_PROGRAM_FROM_CHANNEL;
                case 32:
                    return START_SETTINGS;
                case 33:
                    return OPEN_INPUTS_VIEW;
                case 34:
                    return SELECT_INPUT;
                case 35:
                    return APPROVE_NEW_CHANNEL;
                case 36:
                    return DENY_NEW_CHANNEL;
                case 37:
                    return CONTENT_METRICS;
                case 38:
                    return DEVICE_STATUS;
                case 39:
                    return APPROVE_ADD_APP_LINK;
                case 40:
                    return DENY_ADD_APP_LINK;
                case 41:
                    return APPROVE_REMOVE_APP_LINK;
                case 42:
                    return DENY_REMOVE_APP_LINK;
                case 43:
                    return MOVE_LAUNCH_ITEM_UP;
                case 44:
                    return MOVE_LAUNCH_ITEM_DOWN;
                case 45:
                    return MOVE_LAUNCH_ITEM_LEFT;
                case 46:
                    return MOVE_LAUNCH_ITEM_RIGHT;
                case 47:
                    return USER_ACTION;
                case 48:
                    return START_VOICE_SEARCH;
                case 49:
                    return START_KEYBOARD_SEARCH;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<TvLauncherEventCode> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return TvLauncherEventCodeVerifier.INSTANCE;
        }

        private static final class TvLauncherEventCodeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new TvLauncherEventCodeVerifier();

            private TvLauncherEventCodeVerifier() {
            }

            public boolean isInRange(int number) {
                return TvLauncherEventCode.forNumber(number) != null;
            }
        }

        private TvLauncherEventCode(int value2) {
            this.value = value2;
        }
    }
}
