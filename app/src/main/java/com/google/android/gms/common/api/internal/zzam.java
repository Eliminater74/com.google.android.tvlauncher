package com.google.android.gms.common.api.internal;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;

/* compiled from: GoogleApiClientConnected */
public final class zzam implements zzbi {
    /* access modifiers changed from: private */
    public final zzbj zza;
    private boolean zzb = false;

    public zzam(zzbj zzbj) {
        this.zza = zzbj;
    }

    public final void zza() {
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(T t) {
        return zzb(t);
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
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
    public final <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.common.api.internal.zzn<? extends com.google.android.gms.common.api.Result, A>> T zzb(T r4) {
        /*
            r3 = this;
            com.google.android.gms.common.api.internal.zzbj r0 = r3.zza     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.internal.zzbb r0 = r0.zzd     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.internal.zzdr r0 = r0.zze     // Catch:{ DeadObjectException -> 0x004e }
            r0.zza(r4)     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.internal.zzbj r0 = r3.zza     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.internal.zzbb r0 = r0.zzd     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.Api$zzc r1 = r4.zzc()     // Catch:{ DeadObjectException -> 0x004e }
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.Api$Client> r0 = r0.zzb     // Catch:{ DeadObjectException -> 0x004e }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.Api$Client r0 = (com.google.android.gms.common.api.Api.Client) r0     // Catch:{ DeadObjectException -> 0x004e }
            java.lang.String r1 = "Appropriate Api was not requested."
            com.google.android.gms.common.internal.zzau.zza(r0, r1)     // Catch:{ DeadObjectException -> 0x004e }
            boolean r1 = r0.isConnected()     // Catch:{ DeadObjectException -> 0x004e }
            if (r1 != 0) goto L_0x0040
            com.google.android.gms.common.api.internal.zzbj r1 = r3.zza     // Catch:{ DeadObjectException -> 0x004e }
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.ConnectionResult> r1 = r1.zzb     // Catch:{ DeadObjectException -> 0x004e }
            com.google.android.gms.common.api.Api$zzc r2 = r4.zzc()     // Catch:{ DeadObjectException -> 0x004e }
            boolean r1 = r1.containsKey(r2)     // Catch:{ DeadObjectException -> 0x004e }
            if (r1 == 0) goto L_0x0040
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ DeadObjectException -> 0x004e }
            r1 = 17
            r0.<init>(r1)     // Catch:{ DeadObjectException -> 0x004e }
            r4.zzc(r0)     // Catch:{ DeadObjectException -> 0x004e }
            goto L_0x0059
        L_0x0040:
            boolean r1 = r0 instanceof com.google.android.gms.common.internal.zzbd     // Catch:{ DeadObjectException -> 0x004e }
            if (r1 == 0) goto L_0x0049
            com.google.android.gms.common.api.Api$zze r0 = com.google.android.gms.common.internal.zzbd.zzc()     // Catch:{ DeadObjectException -> 0x004e }
            goto L_0x004a
        L_0x0049:
        L_0x004a:
            r4.zzb(r0)     // Catch:{ DeadObjectException -> 0x004e }
            goto L_0x0059
        L_0x004e:
            r0 = move-exception
            com.google.android.gms.common.api.internal.zzbj r0 = r3.zza
            com.google.android.gms.common.api.internal.zzan r1 = new com.google.android.gms.common.api.internal.zzan
            r1.<init>(r3, r3)
            r0.zza(r1)
        L_0x0059:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzam.zzb(com.google.android.gms.common.api.internal.zzn):com.google.android.gms.common.api.internal.zzn");
    }

    public final boolean zzb() {
        if (this.zzb) {
            return false;
        }
        if (this.zza.zzd.zzg()) {
            this.zzb = true;
            for (zzdo zza2 : this.zza.zzd.zzd) {
                zza2.zza();
            }
            return false;
        }
        this.zza.zza((ConnectionResult) null);
        return true;
    }

    public final void zzc() {
        if (this.zzb) {
            this.zzb = false;
            this.zza.zza(new zzao(this, this));
        }
    }

    public final void zza(Bundle bundle) {
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final void zza(int i) {
        this.zza.zza((ConnectionResult) null);
        this.zza.zze.zza(i, this.zzb);
    }

    /* access modifiers changed from: package-private */
    public final void zzd() {
        if (this.zzb) {
            this.zzb = false;
            this.zza.zzd.zze.zza();
            zzb();
        }
    }
}
