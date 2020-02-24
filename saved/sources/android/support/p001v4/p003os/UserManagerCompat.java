package android.support.p001v4.p003os;

import android.content.Context;
import android.os.Build;
import android.os.UserManager;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.os.UserManagerCompat */
public class UserManagerCompat {
    private UserManagerCompat() {
    }

    public static boolean isUserUnlocked(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((UserManager) context.getSystemService(UserManager.class)).isUserUnlocked();
        }
        return true;
    }
}
