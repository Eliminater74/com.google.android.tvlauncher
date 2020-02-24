package android.support.p004v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.internal.view.SupportMenuItem;
import android.support.p001v4.view.ActionProvider;
import android.util.Log;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.view.menu.MenuItemWrapperICS */
public class MenuItemWrapperICS extends BaseMenuWrapper implements MenuItem {
    static final String LOG_TAG = "MenuItemWrapper";
    private Method mSetExclusiveCheckableMethod;
    private final SupportMenuItem mWrappedObject;

    public MenuItemWrapperICS(Context context, SupportMenuItem object) {
        super(context);
        if (object != null) {
            this.mWrappedObject = object;
            return;
        }
        throw new IllegalArgumentException("Wrapped Object can not be null.");
    }

    public int getItemId() {
        return this.mWrappedObject.getItemId();
    }

    public int getGroupId() {
        return this.mWrappedObject.getGroupId();
    }

    public int getOrder() {
        return this.mWrappedObject.getOrder();
    }

    public MenuItem setTitle(CharSequence title) {
        this.mWrappedObject.setTitle(title);
        return this;
    }

    public MenuItem setTitle(int title) {
        this.mWrappedObject.setTitle(title);
        return this;
    }

    public CharSequence getTitle() {
        return this.mWrappedObject.getTitle();
    }

    public MenuItem setTitleCondensed(CharSequence title) {
        this.mWrappedObject.setTitleCondensed(title);
        return this;
    }

    public CharSequence getTitleCondensed() {
        return this.mWrappedObject.getTitleCondensed();
    }

    public MenuItem setIcon(Drawable icon) {
        this.mWrappedObject.setIcon(icon);
        return this;
    }

    public MenuItem setIcon(int iconRes) {
        this.mWrappedObject.setIcon(iconRes);
        return this;
    }

    public Drawable getIcon() {
        return this.mWrappedObject.getIcon();
    }

    public MenuItem setIntent(Intent intent) {
        this.mWrappedObject.setIntent(intent);
        return this;
    }

    public Intent getIntent() {
        return this.mWrappedObject.getIntent();
    }

    public MenuItem setShortcut(char numericChar, char alphaChar) {
        this.mWrappedObject.setShortcut(numericChar, alphaChar);
        return this;
    }

    public MenuItem setShortcut(char numericChar, char alphaChar, int numericModifiers, int alphaModifiers) {
        this.mWrappedObject.setShortcut(numericChar, alphaChar, numericModifiers, alphaModifiers);
        return this;
    }

    public MenuItem setNumericShortcut(char numericChar) {
        this.mWrappedObject.setNumericShortcut(numericChar);
        return this;
    }

    public MenuItem setNumericShortcut(char numericChar, int numericModifiers) {
        this.mWrappedObject.setNumericShortcut(numericChar, numericModifiers);
        return this;
    }

    public char getNumericShortcut() {
        return this.mWrappedObject.getNumericShortcut();
    }

    public int getNumericModifiers() {
        return this.mWrappedObject.getNumericModifiers();
    }

    public MenuItem setAlphabeticShortcut(char alphaChar) {
        this.mWrappedObject.setAlphabeticShortcut(alphaChar);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char alphaChar, int alphaModifiers) {
        this.mWrappedObject.setAlphabeticShortcut(alphaChar, alphaModifiers);
        return this;
    }

    public char getAlphabeticShortcut() {
        return this.mWrappedObject.getAlphabeticShortcut();
    }

    public int getAlphabeticModifiers() {
        return this.mWrappedObject.getAlphabeticModifiers();
    }

    public MenuItem setCheckable(boolean checkable) {
        this.mWrappedObject.setCheckable(checkable);
        return this;
    }

    public boolean isCheckable() {
        return this.mWrappedObject.isCheckable();
    }

    public MenuItem setChecked(boolean checked) {
        this.mWrappedObject.setChecked(checked);
        return this;
    }

    public boolean isChecked() {
        return this.mWrappedObject.isChecked();
    }

    public MenuItem setVisible(boolean visible) {
        return this.mWrappedObject.setVisible(visible);
    }

    public boolean isVisible() {
        return this.mWrappedObject.isVisible();
    }

    public MenuItem setEnabled(boolean enabled) {
        this.mWrappedObject.setEnabled(enabled);
        return this;
    }

    public boolean isEnabled() {
        return this.mWrappedObject.isEnabled();
    }

    public boolean hasSubMenu() {
        return this.mWrappedObject.hasSubMenu();
    }

    public SubMenu getSubMenu() {
        return getSubMenuWrapper(this.mWrappedObject.getSubMenu());
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
        this.mWrappedObject.setOnMenuItemClickListener(menuItemClickListener != null ? new OnMenuItemClickListenerWrapper(menuItemClickListener) : null);
        return this;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.mWrappedObject.getMenuInfo();
    }

    public void setShowAsAction(int actionEnum) {
        this.mWrappedObject.setShowAsAction(actionEnum);
    }

    public MenuItem setShowAsActionFlags(int actionEnum) {
        this.mWrappedObject.setShowAsActionFlags(actionEnum);
        return this;
    }

    public MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new CollapsibleActionViewWrapper(view);
        }
        this.mWrappedObject.setActionView(view);
        return this;
    }

    public MenuItem setActionView(int resId) {
        this.mWrappedObject.setActionView(resId);
        View actionView = this.mWrappedObject.getActionView();
        if (actionView instanceof CollapsibleActionView) {
            this.mWrappedObject.setActionView(new CollapsibleActionViewWrapper(actionView));
        }
        return this;
    }

    public View getActionView() {
        View actionView = this.mWrappedObject.getActionView();
        if (actionView instanceof CollapsibleActionViewWrapper) {
            return ((CollapsibleActionViewWrapper) actionView).getWrappedView();
        }
        return actionView;
    }

    public MenuItem setActionProvider(ActionProvider provider) {
        ActionProviderWrapper actionProviderWrapper;
        if (Build.VERSION.SDK_INT >= 16) {
            actionProviderWrapper = new ActionProviderWrapperJB(this.mContext, provider);
        } else {
            actionProviderWrapper = new ActionProviderWrapper(this.mContext, provider);
        }
        this.mWrappedObject.setSupportActionProvider(provider != null ? actionProviderWrapper : null);
        return this;
    }

    public ActionProvider getActionProvider() {
        android.support.p001v4.view.ActionProvider provider = this.mWrappedObject.getSupportActionProvider();
        if (provider instanceof ActionProviderWrapper) {
            return ((ActionProviderWrapper) provider).mInner;
        }
        return null;
    }

    public boolean expandActionView() {
        return this.mWrappedObject.expandActionView();
    }

    public boolean collapseActionView() {
        return this.mWrappedObject.collapseActionView();
    }

    public boolean isActionViewExpanded() {
        return this.mWrappedObject.isActionViewExpanded();
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
        this.mWrappedObject.setOnActionExpandListener(listener != null ? new OnActionExpandListenerWrapper(listener) : null);
        return this;
    }

    public MenuItem setContentDescription(CharSequence contentDescription) {
        this.mWrappedObject.setContentDescription(contentDescription);
        return this;
    }

    public CharSequence getContentDescription() {
        return this.mWrappedObject.getContentDescription();
    }

    public MenuItem setTooltipText(CharSequence tooltipText) {
        this.mWrappedObject.setTooltipText(tooltipText);
        return this;
    }

    public CharSequence getTooltipText() {
        return this.mWrappedObject.getTooltipText();
    }

    public MenuItem setIconTintList(ColorStateList tint) {
        this.mWrappedObject.setIconTintList(tint);
        return this;
    }

    public ColorStateList getIconTintList() {
        return this.mWrappedObject.getIconTintList();
    }

    public MenuItem setIconTintMode(PorterDuff.Mode tintMode) {
        this.mWrappedObject.setIconTintMode(tintMode);
        return this;
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.mWrappedObject.getIconTintMode();
    }

    public void setExclusiveCheckable(boolean checkable) {
        try {
            if (this.mSetExclusiveCheckableMethod == null) {
                this.mSetExclusiveCheckableMethod = this.mWrappedObject.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
            }
            this.mSetExclusiveCheckableMethod.invoke(this.mWrappedObject, Boolean.valueOf(checkable));
        } catch (Exception e) {
            Log.w(LOG_TAG, "Error while calling setExclusiveCheckable", e);
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$OnMenuItemClickListenerWrapper */
    private class OnMenuItemClickListenerWrapper implements MenuItem.OnMenuItemClickListener {
        private final MenuItem.OnMenuItemClickListener mObject;

        OnMenuItemClickListenerWrapper(MenuItem.OnMenuItemClickListener object) {
            this.mObject = object;
        }

        public boolean onMenuItemClick(MenuItem item) {
            return this.mObject.onMenuItemClick(MenuItemWrapperICS.this.getMenuItemWrapper(item));
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$OnActionExpandListenerWrapper */
    private class OnActionExpandListenerWrapper implements MenuItem.OnActionExpandListener {
        private final MenuItem.OnActionExpandListener mObject;

        OnActionExpandListenerWrapper(MenuItem.OnActionExpandListener object) {
            this.mObject = object;
        }

        public boolean onMenuItemActionExpand(MenuItem item) {
            return this.mObject.onMenuItemActionExpand(MenuItemWrapperICS.this.getMenuItemWrapper(item));
        }

        public boolean onMenuItemActionCollapse(MenuItem item) {
            return this.mObject.onMenuItemActionCollapse(MenuItemWrapperICS.this.getMenuItemWrapper(item));
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$ActionProviderWrapper */
    private class ActionProviderWrapper extends android.support.p001v4.view.ActionProvider {
        final ActionProvider mInner;

        ActionProviderWrapper(Context context, ActionProvider inner) {
            super(context);
            this.mInner = inner;
        }

        public View onCreateActionView() {
            return this.mInner.onCreateActionView();
        }

        public boolean onPerformDefaultAction() {
            return this.mInner.onPerformDefaultAction();
        }

        public boolean hasSubMenu() {
            return this.mInner.hasSubMenu();
        }

        public void onPrepareSubMenu(SubMenu subMenu) {
            this.mInner.onPrepareSubMenu(MenuItemWrapperICS.this.getSubMenuWrapper(subMenu));
        }
    }

    @RequiresApi(16)
    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$ActionProviderWrapperJB */
    private class ActionProviderWrapperJB extends ActionProviderWrapper implements ActionProvider.VisibilityListener {
        private ActionProvider.VisibilityListener mListener;

        ActionProviderWrapperJB(Context context, android.view.ActionProvider inner) {
            super(context, inner);
        }

        public View onCreateActionView(MenuItem forItem) {
            return this.mInner.onCreateActionView(forItem);
        }

        public boolean overridesItemVisibility() {
            return this.mInner.overridesItemVisibility();
        }

        public boolean isVisible() {
            return this.mInner.isVisible();
        }

        public void refreshVisibility() {
            this.mInner.refreshVisibility();
        }

        public void setVisibilityListener(ActionProvider.VisibilityListener listener) {
            this.mListener = listener;
            this.mInner.setVisibilityListener(listener != null ? this : null);
        }

        public void onActionProviderVisibilityChanged(boolean isVisible) {
            ActionProvider.VisibilityListener visibilityListener = this.mListener;
            if (visibilityListener != null) {
                visibilityListener.onActionProviderVisibilityChanged(isVisible);
            }
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$CollapsibleActionViewWrapper */
    static class CollapsibleActionViewWrapper extends FrameLayout implements android.support.p004v7.view.CollapsibleActionView {
        final CollapsibleActionView mWrappedView;

        CollapsibleActionViewWrapper(View actionView) {
            super(actionView.getContext());
            this.mWrappedView = (CollapsibleActionView) actionView;
            addView(actionView);
        }

        public void onActionViewExpanded() {
            this.mWrappedView.onActionViewExpanded();
        }

        public void onActionViewCollapsed() {
            this.mWrappedView.onActionViewCollapsed();
        }

        /* access modifiers changed from: package-private */
        public View getWrappedView() {
            return (View) this.mWrappedView;
        }
    }
}
