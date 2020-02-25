package com.google.android.tvlauncher.appsview.palette;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;

public class InstallingItemPaletteBitmapResource implements Resource<InstallingItemPaletteBitmapContainer> {
    private final BitmapPool mBitmapPool;
    private final InstallingItemPaletteBitmapContainer mPaletteBitmapContainer;

    public InstallingItemPaletteBitmapResource(@NonNull InstallingItemPaletteBitmapContainer paletteBitmapContainer, @NonNull BitmapPool bitmapPool) {
        this.mPaletteBitmapContainer = paletteBitmapContainer;
        this.mBitmapPool = bitmapPool;
    }

    public Class<InstallingItemPaletteBitmapContainer> getResourceClass() {
        return InstallingItemPaletteBitmapContainer.class;
    }

    public InstallingItemPaletteBitmapContainer get() {
        return this.mPaletteBitmapContainer;
    }

    public int getSize() {
        return Util.getBitmapByteSize(this.mPaletteBitmapContainer.getBitmap());
    }

    public void recycle() {
        this.mBitmapPool.put(this.mPaletteBitmapContainer.getBitmap());
    }
}
