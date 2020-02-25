package androidx.leanback.app;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.app.FragmentActivity;
import android.support.p001v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.leanback.C0364R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;
import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.BaseOnItemViewSelectedListener;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.DetailsParallax;
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import androidx.leanback.widget.ItemAlignmentFacet;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;

import java.lang.ref.WeakReference;

public class DetailsSupportFragment extends BaseSupportFragment {
    static final boolean DEBUG = false;
    static final String TAG = "DetailsSupportFragment";
    final StateMachine.Event EVT_DETAILS_ROW_LOADED = new StateMachine.Event("onFirstRowLoaded");
    final StateMachine.Event EVT_ENTER_TRANSIITON_DONE = new StateMachine.Event("onEnterTransitionDone");
    final StateMachine.Event EVT_NO_ENTER_TRANSITION = new StateMachine.Event("EVT_NO_ENTER_TRANSITION");
    final StateMachine.Event EVT_ONSTART = new StateMachine.Event("onStart");
    final StateMachine.Event EVT_SWITCH_TO_VIDEO = new StateMachine.Event("switchToVideo");
    final StateMachine.State STATE_ENTER_TRANSITION_COMPLETE = new StateMachine.State("STATE_ENTER_TRANSIITON_COMPLETE", true, false);
    final StateMachine.State STATE_ENTER_TRANSITION_INIT = new StateMachine.State("STATE_ENTER_TRANSIITON_INIT");
    final SetSelectionRunnable mSetSelectionRunnable = new SetSelectionRunnable();
    ObjectAdapter mAdapter;
    Drawable mBackgroundDrawable;
    View mBackgroundView;
    int mContainerListAlignTop;
    DetailsSupportFragmentBackgroundController mDetailsBackgroundController;
    final StateMachine.State STATE_ON_SAFE_START = new StateMachine.State("STATE_ON_SAFE_START") {
        public void run() {
            DetailsSupportFragment.this.onSafeStart();
        }
    };
    DetailsParallax mDetailsParallax;
    BaseOnItemViewSelectedListener mExternalOnItemViewSelectedListener;
    BaseOnItemViewClickedListener mOnItemViewClickedListener;
    boolean mPendingFocusOnVideo = false;
    BrowseFrameLayout mRootView;
    RowsSupportFragment mRowsSupportFragment;
    final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State("STATE_SET_ENTRANCE_START_STATE") {
        public void run() {
            DetailsSupportFragment.this.mRowsSupportFragment.setEntranceTransitionState(false);
        }
    };
    final StateMachine.State STATE_SWITCH_TO_VIDEO_IN_ON_CREATE = new StateMachine.State("STATE_SWITCH_TO_VIDEO_IN_ON_CREATE", false, false) {
        public void run() {
            DetailsSupportFragment.this.switchToVideoBeforeVideoSupportFragmentCreated();
        }
    };
    final BaseOnItemViewSelectedListener<Object> mOnItemViewSelectedListener = new BaseOnItemViewSelectedListener<Object>() {
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Object row) {
            DetailsSupportFragment.this.onRowSelected(DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().getSelectedPosition(), DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().getSelectedSubPosition());
            if (DetailsSupportFragment.this.mExternalOnItemViewSelectedListener != null) {
                DetailsSupportFragment.this.mExternalOnItemViewSelectedListener.onItemSelected(itemViewHolder, item, rowViewHolder, row);
            }
        }
    };
    Object mSceneAfterEntranceTransition;
    Fragment mVideoSupportFragment;
    TransitionListener mReturnTransitionListener = new TransitionListener() {
        public void onTransitionStart(Object transition) {
            DetailsSupportFragment.this.onReturnTransitionStart();
        }
    };
    WaitEnterTransitionTimeout mWaitEnterTransitionTimeout;
    final StateMachine.State STATE_ENTER_TRANSITION_CANCEL = new StateMachine.State("STATE_ENTER_TRANSITION_CANCEL", false, false) {
        public void run() {
            if (DetailsSupportFragment.this.mWaitEnterTransitionTimeout != null) {
                DetailsSupportFragment.this.mWaitEnterTransitionTimeout.mRef.clear();
            }
            if (DetailsSupportFragment.this.getActivity() != null) {
                Window window = DetailsSupportFragment.this.getActivity().getWindow();
                Object returnTransition = TransitionHelper.getReturnTransition(window);
                Object sharedReturnTransition = TransitionHelper.getSharedElementReturnTransition(window);
                TransitionHelper.setEnterTransition(window, (Object) null);
                TransitionHelper.setSharedElementEnterTransition(window, (Object) null);
                TransitionHelper.setReturnTransition(window, returnTransition);
                TransitionHelper.setSharedElementReturnTransition(window, sharedReturnTransition);
            }
        }
    };
    final StateMachine.State STATE_ENTER_TRANSITION_PENDING = new StateMachine.State("STATE_ENTER_TRANSITION_PENDING") {
        public void run() {
            if (DetailsSupportFragment.this.mWaitEnterTransitionTimeout == null) {
                new WaitEnterTransitionTimeout(DetailsSupportFragment.this);
            }
        }
    };
    TransitionListener mEnterTransitionListener = new TransitionListener() {
        public void onTransitionStart(Object transition) {
            if (DetailsSupportFragment.this.mWaitEnterTransitionTimeout != null) {
                DetailsSupportFragment.this.mWaitEnterTransitionTimeout.mRef.clear();
            }
        }

        public void onTransitionCancel(Object transition) {
            DetailsSupportFragment.this.mStateMachine.fireEvent(DetailsSupportFragment.this.EVT_ENTER_TRANSIITON_DONE);
        }

        public void onTransitionEnd(Object transition) {
            DetailsSupportFragment.this.mStateMachine.fireEvent(DetailsSupportFragment.this.EVT_ENTER_TRANSIITON_DONE);
        }
    };
    final StateMachine.State STATE_ENTER_TRANSITION_ADDLISTENER = new StateMachine.State("STATE_ENTER_TRANSITION_PENDING") {
        public void run() {
            TransitionHelper.addTransitionListener(TransitionHelper.getEnterTransition(DetailsSupportFragment.this.getActivity().getWindow()), DetailsSupportFragment.this.mEnterTransitionListener);
        }
    };

    /* access modifiers changed from: package-private */
    public void switchToVideoBeforeVideoSupportFragmentCreated() {
        this.mDetailsBackgroundController.switchToVideoBeforeCreate();
        showTitle(false);
        this.mPendingFocusOnVideo = true;
        slideOutGridView();
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineStates() {
        super.createStateMachineStates();
        this.mStateMachine.addState(this.STATE_SET_ENTRANCE_START_STATE);
        this.mStateMachine.addState(this.STATE_ON_SAFE_START);
        this.mStateMachine.addState(this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_INIT);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_ADDLISTENER);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_CANCEL);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_PENDING);
        this.mStateMachine.addState(this.STATE_ENTER_TRANSITION_COMPLETE);
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineTransitions() {
        super.createStateMachineTransitions();
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ENTER_TRANSITION_INIT, this.EVT_ON_CREATE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_COMPLETE, this.COND_TRANSITION_NOT_SUPPORTED);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_NO_ENTER_TRANSITION);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_CANCEL, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_CANCEL, this.STATE_ENTER_TRANSITION_COMPLETE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_INIT, this.STATE_ENTER_TRANSITION_ADDLISTENER, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_ADDLISTENER, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_ENTER_TRANSIITON_DONE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_ADDLISTENER, this.STATE_ENTER_TRANSITION_PENDING, this.EVT_DETAILS_ROW_LOADED);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_PENDING, this.STATE_ENTER_TRANSITION_COMPLETE, this.EVT_ENTER_TRANSIITON_DONE);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_COMPLETE, this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.STATE_ENTRANCE_COMPLETE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_COMPLETE, this.STATE_SWITCH_TO_VIDEO_IN_ON_CREATE, this.EVT_SWITCH_TO_VIDEO);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_SET_ENTRANCE_START_STATE, this.EVT_ONSTART);
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ON_SAFE_START, this.EVT_ONSTART);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_COMPLETE, this.STATE_ON_SAFE_START);
        this.mStateMachine.addTransition(this.STATE_ENTER_TRANSITION_COMPLETE, this.STATE_ON_SAFE_START);
    }

    public ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ObjectAdapter adapter) {
        this.mAdapter = adapter;
        Presenter[] presenters = adapter.getPresenterSelector().getPresenters();
        if (presenters != null) {
            for (Presenter presenter : presenters) {
                setupPresenter(presenter);
            }
        } else {
            Log.e(TAG, "PresenterSelector.getPresenters() not implemented");
        }
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment != null) {
            rowsSupportFragment.setAdapter(adapter);
        }
    }

    public void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener listener) {
        this.mExternalOnItemViewSelectedListener = listener;
    }

    public BaseOnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener listener) {
        if (this.mOnItemViewClickedListener != listener) {
            this.mOnItemViewClickedListener = listener;
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (rowsSupportFragment != null) {
                rowsSupportFragment.setOnItemViewClickedListener(listener);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContainerListAlignTop = getResources().getDimensionPixelSize(C0364R.dimen.lb_details_rows_align_top);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (TransitionHelper.getEnterTransition(activity.getWindow()) == null) {
                this.mStateMachine.fireEvent(this.EVT_NO_ENTER_TRANSITION);
            }
            Object transition = TransitionHelper.getReturnTransition(activity.getWindow());
            if (transition != null) {
                TransitionHelper.addTransitionListener(transition, this.mReturnTransitionListener);
                return;
            }
            return;
        }
        this.mStateMachine.fireEvent(this.EVT_NO_ENTER_TRANSITION);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = (BrowseFrameLayout) inflater.inflate(C0364R.layout.lb_details_fragment, container, false);
        this.mBackgroundView = this.mRootView.findViewById(C0364R.C0366id.details_background_view);
        View view = this.mBackgroundView;
        if (view != null) {
            view.setBackground(this.mBackgroundDrawable);
        }
        this.mRowsSupportFragment = (RowsSupportFragment) getChildFragmentManager().findFragmentById(C0364R.C0366id.details_rows_dock);
        if (this.mRowsSupportFragment == null) {
            this.mRowsSupportFragment = new RowsSupportFragment();
            getChildFragmentManager().beginTransaction().replace(C0364R.C0366id.details_rows_dock, this.mRowsSupportFragment).commit();
        }
        installTitleView(inflater, this.mRootView, savedInstanceState);
        this.mRowsSupportFragment.setAdapter(this.mAdapter);
        this.mRowsSupportFragment.setOnItemViewSelectedListener(this.mOnItemViewSelectedListener);
        this.mRowsSupportFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(this.mRootView, new Runnable() {
            public void run() {
                DetailsSupportFragment.this.mRowsSupportFragment.setEntranceTransitionState(true);
            }
        });
        setupDpadNavigation();
        if (Build.VERSION.SDK_INT >= 21) {
            this.mRowsSupportFragment.setExternalAdapterListener(new ItemBridgeAdapter.AdapterListener() {
                public void onCreate(ItemBridgeAdapter.ViewHolder vh) {
                    if (DetailsSupportFragment.this.mDetailsParallax != null && (vh.getViewHolder() instanceof FullWidthDetailsOverviewRowPresenter.ViewHolder)) {
                        ((FullWidthDetailsOverviewRowPresenter.ViewHolder) vh.getViewHolder()).getOverviewView().setTag(C0364R.C0366id.lb_parallax_source, DetailsSupportFragment.this.mDetailsParallax);
                    }
                }
            });
        }
        return this.mRootView;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public View inflateTitle(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return super.onInflateTitleView(inflater, parent, savedInstanceState);
    }

    public View onInflateTitleView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflateTitle(inflater, parent, savedInstanceState);
    }

    /* access modifiers changed from: package-private */
    public void setVerticalGridViewLayout(VerticalGridView listview) {
        listview.setItemAlignmentOffset(-this.mContainerListAlignTop);
        listview.setItemAlignmentOffsetPercent(-1.0f);
        listview.setWindowAlignmentOffset(0);
        listview.setWindowAlignmentOffsetPercent(-1.0f);
        listview.setWindowAlignment(0);
    }

    /* access modifiers changed from: protected */
    public void setupPresenter(Presenter rowPresenter) {
        if (rowPresenter instanceof FullWidthDetailsOverviewRowPresenter) {
            setupDetailsOverviewRowPresenter((FullWidthDetailsOverviewRowPresenter) rowPresenter);
        }
    }

    /* access modifiers changed from: protected */
    public void setupDetailsOverviewRowPresenter(FullWidthDetailsOverviewRowPresenter presenter) {
        ItemAlignmentFacet facet = new ItemAlignmentFacet();
        ItemAlignmentFacet.ItemAlignmentDef alignDef1 = new ItemAlignmentFacet.ItemAlignmentDef();
        alignDef1.setItemAlignmentViewId(C0364R.C0366id.details_frame);
        alignDef1.setItemAlignmentOffset(-getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_align_pos_for_actions));
        alignDef1.setItemAlignmentOffsetPercent(0.0f);
        ItemAlignmentFacet.ItemAlignmentDef alignDef2 = new ItemAlignmentFacet.ItemAlignmentDef();
        alignDef2.setItemAlignmentViewId(C0364R.C0366id.details_frame);
        alignDef2.setItemAlignmentFocusViewId(C0364R.C0366id.details_overview_description);
        alignDef2.setItemAlignmentOffset(-getResources().getDimensionPixelSize(C0364R.dimen.lb_details_v2_align_pos_for_description));
        alignDef2.setItemAlignmentOffsetPercent(0.0f);
        facet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{alignDef1, alignDef2});
        presenter.setFacet(ItemAlignmentFacet.class, facet);
    }

    /* access modifiers changed from: package-private */
    public VerticalGridView getVerticalGridView() {
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment == null) {
            return null;
        }
        return rowsSupportFragment.getVerticalGridView();
    }

    public RowsSupportFragment getRowsSupportFragment() {
        return this.mRowsSupportFragment;
    }

    private void setupChildFragmentLayout() {
        setVerticalGridViewLayout(this.mRowsSupportFragment.getVerticalGridView());
    }

    public void setSelectedPosition(int position) {
        setSelectedPosition(position, true);
    }

    public void setSelectedPosition(int position, boolean smooth) {
        SetSelectionRunnable setSelectionRunnable = this.mSetSelectionRunnable;
        setSelectionRunnable.mPosition = position;
        setSelectionRunnable.mSmooth = smooth;
        if (getView() != null && getView().getHandler() != null) {
            getView().getHandler().post(this.mSetSelectionRunnable);
        }
    }

    /* access modifiers changed from: package-private */
    public void switchToVideo() {
        Fragment fragment = this.mVideoSupportFragment;
        if (fragment == null || fragment.getView() == null) {
            this.mStateMachine.fireEvent(this.EVT_SWITCH_TO_VIDEO);
        } else {
            this.mVideoSupportFragment.getView().requestFocus();
        }
    }

    /* access modifiers changed from: package-private */
    public void switchToRows() {
        this.mPendingFocusOnVideo = false;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null && verticalGridView.getChildCount() > 0) {
            verticalGridView.requestFocus();
        }
    }

    /* access modifiers changed from: package-private */
    public final Fragment findOrCreateVideoSupportFragment() {
        Fragment fragment = this.mVideoSupportFragment;
        if (fragment != null) {
            return fragment;
        }
        Fragment fragment2 = getChildFragmentManager().findFragmentById(C0364R.C0366id.video_surface_container);
        if (fragment2 == null && this.mDetailsBackgroundController != null) {
            FragmentTransaction ft2 = getChildFragmentManager().beginTransaction();
            int i = C0364R.C0366id.video_surface_container;
            Fragment onCreateVideoSupportFragment = this.mDetailsBackgroundController.onCreateVideoSupportFragment();
            fragment2 = onCreateVideoSupportFragment;
            ft2.add(i, onCreateVideoSupportFragment);
            ft2.commit();
            if (this.mPendingFocusOnVideo) {
                getView().post(new Runnable() {
                    public void run() {
                        if (DetailsSupportFragment.this.getView() != null) {
                            DetailsSupportFragment.this.switchToVideo();
                        }
                        DetailsSupportFragment.this.mPendingFocusOnVideo = false;
                    }
                });
            }
        }
        this.mVideoSupportFragment = fragment2;
        return this.mVideoSupportFragment;
    }

    /* access modifiers changed from: package-private */
    public void onRowSelected(int selectedPosition, int selectedSubPosition) {
        ObjectAdapter adapter = getAdapter();
        RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
        if (rowsSupportFragment == null || rowsSupportFragment.getView() == null || !this.mRowsSupportFragment.getView().hasFocus() || this.mPendingFocusOnVideo || !(adapter == null || adapter.size() == 0 || (getVerticalGridView().getSelectedPosition() == 0 && getVerticalGridView().getSelectedSubPosition() == 0))) {
            showTitle(false);
        } else {
            showTitle(true);
        }
        if (adapter != null && adapter.size() > selectedPosition) {
            VerticalGridView gridView = getVerticalGridView();
            int count = gridView.getChildCount();
            if (count > 0) {
                this.mStateMachine.fireEvent(this.EVT_DETAILS_ROW_LOADED);
            }
            for (int i = 0; i < count; i++) {
                ItemBridgeAdapter.ViewHolder bridgeViewHolder = (ItemBridgeAdapter.ViewHolder) gridView.getChildViewHolder(gridView.getChildAt(i));
                RowPresenter rowPresenter = (RowPresenter) bridgeViewHolder.getPresenter();
                onSetRowStatus(rowPresenter, rowPresenter.getRowViewHolder(bridgeViewHolder.getViewHolder()), bridgeViewHolder.getAdapterPosition(), selectedPosition, selectedSubPosition);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @CallSuper
    public void onSafeStart() {
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController != null) {
            detailsSupportFragmentBackgroundController.onStart();
        }
    }

    /* access modifiers changed from: package-private */
    @CallSuper
    public void onReturnTransitionStart() {
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController != null && !detailsSupportFragmentBackgroundController.disableVideoParallax() && this.mVideoSupportFragment != null) {
            FragmentTransaction ft2 = getChildFragmentManager().beginTransaction();
            ft2.remove(this.mVideoSupportFragment);
            ft2.commit();
            this.mVideoSupportFragment = null;
        }
    }

    public void onStop() {
        DetailsSupportFragmentBackgroundController detailsSupportFragmentBackgroundController = this.mDetailsBackgroundController;
        if (detailsSupportFragmentBackgroundController != null) {
            detailsSupportFragmentBackgroundController.onStop();
        }
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onSetRowStatus(RowPresenter presenter, RowPresenter.ViewHolder viewHolder, int adapterPosition, int selectedPosition, int selectedSubPosition) {
        if (presenter instanceof FullWidthDetailsOverviewRowPresenter) {
            onSetDetailsOverviewRowStatus((FullWidthDetailsOverviewRowPresenter) presenter, (FullWidthDetailsOverviewRowPresenter.ViewHolder) viewHolder, adapterPosition, selectedPosition, selectedSubPosition);
        }
    }

    /* access modifiers changed from: protected */
    public void onSetDetailsOverviewRowStatus(FullWidthDetailsOverviewRowPresenter presenter, FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder, int adapterPosition, int selectedPosition, int selectedSubPosition) {
        if (selectedPosition > adapterPosition) {
            presenter.setState(viewHolder, 0);
        } else if (selectedPosition == adapterPosition && selectedSubPosition == 1) {
            presenter.setState(viewHolder, 0);
        } else if (selectedPosition == adapterPosition && selectedSubPosition == 0) {
            presenter.setState(viewHolder, 1);
        } else {
            presenter.setState(viewHolder, 2);
        }
    }

    public void onStart() {
        super.onStart();
        setupChildFragmentLayout();
        this.mStateMachine.fireEvent(this.EVT_ONSTART);
        DetailsParallax detailsParallax = this.mDetailsParallax;
        if (detailsParallax != null) {
            detailsParallax.setRecyclerView(this.mRowsSupportFragment.getVerticalGridView());
        }
        if (this.mPendingFocusOnVideo) {
            slideOutGridView();
        } else if (!getView().hasFocus()) {
            this.mRowsSupportFragment.getVerticalGridView().requestFocus();
        }
    }

    /* access modifiers changed from: protected */
    public Object createEntranceTransition() {
        return TransitionHelper.loadTransition(getContext(), C0364R.C0367transition.lb_details_enter_transition);
    }

    /* access modifiers changed from: protected */
    public void runEntranceTransition(Object entranceTransition) {
        TransitionHelper.runTransition(this.mSceneAfterEntranceTransition, entranceTransition);
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionEnd() {
        this.mRowsSupportFragment.onTransitionEnd();
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionPrepare() {
        this.mRowsSupportFragment.onTransitionPrepare();
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionStart() {
        this.mRowsSupportFragment.onTransitionStart();
    }

    public DetailsParallax getParallax() {
        if (this.mDetailsParallax == null) {
            this.mDetailsParallax = new DetailsParallax();
            RowsSupportFragment rowsSupportFragment = this.mRowsSupportFragment;
            if (!(rowsSupportFragment == null || rowsSupportFragment.getView() == null)) {
                this.mDetailsParallax.setRecyclerView(this.mRowsSupportFragment.getVerticalGridView());
            }
        }
        return this.mDetailsParallax;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundDrawable(Drawable drawable) {
        View view = this.mBackgroundView;
        if (view != null) {
            view.setBackground(drawable);
        }
        this.mBackgroundDrawable = drawable;
    }

    /* access modifiers changed from: package-private */
    public void setupDpadNavigation() {
        this.mRootView.setOnChildFocusListener(new BrowseFrameLayout.OnChildFocusListener() {
            public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
                return false;
            }

            public void onRequestChildFocus(View child, View focused) {
                if (child == DetailsSupportFragment.this.mRootView.getFocusedChild()) {
                    return;
                }
                if (child.getId() == C0364R.C0366id.details_fragment_root) {
                    if (!DetailsSupportFragment.this.mPendingFocusOnVideo) {
                        DetailsSupportFragment.this.slideInGridView();
                        DetailsSupportFragment.this.showTitle(true);
                    }
                } else if (child.getId() == C0364R.C0366id.video_surface_container) {
                    DetailsSupportFragment.this.slideOutGridView();
                    DetailsSupportFragment.this.showTitle(false);
                } else {
                    DetailsSupportFragment.this.showTitle(true);
                }
            }
        });
        this.mRootView.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() {
            public View onFocusSearch(View focused, int direction) {
                if (DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView() == null || !DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView().hasFocus()) {
                    if (DetailsSupportFragment.this.getTitleView() != null && DetailsSupportFragment.this.getTitleView().hasFocus() && direction == 130 && DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView() != null) {
                        return DetailsSupportFragment.this.mRowsSupportFragment.getVerticalGridView();
                    }
                } else if (direction == 33) {
                    if (DetailsSupportFragment.this.mDetailsBackgroundController != null && DetailsSupportFragment.this.mDetailsBackgroundController.canNavigateToVideoSupportFragment() && DetailsSupportFragment.this.mVideoSupportFragment != null && DetailsSupportFragment.this.mVideoSupportFragment.getView() != null) {
                        return DetailsSupportFragment.this.mVideoSupportFragment.getView();
                    }
                    if (DetailsSupportFragment.this.getTitleView() != null && DetailsSupportFragment.this.getTitleView().hasFocusable()) {
                        return DetailsSupportFragment.this.getTitleView();
                    }
                }
                return focused;
            }
        });
        this.mRootView.setOnDispatchKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (DetailsSupportFragment.this.mVideoSupportFragment == null || DetailsSupportFragment.this.mVideoSupportFragment.getView() == null || !DetailsSupportFragment.this.mVideoSupportFragment.getView().hasFocus()) {
                    return false;
                }
                if ((keyCode != 4 && keyCode != 111) || DetailsSupportFragment.this.getVerticalGridView().getChildCount() <= 0) {
                    return false;
                }
                DetailsSupportFragment.this.getVerticalGridView().requestFocus();
                return true;
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void slideOutGridView() {
        if (getVerticalGridView() != null) {
            getVerticalGridView().animateOut();
        }
    }

    /* access modifiers changed from: package-private */
    public void slideInGridView() {
        if (getVerticalGridView() != null) {
            getVerticalGridView().animateIn();
        }
    }

    static class WaitEnterTransitionTimeout implements Runnable {
        static final long WAIT_ENTERTRANSITION_START = 200;
        final WeakReference<DetailsSupportFragment> mRef;

        WaitEnterTransitionTimeout(DetailsSupportFragment f) {
            this.mRef = new WeakReference<>(f);
            f.getView().postDelayed(this, WAIT_ENTERTRANSITION_START);
        }

        public void run() {
            DetailsSupportFragment f = this.mRef.get();
            if (f != null) {
                f.mStateMachine.fireEvent(f.EVT_ENTER_TRANSIITON_DONE);
            }
        }
    }

    private class SetSelectionRunnable implements Runnable {
        int mPosition;
        boolean mSmooth = true;

        SetSelectionRunnable() {
        }

        public void run() {
            if (DetailsSupportFragment.this.mRowsSupportFragment != null) {
                DetailsSupportFragment.this.mRowsSupportFragment.setSelectedPosition(this.mPosition, this.mSmooth);
            }
        }
    }
}
