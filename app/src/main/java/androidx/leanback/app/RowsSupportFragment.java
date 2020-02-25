package androidx.leanback.app;

import android.animation.TimeAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p004v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.leanback.C0364R;
import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.BaseOnItemViewSelectedListener;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;
import androidx.leanback.widget.ViewHolderTask;

import java.util.ArrayList;

public class RowsSupportFragment extends BaseRowSupportFragment implements BrowseSupportFragment.MainFragmentRowsAdapterProvider, BrowseSupportFragment.MainFragmentAdapterProvider {
    static final int ALIGN_TOP_NOT_SET = Integer.MIN_VALUE;
    static final boolean DEBUG = false;
    static final String TAG = "RowsSupportFragment";
    boolean mAfterEntranceTransition = true;
    boolean mExpand = true;
    ItemBridgeAdapter.AdapterListener mExternalAdapterListener;
    boolean mFreezeRows;
    BaseOnItemViewClickedListener mOnItemViewClickedListener;
    BaseOnItemViewSelectedListener mOnItemViewSelectedListener;
    ItemBridgeAdapter.ViewHolder mSelectedViewHolder;
    boolean mViewsCreated;
    private int mAlignedTop = Integer.MIN_VALUE;
    private MainFragmentAdapter mMainFragmentAdapter;
    private MainFragmentRowsAdapter mMainFragmentRowsAdapter;
    private ArrayList<Presenter> mPresenterMapper;
    private RecyclerView.RecycledViewPool mRecycledViewPool;
    private final ItemBridgeAdapter.AdapterListener mBridgeAdapterListener = new ItemBridgeAdapter.AdapterListener() {
        public void onAddPresenter(Presenter presenter, int type) {
            if (RowsSupportFragment.this.mExternalAdapterListener != null) {
                RowsSupportFragment.this.mExternalAdapterListener.onAddPresenter(presenter, type);
            }
        }

        public void onCreate(ItemBridgeAdapter.ViewHolder vh) {
            VerticalGridView listView = RowsSupportFragment.this.getVerticalGridView();
            if (listView != null) {
                listView.setClipChildren(false);
            }
            RowsSupportFragment.this.setupSharedViewPool(vh);
            RowsSupportFragment.this.mViewsCreated = true;
            vh.setExtraObject(new RowViewHolderExtra(vh));
            RowsSupportFragment.setRowViewSelected(vh, false, true);
            if (RowsSupportFragment.this.mExternalAdapterListener != null) {
                RowsSupportFragment.this.mExternalAdapterListener.onCreate(vh);
            }
        }

        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder vh) {
            RowsSupportFragment.setRowViewExpanded(vh, RowsSupportFragment.this.mExpand);
            RowPresenter rowPresenter = (RowPresenter) vh.getPresenter();
            RowPresenter.ViewHolder rowVh = rowPresenter.getRowViewHolder(vh.getViewHolder());
            rowPresenter.setEntranceTransitionState(rowVh, RowsSupportFragment.this.mAfterEntranceTransition);
            rowVh.setOnItemViewSelectedListener(RowsSupportFragment.this.mOnItemViewSelectedListener);
            rowVh.setOnItemViewClickedListener(RowsSupportFragment.this.mOnItemViewClickedListener);
            rowPresenter.freeze(rowVh, RowsSupportFragment.this.mFreezeRows);
            if (RowsSupportFragment.this.mExternalAdapterListener != null) {
                RowsSupportFragment.this.mExternalAdapterListener.onAttachedToWindow(vh);
            }
        }

        public void onDetachedFromWindow(ItemBridgeAdapter.ViewHolder vh) {
            if (RowsSupportFragment.this.mSelectedViewHolder == vh) {
                RowsSupportFragment.setRowViewSelected(RowsSupportFragment.this.mSelectedViewHolder, false, true);
                RowsSupportFragment.this.mSelectedViewHolder = null;
            }
            RowPresenter.ViewHolder rowVh = ((RowPresenter) vh.getPresenter()).getRowViewHolder(vh.getViewHolder());
            rowVh.setOnItemViewSelectedListener(null);
            rowVh.setOnItemViewClickedListener(null);
            if (RowsSupportFragment.this.mExternalAdapterListener != null) {
                RowsSupportFragment.this.mExternalAdapterListener.onDetachedFromWindow(vh);
            }
        }

        public void onBind(ItemBridgeAdapter.ViewHolder vh) {
            if (RowsSupportFragment.this.mExternalAdapterListener != null) {
                RowsSupportFragment.this.mExternalAdapterListener.onBind(vh);
            }
        }

        public void onUnbind(ItemBridgeAdapter.ViewHolder vh) {
            RowsSupportFragment.setRowViewSelected(vh, false, true);
            if (RowsSupportFragment.this.mExternalAdapterListener != null) {
                RowsSupportFragment.this.mExternalAdapterListener.onUnbind(vh);
            }
        }
    };
    private int mSubPosition;

    static void setRowViewExpanded(ItemBridgeAdapter.ViewHolder vh, boolean expanded) {
        ((RowPresenter) vh.getPresenter()).setRowViewExpanded(vh.getViewHolder(), expanded);
    }

    static void setRowViewSelected(ItemBridgeAdapter.ViewHolder vh, boolean selected, boolean immediate) {
        ((RowViewHolderExtra) vh.getExtraObject()).animateSelect(selected, immediate);
        ((RowPresenter) vh.getPresenter()).setRowViewSelected(vh.getViewHolder(), selected);
    }

    static RowPresenter.ViewHolder getRowViewHolder(ItemBridgeAdapter.ViewHolder ibvh) {
        if (ibvh == null) {
            return null;
        }
        return ((RowPresenter) ibvh.getPresenter()).getRowViewHolder(ibvh.getViewHolder());
    }

    public /* bridge */ /* synthetic */ int getSelectedPosition() {
        return super.getSelectedPosition();
    }

    public /* bridge */ /* synthetic */ void setSelectedPosition(int i) {
        super.setSelectedPosition(i);
    }

    public /* bridge */ /* synthetic */ View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public /* bridge */ /* synthetic */ void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public /* bridge */ /* synthetic */ void onTransitionStart() {
        super.onTransitionStart();
    }

    public /* bridge */ /* synthetic */ void setSelectedPosition(int i, boolean z) {
        super.setSelectedPosition(i, z);
    }

    public BrowseSupportFragment.MainFragmentAdapter getMainFragmentAdapter() {
        if (this.mMainFragmentAdapter == null) {
            this.mMainFragmentAdapter = new MainFragmentAdapter(this);
        }
        return this.mMainFragmentAdapter;
    }

    public BrowseSupportFragment.MainFragmentRowsAdapter getMainFragmentRowsAdapter() {
        if (this.mMainFragmentRowsAdapter == null) {
            this.mMainFragmentRowsAdapter = new MainFragmentRowsAdapter(this);
        }
        return this.mMainFragmentRowsAdapter;
    }

    /* access modifiers changed from: protected */
    public VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view.findViewById(C0364R.C0366id.container_list);
    }

    public BaseOnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener listener) {
        this.mOnItemViewClickedListener = listener;
        if (this.mViewsCreated) {
            throw new IllegalStateException("Item clicked listener must be set before views are created");
        }
    }

    @Deprecated
    public void enableRowScaling(boolean enable) {
    }

    public void setExpand(boolean expand) {
        this.mExpand = expand;
        VerticalGridView listView = getVerticalGridView();
        if (listView != null) {
            int count = listView.getChildCount();
            for (int i = 0; i < count; i++) {
                setRowViewExpanded((ItemBridgeAdapter.ViewHolder) listView.getChildViewHolder(listView.getChildAt(i)), this.mExpand);
            }
        }
    }

    public BaseOnItemViewSelectedListener getOnItemViewSelectedListener() {
        return this.mOnItemViewSelectedListener;
    }

    public void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener listener) {
        this.mOnItemViewSelectedListener = listener;
        VerticalGridView listView = getVerticalGridView();
        if (listView != null) {
            int count = listView.getChildCount();
            for (int i = 0; i < count; i++) {
                getRowViewHolder((ItemBridgeAdapter.ViewHolder) listView.getChildViewHolder(listView.getChildAt(i))).setOnItemViewSelectedListener(this.mOnItemViewSelectedListener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onRowSelected(RecyclerView parent, RecyclerView.ViewHolder viewHolder, int position, int subposition) {
        boolean z = true;
        if (!(this.mSelectedViewHolder == viewHolder && this.mSubPosition == subposition)) {
            this.mSubPosition = subposition;
            ItemBridgeAdapter.ViewHolder viewHolder2 = this.mSelectedViewHolder;
            if (viewHolder2 != null) {
                setRowViewSelected(viewHolder2, false, false);
            }
            this.mSelectedViewHolder = (ItemBridgeAdapter.ViewHolder) viewHolder;
            ItemBridgeAdapter.ViewHolder viewHolder3 = this.mSelectedViewHolder;
            if (viewHolder3 != null) {
                setRowViewSelected(viewHolder3, true, false);
            }
        }
        MainFragmentAdapter mainFragmentAdapter = this.mMainFragmentAdapter;
        if (mainFragmentAdapter != null) {
            BrowseSupportFragment.FragmentHost fragmentHost = mainFragmentAdapter.getFragmentHost();
            if (position > 0) {
                z = false;
            }
            fragmentHost.showTitleView(z);
        }
    }

    public RowPresenter.ViewHolder getRowViewHolder(int position) {
        VerticalGridView verticalView = getVerticalGridView();
        if (verticalView == null) {
            return null;
        }
        return getRowViewHolder((ItemBridgeAdapter.ViewHolder) verticalView.findViewHolderForAdapterPosition(position));
    }

    /* access modifiers changed from: package-private */
    public int getLayoutResourceId() {
        return C0364R.layout.lb_rows_fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getVerticalGridView().setItemAlignmentViewId(C0364R.C0366id.row_content);
        getVerticalGridView().setSaveChildrenPolicy(2);
        setAlignment(this.mAlignedTop);
        this.mRecycledViewPool = null;
        this.mPresenterMapper = null;
        MainFragmentAdapter mainFragmentAdapter = this.mMainFragmentAdapter;
        if (mainFragmentAdapter != null) {
            mainFragmentAdapter.getFragmentHost().notifyViewCreated(this.mMainFragmentAdapter);
        }
    }

    public void onDestroyView() {
        this.mViewsCreated = false;
        super.onDestroyView();
    }

    /* access modifiers changed from: package-private */
    public void setExternalAdapterListener(ItemBridgeAdapter.AdapterListener listener) {
        this.mExternalAdapterListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setupSharedViewPool(ItemBridgeAdapter.ViewHolder bridgeVh) {
        RowPresenter.ViewHolder rowVh = ((RowPresenter) bridgeVh.getPresenter()).getRowViewHolder(bridgeVh.getViewHolder());
        if (rowVh instanceof ListRowPresenter.ViewHolder) {
            HorizontalGridView view = ((ListRowPresenter.ViewHolder) rowVh).getGridView();
            RecyclerView.RecycledViewPool recycledViewPool = this.mRecycledViewPool;
            if (recycledViewPool == null) {
                this.mRecycledViewPool = view.getRecycledViewPool();
            } else {
                view.setRecycledViewPool(recycledViewPool);
            }
            ItemBridgeAdapter bridgeAdapter = ((ListRowPresenter.ViewHolder) rowVh).getBridgeAdapter();
            ArrayList<Presenter> arrayList = this.mPresenterMapper;
            if (arrayList == null) {
                this.mPresenterMapper = bridgeAdapter.getPresenterMapper();
            } else {
                bridgeAdapter.setPresenterMapper(arrayList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateAdapter() {
        super.updateAdapter();
        this.mSelectedViewHolder = null;
        this.mViewsCreated = false;
        ItemBridgeAdapter adapter = getBridgeAdapter();
        if (adapter != null) {
            adapter.setAdapterListener(this.mBridgeAdapterListener);
        }
    }

    public boolean onTransitionPrepare() {
        boolean prepared = super.onTransitionPrepare();
        if (prepared) {
            freezeRows(true);
        }
        return prepared;
    }

    public void onTransitionEnd() {
        super.onTransitionEnd();
        freezeRows(false);
    }

    private void freezeRows(boolean freeze) {
        this.mFreezeRows = freeze;
        VerticalGridView verticalView = getVerticalGridView();
        if (verticalView != null) {
            int count = verticalView.getChildCount();
            for (int i = 0; i < count; i++) {
                ItemBridgeAdapter.ViewHolder ibvh = (ItemBridgeAdapter.ViewHolder) verticalView.getChildViewHolder(verticalView.getChildAt(i));
                RowPresenter rowPresenter = (RowPresenter) ibvh.getPresenter();
                rowPresenter.freeze(rowPresenter.getRowViewHolder(ibvh.getViewHolder()), freeze);
            }
        }
    }

    public void setEntranceTransitionState(boolean afterTransition) {
        this.mAfterEntranceTransition = afterTransition;
        VerticalGridView verticalView = getVerticalGridView();
        if (verticalView != null) {
            int count = verticalView.getChildCount();
            for (int i = 0; i < count; i++) {
                ItemBridgeAdapter.ViewHolder ibvh = (ItemBridgeAdapter.ViewHolder) verticalView.getChildViewHolder(verticalView.getChildAt(i));
                RowPresenter rowPresenter = (RowPresenter) ibvh.getPresenter();
                rowPresenter.setEntranceTransitionState(rowPresenter.getRowViewHolder(ibvh.getViewHolder()), this.mAfterEntranceTransition);
            }
        }
    }

    public void setSelectedPosition(int rowPosition, boolean smooth, final Presenter.ViewHolderTask rowHolderTask) {
        VerticalGridView verticalView = getVerticalGridView();
        if (verticalView != null) {
            ViewHolderTask task = null;
            if (rowHolderTask != null) {
                task = new ViewHolderTask() {
                    public void run(final RecyclerView.ViewHolder rvh) {
                        rvh.itemView.post(new Runnable() {
                            public void run() {
                                rowHolderTask.run(RowsSupportFragment.getRowViewHolder((ItemBridgeAdapter.ViewHolder) rvh));
                            }
                        });
                    }
                };
            }
            if (smooth) {
                verticalView.setSelectedPositionSmooth(rowPosition, task);
            } else {
                verticalView.setSelectedPosition(rowPosition, task);
            }
        }
    }

    public boolean isScrolling() {
        if (getVerticalGridView() == null || getVerticalGridView().getScrollState() == 0) {
            return false;
        }
        return true;
    }

    public void setAlignment(int windowAlignOffsetFromTop) {
        if (windowAlignOffsetFromTop != Integer.MIN_VALUE) {
            this.mAlignedTop = windowAlignOffsetFromTop;
            VerticalGridView gridView = getVerticalGridView();
            if (gridView != null) {
                gridView.setItemAlignmentOffset(0);
                gridView.setItemAlignmentOffsetPercent(-1.0f);
                gridView.setItemAlignmentOffsetWithPadding(true);
                gridView.setWindowAlignmentOffset(this.mAlignedTop);
                gridView.setWindowAlignmentOffsetPercent(-1.0f);
                gridView.setWindowAlignment(0);
            }
        }
    }

    public RowPresenter.ViewHolder findRowViewHolderByPosition(int position) {
        if (this.mVerticalGridView == null) {
            return null;
        }
        return getRowViewHolder((ItemBridgeAdapter.ViewHolder) this.mVerticalGridView.findViewHolderForAdapterPosition(position));
    }

    static final class RowViewHolderExtra implements TimeAnimator.TimeListener {
        static final Interpolator sSelectAnimatorInterpolator = new DecelerateInterpolator(2.0f);
        final RowPresenter mRowPresenter;
        final Presenter.ViewHolder mRowViewHolder;
        final TimeAnimator mSelectAnimator = new TimeAnimator();
        final int mSelectAnimatorDurationInUse;
        final Interpolator mSelectAnimatorInterpolatorInUse;
        float mSelectLevelAnimDelta;
        float mSelectLevelAnimStart;

        RowViewHolderExtra(ItemBridgeAdapter.ViewHolder ibvh) {
            this.mRowPresenter = (RowPresenter) ibvh.getPresenter();
            this.mRowViewHolder = ibvh.getViewHolder();
            this.mSelectAnimator.setTimeListener(this);
            this.mSelectAnimatorDurationInUse = ibvh.itemView.getResources().getInteger(C0364R.integer.lb_browse_rows_anim_duration);
            this.mSelectAnimatorInterpolatorInUse = sSelectAnimatorInterpolator;
        }

        public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
            if (this.mSelectAnimator.isRunning()) {
                updateSelect(totalTime, deltaTime);
            }
        }

        /* access modifiers changed from: package-private */
        public void updateSelect(long totalTime, long deltaTime) {
            float fraction;
            int i = this.mSelectAnimatorDurationInUse;
            if (totalTime >= ((long) i)) {
                fraction = 1.0f;
                this.mSelectAnimator.end();
            } else {
                double d = (double) totalTime;
                double d2 = (double) i;
                Double.isNaN(d);
                Double.isNaN(d2);
                fraction = (float) (d / d2);
            }
            Interpolator interpolator = this.mSelectAnimatorInterpolatorInUse;
            if (interpolator != null) {
                fraction = interpolator.getInterpolation(fraction);
            }
            this.mRowPresenter.setSelectLevel(this.mRowViewHolder, this.mSelectLevelAnimStart + (this.mSelectLevelAnimDelta * fraction));
        }

        /* access modifiers changed from: package-private */
        public void animateSelect(boolean select, boolean immediate) {
            this.mSelectAnimator.end();
            float end = select ? 1.0f : 0.0f;
            if (immediate) {
                this.mRowPresenter.setSelectLevel(this.mRowViewHolder, end);
            } else if (this.mRowPresenter.getSelectLevel(this.mRowViewHolder) != end) {
                this.mSelectLevelAnimStart = this.mRowPresenter.getSelectLevel(this.mRowViewHolder);
                this.mSelectLevelAnimDelta = end - this.mSelectLevelAnimStart;
                this.mSelectAnimator.start();
            }
        }
    }

    public static class MainFragmentAdapter extends BrowseSupportFragment.MainFragmentAdapter<RowsSupportFragment> {
        public MainFragmentAdapter(RowsSupportFragment fragment) {
            super(fragment);
            setScalingEnabled(true);
        }

        public boolean isScrolling() {
            return ((RowsSupportFragment) getFragment()).isScrolling();
        }

        public void setExpand(boolean expand) {
            ((RowsSupportFragment) getFragment()).setExpand(expand);
        }

        public void setEntranceTransitionState(boolean state) {
            ((RowsSupportFragment) getFragment()).setEntranceTransitionState(state);
        }

        public void setAlignment(int windowAlignOffsetFromTop) {
            ((RowsSupportFragment) getFragment()).setAlignment(windowAlignOffsetFromTop);
        }

        public boolean onTransitionPrepare() {
            return ((RowsSupportFragment) getFragment()).onTransitionPrepare();
        }

        public void onTransitionStart() {
            ((RowsSupportFragment) getFragment()).onTransitionStart();
        }

        public void onTransitionEnd() {
            ((RowsSupportFragment) getFragment()).onTransitionEnd();
        }
    }

    public static class MainFragmentRowsAdapter extends BrowseSupportFragment.MainFragmentRowsAdapter<RowsSupportFragment> {
        public MainFragmentRowsAdapter(RowsSupportFragment fragment) {
            super(fragment);
        }

        public void setAdapter(ObjectAdapter adapter) {
            ((RowsSupportFragment) getFragment()).setAdapter(adapter);
        }

        public void setOnItemViewClickedListener(OnItemViewClickedListener listener) {
            ((RowsSupportFragment) getFragment()).setOnItemViewClickedListener(listener);
        }

        public void setOnItemViewSelectedListener(OnItemViewSelectedListener listener) {
            ((RowsSupportFragment) getFragment()).setOnItemViewSelectedListener(listener);
        }

        public void setSelectedPosition(int rowPosition, boolean smooth, Presenter.ViewHolderTask rowHolderTask) {
            ((RowsSupportFragment) getFragment()).setSelectedPosition(rowPosition, smooth, rowHolderTask);
        }

        public void setSelectedPosition(int rowPosition, boolean smooth) {
            ((RowsSupportFragment) getFragment()).setSelectedPosition(rowPosition, smooth);
        }

        public int getSelectedPosition() {
            return ((RowsSupportFragment) getFragment()).getSelectedPosition();
        }

        public RowPresenter.ViewHolder findRowViewHolderByPosition(int position) {
            return ((RowsSupportFragment) getFragment()).findRowViewHolderByPosition(position);
        }
    }
}
