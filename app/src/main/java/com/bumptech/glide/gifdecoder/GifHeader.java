package com.bumptech.glide.gifdecoder;

import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

public class GifHeader {
    public static final int NETSCAPE_LOOP_COUNT_DOES_NOT_EXIST = -1;
    public static final int NETSCAPE_LOOP_COUNT_FOREVER = 0;
    final List<GifFrame> frames = new ArrayList();
    @ColorInt
    int bgColor;
    int bgIndex;
    GifFrame currentFrame;
    int frameCount = 0;
    @ColorInt
    int[] gct = null;
    boolean gctFlag;
    int gctSize;
    int height;
    int loopCount = -1;
    int pixelAspect;
    int status = 0;
    int width;

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getNumFrames() {
        return this.frameCount;
    }

    public int getStatus() {
        return this.status;
    }
}
