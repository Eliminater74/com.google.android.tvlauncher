package androidx.leanback.app;

import android.app.Fragment;
import android.os.Build;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class PermissionHelper {
    public static void requestPermissions(Fragment fragment, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            fragment.requestPermissions(permissions, requestCode);
        }
    }

    private PermissionHelper() {
    }
}
