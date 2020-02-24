package com.google.android.tvlauncher.util;

import android.net.Uri;

public class ChannelConfigContract {
    public static final String CONFIG_AUTHORITY = "tvrecs.config";

    /* access modifiers changed from: private */
    public static Uri buildUri(String path) {
        return new Uri.Builder().scheme("content").authority("tvrecs.config").appendPath(path).build();
    }

    public static final class ColumnNames {
        public static final String CAN_HIDE = "can_hide";
        public static final String CAN_MOVE = "can_move";
        public static final String CHANNEL_POSITION = "position";
        public static final String SPONSORED = "sponsored";
        public static final String SYSTEM_CHANNEL_KEY = "system_channel_key";

        private ColumnNames() {
        }
    }

    public static final class Uris {
        public static final Uri CHANNEL_CONFIG = ChannelConfigContract.buildUri("channel");

        private Uris() {
        }
    }

    public static final class UriResourceNames {
        public static final String CHANNEL_CONFIG = "channel";

        private UriResourceNames() {
        }
    }
}
