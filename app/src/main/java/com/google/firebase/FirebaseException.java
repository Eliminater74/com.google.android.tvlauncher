package com.google.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.common.internal.zzau;

public class FirebaseException extends Exception {
    @Deprecated
    protected FirebaseException() {
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String */
    public FirebaseException(@NonNull String str) {
        super(zzau.zza(str, (Object) "Detail message must not be empty"));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
     arg types: [java.lang.String, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String */
    public FirebaseException(@NonNull String str, Throwable th) {
        super(zzau.zza(str, (Object) "Detail message must not be empty"), th);
    }
}
