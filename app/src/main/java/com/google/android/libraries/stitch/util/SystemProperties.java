package com.google.android.libraries.stitch.util;

import android.util.Log;
import com.google.android.gms.people.PeopleConstants;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

public final class SystemProperties {
    private static final String CLASSNAME_SYSTEM_PROPERTIES = "android.os.SystemProperties";
    private static final String TAG = "SystemProperties";
    private static final Method sGetIntMethod;
    private static final Method sGetLongMethod;
    private static final Method sGetStringMethod;

    static {
        Method tempGetString = null;
        Method tempGetInt = null;
        Method tempGetLong = null;
        try {
            Class<?> systemProperties = Class.forName(CLASSNAME_SYSTEM_PROPERTIES);
            tempGetString = systemProperties.getMethod(PeopleConstants.Endpoints.ENDPOINT_GET, String.class, String.class);
            tempGetInt = systemProperties.getMethod("getInt", String.class, Integer.TYPE);
            tempGetLong = systemProperties.getMethod("getLong", String.class, Long.TYPE);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        } catch (Throwable th) {
            sGetStringMethod = null;
            sGetIntMethod = null;
            sGetLongMethod = null;
            throw th;
        }
        sGetStringMethod = tempGetString;
        sGetIntMethod = tempGetInt;
        sGetLongMethod = tempGetLong;
    }

    private SystemProperties() {
    }

    public static String getString(String key, @Nullable String defaultValue) {
        try {
            String value = (String) sGetStringMethod.invoke(null, key, defaultValue);
            if (defaultValue != null || !"".equals(value)) {
                return value;
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "get error", e);
            return defaultValue;
        }
    }

    public static int getInt(String key, int defaultValue) {
        try {
            if (sGetIntMethod != null) {
                return ((Integer) sGetIntMethod.invoke(null, key, Integer.valueOf(defaultValue))).intValue();
            }
        } catch (Exception e) {
            Log.e(TAG, "get error", e);
        }
        return defaultValue;
    }

    public static long getLong(String key, long defaultValue) {
        try {
            if (sGetLongMethod != null) {
                return ((Long) sGetLongMethod.invoke(null, key, Long.valueOf(defaultValue))).longValue();
            }
        } catch (Exception e) {
            Log.e(TAG, "get error", e);
        }
        return defaultValue;
    }
}
