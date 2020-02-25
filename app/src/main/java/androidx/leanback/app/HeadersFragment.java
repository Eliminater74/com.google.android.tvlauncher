package androidx.leanback.app;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p004v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.leanback.C0364R;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.DividerPresenter;
import androidx.leanback.widget.DividerRow;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.SectionRow;
import androidx.leanback.widget.VerticalGridView;

@Deprecated
public class HeadersFragment extends BaseRowFragment {
    private static final PresenterSelector sHeaderPresenter = new ClassPresenterSelector().addClassPresenter(DividerRow.class, new DividerPresenter()).addClassPresenter(SectionRow.class, new RowHeaderPresenter(C0364R.layout.lb_section_header, false)).addClassPresenter(Row.class, new RowHeaderPresenter(C0364R.layout.lb_header));
    static View.OnLayoutChangeListener sLayoutChangeListener = new View.OnLayoutChangeListener() {
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            v.setPivotX(v.getLayoutDirection() == 1 ? (float) v.getWidth() : 0.0f);
            v.setPivotY((float) (v.getMeasuredHeight() / 2));
        }
    };
    final ItemBridgeAdapter.Wrapper mWrapper = new ItemBridgeAdapter.Wrapper() {
        public void wrap(View wrapper, View wrapped) {
            ((FrameLayout) wrapper).addView(wrapped);
        }

        public View createWrapper(View root) {
            return new NoOverlappingFrameLayout(root.getContext());
        }
    };
    OnHeaderClickedListener mOnHeaderClickedListener;
    private final ItemBridgeAdapter.AdapterListener mAdapterListener = new ItemBridgeAdapter.AdapterListener() {
        public void onCreate(final ItemBridgeAdapter.ViewHolder viewHolder) {
            View headerView = viewHolder.getViewHolder().view;
            headerView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (HeadersFragment.this.mOnHeaderClickedListener != null) {
                        HeadersFragment.this.mOnHeaderClickedListener.onHeaderClicked((RowHeaderPresenter.ViewHolder) viewHolder.getViewHolder(), (Row) viewHolder.getItem());
                    }
                }
            });
            if (HeadersFragment.this.mWrapper != null) {
                viewHolder.itemView.addOnLayoutChangeListener(HeadersFragment.sLayoutChangeListener);
            } else {
                headerView.addOnLayoutChangeListener(HeadersFragment.sLayoutChangeListener);
            }
        }
    };
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private boolean mHeadersEnabled = true;
    private boolean mHeadersGone = false;
    private OnHeaderViewSelectedListener mOnHeaderViewSelectedListener;

    public HeadersFragment() {
        setPresenterSelector(sHeaderPresenter);
        FocusHighlightHelper.setupHeaderItemFocusHighlight(getBridgeAdapter());
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

    public /* bridge */ /* synthetic */ void onDestroyView() {
        super.onDestroyView();
    }

    public /* bridge */ /* synthetic */ void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public /* bridge */ /* synthetic */ boolean onTransitionPrepare() {
        return super.onTransitionPrepare();
    }

    public /* bridge */ /* synthetic */ void setAlignment(int i) {
        super.setAlignment(i);
    }

    public /* bridge */ /* synthetic */ void setSelectedPosition(int i, boolean z) {
        super.setSelectedPosition(i, z);
    }

    public void setOnHeaderClickedListener(OnHeaderClickedListener listener) {
        this.mOnHeaderClickedListener = listener;
    }

    public void setOnHeaderViewSelectedListener(OnHeaderViewSelectedListener listener) {
        this.mOnHeaderViewSelectedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view.findViewById(C0364R.C0366id.browse_headers);
    }

    /* access modifiers changed from: package-private */
    public void onRowSelected(RecyclerView parent, RecyclerView.ViewHolder viewHolder, int position, int subposition) {
        OnHeaderViewSelectedListener onHeaderViewSelectedListener = this.mOnHeaderViewSelectedListener;
        if (onHeaderViewSelectedListener == null) {
            return;
        }
        if (viewHolder == null || position < 0) {
            this.mOnHeaderViewSelectedListener.onHeaderSelected(null, null);
            return;
        }
        ItemBridgeAdapter.ViewHolder vh = (ItemBridgeAdapter.ViewHolder) viewHolder;
        onHeaderViewSelectedListener.onHeaderSelected((RowHeaderPresenter.ViewHolder) vh.getViewHolder(), (Row) vh.getItem());
    }

    /* access modifiers changed from: package-private */
    public int getLayoutResourceId() {
        return C0364R.layout.lb_headers_fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VerticalGridView listView = getVerticalGridView();
        if (listView != null) {
            if (this.mBackgroundColorSet) {
                listView.setBackgroundColor(this.mBackgroundColor);
                updateFadingEdgeToBrandColor(this.mBackgroundColor);
            } else {
                Drawable d = listView.getBackground();
                if (d instanceof ColorDrawable) {
                    updateFadingEdgeToBrandColor(((ColorDrawable) d).getColor());
                }
            }
            updateListViewVisibility();
        }
    }

    private void updateListViewVisibility() {
        VerticalGridView listView = getVerticalGridView();
        if (listView != null) {
            getView().setVisibility(this.mHeadersGone ? 8 : 0);
            if (this.mHeadersGone) {
                return;
            }
            if (this.mHeadersEnabled) {
                listView.setChildrenVisibility(0);
            } else {
                listView.setChildrenVisibility(4);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setHeadersEnabled(boolean enabled) {
        this.mHeadersEnabled = enabled;
        updateListViewVisibility();
    }

    /* access modifiers changed from: package-private */
    public void setHeadersGone(boolean gone) {
        this.mHeadersGone = gone;
        updateListViewVisibility();
    }

    /* access modifiers changed from: package-private */
    public void updateAdapter() {
        super.updateAdapter();
        ItemBridgeAdapter adapter = getBridgeAdapter();
        adapter.setAdapterListener(this.mAdapterListener);
        adapter.setWrapper(this.mWrapper);
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
        this.mBackgroundColorSet = true;
        if (getVerticalGridView() != null) {
            getVerticalGridView().setBackgroundColor(this.mBackgroundColor);
            updateFadingEdgeToBrandColor(this.mBackgroundColor);
        }
    }

    private void updateFadingEdgeToBrandColor(int backgroundColor) {
        Drawable background = getView().findViewById(C0364R.C0366id.fade_out_edge).getBackground();
        if (background instanceof GradientDrawable) {
            background.mutate();
            ((GradientDrawable) background).setColors(new int[]{0, backgroundColor});
        }
    }

    public void onTransitionStart() {
        VerticalGridView listView;
        super.onTransitionStart();
        if (!this.mHeadersEnabled && (listView = getVerticalGridView()) != null) {
            listView.setDescendantFocusability(131072);
            if (listView.hasFocus()) {
                listView.requestFocus();
            }
        }
    }

    public void onTransitionEnd() {
        VerticalGridView listView;
        if (this.mHeadersEnabled && (listView = getVerticalGridView()) != null) {
            listView.setDescendantFocusability(262144);
            if (listView.hasFocus()) {
                listView.requestFocus();
            }
        }
        super.onTransitionEnd();
    }

    public boolean isScrolling() {
        return getVerticalGridView().getScrollState() != 0;
    }

    @Deprecated
    public interface OnHeaderClickedListener {
        void onHeaderClicked(RowHeaderPresenter.ViewHolder viewHolder, Row row);
    }

    @Deprecated
    public interface OnHeaderViewSelectedListener {
        void onHeaderSelected(RowHeaderPresenter.ViewHolder viewHolder, Row row);
    }

    static class NoOverlappingFrameLayout extends FrameLayout {
        public NoOverlappingFrameLayout(Context context) {
            super(context);
        }

        public boolean hasOverlappingRendering() {
            return false;
        }
    }
}
