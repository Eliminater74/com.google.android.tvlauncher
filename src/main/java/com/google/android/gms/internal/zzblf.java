package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* compiled from: CommonApiImpl */
public final class zzblf implements zzble {
    public final PendingResult<Status> zza(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new zzblg(this, googleApiClient));
    }
}
