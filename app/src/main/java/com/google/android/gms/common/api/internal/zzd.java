package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;

/* compiled from: ApiCallRunner */
public final class zzd<A extends zzn<? extends Result, Api.zzb>> extends zzb {
    private final A zza;

    public zzd(int i, A a) {
        super(i);
        this.zza = a;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: A
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
    public final void zza(com.google.android.gms.common.api.internal.zzbp<?> r6) throws android.os.DeadObjectException {
        /*
            r5 = this;
            A r0 = r5.zza     // Catch:{ RuntimeException -> 0x000a }
            com.google.android.gms.common.api.Api$Client r6 = r6.zzb()     // Catch:{ RuntimeException -> 0x000a }
            r0.zzb(r6)     // Catch:{ RuntimeException -> 0x000a }
            return
        L_0x000a:
            r6 = move-exception
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status
            r1 = 10
            java.lang.Class r2 = r6.getClass()
            java.lang.String r2 = r2.getSimpleName()
            java.lang.String r6 = r6.getLocalizedMessage()
            java.lang.String r3 = java.lang.String.valueOf(r2)
            int r3 = r3.length()
            int r3 = r3 + 2
            java.lang.String r4 = java.lang.String.valueOf(r6)
            int r4 = r4.length()
            int r3 = r3 + r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            r4.append(r2)
            java.lang.String r2 = ": "
            r4.append(r2)
            r4.append(r6)
            java.lang.String r6 = r4.toString()
            r0.<init>(r1, r6)
            A r6 = r5.zza
            r6.zzc(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzd.zza(com.google.android.gms.common.api.internal.zzbp):void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: A
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
    public final void zza(@android.support.annotation.NonNull com.google.android.gms.common.api.Status r2) {
        /*
            r1 = this;
            A r0 = r1.zza
            r0.zzc(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzd.zza(com.google.android.gms.common.api.Status):void");
    }

    public final void zza(@NonNull zzaf zzaf, boolean z) {
        zzaf.zza((BasePendingResult<? extends Result>) this.zza, z);
    }
}
