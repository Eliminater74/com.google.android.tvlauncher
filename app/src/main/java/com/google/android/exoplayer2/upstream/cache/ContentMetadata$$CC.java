package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import android.support.annotation.Nullable;

public abstract /* synthetic */ class ContentMetadata$$CC {
    public static long getContentLength$$STATIC$$(ContentMetadata contentMetadata) {
        return contentMetadata.get(ContentMetadata.KEY_CONTENT_LENGTH, -1);
    }

    @Nullable
    public static Uri getRedirectedUri$$STATIC$$(ContentMetadata contentMetadata) {
        String redirectedUri = contentMetadata.get(ContentMetadata.KEY_REDIRECTED_URI, (String) null);
        if (redirectedUri == null) {
            return null;
        }
        return Uri.parse(redirectedUri);
    }
}
