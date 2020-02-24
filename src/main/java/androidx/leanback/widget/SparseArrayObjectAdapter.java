package androidx.leanback.widget;

import android.util.SparseArray;

public class SparseArrayObjectAdapter extends ObjectAdapter {
    private SparseArray<Object> mItems = new SparseArray<>();

    public SparseArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public SparseArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public SparseArrayObjectAdapter() {
    }

    public int size() {
        return this.mItems.size();
    }

    public Object get(int position) {
        return this.mItems.valueAt(position);
    }

    public int indexOf(Object item) {
        return this.mItems.indexOfValue(item);
    }

    public int indexOf(int key) {
        return this.mItems.indexOfKey(key);
    }

    public void notifyArrayItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    public void set(int key, Object item) {
        int index = this.mItems.indexOfKey(key);
        if (index < 0) {
            this.mItems.append(key, item);
            notifyItemRangeInserted(this.mItems.indexOfKey(key), 1);
        } else if (this.mItems.valueAt(index) != item) {
            this.mItems.setValueAt(index, item);
            notifyItemRangeChanged(index, 1);
        }
    }

    public void clear(int key) {
        int index = this.mItems.indexOfKey(key);
        if (index >= 0) {
            this.mItems.removeAt(index);
            notifyItemRangeRemoved(index, 1);
        }
    }

    public void clear() {
        int itemCount = this.mItems.size();
        if (itemCount != 0) {
            this.mItems.clear();
            notifyItemRangeRemoved(0, itemCount);
        }
    }

    public Object lookup(int key) {
        return this.mItems.get(key);
    }

    public boolean isImmediateNotifySupported() {
        return true;
    }
}
