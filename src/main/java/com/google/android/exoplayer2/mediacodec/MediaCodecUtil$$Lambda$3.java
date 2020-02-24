package com.google.android.exoplayer2.mediacodec;

import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import java.util.Comparator;

final /* synthetic */ class MediaCodecUtil$$Lambda$3 implements Comparator {
    private final MediaCodecUtil.ScoreProvider arg$1;

    MediaCodecUtil$$Lambda$3(MediaCodecUtil.ScoreProvider scoreProvider) {
        this.arg$1 = scoreProvider;
    }

    public int compare(Object obj, Object obj2) {
        return MediaCodecUtil.lambda$sortByScore$3$MediaCodecUtil(this.arg$1, obj, obj2);
    }
}
