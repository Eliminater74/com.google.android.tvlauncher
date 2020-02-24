package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.content.res.AppCompatResources;
import android.support.p004v7.util.DiffUtil;
import android.support.p004v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class PreferenceGroupAdapter extends RecyclerView.Adapter<PreferenceViewHolder> implements Preference.OnPreferenceChangeInternalListener, PreferenceGroup.PreferencePositionCallback {
    private Handler mHandler;
    private PreferenceGroup mPreferenceGroup;
    private List<PreferenceResourceDescriptor> mPreferenceResourceDescriptors;
    private List<Preference> mPreferences;
    private Runnable mSyncRunnable = new Runnable() {
        public void run() {
            PreferenceGroupAdapter.this.updatePreferences();
        }
    };
    private List<Preference> mVisiblePreferences;

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        this.mHandler = new Handler();
        this.mPreferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferences = new ArrayList();
        this.mVisiblePreferences = new ArrayList();
        this.mPreferenceResourceDescriptors = new ArrayList();
        PreferenceGroup preferenceGroup2 = this.mPreferenceGroup;
        if (preferenceGroup2 instanceof PreferenceScreen) {
            setHasStableIds(((PreferenceScreen) preferenceGroup2).shouldUseGeneratedIds());
        } else {
            setHasStableIds(true);
        }
        updatePreferences();
    }

    /* access modifiers changed from: package-private */
    public void updatePreferences() {
        for (Preference preference : this.mPreferences) {
            preference.setOnPreferenceChangeInternalListener(null);
        }
        this.mPreferences = new ArrayList(this.mPreferences.size());
        flattenPreferenceGroup(this.mPreferences, this.mPreferenceGroup);
        final List<Preference> oldVisibleList = this.mVisiblePreferences;
        final List<Preference> visiblePreferenceList = createVisiblePreferencesList(this.mPreferenceGroup);
        this.mVisiblePreferences = visiblePreferenceList;
        PreferenceManager preferenceManager = this.mPreferenceGroup.getPreferenceManager();
        if (preferenceManager == null || preferenceManager.getPreferenceComparisonCallback() == null) {
            notifyDataSetChanged();
        } else {
            final PreferenceManager.PreferenceComparisonCallback comparisonCallback = preferenceManager.getPreferenceComparisonCallback();
            DiffUtil.calculateDiff(new DiffUtil.Callback() {
                public int getOldListSize() {
                    return oldVisibleList.size();
                }

                public int getNewListSize() {
                    return visiblePreferenceList.size();
                }

                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return comparisonCallback.arePreferenceItemsTheSame((Preference) oldVisibleList.get(oldItemPosition), (Preference) visiblePreferenceList.get(newItemPosition));
                }

                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return comparisonCallback.arePreferenceContentsTheSame((Preference) oldVisibleList.get(oldItemPosition), (Preference) visiblePreferenceList.get(newItemPosition));
                }
            }).dispatchUpdatesTo(this);
        }
        for (Preference preference2 : this.mPreferences) {
            preference2.clearWasDetached();
        }
    }

    private void flattenPreferenceGroup(List<Preference> preferences, PreferenceGroup group) {
        group.sortPreferences();
        int groupSize = group.getPreferenceCount();
        for (int i = 0; i < groupSize; i++) {
            Preference preference = group.getPreference(i);
            preferences.add(preference);
            PreferenceResourceDescriptor descriptor = new PreferenceResourceDescriptor(preference);
            if (!this.mPreferenceResourceDescriptors.contains(descriptor)) {
                this.mPreferenceResourceDescriptors.add(descriptor);
            }
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup nestedGroup = (PreferenceGroup) preference;
                if (nestedGroup.isOnSameScreenAsChildren()) {
                    flattenPreferenceGroup(preferences, nestedGroup);
                }
            }
            preference.setOnPreferenceChangeInternalListener(this);
        }
    }

    private List<Preference> createVisiblePreferencesList(PreferenceGroup group) {
        int visiblePreferenceCount = 0;
        List<Preference> visiblePreferences = new ArrayList<>();
        List<Preference> collapsedPreferences = new ArrayList<>();
        int groupSize = group.getPreferenceCount();
        for (int i = 0; i < groupSize; i++) {
            Preference preference = group.getPreference(i);
            if (preference.isVisible()) {
                if (!isGroupExpandable(group) || visiblePreferenceCount < group.getInitialExpandedChildrenCount()) {
                    visiblePreferences.add(preference);
                } else {
                    collapsedPreferences.add(preference);
                }
                if (!(preference instanceof PreferenceGroup)) {
                    visiblePreferenceCount++;
                } else {
                    PreferenceGroup innerGroup = (PreferenceGroup) preference;
                    if (!innerGroup.isOnSameScreenAsChildren()) {
                        continue;
                    } else if (!isGroupExpandable(group) || !isGroupExpandable(innerGroup)) {
                        for (Preference inner : createVisiblePreferencesList(innerGroup)) {
                            if (!isGroupExpandable(group) || visiblePreferenceCount < group.getInitialExpandedChildrenCount()) {
                                visiblePreferences.add(inner);
                            } else {
                                collapsedPreferences.add(inner);
                            }
                            visiblePreferenceCount++;
                        }
                    } else {
                        throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                    }
                }
            }
        }
        if (isGroupExpandable(group) != 0 && visiblePreferenceCount > group.getInitialExpandedChildrenCount()) {
            visiblePreferences.add(createExpandButton(group, collapsedPreferences));
        }
        return visiblePreferences;
    }

    private ExpandButton createExpandButton(final PreferenceGroup group, List<Preference> collapsedPreferences) {
        ExpandButton preference = new ExpandButton(group.getContext(), collapsedPreferences, group.getId());
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                group.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
                PreferenceGroupAdapter.this.onPreferenceHierarchyChange(preference);
                PreferenceGroup.OnExpandButtonClickListener listener = group.getOnExpandButtonClickListener();
                if (listener == null) {
                    return true;
                }
                listener.onExpandButtonClick();
                return true;
            }
        });
        return preference;
    }

    private boolean isGroupExpandable(PreferenceGroup preferenceGroup) {
        return preferenceGroup.getInitialExpandedChildrenCount() != Integer.MAX_VALUE;
    }

    public Preference getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            return null;
        }
        return this.mVisiblePreferences.get(position);
    }

    public int getItemCount() {
        return this.mVisiblePreferences.size();
    }

    public long getItemId(int position) {
        if (!hasStableIds()) {
            return -1;
        }
        return getItem(position).getId();
    }

    public void onPreferenceChange(Preference preference) {
        int index = this.mVisiblePreferences.indexOf(preference);
        if (index != -1) {
            notifyItemChanged(index, preference);
        }
    }

    public void onPreferenceHierarchyChange(Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    public void onPreferenceVisibilityChange(Preference preference) {
        onPreferenceHierarchyChange(preference);
    }

    public int getItemViewType(int position) {
        PreferenceResourceDescriptor descriptor = new PreferenceResourceDescriptor(getItem(position));
        int viewType = this.mPreferenceResourceDescriptors.indexOf(descriptor);
        if (viewType != -1) {
            return viewType;
        }
        int viewType2 = this.mPreferenceResourceDescriptors.size();
        this.mPreferenceResourceDescriptors.add(descriptor);
        return viewType2;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public PreferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PreferenceResourceDescriptor descriptor = this.mPreferenceResourceDescriptors.get(viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TypedArray a = parent.getContext().obtainStyledAttributes((AttributeSet) null, C0731R.styleable.BackgroundStyle);
        Drawable background = a.getDrawable(C0731R.styleable.BackgroundStyle_android_selectableItemBackground);
        if (background == null) {
            background = AppCompatResources.getDrawable(parent.getContext(), 17301602);
        }
        a.recycle();
        View view = inflater.inflate(descriptor.mLayoutResId, parent, false);
        if (view.getBackground() == null) {
            ViewCompat.setBackground(view, background);
        }
        ViewGroup widgetFrame = (ViewGroup) view.findViewById(16908312);
        if (widgetFrame != null) {
            if (descriptor.mWidgetLayoutResId != 0) {
                inflater.inflate(descriptor.mWidgetLayoutResId, widgetFrame);
            } else {
                widgetFrame.setVisibility(8);
            }
        }
        return new PreferenceViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PreferenceViewHolder holder, int position) {
        getItem(position).onBindViewHolder(holder);
    }

    public int getPreferenceAdapterPosition(String key) {
        int size = this.mVisiblePreferences.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(key, this.mVisiblePreferences.get(i).getKey())) {
                return i;
            }
        }
        return -1;
    }

    public int getPreferenceAdapterPosition(Preference preference) {
        int size = this.mVisiblePreferences.size();
        for (int i = 0; i < size; i++) {
            Preference candidate = this.mVisiblePreferences.get(i);
            if (candidate != null && candidate.equals(preference)) {
                return i;
            }
        }
        return -1;
    }

    private static class PreferenceResourceDescriptor {
        String mClassName;
        int mLayoutResId;
        int mWidgetLayoutResId;

        PreferenceResourceDescriptor(Preference preference) {
            this.mClassName = preference.getClass().getName();
            this.mLayoutResId = preference.getLayoutResource();
            this.mWidgetLayoutResId = preference.getWidgetLayoutResource();
        }

        public boolean equals(Object o) {
            if (!(o instanceof PreferenceResourceDescriptor)) {
                return false;
            }
            PreferenceResourceDescriptor other = (PreferenceResourceDescriptor) o;
            if (this.mLayoutResId == other.mLayoutResId && this.mWidgetLayoutResId == other.mWidgetLayoutResId && TextUtils.equals(this.mClassName, other.mClassName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((17 * 31) + this.mLayoutResId) * 31) + this.mWidgetLayoutResId) * 31) + this.mClassName.hashCode();
        }
    }
}
