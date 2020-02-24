package android.support.p001v4.app;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.Preconditions;
import android.support.p001v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentController */
public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    @NonNull
    public static FragmentController createController(@NonNull FragmentHostCallback<?> callbacks) {
        return new FragmentController((FragmentHostCallback) Preconditions.checkNotNull(callbacks, "callbacks == null"));
    }

    private FragmentController(FragmentHostCallback<?> callbacks) {
        this.mHost = callbacks;
    }

    @NonNull
    public FragmentManager getSupportFragmentManager() {
        return this.mHost.mFragmentManager;
    }

    @SuppressLint({"UnknownNullness"})
    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        throw new UnsupportedOperationException("Loaders are managed separately from FragmentController, use LoaderManager.getInstance() to obtain a LoaderManager.");
    }

    @Nullable
    public Fragment findFragmentByWho(@NonNull String who) {
        return this.mHost.mFragmentManager.findFragmentByWho(who);
    }

    public int getActiveFragmentsCount() {
        return this.mHost.mFragmentManager.getActiveFragmentCount();
    }

    @NonNull
    public List<Fragment> getActiveFragments(@SuppressLint({"UnknownNullness"}) List<Fragment> list) {
        return this.mHost.mFragmentManager.getActiveFragments();
    }

    public void attachHost(@Nullable Fragment parent) {
        FragmentManagerImpl fragmentManagerImpl = this.mHost.mFragmentManager;
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        fragmentManagerImpl.attachController(fragmentHostCallback, fragmentHostCallback, parent);
    }

    @Nullable
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return this.mHost.mFragmentManager.onCreateView(parent, name, context, attrs);
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    @Nullable
    public Parcelable saveAllState() {
        return this.mHost.mFragmentManager.saveAllState();
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable state, @Nullable List<Fragment> nonConfigList) {
        this.mHost.mFragmentManager.restoreAllState(state, new FragmentManagerNonConfig(nonConfigList, null, null));
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable state, @Nullable FragmentManagerNonConfig nonConfig) {
        this.mHost.mFragmentManager.restoreAllState(state, nonConfig);
    }

    public void restoreSaveState(@Nullable Parcelable state) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            fragmentHostCallback.mFragmentManager.restoreSaveState(state);
            return;
        }
        throw new IllegalStateException("Your FragmentHostCallback must implement ViewModelStoreOwner to call restoreSaveState(). Call restoreAllState()  if you're still using retainNestedNonConfig().");
    }

    @Nullable
    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig nonconf = this.mHost.mFragmentManager.retainNonConfig();
        if (nonconf == null || nonconf.getFragments() == null) {
            return null;
        }
        return new ArrayList(nonconf.getFragments());
    }

    @Nullable
    @Deprecated
    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    @Deprecated
    public void dispatchReallyStop() {
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(isInMultiWindowMode);
    }

    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    public void dispatchConfigurationChanged(@NonNull Configuration newConfig) {
        this.mHost.mFragmentManager.dispatchConfigurationChanged(newConfig);
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    public boolean dispatchCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu, inflater);
    }

    public boolean dispatchPrepareOptionsMenu(@NonNull Menu menu) {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu);
    }

    public boolean dispatchOptionsItemSelected(@NonNull MenuItem item) {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(item);
    }

    public boolean dispatchContextItemSelected(@NonNull MenuItem item) {
        return this.mHost.mFragmentManager.dispatchContextItemSelected(item);
    }

    public void dispatchOptionsMenuClosed(@NonNull Menu menu) {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    @Deprecated
    public void doLoaderStart() {
    }

    @Deprecated
    public void doLoaderStop(boolean retain) {
    }

    @Deprecated
    public void doLoaderRetain() {
    }

    @Deprecated
    public void doLoaderDestroy() {
    }

    @Deprecated
    public void reportLoaderStart() {
    }

    @Nullable
    @Deprecated
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return null;
    }

    @Deprecated
    public void restoreLoaderNonConfig(@SuppressLint({"UnknownNullness"}) SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
    }

    @Deprecated
    public void dumpLoaders(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
    }
}
