package com.google.android.exoplayer2.source.hls;

import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;

public final class HlsManifest {
    public final HlsMasterPlaylist masterPlaylist;
    public final HlsMediaPlaylist mediaPlaylist;

    HlsManifest(HlsMasterPlaylist masterPlaylist2, HlsMediaPlaylist mediaPlaylist2) {
        this.masterPlaylist = masterPlaylist2;
        this.mediaPlaylist = mediaPlaylist2;
    }
}
