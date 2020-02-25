package android.support.p001v4.app;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.content.Loader;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.app.LoaderManager */
public abstract class LoaderManager {

    @NonNull
    public static <T extends LifecycleOwner & ViewModelStoreOwner> LoaderManager getInstance(@NonNull T owner) {
        return new LoaderManagerImpl(owner, ((ViewModelStoreOwner) owner).getViewModelStore());
    }

    public static void enableDebugLogging(boolean enabled) {
        LoaderManagerImpl.DEBUG = enabled;
    }

    @MainThread
    public abstract void destroyLoader(int i);

    @Deprecated
    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @Nullable
    public abstract <D> Loader<D> getLoader(int i);

    @MainThread
    @NonNull
    public abstract <D> Loader<D> initLoader(int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks<D> loaderCallbacks);

    public abstract void markForRedelivery();

    @MainThread
    @NonNull
    public abstract <D> Loader<D> restartLoader(int i, @Nullable Bundle bundle, @NonNull LoaderCallbacks<D> loaderCallbacks);

    public boolean hasRunningLoaders() {
        return false;
    }

    /* renamed from: android.support.v4.app.LoaderManager$LoaderCallbacks */
    public interface LoaderCallbacks<D> {
        @MainThread
        @NonNull
        Loader<D> onCreateLoader(int i, @Nullable Bundle bundle);

        @MainThread
        void onLoadFinished(@NonNull Loader<D> loader, D d);

        @MainThread
        void onLoaderReset(@NonNull Loader<D> loader);
    }
}
