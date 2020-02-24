package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.trackselection.BufferSizeAdaptationBuilder;

final /* synthetic */ class BufferSizeAdaptationBuilder$DynamicFormatFilter$$Lambda$0 implements BufferSizeAdaptationBuilder.DynamicFormatFilter {
    static final BufferSizeAdaptationBuilder.DynamicFormatFilter $instance = new BufferSizeAdaptationBuilder$DynamicFormatFilter$$Lambda$0();

    private BufferSizeAdaptationBuilder$DynamicFormatFilter$$Lambda$0() {
    }

    public boolean isFormatAllowed(Format format, int i, boolean z) {
        return BufferSizeAdaptationBuilder$DynamicFormatFilter$$CC.m22x98f47864(format, i, z);
    }
}
