package androidx.savedstate;

import android.annotation.SuppressLint;
import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Iterator;
import java.util.Map;

@SuppressLint({"RestrictedApi"})
public final class SavedStateRegistry {
    private static final String SAVED_COMPONENTS_KEY = "android.arch.lifecycle.BundlableSavedStateRegistry.key";
    boolean mAllowingSavingState = true;
    private SafeIterableMap<String, SavedStateProvider> mComponents = new SafeIterableMap<>();
    private Recreator.SavedStateProvider mRecreatorProvider;
    private boolean mRestored;
    @Nullable
    private Bundle mRestoredState;

    SavedStateRegistry() {
    }

    @Nullable
    @MainThread
    public Bundle consumeRestoredStateForKey(@NonNull String key) {
        if (this.mRestored) {
            Bundle bundle = this.mRestoredState;
            if (bundle == null) {
                return null;
            }
            Bundle result = bundle.getBundle(key);
            this.mRestoredState.remove(key);
            if (this.mRestoredState.isEmpty()) {
                this.mRestoredState = null;
            }
            return result;
        }
        throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
    }

    @MainThread
    public void registerSavedStateProvider(@NonNull String key, @NonNull SavedStateProvider provider) {
        if (this.mComponents.putIfAbsent(key, provider) != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
        }
    }

    @MainThread
    public void unregisterSavedStateProvider(@NonNull String key) {
        this.mComponents.remove(key);
    }

    @MainThread
    public boolean isRestored() {
        return this.mRestored;
    }

    @MainThread
    public void runOnNextRecreation(@NonNull Class<? extends AutoRecreated> clazz) {
        if (this.mAllowingSavingState) {
            if (this.mRecreatorProvider == null) {
                this.mRecreatorProvider = new Recreator.SavedStateProvider(this);
            }
            try {
                clazz.getDeclaredConstructor(new Class[0]);
                this.mRecreatorProvider.add(clazz.getName());
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Class" + clazz.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
            }
        } else {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void performRestore(@NonNull Lifecycle lifecycle, @Nullable Bundle savedState) {
        if (!this.mRestored) {
            if (savedState != null) {
                this.mRestoredState = savedState.getBundle(SAVED_COMPONENTS_KEY);
            }
            lifecycle.addObserver(new GenericLifecycleObserver() {
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_START) {
                        SavedStateRegistry.this.mAllowingSavingState = true;
                    } else if (event == Lifecycle.Event.ON_STOP) {
                        SavedStateRegistry.this.mAllowingSavingState = false;
                    }
                }
            });
            this.mRestored = true;
            return;
        }
        throw new IllegalStateException("SavedStateRegistry was already restored.");
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void performSave(@NonNull Bundle outBundle) {
        Bundle components = new Bundle();
        Bundle bundle = this.mRestoredState;
        if (bundle != null) {
            components.putAll(bundle);
        }
        Iterator<Map.Entry<String, SavedStateProvider>> it = this.mComponents.iteratorWithAdditions();
        while (it.hasNext()) {
            Map.Entry<String, SavedStateProvider> entry1 = it.next();
            components.putBundle((String) entry1.getKey(), ((SavedStateProvider) entry1.getValue()).saveState());
        }
        outBundle.putBundle(SAVED_COMPONENTS_KEY, components);
    }

    public interface AutoRecreated {
        void onRecreated(@NonNull SavedStateRegistryOwner savedStateRegistryOwner);
    }

    public interface SavedStateProvider {
        @NonNull
        Bundle saveState();
    }
}
