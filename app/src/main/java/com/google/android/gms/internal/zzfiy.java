package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ConfigurationContentLoader */
public final class zzfiy {
    private static final ConcurrentHashMap<Uri, zzfiy> zza = new ConcurrentHashMap<>();
    private static final String[] zzg = {"key", "value"};
    private final ContentResolver zzb;
    private final Uri zzc;
    private final ContentObserver zzd;
    private final Object zze = new Object();
    private volatile Map<String, String> zzf;

    private zzfiy(ContentResolver contentResolver, Uri uri) {
        this.zzb = contentResolver;
        this.zzc = uri;
        this.zzd = new zzfiz(this, null);
    }

    public static zzfiy zza(ContentResolver contentResolver, Uri uri) {
        zzfiy zzfiy = zza.get(uri);
        if (zzfiy != null) {
            return zzfiy;
        }
        zzfiy zzfiy2 = new zzfiy(contentResolver, uri);
        zzfiy putIfAbsent = zza.putIfAbsent(uri, zzfiy2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        zzfiy2.zzb.registerContentObserver(zzfiy2.zzc, false, zzfiy2.zzd);
        return zzfiy2;
    }

    public final Map<String, String> zza() {
        Map<String, String> zzc2 = zzfja.zza("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzc() : this.zzf;
        if (zzc2 == null) {
            synchronized (this.zze) {
                Map<String, String> map = this.zzf;
                if (map == null) {
                    map = zzc();
                    this.zzf = map;
                }
            }
        }
        return zzc2 != null ? zzc2 : Collections.emptyMap();
    }

    public final void zzb() {
        synchronized (this.zze) {
            this.zzf = null;
        }
    }

    private final Map<String, String> zzc() {
        Cursor query;
        try {
            HashMap hashMap = new HashMap();
            query = this.zzb.query(this.zzc, zzg, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    hashMap.put(query.getString(0), query.getString(1));
                }
                query.close();
            }
            return hashMap;
        } catch (SQLiteException | SecurityException e) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }
}
