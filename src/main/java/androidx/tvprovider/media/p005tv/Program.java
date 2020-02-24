package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import androidx.tvprovider.media.p005tv.BaseProgram;
import androidx.tvprovider.media.p005tv.TvContractCompat;

/* renamed from: androidx.tvprovider.media.tv.Program */
public final class Program extends BaseProgram implements Comparable<Program> {
    private static final long INVALID_LONG_VALUE = -1;
    private static final int IS_RECORDING_PROHIBITED = 1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String[] PROJECTION = getProjection();

    Program(Builder builder) {
        super(builder);
    }

    public long getChannelId() {
        Long l = this.mValues.getAsLong("channel_id");
        if (l == null) {
            return -1;
        }
        return l.longValue();
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

    public String[] getBroadcastGenres() {
        return TvContractCompat.Programs.Genres.decode(this.mValues.getAsString("broadcast_genre"));
    }

    public boolean isRecordingProhibited() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.Programs.COLUMN_RECORDING_PROHIBITED);
        return i != null && i.intValue() == 1;
    }

    public int hashCode() {
        return this.mValues.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Program)) {
            return false;
        }
        return this.mValues.equals(((Program) other).mValues);
    }

    public int compareTo(@NonNull Program other) {
        return (this.mValues.getAsLong("start_time_utc_millis").longValue() > other.mValues.getAsLong("start_time_utc_millis").longValue() ? 1 : (this.mValues.getAsLong("start_time_utc_millis").longValue() == other.mValues.getAsLong("start_time_utc_millis").longValue() ? 0 : -1));
    }

    public String toString() {
        return "Program{" + this.mValues.toString() + "}";
    }

    public ContentValues toContentValues() {
        ContentValues values = super.toContentValues();
        if (Build.VERSION.SDK_INT < 24) {
            values.remove(TvContractCompat.Programs.COLUMN_RECORDING_PROHIBITED);
        }
        return values;
    }

    public static Program fromCursor(Cursor cursor) {
        Builder builder = new Builder();
        BaseProgram.setFieldsFromCursor(cursor, builder);
        int columnIndex = cursor.getColumnIndex("channel_id");
        int index = columnIndex;
        if (columnIndex >= 0 && !cursor.isNull(index)) {
            builder.setChannelId(cursor.getLong(index));
        }
        int columnIndex2 = cursor.getColumnIndex("broadcast_genre");
        int index2 = columnIndex2;
        if (columnIndex2 >= 0 && !cursor.isNull(index2)) {
            builder.setBroadcastGenres(TvContractCompat.Programs.Genres.decode(cursor.getString(index2)));
        }
        int columnIndex3 = cursor.getColumnIndex("start_time_utc_millis");
        int index3 = columnIndex3;
        if (columnIndex3 >= 0 && !cursor.isNull(index3)) {
            builder.setStartTimeUtcMillis(cursor.getLong(index3));
        }
        int columnIndex4 = cursor.getColumnIndex("end_time_utc_millis");
        int index4 = columnIndex4;
        if (columnIndex4 >= 0 && !cursor.isNull(index4)) {
            builder.setEndTimeUtcMillis(cursor.getLong(index4));
        }
        if (Build.VERSION.SDK_INT >= 24) {
            int columnIndex5 = cursor.getColumnIndex(TvContractCompat.Programs.COLUMN_RECORDING_PROHIBITED);
            int index5 = columnIndex5;
            if (columnIndex5 >= 0 && !cursor.isNull(index5)) {
                boolean z = true;
                if (cursor.getInt(index5) != 1) {
                    z = false;
                }
                builder.setRecordingProhibited(z);
            }
        }
        return builder.build();
    }

    private static String[] getProjection() {
        String[] baseColumns = {"channel_id", "broadcast_genre", "start_time_utc_millis", "end_time_utc_millis"};
        String[] nougatColumns = {TvContractCompat.Programs.COLUMN_RECORDING_PROHIBITED};
        if (Build.VERSION.SDK_INT >= 24) {
            return (String[]) CollectionUtils.concatAll(BaseProgram.PROJECTION, baseColumns, nougatColumns);
        }
        return (String[]) CollectionUtils.concatAll(BaseProgram.PROJECTION, baseColumns);
    }

    /* renamed from: androidx.tvprovider.media.tv.Program$Builder */
    public static class Builder extends BaseProgram.Builder<Builder> {
        public Builder() {
        }

        public Builder(Program other) {
            this.mValues = new ContentValues(other.mValues);
        }

        public Builder setChannelId(long channelId) {
            this.mValues.put("channel_id", Long.valueOf(channelId));
            return this;
        }

        public Builder setStartTimeUtcMillis(long startTimeUtcMillis) {
            this.mValues.put("start_time_utc_millis", Long.valueOf(startTimeUtcMillis));
            return this;
        }

        public Builder setEndTimeUtcMillis(long endTimeUtcMillis) {
            this.mValues.put("end_time_utc_millis", Long.valueOf(endTimeUtcMillis));
            return this;
        }

        public Builder setBroadcastGenres(String[] genres) {
            this.mValues.put("broadcast_genre", TvContractCompat.Programs.Genres.encode(genres));
            return this;
        }

        public Builder setRecordingProhibited(boolean prohibited) {
            this.mValues.put(TvContractCompat.Programs.COLUMN_RECORDING_PROHIBITED, Integer.valueOf(prohibited));
            return this;
        }

        public Program build() {
            return new Program(this);
        }
    }
}
