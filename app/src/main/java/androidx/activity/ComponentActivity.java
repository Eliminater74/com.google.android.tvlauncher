package androidx.activity;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ReportFragment;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ContentView;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.SupportActivity;
import android.view.View;
import android.view.Window;

import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

public class ComponentActivity extends SupportActivity implements LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
    private final LifecycleRegistry mLifecycleRegistry;
    private final OnBackPressedDispatcher mOnBackPressedDispatcher;
    private final SavedStateRegistryController mSavedStateRegistryController;
    @LayoutRes
    private int mContentLayoutId;
    private ViewModelStore mViewModelStore;

    public ComponentActivity() {
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
        this.mOnBackPressedDispatcher = new OnBackPressedDispatcher();
        if (getLifecycle() != null) {
            if (Build.VERSION.SDK_INT >= 19) {
                getLifecycle().addObserver(new GenericLifecycleObserver() {
                    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                        if (event == Lifecycle.Event.ON_STOP) {
                            Window window = ComponentActivity.this.getWindow();
                            View decor = window != null ? window.peekDecorView() : null;
                            if (decor != null) {
                                decor.cancelPendingInputEvents();
                            }
                        }
                    }
                });
            }
            getLifecycle().addObserver(new GenericLifecycleObserver() {
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY && !ComponentActivity.this.isChangingConfigurations()) {
                        ComponentActivity.this.getViewModelStore().clear();
                    }
                }
            });
            if (19 <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT <= 23) {
                getLifecycle().addObserver(new ImmLeaksCleaner(this));
                return;
            }
            return;
        }
        throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
    }

    @ContentView
    public ComponentActivity(@LayoutRes int contentLayoutId) {
        this();
        this.mContentLayoutId = contentLayoutId;
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedStateRegistryController.performRestore(savedInstanceState);
        ReportFragment.injectIfNeededIn(this);
        int i = this.mContentLayoutId;
        if (i != 0) {
            setContentView(i);
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Lifecycle lifecycle = getLifecycle();
        if (lifecycle instanceof LifecycleRegistry) {
            ((LifecycleRegistry) lifecycle).markState(Lifecycle.State.CREATED);
        }
        super.onSaveInstanceState(outState);
        this.mSavedStateRegistryController.performSave(outState);
    }

    @Nullable
    public final Object onRetainNonConfigurationInstance() {
        NonConfigurationInstances nc;
        Object custom = onRetainCustomNonConfigurationInstance();
        ViewModelStore viewModelStore = this.mViewModelStore;
        if (viewModelStore == null && (nc = (NonConfigurationInstances) getLastNonConfigurationInstance()) != null) {
            viewModelStore = nc.viewModelStore;
        }
        if (viewModelStore == null && custom == null) {
            return null;
        }
        NonConfigurationInstances nci = new NonConfigurationInstances();
        nci.custom = custom;
        nci.viewModelStore = viewModelStore;
        return nci;
    }

    @Nullable
    @Deprecated
    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    @Nullable
    @Deprecated
    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nc = (NonConfigurationInstances) getLastNonConfigurationInstance();
        if (nc != null) {
            return nc.custom;
        }
        return null;
    }

    @NonNull
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @NonNull
    public ViewModelStore getViewModelStore() {
        if (getApplication() != null) {
            if (this.mViewModelStore == null) {
                NonConfigurationInstances nc = (NonConfigurationInstances) getLastNonConfigurationInstance();
                if (nc != null) {
                    this.mViewModelStore = nc.viewModelStore;
                }
                if (this.mViewModelStore == null) {
                    this.mViewModelStore = new ViewModelStore();
                }
            }
            return this.mViewModelStore;
        }
        throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
    }

    public void onBackPressed() {
        if (this.mOnBackPressedDispatcher.hasEnabledCallbacks()) {
            this.mOnBackPressedDispatcher.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @NonNull
    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        return this.mOnBackPressedDispatcher;
    }

    @Deprecated
    public void addOnBackPressedCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Deprecated
    public void addOnBackPressedCallback(@NonNull LifecycleOwner owner, @NonNull OnBackPressedCallback onBackPressedCallback) {
        getOnBackPressedDispatcher().addCallback(owner, onBackPressedCallback);
    }

    @Deprecated
    public void removeOnBackPressedCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        onBackPressedCallback.remove();
    }

    @NonNull
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    static final class NonConfigurationInstances {
        Object custom;
        ViewModelStore viewModelStore;

        NonConfigurationInstances() {
        }
    }
}
