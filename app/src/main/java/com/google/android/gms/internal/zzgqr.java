package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

/* compiled from: SmallSortedMap */
final class zzgqr extends zzgqx {
    private final /* synthetic */ zzgqo zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private zzgqr(zzgqo zzgqo) {
        super(zzgqo, null);
        this.zza = zzgqo;
    }

    /* synthetic */ zzgqr(zzgqo zzgqo, zzgqp zzgqp) {
        this(zzgqo);
    }

    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzgqq(this.zza, null);
    }
}
