package androidx.leanback.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.leanback.C0364R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.InvisibleRowPresenter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.PageRow;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.ScaleFrameLayout;
import androidx.leanback.widget.VerticalGridView;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class BrowseFragment extends BaseFragment {
    public static final int HEADERS_DISABLED = 3;
    public static final int HEADERS_ENABLED = 1;
    public static final int HEADERS_HIDDEN = 2;
    static final boolean DEBUG = false;
    static final String HEADER_SHOW = "headerShow";
    static final String HEADER_STACK_INDEX = "headerStackIndex";
    static final String TAG = "BrowseFragment";
    private static final String ARG_HEADERS_STATE = (BrowseFragment.class.getCanonicalName() + ".headersState");
    private static final String ARG_TITLE = (BrowseFragment.class.getCanonicalName() + ".title");
    private static final String CURRENT_SELECTED_POSITION = "currentSelectedPosition";
    private static final String IS_PAGE_ROW = "isPageRow";
    private static final String LB_HEADERS_BACKSTACK = "lbHeadersBackStack_";
    final StateMachine.Event EVT_HEADER_VIEW_CREATED = new StateMachine.Event("headerFragmentViewCreated");
    final StateMachine.Event EVT_MAIN_FRAGMENT_VIEW_CREATED = new StateMachine.Event("mainFragmentViewCreated");
    final StateMachine.Event EVT_SCREEN_DATA_READY = new StateMachine.Event("screenDataReady");
    private final SetSelectionRunnable mSetSelectionRunnable = new SetSelectionRunnable();
    BackStackListener mBackStackChangedListener;
    BrowseFrameLayout mBrowseFrame;
    BrowseTransitionListener mBrowseTransitionListener;
    boolean mCanShowHeaders = true;
    OnItemViewSelectedListener mExternalOnItemViewSelectedListener;
    boolean mHeadersBackStackEnabled = true;
    HeadersFragment mHeadersFragment;
    Object mHeadersTransition;
    boolean mIsPageRow;
    Fragment mMainFragment;
    MainFragmentAdapter mMainFragmentAdapter;
    ListRowDataAdapter mMainFragmentListRowDataAdapter;
    MainFragmentRowsAdapter mMainFragmentRowsAdapter;
    Object mPageRow;
    Object mSceneWithHeaders;
    Object mSceneWithoutHeaders;
    boolean mShowingHeaders = true;
    boolean mStopped = true;
    private final RecyclerView.OnScrollListener mWaitScrollFinishAndCommitMainFragment = new RecyclerView.OnScrollListener() {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 0) {
                recyclerView.removeOnScrollListener(this);
                if (!BrowseFragment.this.mStopped) {
                    BrowseFragment.this.commitMainFragment();
                }
            }
        }
    };
    String mWithHeadersBackStackName;
    private ObjectAdapter mAdapter;
    private final BrowseFrameLayout.OnFocusSearchListener mOnFocusSearchListener = new BrowseFrameLayout.OnFocusSearchListener() {
        public View onFocusSearch(View focused, int direction) {
            if (BrowseFragment.this.mCanShowHeaders && BrowseFragment.this.isInHeadersTransition()) {
                return focused;
            }
            if (BrowseFragment.this.getTitleView() != null && focused != BrowseFragment.this.getTitleView() && direction == 33) {
                return BrowseFragment.this.getTitleView();
            }
            if (BrowseFragment.this.getTitleView() != null && BrowseFragment.this.getTitleView().hasFocus() && direction == 130) {
                return (!BrowseFragment.this.mCanShowHeaders || !BrowseFragment.this.mShowingHeaders) ? BrowseFragment.this.mMainFragment.getView() : BrowseFragment.this.mHeadersFragment.getVerticalGridView();
            }
            boolean z = true;
            if (ViewCompat.getLayoutDirection(focused) != 1) {
                z = false;
            }
            boolean isRtl = z;
            int towardEnd = 66;
            int towardStart = isRtl ? 66 : 17;
            if (isRtl) {
                towardEnd = 17;
            }
            if (!BrowseFragment.this.mCanShowHeaders || direction != towardStart) {
                if (direction == towardEnd) {
                    if (BrowseFragment.this.isVerticalScrolling() || BrowseFragment.this.mMainFragment == null || BrowseFragment.this.mMainFragment.getView() == null) {
                        return focused;
                    }
                    return BrowseFragment.this.mMainFragment.getView();
                } else if (direction != 130 || !BrowseFragment.this.mShowingHeaders) {
                    return null;
                } else {
                    return focused;
                }
            } else if (BrowseFragment.this.isVerticalScrolling() || BrowseFragment.this.mShowingHeaders || !BrowseFragment.this.isHeadersDataReady()) {
                return focused;
            } else {
                return BrowseFragment.this.mHeadersFragment.getVerticalGridView();
            }
        }
    };
    private PresenterSelector mAdapterPresenter;
    private int mBrandColor = 0;
    private boolean mBrandColorSet;
    private int mContainerListAlignTop;
    private int mContainerListMarginStart;
    final StateMachine.State STATE_SET_ENTRANCE_START_STATE = new StateMachine.State("SET_ENTRANCE_START_STATE") {
        public void run() {
            BrowseFragment.this.setEntranceTransitionStartState();
        }
    };
    private PresenterSelector mHeaderPresenterSelector;
    private HeadersFragment.OnHeaderViewSelectedListener mHeaderViewSelectedListener = new HeadersFragment.OnHeaderViewSelectedListener() {
        public void onHeaderSelected(RowHeaderPresenter.ViewHolder viewHolder, Row row) {
            int position = BrowseFragment.this.mHeadersFragment.getSelectedPosition();
            if (BrowseFragment.this.mShowingHeaders) {
                BrowseFragment.this.onRowSelected(position);
            }
        }
    };
    private int mHeadersState = 1;
    private MainFragmentAdapterRegistry mMainFragmentAdapterRegistry = new MainFragmentAdapterRegistry();
    private boolean mMainFragmentScaleEnabled = true;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    private float mScaleFactor;
    private ScaleFrameLayout mScaleFrameLayout;
    private Object mSceneAfterEntranceTransition;
    private int mSelectedPosition = -1;
    private HeadersFragment.OnHeaderClickedListener mHeaderClickedListener = new HeadersFragment.OnHeaderClickedListener() {
        public void onHeaderClicked(RowHeaderPresenter.ViewHolder viewHolder, Row row) {
            if (BrowseFragment.this.mCanShowHeaders && BrowseFragment.this.mShowingHeaders && !BrowseFragment.this.isInHeadersTransition() && BrowseFragment.this.mMainFragment != null && BrowseFragment.this.mMainFragment.getView() != null) {
                BrowseFragment.this.startHeadersTransitionInternal(false);
                BrowseFragment.this.mMainFragment.getView().requestFocus();
            }
        }
    };
    private final BrowseFrameLayout.OnChildFocusListener mOnChildFocusListener = new BrowseFrameLayout.OnChildFocusListener() {
        public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
            if (BrowseFragment.this.getChildFragmentManager().isDestroyed()) {
                return true;
            }
            if (BrowseFragment.this.mCanShowHeaders && BrowseFragment.this.mShowingHeaders && BrowseFragment.this.mHeadersFragment != null && BrowseFragment.this.mHeadersFragment.getView() != null && BrowseFragment.this.mHeadersFragment.getView().requestFocus(direction, previouslyFocusedRect)) {
                return true;
            }
            if (BrowseFragment.this.mMainFragment != null && BrowseFragment.this.mMainFragment.getView() != null && BrowseFragment.this.mMainFragment.getView().requestFocus(direction, previouslyFocusedRect)) {
                return true;
            }
            if (BrowseFragment.this.getTitleView() == null || !BrowseFragment.this.getTitleView().requestFocus(direction, previouslyFocusedRect)) {
                return false;
            }
            return true;
        }

        public void onRequestChildFocus(View child, View focused) {
            if (!BrowseFragment.this.getChildFragmentManager().isDestroyed() && BrowseFragment.this.mCanShowHeaders && !BrowseFragment.this.isInHeadersTransition()) {
                int childId = child.getId();
                if (childId == C0364R.C0366id.browse_container_dock && BrowseFragment.this.mShowingHeaders) {
                    BrowseFragment.this.startHeadersTransitionInternal(false);
                } else if (childId == C0364R.C0366id.browse_headers_dock && !BrowseFragment.this.mShowingHeaders) {
                    BrowseFragment.this.startHeadersTransitionInternal(true);
                }
            }
        }
    };

    public static Bundle createArgs(Bundle args, String title, int headersState) {
        if (args == null) {
            args = new Bundle();
        }
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_HEADERS_STATE, headersState);
        return args;
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineStates() {
        super.createStateMachineStates();
        this.mStateMachine.addState(this.STATE_SET_ENTRANCE_START_STATE);
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineTransitions() {
        super.createStateMachineTransitions();
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_SET_ENTRANCE_START_STATE, this.EVT_HEADER_VIEW_CREATED);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW, this.EVT_MAIN_FRAGMENT_VIEW_CREATED);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_PERFORM, this.EVT_SCREEN_DATA_READY);
    }

    private boolean createMainFragment(ObjectAdapter adapter, int position) {
        Object item = null;
        boolean swap = true;
        if (this.mCanShowHeaders) {
            if (adapter == null || adapter.size() == 0) {
                return false;
            }
            if (position < 0) {
                position = 0;
            } else if (position >= adapter.size()) {
                throw new IllegalArgumentException(String.format("Invalid position %d requested", Integer.valueOf(position)));
            }
            item = adapter.get(position);
        }
        boolean oldIsPageRow = this.mIsPageRow;
        Object oldPageRow = this.mPageRow;
        this.mIsPageRow = this.mCanShowHeaders && (item instanceof PageRow);
        this.mPageRow = this.mIsPageRow ? item : null;
        if (this.mMainFragment == null) {
            swap = true;
        } else if (!oldIsPageRow) {
            swap = this.mIsPageRow;
        } else if (!this.mIsPageRow) {
            swap = true;
        } else if (oldPageRow == null) {
            swap = false;
        } else if (oldPageRow == this.mPageRow) {
            swap = false;
        }
        if (swap) {
            this.mMainFragment = this.mMainFragmentAdapterRegistry.createFragment(item);
            if (this.mMainFragment instanceof MainFragmentAdapterProvider) {
                setMainFragmentAdapter();
            } else {
                throw new IllegalArgumentException("Fragment must implement MainFragmentAdapterProvider");
            }
        }
        return swap;
    }

    /* access modifiers changed from: package-private */
    public void setMainFragmentAdapter() {
        this.mMainFragmentAdapter = ((MainFragmentAdapterProvider) this.mMainFragment).getMainFragmentAdapter();
        this.mMainFragmentAdapter.setFragmentHost(new FragmentHostImpl());
        if (!this.mIsPageRow) {
            Fragment fragment = this.mMainFragment;
            if (fragment instanceof MainFragmentRowsAdapterProvider) {
                setMainFragmentRowsAdapter(((MainFragmentRowsAdapterProvider) fragment).getMainFragmentRowsAdapter());
            } else {
                setMainFragmentRowsAdapter(null);
            }
            this.mIsPageRow = this.mMainFragmentRowsAdapter == null;
            return;
        }
        setMainFragmentRowsAdapter(null);
    }

    @ColorInt
    public int getBrandColor() {
        return this.mBrandColor;
    }

    public void setBrandColor(@ColorInt int color) {
        this.mBrandColor = color;
        this.mBrandColorSet = true;
        HeadersFragment headersFragment = this.mHeadersFragment;
        if (headersFragment != null) {
            headersFragment.setBackgroundColor(this.mBrandColor);
        }
    }

    private void updateWrapperPresenter() {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter == null) {
            this.mAdapterPresenter = null;
            return;
        }
        final PresenterSelector adapterPresenter = objectAdapter.getPresenterSelector();
        if (adapterPresenter == null) {
            throw new IllegalArgumentException("Adapter.getPresenterSelector() is null");
        } else if (adapterPresenter != this.mAdapterPresenter) {
            this.mAdapterPresenter = adapterPresenter;
            Presenter[] presenters = adapterPresenter.getPresenters();
            final Presenter invisibleRowPresenter = new InvisibleRowPresenter();
            final Presenter[] allPresenters = new Presenter[(presenters.length + 1)];
            System.arraycopy(allPresenters, 0, presenters, 0, presenters.length);
            allPresenters[allPresenters.length - 1] = invisibleRowPresenter;
            this.mAdapter.setPresenterSelector(new PresenterSelector() {
                public Presenter getPresenter(Object item) {
                    if (((Row) item).isRenderedAsRowView()) {
                        return adapterPresenter.getPresenter(item);
                    }
                    return invisibleRowPresenter;
                }

                public Presenter[] getPresenters() {
                    return allPresenters;
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void setMainFragmentRowsAdapter(MainFragmentRowsAdapter mainFragmentRowsAdapter) {
        MainFragmentRowsAdapter mainFragmentRowsAdapter2 = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter != mainFragmentRowsAdapter2) {
            if (mainFragmentRowsAdapter2 != null) {
                mainFragmentRowsAdapter2.setAdapter(null);
            }
            this.mMainFragmentRowsAdapter = mainFragmentRowsAdapter;
            MainFragmentRowsAdapter mainFragmentRowsAdapter3 = this.mMainFragmentRowsAdapter;
            if (mainFragmentRowsAdapter3 != null) {
                mainFragmentRowsAdapter3.setOnItemViewSelectedListener(new MainFragmentItemViewSelectedListener(mainFragmentRowsAdapter3));
                this.mMainFragmentRowsAdapter.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
            }
            updateMainFragmentRowsAdapter();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateMainFragmentRowsAdapter() {
        ListRowDataAdapter listRowDataAdapter = this.mMainFragmentListRowDataAdapter;
        ListRowDataAdapter listRowDataAdapter2 = null;
        if (listRowDataAdapter != null) {
            listRowDataAdapter.detach();
            this.mMainFragmentListRowDataAdapter = null;
        }
        if (this.mMainFragmentRowsAdapter != null) {
            ObjectAdapter objectAdapter = this.mAdapter;
            if (objectAdapter != null) {
                listRowDataAdapter2 = new ListRowDataAdapter(objectAdapter);
            }
            this.mMainFragmentListRowDataAdapter = listRowDataAdapter2;
            this.mMainFragmentRowsAdapter.setAdapter(this.mMainFragmentListRowDataAdapter);
        }
    }

    public final MainFragmentAdapterRegistry getMainFragmentRegistry() {
        return this.mMainFragmentAdapterRegistry;
    }

    public ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ObjectAdapter adapter) {
        this.mAdapter = adapter;
        updateWrapperPresenter();
        if (getView() != null) {
            updateMainFragmentRowsAdapter();
            this.mHeadersFragment.setAdapter(this.mAdapter);
        }
    }

    public OnItemViewSelectedListener getOnItemViewSelectedListener() {
        return this.mExternalOnItemViewSelectedListener;
    }

    public void setOnItemViewSelectedListener(OnItemViewSelectedListener listener) {
        this.mExternalOnItemViewSelectedListener = listener;
    }

    public RowsFragment getRowsFragment() {
        Fragment fragment = this.mMainFragment;
        if (fragment instanceof RowsFragment) {
            return (RowsFragment) fragment;
        }
        return null;
    }

    public Fragment getMainFragment() {
        return this.mMainFragment;
    }

    public HeadersFragment getHeadersFragment() {
        return this.mHeadersFragment;
    }

    public OnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public void setOnItemViewClickedListener(OnItemViewClickedListener listener) {
        this.mOnItemViewClickedListener = listener;
        MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter != null) {
            mainFragmentRowsAdapter.setOnItemViewClickedListener(listener);
        }
    }

    public void startHeadersTransition(boolean withHeaders) {
        if (!this.mCanShowHeaders) {
            throw new IllegalStateException("Cannot start headers transition");
        } else if (!isInHeadersTransition() && this.mShowingHeaders != withHeaders) {
            startHeadersTransitionInternal(withHeaders);
        }
    }

    public boolean isInHeadersTransition() {
        return this.mHeadersTransition != null;
    }

    public boolean isShowingHeaders() {
        return this.mShowingHeaders;
    }

    public void setBrowseTransitionListener(BrowseTransitionListener listener) {
        this.mBrowseTransitionListener = listener;
    }

    @Deprecated
    public void enableRowScaling(boolean enable) {
        enableMainFragmentScaling(enable);
    }

    public void enableMainFragmentScaling(boolean enable) {
        this.mMainFragmentScaleEnabled = enable;
    }

    /* access modifiers changed from: package-private */
    public void startHeadersTransitionInternal(final boolean withHeaders) {
        if (!getFragmentManager().isDestroyed() && isHeadersDataReady()) {
            this.mShowingHeaders = withHeaders;
            this.mMainFragmentAdapter.onTransitionPrepare();
            this.mMainFragmentAdapter.onTransitionStart();
            onExpandTransitionStart(!withHeaders, new Runnable() {
                public void run() {
                    BrowseFragment.this.mHeadersFragment.onTransitionPrepare();
                    BrowseFragment.this.mHeadersFragment.onTransitionStart();
                    BrowseFragment.this.createHeadersTransition();
                    if (BrowseFragment.this.mBrowseTransitionListener != null) {
                        BrowseFragment.this.mBrowseTransitionListener.onHeadersTransitionStart(withHeaders);
                    }
                    TransitionHelper.runTransition(withHeaders ? BrowseFragment.this.mSceneWithHeaders : BrowseFragment.this.mSceneWithoutHeaders, BrowseFragment.this.mHeadersTransition);
                    if (!BrowseFragment.this.mHeadersBackStackEnabled) {
                        return;
                    }
                    if (!withHeaders) {
                        BrowseFragment.this.getFragmentManager().beginTransaction().addToBackStack(BrowseFragment.this.mWithHeadersBackStackName).commit();
                        return;
                    }
                    int index = BrowseFragment.this.mBackStackChangedListener.mIndexOfHeadersBackStack;
                    if (index >= 0) {
                        BrowseFragment.this.getFragmentManager().popBackStackImmediate(BrowseFragment.this.getFragmentManager().getBackStackEntryAt(index).getId(), 1);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isVerticalScrolling() {
        return this.mHeadersFragment.isScrolling() || this.mMainFragmentAdapter.isScrolling();
    }

    /* access modifiers changed from: package-private */
    public final boolean isHeadersDataReady() {
        ObjectAdapter objectAdapter = this.mAdapter;
        return (objectAdapter == null || objectAdapter.size() == 0) ? false : true;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_SELECTED_POSITION, this.mSelectedPosition);
        outState.putBoolean(IS_PAGE_ROW, this.mIsPageRow);
        BackStackListener backStackListener = this.mBackStackChangedListener;
        if (backStackListener != null) {
            backStackListener.save(outState);
        } else {
            outState.putBoolean(HEADER_SHOW, this.mShowingHeaders);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = FragmentUtil.getContext(this);
        TypedArray ta = context.obtainStyledAttributes(C0364R.styleable.LeanbackTheme);
        this.mContainerListMarginStart = (int) ta.getDimension(C0364R.styleable.LeanbackTheme_browseRowsMarginStart, (float) context.getResources().getDimensionPixelSize(C0364R.dimen.lb_browse_rows_margin_start));
        this.mContainerListAlignTop = (int) ta.getDimension(C0364R.styleable.LeanbackTheme_browseRowsMarginTop, (float) context.getResources().getDimensionPixelSize(C0364R.dimen.lb_browse_rows_margin_top));
        ta.recycle();
        readArguments(getArguments());
        if (this.mCanShowHeaders) {
            if (this.mHeadersBackStackEnabled) {
                this.mWithHeadersBackStackName = LB_HEADERS_BACKSTACK + this;
                this.mBackStackChangedListener = new BackStackListener();
                getFragmentManager().addOnBackStackChangedListener(this.mBackStackChangedListener);
                this.mBackStackChangedListener.load(savedInstanceState);
            } else if (savedInstanceState != null) {
                this.mShowingHeaders = savedInstanceState.getBoolean(HEADER_SHOW);
            }
        }
        this.mScaleFactor = getResources().getFraction(C0364R.fraction.lb_browse_rows_scale, 1, 1);
    }

    public void onDestroyView() {
        setMainFragmentRowsAdapter(null);
        this.mPageRow = null;
        this.mMainFragmentAdapter = null;
        this.mMainFragment = null;
        this.mHeadersFragment = null;
        super.onDestroyView();
    }

    public void onDestroy() {
        if (this.mBackStackChangedListener != null) {
            getFragmentManager().removeOnBackStackChangedListener(this.mBackStackChangedListener);
        }
        super.onDestroy();
    }

    public HeadersFragment onCreateHeadersFragment() {
        return new HeadersFragment();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getChildFragmentManager().findFragmentById(C0364R.C0366id.scale_frame) == null) {
            this.mHeadersFragment = onCreateHeadersFragment();
            createMainFragment(this.mAdapter, this.mSelectedPosition);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction().replace(C0364R.C0366id.browse_headers_dock, this.mHeadersFragment);
            if (this.mMainFragment != null) {
                ft.replace(C0364R.C0366id.scale_frame, this.mMainFragment);
            } else {
                this.mMainFragmentAdapter = new MainFragmentAdapter(null);
                this.mMainFragmentAdapter.setFragmentHost(new FragmentHostImpl());
            }
            ft.commit();
        } else {
            this.mHeadersFragment = (HeadersFragment) getChildFragmentManager().findFragmentById(C0364R.C0366id.browse_headers_dock);
            this.mMainFragment = getChildFragmentManager().findFragmentById(C0364R.C0366id.scale_frame);
            this.mIsPageRow = savedInstanceState != null && savedInstanceState.getBoolean(IS_PAGE_ROW, false);
            this.mSelectedPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_SELECTED_POSITION, 0) : 0;
            setMainFragmentAdapter();
        }
        this.mHeadersFragment.setHeadersGone(true ^ this.mCanShowHeaders);
        PresenterSelector presenterSelector = this.mHeaderPresenterSelector;
        if (presenterSelector != null) {
            this.mHeadersFragment.setPresenterSelector(presenterSelector);
        }
        this.mHeadersFragment.setAdapter(this.mAdapter);
        this.mHeadersFragment.setOnHeaderViewSelectedListener(this.mHeaderViewSelectedListener);
        this.mHeadersFragment.setOnHeaderClickedListener(this.mHeaderClickedListener);
        View root = inflater.inflate(C0364R.layout.lb_browse_fragment, container, false);
        getProgressBarManager().setRootView((ViewGroup) root);
        this.mBrowseFrame = (BrowseFrameLayout) root.findViewById(C0364R.C0366id.browse_frame);
        this.mBrowseFrame.setOnChildFocusListener(this.mOnChildFocusListener);
        this.mBrowseFrame.setOnFocusSearchListener(this.mOnFocusSearchListener);
        installTitleView(inflater, this.mBrowseFrame, savedInstanceState);
        this.mScaleFrameLayout = (ScaleFrameLayout) root.findViewById(C0364R.C0366id.scale_frame);
        this.mScaleFrameLayout.setPivotX(0.0f);
        this.mScaleFrameLayout.setPivotY((float) this.mContainerListAlignTop);
        if (this.mBrandColorSet) {
            this.mHeadersFragment.setBackgroundColor(this.mBrandColor);
        }
        this.mSceneWithHeaders = TransitionHelper.createScene(this.mBrowseFrame, new Runnable() {
            public void run() {
                BrowseFragment.this.showHeaders(true);
            }
        });
        this.mSceneWithoutHeaders = TransitionHelper.createScene(this.mBrowseFrame, new Runnable() {
            public void run() {
                BrowseFragment.this.showHeaders(false);
            }
        });
        this.mSceneAfterEntranceTransition = TransitionHelper.createScene(this.mBrowseFrame, new Runnable() {
            public void run() {
                BrowseFragment.this.setEntranceTransitionEndState();
            }
        });
        return root;
    }

    /* access modifiers changed from: package-private */
    public void createHeadersTransition() {
        this.mHeadersTransition = TransitionHelper.loadTransition(FragmentUtil.getContext(this), this.mShowingHeaders ? C0364R.C0367transition.lb_browse_headers_in : C0364R.C0367transition.lb_browse_headers_out);
        TransitionHelper.addTransitionListener(this.mHeadersTransition, new TransitionListener() {
            public void onTransitionStart(Object transition) {
            }

            public void onTransitionEnd(Object transition) {
                VerticalGridView headerGridView;
                View mainFragmentView;
                BrowseFragment browseFragment = BrowseFragment.this;
                browseFragment.mHeadersTransition = null;
                if (browseFragment.mMainFragmentAdapter != null) {
                    BrowseFragment.this.mMainFragmentAdapter.onTransitionEnd();
                    if (!BrowseFragment.this.mShowingHeaders && BrowseFragment.this.mMainFragment != null && (mainFragmentView = BrowseFragment.this.mMainFragment.getView()) != null && !mainFragmentView.hasFocus()) {
                        mainFragmentView.requestFocus();
                    }
                }
                if (BrowseFragment.this.mHeadersFragment != null) {
                    BrowseFragment.this.mHeadersFragment.onTransitionEnd();
                    if (BrowseFragment.this.mShowingHeaders && (headerGridView = BrowseFragment.this.mHeadersFragment.getVerticalGridView()) != null && !headerGridView.hasFocus()) {
                        headerGridView.requestFocus();
                    }
                }
                BrowseFragment.this.updateTitleViewVisibility();
                if (BrowseFragment.this.mBrowseTransitionListener != null) {
                    BrowseFragment.this.mBrowseTransitionListener.onHeadersTransitionStop(BrowseFragment.this.mShowingHeaders);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void updateTitleViewVisibility() {
        boolean showBranding;
        MainFragmentAdapter mainFragmentAdapter;
        boolean showTitleView;
        MainFragmentAdapter mainFragmentAdapter2;
        if (!this.mShowingHeaders) {
            if (!this.mIsPageRow || (mainFragmentAdapter2 = this.mMainFragmentAdapter) == null) {
                showTitleView = isFirstRowWithContent(this.mSelectedPosition);
            } else {
                showTitleView = mainFragmentAdapter2.mFragmentHost.mShowTitleView;
            }
            if (showTitleView) {
                showTitle(6);
            } else {
                showTitle(false);
            }
        } else {
            if (!this.mIsPageRow || (mainFragmentAdapter = this.mMainFragmentAdapter) == null) {
                showBranding = isFirstRowWithContent(this.mSelectedPosition);
            } else {
                showBranding = mainFragmentAdapter.mFragmentHost.mShowTitleView;
            }
            boolean showSearch = isFirstRowWithContentOrPageRow(this.mSelectedPosition);
            int flags = 0;
            if (showBranding) {
                flags = 0 | 2;
            }
            if (showSearch) {
                flags |= 4;
            }
            if (flags != 0) {
                showTitle(flags);
            } else {
                showTitle(false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isFirstRowWithContentOrPageRow(int rowPosition) {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter == null || objectAdapter.size() == 0) {
            return true;
        }
        int i = 0;
        while (i < this.mAdapter.size()) {
            Row row = (Row) this.mAdapter.get(i);
            if (!row.isRenderedAsRowView() && !(row instanceof PageRow)) {
                i++;
            } else if (rowPosition == i) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean isFirstRowWithContent(int rowPosition) {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter == null || objectAdapter.size() == 0) {
            return true;
        }
        int i = 0;
        while (i < this.mAdapter.size()) {
            if (!((Row) this.mAdapter.get(i)).isRenderedAsRowView()) {
                i++;
            } else if (rowPosition == i) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public void setHeaderPresenterSelector(PresenterSelector headerPresenterSelector) {
        this.mHeaderPresenterSelector = headerPresenterSelector;
        HeadersFragment headersFragment = this.mHeadersFragment;
        if (headersFragment != null) {
            headersFragment.setPresenterSelector(this.mHeaderPresenterSelector);
        }
    }

    private void setHeadersOnScreen(boolean onScreen) {
        View containerList = this.mHeadersFragment.getView();
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) containerList.getLayoutParams();
        lp.setMarginStart(onScreen ? 0 : -this.mContainerListMarginStart);
        containerList.setLayoutParams(lp);
    }

    /* access modifiers changed from: package-private */
    public void showHeaders(boolean show) {
        this.mHeadersFragment.setHeadersEnabled(show);
        setHeadersOnScreen(show);
        expandMainFragment(!show);
    }

    private void expandMainFragment(boolean expand) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mScaleFrameLayout.getLayoutParams();
        params.setMarginStart(!expand ? this.mContainerListMarginStart : 0);
        this.mScaleFrameLayout.setLayoutParams(params);
        this.mMainFragmentAdapter.setExpand(expand);
        setMainFragmentAlignment();
        float scaleFactor = (expand || !this.mMainFragmentScaleEnabled || !this.mMainFragmentAdapter.isScalingEnabled()) ? 1.0f : this.mScaleFactor;
        this.mScaleFrameLayout.setLayoutScaleY(scaleFactor);
        this.mScaleFrameLayout.setChildScale(scaleFactor);
    }

    /* access modifiers changed from: package-private */
    public void onRowSelected(int position) {
        this.mSetSelectionRunnable.post(position, 0, true);
    }

    /* access modifiers changed from: package-private */
    public void setSelection(int position, boolean smooth) {
        if (position != -1) {
            this.mSelectedPosition = position;
            HeadersFragment headersFragment = this.mHeadersFragment;
            if (headersFragment != null && this.mMainFragmentAdapter != null) {
                headersFragment.setSelectedPosition(position, smooth);
                replaceMainFragment(position);
                MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
                if (mainFragmentRowsAdapter != null) {
                    mainFragmentRowsAdapter.setSelectedPosition(position, smooth);
                }
                updateTitleViewVisibility();
            }
        }
    }

    private void replaceMainFragment(int position) {
        if (createMainFragment(this.mAdapter, position)) {
            swapToMainFragment();
            expandMainFragment(!this.mCanShowHeaders || !this.mShowingHeaders);
        }
    }

    /* access modifiers changed from: package-private */
    public final void commitMainFragment() {
        FragmentManager fm = getChildFragmentManager();
        if (fm.findFragmentById(C0364R.C0366id.scale_frame) != this.mMainFragment) {
            fm.beginTransaction().replace(C0364R.C0366id.scale_frame, this.mMainFragment).commit();
        }
    }

    private void swapToMainFragment() {
        if (!this.mStopped) {
            VerticalGridView gridView = this.mHeadersFragment.getVerticalGridView();
            if (!isShowingHeaders() || gridView == null || gridView.getScrollState() == 0) {
                commitMainFragment();
                return;
            }
            getChildFragmentManager().beginTransaction().replace(C0364R.C0366id.scale_frame, new Fragment()).commit();
            gridView.removeOnScrollListener(this.mWaitScrollFinishAndCommitMainFragment);
            gridView.addOnScrollListener(this.mWaitScrollFinishAndCommitMainFragment);
        }
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        setSelectedPosition(position, true);
    }

    public RowPresenter.ViewHolder getSelectedRowViewHolder() {
        MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
        if (mainFragmentRowsAdapter == null) {
            return null;
        }
        return this.mMainFragmentRowsAdapter.findRowViewHolderByPosition(mainFragmentRowsAdapter.getSelectedPosition());
    }

    public void setSelectedPosition(int position, boolean smooth) {
        this.mSetSelectionRunnable.post(position, 1, smooth);
    }

    public void setSelectedPosition(int rowPosition, boolean smooth, Presenter.ViewHolderTask rowHolderTask) {
        if (this.mMainFragmentAdapterRegistry != null) {
            if (rowHolderTask != null) {
                startHeadersTransition(false);
            }
            MainFragmentRowsAdapter mainFragmentRowsAdapter = this.mMainFragmentRowsAdapter;
            if (mainFragmentRowsAdapter != null) {
                mainFragmentRowsAdapter.setSelectedPosition(rowPosition, smooth, rowHolderTask);
            }
        }
    }

    public void onStart() {
        Fragment fragment;
        HeadersFragment headersFragment;
        super.onStart();
        this.mHeadersFragment.setAlignment(this.mContainerListAlignTop);
        setMainFragmentAlignment();
        if (this.mCanShowHeaders && this.mShowingHeaders && (headersFragment = this.mHeadersFragment) != null && headersFragment.getView() != null) {
            this.mHeadersFragment.getView().requestFocus();
        } else if (!((this.mCanShowHeaders && this.mShowingHeaders) || (fragment = this.mMainFragment) == null || fragment.getView() == null)) {
            this.mMainFragment.getView().requestFocus();
        }
        if (this.mCanShowHeaders) {
            showHeaders(this.mShowingHeaders);
        }
        this.mStateMachine.fireEvent(this.EVT_HEADER_VIEW_CREATED);
        this.mStopped = false;
        commitMainFragment();
        this.mSetSelectionRunnable.start();
    }

    public void onStop() {
        this.mStopped = true;
        this.mSetSelectionRunnable.stop();
        super.onStop();
    }

    private void onExpandTransitionStart(boolean expand, Runnable callback) {
        if (expand) {
            callback.run();
        } else {
            new ExpandPreLayout(callback, this.mMainFragmentAdapter, getView()).execute();
        }
    }

    private void setMainFragmentAlignment() {
        int alignOffset = this.mContainerListAlignTop;
        if (this.mMainFragmentScaleEnabled && this.mMainFragmentAdapter.isScalingEnabled() && this.mShowingHeaders) {
            alignOffset = (int) ((((float) alignOffset) / this.mScaleFactor) + 0.5f);
        }
        this.mMainFragmentAdapter.setAlignment(alignOffset);
    }

    public final boolean isHeadersTransitionOnBackEnabled() {
        return this.mHeadersBackStackEnabled;
    }

    public final void setHeadersTransitionOnBackEnabled(boolean headersBackStackEnabled) {
        this.mHeadersBackStackEnabled = headersBackStackEnabled;
    }

    private void readArguments(Bundle args) {
        if (args != null) {
            if (args.containsKey(ARG_TITLE)) {
                setTitle(args.getString(ARG_TITLE));
            }
            if (args.containsKey(ARG_HEADERS_STATE)) {
                setHeadersState(args.getInt(ARG_HEADERS_STATE));
            }
        }
    }

    public int getHeadersState() {
        return this.mHeadersState;
    }

    public void setHeadersState(int headersState) {
        if (headersState < 1 || headersState > 3) {
            throw new IllegalArgumentException("Invalid headers state: " + headersState);
        } else if (headersState != this.mHeadersState) {
            this.mHeadersState = headersState;
            if (headersState == 1) {
                this.mCanShowHeaders = true;
                this.mShowingHeaders = true;
            } else if (headersState == 2) {
                this.mCanShowHeaders = true;
                this.mShowingHeaders = false;
            } else if (headersState != 3) {
                Log.w(TAG, "Unknown headers state: " + headersState);
            } else {
                this.mCanShowHeaders = false;
                this.mShowingHeaders = false;
            }
            HeadersFragment headersFragment = this.mHeadersFragment;
            if (headersFragment != null) {
                headersFragment.setHeadersGone(true ^ this.mCanShowHeaders);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object createEntranceTransition() {
        return TransitionHelper.loadTransition(FragmentUtil.getContext(this), C0364R.C0367transition.lb_browse_entrance_transition);
    }

    /* access modifiers changed from: protected */
    public void runEntranceTransition(Object entranceTransition) {
        TransitionHelper.runTransition(this.mSceneAfterEntranceTransition, entranceTransition);
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionPrepare() {
        this.mHeadersFragment.onTransitionPrepare();
        this.mMainFragmentAdapter.setEntranceTransitionState(false);
        this.mMainFragmentAdapter.onTransitionPrepare();
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionStart() {
        this.mHeadersFragment.onTransitionStart();
        this.mMainFragmentAdapter.onTransitionStart();
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionEnd() {
        MainFragmentAdapter mainFragmentAdapter = this.mMainFragmentAdapter;
        if (mainFragmentAdapter != null) {
            mainFragmentAdapter.onTransitionEnd();
        }
        HeadersFragment headersFragment = this.mHeadersFragment;
        if (headersFragment != null) {
            headersFragment.onTransitionEnd();
        }
    }

    /* access modifiers changed from: package-private */
    public void setSearchOrbViewOnScreen(boolean onScreen) {
        View searchOrbView = getTitleViewAdapter().getSearchAffordanceView();
        if (searchOrbView != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) searchOrbView.getLayoutParams();
            lp.setMarginStart(onScreen ? 0 : -this.mContainerListMarginStart);
            searchOrbView.setLayoutParams(lp);
        }
    }

    /* access modifiers changed from: package-private */
    public void setEntranceTransitionStartState() {
        setHeadersOnScreen(false);
        setSearchOrbViewOnScreen(false);
    }

    /* access modifiers changed from: package-private */
    public void setEntranceTransitionEndState() {
        setHeadersOnScreen(this.mShowingHeaders);
        setSearchOrbViewOnScreen(true);
        this.mMainFragmentAdapter.setEntranceTransitionState(true);
    }

    @Deprecated
    public interface FragmentHost {
        void notifyDataReady(MainFragmentAdapter mainFragmentAdapter);

        void notifyViewCreated(MainFragmentAdapter mainFragmentAdapter);

        void showTitleView(boolean z);
    }

    @Deprecated
    public interface MainFragmentAdapterProvider {
        MainFragmentAdapter getMainFragmentAdapter();
    }

    @Deprecated
    public interface MainFragmentRowsAdapterProvider {
        MainFragmentRowsAdapter getMainFragmentRowsAdapter();
    }

    @Deprecated
    public static abstract class FragmentFactory<T extends Fragment> {
        public abstract T createFragment(Object obj);
    }

    @Deprecated
    public static class BrowseTransitionListener {
        public void onHeadersTransitionStart(boolean withHeaders) {
        }

        public void onHeadersTransitionStop(boolean withHeaders) {
        }
    }

    @Deprecated
    public static class MainFragmentAdapter<T extends Fragment> {
        private final T mFragment;
        FragmentHostImpl mFragmentHost;
        private boolean mScalingEnabled;

        public MainFragmentAdapter(T fragment) {
            this.mFragment = fragment;
        }

        public final T getFragment() {
            return this.mFragment;
        }

        public boolean isScrolling() {
            return false;
        }

        public void setExpand(boolean expand) {
        }

        public void setEntranceTransitionState(boolean state) {
        }

        public void setAlignment(int windowAlignOffsetFromTop) {
        }

        public boolean onTransitionPrepare() {
            return false;
        }

        public void onTransitionStart() {
        }

        public void onTransitionEnd() {
        }

        public boolean isScalingEnabled() {
            return this.mScalingEnabled;
        }

        public void setScalingEnabled(boolean scalingEnabled) {
            this.mScalingEnabled = scalingEnabled;
        }

        public final FragmentHost getFragmentHost() {
            return this.mFragmentHost;
        }

        /* access modifiers changed from: package-private */
        public void setFragmentHost(FragmentHostImpl fragmentHost) {
            this.mFragmentHost = fragmentHost;
        }
    }

    @Deprecated
    public static class MainFragmentRowsAdapter<T extends Fragment> {
        private final T mFragment;

        public MainFragmentRowsAdapter(T fragment) {
            if (fragment != null) {
                this.mFragment = fragment;
                return;
            }
            throw new IllegalArgumentException("Fragment can't be null");
        }

        public final T getFragment() {
            return this.mFragment;
        }

        public void setAdapter(ObjectAdapter adapter) {
        }

        public void setOnItemViewClickedListener(OnItemViewClickedListener listener) {
        }

        public void setOnItemViewSelectedListener(OnItemViewSelectedListener listener) {
        }

        public void setSelectedPosition(int rowPosition, boolean smooth, Presenter.ViewHolderTask rowHolderTask) {
        }

        public void setSelectedPosition(int rowPosition, boolean smooth) {
        }

        public int getSelectedPosition() {
            return 0;
        }

        public RowPresenter.ViewHolder findRowViewHolderByPosition(int position) {
            return null;
        }
    }

    @Deprecated
    public static class ListRowFragmentFactory extends FragmentFactory<RowsFragment> {
        public RowsFragment createFragment(Object row) {
            return new RowsFragment();
        }
    }

    @Deprecated
    public static final class MainFragmentAdapterRegistry {
        private static final FragmentFactory sDefaultFragmentFactory = new ListRowFragmentFactory();
        private final Map<Class, FragmentFactory> mItemToFragmentFactoryMapping = new HashMap();

        public MainFragmentAdapterRegistry() {
            registerFragment(ListRow.class, sDefaultFragmentFactory);
        }

        public void registerFragment(Class rowClass, FragmentFactory factory) {
            this.mItemToFragmentFactoryMapping.put(rowClass, factory);
        }

        public Fragment createFragment(Object item) {
            FragmentFactory fragmentFactory;
            if (item == null) {
                fragmentFactory = sDefaultFragmentFactory;
            } else {
                fragmentFactory = this.mItemToFragmentFactoryMapping.get(item.getClass());
            }
            if (fragmentFactory == null && !(item instanceof PageRow)) {
                fragmentFactory = sDefaultFragmentFactory;
            }
            return fragmentFactory.createFragment(item);
        }
    }

    final class BackStackListener implements FragmentManager.OnBackStackChangedListener {
        int mIndexOfHeadersBackStack = -1;
        int mLastEntryCount;

        BackStackListener() {
            this.mLastEntryCount = BrowseFragment.this.getFragmentManager().getBackStackEntryCount();
        }

        /* access modifiers changed from: package-private */
        public void load(Bundle savedInstanceState) {
            if (savedInstanceState != null) {
                this.mIndexOfHeadersBackStack = savedInstanceState.getInt(BrowseFragment.HEADER_STACK_INDEX, -1);
                BrowseFragment.this.mShowingHeaders = this.mIndexOfHeadersBackStack == -1;
            } else if (!BrowseFragment.this.mShowingHeaders) {
                BrowseFragment.this.getFragmentManager().beginTransaction().addToBackStack(BrowseFragment.this.mWithHeadersBackStackName).commit();
            }
        }

        /* access modifiers changed from: package-private */
        public void save(Bundle outState) {
            outState.putInt(BrowseFragment.HEADER_STACK_INDEX, this.mIndexOfHeadersBackStack);
        }

        public void onBackStackChanged() {
            if (BrowseFragment.this.getFragmentManager() == null) {
                Log.w(BrowseFragment.TAG, "getFragmentManager() is null, stack:", new Exception());
                return;
            }
            int count = BrowseFragment.this.getFragmentManager().getBackStackEntryCount();
            int i = this.mLastEntryCount;
            if (count > i) {
                if (BrowseFragment.this.mWithHeadersBackStackName.equals(BrowseFragment.this.getFragmentManager().getBackStackEntryAt(count - 1).getName())) {
                    this.mIndexOfHeadersBackStack = count - 1;
                }
            } else if (count < i && this.mIndexOfHeadersBackStack >= count) {
                if (!BrowseFragment.this.isHeadersDataReady()) {
                    BrowseFragment.this.getFragmentManager().beginTransaction().addToBackStack(BrowseFragment.this.mWithHeadersBackStackName).commit();
                    return;
                }
                this.mIndexOfHeadersBackStack = -1;
                if (!BrowseFragment.this.mShowingHeaders) {
                    BrowseFragment.this.startHeadersTransitionInternal(true);
                }
            }
            this.mLastEntryCount = count;
        }
    }

    private final class SetSelectionRunnable implements Runnable {
        static final int TYPE_INTERNAL_SYNC = 0;
        static final int TYPE_INVALID = -1;
        static final int TYPE_USER_REQUEST = 1;
        private int mPosition;
        private boolean mSmooth;
        private int mType;

        SetSelectionRunnable() {
            reset();
        }

        /* access modifiers changed from: package-private */
        public void post(int position, int type, boolean smooth) {
            if (type >= this.mType) {
                this.mPosition = position;
                this.mType = type;
                this.mSmooth = smooth;
                BrowseFragment.this.mBrowseFrame.removeCallbacks(this);
                if (!BrowseFragment.this.mStopped) {
                    BrowseFragment.this.mBrowseFrame.post(this);
                }
            }
        }

        public void run() {
            BrowseFragment.this.setSelection(this.mPosition, this.mSmooth);
            reset();
        }

        public void stop() {
            BrowseFragment.this.mBrowseFrame.removeCallbacks(this);
        }

        public void start() {
            if (this.mType != -1) {
                BrowseFragment.this.mBrowseFrame.post(this);
            }
        }

        private void reset() {
            this.mPosition = -1;
            this.mType = -1;
            this.mSmooth = false;
        }
    }

    private final class FragmentHostImpl implements FragmentHost {
        boolean mShowTitleView = true;

        FragmentHostImpl() {
        }

        public void notifyViewCreated(MainFragmentAdapter fragmentAdapter) {
            BrowseFragment.this.mStateMachine.fireEvent(BrowseFragment.this.EVT_MAIN_FRAGMENT_VIEW_CREATED);
            if (!BrowseFragment.this.mIsPageRow) {
                BrowseFragment.this.mStateMachine.fireEvent(BrowseFragment.this.EVT_SCREEN_DATA_READY);
            }
        }

        public void notifyDataReady(MainFragmentAdapter fragmentAdapter) {
            if (BrowseFragment.this.mMainFragmentAdapter != null && BrowseFragment.this.mMainFragmentAdapter.getFragmentHost() == this && BrowseFragment.this.mIsPageRow) {
                BrowseFragment.this.mStateMachine.fireEvent(BrowseFragment.this.EVT_SCREEN_DATA_READY);
            }
        }

        public void showTitleView(boolean show) {
            this.mShowTitleView = show;
            if (BrowseFragment.this.mMainFragmentAdapter != null && BrowseFragment.this.mMainFragmentAdapter.getFragmentHost() == this && BrowseFragment.this.mIsPageRow) {
                BrowseFragment.this.updateTitleViewVisibility();
            }
        }
    }

    class MainFragmentItemViewSelectedListener implements OnItemViewSelectedListener {
        MainFragmentRowsAdapter mMainFragmentRowsAdapter;

        public MainFragmentItemViewSelectedListener(MainFragmentRowsAdapter fragmentRowsAdapter) {
            this.mMainFragmentRowsAdapter = fragmentRowsAdapter;
        }

        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            BrowseFragment.this.onRowSelected(this.mMainFragmentRowsAdapter.getSelectedPosition());
            if (BrowseFragment.this.mExternalOnItemViewSelectedListener != null) {
                BrowseFragment.this.mExternalOnItemViewSelectedListener.onItemSelected(itemViewHolder, item, rowViewHolder, row);
            }
        }
    }

    private class ExpandPreLayout implements ViewTreeObserver.OnPreDrawListener {
        static final int STATE_FIRST_DRAW = 1;
        static final int STATE_INIT = 0;
        static final int STATE_SECOND_DRAW = 2;
        private final Runnable mCallback;
        private final View mView;
        private int mState;
        private MainFragmentAdapter mainFragmentAdapter;

        ExpandPreLayout(Runnable callback, MainFragmentAdapter adapter, View view) {
            this.mView = view;
            this.mCallback = callback;
            this.mainFragmentAdapter = adapter;
        }

        /* access modifiers changed from: package-private */
        public void execute() {
            this.mView.getViewTreeObserver().addOnPreDrawListener(this);
            this.mainFragmentAdapter.setExpand(false);
            this.mView.invalidate();
            this.mState = 0;
        }

        public boolean onPreDraw() {
            if (BrowseFragment.this.getView() == null || FragmentUtil.getContext(BrowseFragment.this) == null) {
                this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
            int i = this.mState;
            if (i == 0) {
                this.mainFragmentAdapter.setExpand(true);
                this.mView.invalidate();
                this.mState = 1;
                return false;
            } else if (i != 1) {
                return false;
            } else {
                this.mCallback.run();
                this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
                this.mState = 2;
                return false;
            }
        }
    }
}
