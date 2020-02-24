package com.google.android.gms.phenotype;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationContentLoader {
    public static final String[] COLUMNS = {"key", "value"};
    private static final ConcurrentHashMap<Uri, ConfigurationContentLoader> zza = new ConcurrentHashMap<>();
    private final ContentResolver zzb;
    private final Uri zzc;
    private final ContentObserver zzd;
    private final Object zze = new Object();
    private volatile Map<String, String> zzf;

    private ConfigurationContentLoader(ContentResolver contentResolver, Uri uri) {
        this.zzb = contentResolver;
        this.zzc = uri;
        this.zzd = new zza(this, null);
    }

    public static void invalidateAllCaches() {
        for (ConfigurationContentLoader invalidateCache : zza.values()) {
            invalidateCache.invalidateCache();
        }
    }

    public static ConfigurationContentLoader getLoader(ContentResolver contentResolver, Uri uri) {
        ConfigurationContentLoader configurationContentLoader = zza.get(uri);
        if (configurationContentLoader != null) {
            return configurationContentLoader;
        }
        ConfigurationContentLoader configurationContentLoader2 = new ConfigurationContentLoader(contentResolver, uri);
        ConfigurationContentLoader putIfAbsent = zza.putIfAbsent(uri, configurationContentLoader2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        configurationContentLoader2.zzb.registerContentObserver(configurationContentLoader2.zzc, false, configurationContentLoader2.zzd);
        return configurationContentLoader2;
    }

    public Map<String, String> getFlags() {
        Map<String, String> zza2 = PhenotypeFlag.zza("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zza() : this.zzf;
        if (zza2 == null) {
            synchronized (this.zze) {
                Map<String, String> map = this.zzf;
                if (map == null) {
                    map = zza();
                    this.zzf = map;
                }
            }
        }
        return zza2;
    }

    public void invalidateCache() {
        synchronized (this.zze) {
            this.zzf = null;
        }
    }

    private final Map<String, String> zza() {
        HashMap hashMap = new HashMap();
        Cursor query = this.zzb.query(this.zzc, COLUMNS, null, null, null);
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    hashMap.put(query.getString(0), query.getString(1));
                } finally {
                    query.close();
                }
            }
        }
        return hashMap;
    }
}
