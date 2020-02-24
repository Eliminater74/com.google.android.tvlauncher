package androidx.leanback.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.C0364R;
import androidx.leanback.widget.ControlBar;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;

class ControlBarPresenter extends Presenter {
    static final int MAX_CONTROLS = 7;
    private static int sChildMarginDefault;
    private static int sControlIconWidth;
    boolean mDefaultFocusToMiddle = true;
    private int mLayoutResourceId;
    OnControlClickedListener mOnControlClickedListener;
    OnControlSelectedListener mOnControlSelectedListener;

    interface OnControlClickedListener {
        void onControlClicked(Presenter.ViewHolder viewHolder, Object obj, BoundData boundData);
    }

    interface OnControlSelectedListener {
        void onControlSelected(Presenter.ViewHolder viewHolder, Object obj, BoundData boundData);
    }

    static class BoundData {
        ObjectAdapter adapter;
        Presenter presenter;

        BoundData() {
        }
    }

    class ViewHolder extends Presenter.ViewHolder {
        ObjectAdapter mAdapter;
        ControlBar mControlBar;
        View mControlsContainer;
        BoundData mData;
        ObjectAdapter.DataObserver mDataObserver;
        Presenter mPresenter;
        SparseArray<Presenter.ViewHolder> mViewHolders = new SparseArray<>();

        ViewHolder(View rootView) {
            super(rootView);
            this.mControlsContainer = rootView.findViewById(C0364R.C0366id.controls_container);
            this.mControlBar = (ControlBar) rootView.findViewById(C0364R.C0366id.control_bar);
            ControlBar controlBar = this.mControlBar;
            if (controlBar != null) {
                controlBar.setDefaultFocusToMiddle(ControlBarPresenter.this.mDefaultFocusToMiddle);
                this.mControlBar.setOnChildFocusedListener(new ControlBar.OnChildFocusedListener(ControlBarPresenter.this) {
                    public void onChildFocusedListener(View child, View focused) {
                        if (ControlBarPresenter.this.mOnControlSelectedListener != null) {
                            for (int position = 0; position < ViewHolder.this.mViewHolders.size(); position++) {
                                if (ViewHolder.this.mViewHolders.get(position).view == child) {
                                    ControlBarPresenter.this.mOnControlSelectedListener.onControlSelected(ViewHolder.this.mViewHolders.get(position), ViewHolder.this.getDisplayedAdapter().get(position), ViewHolder.this.mData);
                                    return;
                                }
                            }
                        }
                    }
                });
                this.mDataObserver = new ObjectAdapter.DataObserver(ControlBarPresenter.this) {
                    public void onChanged() {
                        if (ViewHolder.this.mAdapter == ViewHolder.this.getDisplayedAdapter()) {
                            ViewHolder viewHolder = ViewHolder.this;
                            viewHolder.showControls(viewHolder.mPresenter);
                        }
                    }

                    public void onItemRangeChanged(int positionStart, int itemCount) {
                        if (ViewHolder.this.mAdapter == ViewHolder.this.getDisplayedAdapter()) {
                            for (int i = 0; i < itemCount; i++) {
                                ViewHolder viewHolder = ViewHolder.this;
                                viewHolder.bindControlToAction(positionStart + i, viewHolder.mPresenter);
                            }
                        }
                    }
                };
                return;
            }
            throw new IllegalStateException("Couldn't find control_bar");
        }

        /* access modifiers changed from: package-private */
        public int getChildMarginFromCenter(Context context, int numControls) {
            return ControlBarPresenter.this.getChildMarginDefault(context) + ControlBarPresenter.this.getControlIconWidth(context);
        }

        /* access modifiers changed from: package-private */
        public void showControls(Presenter presenter) {
            ObjectAdapter adapter = getDisplayedAdapter();
            int adapterSize = adapter == null ? 0 : adapter.size();
            View focusedView = this.mControlBar.getFocusedChild();
            if (focusedView != null && adapterSize > 0 && this.mControlBar.indexOfChild(focusedView) >= adapterSize) {
                this.mControlBar.getChildAt(adapter.size() - 1).requestFocus();
            }
            for (int i = this.mControlBar.getChildCount() - 1; i >= adapterSize; i--) {
                this.mControlBar.removeViewAt(i);
            }
            int position = 0;
            while (position < adapterSize && position < 7) {
                bindControlToAction(position, adapter, presenter);
                position++;
            }
            ControlBar controlBar = this.mControlBar;
            controlBar.setChildMarginFromCenter(getChildMarginFromCenter(controlBar.getContext(), adapterSize));
        }

        /* access modifiers changed from: package-private */
        public void bindControlToAction(int position, Presenter presenter) {
            bindControlToAction(position, getDisplayedAdapter(), presenter);
        }

        private void bindControlToAction(final int position, ObjectAdapter adapter, Presenter presenter) {
            Presenter.ViewHolder vh = this.mViewHolders.get(position);
            Object item = adapter.get(position);
            if (vh == null) {
                vh = presenter.onCreateViewHolder(this.mControlBar);
                this.mViewHolders.put(position, vh);
                final Presenter.ViewHolder itemViewHolder = vh;
                presenter.setOnClickListener(vh, new View.OnClickListener() {
                    public void onClick(View v) {
                        Object item = ViewHolder.this.getDisplayedAdapter().get(position);
                        if (ControlBarPresenter.this.mOnControlClickedListener != null) {
                            ControlBarPresenter.this.mOnControlClickedListener.onControlClicked(itemViewHolder, item, ViewHolder.this.mData);
                        }
                    }
                });
            }
            if (vh.view.getParent() == null) {
                this.mControlBar.addView(vh.view);
            }
            presenter.onBindViewHolder(vh, item);
        }

        /* access modifiers changed from: package-private */
        public ObjectAdapter getDisplayedAdapter() {
            return this.mAdapter;
        }
    }

    public ControlBarPresenter(int layoutResourceId) {
        this.mLayoutResourceId = layoutResourceId;
    }

    public int getLayoutResourceId() {
        return this.mLayoutResourceId;
    }

    public void setOnControlClickedListener(OnControlClickedListener listener) {
        this.mOnControlClickedListener = listener;
    }

    public OnControlClickedListener getOnItemViewClickedListener() {
        return this.mOnControlClickedListener;
    }

    public void setOnControlSelectedListener(OnControlSelectedListener listener) {
        this.mOnControlSelectedListener = listener;
    }

    public OnControlSelectedListener getOnItemControlListener() {
        return this.mOnControlSelectedListener;
    }

    public void setBackgroundColor(ViewHolder vh, int color) {
        vh.mControlsContainer.setBackgroundColor(color);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutResourceId(), parent, false));
    }

    public void onBindViewHolder(Presenter.ViewHolder holder, Object item) {
        ViewHolder vh = (ViewHolder) holder;
        BoundData data = (BoundData) item;
        if (vh.mAdapter != data.adapter) {
            vh.mAdapter = data.adapter;
            if (vh.mAdapter != null) {
                vh.mAdapter.registerObserver(vh.mDataObserver);
            }
        }
        vh.mPresenter = data.presenter;
        vh.mData = data;
        vh.showControls(vh.mPresenter);
    }

    public void onUnbindViewHolder(Presenter.ViewHolder holder) {
        ViewHolder vh = (ViewHolder) holder;
        if (vh.mAdapter != null) {
            vh.mAdapter.unregisterObserver(vh.mDataObserver);
            vh.mAdapter = null;
        }
        vh.mData = null;
    }

    /* access modifiers changed from: package-private */
    public int getChildMarginDefault(Context context) {
        if (sChildMarginDefault == 0) {
            sChildMarginDefault = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_controls_child_margin_default);
        }
        return sChildMarginDefault;
    }

    /* access modifiers changed from: package-private */
    public int getControlIconWidth(Context context) {
        if (sControlIconWidth == 0) {
            sControlIconWidth = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_control_icon_width);
        }
        return sControlIconWidth;
    }

    /* access modifiers changed from: package-private */
    public void setDefaultFocusToMiddle(boolean defaultFocusToMiddle) {
        this.mDefaultFocusToMiddle = defaultFocusToMiddle;
    }
}
