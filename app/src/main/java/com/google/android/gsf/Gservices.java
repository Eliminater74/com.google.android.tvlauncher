package com.google.android.gsf;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class Gservices {
    public static final String CHANGED_ACTION = "com.google.gservices.intent.action.GSERVICES_CHANGED";
    public static final Uri CONTENT_PREFIX_URI = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    public static final Pattern FALSE_PATTERN = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    public static final String OVERRIDE_ACTION = "com.google.gservices.intent.action.GSERVICES_OVERRIDE";
    public static final String PERMISSION_READ_GSERVICES = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    public static final String TAG = "Gservices";
    public static final Pattern TRUE_PATTERN = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    /* access modifiers changed from: private */
    public static final AtomicBoolean sInvalidateCache = new AtomicBoolean();
    static final HashMap<String, Boolean> sBooleanCache = new HashMap<>();
    static final HashMap<String, Float> sFloatCache = new HashMap<>();
    static final HashMap<String, Integer> sIntCache = new HashMap<>();
    static final HashMap<String, Long> sLongCache = new HashMap<>();
    private static final boolean DEBUG = false;
    static HashMap<String, String> sCache;
    static String[] sPreloadedPrefixes = new String[0];
    private static boolean sPreloaded;
    private static Object sVersionToken;

    private static void ensureCacheInitializedLocked(ContentResolver cr) {
        if (sCache == null) {
            sInvalidateCache.set(false);
            sCache = new HashMap<>();
            sVersionToken = new Object();
            sPreloaded = false;
            cr.registerContentObserver(CONTENT_URI, true, new ContentObserver(null) {
                public void onChange(boolean selfChange) {
                    Gservices.sInvalidateCache.set(true);
                }
            });
        } else if (sInvalidateCache.getAndSet(false)) {
            sCache.clear();
            sBooleanCache.clear();
            sIntCache.clear();
            sLongCache.clear();
            sFloatCache.clear();
            sVersionToken = new Object();
            sPreloaded = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0057, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005c, code lost:
        r0 = r9.query(com.google.android.gsf.Gservices.CONTENT_URI, null, null, new java.lang.String[]{r10}, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006c, code lost:
        if (r0 != null) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x006f, code lost:
        if (r0 == null) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0071, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0074, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0079, code lost:
        if (r0.moveToFirst() != false) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007b, code lost:
        putStringCache(r1, r10, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0084, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r2 = r0.getString(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0089, code lost:
        if (r2 == null) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x008f, code lost:
        if (r2.equals(r11) == false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0091, code lost:
        r2 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0092, code lost:
        putStringCache(r1, r10, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0095, code lost:
        if (r2 == null) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0097, code lost:
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0099, code lost:
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009a, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x009e, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x009f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a0, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00a3, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getString(android.content.ContentResolver r9, java.lang.String r10, java.lang.String r11) {
        /*
            java.lang.Class<com.google.android.gsf.Gservices> r0 = com.google.android.gsf.Gservices.class
            monitor-enter(r0)
            ensureCacheInitializedLocked(r9)     // Catch:{ all -> 0x00a4 }
            java.lang.Object r1 = com.google.android.gsf.Gservices.sVersionToken     // Catch:{ all -> 0x00a4 }
            java.util.HashMap<java.lang.String, java.lang.String> r2 = com.google.android.gsf.Gservices.sCache     // Catch:{ all -> 0x00a4 }
            boolean r2 = r2.containsKey(r10)     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x001f
            java.util.HashMap<java.lang.String, java.lang.String> r2 = com.google.android.gsf.Gservices.sCache     // Catch:{ all -> 0x00a4 }
            java.lang.Object r2 = r2.get(r10)     // Catch:{ all -> 0x00a4 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x001c
            r3 = r2
            goto L_0x001d
        L_0x001c:
            r3 = r11
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r3
        L_0x001f:
            java.lang.String[] r2 = com.google.android.gsf.Gservices.sPreloadedPrefixes     // Catch:{ all -> 0x00a4 }
            int r3 = r2.length     // Catch:{ all -> 0x00a4 }
            r4 = 0
            r5 = 0
        L_0x0024:
            if (r5 >= r3) goto L_0x005b
            r6 = r2[r5]     // Catch:{ all -> 0x00a4 }
            boolean r7 = r10.startsWith(r6)     // Catch:{ all -> 0x00a4 }
            if (r7 == 0) goto L_0x0058
            boolean r2 = com.google.android.gsf.Gservices.sPreloaded     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x003a
            java.util.HashMap<java.lang.String, java.lang.String> r2 = com.google.android.gsf.Gservices.sCache     // Catch:{ all -> 0x00a4 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x0056
        L_0x003a:
            java.lang.String[] r2 = com.google.android.gsf.Gservices.sPreloadedPrefixes     // Catch:{ all -> 0x00a4 }
            bulkCacheByPrefixLocked(r9, r2)     // Catch:{ all -> 0x00a4 }
            java.util.HashMap<java.lang.String, java.lang.String> r2 = com.google.android.gsf.Gservices.sCache     // Catch:{ all -> 0x00a4 }
            boolean r2 = r2.containsKey(r10)     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x0056
            java.util.HashMap<java.lang.String, java.lang.String> r2 = com.google.android.gsf.Gservices.sCache     // Catch:{ all -> 0x00a4 }
            java.lang.Object r2 = r2.get(r10)     // Catch:{ all -> 0x00a4 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x0053
            r3 = r2
            goto L_0x0054
        L_0x0053:
            r3 = r11
        L_0x0054:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r3
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return r11
        L_0x0058:
            int r5 = r5 + 1
            goto L_0x0024
        L_0x005b:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            android.net.Uri r3 = com.google.android.gsf.Gservices.CONTENT_URI
            r0 = 0
            r5 = 0
            r8 = 1
            java.lang.String[] r6 = new java.lang.String[r8]
            r6[r4] = r10
            r7 = 0
            r2 = r9
            r4 = r0
            android.database.Cursor r0 = r2.query(r3, r4, r5, r6, r7)
            if (r0 != 0) goto L_0x0075
            if (r0 == 0) goto L_0x0074
            r0.close()
        L_0x0074:
            return r11
        L_0x0075:
            boolean r2 = r0.moveToFirst()     // Catch:{ all -> 0x009f }
            if (r2 != 0) goto L_0x0085
            r2 = 0
            putStringCache(r1, r10, r2)     // Catch:{ all -> 0x009f }
            r0.close()
            return r11
        L_0x0085:
            java.lang.String r2 = r0.getString(r8)     // Catch:{ all -> 0x009f }
            if (r2 == 0) goto L_0x0092
            boolean r3 = r2.equals(r11)     // Catch:{ all -> 0x009f }
            if (r3 == 0) goto L_0x0092
            r2 = r11
        L_0x0092:
            putStringCache(r1, r10, r2)     // Catch:{ all -> 0x009f }
            if (r2 == 0) goto L_0x0099
            r3 = r2
            goto L_0x009a
        L_0x0099:
            r3 = r11
        L_0x009a:
            r0.close()
            return r3
        L_0x009f:
            r2 = move-exception
            r0.close()
            throw r2
        L_0x00a4:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gsf.Gservices.getString(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static void putStringCache(Object version, String key, String value) {
        synchronized (Gservices.class) {
            if (version == sVersionToken) {
                sCache.put(key, value);
            }
        }
    }

    @Deprecated
    public static String getString(ContentResolver cr, String key) {
        return getString(cr, key, null);
    }

    public static int getInt(ContentResolver cr, String key, int defValue) {
        int value;
        Object version = getVersionToken(cr);
        Integer cacheValue = (Integer) getValue(sIntCache, key, Integer.valueOf(defValue));
        if (cacheValue != null) {
            return cacheValue.intValue();
        }
        String valString = getString(cr, key);
        if (valString == null) {
            value = defValue;
        } else {
            try {
                value = Integer.parseInt(valString);
                cacheValue = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                value = defValue;
            }
        }
        putValueAndRemoveFromStringCache(version, sIntCache, key, cacheValue);
        return value;
    }

    public static float getFloat(ContentResolver cr, String key, float defValue) {
        float value;
        Object version = getVersionToken(cr);
        Float cacheValue = (Float) getValue(sFloatCache, key, Float.valueOf(defValue));
        if (cacheValue != null) {
            return cacheValue.floatValue();
        }
        String valString = getString(cr, key);
        if (valString == null) {
            value = defValue;
        } else {
            try {
                value = Float.parseFloat(valString);
                cacheValue = Float.valueOf(value);
            } catch (NumberFormatException e) {
                value = defValue;
            }
        }
        putValueAndRemoveFromStringCache(version, sFloatCache, key, cacheValue);
        return value;
    }

    public static long getLong(ContentResolver cr, String key, long defValue) {
        long value;
        Object version = getVersionToken(cr);
        Long cacheValue = (Long) getValue(sLongCache, key, Long.valueOf(defValue));
        if (cacheValue != null) {
            return cacheValue.longValue();
        }
        String valString = getString(cr, key);
        if (valString == null) {
            value = defValue;
        } else {
            try {
                value = Long.parseLong(valString);
                cacheValue = Long.valueOf(value);
            } catch (NumberFormatException e) {
                value = defValue;
            }
        }
        putValueAndRemoveFromStringCache(version, sLongCache, key, cacheValue);
        return value;
    }

    public static boolean getBoolean(ContentResolver cr, String key, boolean defValue) {
        boolean value;
        Object version = getVersionToken(cr);
        Boolean cacheValue = (Boolean) getValue(sBooleanCache, key, Boolean.valueOf(defValue));
        if (cacheValue != null) {
            return cacheValue.booleanValue();
        }
        String valString = getString(cr, key);
        if (valString == null || valString.equals("")) {
            value = defValue;
        } else if (TRUE_PATTERN.matcher(valString).matches()) {
            value = true;
            cacheValue = true;
        } else if (FALSE_PATTERN.matcher(valString).matches()) {
            value = false;
            cacheValue = false;
        } else {
            Log.w(TAG, "attempt to read gservices key " + key + " (value \"" + valString + "\") as boolean");
            value = defValue;
        }
        putValueAndRemoveFromStringCache(version, sBooleanCache, key, cacheValue);
        return value;
    }

    public static Map<String, String> getStringsByPrefix(ContentResolver cr, String... prefixes) {
        Cursor c = cr.query(CONTENT_PREFIX_URI, null, null, prefixes, null);
        TreeMap<String, String> out = new TreeMap<>();
        if (c == null) {
            return out;
        }
        while (c.moveToNext()) {
            try {
                out.put(c.getString(0), c.getString(1));
            } finally {
                c.close();
            }
        }
        return out;
    }

    public static void bulkCacheByPrefix(ContentResolver cr, String... prefixes) {
        if (prefixes.length != 0) {
            synchronized (Gservices.class) {
                ensureCacheInitializedLocked(cr);
                String[] addedPrefixes = addNewPrefixesLocked(prefixes);
                if (sPreloaded) {
                    if (!sCache.isEmpty()) {
                        if (addedPrefixes.length != 0) {
                            bulkCacheByPrefixLocked(cr, addedPrefixes);
                        }
                    }
                }
                bulkCacheByPrefixLocked(cr, sPreloadedPrefixes);
            }
        }
    }

    private static String[] addNewPrefixesLocked(String[] newPrefixes) {
        HashSet<String> newPreloadedPrefixes = new HashSet<>((((sPreloadedPrefixes.length + newPrefixes.length) * 4) / 3) + 1);
        newPreloadedPrefixes.addAll(Arrays.asList(sPreloadedPrefixes));
        ArrayList<String> addedPrefixes = new ArrayList<>();
        for (String newPrefix : newPrefixes) {
            if (newPreloadedPrefixes.add(newPrefix)) {
                addedPrefixes.add(newPrefix);
            }
        }
        if (addedPrefixes.isEmpty()) {
            return new String[0];
        }
        sPreloadedPrefixes = (String[]) newPreloadedPrefixes.toArray(new String[newPreloadedPrefixes.size()]);
        return (String[]) addedPrefixes.toArray(new String[addedPrefixes.size()]);
    }

    private static void bulkCacheByPrefixLocked(ContentResolver cr, String[] prefixes) {
        sCache.putAll(getStringsByPrefix(cr, prefixes));
        sPreloaded = true;
    }

    public static Object getVersionToken(ContentResolver cr) {
        Object obj;
        synchronized (Gservices.class) {
            ensureCacheInitializedLocked(cr);
            obj = sVersionToken;
        }
        return obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T getValue(java.util.HashMap<java.lang.String, T> r3, java.lang.String r4, T r5) {
        /*
            java.lang.Class<com.google.android.gsf.Gservices> r0 = com.google.android.gsf.Gservices.class
            monitor-enter(r0)
            boolean r1 = r3.containsKey(r4)     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x0014
            java.lang.Object r1 = r3.get(r4)     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x0011
            r2 = r1
            goto L_0x0012
        L_0x0011:
            r2 = r5
        L_0x0012:
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            return r2
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            r0 = 0
            return r0
        L_0x0017:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gsf.Gservices.getValue(java.util.HashMap, java.lang.String, java.lang.Object):java.lang.Object");
    }

    private static <T> void putValueAndRemoveFromStringCache(Object version, HashMap<String, T> cache, String key, T value) {
        synchronized (Gservices.class) {
            if (version == sVersionToken) {
                cache.put(key, value);
                sCache.remove(key);
            }
        }
    }
}
