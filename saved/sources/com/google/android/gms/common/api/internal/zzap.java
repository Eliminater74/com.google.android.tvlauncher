package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.zzax;
import com.google.android.gms.internal.zzemf;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

/* compiled from: GoogleApiClientConnecting */
public final class zzap implements zzbi {
    /* access modifiers changed from: private */
    public final zzbj zza;
    /* access modifiers changed from: private */
    public final Lock zzb;
    /* access modifiers changed from: private */
    public final Context zzc;
    /* access modifiers changed from: private */
    public final GoogleApiAvailabilityLight zzd;
    private ConnectionResult zze;
    private int zzf;
    private int zzg = 0;
    private int zzh;
    private final Bundle zzi = new Bundle();
    private final Set<Api.zzc> zzj = new HashSet();
    /* access modifiers changed from: private */
    public zzd zzk;
    private boolean zzl;
    /* access modifiers changed from: private */
    public boolean zzm;
    private boolean zzn;
    /* access modifiers changed from: private */
    public IAccountAccessor zzo;
    private boolean zzp;
    private boolean zzq;
    private final ClientSettings zzr;
    private final Map<Api<?>, Boolean> zzs;
    private final Api.zza<? extends zzd, SignInOptions> zzt;
    private ArrayList<Future<?>> zzu = new ArrayList<>();

    public zzap(zzbj zzbj, ClientSettings clientSettings, Map<Api<?>, Boolean> map, GoogleApiAvailabilityLight googleApiAvailabilityLight, Api.zza<? extends zzd, SignInOptions> zza2, Lock lock, Context context) {
        this.zza = zzbj;
        this.zzr = clientSettings;
        this.zzs = map;
        this.zzd = googleApiAvailabilityLight;
        this.zzt = zza2;
        this.zzb = lock;
        this.zzc = context;
    }

    public final void zza() {
        this.zza.zzb.clear();
        this.zzm = false;
        this.zze = null;
        this.zzg = 0;
        this.zzl = true;
        this.zzn = false;
        this.zzp = false;
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (Api next : this.zzs.keySet()) {
            Api.Client client = this.zza.zza.get(next.zzc());
            z |= next.zza().zza() == 1;
            boolean booleanValue = this.zzs.get(next).booleanValue();
            if (client.requiresSignIn()) {
                this.zzm = true;
                if (booleanValue) {
                    this.zzj.add(next.zzc());
                } else {
                    this.zzl = false;
                }
            }
            hashMap.put(client, new zzar(this, next, booleanValue));
        }
        if (z) {
            this.zzm = false;
        }
        if (this.zzm) {
            this.zzr.setClientSessionId(Integer.valueOf(System.identityHashCode(this.zza.zzd)));
            zzay zzay = new zzay(this, null);
            Api.zza<? extends zzd, SignInOptions> zza2 = this.zzt;
            Context context = this.zzc;
            Looper zzc2 = this.zza.zzd.zzc();
            ClientSettings clientSettings = this.zzr;
            this.zzk = (zzd) zza2.zza(context, zzc2, clientSettings, clientSettings.getSignInOptions(), zzay, zzay);
        }
        this.zzh = this.zza.zza.size();
        this.zzu.add(zzbm.zza().submit(new zzas(this, hashMap)));
    }

    /* access modifiers changed from: private */
    public final boolean zzd() {
        this.zzh--;
        int i = this.zzh;
        if (i > 0) {
            return false;
        }
        if (i < 0) {
            Log.w("GoogleApiClientConnecting", this.zza.zzd.zzh());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zzb(new ConnectionResult(8, null));
            return false;
        }
        ConnectionResult connectionResult = this.zze;
        if (connectionResult == null) {
            return true;
        }
        this.zza.zzc = this.zzf;
        zzb(connectionResult);
        return false;
    }

    /* access modifiers changed from: private */
    public final void zza(zzemf zzemf) {
        if (zzb(0)) {
            ConnectionResult zza2 = zzemf.zza();
            if (zza2.isSuccess()) {
                zzax zzb2 = zzemf.zzb();
                ConnectionResult zzb3 = zzb2.zzb();
                if (!zzb3.isSuccess()) {
                    String valueOf = String.valueOf(zzb3);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                    sb.append("Sign-in succeeded with resolve account failure: ");
                    sb.append(valueOf);
                    Log.wtf("GoogleApiClientConnecting", sb.toString(), new Exception());
                    zzb(zzb3);
                    return;
                }
                this.zzn = true;
                this.zzo = zzb2.zza();
                this.zzp = zzb2.zzc();
                this.zzq = zzb2.zzd();
                zze();
            } else if (zza(zza2)) {
                zzg();
                zze();
            } else {
                zzb(zza2);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zze() {
        if (this.zzh == 0) {
            if (!this.zzm || this.zzn) {
                ArrayList arrayList = new ArrayList();
                this.zzg = 1;
                this.zzh = this.zza.zza.size();
                for (Api.zzc next : this.zza.zza.keySet()) {
                    if (!this.zza.zzb.containsKey(next)) {
                        arrayList.add(this.zza.zza.get(next));
                    } else if (zzd()) {
                        zzf();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zzu.add(zzbm.zza().submit(new zzav(this, arrayList)));
                }
            }
        }
    }

    public final void zza(Bundle bundle) {
        if (zzb(1)) {
            if (bundle != null) {
                this.zzi.putAll(bundle);
            }
            if (zzd()) {
                zzf();
            }
        }
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zzb(1)) {
            zzb(connectionResult, api, z);
            if (zzd()) {
                zzf();
            }
        }
    }

    private final void zzf() {
        this.zza.zzi();
        zzbm.zza().execute(new zzaq(this));
        zzd zzd2 = this.zzk;
        if (zzd2 != null) {
            if (this.zzp) {
                zzd2.zza(this.zzo, this.zzq);
            }
            zza(false);
        }
        for (Api.zzc<?> zzc2 : this.zza.zzb.keySet()) {
            this.zza.zza.get(zzc2).disconnect();
        }
        this.zza.zze.zza(this.zzi.isEmpty() ? null : this.zzi);
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(T t) {
        this.zza.zzd.zza.add(t);
        return t;
    }

    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    public final void zzc() {
    }

    public final boolean zzb() {
        zzh();
        zza(true);
        this.zza.zza((ConnectionResult) null);
        return true;
    }

    public final void zza(int i) {
        zzb(new ConnectionResult(8, null));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        if (r7 != false) goto L_0x0026;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(com.google.android.gms.common.ConnectionResult r5, com.google.android.gms.common.api.Api<?> r6, boolean r7) {
        /*
            r4 = this;
            com.google.android.gms.common.api.Api$zzd r0 = r6.zza()
            int r0 = r0.zza()
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L_0x0026
            boolean r7 = r5.hasResolution()
            if (r7 == 0) goto L_0x0015
            r7 = 1
            goto L_0x0024
        L_0x0015:
            com.google.android.gms.common.GoogleApiAvailabilityLight r7 = r4.zzd
            int r3 = r5.getErrorCode()
            android.content.Intent r7 = r7.getErrorResolutionIntent(r3)
            if (r7 == 0) goto L_0x0023
            r7 = 1
            goto L_0x0024
        L_0x0023:
            r7 = 0
        L_0x0024:
            if (r7 == 0) goto L_0x002f
        L_0x0026:
            com.google.android.gms.common.ConnectionResult r7 = r4.zze
            if (r7 == 0) goto L_0x0030
            int r7 = r4.zzf
            if (r0 >= r7) goto L_0x002f
            goto L_0x0030
        L_0x002f:
            goto L_0x0031
        L_0x0030:
            r1 = 1
        L_0x0031:
            if (r1 == 0) goto L_0x0037
            r4.zze = r5
            r4.zzf = r0
        L_0x0037:
            com.google.android.gms.common.api.internal.zzbj r7 = r4.zza
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.ConnectionResult> r7 = r7.zzb
            com.google.android.gms.common.api.Api$zzc r6 = r6.zzc()
            r7.put(r6, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzap.zzb(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    /* access modifiers changed from: private */
    public final void zzg() {
        this.zzm = false;
        this.zza.zzd.zzc = Collections.emptySet();
        for (Api.zzc next : this.zzj) {
            if (!this.zza.zzb.containsKey(next)) {
                this.zza.zzb.put(next, new ConnectionResult(17, null));
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean zza(ConnectionResult connectionResult) {
        return this.zzl && !connectionResult.hasResolution();
    }

    /* access modifiers changed from: private */
    public final void zzb(ConnectionResult connectionResult) {
        zzh();
        zza(!connectionResult.hasResolution());
        this.zza.zza(connectionResult);
        this.zza.zze.zza(connectionResult);
    }

    private final void zza(boolean z) {
        zzd zzd2 = this.zzk;
        if (zzd2 != null) {
            if (zzd2.isConnected() && z) {
                this.zzk.zzc();
            }
            this.zzk.disconnect();
            this.zzo = null;
        }
    }

    private final void zzh() {
        ArrayList arrayList = this.zzu;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zzu.clear();
    }

    /* access modifiers changed from: private */
    public final Set<Scope> zzi() {
        ClientSettings clientSettings = this.zzr;
        if (clientSettings == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = this.zzr.getOptionalApiSettings();
        for (Api next : optionalApiSettings.keySet()) {
            if (!this.zza.zzb.containsKey(next.zzc())) {
                hashSet.addAll(optionalApiSettings.get(next).mScopes);
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: private */
    public final boolean zzb(int i) {
        if (this.zzg == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zza.zzd.zzh());
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
        sb.append("Unexpected callback in ");
        sb.append(valueOf);
        Log.w("GoogleApiClientConnecting", sb.toString());
        int i2 = this.zzh;
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("mRemainingConnections=");
        sb2.append(i2);
        Log.w("GoogleApiClientConnecting", sb2.toString());
        String zzc2 = zzc(this.zzg);
        String zzc3 = zzc(i);
        StringBuilder sb3 = new StringBuilder(String.valueOf(zzc2).length() + 70 + String.valueOf(zzc3).length());
        sb3.append("GoogleApiClient connecting is in step ");
        sb3.append(zzc2);
        sb3.append(" but received callback for step ");
        sb3.append(zzc3);
        Log.wtf("GoogleApiClientConnecting", sb3.toString(), new Exception());
        zzb(new ConnectionResult(8, null));
        return false;
    }

    private static String zzc(int i) {
        if (i == 0) {
            return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
        }
        if (i != 1) {
            return "UNKNOWN";
        }
        return "STEP_GETTING_REMOTE_SERVICE";
    }
}
