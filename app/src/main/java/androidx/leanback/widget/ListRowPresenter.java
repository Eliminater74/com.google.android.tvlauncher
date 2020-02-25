package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.p004v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.C0364R;
import androidx.leanback.system.Settings;
import androidx.leanback.transition.TransitionHelper;

import java.util.HashMap;

public class ListRowPresenter extends RowPresenter {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_RECYCLED_POOL_SIZE = 24;
    private static final String TAG = "ListRowPresenter";
    private static int sExpandedRowNoHovercardBottomPadding;
    private static int sExpandedSelectedRowTopPadding;
    private static int sSelectedRowTopPadding;
    ShadowOverlayHelper mShadowOverlayHelper;
    private int mBrowseRowsFadingEdgeLength;
    private int mExpandedRowHeight;
    private int mFocusZoomFactor;
    private PresenterSelector mHoverCardPresenterSelector;
    private boolean mKeepChildForeground;
    private int mNumRows;
    private HashMap<Presenter, Integer> mRecycledPoolSize;
    private boolean mRoundedCornersEnabled;
    private int mRowHeight;
    private boolean mShadowEnabled;
    private ItemBridgeAdapter.Wrapper mShadowOverlayWrapper;
    private boolean mUseFocusDimmer;

    public ListRowPresenter() {
        this(2);
    }

    public ListRowPresenter(int focusZoomFactor) {
        this(focusZoomFactor, false);
    }

    public ListRowPresenter(int focusZoomFactor, boolean useFocusDimmer) {
        this.mNumRows = 1;
        this.mShadowEnabled = true;
        this.mBrowseRowsFadingEdgeLength = -1;
        this.mRoundedCornersEnabled = true;
        this.mKeepChildForeground = true;
        this.mRecycledPoolSize = new HashMap<>();
        if (FocusHighlightHelper.isValidZoomIndex(focusZoomFactor)) {
            this.mFocusZoomFactor = focusZoomFactor;
            this.mUseFocusDimmer = useFocusDimmer;
            return;
        }
        throw new IllegalArgumentException("Unhandled zoom factor");
    }

    private static void initStatics(Context context) {
        if (sSelectedRowTopPadding == 0) {
            sSelectedRowTopPadding = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_browse_selected_row_top_padding);
            sExpandedSelectedRowTopPadding = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_browse_expanded_selected_row_top_padding);
            sExpandedRowNoHovercardBottomPadding = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_browse_expanded_row_no_hovercard_bottom_padding);
        }
    }

    public int getRowHeight() {
        return this.mRowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.mRowHeight = rowHeight;
    }

    public int getExpandedRowHeight() {
        int i = this.mExpandedRowHeight;
        return i != 0 ? i : this.mRowHeight;
    }

    public void setExpandedRowHeight(int rowHeight) {
        this.mExpandedRowHeight = rowHeight;
    }

    public final int getFocusZoomFactor() {
        return this.mFocusZoomFactor;
    }

    @Deprecated
    public final int getZoomFactor() {
        return this.mFocusZoomFactor;
    }

    public final boolean isFocusDimmerUsed() {
        return this.mUseFocusDimmer;
    }

    public void setNumRows(int numRows) {
        this.mNumRows = numRows;
    }

    /* access modifiers changed from: protected */
    public void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;
        Context context = holder.view.getContext();
        boolean z = true;
        if (this.mShadowOverlayHelper == null) {
            this.mShadowOverlayHelper = new ShadowOverlayHelper.Builder().needsOverlay(needsDefaultListSelectEffect()).needsShadow(needsDefaultShadow()).needsRoundedCorner(isUsingOutlineClipping(context) && areChildRoundedCornersEnabled()).preferZOrder(isUsingZOrder(context)).keepForegroundDrawable(this.mKeepChildForeground).options(createShadowOverlayOptions()).build(context);
            if (this.mShadowOverlayHelper.needsWrapper()) {
                this.mShadowOverlayWrapper = new ItemBridgeAdapterShadowOverlayWrapper(this.mShadowOverlayHelper);
            }
        }
        rowViewHolder.mItemBridgeAdapter = new ListRowPresenterItemBridgeAdapter(rowViewHolder);
        rowViewHolder.mItemBridgeAdapter.setWrapper(this.mShadowOverlayWrapper);
        this.mShadowOverlayHelper.prepareParentForShadow(rowViewHolder.mGridView);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(rowViewHolder.mItemBridgeAdapter, this.mFocusZoomFactor, this.mUseFocusDimmer);
        HorizontalGridView horizontalGridView = rowViewHolder.mGridView;
        if (this.mShadowOverlayHelper.getShadowType() == 3) {
            z = false;
        }
        horizontalGridView.setFocusDrawingOrderEnabled(z);
        rowViewHolder.mGridView.setOnChildSelectedListener(new OnChildSelectedListener() {
            public void onChildSelected(ViewGroup parent, View view, int position, long id) {
                ListRowPresenter.this.selectChildView(rowViewHolder, view, true);
            }
        });
        rowViewHolder.mGridView.setOnUnhandledKeyListener(new BaseGridView.OnUnhandledKeyListener() {
            public boolean onUnhandledKey(KeyEvent event) {
                return rowViewHolder.getOnKeyListener() != null && rowViewHolder.getOnKeyListener().onKey(rowViewHolder.view, event.getKeyCode(), event);
            }
        });
        rowViewHolder.mGridView.setNumRows(this.mNumRows);
    }

    /* access modifiers changed from: package-private */
    public final boolean needsDefaultListSelectEffect() {
        return isUsingDefaultListSelectEffect() && getSelectEffectEnabled();
    }

    public void setRecycledPoolSize(Presenter presenter, int size) {
        this.mRecycledPoolSize.put(presenter, Integer.valueOf(size));
    }

    public int getRecycledPoolSize(Presenter presenter) {
        if (this.mRecycledPoolSize.containsKey(presenter)) {
            return this.mRecycledPoolSize.get(presenter).intValue();
        }
        return 24;
    }

    public final PresenterSelector getHoverCardPresenterSelector() {
        return this.mHoverCardPresenterSelector;
    }

    public final void setHoverCardPresenterSelector(PresenterSelector selector) {
        this.mHoverCardPresenterSelector = selector;
    }

    /* access modifiers changed from: package-private */
    public void selectChildView(ViewHolder rowViewHolder, View view, boolean fireEvent) {
        if (view == null) {
            if (this.mHoverCardPresenterSelector != null) {
                rowViewHolder.mHoverCardViewSwitcher.unselect();
            }
            if (fireEvent && rowViewHolder.getOnItemViewSelectedListener() != null) {
                rowViewHolder.getOnItemViewSelectedListener().onItemSelected(null, null, rowViewHolder, rowViewHolder.mRow);
            }
        } else if (rowViewHolder.mSelected) {
            ItemBridgeAdapter.ViewHolder ibh = (ItemBridgeAdapter.ViewHolder) rowViewHolder.mGridView.getChildViewHolder(view);
            if (this.mHoverCardPresenterSelector != null) {
                rowViewHolder.mHoverCardViewSwitcher.select(rowViewHolder.mGridView, view, ibh.mItem);
            }
            if (fireEvent && rowViewHolder.getOnItemViewSelectedListener() != null) {
                rowViewHolder.getOnItemViewSelectedListener().onItemSelected(ibh.mHolder, ibh.mItem, rowViewHolder, rowViewHolder.mRow);
            }
        }
    }

    private int getSpaceUnderBaseline(ViewHolder vh) {
        RowHeaderPresenter.ViewHolder headerViewHolder = vh.getHeaderViewHolder();
        if (headerViewHolder == null) {
            return 0;
        }
        if (getHeaderPresenter() != null) {
            return getHeaderPresenter().getSpaceUnderBaseline(headerViewHolder);
        }
        return headerViewHolder.view.getPaddingBottom();
    }

    private void setVerticalPadding(ViewHolder vh) {
        int paddingTop;
        int headerSpaceUnderBaseline;
        if (vh.isExpanded()) {
            paddingTop = (vh.isSelected() ? sExpandedSelectedRowTopPadding : vh.mPaddingTop) - getSpaceUnderBaseline(vh);
            headerSpaceUnderBaseline = this.mHoverCardPresenterSelector == null ? sExpandedRowNoHovercardBottomPadding : vh.mPaddingBottom;
        } else if (vh.isSelected() != 0) {
            paddingTop = sSelectedRowTopPadding - vh.mPaddingBottom;
            headerSpaceUnderBaseline = sSelectedRowTopPadding;
        } else {
            paddingTop = 0;
            headerSpaceUnderBaseline = vh.mPaddingBottom;
        }
        vh.getGridView().setPadding(vh.mPaddingLeft, paddingTop, vh.mPaddingRight, headerSpaceUnderBaseline);
    }

    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        initStatics(parent.getContext());
        ListRowView rowView = new ListRowView(parent.getContext());
        setupFadingEffect(rowView);
        if (this.mRowHeight != 0) {
            rowView.getGridView().setRowHeight(this.mRowHeight);
        }
        return new ViewHolder(rowView, rowView.getGridView(), this);
    }

    /* access modifiers changed from: protected */
    public void dispatchItemSelectedListener(RowPresenter.ViewHolder holder, boolean selected) {
        ViewHolder vh = (ViewHolder) holder;
        ItemBridgeAdapter.ViewHolder itemViewHolder = (ItemBridgeAdapter.ViewHolder) vh.mGridView.findViewHolderForPosition(vh.mGridView.getSelectedPosition());
        if (itemViewHolder == null) {
            super.dispatchItemSelectedListener(holder, selected);
        } else if (selected && holder.getOnItemViewSelectedListener() != null) {
            holder.getOnItemViewSelectedListener().onItemSelected(itemViewHolder.getViewHolder(), itemViewHolder.mItem, vh, vh.getRow());
        }
    }

    /* access modifiers changed from: protected */
    public void onRowViewSelected(RowPresenter.ViewHolder holder, boolean selected) {
        super.onRowViewSelected(holder, selected);
        ViewHolder vh = (ViewHolder) holder;
        setVerticalPadding(vh);
        updateFooterViewSwitcher(vh);
    }

    private void updateFooterViewSwitcher(ViewHolder vh) {
        if (vh.mExpanded && vh.mSelected) {
            if (this.mHoverCardPresenterSelector != null) {
                vh.mHoverCardViewSwitcher.init((ViewGroup) vh.view, this.mHoverCardPresenterSelector);
            }
            ItemBridgeAdapter.ViewHolder ibh = (ItemBridgeAdapter.ViewHolder) vh.mGridView.findViewHolderForPosition(vh.mGridView.getSelectedPosition());
            selectChildView(vh, ibh == null ? null : ibh.itemView, false);
        } else if (this.mHoverCardPresenterSelector != null) {
            vh.mHoverCardViewSwitcher.unselect();
        }
    }

    private void setupFadingEffect(ListRowView rowView) {
        HorizontalGridView gridView = rowView.getGridView();
        if (this.mBrowseRowsFadingEdgeLength < 0) {
            TypedArray ta = gridView.getContext().obtainStyledAttributes(C0364R.styleable.LeanbackTheme);
            this.mBrowseRowsFadingEdgeLength = (int) ta.getDimension(C0364R.styleable.LeanbackTheme_browseRowsFadingEdgeLength, 0.0f);
            ta.recycle();
        }
        gridView.setFadingLeftEdgeLength(this.mBrowseRowsFadingEdgeLength);
    }

    /* access modifiers changed from: protected */
    public void onRowViewExpanded(RowPresenter.ViewHolder holder, boolean expanded) {
        super.onRowViewExpanded(holder, expanded);
        ViewHolder vh = (ViewHolder) holder;
        if (getRowHeight() != getExpandedRowHeight()) {
            vh.getGridView().setRowHeight(expanded ? getExpandedRowHeight() : getRowHeight());
        }
        setVerticalPadding(vh);
        updateFooterViewSwitcher(vh);
    }

    /* access modifiers changed from: protected */
    public void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        ViewHolder vh = (ViewHolder) holder;
        ListRow rowItem = (ListRow) item;
        vh.mItemBridgeAdapter.setAdapter(rowItem.getAdapter());
        vh.mGridView.setAdapter(vh.mItemBridgeAdapter);
        vh.mGridView.setContentDescription(rowItem.getContentDescription());
    }

    /* access modifiers changed from: protected */
    public void onUnbindRowViewHolder(RowPresenter.ViewHolder holder) {
        ViewHolder vh = (ViewHolder) holder;
        vh.mGridView.setAdapter(null);
        vh.mItemBridgeAdapter.clear();
        super.onUnbindRowViewHolder(holder);
    }

    public final boolean isUsingDefaultSelectEffect() {
        return false;
    }

    public boolean isUsingDefaultListSelectEffect() {
        return true;
    }

    public boolean isUsingDefaultShadow() {
        return ShadowOverlayHelper.supportsShadow();
    }

    public boolean isUsingZOrder(Context context) {
        return !Settings.getInstance(context).preferStaticShadows();
    }

    public boolean isUsingOutlineClipping(Context context) {
        return !Settings.getInstance(context).isOutlineClippingDisabled();
    }

    public final boolean getShadowEnabled() {
        return this.mShadowEnabled;
    }

    public final void setShadowEnabled(boolean enabled) {
        this.mShadowEnabled = enabled;
    }

    public final void enableChildRoundedCorners(boolean enable) {
        this.mRoundedCornersEnabled = enable;
    }

    public final boolean areChildRoundedCornersEnabled() {
        return this.mRoundedCornersEnabled;
    }

    /* access modifiers changed from: package-private */
    public final boolean needsDefaultShadow() {
        return isUsingDefaultShadow() && getShadowEnabled();
    }

    public final boolean isKeepChildForeground() {
        return this.mKeepChildForeground;
    }

    public final void setKeepChildForeground(boolean keep) {
        this.mKeepChildForeground = keep;
    }

    /* access modifiers changed from: protected */
    public ShadowOverlayHelper.Options createShadowOverlayOptions() {
        return ShadowOverlayHelper.Options.DEFAULT;
    }

    /* access modifiers changed from: protected */
    public void onSelectLevelChanged(RowPresenter.ViewHolder holder) {
        super.onSelectLevelChanged(holder);
        ViewHolder vh = (ViewHolder) holder;
        int count = vh.mGridView.getChildCount();
        for (int i = 0; i < count; i++) {
            applySelectLevelToChild(vh, vh.mGridView.getChildAt(i));
        }
    }

    /* access modifiers changed from: protected */
    public void applySelectLevelToChild(ViewHolder rowViewHolder, View childView) {
        ShadowOverlayHelper shadowOverlayHelper = this.mShadowOverlayHelper;
        if (shadowOverlayHelper != null && shadowOverlayHelper.needsOverlay()) {
            this.mShadowOverlayHelper.setOverlayColor(childView, rowViewHolder.mColorDimmer.getPaint().getColor());
        }
    }

    public void freeze(RowPresenter.ViewHolder holder, boolean freeze) {
        ViewHolder vh = (ViewHolder) holder;
        vh.mGridView.setScrollEnabled(!freeze);
        vh.mGridView.setAnimateChildLayout(!freeze);
    }

    public void setEntranceTransitionState(RowPresenter.ViewHolder holder, boolean afterEntrance) {
        super.setEntranceTransitionState(holder, afterEntrance);
        ((ViewHolder) holder).mGridView.setChildrenVisibility(afterEntrance ? 0 : 4);
    }

    public static class ViewHolder extends RowPresenter.ViewHolder {
        final HorizontalGridView mGridView;
        final HorizontalHoverCardSwitcher mHoverCardViewSwitcher = new HorizontalHoverCardSwitcher();
        final ListRowPresenter mListRowPresenter;
        final int mPaddingBottom;
        final int mPaddingLeft;
        final int mPaddingRight;
        final int mPaddingTop;
        ItemBridgeAdapter mItemBridgeAdapter;

        public ViewHolder(View rootView, HorizontalGridView gridView, ListRowPresenter p) {
            super(rootView);
            this.mGridView = gridView;
            this.mListRowPresenter = p;
            this.mPaddingTop = this.mGridView.getPaddingTop();
            this.mPaddingBottom = this.mGridView.getPaddingBottom();
            this.mPaddingLeft = this.mGridView.getPaddingLeft();
            this.mPaddingRight = this.mGridView.getPaddingRight();
        }

        public final ListRowPresenter getListRowPresenter() {
            return this.mListRowPresenter;
        }

        public final HorizontalGridView getGridView() {
            return this.mGridView;
        }

        public final ItemBridgeAdapter getBridgeAdapter() {
            return this.mItemBridgeAdapter;
        }

        public int getSelectedPosition() {
            return this.mGridView.getSelectedPosition();
        }

        public Presenter.ViewHolder getItemViewHolder(int position) {
            ItemBridgeAdapter.ViewHolder ibvh = (ItemBridgeAdapter.ViewHolder) this.mGridView.findViewHolderForAdapterPosition(position);
            if (ibvh == null) {
                return null;
            }
            return ibvh.getViewHolder();
        }

        public Presenter.ViewHolder getSelectedItemViewHolder() {
            return getItemViewHolder(getSelectedPosition());
        }

        public Object getSelectedItem() {
            ItemBridgeAdapter.ViewHolder ibvh = (ItemBridgeAdapter.ViewHolder) this.mGridView.findViewHolderForAdapterPosition(getSelectedPosition());
            if (ibvh == null) {
                return null;
            }
            return ibvh.getItem();
        }
    }

    public static class SelectItemViewHolderTask extends Presenter.ViewHolderTask {
        Presenter.ViewHolderTask mItemTask;
        private int mItemPosition;
        private boolean mSmoothScroll = true;

        public SelectItemViewHolderTask(int itemPosition) {
            setItemPosition(itemPosition);
        }

        public int getItemPosition() {
            return this.mItemPosition;
        }

        public void setItemPosition(int itemPosition) {
            this.mItemPosition = itemPosition;
        }

        public boolean isSmoothScroll() {
            return this.mSmoothScroll;
        }

        public void setSmoothScroll(boolean smoothScroll) {
            this.mSmoothScroll = smoothScroll;
        }

        public Presenter.ViewHolderTask getItemTask() {
            return this.mItemTask;
        }

        public void setItemTask(Presenter.ViewHolderTask itemTask) {
            this.mItemTask = itemTask;
        }

        public void run(Presenter.ViewHolder holder) {
            if (holder instanceof ViewHolder) {
                HorizontalGridView gridView = ((ViewHolder) holder).getGridView();
                ViewHolderTask task = null;
                if (this.mItemTask != null) {
                    task = new ViewHolderTask() {
                        final Presenter.ViewHolderTask itemTask = SelectItemViewHolderTask.this.mItemTask;

                        public void run(RecyclerView.ViewHolder rvh) {
                            this.itemTask.run(((ItemBridgeAdapter.ViewHolder) rvh).getViewHolder());
                        }
                    };
                }
                if (isSmoothScroll()) {
                    gridView.setSelectedPositionSmooth(this.mItemPosition, task);
                } else {
                    gridView.setSelectedPosition(this.mItemPosition, task);
                }
            }
        }
    }

    class ListRowPresenterItemBridgeAdapter extends ItemBridgeAdapter {
        ViewHolder mRowViewHolder;

        ListRowPresenterItemBridgeAdapter(ViewHolder rowViewHolder) {
            this.mRowViewHolder = rowViewHolder;
        }

        /* access modifiers changed from: protected */
        public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                TransitionHelper.setTransitionGroup((ViewGroup) viewHolder.itemView, true);
            }
            if (ListRowPresenter.this.mShadowOverlayHelper != null) {
                ListRowPresenter.this.mShadowOverlayHelper.onViewCreated(viewHolder.itemView);
            }
        }

        public void onBind(final ItemBridgeAdapter.ViewHolder viewHolder) {
            if (this.mRowViewHolder.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ItemBridgeAdapter.ViewHolder ibh = (ItemBridgeAdapter.ViewHolder) ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.mGridView.getChildViewHolder(viewHolder.itemView);
                        if (ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.getOnItemViewClickedListener() != null) {
                            ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.getOnItemViewClickedListener().onItemClicked(viewHolder.mHolder, ibh.mItem, ListRowPresenterItemBridgeAdapter.this.mRowViewHolder, (ListRow) ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.mRow);
                        }
                    }
                });
            }
        }

        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (this.mRowViewHolder.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(null);
            }
        }

        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            ListRowPresenter.this.applySelectLevelToChild(this.mRowViewHolder, viewHolder.itemView);
            this.mRowViewHolder.syncActivatedStatus(viewHolder.itemView);
        }

        public void onAddPresenter(Presenter presenter, int type) {
            this.mRowViewHolder.getGridView().getRecycledViewPool().setMaxRecycledViews(type, ListRowPresenter.this.getRecycledPoolSize(presenter));
        }
    }
}
