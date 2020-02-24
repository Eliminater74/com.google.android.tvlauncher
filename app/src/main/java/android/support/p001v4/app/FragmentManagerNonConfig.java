package android.support.p001v4.app;

import android.arch.lifecycle.ViewModelStore;
import android.support.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

@Deprecated
/* renamed from: android.support.v4.app.FragmentManagerNonConfig */
public class FragmentManagerNonConfig {
    @Nullable
    private final Map<String, FragmentManagerNonConfig> mChildNonConfigs;
    @Nullable
    private final Collection<Fragment> mFragments;
    @Nullable
    private final Map<String, ViewModelStore> mViewModelStores;

    FragmentManagerNonConfig(@Nullable Collection<Fragment> fragments, @Nullable Map<String, FragmentManagerNonConfig> childNonConfigs, @Nullable Map<String, ViewModelStore> viewModelStores) {
        this.mFragments = fragments;
        this.mChildNonConfigs = childNonConfigs;
        this.mViewModelStores = viewModelStores;
    }

    /* access modifiers changed from: package-private */
    public boolean isRetaining(Fragment f) {
        Collection<Fragment> collection = this.mFragments;
        if (collection == null) {
            return false;
        }
        return collection.contains(f);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Collection<Fragment> getFragments() {
        return this.mFragments;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Map<String, FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Map<String, ViewModelStore> getViewModelStores() {
        return this.mViewModelStores;
    }
}
