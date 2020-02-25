package android.support.p004v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p004v7.util.DiffUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* renamed from: android.support.v7.recyclerview.extensions.AsyncDifferConfig */
public final class AsyncDifferConfig<T> {
    @NonNull
    private final Executor mBackgroundThreadExecutor;
    @NonNull
    private final DiffUtil.ItemCallback<T> mDiffCallback;
    @Nullable
    private final Executor mMainThreadExecutor;

    AsyncDifferConfig(@Nullable Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        this.mMainThreadExecutor = mainThreadExecutor;
        this.mBackgroundThreadExecutor = backgroundThreadExecutor;
        this.mDiffCallback = diffCallback;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    @NonNull
    public DiffUtil.ItemCallback<T> getDiffCallback() {
        return this.mDiffCallback;
    }

    /* renamed from: android.support.v7.recyclerview.extensions.AsyncDifferConfig$Builder */
    public static final class Builder<T> {
        private static final Object sExecutorLock = new Object();
        private static Executor sDiffExecutor = null;
        private final DiffUtil.ItemCallback<T> mDiffCallback;
        private Executor mBackgroundThreadExecutor;
        @Nullable
        private Executor mMainThreadExecutor;

        public Builder(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
            this.mDiffCallback = diffCallback;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @NonNull
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        @NonNull
        public AsyncDifferConfig<T> build() {
            if (this.mBackgroundThreadExecutor == null) {
                synchronized (sExecutorLock) {
                    if (sDiffExecutor == null) {
                        sDiffExecutor = Executors.newFixedThreadPool(2);
                    }
                }
                this.mBackgroundThreadExecutor = sDiffExecutor;
            }
            return new AsyncDifferConfig<>(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }
    }
}
