package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;

final /* synthetic */ class Util$$Lambda$2 implements MetadataOutput {
    static final MetadataOutput $instance = new Util$$Lambda$2();

    private Util$$Lambda$2() {
    }

    public void onMetadata(Metadata metadata) {
        Util.lambda$getRendererCapabilities$2$Util(metadata);
    }
}
