package com.google.android.exoplayer2.drm;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@TargetApi(18)
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private static final int MAX_MANUAL_REDIRECTS = 5;
    private final HttpDataSource.Factory dataSourceFactory;
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    public HttpMediaDrmCallback(String defaultLicenseUrl2, HttpDataSource.Factory dataSourceFactory2) {
        this(defaultLicenseUrl2, false, dataSourceFactory2);
    }

    public HttpMediaDrmCallback(String defaultLicenseUrl2, boolean forceDefaultLicenseUrl2, HttpDataSource.Factory dataSourceFactory2) {
        this.dataSourceFactory = dataSourceFactory2;
        this.defaultLicenseUrl = defaultLicenseUrl2;
        this.forceDefaultLicenseUrl = forceDefaultLicenseUrl2;
        this.keyRequestProperties = new HashMap();
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x006e A[SYNTHETIC, Splitter:B:27:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0079 A[LOOP:1: B:7:0x002e->B:33:0x0079, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0081 A[EDGE_INSN: B:34:0x0081->B:35:? ?: BREAK  , SYNTHETIC, Splitter:B:34:0x0081] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] executePost(com.google.android.exoplayer2.upstream.HttpDataSource.Factory r15, java.lang.String r16, byte[] r17, @android.support.annotation.Nullable java.util.Map<java.lang.String, java.lang.String> r18) throws java.io.IOException {
        /*
            com.google.android.exoplayer2.upstream.HttpDataSource r1 = r15.createDataSource()
            if (r18 == 0) goto L_0x002a
            java.util.Set r0 = r18.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x000e:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x002a
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r4 = r2.getValue()
            java.lang.String r4 = (java.lang.String) r4
            r1.setRequestProperty(r3, r4)
            goto L_0x000e
        L_0x002a:
            r0 = 0
            r2 = r16
            r3 = r0
        L_0x002e:
            com.google.android.exoplayer2.upstream.DataSpec r0 = new com.google.android.exoplayer2.upstream.DataSpec
            android.net.Uri r5 = android.net.Uri.parse(r2)
            r7 = 0
            r9 = 0
            r11 = -1
            r13 = 0
            r14 = 1
            r4 = r0
            r6 = r17
            r4.<init>(r5, r6, r7, r9, r11, r13, r14)
            com.google.android.exoplayer2.upstream.DataSourceInputStream r0 = new com.google.android.exoplayer2.upstream.DataSourceInputStream
            r0.<init>(r1, r4)
            r5 = r0
            byte[] r0 = com.google.android.exoplayer2.util.Util.toByteArray(r5)     // Catch:{ InvalidResponseCodeException -> 0x0052 }
            com.google.android.exoplayer2.util.Util.closeQuietly(r5)
            return r0
        L_0x0050:
            r0 = move-exception
            goto L_0x0082
        L_0x0052:
            r0 = move-exception
            r6 = r0
            r0 = r6
            int r6 = r0.responseCode     // Catch:{ all -> 0x0050 }
            r7 = 307(0x133, float:4.3E-43)
            if (r6 == r7) goto L_0x0061
            int r6 = r0.responseCode     // Catch:{ all -> 0x0050 }
            r7 = 308(0x134, float:4.32E-43)
            if (r6 != r7) goto L_0x0069
        L_0x0061:
            int r6 = r3 + 1
            r7 = 5
            if (r3 >= r7) goto L_0x0068
            r3 = 1
            goto L_0x006c
        L_0x0068:
            r3 = r6
        L_0x0069:
            r6 = 0
            r6 = r3
            r3 = 0
        L_0x006c:
            if (r3 == 0) goto L_0x0076
            java.lang.String r7 = getRedirectUrl(r0)     // Catch:{ all -> 0x0073 }
            goto L_0x0077
        L_0x0073:
            r0 = move-exception
            r3 = r6
            goto L_0x0082
        L_0x0076:
            r7 = 0
        L_0x0077:
            if (r7 == 0) goto L_0x0080
            r2 = r7
            com.google.android.exoplayer2.util.Util.closeQuietly(r5)
            r3 = r6
            goto L_0x002e
        L_0x0080:
            throw r0     // Catch:{ all -> 0x0073 }
        L_0x0082:
            com.google.android.exoplayer2.util.Util.closeQuietly(r5)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.HttpMediaDrmCallback.executePost(com.google.android.exoplayer2.upstream.HttpDataSource$Factory, java.lang.String, byte[], java.util.Map):byte[]");
    }

    @Nullable
    private static String getRedirectUrl(HttpDataSource.InvalidResponseCodeException exception) {
        List<String> locationHeaders;
        Map<String, List<String>> headerFields = exception.headerFields;
        if (headerFields == null || (locationHeaders = headerFields.get("Location")) == null || locationHeaders.isEmpty()) {
            return null;
        }
        return (String) locationHeaders.get(0);
    }

    public void setKeyRequestProperty(String name, String value) {
        Assertions.checkNotNull(name);
        Assertions.checkNotNull(value);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(name, value);
        }
    }

    public void clearKeyRequestProperty(String name) {
        Assertions.checkNotNull(name);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(name);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws IOException {
        String defaultUrl = request.getDefaultUrl();
        String fromUtf8Bytes = Util.fromUtf8Bytes(request.getData());
        StringBuilder sb = new StringBuilder(String.valueOf(defaultUrl).length() + 15 + String.valueOf(fromUtf8Bytes).length());
        sb.append(defaultUrl);
        sb.append("&signedRequest=");
        sb.append(fromUtf8Bytes);
        return executePost(this.dataSourceFactory, sb.toString(), Util.EMPTY_BYTE_ARRAY, null);
    }

    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws Exception {
        String contentType;
        String url = request.getLicenseServerUrl();
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(url)) {
            url = this.defaultLicenseUrl;
        }
        Map<String, String> requestProperties = new HashMap<>();
        if (C0841C.PLAYREADY_UUID.equals(uuid)) {
            contentType = "text/xml";
        } else {
            contentType = C0841C.CLEARKEY_UUID.equals(uuid) ? "application/json" : "application/octet-stream";
        }
        requestProperties.put("Content-Type", contentType);
        if (C0841C.PLAYREADY_UUID.equals(uuid)) {
            requestProperties.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            requestProperties.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, url, request.getData(), requestProperties);
    }
}
