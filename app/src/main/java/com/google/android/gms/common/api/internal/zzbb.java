package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.p001v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.internal.zzblc;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

/* compiled from: GoogleApiClientImpl */
public final class zzbb extends GoogleApiClient implements zzcd {
    final Queue<zzn<?, ?>> zza = new LinkedList();
    final Map<Api.zzc<?>, Api.Client> zzb;
    Set<Scope> zzc = new HashSet();
    Set<zzdo> zzd = null;
    final zzdr zze;
    private final Lock zzf;
    private boolean zzg;
    private final zzo zzh;
    private zzcc zzi = null;
    private final int zzj;
    /* access modifiers changed from: private */
    public final Context zzk;
    private final Looper zzl;
    private volatile boolean zzm;
    private long zzn = 120000;
    private long zzo = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
    private final zzbg zzp;
    private final GoogleApiAvailability zzq;
    private zzby zzr;
    private final ClientSettings zzs;
    private final Map<Api<?>, Boolean> zzt;
    private final Api.zza<? extends zzd, SignInOptions> zzu;
    private final zzcn zzv = new zzcn();
    private final ArrayList<zzu> zzw;
    private Integer zzx = null;
    private final zzp zzy = new zzbc(this);

    public zzbb(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, Api.zza<? extends zzd, SignInOptions> zza2, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.zzc<?>, Api.Client> map2, int i, int i2, ArrayList<zzu> arrayList, boolean z) {
        this.zzk = context;
        this.zzf = lock;
        this.zzg = false;
        this.zzh = new zzo(looper, this.zzy);
        this.zzl = looper;
        this.zzp = new zzbg(this, looper);
        this.zzq = googleApiAvailability;
        this.zzj = i;
        if (this.zzj >= 0) {
            this.zzx = Integer.valueOf(i2);
        }
        this.zzt = map;
        this.zzb = map2;
        this.zzw = arrayList;
        this.zze = new zzdr(this.zzb);
        for (GoogleApiClient.ConnectionCallbacks zza3 : list) {
            this.zzh.zza(zza3);
        }
        for (GoogleApiClient.OnConnectionFailedListener zza4 : list2) {
            this.zzh.zza(zza4);
        }
        this.zzs = clientSettings;
        this.zzu = zza2;
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(@NonNull T t) {
        zzau.zzb(t.zzc() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zzb.containsKey(t.zzc());
        String zzd2 = t.zzd() != null ? t.zzd().zzd() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(zzd2).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(zzd2);
        sb.append(" required for this call.");
        zzau.zzb(containsKey, sb.toString());
        this.zzf.lock();
        try {
            if (this.zzi == null) {
                this.zza.add(t);
                return t;
            }
            T zza2 = this.zzi.zza((zzn) t);
            this.zzf.unlock();
            return zza2;
        } finally {
            this.zzf.unlock();
        }
    }

    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull T t) {
        zzau.zzb(t.zzc() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zzb.containsKey(t.zzc());
        String zzd2 = t.zzd() != null ? t.zzd().zzd() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(zzd2).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(zzd2);
        sb.append(" required for this call.");
        zzau.zzb(containsKey, sb.toString());
        this.zzf.lock();
        try {
            if (this.zzi == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            } else if (this.zzm) {
                this.zza.add(t);
                while (!this.zza.isEmpty()) {
                    zzn remove = this.zza.remove();
                    this.zze.zza(remove);
                    remove.zzc(Status.zzc);
                }
                return t;
            } else {
                T zzb2 = this.zzi.zzb(t);
                this.zzf.unlock();
                return zzb2;
            }
        } finally {
            this.zzf.unlock();
        }
    }

    public final <L> zzcj<L> zza(@NonNull L l) {
        this.zzf.lock();
        try {
            return this.zzv.zza(l, this.zzl, "NO_TYPE");
        } finally {
            this.zzf.unlock();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [C, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    @NonNull
    public final <C extends Api.Client> C zza(@NonNull Api.zzc<C> zzc2) {
        C c = (Api.Client) this.zzb.get(zzc2);
        zzau.zza((Object) c, (Object) "Appropriate Api was not requested.");
        return c;
    }

    public final boolean zza(@NonNull Api<?> api) {
        return this.zzb.containsKey(api.zzc());
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        Api.Client client;
        if (isConnected() && (client = this.zzb.get(api.zzc())) != null && client.isConnected()) {
            return true;
        }
        return false;
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zzf.lock();
        try {
            if (!isConnected()) {
                if (!this.zzm) {
                    throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
                }
            }
            if (this.zzb.containsKey(api.zzc())) {
                ConnectionResult zza2 = this.zzi.zza(api);
                if (zza2 != null) {
                    this.zzf.unlock();
                    return zza2;
                } else if (this.zzm) {
                    return ConnectionResult.zza;
                } else {
                    Log.w("GoogleApiClientImpl", zzh());
                    Log.wtf("GoogleApiClientImpl", String.valueOf(api.zzd()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                    ConnectionResult connectionResult = new ConnectionResult(8, null);
                    this.zzf.unlock();
                    return connectionResult;
                }
            } else {
                throw new IllegalArgumentException(String.valueOf(api.zzd()).concat(" was never registered with GoogleApiClient"));
            }
        } finally {
            this.zzf.unlock();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int
     arg types: [java.util.Collection<com.google.android.gms.common.api.Api$Client>, int]
     candidates:
      com.google.android.gms.common.api.internal.zzbb.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzcd.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int */
    public final void connect() {
        this.zzf.lock();
        try {
            boolean z = false;
            if (this.zzj >= 0) {
                if (this.zzx != null) {
                    z = true;
                }
                zzau.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzx == null) {
                this.zzx = Integer.valueOf(zza((Iterable<Api.Client>) this.zzb.values(), false));
            } else if (this.zzx.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zzx.intValue());
        } finally {
            this.zzf.unlock();
        }
    }

    public final void connect(int i) {
        this.zzf.lock();
        boolean z = true;
        if (!(i == 3 || i == 1 || i == 2)) {
            z = false;
        }
        try {
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(i);
            zzau.zzb(z, sb.toString());
            zza(i);
            zzi();
        } finally {
            this.zzf.unlock();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int
     arg types: [java.util.Collection<com.google.android.gms.common.api.Api$Client>, int]
     candidates:
      com.google.android.gms.common.api.internal.zzbb.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzcd.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int */
    public final ConnectionResult blockingConnect() {
        boolean z = true;
        zzau.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.zzf.lock();
        try {
            if (this.zzj >= 0) {
                if (this.zzx == null) {
                    z = false;
                }
                zzau.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzx == null) {
                this.zzx = Integer.valueOf(zza((Iterable<Api.Client>) this.zzb.values(), false));
            } else if (this.zzx.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zza(this.zzx.intValue());
            this.zzh.zzb();
            return this.zzi.zzb();
        } finally {
            this.zzf.unlock();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int
     arg types: [java.util.Collection<com.google.android.gms.common.api.Api$Client>, int]
     candidates:
      com.google.android.gms.common.api.internal.zzbb.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzcd.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int */
    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        zzau.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        zzau.zza(timeUnit, "TimeUnit must not be null");
        this.zzf.lock();
        try {
            if (this.zzx == null) {
                this.zzx = Integer.valueOf(zza((Iterable<Api.Client>) this.zzb.values(), false));
            } else if (this.zzx.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zza(this.zzx.intValue());
            this.zzh.zzb();
            return this.zzi.zza(j, timeUnit);
        } finally {
            this.zzf.unlock();
        }
    }

    public final void disconnect() {
        this.zzf.lock();
        try {
            this.zze.zza();
            if (this.zzi != null) {
                this.zzi.zzc();
            }
            this.zzv.zza();
            for (zzn next : this.zza) {
                next.zza((zzdu) null);
                next.cancel();
            }
            this.zza.clear();
            if (this.zzi != null) {
                zzf();
                this.zzh.zza();
                this.zzf.unlock();
            }
        } finally {
            this.zzf.unlock();
        }
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final void zza(ResultStore resultStore) {
        this.zze.zza(resultStore);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzau.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzau.zza(this.zzx.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        zzdh zzdh = new zzdh(this);
        if (this.zzb.containsKey(zzblc.zza)) {
            zza(this, zzdh, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new GoogleApiClient.Builder(this.zzk).addApi(zzblc.zzb).addConnectionCallbacks(new zzbd(this, atomicReference, zzdh)).addOnConnectionFailedListener(new zzbe(this, zzdh)).setHandler(this.zzp).build();
            atomicReference.set(build);
            build.connect();
        }
        return zzdh;
    }

    /* access modifiers changed from: private */
    public final void zza(GoogleApiClient googleApiClient, zzdh zzdh, boolean z) {
        zzblc.zzc.zza(googleApiClient).setResultCallback(new zzbf(this, zzdh, z, googleApiClient));
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        zzce zzce = new zzce(fragmentActivity);
        if (this.zzj >= 0) {
            zzj.zza(zzce).zza(this.zzj);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final boolean isConnected() {
        zzcc zzcc = this.zzi;
        return zzcc != null && zzcc.zzd();
    }

    public final boolean isConnecting() {
        zzcc zzcc = this.zzi;
        return zzcc != null && zzcc.zze();
    }

    private final void zza(int i) {
        Integer num = this.zzx;
        if (num == null) {
            this.zzx = Integer.valueOf(i);
        } else if (num.intValue() != i) {
            String zzb2 = zzb(i);
            String zzb3 = zzb(this.zzx.intValue());
            StringBuilder sb = new StringBuilder(String.valueOf(zzb2).length() + 51 + String.valueOf(zzb3).length());
            sb.append("Cannot use sign-in mode: ");
            sb.append(zzb2);
            sb.append(". Mode was already set to ");
            sb.append(zzb3);
            throw new IllegalStateException(sb.toString());
        }
        if (this.zzi == null) {
            boolean z = false;
            boolean z2 = false;
            for (Api.Client next : this.zzb.values()) {
                if (next.requiresSignIn()) {
                    z = true;
                }
                if (next.providesSignIn()) {
                    z2 = true;
                }
            }
            int intValue = this.zzx.intValue();
            if (intValue != 1) {
                if (intValue != 2) {
                    if (intValue != 3) {
                    }
                } else if (z) {
                    if (this.zzg) {
                        this.zzi = new zzab(this.zzk, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw, this, true);
                        return;
                    } else {
                        this.zzi = zzw.zza(this.zzk, this, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw);
                        return;
                    }
                }
            } else if (!z) {
                throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            } else if (z2) {
                throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            if (!this.zzg || z2) {
                this.zzi = new zzbj(this.zzk, this, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw, this);
            } else {
                this.zzi = new zzab(this.zzk, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw, this, false);
            }
        }
    }

    private final void zzi() {
        this.zzh.zzb();
        this.zzi.zza();
    }

    /* access modifiers changed from: private */
    public final void zzj() {
        this.zzf.lock();
        try {
            if (this.zzm) {
                zzi();
            }
        } finally {
            this.zzf.unlock();
        }
    }

    /* access modifiers changed from: private */
    public final void zzk() {
        this.zzf.lock();
        try {
            if (zzf()) {
                zzi();
            }
        } finally {
            this.zzf.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        if (!this.zzm) {
            return false;
        }
        this.zzm = false;
        this.zzp.removeMessages(2);
        this.zzp.removeMessages(1);
        zzby zzby = this.zzr;
        if (zzby != null) {
            zzby.zza();
            this.zzr = null;
        }
        return true;
    }

    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzh.zza(connectionCallbacks);
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zzh.zzb(connectionCallbacks);
    }

    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzh.zzc(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzh.zza(onConnectionFailedListener);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzh.zzb(onConnectionFailedListener);
    }

    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzh.zzc(onConnectionFailedListener);
    }

    public final void zza(Bundle bundle) {
        while (!this.zza.isEmpty()) {
            zzb(this.zza.remove());
        }
        this.zzh.zza(bundle);
    }

    public final void zza(ConnectionResult connectionResult) {
        if (!this.zzq.isPlayServicesPossiblyUpdating(this.zzk, connectionResult.getErrorCode())) {
            zzf();
        }
        if (!this.zzm) {
            this.zzh.zza(connectionResult);
            this.zzh.zza();
        }
    }

    public final void zza(int i, boolean z) {
        if (i == 1 && !z && !this.zzm) {
            this.zzm = true;
            if (this.zzr == null) {
                this.zzr = GoogleApiAvailability.zza(this.zzk.getApplicationContext(), new zzbh(this));
            }
            zzbg zzbg = this.zzp;
            zzbg.sendMessageDelayed(zzbg.obtainMessage(1), this.zzn);
            zzbg zzbg2 = this.zzp;
            zzbg2.sendMessageDelayed(zzbg2.obtainMessage(2), this.zzo);
        }
        this.zze.zzb();
        this.zzh.zza(i);
        this.zzh.zza();
        if (i == 2) {
            zzi();
        }
    }

    public final Context zzb() {
        return this.zzk;
    }

    public final Looper zzc() {
        return this.zzl;
    }

    public final boolean zza(zzda zzda) {
        zzcc zzcc = this.zzi;
        return zzcc != null && zzcc.zza(zzda);
    }

    public final void zzd() {
        zzcc zzcc = this.zzi;
        if (zzcc != null) {
            zzcc.zzg();
        }
    }

    public final void zza(zzdo zzdo) {
        this.zzf.lock();
        try {
            if (this.zzd == null) {
                this.zzd = new HashSet();
            }
            this.zzd.add(zzdo);
        } finally {
            this.zzf.unlock();
        }
    }

    public final void zzb(zzdo zzdo) {
        this.zzf.lock();
        try {
            if (this.zzd == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.zzd.remove(zzdo)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zzg()) {
                this.zzi.zzf();
            }
        } finally {
            this.zzf.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final boolean zzg() {
        this.zzf.lock();
        try {
            if (this.zzd == null) {
                this.zzf.unlock();
                return false;
            }
            boolean z = !this.zzd.isEmpty();
            this.zzf.unlock();
            return z;
        } catch (Throwable th) {
            this.zzf.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public final String zzh() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append((CharSequence) "mContext=").println(this.zzk);
        printWriter.append((CharSequence) str).append((CharSequence) "mResuming=").print(this.zzm);
        printWriter.append((CharSequence) " mWorkQueue.size()=").print(this.zza.size());
        printWriter.append((CharSequence) " mUnconsumedApiCalls.size()=").println(this.zze.zzb.size());
        zzcc zzcc = this.zzi;
        if (zzcc != null) {
            zzcc.zza(str, fileDescriptor, printWriter, strArr);
        }
    }

    public static int zza(Iterable<Api.Client> iterable, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (Api.Client next : iterable) {
            if (next.requiresSignIn()) {
                z2 = true;
            }
            if (next.providesSignIn()) {
                z3 = true;
            }
        }
        if (!z2) {
            return 3;
        }
        if (!z3 || !z) {
            return 1;
        }
        return 2;
    }

    private static String zzb(int i) {
        if (i == 1) {
            return "SIGN_IN_MODE_REQUIRED";
        }
        if (i == 2) {
            return "SIGN_IN_MODE_OPTIONAL";
        }
        if (i != 3) {
            return "UNKNOWN";
        }
        return "SIGN_IN_MODE_NONE";
    }
}
