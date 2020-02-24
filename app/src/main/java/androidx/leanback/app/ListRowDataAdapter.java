package androidx.leanback.app;

import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Row;

class ListRowDataAdapter extends ObjectAdapter {
    public static final int ON_CHANGED = 16;
    public static final int ON_ITEM_RANGE_CHANGED = 2;
    public static final int ON_ITEM_RANGE_INSERTED = 4;
    public static final int ON_ITEM_RANGE_REMOVED = 8;
    private final ObjectAdapter mAdapter;
    final ObjectAdapter.DataObserver mDataObserver;
    int mLastVisibleRowIndex;

    public ListRowDataAdapter(ObjectAdapter adapter) {
        super(adapter.getPresenterSelector());
        this.mAdapter = adapter;
        initialize();
        if (adapter.isImmediateNotifySupported()) {
            this.mDataObserver = new SimpleDataObserver();
        } else {
            this.mDataObserver = new QueueBasedDataObserver();
        }
        attach();
    }

    /* access modifiers changed from: package-private */
    public void detach() {
        this.mAdapter.unregisterObserver(this.mDataObserver);
    }

    /* access modifiers changed from: package-private */
    public void attach() {
        initialize();
        this.mAdapter.registerObserver(this.mDataObserver);
    }

    /* access modifiers changed from: package-private */
    public void initialize() {
        this.mLastVisibleRowIndex = -1;
        for (int i = this.mAdapter.size() - 1; i >= 0; i--) {
            if (((Row) this.mAdapter.get(i)).isRenderedAsRowView()) {
                this.mLastVisibleRowIndex = i;
                return;
            }
        }
    }

    public int size() {
        return this.mLastVisibleRowIndex + 1;
    }

    public Object get(int index) {
        return this.mAdapter.get(index);
    }

    /* access modifiers changed from: package-private */
    public void doNotify(int eventType, int positionStart, int itemCount) {
        if (eventType == 2) {
            notifyItemRangeChanged(positionStart, itemCount);
        } else if (eventType == 4) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else if (eventType == 8) {
            notifyItemRangeRemoved(positionStart, itemCount);
        } else if (eventType == 16) {
            notifyChanged();
        } else {
            throw new IllegalArgumentException("Invalid event type " + eventType);
        }
    }

    private class SimpleDataObserver extends ObjectAdapter.DataObserver {
        SimpleDataObserver() {
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (positionStart <= ListRowDataAdapter.this.mLastVisibleRowIndex) {
                onEventFired(2, positionStart, Math.min(itemCount, (ListRowDataAdapter.this.mLastVisibleRowIndex - positionStart) + 1));
            }
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (positionStart <= ListRowDataAdapter.this.mLastVisibleRowIndex) {
                ListRowDataAdapter.this.mLastVisibleRowIndex += itemCount;
                onEventFired(4, positionStart, itemCount);
                return;
            }
            int lastVisibleRowIndex = ListRowDataAdapter.this.mLastVisibleRowIndex;
            ListRowDataAdapter.this.initialize();
            if (ListRowDataAdapter.this.mLastVisibleRowIndex > lastVisibleRowIndex) {
                onEventFired(4, lastVisibleRowIndex + 1, ListRowDataAdapter.this.mLastVisibleRowIndex - lastVisibleRowIndex);
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if ((positionStart + itemCount) - 1 < ListRowDataAdapter.this.mLastVisibleRowIndex) {
                ListRowDataAdapter.this.mLastVisibleRowIndex -= itemCount;
                onEventFired(8, positionStart, itemCount);
                return;
            }
            int lastVisibleRowIndex = ListRowDataAdapter.this.mLastVisibleRowIndex;
            ListRowDataAdapter.this.initialize();
            int totalItems = lastVisibleRowIndex - ListRowDataAdapter.this.mLastVisibleRowIndex;
            if (totalItems > 0) {
                onEventFired(8, Math.min(ListRowDataAdapter.this.mLastVisibleRowIndex + 1, positionStart), totalItems);
            }
        }

        public void onChanged() {
            ListRowDataAdapter.this.initialize();
            onEventFired(16, -1, -1);
        }

        /* access modifiers changed from: protected */
        public void onEventFired(int eventType, int positionStart, int itemCount) {
            ListRowDataAdapter.this.doNotify(eventType, positionStart, itemCount);
        }
    }

    private class QueueBasedDataObserver extends ObjectAdapter.DataObserver {
        QueueBasedDataObserver() {
        }

        public void onChanged() {
            ListRowDataAdapter.this.initialize();
            ListRowDataAdapter.this.notifyChanged();
        }
    }
}
