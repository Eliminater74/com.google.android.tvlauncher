package com.google.android.libraries.phenotype.client;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.GuardedBy;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.util.ArrayMap;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigurationContentLoader implements FlagLoader {
    @VisibleForTesting
    static final int ARRAYMAP_THRESHOLD = 256;
    public static final String[] COLUMNS = {"key", "value"};
    @GuardedBy("ConfigurationContentLoader.class")
    static final Map<Uri, ConfigurationContentLoader> LOADERS_BY_URI = new ArrayMap();
    private static final String TAG = "ConfigurationContentLoader";
    private final Object cacheLock = new Object();
    private volatile Map<String, String> cachedFlags;
    @GuardedBy("this")
    private final List<ConfigurationUpdatedListener> listeners = new ArrayList();
    private final ContentResolver resolver;
    private final Uri uri;

    private ConfigurationContentLoader(ContentResolver resolver2, Uri uri2) {
        this.resolver = resolver2;
        this.uri = uri2;
        this.resolver.registerContentObserver(uri2, false, new ContentObserver(null) {
            public void onChange(boolean selfChange) {
                ConfigurationContentLoader.this.invalidateCache();
            }
        });
    }

    public static void invalidateAllCaches() {
        synchronized (ConfigurationContentLoader.class) {
            for (ConfigurationContentLoader loader : LOADERS_BY_URI.values()) {
                loader.invalidateCache();
            }
        }
    }

    public static ConfigurationContentLoader getLoader(ContentResolver resolver2, Uri uri2) {
        ConfigurationContentLoader loader;
        synchronized (ConfigurationContentLoader.class) {
            loader = LOADERS_BY_URI.get(uri2);
            if (loader == null) {
                try {
                    loader = new ConfigurationContentLoader(resolver2, uri2);
                    LOADERS_BY_URI.put(uri2, loader);
                } catch (SecurityException e) {
                }
            }
        }
        return loader;
    }

    public Map<String, String> getFlags() {
        Map<String, String> flags = this.cachedFlags;
        if (flags == null) {
            synchronized (this.cacheLock) {
                flags = this.cachedFlags;
                if (flags == null) {
                    flags = readFlagsFromContentProvider();
                    this.cachedFlags = flags;
                }
            }
        }
        return flags != null ? flags : Collections.emptyMap();
    }

    public String getFlag(String flagName) {
        return getFlags().get(flagName);
    }

    public void invalidateCache() {
        synchronized (this.cacheLock) {
            this.cachedFlags = null;
            PhenotypeFlag.invalidateProcessCache();
        }
        notifyConfigurationUpdatedListeners();
    }

    public static void invalidateCache(Uri uri2) {
        synchronized (ConfigurationContentLoader.class) {
            ConfigurationContentLoader loader = LOADERS_BY_URI.get(uri2);
            if (loader != null) {
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

    private Map<String, String> readFlagsFromContentProvider() {
        try {
            return (Map) FlagLoader$$CC.executeBinderAware$$STATIC$$(new ConfigurationContentLoader$$Lambda$0(this));
        } catch (SQLiteException | IllegalStateException | SecurityException e) {
            Log.e(TAG, "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Map lambda$readFlagsFromContentProvider$0$ConfigurationContentLoader() {
        Map<String, String> flags;
        Cursor cursor = this.resolver.query(this.uri, COLUMNS, null, null, null);
        if (cursor == null) {
            return Collections.emptyMap();
        }
        try {
            int count = cursor.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            if (count <= 256) {
                flags = new ArrayMap<>(count);
            } else {
                flags = new HashMap<>(count, 1.0f);
            }
            while (cursor.moveToNext()) {
                flags.put(cursor.getString(0), cursor.getString(1));
            }
            cursor.close();
            return flags;
        } finally {
            cursor.close();
        }
    }

    private void notifyConfigurationUpdatedListeners() {
        synchronized (this) {
            for (ConfigurationUpdatedListener listener : this.listeners) {
                listener.onConfigurationUpdated();
            }
        }
    }

    @VisibleForTesting
    public static synchronized void clearLoaderMapForTesting() {
        synchronized (ConfigurationContentLoader.class) {
            LOADERS_BY_URI.clear();
        }
    }
}
