package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.p004v7.widget.RecyclerView;
import android.support.p004v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.leanback.C0364R;

public abstract class BaseGridView extends RecyclerView {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int FOCUS_SCROLL_ALIGNED = 0;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int FOCUS_SCROLL_ITEM = 1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int FOCUS_SCROLL_PAGE = 2;
    public static final float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    private static final int PFLAG_RETAIN_FOCUS_FOR_CHILD = 1;
    public static final int SAVE_ALL_CHILD = 3;
    public static final int SAVE_LIMITED_CHILD = 2;
    public static final int SAVE_NO_CHILD = 0;
    public static final int SAVE_ON_SCREEN_CHILD = 1;
    public static final int WINDOW_ALIGN_BOTH_EDGE = 3;
    public static final int WINDOW_ALIGN_HIGH_EDGE = 2;
    public static final int WINDOW_ALIGN_LOW_EDGE = 1;
    public static final int WINDOW_ALIGN_NO_EDGE = 0;
    public static final float WINDOW_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    private boolean mAnimateChildLayout = true;
    RecyclerView.RecyclerListener mChainedRecyclerListener;
    private boolean mHasOverlappingRendering = true;
    int mInitialPrefetchItemCount = 4;
    final GridLayoutManager mLayoutManager = new GridLayoutManager(this);
    private OnKeyInterceptListener mOnKeyInterceptListener;
    private OnMotionInterceptListener mOnMotionInterceptListener;
    private OnTouchInterceptListener mOnTouchInterceptListener;
    private OnUnhandledKeyListener mOnUnhandledKeyListener;
    private int mPrivateFlag;
    private RecyclerView.ItemAnimator mSavedItemAnimator;

    public interface OnKeyInterceptListener {
        boolean onInterceptKeyEvent(KeyEvent keyEvent);
    }

    public interface OnMotionInterceptListener {
        boolean onInterceptMotionEvent(MotionEvent motionEvent);
    }

    public interface OnTouchInterceptListener {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);
    }

    public interface OnUnhandledKeyListener {
        boolean onUnhandledKey(KeyEvent keyEvent);
    }

    BaseGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(this.mLayoutManager);
        setPreserveFocusAfterLayout(false);
        setDescendantFocusability(262144);
        setHasFixedSize(true);
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(true);
        setOverScrollMode(2);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
        super.setRecyclerListener(new RecyclerView.RecyclerListener() {
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                BaseGridView.this.mLayoutManager.onChildRecycled(holder);
                if (BaseGridView.this.mChainedRecyclerListener != null) {
                    BaseGridView.this.mChainedRecyclerListener.onViewRecycled(holder);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void initBaseGridViewAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0364R.styleable.lbBaseGridView);
        this.mLayoutManager.setFocusOutAllowed(a.getBoolean(C0364R.styleable.lbBaseGridView_focusOutFront, false), a.getBoolean(C0364R.styleable.lbBaseGridView_focusOutEnd, false));
        this.mLayoutManager.setFocusOutSideAllowed(a.getBoolean(C0364R.styleable.lbBaseGridView_focusOutSideStart, true), a.getBoolean(C0364R.styleable.lbBaseGridView_focusOutSideEnd, true));
        this.mLayoutManager.setVerticalSpacing(a.getDimensionPixelSize(C0364R.styleable.lbBaseGridView_android_verticalSpacing, a.getDimensionPixelSize(C0364R.styleable.lbBaseGridView_verticalMargin, 0)));
        this.mLayoutManager.setHorizontalSpacing(a.getDimensionPixelSize(C0364R.styleable.lbBaseGridView_android_horizontalSpacing, a.getDimensionPixelSize(C0364R.styleable.lbBaseGridView_horizontalMargin, 0)));
        if (a.hasValue(C0364R.styleable.lbBaseGridView_android_gravity)) {
            setGravity(a.getInt(C0364R.styleable.lbBaseGridView_android_gravity, 0));
        }
        a.recycle();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setFocusScrollStrategy(int scrollStrategy) {
        if (scrollStrategy == 0 || scrollStrategy == 1 || scrollStrategy == 2) {
            this.mLayoutManager.setFocusScrollStrategy(scrollStrategy);
            requestLayout();
            return;
        }
        throw new IllegalArgumentException("Invalid scrollStrategy");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getFocusScrollStrategy() {
        return this.mLayoutManager.getFocusScrollStrategy();
    }

    public void setWindowAlignment(int windowAlignment) {
        this.mLayoutManager.setWindowAlignment(windowAlignment);
        requestLayout();
    }

    public int getWindowAlignment() {
        return this.mLayoutManager.getWindowAlignment();
    }

    public void setWindowAlignmentPreferKeyLineOverLowEdge(boolean preferKeyLineOverLowEdge) {
        this.mLayoutManager.mWindowAlignment.mainAxis().setPreferKeylineOverLowEdge(preferKeyLineOverLowEdge);
        requestLayout();
    }

    public void setWindowAlignmentPreferKeyLineOverHighEdge(boolean preferKeyLineOverHighEdge) {
        this.mLayoutManager.mWindowAlignment.mainAxis().setPreferKeylineOverHighEdge(preferKeyLineOverHighEdge);
        requestLayout();
    }

    public boolean isWindowAlignmentPreferKeyLineOverLowEdge() {
        return this.mLayoutManager.mWindowAlignment.mainAxis().isPreferKeylineOverLowEdge();
    }

    public boolean isWindowAlignmentPreferKeyLineOverHighEdge() {
        return this.mLayoutManager.mWindowAlignment.mainAxis().isPreferKeylineOverHighEdge();
    }

    public void setWindowAlignmentOffset(int offset) {
        this.mLayoutManager.setWindowAlignmentOffset(offset);
        requestLayout();
    }

    public int getWindowAlignmentOffset() {
        return this.mLayoutManager.getWindowAlignmentOffset();
    }

    public void setWindowAlignmentOffsetPercent(float offsetPercent) {
        this.mLayoutManager.setWindowAlignmentOffsetPercent(offsetPercent);
        requestLayout();
    }

    public float getWindowAlignmentOffsetPercent() {
        return this.mLayoutManager.getWindowAlignmentOffsetPercent();
    }

    public void setItemAlignmentOffset(int offset) {
        this.mLayoutManager.setItemAlignmentOffset(offset);
        requestLayout();
    }

    public int getItemAlignmentOffset() {
        return this.mLayoutManager.getItemAlignmentOffset();
    }

    public void setItemAlignmentOffsetWithPadding(boolean withPadding) {
        this.mLayoutManager.setItemAlignmentOffsetWithPadding(withPadding);
        requestLayout();
    }

    public boolean isItemAlignmentOffsetWithPadding() {
        return this.mLayoutManager.isItemAlignmentOffsetWithPadding();
    }

    public void setItemAlignmentOffsetPercent(float offsetPercent) {
        this.mLayoutManager.setItemAlignmentOffsetPercent(offsetPercent);
        requestLayout();
    }

    public float getItemAlignmentOffsetPercent() {
        return this.mLayoutManager.getItemAlignmentOffsetPercent();
    }

    public void setItemAlignmentViewId(int viewId) {
        this.mLayoutManager.setItemAlignmentViewId(viewId);
    }

    public int getItemAlignmentViewId() {
        return this.mLayoutManager.getItemAlignmentViewId();
    }

    @Deprecated
    public void setItemMargin(int margin) {
        setItemSpacing(margin);
    }

    public void setItemSpacing(int spacing) {
        this.mLayoutManager.setItemSpacing(spacing);
        requestLayout();
    }

    @Deprecated
    public void setVerticalMargin(int margin) {
        setVerticalSpacing(margin);
    }

    @Deprecated
    public int getVerticalMargin() {
        return this.mLayoutManager.getVerticalSpacing();
    }

    @Deprecated
    public void setHorizontalMargin(int margin) {
        setHorizontalSpacing(margin);
    }

    @Deprecated
    public int getHorizontalMargin() {
        return this.mLayoutManager.getHorizontalSpacing();
    }

    public void setVerticalSpacing(int spacing) {
        this.mLayoutManager.setVerticalSpacing(spacing);
        requestLayout();
    }

    public int getVerticalSpacing() {
        return this.mLayoutManager.getVerticalSpacing();
    }

    public void setHorizontalSpacing(int spacing) {
        this.mLayoutManager.setHorizontalSpacing(spacing);
        requestLayout();
    }

    public int getHorizontalSpacing() {
        return this.mLayoutManager.getHorizontalSpacing();
    }

    public void setOnChildLaidOutListener(OnChildLaidOutListener listener) {
        this.mLayoutManager.setOnChildLaidOutListener(listener);
    }

    public void setOnChildSelectedListener(OnChildSelectedListener listener) {
        this.mLayoutManager.setOnChildSelectedListener(listener);
    }

    public void setOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener listener) {
        this.mLayoutManager.setOnChildViewHolderSelectedListener(listener);
    }

    public void addOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener listener) {
        this.mLayoutManager.addOnChildViewHolderSelectedListener(listener);
    }

    public void removeOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener listener) {
        this.mLayoutManager.removeOnChildViewHolderSelectedListener(listener);
    }

    public void setSelectedPosition(int position) {
        this.mLayoutManager.setSelection(position, 0);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSelectedPositionWithSub(int position, int subposition) {
        this.mLayoutManager.setSelectionWithSub(position, subposition, 0);
    }

    public void setSelectedPosition(int position, int scrollExtra) {
        this.mLayoutManager.setSelection(position, scrollExtra);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSelectedPositionWithSub(int position, int subposition, int scrollExtra) {
        this.mLayoutManager.setSelectionWithSub(position, subposition, scrollExtra);
    }

    public void setSelectedPositionSmooth(int position) {
        this.mLayoutManager.setSelectionSmooth(position);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSelectedPositionSmoothWithSub(int position, int subposition) {
        this.mLayoutManager.setSelectionSmoothWithSub(position, subposition);
    }

    public void setSelectedPositionSmooth(final int position, final ViewHolderTask task) {
        if (task != null) {
            RecyclerView.ViewHolder vh = findViewHolderForPosition(position);
            if (vh == null || hasPendingAdapterUpdates()) {
                addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
                    public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int selectedPosition, int subposition) {
                        if (selectedPosition == position) {
                            BaseGridView.this.removeOnChildViewHolderSelectedListener(this);
                            task.run(child);
                        }
                    }
                });
            } else {
                task.run(vh);
            }
        }
        setSelectedPositionSmooth(position);
    }

    public void setSelectedPosition(final int position, final ViewHolderTask task) {
        if (task != null) {
            RecyclerView.ViewHolder vh = findViewHolderForPosition(position);
            if (vh == null || hasPendingAdapterUpdates()) {
                addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
                    public void onChildViewHolderSelectedAndPositioned(RecyclerView parent, RecyclerView.ViewHolder child, int selectedPosition, int subposition) {
                        if (selectedPosition == position) {
                            BaseGridView.this.removeOnChildViewHolderSelectedListener(this);
                            task.run(child);
                        }
                    }
                });
            } else {
                task.run(vh);
            }
        }
        setSelectedPosition(position);
    }

    public int getSelectedPosition() {
        return this.mLayoutManager.getSelection();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getSelectedSubPosition() {
        return this.mLayoutManager.getSubSelection();
    }

    public void setAnimateChildLayout(boolean animateChildLayout) {
        if (this.mAnimateChildLayout != animateChildLayout) {
            this.mAnimateChildLayout = animateChildLayout;
            if (!this.mAnimateChildLayout) {
                this.mSavedItemAnimator = getItemAnimator();
                super.setItemAnimator(null);
                return;
            }
            super.setItemAnimator(this.mSavedItemAnimator);
        }
    }

    public boolean isChildLayoutAnimated() {
        return this.mAnimateChildLayout;
    }

    public void setGravity(int gravity) {
        this.mLayoutManager.setGravity(gravity);
        requestLayout();
    }

    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        if ((this.mPrivateFlag & 1) == 1) {
            return false;
        }
        return this.mLayoutManager.gridOnRequestFocusInDescendants(this, direction, previouslyFocusedRect);
    }

    public void getViewSelectedOffsets(View view, int[] offsets) {
        this.mLayoutManager.getViewSelectedOffsets(view, offsets);
    }

    public int getChildDrawingOrder(int childCount, int i) {
        return this.mLayoutManager.getChildDrawingOrder(this, childCount, i);
    }

    /* access modifiers changed from: package-private */
    public final boolean isChildrenDrawingOrderEnabledInternal() {
        return isChildrenDrawingOrderEnabled();
    }

    public View focusSearch(int direction) {
        if (isFocused()) {
            GridLayoutManager gridLayoutManager = this.mLayoutManager;
            View view = gridLayoutManager.findViewByPosition(gridLayoutManager.getSelection());
            if (view != null) {
                return focusSearch(view, direction);
            }
        }
        return super.focusSearch(direction);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        this.mLayoutManager.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    public final void setFocusSearchDisabled(boolean disabled) {
        setDescendantFocusability(disabled ? 393216 : 262144);
        this.mLayoutManager.setFocusSearchDisabled(disabled);
    }

    public final boolean isFocusSearchDisabled() {
        return this.mLayoutManager.isFocusSearchDisabled();
    }

    public void setLayoutEnabled(boolean layoutEnabled) {
        this.mLayoutManager.setLayoutEnabled(layoutEnabled);
    }

    public void setChildrenVisibility(int visibility) {
        this.mLayoutManager.setChildrenVisibility(visibility);
    }

    public void setPruneChild(boolean pruneChild) {
        this.mLayoutManager.setPruneChild(pruneChild);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.mLayoutManager.setScrollEnabled(scrollEnabled);
    }

    public boolean isScrollEnabled() {
        return this.mLayoutManager.isScrollEnabled();
    }

    public boolean hasPreviousViewInSameRow(int position) {
        return this.mLayoutManager.hasPreviousViewInSameRow(position);
    }

    public void setFocusDrawingOrderEnabled(boolean enabled) {
        super.setChildrenDrawingOrderEnabled(enabled);
    }

    public boolean isFocusDrawingOrderEnabled() {
        return super.isChildrenDrawingOrderEnabled();
    }

    public void setOnTouchInterceptListener(OnTouchInterceptListener listener) {
        this.mOnTouchInterceptListener = listener;
    }

    public void setOnMotionInterceptListener(OnMotionInterceptListener listener) {
        this.mOnMotionInterceptListener = listener;
    }

    public void setOnKeyInterceptListener(OnKeyInterceptListener listener) {
        this.mOnKeyInterceptListener = listener;
    }

    public void setOnUnhandledKeyListener(OnUnhandledKeyListener listener) {
        this.mOnUnhandledKeyListener = listener;
    }

    public OnUnhandledKeyListener getOnUnhandledKeyListener() {
        return this.mOnUnhandledKeyListener;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        OnKeyInterceptListener onKeyInterceptListener = this.mOnKeyInterceptListener;
        if ((onKeyInterceptListener != null && onKeyInterceptListener.onInterceptKeyEvent(event)) || super.dispatchKeyEvent(event)) {
            return true;
        }
        OnUnhandledKeyListener onUnhandledKeyListener = this.mOnUnhandledKeyListener;
        if (onUnhandledKeyListener == null || !onUnhandledKeyListener.onUnhandledKey(event)) {
            return false;
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        OnTouchInterceptListener onTouchInterceptListener = this.mOnTouchInterceptListener;
        if (onTouchInterceptListener == null || !onTouchInterceptListener.onInterceptTouchEvent(event)) {
            return super.dispatchTouchEvent(event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchGenericFocusedEvent(MotionEvent event) {
        OnMotionInterceptListener onMotionInterceptListener = this.mOnMotionInterceptListener;
        if (onMotionInterceptListener == null || !onMotionInterceptListener.onInterceptMotionEvent(event)) {
            return super.dispatchGenericFocusedEvent(event);
        }
        return true;
    }

    public final int getSaveChildrenPolicy() {
        return this.mLayoutManager.mChildrenStates.getSavePolicy();
    }

    public final int getSaveChildrenLimitNumber() {
        return this.mLayoutManager.mChildrenStates.getLimitNumber();
    }

    public final void setSaveChildrenPolicy(int savePolicy) {
        this.mLayoutManager.mChildrenStates.setSavePolicy(savePolicy);
    }

    public final void setSaveChildrenLimitNumber(int limitNumber) {
        this.mLayoutManager.mChildrenStates.setLimitNumber(limitNumber);
    }

    public boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    public void setHasOverlappingRendering(boolean hasOverlapping) {
        this.mHasOverlappingRendering = hasOverlapping;
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        this.mLayoutManager.onRtlPropertiesChanged(layoutDirection);
    }

    public void setRecyclerListener(RecyclerView.RecyclerListener listener) {
        this.mChainedRecyclerListener = listener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setExtraLayoutSpace(int extraLayoutSpace) {
        this.mLayoutManager.setExtraLayoutSpace(extraLayoutSpace);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getExtraLayoutSpace() {
        return this.mLayoutManager.getExtraLayoutSpace();
    }

    public void animateOut() {
        this.mLayoutManager.slideOut();
    }

    public void animateIn() {
        this.mLayoutManager.slideIn();
    }

    public void scrollToPosition(int position) {
        if (this.mLayoutManager.isSlidingChildViews()) {
            this.mLayoutManager.setSelectionWithSub(position, 0, 0);
        } else {
            super.scrollToPosition(position);
        }
    }

    public void smoothScrollToPosition(int position) {
        if (this.mLayoutManager.isSlidingChildViews()) {
            this.mLayoutManager.setSelectionWithSub(position, 0, 0);
        } else {
            super.smoothScrollToPosition(position);
        }
    }

    public void setInitialPrefetchItemCount(int itemCount) {
        this.mInitialPrefetchItemCount = itemCount;
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }

    public void removeView(View view) {
        boolean retainFocusForChild = view.hasFocus() && isFocusable();
        if (retainFocusForChild) {
            this.mPrivateFlag = 1 | this.mPrivateFlag;
            requestFocus();
        }
        super.removeView(view);
        if (retainFocusForChild) {
            this.mPrivateFlag ^= -2;
        }
    }

    public void removeViewAt(int index) {
        boolean retainFocusForChild = getChildAt(index).hasFocus();
        if (retainFocusForChild) {
            this.mPrivateFlag |= 1;
            requestFocus();
        }
        super.removeViewAt(index);
        if (retainFocusForChild) {
            this.mPrivateFlag ^= -2;
        }
    }
}
