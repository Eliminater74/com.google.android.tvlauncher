package android.support.p004v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.internal.view.SupportSubMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        /* renamed from: android.support.v7.view.menu.SubMenuWrapperICS */
class SubMenuWrapperICS extends MenuWrapperICS implements SubMenu {
    private final SupportSubMenu mSubMenu;

    SubMenuWrapperICS(Context context, SupportSubMenu subMenu) {
        super(context, subMenu);
        this.mSubMenu = subMenu;
    }

    public SubMenu setHeaderTitle(int titleRes) {
        this.mSubMenu.setHeaderTitle(titleRes);
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence title) {
        this.mSubMenu.setHeaderTitle(title);
        return this;
    }

    public SubMenu setHeaderIcon(int iconRes) {
        this.mSubMenu.setHeaderIcon(iconRes);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable icon) {
        this.mSubMenu.setHeaderIcon(icon);
        return this;
    }

    public SubMenu setHeaderView(View view) {
        this.mSubMenu.setHeaderView(view);
        return this;
    }

    public void clearHeader() {
        this.mSubMenu.clearHeader();
    }

    public SubMenu setIcon(int iconRes) {
        this.mSubMenu.setIcon(iconRes);
        return this;
    }

    public SubMenu setIcon(Drawable icon) {
        this.mSubMenu.setIcon(icon);
        return this;
    }

    public MenuItem getItem() {
        return getMenuItemWrapper(this.mSubMenu.getItem());
    }
}
