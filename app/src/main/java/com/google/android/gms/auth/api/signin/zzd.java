package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.Scope;

import java.util.Comparator;

/* compiled from: GoogleSignInOptions */
final class zzd implements Comparator<Scope> {
    zzd() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Scope) obj).zza().compareTo(((Scope) obj2).zza());
    }
}
