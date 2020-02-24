package com.google.android.libraries.stitch.util;

public final class ImageType {
    public static final int TYPE_360_PANORAMA = 4;
    public static final int TYPE_GIF = 2;
    public static final int TYPE_PANORAMA = 3;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_UNDETERMINED = 0;
    public static final int TYPE_UNKNOWN = -1;

    public static boolean isPanorama(int imageType) {
        return imageType == 4 || imageType == 3;
    }

    private ImageType() {
    }
}
