package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.C0841C;

import java.util.Collection;
import java.util.HashSet;

public class UseLocationForServices {
    public static final String ACTION_SET_USE_LOCATION_FOR_SERVICES = "com.google.android.gsf.action.SET_USE_LOCATION_FOR_SERVICES";
    public static final String EXTRA_DISABLE_USE_LOCATION_FOR_SERVICES = "disable";
    public static final int USE_LOCATION_FOR_SERVICES_NOT_SET = 2;
    public static final int USE_LOCATION_FOR_SERVICES_OFF = 0;
    public static final int USE_LOCATION_FOR_SERVICES_ON = 1;
    private static final String[] GOOGLE_GEOLOCATION_ORIGINS = {"http://www.google.com", "http://www.google.co.uk"};
    private static final String TAG = "UseLocationForServices";

    public static int getUseLocationForServices(Context context) {
        return GoogleSettingsContract.Partner.getInt(context.getContentResolver(), GoogleSettingsContract.Partner.USE_LOCATION_FOR_SERVICES, 2);
    }

    @Deprecated
    public static boolean setUseLocationForServices(Context context, boolean value) {
        if (!value) {
            return forceSetUseLocationForServices(context, value);
        }
        if (getUseLocationForServices(context) == 1) {
            return false;
        }
        Intent intent = new Intent("com.google.android.gsf.GOOGLE_APPS_LOCATION_SETTINGS");
        intent.setFlags(C0841C.ENCODING_PCM_MU_LAW);
        context.startActivity(intent);
        return false;
    }

    @Deprecated
    public static boolean forceSetUseLocationForServices(Context context, boolean value) {
        setGoogleBrowserGeolocation(context, value);
        boolean success = GoogleSettingsContract.Partner.putInt(context.getContentResolver(), GoogleSettingsContract.Partner.USE_LOCATION_FOR_SERVICES, (int) value);
        context.sendBroadcast(new Intent("com.google.android.gsf.settings.GoogleLocationSettings.UPDATE_LOCATION_SETTINGS"));
        return success;
    }

    public static void registerUseLocationForServicesObserver(Context context, ContentObserver observer) {
        context.getContentResolver().registerContentObserver(GoogleSettingsContract.Partner.getUriFor(GoogleSettingsContract.Partner.USE_LOCATION_FOR_SERVICES), false, observer);
    }

    private static void setGoogleBrowserGeolocation(Context context, boolean allow) {
        try {
            ContentResolver resolver = context.getContentResolver();
            String oldValue = Settings.Secure.getString(resolver, "allowed_geolocation_origins");
            Settings.Secure.putString(resolver, "allowed_geolocation_origins", allow ? addGoogleOrigins(oldValue) : removeGoogleOrigins(oldValue));
        } catch (RuntimeException ex) {
            Log.e(TAG, "Failed to set browser geolocation permissions: " + ex);
        }
    }

    private static String addGoogleOrigins(String setting) {
        HashSet<String> origins = parseAllowGeolocationOrigins(setting);
        for (String googleOrigin : GOOGLE_GEOLOCATION_ORIGINS) {
            origins.add(googleOrigin);
        }
        return formatAllowGeolocationOrigins(origins);
    }

    private static String removeGoogleOrigins(String setting) {
        HashSet<String> origins = parseAllowGeolocationOrigins(setting);
        for (String googleOrigin : GOOGLE_GEOLOCATION_ORIGINS) {
            origins.remove(googleOrigin);
        }
        return formatAllowGeolocationOrigins(origins);
    }

    private static HashSet<String> parseAllowGeolocationOrigins(String setting) {
        HashSet<String> origins = new HashSet<>();
        if (!TextUtils.isEmpty(setting)) {
            for (String origin : setting.split("\\s+")) {
                if (!TextUtils.isEmpty(origin)) {
                    origins.add(origin);
                }
            }
        }
        return origins;
    }

    private static String formatAllowGeolocationOrigins(Collection<String> origins) {
        StringBuilder sb = new StringBuilder();
        for (String origin : origins) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(origin);
        }
        return sb.toString();
    }
}
