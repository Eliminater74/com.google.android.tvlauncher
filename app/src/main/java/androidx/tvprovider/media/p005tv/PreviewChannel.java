package androidx.tvprovider.media.p005tv;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.tv.TvContract;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Objects;

/* renamed from: androidx.tvprovider.media.tv.PreviewChannel */
public class PreviewChannel {
    private static final long INVALID_CHANNEL_ID = -1;
    private static final int IS_BROWSABLE = 1;
    private static final String TAG = "PreviewChannel";
    private boolean mLogoChanged;
    private volatile boolean mLogoFetched;
    private volatile Bitmap mLogoImage;
    private Uri mLogoUri;
    ContentValues mValues;

    PreviewChannel(Builder builder) {
        this.mValues = builder.mValues;
        this.mLogoImage = builder.mLogoBitmap;
        this.mLogoUri = builder.mLogoUri;
        this.mLogoChanged = (this.mLogoImage == null && this.mLogoUri == null) ? false : true;
    }

    public static PreviewChannel fromCursor(Cursor cursor) {
        Builder builder = new Builder();
        builder.setId((long) cursor.getInt(0));
        builder.setPackageName(cursor.getString(1));
        builder.setType(cursor.getString(2));
        builder.setDisplayName(cursor.getString(3));
        builder.setDescription(cursor.getString(4));
        builder.setAppLinkIntentUri(Uri.parse(cursor.getString(5)));
        builder.setInternalProviderId(cursor.getString(6));
        builder.setInternalProviderData(cursor.getBlob(7));
        builder.setInternalProviderFlag1(cursor.getLong(8));
        builder.setInternalProviderFlag2(cursor.getLong(9));
        builder.setInternalProviderFlag3(cursor.getLong(10));
        builder.setInternalProviderFlag4(cursor.getLong(11));
        return builder.build();
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

    public String getType() {
        return this.mValues.getAsString("type");
    }

    public CharSequence getDisplayName() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_DISPLAY_NAME);
    }

    public CharSequence getDescription() {
        return this.mValues.getAsString(TvContractCompat.Channels.COLUMN_DESCRIPTION);
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

    @WorkerThread
    public Bitmap getLogo(Context context) {
        if (!this.mLogoFetched && this.mLogoImage == null) {
            try {
                this.mLogoImage = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(TvContract.buildChannelLogoUri(getId())));
            } catch (SQLiteException | FileNotFoundException e) {
                Log.e(TAG, "Logo for preview channel (ID:" + getId() + ") not found.", e);
            }
            this.mLogoFetched = true;
        }
        return this.mLogoImage;
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isLogoChanged() {
        return this.mLogoChanged;
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Uri getLogoUri() {
        return this.mLogoUri;
    }

    public byte[] getInternalProviderDataByteArray() {
        return this.mValues.getAsByteArray("internal_provider_data");
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

    public boolean isBrowsable() {
        Integer i = this.mValues.getAsInteger("browsable");
        return i != null && i.intValue() == 1;
    }

    public int hashCode() {
        return this.mValues.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof PreviewChannel)) {
            return false;
        }
        return this.mValues.equals(((PreviewChannel) other).mValues);
    }

    public boolean hasAnyUpdatedValues(PreviewChannel update) {
        for (String key : update.mValues.keySet()) {
            if (!Objects.deepEquals(update.mValues.get(key), this.mValues.get(key))) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "Channel{" + this.mValues.toString() + "}";
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ContentValues toContentValues() {
        return new ContentValues(this.mValues);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* renamed from: androidx.tvprovider.media.tv.PreviewChannel$Columns */
    public static class Columns {
        public static final int COL_APP_LINK_INTENT_URI = 5;
        public static final int COL_DESCRIPTION = 4;
        public static final int COL_DISPLAY_NAME = 3;
        public static final int COL_ID = 0;
        public static final int COL_INTERNAL_PROVIDER_DATA = 7;
        public static final int COL_INTERNAL_PROVIDER_FLAG1 = 8;
        public static final int COL_INTERNAL_PROVIDER_FLAG2 = 9;
        public static final int COL_INTERNAL_PROVIDER_FLAG3 = 10;
        public static final int COL_INTERNAL_PROVIDER_FLAG4 = 11;
        public static final int COL_INTERNAL_PROVIDER_ID = 6;
        public static final int COL_PACKAGE_NAME = 1;
        public static final int COL_TYPE = 2;
        public static final String[] PROJECTION = {"_id", "package_name", "type", TvContractCompat.Channels.COLUMN_DISPLAY_NAME, TvContractCompat.Channels.COLUMN_DESCRIPTION, TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI, "internal_provider_id", "internal_provider_data", "internal_provider_flag1", "internal_provider_flag2", "internal_provider_flag3", "internal_provider_flag4"};

        private Columns() {
        }
    }

    /* renamed from: androidx.tvprovider.media.tv.PreviewChannel$Builder */
    public static final class Builder {
        Bitmap mLogoBitmap;
        Uri mLogoUri;
        ContentValues mValues;

        public Builder() {
            this.mValues = new ContentValues();
        }

        public Builder(PreviewChannel other) {
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

        /* access modifiers changed from: package-private */
        public Builder setType(String type) {
            this.mValues.put("type", type);
            return this;
        }

        public Builder setDisplayName(CharSequence displayName) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_DISPLAY_NAME, displayName.toString());
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.mValues.put(TvContractCompat.Channels.COLUMN_DESCRIPTION, description.toString());
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

        public Builder setInternalProviderId(String internalProviderId) {
            this.mValues.put("internal_provider_id", internalProviderId);
            return this;
        }

        public Builder setInternalProviderData(byte[] internalProviderData) {
            this.mValues.put("internal_provider_data", internalProviderData);
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

        public Builder setLogo(@NonNull Bitmap logoImage) {
            this.mLogoBitmap = logoImage;
            this.mLogoUri = null;
            return this;
        }

        public Builder setLogo(@NonNull Uri logoUri) {
            this.mLogoUri = logoUri;
            this.mLogoBitmap = null;
            return this;
        }

        public PreviewChannel build() {
            setType(TvContractCompat.Channels.TYPE_PREVIEW);
            if (TextUtils.isEmpty(this.mValues.getAsString(TvContractCompat.Channels.COLUMN_DISPLAY_NAME))) {
                throw new IllegalStateException("Need channel name. Use method setDisplayName(String) to set it.");
            } else if (!TextUtils.isEmpty(this.mValues.getAsString(TvContractCompat.Channels.COLUMN_APP_LINK_INTENT_URI))) {
                return new PreviewChannel(this);
            } else {
                throw new IllegalStateException("Need app link intent uri for channel. Use method setAppLinkIntent or setAppLinkIntentUri to set it.");
            }
        }
    }
}
