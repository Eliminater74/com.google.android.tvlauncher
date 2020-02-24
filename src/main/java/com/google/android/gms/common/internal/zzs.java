package com.google.android.gms.common.internal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

@Hide
/* compiled from: GmsIntents */
public final class zzs {
    private static final Uri zza;
    private static final Uri zzb;

    public static Intent zza(String str) {
        Uri fromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    public static Intent zza(String str, @Nullable String str2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri.Builder appendQueryParameter = Uri.parse("market://details").buildUpon().appendQueryParameter(TtmlNode.ATTR_ID, str);
        if (!TextUtils.isEmpty(str2)) {
            appendQueryParameter.appendQueryParameter("pcampaignid", str2);
        }
        intent.setData(appendQueryParameter.build());
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    public static Intent zza() {
        Intent intent = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
        intent.setPackage("com.google.android.wearable.app");
        return intent;
    }

    public static boolean zza(PackageManager packageManager, Intent intent) {
        return packageManager.resolveActivity(intent, 65536) != null;
    }

    static {
        Uri parse = Uri.parse("https://plus.google.com/");
        zza = parse;
        zzb = parse.buildUpon().appendPath("circles").appendPath("find").build();
    }
}
