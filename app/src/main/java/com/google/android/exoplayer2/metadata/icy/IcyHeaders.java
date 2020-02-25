package com.google.android.exoplayer2.metadata.icy;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class IcyHeaders implements Metadata.Entry {
    public static final Parcelable.Creator<IcyHeaders> CREATOR = new Parcelable.Creator<IcyHeaders>() {
        public IcyHeaders createFromParcel(Parcel in) {
            return new IcyHeaders(in);
        }

        public IcyHeaders[] newArray(int size) {
            return new IcyHeaders[size];
        }
    };
    public static final String REQUEST_HEADER_ENABLE_METADATA_NAME = "Icy-MetaData";
    public static final String REQUEST_HEADER_ENABLE_METADATA_VALUE = "1";
    private static final String RESPONSE_HEADER_BITRATE = "icy-br";
    private static final String RESPONSE_HEADER_GENRE = "icy-genre";
    private static final String RESPONSE_HEADER_METADATA_INTERVAL = "icy-metaint";
    private static final String RESPONSE_HEADER_NAME = "icy-name";
    private static final String RESPONSE_HEADER_PUB = "icy-pub";
    private static final String RESPONSE_HEADER_URL = "icy-url";
    private static final String TAG = "IcyHeaders";
    public final int bitrate;
    @Nullable
    public final String genre;
    public final boolean isPublic;
    public final int metadataInterval;
    @Nullable
    public final String name;
    @Nullable
    public final String url;

    public IcyHeaders(int bitrate2, @Nullable String genre2, @Nullable String name2, @Nullable String url2, boolean isPublic2, int metadataInterval2) {
        Assertions.checkArgument(metadataInterval2 == -1 || metadataInterval2 > 0);
        this.bitrate = bitrate2;
        this.genre = genre2;
        this.name = name2;
        this.url = url2;
        this.isPublic = isPublic2;
        this.metadataInterval = metadataInterval2;
    }

    IcyHeaders(Parcel in) {
        this.bitrate = in.readInt();
        this.genre = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.isPublic = Util.readBoolean(in);
        this.metadataInterval = in.readInt();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.metadata.icy.IcyHeaders parse(java.util.Map<java.lang.String, java.util.List<java.lang.String>> r20) {
        /*
            r1 = r20
            java.lang.String r2 = "Invalid metadata interval: "
            r3 = 0
            r4 = -1
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = -1
            java.lang.String r0 = "icy-br"
            java.lang.Object r0 = r1.get(r0)
            r10 = r0
            java.util.List r10 = (java.util.List) r10
            java.lang.String r11 = "IcyHeaders"
            r12 = 0
            if (r10 == 0) goto L_0x0063
            java.lang.Object r0 = r10.get(r12)
            r13 = r0
            java.lang.String r13 = (java.lang.String) r13
            int r0 = java.lang.Integer.parseInt(r13)     // Catch:{ NumberFormatException -> 0x0048 }
            int r4 = r0 * 1000
            if (r4 <= 0) goto L_0x002b
            r0 = 1
            r3 = r0
            goto L_0x0047
        L_0x002b:
            java.lang.String r0 = "Invalid bitrate: "
            java.lang.String r14 = java.lang.String.valueOf(r13)     // Catch:{ NumberFormatException -> 0x0048 }
            int r15 = r14.length()     // Catch:{ NumberFormatException -> 0x0048 }
            if (r15 == 0) goto L_0x003c
            java.lang.String r0 = r0.concat(r14)     // Catch:{ NumberFormatException -> 0x0048 }
            goto L_0x0042
        L_0x003c:
            java.lang.String r14 = new java.lang.String     // Catch:{ NumberFormatException -> 0x0048 }
            r14.<init>(r0)     // Catch:{ NumberFormatException -> 0x0048 }
            r0 = r14
        L_0x0042:
            com.google.android.exoplayer2.util.Log.m30w(r11, r0)     // Catch:{ NumberFormatException -> 0x0048 }
            r0 = -1
            r4 = r0
        L_0x0047:
            goto L_0x0063
        L_0x0048:
            r0 = move-exception
            java.lang.String r14 = "Invalid bitrate header: "
            java.lang.String r15 = java.lang.String.valueOf(r13)
            int r16 = r15.length()
            if (r16 == 0) goto L_0x005b
            java.lang.String r14 = r14.concat(r15)
            r15 = r14
            goto L_0x0060
        L_0x005b:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r14)
        L_0x0060:
            com.google.android.exoplayer2.util.Log.m30w(r11, r15)
        L_0x0063:
            java.lang.String r0 = "icy-genre"
            java.lang.Object r0 = r1.get(r0)
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x0075
            java.lang.Object r10 = r0.get(r12)
            r5 = r10
            java.lang.String r5 = (java.lang.String) r5
            r3 = 1
        L_0x0075:
            java.lang.String r10 = "icy-name"
            java.lang.Object r10 = r1.get(r10)
            r0 = r10
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x0088
            java.lang.Object r10 = r0.get(r12)
            r6 = r10
            java.lang.String r6 = (java.lang.String) r6
            r3 = 1
        L_0x0088:
            java.lang.String r10 = "icy-url"
            java.lang.Object r10 = r1.get(r10)
            r0 = r10
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x009b
            java.lang.Object r10 = r0.get(r12)
            r7 = r10
            java.lang.String r7 = (java.lang.String) r7
            r3 = 1
        L_0x009b:
            java.lang.String r10 = "icy-pub"
            java.lang.Object r10 = r1.get(r10)
            r0 = r10
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x00b3
            java.lang.Object r10 = r0.get(r12)
            java.lang.String r10 = (java.lang.String) r10
            java.lang.String r13 = "1"
            boolean r8 = r10.equals(r13)
            r3 = 1
        L_0x00b3:
            java.lang.String r10 = "icy-metaint"
            java.lang.Object r10 = r1.get(r10)
            java.util.List r10 = (java.util.List) r10
            if (r10 == 0) goto L_0x0101
            java.lang.Object r0 = r10.get(r12)
            r12 = r0
            java.lang.String r12 = (java.lang.String) r12
            int r0 = java.lang.Integer.parseInt(r12)     // Catch:{ NumberFormatException -> 0x00e8 }
            r9 = r0
            if (r9 <= 0) goto L_0x00ce
            r0 = 1
            r3 = r0
            goto L_0x00e7
        L_0x00ce:
            java.lang.String r0 = java.lang.String.valueOf(r12)     // Catch:{ NumberFormatException -> 0x00e8 }
            int r13 = r0.length()     // Catch:{ NumberFormatException -> 0x00e8 }
            if (r13 == 0) goto L_0x00dd
            java.lang.String r0 = r2.concat(r0)     // Catch:{ NumberFormatException -> 0x00e8 }
            goto L_0x00e2
        L_0x00dd:
            java.lang.String r0 = new java.lang.String     // Catch:{ NumberFormatException -> 0x00e8 }
            r0.<init>(r2)     // Catch:{ NumberFormatException -> 0x00e8 }
        L_0x00e2:
            com.google.android.exoplayer2.util.Log.m30w(r11, r0)     // Catch:{ NumberFormatException -> 0x00e8 }
            r0 = -1
            r9 = r0
        L_0x00e7:
            goto L_0x0101
        L_0x00e8:
            r0 = move-exception
            java.lang.String r13 = java.lang.String.valueOf(r12)
            int r14 = r13.length()
            if (r14 == 0) goto L_0x00f8
            java.lang.String r2 = r2.concat(r13)
            goto L_0x00fe
        L_0x00f8:
            java.lang.String r13 = new java.lang.String
            r13.<init>(r2)
            r2 = r13
        L_0x00fe:
            com.google.android.exoplayer2.util.Log.m30w(r11, r2)
        L_0x0101:
            if (r3 == 0) goto L_0x0114
            com.google.android.exoplayer2.metadata.icy.IcyHeaders r0 = new com.google.android.exoplayer2.metadata.icy.IcyHeaders
            r13 = r0
            r14 = r4
            r15 = r5
            r16 = r6
            r17 = r7
            r18 = r8
            r19 = r9
            r13.<init>(r14, r15, r16, r17, r18, r19)
            goto L_0x0115
        L_0x0114:
            r0 = 0
        L_0x0115:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.icy.IcyHeaders.parse(java.util.Map):com.google.android.exoplayer2.metadata.icy.IcyHeaders");
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IcyHeaders other = (IcyHeaders) obj;
        if (this.bitrate != other.bitrate || !Util.areEqual(this.genre, other.genre) || !Util.areEqual(this.name, other.name) || !Util.areEqual(this.url, other.url) || this.isPublic != other.isPublic || this.metadataInterval != other.metadataInterval) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = ((17 * 31) + this.bitrate) * 31;
        String str = this.genre;
        int i = 0;
        int result2 = (result + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.name;
        int result3 = (result2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.url;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return ((((result3 + i) * 31) + (this.isPublic ? 1 : 0)) * 31) + this.metadataInterval;
    }

    public String toString() {
        String str = this.name;
        String str2 = this.genre;
        int i = this.bitrate;
        int i2 = this.metadataInterval;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 80 + String.valueOf(str2).length());
        sb.append("IcyHeaders: name=\"");
        sb.append(str);
        sb.append("\", genre=\"");
        sb.append(str2);
        sb.append("\", bitrate=");
        sb.append(i);
        sb.append(", metadataInterval=");
        sb.append(i2);
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bitrate);
        dest.writeString(this.genre);
        dest.writeString(this.name);
        dest.writeString(this.url);
        Util.writeBoolean(dest, this.isPublic);
        dest.writeInt(this.metadataInterval);
    }

    public int describeContents() {
        return 0;
    }
}
