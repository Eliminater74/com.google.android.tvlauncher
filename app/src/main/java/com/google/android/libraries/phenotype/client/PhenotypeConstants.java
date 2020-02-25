package com.google.android.libraries.phenotype.client;

import android.content.Context;
import android.net.Uri;

public final class PhenotypeConstants {
    public static final String ACTION_UPDATE = "com.google.android.gms.phenotype.UPDATE";
    public static final String ALL_USERS = "ALL_USERS";
    public static final String CONFIGURATION_VERSION_FLAG_NAME = "__phenotype_configuration_version";
    public static final String CONTENT_PROVIDER_AUTHORITY = "com.google.android.gms.phenotype";
    public static final String EMPTY_ALTERNATE = "com.google.EMPTY";
    public static final String EXTRA_PACKAGE_NAME = "com.google.android.gms.phenotype.PACKAGE_NAME";
    public static final String EXTRA_UPDATE_REASON = "com.google.android.gms.phenotype.UPDATE_REASON";
    public static final String EXTRA_URGENT_UPDATE = "com.google.android.gms.phenotype.URGENT";
    public static final String LOGGED_OUT_USER = "";
    public static final String SERVER_TOKEN_FLAG_NAME = "__phenotype_server_token";
    public static final String SNAPSHOT_TOKEN_FLAG_NAME = "__phenotype_snapshot_token";

    private PhenotypeConstants() {
    }

    public static Uri getContentProviderUri(String configPackageName) {
        String valueOf = String.valueOf(Uri.encode(configPackageName));
        return Uri.parse(valueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(valueOf) : new String("content://com.google.android.gms.phenotype/"));
    }

    public static String getSharedPreferencesName(String configPackageName) {
        String valueOf = String.valueOf(configPackageName);
        return valueOf.length() != 0 ? "phenotype__".concat(valueOf) : new String("phenotype__");
    }

    public static String getSubpackagedName(Context context, String configPackageName) {
        return getSubpackagedName(context, configPackageName, false);
    }

    public static String getSubpackagedName(Context context, String configPackageName, boolean multiCommit) {
        if (configPackageName.contains("#")) {
            String valueOf = String.valueOf(configPackageName);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "The passed in package cannot already have a subpackage: ".concat(valueOf) : new String("The passed in package cannot already have a subpackage: "));
        }
        String str = multiCommit ? "@" : "";
        String packageName = context.getPackageName();
        StringBuilder sb = new StringBuilder(String.valueOf(configPackageName).length() + 1 + String.valueOf(str).length() + String.valueOf(packageName).length());
        sb.append(configPackageName);
        sb.append("#");
        sb.append(str);
        sb.append(packageName);
        return sb.toString();
    }

    public static boolean isMultiCommitPackage(String configPackageName) {
        int index = configPackageName.indexOf("#");
        if (index < 0 || index + 1 >= configPackageName.length() || configPackageName.charAt(index + 1) != '@') {
            return false;
        }
        return true;
    }
}
