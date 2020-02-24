package com.google.android.exoplayer2.metadata.icy;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;

public final class IcyDecoder implements MetadataDecoder {
    private static final Pattern METADATA_ELEMENT = Pattern.compile("(.+?)='(.+?)';");
    private static final String STREAM_KEY_NAME = "streamtitle";
    private static final String STREAM_KEY_URL = "streamurl";
    private static final String TAG = "IcyDecoder";

    @Nullable
    public Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer buffer = inputBuffer.data;
        return decode(Util.fromUtf8Bytes(buffer.array(), 0, buffer.limit()));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0035, code lost:
        if (r4.equals(com.google.android.exoplayer2.metadata.icy.IcyDecoder.STREAM_KEY_NAME) == false) goto L_0x0043;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    @android.support.annotation.Nullable
    @android.support.annotation.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.metadata.Metadata decode(java.lang.String r12) {
        /*
            r11 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            java.util.regex.Pattern r3 = com.google.android.exoplayer2.metadata.icy.IcyDecoder.METADATA_ELEMENT
            java.util.regex.Matcher r3 = r3.matcher(r12)
        L_0x0009:
            boolean r4 = r3.find(r2)
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x006e
            java.lang.String r4 = r3.group(r6)
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r4)
            r7 = 2
            java.lang.String r7 = r3.group(r7)
            r8 = -1
            int r9 = r4.hashCode()
            r10 = -315603473(0xffffffffed3045ef, float:-3.409619E27)
            if (r9 == r10) goto L_0x0038
            r10 = 1646559960(0x622482d8, float:7.586736E20)
            if (r9 == r10) goto L_0x002e
        L_0x002d:
            goto L_0x0043
        L_0x002e:
            java.lang.String r9 = "streamtitle"
            boolean r9 = r4.equals(r9)
            if (r9 == 0) goto L_0x002d
            goto L_0x0044
        L_0x0038:
            java.lang.String r5 = "streamurl"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x002d
            r5 = 1
            goto L_0x0044
        L_0x0043:
            r5 = -1
        L_0x0044:
            if (r5 == 0) goto L_0x0067
            if (r5 == r6) goto L_0x0065
            java.lang.String r5 = "Unrecognized ICY tag: "
            java.lang.String r6 = java.lang.String.valueOf(r0)
            int r8 = r6.length()
            if (r8 == 0) goto L_0x0059
            java.lang.String r5 = r5.concat(r6)
            goto L_0x005f
        L_0x0059:
            java.lang.String r6 = new java.lang.String
            r6.<init>(r5)
            r5 = r6
        L_0x005f:
            java.lang.String r6 = "IcyDecoder"
            com.google.android.exoplayer2.util.Log.m30w(r6, r5)
            goto L_0x0069
        L_0x0065:
            r1 = r7
            goto L_0x0069
        L_0x0067:
            r0 = r7
        L_0x0069:
            int r2 = r3.end()
            goto L_0x0009
        L_0x006e:
            if (r0 != 0) goto L_0x0075
            if (r1 == 0) goto L_0x0073
            goto L_0x0075
        L_0x0073:
            r4 = 0
            goto L_0x0083
        L_0x0075:
            com.google.android.exoplayer2.metadata.Metadata r4 = new com.google.android.exoplayer2.metadata.Metadata
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r6 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r6]
            com.google.android.exoplayer2.metadata.icy.IcyInfo r7 = new com.google.android.exoplayer2.metadata.icy.IcyInfo
            r7.<init>(r0, r1)
            r6[r5] = r7
            r4.<init>(r6)
        L_0x0083:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.icy.IcyDecoder.decode(java.lang.String):com.google.android.exoplayer2.metadata.Metadata");
    }
}
