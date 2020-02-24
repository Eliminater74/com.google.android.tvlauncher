package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.TypedArrayUtils;
import android.support.p001v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.preference.Preference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PreferenceGroup extends Preference {
    private static final String TAG = "PreferenceGroup";
    private boolean mAttachedToHierarchy;
    private final Runnable mClearRecycleCacheRunnable;
    private int mCurrentPreferenceOrder;
    private final Handler mHandler;
    final SimpleArrayMap<String, Long> mIdRecycleCache;
    private int mInitialExpandedChildrenCount;
    private OnExpandButtonClickListener mOnExpandButtonClickListener;
    private boolean mOrderingAsAdded;
    private List<Preference> mPreferences;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public interface OnExpandButtonClickListener {
        void onExpandButtonClick();
    }

    public interface PreferencePositionCallback {
        int getPreferenceAdapterPosition(Preference preference);

        int getPreferenceAdapterPosition(String str);
    }

    public PreferenceGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIdRecycleCache = new SimpleArrayMap<>();
        this.mHandler = new Handler();
        this.mOrderingAsAdded = true;
        this.mCurrentPreferenceOrder = 0;
        this.mAttachedToHierarchy = false;
        this.mInitialExpandedChildrenCount = Integer.MAX_VALUE;
        this.mOnExpandButtonClickListener = null;
        this.mClearRecycleCacheRunnable = new Runnable() {
            public void run() {
                synchronized (this) {
                    PreferenceGroup.this.mIdRecycleCache.clear();
                }
            }
        };
        this.mPreferences = new ArrayList();
        TypedArray a = context.obtainStyledAttributes(attrs, C0731R.styleable.PreferenceGroup, defStyleAttr, defStyleRes);
        this.mOrderingAsAdded = TypedArrayUtils.getBoolean(a, C0731R.styleable.PreferenceGroup_orderingFromXml, C0731R.styleable.PreferenceGroup_orderingFromXml, true);
        if (a.hasValue(C0731R.styleable.PreferenceGroup_initialExpandedChildrenCount)) {
            setInitialExpandedChildrenCount(TypedArrayUtils.getInt(a, C0731R.styleable.PreferenceGroup_initialExpandedChildrenCount, C0731R.styleable.PreferenceGroup_initialExpandedChildrenCount, Integer.MAX_VALUE));
        }
        a.recycle();
    }

    public PreferenceGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PreferenceGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setOrderingAsAdded(boolean orderingAsAdded) {
        this.mOrderingAsAdded = orderingAsAdded;
    }

    public boolean isOrderingAsAdded() {
        return this.mOrderingAsAdded;
    }

    public void setInitialExpandedChildrenCount(int expandedCount) {
        if (expandedCount != Integer.MAX_VALUE && !hasKey()) {
            Log.e(TAG, getClass().getSimpleName() + " should have a key defined if it contains an expandable preference");
        }
        this.mInitialExpandedChildrenCount = expandedCount;
    }

    public int getInitialExpandedChildrenCount() {
        return this.mInitialExpandedChildrenCount;
    }

    public void addItemFromInflater(Preference preference) {
        addPreference(preference);
    }

    public int getPreferenceCount() {
        return this.mPreferences.size();
    }

    public Preference getPreference(int index) {
        return this.mPreferences.get(index);
    }

    public boolean addPreference(Preference preference) {
        long id;
        if (this.mPreferences.contains(preference)) {
            return true;
        }
        if (preference.getKey() != null) {
            PreferenceGroup root = this;
            while (root.getParent() != null) {
                root = root.getParent();
            }
            String key = preference.getKey();
            if (root.findPreference(key) != null) {
                Log.e(TAG, "Found duplicated key: \"" + key + "\". This can cause unintended behaviour, please use unique keys for every preference.");
            }
        }
        if (preference.getOrder() == Integer.MAX_VALUE) {
            if (this.mOrderingAsAdded) {
                int i = this.mCurrentPreferenceOrder;
                this.mCurrentPreferenceOrder = i + 1;
                preference.setOrder(i);
            }
            if (preference instanceof PreferenceGroup) {
                ((PreferenceGroup) preference).setOrderingAsAdded(this.mOrderingAsAdded);
            }
        }
        int insertionIndex = Collections.binarySearch(this.mPreferences, preference);
        if (insertionIndex < 0) {
            insertionIndex = (insertionIndex * -1) - 1;
        }
        if (!onPrepareAddPreference(preference)) {
            return false;
        }
        synchronized (this) {
            this.mPreferences.add(insertionIndex, preference);
        }
        PreferenceManager preferenceManager = getPreferenceManager();
        String key2 = preference.getKey();
        if (key2 == null || !this.mIdRecycleCache.containsKey(key2)) {
            id = preferenceManager.getNextId();
        } else {
            id = this.mIdRecycleCache.get(key2).longValue();
            this.mIdRecycleCache.remove(key2);
        }
        preference.onAttachedToHierarchy(preferenceManager, id);
        preference.assignParent(this);
        if (this.mAttachedToHierarchy) {
            preference.onAttached();
        }
        notifyHierarchyChanged();
        return true;
    }

    public boolean removePreference(Preference preference) {
        boolean returnValue = removePreferenceInt(preference);
        notifyHierarchyChanged();
        return returnValue;
    }

    public boolean removePreferenceRecursively(@NonNull CharSequence key) {
        Preference preference = findPreference(key);
        if (preference == null) {
            return false;
        }
        return preference.getParent().removePreference(preference);
    }

    private boolean removePreferenceInt(Preference preference) {
        boolean success;
        synchronized (this) {
            preference.onPrepareForRemoval();
            if (preference.getParent() == this) {
                preference.assignParent(null);
            }
            success = this.mPreferences.remove(preference);
            if (success) {
                String key = preference.getKey();
                if (key != null) {
                    this.mIdRecycleCache.put(key, Long.valueOf(preference.getId()));
                    this.mHandler.removeCallbacks(this.mClearRecycleCacheRunnable);
                    this.mHandler.post(this.mClearRecycleCacheRunnable);
                }
                if (this.mAttachedToHierarchy) {
                    preference.onDetached();
                }
            }
        }
        return success;
    }

    public void removeAll() {
        synchronized (this) {
            List<Preference> preferences = this.mPreferences;
            for (int i = preferences.size() - 1; i >= 0; i--) {
                removePreferenceInt(preferences.get(0));
            }
        }
        notifyHierarchyChanged();
    }

    /* access modifiers changed from: protected */
    public boolean onPrepareAddPreference(Preference preference) {
        preference.onParentChanged(this, shouldDisableDependents());
        return true;
    }

    @Nullable
    public <T extends Preference> T findPreference(@NonNull CharSequence key) {
        T returnedPreference;
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        } else if (TextUtils.equals(getKey(), key)) {
            return this;
        } else {
            int preferenceCount = getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                T preference = getPreference(i);
                if (TextUtils.equals(preference.getKey(), key)) {
                    return preference;
                }
                if ((preference instanceof PreferenceGroup) && (returnedPreference = ((PreferenceGroup) preference).findPreference(key)) != null) {
                    return returnedPreference;
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isOnSameScreenAsChildren() {
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean isAttached() {
        return this.mAttachedToHierarchy;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOnExpandButtonClickListener(@Nullable OnExpandButtonClickListener onExpandButtonClickListener) {
        this.mOnExpandButtonClickListener = onExpandButtonClickListener;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public OnExpandButtonClickListener getOnExpandButtonClickListener() {
        return this.mOnExpandButtonClickListener;
    }

    public void onAttached() {
        super.onAttached();
        this.mAttachedToHierarchy = true;
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onAttached();
        }
    }

    public void onDetached() {
        super.onDetached();
        this.mAttachedToHierarchy = false;
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onDetached();
        }
    }

    public void notifyDependencyChange(boolean disableDependents) {
        super.notifyDependencyChange(disableDependents);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onParentChanged(this, disableDependents);
        }
    }

    /* access modifiers changed from: package-private */
    public void sortPreferences() {
        synchronized (this) {
            Collections.sort(this.mPreferences);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(Bundle container) {
        super.dispatchSaveInstanceState(container);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).dispatchSaveInstanceState(container);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(Bundle container) {
        super.dispatchRestoreInstanceState(container);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).dispatchRestoreInstanceState(container);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mInitialExpandedChildrenCount);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState groupState = (SavedState) state;
        this.mInitialExpandedChildrenCount = groupState.mInitialExpandedChildrenCount;
        super.onRestoreInstanceState(groupState.getSuperState());
    }

    static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int mInitialExpandedChildrenCount;

        SavedState(Parcel source) {
            super(source);
            this.mInitialExpandedChildrenCount = source.readInt();
        }

        SavedState(Parcelable superState, int initialExpandedChildrenCount) {
            super(superState);
            this.mInitialExpandedChildrenCount = initialExpandedChildrenCount;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mInitialExpandedChildrenCount);
        }
    }
}
