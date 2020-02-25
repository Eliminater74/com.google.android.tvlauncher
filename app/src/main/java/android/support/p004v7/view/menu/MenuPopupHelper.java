package android.support.p004v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.p001v4.view.GravityCompat;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.appcompat.C0233R;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.view.menu.MenuPopupHelper */
public class MenuPopupHelper implements MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private final Context mContext;
    private final PopupWindow.OnDismissListener mInternalOnDismissListener;
    private final MenuBuilder mMenu;
    private final boolean mOverflowOnly;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private View mAnchorView;
    private int mDropDownGravity;
    private boolean mForceShowIcon;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private MenuPopup mPopup;
    private MenuPresenter.Callback mPresenterCallback;

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menu) {
        this(context, menu, null, false, C0233R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menu, @NonNull View anchorView) {
        this(context, menu, anchorView, false, C0233R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menu, @NonNull View anchorView, boolean overflowOnly, @AttrRes int popupStyleAttr) {
        this(context, menu, anchorView, overflowOnly, popupStyleAttr, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menu, @NonNull View anchorView, boolean overflowOnly, @AttrRes int popupStyleAttr, @StyleRes int popupStyleRes) {
        this.mDropDownGravity = GravityCompat.START;
        this.mInternalOnDismissListener = new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                MenuPopupHelper.this.onDismiss();
            }
        };
        this.mContext = context;
        this.mMenu = menu;
        this.mAnchorView = anchorView;
        this.mOverflowOnly = overflowOnly;
        this.mPopupStyleAttr = popupStyleAttr;
        this.mPopupStyleRes = popupStyleRes;
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public void setAnchorView(@NonNull View anchor) {
        this.mAnchorView = anchor;
    }

    public void setForceShowIcon(boolean forceShowIcon) {
        this.mForceShowIcon = forceShowIcon;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup != null) {
            menuPopup.setForceShowIcon(forceShowIcon);
        }
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    public void setGravity(int gravity) {
        this.mDropDownGravity = gravity;
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int x, int y) {
        if (!tryShow(x, y)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    @NonNull
    public MenuPopup getPopup() {
        if (this.mPopup == null) {
            this.mPopup = createPopup();
        }
        return this.mPopup;
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int x, int y) {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(x, y, true, true);
        return true;
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Type inference failed for: r6v2, types: [android.support.v7.view.menu.CascadingMenuPopup] */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.p004v7.view.menu.MenuPopup createPopup() {
        /*
            r14 = this;
            android.content.Context r0 = r14.mContext
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.view.Display r1 = r0.getDefaultDisplay()
            android.graphics.Point r2 = new android.graphics.Point
            r2.<init>()
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 17
            if (r3 < r4) goto L_0x001e
            r1.getRealSize(r2)
            goto L_0x0021
        L_0x001e:
            r1.getSize(r2)
        L_0x0021:
            int r3 = r2.x
            int r4 = r2.y
            int r3 = java.lang.Math.min(r3, r4)
            android.content.Context r4 = r14.mContext
            android.content.res.Resources r4 = r4.getResources()
            int r5 = android.support.p004v7.appcompat.C0233R.dimen.abc_cascading_menus_min_smallest_width
            int r4 = r4.getDimensionPixelSize(r5)
            if (r3 < r4) goto L_0x0039
            r5 = 1
            goto L_0x003a
        L_0x0039:
            r5 = 0
        L_0x003a:
            if (r5 == 0) goto L_0x004d
            android.support.v7.view.menu.CascadingMenuPopup r12 = new android.support.v7.view.menu.CascadingMenuPopup
            android.content.Context r7 = r14.mContext
            android.view.View r8 = r14.mAnchorView
            int r9 = r14.mPopupStyleAttr
            int r10 = r14.mPopupStyleRes
            boolean r11 = r14.mOverflowOnly
            r6 = r12
            r6.<init>(r7, r8, r9, r10, r11)
            goto L_0x005f
        L_0x004d:
            android.support.v7.view.menu.StandardMenuPopup r6 = new android.support.v7.view.menu.StandardMenuPopup
            android.content.Context r8 = r14.mContext
            android.support.v7.view.menu.MenuBuilder r9 = r14.mMenu
            android.view.View r10 = r14.mAnchorView
            int r11 = r14.mPopupStyleAttr
            int r12 = r14.mPopupStyleRes
            boolean r13 = r14.mOverflowOnly
            r7 = r6
            r7.<init>(r8, r9, r10, r11, r12, r13)
        L_0x005f:
            android.support.v7.view.menu.MenuBuilder r7 = r14.mMenu
            r6.addMenu(r7)
            android.widget.PopupWindow$OnDismissListener r7 = r14.mInternalOnDismissListener
            r6.setOnDismissListener(r7)
            android.view.View r7 = r14.mAnchorView
            r6.setAnchorView(r7)
            android.support.v7.view.menu.MenuPresenter$Callback r7 = r14.mPresenterCallback
            r6.setCallback(r7)
            boolean r7 = r14.mForceShowIcon
            r6.setForceShowIcon(r7)
            int r7 = r14.mDropDownGravity
            r6.setGravity(r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.view.menu.MenuPopupHelper.createPopup():android.support.v7.view.menu.MenuPopup");
    }

    private void showPopup(int xOffset, int yOffset, boolean useOffsets, boolean showTitle) {
        MenuPopup popup = getPopup();
        popup.setShowTitle(showTitle);
        if (useOffsets) {
            if ((GravityCompat.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 7) == 5) {
                xOffset -= this.mAnchorView.getWidth();
            }
            popup.setHorizontalOffset(xOffset);
            popup.setVerticalOffset(yOffset);
            int halfSize = (int) ((48.0f * this.mContext.getResources().getDisplayMetrics().density) / 2.0f);
            popup.setEpicenterBounds(new Rect(xOffset - halfSize, yOffset - halfSize, xOffset + halfSize, yOffset + halfSize));
        }
        popup.show();
    }

    public void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDismiss() {
        this.mPopup = null;
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public boolean isShowing() {
        MenuPopup menuPopup = this.mPopup;
        return menuPopup != null && menuPopup.isShowing();
    }

    public void setPresenterCallback(@Nullable MenuPresenter.Callback cb) {
        this.mPresenterCallback = cb;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup != null) {
            menuPopup.setCallback(cb);
        }
    }

    public ListView getListView() {
        return getPopup().getListView();
    }
}
