package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RestrictTo;
import androidx.tvprovider.media.p005tv.BasePreviewProgram;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* renamed from: androidx.tvprovider.media.tv.WatchNextProgram */
public final class WatchNextProgram extends BasePreviewProgram {
    private static final int INVALID_INT_VALUE = -1;
    private static final long INVALID_LONG_VALUE = -1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String[] PROJECTION = getProjection();
    public static final int WATCH_NEXT_TYPE_UNKNOWN = -1;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.tvprovider.media.tv.WatchNextProgram$WatchNextType */
    public @interface WatchNextType {
    }

    WatchNextProgram(Builder builder) {
        super(builder);
    }

    public int getWatchNextType() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.WatchNextPrograms.COLUMN_WATCH_NEXT_TYPE);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public long getLastEngagementTimeUtcMillis() {
        Long l = this.mValues.getAsLong(TvContractCompat.WatchNextPrograms.COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS);
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    public boolean equals(Object other) {
        if (!(other instanceof WatchNextProgram)) {
            return false;
        }
        return this.mValues.equals(((WatchNextProgram) other).mValues);
    }

    public boolean hasAnyUpdatedValues(WatchNextProgram update) {
        for (String key : update.mValues.keySet()) {
            if (!Objects.deepEquals(update.mValues.get(key), this.mValues.get(key))) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "WatchNextProgram{" + this.mValues.toString() + "}";
    }

    public ContentValues toContentValues() {
        return toContentValues(false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ContentValues toContentValues(boolean includeProtectedFields) {
        ContentValues values = super.toContentValues(includeProtectedFields);
        if (Build.VERSION.SDK_INT < 26) {
            values.remove(TvContractCompat.WatchNextPrograms.COLUMN_WATCH_NEXT_TYPE);
            values.remove(TvContractCompat.WatchNextPrograms.COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS);
        }
        return values;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.tvprovider.media.tv.BasePreviewProgram.setFieldsFromCursor(android.database.Cursor, androidx.tvprovider.media.tv.BasePreviewProgram$Builder):void
     arg types: [android.database.Cursor, androidx.tvprovider.media.tv.WatchNextProgram$Builder]
     candidates:
      androidx.tvprovider.media.tv.BaseProgram.setFieldsFromCursor(android.database.Cursor, androidx.tvprovider.media.tv.BaseProgram$Builder):void
      androidx.tvprovider.media.tv.BasePreviewProgram.setFieldsFromCursor(android.database.Cursor, androidx.tvprovider.media.tv.BasePreviewProgram$Builder):void */
    public static WatchNextProgram fromCursor(Cursor cursor) {
        Builder builder = new Builder();
        BasePreviewProgram.setFieldsFromCursor(cursor, (BasePreviewProgram.Builder) builder);
        int columnIndex = cursor.getColumnIndex(TvContractCompat.WatchNextPrograms.COLUMN_WATCH_NEXT_TYPE);
        int index = columnIndex;
        if (columnIndex >= 0 && !cursor.isNull(index)) {
            builder.setWatchNextType(cursor.getInt(index));
        }
        int columnIndex2 = cursor.getColumnIndex(TvContractCompat.WatchNextPrograms.COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS);
        int index2 = columnIndex2;
        if (columnIndex2 >= 0 && !cursor.isNull(index2)) {
            builder.setLastEngagementTimeUtcMillis(cursor.getLong(index2));
        }
        return builder.build();
    }

    private static String[] getProjection() {
        String[] oColumns = {TvContractCompat.WatchNextPrograms.COLUMN_WATCH_NEXT_TYPE, TvContractCompat.WatchNextPrograms.COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS};
        return (String[]) CollectionUtils.concatAll(BasePreviewProgram.PROJECTION, oColumns);
    }

    /* renamed from: androidx.tvprovider.media.tv.WatchNextProgram$Builder */
    public static final class Builder extends BasePreviewProgram.Builder<Builder> {
        public Builder() {
        }

        public Builder(WatchNextProgram other) {
            this.mValues = new ContentValues(other.mValues);
        }

        public Builder setWatchNextType(int watchNextType) {
            this.mValues.put(TvContractCompat.WatchNextPrograms.COLUMN_WATCH_NEXT_TYPE, Integer.valueOf(watchNextType));
            return this;
        }

        public Builder setLastEngagementTimeUtcMillis(long lastEngagementTimeUtcMillis) {
            this.mValues.put(TvContractCompat.WatchNextPrograms.COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS, Long.valueOf(lastEngagementTimeUtcMillis));
            return this;
        }

        public WatchNextProgram build() {
            return new WatchNextProgram(this);
        }
    }
}
