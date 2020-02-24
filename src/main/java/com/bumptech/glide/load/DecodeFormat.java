package com.bumptech.glide.load;

/* JADX INFO: Failed to restore enum class, 'enum' modifier removed */
public final class DecodeFormat extends Enum<DecodeFormat> {
    private static final /* synthetic */ DecodeFormat[] $VALUES;
    public static final DecodeFormat DEFAULT;
    public static final DecodeFormat PREFER_ARGB_8888 = new DecodeFormat("PREFER_ARGB_8888", 0);
    public static final DecodeFormat PREFER_RGB_565 = new DecodeFormat("PREFER_RGB_565", 1);

    private DecodeFormat(String str, int i) {
    }

    public static DecodeFormat valueOf(String name) {
        return (DecodeFormat) Enum.valueOf(DecodeFormat.class, name);
    }

    public static DecodeFormat[] values() {
        return (DecodeFormat[]) $VALUES.clone();
    }

    static {
        DecodeFormat decodeFormat = PREFER_ARGB_8888;
        $VALUES = new DecodeFormat[]{decodeFormat, PREFER_RGB_565};
        DEFAULT = decodeFormat;
    }
}
