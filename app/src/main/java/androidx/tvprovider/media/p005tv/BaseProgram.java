package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.database.Cursor;
import android.media.tv.TvContentRating;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: androidx.tvprovider.media.tv.BaseProgram */
public abstract class BaseProgram {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String[] PROJECTION = getProjection();
    private static final int INVALID_INT_VALUE = -1;
    private static final long INVALID_LONG_VALUE = -1;
    private static final int IS_SEARCHABLE = 1;
    private static final int REVIEW_RATING_STYLE_UNKNOWN = -1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected ContentValues mValues;

    BaseProgram(Builder builder) {
        this.mValues = builder.mValues;
    }

    static void setFieldsFromCursor(Cursor cursor, Builder builder) {
        int columnIndex = cursor.getColumnIndex("_id");
        int index = columnIndex;
        if (columnIndex >= 0 && !cursor.isNull(index)) {
            builder.setId(cursor.getLong(index));
        }
        int columnIndex2 = cursor.getColumnIndex("package_name");
        int index2 = columnIndex2;
        if (columnIndex2 >= 0 && !cursor.isNull(index2)) {
            builder.setPackageName(cursor.getString(index2));
        }
        int columnIndex3 = cursor.getColumnIndex("title");
        int index3 = columnIndex3;
        if (columnIndex3 >= 0 && !cursor.isNull(index3)) {
            builder.setTitle(cursor.getString(index3));
        }
        int columnIndex4 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_EPISODE_TITLE);
        int index4 = columnIndex4;
        if (columnIndex4 >= 0 && !cursor.isNull(index4)) {
            builder.setEpisodeTitle(cursor.getString(index4));
        }
        if (Build.VERSION.SDK_INT >= 24) {
            int columnIndex5 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_SEASON_DISPLAY_NUMBER);
            int index5 = columnIndex5;
            if (columnIndex5 >= 0 && !cursor.isNull(index5)) {
                builder.setSeasonNumber(cursor.getString(index5), -1);
            }
        } else {
            int columnIndex6 = cursor.getColumnIndex(TvContractCompat.Programs.COLUMN_SEASON_NUMBER);
            int index6 = columnIndex6;
            if (columnIndex6 >= 0 && !cursor.isNull(index6)) {
                builder.setSeasonNumber(cursor.getInt(index6));
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            int columnIndex7 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_EPISODE_DISPLAY_NUMBER);
            int index7 = columnIndex7;
            if (columnIndex7 >= 0 && !cursor.isNull(index7)) {
                builder.setEpisodeNumber(cursor.getString(index7), -1);
            }
        } else {
            int columnIndex8 = cursor.getColumnIndex(TvContractCompat.Programs.COLUMN_EPISODE_NUMBER);
            int index8 = columnIndex8;
            if (columnIndex8 >= 0 && !cursor.isNull(index8)) {
                builder.setEpisodeNumber(cursor.getInt(index8));
            }
        }
        int columnIndex9 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_SHORT_DESCRIPTION);
        int index9 = columnIndex9;
        if (columnIndex9 >= 0 && !cursor.isNull(index9)) {
            builder.setDescription(cursor.getString(index9));
        }
        int columnIndex10 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_LONG_DESCRIPTION);
        int index10 = columnIndex10;
        if (columnIndex10 >= 0 && !cursor.isNull(index10)) {
            builder.setLongDescription(cursor.getString(index10));
        }
        int columnIndex11 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_POSTER_ART_URI);
        int index11 = columnIndex11;
        if (columnIndex11 >= 0 && !cursor.isNull(index11)) {
            builder.setPosterArtUri(Uri.parse(cursor.getString(index11)));
        }
        int columnIndex12 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_THUMBNAIL_URI);
        int index12 = columnIndex12;
        if (columnIndex12 >= 0 && !cursor.isNull(index12)) {
            builder.setThumbnailUri(Uri.parse(cursor.getString(index12)));
        }
        int columnIndex13 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_AUDIO_LANGUAGE);
        int index13 = columnIndex13;
        if (columnIndex13 >= 0 && !cursor.isNull(index13)) {
            builder.setAudioLanguages(TvContractUtils.stringToAudioLanguages(cursor.getString(index13)));
        }
        int columnIndex14 = cursor.getColumnIndex("canonical_genre");
        int index14 = columnIndex14;
        if (columnIndex14 >= 0 && !cursor.isNull(index14)) {
            builder.setCanonicalGenres(TvContractCompat.Programs.Genres.decode(cursor.getString(index14)));
        }
        int columnIndex15 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_CONTENT_RATING);
        int index15 = columnIndex15;
        if (columnIndex15 >= 0 && !cursor.isNull(index15)) {
            builder.setContentRatings(TvContractUtils.stringToContentRatings(cursor.getString(index15)));
        }
        int columnIndex16 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_VIDEO_WIDTH);
        int index16 = columnIndex16;
        if (columnIndex16 >= 0 && !cursor.isNull(index16)) {
            builder.setVideoWidth((int) cursor.getLong(index16));
        }
        int columnIndex17 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_VIDEO_HEIGHT);
        int index17 = columnIndex17;
        if (columnIndex17 >= 0 && !cursor.isNull(index17)) {
            builder.setVideoHeight((int) cursor.getLong(index17));
        }
        int columnIndex18 = cursor.getColumnIndex("internal_provider_data");
        int index18 = columnIndex18;
        if (columnIndex18 >= 0 && !cursor.isNull(index18)) {
            builder.setInternalProviderData(cursor.getBlob(index18));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int columnIndex19 = cursor.getColumnIndex("searchable");
            int index19 = columnIndex19;
            if (columnIndex19 >= 0 && !cursor.isNull(index19)) {
                boolean z = true;
                if (cursor.getInt(index19) != 1) {
                    z = false;
                }
                builder.setSearchable(z);
            }
            int columnIndex20 = cursor.getColumnIndex("internal_provider_flag1");
            int index20 = columnIndex20;
            if (columnIndex20 >= 0 && !cursor.isNull(index20)) {
                builder.setInternalProviderFlag1(cursor.getLong(index20));
            }
            int columnIndex21 = cursor.getColumnIndex("internal_provider_flag2");
            int index21 = columnIndex21;
            if (columnIndex21 >= 0 && !cursor.isNull(index21)) {
                builder.setInternalProviderFlag2(cursor.getLong(index21));
            }
            int columnIndex22 = cursor.getColumnIndex("internal_provider_flag3");
            int index22 = columnIndex22;
            if (columnIndex22 >= 0 && !cursor.isNull(index22)) {
                builder.setInternalProviderFlag3(cursor.getLong(index22));
            }
            int columnIndex23 = cursor.getColumnIndex("internal_provider_flag4");
            int index23 = columnIndex23;
            if (columnIndex23 >= 0 && !cursor.isNull(index23)) {
                builder.setInternalProviderFlag4(cursor.getLong(index23));
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            int columnIndex24 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_SEASON_TITLE);
            int index24 = columnIndex24;
            if (columnIndex24 >= 0 && !cursor.isNull(index24)) {
                builder.setSeasonTitle(cursor.getString(index24));
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            int columnIndex25 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING_STYLE);
            int index25 = columnIndex25;
            if (columnIndex25 >= 0 && !cursor.isNull(index25)) {
                builder.setReviewRatingStyle(cursor.getInt(index25));
            }
            int columnIndex26 = cursor.getColumnIndex(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING);
            int index26 = columnIndex26;
            if (columnIndex26 >= 0 && !cursor.isNull(index26)) {
                builder.setReviewRating(cursor.getString(index26));
            }
        }
    }

    private static String[] getProjection() {
        String[] baseColumns = new String[16];
        baseColumns[0] = "_id";
        baseColumns[1] = "package_name";
        baseColumns[2] = "title";
        baseColumns[3] = TvContractCompat.ProgramColumns.COLUMN_EPISODE_TITLE;
        baseColumns[4] = Build.VERSION.SDK_INT >= 24 ? TvContractCompat.ProgramColumns.COLUMN_SEASON_DISPLAY_NUMBER : TvContractCompat.Programs.COLUMN_SEASON_NUMBER;
        baseColumns[5] = Build.VERSION.SDK_INT >= 24 ? TvContractCompat.ProgramColumns.COLUMN_EPISODE_DISPLAY_NUMBER : TvContractCompat.Programs.COLUMN_EPISODE_NUMBER;
        baseColumns[6] = TvContractCompat.ProgramColumns.COLUMN_SHORT_DESCRIPTION;
        baseColumns[7] = TvContractCompat.ProgramColumns.COLUMN_LONG_DESCRIPTION;
        baseColumns[8] = TvContractCompat.ProgramColumns.COLUMN_POSTER_ART_URI;
        baseColumns[9] = TvContractCompat.ProgramColumns.COLUMN_THUMBNAIL_URI;
        baseColumns[10] = TvContractCompat.ProgramColumns.COLUMN_AUDIO_LANGUAGE;
        baseColumns[11] = "canonical_genre";
        baseColumns[12] = TvContractCompat.ProgramColumns.COLUMN_CONTENT_RATING;
        baseColumns[13] = TvContractCompat.ProgramColumns.COLUMN_VIDEO_WIDTH;
        baseColumns[14] = TvContractCompat.ProgramColumns.COLUMN_VIDEO_HEIGHT;
        baseColumns[15] = "internal_provider_data";
        String[] marshmallowColumns = {"searchable", "internal_provider_flag1", "internal_provider_flag2", "internal_provider_flag3", "internal_provider_flag4"};
        String[] nougatColumns = {TvContractCompat.ProgramColumns.COLUMN_SEASON_TITLE};
        String[] oColumns = {TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING, TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING_STYLE};
        if (Build.VERSION.SDK_INT >= 26) {
            return (String[]) CollectionUtils.concatAll(baseColumns, marshmallowColumns, nougatColumns, oColumns);
        } else if (Build.VERSION.SDK_INT >= 24) {
            return (String[]) CollectionUtils.concatAll(baseColumns, marshmallowColumns, nougatColumns);
        } else if (Build.VERSION.SDK_INT < 23) {
            return baseColumns;
        } else {
            return (String[]) CollectionUtils.concatAll(baseColumns, marshmallowColumns);
        }
    }

    public long getId() {
        Long l = this.mValues.getAsLong("_id");
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public String getPackageName() {
        return this.mValues.getAsString("package_name");
    }

    public String getTitle() {
        return this.mValues.getAsString("title");
    }

    public String getEpisodeTitle() {
        return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_EPISODE_TITLE);
    }

    public String getSeasonNumber() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_SEASON_DISPLAY_NUMBER);
        }
        return this.mValues.getAsString(TvContractCompat.Programs.COLUMN_SEASON_NUMBER);
    }

    public String getEpisodeNumber() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_EPISODE_DISPLAY_NUMBER);
        }
        return this.mValues.getAsString(TvContractCompat.Programs.COLUMN_EPISODE_NUMBER);
    }

    public String getDescription() {
        return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_SHORT_DESCRIPTION);
    }

    public String getLongDescription() {
        return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_LONG_DESCRIPTION);
    }

    public int getVideoWidth() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.ProgramColumns.COLUMN_VIDEO_WIDTH);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public int getVideoHeight() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.ProgramColumns.COLUMN_VIDEO_HEIGHT);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public String[] getCanonicalGenres() {
        return TvContractCompat.Programs.Genres.decode(this.mValues.getAsString("canonical_genre"));
    }

    public TvContentRating[] getContentRatings() {
        return TvContractUtils.stringToContentRatings(this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_CONTENT_RATING));
    }

    public Uri getPosterArtUri() {
        String uri = this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_POSTER_ART_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public Uri getThumbnailUri() {
        String uri = this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_POSTER_ART_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public byte[] getInternalProviderDataByteArray() {
        return this.mValues.getAsByteArray("internal_provider_data");
    }

    public String[] getAudioLanguages() {
        return TvContractUtils.stringToAudioLanguages(this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_AUDIO_LANGUAGE));
    }

    public boolean isSearchable() {
        Integer i = this.mValues.getAsInteger("searchable");
        return i == null || i.intValue() == 1;
    }

    public Long getInternalProviderFlag1() {
        return this.mValues.getAsLong("internal_provider_flag1");
    }

    public Long getInternalProviderFlag2() {
        return this.mValues.getAsLong("internal_provider_flag2");
    }

    public Long getInternalProviderFlag3() {
        return this.mValues.getAsLong("internal_provider_flag3");
    }

    public Long getInternalProviderFlag4() {
        return this.mValues.getAsLong("internal_provider_flag4");
    }

    public String getSeasonTitle() {
        return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_SEASON_TITLE);
    }

    public int getReviewRatingStyle() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING_STYLE);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public String getReviewRating() {
        return this.mValues.getAsString(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING);
    }

    public int hashCode() {
        return this.mValues.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof BaseProgram)) {
            return false;
        }
        return this.mValues.equals(((BaseProgram) other).mValues);
    }

    public String toString() {
        return "BaseProgram{" + this.mValues.toString() + "}";
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues(this.mValues);
        if (Build.VERSION.SDK_INT < 23) {
            values.remove("searchable");
            values.remove("internal_provider_flag1");
            values.remove("internal_provider_flag2");
            values.remove("internal_provider_flag3");
            values.remove("internal_provider_flag4");
        }
        if (Build.VERSION.SDK_INT < 24) {
            values.remove(TvContractCompat.ProgramColumns.COLUMN_SEASON_TITLE);
        }
        if (Build.VERSION.SDK_INT < 26) {
            values.remove(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING_STYLE);
            values.remove(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING);
        }
        return values;
    }

    /* renamed from: androidx.tvprovider.media.tv.BaseProgram$Builder */
    public static abstract class Builder<T extends Builder> {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected ContentValues mValues;

        public Builder() {
            this.mValues = new ContentValues();
        }

        public Builder(BaseProgram other) {
            this.mValues = new ContentValues(other.mValues);
        }

        public T setId(long programId) {
            this.mValues.put("_id", Long.valueOf(programId));
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public T setPackageName(String packageName) {
            this.mValues.put("package_name", packageName);
            return this;
        }

        public T setTitle(String title) {
            this.mValues.put("title", title);
            return this;
        }

        public T setEpisodeTitle(String episodeTitle) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_EPISODE_TITLE, episodeTitle);
            return this;
        }

        public T setSeasonNumber(int seasonNumber) {
            setSeasonNumber(String.valueOf(seasonNumber), seasonNumber);
            return this;
        }

        public T setSeasonNumber(String seasonNumber, int numericalSeasonNumber) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_SEASON_DISPLAY_NUMBER, seasonNumber);
            } else {
                this.mValues.put(TvContractCompat.Programs.COLUMN_SEASON_NUMBER, Integer.valueOf(numericalSeasonNumber));
            }
            return this;
        }

        public T setEpisodeNumber(int episodeNumber) {
            setEpisodeNumber(String.valueOf(episodeNumber), episodeNumber);
            return this;
        }

        public T setEpisodeNumber(String episodeNumber, int numericalEpisodeNumber) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_EPISODE_DISPLAY_NUMBER, episodeNumber);
            } else {
                this.mValues.put(TvContractCompat.Programs.COLUMN_EPISODE_NUMBER, Integer.valueOf(numericalEpisodeNumber));
            }
            return this;
        }

        public T setDescription(String description) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_SHORT_DESCRIPTION, description);
            return this;
        }

        public T setLongDescription(String longDescription) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_LONG_DESCRIPTION, longDescription);
            return this;
        }

        public T setVideoWidth(int width) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_VIDEO_WIDTH, Integer.valueOf(width));
            return this;
        }

        public T setVideoHeight(int height) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_VIDEO_HEIGHT, Integer.valueOf(height));
            return this;
        }

        public T setContentRatings(TvContentRating[] contentRatings) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_CONTENT_RATING, TvContractUtils.contentRatingsToString(contentRatings));
            return this;
        }

        public T setPosterArtUri(Uri posterArtUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (posterArtUri == null) {
                str = null;
            } else {
                str = posterArtUri.toString();
            }
            contentValues.put(TvContractCompat.ProgramColumns.COLUMN_POSTER_ART_URI, str);
            return this;
        }

        public T setThumbnailUri(Uri thumbnailUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (thumbnailUri == null) {
                str = null;
            } else {
                str = thumbnailUri.toString();
            }
            contentValues.put(TvContractCompat.ProgramColumns.COLUMN_THUMBNAIL_URI, str);
            return this;
        }

        public T setCanonicalGenres(String[] genres) {
            this.mValues.put("canonical_genre", TvContractCompat.Programs.Genres.encode(genres));
            return this;
        }

        public T setInternalProviderData(byte[] data) {
            this.mValues.put("internal_provider_data", data);
            return this;
        }

        public T setAudioLanguages(String[] audioLanguages) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_AUDIO_LANGUAGE, TvContractUtils.audioLanguagesToString(audioLanguages));
            return this;
        }

        public T setSearchable(boolean searchable) {
            this.mValues.put("searchable", Integer.valueOf(searchable));
            return this;
        }

        public T setInternalProviderFlag1(long flag) {
            this.mValues.put("internal_provider_flag1", Long.valueOf(flag));
            return this;
        }

        public T setInternalProviderFlag2(long flag) {
            this.mValues.put("internal_provider_flag2", Long.valueOf(flag));
            return this;
        }

        public T setInternalProviderFlag3(long flag) {
            this.mValues.put("internal_provider_flag3", Long.valueOf(flag));
            return this;
        }

        public T setInternalProviderFlag4(long flag) {
            this.mValues.put("internal_provider_flag4", Long.valueOf(flag));
            return this;
        }

        public T setReviewRatingStyle(int reviewRatingStyle) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING_STYLE, Integer.valueOf(reviewRatingStyle));
            return this;
        }

        public T setReviewRating(String reviewRating) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_REVIEW_RATING, reviewRating);
            return this;
        }

        public T setSeasonTitle(String seasonTitle) {
            this.mValues.put(TvContractCompat.ProgramColumns.COLUMN_SEASON_TITLE, seasonTitle);
            return this;
        }
    }
}
