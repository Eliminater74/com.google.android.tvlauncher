package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.support.p001v4.util.ArraySet;
import android.view.View;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.SignInOptions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ClientSettings {
    public static final String KEY_CLIENT_SESSION_ID = "com.google.android.gms.common.internal.ClientSettings.sessionId";
    private final Account zza;
    private final Set<Scope> zzb;
    private final Set<Scope> zzc;
    private final Map<Api<?>, OptionalApiSettings> zzd;
    private final int zze;
    private final View zzf;
    private final String zzg;
    private final String zzh;
    private final SignInOptions zzi;
    private Integer zzj;

    @Hide
    public ClientSettings(Account account, Set<Scope> set, Map<Api<?>, OptionalApiSettings> map, int i, View view, String str, String str2, SignInOptions signInOptions) {
        this.zza = account;
        this.zzb = set == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(set);
        this.zzd = map == null ? Collections.EMPTY_MAP : map;
        this.zzf = view;
        this.zze = i;
        this.zzg = str;
        this.zzh = str2;
        this.zzi = signInOptions;
        HashSet hashSet = new HashSet(this.zzb);
        for (OptionalApiSettings optionalApiSettings : this.zzd.values()) {
            hashSet.addAll(optionalApiSettings.mScopes);
        }
        this.zzc = Collections.unmodifiableSet(hashSet);
    }

    public static ClientSettings createDefault(Context context) {
        return new GoogleApiClient.Builder(context).zza();
    }

    @Deprecated
    public final String getAccountName() {
        Account account = this.zza;
        if (account != null) {
            return account.name;
        }
        return null;
    }

    public final Account getAccount() {
        return this.zza;
    }

    public final Account getAccountOrDefault() {
        Account account = this.zza;
        if (account != null) {
            return account;
        }
        return new Account("<<default account>>", "com.google");
    }

    public final int getGravityForPopups() {
        return this.zze;
    }

    public final Set<Scope> getRequiredScopes() {
        return this.zzb;
    }

    public final Set<Scope> getAllRequestedScopes() {
        return this.zzc;
    }

    public final Map<Api<?>, OptionalApiSettings> getOptionalApiSettings() {
        return this.zzd;
    }

    public final String getRealClientPackageName() {
        return this.zzg;
    }

    public final String getRealClientClassName() {
        return this.zzh;
    }

    public final View getViewForPopups() {
        return this.zzf;
    }

    public final SignInOptions getSignInOptions() {
        return this.zzi;
    }

    public final Integer getClientSessionId() {
        return this.zzj;
    }

    public final void setClientSessionId(Integer num) {
        this.zzj = num;
    }

    public final Set<Scope> getApplicableScopes(Api<?> api) {
        OptionalApiSettings optionalApiSettings = this.zzd.get(api);
        if (optionalApiSettings == null || optionalApiSettings.mScopes.isEmpty()) {
            return this.zzb;
        }
        HashSet hashSet = new HashSet(this.zzb);
        hashSet.addAll(optionalApiSettings.mScopes);
        return hashSet;
    }

    public static final class OptionalApiSettings {
        public final Set<Scope> mScopes;

        public OptionalApiSettings(Set<Scope> set) {
            zzau.zza(set);
            this.mScopes = Collections.unmodifiableSet(set);
        }
    }

    public static final class zza {
        private Account zza;
        private ArraySet<Scope> zzb;
        private int zzc = 0;
        private String zzd;
        private String zze;
        private SignInOptions zzf = SignInOptions.DEFAULT;

        public final zza zza(Account account) {
            this.zza = account;
            return this;
        }

        public final zza zza(Collection<Scope> collection) {
            if (this.zzb == null) {
                this.zzb = new ArraySet<>();
            }
            this.zzb.addAll(collection);
            return this;
        }

        public final zza zza(String str) {
            this.zzd = str;
            return this;
        }

        public final zza zzb(String str) {
            this.zze = str;
            return this;
        }

        public final ClientSettings zza() {
            return new ClientSettings(this.zza, this.zzb, null, 0, null, this.zzd, this.zze, this.zzf);
        }
    }
}
