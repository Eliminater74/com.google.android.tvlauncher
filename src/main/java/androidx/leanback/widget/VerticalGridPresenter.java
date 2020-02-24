package androidx.leanback.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.C0364R;
import androidx.leanback.system.Settings;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.ShadowOverlayHelper;

public class VerticalGridPresenter extends Presenter {
    private static final boolean DEBUG = false;
    private static final String TAG = "GridPresenter";
    private int mFocusZoomFactor;
    private boolean mKeepChildForeground;
    private int mNumColumns;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    private OnItemViewSelectedListener mOnItemViewSelectedListener;
    private boolean mRoundedCornersEnabled;
    private boolean mShadowEnabled;
    ShadowOverlayHelper mShadowOverlayHelper;
    private ItemBridgeAdapter.Wrapper mShadowOverlayWrapper;
    private boolean mUseFocusDimmer;

    class VerticalGridItemBridgeAdapter extends ItemBridgeAdapter {
        VerticalGridItemBridgeAdapter() {
        }

        /* access modifiers changed from: protected */
        public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                TransitionHelper.setTransitionGroup((ViewGroup) viewHolder.itemView, true);
            }
            if (VerticalGridPresenter.this.mShadowOverlayHelper != null) {
                VerticalGridPresenter.this.mShadowOverlayHelper.onViewCreated(viewHolder.itemView);
            }
        }

        public void onBind(final ItemBridgeAdapter.ViewHolder itemViewHolder) {
            if (VerticalGridPresenter.this.getOnItemViewClickedListener() != null) {
                itemViewHolder.mHolder.view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (VerticalGridPresenter.this.getOnItemViewClickedListener() != null) {
                            VerticalGridPresenter.this.getOnItemViewClickedListener().onItemClicked(itemViewHolder.mHolder, itemViewHolder.mItem, null, null);
                        }
                    }
                });
            }
        }

        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (VerticalGridPresenter.this.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(null);
            }
        }

        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            viewHolder.itemView.setActivated(true);
        }
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        final VerticalGridView mGridView;
        boolean mInitialized;
        ItemBridgeAdapter mItemBridgeAdapter;

        public ViewHolder(VerticalGridView view) {
            super(view);
            this.mGridView = view;
        }

        public VerticalGridView getGridView() {
            return this.mGridView;
        }
    }

    public VerticalGridPresenter() {
        this(3);
    }

    public VerticalGridPresenter(int focusZoomFactor) {
        this(focusZoomFactor, true);
    }

    public VerticalGridPresenter(int focusZoomFactor, boolean useFocusDimmer) {
        this.mNumColumns = -1;
        this.mShadowEnabled = true;
        this.mKeepChildForeground = true;
        this.mRoundedCornersEnabled = true;
        this.mFocusZoomFactor = focusZoomFactor;
        this.mUseFocusDimmer = useFocusDimmer;
    }

    public void setNumberOfColumns(int numColumns) {
        if (numColumns < 0) {
            throw new IllegalArgumentException("Invalid number of columns");
        } else if (this.mNumColumns != numColumns) {
            this.mNumColumns = numColumns;
        }
    }

    public int getNumberOfColumns() {
        return this.mNumColumns;
    }

    public final void setShadowEnabled(boolean enabled) {
        this.mShadowEnabled = enabled;
    }

    public final boolean getShadowEnabled() {
        return this.mShadowEnabled;
    }

    public boolean isUsingDefaultShadow() {
        return ShadowOverlayHelper.supportsShadow();
    }

    public final void enableChildRoundedCorners(boolean enable) {
        this.mRoundedCornersEnabled = enable;
    }

    public final boolean areChildRoundedCornersEnabled() {
        return this.mRoundedCornersEnabled;
    }

    public boolean isUsingZOrder(Context context) {
        return !Settings.getInstance(context).preferStaticShadows();
    }

    /* access modifiers changed from: package-private */
    public final boolean needsDefaultShadow() {
        return isUsingDefaultShadow() && getShadowEnabled();
    }

    public final int getFocusZoomFactor() {
        return this.mFocusZoomFactor;
    }

    public final boolean isFocusDimmerUsed() {
        return this.mUseFocusDimmer;
    }

    public final ViewHolder onCreateViewHolder(ViewGroup parent) {
        ViewHolder vh = createGridViewHolder(parent);
        vh.mInitialized = false;
        vh.mItemBridgeAdapter = new VerticalGridItemBridgeAdapter();
        initializeGridViewHolder(vh);
        if (vh.mInitialized) {
            return vh;
        }
        throw new RuntimeException("super.initializeGridViewHolder() must be called");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* access modifiers changed from: protected */
    public ViewHolder createGridViewHolder(ViewGroup parent) {
        return new ViewHolder((VerticalGridView) LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_vertical_grid, parent, false).findViewById(C0364R.C0366id.browse_grid));
    }

    /* access modifiers changed from: protected */
    public void initializeGridViewHolder(ViewHolder vh) {
        if (this.mNumColumns != -1) {
            vh.getGridView().setNumColumns(this.mNumColumns);
            boolean z = true;
            vh.mInitialized = true;
            Context context = vh.mGridView.getContext();
            if (this.mShadowOverlayHelper == null) {
                this.mShadowOverlayHelper = new ShadowOverlayHelper.Builder().needsOverlay(this.mUseFocusDimmer).needsShadow(needsDefaultShadow()).needsRoundedCorner(areChildRoundedCornersEnabled()).preferZOrder(isUsingZOrder(context)).keepForegroundDrawable(this.mKeepChildForeground).options(createShadowOverlayOptions()).build(context);
                if (this.mShadowOverlayHelper.needsWrapper()) {
                    this.mShadowOverlayWrapper = new ItemBridgeAdapterShadowOverlayWrapper(this.mShadowOverlayHelper);
                }
            }
            vh.mItemBridgeAdapter.setWrapper(this.mShadowOverlayWrapper);
            this.mShadowOverlayHelper.prepareParentForShadow(vh.mGridView);
            VerticalGridView gridView = vh.getGridView();
            if (this.mShadowOverlayHelper.getShadowType() == 3) {
                z = false;
            }
            gridView.setFocusDrawingOrderEnabled(z);
            FocusHighlightHelper.setupBrowseItemFocusHighlight(vh.mItemBridgeAdapter, this.mFocusZoomFactor, this.mUseFocusDimmer);
            final ViewHolder gridViewHolder = vh;
            vh.getGridView().setOnChildSelectedListener(new OnChildSelectedListener() {
                public void onChildSelected(ViewGroup parent, View view, int position, long id) {
                    VerticalGridPresenter.this.selectChildView(gridViewHolder, view);
                }
            });
            return;
        }
        throw new IllegalStateException("Number of columns must be set");
    }

    public final void setKeepChildForeground(boolean keep) {
        this.mKeepChildForeground = keep;
    }

    public final boolean getKeepChildForeground() {
        return this.mKeepChildForeground;
    }

    /* access modifiers changed from: protected */
    public ShadowOverlayHelper.Options createShadowOverlayOptions() {
        return ShadowOverlayHelper.Options.DEFAULT;
    }

    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.mItemBridgeAdapter.setAdapter((ObjectAdapter) item);
        vh.getGridView().setAdapter(vh.mItemBridgeAdapter);
    }

    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.mItemBridgeAdapter.setAdapter(null);
        vh.getGridView().setAdapter(null);
    }

    public final void setOnItemViewSelectedListener(OnItemViewSelectedListener listener) {
        this.mOnItemViewSelectedListener = listener;
    }

    public final OnItemViewSelectedListener getOnItemViewSelectedListener() {
        return this.mOnItemViewSelectedListener;
    }

    public final void setOnItemViewClickedListener(OnItemViewClickedListener listener) {
        this.mOnItemViewClickedListener = listener;
    }

    public final OnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    /* access modifiers changed from: package-private */
    public void selectChildView(ViewHolder vh, View view) {
        ItemBridgeAdapter.ViewHolder ibh;
        if (getOnItemViewSelectedListener() != null) {
            if (view == null) {
                ibh = null;
            } else {
                ibh = (ItemBridgeAdapter.ViewHolder) vh.getGridView().getChildViewHolder(view);
            }
            if (ibh == null) {
                getOnItemViewSelectedListener().onItemSelected(null, null, null, null);
            } else {
                getOnItemViewSelectedListener().onItemSelected(ibh.mHolder, ibh.mItem, null, null);
            }
        }
    }

    public void setEntranceTransitionState(ViewHolder holder, boolean afterEntrance) {
        holder.mGridView.setChildrenVisibility(afterEntrance ? 0 : 4);
    }
}
