package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.zzbme;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/* compiled from: ConnectionlessGoogleApiClient */
public final class zzab implements zzcc {
    /* access modifiers changed from: private */
    public final Map<Api.zzc<?>, zzaa<?>> zza = new HashMap();
    /* access modifiers changed from: private */
    public final Map<Api.zzc<?>, zzaa<?>> zzb = new HashMap();
    private final Map<Api<?>, Boolean> zzc;
    private final zzbn zzd;
    /* access modifiers changed from: private */
    public final zzbb zze;
    /* access modifiers changed from: private */
    public final Lock zzf;
    private final Looper zzg;
    private final GoogleApiAvailabilityLight zzh;
    /* access modifiers changed from: private */
    public final Condition zzi;
    private final ClientSettings zzj;
    private final boolean zzk;
    /* access modifiers changed from: private */
    public final boolean zzl;
    private final Queue<zzn<?, ?>> zzm = new LinkedList();
    /* access modifiers changed from: private */
    public boolean zzn;
    /* access modifiers changed from: private */
    public Map<zzi<?>, ConnectionResult> zzo;
    /* access modifiers changed from: private */
    public Map<zzi<?>, ConnectionResult> zzp;
    private zzae zzq;
    /* access modifiers changed from: private */
    public ConnectionResult zzr;

    public zzab(Context context, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.zzc<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.zza<? extends zzd, SignInOptions> zza2, ArrayList<zzu> arrayList, zzbb zzbb, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        this.zzf = lock;
        this.zzg = looper;
        this.zzi = lock.newCondition();
        this.zzh = googleApiAvailabilityLight;
        this.zze = zzbb;
        this.zzc = map2;
        this.zzj = clientSettings;
        this.zzk = z;
        HashMap hashMap = new HashMap();
        for (Api next : map2.keySet()) {
            hashMap.put(next.zzc(), next);
        }
        HashMap hashMap2 = new HashMap();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzu zzu = (zzu) obj;
            hashMap2.put(zzu.zza, zzu);
        }
        boolean z5 = true;
        boolean z6 = false;
        boolean z7 = true;
        boolean z8 = false;
        for (Map.Entry next2 : map.entrySet()) {
            Api api = (Api) hashMap.get(next2.getKey());
            Api.Client client = (Api.Client) next2.getValue();
            if (!client.requiresGooglePlayServices()) {
                z2 = z6;
                z4 = z8;
                z3 = false;
            } else if (!this.zzc.get(api).booleanValue()) {
                z3 = z7;
                z4 = true;
                z2 = true;
            } else {
                z3 = z7;
                z4 = z8;
                z2 = true;
            }
            zzaa zzaa = r1;
            zzaa zzaa2 = new zzaa(context, api, looper, client, (zzu) hashMap2.get(api), clientSettings, zza2);
            this.zza.put((Api.zzc) next2.getKey(), zzaa);
            if (client.requiresSignIn()) {
                this.zzb.put((Api.zzc) next2.getKey(), zzaa);
            }
            z8 = z4;
            z7 = z3;
            z6 = z2;
        }
        this.zzl = (!z6 || z7 || z8) ? false : z5;
        this.zzd = zzbn.zza();
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(@NonNull T t) {
        if (this.zzk && zzc((zzn) t)) {
            return t;
        }
        if (!zzd()) {
            this.zzm.add(t);
            return t;
        }
        this.zze.zze.zza((BasePendingResult<? extends Result>) t);
        return this.zza.get(t.zzc()).zza((zzn) t);
    }

    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull T t) {
        Api.zzc zzc2 = t.zzc();
        if (this.zzk && zzc((zzn) t)) {
            return t;
        }
        this.zze.zze.zza((BasePendingResult<? extends Result>) t);
        return this.zza.get(zzc2).zzb((zzn) t);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    private final <T extends com.google.android.gms.common.api.internal.zzn<? extends com.google.android.gms.common.api.Result, ? extends com.google.android.gms.common.api.Api.zzb>> boolean zzc(@android.support.annotation.NonNull T r7) {
        /*
            r6 = this;
            com.google.android.gms.common.api.Api$zzc r0 = r7.zzc()
            com.google.android.gms.common.ConnectionResult r1 = r6.zza(r0)
            if (r1 == 0) goto L_0x0034
            int r1 = r1.getErrorCode()
            r2 = 4
            if (r1 != r2) goto L_0x0034
            com.google.android.gms.common.api.Status r1 = new com.google.android.gms.common.api.Status
            r3 = 0
            com.google.android.gms.common.api.internal.zzbn r4 = r6.zzd
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.internal.zzaa<?>> r5 = r6.zza
            java.lang.Object r0 = r5.get(r0)
            com.google.android.gms.common.api.internal.zzaa r0 = (com.google.android.gms.common.api.internal.zzaa) r0
            com.google.android.gms.common.api.internal.zzi r0 = r0.zzd()
            com.google.android.gms.common.api.internal.zzbb r5 = r6.zze
            int r5 = java.lang.System.identityHashCode(r5)
            android.app.PendingIntent r0 = r4.zza(r0, r5)
            r1.<init>(r2, r3, r0)
            r7.zzc(r1)
            r7 = 1
            return r7
        L_0x0034:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzab.zzc(com.google.android.gms.common.api.internal.zzn):boolean");
    }

    public final void zza() {
        this.zzf.lock();
        try {
            if (!this.zzn) {
                this.zzn = true;
                this.zzo = null;
                this.zzp = null;
                this.zzq = null;
                this.zzr = null;
                this.zzd.zzd();
                this.zzd.zza(this.zza.values()).addOnCompleteListener(new zzbme(this.zzg), new zzad(this));
                this.zzf.unlock();
            }
        } finally {
            this.zzf.unlock();
        }
    }

    public final ConnectionResult zzb() {
        zza();
        while (zze()) {
            try {
                this.zzi.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (zzd()) {
            return ConnectionResult.zza;
        }
        ConnectionResult connectionResult = this.zzr;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    public final ConnectionResult zza(long j, TimeUnit timeUnit) {
        zza();
        long nanos = timeUnit.toNanos(j);
        while (zze()) {
            if (nanos <= 0) {
                try {
                    zzc();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            } else {
                nanos = this.zzi.awaitNanos(nanos);
            }
        }
        if (zzd()) {
            return ConnectionResult.zza;
        }
        ConnectionResult connectionResult = this.zzr;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    public final void zzc() {
        this.zzf.lock();
        try {
            this.zzn = false;
            this.zzo = null;
            this.zzp = null;
            if (this.zzq != null) {
                this.zzq.zza();
                this.zzq = null;
            }
            this.zzr = null;
            while (!this.zzm.isEmpty()) {
                zzn remove = this.zzm.remove();
                remove.zza((zzdu) null);
                remove.cancel();
            }
            this.zzi.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    @Nullable
    public final ConnectionResult zza(@NonNull Api<?> api) {
        return zza(api.zzc());
    }

    @Nullable
    private final ConnectionResult zza(@NonNull Api.zzc<?> zzc2) {
        this.zzf.lock();
        try {
            zzaa zzaa = this.zza.get(zzc2);
            if (this.zzo != null && zzaa != null) {
                return this.zzo.get(zzaa.zzd());
            }
            this.zzf.unlock();
            return null;
        } finally {
            this.zzf.unlock();
        }
    }

    public final boolean zzd() {
        this.zzf.lock();
        try {
            return this.zzo != null && this.zzr == null;
        } finally {
            this.zzf.unlock();
        }
    }

    public final boolean zze() {
        this.zzf.lock();
        try {
            return this.zzo == null && this.zzn;
        } finally {
            this.zzf.unlock();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f A[Catch:{ all -> 0x0046 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzh() {
        /*
            r3 = this;
            java.util.concurrent.locks.Lock r0 = r3.zzf
            r0.lock()
            boolean r0 = r3.zzn     // Catch:{ all -> 0x0046 }
            r1 = 0
            if (r0 == 0) goto L_0x0040
            boolean r0 = r3.zzk     // Catch:{ all -> 0x0046 }
            if (r0 != 0) goto L_0x000f
            goto L_0x0040
        L_0x000f:
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.internal.zzaa<?>> r0 = r3.zzb     // Catch:{ all -> 0x0046 }
            java.util.Set r0 = r0.keySet()     // Catch:{ all -> 0x0046 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0046 }
        L_0x0019:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x0039
            java.lang.Object r2 = r0.next()     // Catch:{ all -> 0x0046 }
            com.google.android.gms.common.api.Api$zzc r2 = (com.google.android.gms.common.api.Api.zzc) r2     // Catch:{ all -> 0x0046 }
            com.google.android.gms.common.ConnectionResult r2 = r3.zza(r2)     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x0033
            boolean r2 = r2.isSuccess()     // Catch:{ all -> 0x0046 }
            if (r2 != 0) goto L_0x0032
            goto L_0x0033
        L_0x0032:
            goto L_0x0019
        L_0x0033:
            java.util.concurrent.locks.Lock r0 = r3.zzf
            r0.unlock()
            return r1
        L_0x0039:
            java.util.concurrent.locks.Lock r0 = r3.zzf
            r0.unlock()
            r0 = 1
            return r0
        L_0x0040:
            java.util.concurrent.locks.Lock r0 = r3.zzf
            r0.unlock()
            return r1
        L_0x0046:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = r3.zzf
            r1.unlock()
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzab.zzh():boolean");
    }

    /* JADX INFO: finally extract failed */
    public final boolean zza(zzda zzda) {
        this.zzf.lock();
        try {
            if (!this.zzn || zzh()) {
                this.zzf.unlock();
                return false;
            }
            this.zzd.zzd();
            this.zzq = new zzae(this, zzda);
            this.zzd.zza(this.zzb.values()).addOnCompleteListener(new zzbme(this.zzg), this.zzq);
            this.zzf.unlock();
            return true;
        } catch (Throwable th) {
            this.zzf.unlock();
            throw th;
        }
    }

    public final void zzg() {
        this.zzf.lock();
        try {
            this.zzd.zze();
            if (this.zzq != null) {
                this.zzq.zza();
                this.zzq = null;
            }
            if (this.zzp == null) {
                this.zzp = new ArrayMap(this.zzb.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            for (zzaa<?> zzd2 : this.zzb.values()) {
                this.zzp.put(zzd2.zzd(), connectionResult);
            }
            if (this.zzo != null) {
                this.zzo.putAll(this.zzp);
            }
        } finally {
            this.zzf.unlock();
        }
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final void zzf() {
    }

    /* access modifiers changed from: private */
    public final void zzi() {
        ClientSettings clientSettings = this.zzj;
        if (clientSettings == null) {
            this.zze.zzc = Collections.emptySet();
            return;
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = this.zzj.getOptionalApiSettings();
        for (Api next : optionalApiSettings.keySet()) {
            ConnectionResult zza2 = zza(next);
            if (zza2 != null && zza2.isSuccess()) {
                hashSet.addAll(optionalApiSettings.get(next).mScopes);
            }
        }
        this.zze.zzc = hashSet;
    }

    /* access modifiers changed from: private */
    public final void zzj() {
        while (!this.zzm.isEmpty()) {
            zzb(this.zzm.remove());
        }
        this.zze.zza((Bundle) null);
    }

    /* access modifiers changed from: private */
    public final boolean zza(zzaa<?> zzaa, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && this.zzc.get(zzaa.zzb()).booleanValue() && zzaa.zza().requiresGooglePlayServices() && this.zzh.isUserResolvableError(connectionResult.getErrorCode());
    }

    /* access modifiers changed from: private */
    @Nullable
    public final ConnectionResult zzk() {
        ConnectionResult connectionResult = null;
        ConnectionResult connectionResult2 = null;
        int i = 0;
        int i2 = 0;
        for (zzaa next : this.zza.values()) {
            Api zzb2 = next.zzb();
            ConnectionResult connectionResult3 = this.zzo.get(next.zzd());
            if (!connectionResult3.isSuccess() && (!this.zzc.get(zzb2).booleanValue() || connectionResult3.hasResolution() || this.zzh.isUserResolvableError(connectionResult3.getErrorCode()))) {
                if (connectionResult3.getErrorCode() != 4 || !this.zzk) {
                    int zza2 = zzb2.zza().zza();
                    if (connectionResult == null || i > zza2) {
                        connectionResult = connectionResult3;
                        i = zza2;
                    }
                } else {
                    int zza3 = zzb2.zza().zza();
                    if (connectionResult2 == null || i2 > zza3) {
                        connectionResult2 = connectionResult3;
                        i2 = zza3;
                    }
                }
            }
        }
        if (connectionResult == null || connectionResult2 == null || i <= i2) {
            return connectionResult;
        }
        return connectionResult2;
    }
}
