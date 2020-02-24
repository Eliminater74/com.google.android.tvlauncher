package com.google.android.exoplayer2.text.cea;

import java.util.Collections;
import java.util.List;

public final class Cea708InitializationData {
    public final boolean isWideAspectRatio;

    private Cea708InitializationData(List<byte[]> initializationData) {
        this.isWideAspectRatio = initializationData.get(0)[0] != 0;
    }

    public static Cea708InitializationData fromData(List<byte[]> initializationData) {
        return new Cea708InitializationData(initializationData);
    }

    public static List<byte[]> buildData(boolean isWideAspectRatio2) {
        return Collections.singletonList(new byte[]{isWideAspectRatio2 ? (byte) 1 : 0});
    }
}
