package com.google.android.tvlauncher.util;

import android.content.Intent;
import android.net.Uri;

public class IntentUtil {
    private static final String ACTION_FORCE_LAUNCH_ON_BOOT = "com.android.tv.action.FORCE_LAUNCH_ON_BOOT";

    public static Intent createVirtualAppIntent(String packageName, String dataUri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setPackage(packageName);
        intent.addFlags(270532608);
        if (dataUri != null) {
            intent.setData(Uri.parse(dataUri));
        }
        return intent;
    }

    public static Intent createForceLaunchOnBootIntent(String pkgName) {
        Intent intent = new Intent(ACTION_FORCE_LAUNCH_ON_BOOT);
        intent.setPackage(pkgName);
        intent.addFlags(270532608);
        return intent;
    }
}
