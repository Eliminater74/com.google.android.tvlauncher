package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

public final class GifBitmapProvider implements GifDecoder.BitmapProvider {
    @Nullable
    private final ArrayPool arrayPool;
    private final BitmapPool bitmapPool;

    public GifBitmapProvider(BitmapPool bitmapPool2) {
        this(bitmapPool2, null);
    }

    public GifBitmapProvider(BitmapPool bitmapPool2, @Nullable ArrayPool arrayPool2) {
        this.bitmapPool = bitmapPool2;
        this.arrayPool = arrayPool2;
    }

    @NonNull
    public Bitmap obtain(int width, int height, @NonNull Bitmap.Config config) {
        return this.bitmapPool.getDirty(width, height, config);
    }

    public void release(@NonNull Bitmap bitmap) {
        this.bitmapPool.put(bitmap);
    }

    @NonNull
    public byte[] obtainByteArray(int size) {
        ArrayPool arrayPool2 = this.arrayPool;
        if (arrayPool2 == null) {
            return new byte[size];
        }
        return (byte[]) arrayPool2.get(size, byte[].class);
    }

    public void release(@NonNull byte[] bytes) {
        ArrayPool arrayPool2 = this.arrayPool;
        if (arrayPool2 != null) {
            arrayPool2.put(bytes);
        }
    }

    @NonNull
    public int[] obtainIntArray(int size) {
        ArrayPool arrayPool2 = this.arrayPool;
        if (arrayPool2 == null) {
            return new int[size];
        }
        return (int[]) arrayPool2.get(size, int[].class);
    }

    public void release(@NonNull int[] array) {
        ArrayPool arrayPool2 = this.arrayPool;
        if (arrayPool2 != null) {
            arrayPool2.put(array);
        }
    }
}
