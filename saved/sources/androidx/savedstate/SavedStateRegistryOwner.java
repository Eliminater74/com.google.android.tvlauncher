package androidx.savedstate;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

public interface SavedStateRegistryOwner extends LifecycleOwner {
    @NonNull
    SavedStateRegistry getSavedStateRegistry();
}
