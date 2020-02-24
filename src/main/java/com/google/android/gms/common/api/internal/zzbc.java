package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.zzp;

/* compiled from: GoogleApiClientImpl */
final class zzbc implements zzp {
    private final /* synthetic */ zzbb zza;

    zzbc(zzbb zzbb) {
        this.zza = zzbb;
    }

    public final boolean isConnected() {
        return this.zza.isConnected();
    }

    public final Bundle getConnectionHint() {
        return null;
    }
}
