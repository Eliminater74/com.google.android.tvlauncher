package com.google.android.exoplayer2.source.smoothstreaming.manifest;

import android.net.Uri;

import com.google.android.exoplayer2.util.Util;

public final class SsUtil {
    private SsUtil() {
    }

    public static Uri fixManifestUri(Uri manifestUri) {
        String lastPathSegment = manifestUri.getLastPathSegment();
        if (lastPathSegment == null || !Util.toLowerInvariant(lastPathSegment).matches("manifest(\\(.+\\))?")) {
            return Uri.withAppendedPath(manifestUri, "Manifest");
        }
        return manifestUri;
    }
}
