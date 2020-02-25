package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RestrictTo;

import java.net.URISyntaxException;
import java.nio.charset.Charset;

/* renamed from: androidx.tvprovider.media.tv.Channel */
public final class Channel {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String[] PROJECTION = getProjection();
    private static final long INVALID_CHANNEL_ID = -1;
    private static final int INVALID_INT_VALUE = -1;
    private static final int IS_BROWSABLE = 1;
    private static final int IS_LOCKED = 1;
    private static final int IS_SEARCHABLE = 1;
    private static final int IS_SYSTEM_APPROVED = 1;
    private static final int IS_TRANSIENT = 1;
    ContentValues mValues;

    Channel(Builder builder) {
        this.mValues = builder.mValues;
    }

    public static Channel fromCursor(Cursor cursor) {
        Builder builder = new Builder();
        int columnIndex = cursor.getColumnIndex("_id");
        int index = columnIndex;
        if (columnIndex >= 0 && !cursor.isNull(index)) {
            builder.setId(cursor.getLong(index));
        }
        int columnIndex2 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_DESCRIPTION);
        int index2 = columnIndex2;
        if (columnIndex2 >= 0 && !cursor.isNull(index2)) {
            builder.setDescription(cursor.getString(index2));
        }
        int columnIndex3 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_DISPLAY_NAME);
        int index3 = columnIndex3;
        if (columnIndex3 >= 0 && !cursor.isNull(index3)) {
            builder.setDisplayName(cursor.getString(index3));
        }
        int columnIndex4 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_DISPLAY_NUMBER);
        int index4 = columnIndex4;
        if (columnIndex4 >= 0 && !cursor.isNull(index4)) {
            builder.setDisplayNumber(cursor.getString(index4));
        }
        int columnIndex5 = cursor.getColumnIndex("input_id");
        int index5 = columnIndex5;
        if (columnIndex5 >= 0 && !cursor.isNull(index5)) {
            builder.setInputId(cursor.getString(index5));
        }
        int columnIndex6 = cursor.getColumnIndex("internal_provider_data");
        int index6 = columnIndex6;
        if (columnIndex6 >= 0 && !cursor.isNull(index6)) {
            builder.setInternalProviderData(cursor.getBlob(index6));
        }
        int columnIndex7 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_NETWORK_AFFILIATION);
        int index7 = columnIndex7;
        if (columnIndex7 >= 0 && !cursor.isNull(index7)) {
            builder.setNetworkAffiliation(cursor.getString(index7));
        }
        int columnIndex8 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_ORIGINAL_NETWORK_ID);
        int index8 = columnIndex8;
        if (columnIndex8 >= 0 && !cursor.isNull(index8)) {
            builder.setOriginalNetworkId(cursor.getInt(index8));
        }
        int columnIndex9 = cursor.getColumnIndex("package_name");
        int index9 = columnIndex9;
        if (columnIndex9 >= 0 && !cursor.isNull(index9)) {
            builder.setPackageName(cursor.getString(index9));
        }
        int columnIndex10 = cursor.getColumnIndex("searchable");
        int index10 = columnIndex10;
        boolean z = false;
        if (columnIndex10 >= 0 && !cursor.isNull(index10)) {
            builder.setSearchable(cursor.getInt(index10) == 1);
        }
        int columnIndex11 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_SERVICE_ID);
        int index11 = columnIndex11;
        if (columnIndex11 >= 0 && !cursor.isNull(index11)) {
            builder.setServiceId(cursor.getInt(index11));
        }
        int columnIndex12 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_SERVICE_TYPE);
        int index12 = columnIndex12;
        if (columnIndex12 >= 0 && !cursor.isNull(index12)) {
            builder.setServiceType(cursor.getString(index12));
        }
        int columnIndex13 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_TRANSPORT_STREAM_ID);
        int index13 = columnIndex13;
        if (columnIndex13 >= 0 && !cursor.isNull(index13)) {
            builder.setTransportStreamId(cursor.getInt(index13));
        }
        int columnIndex14 = cursor.getColumnIndex("type");
        int index14 = columnIndex14;
        if (columnIndex14 >= 0 && !cursor.isNull(index14)) {
            builder.setType(cursor.getString(index14));
        }
        int columnIndex15 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_VIDEO_FORMAT);
        int index15 = columnIndex15;
        if (columnIndex15 >= 0 && !cursor.isNull(index15)) {
            builder.setVideoFormat(cursor.getString(index15));
        }
        int columnIndex16 = cursor.getColumnIndex("browsable");
        int index16 = columnIndex16;
        if (columnIndex16 >= 0 && !cursor.isNull(index16)) {
            builder.setBrowsable(cursor.getInt(index16) == 1);
        }
        int columnIndex17 = cursor.getColumnIndex("locked");
        int index17 = columnIndex17;
        if (columnIndex17 >= 0 && !cursor.isNull(index17)) {
            builder.setLocked(cursor.getInt(index17) == 1);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int columnIndex18 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_APP_LINK_COLOR);
            int index18 = columnIndex18;
            if (columnIndex18 >= 0 && !cursor.isNull(index18)) {
                builder.setAppLinkColor(cursor.getInt(index18));
            }
            int columnIndex19 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_APP_LINK_ICON_URI);
            int index19 = columnIndex19;
            if (columnIndex19 >= 0 && !cursor.isNull(index19)) {
                builder.setAppLinkIconUri(Uri.parse(cursor.getString(index19)));
            }
            int columnIndex20 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI);
            int index20 = columnIndex20;
            if (columnIndex20 >= 0 && !cursor.isNull(index20)) {
                builder.setAppLinkIntentUri(Uri.parse(cursor.getString(index20)));
            }
            int columnIndex21 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_APP_LINK_POSTER_ART_URI);
            int index21 = columnIndex21;
            if (columnIndex21 >= 0 && !cursor.isNull(index21)) {
                builder.setAppLinkPosterArtUri(Uri.parse(cursor.getString(index21)));
            }
            int columnIndex22 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_APP_LINK_TEXT);
            int index22 = columnIndex22;
            if (columnIndex22 >= 0 && !cursor.isNull(index22)) {
                builder.setAppLinkText(cursor.getString(index22));
            }
            int columnIndex23 = cursor.getColumnIndex("internal_provider_flag1");
            int index23 = columnIndex23;
            if (columnIndex23 >= 0 && !cursor.isNull(index23)) {
                builder.setInternalProviderFlag1(cursor.getLong(index23));
            }
            int columnIndex24 = cursor.getColumnIndex("internal_provider_flag2");
            int index24 = columnIndex24;
            if (columnIndex24 >= 0 && !cursor.isNull(index24)) {
                builder.setInternalProviderFlag2(cursor.getLong(index24));
            }
            int columnIndex25 = cursor.getColumnIndex("internal_provider_flag3");
            int index25 = columnIndex25;
            if (columnIndex25 >= 0 && !cursor.isNull(index25)) {
                builder.setInternalProviderFlag3(cursor.getLong(index25));
            }
            int columnIndex26 = cursor.getColumnIndex("internal_provider_flag4");
            int index26 = columnIndex26;
            if (columnIndex26 >= 0 && !cursor.isNull(index26)) {
                builder.setInternalProviderFlag4(cursor.getLong(index26));
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            int columnIndex27 = cursor.getColumnIndex("internal_provider_id");
            int index27 = columnIndex27;
            if (columnIndex27 >= 0 && !cursor.isNull(index27)) {
                builder.setInternalProviderId(cursor.getString(index27));
            }
            int columnIndex28 = cursor.getColumnIndex("transient");
            int index28 = columnIndex28;
            if (columnIndex28 >= 0 && !cursor.isNull(index28)) {
                builder.setTransient(cursor.getInt(index28) == 1);
            }
            int columnIndex29 = cursor.getColumnIndex(TvContractCompat.Channels.COLUMN_SYSTEM_APPROVED);
            int index29 = columnIndex29;
            if (columnIndex29 >= 0 && !cursor.isNull(index29)) {
                if (cursor.getInt(index29) == 1) {
                    z = true;
                }
                builder.setSystemApproved(z);
            }
            int columnIndex30 = cursor.getColumnIndex("configuration_display_order");
            int index30 = columnIndex30;
            if (columnIndex30 >= 0 && !cursor.isNull(index30)) {
                builder.setConfigurationDisplayOrder(cursor.getInt(index30));
            }
            int columnIndex31 = cursor.getColumnIndex("system_channel_key");
            int index31 = columnIndex31;
            if (columnIndex31 >= 0 && !cursor.isNull(index31)) {
                builder.setSystemChannelKey(cursor.getString(index31));
            }
        }
        return builder.build();
    }

    private static String[] getProjection() {
        String[] baseColumns = {"_id", TvContractCompat.Channels.COLUMN_DESCRIPTION, TvContractCompat.Channels.COLUMN_DISPLAY_NAME, TvContractCompat.Channels.COLUMN_DISPLAY_NUMBER, "input_id", "internal_provider_data", TvContractCompat.Channels.COLUMN_NETWORK_AFFILIATION, TvContractCompat.Channels.COLUMN_ORIGINAL_NETWORK_ID, "package_name", "searchable", TvContractCompat.Channels.COLUMN_SERVICE_ID, TvContractCompat.Channels.COLUMN_SERVICE_TYPE, TvContractCompat.Channels.COLUMN_TRANSPORT_STREAM_ID, "type", TvContractCompat.Channels.COLUMN_VIDEO_FORMAT, "browsable", "locked"};
        String[] marshmallowColumns = {TvContractCompat.Channels.COLUMN_APP_LINK_COLOR, TvContractCompat.Channels.COLUMN_APP_LINK_ICON_URI, TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI, TvContractCompat.Channels.COLUMN_APP_LINK_POSTER_ART_URI, TvContractCompat.Channels.COLUMN_APP_LINK_TEXT, "internal_provider_flag1", "internal_provider_flag2", "internal_provider_flag3", "internal_provider_flag4"};
        String[] oReleaseColumns = {"internal_provider_id", "transient", TvContractCompat.Channels.COLUMN_SYSTEM_APPROVED, "configuration_display_order", "system_channel_key"};
        if (Build.VERSION.SDK_INT >= 26) {
            return (String[]) CollectionUtils.concatAll(baseColumns, marshmallowColumns, oReleaseColumns);
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

    public String getPackageName() {
        return this.mValues.getAsString("package_name");
    }

    public String getInputId() {
        return this.mValues.getAsString("input_id");
    }

    public String getType() {
        return this.mValues.getAsString("type");
    }

    public String getDisplayNumber() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_DISPLAY_NUMBER);
    }

    public String getDisplayName() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_DISPLAY_NAME);
    }

    public String getDescription() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_DESCRIPTION);
    }

    public String getVideoFormat() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_VIDEO_FORMAT);
    }

    public int getOriginalNetworkId() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.Channels.COLUMN_ORIGINAL_NETWORK_ID);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public int getTransportStreamId() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.Channels.COLUMN_TRANSPORT_STREAM_ID);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public int getServiceId() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.Channels.COLUMN_SERVICE_ID);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public String getAppLinkText() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_APP_LINK_TEXT);
    }

    public int getAppLinkColor() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.Channels.COLUMN_APP_LINK_COLOR);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public Uri getAppLinkIconUri() {
        String uri = this.mValues.getAsString(TvContractCompat.Channels.COLUMN_APP_LINK_ICON_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public Uri getAppLinkPosterArtUri() {
        String uri = this.mValues.getAsString(TvContractCompat.Channels.COLUMN_APP_LINK_POSTER_ART_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public Uri getAppLinkIntentUri() {
        String uri = this.mValues.getAsString(TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI);
        if (uri == null) {
            return null;
        }
        return Uri.parse(uri);
    }

    public Intent getAppLinkIntent() throws URISyntaxException {
        String uri = this.mValues.getAsString(TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI);
        if (uri == null) {
            return null;
        }
        return Intent.parseUri(uri.toString(), 1);
    }

    public String getNetworkAffiliation() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_NETWORK_AFFILIATION);
    }

    public boolean isSearchable() {
        Integer i = this.mValues.getAsInteger("searchable");
        return i == null || i.intValue() == 1;
    }

    public byte[] getInternalProviderDataByteArray() {
        return this.mValues.getAsByteArray("internal_provider_data");
    }

    public String getServiceType() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_SERVICE_TYPE);
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

    public String getInternalProviderId() {
        return this.mValues.getAsString("internal_provider_id");
    }

    public boolean isTransient() {
        Integer i = this.mValues.getAsInteger("transient");
        return i != null && i.intValue() == 1;
    }

    public boolean isBrowsable() {
        Integer i = this.mValues.getAsInteger("browsable");
        return i != null && i.intValue() == 1;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isSystemApproved() {
        Integer i = this.mValues.getAsInteger(TvContractCompat.Channels.COLUMN_SYSTEM_APPROVED);
        return i != null && i.intValue() == 1;
    }

    public int getConfigurationDisplayOrder() {
        return this.mValues.getAsInteger("configuration_display_order").intValue();
    }

    public String getSystemChannelKey() {
        return this.mValues.getAsString("system_channel_key");
    }

    public boolean isLocked() {
        Integer i = this.mValues.getAsInteger("locked");
        return i != null && i.intValue() == 1;
    }

    public int hashCode() {
        return this.mValues.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Channel)) {
            return false;
        }
        return this.mValues.equals(((Channel) other).mValues);
    }

    public String toString() {
        return "Channel{" + this.mValues.toString() + "}";
    }

    public ContentValues toContentValues() {
        return toContentValues(false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ContentValues toContentValues(boolean includeProtectedFields) {
        ContentValues values = new ContentValues(this.mValues);
        if (Build.VERSION.SDK_INT < 23) {
            values.remove(TvContractCompat.Channels.COLUMN_APP_LINK_COLOR);
            values.remove(TvContractCompat.Channels.COLUMN_APP_LINK_TEXT);
            values.remove(TvContractCompat.Channels.COLUMN_APP_LINK_ICON_URI);
            values.remove(TvContractCompat.Channels.COLUMN_APP_LINK_POSTER_ART_URI);
            values.remove(TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI);
            values.remove("internal_provider_flag1");
            values.remove("internal_provider_flag2");
            values.remove("internal_provider_flag3");
            values.remove("internal_provider_flag4");
        }
        if (Build.VERSION.SDK_INT < 26) {
            values.remove("internal_provider_id");
            values.remove("transient");
            values.remove("configuration_display_order");
            values.remove("system_channel_key");
        }
        if (!includeProtectedFields) {
            values.remove("browsable");
            values.remove("locked");
        }
        if (Build.VERSION.SDK_INT < 26 || !includeProtectedFields) {
            values.remove(TvContractCompat.Channels.COLUMN_SYSTEM_APPROVED);
        }
        return values;
    }

    /* renamed from: androidx.tvprovider.media.tv.Channel$Builder */
    public static final class Builder {
        ContentValues mValues;

        public Builder() {
            this.mValues = new ContentValues();
        }

        public Builder(Channel other) {
            this.mValues = new ContentValues(other.mValues);
        }

        /* access modifiers changed from: package-private */
        public Builder setId(long id) {
            this.mValues.put("_id", Long.valueOf(id));
            return this;
        }

        /* access modifiers changed from: package-private */
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder setPackageName(String packageName) {
            this.mValues.put("package_name", packageName);
            return this;
        }

        public Builder setInputId(String inputId) {
            this.mValues.put("input_id", inputId);
            return this;
        }

        public Builder setType(String type) {
            this.mValues.put("type", type);
            return this;
        }

        public Builder setDisplayNumber(String displayNumber) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_DISPLAY_NUMBER, displayNumber);
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_DISPLAY_NAME, displayName);
            return this;
        }

        public Builder setDescription(String description) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_DESCRIPTION, description);
            return this;
        }

        public Builder setVideoFormat(String videoFormat) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_VIDEO_FORMAT, videoFormat);
            return this;
        }

        public Builder setOriginalNetworkId(int originalNetworkId) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_ORIGINAL_NETWORK_ID, Integer.valueOf(originalNetworkId));
            return this;
        }

        public Builder setTransportStreamId(int transportStreamId) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_TRANSPORT_STREAM_ID, Integer.valueOf(transportStreamId));
            return this;
        }

        public Builder setServiceId(int serviceId) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_SERVICE_ID, Integer.valueOf(serviceId));
            return this;
        }

        public Builder setInternalProviderData(byte[] internalProviderData) {
            this.mValues.put("internal_provider_data", internalProviderData);
            return this;
        }

        public Builder setInternalProviderData(String internalProviderData) {
            this.mValues.put("internal_provider_data", internalProviderData.getBytes(Charset.defaultCharset()));
            return this;
        }

        public Builder setAppLinkText(String appLinkText) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_APP_LINK_TEXT, appLinkText);
            return this;
        }

        public Builder setAppLinkColor(int appLinkColor) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_APP_LINK_COLOR, Integer.valueOf(appLinkColor));
            return this;
        }

        public Builder setAppLinkIconUri(Uri appLinkIconUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (appLinkIconUri == null) {
                str = null;
            } else {
                str = appLinkIconUri.toString();
            }
            contentValues.put(TvContractCompat.Channels.COLUMN_APP_LINK_ICON_URI, str);
            return this;
        }

        public Builder setAppLinkPosterArtUri(Uri appLinkPosterArtUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (appLinkPosterArtUri == null) {
                str = null;
            } else {
                str = appLinkPosterArtUri.toString();
            }
            contentValues.put(TvContractCompat.Channels.COLUMN_APP_LINK_POSTER_ART_URI, str);
            return this;
        }

        public Builder setAppLinkIntent(Intent appLinkIntent) {
            return setAppLinkIntentUri(Uri.parse(appLinkIntent.toUri(1)));
        }

        public Builder setAppLinkIntentUri(Uri appLinkIntentUri) {
            String str;
            ContentValues contentValues = this.mValues;
            if (appLinkIntentUri == null) {
                str = null;
            } else {
                str = appLinkIntentUri.toString();
            }
            contentValues.put(TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI, str);
            return this;
        }

        public Builder setNetworkAffiliation(String networkAffiliation) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_NETWORK_AFFILIATION, networkAffiliation);
            return this;
        }

        public Builder setSearchable(boolean searchable) {
            this.mValues.put("searchable", Integer.valueOf(searchable));
            return this;
        }

        public Builder setServiceType(String serviceType) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_SERVICE_TYPE, serviceType);
            return this;
        }

        public Builder setInternalProviderFlag1(long flag) {
            this.mValues.put("internal_provider_flag1", Long.valueOf(flag));
            return this;
        }

        public Builder setInternalProviderFlag2(long flag) {
            this.mValues.put("internal_provider_flag2", Long.valueOf(flag));
            return this;
        }

        public Builder setInternalProviderFlag3(long flag) {
            this.mValues.put("internal_provider_flag3", Long.valueOf(flag));
            return this;
        }

        public Builder setInternalProviderFlag4(long flag) {
            this.mValues.put("internal_provider_flag4", Long.valueOf(flag));
            return this;
        }

        public Builder setInternalProviderId(String internalProviderId) {
            this.mValues.put("internal_provider_id", internalProviderId);
            return this;
        }

        public Builder setTransient(boolean value) {
            this.mValues.put("transient", Integer.valueOf(value));
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder setBrowsable(boolean value) {
            this.mValues.put("browsable", Integer.valueOf(value));
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder setSystemApproved(boolean value) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_SYSTEM_APPROVED, Integer.valueOf(value));
            return this;
        }

        public Builder setConfigurationDisplayOrder(int value) {
            this.mValues.put("configuration_display_order", Integer.valueOf(value));
            return this;
        }

        public Builder setSystemChannelKey(String value) {
            this.mValues.put("system_channel_key", value);
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder setLocked(boolean value) {
            this.mValues.put("locked", Integer.valueOf(value));
            return this;
        }

        public Channel build() {
            return new Channel(this);
        }
    }
}
