package android.support.p001v4.media;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;

@SuppressLint({"BanParcelableUsage"})
/* renamed from: android.support.v4.media.MediaDescriptionCompat */
public final class MediaDescriptionCompat implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator<MediaDescriptionCompat>() {
        public MediaDescriptionCompat createFromParcel(Parcel in) {
            if (Build.VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat(in);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescription.CREATOR.createFromParcel(in));
        }

        public MediaDescriptionCompat[] newArray(int size) {
            return new MediaDescriptionCompat[size];
        }
    };
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
    public static final long STATUS_DOWNLOADED = 2;
    public static final long STATUS_DOWNLOADING = 1;
    public static final long STATUS_NOT_DOWNLOADED = 0;
    private final CharSequence mDescription;
    private MediaDescription mDescriptionFwk;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    MediaDescriptionCompat(String mediaId, CharSequence title, CharSequence subtitle, CharSequence description, Bitmap icon, Uri iconUri, Bundle extras, Uri mediaUri) {
        this.mMediaId = mediaId;
        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mDescription = description;
        this.mIcon = icon;
        this.mIconUri = iconUri;
        this.mExtras = extras;
        this.mMediaUri = mediaUri;
    }

    MediaDescriptionCompat(Parcel in) {
        this.mMediaId = in.readString();
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        ClassLoader loader = getClass().getClassLoader();
        this.mIcon = (Bitmap) in.readParcelable(loader);
        this.mIconUri = (Uri) in.readParcelable(loader);
        this.mExtras = in.readBundle(loader);
        this.mMediaUri = (Uri) in.readParcelable(loader);
    }

    @Nullable
    public String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Nullable
    public CharSequence getDescription() {
        return this.mDescription;
    }

    @Nullable
    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @Nullable
    public Uri getIconUri() {
        return this.mIconUri;
    }

    @Nullable
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (Build.VERSION.SDK_INT < 21) {
            dest.writeString(this.mMediaId);
            TextUtils.writeToParcel(this.mTitle, dest, flags);
            TextUtils.writeToParcel(this.mSubtitle, dest, flags);
            TextUtils.writeToParcel(this.mDescription, dest, flags);
            dest.writeParcelable(this.mIcon, flags);
            dest.writeParcelable(this.mIconUri, flags);
            dest.writeBundle(this.mExtras);
            dest.writeParcelable(this.mMediaUri, flags);
            return;
        }
        ((MediaDescription) getMediaDescription()).writeToParcel(dest, flags);
    }

    public String toString() {
        return ((Object) this.mTitle) + ", " + ((Object) this.mSubtitle) + ", " + ((Object) this.mDescription);
    }

    public Object getMediaDescription() {
        if (this.mDescriptionFwk != null || Build.VERSION.SDK_INT < 21) {
            return this.mDescriptionFwk;
        }
        MediaDescription.Builder bob = new MediaDescription.Builder();
        bob.setMediaId(this.mMediaId);
        bob.setTitle(this.mTitle);
        bob.setSubtitle(this.mSubtitle);
        bob.setDescription(this.mDescription);
        bob.setIconBitmap(this.mIcon);
        bob.setIconUri(this.mIconUri);
        Bundle extras = this.mExtras;
        if (Build.VERSION.SDK_INT < 23 && this.mMediaUri != null) {
            if (extras == null) {
                extras = new Bundle();
                extras.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
            }
            extras.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.mMediaUri);
        }
        bob.setExtras(extras);
        if (Build.VERSION.SDK_INT >= 23) {
            bob.setMediaUri(this.mMediaUri);
        }
        this.mDescriptionFwk = bob.build();
        return this.mDescriptionFwk;
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.p001v4.media.MediaDescriptionCompat fromMediaDescription(java.lang.Object r8) {
        /*
            if (r8 == 0) goto L_0x0083
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 < r1) goto L_0x0083
            android.support.v4.media.MediaDescriptionCompat$Builder r0 = new android.support.v4.media.MediaDescriptionCompat$Builder
            r0.<init>()
            r1 = r8
            android.media.MediaDescription r1 = (android.media.MediaDescription) r1
            java.lang.String r2 = r1.getMediaId()
            r0.setMediaId(r2)
            java.lang.CharSequence r2 = r1.getTitle()
            r0.setTitle(r2)
            java.lang.CharSequence r2 = r1.getSubtitle()
            r0.setSubtitle(r2)
            java.lang.CharSequence r2 = r1.getDescription()
            r0.setDescription(r2)
            android.graphics.Bitmap r2 = r1.getIconBitmap()
            r0.setIconBitmap(r2)
            android.net.Uri r2 = r1.getIconUri()
            r0.setIconUri(r2)
            android.os.Bundle r2 = r1.getExtras()
            r3 = 0
            java.lang.String r4 = "android.support.v4.media.description.MEDIA_URI"
            if (r2 == 0) goto L_0x004d
            android.support.p001v4.media.session.MediaSessionCompat.ensureClassLoader(r2)
            android.os.Parcelable r5 = r2.getParcelable(r4)
            r3 = r5
            android.net.Uri r3 = (android.net.Uri) r3
        L_0x004d:
            if (r3 == 0) goto L_0x0066
            java.lang.String r5 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            boolean r6 = r2.containsKey(r5)
            if (r6 == 0) goto L_0x0060
            int r6 = r2.size()
            r7 = 2
            if (r6 != r7) goto L_0x0060
            r2 = 0
            goto L_0x0066
        L_0x0060:
            r2.remove(r4)
            r2.remove(r5)
        L_0x0066:
            r0.setExtras(r2)
            if (r3 == 0) goto L_0x006f
            r0.setMediaUri(r3)
            goto L_0x007c
        L_0x006f:
            int r4 = android.os.Build.VERSION.SDK_INT
            r5 = 23
            if (r4 < r5) goto L_0x007c
            android.net.Uri r4 = r1.getMediaUri()
            r0.setMediaUri(r4)
        L_0x007c:
            android.support.v4.media.MediaDescriptionCompat r4 = r0.build()
            r4.mDescriptionFwk = r1
            return r4
        L_0x0083:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.media.MediaDescriptionCompat.fromMediaDescription(java.lang.Object):android.support.v4.media.MediaDescriptionCompat");
    }

    /* renamed from: android.support.v4.media.MediaDescriptionCompat$Builder */
    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public Builder setMediaId(@Nullable String mediaId) {
            this.mMediaId = mediaId;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence subtitle) {
            this.mSubtitle = subtitle;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence description) {
            this.mDescription = description;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setIconUri(@Nullable Uri iconUri) {
            this.mIconUri = iconUri;
            return this;
        }

        public Builder setExtras(@Nullable Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri mediaUri) {
            this.mMediaUri = mediaUri;
            return this;
        }

        public MediaDescriptionCompat build() {
            return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }
    }
}
