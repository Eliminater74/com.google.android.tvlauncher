package androidx.tvprovider.media.p005tv;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* renamed from: androidx.tvprovider.media.tv.TvContractCompat */
public final class TvContractCompat {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String ACTION_CHANNEL_BROWSABLE_REQUESTED = "android.media.tv.action.CHANNEL_BROWSABLE_REQUESTED";
    public static final String ACTION_INITIALIZE_PROGRAMS = "android.media.tv.action.INITIALIZE_PROGRAMS";
    public static final String ACTION_PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT = "android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT";
    public static final String ACTION_PREVIEW_PROGRAM_BROWSABLE_DISABLED = "android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED";
    public static final String ACTION_REQUEST_CHANNEL_BROWSABLE = "android.media.tv.action.REQUEST_CHANNEL_BROWSABLE";
    public static final String ACTION_WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED = "android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED";
    public static final String AUTHORITY = "android.media.tv";
    public static final String EXTRA_CHANNEL_ID = "android.media.tv.extra.CHANNEL_ID";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String EXTRA_COLUMN_NAME = "android.media.tv.extra.COLUMN_NAME";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String EXTRA_DATA_TYPE = "android.media.tv.extra.DATA_TYPE";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String EXTRA_DEFAULT_VALUE = "android.media.tv.extra.DEFAULT_VALUE";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String EXTRA_EXISTING_COLUMN_NAMES = "android.media.tv.extra.EXISTING_COLUMN_NAMES";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String EXTRA_PACKAGE_NAME = "android.media.tv.extra.PACKAGE_NAME";
    public static final String EXTRA_PREVIEW_PROGRAM_ID = "android.media.tv.extra.PREVIEW_PROGRAM_ID";
    public static final String EXTRA_WATCH_NEXT_PROGRAM_ID = "android.media.tv.extra.WATCH_NEXT_PROGRAM_ID";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String METHOD_ADD_COLUMN = "add_column";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String METHOD_GET_COLUMNS = "get_columns";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARAM_BROWSABLE_ONLY = "browsable_only";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARAM_CANONICAL_GENRE = "canonical_genre";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARAM_CHANNEL = "channel";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARAM_END_TIME = "end_time";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARAM_INPUT = "input";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PARAM_START_TIME = "start_time";
    private static final String PATH_CHANNEL = "channel";
    private static final String PATH_PASSTHROUGH = "passthrough";
    private static final String PATH_PREVIEW_PROGRAM = "preview_program";
    private static final String PATH_PROGRAM = "program";
    private static final String PATH_RECORDED_PROGRAM = "recorded_program";
    private static final String PATH_WATCH_NEXT_PROGRAM = "watch_next_program";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String PERMISSION_READ_TV_LISTINGS = "android.permission.READ_TV_LISTINGS";

    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$BaseTvColumns */
    public interface BaseTvColumns extends BaseColumns {
        public static final String COLUMN_PACKAGE_NAME = "package_name";
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$PreviewProgramColumns */
    public interface PreviewProgramColumns {
        public static final int ASPECT_RATIO_16_9 = 0;
        public static final int ASPECT_RATIO_1_1 = 3;
        public static final int ASPECT_RATIO_2_3 = 4;
        public static final int ASPECT_RATIO_3_2 = 1;
        public static final int ASPECT_RATIO_4_3 = 2;
        public static final int ASPECT_RATIO_MOVIE_POSTER = 5;
        public static final int AVAILABILITY_AVAILABLE = 0;
        public static final int AVAILABILITY_FREE = 4;
        public static final int AVAILABILITY_FREE_WITH_SUBSCRIPTION = 1;
        public static final int AVAILABILITY_PAID_CONTENT = 2;
        public static final int AVAILABILITY_PURCHASED = 3;
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_AVAILABILITY = "availability";
        public static final String COLUMN_BROWSABLE = "browsable";
        public static final String COLUMN_CONTENT_ID = "content_id";
        public static final String COLUMN_DURATION_MILLIS = "duration_millis";
        public static final String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_INTENT_URI = "intent_uri";
        public static final String COLUMN_INTERACTION_COUNT = "interaction_count";
        public static final String COLUMN_INTERACTION_TYPE = "interaction_type";
        public static final String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
        public static final String COLUMN_ITEM_COUNT = "item_count";
        public static final String COLUMN_LAST_PLAYBACK_POSITION_MILLIS = "last_playback_position_millis";
        public static final String COLUMN_LIVE = "live";
        public static final String COLUMN_LOGO_CONTENT_DESCRIPTION = "logo_content_description";
        public static final String COLUMN_LOGO_URI = "logo_uri";
        public static final String COLUMN_OFFER_PRICE = "offer_price";
        public static final String COLUMN_POSTER_ART_ASPECT_RATIO = "poster_art_aspect_ratio";
        public static final String COLUMN_PREVIEW_AUDIO_URI = "preview_audio_uri";
        public static final String COLUMN_PREVIEW_VIDEO_URI = "preview_video_uri";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_STARTING_PRICE = "starting_price";
        public static final String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final String COLUMN_THUMBNAIL_ASPECT_RATIO = "poster_thumbnail_aspect_ratio";
        public static final String COLUMN_TRANSIENT = "transient";
        public static final String COLUMN_TV_SERIES_ITEM_TYPE = "tv_series_item_type";
        public static final String COLUMN_TYPE = "type";
        public static final int INTERACTION_TYPE_FANS = 3;
        public static final int INTERACTION_TYPE_FOLLOWERS = 2;
        public static final int INTERACTION_TYPE_LIKES = 4;
        public static final int INTERACTION_TYPE_LISTENS = 1;
        public static final int INTERACTION_TYPE_THUMBS = 5;
        public static final int INTERACTION_TYPE_VIEWERS = 6;
        public static final int INTERACTION_TYPE_VIEWS = 0;
        public static final int TV_SERIES_ITEM_TYPE_CHAPTER = 1;
        public static final int TV_SERIES_ITEM_TYPE_EPISODE = 0;
        public static final int TYPE_ALBUM = 8;
        public static final int TYPE_ARTIST = 9;
        public static final int TYPE_CHANNEL = 6;
        public static final int TYPE_CLIP = 4;
        public static final int TYPE_EVENT = 5;
        public static final int TYPE_GAME = 12;
        public static final int TYPE_MOVIE = 0;
        public static final int TYPE_PLAYLIST = 10;
        public static final int TYPE_STATION = 11;
        public static final int TYPE_TRACK = 7;
        public static final int TYPE_TV_EPISODE = 3;
        public static final int TYPE_TV_SEASON = 2;
        public static final int TYPE_TV_SERIES = 1;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$ProgramColumns */
    interface ProgramColumns {
        public static final String COLUMN_AUDIO_LANGUAGE = "audio_language";
        public static final String COLUMN_CANONICAL_GENRE = "canonical_genre";
        public static final String COLUMN_CONTENT_RATING = "content_rating";
        public static final String COLUMN_EPISODE_DISPLAY_NUMBER = "episode_display_number";
        public static final String COLUMN_EPISODE_TITLE = "episode_title";
        public static final String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
        public static final String COLUMN_LONG_DESCRIPTION = "long_description";
        public static final String COLUMN_POSTER_ART_URI = "poster_art_uri";
        public static final String COLUMN_REVIEW_RATING = "review_rating";
        public static final String COLUMN_REVIEW_RATING_STYLE = "review_rating_style";
        public static final String COLUMN_SEARCHABLE = "searchable";
        public static final String COLUMN_SEASON_DISPLAY_NUMBER = "season_display_number";
        public static final String COLUMN_SEASON_TITLE = "season_title";
        public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
        public static final String COLUMN_THUMBNAIL_URI = "thumbnail_uri";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VERSION_NUMBER = "version_number";
        public static final String COLUMN_VIDEO_HEIGHT = "video_height";
        public static final String COLUMN_VIDEO_WIDTH = "video_width";
        public static final int REVIEW_RATING_STYLE_PERCENTAGE = 2;
        public static final int REVIEW_RATING_STYLE_STARS = 0;
        public static final int REVIEW_RATING_STYLE_THUMBS_UP_DOWN = 1;
    }

    public static String buildInputId(ComponentName name) {
        return TvContract.buildInputId(name);
    }

    public static Uri buildChannelUri(long channelId) {
        return TvContract.buildChannelUri(channelId);
    }

    public static Uri buildChannelUriForPassthroughInput(String inputId) {
        return TvContract.buildChannelUriForPassthroughInput(inputId);
    }

    public static Uri buildChannelLogoUri(long channelId) {
        return TvContract.buildChannelLogoUri(channelId);
    }

    public static Uri buildChannelLogoUri(Uri channelUri) {
        return TvContract.buildChannelLogoUri(channelUri);
    }

    public static Uri buildChannelsUriForInput(@Nullable String inputId) {
        return TvContract.buildChannelsUriForInput(inputId);
    }

    public static Uri buildProgramUri(long programId) {
        return TvContract.buildProgramUri(programId);
    }

    public static Uri buildProgramsUriForChannel(long channelId) {
        return TvContract.buildProgramsUriForChannel(channelId);
    }

    public static Uri buildProgramsUriForChannel(Uri channelUri) {
        return TvContract.buildProgramsUriForChannel(channelUri);
    }

    public static Uri buildProgramsUriForChannel(long channelId, long startTime, long endTime) {
        return TvContract.buildProgramsUriForChannel(channelId, startTime, endTime);
    }

    public static Uri buildProgramsUriForChannel(Uri channelUri, long startTime, long endTime) {
        return TvContract.buildProgramsUriForChannel(channelUri, startTime, endTime);
    }

    public static Uri buildRecordedProgramUri(long recordedProgramId) {
        if (Build.VERSION.SDK_INT >= 24) {
            return TvContract.buildRecordedProgramUri(recordedProgramId);
        }
        return ContentUris.withAppendedId(RecordedPrograms.CONTENT_URI, recordedProgramId);
    }

    public static Uri buildPreviewProgramUri(long previewProgramId) {
        return ContentUris.withAppendedId(PreviewPrograms.CONTENT_URI, previewProgramId);
    }

    public static Uri buildPreviewProgramsUriForChannel(long channelId) {
        return PreviewPrograms.CONTENT_URI.buildUpon().appendQueryParameter("channel", String.valueOf(channelId)).build();
    }

    public static Uri buildPreviewProgramsUriForChannel(Uri channelUri) {
        if (isChannelUriForTunerInput(channelUri)) {
            return buildPreviewProgramsUriForChannel(ContentUris.parseId(channelUri));
        }
        throw new IllegalArgumentException("Not a channel: " + channelUri);
    }

    public static Uri buildWatchNextProgramUri(long watchNextProgramId) {
        return ContentUris.withAppendedId(WatchNextPrograms.CONTENT_URI, watchNextProgramId);
    }

    private static boolean isTvUri(Uri uri) {
        return uri != null && "content".equals(uri.getScheme()) && AUTHORITY.equals(uri.getAuthority());
    }

    private static boolean isTwoSegmentUriStartingWith(Uri uri, String pathSegment) {
        List<String> pathSegments = uri.getPathSegments();
        return pathSegments.size() == 2 && pathSegment.equals(pathSegments.get(0));
    }

    public static boolean isChannelUri(Uri uri) {
        if (Build.VERSION.SDK_INT >= 24) {
            return TvContract.isChannelUri(uri);
        }
        return isChannelUriForTunerInput(uri) || isChannelUriForPassthroughInput(uri);
    }

    public static boolean isChannelUriForTunerInput(Uri uri) {
        if (Build.VERSION.SDK_INT >= 24) {
            return TvContract.isChannelUriForTunerInput(uri);
        }
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, "channel");
    }

    public static boolean isChannelUriForPassthroughInput(Uri uri) {
        if (Build.VERSION.SDK_INT >= 24) {
            return TvContract.isChannelUriForPassthroughInput(uri);
        }
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_PASSTHROUGH);
    }

    public static boolean isProgramUri(Uri uri) {
        if (Build.VERSION.SDK_INT >= 24) {
            return TvContract.isProgramUri(uri);
        }
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_PROGRAM);
    }

    public static boolean isRecordedProgramUri(Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_RECORDED_PROGRAM);
    }

    public static void requestChannelBrowsable(Context context, long channelId) {
        if (Build.VERSION.SDK_INT >= 26) {
            TvContract.requestChannelBrowsable(context, channelId);
        }
    }

    private TvContractCompat() {
    }

    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Channels */
    public static final class Channels implements BaseTvColumns {
        public static final String COLUMN_APP_LINK_COLOR = "app_link_color";
        public static final String COLUMN_APP_LINK_ICON_URI = "app_link_icon_uri";
        public static final String COLUMN_APP_LINK_INTENT_URI = "app_link_intent_uri";
        public static final String COLUMN_APP_LINK_POSTER_ART_URI = "app_link_poster_art_uri";
        public static final String COLUMN_APP_LINK_TEXT = "app_link_text";
        public static final String COLUMN_BROWSABLE = "browsable";
        public static final String COLUMN_CONFIGURATION_DISPLAY_ORDER = "configuration_display_order";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DISPLAY_NAME = "display_name";
        public static final String COLUMN_DISPLAY_NUMBER = "display_number";
        public static final String COLUMN_INPUT_ID = "input_id";
        public static final String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
        public static final String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
        public static final String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
        public static final String COLUMN_LOCKED = "locked";
        public static final String COLUMN_NETWORK_AFFILIATION = "network_affiliation";
        public static final String COLUMN_ORIGINAL_NETWORK_ID = "original_network_id";
        public static final String COLUMN_SEARCHABLE = "searchable";
        public static final String COLUMN_SERVICE_ID = "service_id";
        public static final String COLUMN_SERVICE_TYPE = "service_type";
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static final String COLUMN_SYSTEM_APPROVED = "system_approved";
        public static final String COLUMN_SYSTEM_CHANNEL_KEY = "system_channel_key";
        public static final String COLUMN_TRANSIENT = "transient";
        public static final String COLUMN_TRANSPORT_STREAM_ID = "transport_stream_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_VERSION_NUMBER = "version_number";
        public static final String COLUMN_VIDEO_FORMAT = "video_format";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/channel";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/channel";
        public static final Uri CONTENT_URI = Uri.parse("content://android.media.tv/channel");
        public static final String SERVICE_TYPE_AUDIO = "SERVICE_TYPE_AUDIO";
        public static final String SERVICE_TYPE_AUDIO_VIDEO = "SERVICE_TYPE_AUDIO_VIDEO";
        public static final String SERVICE_TYPE_OTHER = "SERVICE_TYPE_OTHER";
        public static final String TYPE_1SEG = "TYPE_1SEG";
        public static final String TYPE_ATSC_C = "TYPE_ATSC_C";
        public static final String TYPE_ATSC_M_H = "TYPE_ATSC_M_H";
        public static final String TYPE_ATSC_T = "TYPE_ATSC_T";
        public static final String TYPE_CMMB = "TYPE_CMMB";
        public static final String TYPE_DTMB = "TYPE_DTMB";
        public static final String TYPE_DVB_C = "TYPE_DVB_C";
        public static final String TYPE_DVB_C2 = "TYPE_DVB_C2";
        public static final String TYPE_DVB_H = "TYPE_DVB_H";
        public static final String TYPE_DVB_S = "TYPE_DVB_S";
        public static final String TYPE_DVB_S2 = "TYPE_DVB_S2";
        public static final String TYPE_DVB_SH = "TYPE_DVB_SH";
        public static final String TYPE_DVB_T = "TYPE_DVB_T";
        public static final String TYPE_DVB_T2 = "TYPE_DVB_T2";
        public static final String TYPE_ISDB_C = "TYPE_ISDB_C";
        public static final String TYPE_ISDB_S = "TYPE_ISDB_S";
        public static final String TYPE_ISDB_T = "TYPE_ISDB_T";
        public static final String TYPE_ISDB_TB = "TYPE_ISDB_TB";
        public static final String TYPE_NTSC = "TYPE_NTSC";
        public static final String TYPE_OTHER = "TYPE_OTHER";
        public static final String TYPE_PAL = "TYPE_PAL";
        public static final String TYPE_PREVIEW = "TYPE_PREVIEW";
        public static final String TYPE_SECAM = "TYPE_SECAM";
        public static final String TYPE_S_DMB = "TYPE_S_DMB";
        public static final String TYPE_T_DMB = "TYPE_T_DMB";
        public static final String VIDEO_FORMAT_1080I = "VIDEO_FORMAT_1080I";
        public static final String VIDEO_FORMAT_1080P = "VIDEO_FORMAT_1080P";
        public static final String VIDEO_FORMAT_2160P = "VIDEO_FORMAT_2160P";
        public static final String VIDEO_FORMAT_240P = "VIDEO_FORMAT_240P";
        public static final String VIDEO_FORMAT_360P = "VIDEO_FORMAT_360P";
        public static final String VIDEO_FORMAT_4320P = "VIDEO_FORMAT_4320P";
        public static final String VIDEO_FORMAT_480I = "VIDEO_FORMAT_480I";
        public static final String VIDEO_FORMAT_480P = "VIDEO_FORMAT_480P";
        public static final String VIDEO_FORMAT_576I = "VIDEO_FORMAT_576I";
        public static final String VIDEO_FORMAT_576P = "VIDEO_FORMAT_576P";
        public static final String VIDEO_FORMAT_720P = "VIDEO_FORMAT_720P";
        private static final Map<String, String> VIDEO_FORMAT_TO_RESOLUTION_MAP = new HashMap();
        public static final String VIDEO_RESOLUTION_ED = "VIDEO_RESOLUTION_ED";
        public static final String VIDEO_RESOLUTION_FHD = "VIDEO_RESOLUTION_FHD";
        public static final String VIDEO_RESOLUTION_HD = "VIDEO_RESOLUTION_HD";
        public static final String VIDEO_RESOLUTION_SD = "VIDEO_RESOLUTION_SD";
        public static final String VIDEO_RESOLUTION_UHD = "VIDEO_RESOLUTION_UHD";

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Channels$ServiceType */
        public @interface ServiceType {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Channels$Type */
        public @interface Type {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Channels$VideoFormat */
        public @interface VideoFormat {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Channels$VideoResolution */
        public @interface VideoResolution {
        }

        static {
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_480I, VIDEO_RESOLUTION_SD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_480P, VIDEO_RESOLUTION_ED);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_576I, VIDEO_RESOLUTION_SD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_576P, VIDEO_RESOLUTION_ED);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_720P, VIDEO_RESOLUTION_HD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_1080I, VIDEO_RESOLUTION_HD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_1080P, VIDEO_RESOLUTION_FHD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_2160P, VIDEO_RESOLUTION_UHD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_4320P, VIDEO_RESOLUTION_UHD);
        }

        @Nullable
        public static String getVideoResolution(String videoFormat) {
            return VIDEO_FORMAT_TO_RESOLUTION_MAP.get(videoFormat);
        }

        private Channels() {
        }

        /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Channels$Logo */
        public static final class Logo {
            public static final String CONTENT_DIRECTORY = "logo";

            private Logo() {
            }
        }
    }

    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Programs */
    public static final class Programs implements BaseTvColumns, ProgramColumns {
        public static final String COLUMN_BROADCAST_GENRE = "broadcast_genre";
        public static final String COLUMN_CHANNEL_ID = "channel_id";
        public static final String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
        @Deprecated
        public static final String COLUMN_EPISODE_NUMBER = "episode_number";
        public static final String COLUMN_RECORDING_PROHIBITED = "recording_prohibited";
        @Deprecated
        public static final String COLUMN_SEASON_NUMBER = "season_number";
        public static final String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/program";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/program";
        public static final Uri CONTENT_URI = Uri.parse("content://android.media.tv/program");

        private Programs() {
        }

        /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Programs$Genres */
        public static final class Genres {
            public static final String ANIMAL_WILDLIFE = "ANIMAL_WILDLIFE";
            public static final String ARTS = "ARTS";
            private static final HashSet<String> CANONICAL_GENRES = new HashSet<>();
            public static final String COMEDY = "COMEDY";
            private static final char COMMA = ',';
            private static final String DELIMITER = ",";
            private static final char DOUBLE_QUOTE = '\"';
            public static final String DRAMA = "DRAMA";
            public static final String EDUCATION = "EDUCATION";
            private static final String[] EMPTY_STRING_ARRAY = new String[0];
            public static final String ENTERTAINMENT = "ENTERTAINMENT";
            public static final String FAMILY_KIDS = "FAMILY_KIDS";
            public static final String GAMING = "GAMING";
            public static final String LIFE_STYLE = "LIFE_STYLE";
            public static final String MOVIES = "MOVIES";
            public static final String MUSIC = "MUSIC";
            public static final String NEWS = "NEWS";
            public static final String PREMIER = "PREMIER";
            public static final String SHOPPING = "SHOPPING";
            public static final String SPORTS = "SPORTS";
            public static final String TECH_SCIENCE = "TECH_SCIENCE";
            public static final String TRAVEL = "TRAVEL";

            @RestrictTo({RestrictTo.Scope.LIBRARY})
            @Retention(RetentionPolicy.SOURCE)
            /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$Programs$Genres$Genre */
            public @interface Genre {
            }

            static {
                CANONICAL_GENRES.add(FAMILY_KIDS);
                CANONICAL_GENRES.add(SPORTS);
                CANONICAL_GENRES.add(SHOPPING);
                CANONICAL_GENRES.add(MOVIES);
                CANONICAL_GENRES.add(COMEDY);
                CANONICAL_GENRES.add(TRAVEL);
                CANONICAL_GENRES.add(DRAMA);
                CANONICAL_GENRES.add(EDUCATION);
                CANONICAL_GENRES.add(ANIMAL_WILDLIFE);
                CANONICAL_GENRES.add(NEWS);
                CANONICAL_GENRES.add(GAMING);
                CANONICAL_GENRES.add(ARTS);
                CANONICAL_GENRES.add(ENTERTAINMENT);
                CANONICAL_GENRES.add(LIFE_STYLE);
                CANONICAL_GENRES.add(MUSIC);
                CANONICAL_GENRES.add(PREMIER);
                CANONICAL_GENRES.add(TECH_SCIENCE);
            }

            private Genres() {
            }

            public static String encode(@NonNull String... genres) {
                if (genres == null) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                String separator = "";
                for (String genre : genres) {
                    sb.append(separator);
                    sb.append(encodeToCsv(genre));
                    separator = DELIMITER;
                }
                return sb.toString();
            }

            private static String encodeToCsv(String genre) {
                StringBuilder sb = new StringBuilder();
                int length = genre.length();
                for (int i = 0; i < length; i++) {
                    char c = genre.charAt(i);
                    if (c == '\"') {
                        sb.append((char) DOUBLE_QUOTE);
                    } else if (c == ',') {
                        sb.append((char) DOUBLE_QUOTE);
                    }
                    sb.append(c);
                }
                return sb.toString();
            }

            public static String[] decode(@NonNull String genres) {
                if (TextUtils.isEmpty(genres)) {
                    return EMPTY_STRING_ARRAY;
                }
                if (genres.indexOf(44) == -1 && genres.indexOf(34) == -1) {
                    return new String[]{genres.trim()};
                }
                StringBuilder sb = new StringBuilder();
                List<String> results = new ArrayList<>();
                int length = genres.length();
                boolean escape = false;
                for (int i = 0; i < length; i++) {
                    char c = genres.charAt(i);
                    if (c != '\"') {
                        if (c == ',' && !escape) {
                            String string = sb.toString().trim();
                            if (string.length() > 0) {
                                results.add(string);
                            }
                            sb = new StringBuilder();
                        }
                    } else if (!escape) {
                        escape = true;
                    }
                    sb.append(c);
                    escape = false;
                }
                String string2 = sb.toString().trim();
                if (string2.length() > 0) {
                    results.add(string2);
                }
                return (String[]) results.toArray(new String[results.size()]);
            }

            public static boolean isCanonical(String genre) {
                return CANONICAL_GENRES.contains(genre);
            }
        }
    }

    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$RecordedPrograms */
    public static final class RecordedPrograms implements BaseTvColumns, ProgramColumns {
        public static final String COLUMN_BROADCAST_GENRE = "broadcast_genre";
        public static final String COLUMN_CHANNEL_ID = "channel_id";
        public static final String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
        public static final String COLUMN_INPUT_ID = "input_id";
        public static final String COLUMN_RECORDING_DATA_BYTES = "recording_data_bytes";
        public static final String COLUMN_RECORDING_DATA_URI = "recording_data_uri";
        public static final String COLUMN_RECORDING_DURATION_MILLIS = "recording_duration_millis";
        public static final String COLUMN_RECORDING_EXPIRE_TIME_UTC_MILLIS = "recording_expire_time_utc_millis";
        public static final String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/recorded_program";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/recorded_program";
        public static final Uri CONTENT_URI = Uri.parse("content://android.media.tv/recorded_program");

        private RecordedPrograms() {
        }
    }

    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$PreviewPrograms */
    public static final class PreviewPrograms implements BaseTvColumns, ProgramColumns, PreviewProgramColumns {
        public static final String COLUMN_CHANNEL_ID = "channel_id";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/preview_program";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/preview_program";
        public static final Uri CONTENT_URI = Uri.parse("content://android.media.tv/preview_program");

        private PreviewPrograms() {
        }
    }

    /* renamed from: androidx.tvprovider.media.tv.TvContractCompat$WatchNextPrograms */
    public static final class WatchNextPrograms implements BaseTvColumns, ProgramColumns, PreviewProgramColumns {
        public static final String COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS = "last_engagement_time_utc_millis";
        public static final String COLUMN_WATCH_NEXT_TYPE = "watch_next_type";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/watch_next_program";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/watch_next_program";
        public static final Uri CONTENT_URI = Uri.parse("content://android.media.tv/watch_next_program");
        public static final int WATCH_NEXT_TYPE_CONTINUE = 0;
        public static final int WATCH_NEXT_TYPE_NEW = 2;
        public static final int WATCH_NEXT_TYPE_NEXT = 1;
        public static final int WATCH_NEXT_TYPE_WATCHLIST = 3;

        private WatchNextPrograms() {
        }
    }
}
