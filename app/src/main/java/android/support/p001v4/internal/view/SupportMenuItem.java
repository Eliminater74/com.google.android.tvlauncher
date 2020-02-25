package android.support.p001v4.internal.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.internal.view.SupportMenuItem */
public interface SupportMenuItem extends MenuItem {
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    boolean collapseActionView();

    boolean expandActionView();

    View getActionView();

    int getAlphabeticModifiers();

    CharSequence getContentDescription();

    SupportMenuItem setContentDescription(CharSequence charSequence);

    ColorStateList getIconTintList();

    PorterDuff.Mode getIconTintMode();

    int getNumericModifiers();

    ActionProvider getSupportActionProvider();

    SupportMenuItem setSupportActionProvider(ActionProvider actionProvider);

    CharSequence getTooltipText();

    SupportMenuItem setTooltipText(CharSequence charSequence);

    boolean isActionViewExpanded();

    boolean requiresActionButton();

    boolean requiresOverflow();

    MenuItem setActionView(int i);

    MenuItem setActionView(View view);

    MenuItem setAlphabeticShortcut(char c, int i);

    MenuItem setIconTintList(ColorStateList colorStateList);

    MenuItem setIconTintMode(PorterDuff.Mode mode);

    MenuItem setNumericShortcut(char c, int i);

    MenuItem setShortcut(char c, char c2, int i, int i2);

    void setShowAsAction(int i);

    MenuItem setShowAsActionFlags(int i);
}
