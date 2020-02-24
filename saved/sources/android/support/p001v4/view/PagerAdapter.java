package android.support.p001v4.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.view.PagerAdapter */
public abstract class PagerAdapter {
    public static final int POSITION_NONE = -2;
    public static final int POSITION_UNCHANGED = -1;
    private final DataSetObservable mObservable = new DataSetObservable();
    private DataSetObserver mViewPagerObserver;

    public abstract int getCount();

    public abstract boolean isViewFromObject(@NonNull View view, @NonNull Object obj);

    public void startUpdate(@NonNull ViewGroup container) {
        startUpdate((View) container);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: android.support.v4.view.PagerAdapter.instantiateItem(android.view.View, int):java.lang.Object
     arg types: [android.view.ViewGroup, int]
     candidates:
      android.support.v4.view.PagerAdapter.instantiateItem(android.view.ViewGroup, int):java.lang.Object
      android.support.v4.view.PagerAdapter.instantiateItem(android.view.View, int):java.lang.Object */
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return instantiateItem((View) container, position);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: android.support.v4.view.PagerAdapter.destroyItem(android.view.View, int, java.lang.Object):void
     arg types: [android.view.ViewGroup, int, java.lang.Object]
     candidates:
      android.support.v4.view.PagerAdapter.destroyItem(android.view.ViewGroup, int, java.lang.Object):void
      android.support.v4.view.PagerAdapter.destroyItem(android.view.View, int, java.lang.Object):void */
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        destroyItem((View) container, position, object);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: android.support.v4.view.PagerAdapter.setPrimaryItem(android.view.View, int, java.lang.Object):void
     arg types: [android.view.ViewGroup, int, java.lang.Object]
     candidates:
      android.support.v4.view.PagerAdapter.setPrimaryItem(android.view.ViewGroup, int, java.lang.Object):void
      android.support.v4.view.PagerAdapter.setPrimaryItem(android.view.View, int, java.lang.Object):void */
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        setPrimaryItem((View) container, position, object);
    }

    public void finishUpdate(@NonNull ViewGroup container) {
        finishUpdate((View) container);
    }

    @Deprecated
    public void startUpdate(@NonNull View container) {
    }

    @Deprecated
    @NonNull
    public Object instantiateItem(@NonNull View container, int position) {
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    @Deprecated
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    @Deprecated
    public void setPrimaryItem(@NonNull View container, int position, @NonNull Object object) {
    }

    @Deprecated
    public void finishUpdate(@NonNull View container) {
    }

    @Nullable
    public Parcelable saveState() {
        return null;
    }

    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
    }

    public int getItemPosition(@NonNull Object object) {
        return -1;
    }

    public void notifyDataSetChanged() {
        synchronized (this) {
            if (this.mViewPagerObserver != null) {
                this.mViewPagerObserver.onChanged();
            }
        }
        this.mObservable.notifyChanged();
    }

    public void registerDataSetObserver(@NonNull DataSetObserver observer) {
        this.mObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
        this.mObservable.unregisterObserver(observer);
    }

    /* access modifiers changed from: package-private */
    public void setViewPagerObserver(DataSetObserver observer) {
        synchronized (this) {
            this.mViewPagerObserver = observer;
        }
    }

    @Nullable
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public float getPageWidth(int position) {
        return 1.0f;
    }
}
