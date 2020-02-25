package androidx.preference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.TypedArrayUtils;
import android.support.p001v4.view.ViewCompat;
import android.support.p001v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p004v7.content.res.AppCompatResources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Preference implements Comparable<Preference> {
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    private static final String CLIPBOARD_ID = "Preference";
    private final View.OnClickListener mClickListener;
    private boolean mAllowDividerAbove;
    private boolean mAllowDividerBelow;
    private boolean mBaseMethodCalled;
    private Context mContext;
    private boolean mCopyingEnabled;
    private Object mDefaultValue;
    private String mDependencyKey;
    private boolean mDependencyMet;
    private List<Preference> mDependents;
    private boolean mEnabled;
    private Bundle mExtras;
    private String mFragment;
    private boolean mHasId;
    private boolean mHasSingleLineTitleAttr;
    private Drawable mIcon;
    private int mIconResId;
    private boolean mIconSpaceReserved;
    private long mId;
    private Intent mIntent;
    private String mKey;
    private int mLayoutResId;
    private OnPreferenceChangeInternalListener mListener;
    private OnPreferenceChangeListener mOnChangeListener;
    private OnPreferenceClickListener mOnClickListener;
    private OnPreferenceCopyListener mOnCopyListener;
    private int mOrder;
    private boolean mParentDependencyMet;
    private PreferenceGroup mParentGroup;
    private boolean mPersistent;
    @Nullable
    private PreferenceDataStore mPreferenceDataStore;
    @Nullable
    private PreferenceManager mPreferenceManager;
    private boolean mRequiresKey;
    private boolean mSelectable;
    private boolean mShouldDisableView;
    private boolean mSingleLineTitle;
    private CharSequence mSummary;
    private SummaryProvider mSummaryProvider;
    private CharSequence mTitle;
    private int mViewId;
    private boolean mVisible;
    private boolean mWasDetached;
    private int mWidgetLayoutResId;

    public Preference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mOrder = Integer.MAX_VALUE;
        this.mViewId = 0;
        this.mEnabled = true;
        this.mSelectable = true;
        this.mPersistent = true;
        this.mDependencyMet = true;
        this.mParentDependencyMet = true;
        this.mVisible = true;
        this.mAllowDividerAbove = true;
        this.mAllowDividerBelow = true;
        this.mSingleLineTitle = true;
        this.mShouldDisableView = true;
        this.mLayoutResId = C0731R.layout.preference;
        this.mClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Preference.this.performClick(v);
            }
        };
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, C0731R.styleable.Preference, defStyleAttr, defStyleRes);
        this.mIconResId = TypedArrayUtils.getResourceId(a, C0731R.styleable.Preference_icon, C0731R.styleable.Preference_android_icon, 0);
        this.mKey = TypedArrayUtils.getString(a, C0731R.styleable.Preference_key, C0731R.styleable.Preference_android_key);
        this.mTitle = TypedArrayUtils.getText(a, C0731R.styleable.Preference_title, C0731R.styleable.Preference_android_title);
        this.mSummary = TypedArrayUtils.getText(a, C0731R.styleable.Preference_summary, C0731R.styleable.Preference_android_summary);
        this.mOrder = TypedArrayUtils.getInt(a, C0731R.styleable.Preference_order, C0731R.styleable.Preference_android_order, Integer.MAX_VALUE);
        this.mFragment = TypedArrayUtils.getString(a, C0731R.styleable.Preference_fragment, C0731R.styleable.Preference_android_fragment);
        this.mLayoutResId = TypedArrayUtils.getResourceId(a, C0731R.styleable.Preference_layout, C0731R.styleable.Preference_android_layout, C0731R.layout.preference);
        this.mWidgetLayoutResId = TypedArrayUtils.getResourceId(a, C0731R.styleable.Preference_widgetLayout, C0731R.styleable.Preference_android_widgetLayout, 0);
        this.mEnabled = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_enabled, C0731R.styleable.Preference_android_enabled, true);
        this.mSelectable = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_selectable, C0731R.styleable.Preference_android_selectable, true);
        this.mPersistent = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_persistent, C0731R.styleable.Preference_android_persistent, true);
        this.mDependencyKey = TypedArrayUtils.getString(a, C0731R.styleable.Preference_dependency, C0731R.styleable.Preference_android_dependency);
        this.mAllowDividerAbove = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_allowDividerAbove, C0731R.styleable.Preference_allowDividerAbove, this.mSelectable);
        this.mAllowDividerBelow = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_allowDividerBelow, C0731R.styleable.Preference_allowDividerBelow, this.mSelectable);
        if (a.hasValue(C0731R.styleable.Preference_defaultValue)) {
            this.mDefaultValue = onGetDefaultValue(a, C0731R.styleable.Preference_defaultValue);
        } else if (a.hasValue(C0731R.styleable.Preference_android_defaultValue)) {
            this.mDefaultValue = onGetDefaultValue(a, C0731R.styleable.Preference_android_defaultValue);
        }
        this.mShouldDisableView = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_shouldDisableView, C0731R.styleable.Preference_android_shouldDisableView, true);
        this.mHasSingleLineTitleAttr = a.hasValue(C0731R.styleable.Preference_singleLineTitle);
        if (this.mHasSingleLineTitleAttr) {
            this.mSingleLineTitle = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_singleLineTitle, C0731R.styleable.Preference_android_singleLineTitle, true);
        }
        this.mIconSpaceReserved = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_iconSpaceReserved, C0731R.styleable.Preference_android_iconSpaceReserved, false);
        this.mVisible = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_isPreferenceVisible, C0731R.styleable.Preference_isPreferenceVisible, true);
        this.mCopyingEnabled = TypedArrayUtils.getBoolean(a, C0731R.styleable.Preference_enableCopying, C0731R.styleable.Preference_enableCopying, false);
        a.recycle();
    }

    public Preference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Preference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context, C0731R.attr.preferenceStyle, 16842894));
    }

    public Preference(Context context) {
        this(context, null);
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray a, int index) {
        return null;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public String getFragment() {
        return this.mFragment;
    }

    public void setFragment(String fragment) {
        this.mFragment = fragment;
    }

    @Nullable
    public PreferenceDataStore getPreferenceDataStore() {
        PreferenceDataStore preferenceDataStore = this.mPreferenceDataStore;
        if (preferenceDataStore != null) {
            return preferenceDataStore;
        }
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            return preferenceManager.getPreferenceDataStore();
        }
        return null;
    }

    public void setPreferenceDataStore(PreferenceDataStore dataStore) {
        this.mPreferenceDataStore = dataStore;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public Bundle peekExtras() {
        return this.mExtras;
    }

    public final int getLayoutResource() {
        return this.mLayoutResId;
    }

    public void setLayoutResource(int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    public final int getWidgetLayoutResource() {
        return this.mWidgetLayoutResId;
    }

    public void setWidgetLayoutResource(int widgetLayoutResId) {
        this.mWidgetLayoutResId = widgetLayoutResId;
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        View itemView = holder.itemView;
        Integer summaryTextColor = null;
        itemView.setOnClickListener(this.mClickListener);
        itemView.setId(this.mViewId);
        TextView summaryView = (TextView) holder.findViewById(16908304);
        int i = 8;
        if (summaryView != null) {
            CharSequence summary = getSummary();
            if (!TextUtils.isEmpty(summary)) {
                summaryView.setText(summary);
                summaryView.setVisibility(0);
                summaryTextColor = Integer.valueOf(summaryView.getCurrentTextColor());
            } else {
                summaryView.setVisibility(8);
            }
        }
        TextView titleView = (TextView) holder.findViewById(16908310);
        if (titleView != null) {
            CharSequence title = getTitle();
            if (!TextUtils.isEmpty(title)) {
                titleView.setText(title);
                titleView.setVisibility(0);
                if (this.mHasSingleLineTitleAttr) {
                    titleView.setSingleLine(this.mSingleLineTitle);
                }
                if (!isSelectable() && isEnabled() && summaryTextColor != null) {
                    titleView.setTextColor(summaryTextColor.intValue());
                }
            } else {
                titleView.setVisibility(8);
            }
        }
        ImageView imageView = (ImageView) holder.findViewById(16908294);
        if (imageView != null) {
            if (!(this.mIconResId == 0 && this.mIcon == null)) {
                if (this.mIcon == null) {
                    this.mIcon = AppCompatResources.getDrawable(this.mContext, this.mIconResId);
                }
                Drawable drawable = this.mIcon;
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            }
            if (this.mIcon != null) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(this.mIconSpaceReserved ? 4 : 8);
            }
        }
        View imageFrame = holder.findViewById(C0731R.C0733id.icon_frame);
        if (imageFrame == null) {
            imageFrame = holder.findViewById(AndroidResources.ANDROID_R_ICON_FRAME);
        }
        if (imageFrame != null) {
            if (this.mIcon != null) {
                imageFrame.setVisibility(0);
            } else {
                if (this.mIconSpaceReserved) {
                    i = 4;
                }
                imageFrame.setVisibility(i);
            }
        }
        if (this.mShouldDisableView) {
            setEnabledStateOnViews(itemView, isEnabled());
        } else {
            setEnabledStateOnViews(itemView, true);
        }
        boolean selectable = isSelectable();
        itemView.setFocusable(selectable);
        itemView.setClickable(selectable);
        holder.setDividerAllowedAbove(this.mAllowDividerAbove);
        holder.setDividerAllowedBelow(this.mAllowDividerBelow);
        boolean copyingEnabled = isCopyingEnabled();
        if (copyingEnabled && this.mOnCopyListener == null) {
            this.mOnCopyListener = new OnPreferenceCopyListener(this);
        }
        itemView.setOnCreateContextMenuListener(copyingEnabled ? this.mOnCopyListener : null);
        itemView.setLongClickable(copyingEnabled);
        if (copyingEnabled && !selectable) {
            ViewCompat.setBackground(itemView, null);
        }
    }

    private void setEnabledStateOnViews(View v, boolean enabled) {
        v.setEnabled(enabled);
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = vg.getChildCount() - 1; i >= 0; i--) {
                setEnabledStateOnViews(vg.getChildAt(i), enabled);
            }
        }
    }

    public int getOrder() {
        return this.mOrder;
    }

    public void setOrder(int order) {
        if (order != this.mOrder) {
            this.mOrder = order;
            notifyHierarchyChanged();
        }
    }

    public void setViewId(int viewId) {
        this.mViewId = viewId;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setTitle(CharSequence title) {
        if ((title == null && this.mTitle != null) || (title != null && !title.equals(this.mTitle))) {
            this.mTitle = title;
            notifyChanged();
        }
    }

    public void setTitle(int titleResId) {
        setTitle(this.mContext.getString(titleResId));
    }

    public Drawable getIcon() {
        int i;
        if (this.mIcon == null && (i = this.mIconResId) != 0) {
            this.mIcon = AppCompatResources.getDrawable(this.mContext, i);
        }
        return this.mIcon;
    }

    public void setIcon(Drawable icon) {
        if (this.mIcon != icon) {
            this.mIcon = icon;
            this.mIconResId = 0;
            notifyChanged();
        }
    }

    public void setIcon(int iconResId) {
        setIcon(AppCompatResources.getDrawable(this.mContext, iconResId));
        this.mIconResId = iconResId;
    }

    public CharSequence getSummary() {
        if (getSummaryProvider() != null) {
            return getSummaryProvider().provideSummary(this);
        }
        return this.mSummary;
    }

    public void setSummary(CharSequence summary) {
        if (getSummaryProvider() != null) {
            throw new IllegalStateException("Preference already has a SummaryProvider set.");
        } else if (!TextUtils.equals(this.mSummary, summary)) {
            this.mSummary = summary;
            notifyChanged();
        }
    }

    public void setSummary(int summaryResId) {
        setSummary(this.mContext.getString(summaryResId));
    }

    public boolean isEnabled() {
        return this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
    }

    public void setEnabled(boolean enabled) {
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    public void setSelectable(boolean selectable) {
        if (this.mSelectable != selectable) {
            this.mSelectable = selectable;
            notifyChanged();
        }
    }

    public boolean getShouldDisableView() {
        return this.mShouldDisableView;
    }

    public void setShouldDisableView(boolean shouldDisableView) {
        if (this.mShouldDisableView != shouldDisableView) {
            this.mShouldDisableView = shouldDisableView;
            notifyChanged();
        }
    }

    public final boolean isVisible() {
        return this.mVisible;
    }

    public final void setVisible(boolean visible) {
        if (this.mVisible != visible) {
            this.mVisible = visible;
            OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
            if (onPreferenceChangeInternalListener != null) {
                onPreferenceChangeInternalListener.onPreferenceVisibilityChange(this);
            }
        }
    }

    public final boolean isShown() {
        if (!isVisible() || getPreferenceManager() == null) {
            return false;
        }
        if (this == getPreferenceManager().getPreferenceScreen()) {
            return true;
        }
        PreferenceGroup parent = getParent();
        if (parent == null) {
            return false;
        }
        return parent.isShown();
    }

    /* access modifiers changed from: package-private */
    public long getId() {
        return this.mId;
    }

    /* access modifiers changed from: protected */
    public void onClick() {
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
        if (this.mRequiresKey && !hasKey()) {
            requireKey();
        }
    }

    /* access modifiers changed from: package-private */
    public void requireKey() {
        if (!TextUtils.isEmpty(this.mKey)) {
            this.mRequiresKey = true;
            return;
        }
        throw new IllegalStateException("Preference does not have a key assigned.");
    }

    public boolean hasKey() {
        return !TextUtils.isEmpty(this.mKey);
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public void setPersistent(boolean persistent) {
        this.mPersistent = persistent;
    }

    /* access modifiers changed from: protected */
    public boolean shouldPersist() {
        return this.mPreferenceManager != null && isPersistent() && hasKey();
    }

    public boolean isSingleLineTitle() {
        return this.mSingleLineTitle;
    }

    public void setSingleLineTitle(boolean singleLineTitle) {
        this.mHasSingleLineTitleAttr = true;
        this.mSingleLineTitle = singleLineTitle;
    }

    public boolean isIconSpaceReserved() {
        return this.mIconSpaceReserved;
    }

    public void setIconSpaceReserved(boolean iconSpaceReserved) {
        if (this.mIconSpaceReserved != iconSpaceReserved) {
            this.mIconSpaceReserved = iconSpaceReserved;
            notifyChanged();
        }
    }

    public boolean isCopyingEnabled() {
        return this.mCopyingEnabled;
    }

    public void setCopyingEnabled(boolean enabled) {
        if (this.mCopyingEnabled != enabled) {
            this.mCopyingEnabled = enabled;
            notifyChanged();
        }
    }

    @Nullable
    public final SummaryProvider getSummaryProvider() {
        return this.mSummaryProvider;
    }

    public final void setSummaryProvider(@Nullable SummaryProvider summaryProvider) {
        this.mSummaryProvider = summaryProvider;
        notifyChanged();
    }

    public boolean callChangeListener(Object newValue) {
        OnPreferenceChangeListener onPreferenceChangeListener = this.mOnChangeListener;
        return onPreferenceChangeListener == null || onPreferenceChangeListener.onPreferenceChange(this, newValue);
    }

    public OnPreferenceChangeListener getOnPreferenceChangeListener() {
        return this.mOnChangeListener;
    }

    public void setOnPreferenceChangeListener(OnPreferenceChangeListener onPreferenceChangeListener) {
        this.mOnChangeListener = onPreferenceChangeListener;
    }

    public OnPreferenceClickListener getOnPreferenceClickListener() {
        return this.mOnClickListener;
    }

    public void setOnPreferenceClickListener(OnPreferenceClickListener onPreferenceClickListener) {
        this.mOnClickListener = onPreferenceClickListener;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void performClick(View view) {
        performClick();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void performClick() {
        PreferenceManager.OnPreferenceTreeClickListener listener;
        if (isEnabled() && isSelectable()) {
            onClick();
            OnPreferenceClickListener onPreferenceClickListener = this.mOnClickListener;
            if (onPreferenceClickListener == null || !onPreferenceClickListener.onPreferenceClick(this)) {
                PreferenceManager preferenceManager = getPreferenceManager();
                if ((preferenceManager == null || (listener = preferenceManager.getOnPreferenceTreeClickListener()) == null || !listener.onPreferenceTreeClick(this)) && this.mIntent != null) {
                    getContext().startActivity(this.mIntent);
                }
            }
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mPreferenceManager == null || getPreferenceDataStore() != null) {
            return null;
        }
        return this.mPreferenceManager.getSharedPreferences();
    }

    public int compareTo(@NonNull Preference another) {
        int i = this.mOrder;
        int i2 = another.mOrder;
        if (i != i2) {
            return i - i2;
        }
        CharSequence charSequence = this.mTitle;
        CharSequence charSequence2 = another.mTitle;
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (charSequence2 == null) {
            return -1;
        }
        return charSequence.toString().compareToIgnoreCase(another.mTitle.toString());
    }

    /* access modifiers changed from: package-private */
    public final void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener listener) {
        this.mListener = listener;
    }

    /* access modifiers changed from: protected */
    public void notifyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            onPreferenceChangeInternalListener.onPreferenceChange(this);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyHierarchyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            onPreferenceChangeInternalListener.onPreferenceHierarchyChange(this);
        }
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        if (!this.mHasId) {
            this.mId = preferenceManager.getNextId();
        }
        dispatchSetInitialValue();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onAttachedToHierarchy(PreferenceManager preferenceManager, long id) {
        this.mId = id;
        this.mHasId = true;
        try {
            onAttachedToHierarchy(preferenceManager);
        } finally {
            this.mHasId = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void assignParent(@Nullable PreferenceGroup parentGroup) {
        if (parentGroup == null || this.mParentGroup == null) {
            this.mParentGroup = parentGroup;
            return;
        }
        throw new IllegalStateException("This preference already has a parent. You must remove the existing parent before assigning a new one.");
    }

    public void onAttached() {
        registerDependency();
    }

    public void onDetached() {
        unregisterDependency();
        this.mWasDetached = true;
    }

    /* access modifiers changed from: package-private */
    public final boolean wasDetached() {
        return this.mWasDetached;
    }

    /* access modifiers changed from: package-private */
    public final void clearWasDetached() {
        this.mWasDetached = false;
    }

    private void registerDependency() {
        if (!TextUtils.isEmpty(this.mDependencyKey)) {
            Preference preference = findPreferenceInHierarchy(this.mDependencyKey);
            if (preference != null) {
                preference.registerDependent(this);
                return;
            }
            throw new IllegalStateException("Dependency \"" + this.mDependencyKey + "\" not found for preference \"" + this.mKey + "\" (title: \"" + ((Object) this.mTitle) + "\"");
        }
    }

    private void unregisterDependency() {
        Preference oldDependency;
        String str = this.mDependencyKey;
        if (str != null && (oldDependency = findPreferenceInHierarchy(str)) != null) {
            oldDependency.unregisterDependent(this);
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public <T extends Preference> T findPreferenceInHierarchy(@NonNull String key) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return preferenceManager.findPreference(key);
    }

    private void registerDependent(Preference dependent) {
        if (this.mDependents == null) {
            this.mDependents = new ArrayList();
        }
        this.mDependents.add(dependent);
        dependent.onDependencyChanged(this, shouldDisableDependents());
    }

    private void unregisterDependent(Preference dependent) {
        List<Preference> list = this.mDependents;
        if (list != null) {
            list.remove(dependent);
        }
    }

    public void notifyDependencyChange(boolean disableDependents) {
        List<Preference> dependents = this.mDependents;
        if (dependents != null) {
            int dependentsCount = dependents.size();
            for (int i = 0; i < dependentsCount; i++) {
                dependents.get(i).onDependencyChanged(this, disableDependents);
            }
        }
    }

    public void onDependencyChanged(Preference dependency, boolean disableDependent) {
        if (this.mDependencyMet == disableDependent) {
            this.mDependencyMet = !disableDependent;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void onParentChanged(Preference parent, boolean disableChild) {
        if (this.mParentDependencyMet == disableChild) {
            this.mParentDependencyMet = !disableChild;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public boolean shouldDisableDependents() {
        return !isEnabled();
    }

    public String getDependency() {
        return this.mDependencyKey;
    }

    public void setDependency(String dependencyKey) {
        unregisterDependency();
        this.mDependencyKey = dependencyKey;
        registerDependency();
    }

    @Nullable
    public PreferenceGroup getParent() {
        return this.mParentGroup;
    }

    /* access modifiers changed from: protected */
    public void onPrepareForRemoval() {
        unregisterDependency();
    }

    public void setDefaultValue(Object defaultValue) {
        this.mDefaultValue = defaultValue;
    }

    private void dispatchSetInitialValue() {
        if (getPreferenceDataStore() != null) {
            onSetInitialValue(true, this.mDefaultValue);
        } else if (!shouldPersist() || !getSharedPreferences().contains(this.mKey)) {
            Object obj = this.mDefaultValue;
            if (obj != null) {
                onSetInitialValue(false, obj);
            }
        } else {
            onSetInitialValue(true, null);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        onSetInitialValue(defaultValue);
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(@Nullable Object defaultValue) {
    }

    private void tryCommit(@NonNull SharedPreferences.Editor editor) {
        if (this.mPreferenceManager.shouldCommit()) {
            editor.apply();
        }
    }

    /* access modifiers changed from: protected */
    public boolean persistString(String value) {
        if (!shouldPersist()) {
            return false;
        }
        if (TextUtils.equals(value, getPersistedString(null))) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putString(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putString(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public String getPersistedString(String defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getString(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, defaultReturnValue);
    }

    public boolean persistStringSet(Set<String> values) {
        if (!shouldPersist()) {
            return false;
        }
        if (values.equals(getPersistedStringSet(null))) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putStringSet(this.mKey, values);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putStringSet(this.mKey, values);
            tryCommit(editor);
        }
        return true;
    }

    public Set<String> getPersistedStringSet(Set<String> defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getStringSet(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, defaultReturnValue);
    }

    /* access modifiers changed from: protected */
    public boolean persistInt(int value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedInt(value ^ -1)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putInt(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putInt(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public int getPersistedInt(int defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getInt(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, defaultReturnValue);
    }

    /* access modifiers changed from: protected */
    public boolean persistFloat(float value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedFloat(Float.NaN)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putFloat(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putFloat(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public float getPersistedFloat(float defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getFloat(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getFloat(this.mKey, defaultReturnValue);
    }

    /* access modifiers changed from: protected */
    public boolean persistLong(long value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedLong(-1 ^ value)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putLong(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putLong(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public long getPersistedLong(long defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getLong(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getLong(this.mKey, defaultReturnValue);
    }

    /* access modifiers changed from: protected */
    public boolean persistBoolean(boolean value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedBoolean(!value)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putBoolean(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putBoolean(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean getPersistedBoolean(boolean defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getBoolean(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, defaultReturnValue);
    }

    public String toString() {
        return getFilterableStringBuilder().toString();
    }

    /* access modifiers changed from: package-private */
    public StringBuilder getFilterableStringBuilder() {
        StringBuilder sb = new StringBuilder();
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            sb.append(title);
            sb.append(' ');
        }
        CharSequence summary = getSummary();
        if (!TextUtils.isEmpty(summary)) {
            sb.append(summary);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb;
    }

    public void saveHierarchyState(Bundle container) {
        dispatchSaveInstanceState(container);
    }

    /* access modifiers changed from: package-private */
    public void dispatchSaveInstanceState(Bundle container) {
        if (hasKey()) {
            this.mBaseMethodCalled = false;
            Parcelable state = onSaveInstanceState();
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            } else if (state != null) {
                container.putParcelable(this.mKey, state);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(Bundle container) {
        dispatchRestoreInstanceState(container);
    }

    /* access modifiers changed from: package-private */
    public void dispatchRestoreInstanceState(Bundle container) {
        Parcelable state;
        if (hasKey() && (state = container.getParcelable(this.mKey)) != null) {
            this.mBaseMethodCalled = false;
            onRestoreInstanceState(state);
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        this.mBaseMethodCalled = true;
        if (state != BaseSavedState.EMPTY_STATE && state != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    @CallSuper
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat info) {
    }

    interface OnPreferenceChangeInternalListener {
        void onPreferenceChange(Preference preference);

        void onPreferenceHierarchyChange(Preference preference);

        void onPreferenceVisibilityChange(Preference preference);
    }

    public interface OnPreferenceChangeListener {
        boolean onPreferenceChange(Preference preference, Object obj);
    }

    public interface OnPreferenceClickListener {
        boolean onPreferenceClick(Preference preference);
    }

    public interface SummaryProvider<T extends Preference> {
        CharSequence provideSummary(Preference preference);
    }

    public static class BaseSavedState extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator<BaseSavedState>() {
            public BaseSavedState createFromParcel(Parcel in) {
                return new BaseSavedState(in);
            }

            public BaseSavedState[] newArray(int size) {
                return new BaseSavedState[size];
            }
        };

        public BaseSavedState(Parcel source) {
            super(source);
        }

        public BaseSavedState(Parcelable superState) {
            super(superState);
        }
    }

    private static class OnPreferenceCopyListener implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private final Preference mPreference;

        OnPreferenceCopyListener(Preference preference) {
            this.mPreference = preference;
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            CharSequence summary = this.mPreference.getSummary();
            if (this.mPreference.isCopyingEnabled() && !TextUtils.isEmpty(summary)) {
                menu.setHeaderTitle(summary);
                menu.add(0, 0, 0, C0731R.string.copy).setOnMenuItemClickListener(this);
            }
        }

        public boolean onMenuItemClick(MenuItem item) {
            CharSequence summary = this.mPreference.getSummary();
            ((ClipboardManager) this.mPreference.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(Preference.CLIPBOARD_ID, summary));
            Toast.makeText(this.mPreference.getContext(), this.mPreference.getContext().getString(C0731R.string.preference_copied, summary), 0).show();
            return true;
        }
    }
}
