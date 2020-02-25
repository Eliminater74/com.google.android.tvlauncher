package android.support.p001v4.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* renamed from: android.support.v4.app.FragmentManagerViewModel */
class FragmentManagerViewModel extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            return new FragmentManagerViewModel(true);
        }
    };
    private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap<>();
    private final HashSet<Fragment> mRetainedFragments = new HashSet<>();
    private final boolean mStateAutomaticallySaved;
    private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap<>();
    private boolean mHasBeenCleared = false;
    private boolean mHasSavedSnapshot = false;

    FragmentManagerViewModel(boolean stateAutomaticallySaved) {
        this.mStateAutomaticallySaved = stateAutomaticallySaved;
    }

    @NonNull
    static FragmentManagerViewModel getInstance(ViewModelStore viewModelStore) {
        return (FragmentManagerViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(FragmentManagerViewModel.class);
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        if (FragmentManagerImpl.DEBUG) {
            Log.d("FragmentManager", "onCleared called for " + this);
        }
        this.mHasBeenCleared = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isCleared() {
        return this.mHasBeenCleared;
    }

    /* access modifiers changed from: package-private */
    public boolean addRetainedFragment(@NonNull Fragment fragment) {
        return this.mRetainedFragments.add(fragment);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Collection<Fragment> getRetainedFragments() {
        return this.mRetainedFragments;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDestroy(@NonNull Fragment fragment) {
        if (!this.mRetainedFragments.contains(fragment)) {
            return true;
        }
        if (this.mStateAutomaticallySaved) {
            return this.mHasBeenCleared;
        }
        return !this.mHasSavedSnapshot;
    }

    /* access modifiers changed from: package-private */
    public boolean removeRetainedFragment(@NonNull Fragment fragment) {
        return this.mRetainedFragments.remove(fragment);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentManagerViewModel getChildNonConfig(@NonNull Fragment f) {
        FragmentManagerViewModel childNonConfig = this.mChildNonConfigs.get(f.mWho);
        if (childNonConfig != null) {
            return childNonConfig;
        }
        FragmentManagerViewModel childNonConfig2 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
        this.mChildNonConfigs.put(f.mWho, childNonConfig2);
        return childNonConfig2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ViewModelStore getViewModelStore(@NonNull Fragment f) {
        ViewModelStore viewModelStore = this.mViewModelStores.get(f.mWho);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.mViewModelStores.put(f.mWho, viewModelStore2);
        return viewModelStore2;
    }

    /* access modifiers changed from: package-private */
    public void clearNonConfigState(@NonNull Fragment f) {
        if (FragmentManagerImpl.DEBUG) {
            Log.d("FragmentManager", "Clearing non-config state for " + f);
        }
        FragmentManagerViewModel childNonConfig = this.mChildNonConfigs.get(f.mWho);
        if (childNonConfig != null) {
            childNonConfig.onCleared();
            this.mChildNonConfigs.remove(f.mWho);
        }
        ViewModelStore viewModelStore = this.mViewModelStores.get(f.mWho);
        if (viewModelStore != null) {
            viewModelStore.clear();
            this.mViewModelStores.remove(f.mWho);
        }
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public void restoreFromSnapshot(@Nullable FragmentManagerNonConfig nonConfig) {
        this.mRetainedFragments.clear();
        this.mChildNonConfigs.clear();
        this.mViewModelStores.clear();
        if (nonConfig != null) {
            Collection<Fragment> fragments = nonConfig.getFragments();
            if (fragments != null) {
                this.mRetainedFragments.addAll(fragments);
            }
            Map<String, FragmentManagerNonConfig> childNonConfigs = nonConfig.getChildNonConfigs();
            if (childNonConfigs != null) {
                for (Map.Entry<String, FragmentManagerNonConfig> entry : childNonConfigs.entrySet()) {
                    FragmentManagerViewModel childViewModel = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
                    childViewModel.restoreFromSnapshot((FragmentManagerNonConfig) entry.getValue());
                    this.mChildNonConfigs.put(entry.getKey(), childViewModel);
                }
            }
            Map<String, ViewModelStore> viewModelStores = nonConfig.getViewModelStores();
            if (viewModelStores != null) {
                this.mViewModelStores.putAll(viewModelStores);
            }
        }
        this.mHasSavedSnapshot = false;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @Deprecated
    public FragmentManagerNonConfig getSnapshot() {
        if (this.mRetainedFragments.isEmpty() && this.mChildNonConfigs.isEmpty() && this.mViewModelStores.isEmpty()) {
            return null;
        }
        HashMap<String, FragmentManagerNonConfig> childNonConfigs = new HashMap<>();
        for (Map.Entry<String, FragmentManagerViewModel> entry : this.mChildNonConfigs.entrySet()) {
            FragmentManagerNonConfig childNonConfig = ((FragmentManagerViewModel) entry.getValue()).getSnapshot();
            if (childNonConfig != null) {
                childNonConfigs.put(entry.getKey(), childNonConfig);
            }
        }
        this.mHasSavedSnapshot = true;
        if (!this.mRetainedFragments.isEmpty() || !childNonConfigs.isEmpty() || !this.mViewModelStores.isEmpty()) {
            return new FragmentManagerNonConfig(new ArrayList(this.mRetainedFragments), childNonConfigs, new HashMap(this.mViewModelStores));
        }
        return null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FragmentManagerViewModel that = (FragmentManagerViewModel) o;
        if (!this.mRetainedFragments.equals(that.mRetainedFragments) || !this.mChildNonConfigs.equals(that.mChildNonConfigs) || !this.mViewModelStores.equals(that.mViewModelStores)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((this.mRetainedFragments.hashCode() * 31) + this.mChildNonConfigs.hashCode()) * 31) + this.mViewModelStores.hashCode();
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator<Fragment> fragmentIterator = this.mRetainedFragments.iterator();
        while (fragmentIterator.hasNext()) {
            sb.append(fragmentIterator.next());
            if (fragmentIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator<String> childNonConfigIterator = this.mChildNonConfigs.keySet().iterator();
        while (childNonConfigIterator.hasNext()) {
            sb.append(childNonConfigIterator.next());
            if (childNonConfigIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator<String> viewModelStoreIterator = this.mViewModelStores.keySet().iterator();
        while (viewModelStoreIterator.hasNext()) {
            sb.append(viewModelStoreIterator.next());
            if (viewModelStoreIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
