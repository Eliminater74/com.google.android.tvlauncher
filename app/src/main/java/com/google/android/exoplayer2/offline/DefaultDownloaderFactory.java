package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;

public class DefaultDownloaderFactory implements DownloaderFactory {
    @Nullable
    private static final Constructor<? extends Downloader> DASH_DOWNLOADER_CONSTRUCTOR;
    @Nullable
    private static final Constructor<? extends Downloader> HLS_DOWNLOADER_CONSTRUCTOR;
    @Nullable
    private static final Constructor<? extends Downloader> SS_DOWNLOADER_CONSTRUCTOR;

    static {
        Constructor<? extends Downloader> dashDownloaderConstructor = null;
        try {
            dashDownloaderConstructor = getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.dash.offline.DashDownloader"));
        } catch (ClassNotFoundException e) {
        }
        DASH_DOWNLOADER_CONSTRUCTOR = dashDownloaderConstructor;
        Constructor<? extends Downloader> hlsDownloaderConstructor = null;
        try {
            hlsDownloaderConstructor = getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.hls.offline.HlsDownloader"));
        } catch (ClassNotFoundException e2) {
        }
        HLS_DOWNLOADER_CONSTRUCTOR = hlsDownloaderConstructor;
        Constructor<? extends Downloader> ssDownloaderConstructor = null;
        try {
            ssDownloaderConstructor = getDownloaderConstructor(Class.forName("com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloader"));
        } catch (ClassNotFoundException e3) {
        }
        SS_DOWNLOADER_CONSTRUCTOR = ssDownloaderConstructor;
    }

    private final DownloaderConstructorHelper downloaderConstructorHelper;

    public DefaultDownloaderFactory(DownloaderConstructorHelper downloaderConstructorHelper2) {
        this.downloaderConstructorHelper = downloaderConstructorHelper2;
    }

    private static Constructor<? extends Downloader> getDownloaderConstructor(Class<?> clazz) {
        try {
            return clazz.asSubclass(Downloader.class).getConstructor(Uri.class, List.class, DownloaderConstructorHelper.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("DASH downloader constructor missing", e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0084  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.offline.Downloader createDownloader(com.google.android.exoplayer2.offline.DownloadRequest r7) {
        /*
            r6 = this;
            java.lang.String r0 = r7.type
            int r1 = r0.hashCode()
            r2 = 3680(0xe60, float:5.157E-42)
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == r2) goto L_0x003c
            r2 = 103407(0x193ef, float:1.44904E-40)
            if (r1 == r2) goto L_0x0032
            r2 = 3075986(0x2eef92, float:4.310374E-39)
            if (r1 == r2) goto L_0x0028
            r2 = 1131547531(0x43720b8b, float:242.04509)
            if (r1 == r2) goto L_0x001d
        L_0x001c:
            goto L_0x0047
        L_0x001d:
            java.lang.String r1 = "progressive"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001c
            r0 = 0
            goto L_0x0048
        L_0x0028:
            java.lang.String r1 = "dash"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001c
            r0 = 1
            goto L_0x0048
        L_0x0032:
            java.lang.String r1 = "hls"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001c
            r0 = 2
            goto L_0x0048
        L_0x003c:
            java.lang.String r1 = "ss"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001c
            r0 = 3
            goto L_0x0048
        L_0x0047:
            r0 = -1
        L_0x0048:
            if (r0 == 0) goto L_0x0084
            if (r0 == r5) goto L_0x007d
            if (r0 == r4) goto L_0x0076
            if (r0 == r3) goto L_0x006f
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Unsupported type: "
            java.lang.String r2 = r7.type
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r3 = r2.length()
            if (r3 == 0) goto L_0x0065
            java.lang.String r1 = r1.concat(r2)
            goto L_0x006b
        L_0x0065:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1)
            r1 = r2
        L_0x006b:
            r0.<init>(r1)
            throw r0
        L_0x006f:
            java.lang.reflect.Constructor<? extends com.google.android.exoplayer2.offline.Downloader> r0 = com.google.android.exoplayer2.offline.DefaultDownloaderFactory.SS_DOWNLOADER_CONSTRUCTOR
            com.google.android.exoplayer2.offline.Downloader r0 = r6.createDownloader(r7, r0)
            return r0
        L_0x0076:
            java.lang.reflect.Constructor<? extends com.google.android.exoplayer2.offline.Downloader> r0 = com.google.android.exoplayer2.offline.DefaultDownloaderFactory.HLS_DOWNLOADER_CONSTRUCTOR
            com.google.android.exoplayer2.offline.Downloader r0 = r6.createDownloader(r7, r0)
            return r0
        L_0x007d:
            java.lang.reflect.Constructor<? extends com.google.android.exoplayer2.offline.Downloader> r0 = com.google.android.exoplayer2.offline.DefaultDownloaderFactory.DASH_DOWNLOADER_CONSTRUCTOR
            com.google.android.exoplayer2.offline.Downloader r0 = r6.createDownloader(r7, r0)
            return r0
        L_0x0084:
            com.google.android.exoplayer2.offline.ProgressiveDownloader r0 = new com.google.android.exoplayer2.offline.ProgressiveDownloader
            android.net.Uri r1 = r7.uri
            java.lang.String r2 = r7.customCacheKey
            com.google.android.exoplayer2.offline.DownloaderConstructorHelper r3 = r6.downloaderConstructorHelper
            r0.<init>(r1, r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DefaultDownloaderFactory.createDownloader(com.google.android.exoplayer2.offline.DownloadRequest):com.google.android.exoplayer2.offline.Downloader");
    }

    private Downloader createDownloader(DownloadRequest request, @Nullable Constructor<? extends Downloader> constructor) {
        if (constructor == null) {
            String valueOf = String.valueOf(request.type);
            throw new IllegalStateException(valueOf.length() != 0 ? "Module missing for: ".concat(valueOf) : new String("Module missing for: "));
        }
        try {
            return (Downloader) constructor.newInstance(request.uri, request.streamKeys, this.downloaderConstructorHelper);
        } catch (Exception e) {
            String valueOf2 = String.valueOf(request.type);
            throw new RuntimeException(valueOf2.length() != 0 ? "Failed to instantiate downloader for: ".concat(valueOf2) : new String("Failed to instantiate downloader for: "), e);
        }
    }
}
