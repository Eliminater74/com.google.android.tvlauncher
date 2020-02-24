package androidx.leanback.widget;

import android.support.annotation.Nullable;
import android.support.p004v7.util.DiffUtil;
import android.support.p004v7.util.ListUpdateCallback;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayObjectAdapter extends ObjectAdapter {
    /* access modifiers changed from: private */
    public static final Boolean DEBUG = false;
    private static final String TAG = "ArrayObjectAdapter";
    private final List mItems = new ArrayList();
    ListUpdateCallback mListUpdateCallback;
    final List mOldItems = new ArrayList();
    private List mUnmodifiableItems;

    public ArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public ArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public ArrayObjectAdapter() {
    }

    public int size() {
        return this.mItems.size();
    }

    public Object get(int index) {
        return this.mItems.get(index);
    }

    public int indexOf(Object item) {
        return this.mItems.indexOf(item);
    }

    public void notifyArrayItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    public void add(Object item) {
        add(this.mItems.size(), item);
    }

    public void add(int index, Object item) {
        this.mItems.add(index, item);
        notifyItemRangeInserted(index, 1);
    }

    public void addAll(int index, Collection items) {
        int itemsCount = items.size();
        if (itemsCount != 0) {
            this.mItems.addAll(index, items);
            notifyItemRangeInserted(index, itemsCount);
        }
    }

    public boolean remove(Object item) {
        int index = this.mItems.indexOf(item);
        if (index >= 0) {
            this.mItems.remove(index);
            notifyItemRangeRemoved(index, 1);
        }
        if (index >= 0) {
            return true;
        }
        return false;
    }

    public void move(int fromPosition, int toPosition) {
        if (fromPosition != toPosition) {
            this.mItems.add(toPosition, this.mItems.remove(fromPosition));
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    public void replace(int position, Object item) {
        this.mItems.set(position, item);
        notifyItemRangeChanged(position, 1);
    }

    public int removeItems(int position, int count) {
        int itemsToRemove = Math.min(count, this.mItems.size() - position);
        if (itemsToRemove <= 0) {
            return 0;
        }
        for (int i = 0; i < itemsToRemove; i++) {
            this.mItems.remove(position);
        }
        notifyItemRangeRemoved(position, itemsToRemove);
        return itemsToRemove;
    }

    public void clear() {
        int itemCount = this.mItems.size();
        if (itemCount != 0) {
            this.mItems.clear();
            notifyItemRangeRemoved(0, itemCount);
        }
    }

    public <E> List<E> unmodifiableList() {
        if (this.mUnmodifiableItems == null) {
            this.mUnmodifiableItems = Collections.unmodifiableList(this.mItems);
        }
        return this.mUnmodifiableItems;
    }

    public boolean isImmediateNotifySupported() {
        return true;
    }

    public void setItems(final List itemList, final DiffCallback callback) {
        if (callback == null) {
            this.mItems.clear();
            this.mItems.addAll(itemList);
            notifyChanged();
            return;
        }
        this.mOldItems.clear();
        this.mOldItems.addAll(this.mItems);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            public int getOldListSize() {
                return ArrayObjectAdapter.this.mOldItems.size();
            }

            public int getNewListSize() {
                return itemList.size();
            }

            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return callback.areItemsTheSame(ArrayObjectAdapter.this.mOldItems.get(oldItemPosition), itemList.get(newItemPosition));
            }

            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return callback.areContentsTheSame(ArrayObjectAdapter.this.mOldItems.get(oldItemPosition), itemList.get(newItemPosition));
            }

            @Nullable
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return callback.getChangePayload(ArrayObjectAdapter.this.mOldItems.get(oldItemPosition), itemList.get(newItemPosition));
            }
        });
        this.mItems.clear();
        this.mItems.addAll(itemList);
        if (this.mListUpdateCallback == null) {
            this.mListUpdateCallback = new ListUpdateCallback() {
                public void onInserted(int position, int count) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onInserted");
                    }
                    ArrayObjectAdapter.this.notifyItemRangeInserted(position, count);
                }

                public void onRemoved(int position, int count) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onRemoved");
                    }
                    ArrayObjectAdapter.this.notifyItemRangeRemoved(position, count);
                }

                public void onMoved(int fromPosition, int toPosition) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onMoved");
                    }
                    ArrayObjectAdapter.this.notifyItemMoved(fromPosition, toPosition);
                }

                public void onChanged(int position, int count, Object payload) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onChanged");
                    }
                    ArrayObjectAdapter.this.notifyItemRangeChanged(position, count, payload);
                }
            };
        }
        diffResult.dispatchUpdatesTo(this.mListUpdateCallback);
        this.mOldItems.clear();
    }
}
