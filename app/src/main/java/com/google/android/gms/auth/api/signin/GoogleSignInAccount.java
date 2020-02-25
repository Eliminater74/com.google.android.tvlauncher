package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoogleSignInAccount extends zzbkv implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zzb();
    @Hide
    private static Clock zza = zzh.zza();
    @Hide
    private final int zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private Uri zzg;
    private String zzh;
    private long zzi;
    private String zzj;
    private List<Scope> zzk;
    private String zzl;
    private String zzm;
    private Set<Scope> zzn = new HashSet();

    GoogleSignInAccount(int i, String str, String str2, String str3, String str4, Uri uri, String str5, long j, String str6, List<Scope> list, String str7, String str8) {
        this.zzb = i;
        this.zzc = str;
        this.zzd = str2;
        this.zze = str3;
        this.zzf = str4;
        this.zzg = uri;
        this.zzh = str5;
        this.zzi = j;
        this.zzj = str6;
        this.zzk = list;
        this.zzl = str7;
        this.zzm = str8;
    }

    @Nullable
    @Hide
    public static GoogleSignInAccount zza(@Nullable String str) throws JSONException {
        Uri uri;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString("photoUrl", null);
        if (!TextUtils.isEmpty(optString)) {
            uri = Uri.parse(optString);
        } else {
            uri = null;
        }
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        GoogleSignInAccount zza2 = zza(jSONObject.optString(TtmlNode.ATTR_ID), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), uri, Long.valueOf(parseLong), jSONObject.getString("obfuscatedIdentifier"), hashSet);
        zza2.zzh = jSONObject.optString("serverAuthCode", null);
        return zza2;
    }

    @Hide
    private static GoogleSignInAccount zza(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Uri uri, @Nullable Long l, @NonNull String str7, @NonNull Set<Scope> set) {
        Long l2;
        if (l == null) {
            l2 = Long.valueOf(zza.currentTimeMillis() / 1000);
        } else {
            l2 = l;
        }
        return new GoogleSignInAccount(3, str, str2, str3, str4, uri, null, l2.longValue(), zzau.zza(str7), new ArrayList((Collection) zzau.zza(set)), str5, str6);
    }

    @Hide
    public static GoogleSignInAccount fromAccountAndScopes(@NonNull Account account, @NonNull Scope scope, Scope... scopeArr) {
        zzau.zza(account);
        zzau.zza(scope);
        HashSet hashSet = new HashSet();
        hashSet.add(scope);
        hashSet.addAll(Arrays.asList(scopeArr));
        return zza(account, hashSet);
    }

    @Hide
    public static GoogleSignInAccount zza() {
        return zza(new Account("<<default account>>", "com.google"), new HashSet());
    }

    private static GoogleSignInAccount zza(Account account, Set<Scope> set) {
        return zza(null, null, account.name, null, null, null, null, 0L, account.name, set);
    }

    @Nullable
    public String getId() {
        return this.zzc;
    }

    @Nullable
    public String getIdToken() {
        return this.zzd;
    }

    @Nullable
    public String getEmail() {
        return this.zze;
    }

    @Nullable
    public Account getAccount() {
        String str = this.zze;
        if (str == null) {
            return null;
        }
        return new Account(str, "com.google");
    }

    @Nullable
    public String getDisplayName() {
        return this.zzf;
    }

    @Nullable
    public String getGivenName() {
        return this.zzl;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzm;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzg;
    }

    @Hide
    public final GoogleSignInAccount zza(Scope... scopeArr) {
        if (scopeArr != null) {
            Collections.addAll(this.zzn, scopeArr);
        }
        return this;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzh;
    }

    @Hide
    public final boolean zzb() {
        return zza.currentTimeMillis() / 1000 >= this.zzi - 300;
    }

    @Hide
    @NonNull
    public final String zzc() {
        return this.zzj;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.zzk);
    }

    @Hide
    @NonNull
    public final Set<Scope> zzd() {
        HashSet hashSet = new HashSet(this.zzk);
        hashSet.addAll(this.zzn);
        return hashSet;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void
     arg types: [android.os.Parcel, int, java.lang.String, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Bundle, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.IBinder, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcel, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.util.SparseArray<java.lang.String>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Boolean, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Double, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Float, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Integer, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.Long, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.util.List<java.lang.Integer>, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, double[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, float[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, int[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, long[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigDecimal[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.math.BigInteger[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, boolean[], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, byte[][], boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, java.lang.String, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.net.Uri, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zzb);
        zzbky.zza(parcel, 2, getId(), false);
        zzbky.zza(parcel, 3, getIdToken(), false);
        zzbky.zza(parcel, 4, getEmail(), false);
        zzbky.zza(parcel, 5, getDisplayName(), false);
        zzbky.zza(parcel, 6, (Parcelable) getPhotoUrl(), i, false);
        zzbky.zza(parcel, 7, getServerAuthCode(), false);
        zzbky.zza(parcel, 8, this.zzi);
        zzbky.zza(parcel, 9, this.zzj, false);
        zzbky.zzc(parcel, 10, this.zzk, false);
        zzbky.zza(parcel, 11, getGivenName(), false);
        zzbky.zza(parcel, 12, getFamilyName(), false);
        zzbky.zza(parcel, zza2);
    }

    public int hashCode() {
        return ((this.zzj.hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + zzd().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) obj;
        if (!googleSignInAccount.zzj.equals(this.zzj) || !googleSignInAccount.zzd().equals(zzd())) {
            return false;
        }
        return true;
    }

    @Hide
    public final String zze() {
        JSONObject zzf2 = zzf();
        zzf2.remove("serverAuthCode");
        return zzf2.toString();
    }

    private final JSONObject zzf() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put(TtmlNode.ATTR_ID, getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put("email", getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                jSONObject.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzi);
            jSONObject.put("obfuscatedIdentifier", this.zzj);
            JSONArray jSONArray = new JSONArray();
            Scope[] scopeArr = (Scope[]) this.zzk.toArray(new Scope[this.zzk.size()]);
            Arrays.sort(scopeArr, zza.zza);
            for (Scope zza2 : scopeArr) {
                jSONArray.put(zza2.zza());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
