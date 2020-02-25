package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;

import com.google.android.exoplayer2.util.Clock;

public final class MetadataRetrieverFactory {
    private MetadataRetrieverFactory() {
    }

    public static MetadataRetriever newInstance(Context context) {
        return new MetadataRetrieverImpl(context, Clock.DEFAULT);
    }

    public static MetadataRetriever newInstance(Context context, Clock clock, Looper eventLooper) {
        return new MetadataRetrieverImpl(context, clock, eventLooper);
    }
}
