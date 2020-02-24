package android.support.p001v4.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.p001v4.internal.view.SupportMenu;
import android.view.Menu;
import android.view.MenuItem;

/* renamed from: android.support.v4.view.MenuCompat */
public final class MenuCompat {
    @Deprecated
    public static void setShowAsAction(MenuItem item, int actionEnum) {
        item.setShowAsAction(actionEnum);
    }

    @SuppressLint({"NewApi"})
    public static void setGroupDividerEnabled(Menu menu, boolean enabled) {
        if (menu instanceof SupportMenu) {
            ((SupportMenu) menu).setGroupDividerEnabled(enabled);
        } else if (Build.VERSION.SDK_INT >= 28) {
            menu.setGroupDividerEnabled(enabled);
        }
    }

    private MenuCompat() {
    }
}
