package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.p004v7.view.menu.ActionMenuItemView;
import android.support.p004v7.view.menu.MenuBuilder;
import android.support.p004v7.view.menu.MenuItemImpl;
import android.support.p004v7.view.menu.MenuPresenter;
import android.support.p004v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v7.widget.ActionMenuView */
public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    MenuBuilder.Callback mMenuBuilderCallback;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    private int mMinCellSize;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaselineAligned(false);
        float density = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * density);
        this.mGeneratedItemPadding = (int) (4.0f * density);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        View view = child;
        int i = cellsRemaining;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, View.MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (i > 0 && (!hasText || i >= 2)) {
            child.measure(View.MeasureSpec.makeMeasureSpec(cellSize * i, Integer.MIN_VALUE), childHeightSpec);
            int measuredWidth = child.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        if (!lp.isOverflowButton && hasText) {
            expandable = true;
        }
        lp.expandable = expandable;
        lp.cellsUsed = cellsUsed;
        child.measure(View.MeasureSpec.makeMeasureSpec(cellsUsed * cellSize, 1073741824), childHeightSpec);
        return cellsUsed;
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setPopupTheme(@StyleRes int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MenuBuilder menuBuilder;
        boolean wasFormatted = this.mFormatItems;
        this.mFormatItems = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        if (wasFormatted != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || (menuBuilder = this.mMenu) == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            menuBuilder.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.rightMargin = 0;
                lp.leftMargin = 0;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
    }

    /* JADX INFO: Multiple debug info for r18v1 int: [D('smallestItemsAt' long), D('heightSize' int)] */
    /* JADX INFO: Multiple debug info for r7v30 int: [D('isGeneratedItem' boolean), D('cellsUsed' int)] */
    private void onMeasureExactFormat(int widthMeasureSpec, int heightMeasureSpec) {
        int maxChildHeight;
        int widthSize;
        boolean needsExpansion;
        int heightSize;
        int heightPadding;
        int cellSizeRemaining;
        boolean z;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize2 = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int heightPadding2 = getPaddingTop() + getPaddingBottom();
        int itemHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding2, -2);
        int widthSize3 = widthSize2 - widthPadding;
        int i = this.mMinCellSize;
        int cellCount = widthSize3 / i;
        int cellSizeRemaining2 = widthSize3 % i;
        if (cellCount == 0) {
            setMeasuredDimension(widthSize3, 0);
            return;
        }
        int cellSize = i + (cellSizeRemaining2 / cellCount);
        boolean hasOverflow = false;
        int childCount = getChildCount();
        int heightSize3 = heightSize2;
        int maxChildHeight2 = 0;
        int visibleItemCount = 0;
        int expandableItemCount = 0;
        int maxCellsUsed = 0;
        int cellsRemaining = cellCount;
        int i2 = 0;
        long smallestItemsAt = 0;
        while (true) {
            int widthPadding2 = widthPadding;
            if (i2 >= childCount) {
                break;
            }
            View child = getChildAt(i2);
            int cellCount2 = cellCount;
            if (child.getVisibility() == 8) {
                heightPadding = heightPadding2;
                cellSizeRemaining = cellSizeRemaining2;
            } else {
                boolean isGeneratedItem = child instanceof ActionMenuItemView;
                visibleItemCount++;
                if (isGeneratedItem) {
                    int i3 = this.mGeneratedItemPadding;
                    cellSizeRemaining = cellSizeRemaining2;
                    z = false;
                    child.setPadding(i3, 0, i3, 0);
                } else {
                    cellSizeRemaining = cellSizeRemaining2;
                    z = false;
                }
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.expanded = z;
                lp.extraPixels = z ? 1 : 0;
                lp.cellsUsed = z;
                lp.expandable = z;
                lp.leftMargin = z;
                lp.rightMargin = z;
                lp.preventEdgeOffset = isGeneratedItem && ((ActionMenuItemView) child).hasText();
                int cellsUsed = measureChildForCells(child, cellSize, lp.isOverflowButton ? 1 : cellsRemaining, itemHeightSpec, heightPadding2);
                maxCellsUsed = Math.max(maxCellsUsed, cellsUsed);
                heightPadding = heightPadding2;
                if (lp.expandable != 0) {
                    expandableItemCount++;
                }
                if (lp.isOverflowButton) {
                    hasOverflow = true;
                }
                cellsRemaining -= cellsUsed;
                maxChildHeight2 = Math.max(maxChildHeight2, child.getMeasuredHeight());
                if (cellsUsed == 1) {
                    smallestItemsAt |= (long) (1 << i2);
                    maxChildHeight2 = maxChildHeight2;
                }
            }
            i2++;
            widthPadding = widthPadding2;
            cellCount = cellCount2;
            cellSizeRemaining2 = cellSizeRemaining;
            heightPadding2 = heightPadding;
        }
        boolean centerSingleExpandedItem = hasOverflow && visibleItemCount == 2;
        boolean needsExpansion2 = false;
        while (true) {
            if (expandableItemCount <= 0 || cellsRemaining <= 0) {
                widthSize = widthSize3;
                maxChildHeight = maxChildHeight2;
                needsExpansion = needsExpansion2;
            } else {
                long minCellsAt = 0;
                int minCells = Integer.MAX_VALUE;
                int minCellsItemCount = 0;
                int i4 = 0;
                while (i4 < childCount) {
                    boolean needsExpansion3 = needsExpansion2;
                    LayoutParams lp2 = (LayoutParams) getChildAt(i4).getLayoutParams();
                    if (lp2.expandable) {
                        if (lp2.cellsUsed < minCells) {
                            minCells = lp2.cellsUsed;
                            minCellsAt = 1 << i4;
                            minCellsItemCount = 1;
                        } else if (lp2.cellsUsed == minCells) {
                            minCellsAt |= 1 << i4;
                            minCellsItemCount++;
                        }
                    }
                    i4++;
                    needsExpansion2 = needsExpansion3;
                }
                needsExpansion = needsExpansion2;
                smallestItemsAt |= minCellsAt;
                if (minCellsItemCount > cellsRemaining) {
                    widthSize = widthSize3;
                    maxChildHeight = maxChildHeight2;
                    break;
                }
                int minCells2 = minCells + 1;
                int i5 = 0;
                while (i5 < childCount) {
                    View child2 = getChildAt(i5);
                    LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                    int minCellsItemCount2 = minCellsItemCount;
                    int widthSize4 = widthSize3;
                    int maxChildHeight3 = maxChildHeight2;
                    if ((minCellsAt & ((long) (1 << i5))) != 0) {
                        if (centerSingleExpandedItem && lp3.preventEdgeOffset && cellsRemaining == 1) {
                            int i6 = this.mGeneratedItemPadding;
                            child2.setPadding(i6 + cellSize, 0, i6, 0);
                        }
                        lp3.cellsUsed++;
                        lp3.expanded = true;
                        cellsRemaining--;
                    } else if (lp3.cellsUsed == minCells2) {
                        smallestItemsAt |= (long) (1 << i5);
                    }
                    i5++;
                    minCellsItemCount = minCellsItemCount2;
                    widthSize3 = widthSize4;
                    maxChildHeight2 = maxChildHeight3;
                }
                needsExpansion2 = true;
            }
        }
        widthSize = widthSize3;
        maxChildHeight = maxChildHeight2;
        needsExpansion = needsExpansion2;
        boolean singleItem = !hasOverflow && visibleItemCount == 1;
        if (cellsRemaining > 0 && smallestItemsAt != 0) {
            if (cellsRemaining < visibleItemCount - 1 || singleItem || maxCellsUsed > 1) {
                float expandCount = (float) Long.bitCount(smallestItemsAt);
                if (!singleItem) {
                    if ((smallestItemsAt & 1) != 0) {
                        if (!((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                            expandCount -= 0.5f;
                        }
                    }
                    if ((smallestItemsAt & ((long) (1 << (childCount - 1)))) != 0 && !((LayoutParams) getChildAt(childCount - 1).getLayoutParams()).preventEdgeOffset) {
                        expandCount -= 0.5f;
                    }
                }
                int extraPixels = expandCount > 0.0f ? (int) (((float) (cellsRemaining * cellSize)) / expandCount) : 0;
                int i7 = 0;
                boolean needsExpansion4 = needsExpansion;
                while (i7 < childCount) {
                    boolean singleItem2 = singleItem;
                    float expandCount2 = expandCount;
                    if ((smallestItemsAt & ((long) (1 << i7))) != 0) {
                        View child3 = getChildAt(i7);
                        LayoutParams lp4 = (LayoutParams) child3.getLayoutParams();
                        if (child3 instanceof ActionMenuItemView) {
                            lp4.extraPixels = extraPixels;
                            lp4.expanded = true;
                            if (i7 == 0 && !lp4.preventEdgeOffset) {
                                lp4.leftMargin = (-extraPixels) / 2;
                            }
                            needsExpansion4 = true;
                        } else if (lp4.isOverflowButton) {
                            lp4.extraPixels = extraPixels;
                            lp4.expanded = true;
                            lp4.rightMargin = (-extraPixels) / 2;
                            needsExpansion4 = true;
                        } else {
                            if (i7 != 0) {
                                lp4.leftMargin = extraPixels / 2;
                            }
                            if (i7 != childCount - 1) {
                                lp4.rightMargin = extraPixels / 2;
                            }
                        }
                    }
                    i7++;
                    singleItem = singleItem2;
                    expandCount = expandCount2;
                }
                needsExpansion = needsExpansion4;
            }
        }
        if (needsExpansion) {
            for (int i8 = 0; i8 < childCount; i8++) {
                View child4 = getChildAt(i8);
                LayoutParams lp5 = (LayoutParams) child4.getLayoutParams();
                if (lp5.expanded) {
                    child4.measure(View.MeasureSpec.makeMeasureSpec((lp5.cellsUsed * cellSize) + lp5.extraPixels, 1073741824), itemHeightSpec);
                }
            }
        }
        if (heightMode != 1073741824) {
            heightSize = maxChildHeight;
        } else {
            heightSize = heightSize3;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    /* JADX INFO: Multiple debug info for r9v11 int: [D('t' int), D('isLayoutRtl' boolean)] */
    /* JADX INFO: Multiple debug info for r2v10 int: [D('midVertical' int), D('b' int)] */
    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int i;
        int overflowWidth;
        int dividerWidth;
        boolean isLayoutRtl;
        int midVertical;
        int r;
        int l;
        ActionMenuView actionMenuView = this;
        if (!actionMenuView.mFormatItems) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }
        int childCount = getChildCount();
        int midVertical2 = (bottom - top) / 2;
        int dividerWidth2 = getDividerWidth();
        int overflowWidth2 = 0;
        int nonOverflowWidth = 0;
        int nonOverflowCount = 0;
        int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
        boolean hasOverflow = false;
        boolean isLayoutRtl2 = ViewUtils.isLayoutRtl(this);
        int i2 = 0;
        while (true) {
            i = 8;
            if (i2 >= childCount) {
                break;
            }
            View v = actionMenuView.getChildAt(i2);
            if (v.getVisibility() == 8) {
                midVertical = midVertical2;
                isLayoutRtl = isLayoutRtl2;
            } else {
                LayoutParams p = (LayoutParams) v.getLayoutParams();
                if (p.isOverflowButton) {
                    overflowWidth2 = v.getMeasuredWidth();
                    if (actionMenuView.hasSupportDividerBeforeChildAt(i2)) {
                        overflowWidth2 += dividerWidth2;
                    }
                    int height = v.getMeasuredHeight();
                    if (isLayoutRtl2) {
                        l = getPaddingLeft() + p.leftMargin;
                        r = l + overflowWidth2;
                    } else {
                        r = (getWidth() - getPaddingRight()) - p.rightMargin;
                        l = r - overflowWidth2;
                    }
                    isLayoutRtl = isLayoutRtl2;
                    int t = midVertical2 - (height / 2);
                    midVertical = midVertical2;
                    v.layout(l, t, r, t + height);
                    widthRemaining -= overflowWidth2;
                    hasOverflow = true;
                } else {
                    midVertical = midVertical2;
                    isLayoutRtl = isLayoutRtl2;
                    int size = v.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    nonOverflowWidth += size;
                    widthRemaining -= size;
                    if (actionMenuView.hasSupportDividerBeforeChildAt(i2)) {
                        nonOverflowWidth += dividerWidth2;
                    }
                    nonOverflowCount++;
                }
            }
            i2++;
            midVertical2 = midVertical;
            isLayoutRtl2 = isLayoutRtl;
        }
        int midVertical3 = midVertical2;
        boolean isLayoutRtl3 = isLayoutRtl2;
        int i3 = 1;
        if (childCount != 1 || hasOverflow) {
            if (hasOverflow) {
                i3 = 0;
            }
            int spacerCount = nonOverflowCount - i3;
            int spacerSize = Math.max(0, spacerCount > 0 ? widthRemaining / spacerCount : 0);
            if (isLayoutRtl3) {
                int startRight = getWidth() - getPaddingRight();
                int i4 = 0;
                while (i4 < childCount) {
                    View v2 = actionMenuView.getChildAt(i4);
                    LayoutParams lp = (LayoutParams) v2.getLayoutParams();
                    if (v2.getVisibility() == i) {
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                    } else if (lp.isOverflowButton) {
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                    } else {
                        int startRight2 = startRight - lp.rightMargin;
                        int width = v2.getMeasuredWidth();
                        int height2 = v2.getMeasuredHeight();
                        int t2 = midVertical3 - (height2 / 2);
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                        v2.layout(startRight2 - width, t2, startRight2, t2 + height2);
                        startRight = startRight2 - ((lp.leftMargin + width) + spacerSize);
                    }
                    i4++;
                    dividerWidth2 = dividerWidth;
                    overflowWidth2 = overflowWidth;
                    i = 8;
                }
                return;
            }
            int startLeft = getPaddingLeft();
            int i5 = 0;
            while (i5 < childCount) {
                View v3 = actionMenuView.getChildAt(i5);
                LayoutParams lp2 = (LayoutParams) v3.getLayoutParams();
                if (v3.getVisibility() != 8 && !lp2.isOverflowButton) {
                    int startLeft2 = startLeft + lp2.leftMargin;
                    int width2 = v3.getMeasuredWidth();
                    int height3 = v3.getMeasuredHeight();
                    int t3 = midVertical3 - (height3 / 2);
                    v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
                    startLeft = startLeft2 + lp2.rightMargin + width2 + spacerSize;
                }
                i5++;
                actionMenuView = this;
            }
            return;
        }
        View v4 = actionMenuView.getChildAt(0);
        int width3 = v4.getMeasuredWidth();
        int height4 = v4.getMeasuredHeight();
        int l2 = ((right - left) / 2) - (width3 / 2);
        int t4 = midVertical3 - (height4 / 2);
        v4.layout(l2, t4, l2 + width3, t4 + height4);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    @Nullable
    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public void setOverflowIcon(@Nullable Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams result = p instanceof LayoutParams ? new LayoutParams((LayoutParams) p) : new LayoutParams(p);
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getWindowAnimations() {
        return 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter = this.mPresenter;
            MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            if (callback == null) {
                callback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter.setCallback(callback);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean hasSupportDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex <= 0 || !(child instanceof ActionMenuChildView)) {
            return result;
        }
        return result | ((ActionMenuChildView) child).needsDividerBefore();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* renamed from: android.support.v7.widget.ActionMenuView$ActionMenuChildView */
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$OnMenuItemClickListener */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$ActionMenuPresenterCallback */
    private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$LayoutParams */
    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;
        boolean expanded;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
        }

        public LayoutParams(LayoutParams other) {
            super((ViewGroup.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.isOverflowButton = false;
        }

        LayoutParams(int width, int height, boolean isOverflowButton2) {
            super(width, height);
            this.isOverflowButton = isOverflowButton2;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$MenuBuilderCallback */
    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }
}
