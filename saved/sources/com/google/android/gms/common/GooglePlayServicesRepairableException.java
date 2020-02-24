package com.google.android.gms.common;

import android.content.Intent;
import com.google.android.gms.common.internal.Hide;

public class GooglePlayServicesRepairableException extends UserRecoverableException {
    private final int zza;

    @Hide
    public GooglePlayServicesRepairableException(int i, String str, Intent intent) {
        super(str, intent);
        this.zza = i;
    }

    public int getConnectionStatusCode() {
        return this.zza;
    }
}
