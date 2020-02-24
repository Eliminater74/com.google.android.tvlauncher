package com.google.android.exoplayer2.metadata;

import android.support.annotation.Nullable;

public interface MetadataDecoder {
    @Nullable
    Metadata decode(MetadataInputBuffer metadataInputBuffer);
}
