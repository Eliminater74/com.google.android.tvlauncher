package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.internal.zzai;
import com.google.android.gms.common.api.internal.zzbn;
import com.google.android.gms.common.api.internal.zzbp;
import com.google.android.gms.common.api.internal.zzbx;
import com.google.android.gms.common.api.internal.zzcj;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.common.api.internal.zzcn;
import com.google.android.gms.common.api.internal.zzcv;
import com.google.android.gms.common.api.internal.zzdb;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.common.api.internal.zzdv;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.Collections;
import java.util.Set;

public class GoogleApi<O extends Api.ApiOptions> {
    @Hide
    protected final zzbn zza;
    private final Context zzb;
    private final Api<O> zzc;
    private final O zzd;
    private final zzi<O> zze;
    private final Looper zzf;
    private final int zzg;
    private final GoogleApiClient zzh;
    private final zzdg zzi;

    @Hide
    protected GoogleApi(@NonNull Context context, Api<O> api, Looper looper) {
        zzau.zza(context, "Null context is not permitted.");
        zzau.zza(api, "Api must not be null.");
        zzau.zza(looper, "Looper must not be null.");
        this.zzb = context.getApplicationContext();
        this.zzc = api;
        this.zzd = null;
        this.zzf = looper;
        this.zze = zzi.zza(api);
        this.zzh = new zzbx(this);
        this.zza = zzbn.zza(this.zzb);
        this.zzg = this.zza.zzc();
        this.zzi = new zzh();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void
     arg types: [android.app.Activity, com.google.android.gms.common.api.Api<O>, O, com.google.android.gms.common.api.GoogleApi$zza]
     candidates:
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void */
    @MainThread
    @Hide
    @Deprecated
    public GoogleApi(@NonNull Activity activity, Api<O> api, O o, Looper looper, zzdg zzdg) {
        this(activity, (Api) api, (Api.ApiOptions) o, new zzd().zza(looper).zza(zzdg).zza());
    }

    @Hide
    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, O o, Looper looper, zzdg zzdg) {
        this(context, api, o, new zzd().zza(looper).zza(zzdg).zza());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzai.zza(android.app.Activity, com.google.android.gms.common.api.internal.zzbn, com.google.android.gms.common.api.internal.zzi<?>):void
     arg types: [android.app.Activity, com.google.android.gms.common.api.internal.zzbn, com.google.android.gms.common.api.internal.zzi<O>]
     candidates:
      com.google.android.gms.common.api.internal.zzp.zza(int, int, android.content.Intent):void
      com.google.android.gms.common.api.internal.LifecycleCallback.zza(int, int, android.content.Intent):void
      com.google.android.gms.common.api.internal.zzai.zza(android.app.Activity, com.google.android.gms.common.api.internal.zzbn, com.google.android.gms.common.api.internal.zzi<?>):void */
    @Hide
    @MainThread
    public GoogleApi(@NonNull Activity activity, Api api, Api.ApiOptions apiOptions, zza zza2) {
        zzau.zza(activity, "Null activity is not permitted.");
        zzau.zza(api, "Api must not be null.");
        zzau.zza(zza2, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zzb = activity.getApplicationContext();
        this.zzc = api;
        this.zzd = apiOptions;
        this.zzf = zza2.zzc;
        this.zze = zzi.zza(this.zzc, this.zzd);
        this.zzh = new zzbx(this);
        this.zza = zzbn.zza(this.zzb);
        this.zzg = this.zza.zzc();
        this.zzi = zza2.zzb;
        zzai.zza(activity, this.zza, (zzi<?>) this.zze);
        this.zza.zza(this);
    }

    @Hide
    public GoogleApi(@NonNull Context context, Api api, Api.ApiOptions apiOptions, zza zza2) {
        zzau.zza(context, "Null context is not permitted.");
        zzau.zza(api, "Api must not be null.");
        zzau.zza(zza2, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zzb = context.getApplicationContext();
        this.zzc = api;
        this.zzd = apiOptions;
        this.zzf = zza2.zzc;
        this.zze = zzi.zza(this.zzc, this.zzd);
        this.zzh = new zzbx(this);
        this.zza = zzbn.zza(this.zzb);
        this.zzg = this.zza.zzc();
        this.zzi = zza2.zzb;
        this.zza.zza(this);
    }

    @Hide
    @Deprecated
    public GoogleApi(@NonNull Activity activity, Api api, Api.ApiOptions apiOptions, zzdg zzdg) {
        this(activity, api, apiOptions, new zzd().zza(zzdg).zza(activity.getMainLooper()).zza());
    }

    @Hide
    @Deprecated
    public GoogleApi(@NonNull Context context, Api api, Api.ApiOptions apiOptions, zzdg zzdg) {
        this(context, api, apiOptions, new zzd().zza(zzdg).zza());
    }

    private final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zza(int i, @NonNull T t) {
        t.zzg();
        this.zza.zza(this, i, t);
        return t;
    }

    private final <TResult, A extends Api.zzb> Task<TResult> zza(int i, @NonNull zzdl<A, TResult> zzdl) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zza.zza(this, i, zzdl, taskCompletionSource, this.zzi);
        return taskCompletionSource.getTask();
    }

    @Hide
    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zza(@NonNull zzn zzn) {
        return zza(0, zzn);
    }

    @Hide
    public final <TResult, A extends Api.zzb> Task<TResult> zza(zzdl zzdl) {
        return zza(0, zzdl);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.GoogleApi.zza(int, com.google.android.gms.common.api.internal.zzn):T
     arg types: [int, T]
     candidates:
      com.google.android.gms.common.api.GoogleApi.zza(int, com.google.android.gms.common.api.internal.zzdl):com.google.android.gms.tasks.Task<TResult>
      com.google.android.gms.common.api.GoogleApi.zza(android.os.Looper, com.google.android.gms.common.api.internal.zzbp):com.google.android.gms.common.api.Api$Client
      com.google.android.gms.common.api.GoogleApi.zza(java.lang.Object, java.lang.String):com.google.android.gms.common.api.internal.zzcj<L>
      com.google.android.gms.common.api.GoogleApi.zza(android.content.Context, android.os.Handler):com.google.android.gms.common.api.internal.zzdb
      com.google.android.gms.common.api.GoogleApi.zza(com.google.android.gms.common.api.internal.zzcv, com.google.android.gms.common.api.internal.zzdv):com.google.android.gms.tasks.Task<java.lang.Void>
      com.google.android.gms.common.api.GoogleApi.zza(int, com.google.android.gms.common.api.internal.zzn):T */
    @Hide
    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull T t) {
        return zza(1, (zzn) t);
    }

    @Hide
    public final <TResult, A extends Api.zzb> Task<TResult> zzb(zzdl<A, TResult> zzdl) {
        return zza(1, zzdl);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.GoogleApi.zza(int, com.google.android.gms.common.api.internal.zzn):T
     arg types: [int, T]
     candidates:
      com.google.android.gms.common.api.GoogleApi.zza(int, com.google.android.gms.common.api.internal.zzdl):com.google.android.gms.tasks.Task<TResult>
      com.google.android.gms.common.api.GoogleApi.zza(android.os.Looper, com.google.android.gms.common.api.internal.zzbp):com.google.android.gms.common.api.Api$Client
      com.google.android.gms.common.api.GoogleApi.zza(java.lang.Object, java.lang.String):com.google.android.gms.common.api.internal.zzcj<L>
      com.google.android.gms.common.api.GoogleApi.zza(android.content.Context, android.os.Handler):com.google.android.gms.common.api.internal.zzdb
      com.google.android.gms.common.api.GoogleApi.zza(com.google.android.gms.common.api.internal.zzcv, com.google.android.gms.common.api.internal.zzdv):com.google.android.gms.tasks.Task<java.lang.Void>
      com.google.android.gms.common.api.GoogleApi.zza(int, com.google.android.gms.common.api.internal.zzn):T */
    @Hide
    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzc(@NonNull T t) {
        return zza(2, (zzn) t);
    }

    @Hide
    public final <A extends Api.zzb, T extends zzcv<A, ?>, U extends zzdv<A, ?>> Task<Void> zza(@NonNull T t, U u) {
        zzau.zza((Object) t);
        zzau.zza((Object) u);
        zzau.zza(t.zza(), "Listener has already been released.");
        zzau.zza(u.zza(), "Listener has already been released.");
        zzau.zzb(t.zza().equals(u.zza()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zza.zza(this, t, u);
    }

    @Hide
    public final Task<Boolean> zza(@NonNull zzcl<?> zzcl) {
        zzau.zza(zzcl, "Listener key cannot be null.");
        return this.zza.zza(this, zzcl);
    }

    @Hide
    public final <L> zzcj<L> zza(@NonNull L l, String str) {
        return zzcn.zzb(l, this.zzf, str);
    }

    @WorkerThread
    @Hide
    public Api.Client zza(Looper looper, zzbp<O> zzbp) {
        return this.zzc.zzb().zza(this.zzb, looper, zza().zza(), this.zzd, zzbp, zzbp);
    }

    @Hide
    public final Api<O> zzb() {
        return this.zzc;
    }

    @Hide
    public final O zzc() {
        return this.zzd;
    }

    @Hide
    public final zzi<O> zzd() {
        return this.zze;
    }

    @Hide
    public final int zze() {
        return this.zzg;
    }

    @Hide
    public GoogleApiClient asGoogleApiClient() {
        return this.zzh;
    }

    @Hide
    public final Looper zzf() {
        return this.zzf;
    }

    @Hide
    public final Context zzg() {
        return this.zzb;
    }

    @Hide
    private final ClientSettings.zza zza() {
        Account account;
        Set<Scope> set;
        GoogleSignInAccount googleSignInAccount;
        GoogleSignInAccount googleSignInAccount2;
        ClientSettings.zza zza2 = new ClientSettings.zza();
        O o = this.zzd;
        if (!(o instanceof Api.ApiOptions.HasGoogleSignInAccountOptions) || (googleSignInAccount2 = ((Api.ApiOptions.HasGoogleSignInAccountOptions) o).getGoogleSignInAccount()) == null) {
            O o2 = this.zzd;
            if (o2 instanceof Api.ApiOptions.HasAccountOptions) {
                account = ((Api.ApiOptions.HasAccountOptions) o2).getAccount();
            } else {
                account = null;
            }
        } else {
            account = googleSignInAccount2.getAccount();
        }
        ClientSettings.zza zza3 = zza2.zza(account);
        O o3 = this.zzd;
        if (!(o3 instanceof Api.ApiOptions.HasGoogleSignInAccountOptions) || (googleSignInAccount = ((Api.ApiOptions.HasGoogleSignInAccountOptions) o3).getGoogleSignInAccount()) == null) {
            set = Collections.emptySet();
        } else {
            set = googleSignInAccount.zzd();
        }
        return zza3.zza(set).zzb(this.zzb.getClass().getName()).zza(this.zzb.getPackageName());
    }

    @Hide
    public zzdb zza(Context context, Handler handler) {
        return new zzdb(context, handler, zza().zza());
    }

    @Hide
    public static class zza {
        public static final zza zza = new zzd().zza();
        public final zzdg zzb;
        public final Looper zzc;

        private zza(zzdg zzdg, Account account, Looper looper) {
            this.zzb = zzdg;
            this.zzc = looper;
        }
    }
}
