package android.support.p004v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p004v7.util.AdapterListUpdateCallback;
import android.support.p004v7.util.DiffUtil;
import android.support.p004v7.widget.RecyclerView;

import java.util.List;

/* renamed from: android.support.v7.recyclerview.extensions.ListAdapter */
public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    final AsyncListDiffer<T> mDiffer;
    private final AsyncListDiffer.ListListener<T> mListener = new AsyncListDiffer.ListListener<T>() {
        public void onCurrentListChanged(@NonNull List<T> previousList, @NonNull List<T> currentList) {
            ListAdapter.this.onCurrentListChanged(previousList, currentList);
        }
    };

    protected ListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        this.mDiffer = new AsyncListDiffer<>(new AdapterListUpdateCallback(this), new AsyncDifferConfig.Builder(diffCallback).build());
        this.mDiffer.addListListener(this.mListener);
    }

    protected ListAdapter(@NonNull AsyncDifferConfig<T> config) {
        this.mDiffer = new AsyncListDiffer<>(new AdapterListUpdateCallback(this), config);
        this.mDiffer.addListListener(this.mListener);
    }

    public void submitList(@Nullable List<T> list) {
        this.mDiffer.submitList(list);
    }

    public void submitList(@Nullable List<T> list, @Nullable Runnable commitCallback) {
        this.mDiffer.submitList(list, commitCallback);
    }

    /* access modifiers changed from: protected */
    public T getItem(int position) {
        return this.mDiffer.getCurrentList().get(position);
    }

    public int getItemCount() {
        return this.mDiffer.getCurrentList().size();
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mDiffer.getCurrentList();
    }

    public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
    }
}
