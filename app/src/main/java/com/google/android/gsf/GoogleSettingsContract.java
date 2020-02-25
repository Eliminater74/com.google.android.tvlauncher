package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GoogleSettingsContract {
    public static final String AUTHORITY = "com.google.settings";
    private static final boolean DEBUG = false;
    private static final String TAG = "GoogleSettings";

    static class UriCacheValue {
        AtomicBoolean invalidateCache = new AtomicBoolean(false);
        HashMap<String, String> valueCache = new HashMap<>();
        Object versionToken = new Object();

        UriCacheValue() {
        }
    }

    public static class NameValueTable implements BaseColumns {
        public static final String NAME = "name";
        public static final String VALUE = "value";
        static HashMap<Uri, UriCacheValue> sCache = new HashMap<>();

        private static UriCacheValue ensureCacheInitializedLocked(ContentResolver cr, Uri uri) {
            UriCacheValue cacheValue = sCache.get(uri);
            if (cacheValue == null) {
                UriCacheValue cacheValue2 = new UriCacheValue();
                sCache.put(uri, cacheValue2);
                final UriCacheValue finalCacheValue = cacheValue2;
                cr.registerContentObserver(uri, true, new ContentObserver(null) {
                    public void onChange(boolean selfChange) {
                        finalCacheValue.invalidateCache.set(true);
                    }
                });
                return cacheValue2;
            } else if (!cacheValue.invalidateCache.getAndSet(false)) {
                return cacheValue;
            } else {
                synchronized (cacheValue) {
                    cacheValue.valueCache.clear();
                    cacheValue.versionToken = new Object();
                }
                return cacheValue;
            }
        }

        protected static Object getVersionToken(ContentResolver resolver, Uri uri) {
            UriCacheValue cacheValue;
            Object obj;
            synchronized (NameValueTable.class) {
                cacheValue = ensureCacheInitializedLocked(resolver, uri);
            }
            synchronized (cacheValue) {
                obj = cacheValue.versionToken;
            }
            return obj;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            r3 = r12.query(r13, new java.lang.String[]{"value"}, "name=?", new java.lang.String[]{r14}, null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
            if (r3 == null) goto L_0x004d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
            if (r3.moveToFirst() != false) goto L_0x0040;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0040, code lost:
            r2 = r3.getString(0);
            putCache(r1, r0, r14, r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
            r3.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            putCache(r1, r0, r14, null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
            if (r3 == null) goto L_0x0057;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
            r3.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0057, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0058, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
            android.util.Log.e(com.google.android.gsf.GoogleSettingsContract.TAG, "Can't get key " + r14 + " from " + r13, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x007a, code lost:
            if (r3 == null) goto L_0x007d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x007d, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x007e, code lost:
            if (r3 != null) goto L_0x0080;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0080, code lost:
            r3.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0083, code lost:
            throw r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected static java.lang.String getString(android.content.ContentResolver r12, android.net.Uri r13, java.lang.String r14) {
            /*
                java.lang.Class<com.google.android.gsf.GoogleSettingsContract$NameValueTable> r0 = com.google.android.gsf.GoogleSettingsContract.NameValueTable.class
                monitor-enter(r0)
                com.google.android.gsf.GoogleSettingsContract$UriCacheValue r1 = ensureCacheInitializedLocked(r12, r13)     // Catch:{ all -> 0x0087 }
                monitor-exit(r0)     // Catch:{ all -> 0x0087 }
                monitor-enter(r1)
                java.lang.Object r0 = r1.versionToken     // Catch:{ all -> 0x0084 }
                java.util.HashMap<java.lang.String, java.lang.String> r2 = r1.valueCache     // Catch:{ all -> 0x0084 }
                boolean r2 = r2.containsKey(r14)     // Catch:{ all -> 0x0084 }
                if (r2 == 0) goto L_0x001d
                java.util.HashMap<java.lang.String, java.lang.String> r2 = r1.valueCache     // Catch:{ all -> 0x0084 }
                java.lang.Object r2 = r2.get(r14)     // Catch:{ all -> 0x0084 }
                java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0084 }
                monitor-exit(r1)     // Catch:{ all -> 0x0084 }
                return r2
            L_0x001d:
                monitor-exit(r1)     // Catch:{ all -> 0x0084 }
                r2 = 0
                r3 = 0
                r4 = 1
                java.lang.String[] r7 = new java.lang.String[r4]     // Catch:{ SQLException -> 0x005a }
                java.lang.String r5 = "value"
                r11 = 0
                r7[r11] = r5     // Catch:{ SQLException -> 0x005a }
                java.lang.String r8 = "name=?"
                java.lang.String[] r9 = new java.lang.String[r4]     // Catch:{ SQLException -> 0x005a }
                r9[r11] = r14     // Catch:{ SQLException -> 0x005a }
                r10 = 0
                r5 = r12
                r6 = r13
                android.database.Cursor r4 = r5.query(r6, r7, r8, r9, r10)     // Catch:{ SQLException -> 0x005a }
                r3 = r4
                if (r3 == 0) goto L_0x004d
                boolean r4 = r3.moveToFirst()     // Catch:{ SQLException -> 0x005a }
                if (r4 != 0) goto L_0x0040
                goto L_0x004d
            L_0x0040:
                java.lang.String r4 = r3.getString(r11)     // Catch:{ SQLException -> 0x005a }
                r2 = r4
                putCache(r1, r0, r14, r2)     // Catch:{ SQLException -> 0x005a }
            L_0x0049:
                r3.close()
                goto L_0x007d
            L_0x004d:
                r4 = 0
                putCache(r1, r0, r14, r4)     // Catch:{ SQLException -> 0x005a }
                if (r3 == 0) goto L_0x0057
                r3.close()
            L_0x0057:
                return r4
            L_0x0058:
                r4 = move-exception
                goto L_0x007e
            L_0x005a:
                r4 = move-exception
                java.lang.String r5 = "GoogleSettings"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
                r6.<init>()     // Catch:{ all -> 0x0058 }
                java.lang.String r7 = "Can't get key "
                r6.append(r7)     // Catch:{ all -> 0x0058 }
                r6.append(r14)     // Catch:{ all -> 0x0058 }
                java.lang.String r7 = " from "
                r6.append(r7)     // Catch:{ all -> 0x0058 }
                r6.append(r13)     // Catch:{ all -> 0x0058 }
                java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0058 }
                android.util.Log.e(r5, r6, r4)     // Catch:{ all -> 0x0058 }
                if (r3 == 0) goto L_0x007d
                goto L_0x0049
            L_0x007d:
                return r2
            L_0x007e:
                if (r3 == 0) goto L_0x0083
                r3.close()
            L_0x0083:
                throw r4
            L_0x0084:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0084 }
                throw r0
            L_0x0087:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0087 }
                throw r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gsf.GoogleSettingsContract.NameValueTable.getString(android.content.ContentResolver, android.net.Uri, java.lang.String):java.lang.String");
        }

        protected static boolean putString(ContentResolver resolver, Uri uri, String name, String value) {
            UriCacheValue cacheValue;
            synchronized (NameValueTable.class) {
                cacheValue = ensureCacheInitializedLocked(resolver, uri);
            }
            removeCachedValue(cacheValue, name);
            try {
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("value", value);
                resolver.insert(uri, values);
                return true;
            } catch (SQLException e) {
                Log.e(GoogleSettingsContract.TAG, "Can't set key " + name + " in " + uri, e);
                return false;
            } catch (IllegalArgumentException e2) {
                Log.e(GoogleSettingsContract.TAG, "Can't set key " + name + " in " + uri, e2);
                return false;
            }
        }

        private static void removeCachedValue(UriCacheValue cacheValue, String key) {
            synchronized (cacheValue) {
                cacheValue.valueCache.remove(key);
            }
        }

        private static void putCache(UriCacheValue cacheValue, Object version, String key, String value) {
            synchronized (cacheValue) {
                if (version == cacheValue.versionToken) {
                    cacheValue.valueCache.put(key, value);
                }
            }
        }

        public static Uri getUriFor(Uri uri, String name) {
            return Uri.withAppendedPath(uri, name);
        }
    }

    public static final class Partner extends NameValueTable {
        public static final String CHROME_CLIENT_ID = "chrome_client_id";
        public static final String CLIENT_ID = "client_id";
        public static final Uri CONTENT_URI = Uri.parse("content://com.google.settings/partner");
        public static final String DATA_STORE_VERSION = "data_store_version";
        public static final String LOGGING_ID2 = "logging_id2";
        public static final String MAPS_CLIENT_ID = "maps_client_id";
        public static final String MARKET_CHECKIN = "market_checkin";
        public static final String MARKET_CLIENT_ID = "market_client_id";
        public static final String NETWORK_LOCATION_OPT_IN = "network_location_opt_in";
        public static final String RLZ = "rlz";
        public static final String SEARCH_CLIENT_ID = "search_client_id";
        public static final String SHOPPER_CLIENT_ID = "shopper_client_id";
        public static final String USE_LOCATION_FOR_ADS = "use_location_for_ads";
        public static final String USE_LOCATION_FOR_SERVICES = "use_location_for_services";
        public static final String VOICESEARCH_CLIENT_ID = "voicesearch_client_id";
        public static final String WALLET_CLIENT_ID = "wallet_client_id";
        public static final String YOUTUBE_CLIENT_ID = "youtube_client_id";

        public static String getString(ContentResolver resolver, String name) {
            return getString(resolver, CONTENT_URI, name);
        }

        public static String getString(ContentResolver resolver, String name, String defaultValue) {
            String value = getString(resolver, name);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        public static boolean putString(ContentResolver resolver, String name, String value) {
            return putString(resolver, CONTENT_URI, name, value);
        }

        public static boolean putInt(ContentResolver resolver, String name, int value) {
            return putString(resolver, name, String.valueOf(value));
        }

        /* JADX INFO: Multiple debug info for r1v2 int: [D('value' int), D('e' java.lang.NumberFormatException)] */
        public static int getInt(ContentResolver resolver, String name, int defValue) {
            String valString = getString(resolver, name);
            if (valString == null) {
                return defValue;
            }
            try {
                return Integer.parseInt(valString);
            } catch (NumberFormatException e) {
                return defValue;
            }
        }

        /* JADX INFO: Multiple debug info for r1v2 long: [D('value' long), D('e' java.lang.NumberFormatException)] */
        public static long getLong(ContentResolver resolver, String name, long defValue) {
            String valString = getString(resolver, name);
            if (valString == null) {
                return defValue;
            }
            try {
                return Long.parseLong(valString);
            } catch (NumberFormatException e) {
                return defValue;
            }
        }

        public static Uri getUriFor(String name) {
            return getUriFor(CONTENT_URI, name);
        }

        public static Object getVersionToken(ContentResolver resolver) {
            return getVersionToken(resolver, CONTENT_URI);
        }
    }
}
