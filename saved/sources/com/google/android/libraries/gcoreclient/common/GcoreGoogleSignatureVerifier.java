package com.google.android.libraries.gcoreclient.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
public interface GcoreGoogleSignatureVerifier {
    boolean isPackageGoogleSigned(PackageManager packageManager, PackageInfo packageInfo);

    boolean isPackageGoogleSigned(PackageManager packageManager, String str);

    boolean isUidGoogleSigned(PackageManager packageManager, int i);

    void verifyPackageIsGoogleSigned(PackageManager packageManager, String str) throws SecurityException;

    void verifyUidIsGoogleSigned(PackageManager packageManager, int i) throws SecurityException;
}
