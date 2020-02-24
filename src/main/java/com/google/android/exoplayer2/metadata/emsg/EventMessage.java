package com.google.android.exoplayer2.metadata.emsg;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class EventMessage implements Metadata.Entry {
    public static final Parcelable.Creator<EventMessage> CREATOR = new Parcelable.Creator<EventMessage>() {
        public EventMessage createFromParcel(Parcel in) {
            return new EventMessage(in);
        }

        public EventMessage[] newArray(int size) {
            return new EventMessage[size];
        }
    };
    public final long durationMs;
    private int hashCode;

    /* renamed from: id */
    public final long f79id;
    public final byte[] messageData;
    public final String schemeIdUri;
    public final String value;

    public EventMessage(String schemeIdUri2, String value2, long durationMs2, long id, byte[] messageData2) {
        this.schemeIdUri = schemeIdUri2;
        this.value = value2;
        this.durationMs = durationMs2;
        this.f79id = id;
        this.messageData = messageData2;
    }

    EventMessage(Parcel in) {
        this.schemeIdUri = (String) Util.castNonNull(in.readString());
        this.value = (String) Util.castNonNull(in.readString());
        this.durationMs = in.readLong();
        this.f79id = in.readLong();
        this.messageData = (byte[]) Util.castNonNull(in.createByteArray());
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int i = 17 * 31;
            String str = this.schemeIdUri;
            int i2 = 0;
            int result = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.value;
            if (str2 != null) {
                i2 = str2.hashCode();
            }
            long j = this.durationMs;
            long j2 = this.f79id;
            this.hashCode = ((((((result + i2) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + Arrays.hashCode(this.messageData);
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EventMessage other = (EventMessage) obj;
        if (this.durationMs != other.durationMs || this.f79id != other.f79id || !Util.areEqual(this.schemeIdUri, other.schemeIdUri) || !Util.areEqual(this.value, other.value) || !Arrays.equals(this.messageData, other.messageData)) {
            return false;
        }
        return true;
    }

    public String toString() {
        String str = this.schemeIdUri;
        long j = this.f79id;
        String str2 = this.value;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 46 + String.valueOf(str2).length());
        sb.append("EMSG: scheme=");
        sb.append(str);
        sb.append(", id=");
        sb.append(j);
        sb.append(", value=");
        sb.append(str2);
        return sb.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schemeIdUri);
        dest.writeString(this.value);
        dest.writeLong(this.durationMs);
        dest.writeLong(this.f79id);
        dest.writeByteArray(this.messageData);
    }
}
