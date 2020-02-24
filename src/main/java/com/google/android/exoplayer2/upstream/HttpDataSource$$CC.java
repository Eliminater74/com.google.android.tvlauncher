package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Predicate;
import com.google.android.exoplayer2.util.Util;

public abstract /* synthetic */ class HttpDataSource$$CC {
    public static void $$triggerInterfaceInit() {
        Predicate<String> predicate = HttpDataSource.REJECT_PAYWALL_TYPES;
    }

    static /* synthetic */ boolean lambda$static$0$HttpDataSource$$CC(String contentType) {
        String contentType2 = Util.toLowerInvariant(contentType);
        return !TextUtils.isEmpty(contentType2) && (!contentType2.contains("text") || contentType2.contains(MimeTypes.TEXT_VTT)) && !contentType2.contains("html") && !contentType2.contains("xml");
    }
}
