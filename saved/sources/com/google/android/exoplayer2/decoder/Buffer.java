package com.google.android.exoplayer2.decoder;

public abstract class Buffer {
    private int flags;

    public void clear() {
        this.flags = 0;
    }

    public final boolean isDecodeOnly() {
        return getFlag(Integer.MIN_VALUE);
    }

    public final boolean isEndOfStream() {
        return getFlag(4);
    }

    public final boolean isKeyFrame() {
        return getFlag(1);
    }

    public final void setFlags(int flags2) {
        this.flags = flags2;
    }

    public final void addFlag(int flag) {
        this.flags |= flag;
    }

    public final void clearFlag(int flag) {
        this.flags &= flag ^ -1;
    }

    /* access modifiers changed from: protected */
    public final boolean getFlag(int flag) {
        return (this.flags & flag) == flag;
    }
}
