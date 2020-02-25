package android.support.p004v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.ViewPropertyAnimatorCompat;
import android.support.p004v7.view.menu.MenuBuilder;
import android.support.p004v7.view.menu.MenuPresenter;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.widget.DecorToolbar */
public interface DecorToolbar {
    void animateToVisibility(int i);

    boolean canShowOverflowMenu();

    void collapseActionView();

    void dismissPopupMenus();

    Context getContext();

    View getCustomView();

    void setCustomView(View view);

    int getDisplayOptions();

    void setDisplayOptions(int i);

    int getDropdownItemCount();

    int getDropdownSelectedPosition();

    void setDropdownSelectedPosition(int i);

    int getHeight();

    Menu getMenu();

    int getNavigationMode();

    void setNavigationMode(int i);

    CharSequence getSubtitle();

    void setSubtitle(CharSequence charSequence);

    CharSequence getTitle();

    void setTitle(CharSequence charSequence);

    ViewGroup getViewGroup();

    int getVisibility();

    void setVisibility(int i);

    boolean hasEmbeddedTabs();

    boolean hasExpandedActionView();

    boolean hasIcon();

    boolean hasLogo();

    boolean hideOverflowMenu();

    void initIndeterminateProgress();

    void initProgress();

    boolean isOverflowMenuShowPending();

    boolean isOverflowMenuShowing();

    boolean isTitleTruncated();

    void restoreHierarchyState(SparseArray<Parcelable> sparseArray);

    void saveHierarchyState(SparseArray<Parcelable> sparseArray);

    void setBackgroundDrawable(Drawable drawable);

    void setCollapsible(boolean z);

    void setDefaultNavigationContentDescription(int i);

    void setDefaultNavigationIcon(Drawable drawable);

    void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener);

    void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView);

    void setHomeButtonEnabled(boolean z);

    void setIcon(int i);

    void setIcon(Drawable drawable);

    void setLogo(int i);

    void setLogo(Drawable drawable);

    void setMenu(Menu menu, MenuPresenter.Callback callback);

    void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2);

    void setMenuPrepared();

    void setNavigationContentDescription(int i);

    void setNavigationContentDescription(CharSequence charSequence);

    void setNavigationIcon(int i);

    void setNavigationIcon(Drawable drawable);

    void setWindowCallback(Window.Callback callback);

    void setWindowTitle(CharSequence charSequence);

    ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j);

    boolean showOverflowMenu();
}
