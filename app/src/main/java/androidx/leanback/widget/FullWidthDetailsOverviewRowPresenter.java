package androidx.leanback.widget;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.p004v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.leanback.C0364R;

public class FullWidthDetailsOverviewRowPresenter extends RowPresenter {
    public static final int ALIGN_MODE_MIDDLE = 1;
    public static final int ALIGN_MODE_START = 0;
    public static final int STATE_FULL = 1;
    public static final int STATE_HALF = 0;
    public static final int STATE_SMALL = 2;
    static final boolean DEBUG = false;
    static final String TAG = "FullWidthDetailsRP";
    static final Handler sHandler = new Handler();
    private static Rect sTmpRect = new Rect();
    final DetailsOverviewLogoPresenter mDetailsOverviewLogoPresenter;
    final Presenter mDetailsPresenter;
    protected int mInitialState;
    OnActionClickedListener mActionClickedListener;
    private int mActionsBackgroundColor;
    private boolean mActionsBackgroundColorSet;
    private int mAlignmentMode;
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private Listener mListener;
    private boolean mParticipatingEntranceTransition;

    public FullWidthDetailsOverviewRowPresenter(Presenter detailsPresenter) {
        this(detailsPresenter, new DetailsOverviewLogoPresenter());
    }

    public FullWidthDetailsOverviewRowPresenter(Presenter detailsPresenter, DetailsOverviewLogoPresenter logoPresenter) {
        this.mInitialState = 0;
        this.mBackgroundColor = 0;
        this.mActionsBackgroundColor = 0;
        setHeaderPresenter(null);
        setSelectEffectEnabled(false);
        this.mDetailsPresenter = detailsPresenter;
        this.mDetailsOverviewLogoPresenter = logoPresenter;
    }

    private static int getNonNegativeWidth(Drawable drawable) {
        int width = drawable == null ? 0 : drawable.getIntrinsicWidth();
        if (width > 0) {
            return width;
        }
        return 0;
    }

    private static int getNonNegativeHeight(Drawable drawable) {
        int height = drawable == null ? 0 : drawable.getIntrinsicHeight();
        if (height > 0) {
            return height;
        }
        return 0;
    }

    public OnActionClickedListener getOnActionClickedListener() {
        return this.mActionClickedListener;
    }

    public void setOnActionClickedListener(OnActionClickedListener listener) {
        this.mActionClickedListener = listener;
    }

    public final int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public final void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
        this.mBackgroundColorSet = true;
    }

    public final int getActionsBackgroundColor() {
        return this.mActionsBackgroundColor;
    }

    public final void setActionsBackgroundColor(int color) {
        this.mActionsBackgroundColor = color;
        this.mActionsBackgroundColorSet = true;
    }

    public final boolean isParticipatingEntranceTransition() {
        return this.mParticipatingEntranceTransition;
    }

    public final void setParticipatingEntranceTransition(boolean participating) {
        this.mParticipatingEntranceTransition = participating;
    }

    public final int getInitialState() {
        return this.mInitialState;
    }

    public final void setInitialState(int state) {
        this.mInitialState = state;
    }

    public final int getAlignmentMode() {
        return this.mAlignmentMode;
    }

    public final void setAlignmentMode(int alignmentMode) {
        this.mAlignmentMode = alignmentMode;
    }

    /* access modifiers changed from: protected */
    public boolean isClippingChildren() {
        return true;
    }

    public final void setListener(Listener listener) {
        this.mListener = listener;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return C0364R.layout.lb_fullwidth_details_overview;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        final ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutResourceId(), parent, false), this.mDetailsPresenter, this.mDetailsOverviewLogoPresenter);
        this.mDetailsOverviewLogoPresenter.setContext(vh.mDetailsLogoViewHolder, vh, this);
        setState(vh, this.mInitialState);
        vh.mActionBridgeAdapter = new ActionsItemBridgeAdapter(vh);
        View overview = vh.mOverviewFrame;
        if (this.mBackgroundColorSet) {
            overview.setBackgroundColor(this.mBackgroundColor);
        }
        if (this.mActionsBackgroundColorSet) {
            overview.findViewById(C0364R.C0366id.details_overview_actions_background).setBackgroundColor(this.mActionsBackgroundColor);
        }
        RoundedRectHelper.setClipToRoundedOutline(overview, true);
        if (!getSelectEffectEnabled()) {
            vh.mOverviewFrame.setForeground(null);
        }
        vh.mActionsRow.setOnUnhandledKeyListener(new BaseGridView.OnUnhandledKeyListener() {
            public boolean onUnhandledKey(KeyEvent event) {
                if (vh.getOnKeyListener() == null || !vh.getOnKeyListener().onKey(vh.view, event.getKeyCode(), event)) {
                    return false;
                }
                return true;
            }
        });
        return vh;
    }

    /* access modifiers changed from: protected */
    public void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        DetailsOverviewRow row = (DetailsOverviewRow) item;
        ViewHolder vh = (ViewHolder) holder;
        this.mDetailsOverviewLogoPresenter.onBindViewHolder(vh.mDetailsLogoViewHolder, row);
        this.mDetailsPresenter.onBindViewHolder(vh.mDetailsDescriptionViewHolder, row.getItem());
        vh.onBind();
    }

    /* access modifiers changed from: protected */
    public void onUnbindRowViewHolder(RowPresenter.ViewHolder holder) {
        ViewHolder vh = (ViewHolder) holder;
        vh.onUnbind();
        this.mDetailsPresenter.onUnbindViewHolder(vh.mDetailsDescriptionViewHolder);
        this.mDetailsOverviewLogoPresenter.onUnbindViewHolder(vh.mDetailsLogoViewHolder);
        super.onUnbindRowViewHolder(holder);
    }

    public final boolean isUsingDefaultSelectEffect() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onSelectLevelChanged(RowPresenter.ViewHolder holder) {
        super.onSelectLevelChanged(holder);
        if (getSelectEffectEnabled()) {
            ViewHolder vh = (ViewHolder) holder;
            ((ColorDrawable) vh.mOverviewFrame.getForeground().mutate()).setColor(vh.mColorDimmer.getPaint().getColor());
        }
    }

    /* access modifiers changed from: protected */
    public void onRowViewAttachedToWindow(RowPresenter.ViewHolder vh) {
        super.onRowViewAttachedToWindow(vh);
        ViewHolder viewHolder = (ViewHolder) vh;
        this.mDetailsPresenter.onViewAttachedToWindow(viewHolder.mDetailsDescriptionViewHolder);
        this.mDetailsOverviewLogoPresenter.onViewAttachedToWindow(viewHolder.mDetailsLogoViewHolder);
    }

    /* access modifiers changed from: protected */
    public void onRowViewDetachedFromWindow(RowPresenter.ViewHolder vh) {
        super.onRowViewDetachedFromWindow(vh);
        ViewHolder viewHolder = (ViewHolder) vh;
        this.mDetailsPresenter.onViewDetachedFromWindow(viewHolder.mDetailsDescriptionViewHolder);
        this.mDetailsOverviewLogoPresenter.onViewDetachedFromWindow(viewHolder.mDetailsLogoViewHolder);
    }

    public final void notifyOnBindLogo(ViewHolder viewHolder) {
        onLayoutOverviewFrame(viewHolder, viewHolder.getState(), true);
        onLayoutLogo(viewHolder, viewHolder.getState(), true);
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onBindLogo(viewHolder);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayoutLogo(ViewHolder viewHolder, int oldState, boolean logoChanged) {
        View v = viewHolder.getLogoViewHolder().view;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        if (this.mAlignmentMode != 1) {
            lp.setMarginStart(v.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_logo_margin_start));
        } else {
            lp.setMarginStart(v.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_left) - lp.width);
        }
        int state = viewHolder.getState();
        if (state == 0) {
            lp.topMargin = v.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_blank_height) + v.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_actions_height) + v.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_description_margin_top);
        } else if (state != 2) {
            lp.topMargin = v.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_blank_height) - (lp.height / 2);
        } else {
            lp.topMargin = 0;
        }
        v.setLayoutParams(lp);
    }

    /* access modifiers changed from: protected */
    public void onLayoutOverviewFrame(ViewHolder viewHolder, int oldState, boolean logoChanged) {
        int descriptionMarginStart;
        int frameMarginStart;
        int i;
        int i2 = 0;
        boolean wasBanner = oldState == 2;
        boolean isBanner = viewHolder.getState() == 2;
        if (wasBanner != isBanner || logoChanged) {
            Resources res = viewHolder.view.getResources();
            int logoWidth = 0;
            if (this.mDetailsOverviewLogoPresenter.isBoundToImage(viewHolder.getLogoViewHolder(), (DetailsOverviewRow) viewHolder.getRow())) {
                logoWidth = viewHolder.getLogoViewHolder().view.getLayoutParams().width;
            }
            if (this.mAlignmentMode != 1) {
                if (isBanner) {
                    frameMarginStart = res.getDimensionPixelSize(C0364R.dimen.lb_details_v2_logo_margin_start);
                    descriptionMarginStart = logoWidth;
                } else {
                    frameMarginStart = 0;
                    descriptionMarginStart = logoWidth + res.getDimensionPixelSize(C0364R.dimen.lb_details_v2_logo_margin_start);
                }
            } else if (isBanner) {
                frameMarginStart = res.getDimensionPixelSize(C0364R.dimen.lb_details_v2_left) - logoWidth;
                descriptionMarginStart = logoWidth;
            } else {
                frameMarginStart = 0;
                descriptionMarginStart = res.getDimensionPixelSize(C0364R.dimen.lb_details_v2_left);
            }
            ViewGroup.MarginLayoutParams lpFrame = (ViewGroup.MarginLayoutParams) viewHolder.getOverviewView().getLayoutParams();
            if (isBanner) {
                i = 0;
            } else {
                i = res.getDimensionPixelSize(C0364R.dimen.lb_details_v2_blank_height);
            }
            lpFrame.topMargin = i;
            lpFrame.rightMargin = frameMarginStart;
            lpFrame.leftMargin = frameMarginStart;
            viewHolder.getOverviewView().setLayoutParams(lpFrame);
            View description = viewHolder.getDetailsDescriptionFrame();
            ViewGroup.MarginLayoutParams lpDesc = (ViewGroup.MarginLayoutParams) description.getLayoutParams();
            lpDesc.setMarginStart(descriptionMarginStart);
            description.setLayoutParams(lpDesc);
            View action = viewHolder.getActionsRow();
            ViewGroup.MarginLayoutParams lpActions = (ViewGroup.MarginLayoutParams) action.getLayoutParams();
            lpActions.setMarginStart(descriptionMarginStart);
            if (!isBanner) {
                i2 = res.getDimensionPixelSize(C0364R.dimen.lb_details_v2_actions_height);
            }
            lpActions.height = i2;
            action.setLayoutParams(lpActions);
        }
    }

    public final void setState(ViewHolder viewHolder, int state) {
        if (viewHolder.getState() != state) {
            int oldState = viewHolder.getState();
            viewHolder.mState = state;
            onStateChanged(viewHolder, oldState);
        }
    }

    /* access modifiers changed from: protected */
    public void onStateChanged(ViewHolder viewHolder, int oldState) {
        onLayoutOverviewFrame(viewHolder, oldState, false);
        onLayoutLogo(viewHolder, oldState, false);
    }

    public void setEntranceTransitionState(RowPresenter.ViewHolder holder, boolean afterEntrance) {
        super.setEntranceTransitionState(holder, afterEntrance);
        if (this.mParticipatingEntranceTransition) {
            holder.view.setVisibility(afterEntrance ? 0 : 4);
        }
    }

    public static abstract class Listener {
        public void onBindLogo(ViewHolder vh) {
        }
    }

    class ActionsItemBridgeAdapter extends ItemBridgeAdapter {
        ViewHolder mViewHolder;

        ActionsItemBridgeAdapter(ViewHolder viewHolder) {
            this.mViewHolder = viewHolder;
        }

        public void onBind(final ItemBridgeAdapter.ViewHolder ibvh) {
            if (this.mViewHolder.getOnItemViewClickedListener() != null || FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener != null) {
                ibvh.getPresenter().setOnClickListener(ibvh.getViewHolder(), new View.OnClickListener() {
                    public void onClick(View v) {
                        if (ActionsItemBridgeAdapter.this.mViewHolder.getOnItemViewClickedListener() != null) {
                            ActionsItemBridgeAdapter.this.mViewHolder.getOnItemViewClickedListener().onItemClicked(ibvh.getViewHolder(), ibvh.getItem(), ActionsItemBridgeAdapter.this.mViewHolder, ActionsItemBridgeAdapter.this.mViewHolder.getRow());
                        }
                        if (FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener != null) {
                            FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener.onActionClicked((Action) ibvh.getItem());
                        }
                    }
                });
            }
        }

        public void onUnbind(ItemBridgeAdapter.ViewHolder ibvh) {
            if (this.mViewHolder.getOnItemViewClickedListener() != null || FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener != null) {
                ibvh.getPresenter().setOnClickListener(ibvh.getViewHolder(), null);
            }
        }

        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            viewHolder.itemView.removeOnLayoutChangeListener(this.mViewHolder.mLayoutChangeListener);
            viewHolder.itemView.addOnLayoutChangeListener(this.mViewHolder.mLayoutChangeListener);
        }

        public void onDetachedFromWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            viewHolder.itemView.removeOnLayoutChangeListener(this.mViewHolder.mLayoutChangeListener);
            this.mViewHolder.checkFirstAndLastPosition(false);
        }
    }

    public class ViewHolder extends RowPresenter.ViewHolder {
        protected final DetailsOverviewRow.Listener mRowListener = createRowListener();
        final HorizontalGridView mActionsRow;
        final OnChildSelectedListener mChildSelectedListener = new OnChildSelectedListener() {
            public void onChildSelected(ViewGroup parent, View view, int position, long id) {
                ViewHolder.this.dispatchItemSelection(view);
            }
        };
        final ViewGroup mDetailsDescriptionFrame;
        final Presenter.ViewHolder mDetailsDescriptionViewHolder;
        final DetailsOverviewLogoPresenter.ViewHolder mDetailsLogoViewHolder;
        final FrameLayout mOverviewFrame;
        final ViewGroup mOverviewRoot;
        ItemBridgeAdapter mActionBridgeAdapter;
        int mNumItems;
        final View.OnLayoutChangeListener mLayoutChangeListener = new View.OnLayoutChangeListener() {
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ViewHolder.this.checkFirstAndLastPosition(false);
            }
        };
        final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ViewHolder.this.checkFirstAndLastPosition(true);
            }
        };
        int mState = 0;
        final Runnable mUpdateDrawableCallback = new Runnable() {
            public void run() {
                Row row = ViewHolder.this.getRow();
                if (row != null) {
                    FullWidthDetailsOverviewRowPresenter.this.mDetailsOverviewLogoPresenter.onBindViewHolder(ViewHolder.this.mDetailsLogoViewHolder, row);
                }
            }
        };

        public ViewHolder(View rootView, Presenter detailsPresenter, DetailsOverviewLogoPresenter logoPresenter) {
            super(rootView);
            this.mOverviewRoot = (ViewGroup) rootView.findViewById(C0364R.C0366id.details_root);
            this.mOverviewFrame = (FrameLayout) rootView.findViewById(C0364R.C0366id.details_frame);
            this.mDetailsDescriptionFrame = (ViewGroup) rootView.findViewById(C0364R.C0366id.details_overview_description);
            this.mActionsRow = (HorizontalGridView) this.mOverviewFrame.findViewById(C0364R.C0366id.details_overview_actions);
            this.mActionsRow.setHasOverlappingRendering(false);
            this.mActionsRow.setOnScrollListener(this.mScrollListener);
            this.mActionsRow.setAdapter(this.mActionBridgeAdapter);
            this.mActionsRow.setOnChildSelectedListener(this.mChildSelectedListener);
            int fadeLength = rootView.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_overview_actions_fade_size);
            this.mActionsRow.setFadingRightEdgeLength(fadeLength);
            this.mActionsRow.setFadingLeftEdgeLength(fadeLength);
            this.mDetailsDescriptionViewHolder = detailsPresenter.onCreateViewHolder(this.mDetailsDescriptionFrame);
            this.mDetailsDescriptionFrame.addView(this.mDetailsDescriptionViewHolder.view);
            this.mDetailsLogoViewHolder = (DetailsOverviewLogoPresenter.ViewHolder) logoPresenter.onCreateViewHolder(this.mOverviewRoot);
            this.mOverviewRoot.addView(this.mDetailsLogoViewHolder.view);
        }

        /* access modifiers changed from: protected */
        public DetailsOverviewRow.Listener createRowListener() {
            return new DetailsOverviewRowListener();
        }

        /* access modifiers changed from: package-private */
        public void bindActions(ObjectAdapter adapter) {
            this.mActionBridgeAdapter.setAdapter(adapter);
            this.mActionsRow.setAdapter(this.mActionBridgeAdapter);
            this.mNumItems = this.mActionBridgeAdapter.getItemCount();
        }

        /* access modifiers changed from: package-private */
        public void onBind() {
            DetailsOverviewRow row = (DetailsOverviewRow) getRow();
            bindActions(row.getActionsAdapter());
            row.addListener(this.mRowListener);
        }

        /* access modifiers changed from: package-private */
        public void onUnbind() {
            ((DetailsOverviewRow) getRow()).removeListener(this.mRowListener);
            FullWidthDetailsOverviewRowPresenter.sHandler.removeCallbacks(this.mUpdateDrawableCallback);
        }

        /* access modifiers changed from: package-private */
        public void dispatchItemSelection(View view) {
            RecyclerView.ViewHolder viewHolder;
            if (isSelected()) {
                if (view != null) {
                    viewHolder = this.mActionsRow.getChildViewHolder(view);
                } else {
                    HorizontalGridView horizontalGridView = this.mActionsRow;
                    viewHolder = horizontalGridView.findViewHolderForPosition(horizontalGridView.getSelectedPosition());
                }
                ItemBridgeAdapter.ViewHolder ibvh = (ItemBridgeAdapter.ViewHolder) viewHolder;
                if (ibvh == null) {
                    if (getOnItemViewSelectedListener() != null) {
                        getOnItemViewSelectedListener().onItemSelected(null, null, this, getRow());
                    }
                } else if (getOnItemViewSelectedListener() != null) {
                    getOnItemViewSelectedListener().onItemSelected(ibvh.getViewHolder(), ibvh.getItem(), this, getRow());
                }
            }
        }

        private int getViewCenter(View view) {
            return (view.getRight() - view.getLeft()) / 2;
        }

        /* access modifiers changed from: package-private */
        public void checkFirstAndLastPosition(boolean fromScroll) {
            RecyclerView.ViewHolder viewHolder = this.mActionsRow.findViewHolderForPosition(this.mNumItems - 1);
            if (viewHolder == null || viewHolder.itemView.getRight() > this.mActionsRow.getWidth()) {
            }
            RecyclerView.ViewHolder viewHolder2 = this.mActionsRow.findViewHolderForPosition(0);
            if (viewHolder2 != null && viewHolder2.itemView.getLeft() >= 0) {
            }
        }

        public final ViewGroup getOverviewView() {
            return this.mOverviewFrame;
        }

        public final DetailsOverviewLogoPresenter.ViewHolder getLogoViewHolder() {
            return this.mDetailsLogoViewHolder;
        }

        public final Presenter.ViewHolder getDetailsDescriptionViewHolder() {
            return this.mDetailsDescriptionViewHolder;
        }

        public final ViewGroup getDetailsDescriptionFrame() {
            return this.mDetailsDescriptionFrame;
        }

        public final ViewGroup getActionsRow() {
            return this.mActionsRow;
        }

        public final int getState() {
            return this.mState;
        }

        public class DetailsOverviewRowListener extends DetailsOverviewRow.Listener {
            public DetailsOverviewRowListener() {
            }

            public void onImageDrawableChanged(DetailsOverviewRow row) {
                FullWidthDetailsOverviewRowPresenter.sHandler.removeCallbacks(ViewHolder.this.mUpdateDrawableCallback);
                FullWidthDetailsOverviewRowPresenter.sHandler.post(ViewHolder.this.mUpdateDrawableCallback);
            }

            public void onItemChanged(DetailsOverviewRow row) {
                if (ViewHolder.this.mDetailsDescriptionViewHolder != null) {
                    FullWidthDetailsOverviewRowPresenter.this.mDetailsPresenter.onUnbindViewHolder(ViewHolder.this.mDetailsDescriptionViewHolder);
                }
                FullWidthDetailsOverviewRowPresenter.this.mDetailsPresenter.onBindViewHolder(ViewHolder.this.mDetailsDescriptionViewHolder, row.getItem());
            }

            public void onActionsAdapterChanged(DetailsOverviewRow row) {
                ViewHolder.this.bindActions(row.getActionsAdapter());
            }
        }
    }
}
