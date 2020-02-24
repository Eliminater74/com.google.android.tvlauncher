package com.google.android.tvlauncher.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.view.GravityCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import java.util.ArrayList;
import java.util.List;

public class ContextMenu {
    @VisibleForTesting
    static final int BOTTOM_ALIGN = 1;
    private static final float FLOAT_COMPARISON_DELTA = 1.0E-4f;
    @VisibleForTesting
    static final int SCROLL = 2;
    @VisibleForTesting
    static final int TOP_ALIGN = 0;
    private Activity mActivity;
    /* access modifiers changed from: private */
    public View mAnchor;
    /* access modifiers changed from: private */
    public View.OnAttachStateChangeListener mAnchorOnAttachStateChangeListener;
    private float mAnchorRealHeight;
    private float mAnchorRealWidth;
    /* access modifiers changed from: private */
    public View.OnLayoutChangeListener mAnchorRootLayoutChangeListener;
    private float mAnchorX;
    private float mAnchorY;
    private List<ContextMenuItem> mContextMenuItems;
    /* access modifiers changed from: private */
    public CutoutOverlayLayout mCutoutOverlay;
    private float mDeltaX;
    private float mDeltaY;
    /* access modifiers changed from: private */
    public final int mDimBackgroundColor;
    private final int mDisabledColor;
    private final int mEnabledColor;
    /* access modifiers changed from: private */
    public final int mFocusedColor;
    private int mGravity;
    private int mHorizontalPosition;
    /* access modifiers changed from: private */
    public boolean mIsShowing;
    private FrameLayout mMenuContainer;
    private int mMenuHeight;
    private final int mMenuHeightPerRow;
    private LinearLayout mMenuLinearLayout;
    private final int mMenuVerticalMargin;
    private int mMenuWidth;
    /* access modifiers changed from: private */
    public OnDismissListener mOnDismissListener;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    /* access modifiers changed from: private */
    public ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private final float mOverlayAlpha;
    private ObjectAnimator mOverlayAnimator;
    private final int mOverlayDismissAnimationDuration;
    private final int mOverlayShowAnimationDuration;
    private final int mOverscanHorizontal;
    private final int mOverscanVertical;
    private PopupWindow mPopupWindow;
    /* access modifiers changed from: private */
    public ViewGroup mRootParentWindow;
    /* access modifiers changed from: private */
    public ImageView mTriangle;
    private final int mTriangleEdgeOffset;
    private final int mTriangleHeight;
    private final int mTriangleVerticalMenuMargin;
    private final int mTriangleWidth;
    /* access modifiers changed from: private */
    public final int mUnfocusedColor;
    @VerticalPosition
    private int mVerticalPosition;
    /* access modifiers changed from: private */
    public List<ContextMenuItem> mVisibleItems;
    private List<View> mVisibleMenuItemViews;

    public interface OnDismissListener {
        void onDismiss();
    }

    public interface OnItemClickListener {
        void onItemClick(ContextMenuItem contextMenuItem);
    }

    @interface VerticalPosition {
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<View> getVisibleMenuItemViews() {
        return this.mVisibleMenuItemViews;
    }

    @VisibleForTesting
    public List<ContextMenuItem> getContextMenuItems() {
        return this.mContextMenuItems;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public View.OnAttachStateChangeListener getAnchorOnAttachStateChangeListener() {
        return this.mAnchorOnAttachStateChangeListener;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public ObjectAnimator getOverlayAnimator() {
        return this.mOverlayAnimator;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public float getDeltaY() {
        return this.mDeltaY;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public float getDeltaX() {
        return this.mDeltaX;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getGravity() {
        return this.mGravity;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public CutoutOverlayLayout getCutoutOverlay() {
        return this.mCutoutOverlay;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getHorizontalPosition() {
        return this.mHorizontalPosition;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getVerticalPosition() {
        return this.mVerticalPosition;
    }

    public ContextMenu(Activity activity, View anchor, int cornerRadius) {
        this(activity, anchor, cornerRadius, anchor.getScaleX(), anchor.getScaleY());
    }

    public ContextMenu(Activity activity, View anchor, int cornerRadius, float scaleX, float scaleY) {
        this.mContextMenuItems = new ArrayList();
        this.mVisibleMenuItemViews = new ArrayList();
        this.mAnchorOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
                ContextMenu.this.alignCutoutOverlayToAnchor();
            }

            public void onViewDetachedFromWindow(View v) {
            }
        };
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            public void onScrollChanged() {
                ContextMenu.this.alignCutoutOverlayToAnchor();
            }
        };
        this.mAnchorRootLayoutChangeListener = new View.OnLayoutChangeListener() {
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ContextMenu.this.alignCutoutOverlayToAnchor();
            }
        };
        this.mIsShowing = false;
        this.mAnchor = anchor;
        this.mActivity = activity;
        this.mMenuContainer = new FrameLayout(this.mActivity);
        this.mMenuContainer.setContentDescription(this.mActivity.getString(C1188R.string.context_menu_description));
        this.mPopupWindow = new PopupWindow(this.mMenuContainer, -2, -2);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                ContextMenu.this.clearDimBackground();
                if (ContextMenu.this.mOnDismissListener != null) {
                    ContextMenu.this.mOnDismissListener.onDismiss();
                }
                ContextMenu.this.mAnchor.removeOnAttachStateChangeListener(ContextMenu.this.mAnchorOnAttachStateChangeListener);
                ContextMenu.this.mAnchor.getViewTreeObserver().removeOnScrollChangedListener(ContextMenu.this.mOnScrollChangedListener);
                ContextMenu.this.mAnchor.getRootView().removeOnLayoutChangeListener(ContextMenu.this.mAnchorRootLayoutChangeListener);
                boolean unused = ContextMenu.this.mIsShowing = false;
            }
        });
        float[] mAnchorCoordinates = new float[2];
        getLocationInWindow(this.mAnchor, mAnchorCoordinates);
        this.mAnchorX = mAnchorCoordinates[0];
        this.mAnchorY = mAnchorCoordinates[1];
        this.mAnchorRealWidth = ((float) this.mAnchor.getWidth()) * scaleX;
        this.mAnchorRealHeight = ((float) this.mAnchor.getHeight()) * scaleY;
        this.mMenuVerticalMargin = getDimenInPixels(C1188R.dimen.context_menu_vertical_margin);
        this.mTriangleVerticalMenuMargin = getDimenInPixels(C1188R.dimen.context_menu_triangle_vertical_margin);
        this.mTriangleEdgeOffset = getDimenInPixels(C1188R.dimen.context_menu_triangle_edge_offset);
        this.mTriangleHeight = getDimenInPixels(C1188R.dimen.context_menu_triangle_height);
        this.mTriangleWidth = getDimenInPixels(C1188R.dimen.context_menu_triangle_width);
        this.mFocusedColor = this.mActivity.getColor(C1188R.color.context_menu_background_focused_color);
        this.mUnfocusedColor = this.mActivity.getColor(C1188R.color.context_menu_background_unfocused_color);
        this.mEnabledColor = this.mActivity.getColor(C1188R.color.context_menu_icon_enabled_color);
        this.mDisabledColor = this.mActivity.getColor(C1188R.color.context_menu_icon_disabled_color);
        this.mMenuHeightPerRow = getDimenInPixels(C1188R.dimen.context_menu_height_per_row);
        this.mOverscanHorizontal = getDimenInPixels(C1188R.dimen.overscan_horizontal);
        this.mOverscanVertical = getDimenInPixels(C1188R.dimen.overscan_vertical);
        this.mOverlayAlpha = getFloat(C1188R.dimen.context_menu_overlay_alpha);
        this.mOverlayShowAnimationDuration = this.mActivity.getResources().getInteger(C1188R.integer.context_menu_overlay_show_animation_duration_ms);
        this.mOverlayDismissAnimationDuration = this.mActivity.getResources().getInteger(C1188R.integer.context_menu_overlay_dismiss_animation_duration_ms);
        this.mDimBackgroundColor = this.mActivity.getColor(C1188R.color.context_menu_overlay_background_color);
        float f = this.mAnchorX;
        float f2 = this.mAnchorY;
        RectF anchorRect = new RectF(f, f2, this.mAnchorRealWidth + f, this.mAnchorRealHeight + f2);
        this.mCutoutOverlay = new CutoutOverlayLayout(this.mActivity, (int) (((float) cornerRadius) * scaleX), (int) (((float) cornerRadius) * scaleY));
        this.mCutoutOverlay.setAnchorRect(anchorRect);
        this.mTriangle = new ImageView(this.mActivity);
    }

    @VisibleForTesting
    private void getLocationInWindow(View anchorView, @Size(2) float[] outLocation) {
        if (outLocation == null || outLocation.length < 2) {
            throw new IllegalArgumentException("outLocation must be an array of two floats");
        }
        float[] position = {0.0f, 0.0f};
        anchorView.getMatrix().mapPoints(position);
        position[0] = position[0] + ((float) anchorView.getLeft());
        position[1] = position[1] + ((float) anchorView.getTop());
        ViewParent viewParent = anchorView.getParent();
        while (viewParent instanceof View) {
            View view = (View) viewParent;
            position[0] = position[0] - ((float) view.getScrollX());
            position[1] = position[1] - ((float) view.getScrollY());
            view.getMatrix().mapPoints(position);
            position[0] = position[0] + ((float) view.getLeft());
            position[1] = position[1] + ((float) view.getTop());
            viewParent = view.getParent();
        }
        outLocation[0] = position[0];
        outLocation[1] = position[1];
    }

    public ContextMenuItem findItem(int menuId) {
        for (ContextMenuItem item : this.mContextMenuItems) {
            if (item.getId() == menuId) {
                return item;
            }
        }
        return null;
    }

    private boolean testBit(int bitSet, int mask) {
        if (mask == 0) {
            if (bitSet == 0) {
                return true;
            }
            return false;
        } else if ((bitSet & mask) == mask) {
            return true;
        } else {
            return false;
        }
    }

    private void dimBackground() {
        ViewGroup viewGroup = this.mRootParentWindow;
        viewGroup.addView(this.mCutoutOverlay, viewGroup.getWidth(), this.mRootParentWindow.getHeight());
        this.mCutoutOverlay.setAlpha(0.0f);
        animateBackgroundOverlayAlpha(this.mOverlayAlpha, this.mOverlayShowAnimationDuration);
    }

    private void animateBackgroundOverlayAlpha(float destinationAlpha, int duration) {
        ObjectAnimator objectAnimator = this.mOverlayAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.mOverlayAnimator = ObjectAnimator.ofFloat(this.mCutoutOverlay, View.ALPHA, destinationAlpha);
        this.mOverlayAnimator.setDuration((long) duration);
        this.mOverlayAnimator.start();
    }

    /* access modifiers changed from: private */
    public void clearDimBackground() {
        animateBackgroundOverlayAlpha(0.0f, this.mOverlayDismissAnimationDuration);
        this.mOverlayAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ContextMenu.this.mRootParentWindow.removeView(ContextMenu.this.mCutoutOverlay);
            }
        });
    }

    public void forceDismiss() {
        this.mPopupWindow.dismiss();
    }

    private int getDimenInPixels(int resourceId) {
        return this.mAnchor.getResources().getDimensionPixelSize(resourceId);
    }

    private List<ContextMenuItem> getVisibleItems() {
        List<ContextMenuItem> list = new ArrayList<>();
        for (ContextMenuItem item : this.mContextMenuItems) {
            if (item.isVisible()) {
                list.add(item);
            }
        }
        return list;
    }

    private void determineGravity() {
        this.mGravity = 0;
        this.mHorizontalPosition = 17;
        if (this.mAnchor.getLayoutDirection() == 1) {
            if ((this.mAnchorX + this.mAnchorRealWidth) - ((float) this.mMenuWidth) >= ((float) this.mOverscanHorizontal)) {
                this.mGravity |= 5;
            } else {
                this.mGravity |= 3;
            }
        } else if (this.mAnchorX + ((float) this.mMenuWidth) <= ((float) (this.mRootParentWindow.getWidth() - this.mOverscanHorizontal))) {
            this.mGravity |= 3;
        } else {
            this.mGravity |= 5;
        }
        float f = this.mAnchorY + this.mAnchorRealHeight + ((float) this.mMenuHeight);
        int height = this.mRootParentWindow.getHeight();
        int i = this.mOverscanVertical;
        if (f <= ((float) (height - i))) {
            this.mGravity |= 80;
            return;
        }
        float f2 = this.mAnchorY;
        int i2 = this.mMenuHeight;
        if (f2 - ((float) i2) >= ((float) i)) {
            this.mGravity |= 48;
            return;
        }
        this.mMenuHeight = i2 - this.mMenuVerticalMargin;
        if (this.mAnchor.getLayoutDirection() == 0) {
            if (this.mAnchorX + this.mAnchorRealWidth + ((float) this.mMenuWidth) + ((float) this.mTriangleHeight) <= ((float) (this.mRootParentWindow.getWidth() - this.mOverscanHorizontal))) {
                this.mHorizontalPosition = 5;
            } else {
                this.mHorizontalPosition = 3;
            }
        } else if ((this.mAnchorX - ((float) this.mMenuWidth)) - ((float) this.mTriangleHeight) >= ((float) this.mOverscanHorizontal)) {
            this.mHorizontalPosition = 3;
        } else {
            this.mHorizontalPosition = 5;
        }
        float f3 = this.mAnchorY + ((float) this.mMenuHeight);
        int height2 = this.mRootParentWindow.getHeight();
        int i3 = this.mOverscanVertical;
        if (f3 <= ((float) (height2 - i3))) {
            this.mVerticalPosition = 0;
        } else if ((this.mAnchorY + this.mAnchorRealHeight) - ((float) this.mMenuHeight) >= ((float) i3)) {
            this.mVerticalPosition = 1;
        } else {
            this.mVerticalPosition = 2;
        }
    }

    private void calculateMenuSize() {
        this.mMenuLinearLayout.measure(0, 0);
        this.mMenuWidth = this.mMenuLinearLayout.getMeasuredWidth();
        this.mMenuHeight = this.mMenuLinearLayout.getMeasuredHeight() + this.mMenuVerticalMargin;
    }

    public void addItem(ContextMenuItem item) {
        this.mContextMenuItems.add(item);
    }

    private int getRelativeGravity(int absoluteGravity, int layoutDirection) {
        if (testBit(absoluteGravity, 5) && layoutDirection == 0) {
            return GravityCompat.END;
        }
        if (testBit(absoluteGravity, 3) && layoutDirection == 1) {
            return GravityCompat.END;
        }
        if (testBit(absoluteGravity, 3) && layoutDirection == 0) {
            return GravityCompat.START;
        }
        if (!testBit(absoluteGravity, 5) || layoutDirection != 1) {
            return 0;
        }
        return GravityCompat.START;
    }

    private void adjustTrianglePosition() {
        FrameLayout.LayoutParams triangleLayoutParams = (FrameLayout.LayoutParams) this.mTriangle.getLayoutParams();
        triangleLayoutParams.gravity = 0;
        if (this.mHorizontalPosition == 17) {
            if (getRelativeGravity(this.mGravity, this.mAnchor.getLayoutDirection()) == 8388613) {
                triangleLayoutParams.gravity |= GravityCompat.END;
                triangleLayoutParams.setMarginEnd(this.mTriangleEdgeOffset);
            } else {
                triangleLayoutParams.gravity |= GravityCompat.START;
                triangleLayoutParams.setMarginStart(this.mTriangleEdgeOffset);
            }
            if (testBit(this.mGravity, 48)) {
                triangleLayoutParams.gravity |= 80;
                triangleLayoutParams.bottomMargin = this.mTriangleVerticalMenuMargin;
                this.mTriangle.setScaleY(-1.0f);
                return;
            }
            triangleLayoutParams.gravity |= 48;
            triangleLayoutParams.topMargin = this.mTriangleVerticalMenuMargin;
            return;
        }
        triangleLayoutParams.gravity |= GravityCompat.START;
        float f = 90.0f;
        if (getRelativeGravity(this.mHorizontalPosition, this.mAnchor.getLayoutDirection()) == 8388611) {
            triangleLayoutParams.setMarginStart(this.mMenuWidth - 2);
            ImageView imageView = this.mTriangle;
            if (this.mAnchor.getLayoutDirection() == 1) {
                f = 270.0f;
            }
            imageView.setRotation(f);
        } else {
            triangleLayoutParams.setMarginStart(0);
            ImageView imageView2 = this.mTriangle;
            if (this.mAnchor.getLayoutDirection() != 1) {
                f = 270.0f;
            }
            imageView2.setRotation(f);
        }
        int i = this.mVerticalPosition;
        if (i == 0) {
            triangleLayoutParams.gravity |= 48;
            triangleLayoutParams.topMargin = (int) ((this.mAnchorRealHeight - ((float) this.mTriangleWidth)) / 2.0f);
        } else if (i == 1) {
            triangleLayoutParams.gravity |= 80;
            triangleLayoutParams.bottomMargin = (int) ((this.mAnchorRealHeight - ((float) this.mTriangleWidth)) / 2.0f);
        } else {
            int menuLocationY = this.mRootParentWindow.getHeight() - this.mMenuHeight;
            triangleLayoutParams.gravity |= 48;
            triangleLayoutParams.topMargin = (int) ((this.mAnchorY - ((float) menuLocationY)) + ((this.mAnchorRealHeight - ((float) this.mTriangleWidth)) / 2.0f));
        }
        this.mMenuWidth += this.mTriangleHeight;
    }

    private void adjustLayoutMenu() {
        ViewGroup.MarginLayoutParams menuLayoutParams = (ViewGroup.MarginLayoutParams) this.mMenuLinearLayout.getLayoutParams();
        int i = this.mHorizontalPosition;
        if (i != 17) {
            menuLayoutParams.topMargin = 0;
            menuLayoutParams.bottomMargin = 0;
            if (i == 3) {
                menuLayoutParams.rightMargin = this.mTriangleHeight;
            } else {
                menuLayoutParams.leftMargin = this.mTriangleHeight;
            }
        } else if (testBit(this.mGravity, 48)) {
            menuLayoutParams.bottomMargin = this.mMenuVerticalMargin;
            menuLayoutParams.topMargin = 0;
        } else {
            menuLayoutParams.bottomMargin = 0;
            menuLayoutParams.topMargin = this.mMenuVerticalMargin;
        }
        if ((this.mHorizontalPosition == 17 && testBit(this.mGravity, 48)) || (this.mHorizontalPosition != 17 && this.mVerticalPosition == 1)) {
            this.mMenuLinearLayout.removeAllViews();
            for (int i2 = this.mVisibleItems.size() - 1; i2 >= 0; i2--) {
                this.mMenuLinearLayout.addView(this.mVisibleMenuItemViews.get(i2));
            }
        }
    }

    private void adjustMenuShowUpPosition() {
        float f = 0.0f;
        this.mDeltaX = 0.0f;
        this.mDeltaY = 0.0f;
        int i = this.mHorizontalPosition;
        if (i == 17) {
            float deltaX = this.mAnchorRealWidth - ((float) this.mAnchor.getWidth());
            this.mDeltaY = testBit(this.mGravity, 80) ? this.mAnchorRealHeight - ((float) this.mAnchor.getHeight()) : 0.0f;
            if (testBit(this.mGravity, 5)) {
                f = deltaX;
            }
            this.mDeltaX = f;
            return;
        }
        if (i == 3) {
            this.mDeltaX = (float) (-this.mMenuWidth);
        } else {
            this.mDeltaX = this.mAnchorRealWidth;
        }
        this.mPopupWindow.setOverlapAnchor(true);
        if (this.mVerticalPosition == 1) {
            this.mDeltaY = -(((float) this.mMenuHeight) - this.mAnchorRealHeight);
        }
    }

    public void show() {
        this.mVisibleItems = getVisibleItems();
        this.mVisibleMenuItemViews.clear();
        if (this.mRootParentWindow == null) {
            this.mRootParentWindow = (ViewGroup) this.mActivity.getWindow().getDecorView().getRootView();
        }
        this.mAnchor.addOnAttachStateChangeListener(this.mAnchorOnAttachStateChangeListener);
        this.mAnchor.getViewTreeObserver().addOnScrollChangedListener(this.mOnScrollChangedListener);
        this.mAnchor.getRootView().addOnLayoutChangeListener(this.mAnchorRootLayoutChangeListener);
        dimBackground();
        this.mMenuLinearLayout = new LinearLayout(this.mActivity);
        this.mMenuLinearLayout.setOrientation(1);
        this.mMenuContainer.addView(this.mMenuLinearLayout, -2, -2);
        this.mMenuContainer.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(ContextMenu.this.mVisibleItems.size(), 0, false));
            }
        });
        this.mMenuLinearLayout.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) view.getResources().getDimensionPixelSize(C1188R.dimen.card_rounded_corner_radius));
            }
        });
        this.mMenuLinearLayout.setClipToOutline(true);
        this.mTriangle.setImageDrawable(this.mActivity.getDrawable(C1188R.C1189drawable.context_menu_triangle));
        this.mTriangle.setColorFilter(this.mUnfocusedColor, PorterDuff.Mode.SRC_ATOP);
        addMenuItemViews();
        calculateMenuSize();
        determineGravity();
        this.mMenuContainer.addView(this.mTriangle, -2, -2);
        adjustTrianglePosition();
        adjustLayoutMenu();
        int triangleTopLocation = ((ViewGroup.MarginLayoutParams) this.mTriangle.getLayoutParams()).topMargin;
        if (this.mHorizontalPosition != 17) {
            int i = 0;
            while (true) {
                if (i >= this.mVisibleItems.size()) {
                    break;
                }
                int i2 = this.mMenuHeightPerRow;
                if (triangleTopLocation >= i2 * i && triangleTopLocation <= i2 * (i + 1)) {
                    this.mVisibleItems.get(i).setLinkedWithTriangle(true);
                    break;
                }
                i++;
            }
        } else {
            this.mVisibleItems.get(0).setLinkedWithTriangle(true);
        }
        adjustMenuShowUpPosition();
        this.mVisibleMenuItemViews.get(0).requestFocus();
        this.mPopupWindow.setWidth(this.mMenuWidth);
        this.mPopupWindow.setHeight(this.mMenuHeight);
        if (this.mHorizontalPosition == 17) {
            this.mPopupWindow.showAsDropDown(this.mAnchor, (int) this.mDeltaX, (int) this.mDeltaY, this.mGravity);
        } else {
            this.mPopupWindow.showAsDropDown(this.mAnchor, (int) this.mDeltaX, (int) this.mDeltaY, 3);
        }
        this.mIsShowing = true;
    }

    public void setOnMenuItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
     arg types: [int, android.util.TypedValue, int]
     candidates:
      ClspMth{android.content.res.Resources.getValue(java.lang.String, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
      ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException} */
    @VisibleForTesting
    private float getFloat(int resourceId) {
        TypedValue resValue = new TypedValue();
        this.mActivity.getResources().getValue(resourceId, resValue, true);
        return resValue.getFloat();
    }

    private void bindMenuItemView(final ContextMenuItem menuItem, final View view) {
        int i;
        TextView actionTextView = (TextView) view.findViewById(C1188R.C1191id.title);
        actionTextView.setText(menuItem.getTitle());
        Context context = view.getContext();
        if (menuItem.isEnabled()) {
            i = C1188R.color.context_menu_text_enabled_color;
        } else {
            i = C1188R.color.context_menu_text_disabled_color;
        }
        actionTextView.setTextColor(context.getColor(i));
        ImageView menuIcon = (ImageView) view.findViewById(C1188R.C1191id.icon);
        menuIcon.setColorFilter(menuItem.isEnabled() ? this.mEnabledColor : this.mDisabledColor, PorterDuff.Mode.SRC_ATOP);
        if (menuItem.getIcon() != null) {
            menuIcon.setImageDrawable(menuItem.getIcon());
        }
        view.setBackgroundColor(this.mUnfocusedColor);
        if (Util.isAccessibilityEnabled(this.mActivity)) {
            view.setFocusable(true);
        } else {
            view.setFocusable(menuItem.isEnabled());
        }
        view.setEnabled(menuItem.isEnabled());
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (menuItem.isEnabled()) {
                    if (menuItem.isAutoDismiss()) {
                        ContextMenu.this.forceDismiss();
                    }
                    if (ContextMenu.this.mOnItemClickListener != null) {
                        ContextMenu.this.mOnItemClickListener.onItemClick(menuItem);
                    }
                }
            }
        });
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (menuItem.isLinkedWithTriangle()) {
                        ContextMenu.this.mTriangle.setColorFilter(ContextMenu.this.mFocusedColor, PorterDuff.Mode.SRC_ATOP);
                    }
                    view.setBackgroundColor(ContextMenu.this.mFocusedColor);
                    return;
                }
                if (menuItem.isLinkedWithTriangle()) {
                    ContextMenu.this.mTriangle.setColorFilter(ContextMenu.this.mUnfocusedColor, PorterDuff.Mode.SRC_ATOP);
                }
                view.setBackgroundColor(ContextMenu.this.mUnfocusedColor);
            }
        });
        this.mVisibleMenuItemViews.add(view);
    }

    private void addMenuItemViews() {
        this.mMenuLinearLayout.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) this.mActivity.getSystemService("layout_inflater");
        for (int i = 0; i < this.mVisibleItems.size(); i++) {
            View rowView = layoutInflater.inflate(C1188R.layout.context_menu_item, (ViewGroup) null);
            bindMenuItemView(this.mVisibleItems.get(i), rowView);
            this.mMenuLinearLayout.addView(rowView, -1, this.mMenuHeightPerRow);
        }
    }

    /* access modifiers changed from: private */
    public void alignCutoutOverlayToAnchor() {
        View view = this.mAnchor;
        if (view != null && this.mCutoutOverlay != null) {
            float[] mAnchorCoordinates = new float[2];
            getLocationInWindow(view, mAnchorCoordinates);
            if (Math.abs(mAnchorCoordinates[0] - this.mAnchorX) > FLOAT_COMPARISON_DELTA || Math.abs(mAnchorCoordinates[1] - this.mAnchorY) > FLOAT_COMPARISON_DELTA) {
                this.mAnchorX = mAnchorCoordinates[0];
                this.mAnchorY = mAnchorCoordinates[1];
                float f = this.mAnchorX;
                float f2 = this.mAnchorY;
                this.mCutoutOverlay.setAnchorRect(new RectF(f, f2, this.mAnchorRealWidth + f, this.mAnchorRealHeight + f2));
                this.mCutoutOverlay.invalidate();
            }
        }
    }

    @VisibleForTesting
    class CutoutOverlayLayout extends FrameLayout {
        private Paint mPaint = new Paint();
        private int mRadiusX;
        private int mRadiusY;
        private RectF mRect;

        public CutoutOverlayLayout(@NonNull Context context, int radiusX, int radiusY) {
            super(context);
            this.mRadiusX = radiusX;
            this.mRadiusY = radiusY;
            setWillNotDraw(false);
            setLayerType(2, null);
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            this.mPaint.setAntiAlias(true);
        }

        public void setAnchorRect(RectF rect) {
            this.mRect = rect;
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            canvas.drawColor(ContextMenu.this.mDimBackgroundColor);
            canvas.drawRoundRect(this.mRect, (float) this.mRadiusX, (float) this.mRadiusY, this.mPaint);
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public RectF getRect() {
            return this.mRect;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public int getRadiusX() {
            return this.mRadiusX;
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public int getRadiusY() {
            return this.mRadiusY;
        }
    }
}
