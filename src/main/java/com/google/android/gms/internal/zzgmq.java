package com.google.android.gms.internal;

import com.google.android.gms.internal.zzgmp;
import com.google.android.gms.internal.zzgmq;

/* compiled from: AbstractMessageLite */
public abstract class zzgmq<MessageType extends zzgmp<MessageType, BuilderType>, BuilderType extends zzgmq<MessageType, BuilderType>> implements zzgpu {
    /* renamed from: zza */
    public abstract BuilderType clone();

    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    public final /* synthetic */ zzgpu zza(zzgpt zzgpt) {
        if (zzr().getClass().isInstance(zzgpt)) {
            return zza((zzgmp) zzgpt);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
