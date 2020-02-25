package androidx.leanback.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.Fragment;
import android.support.p004v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.VerticalGridView;

abstract class BaseRowSupportFragment extends Fragment {
    private static final String CURRENT_SELECTED_POSITION = "currentSelectedPosition";
    final ItemBridgeAdapter mBridgeAdapter = new ItemBridgeAdapter();
    LateSelectionObserver mLateSelectionObserver = new LateSelectionObserver();
    int mSelectedPosition = -1;
    private final OnChildViewHolderSelectedListener mRowSelectedListener = new OnChildViewHolderSelectedListener() {
        public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder view, int position, int subposition) {
            if (!BaseRowSupportFragment.this.mLateSelectionObserver.mIsLateSelection) {
                BaseRowSupportFragment baseRowSupportFragment = BaseRowSupportFragment.this;
                baseRowSupportFragment.mSelectedPosition = position;
                baseRowSupportFragment.onRowSelected(parent, view, position, subposition);
            }
        }
    };
    VerticalGridView mVerticalGridView;
    private ObjectAdapter mAdapter;
    private boolean mPendingTransitionPrepare;
    private PresenterSelector mPresenterSelector;

    BaseRowSupportFragment() {
    }

    /* access modifiers changed from: package-private */
    public abstract int getLayoutResourceId();

    /* access modifiers changed from: package-private */
    public void onRowSelected(RecyclerView parent, RecyclerView.ViewHolder view, int position, int subposition) {
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        this.mVerticalGridView = findGridViewFromRoot(view);
        if (this.mPendingTransitionPrepare) {
            this.mPendingTransitionPrepare = false;
            onTransitionPrepare();
        }
        return view;
    }

    /* access modifiers changed from: package-private */
    public VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mSelectedPosition = savedInstanceState.getInt(CURRENT_SELECTED_POSITION, -1);
        }
        setAdapterAndSelection();
        this.mVerticalGridView.setOnChildViewHolderSelectedListener(this.mRowSelectedListener);
    }

    /* access modifiers changed from: package-private */
    public void setAdapterAndSelection() {
        if (this.mAdapter != null) {
            RecyclerView.Adapter adapter = this.mVerticalGridView.getAdapter();
            ItemBridgeAdapter itemBridgeAdapter = this.mBridgeAdapter;
            if (adapter != itemBridgeAdapter) {
                this.mVerticalGridView.setAdapter(itemBridgeAdapter);
            }
            if (this.mBridgeAdapter.getItemCount() == 0 && this.mSelectedPosition >= 0) {
                this.mLateSelectionObserver.startLateSelection();
                return;
            }
            int i = this.mSelectedPosition;
            if (i >= 0) {
                this.mVerticalGridView.setSelectedPosition(i);
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mLateSelectionObserver.clear();
        this.mVerticalGridView = null;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_SELECTED_POSITION, this.mSelectedPosition);
    }

    public final PresenterSelector getPresenterSelector() {
        return this.mPresenterSelector;
    }

    public final void setPresenterSelector(PresenterSelector presenterSelector) {
        if (this.mPresenterSelector != presenterSelector) {
            this.mPresenterSelector = presenterSelector;
            updateAdapter();
        }
    }

    public final ObjectAdapter getAdapter() {
        return this.mAdapter;
    }

    public final void setAdapter(ObjectAdapter rowsAdapter) {
        if (this.mAdapter != rowsAdapter) {
            this.mAdapter = rowsAdapter;
            updateAdapter();
        }
    }

    public final ItemBridgeAdapter getBridgeAdapter() {
        return this.mBridgeAdapter;
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        setSelectedPosition(position, true);
    }

    public void setSelectedPosition(int position, boolean smooth) {
        if (this.mSelectedPosition != position) {
            this.mSelectedPosition = position;
            if (this.mVerticalGridView != null && !this.mLateSelectionObserver.mIsLateSelection) {
                if (smooth) {
                    this.mVerticalGridView.setSelectedPositionSmooth(position);
                } else {
                    this.mVerticalGridView.setSelectedPosition(position);
                }
            }
        }
    }

    public final VerticalGridView getVerticalGridView() {
        return this.mVerticalGridView;
    }

    /* access modifiers changed from: package-private */
    public void updateAdapter() {
        this.mBridgeAdapter.setAdapter(this.mAdapter);
        this.mBridgeAdapter.setPresenter(this.mPresenterSelector);
        if (this.mVerticalGridView != null) {
            setAdapterAndSelection();
        }
    }

    /* access modifiers changed from: package-private */
    public Object getItem(Row row, int position) {
        if (row instanceof ListRow) {
            return ((ListRow) row).getAdapter().get(position);
        }
        return null;
    }

    public boolean onTransitionPrepare() {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setAnimateChildLayout(false);
            this.mVerticalGridView.setScrollEnabled(false);
            return true;
        }
        this.mPendingTransitionPrepare = true;
        return false;
    }

    public void onTransitionStart() {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setPruneChild(false);
            this.mVerticalGridView.setLayoutFrozen(true);
            this.mVerticalGridView.setFocusSearchDisabled(true);
        }
    }

    public void onTransitionEnd() {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setLayoutFrozen(false);
            this.mVerticalGridView.setAnimateChildLayout(true);
            this.mVerticalGridView.setPruneChild(true);
            this.mVerticalGridView.setFocusSearchDisabled(false);
            this.mVerticalGridView.setScrollEnabled(true);
        }
    }

    public void setAlignment(int windowAlignOffsetTop) {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView != null) {
            verticalGridView.setItemAlignmentOffset(0);
            this.mVerticalGridView.setItemAlignmentOffsetPercent(-1.0f);
            this.mVerticalGridView.setWindowAlignmentOffset(windowAlignOffsetTop);
            this.mVerticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
            this.mVerticalGridView.setWindowAlignment(0);
        }
    }

    private class LateSelectionObserver extends RecyclerView.AdapterDataObserver {
        boolean mIsLateSelection = false;

        LateSelectionObserver() {
        }

        public void onChanged() {
            performLateSelection();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            performLateSelection();
        }

        /* access modifiers changed from: package-private */
        public void startLateSelection() {
            this.mIsLateSelection = true;
            BaseRowSupportFragment.this.mBridgeAdapter.registerAdapterDataObserver(this);
        }

        /* access modifiers changed from: package-private */
        public void performLateSelection() {
            clear();
            if (BaseRowSupportFragment.this.mVerticalGridView != null) {
                BaseRowSupportFragment.this.mVerticalGridView.setSelectedPosition(BaseRowSupportFragment.this.mSelectedPosition);
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            if (this.mIsLateSelection) {
                this.mIsLateSelection = false;
                BaseRowSupportFragment.this.mBridgeAdapter.unregisterAdapterDataObserver(this);
            }
        }
    }
}
