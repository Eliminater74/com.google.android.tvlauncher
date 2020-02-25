package com.bumptech.glide.manager;

import android.support.annotation.NonNull;

import com.bumptech.glide.util.Util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

class ActivityFragmentLifecycle implements Lifecycle {
    private final Set<LifecycleListener> lifecycleListeners = Collections.newSetFromMap(new WeakHashMap());
    private boolean isDestroyed;
    private boolean isStarted;

    ActivityFragmentLifecycle() {
    }

    public void addListener(@NonNull LifecycleListener listener) {
        this.lifecycleListeners.add(listener);
        if (this.isDestroyed) {
            listener.onDestroy();
        } else if (this.isStarted) {
            listener.onStart();
        } else {
            listener.onStop();
        }
    }

    public void removeListener(@NonNull LifecycleListener listener) {
        this.lifecycleListeners.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        this.isStarted = true;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(this.lifecycleListeners)) {
            lifecycleListener.onStart();
        }
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        this.isStarted = false;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(this.lifecycleListeners)) {
            lifecycleListener.onStop();
        }
    }

    /* access modifiers changed from: package-private */
    public void onDestroy() {
        this.isDestroyed = true;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(this.lifecycleListeners)) {
            lifecycleListener.onDestroy();
        }
    }
}
