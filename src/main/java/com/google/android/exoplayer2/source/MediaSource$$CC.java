package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.TransferListener;

public abstract /* synthetic */ class MediaSource$$CC {
    @Nullable
    public static Object getTag$$dflt$$(MediaSource mediaSource) {
        return null;
    }

    @Deprecated
    public static void prepareSource$$dflt$$(MediaSource mediaSource, ExoPlayer player, boolean isTopLevelSource, @Nullable MediaSource.SourceInfoRefreshListener listener, TransferListener mediaTransferListener) {
        mediaSource.prepareSource(listener, mediaTransferListener);
    }
}
