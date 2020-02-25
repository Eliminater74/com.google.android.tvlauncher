package android.support.p001v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/* renamed from: android.support.v4.app.FragmentStatePagerAdapter */
public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapt";
    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();

    public FragmentStatePagerAdapter(@NonNull FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    @NonNull
    public abstract Fragment getItem(int i);

    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment.SavedState fss;
        Fragment f;
        if (this.mFragments.size() > position && (f = this.mFragments.get(position)) != null) {
            return f;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Fragment fragment = getItem(position);
        if (this.mSavedState.size() > position && (fss = this.mSavedState.get(position)) != null) {
            fragment.setInitialSavedState(fss);
        }
        while (this.mFragments.size() <= position) {
            this.mFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        this.mFragments.set(position, fragment);
        this.mCurTransaction.add(container.getId(), fragment);
        return fragment;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= position) {
            this.mSavedState.add(null);
        }
        this.mSavedState.set(position, fragment.isAdded() ? this.mFragmentManager.saveFragmentInstanceState(fragment) : null);
        this.mFragments.set(position, null);
        this.mCurTransaction.remove(fragment);
    }

    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        Fragment fragment2 = this.mCurrentPrimaryItem;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            this.mCurrentPrimaryItem = fragment;
        }
    }

    public void finishUpdate(@NonNull ViewGroup container) {
        FragmentTransaction fragmentTransaction = this.mCurTransaction;
        if (fragmentTransaction != null) {
            fragmentTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Nullable
    public Parcelable saveState() {
        Bundle state = null;
        if (this.mSavedState.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < this.mFragments.size(); i++) {
            Fragment f = this.mFragments.get(i);
            if (f != null && f.isAdded()) {
                if (state == null) {
                    state = new Bundle();
                }
                this.mFragmentManager.putFragment(state, "f" + i, f);
            }
        }
        return state;
    }

    /* JADX INFO: Multiple debug info for r2v2 java.util.Set<java.lang.String>: [D('keys' java.lang.Iterable<java.lang.String>), D('i' int)] */
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (fss != null) {
                for (Parcelable parcelable : fss) {
                    this.mSavedState.add((Fragment.SavedState) parcelable);
                }
            }
            for (String key : bundle.keySet()) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = this.mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (this.mFragments.size() <= index) {
                            this.mFragments.add(null);
                        }
                        f.setMenuVisibility(false);
                        this.mFragments.set(index, f);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }
}
