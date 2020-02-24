package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RestrictTo;
import androidx.tvprovider.media.p005tv.BaseProgram;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram */
public abstract class BasePreviewProgram extends BaseProgram {
    private static final int ASPECT_RATIO_UNKNOWN = -1;
    private static final int AVAILABILITY_UNKNOWN = -1;
    private static final int INTERACTION_TYPE_UNKNOWN = -1;
    private static final int INVALID_INT_VALUE = -1;
    private static final long INVALID_LONG_VALUE = -1;
    private static final int IS_BROWSABLE = 1;
    private static final int IS_LIVE = 1;
    private static final int IS_TRANSIENT = 1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String[] PROJECTION = getProjection();
    private static final int TYPE_UNKNOWN = -1;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram$AspectRatio */
    public @interface AspectRatio {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram$Availability */
    public @interface Availability {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram$InteractionType */
    public @interface InteractionType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram$TvSeriesItemType */
    public @interface TvSeriesItemType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram$Type */
    public @interface Type {
    }

    BasePreviewProgram(Builder builder) {
        super(builder);
    }

    public String getInternalProviderId() {
        return this.mValues.getAsString("internal_provider_id");
    }

    public Uri getPreviewVideoUri() {
        String uri = this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public int getLastPlaybackPositionMillis() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_LAST_PLAYBACK_POSITION_MILLIS);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public int getDurationMillis() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_DURATION_MILLIS);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public Uri getIntentUri() {
        String uri = this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public Intent getIntent() throws URISyntaxException {
        String uri = this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI);
        if (uri == null) {
            return null;
        }
        return Intent.parseUri(uri, 1);
    }

    public boolean isTransient() {
        Integer i = this.mValues.getAsInteger("transient");
        return i != null && i.intValue() == 1;
    }

    public int getType() {
        Integer i = this.mValues.getAsInteger("type");
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public int getTvSeriesItemType() {
        return this.mValues.getAsInteger("tv_series_item_type").intValue();
    }

    public int getPosterArtAspectRatio() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_POSTER_ART_ASPECT_RATIO);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public int getThumbnailAspectRatio() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_THUMBNAIL_ASPECT_RATIO);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public Uri getLogoUri() {
        String uri = this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_LOGO_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public int getAvailability() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_AVAILABILITY);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public String getStartingPrice() {
        return this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_STARTING_PRICE);
    }

    public String getOfferPrice() {
        return this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_OFFER_PRICE);
    }

    public String getReleaseDate() {
        return this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_RELEASE_DATE);
    }

    public int getItemCount() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_ITEM_COUNT);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public boolean isLive() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_LIVE);
        return i != null && i.intValue() == 1;
    }

    public int getInteractionType() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_TYPE);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public long getInteractionCount() {
        Long l = this.mValues.getAsLong(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_COUNT);
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    public String getAuthor() {
        return this.mValues.getAsString(TvContractCompat.PreviewProgramColumns.COLUMN_AUTHOR);
    }

    public boolean isBrowsable() {
        Integer i = this.mValues.getAsInteger("browsable");
        return i != null && i.intValue() == 1;
    }

    public String getContentId() {
        return this.mValues.getAsString("content_id");
    }

    public String getLogoContentDescription() {
        return this.mValues.getAsString("logo_content_description");
    }

    public String getGenre() {
        return this.mValues.getAsString("genre");
    }

    public long getStartTimeUtcMillis() {
        Long l = this.mValues.getAsLong("start_time_utc_millis");
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    public long getEndTimeUtcMillis() {
        Long l = this.mValues.getAsLong("end_time_utc_millis");
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    public Uri getPreviewAudioUri() {
        String uri = this.mValues.getAsString("preview_audio_uri");
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public boolean equals(Object other) {
        if (!(other instanceof BasePreviewProgram)) {
            return false;
        }
        return this.mValues.equals(((BasePreviewProgram) other).mValues);
    }

    public ContentValues toContentValues() {
        return toContentValues(false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ContentValues toContentValues(boolean includeProtectedFields) {
        ContentValues values = super.toContentValues();
        if (Build.VERSION.SDK_INT < 26) {
            values.remove("internal_provider_id");
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_LAST_PLAYBACK_POSITION_MILLIS);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_DURATION_MILLIS);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI);
            values.remove("transient");
            values.remove("type");
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_POSTER_ART_ASPECT_RATIO);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_THUMBNAIL_ASPECT_RATIO);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_LOGO_URI);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_AVAILABILITY);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_STARTING_PRICE);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_OFFER_PRICE);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_RELEASE_DATE);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_ITEM_COUNT);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_LIVE);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_COUNT);
            values.remove(TvContractCompat.PreviewProgramColumns.COLUMN_AUTHOR);
            values.remove("content_id");
            values.remove("logo_content_description");
            values.remove("genre");
            values.remove("start_time_utc_millis");
            values.remove("end_time_utc_millis");
            values.remove("preview_audio_uri");
            values.remove("tv_series_item_type");
        }
        if (Build.VERSION.SDK_INT < 26 || !includeProtectedFields) {
            values.remove("browsable");
        }
        return values;
    }

    static void setFieldsFromCursor(Cursor cursor, Builder builder) {
        BaseProgram.setFieldsFromCursor(cursor, builder);
        if (Build.VERSION.SDK_INT >= 26) {
            int columnIndex = cursor.getColumnIndex("internal_provider_id");
            int index = columnIndex;
            if (columnIndex >= 0 && !cursor.isNull(index)) {
                builder.setInternalProviderId(cursor.getString(index));
            }
            int columnIndex2 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI);
            int index2 = columnIndex2;
            if (columnIndex2 >= 0 && !cursor.isNull(index2)) {
                builder.setPreviewVideoUri(Uri.parse(cursor.getString(index2)));
            }
            int columnIndex3 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_LAST_PLAYBACK_POSITION_MILLIS);
            int index3 = columnIndex3;
            if (columnIndex3 >= 0 && !cursor.isNull(index3)) {
                builder.setLastPlaybackPositionMillis(cursor.getInt(index3));
            }
            int columnIndex4 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_DURATION_MILLIS);
            int index4 = columnIndex4;
            if (columnIndex4 >= 0 && !cursor.isNull(index4)) {
                builder.setDurationMillis(cursor.getInt(index4));
            }
            int columnIndex5 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI);
            int index5 = columnIndex5;
            if (columnIndex5 >= 0 && !cursor.isNull(index5)) {
                builder.setIntentUri(Uri.parse(cursor.getString(index5)));
            }
            int columnIndex6 = cursor.getColumnIndex("transient");
            int index6 = columnIndex6;
            boolean z = false;
            if (columnIndex6 >= 0 && !cursor.isNull(index6)) {
                builder.setTransient(cursor.getInt(index6) == 1);
            }
            int columnIndex7 = cursor.getColumnIndex("type");
            int index7 = columnIndex7;
            if (columnIndex7 >= 0 && !cursor.isNull(index7)) {
                builder.setType(cursor.getInt(index7));
            }
            int columnIndex8 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_POSTER_ART_ASPECT_RATIO);
            int index8 = columnIndex8;
            if (columnIndex8 >= 0 && !cursor.isNull(index8)) {
                builder.setPosterArtAspectRatio(cursor.getInt(index8));
            }
            int columnIndex9 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_THUMBNAIL_ASPECT_RATIO);
            int index9 = columnIndex9;
            if (columnIndex9 >= 0 && !cursor.isNull(index9)) {
                builder.setThumbnailAspectRatio(cursor.getInt(index9));
            }
            int columnIndex10 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_LOGO_URI);
            int index10 = columnIndex10;
            if (columnIndex10 >= 0 && !cursor.isNull(index10)) {
                builder.setLogoUri(Uri.parse(cursor.getString(index10)));
            }
            int columnIndex11 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_AVAILABILITY);
            int index11 = columnIndex11;
            if (columnIndex11 >= 0 && !cursor.isNull(index11)) {
                builder.setAvailability(cursor.getInt(index11));
            }
            int columnIndex12 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_STARTING_PRICE);
            int index12 = columnIndex12;
            if (columnIndex12 >= 0 && !cursor.isNull(index12)) {
                builder.setStartingPrice(cursor.getString(index12));
            }
            int columnIndex13 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_OFFER_PRICE);
            int index13 = columnIndex13;
            if (columnIndex13 >= 0 && !cursor.isNull(index13)) {
                builder.setOfferPrice(cursor.getString(index13));
            }
            int columnIndex14 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_RELEASE_DATE);
            int index14 = columnIndex14;
            if (columnIndex14 >= 0 && !cursor.isNull(index14)) {
                builder.setReleaseDate(cursor.getString(index14));
            }
            int columnIndex15 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_ITEM_COUNT);
            int index15 = columnIndex15;
            if (columnIndex15 >= 0 && !cursor.isNull(index15)) {
                builder.setItemCount(cursor.getInt(index15));
            }
            int columnIndex16 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_LIVE);
            int index16 = columnIndex16;
            if (columnIndex16 >= 0 && !cursor.isNull(index16)) {
                builder.setLive(cursor.getInt(index16) == 1);
            }
            int columnIndex17 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_TYPE);
            int index17 = columnIndex17;
            if (columnIndex17 >= 0 && !cursor.isNull(index17)) {
                builder.setInteractionType(cursor.getInt(index17));
            }
            int columnIndex18 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_COUNT);
            int index18 = columnIndex18;
            if (columnIndex18 >= 0 && !cursor.isNull(index18)) {
                builder.setInteractionCount((long) cursor.getInt(index18));
            }
            int columnIndex19 = cursor.getColumnIndex(TvContractCompat.PreviewProgramColumns.COLUMN_AUTHOR);
            int index19 = columnIndex19;
            if (columnIndex19 >= 0 && !cursor.isNull(index19)) {
                builder.setAuthor(cursor.getString(index19));
            }
            int columnIndex20 = cursor.getColumnIndex("browsable");
            int index20 = columnIndex20;
            if (columnIndex20 >= 0 && !cursor.isNull(index20)) {
                if (cursor.getInt(index20) == 1) {
                    z = true;
                }
                builder.setBrowsable(z);
            }
            int columnIndex21 = cursor.getColumnIndex("content_id");
            int index21 = columnIndex21;
            if (columnIndex21 >= 0 && !cursor.isNull(index21)) {
                builder.setContentId(cursor.getString(index21));
            }
            int columnIndex22 = cursor.getColumnIndex("logo_content_description");
            int index22 = columnIndex22;
            if (columnIndex22 >= 0 && !cursor.isNull(index22)) {
                builder.setLogoContentDescription(cursor.getString(index22));
            }
            int columnIndex23 = cursor.getColumnIndex("genre");
            int index23 = columnIndex23;
            if (columnIndex23 >= 0 && !cursor.isNull(index23)) {
                builder.setGenre(cursor.getString(index23));
            }
            int columnIndex24 = cursor.getColumnIndex("start_time_utc_millis");
            int index24 = columnIndex24;
            if (columnIndex24 >= 0 && !cursor.isNull(index24)) {
                builder.setStartTimeUtcMillis(cursor.getLong(index24));
            }
            int columnIndex25 = cursor.getColumnIndex("end_time_utc_millis");
            int index25 = columnIndex25;
            if (columnIndex25 >= 0 && !cursor.isNull(index25)) {
                builder.setEndTimeUtcMillis(cursor.getLong(index25));
            }
            int columnIndex26 = cursor.getColumnIndex("preview_audio_uri");
            int index26 = columnIndex26;
            if (columnIndex26 >= 0 && !cursor.isNull(index26)) {
                builder.setPreviewAudioUri(Uri.parse(cursor.getString(index26)));
            }
            int columnIndex27 = cursor.getColumnIndex("tv_series_item_type");
            int index27 = columnIndex27;
            if (columnIndex27 >= 0 && !cursor.isNull(index27)) {
                builder.setTvSeriesItemType(cursor.getInt(index27));
            }
        }
    }

    private static String[] getProjection() {
        String[] oColumns = {"internal_provider_id", TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI, TvContractCompat.PreviewProgramColumns.COLUMN_LAST_PLAYBACK_POSITION_MILLIS, TvContractCompat.PreviewProgramColumns.COLUMN_DURATION_MILLIS, TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI, "transient", "type", TvContractCompat.PreviewProgramColumns.COLUMN_POSTER_ART_ASPECT_RATIO, TvContractCompat.PreviewProgramColumns.COLUMN_THUMBNAIL_ASPECT_RATIO, TvContractCompat.PreviewProgramColumns.COLUMN_LOGO_URI, TvContractCompat.PreviewProgramColumns.COLUMN_AVAILABILITY, TvContractCompat.PreviewProgramColumns.COLUMN_STARTING_PRICE, TvContractCompat.PreviewProgramColumns.COLUMN_OFFER_PRICE, TvContractCompat.PreviewProgramColumns.COLUMN_RELEASE_DATE, TvContractCompat.PreviewProgramColumns.COLUMN_ITEM_COUNT, TvContractCompat.PreviewProgramColumns.COLUMN_LIVE, TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_TYPE, TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_COUNT, TvContractCompat.PreviewProgramColumns.COLUMN_AUTHOR, "browsable", "content_id", "logo_content_description", "genre", "start_time_utc_millis", "end_time_utc_millis", "preview_audio_uri", "tv_series_item_type"};
        return (String[]) CollectionUtils.concatAll(BaseProgram.PROJECTION, oColumns);
    }

    /* renamed from: androidx.tvprovider.media.tv.BasePreviewProgram$Builder */
    public static abstract class Builder<T extends Builder> extends BaseProgram.Builder<T> {
        private static final SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        static {
            sFormat.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        }

        public Builder() {
        }

        public Builder(BasePreviewProgram other) {
            this.mValues = new ContentValues(other.mValues);
        }

        public T setInternalProviderId(String externalId) {
            this.mValues.put("internal_provider_id", externalId);
            return this;
        }

        public T setPreviewVideoUri(Uri previewVideoUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (previewVideoUri == null) {
                str = null;
            } else {
                str = previewVideoUri.toString();
            }
            contentValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI, str);
            return this;
        }

        public T setLastPlaybackPositionMillis(int position) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_LAST_PLAYBACK_POSITION_MILLIS, Integer.valueOf(position));
            return this;
        }

        public T setDurationMillis(int duration) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_DURATION_MILLIS, Integer.valueOf(duration));
            return this;
        }

        public T setIntentUri(Uri intentUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (intentUri == null) {
                str = null;
            } else {
                str = intentUri.toString();
            }
            contentValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_INTENT_URI, str);
            return this;
        }

        public T setIntent(Intent intent) {
            return setIntentUri(Uri.parse(intent.toUri(1)));
        }

        public T setTransient(boolean transientValue) {
            this.mValues.put("transient", Integer.valueOf(transientValue));
            return this;
        }

        public T setType(int type) {
            this.mValues.put("type", Integer.valueOf(type));
            return this;
        }

        public T setPosterArtAspectRatio(int ratio) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_POSTER_ART_ASPECT_RATIO, Integer.valueOf(ratio));
            return this;
        }

        public T setThumbnailAspectRatio(int ratio) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_THUMBNAIL_ASPECT_RATIO, Integer.valueOf(ratio));
            return this;
        }

        public T setLogoUri(Uri logoUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (logoUri == null) {
                str = null;
            } else {
                str = logoUri.toString();
            }
            contentValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_LOGO_URI, str);
            return this;
        }

        public T setAvailability(int availability) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_AVAILABILITY, Integer.valueOf(availability));
            return this;
        }

        public T setStartingPrice(String price) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_STARTING_PRICE, price);
            return this;
        }

        public T setOfferPrice(String price) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_OFFER_PRICE, price);
            return this;
        }

        public T setReleaseDate(String releaseDate) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_RELEASE_DATE, releaseDate);
            return this;
        }

        public T setReleaseDate(Date releaseDate) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_RELEASE_DATE, sFormat.format(releaseDate));
            return this;
        }

        public T setItemCount(int itemCount) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_ITEM_COUNT, Integer.valueOf(itemCount));
            return this;
        }

        public T setLive(boolean live) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_LIVE, Integer.valueOf(live));
            return this;
        }

        public T setInteractionType(int interactionType) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_TYPE, Integer.valueOf(interactionType));
            return this;
        }

        public T setInteractionCount(long interactionCount) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_INTERACTION_COUNT, Long.valueOf(interactionCount));
            return this;
        }

        public T setAuthor(String author) {
            this.mValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_AUTHOR, author);
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public T setBrowsable(boolean browsable) {
            this.mValues.put("browsable", Integer.valueOf(browsable));
            return this;
        }

        public T setContentId(String contentId) {
            this.mValues.put("content_id", contentId);
            return this;
        }

        public T setLogoContentDescription(String logoContentDescription) {
            this.mValues.put("logo_content_description", logoContentDescription);
            return this;
        }

        public T setGenre(String genre) {
            this.mValues.put("genre", genre);
            return this;
        }

        public T setStartTimeUtcMillis(long startTime) {
            this.mValues.put("start_time_utc_millis", Long.valueOf(startTime));
            return this;
        }

        public T setEndTimeUtcMillis(long endTime) {
            this.mValues.put("end_time_utc_millis", Long.valueOf(endTime));
            return this;
        }

        public T setPreviewAudioUri(Uri previewAudioUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (previewAudioUri == null) {
                str = null;
            } else {
                str = previewAudioUri.toString();
            }
            contentValues.put("preview_audio_uri", str);
            return this;
        }

        public T setTvSeriesItemType(int type) {
            this.mValues.put("tv_series_item_type", Integer.valueOf(type));
            return this;
        }
    }
}
