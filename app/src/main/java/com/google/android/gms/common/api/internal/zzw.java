package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/* compiled from: CompositeGoogleApiClient */
final class zzw implements zzcc {
    private final Context zza;
    private final zzbb zzb;
    private final Looper zzc;
    /* access modifiers changed from: private */
    public final zzbj zzd;
    /* access modifiers changed from: private */
    public final zzbj zze;
    private final Map<Api.zzc<?>, zzbj> zzf;
    private final Set<zzda> zzg = Collections.newSetFromMap(new WeakHashMap());
    private final Api.Client zzh;
    private Bundle zzi;
    /* access modifiers changed from: private */
    public ConnectionResult zzj = null;
    /* access modifiers changed from: private */
    public ConnectionResult zzk = null;
    /* access modifiers changed from: private */
    public boolean zzl = false;
    /* access modifiers changed from: private */
    public final Lock zzm;
    private int zzn = 0;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    public static zzw zza(Context context, zzbb zzbb, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.zzc<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.zza<? extends zzd, SignInOptions> zza2, ArrayList<zzu> arrayList) {
        Map<Api<?>, Boolean> map3 = map2;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Api.Client client = null;
        for (Map.Entry next : map.entrySet()) {
            Api.Client client2 = (Api.Client) next.getValue();
            if (client2.providesSignIn()) {
                client = client2;
            }
            if (client2.requiresSignIn()) {
                arrayMap.put((Api.zzc) next.getKey(), client2);
            } else {
                arrayMap2.put((Api.zzc) next.getKey(), client2);
            }
        }
        zzau.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api next2 : map2.keySet()) {
            Api.zzc<?> zzc2 = next2.zzc();
            if (arrayMap.containsKey(zzc2)) {
                arrayMap3.put(next2, map3.get(next2));
            } else if (arrayMap2.containsKey(zzc2)) {
                arrayMap4.put(next2, map3.get(next2));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zzu zzu = (zzu) obj;
            if (arrayMap3.containsKey(zzu.zza)) {
                arrayList2.add(zzu);
            } else if (arrayMap4.containsKey(zzu.zza)) {
                arrayList3.add(zzu);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzw(context, zzbb, lock, looper, googleApiAvailabilityLight, arrayMap, arrayMap2, clientSettings, zza2, client, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private zzw(Context context, zzbb zzbb, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.zzc<?>, Api.Client> map, Map<Api.zzc<?>, Api.Client> map2, ClientSettings clientSettings, Api.zza<? extends zzd, SignInOptions> zza2, Api.Client client, ArrayList<zzu> arrayList, ArrayList<zzu> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.zza = context;
        this.zzb = zzbb;
        this.zzm = lock;
        this.zzc = looper;
        this.zzh = client;
        Context context2 = context;
        Lock lock2 = lock;
        Looper looper2 = looper;
        GoogleApiAvailabilityLight googleApiAvailabilityLight2 = googleApiAvailabilityLight;
        zzbj zzbj = r3;
        zzbj zzbj2 = new zzbj(context2, this.zzb, lock2, looper2, googleApiAvailabilityLight2, map2, null, map4, null, arrayList2, new zzy(this, null));
        this.zzd = zzbj;
        this.zze = new zzbj(context2, this.zzb, lock2, looper2, googleApiAvailabilityLight2, map, clientSettings, map3, zza2, arrayList, new zzz(this, null));
        ArrayMap arrayMap = new ArrayMap();
        for (Api.zzc<?> put : map2.keySet()) {
            arrayMap.put(put, this.zzd);
        }
        for (Api.zzc<?> put2 : map.keySet()) {
            arrayMap.put(put2, this.zze);
        }
        this.zzf = Collections.unmodifiableMap(arrayMap);
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
    public final <A extends com.google.android.gms.common.api.Api.zzb, R extends com.google.android.gms.common.api.Result, T extends com.google.android.gms.common.api.internal.zzn<R, A>> T zza(@android.support.annotation.NonNull T r5) {
        /*
            r4 = this;
            boolean r0 = r4.zzc(r5)
            if (r0 == 0) goto L_0x0022
            boolean r0 = r4.zzj()
            if (r0 == 0) goto L_0x001b
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status
            r1 = 4
            r2 = 0
            android.app.PendingIntent r3 = r4.zzk()
            r0.<init>(r1, r2, r3)
            r5.zzc(r0)
            return r5
        L_0x001b:
            com.google.android.gms.common.api.internal.zzbj r0 = r4.zze
            com.google.android.gms.common.api.internal.zzn r5 = r0.zza(r5)
            return r5
        L_0x0022:
            com.google.android.gms.common.api.internal.zzbj r0 = r4.zzd
            com.google.android.gms.common.api.internal.zzn r5 = r0.zza(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzw.zza(com.google.android.gms.common.api.internal.zzn):com.google.android.gms.common.api.internal.zzn");
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
    public final <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.common.api.internal.zzn<? extends com.google.android.gms.common.api.Result, A>> T zzb(@android.support.annotation.NonNull T r5) {
        /*
            r4 = this;
            boolean r0 = r4.zzc(r5)
            if (r0 == 0) goto L_0x0022
            boolean r0 = r4.zzj()
            if (r0 == 0) goto L_0x001b
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status
            r1 = 4
            r2 = 0
            android.app.PendingIntent r3 = r4.zzk()
            r0.<init>(r1, r2, r3)
            r5.zzc(r0)
            return r5
        L_0x001b:
            com.google.android.gms.common.api.internal.zzbj r0 = r4.zze
            com.google.android.gms.common.api.internal.zzn r5 = r0.zzb(r5)
            return r5
        L_0x0022:
            com.google.android.gms.common.api.internal.zzbj r0 = r4.zzd
            com.google.android.gms.common.api.internal.zzn r5 = r0.zzb(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzw.zzb(com.google.android.gms.common.api.internal.zzn):com.google.android.gms.common.api.internal.zzn");
    }

    @Nullable
    public final ConnectionResult zza(@NonNull Api<?> api) {
        if (!this.zzf.get(api.zzc()).equals(this.zze)) {
            return this.zzd.zza(api);
        }
        if (zzj()) {
            return new ConnectionResult(4, zzk());
        }
        return this.zze.zza(api);
    }

    public final void zza() {
        this.zzn = 2;
        this.zzl = false;
        this.zzk = null;
        this.zzj = null;
        this.zzd.zza();
        this.zze.zza();
    }

    public final ConnectionResult zzb() {
        throw new UnsupportedOperationException();
    }

    public final ConnectionResult zza(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public final void zzc() {
        this.zzk = null;
        this.zzj = null;
        this.zzn = 0;
        this.zzd.zzc();
        this.zze.zzc();
        zzi();
    }

    public final boolean zzd() {
        this.zzm.lock();
        try {
            boolean z = true;
            if (!this.zzd.zzd() || (!this.zze.zzd() && !zzj() && this.zzn != 1)) {
                z = false;
            }
            return z;
        } finally {
            this.zzm.unlock();
        }
    }

    public final boolean zze() {
        this.zzm.lock();
        try {
            return this.zzn == 2;
        } finally {
            this.zzm.unlock();
        }
    }

    public final boolean zza(zzda zzda) {
        this.zzm.lock();
        try {
            if ((zze() || zzd()) && !this.zze.zzd()) {
                this.zzg.add(zzda);
                if (this.zzn == 0) {
                    this.zzn = 1;
                }
                this.zzk = null;
                this.zze.zza();
                return true;
            }
            this.zzm.unlock();
            return false;
        } finally {
            this.zzm.unlock();
        }
    }

    public final void zzf() {
        this.zzd.zzf();
        this.zze.zzf();
    }

    public final void zzg() {
        this.zzm.lock();
        try {
            boolean zze2 = zze();
            this.zze.zzc();
            this.zzk = new ConnectionResult(4);
            if (zze2) {
                new Handler(this.zzc).post(new zzx(this));
            } else {
                zzi();
            }
        } finally {
            this.zzm.unlock();
        }
    }

    /* access modifiers changed from: private */
    public final void zzh() {
        if (zzb(this.zzj)) {
            if (zzb(this.zzk) || zzj()) {
                int i = this.zzn;
                if (i != 1) {
                    if (i != 2) {
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        this.zzn = 0;
                        return;
                    }
                    this.zzb.zza(this.zzi);
                }
                zzi();
                this.zzn = 0;
                return;
            }
            ConnectionResult connectionResult = this.zzk;
            if (connectionResult == null) {
                return;
            }
            if (this.zzn == 1) {
                zzi();
                return;
            }
            zza(connectionResult);
            this.zzd.zzc();
        } else if (this.zzj == null || !zzb(this.zzk)) {
            ConnectionResult connectionResult2 = this.zzj;
            if (connectionResult2 != null && this.zzk != null) {
                if (this.zze.zzc < this.zzd.zzc) {
                    connectionResult2 = this.zzk;
                }
                zza(connectionResult2);
            }
        } else {
            this.zze.zzc();
            zza(this.zzj);
        }
    }

    private final void zza(ConnectionResult connectionResult) {
        int i = this.zzn;
        if (i != 1) {
            if (i != 2) {
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                this.zzn = 0;
            }
            this.zzb.zza(connectionResult);
        }
        zzi();
        this.zzn = 0;
    }

    private final void zzi() {
        for (zzda zza2 : this.zzg) {
            zza2.zza();
        }
        this.zzg.clear();
    }

    /* access modifiers changed from: private */
    public final void zza(int i, boolean z) {
        this.zzb.zza(i, z);
        this.zzk = null;
        this.zzj = null;
    }

    private final boolean zzj() {
        ConnectionResult connectionResult = this.zzk;
        return connectionResult != null && connectionResult.getErrorCode() == 4;
    }

    private final boolean zzc(zzn<? extends Result, ? extends Api.zzb> zzn2) {
        Api.zzc<? extends Api.zzb> zzc2 = zzn2.zzc();
        zzau.zzb(this.zzf.containsKey(zzc2), "GoogleApiClient is not configured to use the API required for this call.");
        return this.zzf.get(zzc2).equals(this.zze);
    }

    @Nullable
    private final PendingIntent zzk() {
        if (this.zzh == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zza, System.identityHashCode(this.zzb), this.zzh.getSignInIntent(), 134217728);
    }

    /* access modifiers changed from: private */
    public final void zza(Bundle bundle) {
        Bundle bundle2 = this.zzi;
        if (bundle2 == null) {
            this.zzi = bundle;
        } else if (bundle != null) {
            bundle2.putAll(bundle);
        }
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append((CharSequence) "authClient").println(":");
        this.zze.zza(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append((CharSequence) str).append((CharSequence) "anonClient").println(":");
        this.zzd.zza(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }
}
