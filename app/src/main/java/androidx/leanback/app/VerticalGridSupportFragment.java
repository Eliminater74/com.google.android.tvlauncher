package androidx.leanback.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.C0364R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.util.StateMachine;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnChildLaidOutListener;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;

public class VerticalGridSupportFragment extends BaseSupportFragment {
    static final boolean DEBUG = false;
    static final String TAG = "VerticalGF";
    VerticalGridPresenter.ViewHolder mGridViewHolder;
    OnItemViewSelectedListener mOnItemViewSelectedListener;
    private ObjectAdapter mAdapter;
    private VerticalGridPresenter mGridPresenter;
    final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State("SET_ENTRANCE_START_STATE") {
        public void run() {
            VerticalGridSupportFragment.this.setEntranceTransitionState(false);
        }
    };
    private OnItemViewClickedListener mOnItemViewClickedListener;
    private Object mSceneAfterEntranceTransition;
    private int mSelectedPosition = -1;
    private final OnChildLaidOutListener mChildLaidOutListener = new OnChildLaidOutListener() {
        public void onChildLaidOut(ViewGroup parent, View view, int position, long id) {
            if (position == 0) {
                VerticalGridSupportFragment.this.showOrHideTitle();
            }
        }
    };
    private final OnItemViewSelectedListener mViewSelectedListener = new OnItemViewSelectedListener() {
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            VerticalGridSupportFragment.this.gridOnItemSelected(VerticalGridSupportFragment.this.mGridViewHolder.getGridView().getSelectedPosition());
            if (VerticalGridSupportFragment.this.mOnItemViewSelectedListener != null) {
                VerticalGridSupportFragment.this.mOnItemViewSelectedListener.onItemSelected(itemViewHolder, item, rowViewHolder, row);
            }
        }
    };

    /* access modifiers changed from: package-private */
    public void createStateMachineStates() {
        super.createStateMachineStates();
        this.mStateMachine.addState(this.STATE_SET_ENTRANCE_START_STATE);
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineTransitions() {
        super.createStateMachineTransitions();
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_SET_ENTRANCE_START_STATE, this.EVT_ON_CREATEVIEW);
    }

    public VerticalGridPresenter getGridPresenter() {
        return this.mGridPresenter;
    }

    public void setGridPresenter(VerticalGridPresenter gridPresenter) {
        if (gridPresenter != null) {
            this.mGridPresenter = gridPresenter;
            this.mGridPresenter.setOnItemViewSelectedListener(this.mViewSelectedListener);
            OnItemViewClickedListener onItemViewClickedListener = this.mOnItemViewClickedListener;
            if (onItemViewClickedListener != null) {
                this.mGridPresenter.setOnItemViewClickedListener(onItemViewClickedListener);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Grid presenter may not be null");
    }

    public ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ObjectAdapter adapter) {
        this.mAdapter = adapter;
        updateAdapter();
    }

    public void setOnItemViewSelectedListener(OnItemViewSelectedListener listener) {
        this.mOnItemViewSelectedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void gridOnItemSelected(int position) {
        if (position != this.mSelectedPosition) {
            this.mSelectedPosition = position;
            showOrHideTitle();
        }
    }

    /* access modifiers changed from: package-private */
    public void showOrHideTitle() {
        if (this.mGridViewHolder.getGridView().findViewHolderForAdapterPosition(this.mSelectedPosition) != null) {
            if (!this.mGridViewHolder.getGridView().hasPreviousViewInSameRow(this.mSelectedPosition)) {
                showTitle(true);
            } else {
                showTitle(false);
            }
        }
    }

    public OnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public void setOnItemViewClickedListener(OnItemViewClickedListener listener) {
        this.mOnItemViewClickedListener = listener;
        VerticalGridPresenter verticalGridPresenter = this.mGridPresenter;
        if (verticalGridPresenter != null) {
            verticalGridPresenter.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(C0364R.layout.lb_vertical_grid_fragment, container, false);
        installTitleView(inflater, (ViewGroup) root.findViewById(C0364R.C0366id.grid_frame), savedInstanceState);
        getProgressBarManager().setRootView(root);
        ViewGroup gridDock = (ViewGroup) root.findViewById(C0364R.C0366id.browse_grid_dock);
        this.mGridViewHolder = this.mGridPresenter.onCreateViewHolder(gridDock);
        gridDock.addView(this.mGridViewHolder.view);
        this.mGridViewHolder.getGridView().setOnChildLaidOutListener(this.mChildLaidOutListener);
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(gridDock, new Runnable() {
            public void run() {
                VerticalGridSupportFragment.this.setEntranceTransitionState(true);
            }
        });
        updateAdapter();
        return root;
    }

    private void setupFocusSearchListener() {
        ((BrowseFrameLayout) getView().findViewById(C0364R.C0366id.grid_frame)).setOnFocusSearchListener(getTitleHelper().getOnFocusSearchListener());
    }

    public void onStart() {
        super.onStart();
        setupFocusSearchListener();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mGridViewHolder = null;
    }

    public void setSelectedPosition(int position) {
        this.mSelectedPosition = position;
        VerticalGridPresenter.ViewHolder viewHolder = this.mGridViewHolder;
        if (viewHolder != null && viewHolder.getGridView().getAdapter() != null) {
            this.mGridViewHolder.getGridView().setSelectedPositionSmooth(position);
        }
    }

    private void updateAdapter() {
        VerticalGridPresenter.ViewHolder viewHolder = this.mGridViewHolder;
        if (viewHolder != null) {
            this.mGridPresenter.onBindViewHolder(viewHolder, this.mAdapter);
            if (this.mSelectedPosition != -1) {
                this.mGridViewHolder.getGridView().setSelectedPosition(this.mSelectedPosition);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object createEntranceTransition() {
        return TransitionHelper.loadTransition(getContext(), C0364R.C0367transition.lb_vertical_grid_entrance_transition);
    }

    /* access modifiers changed from: protected */
    public void runEntranceTransition(Object entranceTransition) {
        TransitionHelper.runTransition(this.mSceneAfterEntranceTransition, entranceTransition);
    }

    /* access modifiers changed from: package-private */
    public void setEntranceTransitionState(boolean afterTransition) {
        this.mGridPresenter.setEntranceTransitionState(this.mGridViewHolder, afterTransition);
    }
}
