package androidx.leanback.widget;

import android.support.p004v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;
import java.util.ArrayList;
import java.util.List;

public class ItemBridgeAdapter extends RecyclerView.Adapter implements FacetProviderAdapter {
    static final boolean DEBUG = false;
    static final String TAG = "ItemBridgeAdapter";
    private ObjectAdapter mAdapter;
    private AdapterListener mAdapterListener;
    private ObjectAdapter.DataObserver mDataObserver;
    FocusHighlightHandler mFocusHighlight;
    private PresenterSelector mPresenterSelector;
    private ArrayList<Presenter> mPresenters;
    Wrapper mWrapper;

    public static abstract class Wrapper {
        public abstract View createWrapper(View view);

        public abstract void wrap(View view, View view2);
    }

    public static class AdapterListener {
        public void onAddPresenter(Presenter presenter, int type) {
        }

        public void onCreate(ViewHolder viewHolder) {
        }

        public void onBind(ViewHolder viewHolder) {
        }

        public void onBind(ViewHolder viewHolder, List payloads) {
            onBind(viewHolder);
        }

        public void onUnbind(ViewHolder viewHolder) {
        }

        public void onAttachedToWindow(ViewHolder viewHolder) {
        }

        public void onDetachedFromWindow(ViewHolder viewHolder) {
        }
    }

    static final class ChainingFocusChangeListener implements View.OnFocusChangeListener {
        final View.OnFocusChangeListener mChainedListener;
        FocusHighlightHandler mFocusHighlight;
        boolean mHasWrapper;

        ChainingFocusChangeListener(View.OnFocusChangeListener chainedListener, boolean hasWrapper, FocusHighlightHandler focusHighlight) {
            this.mChainedListener = chainedListener;
            this.mHasWrapper = hasWrapper;
            this.mFocusHighlight = focusHighlight;
        }

        /* access modifiers changed from: package-private */
        public void update(boolean hasWrapper, FocusHighlightHandler focusHighlight) {
            this.mHasWrapper = hasWrapper;
            this.mFocusHighlight = focusHighlight;
        }

        /* JADX WARN: Type inference failed for: r0v3, types: [android.view.ViewParent] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFocusChange(android.view.View r2, boolean r3) {
            /*
                r1 = this;
                boolean r0 = r1.mHasWrapper
                if (r0 == 0) goto L_0x000b
                android.view.ViewParent r0 = r2.getParent()
                r2 = r0
                android.view.View r2 = (android.view.View) r2
            L_0x000b:
                androidx.leanback.widget.FocusHighlightHandler r0 = r1.mFocusHighlight
                r0.onItemFocused(r2, r3)
                android.view.View$OnFocusChangeListener r0 = r1.mChainedListener
                if (r0 == 0) goto L_0x0017
                r0.onFocusChange(r2, r3)
            L_0x0017:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.ItemBridgeAdapter.ChainingFocusChangeListener.onFocusChange(android.view.View, boolean):void");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        Object mExtraObject;
        final Presenter.ViewHolder mHolder;
        Object mItem;
        final Presenter mPresenter;

        public final Presenter getPresenter() {
            return this.mPresenter;
        }

        public final Presenter.ViewHolder getViewHolder() {
            return this.mHolder;
        }

        public final Object getItem() {
            return this.mItem;
        }

        public final Object getExtraObject() {
            return this.mExtraObject;
        }

        public void setExtraObject(Object object) {
            this.mExtraObject = object;
        }

        public Object getFacet(Class<?> facetClass) {
            return this.mHolder.getFacet(facetClass);
        }

        ViewHolder(Presenter presenter, View view, Presenter.ViewHolder holder) {
            super(view);
            this.mPresenter = presenter;
            this.mHolder = holder;
        }
    }

    public ItemBridgeAdapter(ObjectAdapter adapter, PresenterSelector presenterSelector) {
        this.mPresenters = new ArrayList<>();
        this.mDataObserver = new ObjectAdapter.DataObserver() {
            public void onChanged() {
                ItemBridgeAdapter.this.notifyDataSetChanged();
            }

            public void onItemRangeChanged(int positionStart, int itemCount) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            }

            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                ItemBridgeAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                ItemBridgeAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            }

            public void onItemMoved(int fromPosition, int toPosition) {
                ItemBridgeAdapter.this.notifyItemMoved(fromPosition, toPosition);
            }
        };
        setAdapter(adapter);
        this.mPresenterSelector = presenterSelector;
    }

    public ItemBridgeAdapter(ObjectAdapter adapter) {
        this(adapter, null);
    }

    public ItemBridgeAdapter() {
        this.mPresenters = new ArrayList<>();
        this.mDataObserver = new ObjectAdapter.DataObserver() {
            public void onChanged() {
                ItemBridgeAdapter.this.notifyDataSetChanged();
            }

            public void onItemRangeChanged(int positionStart, int itemCount) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            }

            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                ItemBridgeAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                ItemBridgeAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            }

            public void onItemMoved(int fromPosition, int toPosition) {
                ItemBridgeAdapter.this.notifyItemMoved(fromPosition, toPosition);
            }
        };
    }

    public void setAdapter(ObjectAdapter adapter) {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (adapter != objectAdapter) {
            if (objectAdapter != null) {
                objectAdapter.unregisterObserver(this.mDataObserver);
            }
            this.mAdapter = adapter;
            ObjectAdapter objectAdapter2 = this.mAdapter;
            if (objectAdapter2 == null) {
                notifyDataSetChanged();
                return;
            }
            objectAdapter2.registerObserver(this.mDataObserver);
            if (hasStableIds() != this.mAdapter.hasStableIds()) {
                setHasStableIds(this.mAdapter.hasStableIds());
            }
            notifyDataSetChanged();
        }
    }

    public void setPresenter(PresenterSelector presenterSelector) {
        this.mPresenterSelector = presenterSelector;
        notifyDataSetChanged();
    }

    public void setWrapper(Wrapper wrapper) {
        this.mWrapper = wrapper;
    }

    public Wrapper getWrapper() {
        return this.mWrapper;
    }

    /* access modifiers changed from: package-private */
    public void setFocusHighlight(FocusHighlightHandler listener) {
        this.mFocusHighlight = listener;
    }

    public void clear() {
        setAdapter(null);
    }

    public void setPresenterMapper(ArrayList<Presenter> presenters) {
        this.mPresenters = presenters;
    }

    public ArrayList<Presenter> getPresenterMapper() {
        return this.mPresenters;
    }

    public int getItemCount() {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter != null) {
            return objectAdapter.size();
        }
        return 0;
    }

    public int getItemViewType(int position) {
        PresenterSelector presenterSelector = this.mPresenterSelector;
        if (presenterSelector == null) {
            presenterSelector = this.mAdapter.getPresenterSelector();
        }
        Presenter presenter = presenterSelector.getPresenter(this.mAdapter.get(position));
        int type = this.mPresenters.indexOf(presenter);
        if (type < 0) {
            this.mPresenters.add(presenter);
            type = this.mPresenters.indexOf(presenter);
            onAddPresenter(presenter, type);
            AdapterListener adapterListener = this.mAdapterListener;
            if (adapterListener != null) {
                adapterListener.onAddPresenter(presenter, type);
            }
        }
        return type;
    }

    /* access modifiers changed from: protected */
    public void onAddPresenter(Presenter presenter, int type) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(ViewHolder viewHolder) {
    }

    /* access modifiers changed from: protected */
    public void onBind(ViewHolder viewHolder) {
    }

    /* access modifiers changed from: protected */
    public void onUnbind(ViewHolder viewHolder) {
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow(ViewHolder viewHolder) {
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow(ViewHolder viewHolder) {
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Presenter.ViewHolder presenterVh;
        View view;
        Presenter presenter = this.mPresenters.get(viewType);
        Wrapper wrapper = this.mWrapper;
        if (wrapper != null) {
            view = wrapper.createWrapper(parent);
            presenterVh = presenter.onCreateViewHolder(parent);
            this.mWrapper.wrap(view, presenterVh.view);
        } else {
            presenterVh = presenter.onCreateViewHolder(parent);
            view = presenterVh.view;
        }
        ViewHolder viewHolder = new ViewHolder(presenter, view, presenterVh);
        onCreate(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onCreate(viewHolder);
        }
        View presenterView = viewHolder.mHolder.view;
        View.OnFocusChangeListener currentListener = presenterView.getOnFocusChangeListener();
        if (this.mFocusHighlight != null) {
            boolean z = true;
            if (currentListener instanceof ChainingFocusChangeListener) {
                ChainingFocusChangeListener chainingFocusChangeListener = (ChainingFocusChangeListener) currentListener;
                if (this.mWrapper == null) {
                    z = false;
                }
                chainingFocusChangeListener.update(z, this.mFocusHighlight);
            } else {
                if (this.mWrapper == null) {
                    z = false;
                }
                presenterView.setOnFocusChangeListener(new ChainingFocusChangeListener(currentListener, z, this.mFocusHighlight));
            }
            this.mFocusHighlight.onInitializeView(view);
        } else if (currentListener instanceof ChainingFocusChangeListener) {
            presenterView.setOnFocusChangeListener(((ChainingFocusChangeListener) currentListener).mChainedListener);
        }
        return viewHolder;
    }

    public void setAdapterListener(AdapterListener listener) {
        this.mAdapterListener = listener;
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mItem = this.mAdapter.get(position);
        viewHolder.mPresenter.onBindViewHolder(viewHolder.mHolder, viewHolder.mItem);
        onBind(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onBind(viewHolder);
        }
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mItem = this.mAdapter.get(position);
        viewHolder.mPresenter.onBindViewHolder(viewHolder.mHolder, viewHolder.mItem, payloads);
        onBind(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onBind(viewHolder, payloads);
        }
    }

    public final void onViewRecycled(RecyclerView.ViewHolder holder) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mPresenter.onUnbindViewHolder(viewHolder.mHolder);
        onUnbind(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onUnbind(viewHolder);
        }
        viewHolder.mItem = null;
    }

    public final boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        onViewRecycled(holder);
        return false;
    }

    public final void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        ViewHolder viewHolder = (ViewHolder) holder;
        onAttachedToWindow(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onAttachedToWindow(viewHolder);
        }
        viewHolder.mPresenter.onViewAttachedToWindow(viewHolder.mHolder);
    }

    public final void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mPresenter.onViewDetachedFromWindow(viewHolder.mHolder);
        onDetachedFromWindow(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onDetachedFromWindow(viewHolder);
        }
    }

    public long getItemId(int position) {
        return this.mAdapter.getId(position);
    }

    public FacetProvider getFacetProvider(int type) {
        return this.mPresenters.get(type);
    }
}
