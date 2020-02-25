package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzau;

import org.json.JSONException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: Storage */
public final class zzaa {
    private static final Lock zza = new ReentrantLock();
    private static zzaa zzb;
    private final Lock zzc = new ReentrantLock();
    private final SharedPreferences zzd;

    private zzaa(Context context) {
        this.zzd = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzaa zza(Context context) {
        zzau.zza(context);
        zza.lock();
        try {
            if (zzb == null) {
                zzb = new zzaa(context.getApplicationContext());
            }
            return zzb;
        } finally {
            zza.unlock();
        }
    }

    private static String zzb(String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzau.zza(googleSignInAccount);
        zzau.zza(googleSignInOptions);
        String zzc2 = googleSignInAccount.zzc();
        zza(zzb("googleSignInAccount", zzc2), googleSignInAccount.zze());
        zza(zzb("googleSignInOptions", zzc2), googleSignInOptions.zzf());
    }

    /* access modifiers changed from: protected */
    public final void zza(String str, String str2) {
        this.zzc.lock();
        try {
            this.zzd.edit().putString(str, str2).apply();
        } finally {
            this.zzc.unlock();
        }
    }

    public final GoogleSignInAccount zza() {
        return zzb(zza("defaultGoogleSignInAccount"));
    }

    private final GoogleSignInAccount zzb(String str) {
        String zza2;
        if (TextUtils.isEmpty(str) || (zza2 = zza(zzb("googleSignInAccount", str))) == null) {
            return null;
        }
        try {
            return GoogleSignInAccount.zza(zza2);
        } catch (JSONException e) {
            return null;
        }
    }

    public final GoogleSignInOptions zzb() {
        return zzc(zza("defaultGoogleSignInAccount"));
    }

    private final GoogleSignInOptions zzc(String str) {
        String zza2;
        if (TextUtils.isEmpty(str) || (zza2 = zza(zzb("googleSignInOptions", str))) == null) {
            return null;
        }
        try {
            return GoogleSignInOptions.zza(zza2);
        } catch (JSONException e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final String zza(String str) {
        this.zzc.lock();
        try {
            return this.zzd.getString(str, null);
        } finally {
            this.zzc.unlock();
        }
    }

    public final void zzc() {
        String zza2 = zza("defaultGoogleSignInAccount");
        zzd("defaultGoogleSignInAccount");
        if (!TextUtils.isEmpty(zza2)) {
            zzd(zzb("googleSignInAccount", zza2));
            zzd(zzb("googleSignInOptions", zza2));
        }
    }

    private final void zzd(String str) {
        this.zzc.lock();
        try {
            this.zzd.edit().remove(str).apply();
        } finally {
            this.zzc.unlock();
        }
    }

    public final void zzd() {
        this.zzc.lock();
        try {
            this.zzd.edit().clear().apply();
        } finally {
            this.zzc.unlock();
        }
    }
}
