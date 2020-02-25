package android.support.p001v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.view.View;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentManager */
public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static final FragmentFactory DEFAULT_FACTORY = new FragmentFactory();
    private FragmentFactory mFragmentFactory = null;

    public static void enableDebugLogging(boolean enabled) {
        FragmentManagerImpl.DEBUG = enabled;
    }

    public abstract void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener);

    @NonNull
    public abstract FragmentTransaction beginTransaction();

    public abstract void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr);

    public abstract boolean executePendingTransactions();

    @Nullable
    public abstract Fragment findFragmentById(@IdRes int i);

    @Nullable
    public abstract Fragment findFragmentByTag(@Nullable String str);

    @NonNull
    public abstract BackStackEntry getBackStackEntryAt(int i);

    public abstract int getBackStackEntryCount();

    @Nullable
    public abstract Fragment getFragment(@NonNull Bundle bundle, @NonNull String str);

    @NonNull
    public abstract List<Fragment> getFragments();

    @Nullable
    public abstract Fragment getPrimaryNavigationFragment();

    public abstract boolean isDestroyed();

    public abstract boolean isStateSaved();

    public abstract void popBackStack();

    public abstract void popBackStack(int i, int i2);

    public abstract void popBackStack(@Nullable String str, int i);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int i, int i2);

    public abstract boolean popBackStackImmediate(@Nullable String str, int i);

    public abstract void putFragment(@NonNull Bundle bundle, @NonNull String str, @NonNull Fragment fragment);

    public abstract void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z);

    public abstract void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener);

    @Nullable
    public abstract Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment fragment);

    public abstract void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    @NonNull
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    @NonNull
    public FragmentFactory getFragmentFactory() {
        if (this.mFragmentFactory == null) {
            this.mFragmentFactory = DEFAULT_FACTORY;
        }
        return this.mFragmentFactory;
    }

    public void setFragmentFactory(@NonNull FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    /* renamed from: android.support.v4.app.FragmentManager$BackStackEntry */
    public interface BackStackEntry {
        @Nullable
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        int getBreadCrumbShortTitleRes();

        @Nullable
        CharSequence getBreadCrumbTitle();

        @StringRes
        int getBreadCrumbTitleRes();

        int getId();

        @Nullable
        String getName();
    }

    /* renamed from: android.support.v4.app.FragmentManager$OnBackStackChangedListener */
    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    /* renamed from: android.support.v4.app.FragmentManager$FragmentLifecycleCallbacks */
    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        }

        public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        }

        public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        }

        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }
    }
}
