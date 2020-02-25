package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.ContextCompat;
import android.text.TextUtils;

public class PreferenceManager {
    public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    private static final int STORAGE_DEFAULT = 0;
    private static final int STORAGE_DEVICE_PROTECTED = 1;
    private Context mContext;
    @Nullable
    private SharedPreferences.Editor mEditor;
    private long mNextId = 0;
    private boolean mNoCommit;
    private OnDisplayPreferenceDialogListener mOnDisplayPreferenceDialogListener;
    private OnNavigateToScreenListener mOnNavigateToScreenListener;
    private OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    private PreferenceComparisonCallback mPreferenceComparisonCallback;
    @Nullable
    private PreferenceDataStore mPreferenceDataStore;
    private PreferenceScreen mPreferenceScreen;
    @Nullable
    private SharedPreferences mSharedPreferences;
    private int mSharedPreferencesMode;
    private String mSharedPreferencesName;
    private int mStorage = 0;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PreferenceManager(Context context) {
        this.mContext = context;
        setSharedPreferencesName(getDefaultSharedPreferencesName(context));
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode());
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return 0;
    }

    public static void setDefaultValues(Context context, int resId, boolean readAgain) {
        setDefaultValues(context, getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode(), resId, readAgain);
    }

    public static void setDefaultValues(Context context, String sharedPreferencesName, int sharedPreferencesMode, int resId, boolean readAgain) {
        SharedPreferences defaultValueSp = context.getSharedPreferences(KEY_HAS_SET_DEFAULT_VALUES, 0);
        if (readAgain || !defaultValueSp.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {
            PreferenceManager pm = new PreferenceManager(context);
            pm.setSharedPreferencesName(sharedPreferencesName);
            pm.setSharedPreferencesMode(sharedPreferencesMode);
            pm.inflateFromResource(context, resId, null);
            defaultValueSp.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true).apply();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PreferenceScreen inflateFromResource(Context context, int resId, PreferenceScreen rootPreferences) {
        setNoCommit(true);
        PreferenceScreen rootPreferences2 = (PreferenceScreen) new PreferenceInflater(context, this).inflate(resId, rootPreferences);
        rootPreferences2.onAttachedToHierarchy(this);
        setNoCommit(false);
        return rootPreferences2;
    }

    public PreferenceScreen createPreferenceScreen(Context context) {
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, null);
        preferenceScreen.onAttachedToHierarchy(this);
        return preferenceScreen;
    }

    /* access modifiers changed from: package-private */
    public long getNextId() {
        long j;
        synchronized (this) {
            j = this.mNextId;
            this.mNextId = 1 + j;
        }
        return j;
    }

    public String getSharedPreferencesName() {
        return this.mSharedPreferencesName;
    }

    public void setSharedPreferencesName(String sharedPreferencesName) {
        this.mSharedPreferencesName = sharedPreferencesName;
        this.mSharedPreferences = null;
    }

    public int getSharedPreferencesMode() {
        return this.mSharedPreferencesMode;
    }

    public void setSharedPreferencesMode(int sharedPreferencesMode) {
        this.mSharedPreferencesMode = sharedPreferencesMode;
        this.mSharedPreferences = null;
    }

    public void setStorageDefault() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mStorage = 0;
            this.mSharedPreferences = null;
        }
    }

    public void setStorageDeviceProtected() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mStorage = 1;
            this.mSharedPreferences = null;
        }
    }

    public boolean isStorageDefault() {
        if (Build.VERSION.SDK_INT < 24 || this.mStorage == 0) {
            return true;
        }
        return false;
    }

    public boolean isStorageDeviceProtected() {
        if (Build.VERSION.SDK_INT < 24 || this.mStorage != 1) {
            return false;
        }
        return true;
    }

    @Nullable
    public PreferenceDataStore getPreferenceDataStore() {
        return this.mPreferenceDataStore;
    }

    public void setPreferenceDataStore(PreferenceDataStore dataStore) {
        this.mPreferenceDataStore = dataStore;
    }

    public SharedPreferences getSharedPreferences() {
        Context storageContext;
        if (getPreferenceDataStore() != null) {
            return null;
        }
        if (this.mSharedPreferences == null) {
            if (this.mStorage != 1) {
                storageContext = this.mContext;
            } else {
                storageContext = ContextCompat.createDeviceProtectedStorageContext(this.mContext);
            }
            this.mSharedPreferences = storageContext.getSharedPreferences(this.mSharedPreferencesName, this.mSharedPreferencesMode);
        }
        return this.mSharedPreferences;
    }

    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceScreen;
    }

    public boolean setPreferences(PreferenceScreen preferenceScreen) {
        PreferenceScreen preferenceScreen2 = this.mPreferenceScreen;
        if (preferenceScreen == preferenceScreen2) {
            return false;
        }
        if (preferenceScreen2 != null) {
            preferenceScreen2.onDetached();
        }
        this.mPreferenceScreen = preferenceScreen;
        return true;
    }

    @Nullable
    public <T extends Preference> T findPreference(@NonNull CharSequence key) {
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen == null) {
            return null;
        }
        return preferenceScreen.findPreference(key);
    }

    /* access modifiers changed from: package-private */
    public SharedPreferences.Editor getEditor() {
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (!this.mNoCommit) {
            return getSharedPreferences().edit();
        }
        if (this.mEditor == null) {
            this.mEditor = getSharedPreferences().edit();
        }
        return this.mEditor;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCommit() {
        return !this.mNoCommit;
    }

    private void setNoCommit(boolean noCommit) {
        SharedPreferences.Editor editor;
        if (!noCommit && (editor = this.mEditor) != null) {
            editor.apply();
        }
        this.mNoCommit = noCommit;
    }

    public Context getContext() {
        return this.mContext;
    }

    public PreferenceComparisonCallback getPreferenceComparisonCallback() {
        return this.mPreferenceComparisonCallback;
    }

    public void setPreferenceComparisonCallback(PreferenceComparisonCallback preferenceComparisonCallback) {
        this.mPreferenceComparisonCallback = preferenceComparisonCallback;
    }

    public OnDisplayPreferenceDialogListener getOnDisplayPreferenceDialogListener() {
        return this.mOnDisplayPreferenceDialogListener;
    }

    public void setOnDisplayPreferenceDialogListener(OnDisplayPreferenceDialogListener onDisplayPreferenceDialogListener) {
        this.mOnDisplayPreferenceDialogListener = onDisplayPreferenceDialogListener;
    }

    public void showDialog(Preference preference) {
        OnDisplayPreferenceDialogListener onDisplayPreferenceDialogListener = this.mOnDisplayPreferenceDialogListener;
        if (onDisplayPreferenceDialogListener != null) {
            onDisplayPreferenceDialogListener.onDisplayPreferenceDialog(preference);
        }
    }

    public OnPreferenceTreeClickListener getOnPreferenceTreeClickListener() {
        return this.mOnPreferenceTreeClickListener;
    }

    public void setOnPreferenceTreeClickListener(OnPreferenceTreeClickListener listener) {
        this.mOnPreferenceTreeClickListener = listener;
    }

    public OnNavigateToScreenListener getOnNavigateToScreenListener() {
        return this.mOnNavigateToScreenListener;
    }

    public void setOnNavigateToScreenListener(OnNavigateToScreenListener listener) {
        this.mOnNavigateToScreenListener = listener;
    }

    public interface OnDisplayPreferenceDialogListener {
        void onDisplayPreferenceDialog(Preference preference);
    }

    public interface OnNavigateToScreenListener {
        void onNavigateToScreen(PreferenceScreen preferenceScreen);
    }

    public interface OnPreferenceTreeClickListener {
        boolean onPreferenceTreeClick(Preference preference);
    }

    public static abstract class PreferenceComparisonCallback {
        public abstract boolean arePreferenceContentsTheSame(Preference preference, Preference preference2);

        public abstract boolean arePreferenceItemsTheSame(Preference preference, Preference preference2);
    }

    public static class SimplePreferenceComparisonCallback extends PreferenceComparisonCallback {
        public boolean arePreferenceItemsTheSame(Preference p1, Preference p2) {
            return p1.getId() == p2.getId();
        }

        public boolean arePreferenceContentsTheSame(Preference p1, Preference p2) {
            if (p1.getClass() != p2.getClass()) {
                return false;
            }
            if ((p1 == p2 && p1.wasDetached()) || !TextUtils.equals(p1.getTitle(), p2.getTitle()) || !TextUtils.equals(p1.getSummary(), p2.getSummary())) {
                return false;
            }
            Drawable p1Icon = p1.getIcon();
            Drawable p2Icon = p2.getIcon();
            if ((p1Icon != p2Icon && (p1Icon == null || !p1Icon.equals(p2Icon))) || p1.isEnabled() != p2.isEnabled() || p1.isSelectable() != p2.isSelectable()) {
                return false;
            }
            if ((p1 instanceof TwoStatePreference) && ((TwoStatePreference) p1).isChecked() != ((TwoStatePreference) p2).isChecked()) {
                return false;
            }
            if (!(p1 instanceof DropDownPreference) || p1 == p2) {
                return true;
            }
            return false;
        }
    }
}
