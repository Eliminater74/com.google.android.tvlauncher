package com.google.android.libraries.stitch.util;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public final class Bundles {
    private static final String MAP_KEYS_BUNDLE_KEY = "keys";
    private static final String MAP_VALUES_BUNDLE_KEY = "values";

    public static boolean equals(@Nullable Bundle a, @Nullable Bundle b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.size() != b.size()) {
            return false;
        }
        for (String key : a.keySet()) {
            Object aValue = a.get(key);
            Object bValue = b.get(key);
            if (!(aValue instanceof Bundle) || !(bValue instanceof Bundle)) {
                if (aValue == null) {
                    if (bValue != null || !b.containsKey(key)) {
                        return false;
                    }
                } else if (!Objects.deepEquals(aValue, bValue)) {
                    return false;
                }
            } else if (!equals((Bundle) aValue, (Bundle) bValue)) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(@Nullable Bundle b) {
        int valueHashCode;
        if (b == null) {
            return 0;
        }
        Set<String> keySet = b.keySet();
        int[] hashCodes = new int[(keySet.size() * 2)];
        int i = 0;
        for (String key : keySet) {
            int i2 = i + 1;
            hashCodes[i] = Objects.hashCode(key);
            Object value = b.get(key);
            if (value instanceof Bundle) {
                valueHashCode = hashCode((Bundle) value);
            } else {
                valueHashCode = Objects.hashCode(value);
            }
            hashCodes[i2] = valueHashCode;
            i = i2 + 1;
        }
        return Arrays.hashCode(hashCodes);
    }

    /* renamed from: of */
    public static Bundle m57of(String key, int value) {
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m56of(String key, float value) {
        Bundle bundle = new Bundle();
        bundle.putFloat(key, value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m58of(String key, long value) {
        Bundle bundle = new Bundle();
        bundle.putFloat(key, (float) value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m62of(String key, boolean value) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(key, value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m61of(String key, @Nullable String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m60of(String key, @Nullable Parcelable value) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(key, value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m59of(String key, @Nullable Bundle value) {
        Bundle bundle = new Bundle();
        bundle.putBundle(key, value);
        return bundle;
    }

    /* renamed from: of */
    public static Bundle m63of(String key, String[] value) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(key, value);
        return bundle;
    }

    public static <K extends Parcelable, V extends Parcelable> void putMap(Bundle bundle, String key, @Nullable Map<K, V> map) {
        if (map == null) {
            bundle.putBundle(key, null);
            return;
        }
        ArrayList<K> mapKeys = new ArrayList<>(map.size());
        ArrayList<V> mapValues = new ArrayList<>(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            mapKeys.add((Parcelable) entry.getKey());
            mapValues.add((Parcelable) entry.getValue());
        }
        Bundle mapBundle = new Bundle();
        mapBundle.putParcelableArrayList(MAP_KEYS_BUNDLE_KEY, mapKeys);
        mapBundle.putParcelableArrayList(MAP_VALUES_BUNDLE_KEY, mapValues);
        bundle.putBundle(key, mapBundle);
    }

    @Nullable
    public static <K extends Parcelable, V extends Parcelable> HashMap<K, V> getMap(Bundle bundle, String key) {
        Bundle mapBundle = bundle.getBundle(key);
        if (mapBundle == null) {
            return null;
        }
        ArrayList<K> mapKeys = (ArrayList) Preconditions.checkNotNull(mapBundle.getParcelableArrayList(MAP_KEYS_BUNDLE_KEY));
        ArrayList<V> mapValues = (ArrayList) Preconditions.checkNotNull(mapBundle.getParcelableArrayList(MAP_VALUES_BUNDLE_KEY));
        Preconditions.checkState(mapKeys.size() == mapValues.size());
        HashMap<K, V> result = new HashMap<>();
        for (int i = 0; i < mapKeys.size(); i++) {
            result.put((Parcelable) mapKeys.get(i), (Parcelable) mapValues.get(i));
        }
        return result;
    }
}
