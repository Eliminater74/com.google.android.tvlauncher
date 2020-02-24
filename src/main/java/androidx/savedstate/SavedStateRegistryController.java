package androidx.savedstate;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class SavedStateRegistryController {
    private final SavedStateRegistryOwner mOwner;
    private final SavedStateRegistry mRegistry = new SavedStateRegistry();

    private SavedStateRegistryController(SavedStateRegistryOwner owner) {
        this.mOwner = owner;
    }

    @NonNull
    public SavedStateRegistry getSavedStateRegistry() {
        return this.mRegistry;
    }

    @MainThread
    public void performRestore(@Nullable Bundle savedState) {
        Lifecycle lifecycle = this.mOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.INITIALIZED) {
            lifecycle.addObserver(new Recreator(this.mOwner));
            this.mRegistry.performRestore(lifecycle, savedState);
            return;
        }
        throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
    }

    @MainThread
    public void performSave(@NonNull Bundle outBundle) {
        this.mRegistry.performSave(outBundle);
    }

    @NonNull
    public static SavedStateRegistryController create(@NonNull SavedStateRegistryOwner owner) {
        return new SavedStateRegistryController(owner);
    }
}
