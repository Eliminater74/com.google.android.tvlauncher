package android.support.p001v4.graphics;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.graphics.BitmapCompat */
public final class BitmapCompat {
    private BitmapCompat() {
    }

    public static boolean hasMipMap(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 18) {
            return bitmap.hasMipMap();
        }
        return false;
    }

    public static void setHasMipMap(@NonNull Bitmap bitmap, boolean hasMipMap) {
        if (Build.VERSION.SDK_INT >= 18) {
            bitmap.setHasMipMap(hasMipMap);
        }
    }

    public static int getAllocationByteCount(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }
}
