package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RestrictTo;

import java.util.Objects;

/* renamed from: androidx.tvprovider.media.tv.PreviewProgram */
public final class PreviewProgram extends BasePreviewProgram {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String[] PROJECTION = getProjection();
    private static final int INVALID_INT_VALUE = -1;
    private static final long INVALID_LONG_VALUE = -1;

    PreviewProgram(Builder builder) {
        super(builder);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.tvprovider.media.tv.BasePreviewProgram.setFieldsFromCursor(android.database.Cursor, androidx.tvprovider.media.tv.BasePreviewProgram$Builder):void
     arg types: [android.database.Cursor, androidx.tvprovider.media.tv.PreviewProgram$Builder]
     candidates:
      androidx.tvprovider.media.tv.BaseProgram.setFieldsFromCursor(android.database.Cursor, androidx.tvprovider.media.tv.BaseProgram$Builder):void
      androidx.tvprovider.media.tv.BasePreviewProgram.setFieldsFromCursor(android.database.Cursor, androidx.tvprovider.media.tv.BasePreviewProgram$Builder):void */
    public static PreviewProgram fromCursor(Cursor cursor) {
        Builder builder = new Builder();
        BasePreviewProgram.setFieldsFromCursor(cursor, (BasePreviewProgram.Builder) builder);
        int columnIndex = cursor.getColumnIndex("channel_id");
        int index = columnIndex;
        if (columnIndex >= 0 && !cursor.isNull(index)) {
            builder.setChannelId(cursor.getLong(index));
        }
        int columnIndex2 = cursor.getColumnIndex(TvContractCompat.PreviewPrograms.COLUMN_WEIGHT);
        int index2 = columnIndex2;
        if (columnIndex2 >= 0 && !cursor.isNull(index2)) {
            builder.setWeight(cursor.getInt(index2));
        }
        return builder.build();
    }

    private static String[] getProjection() {
        String[] oColumns = {"channel_id", TvContractCompat.PreviewPrograms.COLUMN_WEIGHT};
        return (String[]) CollectionUtils.concatAll(BasePreviewProgram.PROJECTION, oColumns);
    }

    public long getChannelId() {
        Long l = this.mValues.getAsLong("channel_id");
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    public int getWeight() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.PreviewPrograms.COLUMN_WEIGHT);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public boolean equals(Object other) {
        if (!(other instanceof PreviewProgram)) {
            return false;
        }
        return this.mValues.equals(((PreviewProgram) other).mValues);
    }

    public boolean hasAnyUpdatedValues(PreviewProgram update) {
        for (String key : update.mValues.keySet()) {
            if (!Objects.deepEquals(update.mValues.get(key), this.mValues.get(key))) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "PreviewProgram{" + this.mValues.toString() + "}";
    }

    public ContentValues toContentValues() {
        return toContentValues(false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ContentValues toContentValues(boolean includeProtectedFields) {
        ContentValues values = super.toContentValues(includeProtectedFields);
        if (Build.VERSION.SDK_INT < 26) {
            values.remove("channel_id");
            values.remove(TvContractCompat.PreviewPrograms.COLUMN_WEIGHT);
        }
        return values;
    }

    /* renamed from: androidx.tvprovider.media.tv.PreviewProgram$Builder */
    public static final class Builder extends BasePreviewProgram.Builder<Builder> {
        public Builder() {
        }

        public Builder(PreviewProgram other) {
            this.mValues = new ContentValues(other.mValues);
        }

        public Builder setChannelId(long channelId) {
            this.mValues.put("channel_id", Long.valueOf(channelId));
            return this;
        }

        public Builder setWeight(int weight) {
            this.mValues.put(TvContractCompat.PreviewPrograms.COLUMN_WEIGHT, Integer.valueOf(weight));
            return this;
        }

        public PreviewProgram build() {
            return new PreviewProgram(this);
        }
    }
}
