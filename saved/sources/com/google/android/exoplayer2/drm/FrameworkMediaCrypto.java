package com.google.android.exoplayer2.drm;

import java.util.UUID;

public final class FrameworkMediaCrypto implements ExoMediaCrypto {
    public final boolean forceAllowInsecureDecoderComponents;
    public final byte[] sessionId;
    public final UUID uuid;

    public FrameworkMediaCrypto(UUID uuid2, byte[] sessionId2, boolean forceAllowInsecureDecoderComponents2) {
        this.uuid = uuid2;
        this.sessionId = sessionId2;
        this.forceAllowInsecureDecoderComponents = forceAllowInsecureDecoderComponents2;
    }
}
