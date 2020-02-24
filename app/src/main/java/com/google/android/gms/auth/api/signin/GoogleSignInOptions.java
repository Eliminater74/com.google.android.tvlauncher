package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zzo;
import com.google.android.gms.auth.api.signin.internal.zzq;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends zzbkv implements Api.ApiOptions.Optional, ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR = new zze();
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(zzd, new Scope[0]).build();
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    @Hide
    public static final Scope zza = new Scope("profile");
    @Hide
    public static final Scope zzb = new Scope("email");
    @Hide
    public static final Scope zzc = new Scope("openid");
    @Hide
    public static final Scope zzd = new Scope("https://www.googleapis.com/auth/games_lite");
    @Hide
    public static final Scope zze = new Scope("https://www.googleapis.com/auth/games");
    private static Comparator<Scope> zzp = new zzd();
    private final int zzf;
    /* access modifiers changed from: private */
    public final ArrayList<Scope> zzg;
    /* access modifiers changed from: private */
    public Account zzh;
    /* access modifiers changed from: private */
    public boolean zzi;
    /* access modifiers changed from: private */
    public final boolean zzj;
    /* access modifiers changed from: private */
    public final boolean zzk;
    /* access modifiers changed from: private */
    public String zzl;
    /* access modifiers changed from: private */
    public String zzm;
    /* access modifiers changed from: private */
    public ArrayList<zzo> zzn;
    private Map<Integer, zzo> zzo;

    @Nullable
    @Hide
    public static GoogleSignInOptions zza(@Nullable String str) throws JSONException {
        Account account;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String optString = jSONObject.optString("accountName", null);
        if (!TextUtils.isEmpty(optString)) {
            account = new Account(optString, "com.google");
        } else {
            account = null;
        }
        return new GoogleSignInOptions(3, new ArrayList(hashSet), account, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap());
    }

    public static final class Builder {
        private Set<Scope> zza = new HashSet();
        private boolean zzb;
        private boolean zzc;
        private boolean zzd;
        private String zze;
        private Account zzf;
        private String zzg;
        private Map<Integer, zzo> zzh = new HashMap();

        public Builder() {
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzau.zza(googleSignInOptions);
            this.zza = new HashSet(googleSignInOptions.zzg);
            this.zzb = googleSignInOptions.zzj;
            this.zzc = googleSignInOptions.zzk;
            this.zzd = googleSignInOptions.zzi;
            this.zze = googleSignInOptions.zzl;
            this.zzf = googleSignInOptions.zzh;
            this.zzg = googleSignInOptions.zzm;
            this.zzh = GoogleSignInOptions.zzb(googleSignInOptions.zzn);
        }

        public final Builder requestId() {
            this.zza.add(GoogleSignInOptions.zzc);
            return this;
        }

        public final Builder requestEmail() {
            this.zza.add(GoogleSignInOptions.zzb);
            return this;
        }

        public final Builder requestProfile() {
            this.zza.add(GoogleSignInOptions.zza);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.zza.add(scope);
            this.zza.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final Builder requestIdToken(String str) {
            this.zzd = true;
            this.zze = zza(str);
            return this;
        }

        public final Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final Builder requestServerAuthCode(String str, boolean z) {
            this.zzb = true;
            this.zze = zza(str);
            this.zzc = z;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zzf = new Account(zzau.zza(str), "com.google");
            return this;
        }

        @Hide
        public final Builder setAccount(Account account) {
            this.zzf = (Account) zzau.zza(account);
            return this;
        }

        public final Builder setHostedDomain(String str) {
            this.zzg = zzau.zza(str);
            return this;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (!this.zzh.containsKey(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()))) {
                if (googleSignInOptionsExtension.getImpliedScopes() != null) {
                    this.zza.addAll(googleSignInOptionsExtension.getImpliedScopes());
                }
                this.zzh.put(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()), new zzo(googleSignInOptionsExtension));
                return this;
            }
            throw new IllegalStateException("Only one extension per type may be added");
        }

        public final GoogleSignInOptions build() {
            if (this.zza.contains(GoogleSignInOptions.zze) && this.zza.contains(GoogleSignInOptions.zzd)) {
                this.zza.remove(GoogleSignInOptions.zzd);
            }
            if (this.zzd && (this.zzf == null || !this.zza.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(3, new ArrayList(this.zza), this.zzf, this.zzd, this.zzb, this.zzc, this.zze, this.zzg, this.zzh, null);
        }

        private final String zza(String str) {
            zzau.zza(str);
            String str2 = this.zze;
            zzau.zzb(str2 == null || str2.equals(str), "two different server client ids provided");
            return str;
        }
    }

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, ArrayList<zzo> arrayList2) {
        this(i, arrayList, account, z, z2, z3, str, str2, zzb(arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, zzo> map) {
        this.zzf = i;
        this.zzg = arrayList;
        this.zzh = account;
        this.zzi = z;
        this.zzj = z2;
        this.zzk = z3;
        this.zzl = str;
        this.zzm = str2;
        this.zzn = new ArrayList<>(map.values());
        this.zzo = map;
    }

    @Hide
    public final ArrayList<Scope> zza() {
        return new ArrayList<>(this.zzg);
    }

    public Scope[] getScopeArray() {
        ArrayList<Scope> arrayList = this.zzg;
        return (Scope[]) arrayList.toArray(new Scope[arrayList.size()]);
    }

    @Hide
    public final Account zzb() {
        return this.zzh;
    }

    @Hide
    public final boolean zzc() {
        return this.zzi;
    }

    @Hide
    public final boolean zzd() {
        return this.zzj;
    }

    @Hide
    public final String zze() {
        return this.zzl;
    }

    /* access modifiers changed from: private */
    public static Map<Integer, zzo> zzb(@Nullable List<zzo> list) {
        HashMap hashMap = new HashMap();
        if (list == null) {
            return hashMap;
        }
        for (zzo next : list) {
            hashMap.put(Integer.valueOf(next.zza()), next);
        }
        return hashMap;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void
     arg types: [android.os.Parcel, int, android.accounts.Account, int, int]
     candidates:
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable[], int, boolean):void
      com.google.android.gms.internal.zzbky.zza(android.os.Parcel, int, android.os.Parcelable, int, boolean):void */
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
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 1, this.zzf);
        zzbky.zzc(parcel, 2, zza(), false);
        zzbky.zza(parcel, 3, (Parcelable) this.zzh, i, false);
        zzbky.zza(parcel, 4, this.zzi);
        zzbky.zza(parcel, 5, this.zzj);
        zzbky.zza(parcel, 6, this.zzk);
        zzbky.zza(parcel, 7, this.zzl, false);
        zzbky.zza(parcel, 8, this.zzm, false);
        zzbky.zzc(parcel, 9, this.zzn, false);
        zzbky.zza(parcel, zza2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
        if (r3.zzh.equals(r4.zzh) != false) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        if (r3.zzl.equals(r4.zzl) != false) goto L_0x0062;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r4 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r4     // Catch:{ ClassCastException -> 0x007a }
            java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.zzo> r1 = r3.zzn     // Catch:{ ClassCastException -> 0x007a }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x007a }
            if (r1 > 0) goto L_0x0079
            java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.zzo> r1 = r4.zzn     // Catch:{ ClassCastException -> 0x007a }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x007a }
            if (r1 <= 0) goto L_0x0017
            goto L_0x0079
        L_0x0017:
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.zzg     // Catch:{ ClassCastException -> 0x007a }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x007a }
            java.util.ArrayList r2 = r4.zza()     // Catch:{ ClassCastException -> 0x007a }
            int r2 = r2.size()     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != r2) goto L_0x0078
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.zzg     // Catch:{ ClassCastException -> 0x007a }
            java.util.ArrayList r2 = r4.zza()     // Catch:{ ClassCastException -> 0x007a }
            boolean r1 = r1.containsAll(r2)     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != 0) goto L_0x0034
            goto L_0x0078
        L_0x0034:
            android.accounts.Account r1 = r3.zzh     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != 0) goto L_0x003d
            android.accounts.Account r1 = r4.zzh     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != 0) goto L_0x0076
            goto L_0x0047
        L_0x003d:
            android.accounts.Account r1 = r3.zzh     // Catch:{ ClassCastException -> 0x007a }
            android.accounts.Account r2 = r4.zzh     // Catch:{ ClassCastException -> 0x007a }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x007a }
            if (r1 == 0) goto L_0x0076
        L_0x0047:
            java.lang.String r1 = r3.zzl     // Catch:{ ClassCastException -> 0x007a }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x007a }
            if (r1 == 0) goto L_0x0058
            java.lang.String r1 = r4.zzl     // Catch:{ ClassCastException -> 0x007a }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x007a }
            if (r1 == 0) goto L_0x0076
            goto L_0x0062
        L_0x0058:
            java.lang.String r1 = r3.zzl     // Catch:{ ClassCastException -> 0x007a }
            java.lang.String r2 = r4.zzl     // Catch:{ ClassCastException -> 0x007a }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x007a }
            if (r1 == 0) goto L_0x0076
        L_0x0062:
            boolean r1 = r3.zzk     // Catch:{ ClassCastException -> 0x007a }
            boolean r2 = r4.zzk     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != r2) goto L_0x0076
            boolean r1 = r3.zzi     // Catch:{ ClassCastException -> 0x007a }
            boolean r2 = r4.zzi     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != r2) goto L_0x0076
            boolean r1 = r3.zzj     // Catch:{ ClassCastException -> 0x007a }
            boolean r4 = r4.zzj     // Catch:{ ClassCastException -> 0x007a }
            if (r1 != r4) goto L_0x0076
            r4 = 1
            return r4
        L_0x0076:
            return r0
        L_0x0078:
            return r0
        L_0x0079:
            return r0
        L_0x007a:
            r4 = move-exception
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = this.zzg;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((Scope) obj).zza());
        }
        Collections.sort(arrayList);
        return new zzq().zza(arrayList).zza(this.zzh).zza(this.zzl).zza(this.zzk).zza(this.zzi).zza(this.zzj).zza();
    }

    @Hide
    public final String zzf() {
        return zzg().toString();
    }

    private final JSONObject zzg() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzg, zzp);
            ArrayList arrayList = this.zzg;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                jSONArray.put(((Scope) obj).zza());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zzh != null) {
                jSONObject.put("accountName", this.zzh.name);
            }
            jSONObject.put("idTokenRequested", this.zzi);
            jSONObject.put("forceCodeForRefreshToken", this.zzk);
            jSONObject.put("serverAuthRequested", this.zzj);
            if (!TextUtils.isEmpty(this.zzl)) {
                jSONObject.put("serverClientId", this.zzl);
            }
            if (!TextUtils.isEmpty(this.zzm)) {
                jSONObject.put("hostedDomain", this.zzm);
            }
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* synthetic */ GoogleSignInOptions(int i, ArrayList arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map map, zzd zzd2) {
        this(3, arrayList, account, z, z2, z3, str, str2, map);
    }
}
