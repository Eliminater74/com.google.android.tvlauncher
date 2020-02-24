package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;

final /* synthetic */ class CacheUtil$$Lambda$0 implements CacheKeyFactory {
    static final CacheKeyFactory $instance = new CacheUtil$$Lambda$0();

    private CacheUtil$$Lambda$0() {
    }

    public String buildCacheKey(DataSpec dataSpec) {
        return CacheUtil.lambda$static$0$CacheUtil(dataSpec);
    }
}
