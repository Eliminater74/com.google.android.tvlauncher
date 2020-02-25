package androidx.leanback.widget;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.p001v4.util.LruCache;
import android.util.SparseArray;
import android.view.View;

import java.util.Map;

class ViewsStateBundle {
    public static final int LIMIT_DEFAULT = 100;
    public static final int UNLIMITED = Integer.MAX_VALUE;
    private LruCache<String, SparseArray<Parcelable>> mChildStates;
    private int mLimitNumber = 100;
    private int mSavePolicy = 0;

    static String getSaveStatesKey(int id) {
        return Integer.toString(id);
    }

    public void clear() {
        LruCache<String, SparseArray<Parcelable>> lruCache = this.mChildStates;
        if (lruCache != null) {
            lruCache.evictAll();
        }
    }

    public void remove(int id) {
        LruCache<String, SparseArray<Parcelable>> lruCache = this.mChildStates;
        if (lruCache != null && lruCache.size() != 0) {
            this.mChildStates.remove(getSaveStatesKey(id));
        }
    }

    public final Bundle saveAsBundle() {
        LruCache<String, SparseArray<Parcelable>> lruCache = this.mChildStates;
        if (lruCache == null || lruCache.size() == 0) {
            return null;
        }
        Map<String, SparseArray<Parcelable>> snapshot = this.mChildStates.snapshot();
        Bundle bundle = new Bundle();
        for (Map.Entry<String, SparseArray<Parcelable>> e : snapshot.entrySet()) {
            bundle.putSparseParcelableArray((String) e.getKey(), (SparseArray) e.getValue());
        }
        return bundle;
    }

    public final void loadFromBundle(Bundle savedBundle) {
        LruCache<String, SparseArray<Parcelable>> lruCache = this.mChildStates;
        if (lruCache != null && savedBundle != null) {
            lruCache.evictAll();
            for (String key : savedBundle.keySet()) {
                this.mChildStates.put(key, savedBundle.getSparseParcelableArray(key));
            }
        }
    }

    public final int getSavePolicy() {
        return this.mSavePolicy;
    }

    public final void setSavePolicy(int savePolicy) {
        this.mSavePolicy = savePolicy;
        applyPolicyChanges();
    }

    public final int getLimitNumber() {
        return this.mLimitNumber;
    }

    public final void setLimitNumber(int limitNumber) {
        this.mLimitNumber = limitNumber;
        applyPolicyChanges();
    }

    /* access modifiers changed from: protected */
    public void applyPolicyChanges() {
        int i = this.mSavePolicy;
        if (i == 2) {
            if (this.mLimitNumber > 0) {
                LruCache<String, SparseArray<Parcelable>> lruCache = this.mChildStates;
                if (lruCache == null || lruCache.maxSize() != this.mLimitNumber) {
                    this.mChildStates = new LruCache<>(this.mLimitNumber);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException();
        } else if (i == 3 || i == 1) {
            LruCache<String, SparseArray<Parcelable>> lruCache2 = this.mChildStates;
            if (lruCache2 == null || lruCache2.maxSize() != Integer.MAX_VALUE) {
                this.mChildStates = new LruCache<>(Integer.MAX_VALUE);
            }
        } else {
            this.mChildStates = null;
        }
    }

    public final void loadView(View view, int id) {
        SparseArray<Parcelable> container;
        if (this.mChildStates != null && (container = this.mChildStates.remove(getSaveStatesKey(id))) != null) {
            view.restoreHierarchyState(container);
        }
    }

    /* access modifiers changed from: protected */
    public final void saveViewUnchecked(View view, int id) {
        if (this.mChildStates != null) {
            String key = getSaveStatesKey(id);
            SparseArray<Parcelable> container = new SparseArray<>();
            view.saveHierarchyState(container);
            this.mChildStates.put(key, container);
        }
    }

    public final Bundle saveOnScreenView(Bundle bundle, View view, int id) {
        if (this.mSavePolicy != 0) {
            String key = getSaveStatesKey(id);
            SparseArray<Parcelable> container = new SparseArray<>();
            view.saveHierarchyState(container);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(key, container);
        }
        return bundle;
    }

    public final void saveOffscreenView(View view, int id) {
        int i = this.mSavePolicy;
        if (i == 1) {
            remove(id);
        } else if (i == 2 || i == 3) {
            saveViewUnchecked(view, id);
        }
    }
}
