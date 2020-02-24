package com.google.android.libraries.stitch.binder;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AndroidManifestModule implements Module {
    private static final String BINDER_MODULE_META_DATA_PREFIX = "module:";
    private static final int BINDER_MODULE_META_DATA_PREFIX_LENGTH = BINDER_MODULE_META_DATA_PREFIX.length();
    private static final String GENERATED_CODE_PACKAGE = "gen_binder";
    private static final String MODULE_META_DATA_PREFIX = "MODULE:";
    private static final String MODULE_META_DATA_PREFIX_LEGACY = "MODULE.";
    private static final int MODULE_META_DATA_PREFIX_LENGTH = MODULE_META_DATA_PREFIX.length();
    private static final String TAG = "Binder";
    private final Module[] mModules;

    public void configure(Context context, Class cls, Object obj, Binder binder) {
        Module$$CC.configure$$dflt$$(this, context, cls, obj, binder);
    }

    public AndroidManifestModule(Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.String.replace(char, char):java.lang.String}
     arg types: [int, int]
     candidates:
      ClspMth{java.lang.String.replace(java.lang.CharSequence, java.lang.CharSequence):java.lang.String}
      ClspMth{java.lang.String.replace(char, char):java.lang.String} */
    public AndroidManifestModule(Context context, Set<String> modulesToExclude) {
        ApplicationInfo applicationInfo = null;
        String packageName = context.getPackageName();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 128);
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf(String.format("Could not find application info for package: %s", packageName), e);
        }
        Bundle metaData = applicationInfo == null ? null : applicationInfo.metaData;
        List<Module> moduleList = new ArrayList<>();
        Set<String> binderModulesInManifest = new HashSet<>();
        if (metaData != null) {
            for (String key : applicationInfo.metaData.keySet()) {
                if (key.startsWith(MODULE_META_DATA_PREFIX_LEGACY)) {
                    moduleList.add(instantiateModule(metaData.getString(key)));
                }
                if (key.startsWith(MODULE_META_DATA_PREFIX)) {
                    moduleList.add(instantiateModule(key.substring(MODULE_META_DATA_PREFIX_LENGTH)));
                } else if (key.startsWith(BINDER_MODULE_META_DATA_PREFIX)) {
                    String className = key.substring(BINDER_MODULE_META_DATA_PREFIX_LENGTH);
                    binderModulesInManifest.add(className);
                    if (modulesToExclude == null || !modulesToExclude.contains(className)) {
                        String valueOf = String.valueOf("gen_binder.");
                        String valueOf2 = String.valueOf(className.replace('.', '$'));
                        moduleList.add(instantiateModule(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                        if (modulesToExclude != null && Log.isLoggable(TAG, 5)) {
                            String valueOf3 = String.valueOf(className);
                            Log.w(TAG, valueOf3.length() != 0 ? "***WARNING*** Root module does not include ".concat(valueOf3) : new String("***WARNING*** Root module does not include "));
                        }
                    }
                }
            }
        }
        this.mModules = (Module[]) moduleList.toArray(new Module[moduleList.size()]);
    }

    private Module instantiateModule(String className) {
        try {
            return (Module) Class.forName(className).newInstance();
        } catch (IllegalAccessException e) {
            String valueOf = String.valueOf(className);
            throw new RuntimeException(valueOf.length() != 0 ? "Failed to add stitch module ".concat(valueOf) : new String("Failed to add stitch module "), e);
        } catch (InstantiationException e2) {
            String valueOf2 = String.valueOf(className);
            throw new RuntimeException(valueOf2.length() != 0 ? "Failed to add stitch module ".concat(valueOf2) : new String("Failed to add stitch module "), e2);
        } catch (ClassNotFoundException e3) {
            String valueOf3 = String.valueOf(className);
            throw new RuntimeException(valueOf3.length() != 0 ? "Failed to add stitch module ".concat(valueOf3) : new String("Failed to add stitch module "), e3);
        }
    }

    public void configure(Context context, Class<?> type, Binder binder) {
        int i = 0;
        while (true) {
            Module[] moduleArr = this.mModules;
            if (i < moduleArr.length) {
                moduleArr[i].configure(context, type, binder);
                i++;
            } else {
                return;
            }
        }
    }
}
