package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DownloadRequest implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new Parcelable.Creator<DownloadRequest>() {
        public DownloadRequest createFromParcel(Parcel in) {
            return new DownloadRequest(in);
        }

        public DownloadRequest[] newArray(int size) {
            return new DownloadRequest[size];
        }
    };
    public static final String TYPE_DASH = "dash";
    public static final String TYPE_HLS = "hls";
    public static final String TYPE_PROGRESSIVE = "progressive";
    public static final String TYPE_SS = "ss";
    @Nullable
    public final String customCacheKey;
    public final byte[] data;

    /* renamed from: id */
    public final String f89id;
    public final List<StreamKey> streamKeys;
    public final String type;
    public final Uri uri;

    public static class UnsupportedRequestException extends IOException {
    }

    public DownloadRequest(String id, String type2, Uri uri2, List<StreamKey> streamKeys2, @Nullable String customCacheKey2, @Nullable byte[] data2) {
        if (TYPE_DASH.equals(type2) || TYPE_HLS.equals(type2) || TYPE_SS.equals(type2)) {
            boolean z = customCacheKey2 == null;
            String valueOf = String.valueOf(type2);
            Assertions.checkArgument(z, valueOf.length() != 0 ? "customCacheKey must be null for type: ".concat(valueOf) : new String("customCacheKey must be null for type: "));
        }
        this.f89id = id;
        this.type = type2;
        this.uri = uri2;
        ArrayList<StreamKey> mutableKeys = new ArrayList<>(streamKeys2);
        Collections.sort(mutableKeys);
        this.streamKeys = Collections.unmodifiableList(mutableKeys);
        this.customCacheKey = customCacheKey2;
        this.data = data2 != null ? Arrays.copyOf(data2, data2.length) : Util.EMPTY_BYTE_ARRAY;
    }

    DownloadRequest(Parcel in) {
        this.f89id = (String) Util.castNonNull(in.readString());
        this.type = (String) Util.castNonNull(in.readString());
        this.uri = Uri.parse((String) Util.castNonNull(in.readString()));
        int streamKeyCount = in.readInt();
        ArrayList<StreamKey> mutableStreamKeys = new ArrayList<>(streamKeyCount);
        for (int i = 0; i < streamKeyCount; i++) {
            mutableStreamKeys.add((StreamKey) in.readParcelable(StreamKey.class.getClassLoader()));
        }
        this.streamKeys = Collections.unmodifiableList(mutableStreamKeys);
        this.customCacheKey = in.readString();
        this.data = new byte[in.readInt()];
        in.readByteArray(this.data);
    }

    public DownloadRequest copyWithId(String id) {
        return new DownloadRequest(id, this.type, this.uri, this.streamKeys, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithMergedRequest(DownloadRequest newRequest) {
        List<StreamKey> mergedKeys;
        Assertions.checkArgument(this.f89id.equals(newRequest.f89id));
        Assertions.checkArgument(this.type.equals(newRequest.type));
        if (this.streamKeys.isEmpty() || newRequest.streamKeys.isEmpty()) {
            mergedKeys = Collections.emptyList();
        } else {
            mergedKeys = new ArrayList<>(this.streamKeys);
            for (int i = 0; i < newRequest.streamKeys.size(); i++) {
                StreamKey newKey = newRequest.streamKeys.get(i);
                if (!mergedKeys.contains(newKey)) {
                    mergedKeys.add(newKey);
                }
            }
        }
        return new DownloadRequest(this.f89id, this.type, newRequest.uri, mergedKeys, newRequest.customCacheKey, newRequest.data);
    }

    public String toString() {
        String str = this.type;
        String str2 = this.f89id;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof DownloadRequest)) {
            return false;
        }
        DownloadRequest that = (DownloadRequest) o;
        if (!this.f89id.equals(that.f89id) || !this.type.equals(that.type) || !this.uri.equals(that.uri) || !this.streamKeys.equals(that.streamKeys) || !Util.areEqual(this.customCacheKey, that.customCacheKey) || !Arrays.equals(this.data, that.data)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int result = ((((((((this.type.hashCode() * 31) + this.f89id.hashCode()) * 31) + this.type.hashCode()) * 31) + this.uri.hashCode()) * 31) + this.streamKeys.hashCode()) * 31;
        String str = this.customCacheKey;
        return ((result + (str != null ? str.hashCode() : 0)) * 31) + Arrays.hashCode(this.data);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.f89id);
        dest.writeString(this.type);
        dest.writeString(this.uri.toString());
        dest.writeInt(this.streamKeys.size());
        for (int i = 0; i < this.streamKeys.size(); i++) {
            dest.writeParcelable(this.streamKeys.get(i), 0);
        }
        dest.writeString(this.customCacheKey);
        dest.writeInt(this.data.length);
        dest.writeByteArray(this.data);
    }
}
