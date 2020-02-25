package android.support.p004v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p004v7.util.AdapterListUpdateCallback;
import android.support.p004v7.util.DiffUtil;
import android.support.p004v7.util.ListUpdateCallback;
import android.support.p004v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* renamed from: android.support.v7.recyclerview.extensions.AsyncListDiffer */
public class AsyncListDiffer<T> {
    private static final Executor sMainThreadExecutor = new MainThreadExecutor();
    final AsyncDifferConfig<T> mConfig;
    private final List<ListListener<T>> mListeners;
    private final ListUpdateCallback mUpdateCallback;
    Executor mMainThreadExecutor;
    int mMaxScheduledGeneration;
    @Nullable
    private List<T> mList;
    @NonNull
    private List<T> mReadOnlyList;

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        this(new AdapterListUpdateCallback(adapter), new AsyncDifferConfig.Builder(diffCallback).build());
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> config) {
        this.mListeners = new CopyOnWriteArrayList();
        this.mReadOnlyList = Collections.emptyList();
        this.mUpdateCallback = listUpdateCallback;
        this.mConfig = config;
        if (config.getMainThreadExecutor() != null) {
            this.mMainThreadExecutor = config.getMainThreadExecutor();
        } else {
            this.mMainThreadExecutor = sMainThreadExecutor;
        }
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mReadOnlyList;
    }

    public void submitList(@Nullable List<T> newList) {
        submitList(newList, null);
    }

    public void submitList(@Nullable List<T> newList, @Nullable Runnable commitCallback) {
        final int runGeneration = this.mMaxScheduledGeneration + 1;
        this.mMaxScheduledGeneration = runGeneration;
        List<T> list = this.mList;
        if (newList != list) {
            List<T> previousList = this.mReadOnlyList;
            if (newList == null) {
                int countRemoved = list.size();
                this.mList = null;
                this.mReadOnlyList = Collections.emptyList();
                this.mUpdateCallback.onRemoved(0, countRemoved);
                onCurrentListChanged(previousList, commitCallback);
            } else if (list == null) {
                this.mList = newList;
                this.mReadOnlyList = Collections.unmodifiableList(newList);
                this.mUpdateCallback.onInserted(0, newList.size());
                onCurrentListChanged(previousList, commitCallback);
            } else {
                final List<T> list2 = this.mList;
                final List<T> list3 = newList;
                final Runnable runnable = commitCallback;
                this.mConfig.getBackgroundThreadExecutor().execute(new Runnable() {
                    public void run() {
                        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                            public int getOldListSize() {
                                return list2.size();
                            }

                            public int getNewListSize() {
                                return list3.size();
                            }

                            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                                T oldItem = list2.get(oldItemPosition);
                                T newItem = list3.get(newItemPosition);
                                if (oldItem == null || newItem == null) {
                                    return oldItem == null && newItem == null;
                                }
                                return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(oldItem, newItem);
                            }

                            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                                T oldItem = list2.get(oldItemPosition);
                                T newItem = list3.get(newItemPosition);
                                if (oldItem != null && newItem != null) {
                                    return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(oldItem, newItem);
                                }
                                if (oldItem == null && newItem == null) {
                                    return true;
                                }
                                throw new AssertionError();
                            }

                            @Nullable
                            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                                T oldItem = list2.get(oldItemPosition);
                                T newItem = list3.get(newItemPosition);
                                if (oldItem != null && newItem != null) {
                                    return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(oldItem, newItem);
                                }
                                throw new AssertionError();
                            }
                        });
                        AsyncListDiffer.this.mMainThreadExecutor.execute(new Runnable() {
                            public void run() {
                                if (AsyncListDiffer.this.mMaxScheduledGeneration == runGeneration) {
                                    AsyncListDiffer.this.latchList(list3, result, runnable);
                                }
                            }
                        });
                    }
                });
            }
        } else if (commitCallback != null) {
            commitCallback.run();
        }
    }

    /* access modifiers changed from: package-private */
    public void latchList(@NonNull List<T> newList, @NonNull DiffUtil.DiffResult diffResult, @Nullable Runnable commitCallback) {
        List<T> previousList = this.mReadOnlyList;
        this.mList = newList;
        this.mReadOnlyList = Collections.unmodifiableList(newList);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
        onCurrentListChanged(previousList, commitCallback);
    }

    private void onCurrentListChanged(@NonNull List<T> previousList, @Nullable Runnable commitCallback) {
        for (ListListener<T> listener : this.mListeners) {
            listener.onCurrentListChanged(previousList, this.mReadOnlyList);
        }
        if (commitCallback != null) {
            commitCallback.run();
        }
    }

    public void addListListener(@NonNull ListListener<T> listener) {
        this.mListeners.add(listener);
    }

    public void removeListListener(@NonNull ListListener<T> listener) {
        this.mListeners.remove(listener);
    }

    /* renamed from: android.support.v7.recyclerview.extensions.AsyncListDiffer$ListListener */
    public interface ListListener<T> {
        void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2);
    }

    /* renamed from: android.support.v7.recyclerview.extensions.AsyncListDiffer$MainThreadExecutor */
    private static class MainThreadExecutor implements Executor {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        MainThreadExecutor() {
        }

        public void execute(@NonNull Runnable command) {
            this.mHandler.post(command);
        }
    }
}
