package com.bumptech.glide.gifdecoder;

import android.support.annotation.ColorInt;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class GifFrame {
    static final int DISPOSAL_BACKGROUND = 2;
    static final int DISPOSAL_NONE = 1;
    static final int DISPOSAL_PREVIOUS = 3;
    static final int DISPOSAL_UNSPECIFIED = 0;
    int bufferFrameStart;
    int delay;
    int dispose;

    /* renamed from: ih */
    int f45ih;
    boolean interlace;

    /* renamed from: iw */
    int f46iw;

    /* renamed from: ix */
    int f47ix;

    /* renamed from: iy */
    int f48iy;
    @ColorInt
    int[] lct;
    int transIndex;
    boolean transparency;

    @Retention(RetentionPolicy.SOURCE)
    private @interface GifDisposalMethod {
    }

    GifFrame() {
    }
}
