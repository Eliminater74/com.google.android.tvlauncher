package androidx.activity;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Iterator;

public final class OnBackPressedDispatcher {
    final ArrayDeque<OnBackPressedCallback> mOnBackPressedCallbacks = new ArrayDeque<>();

    OnBackPressedDispatcher() {
    }

    @MainThread
    public void addCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        addCancellableCallback(onBackPressedCallback);
    }

    /* access modifiers changed from: package-private */
    @MainThread
    @NonNull
    public Cancellable addCancellableCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        this.mOnBackPressedCallbacks.add(onBackPressedCallback);
        OnBackPressedCancellable cancellable = new OnBackPressedCancellable(onBackPressedCallback);
        onBackPressedCallback.addCancellable(cancellable);
        return cancellable;
    }

    @MainThread
    public void addCallback(@NonNull LifecycleOwner owner, @NonNull OnBackPressedCallback onBackPressedCallback) {
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            onBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(lifecycle, onBackPressedCallback));
        }
    }

    @MainThread
    public boolean hasEnabledCallbacks() {
        Iterator<OnBackPressedCallback> iterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isEnabled()) {
                return true;
            }
        }
        return false;
    }

    @MainThread
    public void onBackPressed() {
        Iterator<OnBackPressedCallback> iterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (iterator.hasNext()) {
            OnBackPressedCallback callback = iterator.next();
            if (callback.isEnabled()) {
                callback.handleOnBackPressed();
                return;
            }
        }
    }

    private class OnBackPressedCancellable implements Cancellable {
        private final OnBackPressedCallback mOnBackPressedCallback;

        OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.mOnBackPressedCallback = onBackPressedCallback;
        }

        public void cancel() {
            OnBackPressedDispatcher.this.mOnBackPressedCallbacks.remove(this.mOnBackPressedCallback);
            this.mOnBackPressedCallback.removeCancellable(this);
        }
    }

    private class LifecycleOnBackPressedCancellable implements GenericLifecycleObserver, Cancellable {
        @Nullable
        private Cancellable mCurrentCancellable;
        private final Lifecycle mLifecycle;
        private final OnBackPressedCallback mOnBackPressedCallback;

        LifecycleOnBackPressedCancellable(@NonNull Lifecycle lifecycle, @NonNull OnBackPressedCallback onBackPressedCallback) {
            this.mLifecycle = lifecycle;
            this.mOnBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.mCurrentCancellable = OnBackPressedDispatcher.this.addCancellableCallback(this.mOnBackPressedCallback);
            } else if (event == Lifecycle.Event.ON_STOP) {
                Cancellable cancellable = this.mCurrentCancellable;
                if (cancellable != null) {
                    cancellable.cancel();
                }
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                cancel();
            }
        }

        public void cancel() {
            this.mLifecycle.removeObserver(this);
            this.mOnBackPressedCallback.removeCancellable(this);
            Cancellable cancellable = this.mCurrentCancellable;
            if (cancellable != null) {
                cancellable.cancel();
                this.mCurrentCancellable = null;
            }
        }
    }
}
