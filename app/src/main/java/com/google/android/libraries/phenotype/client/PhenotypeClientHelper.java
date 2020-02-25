package com.google.android.libraries.phenotype.client;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;

import com.google.common.base.Optional;

public final class PhenotypeClientHelper {
    private static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    private static final Object isValidContentProviderLock = new Object();
    static volatile Optional<Boolean> isValidContentProvider = Optional.absent();

    private static boolean isGmsCorePreinstalled(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo("com.google.android.gms", 0).flags & 129) != 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean validateContentProvider(Context context, Uri uri) {
        boolean isProviderGmsCore;
        String authority = uri.getAuthority();
        if (!PhenotypeConstants.CONTENT_PROVIDER_AUTHORITY.equals(authority)) {
            StringBuilder sb = new StringBuilder(String.valueOf(authority).length() + 91);
            sb.append(authority);
            sb.append(" is an unsupported authority. Only ");
            sb.append(PhenotypeConstants.CONTENT_PROVIDER_AUTHORITY);
            sb.append(" authority is supported.");
            throw new IllegalArgumentException(sb.toString());
        } else if (isValidContentProvider.isPresent()) {
            return isValidContentProvider.get().booleanValue();
        } else {
            synchronized (isValidContentProviderLock) {
                if (isValidContentProvider.isPresent()) {
                    boolean booleanValue = isValidContentProvider.get().booleanValue();
                    return booleanValue;
                }
                boolean z = true;
                if ("com.google.android.gms".equals(context.getPackageName())) {
                    isProviderGmsCore = true;
                } else {
                    ProviderInfo providerInfo = context.getPackageManager().resolveContentProvider(PhenotypeConstants.CONTENT_PROVIDER_AUTHORITY, 0);
                    isProviderGmsCore = providerInfo != null && "com.google.android.gms".equals(providerInfo.packageName);
                }
                if (!isProviderGmsCore || !isGmsCorePreinstalled(context)) {
                    z = false;
                }
                isValidContentProvider = Optional.m80of(Boolean.valueOf(z));
                return isValidContentProvider.get().booleanValue();
            }
        }
    }
}
