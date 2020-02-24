package com.google.android.tvlauncher.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Outline;
import android.support.annotation.VisibleForTesting;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;
import java.util.LinkedHashMap;
import java.util.Map;

public class AccessibilityContextMenu {
    private final Activity mActivity;
    /* access modifiers changed from: private */
    public final SparseArray<View> mContextItemViews = new SparseArray<>();
    /* access modifiers changed from: private */
    public final LinkedHashMap<Integer, ContextMenuItem> mContextItems = new LinkedHashMap<>();
    /* access modifiers changed from: private */
    public boolean mIsShowing;
    private final LinearLayout mMenuContainer;
    /* access modifiers changed from: private */
    public final int mMenuItemCornerRadius;
    /* access modifiers changed from: private */
    public final int mMenuItemDisabledColor;
    /* access modifiers changed from: private */
    public final int mMenuItemEnabledColor;
    private final int mMenuItemHeight;
    private final int mMenuItemMarginEnd;
    private final int mMenuItemWidth;
    /* access modifiers changed from: private */
    public ContextMenu.OnDismissListener mOnDismissListener;
    /* access modifiers changed from: private */
    public ContextMenu.OnItemClickListener mOnItemClickListener;
    private View.OnFocusChangeListener mOnItemViewFocusChangeListener = new View.OnFocusChangeListener(this) {
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                v.setAccessibilityLiveRegion(1);
            } else {
                v.setAccessibilityLiveRegion(0);
            }
        }
    };
    private ContextMenuItem.OnMenuItemChangedListener mOnMenuItemChangedListener;
    private final PopupWindow mPopupWindow;
    private final ViewOutlineProvider mViewOutlineProvider;

    public AccessibilityContextMenu(Activity activity) {
        this.mActivity = activity;
        Resources res = this.mActivity.getResources();
        this.mMenuContainer = (LinearLayout) ((LayoutInflater) this.mActivity.getSystemService("layout_inflater")).inflate(C1188R.layout.accessibility_context_menu_container, (ViewGroup) null);
        this.mPopupWindow = new PopupWindow(this.mMenuContainer, -1, -2);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                boolean unused = AccessibilityContextMenu.this.mIsShowing = false;
                if (AccessibilityContextMenu.this.mOnDismissListener != null) {
                    AccessibilityContextMenu.this.mOnDismissListener.onDismiss();
                }
            }
        });
        this.mMenuItemHeight = res.getDimensionPixelSize(C1188R.dimen.accessibility_context_menu_item_height);
        this.mMenuItemWidth = res.getDimensionPixelSize(C1188R.dimen.accessibility_context_menu_item_width);
        this.mMenuItemMarginEnd = res.getDimensionPixelSize(C1188R.dimen.accessibility_context_menu_item_margin_end);
        this.mMenuItemCornerRadius = res.getDimensionPixelSize(C1188R.dimen.accessibility_context_menu_item_corner_radius);
        this.mMenuItemEnabledColor = this.mActivity.getColor(C1188R.color.accessibility_context_menu_background_enabled_color);
        this.mMenuItemDisabledColor = this.mActivity.getColor(C1188R.color.accessibility_context_menu_background_disabled_color);
        this.mViewOutlineProvider = new ViewOutlineProvider() {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) AccessibilityContextMenu.this.mMenuItemCornerRadius);
            }
        };
        this.mOnMenuItemChangedListener = new ContextMenuItem.OnMenuItemChangedListener() {
            public void onMenuItemChanged(ContextMenuItem contextMenuItem) {
                int i;
                View itemView = (View) AccessibilityContextMenu.this.mContextItemViews.get(contextMenuItem.getId());
                if (itemView != null) {
                    itemView.setEnabled(contextMenuItem.isEnabled());
                    if (contextMenuItem.isEnabled()) {
                        i = AccessibilityContextMenu.this.mMenuItemEnabledColor;
                    } else {
                        i = AccessibilityContextMenu.this.mMenuItemDisabledColor;
                    }
                    itemView.setBackgroundColor(i);
                }
            }
        };
    }

    public void show() {
        LayoutInflater layoutInflater = (LayoutInflater) this.mActivity.getSystemService("layout_inflater");
        this.mMenuContainer.removeAllViews();
        for (Map.Entry<Integer, ContextMenuItem> entry : this.mContextItems.entrySet()) {
            ContextMenuItem item = (ContextMenuItem) entry.getValue();
            item.setOnMenuItemChangedListener(this.mOnMenuItemChangedListener);
            LinearLayout itemView = (LinearLayout) layoutInflater.inflate(C1188R.layout.accessibility_context_menu_item, (ViewGroup) null);
            setUpMenuItemView(item, itemView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this.mMenuItemWidth, this.mMenuItemHeight);
            params.setMarginEnd(this.mMenuItemMarginEnd);
            this.mContextItemViews.put(item.getId(), itemView);
            this.mMenuContainer.addView(itemView, params);
        }
        this.mMenuContainer.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(AccessibilityContextMenu.this.mContextItems.size(), 0, false));
            }
        });
        this.mPopupWindow.showAtLocation(this.mActivity.getWindow().getDecorView().getRootView(), 80, 0, 0);
        this.mIsShowing = true;
    }

    public void addItem(ContextMenuItem item) {
        this.mContextItems.put(Integer.valueOf(item.getId()), item);
    }

    public void setOnMenuItemClickListener(ContextMenu.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @VisibleForTesting
    public ContextMenu.OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public void setOnDismissListener(ContextMenu.OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public void dismiss() {
        this.mPopupWindow.dismiss();
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public ContextMenuItem findItem(int menuId) {
        return this.mContextItems.get(Integer.valueOf(menuId));
    }

    private void setUpMenuItemView(final ContextMenuItem item, View view) {
        view.setOutlineProvider(this.mViewOutlineProvider);
        view.setClipToOutline(true);
        ((TextView) view.findViewById(C1188R.C1191id.title)).setText(item.getTitle());
        ImageView iconView = (ImageView) view.findViewById(C1188R.C1191id.icon);
        iconView.setImageTintList(view.getContext().getResources().getColorStateList(C1188R.color.context_menu_icon_enabled_color, null));
        iconView.setImageDrawable(item.getIcon());
        view.setEnabled(item.isEnabled());
        view.setBackgroundColor(item.isEnabled() ? this.mMenuItemEnabledColor : this.mMenuItemDisabledColor);
        view.setOnFocusChangeListener(this.mOnItemViewFocusChangeListener);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (item.isEnabled() && AccessibilityContextMenu.this.mOnItemClickListener != null) {
                    AccessibilityContextMenu.this.mOnItemClickListener.onItemClick(item);
                }
            }
        });
    }
}
