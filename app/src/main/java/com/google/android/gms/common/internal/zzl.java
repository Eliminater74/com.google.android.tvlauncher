package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import java.util.Set;

/* compiled from: GmsClient */
public abstract class zzl<T extends IInterface> extends BaseGmsClient<T> implements Api.Client, zzp {
    private final ClientSettings zzc;
    private final Set<Scope> zzd;
    private final Account zze;

    protected zzl(Context context, Looper looper, int i, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), i, clientSettings, (GoogleApiClient.ConnectionCallbacks) zzau.zza(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) zzau.zza(onConnectionFailedListener));
    }

    protected zzl(Context context, Looper looper, int i, ClientSettings clientSettings) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), 25, clientSettings, null, null);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private zzl(android.content.Context r11, android.os.Looper r12, com.google.android.gms.common.internal.GmsClientSupervisor r13, com.google.android.gms.common.GoogleApiAvailability r14, int r15, com.google.android.gms.common.internal.ClientSettings r16, com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks r17, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener r18) {
        /*
            r10 = this;
            r9 = r10
            r0 = r17
            r1 = r18
            r2 = 0
            if (r0 != 0) goto L_0x000b
            r6 = r2
            goto L_0x0011
        L_0x000b:
            com.google.android.gms.common.internal.zzm r3 = new com.google.android.gms.common.internal.zzm
            r3.<init>(r0)
            r6 = r3
        L_0x0011:
            if (r1 != 0) goto L_0x0017
            r7 = r2
            goto L_0x001d
        L_0x0017:
            com.google.android.gms.common.internal.zzn r0 = new com.google.android.gms.common.internal.zzn
            r0.<init>(r1)
            r7 = r0
        L_0x001d:
            java.lang.String r8 = r16.getRealClientClassName()
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r13
            r4 = r14
            r5 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r16
            r9.zzc = r0
            android.accounts.Account r1 = r16.getAccount()
            r9.zze = r1
            java.util.Set r0 = r16.getAllRequestedScopes()
            java.util.Set r1 = r10.zza(r0)
            java.util.Iterator r2 = r1.iterator()
        L_0x0041:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x005c
            java.lang.Object r3 = r2.next()
            com.google.android.gms.common.api.Scope r3 = (com.google.android.gms.common.api.Scope) r3
            boolean r3 = r0.contains(r3)
            if (r3 == 0) goto L_0x0054
            goto L_0x0041
        L_0x0054:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Expanding scopes is not permitted, use implied scopes instead"
            r0.<init>(r1)
            throw r0
        L_0x005c:
            r9.zzd = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzl.<init>(android.content.Context, android.os.Looper, com.google.android.gms.common.internal.GmsClientSupervisor, com.google.android.gms.common.GoogleApiAvailability, int, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void");
    }

    /* access modifiers changed from: protected */
    @Hide
    @NonNull
    public Set<Scope> zza(@NonNull Set<Scope> set) {
        return set;
    }

    public final Account getAccount() {
        return this.zze;
    }

    /* access modifiers changed from: protected */
    public final ClientSettings zzah() {
        return this.zzc;
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> getScopes() {
        return this.zzd;
    }

    public Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    public int zza() {
        return GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }
}
