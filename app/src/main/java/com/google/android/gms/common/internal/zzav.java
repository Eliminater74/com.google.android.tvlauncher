package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

/* compiled from: ResolveAccountRequest */
public final class zzav extends zzbkv {
    public static final Parcelable.Creator<zzav> CREATOR = new zzaw();
    private final int zza;
    private final Account zzb;
    private final int zzc;
    private final GoogleSignInAccount zzd;

    zzav(int i, Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this.zza = i;
        this.zzb = account;
        this.zzc = i2;
        this.zzd = googleSignInAccount;
    }

    public zzav(Account account, int i, GoogleSignInAccount googleSignInAccount) {
        this(2, account, i, googleSignInAccount);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.accounts.Account, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, com.google.android.gms.auth.api.signin.GoogleSignInAccount, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public final void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zza);
        zzbky.zza(parcel, 2, (Parcelable) this.zzb, i, false);
        zzbky.zza(parcel, 3, this.zzc);
        zzbky.zza(parcel, 4, (Parcelable) this.zzd, i, false);
        zzbky.zza(parcel, zza2);
    }
}
