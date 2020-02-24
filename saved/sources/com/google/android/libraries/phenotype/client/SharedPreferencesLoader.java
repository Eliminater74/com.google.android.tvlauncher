package com.google.android.libraries.phenotype.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.GuardedBy;
import com.google.android.libraries.directboot.DirectBootUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SharedPreferencesLoader implements FlagLoader {
    @GuardedBy("SharedPreferencesLoader.class")
    static final Map<String, SharedPreferencesLoader> LOADERS_BY_NAME = new HashMap();
    private final Object cacheLock = new Object();
    private volatile Map<String, ?> cachedFlags;
    private final SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferencesLoader$$Lambda$0(this);
    @GuardedBy("this")
    private final List<ConfigurationUpdatedListener> listeners = new ArrayList();
    private final SharedPreferences sharedPreferences;

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$SharedPreferencesLoader(SharedPreferences prefs, String key) {
        invalidateCache();
    }

    static SharedPreferencesLoader getLoader(Context context, String sharedPrefsName) {
        SharedPreferencesLoader loader;
        if (!isSharedPreferencesAccessible(context, sharedPrefsName)) {
            return null;
        }
        synchronized (SharedPreferencesLoader.class) {
            loader = LOADERS_BY_NAME.get(sharedPrefsName);
            if (loader == null) {
                loader = new SharedPreferencesLoader(getSharedPreferences(context, sharedPrefsName));
                LOADERS_BY_NAME.put(sharedPrefsName, loader);
            }
        }
        return loader;
    }

    private static boolean isSharedPreferencesAccessible(Context context, String sharedPrefsName) {
        if (!DirectBootUtils.supportsDirectBoot() || sharedPrefsName.startsWith(PhenotypeFlag.DIRECT_BOOT_PREFIX)) {
            return true;
        }
        return DirectBootUtils.isUserUnlocked(context);
    }

    private static SharedPreferences getSharedPreferences(Context context, String sharedPrefsName) {
        if (!sharedPrefsName.startsWith(PhenotypeFlag.DIRECT_BOOT_PREFIX)) {
            return context.getSharedPreferences(sharedPrefsName, 0);
        }
        Context storageContext = context;
        if (DirectBootUtils.supportsDirectBoot()) {
            storageContext = context.createDeviceProtectedStorageContext();
        }
        return storageContext.getSharedPreferences(sharedPrefsName.substring(PhenotypeFlag.DIRECT_BOOT_PREFIX.length()), 0);
    }

    private SharedPreferencesLoader(SharedPreferences sharedPreferences2) {
        this.sharedPreferences = sharedPreferences2;
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.changeListener);
    }

    public Object getFlag(String flagName) {
        Map<String, ?> flags = this.cachedFlags;
        if (flags == null) {
            synchronized (this.cacheLock) {
                flags = this.cachedFlags;
                if (flags == null) {
                    flags = this.sharedPreferences.getAll();
                    this.cachedFlags = flags;
                }
            }
        }
        if (flags != null) {
            return flags.get(flagName);
        }
        return null;
    }

    private void invalidateCache() {
        synchronized (this.cacheLock) {
            this.cachedFlags = null;
            PhenotypeFlag.invalidateProcessCache();
        }
        notifyConfigurationUpdatedListeners();
    }

    public static void invalidateAllCaches() {
        synchronized (SharedPreferencesLoader.class) {
            for (SharedPreferencesLoader loader : LOADERS_BY_NAME.values()) {
                loader.invalidateCache();
            }
        }
    }

    public void addConfigurationUpdatedListener(ConfigurationUpdatedListener listener) {
        synchronized (this) {
            this.listeners.add(listener);
        }
    }

    public void removeConfigurationUpdatedListener(ConfigurationUpdatedListener listener) {
        synchronized (this) {
            this.listeners.remove(listener);
        }
    }

    private void notifyConfigurationUpdatedListeners() {
        synchronized (this) {
            for (ConfigurationUpdatedListener listener : this.listeners) {
                listener.onConfigurationUpdated();
            }
        }
    }
}
