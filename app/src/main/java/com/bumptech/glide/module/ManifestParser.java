package com.bumptech.glide.module;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class ManifestParser {
    private static final String GLIDE_MODULE_VALUE = "GlideModule";
    private static final String TAG = "ManifestParser";
    private final Context context;

    public ManifestParser(Context context2) {
        this.context = context2;
    }

    public List<GlideModule> parse() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Loading Glide modules");
        }
        List<GlideModule> modules = new ArrayList<>();
        try {
            ApplicationInfo appInfo = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128);
            if (appInfo.metaData == null) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Got null app info metadata");
                }
                return modules;
            }
            if (Log.isLoggable(TAG, 2)) {
                String valueOf = String.valueOf(appInfo.metaData);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
                sb.append("Got app info metadata: ");
                sb.append(valueOf);
                Log.v(TAG, sb.toString());
            }
            for (String key : appInfo.metaData.keySet()) {
                if (GLIDE_MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                    modules.add(parseModule(key));
                    if (Log.isLoggable(TAG, 3)) {
                        String valueOf2 = String.valueOf(key);
                        Log.d(TAG, valueOf2.length() != 0 ? "Loaded Glide module: ".concat(valueOf2) : new String("Loaded Glide module: "));
                    }
                }
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Finished loading Glide modules");
            }
            return modules;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse GlideModules", e);
        }
    }

    private static GlideModule parseModule(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Object module = null;
            try {
                module = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (InstantiationException e) {
                throwInstantiateGlideModuleException(clazz, e);
            } catch (IllegalAccessException e2) {
                throwInstantiateGlideModuleException(clazz, e2);
            } catch (NoSuchMethodException e3) {
                throwInstantiateGlideModuleException(clazz, e3);
            } catch (InvocationTargetException e4) {
                throwInstantiateGlideModuleException(clazz, e4);
            }
            if (module instanceof GlideModule) {
                return (GlideModule) module;
            }
            String valueOf = String.valueOf(module);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 44);
            sb.append("Expected instanceof GlideModule, but found: ");
            sb.append(valueOf);
            throw new RuntimeException(sb.toString());
        } catch (ClassNotFoundException e5) {
            throw new IllegalArgumentException("Unable to find GlideModule implementation", e5);
        }
    }

    private static void throwInstantiateGlideModuleException(Class<?> clazz, Exception e) {
        String valueOf = String.valueOf(clazz);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 53);
        sb.append("Unable to instantiate GlideModule implementation for ");
        sb.append(valueOf);
        throw new RuntimeException(sb.toString(), e);
    }
}
