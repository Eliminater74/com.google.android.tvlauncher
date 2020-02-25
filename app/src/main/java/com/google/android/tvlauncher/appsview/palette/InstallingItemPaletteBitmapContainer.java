package com.google.android.tvlauncher.appsview.palette;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import androidx.palette.graphics.Palette;

public class InstallingItemPaletteBitmapContainer {
    private final Bitmap mBitmap;
    private final Palette mPalette;

    public InstallingItemPaletteBitmapContainer(@NonNull Bitmap bitmap, @NonNull Palette palette) {
        this.mBitmap = bitmap;
        this.mPalette = palette;
    }

    public Palette getPalette() {
        return this.mPalette;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }
}
