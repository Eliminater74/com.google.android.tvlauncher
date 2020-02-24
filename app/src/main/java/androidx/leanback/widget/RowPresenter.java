package androidx.leanback.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.graphics.ColorOverlayDimmer;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowHeaderPresenter;

public abstract class RowPresenter extends Presenter {
    public static final int SYNC_ACTIVATED_CUSTOM = 0;
    public static final int SYNC_ACTIVATED_TO_EXPANDED = 1;
    public static final int SYNC_ACTIVATED_TO_EXPANDED_AND_SELECTED = 3;
    public static final int SYNC_ACTIVATED_TO_SELECTED = 2;
    private RowHeaderPresenter mHeaderPresenter = new RowHeaderPresenter();
    boolean mSelectEffectEnabled = true;
    int mSyncActivatePolicy = 1;

    /* access modifiers changed from: protected */
    public abstract ViewHolder createRowViewHolder(ViewGroup viewGroup);

    static class ContainerViewHolder extends Presenter.ViewHolder {
        final ViewHolder mRowViewHolder;

        public ContainerViewHolder(RowContainerView containerView, ViewHolder rowViewHolder) {
            super(containerView);
            containerView.addRowView(rowViewHolder.view);
            if (rowViewHolder.mHeaderViewHolder != null) {
                containerView.addHeaderView(rowViewHolder.mHeaderViewHolder.view);
            }
            this.mRowViewHolder = rowViewHolder;
            this.mRowViewHolder.mContainerViewHolder = this;
        }
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        private static final int ACTIVATED = 1;
        private static final int ACTIVATED_NOT_ASSIGNED = 0;
        private static final int NOT_ACTIVATED = 2;
        int mActivated = 0;
        protected final ColorOverlayDimmer mColorDimmer;
        ContainerViewHolder mContainerViewHolder;
        boolean mExpanded;
        RowHeaderPresenter.ViewHolder mHeaderViewHolder;
        boolean mInitialzed;
        private BaseOnItemViewClickedListener mOnItemViewClickedListener;
        BaseOnItemViewSelectedListener mOnItemViewSelectedListener;
        private View.OnKeyListener mOnKeyListener;
        Row mRow;
        Object mRowObject;
        float mSelectLevel = 0.0f;
        boolean mSelected;

        public ViewHolder(View view) {
            super(view);
            this.mColorDimmer = ColorOverlayDimmer.createDefault(view.getContext());
        }

        public final Row getRow() {
            return this.mRow;
        }

        public final Object getRowObject() {
            return this.mRowObject;
        }

        public final boolean isExpanded() {
            return this.mExpanded;
        }

        public final boolean isSelected() {
            return this.mSelected;
        }

        public final float getSelectLevel() {
            return this.mSelectLevel;
        }

        public final RowHeaderPresenter.ViewHolder getHeaderViewHolder() {
            return this.mHeaderViewHolder;
        }

        public final void setActivated(boolean activated) {
            this.mActivated = activated ? 1 : 2;
        }

        public final void syncActivatedStatus(View view) {
            int i = this.mActivated;
            if (i == 1) {
                view.setActivated(true);
            } else if (i == 2) {
                view.setActivated(false);
            }
        }

        public void setOnKeyListener(View.OnKeyListener keyListener) {
            this.mOnKeyListener = keyListener;
        }

        public View.OnKeyListener getOnKeyListener() {
            return this.mOnKeyListener;
        }

        public final void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener listener) {
            this.mOnItemViewSelectedListener = listener;
        }

        public final BaseOnItemViewSelectedListener getOnItemViewSelectedListener() {
            return this.mOnItemViewSelectedListener;
        }

        public final void setOnItemViewClickedListener(BaseOnItemViewClickedListener listener) {
            this.mOnItemViewClickedListener = listener;
        }

        public final BaseOnItemViewClickedListener getOnItemViewClickedListener() {
            return this.mOnItemViewClickedListener;
        }

        public Presenter.ViewHolder getSelectedItemViewHolder() {
            return null;
        }

        public Object getSelectedItem() {
            return null;
        }
    }

    public RowPresenter() {
        this.mHeaderPresenter.setNullItemVisibilityGone(true);
    }

    public final Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Presenter.ViewHolder result;
        ViewHolder vh = createRowViewHolder(parent);
        vh.mInitialzed = false;
        if (needsRowContainerView()) {
            RowContainerView containerView = new RowContainerView(parent.getContext());
            RowHeaderPresenter rowHeaderPresenter = this.mHeaderPresenter;
            if (rowHeaderPresenter != null) {
                vh.mHeaderViewHolder = (RowHeaderPresenter.ViewHolder) rowHeaderPresenter.onCreateViewHolder((ViewGroup) vh.view);
            }
            result = new ContainerViewHolder(containerView, vh);
        } else {
            result = vh;
        }
        initializeRowViewHolder(vh);
        if (vh.mInitialzed) {
            return result;
        }
        throw new RuntimeException("super.initializeRowViewHolder() must be called");
    }

    /* access modifiers changed from: protected */
    public boolean isClippingChildren() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void initializeRowViewHolder(ViewHolder vh) {
        vh.mInitialzed = true;
        if (!isClippingChildren()) {
            if (vh.view instanceof ViewGroup) {
                ((ViewGroup) vh.view).setClipChildren(false);
            }
            if (vh.mContainerViewHolder != null) {
                ((ViewGroup) vh.mContainerViewHolder.view).setClipChildren(false);
            }
        }
    }

    public final void setHeaderPresenter(RowHeaderPresenter headerPresenter) {
        this.mHeaderPresenter = headerPresenter;
    }

    public final RowHeaderPresenter getHeaderPresenter() {
        return this.mHeaderPresenter;
    }

    public final ViewHolder getRowViewHolder(Presenter.ViewHolder holder) {
        if (holder instanceof ContainerViewHolder) {
            return ((ContainerViewHolder) holder).mRowViewHolder;
        }
        return (ViewHolder) holder;
    }

    public final void setRowViewExpanded(Presenter.ViewHolder holder, boolean expanded) {
        ViewHolder rowViewHolder = getRowViewHolder(holder);
        rowViewHolder.mExpanded = expanded;
        onRowViewExpanded(rowViewHolder, expanded);
    }

    public final void setRowViewSelected(Presenter.ViewHolder holder, boolean selected) {
        ViewHolder rowViewHolder = getRowViewHolder(holder);
        rowViewHolder.mSelected = selected;
        onRowViewSelected(rowViewHolder, selected);
    }

    /* access modifiers changed from: protected */
    public void onRowViewExpanded(ViewHolder vh, boolean expanded) {
        updateHeaderViewVisibility(vh);
        updateActivateStatus(vh, vh.view);
    }

    private void updateActivateStatus(ViewHolder vh, View view) {
        int i = this.mSyncActivatePolicy;
        boolean z = true;
        if (i == 1) {
            vh.setActivated(vh.isExpanded());
        } else if (i == 2) {
            vh.setActivated(vh.isSelected());
        } else if (i == 3) {
            if (!vh.isExpanded() || !vh.isSelected()) {
                z = false;
            }
            vh.setActivated(z);
        }
        vh.syncActivatedStatus(view);
    }

    public final void setSyncActivatePolicy(int syncActivatePolicy) {
        this.mSyncActivatePolicy = syncActivatePolicy;
    }

    public final int getSyncActivatePolicy() {
        return this.mSyncActivatePolicy;
    }

    /* access modifiers changed from: protected */
    public void dispatchItemSelectedListener(ViewHolder vh, boolean selected) {
        if (selected && vh.mOnItemViewSelectedListener != null) {
            vh.mOnItemViewSelectedListener.onItemSelected(null, null, vh, vh.getRowObject());
        }
    }

    /* access modifiers changed from: protected */
    public void onRowViewSelected(ViewHolder vh, boolean selected) {
        dispatchItemSelectedListener(vh, selected);
        updateHeaderViewVisibility(vh);
        updateActivateStatus(vh, vh.view);
    }

    private void updateHeaderViewVisibility(ViewHolder vh) {
        if (this.mHeaderPresenter != null && vh.mHeaderViewHolder != null) {
            ((RowContainerView) vh.mContainerViewHolder.view).showHeader(vh.isExpanded());
        }
    }

    public final void setSelectLevel(Presenter.ViewHolder vh, float level) {
        ViewHolder rowViewHolder = getRowViewHolder(vh);
        rowViewHolder.mSelectLevel = level;
        onSelectLevelChanged(rowViewHolder);
    }

    public final float getSelectLevel(Presenter.ViewHolder vh) {
        return getRowViewHolder(vh).mSelectLevel;
    }

    /* access modifiers changed from: protected */
    public void onSelectLevelChanged(ViewHolder vh) {
        if (getSelectEffectEnabled()) {
            vh.mColorDimmer.setActiveLevel(vh.mSelectLevel);
            if (vh.mHeaderViewHolder != null) {
                this.mHeaderPresenter.setSelectLevel(vh.mHeaderViewHolder, vh.mSelectLevel);
            }
            if (isUsingDefaultSelectEffect()) {
                ((RowContainerView) vh.mContainerViewHolder.view).setForegroundColor(vh.mColorDimmer.getPaint().getColor());
            }
        }
    }

    public final void setSelectEffectEnabled(boolean applyDimOnSelect) {
        this.mSelectEffectEnabled = applyDimOnSelect;
    }

    public final boolean getSelectEffectEnabled() {
        return this.mSelectEffectEnabled;
    }

    public boolean isUsingDefaultSelectEffect() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean needsDefaultSelectEffect() {
        return isUsingDefaultSelectEffect() && getSelectEffectEnabled();
    }

    /* access modifiers changed from: package-private */
    public final boolean needsRowContainerView() {
        return this.mHeaderPresenter != null || needsDefaultSelectEffect();
    }

    public final void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        onBindRowViewHolder(getRowViewHolder(viewHolder), item);
    }

    /* access modifiers changed from: protected */
    public void onBindRowViewHolder(ViewHolder vh, Object item) {
        vh.mRowObject = item;
        vh.mRow = item instanceof Row ? (Row) item : null;
        if (vh.mHeaderViewHolder != null && vh.getRow() != null) {
            this.mHeaderPresenter.onBindViewHolder(vh.mHeaderViewHolder, item);
        }
    }

    public final void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        onUnbindRowViewHolder(getRowViewHolder(viewHolder));
    }

    /* access modifiers changed from: protected */
    public void onUnbindRowViewHolder(ViewHolder vh) {
        if (vh.mHeaderViewHolder != null) {
            this.mHeaderPresenter.onUnbindViewHolder(vh.mHeaderViewHolder);
        }
        vh.mRow = null;
        vh.mRowObject = null;
    }

    public final void onViewAttachedToWindow(Presenter.ViewHolder holder) {
        onRowViewAttachedToWindow(getRowViewHolder(holder));
    }

    /* access modifiers changed from: protected */
    public void onRowViewAttachedToWindow(ViewHolder vh) {
        if (vh.mHeaderViewHolder != null) {
            this.mHeaderPresenter.onViewAttachedToWindow(vh.mHeaderViewHolder);
        }
    }

    public final void onViewDetachedFromWindow(Presenter.ViewHolder holder) {
        onRowViewDetachedFromWindow(getRowViewHolder(holder));
    }

    /* access modifiers changed from: protected */
    public void onRowViewDetachedFromWindow(ViewHolder vh) {
        if (vh.mHeaderViewHolder != null) {
            this.mHeaderPresenter.onViewDetachedFromWindow(vh.mHeaderViewHolder);
        }
        cancelAnimationsRecursive(vh.view);
    }

    public void freeze(ViewHolder holder, boolean freeze) {
    }

    public void setEntranceTransitionState(ViewHolder holder, boolean afterEntrance) {
        if (holder.mHeaderViewHolder != null && holder.mHeaderViewHolder.view.getVisibility() != 8) {
            holder.mHeaderViewHolder.view.setVisibility(afterEntrance ? 0 : 4);
        }
    }
}
