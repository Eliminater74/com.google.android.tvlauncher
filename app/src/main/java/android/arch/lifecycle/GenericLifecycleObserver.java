package android.arch.lifecycle;

import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public interface GenericLifecycleObserver extends LifecycleObserver {
    void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event);
}
